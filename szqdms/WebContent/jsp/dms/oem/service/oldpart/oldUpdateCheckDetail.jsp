<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="oldPartDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-Detailfm" class="editForm" >
			<input type="hidden" id="detailId" datasource="DETAIL_ID" />
			<input type="hidden" id="partId" datasource="PART_ID"/>
			<input type="hidden" id="prosupplyId" datasource="PROSUPPLY_ID"/>
			<input type="hidden" id="updatePartId" datasource="UPDATE_PART_ID"/>
			<input type="hidden" id="updateSupplyId" datasource="UPDATE_SUPPLY_ID"/>
			<div align="left">
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('dia-DetailTable')">&nbsp;原旧件信息&gt;&gt;</a></legend>
				<table class="editTable" id="dia-DetailTable">
					<tr>
						<td><label>索赔单号：</label></td>
						<td><input type="text" id="dia_claimNo" name="dia_claimNo" datasource="CLAIM_NO" readonly="readonly"/></td>
						<td><label>原旧件代码：</label></td>
						<td><input type="text" id="dia_partCode" name="dia_partCode" datasource="PART_CODE" readonly="readonly"/></td>
						<td><label>原旧件名称：</label></td>
						<td><input type="text" id="dia_partName" name="dia_partName" datasource="PART_NAME" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>原旧件数量：</label></td>
						<td><input type="text" id="actulCount" name="actulCount" datasource="ACTUL_COUNT" readonly="readonly"/></td>
						<td><label>原旧件供应商代码：</label></td>
						<td><input type="text" id="dia_prosupplyCode" name="dia_prosupplyCode" datasource="PROSUPPLY_CODE" readonly="readonly"/></td>
						<td><label>原旧件供应商名称：</label></td>
						<td><input type="text" id="dia_prosupplyName" name="dia_prosupplyName" datasource="PROSUPPLY_NAME" readonly="readonly"/></td>
					</tr>
				</table>
			</fieldset>
			</div>
			<div align="left">
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('dia-DetailUpdateTable')">&nbsp;修改旧件信息&gt;&gt;</a></legend>
				<table class="editTable" id="dia-DetailUpdateTable">
					<tr>
						<td><label>旧件代码：</label></td>
						<td><input type="text" id="dia_updatePartCode" name="dia_updatePartCode"  datatype="1,is_null,30" datasource="UPDATE_PART_CODE" readonly="readonly" /></td>
						<td><label>旧件名称：</label></td>
						<td><input type="text" id="dia_updatePartName" name="dia_updatePartName"  datatype="1,is_null,300" datasource="UPDATE_PART_NAME" readonly="readonly"/></td>
						<td><label>旧件数量：</label></td>
						<td><input type="text" id="updatePartCount" name="updatePartCount" datasource="UPDATE_PART_COUNT"  datatype="1,is_null,10" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>旧件供应商代码：</label></td>
						<td><input type="text" id="dia_updateSupplyCode" name="dia_updateSupplyCode" datasource="UPDATE_SUPPLY_CODE"  datatype="1,is_null,30" readonly="readonly"/></td>
						<td><label>旧件供应商名称：</label></td>
						<td colspan="3"><input type="text" id="dia_updateSupplyName" name="dia_updateSupplyName" datasource="UPDATE_SUPPLY_NAME"  datatype="1,is_null,300" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
						<td colspan="5"><textarea id="dia-ramarks" style="width: 450px; height: 50px;" name="dia-ramarks" datasource="UPDATE_REMARKS" datatype="1,is_null,1000" readonly="readonly"></textarea></td>
					</tr>
					<tr>
						<td><label>审核意见：</label></td>
						<td colspan="5"><textarea id="dia-opinion" style="width: 450px; height: 50px;" name="dia-opinion" datasource="UPDATE_OPINION" datatype="0,is_null,1000" ></textarea></td>
					</tr>
				</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="pass" onclick="doCheck('<%=DicConstant.JJXGSQZT_02%>')">审核通过</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="reject" onclick="doCheck('<%=DicConstant.JJXGSQZT_03%>')">审核驳回</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("olpPartUpdate");
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartUpdateApplyAction";
var dia_di_options = {max:false,width:900,height:700,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
	var selectedRows = $("#oldPartList").getSelectedRows();
	setEditValue("dia-Detailfm",selectedRows[0].attr("rowdata"));
	
	//保存
	$("#save").bind("click",function(){
		
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function doCheck(status){
	var btnCheck=null;
	if(status==<%=DicConstant.JJXGSQZT_02%>){
		btnCheck="pass";
	}else{
		btnCheck="reject";
	}
	
	var $f = $("#dia-Detailfm");
	if (submitForm($f) == false) {
		return false;
	}
	var reason=$("#dia-opinion").val();
	if(reason.length==0){
		alertMsg.warn("审核意见不能为空，请填写审核意见！");
		return false;
	}
	var sCondition = {};
	sCondition = $("#dia-Detailfm").combined(1) || {};
	var updateUrl = diaSaveAction + "/oldPartUpdateCheck.ajax?status="+status;
	doNormalSubmit($f,updateUrl,btnCheck,sCondition,diaSaveCallBack);
}
//保存回调
function diaSaveCallBack(res){
	try
	{
		var $row = $("#oldPartList").getSelectedRows();//选择行
		if($row[0]){
			$("#oldPartList").removeResult($row[0]);//移除选择行
			$.pdialog.close(dialog);
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