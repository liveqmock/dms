<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-product" class="editForm" style="width: 100%;">
			<input type="hidden" id="dia-vehicleProId" datasource="VEHICLE_PRO_ID"/>
			<div id="dia-vehicle">
			<table width="100%" id="dia-productTable" name="dia-productTable" class="editTable" >
                 <tr>
					<td><label>VIN开始：</label></td>
					<td><input type="text" id="dia-vinStart" name="dia-vinStart" value="" datasource="VIN_START" /></td>
					<td><label>VIN结束：</label></td>
					<td><input type="text" id="dia-vinEnd" name="dia-vinEnd" value="" datasource="VIN_END"/></td>
				</tr>
				<tr>
					<td><label>生产单位：</label></td>
					<td><input type="text" id="dia-vehicleProduct" name="dia-vehicleProduct" value="" datasource="VEHICLE_PRODUCT" /></td>
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
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction";
var action = "<%=action%>";
$(function(){
	if(action != "1"){
		var selectedRows = $("#vehicleList").getSelectedRows();
		setEditValue("dia-product",selectedRows[0].attr("rowdata"));
	}
	
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	
	$("#dia-save").bind("click",function(){
		var $fm = $("#dia-product");
		if (submitForm($fm) == false) {
			return false;
		}
		var sCondition = {};
		sCondition = $fm.combined(1) || {};
		
		var vehicleProId=$("#dia-vehicleProId").val();
		if(vehicleProId == '')	//插入动作
		{
			var insertUrl = diaSaveAction + "/productInsert.ajax";
			doNormalSubmit($fm,insertUrl,"dia-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/productUpdate.ajax";
			doNormalSubmit($fm,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
		
	});
});
//修改回调
function diaUpdateCallBack(res){
	try
	{
		var selectedIndex = $("#vehicleList").getSelectedIndex();
		$("#vehicleList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//新增回调
function diaInsertCallBack(res){
	try{
		$("#vehicleList").insertResult(res,0);
		$("td input[type=radio]",parent.$("#vehicleList_content").find("tr").eq(0)).attr("checked",true);
		if(parent.$("#vehicleList_content").size()>0){
			$("td input[type=radio]",parent.$("#vehicleList_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#vehicleList").find("tr").eq(0)).attr("checked",true);
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>

