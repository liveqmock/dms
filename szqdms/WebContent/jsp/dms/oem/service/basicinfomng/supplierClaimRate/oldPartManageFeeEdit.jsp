<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-oldPartManage" class="editForm" style="width: 100%;">
			<input type="hidden" id="dia-partId" datasource="PART_ID"/>
			<div id="dia-vehicle">
			<table width="100%" id="dia-partTable" name="dia-partTable" class="editTable" >
				<tr>
					<td><label>配件代码：</label></td>
					<td><input type="text" id="dia-partCode" name="dia-partCode" value="" datasource="PART_CODE" readonly="readonly"/></td>
					<td><label>配件名称：</label></td>
					<td><input type="text" id="dia-partName" name="dia-partName" value="" datasource="PART_NAME" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>旧件管理系数：</label></td>
					<td><input type="text" id="dia-oldPartFee" name="dia-oldPartFee" value="" datasource="OLD_MANAGE_FEE" datatype="1,is_double,30"  /></td>
				</tr>
				<tr>
					<td colspan="2"><label><font color="red">*注：旧件管理系数为小数</font></label></td>
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
	var selectedRows = $("#partList").getSelectedRows();
	setEditValue("dia-oldPartManage",selectedRows[0].attr("rowdata"));
	
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	
	$("#dia-save").bind("click",function(){
		var $fm = $("#dia-oldPartManage");
		if (submitForm($fm) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $fm.combined(1) || {};
		var updateUrl = diaSaveAction + "/oldPartManageUpdate.ajax";
		doNormalSubmit($fm,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
	});
});
//保存回调
function diaUpdateCallBack(res){
	try
	{
		var selectedIndex = $("#partList").getSelectedIndex();
		$("#partList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>

