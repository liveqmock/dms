<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-dealerInfo" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="width:100%;">
		<form method="post" id="dia-fm-dealerInfo" class="editForm" >
			<!-- 隐藏域 -->
			<input type="hidden" id="dia-dealerId" name="dia-dealerId" datasource="DEALER_ID" />
			<input type="hidden" id="dia-status" name="dia-status" datasource="STATUS" />
			<input type="hidden" id="dia-dealerStarName" name="dia-dealerStarName" datasource="DEALER_STAR_NAME" />
			<div align="left" id="div-dealerInfo" style="overflow-y:auto;overflow-x:hidden;">
				<table class="editTable" id="dia-tab-dealerInfo" >
					<!-- hidden域 -->
					<tr>
						<td><label>渠道代码：</label></td>
					    <td><input type="text" id="dia-dealerCode" name="dia-dealerCode" datasource="DEALER_CODE" datatype="1,is_null,30" readOnly="true" /></td>
						<td><label>渠道名称：</label></td>
					    <td colspan="3"><input type="text" id="dia-dealerName" name="dia-dealerName" style="width:89.5%;" datasource="DEALER_NAME" datatype="1,is_null,200" readOnly="true" /></td>
					</tr>
					<tr>
						<td><label>渠道简称：</label></td>
					    <td><input type="text" id="dia-dealerSName" name="dia-dealerSName" datasource="DEALER_SHORTNAME" datatype="1,is_null,100" readonly /></td>
					    <td><label>渠道类型：</label></td>
					    <td><input type="text" id="dia-dealerType" name="dia-dealerType" datasource="DEALER_TYPE" datatype="1,is_null,30" readonly /></td>
					    <td><label>所属办事处：</label></td>
					    <td><input type="text" id="dia-dealerOffice" name="dia-dealerOffice" datasource="BELONG_OFFICE" datatype="1,is_null,30" readonly /></td>
					</tr>
					<tr >
						<td><label style="color:red;">渠道状态：</label></td>
					    <td>
					    	<select type="text" id="dia-dealerStatus" name="dia-dealerStatus" datasource="DEALER_STATUS" kind="dic" src="<%=DicConstant.ZZYWZT %>" datatype="0,is_null,30" >
					    		<option value="<%=DicConstant.ZZYWZT_01%>" selected>运营</option>
					    	</select>
					    </td>
					    <td><label style="color:red;">渠道星级：</label></td>
					    <td>
					    	<select type="text" id="dealerStar" name="dealerStar" kind="dic" src="T#user_para_configure:PARAKEY:PARANAME:1=1 AND apptype='2003'" datasource="DEALER_STAR" datatype="1,is_null,60" >
				    			<option value="-1">--</option>
				    		</select>
				    	</td>
				    	<td><label style="color:red;">超单天数：</label></td>
					    <td><input type="text" id="dia-days" name="dia-days" datasource="OVERDUE_DAYS" datatype="1,is_digit,10"  /></td>
					</tr>
					<tr >
						<td><label style="color:red;">与集中点距离：</label></td>
					    <td><input type="text" id="dia-focusMiles" name="dia-focusMiles" datasource="FOCUS_MILES" datatype="1,is_digit,10"  /></td>
					    <td><label style="color:red;">运费单价：</label></td>
					    <td><input type="text" id="dia-transPrice" name="dia-transPrice" datasource="TRANS_PRICE" datatype="1,is_money,10"  /></td>
				    	<td><label style="color:red;">是否自动审核：</label></td>
					    <td>
					    	<select type="text" id="dia-ifAutoCheck" name="dia-ifAutoCheck" kind="dic" src="<%=DicConstant.SF %>" datasource="IF_AUTO_CHECK" datatype="1,is_null,30"  >
					    		<option value="<%=DicConstant.SF_02%>" selected>否</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>企业性质：</label></td>
					    <td><input type="text" id="dia-enNature" name="dia-enNature" datasource="ENTERPRISE_NATURE" datatype="1,is_null,30"  /></td>
					    <td><label>法人名称：</label></td>
					    <td><input type="text" id="dia-lName" name="dia-lName" datasource="LEGAL_NAME" datatype="1,is_null,30"  /></td>
					    <td><label>法人电话：</label></td>
					    <td><input type="text" id="dia-lTel" name="dia-lTel" datasource="LEGAL_TEL" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>电子邮件：</label></td>
					    <td><input type="text" id="dia-email" name="dia-email" datasource="E_MAIL" datatype="1,is_null,30"  /></td>
					    <td><label>税务登记：</label></td>
					    <td colspan="3"><input type="text" id="dia-faxRegist" name="dia-faxRegist" style="width:89.5%;" datasource="FAX_REGIST" datatype="1,is_null,300"  /></td>
					</tr>
					<tr>
						<td><label>站长姓名：</label></td>
					    <td><input type="text" id="dia-staName" name="dia-staName" datasource="STATION_NAME" datatype="1,is_null,30"  /></td>
					    <td><label>站长电话：</label></td>
					    <td><input type="text" id="dia-staTel" name="dia-staTel" datasource="STATION_TEL" datatype="1,is_null,30"  /></td>
					    <td><label>值班电话：</label></td>
					    <td><input type="text" id="dia-duTel" name="dia-duTel" datasource="DUTY_TEL" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>传真：</label></td>
					    <td><input type="text" id="dia-staName" name="dia-staName" datasource="FAX" datatype="1,is_null,30"  /></td>
					    <td><label>省：</label></td>
					    <td><input type="text" id="dia-province" name="dia-province" kind="dic" src="XZQH" filtercode="\d{2}0000$" datasource="PROVINCE" datatype="1,is_null,30"  /></td>
					    <td><label>市县区：</label></td>
					    <td><input type="text" id="dia-city" name="dia-city" kind="dic" datasource="CITY" src="XZQH"  dicwidth="300" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>邮编：</label></td>
					    <td><input type="text" id="dia-zipCode" name="dia-zipCode" datasource="ZIP_CODE" datatype="1,is_digit_letter,30"  /></td>
					    <td><label>开户银行：</label></td>
					    <td ><input type="text" id="dia-openBank" name="dia-openBank" style="width:89.5%;" datasource="OPEN_BANK" datatype="1,is_null,300"  /></td>
					    <td><label>银行账户：</label></td>
					    <td><input type="text" id="dia-bankAccount" name="dia-bankAccount" datasource="BANK_ACCOUNT" datatype="1,is_digit_letter,30"  /></td>
					</tr>
					<tr>
						<td><label>税号：</label></td>
					    <td><input type="text" id="dia-tariff" name="dia-tariff" datasource="TARIFF" datatype="1,is_digit_letter,30"  /></td>
					    <td><label>财务姓名：</label></td>
					    <td><input type="text" id="dia-finStaff" name="dia-finStaff" datasource="FIN_STAFF" datatype="1,is_null,30"  /></td>
					    <td><label>财务电话：</label></td>
					    <td><input type="text" id="dia-finStaffTel" name="dia-finStaffTel" datasource="FIN_STAFF_TEL" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>陕汽业务负责人：</label></td>
					    <td><input type="text" id="dia-busPerson" name="dia-busPerson" datasource="BUS_PERSON" datatype="1,is_null,30"  /></td>
					    <td><label>负责人电话：</label></td>
					    <td><input type="text" id="dia-busPersonTes" name="dia-busPersonTes" datasource="BUS_PERSON_TEL" datatype="1,is_null,30"  /></td>
					    <td><label>信息员：</label></td>
					    <td><input type="text" id="dia-infoPerson" name="dia-infoPerson" datasource="INFO_PERSON" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>信息员电话：</label></td>
					    <td><input type="text" id="dia-busPersonTel" name="dia-busPersonTel" datasource="INFO_PERSON_TEL" datatype="1,is_null,30"  /></td>
					    <td><label>鉴定员：</label></td>
					    <td><input type="text" id="dia-surveyor" name="dia-surveyor" datasource="SURVEYOR" datatype="1,is_null,30"  /></td>
					    <td><label>鉴定员电话：</label></td>
					    <td><input type="text" id="dia-surveyorTel" name="dia-surveyorTel" datasource="SURVEYOR_TEL" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>上级主管单位：</label></td>
					    <td><input type="text" id="dia-hCompany" name="dia-hCompany" datasource="HIGHER_COMPANY" datatype="1,is_null,60"  /></td>
					    <td><label>职工总数：</label></td>
					    <td><input type="text" id="dia-staffCounts" name="dia-staffCounts" datasource="STAFF_COUNTS" datatype="1,is_digit,10"  /></td>
					    <td><label>技术人员数：</label></td>
					    <td><input type="text" id="dia-tecCounts" name="dia-tecCounts" datasource="TEC_COUNTS" datatype="1,is_digit,10"  /></td>
					</tr>
					<tr>
						<td><label>管理人员数：</label></td>
					    <td><input type="text" id="dia-manCounts" name="dia-manCounts" datasource="MAN_COUNTS" datatype="1,is_digit,10"  /></td>
					    <td><label>专业修理人员数：</label></td>
					    <td><input type="text" id="dia-reCounts" name="dia-reCounts" datasource="REPAIR_COUNTS" datatype="1,is_digit,10"  /></td>
					    <td><label>检验人员数：</label></td>
					    <td><input type="text" id="dia-inCounts" name="dia-inCounts" datasource="INSPECT_COUNTS" datatype="1,is_digit,10"  /></td>
					</tr>
					<tr>
						<td><label>成立日期：</label></td>
					    <td><input type="text" id="dia-setupTime" name="dia-setupTime" kind="date" onclick="WdatePicker()" datasource="SETUP_TIME" datatype="1,is_null,30"  /></td>
					    <td><label>详细地址：</label></td>
					    <td colspan="3"><input type="text" id="dia-address" name="dia-address" style="width:87%;" datasource="ADDRESS" datatype="0,is_null,300"  /></td>
					</tr>
					<tr>
						<td><label>注册资金：</label></td>
					    <td><input type="text" id="dia-regFunds" name="dia-regFunds" datasource="REGIST_FUNDS" datatype="1,is_money,30"  /></td>
						<td><label>经营范围：</label></td>
					    <td colspan="3"><input type="text" id="dia-scopeBus" name="dia-scopeBus" style="width:89.5%;" datasource="SCOPE_BUS" datatype="1,is_null,1000"  /></td>
					</tr>
					<tr>
						<td><label style="color:red;">主修车型：</label></td>
					    <td colspan="5"><input type="text" id="dia-maiModels" name="dia-maiModels" style="width:93.5%;" datasource="MAJOR_MODELS" datatype="1,is_null,300"  /></td>
					</tr>
					<tr>
						<td><label>全厂面积：</label></td>
					    <td><input type="text" id="dia-allArea" name="dia-allArea" datasource="ALL_AREA" datatype="1,is_null,30"  /></td>
					    <td><label>厂房面积：</label></td>
					    <td><input type="text" id="dia-workshopArea" name="dia-workshopArea" datasource="WORKSHOP_AREA" datatype="1,is_null,30"  /></td>
					    <td><label>库房面积：</label></td>
					    <td><input type="text" id="dia-warehouseArea" name="dia-warehouseArea" datasource="WAREHOUSE_AREA" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>停车厂面积：</label></td>
					    <td><input type="text" id="dia-parkArea" name="dia-parkArea" datasource="PARKING_AREA" datatype="1,is_null,30"  /></td>
					    <td><label>现是何厂家：</label></td>
					    <td colspan="3"><input type="text" id="dia-nowFactory" name="dia-nowFactory" style="width:89.5%;" datasource="NOW_FACTORY" datatype="1,is_null,300"  /></td>
					</tr>
					<tr>
						<td><label>是否潍柴站：</label></td>
					    <td>
					    	<select type="text" id="dia-ifWc" name="dia-ifWc" kind="dic" src="<%=DicConstant.SF %>" datasource="IF_WC" datatype="1,is_null,30"  >
					    		<option value="<%=DicConstant.SF_02%>" selected>否</option>
					    	</select>
					    </td>
					    <td><label>加入潍柴日期：</label></td>
					    <td><input type="text" id="dia-joinWcTime" name="dia-joinWcTime" kind="date" onclick="WdatePicker()" datasource="JIONWC_TIME" datatype="1,is_null,30"  /></td>
					    <td><label>潍柴编号：</label></td>
					    <td><input type="text" id="dia-wcNo" name="dia-wcNo" datasource="WC_NO" datatype="1,is_digit_letter,30"  /></td>
					</tr>
					<tr>
						<td><label>是否法士特站：</label></td>
					    <td>
					    	<select type="text" id="dia-ifFst" name="dia-ifFst" kind="dic" src="<%=DicConstant.SF %>" datasource="IF_FST" datatype="1,is_null,30"  >
					    		<option value="<%=DicConstant.SF_02%>" selected>否</option>
					    	</select>
					    </td>
					    <td><label>加入法士特日期：</label></td>
					    <td><input type="text" id="dia-joinFstTime" name="dia-joinFstTime" kind="date" onclick="WdatePicker()" datasource="JIONFST_TIME" datatype="1,is_null,30"  /></td>
					    <td><label>法士特编号：</label></td>
					    <td><input type="text" id="dia-fstNo" name="dia-fstNo" datasource="FST_NO" datatype="1,is_digit_letter,30"  /></td>
					</tr>
					<tr>
						<td><label>是否康明斯站：</label></td>
					    <td>
					    	<select type="text" id="dia-ifKms" name="dia-ifKms" kind="dic" src="<%=DicConstant.SF %>" datasource="IF_KMS" datatype="1,is_null,30"  >
					    		<option value="<%=DicConstant.SF_02%>" selected>否</option>
					    	</select>
					    </td>
					    <td><label>加入康明斯日期：</label></td>
					    <td><input type="text" id="dia-joinKmsTime" name="dia-joinKmsTime" kind="date" onclick="WdatePicker()" datasource="JIONKMS_TIME" datatype="1,is_null,30"  /></td>
					    <td><label>康明斯编号：</label></td>
					    <td><input type="text" id="dia-kmsNo" name="dia-kmsNo" datasource="KMS_NO" datatype="1,is_digit_letter,30"  /></td>
					</tr>
					<tr>
						<td><label>加入陕重汽日期：</label></td>
					    <td><input type="text" id="dia-joinSzqTime" name="dia-joinSzqTime" kind="date" onclick="WdatePicker()" datasource="JIONSZQ_TIME" datatype="1,is_null,30"  /></td>
					    <td><label>系统管理员：</label></td>
					    <td><input type="text" id="dia-sysManager" name="dia-sysManager" datasource="SYSTEM_MANAGER" datatype="1,is_null,30"  /></td>
					    <td><label>配件管理员：</label></td>
					    <td><input type="text" id="dia-partManager" name="dia-partManager" datasource="PART_MANAGER" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>办事处主任名称：</label></td>
					    <td><input type="text" id="dia-officeName" name="dia-officeName" datasource="OFFICER_NAME" datatype="1,is_null,30"  /></td>
					    <td><label>联系方式：</label></td>
					    <td><input type="text" id="dia-officeTel" name="dia-officeTel" datasource="OFFICER_TEL" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>服务经理名称：</label></td>
					    <td><input type="text" id="dia-seManager" name="dia-seManager" datasource="SE_MANAGEER" datatype="1,is_null,30"  /></td>
					    <td><label>服务助理名称：</label></td>
					    <td><input type="text" id="dia-seAssistant" name="dia-seAssistant" datasource="SE_ASSISTANT" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>总部计划员：</label></td>
					    <td><input type="text" id="dia-planner" name="dia-planner" datasource="PLANER" datatype="1,is_null,30"  /></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
						<td colspan="5"><textarea type="text" id="dia-remarks" name="dia-remarks" style="width:93.5%;height:30px;overflow:auto;" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
					</tr>
				</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-diaSave">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>	
<script type="text/javascript">
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/sysmng/dealermng/DealerMngAction";

//初始化
$(function(){
	$("#div-dealerInfo").height(document.documentElement.clientHeight -90);
	//修改操作
	if(action != "1")
	{
		var selectedRows = $("#tab-dealerList").getSelectedRows();
		setEditValue("dia-fm-dealerInfo",selectedRows[0].attr("rowdata"));
		$("#dia-code").attr("readonly",true);
	}
	$("#btn-diaSave").bind("click", function(event){
		doDiaSave();
	});
});


//保存
function doDiaSave()
{
	var $f = $("#dia-fm-dealerInfo");
	if (submitForm($f) == false) return false;
	var sCondition = {};
		sCondition = $("#dia-fm-dealerInfo").combined(1) || {};
	var url = diaSaveAction + "/update.ajax";
	doNormalSubmit($f,url,"btn-diaSave",sCondition,diaUpdateCallBack);
}

//回调函数
function diaUpdateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-dealerList").getSelectedIndex();
		$("#tab-dealerList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	
	return true;
}
</script>