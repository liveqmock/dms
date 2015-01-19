<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>人工审核数量</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/CheckUserAction/checkCountSearch.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-checkCount");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"checkCountList");
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 人工审核数</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-checkCount">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-checkCount">
					<tr>
						<td><label>审核人名称：</label></td>
					    <td><input type="text" id="userName" name="userName" datasource="U.USER_NAME" datatype="1,is_null,30" operation="like" /></td>
						<td><label>审核人帐号：</label></td>
					    <td><input type="text" id="userAccount" name="userAccount" datasource="U.USER_ACCOUNT" datatype="1,is_null,30" operation="like" /></td>
						<td><label>审核日期：</label></td>
					    <td>
				    		<input type="text" group="checkDateStart,checkDateEnd"  id="checkDateStart" kind="date" name="checkDateStart" style="width:75px;" operation=">=" datasource="C.CHECKPASS_DATE " datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="checkDateStart,checkDateEnd"  id="checkDateEnd" kind="date" name="checkDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="C.CHECKPASS_DATE " datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="checkCount" >
			<table style="display:none;width:100%;" id="checkCountList" name="checkCountList" ref="checkCount" refQuery="tab-checkCount" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;" colwidth="10"></th>
							<th fieldname="USER_NAME"  colwidth="200">审核员</th> 
							<th fieldname="USER_ACCOUNT"  colwidth="200">审核员帐号</th> 
							<th fieldname="CLAIM_COUNT" colwidth="200" >审核数量</th>
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