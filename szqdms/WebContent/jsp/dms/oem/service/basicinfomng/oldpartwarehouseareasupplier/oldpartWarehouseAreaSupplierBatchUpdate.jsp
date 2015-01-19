<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String relationIds = request.getParameter("relationIds");
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fmBU-oldpartWarehouseAreaSupplier" class="editForm" >
	    <!-- 隐藏域
		<input type="hidden" id="diaBU-keeperId" name="dia-keeperId" value="<%=relationIds%>" />
		 -->
		<div align="left">
			<fieldset>
			<table class="editTable" id="tabBU-oldpartWarehouseAreaSupplierInfo">
				<tr>
				    <td><label>旧件库区名称：</label></td>
				    <td><input type="text" class="combox" id="diaBU-areaCode" name="diaBU-areaCode" datasource="AREA_CODE" operation="like" kind="dic" src="T#SE_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID}:STATUS=100201" datatype="0,is_null,30" />
					    <input type="hidden" id="diaBU-areaId" name="diaBU-areaId" datasource="AREA_ID"/>
					    <input type="hidden" id="diaBU-areaName" name="diaBU-areaName" datasource="AREA_NAME"/>	
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
var relationIds = "<%=relationIds%>";

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fmBU-oldpartWarehouseAreaSupplier");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#fmBU-oldpartWarehouseAreaSupplier").combined(1) || {};
		var updateUrl = diaSaveAction + "/batchUpdate.ajax?relationIds="+relationIds;
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
	});
});

//批量更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
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
	if(id=="diaBU-areaCode") 
	{
		$("#diaBU-areaName").val($("#"+id).val());
		$("#diaBU-areaId").val($row.attr("AREA_ID"));
	}
	return true;
}
</script>