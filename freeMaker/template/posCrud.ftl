package ${entity.javaPackage};

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.payment.smp.login.PubUtils;
import com.payment.smp.login.User;
import com.payment.smp.pub.SendMailTool;

import cn.toruk.core.db.Dao;
import cn.toruk.core.db.SqlExecutor;
import cn.toruk.expand.pub.utils.DateUtils;
import cn.toruk.expand.pub.utils.RsaUtils;
import cn.toruk.pub.map.AreaContext;
import cn.toruk.pub.map.AreaErr;
import cn.toruk.pub.map.DataMap;
import cn.toruk.pub.map.DataTable;
/**
 * This code is generated by FreeMarker
 * @author samon_liang@foxmail.com
 * 业务：${entity.yewuName}
 */ 
public class ${entity.className} <#if entity.superclass?has_content> extends ${entity.superclass}</#if> 
{
    /********** attribute ***********/

   public void ${queryTab}Query(AreaContext ctx) throws UnsupportedEncodingException{
		try {
		    <#list entity.properties as property>
			<#if (property.javaType) =='Date' >
		      String  ${property.propertyName}_start=ctx.getString("${property.propertyName}_start"); //${property.propertyComments}开始
		      String  ${property.propertyName}_end=ctx.getString("${property.propertyName}_end"); //${property.propertyComments}结束
		      <#else>
			  String  ${property.propertyName}=ctx.getString("${property.propertyName}"); //${property.propertyComments}
		      </#if>
			</#list> 
			/*分页查询时汉字乱码处理
			if(!StringUtils.isEmpty(devName)&&devName.indexOf("%") != -1){
				devName = URLDecoder.decode(devName,"utf-8");   
			}
			*/
			/*
			 * 保存查询条件
			 * 用于页面重显
			 */
			<#list entity.properties as property>
		 	<#if (property.javaType) =='Date' >
	        ctx.getAreaOut().add("${property.propertyName}_start", ${property.propertyName}_start);  //${property.propertyComments}开始
	        ctx.getAreaOut().add("${property.propertyName}_end", ${property.propertyName}_end);  //${property.propertyComments}结束
	        <#else>
			ctx.getAreaOut().add("${property.propertyName}", ${property.propertyName});  //${property.propertyComments}
	        </#if>
			</#list> 
			//当前显示页
			String currentPageStr = ctx.getString("currentPage");
			int currentPage;
			if(StringUtils.isEmpty(currentPageStr)){
				/*条件查询，默认当前页为0*/
				currentPage = 0 ;
			}else{
				/*
				 * 需将查询条件赋值
				 * 并将currentPage的值减1，分页查询时上送的为下一页的值
				 */
				currentPage = Integer.parseInt(currentPageStr) -1;
			}
			/*
			 * 根据查询条件查询列表
			 */
			SqlExecutor executor = new SqlExecutor(true);
			executor.setMaxRecord(0);
			//查询参数			
			DataMap params = new DataMap();
			DataTable table = new DataTable();
			/*
			 * 查询每页显示数量
			 */
			table = Dao.execQuery("query_pub_queryPageSize", executor, null);
			String pageSizeStr = "";
			if(table.getRow() != null){
				pageSizeStr = (String)table.getRow().get("param_val");
			}
			if(StringUtils.isEmpty(pageSizeStr)){
				pageSizeStr = "4";
			}
			int pageSize = Integer.parseInt(pageSizeStr);		
			int startNum = currentPage*pageSize;//查询起始位置
			int endNum = (currentPage + 1)*pageSize;//查询结束位置
			/* 名称这种需要模糊 查询的  则 可以打开替换为相应 变量
		     if(!StringUtils.isEmpty(devName)){
		   	  devName = "%" + devName + "%";
		     }
		     */
		    <#list entity.properties as property>
		    <#if (property.javaType) =='Date' >
			params.add("${property.propertyName}_start", ${property.propertyName}_start); //${property.propertyComments}开始 
			params.add("${property.propertyName}_end", ${property.propertyName}_end); //${property.propertyComments}结束
			<#else>
			params.add("${property.propertyName}", ${property.propertyName}); //${property.propertyComments}
			</#if>
			</#list> 
			params.add("startNum", startNum);
			params.add("endNum", endNum);
			/*查询符合查询条件 的${entity.yewuName}总数*/
			table = Dao.execQuery("query_${entity.tableName}_listCount", executor,params);
			SortedMap<Object, Object> countMsg = table.getRow();
			//${entity.yewuName}总数
			String count = (String)countMsg.get("countnum");
			/*
			 * 如果操作总数为零，直接返回
			 */
			if("0".equals(count)){
				return;
			}
			/*查询${entity.yewuName}列表*/
			table = Dao.execQuery("query_${entity.tableName}_list", executor,params);
			List<SortedMap<Object, Object>> rsList = table.getTable();
			int flag = (Integer.parseInt(count))%pageSize;//是否为整除标识
			String pages; //总页数
			if(flag == 0){
				pages = (Integer.parseInt(count))/pageSize + "";
			}else{
				/*非整除时总页数需+1*/
				pages = (Integer.parseInt(count))/pageSize + 1 + "";
			}
			currentPage ++ ;//更改当前面
			/*
			 * 组装返回结果
			 */
			ctx.getAreaOut().add("pages", pages);//总页数
			ctx.getAreaOut().add("currentPage",currentPage );//当前页
			ctx.getAreaOut().add("pageSize", pageSize);//每页显示
			ctx.getAreaOut().add("count", count);//总条数
			ctx.getAreaOut().add("${queryTab}List", rsList);//查询结果集
		} catch (Exception e) {
			AreaErr.raise("sys_error", "系统异常");
		}
   }

    /**
	 * 
	 * 功能：新增${entity.yewuName}
	 * 
	 * @param ctx
	 */
	public void ${queryTab}Add(AreaContext ctx) {
		try {
		    <#list entity.properties as property>
			String  ${property.propertyName}=ctx.getString("add_${property.propertyName}"); //${property.propertyComments}
			</#list> 
			/*
			 * 新增${entity.yewuName}信息
			 */
			SqlExecutor executor = new SqlExecutor(false);
			executor.setMaxRecord(0);
			//参数			
			DataMap params = new DataMap();
			<#list entity.properties as property>
			params.add("${property.propertyName}", ${property.propertyName});    //${property.propertyComments}
			</#list> 
			/*
			 * 根据${entity.yewuName}某种元素查询是否已经存在
			 */
			DataTable table = new DataTable();
			table = Dao.execQuery("query_${entity.tableName}_ById", executor,params);
			/*
			 * 判断${entity.yewuName}信息是否已存在
			 * 存在则不能重复新增
			 */
			if(table.getRowCount() != 0){
				ctx.setAjaxContent("${entity.yewuName}已存在!");
				return;
			}
			/* 新增${entity.yewuName}信息*/
			int rs = Dao.execUpdate("insert_${entity.tableName}", executor,params);
			/*判断${entity.yewuName}新增是否成功*/
			if(rs != 1){
				ctx.setAjaxContent("系统异常-添加${entity.yewuName}信息失败");
				return ;
			}
			executor.commit();
			ctx.setAjaxContent("OK");
		} catch (Exception e) {
			e.printStackTrace();
			ctx.setAjaxContent("系统异常-添加${entity.yewuName}信息失败");
		}
	}
   
   

	/**
	 * 
	 * 功能：修改${entity.yewuName}
	 * 
	 * @param ctx
	 */
	public void ${queryTab}Edit(AreaContext ctx) {
		try {
		    <#list entity.properties as property>
			String  ${property.propertyName}=ctx.getString("edit_${property.propertyName}");  //${property.propertyComments}
			</#list> 
			
			/*
			 * 更新信息
			 */
			SqlExecutor executor = new SqlExecutor(true);
			executor.setMaxRecord(0);
			//参数			
			DataMap params = new DataMap();
			<#list entity.properties as property>
			params.add("${property.propertyName}", ${property.propertyName}); //${property.propertyComments}
			</#list> 
			Dao.execUpdate("update_${entity.tableName}", executor, params);
			ctx.setAjaxContent("OK");
		} catch (Exception e) {
			ctx.setAjaxContent("修改${entity.yewuName}信息失败");
		}
	}

 

     /**
	 * 
	 * 功能：删除${entity.yewuName}
	 * 
	 * @param ctx
	 * @throws Exception 
	 */
	public void ${queryTab}Del(AreaContext ctx) throws Exception {
		try {
			String delete_Id = ctx.getString("delete_id");//${entity.yewuName}id
			/*
			 * 删除${entity.yewuName}
			 */
			SqlExecutor executor = new SqlExecutor(true);
			executor.setMaxRecord(0);
			/*
			 * 设置数据库操作参数,修改为自身表的主键
			 */
			DataMap params = new DataMap();
			params.add("id", delete_Id);
			/*删除${entity.yewuName}*/
			Dao.execUpdate("delete_${entity.tableName}_ById", executor,params);
			ctx.setAjaxContent("OK");
		} catch (Exception e) {
			ctx.setAjaxContent("删除${entity.yewuName}失败-请稍后重试");
		}
	}
}