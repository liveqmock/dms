<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>未知标识明细表（追偿费用）</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/reportForms/UnknownDetailMngAction/search.ajax";

$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchUnknowDetail");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-unknowDetailList");
	});
	
	//导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchUnknowDetail");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/UnknownDetailMngAction/download.do");
		$("#exportFm").submit();
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;未知标识明细表</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchUnknowDetail" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchUnknowDetail">
						<tr>
							<td><label>服务站名称：</label></td>
							<td><input type="text" id="oName" name="oName" datasource="ONAME" operation="like" datatype="1,is_null,100" /></td>	   		
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo" datasource="CLAIM_NO" operation="like" datatype="1,is_null,300" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expExcel" >导出Excel</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_unknowDetailList">
				<table style="display:none;width:100%;" id="tab-unknowDetailList" name="tablist" ref="page_unknowDetailList" refQuery="tab-searchUnknowDetail">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ONAME" >服务站名称</th>
							<th fieldname="CLAIM_NO">索赔单号</th>
							<th fieldname="OLD_PART_NAME">零件名称</th>
							<th fieldname="OLD_PART_CODE">零件图号</th>
							<th fieldname="WORK_COSTS">工时费</th>
							<th fieldname="SEVEH_COSTS">服务车费</th>
							<th fieldname="TRAVEL_COSTS">差旅费</th>
							<th fieldname="MEALS_COSTS">在途餐补费</th>
							<th fieldname="MATERIAL_COSTS">材料费</th>
							<th fieldname="OTHER_COSTS">其他费用</th>
							<th fieldname="TOTAL_COST">合计费用</th>
						</tr>
					</thead>
					<tbody>
					</tbody>	
				</table>
			</div>
		</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>