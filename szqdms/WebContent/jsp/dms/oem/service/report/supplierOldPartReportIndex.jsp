<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务站旧件数据统计表</title>
<%
	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
	String year = yearFormat.format(Pub.getCurrentDate());
	String monthDate = monthFormat.format(Pub.getCurrentDate());
    Integer month = Integer.valueOf(monthDate);
	String date = year + "-" + String.valueOf(month);
%>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/supOldPartCountSearch.ajax";
		doFormSubmit($f,url,"search",1,sCondition,"claimSearchlist");
	});
	 //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#claimSearchform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	}); 
	$("#export").bind("click",function(){
		var $f = $("#claimSearchform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/supOldPartCountDownload.do");
		$("#exportFm").submit();
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：售后管理&gt;报表管理&gt;服务站旧件数据统计表</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimSearchTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="G.ORG_ID" datatype="1,is_null,10000" value=""  operation="like" /></td>
						    <td><label>供应商名称：</label></td>
							<td><input type="text" id="orgName" name="orgName" datasource="A.PROSUPPLY_NAME" datatype="1,is_null,30" value=""  operation="like" /></td>
						    <td><label>办事处名称：</label></td>
							<td><input type="text" id="bscName" name="bscName" datasource="G1.ONAME" datatype="1,is_null,30" value=""  operation="like" /></td>
						</tr>	
						<tr>
						 	<td><label>旧件产生年月：</label></td>
							<td><input type="text" id="produceDate" name="produceDate" datasource="A.PRODUCE_DATE" style="width:75px;"   onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy-MM'})" datatype="1,is_null,30" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="claimSearchlist" name="claimSearchlist" ref="preAuth" refQuery="claimSearchTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PRODUCE_DATE" >旧件产生月份</th>
							<th fieldname="PROSUPPLY_NAME" >供应商名称</th>
							<th fieldname="BSC" >办事处名称</th>
							<th fieldname="FWZ" >渠道商名称</th>
							<th fieldname="SHOULD_COUNT" >报单件数</th>
							<th fieldname="OUGHT_COUNT" >返集中点件数</th>
							<th fieldname="ALREADY_IN" >返西安库房件数</th>
							<th fieldname="ALREADY_OUT" >供应商出库件数</th>
							<th fieldname="ACTUL_COUNT" >供应商异议件数</th>
							<th fieldname="OLDPART_FINAL_DATE" >终审日期</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data"></input>
</form>
</body>
</html>