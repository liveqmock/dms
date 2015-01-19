<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>手工帐编辑</title>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var action = "<%=action%>";
var diaSaveAction="<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction";
$(function(){
	//初始化页面
	if(action !=1 ){
		var selectedRows = parent.$("#manuallyList").getSelectedRows();
		setEditValue("manually-form",selectedRows[0].attr("rowdata"));
		$("#btnNext1").show();
	}
	//通过
	$("#btn-check").bind("click",function(){
		var manuallyId= $("#manuallyId").val();
		var $f = $("#manually-form");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $("#manually-form").combined(1) || {};
		var reportUrl = diaSaveAction + "/checkPass.ajax?manuallyId="+manuallyId;
		doNormalSubmit($f,reportUrl,"btn-check",sCondition,diaReportCallBack);
	});
	//驳回
	$("#dia_reject").bind("click",function(){
		var manuallyId= $("#manuallyId").val();
		var $f = $("#manually-form");
		if (submitForm($f) == false) {
			return false;
		}
		var shyj=$("#remarks1").val();
		if(null==shyj||shyj==""){
			alertMsg.warn("请先填写审核意见。");
			return false;
		}
		var sCondition = {};
		sCondition = $("#manually-form").combined(1) || {};
		var reportUrl = diaSaveAction + "/checkReject.ajax?manuallyId="+manuallyId;
		doNormalSubmit($f,reportUrl,"btn-check",sCondition,diaReportCallBack);
	});
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("oldPart");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
//修改回调
function diaUpdateCallBack(res){
	try
	{
		parent.$("#search").click();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//提报回调
function diaReportCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result!='1'){
			parent.$.pdialog.closeCurrent();
			parent.$("#search").click();
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsContent">
		<div class="page">
		<div class="pageContent" style="" >
			<form method="post" id="manually-form" class="editForm" >
				<input type="hidden" id="manuallyId" name="manuallyId" datasource="MANUALLY_ID"/>
				<div align="left">
				<fieldset>
				<table class="editTable" id="dia-oldpartTab">
					<tr>
						<td ><label>入账单位：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="ORG_CODE" readonly="readonly"/></td>
					</tr>
					<tr>	
						<td><label>入账类型：</label></td>
						<td><input  type="text" id="manuallyType" name="manuallyType" datasource="MANUALLY_TYPE"  src="SGRZLX"  readonly="readonly" value="" />
						</td>
						<td><label>入账路径：</label></td>
						<td><input  type="text" id="manuallyWay" name="manuallyWay" datasource="MANUALLY_WAY"   src="JSLX"  readonly="readonly" value="" />
						</td>
					</tr>
					<tr>	
						<td ><label>入账金额：</label></td>
						<td><input type="text" id="manuallyCosts" name="manuallyCosts" datasource="MANUALLY_COSTS"readonly="readonly"/></td>
						<td ><label>索赔单号：</label></td>
						<td><input type="text" id="claimNo" name="claimNo" datasource="CLAIM_NO" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="remarks" name="remarks" datasource="REMARKS" style="width:100%" readonly="readonly"></textarea></td>
					</tr>
					<tr>
						<td><label>审核意见：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="remarks1" name="remarks1" datasource="CHECK_REMARKS" style="width:100%"  datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
				</fieldset>
				</div>
			</form>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="btn-check" >通&nbsp;&nbsp;过</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia_reject">驳&nbsp;&nbsp;回</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
	</div>
	 <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>