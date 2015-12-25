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
 * 生成java代码段
 * @author samon_liang@foxmail.com
 *
 */
public class EntityOpe {
     
    private static File javaFile = null;
 
    public static void main(String[] args) throws SQLException {
        Configuration cfg = new Configuration();    
        try { 
            // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
            cfg.setDirectoryForTemplateLoading(new File("./template"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
             
            // 步骤二：获取 模板文件
            Template template = cfg.getTemplate("crud.ftl");
             
            // 步骤三：创建 数据模型
            Map<String, Object> root = createDataModelFromDb();
             
            // 步骤四：合并 模板 和 数据模型
            // 创建.java类文件
            if(javaFile != null){
                Writer javaWriter = new FileWriter(javaFile);
                template.process(root, javaWriter);
                javaWriter.flush();
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
    
    public static Map<String,Object> createDataModelFromDb() throws SQLException {
    	Map<String,Object> root = new HashMap<String,Object>();
        Entity entity = new Entity();
        entity.setJavaPackage("com.study.entity"); // 创建包名
        String tabName="t_hom_classify";
        entity.setClassName(className(tabName));    // 创建类名
        entity.setConstructors(false);              // 是否创建构造函数
        // user.setSuperclass(person);
        List<Property> propertyList = new ArrayList<Property>();
        // 将属性集合添加到实体对象中
        entity.setProperties(propertyList);
        // 创建.java类文件
        File outDirFile = new File("./src-template");
        if(!outDirFile.exists()){
            outDirFile.mkdir();
        }
        Property attribute1 = new Property();
        javaFile = toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName());
        OpePropertype  oper=tabOpe(tabName);
        entity.setOpepropertype(oper);
        root.put("entity", entity);
        root.put("queryTab", opeName(tabName));
        
        return root;
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
 
}
