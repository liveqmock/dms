<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供应商索赔单查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimSupplierSearchAction/claimSearch.ajax";
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#fm-claimSearch");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"claimList");
	});
	$("#export").bind("click",function(){
		var $f = $("#fm-claimSearch");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimSupplierSearchAction/claimDownload.do");
		$("#exportFm").submit();
	});
});
//明细
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/service/claimmng/claimSupplierSearchDetail.jsp", "detail", "索赔单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 供应商索赔单查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-claimSearch">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-claimSearch">
					<tr>
						<td><label>索赔单号：</label></td>
					    <td><input type="text" id="claimNo" name="claimNo" datasource="T.CLAIM_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
					   	<td><label>索赔状态：</label></td>
							<td><select type="text" id="claimStatus" name="claimStatus" datasource="T.CLAIM_STATUS" datatype="1,is_null,100" class="combox" kind="dic" src="SPDZT" filtercode="301003|301004|301005|301006|301007|301008|301009|301015">
									<option value=-1>--</option>
								</select>
							</td>
					   	<td><label>提报日期：</label></td>
						<td>
						     <input type="text" group="applyDateStart,applyDateEnd"  id="applyDateStart" kind="date" name="applyDateStart" style="width:75px;" operation=">=" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						     <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
						     <input type="text" group="applyDateStart,applyDateEnd"  id="applyDateEnd" kind="date" name="applyDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
					<tr>
						<td><label>初审通过时间：</label></td>
					    <td >
				    		<input type="text" id="in-ckrq1"  group="in-ckrq1,in-jsrq1" style="width:75px;" name="in-ckrq" datasource="T.CHECKPASS_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq1"  group="in-ckrq1,in-jsrq1" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.CHECKPASS_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
				   		 </td>
						 <td><label>终审通过时间：</label></td>
					     <td >
				    	 	<input type="text" id="in-ckrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
				    	 	<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="claim" >
			<table style="display:none;width:100%;" id="claimList" name="claimList" ref="claim" refQuery="tab-claimSearch" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔单类型</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="APPLY_DATE" >提报时间</th>
							<th fieldname="CHECKPASS_DATE" >初审时间</th>
							<th fieldname="OLDPART_FINAL_DATE" >终审时间</th>
							<th colwidth="105" type="link" title="[明细]"  action="doUpdate" >操作</th>
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