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
		var searchUrl = mngUrl+"/invioceSearch.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/financeMng/invoiceNotice/SupInoviceSearchMngAction/exportExcel.do");
		$("#exportFm").submit();
	})
});
function amountFormatStr(obj){
	return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "SUPPLIER_CODE")
	{
		$("#"+id).attr("SUPPLIER_ID",$row.attr("SUPPLIER_ID"));
		$("#SUPPLIER_NAME").val($("#"+id).val());
		$("#SUPPLIER_ID").val($("#"+id).attr("SUPPLIER_ID"));
		
	}
	return ret;
}
function showLink(obj)
{
	var $row=$(obj).parent();
	var type = $row.attr("TYPE");
	if(type=="1"){
		return "<a href='#' onclick=openDetail1("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
	}else{
		return "<a href='#' onclick=openDetail2("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
	}
    
}
function openDetail1(SPLIT_ID){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/purchaseOrderInfoDetail.jsp?SPLIT_ID="+SPLIT_ID, "pchOrderDetail", "采购拆分单明细", options,true);
}
function openDetail2(RETURN_ID){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/pchReturnOrderForDetails.jsp?id=" +RETURN_ID, "pchOrderDetail", "采购退货单明细", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 财务相关   &gt; 供应商开票查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchInvioce">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-invoiceSearch">
					<tr>
						<td><label>结算月份：</label></td>
					    <td><input type="text" id="invioceMonth" name="invioceMonth" datasource="A.INVOICE_MONTH" datatype="1,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" />
					    </td>
						<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="A.SUPPLIER_CODE" kind="dic" dicwidth="300" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,3000"/>
					    	<input type="hidden" id="SUPPLIER_ID" name="SUPPLIER_ID" datasource="A.SUPPLIER_ID" />
					    </td>
					</tr>
					<tr>
						<td><label>订单号：</label></td>
					    <td><input type="text" id="SPLIT_NO" name="SPLIT_NO" datatype="1,is_digit_letter,30" dataSource="A.ORDER_NO" operation="like" /></td>
						<td><label>开票通知人：</label></td>
						<td><input type="text" id="APPLY_USER" name="APPLY_USER" datatype="1,is_null,30" dataSource="A.APPLY_USER" operation="like" /></td>
					</tr>
					<tr>
						<td><label>开票状态：</label></td>
					    <td><select type="text" class="combox" id="INVOICE_STATUS" name="INVOICE_STATUS" kind="dic" src="GYSKPZT" datasource="A.INVOICE_STATUS" datatype="1,is_null,6" readonly="readonly" filtercode="<%=DicConstant.GYSKPZT_01%>|<%=DicConstant.GYSKPZT_02%>|<%=DicConstant.GYSKPZT_03%>|<%=DicConstant.GYSKPZT_04%>">
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
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="INVOICE_MONTH" >结算月份</th>
							<th fieldname="ORDER_NO" refer="showLink">订单号</th>
							<th fieldname="PURCHASE_AMOUNT" align="right" refer="amountFormatStr">采购金额</th>
							<th fieldname="INVOICE_NO" >发票号</th>
							<th fieldname="APPLY_USER" >开票通知人</th>
							<th fieldname="INVOICE_DATE" >开票时间</th>
							<th fieldname="INVOICE_STATUS" >开票状态</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
	</div>
</div>
</body>
</html>