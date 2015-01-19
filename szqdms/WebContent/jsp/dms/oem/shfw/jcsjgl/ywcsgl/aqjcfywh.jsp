<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>安全检查费用维护</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#aqjcfylb").is(":hidden")){
			$("#aqjcfylb").show();
			$("#aqjcfylb").jTable();
		}
	});
});
function open(){
	alertMsg.info("弹出服务商树!");
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:400,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/aqjcfyxg.jsp", "aqjcfyxg", "安全检查费用编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;安全检查费用维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="aqjcfywhform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="aqjcfywhTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						</tr>	
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="aqjcfy">
				<table width="100%" id="aqjcfylb" name="aqjcfylb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务商代码</th>
							<th>服务商名称</th>
							<th align="right">费用(元)</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td>400.00</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码2</td>
							<td>服务商名称2</td>
							<td></td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务商代码3</td>
							<td>服务商名称3</td>
							<td>300.00</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务商代码4</td>
							<td>服务商名称4</td>
							<td></td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>