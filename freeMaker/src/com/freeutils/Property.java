package com.freeutils;

/**
 * 实体对应的属性类
 * @author  lvzb.software@qq.com
 *
 */
public class Property {
    // 属性数据类型
    private String javaType;
    // 属性名称
    private String propertyName;
    // 属性长度
    private String propertylength;
    // 属性备注
    private String propertyComments;
     
    private PropertyType propertyType;
     
    public String getJavaType() {
        return javaType;
    }
 
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
 
    public String getPropertyName() {
        return propertyName;
    }
 
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
 
    public PropertyType getPropertyType() {
        return propertyType;
    }
 
    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

	public String getPropertylength() {
		return propertylength;
	}

	public void setPropertylength(String propertylength) {
		this.propertylength = propertylength;
	}

	public String getPropertyComments() {
		return propertyComments;
	}

	public void setPropertyComments(String propertyComments) {
		this.propertyComments = propertyComments;
	}
         
}
