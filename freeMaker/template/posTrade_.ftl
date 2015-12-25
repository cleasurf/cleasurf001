<?xml version="1.0" encoding="UTF-8"?>
<toruk>
  <trades>
  
    <!-- 查询${entity.yewuName} -->
    <trade id="${queryTab}Query">
      <desc>查询${entity.yewuName}</desc>
      <ext_checkLogin>true</ext_checkLogin>
      <ext_checkRight>true</ext_checkRight>
      <classMethod>
        ${entity.javaPackage}.${entity.className}:${queryTab}Query
      </classMethod>
      <logPrint>true</logPrint>
      <logPrefix></logPrefix>
      <#list entity.properties as property>
      <#if (property.javaType) =='Date' >
      <postCheck require="false" varNameCH="${property.propertyComments}开始" >${property.propertyName}_start</postCheck>
      <postCheck require="false" varNameCH="${property.propertyComments}时间" >${property.propertyName}_end</postCheck>
      <#else>
      <postCheck require="false" varNameCH="${property.propertyComments}">${property.propertyName}</postCheck>
	  </#if>
	  </#list> 
      <postCheck require="false" varNameCH="当前展示页">currentPage</postCheck>
      <status>0</status>
      <nextPage>/manager/developmanger/${entity.className}.jsp</nextPage>
    </trade> 
    
    
     <!--  新增${entity.yewuName} -->
    <trade id="${queryTab}Add">
      <desc>新增${entity.yewuName}</desc>
      <ext_checkLogin>true</ext_checkLogin>
      <classMethod>
        ${entity.javaPackage}.${entity.className}:${queryTab}Add
      </classMethod>
      <logPrint>true</logPrint>
      <logPrefix></logPrefix>
      <#list entity.properties as property>
      <postCheck require="false" varNameCH="${property.propertyComments}">add_${property.propertyName}</postCheck>
	  </#list> 
      <status>0</status>
    </trade>
    
   
    
    <!-- 修改${entity.yewuName}-->
    <trade id="${queryTab}Edit">
      <desc>修改${entity.yewuName}</desc>
      <ext_checkLogin>true</ext_checkLogin>
      <classMethod>
        ${entity.javaPackage}.${entity.className}:${queryTab}Edit
      </classMethod>
      <logPrint>true</logPrint>
      <logPrefix></logPrefix>
      <#list entity.properties as property>
      <postCheck require="false" varNameCH="${property.propertyComments}">edit_${property.propertyName}</postCheck>
	  </#list>
      <status>0</status>
    </trade>
    
    <!-- 删除${entity.yewuName} -->
    <trade id="${queryTab}Del">
      <desc>删除${entity.yewuName}</desc>
      <ext_checkLogin>true</ext_checkLogin>
      <classMethod>
        ${entity.javaPackage}.${entity.className}:${queryTab}Del
      </classMethod>
      <logPrint>true</logPrint>
      <logPrefix></logPrefix>
      <postCheck require="true" varNameCH="id">delete_id</postCheck>
      <status>0</status>
    </trade>
    
  </trades>
</toruk>
    