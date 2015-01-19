<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>其他费用类型维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Qtfylxgl/QtfylxglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#qtfylb").is(":hidden"))
		{
			$("#qtfylb").show();
			$("#qtfylb").jTable();
		}
	});
});
function doDelete(){
	//alert(alertMsg.confirm("确定删除?"));
	alertMsg.info("删除成功");
}
function doAdd(){
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/qtfyxz.jsp?action=1", "qtfyxx", "其他费用类型新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/qtfyxz.jsp?action=2", "qtfyxx", "其他费用类型编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;其他费用类型维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="qtfywhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="qtfyTable">
					<tr>
						<td><label>项目代码：</label></td>
						<td><input type="text" id="XMDM" name="XMDM"datatype="1,is_null,100" value="" /></td>
						<td><label>项目名称：</label></td>
						<td><input type="text" id="XMMC" name="XMMC"datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="qtfy">
			<table width="100%" id="qtfylb" name="qtfylb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>项目代码</th>
						<th>项目名称</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDelete|doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>TC2</td>
						<td>高速拖车费</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>YZ2</td>
						<td>运杂费</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>FL2</td>
						<td>辅料费</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
					<tr>
						<td>4</td>
						<td>QTFY2</td>
						<td>其它费用</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>