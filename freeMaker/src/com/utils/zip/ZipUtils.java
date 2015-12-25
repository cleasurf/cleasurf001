package com.utils.zip;
  
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
  
/** 
 * <p> 
 * ZIP工具包 
 * </p> 
 * <p> 
 * 依赖：ant-1.7.1.jar 
 * </p> 
 *  
 */  
public class ZipUtils {  
      
    /** 
     * 使用GBK编码可以避免压缩中文文件名乱码 
     */  
    private static final Charset CHINESE_CHARSET = Charset.forName("GBK");  
      
    /** 
     * 文件读取缓冲区大小 
     */  
    private static final int CACHE_SIZE = 1024;  
      
    /** 
     * <p> 
     * 压缩文件 
     * </p> 
     *  
     * @param sourceFolder 压缩文件夹 
     * @param zipFilePath 压缩文件输出路径 
     * @throws Exception 
     */  
    public static void zip(String sourceFolder, String zipFilePath) throws Exception {  
        OutputStream out = new FileOutputStream(zipFilePath);  
        BufferedOutputStream bos = new BufferedOutputStream(out);  
        // 解决中文文件名乱码  
        ZipOutputStream zos =  new ZipOutputStream(bos);  
        File file = new File(sourceFolder);  
        String basePath = null;  
        if (file.isDirectory()) {  
            basePath = file.getPath();  
        } else {  
            basePath = file.getParent();  
        }  
        zipFile(file, basePath, zos);  
        zos.closeEntry();  
        zos.close();  
        bos.close();  
        out.close();  
    }  
      
    /** 
     * <p> 
     * 递归压缩文件 
     * </p> 
     *  
     * @param parentFile 
     * @param basePath 
     * @param zos 
     * @throws Exception 
     */  
    private static void zipFile(File parentFile, String basePath, ZipOutputStream zos) throws Exception {  
        File[] files = new File[0];  
        if (parentFile.isDirectory()) {  
            files = parentFile.listFiles();  
        } else {  
            files = new File[1];  
            files[0] = parentFile;  
        }  
        String pathName;  
        InputStream is;  
        BufferedInputStream bis;  
        byte[] cache = new byte[CACHE_SIZE];  
        for (File file : files) {  
            if (file.isDirectory()) {  
                pathName = file.getPath().substring(basePath.length() + 1) + "/";  
                zos.putNextEntry(new ZipEntry(pathName));  
                zipFile(file, basePath, zos);  
            } else {  
                pathName = file.getPath().substring(basePath.length() + 1);  
                is = new FileInputStream(file);  
                bis = new BufferedInputStream(is);  
                zos.putNextEntry(new ZipEntry(pathName));  
                int nRead = 0;  
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {  
                    zos.write(cache, 0, nRead);  
                }  
                bis.close();  
                is.close();  
            }  
        }  
    }  
      
  /**
  * 
  * @Title: 解压上传的文件
  * @Package  
  * @Description: TODO(用一句话描述该文件做什么) 
  * @author zhl   
  * @date 2015年7月27日
  * @version V1.0
  * @return List<String> 所有文件的全目录
   */
    public static List<String> unZip(String zipFilePath, String destDir) throws Exception {   
    	List <String>  allFile = new ArrayList<String>(); 
    	ZipFile zipFile =   new ZipFile(zipFilePath);  
        Enumeration<?> emu = zipFile.entries();
        BufferedInputStream bis = null; 
        FileOutputStream fos = null;  
        BufferedOutputStream bos = null;   
        File file = null; 
        File parentFile = null; 
        ZipEntry entry = null;  
        byte[] cache = new byte[CACHE_SIZE];  
        //循环ZIP里面的所有文件
        while (emu.hasMoreElements()) {  
            entry = (ZipEntry) emu.nextElement();  
            if (entry.isDirectory()) {  
                new File(destDir + entry.getName()).mkdirs();  
                continue;  
            }  
            bis = new BufferedInputStream(zipFile.getInputStream(entry));  
            String readFilePath = destDir + entry.getName();
            //添加所有的文件目前录
            allFile.add(readFilePath);
            file = new File(readFilePath); 
            parentFile = file.getParentFile();  
            if (parentFile != null && (!parentFile.exists())) {  
                parentFile.mkdirs();  
            }  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos, CACHE_SIZE);  
            int nRead = 0;  
            while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {  
                fos.write(cache, 0, nRead);  
            }  
            bos.flush();  
            bos.close();  
            fos.close();  
            bis.close();  
        }  
        
        zipFile.close();  
        return allFile;
    }  
      
    public static void main(String[] args) throws Exception {  
        String sourceFolder = "E:\\hd\\bug文件\\加班调休单";  
        String zipFilePath = "F:\\tt.zip";      
        String destDir = "f:/tt1/";  
       // ZipUtils.zip(sourceFolder, zipFilePath);  
        List<String> list = ZipUtils.unZip(zipFilePath, destDir);  
        for (String string : list) {
			System.out.println(string);
		}
    	System.out.println("执行完成");
    }  
       
}  