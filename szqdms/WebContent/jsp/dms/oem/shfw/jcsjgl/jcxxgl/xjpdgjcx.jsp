<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>星级评定轨迹查询</title>
<script type="text/javascript">
var url = "<%=request.getContextPath()%>/Xjpdgjgl/XjpdgjglAction.do";
//查询按钮响应方法
 $(function(){
	$("#search").click(function(){
		if($("#xjpdgjlb").is(":hidden")){
			$("#xjpdgjlb").show();
			$("#xjpdgjlb").jTable();
		}
	});
});
function open(){
	alertMsg.info("弹出服务商树！");
}
function doExp(){
	alertMsg.info("导出星级评定轨迹！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;星级评定轨迹查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="xjpdgjform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="xjpdgjTable">
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
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">导&nbsp;&nbsp;出</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="xjpdgj">
				<table width="100%" id="xjpdgjlb" name="xjpdgjlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务站代码</th>
							<th>服务站名称</th>
							<th>评定人</th>
							<th>评定日期</th>
							<th>评定结果(星级)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务站代码1</td>
							<td>服务站名称1</td>
							<td>李瑞</td>
							<td>2014-03-16</td>
							<td>4</td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务站代码2</td>
							<td>服务站名称2</td>
							<td>李钊</td>
							<td>2014-05-16</td>
							<td>3</td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务站代码3</td>
							<td>服务站名称3</td>
							<td>张超</td>
							<td>2014-05-11</td>
							<td>3</td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务站代码4</td>
							<td>服务站名称4</td>
							<td>张学林</td>
							<td>2014-05-16</td>
							<td>2</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>