package com.freeutils;

import java.util.List;

/**
 * 实体类
 * @author  lvzb.software@qq.com
 *
 */
public class Entity {
    // 实体所在的包名
    private String javaPackage;
    
    // 实体表名小写
    private String tableName;
    
    // 业务名称
    private String yewuName;
    
    // 实体类名
    private String className;
    
    // 父类名
    private String superclass;
    
    
    // 属性集合
    List<Property> properties;
    // 是否有构造函数
    private boolean constructors;   
    
    private OpePropertype opepropertype;
     
    public OpePropertype getOpepropertype() {
		return opepropertype;
	}

	public void setOpepropertype(OpePropertype opepropertype) {
		this.opepropertype = opepropertype;
	}

	public String getJavaPackage() {
        return javaPackage;
    }
     
    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }
     
    public String getClassName() {
        return className;
    }
     
    public void setClassName(String className) {
        this.className = className;
    }
     
    public String getSuperclass() {
        return superclass;
    }
     
    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }
     
    public List<Property> getProperties() {
        return properties;
    }
     
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
     
    public boolean isConstructors() {
        return constructors;
    }
     
    public void setConstructors(boolean constructors) {
        this.constructors = constructors;
    }

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getYewuName() {
		return yewuName;
	}

	public void setYewuName(String yewuName) {
		this.yewuName = yewuName;
	}   
 
}
