<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>追偿清单调整</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/RecoverListSearchAction";
$(function()
{
	var selectedRows = parent.$("#recoverList").getSelectedRows();
	setEditValue("dia_adjustDetailfm",selectedRows[0].attr("rowdata"));
	

	$("#dia_adjustCost").bind("blur",function(){
		if (is_all_digit_2($("#dia_adjustCost")) === true) {
			doSum();
		}else{
			alertMsg.error("请输入正确的数字.");
		}
		
	});
	
	//调整完成
	$("#dia_save").bind("click",function(){
		var $form = $("#dia_adjustDetailfm");
		if (submitForm($form) === true) {
			if (!is_all_digit_2($("#dia_adjustCost")) === true) {
				alertMsg.error("请输入正确的数字.");
				return;
			}
			var sCondition = {};
			sCondition = $form.combined(1) || {};
			var addUrl = diaSaveAction + "/recoveryAdjustSave.ajax";
			doNormalSubmit($form,addUrl,"dia-save",sCondition,adjustCallBack);
		}
	});
	
	//上传附件
	$("#dia_addAtt").bind("click",function(){
		var recoveryId=$("#dia_recoveryId").val();
		$.filestore.open(recoveryId,{"fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	
	//查看附件
	$("#dia_viewAtt").bind("click",function(){
		var recoveryId=$("#dia_recoveryId").val();
		$.filestore.view(recoveryId);
	});
	
	//关闭当前页面
	$("button.close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
	
});
//校验正确的数字格式，可以为负数，实数保留2位有效数字
function is_all_digit_2(handle) {
	var pattern = /^((\-[1-9]\d*)|(([1-9]\d*)|0))(\.\d{1,2})?$/;
	if(!pattern.exec(handle.val())){
		return false;
	}else{
		return true;
	}
}
function doSum(){
	var sum=parseFloat($("#dia_claimTotalCost").val())
			+parseFloat($("#dia_claimManageCost").val())
			+parseFloat($("#dia_oldPartManageCost").val())
			+parseFloat($("#dia_unknownTatalCost").val())
			+parseFloat($("#dia_adjustCost").val());
	$("#dia_totalCost").val(sum);
}
//调整回调
function adjustCallBack(res){
	try{
		//处理调整后主页面回显金额
		parent.$("#search").trigger("click");
	}catch(e){
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
		<form method="post" id="dia_adjustDetailfm" class="editForm" >
			<input type="hidden" id="dia_recoveryId" name="dia_recoveryId" datasource="RECOVERY_ID"/>
			<div align="left">
			<fieldset>
				<table class="editTable" id="dia_adjustDetailTable">
					<tr>
						<td><label>供应商名称：</label></td>
						<td><input type="text" id="dia_supplierName" name="dia_supplierName" datasource="SUPPLIER_NAME" readonly="readonly"/></td>
						<td><label>供应商代码：</label></td>
						<td><input type="text" id="dia_supplierCode" name="dia_supplierCode" datasource="SUPPLIER_CODE" readonly="readonly"/></td>
						<td><label>追偿日期：</label></td>
						<td><input type="text" id="dia_recoveryDate" name="dia_recoveryDate" datasource="RECOVERY_DATE" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>总费用：</label></td>
						<td><input type="text" id="dia_totalCost" name="dia_totalCost" datasource="TOTAL_COST"  datatype="1,is_null,30" readonly="readonly" value="" /></td>
						<td><label>报单总费用：</label></td>
						<td><input type="text" id="dia_claimTotalCost" name="dia_claimTotalCost" datasource="CLAIM_TOTAL_COST"  datatype="1,is_null,30" value="" readonly="readonly"/></td>
						<td><label>服务管理费：</label></td>
						<td><input type="text" id="dia_claimManageCost" name="dia_claimManageCost" datasource="CLAIM_MANAGE_COST"  datatype="1,is_null,30" value=""readonly="readonly" /></td>
					</tr>
					<tr>
						<td><label>旧件管理费：</label></td>
						<td><input type="text" id="dia_oldPartManageCost" name="dia_oldPartManageCost" datasource="OLDPART_MANAGE_COST"  datatype="1,is_null,30" value=""readonly="readonly" /></td>
						<td><label>未知费用：</label></td>
						<td><input type="text" id="dia_unknownTatalCost" name="dia_unknownTatalCost" datasource="UNKNOWN_TOTAL_COST"  datatype="1,is_null,30"  value="" readonly="readonly"/></td>
						<td><label>调整费用：</label></td>
						<td><input type="text" id="dia_adjustCost" name="dia_adjustCost" datasource="ADJUST_COST"  datatype="0,is_null,30" value="" /></td>
					</tr>
				</table>
		  	</fieldset>
			</div>
		</form>	
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_save">调整完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_addAtt">上传附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_viewAtt">查看附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
</body>
</html>

