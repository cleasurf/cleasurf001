package com.pos;

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

import com.freeutils.Entity;
import com.freeutils.OpePropertype;
import com.freeutils.Property;
import com.freeutils.PropertyType;
import com.utils.ChangeType;
import com.utils.TableNameUtils;
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
public class JavaConstuctor {
     
    private static File javaFile = null;
 
    public static void createJava(String javaPackage,String tableName,String yewuName,String projectName) throws SQLException {
        Configuration cfg = new Configuration();    
        try { 
            // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
            cfg.setDirectoryForTemplateLoading(new File("./template"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
             
            // 步骤二：获取 模板文件
            Template template = cfg.getTemplate("posCrud.ftl");
             
            // 步骤三：创建 数据模型
            // 传入数据库de表名  ,(备注：大写的)
            Map<String, Object> root = createDataModelFromDb(javaPackage,tableName,yewuName,projectName);
             
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
    
    /**
     * 
     * @Description: TODO
     * @param javaPackage  包路径
     * @param tabName  表名称，大写
     * @param yewuName 业务
     * @return
     * @throws SQLException   
     * @return Map<String,Object>  
     * @throws
     * @author liangl
     * @date 2015-11-27
     */
    public static Map<String,Object> createDataModelFromDb(String javaPackage,String tabName,String yewuName,String projectName) throws SQLException {
    	TableNameUtils  tableNametool=new TableNameUtils(); //获得表名类名工具类
    	tabName=tabName.toUpperCase();  //强制转换为大写
    	Map<String,Object> root = new HashMap<String,Object>();
        Entity entity = new Entity();
        entity.setYewuName(yewuName);        //设置业务名
        entity.setJavaPackage(javaPackage); // 设置包名
        entity.setClassName(tableNametool.className(tabName));    // 设置类名
        entity.setTableName(tableNametool.getTableName(tabName));    // 设置表名
        root.put("queryTab", tableNametool.opeName(tabName));
        root.put("projectName", projectName);
        entity.setConstructors(false);              // 是否创建构造函数
        // user.setSuperclass(person);
        // 将属性集合添加到实体对象中
        entity.setProperties(tableNametool.getDBData(tabName));  //设置表结构属性值
        // 创建.java类文件
        File outDirFile = new File("./src-template");
        if(!outDirFile.exists()){
            outDirFile.mkdir();
        }
        //创建文件，包含文件名称，文件类型，文件存放路径
        String type=".java";
        javaFile = tableNametool.toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName(),type);  
        OpePropertype  oper=tableNametool.tabOpe(tabName);
        entity.setOpepropertype(oper);
        root.put("entity", entity);
        
        return root;
    }
    
      
     
   
     
     
    
 

}
