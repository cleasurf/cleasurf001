
<!-- 进入${entity.yewuName}查询页面 -->
<tradeId id="/${projectName}/${queryTab}Query.do">${queryTab}Query</tradeId>
<!-- 新增${entity.yewuName} -->
<tradeId id="/${projectName}/${queryTab}Add.do">${queryTab}Add</tradeId>
<!-- 修改${entity.yewuName} -->
<tradeId id="/${projectName}/${queryTab}Edit.do">${queryTab}Edit</tradeId>
<!-- 删除${entity.yewuName} -->
<tradeId id="/${projectName}/${queryTab}Del.do">${queryTab}Del</tradeId>

<!--数据库加 菜单-->
insert into T_AUTH (TRADEID, TRADENAME, FUNCTION, FUNCTION_NAME, IS_LEAF, PARENTID, ORDERID)
values ('${queryTab}Query', '${entity.yewuName}', '${queryTab}Query.do', '${entity.yewuName}', '0', 'developManager', '2');


<!--菜单分配权限-->
insert into T_ROLE_AUTH values(SEQ_ROLE_AUTH_ID.NEXTVAL,'1','${queryTab}Query');
