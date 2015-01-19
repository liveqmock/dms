<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="dealerDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia_dealerDetailfm" class="editForm" >
			<input type="hidden" id="dia_dealerId" name="dia_dealerId" datasource="DEALER_ID"/>
			<div align="left">
			<fieldset>
				<table class="editTable" id="dia_dealerDetailTable">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="dia_dealerCode" name="dia_dealerCode" datasource="DEALER_CODE"  readonly="readonly"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="dia_dealerName" name="dia_dealerName" datasource="DEALER_SHORTNAME" style="width:200px" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>结算单类型：</label></td>
						<td>
							<select type="text" id="dia_settleType" name="dia_settleType" class="combox" kind="dic" src="JSLX" datasource="SETTLE_TYPE" datatype="1,is_null,6"> 
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>发票类型：</label></td>
						<td>
							<select type="text" id="dia_invoiceType" name="dia_invoiceType" class="combox" kind="dic" src="FPLX" datasource="INVOICE_TYPE" datatype="1,is_null,6"> 
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
					<tr  id="setDate" style="display:none">
						<td><label>结算单类型执行开始日期：</label></td>
						<td><input type="text" group="dia_settleStartDate,dia_settleEndDate" id="dia_settleStartDate" name="dia_settleStartDate" datasource="SETTLE_START_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind="date"  /></td>
						<td><label>结算单类型执行结束日期：</label></td>
						<td><input type="text" group="dia_settleStartDate,dia_settleEndDate" id="dia_settleEndDate" name="dia_settleEndDate" datasource="SETTLE_END_DATE"  datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind="date" /></td>
					</tr>
					<tr>
						<td><label>是否补足金额：</label></td>
						<td>
							<select type="text" id="dia_ifmakeamount" name="dia_ifmakeamount" class="combox" kind="dic" src="SF" datasource="IF_MAKE_AMOUNT" datatype="1,is_null,6"> 
								<option value=-1>--</option>
							</select>
						</td>
					</tr>
					<tr  id="setDate1" style="display:none">
						<td><label>补足开始日期：</label></td>
						<td><input type="text" group="dia_settleStartDate1,dia_settleEndDate1" id="dia_settleStartDate1" name="dia_settleStartDate1" datasource="MAKE_START_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind="date"  /></td>
						<td><label>补足结束日期：</label></td>
						<td><input type="text" group="dia_settleStartDate1,dia_settleEndDate1" id="dia_settleEndDate1" name="dia_settleEndDate1" datasource="MAKE_END_DATE"  datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind="date" /></td>
					</tr>
				</table>
		  	</fieldset>
			</div>
		</form>	
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_save">设&nbsp;&nbsp;置</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction";
$(function(){
	var selectedRows = $("#dealerList").getSelectedRows();
	setEditValue("dia_dealerDetailfm",selectedRows[0].attr("rowdata"));
	if($("#dia_ifmakeamount").val()==100101){
		$("#setDate1").show();	
	}
	if($("#dia_settleType").attr("code")!="-1"){
		$("#setDate").show();
	}
	
	$("#dia_ifmakeamount").change(function(){
		var sfbzje=$(this).val();
		if(sfbzje==100101){
			$("#setDate1").show();
		}else {
			$("#setDate1").hide();
		}
	});
	//设置
	$("#dia_save").bind("click",function(){
		var $form = $("#dia_dealerDetailfm");
		if (submitForm($form) == false) {
			return false;
		}
		if($("#dia_settleType").attr("code")!="-1"){
			if($("#dia_settleStartDate").attr("code")==''){
				alertMsg.warn("结算单类型执行请选择开始日期.");
				return ;
			}
			if($("#dia_settleEndDate").attr("code")==''){
				alertMsg.warn("结算单类型执行请选择结束日期.");
				return ;
			}
		}
		if($("#dia_ifmakeamount").val()==100101){
			if($("#dia_settleStartDate1").attr("code")==''){
				alertMsg.warn("补足请选择开始日期.");
				return ;
			}
			if($("#dia_settleEndDate1").attr("code")==''){
				alertMsg.warn("补足请选择结束日期.");
				return ;
			}
		}
		var sCondition = {};
		sCondition = $form.combined(1) || {};
		var addUrl = diaSaveAction + "/dealerUpdate.ajax";
		doNormalSubmit($form,addUrl,"dia_save",sCondition,updateCallBack);
	});
});
//设置回调
function updateCallBack(res)
{
	try
	{
		var selectedIndex = $("#dealerList").getSelectedIndex();
		$("#dealerList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;	
}

function afterDicItemClick(id, $row, selIndex){
	if(id=="dia_settleType"){
		if($("#dia_settleType").attr("code")=="-1"){
			$("#dia_settleStartDate").val("");
			$("#dia_settleStartDate").attr("code","");
			$("#dia_settleEndDate").val("");
			$("#dia_settleEndDate").attr("code","");
			$("#setDate").hide();
		}else{
			$("#setDate").show();
		}
	}
	return true;
}
</script>