<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>首保费用维护</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Sbfygl/SbfyglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#sbfylb").is(":hidden")){
			$("#sbfylb").show();
			$("#sbfylb").jTable();
		}
	});
});
function doAdd(){
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbfyxz.jsp?action=1", "sbfyxx", "首保费用新增", options);
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/sbfyxz.jsp?action=2", "sbfyxx", "首保费用编辑", options);
}
function doDelete(rowobj){
	//alert(alertMsg.confirm("确定删除?"));
	row = $(rowobj);
	alertMsg.info("删除成功");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if(row)
			row.remove();
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;首保费用维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="sbfywhform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="sbfyTable">
					<tr>
						<td><label>驱动形式：</label></td>
						<td><input type="text" id="QDXS" name="QDXS" datatype="1,is_null,100"/></td>
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
		<div id="sbfy">
			<table width="100%" id="sbfylb" name="sbfylb" style="display: none" >
				<thead>
					<tr>
						<th  name="XH" style="display:">序号</th>
						<th>驱动形式</th>
						<th align="right">费用(元)</th>
						<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>1</td>
						<td>4*2</td>
						<td>200.00</td>
						<td ><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
					<tr>
						<td>2</td>
						<td>6*2</td>
						<td>150</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
					<tr>
						<td>3</td>
						<td>8*2</td>
						<td>300</td>
						<td><a href="#" onclick="doUpdate()" class="op">[编辑]</a><a href="#" onclick="doDelete()" class="op">[删除]</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>