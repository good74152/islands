package myservice.mynamespace.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.geo.Geospatial.Dimension;
import org.apache.olingo.commons.api.edm.geo.Point;
import org.apache.olingo.commons.api.edm.geo.Polygon;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.server.api.uri.UriParameter;

import myservice.mynamespace.service.DemoEdmProvider;
import myservice.mynamespace.util.Util;

public class Storage {
	
	private List<Entity> islandList;
	private List<Entity> imageList;
	private List<Entity> changeList;
	private List<Entity> sessionList;
	
	public Storage() {
		islandList = new ArrayList<Entity>();
		imageList = new ArrayList<Entity>();
		changeList = new ArrayList<Entity>();
		sessionList = new ArrayList<Entity>();

	    // creating some sample data
	    initIslandSampleData();
	    initImageSampleData();
	    initChangeSampleData();
	    initSessionSampleData();
	}

	@SuppressWarnings("deprecation")
	private void initIslandSampleData() {
		Entity entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "name_tw", ValueType.PRIMITIVE, "南威島"));
		entity.addProperty(new Property(null, "name_cn", ValueType.PRIMITIVE, "南威島"));
		entity.addProperty(new Property(null, "location", ValueType.GEOSPATIAL, createPoint(1.5,4.25)));
		entity.addProperty(new Property(null, "description", ValueType.PRIMITIVE, "南威島位於尹慶群礁西南方約 35 公里處"));
		entity.addProperty(new Property(null, "boundary", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																							Arrays.asList(
																									createPoint(111.91591109198832, 8.64644393402341), 
																									createPoint(111.92515891519959, 8.650160014824982), 
																									createPoint(111.92471633486925, 8.641971844384928), 
																									createPoint(111.91597783732857, 8.64005358122585), 
																									createPoint(111.91591109198832, 8.64644393402341)))));
		entity.addProperty(new Property(null, "area", ValueType.PRIMITIVE, 0.38));
		entity.addProperty(new Property(null, "shoreline_length", ValueType.PRIMITIVE, 4.68));
		entity.addProperty(new Property(null, "road_length", ValueType.PRIMITIVE, 3.51));
		entity.addProperty(new Property(null, "airport_length", ValueType.PRIMITIVE, 1.21));
		entity.addProperty(new Property(null, "pier_number", ValueType.PRIMITIVE, 6));
		entity.addProperty(new Property(null, "lighthouse_number", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "building_number", ValueType.PRIMITIVE, 298));
		entity.addProperty(new Property(null, "tide", ValueType.PRIMITIVE, -0.44));
		entity.addProperty(new Property(null, "update", ValueType.PRIMITIVE, createDate("2020/02/12")));
		
		entity.setType(DemoEdmProvider.ET_ISLAND_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    islandList.add(entity);
	}
	

	private Date createDate(String string) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		try {
			date = parser.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	@SuppressWarnings("deprecation")
	private void initImageSampleData() {
		Entity entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "WV3_20180318_F6944_1"));
		entity.addProperty(new Property(null, "source", ValueType.PRIMITIVE, "107NSB1"));
		entity.addProperty(new Property(null, "satellite", ValueType.PRIMITIVE, "WorldView-3"));
		entity.addProperty(new Property(null, "resolution", ValueType.PRIMITIVE, 0.3));
		entity.addProperty(new Property(null, "band_valuecode", ValueType.PRIMITIVE, 5));
		entity.addProperty(new Property(null, "time", ValueType.PRIMITIVE, createDateTime("2018-03-18T03:31:37")));
		entity.addProperty(new Property(null, "coverage", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																													Arrays.asList(
																															createPoint(111.912053762446, 8.65912445626707),
																															createPoint(111.931337174151, 8.65912445626707),
																															createPoint(111.931337174151, 8.63739653008216),
																															createPoint(111.912053762446, 8.63739653008216),
																															createPoint(111.912053762446, 8.65912445626707)))));
		entity.addProperty(new Property(null, "servicepath", ValueType.PRIMITIVE, "http://140.115.110.11/SS039/wmts/WV3_20180318_F6944_1_3857/{z}/{x}/{y}.png"));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		
		entity.setType(DemoEdmProvider.ET_IMAGE_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    imageList.add(entity);
	    
	    entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 2));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "WV2_20180511_F7190"));
		entity.addProperty(new Property(null, "source", ValueType.PRIMITIVE, "107NSB2"));
		entity.addProperty(new Property(null, "satellite", ValueType.PRIMITIVE, "WorldView-2"));
		entity.addProperty(new Property(null, "resolution", ValueType.PRIMITIVE, 0.4));
		entity.addProperty(new Property(null, "band_valuecode", ValueType.PRIMITIVE, 5));
		entity.addProperty(new Property(null, "time", ValueType.PRIMITIVE, createDateTime("2018-05-11T03:31:38")));
		entity.addProperty(new Property(null, "coverage", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																													Arrays.asList(
																															createPoint(111.912053309109, 8.65912490889788),
																															createPoint(111.931336714206, 8.65912490889788),
																															createPoint(111.931336714206, 8.63739426951417),
																															createPoint(111.912053309109, 8.63739426951417),
																															createPoint(111.912053309109, 8.65912490889788)))));
		entity.addProperty(new Property(null, "servicepath", ValueType.PRIMITIVE, "http://140.115.110.11/SS039/wmts/WV2_20180511_F7190_3857/{z}/{x}/{y}.png"));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		
		entity.setType(DemoEdmProvider.ET_IMAGE_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    imageList.add(entity);
	    
	    entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 3));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "WV2_20180511_APGT-45390-20180511032138-01"));
		entity.addProperty(new Property(null, "source", ValueType.PRIMITIVE, "107MND1"));
		entity.addProperty(new Property(null, "satellite", ValueType.PRIMITIVE, "WorldView-2"));
		entity.addProperty(new Property(null, "resolution", ValueType.PRIMITIVE, 0.5));
		entity.addProperty(new Property(null, "band_valuecode", ValueType.PRIMITIVE, 5));
		entity.addProperty(new Property(null, "time", ValueType.PRIMITIVE, createDateTime("2018-05-11T03:21:38")));
		entity.addProperty(new Property(null, "coverage", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																													Arrays.asList(
																															createPoint(111.912052855772, 8.65912536218678),
																															createPoint(111.931339898315, 8.65912536218678),
																															createPoint(111.931339898315, 8.63739561839544),
																															createPoint(111.912052855772, 8.63739561839544),
																															createPoint(111.912052855772, 8.65912536218678)))));
		entity.addProperty(new Property(null, "servicepath", ValueType.PRIMITIVE, "http://140.115.110.11/SS039/wmts/WV2_20180511_APGT-45390-20180511032138-01_3857/{z}/{x}/{y}.png"));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		
		entity.setType(DemoEdmProvider.ET_IMAGE_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    imageList.add(entity);
	}

	private Date createDateTime(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date datetime = new Date();
		try {
			datetime = sdf.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datetime;
	}

	@SuppressWarnings("deprecation")
	private void initChangeSampleData() {
		Entity entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "code_session", ValueType.PRIMITIVE, "107-2"));
		entity.addProperty(new Property(null, "time_discover", ValueType.PRIMITIVE, createDate("2018/3/18")));
		entity.addProperty(new Property(null, "image_discover", ValueType.PRIMITIVE, "http://140.115.110.11/SS039/wmts/WV3_20180318_F6944_1_3857/{z}/{x}/{y}.png"));
		entity.addProperty(new Property(null, "time_last_nochange", ValueType.PRIMITIVE, createDate("2017/9/16")));
		entity.addProperty(new Property(null, "image_last_nochange", ValueType.PRIMITIVE, "WV2_20170916_E3726"));
		entity.addProperty(new Property(null, "change_type", ValueType.PRIMITIVE, "永久性房屋"));
		entity.addProperty(new Property(null, "location", ValueType.GEOSPATIAL, createPoint(111.91849734013103, 8.64405619070411)));
		entity.addProperty(new Property(null, "bbox", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																							Arrays.asList(createPoint(111.9183544561776, 8.644233610469922), 
																											createPoint(111.91869496953971, 8.644170280413192), 
																											createPoint(111.9186402239448, 8.643878770897347), 
																											createPoint(111.91829971082501, 8.643942100879977), 
																											createPoint(111.9183544561776, 8.644233610469922)))));
		entity.addProperty(new Property(null, "description", ValueType.PRIMITIVE, "永久性房屋增建1棟"));
		entity.addProperty(new Property(null, "last_modified", ValueType.PRIMITIVE, createDate("2018/6/5")));
		
		
		entity.setType(DemoEdmProvider.ET_CHANGE_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    changeList.add(entity);
	    
	    entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 2));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "code_session", ValueType.PRIMITIVE, "107-2"));
		entity.addProperty(new Property(null, "time_discover", ValueType.PRIMITIVE, createDate("2018/3/18")));
		entity.addProperty(new Property(null, "image_discover", ValueType.PRIMITIVE, "http://140.115.110.11/SS039/wmts/WV3_20180318_F6944_1_3857/{z}/{x}/{y}.png"));
		entity.addProperty(new Property(null, "time_last_nochange", ValueType.PRIMITIVE, createDate("2017/9/16")));
		entity.addProperty(new Property(null, "image_last_nochange", ValueType.PRIMITIVE, "WV2_20170916_E3726"));
		entity.addProperty(new Property(null, "change_type", ValueType.PRIMITIVE, "永久性房屋"));
		entity.addProperty(new Property(null, "location", ValueType.GEOSPATIAL, createPoint(111.921623441623, 8.645572500073312)));
		entity.addProperty(new Property(null, "bbox", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																							Arrays.asList(createPoint(111.92150438819819, 8.645698077786006), 
																											createPoint(111.9217437807868, 8.645687389222877), 
																											createPoint(111.92174465477689, 8.645453358339374), 
																											createPoint(111.92150523094932, 8.645451125117123), 
																											createPoint(111.92150438819819, 8.645698077786006)))));
		entity.addProperty(new Property(null, "description", ValueType.PRIMITIVE, "永久性房屋增建1棟"));
		entity.addProperty(new Property(null, "last_modified", ValueType.PRIMITIVE, createDate("2018/6/5")));
		
		
		entity.setType(DemoEdmProvider.ET_CHANGE_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    changeList.add(entity);
	    
	    entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 3));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "code_session", ValueType.PRIMITIVE, "107-2"));
		entity.addProperty(new Property(null, "time_discover", ValueType.PRIMITIVE, createDate("2018/3/18")));
		entity.addProperty(new Property(null, "image_discover", ValueType.PRIMITIVE, "http://140.115.110.11/SS039/wmts/WV3_20180318_F6944_1_3857/{z}/{x}/{y}.png"));
		entity.addProperty(new Property(null, "time_last_nochange", ValueType.PRIMITIVE, createDate("2017/9/16")));
		entity.addProperty(new Property(null, "image_last_nochange", ValueType.PRIMITIVE, "WV2_20170916_E3726"));
		entity.addProperty(new Property(null, "change_type", ValueType.PRIMITIVE, "其它地物類別"));
		entity.addProperty(new Property(null, "location", ValueType.GEOSPATIAL, createPoint(111.91908963100654, 8.64702176106863)));
		entity.addProperty(new Property(null, "bbox", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																							Arrays.asList(createPoint(111.91831829598017, 8.647770099456459), 
																											createPoint(111.92002512474889, 8.647586531961165), 
																											createPoint(111.91984540343111, 8.646260820047853), 
																											createPoint(111.91815883940015, 8.646466332563492), 
																											createPoint(111.91831829598017, 8.647770099456459)))));
		entity.addProperty(new Property(null, "description", ValueType.PRIMITIVE, "建物屋頂加裝太陽能板"));
		entity.addProperty(new Property(null, "last_modified", ValueType.PRIMITIVE, createDate("2018/6/5")));
		
		
		entity.setType(DemoEdmProvider.ET_CHANGE_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    changeList.add(entity);
	}

	@SuppressWarnings("deprecation")
	private void initSessionSampleData() {
		Entity entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "107-2"));
		entity.addProperty(new Property(null, "boundary", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																									Arrays.asList(createPoint(111.91594413411923, 8.647767012238829), 
																											createPoint(111.92532755014705, 8.649993803959857), 
																											createPoint(111.92456211264368, 8.641787901828456), 
																											createPoint(111.9159496908481, 8.640109602755247), 
																											createPoint(111.9159496908481, 8.640109602755247)))));
		entity.addProperty(new Property(null, "area", ValueType.PRIMITIVE, 0.38));
		entity.addProperty(new Property(null, "shoreline_length", ValueType.PRIMITIVE, 4.72));
		entity.addProperty(new Property(null, "road_length", ValueType.PRIMITIVE, 2.6));
		entity.addProperty(new Property(null, "runway_length", ValueType.PRIMITIVE, 1.21));
		entity.addProperty(new Property(null, "pier_number", ValueType.PRIMITIVE, 6));
		entity.addProperty(new Property(null, "lighthouse_number", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "building_number", ValueType.PRIMITIVE, 279));
		entity.addProperty(new Property(null, "tide", ValueType.PRIMITIVE, 0.08));
		entity.addProperty(new Property(null, "start_date", ValueType.PRIMITIVE, createDate("2017/12/2")));
		entity.addProperty(new Property(null, "end_date", ValueType.PRIMITIVE, createDate("2018/3/18")));
		entity.addProperty(new Property(null, "note", ValueType.PRIMITIVE, "南威島本期中移除大範圍的樹木，本次判釋發現 1 處較明顯變化之區域"));

		
		
		entity.setType(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    sessionList.add(entity);
	    
	    entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 2));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "107-3"));
		entity.addProperty(new Property(null, "boundary", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																									Arrays.asList(createPoint(111.91580190120246, 8.647830601727525), 
																											createPoint(111.92521386996638, 8.64993289550065), 
																											createPoint(111.92444032659306, 8.640372613359792), 
																											createPoint(111.91570824482675, 8.640278557541768), 
																											createPoint(111.91580190120246, 8.647830601727525)))));
		entity.addProperty(new Property(null, "area", ValueType.PRIMITIVE, 0.38));
		entity.addProperty(new Property(null, "shoreline_length", ValueType.PRIMITIVE, 4.95));
		entity.addProperty(new Property(null, "road_length", ValueType.PRIMITIVE, 3.35));
		entity.addProperty(new Property(null, "runway_length", ValueType.PRIMITIVE, 1.21));
		entity.addProperty(new Property(null, "pier_number", ValueType.PRIMITIVE, 6));
		entity.addProperty(new Property(null, "lighthouse_number", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "building_number", ValueType.PRIMITIVE, 278));
		entity.addProperty(new Property(null, "tide", ValueType.PRIMITIVE, 0.2));
		entity.addProperty(new Property(null, "start_date", ValueType.PRIMITIVE, createDate("2018/5/11")));
		entity.addProperty(new Property(null, "end_date", ValueType.PRIMITIVE, createDate("2018/5/11")));
		entity.addProperty(new Property(null, "note", ValueType.PRIMITIVE, "海岸線長度受海水潮汐影響有些微變化，島礁陸域上建設工程積極進行中，新增永久性房屋及道路。"));

		
		
		entity.setType(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    sessionList.add(entity);
	    
	    entity = new Entity();
		entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, 3));
		entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, "SS039"));
		entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, "107-4"));
		entity.addProperty(new Property(null, "boundary", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
																									Arrays.asList(createPoint(111.91488353840195, 8.645950797664721), 
																											createPoint(111.92464666755879, 8.650081476311225), 
																											createPoint(111.92613961418192, 8.646995774292275), 
																											createPoint(111.91659870119541, 8.639265618891692), 
																											createPoint(111.91488353840195, 8.645950797664721)))));
		entity.addProperty(new Property(null, "area", ValueType.PRIMITIVE, 0.37));
		entity.addProperty(new Property(null, "shoreline_length", ValueType.PRIMITIVE, 4.82));
		entity.addProperty(new Property(null, "road_length", ValueType.PRIMITIVE, 3.26));
		entity.addProperty(new Property(null, "runway_length", ValueType.PRIMITIVE, 1.21));
		entity.addProperty(new Property(null, "pier_number", ValueType.PRIMITIVE, 6));
		entity.addProperty(new Property(null, "lighthouse_number", ValueType.PRIMITIVE, 1));
		entity.addProperty(new Property(null, "building_number", ValueType.PRIMITIVE, 285));
		entity.addProperty(new Property(null, "tide", ValueType.PRIMITIVE, -0.02));
		entity.addProperty(new Property(null, "start_date", ValueType.PRIMITIVE, createDate("2018/7/22")));
		entity.addProperty(new Property(null, "end_date", ValueType.PRIMITIVE, createDate("2018/7/22")));
		entity.addProperty(new Property(null, "note", ValueType.PRIMITIVE, "無填海造陸工程進行；島礁上人工建設工程積極進行中,永久性房屋道路(C)、道路(C)及其他地物類別(G)有明顯變遷"));

		
		
		entity.setType(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString());
	    entity.setId(createId(entity, "ID"));
	    sessionList.add(entity);
	}

	private URI createId(Entity entity, String idPropertyName) {
		return createId(entity, idPropertyName, null);
	}

	private URI createId(Entity entity, String idPropertyName, String navigationName) {
		try {
		      StringBuilder sb = new StringBuilder(getEntitySetName(entity)).append("(");
		      final Property property = entity.getProperty(idPropertyName);
		      sb.append(property.asPrimitive()).append(")");
		      if(navigationName != null) {
		        sb.append("/").append(navigationName);
		      }
		      return new URI(sb.toString());
		    } catch (URISyntaxException e) {
		      throw new ODataRuntimeException("Unable to create (Atom) id for entity: " + entity, e);
		    }
	}
	
	private String getEntitySetName(Entity entity) {
		if(DemoEdmProvider.ET_ISLAND_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
		      return DemoEdmProvider.ES_ISLANDS_NAME;
		    } else if(DemoEdmProvider.ET_IMAGE_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
		      return DemoEdmProvider.ES_IMAGES_NAME;
		    } else if(DemoEdmProvider.ET_CHANGE_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
		      return DemoEdmProvider.ES_CHANGES_NAME;
		    } else if(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString().equals(entity.getType())) {
			  return DemoEdmProvider.ES_SESSIONS_NAME;
		    }
		return entity.getType();
	}

	private Point createPoint(final double x, final double y) {
		Point point = new Point(Dimension.GEOMETRY, null);
	    point.setX(x);
	    point.setY(y);
	    return point;
	}

	public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyPredicates) {
		Entity entity = null;

	    EdmEntityType edmEntityType = edmEntitySet.getEntityType();

	    if (edmEntityType.getName().equals(DemoEdmProvider.ET_ISLAND_NAME)) {
	      entity = getIsland(edmEntityType, keyPredicates);
	    } else if (edmEntityType.getName().equals(DemoEdmProvider.ET_IMAGE_NAME)) {
	      entity = getImage(edmEntityType, keyPredicates);
	    } else if (edmEntityType.getName().equals(DemoEdmProvider.ET_CHANGE_NAME)) {
	      entity = getChange(edmEntityType, keyPredicates);
	    } else if (edmEntityType.getName().equals(DemoEdmProvider.ET_SESSION_NAME)) {
		  entity = getSession(edmEntityType, keyPredicates);
	    }
	    return entity;
	}

	private Entity getSession(EdmEntityType edmEntityType, List<UriParameter> keyPredicates) {
		EntityCollection entityCollection = getSessions();

	    return Util.findEntity(edmEntityType, entityCollection, keyPredicates);
	}

	private Entity getChange(EdmEntityType edmEntityType, List<UriParameter> keyPredicates) {
		EntityCollection entityCollection = getChanges();

	    return Util.findEntity(edmEntityType, entityCollection, keyPredicates);
	}

	private Entity getImage(EdmEntityType edmEntityType, List<UriParameter> keyPredicates) {
		EntityCollection entityCollection = getImages();

	    return Util.findEntity(edmEntityType, entityCollection, keyPredicates);
	}

	private Entity getIsland(EdmEntityType edmEntityType, List<UriParameter> keyPredicates) {
	    EntityCollection entityCollection = getIslands();

	    return Util.findEntity(edmEntityType, entityCollection, keyPredicates);
	}

	public Entity getRelatedEntity(Entity sourceEntity, EdmEntityType responseEdmEntityType) {
		EntityCollection collection = getRelatedEntityCollection(sourceEntity, responseEdmEntityType);
	    if (collection.getEntities().isEmpty()) {
	      return null;
	    }
	    return collection.getEntities().get(0);
	}

	public EntityCollection readEntitySetData(EdmEntitySet startEdmEntitySet) {
		EntityCollection entitySet = null;

	    if (startEdmEntitySet.getName().equals(DemoEdmProvider.ES_ISLANDS_NAME)) {
	    	entitySet = getIslands();
	    } else if (startEdmEntitySet.getName().equals(DemoEdmProvider.ES_IMAGES_NAME)) {
	    	entitySet = getImages();
	    } else if (startEdmEntitySet.getName().equals(DemoEdmProvider.ES_CHANGES_NAME)) {
	    	entitySet = getChanges();
	    } else if (startEdmEntitySet.getName().equals(DemoEdmProvider.ES_SESSIONS_NAME)) {
	    	entitySet = getSessions();
	    }
	    return entitySet;
	}

	private EntityCollection getSessions() {
		EntityCollection retEntitySet = new EntityCollection();

	    for (Entity sessionEntity : this.sessionList) {
	      retEntitySet.getEntities().add(sessionEntity);
	    }

	    return retEntitySet;
	}

	private EntityCollection getChanges() {
		EntityCollection retEntitySet = new EntityCollection();

	    for (Entity changeEntity : this.changeList) {
	      retEntitySet.getEntities().add(changeEntity);
	    }

	    return retEntitySet;
	}

	private EntityCollection getImages() {
		EntityCollection retEntitySet = new EntityCollection();

	    for (Entity imageEntity : this.imageList) {
	      retEntitySet.getEntities().add(imageEntity);
	    }

	    return retEntitySet;
	}

	private EntityCollection getIslands() {
		EntityCollection retEntitySet = new EntityCollection();

	    for (Entity islandEntity : this.islandList) {
	      retEntitySet.getEntities().add(islandEntity);
	    }

	    return retEntitySet;
	}

	public EntityCollection getRelatedEntityCollection(Entity sourceEntity, EdmEntityType targetEntityType) {
		EntityCollection navigationTargetEntityCollection = new EntityCollection();

	    FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
	    String sourceEntityFqn = sourceEntity.getType();
	    
	    if (sourceEntityFqn.equals(DemoEdmProvider.ET_ISLAND_FQN.getFullQualifiedNameAsString())
	    		&& relatedEntityFqn.equals(DemoEdmProvider.ET_IMAGE_FQN)) {
	    	//islands(id)/images
	    	String code = (String) sourceEntity.getProperty("code").getValue();
	    	for (Entity imageEntity : this.imageList) {
	    		String code_island = (String) imageEntity.getProperty("code_island").getValue();
	    		if (code.equals(code_island)) {
	    			navigationTargetEntityCollection.getEntities().add(imageEntity);
	    		}
	    	}
	    } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_IMAGE_FQN.getFullQualifiedNameAsString())
	    		&& relatedEntityFqn.equals(DemoEdmProvider.ET_ISLAND_FQN)) {
	    	//images(id)/islands
	    	String code = (String) sourceEntity.getProperty("code_island").getValue();
	    	for (Entity islandEntity : this.islandList) {
	    		String code_island = (String) islandEntity.getProperty("code").getValue();
	    		if (code.equals(code_island)) {
	    			navigationTargetEntityCollection.getEntities().add(islandEntity);
	    		}
	    	}
	    } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_ISLAND_FQN.getFullQualifiedNameAsString())
	            && relatedEntityFqn.equals(DemoEdmProvider.ET_CHANGE_FQN)) {
	    	//islands(id)/changes
	    	String code = (String) sourceEntity.getProperty("code").getValue();
	    	for (Entity changeEntity : this.changeList) {
	    		String code_island = (String) changeEntity.getProperty("code_island").getValue();
	    		if (code.equals(code_island)) {
	    			navigationTargetEntityCollection.getEntities().add(changeEntity);
	    		}
	    	}
	    } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_ISLAND_FQN.getFullQualifiedNameAsString())
	            && relatedEntityFqn.equals(DemoEdmProvider.ET_SESSION_FQN)) {
	    	//island(id)/sessions
	    	String code = (String) sourceEntity.getProperty("code").getValue();
	    	for (Entity sessionEntity : this.sessionList) {
	    		String code_island = (String) sessionEntity.getProperty("code_island").getValue();
	    		if (code.equals(code_island)) {
	    			navigationTargetEntityCollection.getEntities().add(sessionEntity);
	    		}
	    	}
	    } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString())
	            && relatedEntityFqn.equals(DemoEdmProvider.ET_CHANGE_FQN)) {
	    	//sessions(id)/changes
	    	String code = (String) sourceEntity.getProperty("code").getValue();
	    	for (Entity changeEntity : this.changeList) {
	    		String code_session = (String) changeEntity.getProperty("code_session").getValue();
	    		if (code.equals(code_session)) {
	    			navigationTargetEntityCollection.getEntities().add(changeEntity);
	    		}
	    	}
	    } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString())
	            && relatedEntityFqn.equals(DemoEdmProvider.ET_IMAGE_FQN)) {
	    	//sessions(id)/images
	    	/*String code = (String) sourceEntity.getProperty("code_island").getValue();
	    	for (Entity imageEntity : this.imageList) {
	    		String code_island = (String) imageEntity.getProperty("code_island").getValue();
	    		if (code.equals(code_island)) {
	    			navigationTargetEntityCollection.getEntities().add(imageEntity);
	    		}
	    	}*/
	    } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_CHANGE_FQN.getFullQualifiedNameAsString())
	            && relatedEntityFqn.equals(DemoEdmProvider.ET_IMAGE_FQN)) {
	    	//changes(id)/images
	    	String image_discover = (String) sourceEntity.getProperty("image_discover").getValue();
	    	String image_last_nochange = (String) sourceEntity.getProperty("image_last_nochange").getValue();
	    	for (Entity imageEntity : this.imageList) {
	    		String image_url = (String) imageEntity.getProperty("servicepath").getValue();
	    		if (image_discover.equals(image_url) || image_last_nochange.equals(image_url)) {
	    			navigationTargetEntityCollection.getEntities().add(imageEntity);
	    		}
	    	}
	    }
	    
	    if (navigationTargetEntityCollection.getEntities().isEmpty()) {
	        return null;
	    }
	    
	    return navigationTargetEntityCollection;
	}

}
