<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>故障模式和配件关系维护</title>
<script type="text/javascript">
$(function(){
	$("#pjlb").jTable();
	
	var dialog = parent.$("body").data("gzmshpjgx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
function doDelete(){
	alertMsg.info("删除成功！");
}
function addPart(){
	var options = {max:false,width:800,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/gzmspjxz.jsp", "gzmspjxz", "故障模式新增配件", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageHeader">
		<form id="gswhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="gsTable">
					<tr>
						<td><label>故障模式代码：</label></td>
						<td><input type="text" id="FM_GZMSDM" name="FM_GZMSDM" datatype="1,is_null,100" value="故障模式代码1" readonly="readonly"/></td>
						<td><label>故障模式名称：</label></td>
						<td><input type="text" id="FM_GZMSMC" name="FM_GZMSMC" datatype="1,is_null,100" value="故障模式名称1" readonly="readonly"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="pj">
			<table width="100%" id="pjlb" name="pjlb" style="display: " >
				<thead>
					<tr>
						<th type="single" name="ra" style="display:" append="plus|addPart"></th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="radio" name="ra" /></td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td ><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td><input type ="radio" name="ra"/></td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td ><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td><input type ="radio" name="ra"/></td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td ><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>