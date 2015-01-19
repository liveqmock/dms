<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>定保单新增</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $checkHisTrue=true;
var $fileTrue=true;
var authorStatus=null;
var action = "<%=action%>";
var dia_dialog = parent.$("body").data("preAuthApply");
var diaSaveAction = "<%=request.getContextPath()%>/service/milesPolicy/MilesPolicyReportAction";
var itemSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/itemSearch.ajax";
var fileSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/fileSearch.ajax";
$(function() 
{
	if(action != "1"){
		var selectedRows = parent.$("#milesPolicylist").getSelectedRows();
		setEditValue("dia-fm-milesPolicyReport",selectedRows[0].attr("rowdata"));
		$("#dia-vin").attr("readonly",true);
		$("#dia-engine_no").attr("readonly",true);
	}else{
		$("#dia-report_li").hide();
	}
	//保存
	$("#dia-save").bind("click",function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-milesPolicyReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-milesPolicyReport").combined(1) || {};
		if(action == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/milesPolicyInsert.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/milesPolicyUpdate.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//提报
	$("#dia-report").bind("click",function(){
		var gId=$("#GID").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-milesPolicyReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-milesPolicyReport").combined(1) || {};
		var reportUrl = diaSaveAction + "/milesPolicyReport.ajax?gId="+gId+"&flag="+2;
		doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	});
	//验证
	$("#dia-checkvin").bind("click",function(){
		var vinVal=$("#dia-vin").val();
		var engineVal=$("#dia-engine_no").val(); 
		if(vinVal==''){
			alertMsg.info("VIN不能为空！");
			return;
		}
		if(engineVal==''){
			alertMsg.info("发动机号不能为空！");
			return;
		}
		var options = {max : true,mask : true,mixable : true,minable : true,resizable : true,drawable : true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/milesPolicy/milesPolicyVinCheck.jsp?vinVal='"+vinVal+"'&engineVal='"+engineVal+"'","vinCheck", "定保单信息维护-校验车辆", options);
	});
	//重新填写
	$("#dia-recheckvin").bind("click",function(){
		alertMsg.confirm("清空车辆信息，确认重新填写?",{okCall:doConOk1,cancelCall:doConOk2});
	});
	//设置高度
	$("#preAuthBasic").height(document.documentElement.clientHeight-30);
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("milesPolicyReport");
		return false;
	});
});
//验证VIN回调
function checkVinCallBack(flag)
{
	if(flag==1){
		$("#dia-vin").attr("readOnly",true);
		$("#dia-vin").addClass("readonly");
		$("#dia-engine_no").attr("readOnly",true);
		$("#dia-engine_no").addClass("readonly");
		$("#dia-vin").val($("#dia-di-vin").val());//vin
		$("#dia-engine_no").val($("#dia-di-engine_no").val());//发动机号
		$("#vehicleId").val($("#dia-di-vehicleId").val());//车辆ID
		$("#modelsId").val($("#dia-di-modelsId").val());//车型ID
		$("#dia-models_code").val($("#dia-di-models_code").val());//车型代码
		$("#dia-certificate").val($("#dia-di-certificate").val());//合格证
		$("#dia-engine_type").val($("#dia-di-engine_type").val());//发动机型号
		$("#dia-user_type").val($("#dia-di-user_type").val());//用户类型
		$("#dia-user_type_code").val($("#dia-di-user_type_code").val());//用户类型
		$("#dia-vehicle_use").val($("#dia-di-vehicle_use").val());//车辆用途
		$("#dia-vehicle_use_code").val($("#dia-di-vehicle_use_code").val());//车辆用途
		$("#dia-drive_form").val($("#dia-di-drive_form").val());//驱动形式
		$("#dia-buy_date").val($("#dia-di-buy_date").val());//购车日期
		$("#dia-mileage").val($("#dia-di-mileage").val());//行驶里程
		$("#dia-guarantee_no").val($("#dia-di-guarantee_no").val());//保修卡
		$("#dia-factory_date").val($("#dia-di-factory_date").val());//出厂日期
		$("#dia-maintenance_date").val($("#dia-di-maintenance_date").val());//首保日期
		$("#dia-license_plate").val($("#dia-di-license_plate").val());//车牌号
		$("#dia-user_name").val($("#dia-di-user_name").val());//用户名称
		$("#dia-user_no").val($("#dia-di-user_no").val());//身份证
		$("#dia-link_man").val($("#dia-di-link_man").val());//联系人
		$("#dia-phone").val($("#dia-di-phone").val());//电话
		$("#dia-user_address").val($("#dia-di-user_address").val());//地址
	}
	if(flag==2){
		vehEmpty();
		alertMsg.info("VIN与发动机号不存在！");
	}
	return true;
}
//confirm返回true
function doConOk1(){
	$("#dia-vin").removeClass("readonly");
	$("#dia-engine_no").removeClass("readonly");
	$("#dia-vin").attr("readOnly",false);
	$("#dia-engine_no").attr("readOnly",false);
	$("#dia-vin").val("");
	$("#dia-engine_no").val("");
	vehEmpty();
}
//车辆信息设置为空
function vehEmpty(){
	$("#vehicleId").val("");//车辆ID
	$("#modelsId").val("");//车型ID
	$("#dia-models_code").val("");//车型代码
	$("#dia-certificate").val("");//合格证
	$("#dia-engine_type").val("");//发动机型号
	$("#dia-user_type").val("");//用户类型
	$("#dia-user_type_code").val("");//用户类型
	$("#dia-vehicle_use").val("");//车辆用途
	$("#dia-vehicle_use_code").val("");//车辆用途
	$("#dia-drive_form").val("");//驱动形式
	$("#dia-buy_date").val("");//购车日期
	$("#dia-mileage").val("");//行驶里程
	$("#dia-guarantee_no").val("");//保修卡
	$("#dia-factory_date").val("");//出厂日期
	$("#dia-maintenance_date").val("");//首保日期
	$("#dia-license_plate").val("");//车牌号
	$("#dia-user_name").val("");//用户名称
	$("#dia-user_no").val("");//身份证
	$("#dia-link_man").val("");//联系人
	$("#dia-phone").val("");//电话
	$("#dia-user_address").text("");//地址
	$("#dia-vin").val($("#dia-di-vin").val());
	$("#dia-engine_no").val($("#dia-di-engine_no").val());
}
// confirm返回true  
function doConOk2(){
	return false;
}
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		var gId =getNodeText(rows[0].getElementsByTagName("G_ID").item(0));
		parent.$("#milesPolicylist").insertResult(res,0);
		if(parent.$("#milesPolicylist_content").size()>0){
			$("td input[type=radio]",parent.$("#milesPolicylist_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#milesPolicylist").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		$("#GID").val(gId);
		$("#dia-report_li").show();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{	
		var selectedIndex = parent.$("#milesPolicylist").getSelectedIndex();
		parent.$("#milesPolicylist").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//提报回调方法
function diaReportCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $row =  parent.$("#milesPolicylist").getSelectedRows();
			if($row[0]){
				parent.$("#milesPolicylist").removeResult($row[0]);
				parent.$.pdialog.close("milesPolicyReport");
			}
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
<div id="dia-layout">
		<div class="page" id="preAuthBasic" style="overflow:auto;">
			<div class="pageContent">
				<form method="post" id="dia-fm-milesPolicyReport" class="editForm" style="width: 99%;">
					<input type="hidden" id="GID" datasource="G_ID"/>
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="modelsId" datasource="MODELS_ID"/>
           		    <input type="hidden" id="dia-user_type_code" name="dia-user_type_code" datasource="USER_TYPE" value="" readonly="readonly" />	
					<input type="hidden" id="dia-vehicle_use_code" name="dia-vehicle_use_code"  datasource="VEHICLE_USE" value="" readonly="readonly" />
					<div align="left">
						<fieldset>
						<legend align="right">
							<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;定保单基本信息编辑&gt;&gt;</a>
						</legend>
						<table class="editTable" id="dia-tab-preAuthBasic">
							<tr>
								<td><label>VIN：</label></td>
								<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN" datatype="0,is_vin,17" value="" operation="like" title="(请输入后8位或者17位)"/></td>
								<td><label>发动机号：</label></td>
								<td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO" datatype="0,is_fdjh,30" value="" operation="like" /></td>
								<td><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin" >验&nbsp;&nbsp;证</button></div></div>
								<div class="button"><div class="buttonContent"><button type="button" id="dia-recheckvin" >重新填写</button></div></div></td>
							</tr>
							<tr>
								<td><label>车辆型号：</label></td>
								<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly" /></td>
								<td><label>发动机型号：</label></td>
								<td><input type="text" id="dia-engine_type" name="dia-engine_type" datasource="ENGINE_TYPE" value="" readonly="readonly" /></td>
								<td><label>用户类型：</label></td>
								<td><input type="text" id="dia-user_type" name="dia-user_type" datasource="USERNAME" value="" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>车辆用途：</label></td>
								<td><input type="text" id="dia-vehicle_use" name="dia-vehicle_use"  datasource="VEHICLENAME" value="" readonly="readonly" /></td>
								<td><label>驱动形式：</label></td>
								<td><input type="text" id="dia-drive_form" name="dia-drive_form" datasource="DRIVENAME" value="" readonly="readonly" /></td>
								<td><label>购车日期：</label></td>
								<td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
							</tr>
							<tr >
								<td><label>行驶里程：</label></td>
								<td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value="" datatype="0,is_digit,10" /></td>
								<td><label>车牌号码：</label></td>
								<td><input type="text" id="dia-license_plate" name="dia-license_plate" datasource="LICENSE_PLATE" value="" datatype="0,is_carno,30" /></td>
								<td><label>用户名称：</label></td>
								<td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value="" datatype="0,is_null,300" /></td>
							</tr>
							<tr>
								<td><label>身份证号：</label></td>
								<td><input type="text" id="dia-user_no" name="dia-user_no"  datasource="USER_NO" value="" datatype="0,is_idcard,30" /></td>
								<td><label>联系人：</label></td>
								<td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value="" datatype="0,is_null,30" /></td>
								<td><label>电话：</label></td>
								<td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value="" datatype="0,is_digit,11" /></td>
							</tr>	
							<tr>
								<td><label>用户地址：</label></td>
								<td colspan="7"><textarea id="dia-user_address" style="width: 450px; height: 30px;" name="dia-user_address" datasource="USER_ADDRESS" datatype="1,is_null,100"></textarea></td>
							</tr>
							<tr>
								<td><label>备注：</label></td>
								<td colspan="7"><textarea id="dia-ramarks" style="width: 450px; height: 50px;" name="dia-ramarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
							</tr>
						</table>
						</fieldset>
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li id="dia-save-li"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="dia-report_li"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
</div>
</body>
</html>