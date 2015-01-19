<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" /> 
<title>操控日志</title>
<script type="text/javascript">
	var searchUrl = "<%=request.getContextPath()%>/logmng/LogUserOperaAction/search.ajax";
	$(function()
	{
		$("#btn-search").click(function()
		{
			var $form = $("#fm-opInfo");
			var condition = {};
			condition = $form.combined() || {};
			doFormSubmit($form, searchUrl, "search", 1, condition, "tab-oplist");
		});	
	})
</script>
</head>
<body>
	<div id="layout" width="100%">
		<div id="background1" class="background"></div>
		<div id="peogressBar1" class="progressBar">loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：系统管理 &gt;日志管理&gt;操控日志</h4>		
		<div class="page">
			<div class="pageHeader">
				<form id ="fm-opInfo" method="post">
					<div class="searchBar" align="legt">
						<table class="searchContent" id="tab-opInfo">
							<tr>
								<td><label>操作人账号:</label></td>
								<td><input type="text" id="USERID" name="USERID" datasource="USERID" datatype="1,is_null,30" operation="=" value=""></input></td>
								<td><label>描述:</label></td>
								<td><input type="text" id="MEMO" name="MEMO" datasource="MEMO" datatype="1,is_null,30" operation="like" value=""></input></td>
							</tr>
							<tr>
								<td><label>操作日期：</label></td>
								<td>
									<input type="text" group="startDate,endDate" id="startDate" kind="date" name="startDate" style="width:75px;" datasource="OPERATETIME" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
									<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
									<input type="text" group="startDate,endDate" id="endDate" kind="date" name="endDate" style="width:75px;margin-left:-30px;" datasource="OPERATETIME" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="page-oplist">
					<table width="100%" pageRows="200" id="tab-oplist" name="tab-oplist" ref="page-oplist" refQuery="tab-opInfo" style="display:none">
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="USERID">操控者身份</th>
								<th fieldname="USERNAME">操控者姓名</th>
								<th fieldname="OPERATEIP" >操控IP</th>
								<th fieldname="OPERATETIME" ordertype='local' class="desc">操控时间</th>
								<th fieldname="MEMO">操控信息内容</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>