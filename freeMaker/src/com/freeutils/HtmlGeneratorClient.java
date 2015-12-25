package com.freeutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
 
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
 
public class HtmlGeneratorClient {
 
    public static void main(String[] args) {
        try {
            Configuration cfg = new Configuration();
            // 指定模板文件从何处加载的数据源，这里设置成一个文件目录
            cfg.setDirectoryForTemplateLoading(new File("./template"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
             
            // 获取或创建模板
            Template template = cfg.getTemplate("test.ftl");
             
            // 创建数据模型
            Map root = new HashMap();
            root.put("user", "Big Joe");        
            Map latest = new HashMap();
            root.put("latestProduct", latest);
            latest.put("url", "http://blog.csdn.net/janice0529/article/details/products/greenmouse.html");
            latest.put("name", "green mouse");
             
            // 将模板和数据模型合并 输出到Console
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
            out.flush();
             
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
 
    }
 
}
