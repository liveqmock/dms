<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="oldPartEdit" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-di-oldPartEditfm" class="editForm" >
			<input type="hidden"  id="dia_di_isMain"  datasource="IS_MAIN_CODE"/>
			<input type="hidden"  id="dia_di_detailId"  datasource="DETAIL_ID"/>
			<input type="hidden"  id="dia_di_claimDtlId"  datasource="CLAIM_DTL_ID"/>
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-di-oldPartEditTable">
				<tr>
					 <td><label>旧件代码：</label></td>
					 <td><input type="text" id="dia_di_PartCode" name="dia_di_PartCode" datasource="PART_CODE" datatype="1,is_null,30" value="" readOnly hasBtn="true" callFunction="openOldPart();"/></td> 
					<!--<td><input type="text" id="dia_di_PartCode" name="dia_di_PartCode" datasource="PART_CODE" datatype="1,is_null,30" value="" readonly="readonly"/></td>-->
			    	<td><label>旧件名称：</label></td>
			    	<td><input type="text" id="dia_di_PartName" name="dia_di_PartName" datasource="PART_NAME"  datatype="1,is_null,30" value="" readonly="readonly"/></td>
			    </tr>
			    <tr>
					<td><label>旧件数量：</label></td>
					<td><input type="text" id="dia_di_PartCount" name="dia_di_PartCount" datasource="OUGHT_COUNT" datatype="0,is_digit,100" value=""/></td>
					<td><label>旧件类别：</label></td>
			    	<td><input type="text" id="dia_di_PartType" name="dia_di_PartType" datasource="PART_TYPE" datatype="1,is_null,6" value="" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>供应商代码：</label></td>
					<td><input type="text" id="dia_di_prosupplyCode" name="dia_di_prosupplyCode" datasource="PROSUPPLY_CODE" datatype="1,is_null,30" value="" disabledcode="true" kind="dic" src="" dicwidth="300"/></td>
			    	<td><label>供应商名称：</label></td>
			    	<td><input type="text" id="dia_di_prosupplyName" name="dia_di_prosupplyName" datatype="1,is_null,100" datasource="PROSUPPLY_NAME" value="" readonly="readonly"/></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_di_save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
	<form id="dia_di_oldPartHfm" style="display:">
	    <input type="hidden" id="dia_di_partIdH" datasource="PART_ID"/>
		<input type="hidden" id="dia_di_supplierIdH" datasource="SUPPLIER_ID"/>
		<input type="hidden" id="dia_di_partCountH" datasource="OUGHT_COUNT"/>
		<input type="hidden" id="dia_di_newPartCountH" datasource="PART_COUNT"/>
		<input type="hidden" id="dia_di_newPartIdH" datasource="NEW_PART_ID"/>
		<input type="hidden" id="dia_di_newSupplierIdH" datasource="NEW_SUPPLIER_ID"/>
		<input type="hidden" id="dia_di_newSalePriceH" datasource="NEW_SALE_PRICE"/>
	</form>
</div>
<script type="text/javascript">
var action=<%=action%>;
var diadiSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/ClaimUpdateAction";
var dia_di_options = {max:false,width:900,height:700,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//弹出窗体
var dialog = $("body").data("claimOldPartEdit");
$(function()
{
	if(action!=1){
		var selectedRows = $("#oldPartList").getSelectedRows();
		setEditValue("dia-di-oldPartEditfm",selectedRows[0].attr("rowdata"));	
		$("#dia_di_partIdH").val(selectedRows[0].attr("PART_ID"));
		$("#dia_di_newPartIdH").val(selectedRows[0].attr("PART_ID"));
		$("#dia_di_supplierIdH").val(selectedRows[0].attr("PROSUPPLY_ID"));
		$("#dia_di_newSupplierIdH").val(selectedRows[0].attr("PROSUPPLY_ID"));
		$("#dia_di_partCountH").val(selectedRows[0].attr("OUGHT_COUNT"));
		//设置表现字典
		$("#dia_di_prosupplyCode").attr("src","T#PT_BA_SUPPLIER S,PT_BA_PART_SUPPLIER_RL R:S.SUPPLIER_CODE:S.SUPPLIER_NAME{S.SUPPLIER_ID}:1=1 AND S.SUPPLIER_ID=R.SUPPLIER_ID AND R.PART_ID="+$("#dia_di_partIdH").val()+"");
	}
	//旧件数量校验
	$("#dia_di_PartCount").bind("blur",function(){
		var partCount=$("#dia_di_partCountH").val();
		var newPartCount=$("#dia_di_PartCount").val();
		if(newPartCount - partCount > 0 ){
			alertMsg.warn("旧件数量不能超过原旧件数量！");
			$("#dia_di_PartCount").val('');
			return false;
		}
		if(newPartCount == 0 ){
			alertMsg.warn("旧件数量不能为0！");
			$("#dia_di_PartCount").val('');
			return false;
		}
	});
	//保存方法
	$("#dia_di_save").bind("click",function(){
		var isMain=$("#dia_di_isMain").val();
		var claimDtlId=$("#dia_di_claimDtlId").val();
		var claimId=$("#dia_claimId").val();
		var detailId=$("#dia_di_detailId").val();
		$("#dia_di_newPartCountH").val($("#dia_di_PartCount").val());
		var $f = $("#dia-di-oldPartEditfm");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia_di_oldPartHfm").combined(1) || {};
		var updateUrl = diadiSaveAction + "/claimOldPartUpdate.ajax?isMain="+isMain+"&claimDtlId="+claimDtlId+"&claimId="+claimId+"&detailId="+detailId;
		doNormalSubmit($f,updateUrl,"dia-save",sCondition,diadiSaveCallBack);
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	if(id.indexOf("dia_di_prosupplyCode") == 0)
	{
		$("#dia_di_newSupplierIdH").val($row.attr("S.SUPPLIER_ID"));
		$("#dia_di_prosupplyName").val($("#dia_di_prosupplyCode").val());
		$("#dia_di_prosupplyCode").val($("#dia_di_prosupplyCode").attr("code"));
	}
	return true;
}
//选择旧件页面
function openOldPart(){
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/claimUpdateOldPartSelect.jsp?flag="+action, "oldPartSelect", "旧件选择", dia_di_options);
}
//保存回调
function diadiSaveCallBack(res){
	try
	{
		$.pdialog.close(dialog);
		var $f = $("#dia-fm-claimMaintain");
		var sCondition = {};
		sCondition = $f.combined() || {};
		var oldPartSearchUrl1 =oldPartSearchUrl+"?claimId="+$("#dia_claimId").val(); 
		doFormSubmit($f,oldPartSearchUrl1,"",1,sCondition,"oldPartList");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>