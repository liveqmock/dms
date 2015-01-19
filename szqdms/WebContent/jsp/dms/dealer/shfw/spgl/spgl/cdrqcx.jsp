<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>超单日期查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	
		$("#cdrqlb").show();
		$("#cdrqlb").jTable();
		$("#ycsjslb").show();
		$("#ycsjslb").jTable();
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：索赔管理&gt;索赔管理&gt;超单日期查询</h4>
		<div class="page">
		<div class="pageHeader">
		</div>
		<div class="pageContent">
			<div id="cdrq">
				<table width="100%" id="cdrqlb" name="cdrqlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务商代码</th>
							<th>服务商名称</th>
							<th>超单天数</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td>7</td>
						</tr>
					</tbody>
				</table>
			</div>
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('ycsjspd')">&nbsp;已超时间索赔单&gt;&gt;</a></legend>
			<div id="ycsjspd">
				<table width="100%" id="ycsjslb" name="ycsjslb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>索赔单号</th>
							<th>类型</th>
							<th>故障日期</th>
							<th>提报日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>单号1</td>
							<td>正常保修</td>
							<td>2014-04-11</td>
							<td>2014-04-19</td>
						</tr>
					</tbody>
				</table>
			</div>
			</fieldset>
	 </div>
	</div>
</div>
</body>
</html>