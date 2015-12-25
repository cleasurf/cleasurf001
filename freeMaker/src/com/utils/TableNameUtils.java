package com.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.freeutils.OpePropertype;
import com.freeutils.Property;
import com.freeutils.PropertyType;

/**
 * 
 * @date 2015-11-28
 * @author liangl
 * @Description: 表名类名工具类
 */
public class TableNameUtils {
	
	public static void main(String[] args){
		System.out.println(opeName("T_ARE_SHIT"));
		
	}
	
	/**
     * 创建.java文件所在路径 和 返回.java文件File对象
     * @param outDirFile 生成文件路径
     * @param javaPackage java包名
     * @param javaClassName java类名
     * @return
     */
    public  static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName,String type) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + type);
        if(!packagePath.exists()){
            packagePath.mkdirs();
        }
        return file;
    }
	
	public static List getDBData(String tabName) throws SQLException{
		List<Property> propertyList = new ArrayList<Property>();
        Connection conn =new conn().getConnection(); 
        String selectSql=
						"SELECT b.column_name   column_name\n" +
						"      ,b.data_type      data_type\n" + 
						"      ,b.data_length    data_length\n" + 
						"      ,a.comments comments\n" + 
						"FROM user_col_comments a\n" + 
						"      ,all_tab_columns b\n" + 
						"WHERE a.table_name = b.table_name\n" + 
						"      and\n" + 
						"       a.column_name=  b.COLUMN_NAME\n" + 
						"      and\n" + 
						"      a.table_name = '"+tabName+"'"+
						" order by b.column_id ";
        
    	conn.setAutoCommit(false);//事物开始                  		
		Statement sm = conn.createStatement();
		ResultSet    ss= sm.executeQuery(selectSql);
		ChangeType  change=new ChangeType();
		while(ss.next()){
			Property attribute1 = new Property();
			attribute1.setJavaType(change.getType(ss.getString("DATA_TYPE").toString()));
			attribute1.setPropertyName(change.getName(ss.getString("COLUMN_NAME").toString()));
			attribute1.setPropertyComments(change.getName(ss.getString("COMMENTS").toString()));
	        attribute1.setPropertyType(PropertyType.String);
	        propertyList.add(attribute1);
		}
		return propertyList;
	}
	
	 /**
     * 
     * @Description: 取得各种数据库操作名称
     * @param tabName
     * @return   
     * @return Map  
     * @throws
     * @author liangl
     * @date 2015-9-21
     */
    public static OpePropertype tabOpe(String tabName){
    	tabName=tabName.toLowerCase();
    	OpePropertype  oper=new OpePropertype();
    	String queryTab="query_"+tabName;  //查询表
    	String queryTabById="query_"+tabName+"_ById";   //查询唯一
    	String insertTab="insert_"+tabName;   //插入
    	String updateTab="update_"+tabName;   //修改
    	String deleteTab="delete_"+tabName+"_ById";   //删除
    	oper.setQueryTab(queryTab);
    	oper.setQueryTabById(queryTabById);
    	oper.setInsertTab(insertTab);
    	oper.setUpdateTab(updateTab);
    	oper.setDeleteTab(deleteTab);
    	return oper;
    }
    
    /**
     * 
     * @Description:去掉数据库之间横杠
     * @param tabName
     * @return   
     * @return Map  
     * @throws
     * @author liangl
     * @date 2015-9-21
     */
    public static String opeName(String tabName){
    	String[] newName= new String[]{};
    	newName=tabName.split("_");
    	String name=newName[0];
    	for(int i=1;i<newName.length;i++){
    		name+=(newName[i].substring(0,1).toUpperCase()+newName[i].substring(1).toLowerCase());
    	}
    	return name;
    }
    
    /**
     * 
     * @Description:   得到类名，第一个字母大写，后边字母小写
     * @param tabName  表名
     * @return         
     * @return String  
     * @throws
     * @author liangl
     * @date 2015-9-21
     */
    public static String className(String tabName){
    	String ss=opeName(tabName);
    	return ss.substring(0,1).toUpperCase()+ss.substring(1);
    }
    
    /**
     * 
     * @Description: 表名变成小写
     * @return   
     * @return String  
     * @throws
     * @author liangl
     * @date 2015-11-27
     */
    public static String getTableName(String name){
    	return name.toLowerCase();
    }

}
