<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="settleInvoiceMailDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-Detailfm" class="editForm" >
			<input type="hidden" id="settleId" datasource="SETTLE_ID" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-DetailTable">
				<tr>
					<td><label>结算单号：</label></td>
					<td><input type="text" id="dia_settleNo" name="dia_settleNo" datasource="SETTLE_NO" readonly="readonly"/></td>
					<td><label>结算产生日期：</label></td>
					<td><input type="text" id="dia_settleDate" name="dia_settleDate" datasource="SETTLE_DATE" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>结算类型：</label></td>
					<td><input type="text" id="dia_settleType" name="dia_settleType" datasource="SETTLE_TYPE" readonly="readonly"/></td>
					<td><label>索赔单数：</label></td>
					<td><input type="text" id="dia_claimCount" name="dia_claimCount" datasource="CLAIM_COUNT" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>索赔总金额(元)：</label></td>
					<td><input type="text" id="dia_summary" name="dia_summary" datasource="SUMMARY" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>快递单号：</label></td>
					<td><input type="text" id="dia_expressNo" name="dia_expressNo" datasource="EXPRESS_NO" datatype="0,is_digit_letter,30"/></td>
					<td><label>收件人：</label></td>
					<td><input type="text" id="dia_recipents" name="dia_recipents" datasource="RECIPIENTS" datatype="0,is_null,30"/></td>
				</tr>
				<tr>
					<td><label>电话：</label></td>
					<td><input type="text" id="dia_tel" name="dia_tel" datasource="TEL" datatype="0,is_null,30"/></td>
					<td><label>地址：</label></td>
					<td><input type="text" id="dia_address" name="dia_address" datasource="ADDRESS" datatype="0,is_null,100"/></td>
				</tr>
				<tr>
					<td><label>发票号：</label></td>
				    <td colspan="2"><textarea class="" rows="2" id="dia_invoiceNo" name="dia_invoiceNo" datasource="INVOICE_NO" style="width:100%" datatype="1,is_null,1000"></textarea></td>
				</tr>
				<tr>	
					<td><label>备注：</label></td>
				    <td colspan="2"><textarea class="" rows="2" id="dia_remarks" name="dia_remarks" datasource="REMARKS" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="save">邮&nbsp;&nbsp;寄</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("settleInvoiceMail");
var diaSaveAction = "<%=request.getContextPath()%>/service/financeMng/SettleInvoiceMailAction";
$(function()
{
	var selectedRows = $("#settleInvoiceMailList").getSelectedRows();
	setEditValue("dia-Detailfm",selectedRows[0].attr("rowdata"));
	//$("#dia_summary").val(amountFormatNew(selectedRows[0].attr("SUMMARY")));
	$("#save").bind("click",function(){
		var $f = $("#dia-Detailfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $f.combined(1) || {};
		var addUrl = diaSaveAction + "/settleInvoiceMail.ajax";
		doNormalSubmit($f,addUrl,"save",sCondition,mailCallBack);
		
	});
	
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function mailCallBack(res){
	try
	{
	    var $row = $("#settleInvoiceMailList").getSelectedRows();//选择行
		if($row[0]){
			$("#settleInvoiceMailList").removeResult($row[0]);//移除选择行
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