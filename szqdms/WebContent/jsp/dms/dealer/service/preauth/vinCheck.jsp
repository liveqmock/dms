<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.action.service.common.MarkedWords"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="vinCh" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-vinCh" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="di_tab_vinCh">
				<input type="hidden" id="dia-di-modelsId" name="dia-di-modelsId" datasource="MODELS_ID"/>
				<input type="hidden" id="dia-di-vehicleId" name="dia-di-vehicleId" datasource="VEHICLE_ID"/>
				<input type="hidden" id="dia-di-blackListFlag" name="dia-di-blackListFlag" datasource="BLACKLISTFLAG"/>
				<tr>
					<td><label>VIN：</label></td>
					<td><input type="text" id="dia-di-vin" name="dia-di-vin" datasource="VIN"  readonly="readonly"/></td>
					<td><label>发动机号：</label></td>
					<td><input type="text" id="dia-di-engine_no" name="dia-di-engine_no" datasource="ENGINE_NO"  readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>车辆型号：</label></td>
					<td><input type="text" id="dia-di-models_code" name="dia-di-models_code" datasource="MODELS_CODE"  readonly="readonly" /></td>
					<!-- <td><label>合格证号：</label></td>
					<td><input type="text" id="dia-di-certificate" name="dia-di-certificate"  datasource="CERTIFICATE" value=""readonly="readonly" /></td> -->
					<td><label>发动机型号：</label></td>
					<td><input type="text" id="dia-di-engine_type" name="dia-di-engine_type" datasource="ENGINE_TYPE" readonly="readonly" value=""/></td>
					<td><label>首保日期：</label></td>
					<td><input type="text" id="dia-di-maintenance_date" name="dia-di-maintenance_date" datasource="MAINTENANCE_DATE" value=""  readonly="readonly" /></td>
				</tr>
				<tr>
				<td>
					<label>用户类型：</label></td>
					<td><input type="text" id="dia-di-user_type" name="dia-di-user_type" datasource="USER_TYPE" value="" readonly="readonly" /></td>
					<td><label>车辆用途：</label></td>
					<td><input type="text" id="dia-di-vehicle_use" name="dia-di-vehicle_use" datasource="VEHICLE_USE" value="" readonly="readonly" /></td>
					<td><label>驱动形式：</label></td>
					<td><input type="text" id="dia-di-drive_form" name="dia-di-drive_form" datasource="DRIVE_FORM" value="" readonly="readonly" /></td>
				</tr>
				<tr >
					<td><label>购车日期：</label></td>
					<td><input type="text" id="dia-di-buy_date" name="dia-di-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
					<td><label>行驶里程：</label></td>
					<td><input type="text" id="dia-di-mileage" name="dia-di-mileage"  datasource="MILEAGE" value="" readonly="readonly"/></td>
					<td><label>保修卡号：</label></td>
					<td><input type="text" id="dia-di-guarantee_no" name="dia-di-guarantee_no" datasourc="GUARANTEE_NO" value=""  readonly="readonly" /> </td>
				</tr>
				<!-- <tr >
					<td><label>出厂日期：</label></td>
					<td><input type="text" id="dia-di-factory_date" name="dia-di-factory_date" datasource="FACTORY_DATE" value=""  readonly="readonly" /></td>
				</tr> -->
				<tr>
					<td><label>车牌号码：</label></td>
					<td><input type="text" id="dia-di-license_plate" name="dia-di-license_plate" datasource="LICENSE_PLATE" value="" readonly="readonly"/></td>
					<td><label>用户名称：</label></td>
					<td><input type="text" id="dia-di-user_name" name="dia-di-user_name" datasource="USER_NAME" value="" readonly="readonly" /></td>
					<td><label>身份证号：</label></td>
					<td><input type="text" id="dia-di-user_no" name="dia-di-user_no"  datasource="USER_NO" value="" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>联系人：</label></td>
					<td><input type="text" id="dia-di-link_man" name="dia-di-link_man" datasource="LINK_MAN" value="" readonly="readonly"/></td>
					<td><label>电话：</label></td>
					<td><input type="text" id="dia-di-phone" name="dia-di-phone" datasource="PHONE" value="" readonly="readonly" /></td>
					<td><label>用户地址：</label></td>
					<td><textarea id="dia-di-user_address" style="width: 150px; height: 30px;" name="dia-di-user_address" datasource="USER_ADDRESS" readonly="readonly"></textarea></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="enter">确&nbsp;&nbsp;定</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
//弹出窗体
var dia_di_dialog =$("body").data("vinCheck");
var vinChUrl="<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/vinCheckSearch.ajax";
$(function()
{
	var diVinVal=$("#dia-vin").val();
	var diEngineNoVal=$("#dia-engine_no").val();
	var url = vinChUrl +"?diVinVal="+diVinVal+"&diEngineNoVal="+diEngineNoVal+"";
	sendPost(url,"","",searchCallBack,"false");
	
	$("#enter").bind("click",function(){
		var blackFlag=$("#dia-di-blackListFlag").val();
		//vin是黑名单的，不能提报
		if(blackFlag!=<%=DicConstant.SF_01%>){
			checkVinCallBack(1);
		}else{
			alertMsg.warn("<%=MarkedWords.markedWord_01%>");
			doConOk1();
		}
		$.pdialog.close(dia_di_dialog);
		return false;
	});
	//关闭当前页面
	$("button.close").click(function() 
	{
		$.pdialog.close(dia_di_dialog);
		return false;
	});
});
function searchCallBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			
			var objxml = res.documentElement;
			setEditValue("di_tab_vinCh",objxml);
		}else
		{
			checkVinCallBack(2);
			$.pdialog.close(dia_di_dialog);
			return false;
		}	
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
</script>