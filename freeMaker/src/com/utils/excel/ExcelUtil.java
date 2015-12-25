package com.utils.excel;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {
	
	private static int CELL_TYPE_NUMERIC = 0;//��������
	private static int CELL_TYPE_STRING = 1;//�ַ�����
	
	/**
	 * ��ȡ��Ԫ���ֵ
	 * @param cell -��Ԫ��
	 * @param retTypeFlag -�������ͱ�־ ��Cell�е�cellType���ͱ���һ�� 1���ַ����� 0����ֵ���ͣ��ɲ鿴POI�ĵ�
	 */
	public static String getCellVal(Cell cell, int retTypeFlag) {
		String result = "";
		if (cell == null){
			return "";
		}
		if(retTypeFlag == 1){
			if (cell.getCellType() == CELL_TYPE_STRING) {
				result = cell.getStringCellValue();
			} else if (cell.getCellType() == CELL_TYPE_NUMERIC) {
				//double cellVal = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("0");
				result = df.format(cell.getNumericCellValue());  
				//long cellVal_l = new Double(cellVal).longValue();
				//result = String.valueOf(cellVal_l);
			} else {
				result = cell.toString();
			}
		} else if(retTypeFlag == 0){
			if (cell.getCellType() == CELL_TYPE_NUMERIC) {
				double cellVal = cell.getNumericCellValue();
				result = String.valueOf(cellVal);
			} else {
				result = cell.toString();
			}
		}
		if (result != null || !"".equals(result)) {
			result = result.trim();
		}
		return result;
	}

}
