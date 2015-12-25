package com.freeutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utils.ChangeType;
import com.utils.conn;

 
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 自动生成实体类客户端
 * @author lvzb.software@qq.com
 *
 */
public class EntityGeneratorClient {
     
    private static File javaFile = null;
 
    public static void main(String[] args) throws SQLException {
        Configuration cfg = new Configuration();    
        try { 
            // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
            cfg.setDirectoryForTemplateLoading(new File("./template"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
             
            // 步骤二：获取 模板文件
            Template template = cfg.getTemplate("entity.ftl");
             
            // 步骤三：创建 数据模型
            // 表明必须大写 
            Map<String, Object> root = createDataModelFromDb("T_DEVELOPER");
             
            // 步骤四：合并 模板 和 数据模型
            // 创建.java类文件
            if(javaFile != null){
                Writer javaWriter = new FileWriter(javaFile);
                template.process(root, javaWriter);
                javaWriter.flush();
                System.out.println("文件生成路径： "+ javaFile.getCanonicalPath());
                 
                javaWriter.close();
            }
            // 输出到Console控制台
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
            out.flush();
            out.close();
             
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
 
    }
    
    public static Map<String,Object> createDataModelFromDb(String tabName) throws SQLException {
    	Map<String,Object> root = new HashMap<String,Object>();
        Entity user = new Entity();
        user.setJavaPackage("com.study.entity"); // 创建包名
        String className=className(tabName);
        user.setClassName(className);               // 创建类名
        user.setConstructors(false);              // 是否创建构造函数
        // user.setSuperclass(person);
         
        List<Property> propertyList = new ArrayList<Property>();
        Connection conn =new conn().getConnection(); 
        String selectSql="SELECT *  FROM user_tab_columns  WHERE table_name = '"+tabName+"'";
        
    	conn.setAutoCommit(false);//事物开始				
		Statement sm = conn.createStatement();
		ResultSet    ss= sm.executeQuery(selectSql);
		ChangeType  change=new ChangeType();
		while(ss.next()){
			Property attribute1 = new Property();
			attribute1.setJavaType(change.getType(ss.getString("DATA_TYPE").toString()));
			attribute1.setPropertyName(change.getName(ss.getString("COLUMN_NAME").toString()));
	        attribute1.setPropertyType(PropertyType.String);
	        propertyList.add(attribute1);
		}
		
		
         
        // 将属性集合添加到实体对象中
        user.setProperties(propertyList);
        // 创建.java类文件
        File outDirFile = new File("./src-template");
        if(!outDirFile.exists()){
            outDirFile.mkdir();
        }
         
        javaFile = toJavaFilename(outDirFile, user.getJavaPackage(), user.getClassName());
        conn.commit(); 
        conn.close();
        root.put("entity", user);
        return root;
    }
     
    /**
     * 创建数据模型
     * @return
     */
    private static Map<String,Object> createDataModel() {
        Map<String,Object> root = new HashMap<String,Object>();
        Entity user = new Entity();
        user.setJavaPackage("com.study.entity"); // 创建包名
        user.setClassName("User");    // 创建类名
        user.setConstructors(true); // 是否创建构造函数
        // user.setSuperclass(person);
         
        List<Property> propertyList = new ArrayList<Property>();
         
        // 创建实体属性一 
        Property attribute1 = new Property();
        attribute1.setJavaType("String");
        attribute1.setPropertyName("name");
        attribute1.setPropertyType(PropertyType.String);
         
        // 创建实体属性二
        Property attribute2 = new Property();
        attribute2.setJavaType("int");
        attribute2.setPropertyName("age");
        attribute2.setPropertyType(PropertyType.Int);
         
        propertyList.add(attribute1);
        propertyList.add(attribute2);
         
        // 将属性集合添加到实体对象中
        user.setProperties(propertyList);
         
        // 创建.java类文件
        File outDirFile = new File("./src-template");
        if(!outDirFile.exists()){
            outDirFile.mkdir();
        }
         
        javaFile = toJavaFilename(outDirFile, user.getJavaPackage(), user.getClassName());
         
        root.put("entity", user);
        return root;
    }
     
     
    /**
     * 创建.java文件所在路径 和 返回.java文件File对象
     * @param outDirFile 生成文件路径
     * @param javaPackage java包名
     * @param javaClassName java类名
     * @return
     */
    private static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + ".java");
        if(!packagePath.exists()){
            packagePath.mkdirs();
        }
        return file;
    }
    /**
     * 
     * @Description:   类名
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
 
}
