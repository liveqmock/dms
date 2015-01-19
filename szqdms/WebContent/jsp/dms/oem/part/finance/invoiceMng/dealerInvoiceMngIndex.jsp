<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>渠道商开票管理</title>
<script type="text/javascript">
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceMng/DealerInvoiceMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/invioceSearch.ajax";
		var $f = $("#fm-searchInvioce");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-invoice_info");
	});
	$("#btn-search").trigger("click");
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var SUM_ID = $(rowobj).attr("SUM_ID");
	$.pdialog.open(webApps+"/jsp/dms/oem/part/finance/invoiceMng/dealerInvoiceAdd.jsp?SUM_ID="+SUM_ID, "明细页面", "明细页面", diaAddOptions);
}
var row;
function  noticeCallBack(res)
{
	try
	{
		var searchUrl = mngUrl+"/invioceSearch.ajax";
		var $f = $("#fm-searchInvioce");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-invoice_info");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function amountFormatStr(obj){
	return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
function doSearch(){
	var searchUrl = mngUrl+"/invioceSearch.ajax";
	var $f = $("#fm-searchInvioce");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-invoice_info");
}
function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "ORG_CODE")
	{
		$("#ORG_ID").val($row.attr("ORG_ID"));
		$("#ORG_CODE").val($row.attr("CODE"));
		$("#ORG_NAME").val($row.attr("ONAME"));
		$("#ORG_CODE").attr("code",$row.attr("CODE"));
	}
	return ret;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 开票管理   &gt; 渠道商开票</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchInvioce">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-invoiceSearch">
					<tr>
						<td><label>结算月份：</label></td>
					    <td><input type="text" id="invioceMonth" name="invioceMonth" datasource="T.INVOICE_MONTH" datatype="1,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" />
					    </td>
						<td><label>渠道商代码：</label></td>
					    <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="T.ORG_CODE" kind="dic" dicwidth="300" src="T#TM_ORG:CODE:ONAME{ORG_ID,CODE,ONAME}:1=1 AND ORG_TYPE=<%=DicConstant.ZZLB_09 %>" datatype="1,is_null,3000"/>
					    	<input type="hidden" id="ORG_ID" name="ORG_ID" datasource="T.ORG_ID" />
					    </td>
					    <td><label>渠道商名称：</label></td>
					    <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="T.ORG_NAME" datatype="1,is_null,3000"/>
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset" >重&nbsp;&nbsp;置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_invoice" >
			<table style="display:none;width:100%;" id="tab-invoice_info" name="tablist" ref="page_invoice" refQuery="fm-searchInvioce" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORG_CODE" >供应商代码</th>
							<th fieldname="ORG_NAME" >供应商名称</th>
							<th fieldname="INVOICE_MONTH" >结算月份</th>
							<th fieldname="SALE_COUNT" >销售数量汇总</th>
							<th fieldname="SALE_AMOUNT" align="right" refer="amountFormatStr">销售金额汇总</th>
							<th fieldname="RETURN_COUNT" >退库数量汇总</th>
							<th fieldname="RETURN_AMOUNT" align="right" refer="amountFormatStr">退库金额汇总</th>
							<th fieldname="INVOICE_AMOUNT" align="right" refer="amountFormatStr">开票金额汇总</th>
							<th colwidth="45" type="link" title="[开票]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
		<table style="display: none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	</div>
</div>
</body>
</html>