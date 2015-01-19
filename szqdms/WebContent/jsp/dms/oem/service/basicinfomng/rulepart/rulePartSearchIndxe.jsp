<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:配件三包规则查询
	 Version:1.0
     Collator：baixiaoliangn@sxqc.com
     Date：2014-07
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<jsp:include page="/head.jsp" />
<title>配件三包规则查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/search.ajax";
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础信息查询&gt;基础信息查询&gt;配件三包查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">	
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="partCode"  name="partCode" datasource="T.PART_CODE"  datatype="1,is_null,30" operation="=" value=""  /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="partName"  name="partName" datasource="T.PART_CODE"  datatype="1,is_null,30" operation="=" value=""  /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none"></th>
						<th fieldname="RULE_NAME" >规则名称</th>
						<th fieldname="PART_CODE" >配件代码</th>
						<th fieldname="PART_NAME" >配件名称</th>
						<th fieldname="MONTHS" >三包月份</th>
						<th fieldname="MILEAGE" >三包里程</th>
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