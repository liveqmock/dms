<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-civilVehicleSaleDateInfo" class="editForm" >
			<!-- 隐藏域 -->
		    <input type="hidden" id="diaOem-vehicleId" name="diaOem-vehicleId" datasource="VEHICLE_ID" />
		    <input type="hidden" id="diaOem-userType" name="diaOem-userType" datasource="USER_TYPE" />
		    <input type="hidden" id="diaOem-oldBuyDate" name="diaOem-oldBuyDate" datasource="OLD_BUY_DATE" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-civilVehicleSaleDateInfo">
				<tr>
					<td><label>VIN：</label></td>
					<td><input type="text" id="diaOem-vin" name="diaOem-vin" datasource="VIN" datatype="0,is_digit_letter,17" /></td>
			   		<td><label>车型：</label></td>
					<td><input type="text" id="diaOem-modelsCode" name="diaOem-modelsCode" datasource="MODELS_CODE" datatype="0,is_null,30"  /></td>
			    </tr>
			    <tr>
			   		<td><label>销售日期：</label></td>
					<td>
						<input type="text" id="diaOem-buyDate" name="diaOem-buyDate" style="width:75px;" datasource="BUY_DATE" datatype="0,is_null,30" onclick="WdatePicker()"  kind="date"/>
					</td>	   
			    </tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">


var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/VehicleSaleDateMngAction";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-civilVehicleSaleDateInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-civilVehicleSaleDateInfo").combined(1) || {};
		
		var updateUrl = diaSaveAction + "/update.ajax";
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
	});
		//初始化
		var selectedRows = $("#tab-civilVehicleSaleDateList").getSelectedRows();
		setEditValue("fm-civilVehicleSaleDateInfo",selectedRows[0].attr("rowdata"));
		
		var buyDate=selectedRows[0].attr("BUY_DATE_SV");//获取原日期相应格式
		$("#diaOem-oldBuyDate").val(buyDate);
				
		//设置置灰值
		$("#diaOem-vin").attr("readonly",true);
		$("#diaOem-modelsCode").attr("readonly",true);		
});

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-civilVehicleSaleDateList").getSelectedIndex();
		$("#tab-civilVehicleSaleDateList").updateResult(res,selectedIndex);
		//关闭当前窗口
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>