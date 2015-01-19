<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆维修频次查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/vehicleRepairCountSearch.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/vehicleRepairCounDownload.do");
		$("#exportFm").submit();
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：售后管理&gt;报表管理&gt;单车维修频次查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimSearchTable">
					<tr>
						<td><label>VIN：</label></td>
							<td><input type="text" id="dia-vin" name="dia-vin" datasource="T.VIN" datatype="1,is_null,30" value=""  operation="like" /></td>
						 	<td><label>终审通过时间：</label></td>
					    <td>
				    	 	<input type="text" id="in-ckrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
				    	 	<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
				   	    </td>
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
							<th fieldname="VIN" >VIN</th>
							<th fieldname="BDS" >报单数</th>
							<th fieldname="WXS" >维修数</th>
							<th fieldname="JJS" >旧件数</th>
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