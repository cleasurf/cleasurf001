package com.utils.excel;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DataInsert {

	public static Connection conn;
	
	public static void main(String[] args) {
		 		try {
					conn = new DB2conn().getConnection();
					conn.setAutoCommit(false);
			        insertData();//tbname，为要插入的数据表名
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

	}
	
	public static void insertData(){
		try {
						
			//casilin:插入数据，先从excel中读取数据
			ExcelReader excelReader = new ExcelReader();
		    
		    //开始建立插入的sql语句,每一次插入的开头都是不变的,都是字段名
		    StringBuffer sqlBegin = new StringBuffer();
		    
		    //下面读取字段内容
		    POIFSFileSystem fs;
		    HSSFWorkbook wb;
		    HSSFSheet sheet;
		    HSSFRow row;
		      
		    InputStream  is = new FileInputStream("D://temp//biao//ab.xls");
	        fs = new POIFSFileSystem(is);    
	        wb = new HSSFWorkbook(fs);  
	        sheet = wb.getSheetAt(0);
	            
	        //得到总行数    
	        int rowNum = sheet.getLastRowNum();    
	        row = sheet.getRow(0);    
	        int colNum = row.getPhysicalNumberOfCells();    
	        //正文内容应该从第二行开始,第一行为表头的标题    
	        String sql = new String(sqlBegin);
	        String temp="";
	        Statement  stmt = conn.createStatement();
	        int count=0;
	        System.out.println("rowNum="+rowNum);
	        for (int i = 0; i <= rowNum; i++) {    
	            row = sheet.getRow(i);    
            	temp = excelReader.getStringCellValue(row.getCell((short) 0)).trim();
	            sql = "insert into ";
	            System.out.println("sql="+sql.toString());
	           // stmt.execute(sql);
	            stmt.executeUpdate(sql);
	            sql = "";
	            count++;
	            if(count>500){
	            	conn.commit();
	            	count=0;
	            	System.out.println("提交行数为："+i);
	            }else if(i>63700){
	            	conn.commit();
	            	System.out.println("提交行数为："+i);
	            }
	        }
			
		}  catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {    
            e.printStackTrace();    
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	
	
	
	


}

