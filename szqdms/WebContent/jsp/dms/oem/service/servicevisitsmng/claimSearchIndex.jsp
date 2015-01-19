<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单查询、下载</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/servicevisitsMng/ClaimSearchAction/claimSearch.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"claimSearchlist");
	});
	//下载
	$("#exp").bind("click",function(){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/servicevisitsMng/ClaimSearchAction/download.do");
		$("#exportFm").submit();
	});
});

function doUpdate(rowobj){
	//$("td input:first",$(rowobj)).attr("checked",true);
	//var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	//$.pdialog.open(webApps+"/jsp/dms/dealer/service/guaranteesHurryPart/guaranteesHurryPartEdit.jsp?action=2", "guaranteesHurryPart", "三包急件编辑", options,true); 
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务回访管理&gt;服务回访管理&gt;索赔信息查询、下载</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimSearchTable">
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo" datasource="T.CLAIM_NO" datatype="1,is_null,100" value=""  operation="like" /></td>
							<td><label>派工单号：</label></td>
							<td><input type="text" id="workNo" name="workNo" datasource="O.WORK_NO" datatype="1,is_null,100" value=""  operation="like" /></td>
							<td><label>提报日期：</label></td>
							<td>
							     <input type="text" group="applyDateStart,applyDateEnd"  id="applyDateStart" kind="date" name="applyDateStart" style="width:75px;" operation=">=" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
							     <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							     <input type="text" group="applyDateStart,applyDateEnd"  id="applyDateEnd" kind="date" name="applyDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="exp">下&nbsp;&nbsp;载</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" id="claimSearchlist" name="claimSearchlist" ref="preAuth" refQuery="claimSearchTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="WORK_NO" >派工单号</th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="CLAIM_TYPE" >索赔单类型</th>
							<th fieldname="APPLY_DATE" >提报时间</th>
							<!-- <th colwidth="85" type="link" title="查询明细"  action="doReport" >操作</th> -->
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