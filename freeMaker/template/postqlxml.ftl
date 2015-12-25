<?xml version="1.0" encoding="UTF-8" ?>
<toruk xmlns="http://www.toruk.cn/schema/toruk" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.toruk.cn/schema/toruk http://www.toruk.cn/schema/toruk ">
<sqlMaps>
	<select id="query_${entity.tableName}_listCount">
		<![CDATA[
         select count(*) as countnum 
        from ${entity.tableName} 
        	]]>
		<dynamic prepend="where">
		<#list entity.properties as property>
		  <#if (property.javaType) =='Date' >
	        <isNotEmpty property="#${property.propertyName}_start#" prepend="and">
			<![CDATA[
				${property.propertyName} >= to_date(#${property.propertyName}_start#, 'yyyy-mm-dd') 
			]]>
			</isNotEmpty>
			<isNotEmpty property="#${property.propertyName}_end#" prepend="and">
			<![CDATA[
				${property.propertyName} < (to_date(#${property.propertyName}_end#, 'yyyy-mm-dd') +1)
			]]>
			</isNotEmpty>
	      <#else>
		    <isNotEmpty property="#${property.propertyName}#" prepend="and">${property.propertyName} = #${property.propertyName}#</isNotEmpty>
	      </#if>
		</#list>
		</dynamic>
	</select>
	
	<select id="query_${entity.tableName}_list">
	select t.* From (
		select 
		<#list entity.properties as property>
	        ${property.propertyName}
	        ,
		</#list>
			rownum num
		from  ${entity.tableName} 
		<dynamic prepend="where">
		<#list entity.properties as property>
		  <#if (property.javaType) =='Date' >
	        <isNotEmpty property="#${property.propertyName}_start#" prepend="and">
			<![CDATA[
				${property.propertyName} >= to_date(#${property.propertyName}_start#, 'yyyy-mm-dd') 
			]]>
			</isNotEmpty>
			<isNotEmpty property="#${property.propertyName}_end#" prepend="and">
			<![CDATA[
				${property.propertyName} < (to_date(#${property.propertyName}_end#, 'yyyy-mm-dd') +1)
			]]>
			</isNotEmpty>
	      <#else>
		    <isNotEmpty property="#${property.propertyName}#" prepend="and">${property.propertyName} = #${property.propertyName}#</isNotEmpty>
	      </#if>
		</#list>
		</dynamic>
		<dynamic prepend="">
			<isNotEmpty property="#tql_order_by#" prepend="">
				order by <orderBy property="#tql_order_by#" />
			</isNotEmpty>
		</dynamic>
		<![CDATA[
             ) t where t.num > #startNum# and t.num <= #endNum#
        ]]>
	</select>
	
	<select id="query_${entity.tableName}_ById">
		<![CDATA[
		select 
        <#list entity.properties as property>
	        ${property.propertyName}
	        ,
		</#list>
			to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') dbtime
		from  ${entity.tableName} 
		]]>
		where
			id = #id#	 
	</select>
	
	<update id="update_${entity.tableName}">
		update ${entity.tableName}
		<dynamic prepend="set">
		<#list entity.properties as property>
	      <#if (property.javaType) =='Date' >
		     <isExist property="#${property.propertyName}#" prepend=",">
				<isNotEquals property="#${property.propertyName}#" equals="$dbTime" prepend="">
					<isEmpty property="#${property.propertyName}#" prepend="">${property.propertyName} = null</isEmpty>
					<isNotEmpty property="#${property.propertyName}#" prepend="">${property.propertyName} = to_date(#${property.propertyName}#,'yyyy-mm-dd hh24:mi:ss')</isNotEmpty>
				</isNotEquals>
			 </isExist>
	      <#else>
		     <isExist property="#${property.propertyName}#" prepend=",">${property.propertyName} = #${property.propertyName}#</isExist>
	      </#if>
		</#list>
		</dynamic>
		where
			id = #id#	 
	</update>
	
	<update id="delete_${entity.tableName}_ById">
		delete from ${entity.tableName} 	
		where
		id=#id#
	</update>
	
	<update id="insert_${entity.tableName}">
		<dynamic prepend="insert into ${entity.tableName} (">
		    <#list entity.properties as property>
			<isExist property="#${property.propertyName}#" prepend=",">${property.propertyName}</isExist> 
			</#list> 
		</dynamic>
		<dynamic prepend=") values (">
	    <#list entity.properties as property>
		 <#if (property.javaType) =='Date' >
		   <isExist property="#${property.propertyName}#" prepend=",">
				<isNotEquals property="#${property.propertyName}#" equals="$dbTime" prepend="">
					<isEmpty property="#${property.propertyName}#" prepend="">null</isEmpty>
					<isNotEmpty property="#${property.propertyName}#" prepend="">to_date(#${property.propertyName}#,'yyyy-mm-dd hh24:mi:ss')</isNotEmpty>
				</isNotEquals>
		   </isExist>
		 <#else>
		   <isExist property="#${property.propertyName}#" prepend=",">#${property.propertyName}#</isExist>
		 </#if>
		</#list> 
		</dynamic> 
		)
	</update>

</sqlMaps>

</toruk>