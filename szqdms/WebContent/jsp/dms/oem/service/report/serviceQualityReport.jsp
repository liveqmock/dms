<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>售后质量信息</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/reportForms/ServiceQualityMngAction/search.ajax";

$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchServiceQuality");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-serviceQualityList");
	});
	
	//导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchServiceQuality");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/ServiceQualityMngAction/download.do");
		$("#exportFm").submit();
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;售后质量信息</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchServiceQuality" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchServiceQuality">
						<tr>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="SUPPLIER_NAME" operation="like" datatype="1,is_null,300" /></td>
					   		<td><label>终审日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq"  group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="OLDPART_FINAL_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>		   		
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
			<div id="page_serviceQualityList">
				<table style="display:none;width:100%;" id="tab-serviceQualityList" name="tablist" ref="page_serviceQualityList" refQuery="tab-searchServiceQuality">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="ONAME" >服务站名称</th>
							<th fieldname="CLAIM_NO">索赔单号</th>
							<th fieldname="VIN">底盘号</th>
							<th fieldname="MODELS_CODE">车辆型号</th>
							<th fieldname="BUY_DATE">购车日期</th>
							<th fieldname="FAULT_DATE">故障日期</th>
							<th fieldname="MILEAGE">行驶里程</th>
							<th fieldname="OLD_PART_NAME">零件名称</th>
							<th fieldname="OLD_PART_CODE">零件图号</th>
							<th fieldname="MEASURES">处理措施</th>
							<th fieldname="PRICE">报单追偿总额</th>
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