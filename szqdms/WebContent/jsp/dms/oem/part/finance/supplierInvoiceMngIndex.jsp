<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>通知开票</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceNotice/InvoiceAddMngAction";
$(function()
{
		var searchUrl = mngUrl+"/invioceSearch.ajax";
		var $f = $("#fm-searchInvioce");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-invoice_info");
});
function doInvSearch(){
	var searchUrl = mngUrl+"/invioceSearch.ajax";
	var $f = $("#fm-searchInvioce");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"",1,sCondition,"tab-invoice_info");
}
function doDetail(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var SUM_ID = $(rowobj).attr("SUM_ID");
	$.pdialog.open(webApps+"/jsp/dms/oem/part/finance/supplierInvoiceEdit.jsp?SUM_ID="+SUM_ID, "orderSelWin", "供应商开票", diaAddOptions);
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 供应商开票管理   &gt; 供应商开票</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchInvoice">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-invioceSearch">
				</table>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_invoice" >
			<table style="display:none;width:100%;" id="tab-invoice_info" name="tablist" ref="page_invoice" refQuery="fm-searchInvoice" >
					<thead>
						<tr>
							<th type="single" name="XH"  style="display:none;"></th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="INVOICE_MONTH" >结算月份</th>
							<th fieldname="IN_COUNT" >采购入库数量汇总</th>
							<th fieldname="IN_AMOUNT" align="right" refer="amountFormatStr">采购金额汇总</th>
							<th fieldname="RETURN_COUNT" >退货数量汇总</th>
							<th fieldname="RETURN_AMOUNT" align="right" refer="amountFormatStr">退货金额汇总</th>
							<th fieldname="INVOICE_AMOUNT" align="right" refer="amountFormatStr">开票金额汇总</th>
							<th colwidth="45" type="link" title="[开票]"  action="doDetail" >操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>