package com.utils.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel0307 {
	
	private static int TYPE_NUMERIC = 0;//数字类型
	private static int TYPE_STRING = 1;//字符串类型
	
	public static void main(String[] args) throws InvalidFormatException{
		try {
			importExcel("2007");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void importExcel(String filetype) throws IOException, InvalidFormatException{
		InputStream ins = new FileInputStream("D://temp//biao//ee.xlsx");
		filetype="2007";
		OPCPackage pkg = null;
		long t1 = System.currentTimeMillis();
		Workbook workbook = null;
		if("2003".equals(filetype)) {
			workbook = new HSSFWorkbook(ins);	
		} else if ("2007".equals(filetype)) {
			pkg = OPCPackage.open(ins);
			workbook = new XSSFWorkbook(pkg);
		}
		long t2 = System.currentTimeMillis();
		int end = 0;
		Sheet sheet = workbook.getSheetAt(0);// 按索引获取sheet引用       
		end = sheet.getLastRowNum();//sheet起结束行索引   

		Row row = null;
		Row headRow = sheet.getRow(0);

		int col = headRow.getLastCellNum();//列数
		int rowNum = sheet.getLastRowNum();    
		System.out.println("总行数："+rowNum);
		System.out.println("总列数："+col);
		
		int commitFlag = 0;
		String varsa ="";
		String varsb ="";
		for (int i = 84900; i <= 84966; i++) {
			varsa=varsb="";
			row = sheet.getRow(i);
			try{
			varsa = ExcelUtil.getCellVal(row.getCell((short) 0), TYPE_STRING);
			varsb = ExcelUtil.getCellVal(row.getCell((short) 1), TYPE_STRING);
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			System.out.println("i="+i+"-----varsa="+varsa+"-----varsb="+varsb);
		}
			
	}

}
