<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>初期故障率统计表</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/reportForms/PrimaryFaultMngAction/search.ajax";

$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchPrimaryFault");
		
		var beginDate = $("#in-ckrq").val();
		var endDate = $("#in-jsrq").val();
		if (beginDate > endDate) {
			alert("开始日期必须小于结束日期！");
			return false;
		}
		
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-primaryFaultList");
	});
	
	//导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchPrimaryFault");
		
		var beginDate = $("#in-ckrq").val();
		var endDate = $("#in-jsrq").val();
		if (!beginDate) {
			alert("开始日期不能为空！");
			return false;
		}
		if (!endDate) {
			alert("结束日期不能为空！");
			return false;
		}
		if (beginDate > endDate) {
			alert("开始日期必须小于结束日期！");
			return false;
		}
		
		var sCondition = {};
    	sCondition = $f.combined() || {};   	
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/PrimaryFaultMngAction/download.do");
		$("#exportFm").submit();
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;初期故障率统计报表</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchPrimaryFault" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchPrimaryFault">
						<tr>
					   		<td><label>统计时间段：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="BEGIN_DATE" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">&nbsp;至</span>
								<input type="text" id="in-jsrq"  group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="END_DATE" datatype="0,is_null,30"  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" operation = "<=" />
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
			<div id="page_primaryFaultList">
				<table style="display:none;width:100%;" id="tab-primaryFaultList" name="tablist" ref="page_primaryFaultList" refQuery="tab-searchPrimaryFault">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="YEARMONTH" >购车月份</th>
							<th fieldname="CX" >车型</th>
							<th fieldname="FAULTNUMBER">直接故障项次</th>
							<th fieldname="TOTAL_SALENUMBER">销售车辆数</th>
							<th fieldname="GZL">初期故障率</th>
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