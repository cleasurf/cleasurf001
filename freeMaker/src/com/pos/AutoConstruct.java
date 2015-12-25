package com.pos;

import java.sql.SQLException;

public class AutoConstruct {

	/**
	 * @Description: 开始生成文件，路径为：
	 * @param args   
	 * @return void  
	 * @throws SQLException 
	 * @throws
	 * @author liangl
	 * @date 2015-11-27
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String javaPackage="com.payment.smp.paramManager";
		String tableName="T_ERROR_DICT";
		String yewuName="错误字典";
		String projectName="pay";
		//创建java文件
		new JavaConstuctor().createJava(javaPackage,tableName, yewuName,projectName);
		//创建tradeIdMapping
		new TradeIdMappingConstuctor().createTradeIdMapping(javaPackage,tableName, yewuName,projectName);
		//创建交易
		new TradeConstuctor().createTrade(javaPackage,tableName, yewuName,projectName);
		//创建jsp
		new JspConstuctor().createJsp(javaPackage,tableName, yewuName,projectName);
		//创建js
		new JsConstuctor().createJs(javaPackage,tableName, yewuName,projectName);
		//创建tql.xml 文件
		new TqlxmlConstuctor().createTqlxml(javaPackage, tableName, yewuName,projectName);
	}

}
