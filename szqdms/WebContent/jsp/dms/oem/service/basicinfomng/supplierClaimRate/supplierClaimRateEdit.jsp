<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-supplierClaimRate" class="editForm" style="width: 100%;">
			<input type="hidden" id="dia-relationId" datasource="RELATION_ID"/>
			<div id="dia-vehicle">
			<table width="100%" id="dia-suppTable" name="dia-suppTable" class="editTable" >
                 <tr>
					<td><label>供应商代码：</label></td>
					<td><input type="text" id="dia-supplierCode" name="dia-supplierCode" value="" datasource="SUPPLIER_CODE" readonly="readonly"/></td>
					<td><label>供应商名称：</label></td>
					<td><input type="text" id="dia-supplierName" name="dia-supplierName" value="" datasource="SUPPLIER_NAME" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>配件代码：</label></td>
					<td><input type="text" id="dia-partCode" name="dia-partCode" value="" datasource="PART_CODE" readonly="readonly"/></td>
					<td><label>配件名称：</label></td>
					<td><input type="text" id="dia-partName" name="dia-partName" value="" datasource="PART_NAME" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>索赔承担系数：</label></td>
					<td><input type="text" id="dia-claimRate" name="dia-claimRate" value="" datasource="CLAIM_RATE" datatype="1,is_double,30"  /></td>
				</tr>
				<tr>
					<td colspan="2"><label><font color="red">*注：索赔单价为小数</font></label></td>
				</tr>
			</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia-save" >保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>    	
	</div>
</div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/SupplierClaimDateAction";
$(function(){
	var selectedRows = $("#supplierList").getSelectedRows();
	setEditValue("dia-supplierClaimRate",selectedRows[0].attr("rowdata"));
	
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	
	$("#dia-save").bind("click",function(){
		var $fm = $("#dia-supplierClaimRate");
		if (submitForm($fm) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $fm.combined(1) || {};
		var updateUrl = diaSaveAction + "/rateUpdate.ajax";
		doNormalSubmit($fm,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
	});
});
//保存回调
function diaUpdateCallBack(res){
	try
	{
		var selectedIndex = $("#supplierList").getSelectedIndex();
		$("#supplierList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>

