<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>供应商结算</title>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/settlement/SupplierSettlementAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/settleModSearch.ajax";
		var $f = $("#fm-searchInvioce");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-invoice_info");
	});
	$("#btn-search").trigger("click");
});
function doDetail(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var SUM_ID = $(rowobj).attr("SUM_ID");
	$.pdialog.open(webApps+"/jsp/dms/oem/part/finance/supplierSettleEdit.jsp?SUM_ID="+SUM_ID, "调整页面", "调整页面", diaAddOptions);
}
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
function doSearch(){
	var searchUrl = mngUrl+"/settleModSearch.ajax";
	var $f = $("#fm-searchInvioce");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-invoice_info");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 供应商开票管理   &gt; 供应商结算调整</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchInvioce">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-invoiceSearch">
					<tr>
						<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,3000"/>
					    	<input type="hidden" id="SUPPLIER_ID" name="SUPPLIER_ID" datasource="SUPPLIER_ID" />
					    </td>
					    <td><label>月度：</label></td>
					    <td><input type="text" id="in-kscjrq"  name="in-kscjrq" operation="="  dataSource="INVOICE_MONTH" style="width:75px;"   datatype="1,is_null,30" onclick="WdatePicker({dateFmt:'yyyy-MM'})" />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
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
							<th type="single" name="XH" unique="SUM_ID" style=""></th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="INVOICE_MONTH" >结算月份</th>
							<th fieldname="IN_COUNT" >采购入库数量汇总</th>
							<th fieldname="IN_AMOUNT" align="right" refer="amountFormatStr">采购金额汇总</th>
							<th fieldname="RETURN_COUNT" >退货数量汇总</th>
							<th fieldname="RETURN_AMOUNT" align="right" refer="amountFormatStr">退货金额汇总</th>
							<th fieldname="INVOICE_AMOUNT" align="right" refer="amountFormatStr">开票金额汇总</th>
							<th fieldname="SETTLE_AMOUNT" align="right" refer="amountFormatStr">已结算金额</th>
							<th fieldname="UNSETTLE_AMOUNT" align="right" refer="amountFormatStr">未结算金额</th>
							<th colwidth="90" type="link" title="[结算调整]"  action="doDetail" >操作</th>
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