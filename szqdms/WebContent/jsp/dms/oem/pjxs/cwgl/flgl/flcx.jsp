<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>返利计算</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		$("#xjzhgllb").show();
		$("#xjzhgllb").jTable();
		
	});
});
function open()
{
	alertMsg.info("弹出服务商树！");
}
function doDownload(){
	alertMsg.info("下载EXCEL文件");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;返利管理&gt;返利计算</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="xjzhglform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="xjzhglTable">
					<tr>
						<td><label>返利周期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="TBRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
				   		 </td>
				   		<td><label>服务商代码：</label></td>
						<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>	
					</tr>	
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" onclick="doDownload()">下&nbsp;&nbsp;载</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="xjzhgl">
			<table width="100%" id="xjzhgllb" name="xjzhgllb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH"     colwidth="20">序号</th>
						<th colwidth="140" >计算周期</th>
						<th colwidth="60" >服务商代码</th>
						<th colwidth="60" >服务商名称</th>
						<th colwidth="60" align="right">返利金额</th>
						<th colwidth="60" >兑现状态</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
					<td class="rownums"><div>1</div></td>
					<td><div>2014-06-01至2014-06-30</div></td>
					<td><div>配送中心1</div></td>
					<td><div>配送中心1</div></td>
					<td><div>1,000.00</div></td>
					<td><div>已兑现</div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>2</div></td>
					<td><div>2014-06-01至2014-06-30</div></td>
					<td><div>配送中心2</div></td>
					<td><div>配送中心2</div></td>
					<td><div>2,000.00</div></td>
					<td><div>未兑现</div></td>
				</tr>
				<tr>
					<td class="rownums"><div>3</div></td>
					<td><div>2014-06-01至2014-06-30</div></td>
					<td><div>配送中心3</div></td>
					<td><div>配送中心3</div></td>
					<td><div>3,000.00</div></td>
					<td><div>未兑现</div></td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>