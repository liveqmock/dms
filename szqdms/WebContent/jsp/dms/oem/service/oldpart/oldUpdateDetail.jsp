<?xml version="1.0" encoding="UTF-8" ?>
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
						<td><input type="text" id="dia_updatePartCode" name="dia_updatePartCode"  datatype="0,is_null,30" datasource="UPDATE_PART_CODE" readOnly hasBtn="true" callFunction="openOldPart();"/></td>
						<td><label>旧件名称：</label></td>
						<td><input type="text" id="dia_updatePartName" name="dia_updatePartName"  datatype="1,is_null,300" datasource="UPDATE_PART_NAME" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>旧件供应商代码：</label></td>
						<td><input type="text" id="dia_updateSupplyCode" name="dia_updateSupplyCode" datasource="UPDATE_SUPPLY_CODE"  datatype="0,is_null,30" disabledcode="true" kind="dic" src="" dicwidth="300"/></td>
						<td><label>旧件供应商名称：</label></td>
						<td><input type="text" id="dia_updateSupplyName" name="dia_updateSupplyName" datasource="UPDATE_SUPPLY_NAME"  datatype="1,is_null,300" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>旧件数量：</label></td>
						<td><input type="text" id="updatePartCount" name="updatePartCount" datasource="UPDATE_PART_COUNT"  datatype="0,is_digit,10"/></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
						<td colspan="3"><textarea id="dia-ramarks" style="width: 450px; height: 50px;" name="dia-ramarks" datasource="UPDATE_REMARKS" datatype="0,is_null,1000"></textarea></td>
					</tr>
				</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("oldPartUpdate");
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartUpdateApplyAction";
var dia_di_options = {max:false,width:900,height:700,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
	var selectedRows = $("#oldPartList").getSelectedRows();
	setEditValue("dia-Detailfm",selectedRows[0].attr("rowdata"));
	
	$("#dia_updatePartCode").val(selectedRows[0].attr("PART_CODE"));
	$("#dia_updatePartName").val(selectedRows[0].attr("PART_NAME"));
	$("#dia_updateSupplyCode").val(selectedRows[0].attr("PROSUPPLY_CODE"));
	$("#dia_updateSupplyName").val(selectedRows[0].attr("PROSUPPLY_NAME"));
	$("#updatePartCount").val(selectedRows[0].attr("ACTUL_COUNT"));
	$("#updatePartId").val(selectedRows[0].attr("PART_ID"));
	$("#updateSupplyId").val(selectedRows[0].attr("PROSUPPLY_ID"));
	
	//设置表现字典
	$("#dia_updateSupplyCode").attr("src","T#PT_BA_SUPPLIER S,PT_BA_PART_SUPPLIER_RL R:S.SUPPLIER_CODE:S.SUPPLIER_NAME{S.SUPPLIER_ID}:1=1 AND S.SUPPLIER_ID=R.SUPPLIER_ID AND R.STATUS=100201 AND  R.PART_ID="+$("#updatePartId").val()+"");
	$("#dia_updateSupplyCode").attr("code",selectedRows[0].attr("PROSUPPLY_CODE"));
	$("#dia_updateSupplyCode").val(selectedRows[0].attr("PROSUPPLY_CODE"));
	
	//旧件数量校验
	$("#updatePartCount").bind("blur",function(){
		var partCount=$("#actulCount").val();
		var newPartCount=$("#updatePartCount").val();
		if(newPartCount - partCount > 0 ){
			alertMsg.warn("旧件数量不能超过原旧件数量！");
			$("#updatePartCount").val('');
			return false;
		}
		if(newPartCount == 0 ){
			alertMsg.warn("旧件数量不能为0！");
			$("#updatePartCount").val('');
			return false;
		}
	});
	
	//保存
	$("#save").bind("click",function(){
		var $f = $("#dia-Detailfm");
		if (submitForm($f) == false) {
			return false;
		}
		var reason=$("#dia-ramarks").val();
		if(reason.length == 0){
			alertMsg.warn("请填写备注信息！");
			return;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-Detailfm").combined(1) || {};
		var updateUrl = diaSaveAction + "/oldPartOemUpdate.ajax";
		doNormalSubmit($f,updateUrl,"save",sCondition,diaSaveCallBack);
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
//选择旧件页面
function openOldPart(){
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/claimUpdateOldPartSelect.jsp", "oldPartSelect", "旧件选择", dia_di_options);
}
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id.indexOf("dia_updateSupplyCode") == 0)
	{
		$("#updateSupplyId").val($row.attr("S.SUPPLIER_ID"));
		$("#dia_updateSupplyName").val($("#dia_updateSupplyCode").val());
		$("#dia_updateSupplyCode").val($("#dia_updateSupplyCode").attr("code"));
	}
	return true;
}

//保存回调
function diaSaveCallBack(res){
	try
	{
		if($row){
			$("#oldPartList").removeResult($row);//移除选择行
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