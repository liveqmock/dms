<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务站维修响应时间统计表</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/reportForms/ServiceHourMngAction/search_fwz.ajax";

$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchServiceHour");
		
		var beginDate = $("#in-ckrq").val();
		var endDate = $("#in-jsrq").val();
		if (beginDate > endDate) {
			alert("开始日期必须小于结束日期！");
			return false;
		}
		
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-serviceHourList");
	});
	
	//导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchServiceHour");
		
		var beginDate = $("#in-ckrq").val();
		var endDate = $("#in-jsrq").val();
		if (beginDate > endDate) {
			alert("开始日期必须小于结束日期！");
			return false;
		}
		
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/ServiceHourMngAction/download_fwz.do");
		$("#exportFm").submit();
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;服务站维修响应时间</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchServiceHour" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchServiceHour">
						<tr>
							<td><label>办事处：</label></td>
							<td><input type="text" id="bsc" name="bsc" datasource="BSC" operation="like" datatype="1,is_null,100" /></td>
							<td><label>渠道商：</label></td>
							<td><input type="text" id="dealer" name="dealer" datasource="DEALER" operation="like" datatype="1,is_null,100" /></td>
					   		<td><label>时间段：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="YEARMONTH" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq"  group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="YEARMONTH" datatype="1,is_null,30"  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" operation = "<=" />
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
			<div id="page_serviceHourList">
				<table style="display:none;width:100%;" id="tab-serviceHourList" name="tablist" ref="page_serviceHourList" refQuery="tab-searchServiceHour">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="BSC" >办事处</th>
							<th fieldname="DEALER" >渠道商</th>
							<th fieldname="YEARMONTH">月份</th>
							<th fieldname="HOUR_OUT">站外维修响应时间（小时）</th>
							<th fieldname="MONTH_GROUTH_OUT">站外环比（%）</th>
							<th fieldname="YEAR_GROUTH_OUT">站外同比（%）</th>
							<th fieldname="HOUR_IN">站内维修响应时间（小时）</th>
							<th fieldname="MONTH_GROUTH_IN">站内环比（%）</th>
							<th fieldname="YEAR_GROUTH_IN">站内同比（%）</th>
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