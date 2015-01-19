<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务商车辆延保查询</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyMngAction/searchDealer.ajax";

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchExtend");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-extendList");
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;车辆延保查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchExtend" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchExtend">
						<tr>
							<td><label>VIN：</label></td>
							<td><input type="text" id="vin" name="vin" datasource="VIN" operation="like" datatype="1,is_null,17" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="PART_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="PART_NAME" operation="like" datatype="1,is_null,30" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_extendList">
				<table style="display:none;width:100%;" id="tab-extendList" name="tablist" ref="page_extendList" refQuery="tab-searchExtend">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>											
							<th fieldname="WARRANTY_NAME">延保名称</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="WARRANTY_MONTH">延保月份</th>
							<th fieldname="REMARKS">延保说明</th>
						</tr>
					</thead>
					<tbody>
					</tbody>	
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>