package com.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @date 2015-9-21
 * @author liangl
 * @Description: oracle 中数据库字段类型转换为 bean类型
 * All rights reserved.
 */
public class ChangeType {
	
	public static void main(String[] args){
		String sss="VARCHAR2";
		System.out.println(getType(sss));
	}
	
	public static String getType(String from){
		Map<String,String>  map=new HashMap();
		map.put("DATE", "Date");
		map.put("VARCHAR2", "String");
		map.put("CHAR", "String");
		map.put("NUMBER", "int");
		if(map.containsKey(from)){
			return map.get(from);
		}else{
			return from.substring(0,1)+from.substring(1).toLowerCase();
		}
	}
	
	public static String getName(String from){
		return from.toLowerCase();
	}
	


}
