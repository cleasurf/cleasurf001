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
public class TradeIdMappingConstuctor {
     
    private static File javaFile = null;
 
    public static void createTradeIdMapping(String javaPackage,String tableName,String yewuName,String projectName) throws SQLException {
        Configuration cfg = new Configuration();    
        try { 
            // 步骤一：指定 模板文件从何处加载的数据源，这里设置一个文件目录
            cfg.setDirectoryForTemplateLoading(new File("./template"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
             
            // 步骤二：获取 模板文件
            Template template = cfg.getTemplate("posTradeIdMapping.ftl");
             
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
    
    public static Map<String,Object> createDataModelFromDb(String javaPackage,String tabName,String yewuName,String projectName) throws SQLException {
    	TableNameUtils  tableNametool=new TableNameUtils(); //获得表名类名工具类
    	Map<String,Object> root = new HashMap<String,Object>();
        Entity entity = new Entity();
        entity.setYewuName(yewuName);
        entity.setJavaPackage(javaPackage);         // 创建包名
        entity.setClassName(tableNametool.className(tabName));    // 创建类名
        entity.setTableName(tableNametool.getTableName(tabName)); // 创建类名
        entity.setConstructors(false);              // 是否创建构造函数
        // user.setSuperclass(person);
        List<Property> propertyList = new ArrayList<Property>();
       
        // 创建.java类文件
        File outDirFile = new File("./src-template");
        if(!outDirFile.exists()){
            outDirFile.mkdir();
        }
        Property attribute1 = new Property();
        String type=".xml";
        javaFile =tableNametool.toJavaFilename(outDirFile, entity.getJavaPackage(), "tradeIdMapping"+entity.getClassName(),type);
        OpePropertype  oper=tableNametool.tabOpe(tabName);
        entity.setOpepropertype(oper);
        root.put("entity", entity);
        root.put("queryTab", tableNametool.opeName(tabName));
        root.put("projectName", projectName);
        return root;
    }
   
 
}
