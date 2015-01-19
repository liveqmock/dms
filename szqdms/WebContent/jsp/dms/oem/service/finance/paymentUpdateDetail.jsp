<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="paymentDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia_paymentDetailFm" class="editForm" >
			<input type="hidden" id="dia_settleId" name="dia_settleId" datasource="SETTLE_ID">
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia_paymentDetailTable">
				<tr>
					<td><label>结算单号：</label></td>
					<td><input type="text" id="dia_settleNo" name="dia_settleNo" datasource="SETTLE_NO" readonly="readonly"/></td>
					<td><label>结算产生日期：</label></td>
					<td><input type="text" id="dia_settleDate" name="dia_settleDate" datasource="SETTLE_DATE" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>结算类型：</label></td>
					<td><input type="text" id="dia_settleType" name="dia_settleType" datasource="SETTLE_TYPE" readonly="readonly"/></td>
					<td><label>索赔总金额(元)：</label></td>
					<td><input type="text" id="dia_summary" name="dia_summary" datasource="SUMMARY" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>发票号：</label></td>
				    <td colspan="2"><textarea class="" rows="2" id="dia_invoiceNo" name="dia_invoiceNo" datasource="INVOICE_NO" style="width:100%" readonly="readonly"></textarea></td>
				</tr>
				<tr>
					<td><label>付款日期：</label></td>
					<td><input type="text" id="dia_invoiceDate" name="dia_invoiceDate" datasource="INVOICE_DATE" kind="date" datatype="0,is_date,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
				</tr>
				<tr>
					<td><label>付款金额：</label></td>
					<td><input type="text" id="dia_paymentAmount" name="dia_paymentAmount" datasource="PAYMENT_AMOUT" datatype="0,is_money,10"/></td>
				</tr>
				<tr>	
					<td><label>备注：</label></td>
				    <td colspan="2"><textarea class="" rows="2" id="dia_remarks" name="dia_remarks" datasource="REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
					
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="save">付款完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/financeMng/PaymentAction";
//弹出窗体
var dialog = $("body").data("payment");
$(function(){
	var selectedRows = $("#paymentList").getSelectedRows();
	setEditValue("dia_paymentDetailFm",selectedRows[0].attr("rowdata")); 
	
	//付款更新
	$("#save").bind("click",function(){
		var $f = $("#dia_paymentDetailFm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $f.combined(1) || {};
		var addUrl = diaSaveAction + "/settleUpdate.ajax";
		doNormalSubmit($f,addUrl,"save",sCondition,paymentCallBack);
	});
	
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function paymentCallBack(res){
	try
	{
	    var $row = $("#paymentList").getSelectedRows();//选择行
		if($row[0]){
			$("#paymentList").removeResult($row[0]);//移除选择行
			$.pdialog.closeCurrent();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>