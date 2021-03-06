<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" style="width:100%;">
			<input type="hidden" id="dia-USER_ID" name="dia-USER_ID" datasource="USER_ID" />
			<div align="left">
			<fieldset>
				<table class="editTable" id="dia-tb-option">
					<tr>
						<td><label>人员姓名：</label></td>
						<td><input type="text" id="dia-USER_NAME" name="dia-USER_NAME" datasource="USER_NAME" datatype="0,is_null,30" value="" /></td>
						<td><label>手机号码：</label></td>
						<td><input type="text" id="dia-MOBIL" name="dia-MOBIL" datasource="MOBIL" datatype="0,is_null,30" value="" /></td>
					</tr>
					<tr>
						<td><label>住址：</label></td>
						<td><input type="text" id="dia-ADDRESS" name="dia-ADDRESS" datasource="ADDRESS" datatype="1,is_null,60" value="" /></td>
						<td><label>状态：</label></td>
			   		 	<td>
			   		 		<select type="text" class="combox" id="dia-USER_STATUS" name="dia-USER_STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
				     			<option value="-1" selected>--</option>				 
				    		</select>
			    		</td>
			    	</tr>
					<tr>
						<td><label>备注：</label></td>
						<td colspan="3"><textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS"  datasource="REMARKS" datatype="1,is_null,100"></textarea></td>
					</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save" >保&nbsp;&nbsp;存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchUerMngAction";
var diaAction = "<%=action%>";
$(function(){
	$("#btn-save").bind("click", function(event){
		var $f = $("#dia-fm-option");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm-option").combined(1) || {};
		if(diaAction == 1)
		{
			var updateUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaInsertCallBack);
		}else
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();	   
		setEditValue("dia-fm-option",selectedRows[0].attr("rowdata"));	 
	}else
	{
		setDiaDefaultValue();
	}
});
function setDiaDefaultValue()
{
	$("#dia-USER_STATUS").val("<%=DicConstant.YXBS_01%>");
	$("#dia-USER_STATUS").attr("code","<%=DicConstant.YXBS_01%>");
	$("#dia-USER_STATUS").find("option").val("<%=DicConstant.YXBS_01%>");
	$("#dia-USER_STATUS").find("option").text("有效");
	$("#dia-USER_STATUS").attr("readonly",true);
}
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		$("#tab-list").insertResult(res,0);	
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
</div>

