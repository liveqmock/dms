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
var diaSaveAction="<%=request.getContextPath()%>/service/financeMng/StatementAdjustAction";
$(function()
{
	var selectedRows = parent.$("#settleList").getSelectedRows();
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
	var $f = $("#dia_settleDetailfm");
	//服务费项事件
	$("#dia_seReCosts").bind("blur",function(){
		if (submitForm($f) == false) {
			$("#dia_seReCosts").val(0);
		}
		doSeSummary();
	});
	$("#dia_policySup").bind("blur",function(){
		if (submitForm($f) == false) {
			$("#dia_policySup").val(0);
		}
		doSeSummary();
	});
	$("#dia_seCashGift").bind("blur",function(){
		if (submitForm($f) == false) {
			$("#dia_seCashGift").val(0);
		}
		doSeSummary();
	});
	$("#dia_seCarAward").bind("blur",function(){
		if (submitForm($f) == false) {
			$("#dia_seCarAward").val(0);
		}
		doSeSummary();
	});
	$("#dia_seApCosts").bind("blur",function(){
		if (submitForm($f) == false) {
			$("#dia_seApCosts").val(0);
		}
		doSeSummary();
	});
	$("#dia_seOthers").bind("blur",function(){
		if (submitForm($f) == false) {
			$("#dia_seOthers").val(0);
		}
		doSeSummary();
	});
	
	//上传附件
	$("#dia_addAtt1").bind("click",function(){
		var settleId=$("#dia_settleId").val();
		$.filestore.open(settleId,{"fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	
	//查看附件
	$("#dia_viewAtt1").bind("click",function(){
		var settleId=$("#dia_settleId").val();
		$.filestore.view(settleId);
	});
	//调整完成
	$("#dia_save").bind("click",function(){
		var $form = $("#dia_settleDetailfm");
		if (submitForm($form) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $form.combined(1) || {};
		var addUrl = diaSaveAction + "/settleAdjustSave.ajax?flag=1";
		doNormalSubmit($form,addUrl,"dia-save",sCondition,settleAdjustCallBack);
	});
	
	//关闭当前页面
	$("button.close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
//调整回调方法
function settleAdjustCallBack(res){
	try{
		/* var selectedIndex = $("#settleList").getSelectedIndex();
		$("#settleList").updateResult(res,selectedIndex); */
		//处理调整后主页面回显金额
		parent.$("#search").trigger("click");
	}catch(e){
		alertMsg.error(e);
		return false;
	}
	return true;
}
//计算服务费汇总
function doSeSummary(){
	
	var seCosts=$("#dia_seCosts").val();//服务费/材料费
	var partMaterialCosts =$("#dia_partMaterialCosts").val();//配件索赔材料费
	var seReCosts;//旧件运费/配件返利
	var policySup;//政策支持
	var seCashGift;//礼金
	var seCarAward;//售车奖励
	var seApCosts;//考核费用
	var seOthers;//其它费用
	//旧件运费/配件返利
	if($("#dia_seReCosts").val()==''){
		seReCosts=0;
	}else{
		seReCosts=$("#dia_seReCosts").val();
	}
	//政策支持
	if($("#dia_policySup").val()==''){
		policySup=0;
	}else{
		policySup=$("#dia_policySup").val();
	}
	//礼金
	if($("#dia_seCashGift").val()==''){
		seCashGift=0;
	}else{
		seCashGift=$("#dia_seCashGift").val();
	}
	//售车奖励
	if($("#dia_seCarAward").val()==''){
		seCarAward=0;
	}else{
		seCarAward=$("#dia_seCarAward").val();
	}
	//考核费用
	if($("#dia_seApCosts").val()==''){
		seApCosts=0;
	}else{
		seApCosts=$("#dia_seApCosts").val();
	}
	//其它费用
	if($("#dia_seOthers").val()==''){
		seOthers=0;
	}else{
		seOthers=$("#dia_seOthers").val();
	}
	//汇总
	var seSummary=parseFloat(seCosts)+parseFloat(partMaterialCosts)+parseFloat(seReCosts)+parseFloat(policySup)+parseFloat(seCashGift)+parseFloat(seCarAward)+parseFloat(seApCosts)+parseFloat(seOthers);
	$("#dia_seSummary").val(seSummary);
}
</script>
</head>
<body>
<div id="settleDetail" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia_settleDetailfm" class="editForm" >
			<input type="hidden" id="dia_settleId" name="dia_settleId" datasource="SETTLE_ID"/>
			<input type="hidden" id="dia_settleNo" name="dia_settleNo" datasource="SETTLE_NO"/>
			<input type="hidden" id="dia_orgCode" name="dia_orgCode" datasource="ORG_CODE"/>
			<div align="left">
			<fieldset>
				<table class="editTable" id="dia_settleDetailReaTable">
					<tr>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="dia_orgName" name="dia_orgName" datasource="ORG_NAME" readonly="readonly"/></td>
						<td><label>结算产生日期：</label></td>
						<td><input type="text" id="dia_settleDate" name="dia_settleDate" datasource="SETTLE_DATE" readonly="readonly"/></td>
						<td><label>结算类型：</label></td>
						<td><input type="text" id="dia_settleType" name="dia_settleType" datasource="SETTLE_TYPE" readonly="readonly"/></td>
					</tr>
				</table>
			</fieldset>
			<fieldset>
			<legend align="right"><a id="typeH" onclick="onTitleClick('dia_settleDetailSeTable')">&nbsp;服务费用信息&gt;&gt;</a></legend>
				<table class="editTable" id="dia_settleDetailSeTable">
					<tr>
						<td><label id="orgNameL">服务费：</label></td>
						<td><input type="text" id="dia_seCosts" name="dia_seCosts" datasource="COSTS" value="" readonly="readonly"/></td>
						<td><label id="dia_seReCostsL">旧件运费：</label></td>
						<td><input type="text" id="dia_seReCosts" name="dia_seReCosts" datasource="RE_COSTS"  datatype="0,is_money,30" value="" /></td>
						<td><label>配件索赔材料费：</label></td>
						<td><input type="text" id="dia_partMaterialCosts" name="dia_partMaterialCosts" datasource="PART_MATERIAL_COSTS"  readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>政策支持：</label></td>
						<td><input type="text" id="dia_policySup" name="dia_policySup" datasource="POLICY_SUP"  datatype="0,is_money,30" readonly="readonly" value="" /></td>
						<td><label>礼金：</label></td>
						<td><input type="text" id="dia_seCashGift" name="dia_seCashGift" datasource="CASH_GIFT"  datatype="0,is_money,30" value="" /></td>
						<td><label>售车奖励：</label></td>
						<td><input type="text" id="dia_seCarAward" name="dia_seCarAward" datasource="CAR_AWARD"  datatype="0,is_money,30" value="" /></td>
					</tr>
					<tr>
						<td><label>考核费用：</label></td>
						<td><input type="text" id="dia_seApCosts" name="dia_seApCosts" datasource="AP_COSTS"  datatype="0,is_money,30" value="" /></td>
						<td><label>其他费用：</label></td>
						<td><input type="text" id="dia_seOthers" name="dia_seOthers" datasource="OTHERS"  datatype="0,is_money,30" readonly="readonly" value="" /></td>
						<td><label>汇总：</label></td>
						<td><input type="text" id="dia_seSummary" name="dia_seSummary" datasource="SUMMARY"  datatype="1,is_money,30" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>手工帐：</label></td>
						<td><input type="text" id="dia_manuallyCost" name="dia_manuallyCost" datasource="MANUALLY_COST"  datatype="1,is_money,30" readonly="readonly"/></td>
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
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_save">调整完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_addAtt1">上传附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia_viewAtt1">查看附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
</body>
</html>


