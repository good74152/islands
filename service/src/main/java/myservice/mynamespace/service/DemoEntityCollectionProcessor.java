/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package myservice.mynamespace.service;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import myservice.mynamespace.data.Storage;
import myservice.mynamespace.util.Util;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.apache.olingo.server.api.uri.UriResourcePrimitiveProperty;
import org.apache.olingo.server.api.uri.queryoption.CountOption;
import org.apache.olingo.server.api.uri.queryoption.OrderByItem;
import org.apache.olingo.server.api.uri.queryoption.OrderByOption;
import org.apache.olingo.server.api.uri.queryoption.SelectOption;
import org.apache.olingo.server.api.uri.queryoption.SkipOption;
import org.apache.olingo.server.api.uri.queryoption.TopOption;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.api.uri.queryoption.expression.Member;

public class DemoEntityCollectionProcessor implements EntityCollectionProcessor {

	  private OData odata;
	  private ServiceMetadata serviceMetadata;
	  private Storage storage;

	  public DemoEntityCollectionProcessor(Storage storage) {
	    this.storage = storage;
	  }

	  public void init(OData odata, ServiceMetadata serviceMetadata) {
	    this.odata = odata;
	    this.serviceMetadata = serviceMetadata;
	  }

	  /*
	   * This method is invoked when a collection of entities has to be read.
	   * In our example, this can be either a "normal" read operation, or a navigation:
	   * 
	   * Example for "normal" read entity set operation:
	   * http://localhost:8080/DemoService/DemoService.svc/Categories
	   * 
	   * Example for navigation
	   * http://localhost:8080/DemoService/DemoService.svc/Categories(3)/Products
	   */
	  public void readEntityCollection(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat) throws ODataApplicationException, SerializerException {

	    EdmEntitySet responseEdmEntitySet = null; // 用來建立ContextURL
	    EntityCollection responseEntityCollection = null; // 用來建立response body
	    EdmEntityType responseEdmEntityType = null;

	    // 第1步 : 從uriInfo取得requested entityset
	    List<UriResource> resourceParts = uriInfo.getUriResourceParts();
	    int segmentCount = resourceParts.size();

	    UriResource uriResource = resourceParts.get(0); // first segment為EntitySet
	    if (!(uriResource instanceof UriResourceEntitySet)) {
	      throw new ODataApplicationException("Only EntitySet is supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
	    }

	    UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) uriResource;
	    EdmEntitySet startEdmEntitySet = uriResourceEntitySet.getEntitySet();

	    if (segmentCount == 1) { // case 1 : 只有一個segment
	      responseEdmEntitySet = startEdmEntitySet; // entityset建立response body

	      // 第2步 : 根據requested entitysetname取得storage的data
	      responseEntityCollection = storage.readEntitySetData(startEdmEntitySet);
	    } else if (segmentCount == 2) { // case 2 : 有2個segment

	      UriResource lastSegment = resourceParts.get(1); 
	      if (lastSegment instanceof UriResourceNavigation) {
	        UriResourceNavigation uriResourceNavigation = (UriResourceNavigation) lastSegment;
	        EdmNavigationProperty edmNavigationProperty = uriResourceNavigation.getProperty();
	        EdmEntityType targetEntityType = edmNavigationProperty.getType();
	        if (!edmNavigationProperty.containsTarget()) {
	       // from Categories(1) to Products
	          responseEdmEntitySet = Util.getNavigationTargetEntitySet(startEdmEntitySet, edmNavigationProperty);
	        } else {
	          responseEdmEntitySet = startEdmEntitySet;
	          responseEdmEntityType = targetEntityType;
	        }

	        // 先取得first segment的entity
	        List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
	        //keypredicates是用來得到first segment的id
	        Entity sourceEntity = storage.readEntityData(startEdmEntitySet, keyPredicates);
	        // error handling : id超過範圍
	        if (sourceEntity == null) {
	          throw new ODataApplicationException("Entity not found.",
	              HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ROOT);
	        }
	        // 取得navigation的entityset
	        // note: we don't need to check uriResourceNavigation.isCollection(),
	        // because we are the EntityCollectionProcessor
	        responseEntityCollection = storage.getRelatedEntityCollection(sourceEntity, targetEntityType);
	      }
	    } else { // error handling : 過多的segment
	      throw new ODataApplicationException("Not supported",
	          HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
	    }
	    
	    // 使用system query options
	    // 根據query option調整result set
	    List<Entity> entityList = responseEntityCollection.getEntities();
	    EntityCollection returnEntityCollection = new EntityCollection();
	    
	    // handle $orderby
	    OrderByOption orderByOption = uriInfo.getOrderByOption();
	    if (orderByOption != null) {
	    	List<OrderByItem> orderItemList = orderByOption.getOrders();
	    	final OrderByItem orderByItem = orderItemList.get(0);
	    	Expression expression = orderByItem.getExpression();
	    	if(expression instanceof Member){
	    		UriInfoResource resourcePath = ((Member)expression).getResourcePath();
	    		uriResource = resourcePath.getUriResourceParts().get(0);
	    		if (uriResource instanceof UriResourcePrimitiveProperty) {
	    			EdmProperty edmProperty = ((UriResourcePrimitiveProperty)uriResource).getProperty();
	    			final String sortPropertyName = edmProperty.getName();
	    			
	    			Collections.sort(entityList, new Comparator<Entity>() {
	    				
	    				public int compare(Entity entity1, Entity entity2) {
	    					int compareResult = 0;
	    				
	    					if(sortPropertyName.equals("end_date")){
	    						Date date1 = (Date) entity1.getProperty(sortPropertyName).getValue();
	    						Date date2 = (Date) entity2.getProperty(sortPropertyName).getValue();
	    						compareResult = date1.compareTo(date2);
	    					}
	    					else if(sortPropertyName.equals("time")){
	    						Date date1 = (Date) entity1.getProperty(sortPropertyName).getValue();
	    						Date date2 = (Date) entity2.getProperty(sortPropertyName).getValue();
	    						compareResult = date1.compareTo(date2);
	    					}
	    					if (orderByItem.isDescending()) {
	    						return - compareResult;
	    					}
	    				
	    					return compareResult;
	    				}
	    		
	    			});
	    		}
	    	}
	    }
	    
	    
	    // handle $select
	    SelectOption selectOption = uriInfo.getSelectOption();
	    
	    // handle $count
	    CountOption countOption = uriInfo.getCountOption();
	    if (countOption != null) {
	        boolean isCount = countOption.getValue();
	        if(isCount){
	            returnEntityCollection.setCount(entityList.size());
	        }
	    }
	    
	    // handle $skip
	    SkipOption skipOption = uriInfo.getSkipOption();
	    if (skipOption != null) {
	        int skipNumber = skipOption.getValue();
	        if (skipNumber >= 0) {
	            if(skipNumber <= entityList.size()) {
	                entityList = entityList.subList(skipNumber, entityList.size());
	            } else {
	                // The client skipped all entities
	                entityList.clear();
	            }
	        } else {
	            throw new ODataApplicationException("Invalid value for $skip", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ROOT);
	        }
	    }
	    
	    // handle $top
	    TopOption topOption = uriInfo.getTopOption();
	    if (topOption != null) {
	        int topNumber = topOption.getValue();
	        if (topNumber >= 0) {
	            if(topNumber <= entityList.size()) {
	                entityList = entityList.subList(0, topNumber);
	            }  // else the client has requested more entities than available => return what we have
	        } else {
	            throw new ODataApplicationException("Invalid value for $top", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ROOT);
	        }
	    }
	    
	    // after applying the query options, create EntityCollection based on the reduced list
	    for(Entity entity : entityList){
	        returnEntityCollection.getEntities().add(entity);
	    }



	    ContextURL contextUrl = null;
	    EdmEntityType edmEntityType = null;
	    // 得到要select的property
	    
	    // 第三步: create a serializer based on the requested format (json)
	    ODataSerializer serializer = odata.createSerializer(responseFormat);
	    
	    // serialize the content: transform from the EntitySet object to InputStream
	    if (isContNav(uriInfo)) {
	    	edmEntityType = responseEdmEntityType;
	    	String selectList = odata.createUriHelper().
		    		buildContextURLSelectList(edmEntityType, null, selectOption);
	    	contextUrl = ContextURL.with().entitySetOrSingletonOrType(request.getRawODataPath())
	    		  						.selectList(selectList)
	    		  						.build();
	      	
	    } else {
	    	edmEntityType = responseEdmEntitySet.getEntityType();
	    	String selectList = odata.createUriHelper().
		    		buildContextURLSelectList(edmEntityType, null, selectOption);
	    	contextUrl = ContextURL.with().entitySet(responseEdmEntitySet)
	    		  						.selectList(selectList)
	    		  						.build(); 
	    	
	    }
	    
	    
	    final String id = request.getRawBaseUri() + "/" + responseEdmEntitySet.getName();
	    EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
	    																			.contextURL(contextUrl)
	    																			.id(id)
	    																			.select(selectOption)
	    																			.count(countOption)
	    																			.build();
	    
	    SerializerResult serializerResult = serializer.entityCollection(this.serviceMetadata, edmEntityType,
	    																returnEntityCollection, opts);

	    InputStream serializedContent = serializerResult.getContent();
	    // 第四步: configure the response object: set the body, headers and status code
	    response.setContent(serializedContent);
	    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
	    response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
	  }

	  private boolean isContNav(UriInfo uriInfo) {
	    List<UriResource> resourceParts = uriInfo.getUriResourceParts();
	    for (UriResource resourcePart : resourceParts) {
	      if (resourcePart instanceof UriResourceNavigation) {
	        UriResourceNavigation navResource = (UriResourceNavigation) resourcePart;
	        if (navResource.getProperty().containsTarget()) {
	          return true;
	        }
	      }
	    }
	    return false;
	  }

	}
