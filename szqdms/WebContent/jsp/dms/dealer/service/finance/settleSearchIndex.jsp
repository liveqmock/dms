<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算单查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/SettleSearchAction/settleDealerSearch.ajax";
var searchDetailUrl = "<%=request.getContextPath()%>/service/financeMng/SettleSearchAction/searchSettleDetail.ajax";
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		$("#claimNo").attr("action","show");
		$("#settleDetail").hide();
		$("#settle").show();
		var $f = $("#settleSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"settleList");
	});
	$("#searchDetail").click(function(){
		$("#claimNo").attr("action","");
		$("#settle").hide();
		$("#settleDetail").show();
		var $f = $("#settleSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchDetailUrl,"searchDetail",1,sCondition,"settleDetailList");
	});
	$("#detailExport").bind("click",function(){
		var $f = $("#settleSearchform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#DtlParams").val(sCondition);
		$("#DtlExportFm").attr("action","<%=request.getContextPath()%>/service/financeMng/SettleSearchAction/dealerDtlDownload.do");
		$("#DtlExportFm").submit();
	});
});
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：结算管理&gt;结算管理&gt;结算单查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="settleSearchform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="settleSearchTable">
					<tr>
						<td><label>结算单号：</label></td>
						<td><input type="text" id="settleNo" name="settleNo"  operation="like" datasource="SETTLE_NO"  datatype="1,is_null,30" value="" /></td>
						<td><label>结算产生日期：</label></td>
						<td>
							<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" datasource="SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						    <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
						    <input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
				   		<td><label>结算类型：</label></td>
						<td><select type ="text" id="settleType" name="settleType" datasource="SETTLE_TYPE" class="combox" kind="dic" src="JSLX" datatype="1,is_null,6">
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>索赔单号：</label></td>
						<td><input type="text"  id="claimNo" name="claimNo"  operation="like"  operation="like"  datasource="CLAIM_NO"  datatype="1,is_null,30"  value="" /></td>
						<td><label>状态：</label></td>
						<td><select type ="text" id="settleStatus" name="settleStatus" datasource="SETTLE_STATUS" class="combox" kind="dic" src="JSZT" filtercode="304503|304504|304505|304506|304507" datatype="1,is_null,6">
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">汇总查询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchDetail">明细查询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="detailExport">明细导出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="settle" style="display: none">
		<div id="settleD">
			<table style="display:none;width:100%;" id="settleList" name="settleList" ref="settleD" refQuery="settleSearchTable">
				<thead>
					<tr>
						<th fieldname="SETTLE_NO" colwidth="60" ordertype="local" class="desc">结算单号</th>
						<th fieldname="SETTLE_DATE" colwidth="80">结算产生日期</th>
						<th fieldname="SETTLE_TYPE" colwidth="60">结算类型</th>
						<th fieldname="CLAIM_COUNT" colwidth="60">索赔单数</th>
						<th fieldname="COSTS" align="right" colwidth="100" refer="amountFormat">服务费/材料费</th>
						<th fieldname="RE_COSTS" align="right" colwidth="120" refer="amountFormat">旧件运费/配件返利</th>
						<th fieldname="POLICY_SUP" align="right" colwidth="60" refer="amountFormat">政策支持</th>
						<th fieldname="CASH_GIFT" align="right" colwidth="60" refer="amountFormat">礼金</th>
						<th fieldname="CAR_AWARD" align="right" colwidth="60" refer="amountFormat">售车奖励</th>
						<th fieldname="AP_COSTS" align="right" colwidth="60" refer="amountFormat">考核费用</th>
						<th fieldname="OTHERS" align="right" colwidth="60" refer="amountFormat">其他费用</th>
						<th fieldname="SUMMARY" align="right" colwidth="100" refer="amountFormat">索赔总金额(元)</th>
						<th fieldname="SETTLE_STATUS" colwidth="60">状态</th>
						<th fieldname="INVOICE_NO" colwidth="60">发票号</th>
						<th fieldname="INVOICE_DATE" colwidth="100">开票日期</th>
						<th fieldname="INVOICE_AMOUNT" align="right" colwidth="60" refer="amountFormat">发票金额</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		</div>
		<div id="settleDetail" style="display: none">
		<div id="settleDetailD">
			<table style="display:none;width:100%;" id="settleDetailList" name="settleDetailList" ref="settleDetailD" refQuery="settleSearchTable" >
				<thead>
					<tr>
						<th name="XH" style="display:none" ></th>
						<th fieldname="CLAIM_NO"colwidth="140">索赔单号</th>
						<th fieldname="SETTLE_NO"colwidth="140">结算单号</th>
						<th fieldname="SETTLE_DATE">结算产生日期</th>
						<th fieldname="SETTLE_TYPE" >结算类型</th>
						<th fieldname="WORK_COSTS" refer="amountFormat"  align="right" >工时金额(元)</th>
						<th fieldname="OUT_COSTS" refer="amountFormat"  align="right" >外出金额(元)</th>
						<th fieldname="MAINTENANCE_COSTS" refer="amountFormat"  align="right" >首保费(元)</th>
						<th fieldname="MATERIAL_COSTS" refer="amountFormat"  align="right" >材料费(元)</th>
						<th fieldname="AQJC_COSTS" refer="amountFormat"  align="right" >安全检查费(元)</th>
						<th fieldname="SQPX_COSTS" refer="amountFormat"  align="right" >售前培训费(元)</th>
						<th fieldname="SPZF" refer="amountFormat"  align="right" >索赔总费(元)</th>
						<th fieldname="SETTLE_STATUS" >状态</th>
						<th fieldname="INVOICE_NO" >发票号</th>
						<th fieldname="INVOICE_DATE" >开票日期</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		</div>
	</div>
	</div>
</div>
<form id="DtlExportFm" method="post" style="display:none">
	<input type="hidden" id="DtlParams" name="data"></input>
</form>
</body>
</html>