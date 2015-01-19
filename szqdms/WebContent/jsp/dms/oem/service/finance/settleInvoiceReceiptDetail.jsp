<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算单调整</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaSaveAction="<%=request.getContextPath()%>/service/financeMng/SettleInvoiceReceiptAction";
$(function()
{
	var selectedRows = parent.$("#receiptList").getSelectedRows();
	setEditValue("dia_settleDetailfm",selectedRows[0].attr("rowdata"));
	if(<%=DicConstant.JSLX_01%>==selectedRows[0].attr("SETTLE_TYPE")){
		$("#typeH").html("服务费用信息");
		$("#orgNameL").text("服务费：");
		$("#dia_seReCostsL").text("旧件运费：");
	}else{
		$("#typeH").html("材料费用信息");
		$("#orgNameL").text("材料费：");
		$("#dia_seReCostsL").text("配件返利：");
	}
	
	//签收
	$("#dia_save").bind("click",function(){
		var $f = $("#dia_settleDetailfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $("#dia_settleDetailfm").combined(1) || {};
		var url = diaSaveAction + "/settleReceiptUpdate.ajax";
		doNormalSubmit($f,url,"dia_save",sCondition,receiptCallBack);
	});
	//关闭当前页面
	$("button.close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
function receiptCallBack(res)
{
	try
	{
		if(parent.$row){
			parent.$("#receiptList").removeResult(parent.$row);
			parent.$.pdialog.closeCurrent();
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="settleDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia_settleDetailfm" class="editForm" >
			<input type="hidden" id="dia_settleId" name="dia_settleId" datasource="SETTLE_ID"/>
			<input type="hidden" id="dia_orgCode" name="dia_orgCode" datasource="ORG_CODE"/>
			<div align="left">
			<fieldset>
			<legend align="right"><a id="typeH" onclick="onTitleClick('dia_settleDetailSeTable')">&nbsp;服务费用信息&gt;&gt;</a></legend>
				<table class="editTable" id="dia_settleDetailSeTable">
					<tr>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="dia_orgName" name="dia_orgName" datasource="ORG_NAME" readonly="readonly"/></td>
						<td><label>结算单号：</label></td>
						<td><input type="text" id="dia_settleNo" name="dia_settleNo" datasource="SETTLE_NO" readonly="readonly"/></td>
						<td><label>结算产生日期：</label></td>
						<td><input type="text" id="dia_settleDate" name="dia_settleDate" datasource="SETTLE_DATE" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>结算类型：</label></td>
						<td><input type="text" id="dia_settleType" name="dia_settleType" datasource="SETTLE_TYPE" readonly="readonly"/></td>
						<td><label id="orgNameL">服务费：</label></td>
						<td><input type="text" id="dia_seCosts" name="dia_seCosts" datasource="COSTS" value="" readonly="readonly"/></td>
						<td><label id="dia_seReCostsL">旧件运费：</label></td>
						<td><input type="text" id="dia_seReCosts" name="dia_seReCosts" datasource="RE_COSTS"  datatype="1,is_null,30" value="" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>售车奖励：</label></td>
						<td><input type="text" id="dia_seCarAward" name="dia_seCarAward" datasource="CAR_AWARD"  datatype="1,is_null,30" value="" readonly="readonly"/></td>
						<td><label>考核费用：</label></td>
						<td><input type="text" id="dia_seApCosts" name="dia_seApCosts" datasource="AP_COSTS"  datatype="1,is_null,30" value=""readonly="readonly"/></td>
						<td><label>礼金：</label></td>
						<td><input type="text" id="dia_seCashGift" name="dia_seCashGift" datasource="CASH_GIFT"  datatype="1,is_null,30" value="" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>快递单号：</label></td>
						<td><input type="text" id="dia_expressNo" name="dia_expressNo" datasource="EXPRESS_NO" datatype="1,is_null,30"  readonly="readonly"/></td>
						<td><label>其他费用：</label></td>
						<td><input type="text" id="dia_seOthers" name="dia_seOthers" datasource="OTHERS"  datatype="1,is_null,30" readonly="readonly" value="" /></td>
						<td><label>政策支持：</label></td>
						<td><input type="text" id="dia_policySup" name="dia_policySup" datasource="POLICY_SUP"  datatype="1,is_null,30" readonly="readonly" value="" /></td>
					</tr>
					<tr>
						<td><label>发票号：</label></td>
					    <td><textarea class="" rows="2" id="dia_invoiceNo" name="dia_invoiceNo" datasource="INVOICE_NO" style="width:100%" datatype="1,is_null,1000" readonly="readonly"></textarea></td>
						<td><label>索赔总金额：</label></td>
						<td><input type="text" id="dia_seSummary" name="dia_seSummary" datasource="SUMMARY"  datatype="1,is_null,30" readonly="readonly"/></td>
						<td><label>发票金额：</label></td>
						<td><input type="text" id="dia_invoiceAmount" name="dia_invoiceAmount" datasource="INVOICE_AMOUNT"  datatype="0,is_digit_0,10" /></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="2"><textarea class="" rows="2" id="dia_seRemarks" name="dia_seRemarks" datasource="REMARKS" style="width:100%" datatype="1,is_null,500"></textarea></td>
					</tr>
				</table>
		  	</fieldset>
			</div>
		</form>	
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_save">签&nbsp;&nbsp;收</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
</body>
</html>