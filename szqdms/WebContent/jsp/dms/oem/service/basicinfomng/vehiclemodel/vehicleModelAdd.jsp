<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="wcxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="dia-tb-option">
				<input type="hidden" id="dia-models_Id" name="dia-models_Id" datasource="MODELS_ID" />
				<input type="hidden" id="dia-status" name="dia-STATUS" datasource="STATUS" value="100201" />			
					<tr>
						<td><label>车型代码：</label></td>
						<td><input type="text" id="dia-modelCode" name="dia-modelCode"  datasource="MODELS_CODE" datatype="0,is_null,30"  /></td>
				   		<td><label>车型名称：</label></td>
						<td><input type="text" id="dia-modelName" name="dia-modelName" datasource="MODELS_NAME" datatype="0,is_null,30"  value="" /></td>
				    </tr>	
				</table>
				</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button"  id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/VehicleModelMngAction";
var diaAction = "<%=action%>";
//初始化
$(function()
{
 	
	$("#btn-save").bind("click", function(event)
	{
		var $f = $("#dia-fm-option");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm-option").combined(1) || {};
		
		if(diaAction == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";		
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));		
	 
	}else{}
});


//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		$("#tab-list").insertResult(res,0);	
		$.pdialog.closeCurrent();
		
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
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>