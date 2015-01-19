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
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceNotice/SupInoviceSearchMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/dealerSaleInvioceSearch.ajax";
		var $f = $("#fm-searchInvioce");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-invoice_info");
	});
	$("#btn-export").click(function(){
		var $f = $("#fm-searchInvioce");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/financeMng/invoiceNotice/SupInoviceSearchMngAction/dealerExportExcel.do");
		$("#exportFm").submit();
	})
});
function amountFormatStr(obj){
	return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
}
function openDetail(ORDER_ID){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 财务相关   &gt; 渠道商开票查询</h4>
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
						<td><label>订单号：</label></td>
					    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="T2.ORDER_NO" operation="like" /></td>
						<td><label>开票状态：</label></td>
					    <td><select type="text" class="combox" id="INVOICE_STATUS" name="INVOICE_STATUS" kind="dic" src="KPZT" datasource="T2.INVOICE_STATUS" datatype="1,is_null,6" readonly="readonly" ">
					    	<option value="-1" selected>--</option>
					    </select></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export">导&nbsp;&nbsp;出</button></div></div></li>
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
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ORG_NAME" >渠道商名称</th>
							<th fieldname="INVOICE_MONTH" >结算月份</th>
							<th fieldname="ORDER_NO" refer="showLink">订单号</th>
							<th fieldname="REAL_AMOUNT" align="right" refer="amountFormatStr">实发金额</th>
							<th fieldname="INVOICE_NO" >发票号</th>
							<th fieldname="INVOICE_DATE" >开票时间</th>
							<th fieldname="INVOICE_STATUS" >开票状态</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</div>
</body>
</html>