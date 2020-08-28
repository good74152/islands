import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Demo {
	public static void main(String[] args) throws IOException {
		
		File file = new File(System.getProperty("user.dir")); //存取目前路徑
		System.out.println(System.getProperty("user.dir"));
		int global_row_num = 0;
		if (file.isDirectory()) {
			
			String[]all_file = file.list(); //得到路徑下所有檔案目錄
			for(String filename:all_file) {
				String filetype = filename.substring(filename.lastIndexOf(".")+1, filename.length());
				if(filetype.equals("xlsx")) { //只存取xlsx檔案
					System.out.println(filename);
					FileInputStream xlsx = new FileInputStream(new File(System.getProperty("user.dir")+"\\"+filename));
					XSSFWorkbook workbook = new XSSFWorkbook(xlsx);
					XSSFSheet sheet = workbook.getSheetAt(0);
					int row_num = sheet.getLastRowNum()+1;
					
					for (int row_index=1; row_index<row_num; row_index++) {
						Row row = sheet.getRow(row_index);
					
						System.out.println(row.getCell(0));
						System.out.println(row.getCell(1));
						System.out.println(row.getCell(2));
						if(row.getCell(3)!= null) {
							String location_tuple = row.getCell(3).toString().split("[\\(\\)]")[1];
							String[] location_coord = location_tuple.split(" ");
							List<Double> list = Arrays.asList(Double.parseDouble(location_coord[0]), Double.parseDouble(location_coord[1])); 
							
							System.out.println("x座標 :"+list.get(0));
					    	System.out.println("y座標 :"+list.get(1));
						}
						System.out.println(row.getCell(4));
						if(row.getCell(5)!= null) {
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
						    for (int i=0; i<list.size(); i=i+2) {
						    	System.out.println("x座標 :"+list.get(i));
						    	System.out.println("y座標 :"+list.get(i+1));
						    }
						    
						}
						}
						System.out.println(Double.parseDouble(row.getCell(6).toString().trim()));
						System.out.println(row.getCell(7));
						System.out.println(row.getCell(8));
						System.out.println(row.getCell(9));
						System.out.println(row.getCell(10));
						System.out.println(row.getCell(11));
						System.out.println(row.getCell(12));
						System.out.println(row.getCell(13));
						System.out.println(Double.parseDouble(row.getCell(14).toString().trim()));
						System.out.println(row.getCell(15));
						System.out.println(global_row_num);
						global_row_num++;
					}
			}
		}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		/*XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet");
		
		int rowNo = 0;
		Row row = sheet.createRow(rowNo++);
		int cellnum = 0;
		Cell cell = row.createCell(cellnum++);
		cell.setCellValue("Demical");
		cell = row.createCell(cellnum++);
		cell.setCellValue(2.5);
		
		cellnum = 0;
		row = sheet.createRow(rowNo++);
		cell = row.createCell(cellnum++);
		cell.setCellValue("Text");
		cell = row.createCell(cellnum++);
		cell.setCellValue("Text data");
		
		FileOutputStream out = new FileOutputStream(new File("test1.xlsx"));
		wb.write(out);
		out.close();
		System.out.println("done!");*/
	}

	
	}
}