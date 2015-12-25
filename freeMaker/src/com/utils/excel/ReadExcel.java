package com.utils.excel;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class ReadExcel {
	/** 
	 * @ClassName: ReadExcel 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 梁雷
	 * @date Oct 3, 2012 6:16:34 PM 
	 *  
	 */
	public static void main(String[] args){
		try{
			//创建文件输入流对象
		FileInputStream is=new FileInputStream("d://bbbbb//excel.xls");   
		//创建poi文件系统
		POIFSFileSystem ts=new POIFSFileSystem(is);
		
		//获取文档对象
		HSSFWorkbook wb=new HSSFWorkbook(ts);
		
		HSSFSheet sheet=null;

		HSSFRow row=null;
		
	    sheet=wb.getSheetAt(0);
		for(int i=0;sheet.getRow(i)!=null;i++){
			row=sheet.getRow(i);
			for(int j=0;row.getCell(j)!=null;j++){
				System.out.print(row.getCell(j)+"  ");
			}
			System.out.println();
		}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}
}
