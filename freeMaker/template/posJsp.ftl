<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
  <title>华大支付平台</title>
  <link rel="shortcut icon" href="images/hd.ico" type="image/x-icon" />
  <link rel="stylesheet" type="text/css" href="css/base.css">
  <link rel="stylesheet" type="text/css" href="css/layout_head.css">
  <link rel="stylesheet" type="text/css" href="css/lib.css">
  <link rel="stylesheet" type="text/css" href="css/store_list.css">
  <link rel="stylesheet" type="text/css" href="js/paginations/simplePagination.css">
  <link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
  <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="js/common_re.js"></script>
  <script type="text/javascript" src="js/common.js"></script>
  <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="js/paginations/jquery.simplePagination.js"></script>
  <script type="text/javascript" src="js/jquery-ui.js"></script>  
  <script type="text/javascript" src="js/manager/${queryTab}.js"></script>
</head>

<body class="zh_CN">
  <!-- 头文件 -->
  <%@ include file="/WEB-INF/manager/common/top.jsp" %>
  <div id="body" class="body page_shaketv">
    <div id="js_container_box" class="container_box cell_layout side_l">
      <!-- 左侧菜单栏区 -->
      <div class="col_side">
        <div class="menu_box" id="menuBar">
          <c:forEach items="${"$"}{ctx.user.menuList}" var="menu" varStatus="status">
           <dl class = "menu"> 
            <dt class="menu_title"> <i class="icon_menu" style="background: url(images/icon_menu_function.png) no-repeat;"></i>${"$"}{menu.menuName } </dt>
              <c:forEach items="${"$"}{menu.sonMenu}" var="sonMenu" varStatus="status">
                <c:if test="${"$"}{ sonMenu.tradename== '${entity.yewuName}'}"> 
                  <dd class="menu_item selected"> <a href="${"$"}{sonMenu.function}">${"$"}{sonMenu.tradename}</a> </dd>
                </c:if>
                <c:if test="${"$"}{ sonMenu.tradename !='${entity.yewuName}'}"> 
                  <dd class="menu_item"> <a href="${"$"}{sonMenu.function}">${"$"}{sonMenu.tradename}</a> </dd>
                </c:if>
              </c:forEach>
             </dl>
          </c:forEach>
        </div>
      </div>
      <!-- 右侧内容展示区 -->
      <div class="col_main">
        <!-- 标题区 -->
        <div class="main_hd">
          <div class="page_nav">
            <a class="icon_goback" href="#">返回上一层</a>
            <a href="#">${entity.yewuName}管理</a>/${entity.yewuName}信息管理
          </div>
        </div>
        <!-- 内容展示区 -->
        <div class="main_bd">
          <!-- 查询条件区 -->
          <div class="sub_title_bar white group"> 
            <form id="develop_query" action="/${projectName}/${queryTab}Query.do" method="post">
	        <#list entity.properties as property>
	         <#if (property.javaType) =='Date' >
	          <span class="frm_input_box append">
                <input  placeholder="${property.propertyComments}开始" class="frm_input" id = "${property.propertyName}_start" name = "${property.propertyName}_start" readonly type="text" value="${"$"}{ctx.out.${property.propertyName}_start}" onClick="WdatePicker()">
              </span> 
              <span class="frm_input_box append">
                <input  placeholder="${property.propertyComments}结束" class="frm_input" id = "${property.propertyName}_end" name = "${property.propertyName}_end" readonly type="text" value="${"$"}{ctx.out.${property.propertyName}_end}" onClick="WdatePicker();">
              </span>
              <#else>
              <span class="frm_input_box append">
                <input id="${property.propertyName}" name="${property.propertyName}" placeholder="${property.propertyComments}" class="frm_input" type="text" value="<c:out value="${"$"}{ctx.out.${property.propertyName} }"/>">
              </span>
             </#if>
			</#list> 
              <div class="group r"> <a class="btn btn_primary r" onclick="querydevelop('develop_query');">查询</a> </div></br></br>
              <div class="group r"> <a class="btn btn_primary r" id="add${queryTab}" >新增</a> </div>
              </span>
            </form>
          </div>
          <!-- 查询结果展示区 -->
          <div class="table_wrp" id="js_search_result">
            <!-- 表格展示结果 -->
            <table cellspacing="0" width="100%" >
              <thead class="thead">
                <tr>
                  <#list entity.properties as property>
                  <th class="table_cell name"><div>${property.propertyComments}</div></th>
				  </#list>
                  <th class="table_cell action"><div>操作</div></th>
                </tr>
              </thead>
            <tbody class="tbody">
             <c:forEach items="${"$"}{ctx.out.${queryTab}List }" var="develop" varStatus = "op">
               <tr>
                 <#list entity.properties as property>
                 <td class="table_cell name" height="30px"  id="t_${property.propertyName}${"$"}{op.index}"><c:out value="${"$"}{develop.${property.propertyName} }"></c:out></td>
				 </#list>
                 <td class="table_cell action" align="center">
                     <a style="color:#2f9833;font-weight:bold" onclick = "${queryTab}EditDialog('${"$"}{op.index }');">修改</a>
                     <a style="color:#2f9833;font-weight:bold" onclick = "${queryTab}DelDialog('${"$"}{develop.id}');">删除</a>
                 </td>
               </tr>
             </c:forEach>
            </tbody>
          </table>
            <!-- 分页区 -->
            <div class="pagination_wrp" id="js_pager">
              <!-- 分布参数 -->
              <input type="hidden" id = "pages" name = "pages" value="${"$"}{ctx.out.pages}"/><!-- 总页数-->
              <input type="hidden" id = "count" name = "count" value="${"$"}{ctx.out.count}"/><!-- 总条数-->
              <input type="hidden" id = "pageSize" name = "pageSize" value="${"$"}{ctx.out.pageSize}"/><!-- 每页显示数量 -->
              <input type="hidden" id = "currentPage" name = "currentPage" value="${"$"}{ctx.out.currentPage}"/><!-- 当前面 -->
              <input type="hidden" id = "pageQuery_developId" name = "pageQuery_developId" value="${"$"}{ctx.out.developId}"/><!-- 开发商号 -->
              <input type="hidden" id = "pageQuery_developName" name = "pageQuery_developName" value="${"$"}{ctx.out.developName}"/><!-- 操作姓名 -->
              <input type="hidden" id = "pageQuery_status" name = "pageQuery_status" value="${"$"}{ctx.out.status}"/><!-- 开发商状态-->
              <!-- 分布显示绑定区 -->
              <div class="pagination_list"></div>
            </div> 
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 新增开发商弹出框 -->
  <div id="${queryTab}Add_dialog" >    
    <div class="ipt_wrap">       
      <form name="${queryTab}AddForm" id= "${queryTab}AddForm" method="post" action="/${projectName}/${queryTab}Add.do">
        <#list entity.properties as property>
         <span class="frm_input_box append">
          <input id="add_${property.propertyName}" name="add_${property.propertyName}" placeholder="${property.propertyComments}" class="frm_input" type="text"  onKeyUp="styleAction('${property.propertyName}_msg');">
         </span>
         <span style = "color:red;display:none"  id = "add_${property.propertyName}_msg">${property.propertyComments}不合法</span>
		</#list>
        </form>
      </div>
      <div class="tool_bar border with_form tc">      
        <span class="btn btn_input btn_primary"><button id="js_submit" type="button" onclick="add${queryTab}();">提交</button></span>     
        <span class="btn btn_input btn_default"><button id="js_submit" type="button" onclick="closeDialog('#${queryTab}Add_dialog');">取消</button></span>     
      </div>
    </div>
    
    
  <!-- 修改${entity.yewuName}弹出框 -->
  <div id="${queryTab}Edit_dialog" >
    <div class="ipt_wrap" >     
      <form name="${queryTab}EditForm" id= "${queryTab}EditForm" method="post" action="/${projectName}/${queryTab}Edit.do">
        <#list entity.properties as property>
         <span class="frm_input_box append">
          <input id="edit_${property.propertyName}" name="edit_${property.propertyName}" placeholder="${property.propertyComments}" class="frm_input" type="text"  onKeyUp="styleAction('edit_${property.propertyName}_msg');">
         </span>
         <span style = "color:red;display:none"  id = "edit_${property.propertyName}_msg">${property.propertyComments}不合法</span>
		</#list>
      </form>
   </div>
   <div class="tool_bar border with_form tc">            
     <span class="btn btn_input btn_primary"><button id="js_submit" type="button" onclick="edit${queryTab}();">提交</button></span>     
     <span class="btn btn_input btn_default"><button id="js_submit" type="button" onclick="closeDialog('#${queryTab}Edit_dialog');">取消</button></span>     
   </div>
  </div>   
    
    
    <!-- 删除${entity.yewuName}弹出框 -->
    <div id="${queryTab}Del_dialog" >  
      <div style="text-align:center">
        <form id="${queryTab}DelForm">
          <input type="hidden" id = "delete_id" name = "delete_id"/>
        </form>
        </br>
        <span >您确定要删除该${entity.yewuName}信息?</span>
        </br>
      </div>
      <div class="tool_bar border with_form tc">            
        <span class="btn btn_input btn_primary"><button id="js_submit" type="button" onclick="del${queryTab}();">确认</button></span>     
        <span class="btn btn_input btn_default"><button id="js_submit" type="button" onclick="closeDialog('#${queryTab}Del_dialog');">取消</button></span>     
      </div>
    </div>
   <%@ include file="/WEB-INF/manager/common/tail.jsp" %>
</body>
</html>