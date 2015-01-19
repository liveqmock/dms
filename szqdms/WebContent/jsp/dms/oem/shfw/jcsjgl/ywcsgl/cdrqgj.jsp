<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>超单修改轨迹查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#cdrqgjlb").is(":hidden")){
			$("#cdrqgjlb").show();
			$("#cdrqgjlb").jTable();
		}
	});
});
function open(){
	alertMsg.info("弹出服务商树");
}
function doDetail(){
	var options = {max:false,width:650,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/spdgj.jsp", "spdxx", "索赔单修改轨迹", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;超单修改轨迹查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="cdrqgjform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="cdrqgjTable">
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
			<div id="cdrqgj">
				<table width="100%" id="cdrqgjlb" name="cdrqgjlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务商代码</th>
							<th>服务商名称</th>
							<th>超单天数</th> 
							<th>修改日期</th>
							<th>修改人</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#" class="op" onclick="doDetail()">服务商代码1</a></td>
							<td>服务商名称1</td>
							<td>8</td>
							<td>2014-5-23</td>
							<td>霍华龙</td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="#" class="op" onclick="doDetail()">服务商代码1</a></td>
							<td>服务商名称1</td>
							<td>7</td>
							<td>2014-5-24</td>
							<td>霍华龙</td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="#" class="op" onclick="doDetail()">服务商代码2</a></td>
							<td>服务商名称2</td>
							<td>6</td>
							<td>2014-5-20</td>
							<td>霍华龙</td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="#" class="op" onclick="doDetail()">服务商代码3</a></td>
							<td>服务商名称3</td>
							<td>7</td>
							<td>2014-5-25</td>
							<td>霍华龙</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>