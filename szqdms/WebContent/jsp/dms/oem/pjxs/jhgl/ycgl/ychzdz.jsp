<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预测汇总调整</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#ycyflb").is(":hidden")){
			$("#ycyflb").show();
			$("#ycyflb").jTable();
		}
		$("#ophzBtn").show();
	});
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:750,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/jhgl/ycgl/ycmx.jsp", "ycmx", "预测明细", options);
}
function doSum(){
	var options = {max:false,width:700,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/jhgl/ycgl/ychzmx.jsp", "ychzmx", "预测汇总调整", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：计划管理&gt;预测管理&gt;预测汇总调整</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="ychzform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="ychzTable">
					<tr>
						<td><label>预测月份：</label></td>
						<td><input type="text" id="YCYF" name="YCYF"datatype="0,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="ycyf">
			<table width="100%" id="ycyflb" name="ycyflb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>配送中心代码</th>
						<th>配送中心名称</th>
						<th>预测月份</th>
						<th colwidth="85" type="link" title="[明细]"  action="doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>配送中心代码1</td>
						<td>配送中心名称1</td>
						<td>2014-06</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[明细]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>配送中心代码2</td>
						<td>配送中心名称2</td>
						<td>2014-06</td>
						<td><a href="#" onclick="doUpdate()" class="op">[明细]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="formBar" id="ophzBtn" style="display:none" >
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSum()" id="sum">汇&nbsp;&nbsp;总</button></div></div></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>