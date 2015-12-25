 //分页栏
  jQuery(function(){
    $('.pagination_wrp').each(function(){
      var $wrap = $(this).find(".pagination_list");
  	  if(document.getElementById("currentPage").value == ""){
		    document.getElementById("currentPage").value = '1';
	    }
      var count = parseInt(document.getElementById("count").value); //获取项目数
      var pageSize = parseInt(document.getElementById("pageSize").value); //每页显示
      var currentPage = parseInt(document.getElementById("currentPage").value); //获取项目数
      var pages = parseInt(document.getElementById("pages").value); //总页数
     
      var params ="";
      <#list entity.properties as property>
      <#if (property.javaType) =='Date' >
       var  ${property.propertyName}_start=document.getElementById("${property.propertyName}_start").value;
  	   params+=('&${property.propertyName}_start='+${property.propertyName}_start);
  	   var  ${property.propertyName}_end=document.getElementById("${property.propertyName}_end").value;
  	   params+=('&${property.propertyName}_end='+${property.propertyName}_end);
      <#else>
  	   var  ${property.propertyName}=document.getElementById("${property.propertyName}").value;
  	   params+=('&${property.propertyName}='+${property.propertyName});
      </#if>
	  </#list>
	  if("" != params){   //汉字需要转移
    	  params = encodeURI(encodeURI(params));   
      }
      $wrap.pagination({
        items: count, //项目总数
        itemsOnPage: pageSize, //每页显示数量
        currentPage: currentPage, //当前页数
        pages:  pages, //总页数
        displayedPages: 3,//displayedPages,
        hrefTextPrefix: '/${projectName}/${queryTab}Query.do?'+params+'&currentPage=',
        cssStyle: 'light-theme'
      });
    });
  });

//--------------------------------------------------------------------------------------------------------------------------------------
  //操作结果提示弹出框
  $(function() {   
    $("#rsMsg_dialog").dialog({      
    	autoOpen: false,      
      height:240, 
      width:280, 
      modal:true,
      title:'操作结果',
      show: {effect: "blind", duration: 200},      
      hide: {effect: "explode",duration: 100}    
    });    
  }); 
  //--------------------------------------------------------------------------------------------------------------------------------------
  //隐藏错误提示语句
  function styleAction(id){
    document.getElementById(id).style.display = "none";
  }
  //--------------------------------------------------------------------------------------------------------------------------------------
  //计算包含中文的字符串转的长度
  function lengthCount(str){ 
    var len = 0;  
    for (var i=0; i<str.length; i++) {  
      if (str.charCodeAt(i)>127 || str.charCodeAt(i)==94) {  
        len += 3;  
      } else {  
        len ++;  
       }  
     }  
    return len;  
  } 
  //--------------------------------------------------------------------------------------------------------------------------------------
  //根据查询条件查询${entity.yewuName}列表
  function querydevelop(id){
	  document.getElementById(id).submit();
  }
  //--------------------------------------------------------------------------------------------------------------------------------------
  //新增${entity.yewuName}弹出框
  $(function() {   
    $("#${queryTab}Add_dialog").dialog({      
    	autoOpen: false,      
      height:340, 
      width:358, 
      modal:true,
      title:'新增${entity.yewuName}',
      show: {effect: "blind", duration: 200},      
      hide: {effect: "explode",duration: 500}    
    });    
    $("#add${queryTab}").click(function() {
    	/*
    	 *初始化新增页面
    	 */
    	<#list entity.properties as property>
  	    document.getElementById('add_${property.propertyName}').value = "" ;  //新增${property.propertyComments}
		</#list>
		<#list entity.properties as property>
	  	document.getElementById('add_${property.propertyName}_msg').style.display = "none" ;
		</#list>
    	$( "#${queryTab}Add_dialog" ).dialog( "open" );    
    }); 
  });
//-------------------------------
  //新增${entity.yewuName}
  function add${queryTab}(){
	  /*
	   *输入项合法性校验
	   */
	  var flag = true;//校验是否通过
	  //此处添加js校验
	  if(!flag){
		  return;
	  }
	  
    $.ajax({ 
      cache: true, 
      type: "POST", 
      url:"/${projectName}/${queryTab}Add.do", 
      data:$('#${queryTab}AddForm').serialize(),// 你的formid 
      async: false, 
      error: function(request) { 
  		document.getElementById('rsCode').value = "FAIL";
	    document.getElementById('rsMsg').innerHTML = "通讯异常，请稍后重试";
	    openRsMsgDialog();
      }, 
      success: function(data) { 
	      if(data == "OK"){
			    data = "新增${entity.yewuName}成功";
			    document.getElementById('rsCode').value = "SUCCESS";
		    }else{
			    document.getElementById('rsCode').value = "FAIL";
		    }
		    document.getElementById('rsMsg').innerHTML =  data;
		    /*
		     *判断是否登录超时
		     */
		    if(data.indexOf("html") == -1){
			    //打开结果显示弹出框
			    openRsMsgDialog();
		    }else{
			    //登录超时,跳转到登录页面
			    window.location.href="/${projectName}/index.do";
		    }
      } 
    });
  }
  //--------------------------------------------------------------------------------------------------------------------------------------
  //删除确认对话框
  $(function() {   
    $("#${queryTab}Del_dialog").dialog({      
    	autoOpen: false,      
      height:240, 
      width:280, 
      modal:true,
      title:'删除确认询问框',
      show: {effect: "blind", duration: 200},      
      hide: {effect: "explode",duration: 10}    
    });    
    $("#delete").click(function() {     
    	$( "#${queryTab}Del_dialog" ).dialog( "open" );
    }); 
  });
  //打开删除确认对话框
  function ${queryTab}DelDialog(developId){
	  //赋值要删除的developId
	  document.getElementById('delete_id').value=developId;  
	  $( "#${queryTab}Del_dialog" ).dialog( "open" );
  }
  //删除${entity.yewuName}信息
  function del${queryTab}(){
	  //关闭删除确认对话框
	  closeDialog('#${queryTab}Del_dialog');
	  $.ajax({ 
	    cache: true, 
	    type: "POST", 
	    url:"/${projectName}/${queryTab}Del.do", 
	    data:$('#${queryTab}DelForm').serialize(),
	    async: true, 
	    error: function(request) { 
    		document.getElementById('rsCode').value = "FAIL";
		    document.getElementById('rsMsg').innerHTML = "通讯异常，请稍后重试";
		    openRsMsgDialog();
	    }, 
	   success: function(data) {
		   if(data == "OK"){
			    data = "${entity.yewuName}信息已删除";
			    document.getElementById('rsCode').value = "SUCCESS";
		    }else{
			    document.getElementById('rsCode').value = "FAIL";
		    }
		    document.getElementById('rsMsg').innerHTML =  data;
		    /*
		     *判断是否登录超时
		     */
		    if(data.indexOf("html") == -1){
			    //打开结果显示弹出框
			    openRsMsgDialog();
		    }else{
			    //登录超时,跳转到登录页面
			    window.location.href="/${projectName}/index.do";
		    }
	   }
	 });
  }
  //--------------------------------------------------------------------------------------------------------------------------------------
  //修改${entity.yewuName}弹出框
  $(function() {   
    $("#${queryTab}Edit_dialog").dialog({      
    	autoOpen: false,      
      height:380, 
      width:350, 
      modal:true,
      title:'审核${entity.yewuName}',
      show: {effect: "blind", duration: 200},      
      hide: {effect: "explode",duration: 300}    
    });    
  });
  //打开修改弹出框
  function ${queryTab}EditDialog(index){
    /*
     * 初始化
     */
    <#list entity.properties as property>
    document.getElementById('edit_${property.propertyName}').value=document.getElementById('t_${property.propertyName}'+index).innerHTML;  // 修改${property.propertyComments}
	document.getElementById('edit_${property.propertyName}_msg').style.display = "none" ;
	</#list>
    //打开弹出框
    $("#${queryTab}Edit_dialog").dialog( "open" );
  }
  //修改信息
  function edit${queryTab}(){
	  $.ajax({ 
	    cache: true, 
	    type: "POST", 
	    url:"/${projectName}/${queryTab}Edit.do", 
	    data:$('#${queryTab}EditForm').serialize(),//提交参数
	    async: false, 
	    error: function(request) { 
    		document.getElementById('rsCode').value = "FAIL";
		    document.getElementById('rsMsg').innerHTML = "通讯异常，请稍后重试";
		    openRsMsgDialog();
	    }, 
	    success: function(data) { 
	      if(data == "OK"){
			    data = "修改${entity.yewuName}信息成功";
			    document.getElementById('rsCode').value = "SUCCESS";
		    }else{
			    document.getElementById('rsCode').value = "FAIL";
		    }
		    document.getElementById('rsMsg').innerHTML =  data;
		    /*
		     *判断是否登录超时或session失效
		     */
		    if(data.indexOf("html") == -1){
			    //打开结果显示弹出框
			    openRsMsgDialog();
		    }else{
			    //登录超时,跳转到登录页面
			    window.location.href="/${projectName}/index.do";
		    }
	    } 
	  });
  }
  //--------------------------------------------------------------------------------------------------------
  //打开结果提示框
  function closeRsMsgDialog(id){
	  var rsCode = document.getElementById('rsCode').value;
	  //关闭提示框
	  closeDialog(id);
	  if("SUCCESS" == rsCode){
		//操作成功，刷新页面
		querydevelop('develop_query');
	  }
  }
  //打开结果提示框
  function openRsMsgDialog(){
	  $( "#rsMsg_dialog" ).dialog( "open" );
  }
   //关闭弹出框 
  function closeDialog(id){
    $(id).dialog("close"); 
  }
//--------------------------------------------------------------------------------------------------------
  