<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>审核员维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/CheckUserAction/checkUserSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/claimmng/CheckUserAction";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-checkUser");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"checkUserList");
	});
	$("#add").bind("click",function(){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/service/claimmng/checkUserAdd.jsp", "checkUser", "审核员信息维护", options);
	});
});

function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:false,width:600,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/service/claimmng/checkUserUpdate.jsp", "checkUserUpdate", "审核员信息维护", options);
}

//删除审核人
var $row;
function doDelete(rowobj){
	$row = $(rowobj);
	var deleteUrl1 = deleteUrl + "/deleteCheckUser.ajax?cuId="+$(rowobj).attr("CU_ID");
	sendPost(deleteUrl1,"delete","",deleteCallBack,"true");
}
function deleteCallBack(res){
	try
	{
		if($row) 
			$("#checkUserList").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 审核员维护</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-checkUser">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-checkUser">
					<tr>
						<td><label>审核人：</label></td>
					    <td><input type="text" id="userName" name="userName" datasource="C.USER_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="add" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="checkUser" >
			<table style="display:none;width:100%;" id="checkUserList" name="checkUserList" ref="checkUser" refQuery="tab-checkUser" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;" colwidth="10"></th>
							<th fieldname="USER_NAME"  colwidth="200">审核员</th>
							<th fieldname="IF_DISTRIB" colwidth="200">是否分配索赔单</th>
							<th colwidth="125" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>