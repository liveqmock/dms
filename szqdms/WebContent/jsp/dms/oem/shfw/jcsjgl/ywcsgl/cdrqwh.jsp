<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>超单日期设置</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#cdrqlb").is(":hidden")){
			$("#cdrqlb").show();
			$("#cdrqlb").jTable();
		}
	});
});
function doDelete(){
	alertMsg.info("删除成功！");
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqxz.jsp?action=2", "cdrqxx", "超单日期编辑", options);
}
function doAdd(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqxz.jsp?action=1", "cdrqxx", "超单日期新增", options);
}
function open(){
	alertMsg.info("弹出服务商树");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;超单日期设置</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="cdrqform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="cdrqTable">
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
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
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
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td>7</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码2</td>
							<td>服务商名称2</td>
							<td>7</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务商代码3</td>
							<td>服务商名称3</td>
							<td>7</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务商代码4</td>
							<td>服务商名称4</td>
							<td>7</td>
							<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>