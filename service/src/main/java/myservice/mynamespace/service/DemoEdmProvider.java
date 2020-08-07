package myservice.mynamespace.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

public class DemoEdmProvider extends CsdlAbstractEdmProvider {
	
	// Service Namespace
	public static final String NAMESPACE = "OData.Demo";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public static final String ET_ISLAND_NAME = "island";
	public static final FullQualifiedName ET_ISLAND_FQN = new FullQualifiedName(NAMESPACE, ET_ISLAND_NAME);
		
	public static final String ET_IMAGE_NAME = "image";
	public static final FullQualifiedName ET_IMAGE_FQN = new FullQualifiedName(NAMESPACE, ET_IMAGE_NAME);
		
	public static final String ET_CHANGE_NAME = "change";
	public static final FullQualifiedName ET_CHANGE_FQN = new FullQualifiedName(NAMESPACE, ET_CHANGE_NAME);
		
	public static final String ET_SESSION_NAME = "session";
	public static final FullQualifiedName ET_SESSION_FQN = new FullQualifiedName(NAMESPACE, ET_SESSION_NAME);

	// Entity Set Names
	public static final String ES_ISLANDS_NAME = "islands";
	public static final String ES_IMAGES_NAME = "images";
	public static final String ES_CHANGES_NAME = "changes";
	public static final String ES_SESSIONS_NAME = "sessions";

	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
		CsdlEntityType entityType = null;
		
		if (entityTypeName.equals(ET_ISLAND_FQN)) {
		      // create EntityType properties
		      CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		      CsdlProperty code = new CsdlProperty().setName("code").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty name_tw = new CsdlProperty().setName("name_tw").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty name_cn = new CsdlProperty().setName("name_cn").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty location = new CsdlProperty().setName("location").setType(EdmPrimitiveTypeKind.GeometryPoint.getFullQualifiedName());
		      CsdlProperty description = new CsdlProperty().setName("description").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty boundary = new CsdlProperty().setName("boundary").setType(EdmPrimitiveTypeKind.GeometryPolygon.getFullQualifiedName());
		      CsdlProperty area = new CsdlProperty().setName("area").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		      CsdlProperty shoreline_length = new CsdlProperty().setName("shoreline_length").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		      CsdlProperty road_length = new CsdlProperty().setName("road_length").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		      CsdlProperty airport_length = new CsdlProperty().setName("airport_length").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		      CsdlProperty pier_number = new CsdlProperty().setName("pier_number").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		      CsdlProperty lighthouse_number = new CsdlProperty().setName("lighthouse_number").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		      CsdlProperty building_number = new CsdlProperty().setName("building_number").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		      CsdlProperty tide = new CsdlProperty().setName("tide").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		      CsdlProperty update = new CsdlProperty().setName("update").setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());
		      
		      // create PropertyRef for Key element
		      CsdlPropertyRef propertyRef = new CsdlPropertyRef();
		      propertyRef.setName("ID");

		      // navigation property: many-to-one, null not allowed (product must have a category)
		      CsdlNavigationProperty navProp1 = new CsdlNavigationProperty().setName("images").setType(ET_IMAGE_FQN).setCollection(true).setPartner("islands");
		      CsdlNavigationProperty navProp2 = new CsdlNavigationProperty().setName("changes").setType(ET_CHANGE_FQN).setCollection(true).setPartner("islands");
		      CsdlNavigationProperty navProp3 = new CsdlNavigationProperty().setName("sessions").setType(ET_SESSION_FQN).setCollection(true).setPartner("islands");
		      
		      List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
		      navPropList.add(navProp1);
		      navPropList.add(navProp2);
		      navPropList.add(navProp3);

		      // configure EntityType
		      entityType = new CsdlEntityType();
		      entityType.setName(ET_ISLAND_NAME);
		      entityType.setProperties(Arrays.asList(id, code, name_tw, name_cn, location, 
		    		  								description, boundary, area, shoreline_length, 
		    		  								road_length, airport_length, pier_number, lighthouse_number,
		    		  								building_number, tide, update));
		      entityType.setKey(Arrays.asList(propertyRef));
		      entityType.setNavigationProperties(navPropList);
		      
		    } else if (entityTypeName.equals(ET_IMAGE_FQN)) {

		      CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		      CsdlProperty code = new CsdlProperty().setName("code").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty source = new CsdlProperty().setName("source").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty satellite = new CsdlProperty().setName("satellite").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty resolution = new CsdlProperty().setName("resolution").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		      CsdlProperty band_valuecode = new CsdlProperty().setName("band_valuecode").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		      CsdlProperty time = new CsdlProperty().setName("time").setType(EdmPrimitiveTypeKind.DateTimeOffset.getFullQualifiedName());
		      CsdlProperty coverage = new CsdlProperty().setName("coverage").setType(EdmPrimitiveTypeKind.GeometryPolygon.getFullQualifiedName());
		      CsdlProperty servicepath = new CsdlProperty().setName("servicepath").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      CsdlProperty code_island = new CsdlProperty().setName("code_island").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		      
		      CsdlPropertyRef propertyRef = new CsdlPropertyRef();
		      propertyRef.setName("ID");

		      CsdlNavigationProperty navProp1 = new CsdlNavigationProperty().setName("islands").setType(ET_ISLAND_FQN).setCollection(true).setPartner("images");
		      CsdlNavigationProperty navProp2 = new CsdlNavigationProperty().setName("changes").setType(ET_CHANGE_FQN).setCollection(true).setPartner("images");
		      CsdlNavigationProperty navProp3 = new CsdlNavigationProperty().setName("sessions").setType(ET_SESSION_FQN).setCollection(true).setPartner("images");
		      
		      List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
		      navPropList.add(navProp1);
		      navPropList.add(navProp2);
		      navPropList.add(navProp3);

		      entityType = new CsdlEntityType();
		      entityType.setName(ET_IMAGE_NAME);
		      entityType.setProperties(Arrays.asList(id, code, source, satellite, resolution, 
		    		  								band_valuecode, time, coverage, servicepath, code_island));
		      entityType.setKey(Arrays.asList(propertyRef));
		      entityType.setNavigationProperties(navPropList);
		      
		    } else if (entityTypeName.equals(ET_CHANGE_FQN)) {

		    	CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		    	CsdlProperty code_island = new CsdlProperty().setName("code_island").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		    	CsdlProperty code_session = new CsdlProperty().setName("code_session").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			    CsdlProperty time_discover = new CsdlProperty().setName("time_discover").setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());
			    CsdlProperty image_discover = new CsdlProperty().setName("image_discover").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			    CsdlProperty time_last_nochange = new CsdlProperty().setName("time_last_nochange").setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());
			    CsdlProperty image_last_nochange = new CsdlProperty().setName("image_last_nochange").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			    CsdlProperty change_type = new CsdlProperty().setName("change_type").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			    CsdlProperty location = new CsdlProperty().setName("location").setType(EdmPrimitiveTypeKind.GeometryPoint.getFullQualifiedName());
			    CsdlProperty bbox = new CsdlProperty().setName("bbox").setType(EdmPrimitiveTypeKind.GeometryPolygon.getFullQualifiedName());
			    CsdlProperty description = new CsdlProperty().setName("description").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			    CsdlProperty last_modified = new CsdlProperty().setName("last_modified").setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());
			     
			    CsdlPropertyRef propertyRef = new CsdlPropertyRef();
			    propertyRef.setName("ID");

			    CsdlNavigationProperty navProp1 = new CsdlNavigationProperty().setName("islands").setType(ET_ISLAND_FQN).setNullable(false).setPartner("changes");
			    CsdlNavigationProperty navProp2 = new CsdlNavigationProperty().setName("images").setType(ET_IMAGE_FQN).setCollection(true).setPartner("changes");
			    CsdlNavigationProperty navProp3 = new CsdlNavigationProperty().setName("sessions").setType(ET_SESSION_FQN).setNullable(false).setPartner("changes");
			      
			    List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
			    navPropList.add(navProp1);
			    navPropList.add(navProp2);
			    navPropList.add(navProp3);

			    entityType = new CsdlEntityType();
			    entityType.setName(ET_CHANGE_NAME);
			    entityType.setProperties(Arrays.asList(id, code_island, code_session, time_discover, image_discover, 
			   		  								image_last_nochange, time_last_nochange, change_type, 
			   		  								location, bbox, description, last_modified));
			    entityType.setKey(Arrays.asList(propertyRef));
			    entityType.setNavigationProperties(navPropList);
		      
		    } else if (entityTypeName.equals(ET_SESSION_FQN)) {
		    	  
		     CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		     CsdlProperty code_island = new CsdlProperty().setName("code_island").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		     CsdlProperty code = new CsdlProperty().setName("code").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			 CsdlProperty boundary = new CsdlProperty().setName("boundary").setType(EdmPrimitiveTypeKind.GeometryPolygon.getFullQualifiedName());
			 CsdlProperty area = new CsdlProperty().setName("area").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
			 CsdlProperty shoreline_length = new CsdlProperty().setName("shoreline_length").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
			 CsdlProperty road_length = new CsdlProperty().setName("road_length").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
			 CsdlProperty airport_length = new CsdlProperty().setName("airport_length").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
			 CsdlProperty pier_number = new CsdlProperty().setName("pier_number").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
			 CsdlProperty lighthouse_number = new CsdlProperty().setName("lighthouse_number").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
			 CsdlProperty building_number = new CsdlProperty().setName("building_number").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
			 CsdlProperty tide = new CsdlProperty().setName("tide").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
			 CsdlProperty start_date = new CsdlProperty().setName("start_date").setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());
			 CsdlProperty end_date = new CsdlProperty().setName("end_date").setType(EdmPrimitiveTypeKind.Date.getFullQualifiedName());
			 CsdlProperty note = new CsdlProperty().setName("note").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			  
			 CsdlPropertyRef propertyRef = new CsdlPropertyRef();
			 propertyRef.setName("ID");
			      
			 CsdlNavigationProperty navProp1 = new CsdlNavigationProperty().setName("islands").setType(ET_ISLAND_FQN).setNullable(false).setPartner("sessions");
			 CsdlNavigationProperty navProp2 = new CsdlNavigationProperty().setName("images").setType(ET_IMAGE_FQN).setCollection(true).setPartner("sessions");
			 CsdlNavigationProperty navProp3 = new CsdlNavigationProperty().setName("changes").setType(ET_CHANGE_FQN).setCollection(true).setPartner("sessions");
			      
			 List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
			 navPropList.add(navProp1);
			 navPropList.add(navProp2);
			 navPropList.add(navProp3);

			 entityType = new CsdlEntityType();
			 entityType.setName(ET_SESSION_NAME);
			 entityType.setProperties(Arrays.asList(id, code_island, code, boundary, area, 
			  	  								shoreline_length, road_length, airport_length, pier_number, 
		 		  								lighthouse_number, building_number, tide, start_date, 
		   		  								end_date, note));
			entityType.setKey(Arrays.asList(propertyRef));
			entityType.setNavigationProperties(navPropList);
		    }

		    return entityType;
	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
		CsdlEntitySet entitySet = null;

	    if (entityContainer.equals(CONTAINER)) {

	      if (entitySetName.equals(ES_ISLANDS_NAME)) {

	        entitySet = new CsdlEntitySet();
	        entitySet.setName(ES_ISLANDS_NAME);
	        entitySet.setType(ET_ISLAND_FQN);

	        // navigation
	        CsdlNavigationPropertyBinding navPropBinding1 = new CsdlNavigationPropertyBinding();
	        navPropBinding1.setTarget("images");
	        navPropBinding1.setPath("images");
	        
	        CsdlNavigationPropertyBinding navPropBinding2 = new CsdlNavigationPropertyBinding();
	        navPropBinding2.setTarget("changes");
	        navPropBinding2.setPath("changes");
	        
	        CsdlNavigationPropertyBinding navPropBinding3 = new CsdlNavigationPropertyBinding();
	        navPropBinding3.setTarget("sessions");
	        navPropBinding3.setPath("sessions");
	        
	        List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
	        navPropBindingList.add(navPropBinding1);
	        navPropBindingList.add(navPropBinding2);
	        navPropBindingList.add(navPropBinding3);
	        
	        entitySet.setNavigationPropertyBindings(navPropBindingList);

	      } else if (entitySetName.equals(ES_IMAGES_NAME)) {

	        entitySet = new CsdlEntitySet();
	        entitySet.setName(ES_IMAGES_NAME);
	        entitySet.setType(ET_IMAGE_FQN);

	        
	        CsdlNavigationPropertyBinding navPropBinding1 = new CsdlNavigationPropertyBinding();
	        navPropBinding1.setTarget("islands");
	        navPropBinding1.setPath("islands");
	        
	        CsdlNavigationPropertyBinding navPropBinding2 = new CsdlNavigationPropertyBinding();
	        navPropBinding2.setTarget("changes");
	        navPropBinding2.setPath("changes");
	        
	        CsdlNavigationPropertyBinding navPropBinding3 = new CsdlNavigationPropertyBinding();
	        navPropBinding3.setTarget("sessions");
	        navPropBinding3.setPath("sessions");
	        
	        List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
	        navPropBindingList.add(navPropBinding1);
	        navPropBindingList.add(navPropBinding2);
	        navPropBindingList.add(navPropBinding3);
	        
	        entitySet.setNavigationPropertyBindings(navPropBindingList);
	        
	      } else if (entitySetName.equals(ES_CHANGES_NAME)) {

	        entitySet = new CsdlEntitySet();
	        entitySet.setName(ES_CHANGES_NAME);
	        entitySet.setType(ET_CHANGE_FQN);

	        // navigation
	        CsdlNavigationPropertyBinding navPropBinding1 = new CsdlNavigationPropertyBinding();
	        navPropBinding1.setTarget("islands");
	        navPropBinding1.setPath("islands");
	        
	        CsdlNavigationPropertyBinding navPropBinding2 = new CsdlNavigationPropertyBinding();
	        navPropBinding2.setTarget("images");
	        navPropBinding2.setPath("images");
	        
	        CsdlNavigationPropertyBinding navPropBinding3 = new CsdlNavigationPropertyBinding();
	        navPropBinding3.setTarget("sessions");
	        navPropBinding3.setPath("sessions");
	        
	        List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
	        navPropBindingList.add(navPropBinding1);
	        navPropBindingList.add(navPropBinding2);
	        navPropBindingList.add(navPropBinding3);
	        
	        entitySet.setNavigationPropertyBindings(navPropBindingList);
	        
	      } else if (entitySetName.equals(ES_SESSIONS_NAME)) {

		    entitySet = new CsdlEntitySet();
		    entitySet.setName(ES_SESSIONS_NAME);
		    entitySet.setType(ET_SESSION_FQN);

		    // navigation
		    CsdlNavigationPropertyBinding navPropBinding1 = new CsdlNavigationPropertyBinding();
	        navPropBinding1.setTarget("islands");
	        navPropBinding1.setPath("islands");
	        
	        CsdlNavigationPropertyBinding navPropBinding2 = new CsdlNavigationPropertyBinding();
	        navPropBinding2.setTarget("images");
	        navPropBinding2.setPath("images");
	        
	        CsdlNavigationPropertyBinding navPropBinding3 = new CsdlNavigationPropertyBinding();
	        navPropBinding3.setTarget("changes");
	        navPropBinding3.setPath("changes");
	        
	        List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
	        navPropBindingList.add(navPropBinding1);
	        navPropBindingList.add(navPropBinding2);
	        navPropBindingList.add(navPropBinding3);
	        
		    entitySet.setNavigationPropertyBindings(navPropBindingList);
		  }
	      
	    }
	    return entitySet;
	}

	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
		// This method is invoked when displaying the service document at
	    // e.g. http://localhost:8080/DemoService/DemoService.svc
	    if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
	      CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
	      entityContainerInfo.setContainerName(CONTAINER);
	      return entityContainerInfo;
	    }

	    return null;
	}

	@Override
	public List<CsdlSchema> getSchemas() throws ODataException {
		// create Schema
	    CsdlSchema schema = new CsdlSchema();
	    schema.setNamespace(NAMESPACE);

	    // add EntityTypes
	    List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
	    entityTypes.add(getEntityType(ET_ISLAND_FQN));
	    entityTypes.add(getEntityType(ET_IMAGE_FQN));
	    entityTypes.add(getEntityType(ET_CHANGE_FQN));
	    entityTypes.add(getEntityType(ET_SESSION_FQN));
	    schema.setEntityTypes(entityTypes);

	    // add EntityContainer
	    schema.setEntityContainer(getEntityContainer());

	    // finally
	    List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
	    schemas.add(schema);

	    return schemas;
	}
	

	@Override
	public CsdlEntityContainer getEntityContainer() throws ODataException {
		// create EntitySets
	    List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
	    entitySets.add(getEntitySet(CONTAINER, ES_ISLANDS_NAME));
	    entitySets.add(getEntitySet(CONTAINER, ES_IMAGES_NAME));
	    entitySets.add(getEntitySet(CONTAINER, ES_CHANGES_NAME));
	    entitySets.add(getEntitySet(CONTAINER, ES_SESSIONS_NAME));

	    // create EntityContainer
	    CsdlEntityContainer entityContainer = new CsdlEntityContainer();
	    entityContainer.setName(CONTAINER_NAME);
	    entityContainer.setEntitySets(entitySets);

	    return entityContainer;
	}

}
