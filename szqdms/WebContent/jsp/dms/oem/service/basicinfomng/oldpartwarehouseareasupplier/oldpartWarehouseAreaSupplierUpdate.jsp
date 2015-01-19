<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
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
	<form method="post" id="fm-oldpartWarehouseAreaSupplier" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-relationId" name="dia-relationId" datasource="RELATION_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-oldpartWarehouseAreaSupplierInfo">
				<tr>
					<td><label>供应商代码：</label></td>
				    <td>
				    	<input type="text" id="dia-supplierCode"  name="dia-supplierCode" datasource="SUPPLIER_CODE" datatype="0,is_null,30" />
				    	<input type="hidden" id="dia-supplierId"  name="dia-supplierId" datasource="SUPPLIER_ID" />
				    </td>
				</tr>
			    <tr>
				    <td><label>供应商名称：</label></td>
				    <td>
				    	<input type="text" id="dia-supplierName"  name="dia-supplierName" datasource="SUPPLIER_NAME" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
					<td><label>旧件库区名称：</label></td>
					<td><input type="text" class="combox" id="dia-areaCode" name="dia-areaCode" datasource="AREA_CODE" operation="like" kind="dic" src="T#SE_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID}:STATUS=100201" datatype="0,is_null,30" />
						<input type="hidden" id="dia-areaId" name="dia-areaId" datasource="AREA_ID"/>
						<input type="hidden" id="dia-areaName" name="dia-areaName" datasource="AREA_NAME"/>	
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

var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaSupplierMngAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction != 1)	//新增初始化
	{
		//修改初始化
		var selectedRows = $("#tab-oldpartWarehouseAreaSupplierList").getSelectedRows();
		setEditValue("fm-oldpartWarehouseAreaSupplier",selectedRows[0].attr("rowdata"));
				
		$("#dia-supplierCode").attr("readonly",true);
		$("#dia-supplierName").attr("readonly",true);
		
		//将字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		//把车辆部位的名称赋给车辆代码表示项		
		var areaCode=getNodeValue(objXML, "AREA_CODE", 0);
		var areaName=getNodeValue(objXML, "AREA_NAME", 0);
		$("#dia-areaCode").val(areaName);
		$("#dia-areaCode").attr("code",areaCode);
	}
	
})

$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-oldpartWarehouseAreaSupplier");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-oldpartWarehouseAreaSupplier").combined(1) || {};
		
		var updateUrl = diaSaveAction + "/update.ajax";
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);		
	});
});

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
		//var selectedIndex = $("#tab-oldpartWarehouseAreaSupplierList").getSelectedIndex();
		//$("#tab-oldpartWarehouseAreaSupplierList").updateResult(res,selectedIndex);
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
		
		//清空val0的内容
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   
	//给旧件库区隐藏域赋值
	if(id=="dia-areaCode") 
	{
		$("#dia-areaName").val($("#"+id).val());
		$("#dia-areaId").val($row.attr("AREA_ID"));
	}
	return true;
}
</script>