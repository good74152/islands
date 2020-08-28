package myservice.mynamespace.data;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import myservice.mynamespace.service.DemoEdmProvider;
import myservice.mynamespace.util.Util;

public class Storage {
	
	private List<Entity> islandList;
	private List<Entity> imageList;
	private List<Entity> changeList;
	private List<Entity> sessionList;
	
	public Storage() throws FileNotFoundException, IOException {
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
	private void initIslandSampleData() throws FileNotFoundException, IOException {
		Entity entity = new Entity();
		
		File file = new File(System.getProperty("user.home")+"/Desktop"+"/data"); //存取目前路徑
		int global_row_num = 1; //此為所有資料表中的row個數
		
		if (file.isDirectory()) {
			String[]all_file = file.list(); //得到路徑下所有檔案目錄
			for(String filename:all_file) {
				String filetype = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				if(filetype.equals("xlsx")) {//只存取xlsx檔案
					FileInputStream xlsx = new FileInputStream(new File(System.getProperty("user.home")+"/Desktop"+"/data"+"//"+filename));
					XSSFWorkbook workbook = new XSSFWorkbook(xlsx);
					XSSFSheet sheet = workbook.getSheetAt(0);
					int row_num = sheet.getLastRowNum()+1; //此為一個sheet中的row個數
					for (int row_index=1; row_index<row_num; row_index++) {
						
						Row row = sheet.getRow(row_index);
						
						entity = new Entity();
						
						entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, global_row_num));//加入id屬性
						if (row.getCell(0)!=null) {
							entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, row.getCell(0).getStringCellValue().trim()));
						}
						if (row.getCell(1)!=null) {
							entity.addProperty(new Property(null, "name_tw", ValueType.PRIMITIVE, row.getCell(1).getStringCellValue().trim()));
						}
						if (row.getCell(2)!=null) {
							entity.addProperty(new Property(null, "name_cn", ValueType.PRIMITIVE, row.getCell(2).getStringCellValue().trim()));
						}
						if (row.getCell(3)!=null) {
							String location_tuple = row.getCell(3).toString().split("[\\(\\)]")[1];
							String[] location_coord = location_tuple.split(" ");
							List<Double> list = Arrays.asList(Double.parseDouble(location_coord[0]), Double.parseDouble(location_coord[1]));
							
							entity.addProperty(new Property(null, "location", ValueType.GEOSPATIAL, createPoint(list.get(0),list.get(1))));
						}
						if (row.getCell(4)!=null) {
							entity.addProperty(new Property(null, "description", ValueType.PRIMITIVE, row.getCell(4).toString().trim()));
						}
						if (row.getCell(5)!=null) {
							Matcher m = Pattern.compile("\\((([^)]+)\\))").matcher(row.getCell(5).toString());
							if(m.find()) {
								String polygon_list = m.group(1).toString().split("[\\(\\)]")[1];
							    String[] polygon_tuple = polygon_list.split(",");
							    List<Double> list = new ArrayList<Double>();
							    
							    for (String s:polygon_tuple) {
							    	//System.out.println(s.toString().trim());
							    	String clean_s = s.toString().trim();
							    	String[] polygon_coord = clean_s.split(" ");
							    	list.add(Double.parseDouble(polygon_coord[0]));
							    	list.add(Double.parseDouble(polygon_coord[1]));
							    }
							    entity.addProperty(new Property(null, "boundary", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
										Arrays.asList(
												createPoint(list.get(0), list.get(1)), 
												createPoint(list.get(2), list.get(3)), 
												createPoint(list.get(4), list.get(5)), 
												createPoint(list.get(6), list.get(7)), 
												createPoint(list.get(8), list.get(9))))));
							}
						}
						if (row.getCell(6)!=null) {
							entity.addProperty(new Property(null, "area", ValueType.PRIMITIVE, row.getCell(6).getNumericCellValue()));
						}
						if (row.getCell(7)!=null) {
							entity.addProperty(new Property(null, "shoreline_length", ValueType.PRIMITIVE, row.getCell(7).getNumericCellValue()));
						}
						if (row.getCell(8)!=null) {
							entity.addProperty(new Property(null, "hardstand_number", ValueType.PRIMITIVE, (int)row.getCell(8).getNumericCellValue()));
						}
						if (row.getCell(9)!=null) {
							entity.addProperty(new Property(null, "road_length", ValueType.PRIMITIVE, row.getCell(9).getNumericCellValue()));
						}
						if (row.getCell(10)!=null) {
							entity.addProperty(new Property(null, "airport_length", ValueType.PRIMITIVE, row.getCell(10).getNumericCellValue()));
						}
						if (row.getCell(11)!=null) {
							entity.addProperty(new Property(null, "pier_number", ValueType.PRIMITIVE, (int)row.getCell(11).getNumericCellValue()));
						}
						if (row.getCell(12)!=null) {
							entity.addProperty(new Property(null, "lighthouse_number", ValueType.PRIMITIVE, (int)row.getCell(12).getNumericCellValue()));
						}
						if (row.getCell(13)!=null) {
							entity.addProperty(new Property(null, "building_number", ValueType.PRIMITIVE, (int)row.getCell(13).getNumericCellValue()));
						}
						if (row.getCell(14)!=null) {
							entity.addProperty(new Property(null, "tide", ValueType.PRIMITIVE, row.getCell(14).getNumericCellValue()));
						}
						if (row.getCell(15)!=null) {
							entity.addProperty(new Property(null, "update", ValueType.PRIMITIVE, row.getCell(15).getDateCellValue()));
						}
						entity.setType(DemoEdmProvider.ET_ISLAND_FQN.getFullQualifiedNameAsString());
					    entity.setId(createId(entity, "ID"));
					    islandList.add(entity);
					    
					    global_row_num++;
					}
				}
			}
		}
		
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
	private void initImageSampleData() throws FileNotFoundException, IOException {
		Entity entity = new Entity();
		
		File file = new File(System.getProperty("user.home")+"/Desktop"+"/data"); //存取目前路徑
		int global_row_num = 1; //此為所有資料表中的row個數
		
		if (file.isDirectory()) {
			String[]all_file = file.list(); //得到路徑下所有檔案目錄
			for(String filename:all_file) {
				String filetype = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				if(filetype.equals("xlsx")) {//只存取xlsx檔案
					FileInputStream xlsx = new FileInputStream(new File(System.getProperty("user.home")+"/Desktop"+"/data"+"//"+filename));
					XSSFWorkbook workbook = new XSSFWorkbook(xlsx);
					XSSFSheet sheet = workbook.getSheetAt(1);
					int row_num = sheet.getLastRowNum()+1; //此為一個sheet中的row個數
					for (int row_index=1; row_index<row_num; row_index++) {
						Row row = sheet.getRow(row_index);
						
						entity = new Entity();
						
						entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, global_row_num));//加入id屬性
						
						if (row.getCell(0)!=null) {
							entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, row.getCell(0).getStringCellValue().trim()));
						}
						if (row.getCell(1)!=null) {
							entity.addProperty(new Property(null, "source", ValueType.PRIMITIVE, row.getCell(1).getStringCellValue().trim()));
						}
						if (row.getCell(2)!=null) {
							entity.addProperty(new Property(null, "satellite", ValueType.PRIMITIVE, row.getCell(2).getStringCellValue().trim()));
						}
						if (row.getCell(3)!=null) {
							entity.addProperty(new Property(null, "resolution", ValueType.PRIMITIVE, row.getCell(3).getNumericCellValue()));
						}
						if (row.getCell(4)!=null) {
							entity.addProperty(new Property(null, "band_valuecode", ValueType.PRIMITIVE, (int)row.getCell(4).getNumericCellValue()));
						}
						if (row.getCell(5)!=null) {
							entity.addProperty(new Property(null, "time", ValueType.PRIMITIVE, createDateTime(row.getCell(5).toString().trim())));
						}
						if (row.getCell(6)!=null) {
							Matcher m = Pattern.compile("\\((([^)]+)\\))").matcher(row.getCell(6).toString());
							if(m.find()) {
								String polygon_list = m.group(1).toString().split("[\\(\\)]")[1];
							    String[] polygon_tuple = polygon_list.split(",");
							    List<Double> list = new ArrayList<Double>();
							    
							    for (String s:polygon_tuple) {
							    	//System.out.println(s.toString().trim());
							    	String clean_s = s.toString().trim();
							    	String[] polygon_coord = clean_s.split(" ");
							    	list.add(Double.parseDouble(polygon_coord[0]));
							    	list.add(Double.parseDouble(polygon_coord[1]));
							    }
							    entity.addProperty(new Property(null, "coverage", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
										Arrays.asList(
												createPoint(list.get(0), list.get(1)), 
												createPoint(list.get(2), list.get(3)), 
												createPoint(list.get(4), list.get(5)), 
												createPoint(list.get(6), list.get(7)), 
												createPoint(list.get(8), list.get(9))))));
							}
						}
						if (row.getCell(7)!=null) {
							entity.addProperty(new Property(null, "servicepath", ValueType.PRIMITIVE, row.getCell(7).getStringCellValue().trim()));
						}
						if (row.getCell(8)!=null) {
							entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, row.getCell(8).getStringCellValue().trim()));
						}
						
						entity.setType(DemoEdmProvider.ET_IMAGE_FQN.getFullQualifiedNameAsString());
					    entity.setId(createId(entity, "ID"));
					    if(entity.getProperty("code")!=null && entity.getProperty("source")!=null && entity.getProperty("satellite")!=null
					    		&& entity.getProperty("resolution")!=null && entity.getProperty("band_valuecode")!=null && entity.getProperty("coverage")!=null
					    		&& entity.getProperty("servicepath")!=null) { //image常出現問題
					    	imageList.add(entity);
					    	global_row_num++;
					    }
					    
					    
					}
				}
			}	
		}		
		
	    
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
	private void initChangeSampleData() throws FileNotFoundException, IOException {
		Entity entity = new Entity();
		
		File file = new File(System.getProperty("user.home")+"/Desktop"+"/data"); //存取目前路徑
		int global_row_num = 1; //此為所有資料表中的row個數
		
		if (file.isDirectory()) {
			String[]all_file = file.list(); //得到路徑下所有檔案目錄
			for(String filename:all_file) {
				String filetype = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				if(filetype.equals("xlsx")) {//只存取xlsx檔案
					FileInputStream xlsx = new FileInputStream(new File(System.getProperty("user.home")+"/Desktop"+"/data"+"//"+filename));
					XSSFWorkbook workbook = new XSSFWorkbook(xlsx);
					XSSFSheet sheet = workbook.getSheetAt(2);
					int row_num = sheet.getLastRowNum()+1; //此為一個sheet中的row個數
					for (int row_index=1; row_index<row_num; row_index++) {
						Row row = sheet.getRow(row_index);
						
						entity = new Entity();
						
						entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, global_row_num));//加入id屬性
						
						if (row.getCell(0)!=null) {
							entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, row.getCell(0).getStringCellValue().trim()));
						}
						
						if (row.getCell(1)!=null) {
							entity.addProperty(new Property(null, "code_session", ValueType.PRIMITIVE, row.getCell(1).getStringCellValue().trim()));
						}
						if (row.getCell(2)!=null) {
							entity.addProperty(new Property(null, "time_discover", ValueType.PRIMITIVE, row.getCell(2).getDateCellValue()));
						}
						if (row.getCell(3)!=null) {
							entity.addProperty(new Property(null, "image_discover", ValueType.PRIMITIVE, row.getCell(3).getStringCellValue()));
						}
						if (row.getCell(4)!=null) {
							entity.addProperty(new Property(null, "time_last_nochange", ValueType.PRIMITIVE, row.getCell(4).getDateCellValue()));
						}
						if (row.getCell(5)!=null) {
							entity.addProperty(new Property(null, "image_last_nochange", ValueType.PRIMITIVE, row.getCell(5).getStringCellValue()));
						}
						if (row.getCell(6)!=null) {
							entity.addProperty(new Property(null, "change_type", ValueType.PRIMITIVE, row.getCell(6).getStringCellValue()));
						}
						if (row.getCell(7)!=null) {
							String location_tuple = row.getCell(7).toString().split("[\\(\\)]")[1];
							String[] location_coord = location_tuple.split(" ");
							List<Double> list = Arrays.asList(Double.parseDouble(location_coord[0]), Double.parseDouble(location_coord[1]));
							
							entity.addProperty(new Property(null, "location", ValueType.GEOSPATIAL, createPoint(list.get(0),list.get(1))));
						}
						if (row.getCell(8)!=null) {
							Matcher m = Pattern.compile("\\((([^)]+)\\))").matcher(row.getCell(8).toString());
							if(m.find()) {
								String polygon_list = m.group(1).toString().split("[\\(\\)]")[1];
							    String[] polygon_tuple = polygon_list.split(",");
							    List<Double> list = new ArrayList<Double>();
							    
							    for (String s:polygon_tuple) {
							    	//System.out.println(s.toString().trim());
							    	String clean_s = s.toString().trim();
							    	String[] polygon_coord = clean_s.split(" ");
							    	list.add(Double.parseDouble(polygon_coord[0]));
							    	list.add(Double.parseDouble(polygon_coord[1]));
							    }
							    entity.addProperty(new Property(null, "bbox", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
										Arrays.asList(
												createPoint(list.get(0), list.get(1)), 
												createPoint(list.get(2), list.get(3)), 
												createPoint(list.get(4), list.get(5)), 
												createPoint(list.get(6), list.get(7)), 
												createPoint(list.get(8), list.get(9))))));
							}
						}
						if (row.getCell(9)!=null) {
							entity.addProperty(new Property(null, "description", ValueType.PRIMITIVE, row.getCell(9).getStringCellValue()));
						}
						if (row.getCell(10)!=null) {
							entity.addProperty(new Property(null, "last_modified", ValueType.PRIMITIVE, row.getCell(10).getDateCellValue()));
						}
						
						entity.setType(DemoEdmProvider.ET_CHANGE_FQN.getFullQualifiedNameAsString());
					    entity.setId(createId(entity, "ID"));
					    if(entity.getProperty("code_island")!=null && entity.getProperty("code_session")!=null && entity.getProperty("time_discover")!=null
					    		&& entity.getProperty("image_discover")!=null && entity.getProperty("time_last_nochange")!=null && entity.getProperty("image_last_nochange")!=null
					    	 && entity.getProperty("location")!=null && entity.getProperty("bbox")!=null
					    	&& entity.getProperty("last_modified")!=null) { //image常出現問題
					    	changeList.add(entity);
					    	global_row_num++;
					    }
					  
					}
				}
			}
		}
						
	}

	@SuppressWarnings("deprecation")
	private void initSessionSampleData() throws FileNotFoundException, IOException {
		Entity entity = new Entity();
		
		File file = new File(System.getProperty("user.home")+"/Desktop"+"/data"); //存取目前路徑
		int global_row_num = 1; //此為所有資料表中的row個數
		
		if (file.isDirectory()) {
			String[]all_file = file.list(); //得到路徑下所有檔案目錄
			for(String filename:all_file) {
				String filetype = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				if(filetype.equals("xlsx")) {//只存取xlsx檔案
					FileInputStream xlsx = new FileInputStream(new File(System.getProperty("user.home")+"/Desktop"+"/data"+"//"+filename));
					XSSFWorkbook workbook = new XSSFWorkbook(xlsx);
					XSSFSheet sheet = workbook.getSheetAt(3);
					int row_num = sheet.getLastRowNum()+1; //此為一個sheet中的row個數
					for (int row_index=1; row_index<row_num; row_index++) {
						Row row = sheet.getRow(row_index);
						
						entity = new Entity();
						
						entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, global_row_num));//加入id屬性
						
						if (row.getCell(0)!=null) {
							entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, row.getCell(0).getStringCellValue().trim()));
						}
						if (row.getCell(1)!=null) {
							entity.addProperty(new Property(null, "code", ValueType.PRIMITIVE, row.getCell(1).getStringCellValue().trim()));
						}
						if (row.getCell(2)!=null) {
							Matcher m = Pattern.compile("\\((([^)]+)\\))").matcher(row.getCell(2).toString());
							if(m.find()) {
								String polygon_list = m.group(1).toString().split("[\\(\\)]")[1];
							    String[] polygon_tuple = polygon_list.split(",");
							    List<Double> list = new ArrayList<Double>();
							    
							    for (String s:polygon_tuple) {
							    	//System.out.println(s.toString().trim());
							    	String clean_s = s.toString().trim();
							    	String[] polygon_coord = clean_s.split(" ");
							    	list.add(Double.parseDouble(polygon_coord[0]));
							    	list.add(Double.parseDouble(polygon_coord[1]));
							    }
							    entity.addProperty(new Property(null, "boundary", ValueType.GEOSPATIAL, new Polygon(Dimension.GEOMETRY, null, null,
										Arrays.asList(
												createPoint(list.get(0), list.get(1)), 
												createPoint(list.get(2), list.get(3)), 
												createPoint(list.get(4), list.get(5)), 
												createPoint(list.get(6), list.get(7)), 
												createPoint(list.get(8), list.get(9))))));
							}
						}
						if (row.getCell(3)!=null) {
							entity.addProperty(new Property(null, "area", ValueType.PRIMITIVE, row.getCell(3).getNumericCellValue()));
						}
						if (row.getCell(4)!=null) {
							entity.addProperty(new Property(null, "shoreline_length", ValueType.PRIMITIVE, row.getCell(4).getNumericCellValue()));
						}
						if (row.getCell(5)!=null) {
							entity.addProperty(new Property(null, "hardstand_number", ValueType.PRIMITIVE, (int)row.getCell(5).getNumericCellValue()));
						}
						if (row.getCell(6)!=null) {
							entity.addProperty(new Property(null, "road_length", ValueType.PRIMITIVE, row.getCell(6).getNumericCellValue()));
						}
						if (row.getCell(7)!=null) {
							entity.addProperty(new Property(null, "airport_length", ValueType.PRIMITIVE, row.getCell(7).getNumericCellValue()));
						}
						if (row.getCell(8)!=null) {
							entity.addProperty(new Property(null, "pier_number", ValueType.PRIMITIVE, (int)row.getCell(8).getNumericCellValue()));
						}
						if (row.getCell(9)!=null) {
							entity.addProperty(new Property(null, "lighthouse_number", ValueType.PRIMITIVE, (int)row.getCell(9).getNumericCellValue()));
						}
						if (row.getCell(10)!=null) {
							entity.addProperty(new Property(null, "building_number", ValueType.PRIMITIVE, (int)row.getCell(10).getNumericCellValue()));
						}
						if (row.getCell(11)!=null) {
							entity.addProperty(new Property(null, "tide", ValueType.PRIMITIVE, row.getCell(11).getNumericCellValue()));
						}
						if (row.getCell(12)!=null) {
							entity.addProperty(new Property(null, "start_time", ValueType.PRIMITIVE, row.getCell(12).getDateCellValue()));
						}
						if (row.getCell(13)!=null) {
							entity.addProperty(new Property(null, "end_time", ValueType.PRIMITIVE, row.getCell(13).getDateCellValue()));
						}
						if (row.getCell(14)!=null) {
							entity.addProperty(new Property(null, "note", ValueType.PRIMITIVE, row.getCell(14).getStringCellValue()));
						}
						if (row.getCell(15)!=null) {
							entity.addProperty(new Property(null, "code_island", ValueType.PRIMITIVE, row.getCell(15).getStringCellValue()));
						}
						
						entity.setType(DemoEdmProvider.ET_SESSION_FQN.getFullQualifiedNameAsString());
					    entity.setId(createId(entity, "ID"));
					    if(entity.getProperty("code_island")!=null && entity.getProperty("code")!=null
					    		&& entity.getProperty("note")!=null) { //image常出現問題
					    	sessionList.add(entity);
					    	global_row_num++;
					    }
					}
				}
			}
		}
		
		
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
