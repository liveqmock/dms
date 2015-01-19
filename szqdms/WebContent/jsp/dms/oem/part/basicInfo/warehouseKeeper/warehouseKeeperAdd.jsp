<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-warehouseKeeper" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="diaU-keeperId" name="diaU-keeperId" datasource="KEEPER_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-warehouseKeeperInfo">
				<tr>
					<td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="diaU-partName"  name="diaU-partName" datasource="PART_NAME" datatype="1,is_null,300" />
				    	<input type="hidden" id="diaU-partId"  name="diaU-partId" datasource="PART_ID" />
				    	<input type="hidden" id="diaU-partCode"  name="diaU-partCode" datasource="PART_CODE" />
				    	<input type="hidden" id="diaU-warehouseId"  name="diaU-warehouseId" datasource="WAREHOUSE_ID" />
				    	<input type="hidden" id="diaU-warehouseCode"  name="diaU-warehouseCode" datasource="WAREHOUSE_CODE" />
				    	<input type="hidden" id="diaU-warehouseName"  name="diaU-warehouseName" datasource="WAREHOUSE_NAME" />
				    </td>
				    <td><label>原库管员：</label></td>
				    <td>
				    	<input type="text" id="diaU-oldUserName"  name="diaU-oldUserName" datasource="OLD_USER_NAME" />
				    </td>
				</tr>
				<tr>
					<td><label>有效标识：</label></td>
				    <td>
				    	<input type="text" id="diaU-status"  name="diaU-status" datasource="STATUS" datatype="1,is_null,6" readonly/>
				    </td>
					<td><label>新库管员：</label></td>
				    <td>
				    	<input type="text" id="diaU-userAccount"  name="diaU-userAccount" kind="dic" 
				    		src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000063' AND STATUS='100201'" 
				    		datasource="USER_ACCOUNT" datatype="0,is_null,32" />
				    	<input type="hidden" id="diaU-userName"  name="diaU-userName" datasource="USER_NAME" />
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
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction";
var diaAction = "<%=action%>";
$(function(){ 
	if(diaAction != 1)	//新增初始化
	{
		//修改初始化
		var selectedRows = $("#tab-warehouseKeeperList").getSelectedRows();
		setEditValue("fm-warehouseKeeper",selectedRows[0].attr("rowdata"));
		
		//字典表回显
		var rowdata=selectedRows[0].attr("rowdata");
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		//配件名称回显		
		var userName = getNodeValue(objXML, "USER_NAME", 0);
		$("#diaU-oldUserName").val(userName);
		
		//配件名称、原采购员名称文本框只读		
		$("#diaU-partName").attr("readonly",true);
		$("#diaU-oldUserName").attr("readonly",true);
		$("#diaU-status").attr("disabled",false);
		$("#diaU-userAccount").val("");
	}
	
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-warehouseKeeper");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-warehouseKeeper").combined(1) || {};
		
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		
	});
});

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
		var selectedIndex = $("#tab-warehouseKeeperList").getSelectedIndex();
		$("#tab-warehouseKeeperList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("diaU-userAccount") == 0)
	{    
	   	$("#diaU-userName").val($("#"+id).val());
	}
	
	return true;
}
</script>