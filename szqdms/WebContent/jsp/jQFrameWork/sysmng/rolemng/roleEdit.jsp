<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String gsid = user.getCompanyId();
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/head.jsp" />
<title>角色信息维护</title>
<script type="text/javascript">
var action = "<%=action%>";
/**
 * 保存url
 */
var saveAction = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction";
//初始化
$(function()
{
	//修改操作
	if(action != "1")
	{
		var selectedRows = parent.$("#tab-rolelist").getSelectedRows();
		setEditValue("fm-roleInfo",selectedRows[0].attr("rowdata"));
		$("#dia-code").attr("readonly",true);
		
	}else
	{
		$("#btn-grantMenus").attr("disabled",true);
		$("#btn-grantMenus").parent().parent().addClass("buttonDisabled");
		$("#btn-grantPersons").attr("disabled",true);
		$("#btn-grantPersons").parent().parent().addClass("buttonDisabled");
	}
	
	$("#btn-grantMenus").click(function(){
		openMenus('',$("#dia-roleId").val());
	});
	$("#btn-save").bind("click",function(event){
		doDiaSave();
	});
});

//保存
function doDiaSave()
{
	var $f = $("#fm-roleInfo");
	if (submitForm($f) == false) return false;
	var sCondition = {};
		sCondition = $("#fm-roleInfo").combined(1) || {};
	if(action == 1)	
	{
		var url = saveAction + "/insert.ajax";
		doNormalSubmit($f,url,"btn-save",sCondition,insertCallBack);
	}else
	{
		var url = saveAction + "/update.ajax";
		doNormalSubmit($f,url,"btn-save",sCondition,updateCallBack);
	}
}
//回调函数
function insertCallBack(res)
{
	try
	{
		parent.$("#tab-rolelist").insertResult(res,0);
		//设置roleId主键域
		$("#dia-roleId").val(getNodeText(res.getElementsByTagName("ROLE_ID").item(0)));
		
		$("#btn-grantMenus").attr("disabled",false);
		$("#btn-grantMenus").parent().parent().removeClass("buttonDisabled");
		$("#btn-grantPersons").attr("disabled",false);
		$("#btn-grantPersons").parent().parent().removeClass("buttonDisabled");
		//同步集群节点（业务功能不需要）
		var syncUrl = saveAction + "/synchronize.ajax?type=1&roleId="+$("#dia-roleId").val();
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	
	return true;
}

//回调函数
function updateCallBack(res)
{
	//alert(res.xml);
	try
	{
		var selectedIndex = parent.$("#tab-rolelist").getSelectedIndex();
		parent.$("#tab-rolelist").updateResult(res,selectedIndex);
		//同步集群节点（业务功能不需要）
		var syncUrl = saveAction + "/synchronize.ajax?type=2&roleId="+$("#dia-roleId").val();
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	
	return true;
}
//弹出窗体
var dialog = parent.$("body").data("editRole");
$(function()
{
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
</script>
</head>
<body>
<h2 class="contentTitle">角色基本信息编辑</h2>
<div id='background1' class='background'></div>
<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
<div id="layout" width="100%" height="100%">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-roleInfo" class="editForm" >
		<div align="left">
		<!-- 隐藏域 -->
		<input type="hidden" id="dia-roleId" name="dia-roleId" datasource="ROLE_ID" />
		<table class="editTable" id="tab-roleInfo">
			<tr><td><label>角色代码：</label></td>
			    <td><input type="text" id="dia-code" alt="" name="dia-code" datasource="CODE" datatype="0,is_digit_letter,30"  value=""/></td>
			    <td><label>角色名称：</label></td>
			    <td><input type="text" id="dia-rname" name="dia-rname" datasource="RNAME" datatype="0,is_name,30" value=""/>
			    </td>
			</tr>
			<tr>
				<td><label>角色级别：</label></td>
			    <td>
			    	<select type="text" class="combox" id="dia-levelCode" name="dia-levelCode" kind="dic" src="JGJB" datasource="LEVEL_CODE" datatype="0,is_null,10" >
				    	<option value="-1" selected>--</option>
				    </select>
				</td>
				<!-- 
				<td><label>所属组织：</label></td>
			    <td><input type="text" id="dia-orgId" name="dia-orgId" datasource="ORG_ID" kind="dic" src="ZZJG"  datatype="0,is_null,30" value=""/></td>
			     -->
			</tr>
			<tr>
			    <td><label>有效标识：</label></td>
			    <td>
				    <select type="text" class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >
				    	<option value="<%=Constant.YXBS_01%>" selected>有效</option>
				    </select>
			    </td>
			    <td><label>角色描述：</label></td>
			    <td>
			    	<input type="text" id="dia-roleMark" name="dia-roleMark" datasource="ROLE_REMARK" datatype="0,is_null,50" value=""/>
			    </td>
			</tr>
		</table>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
			<li><div class="button"><div class="buttonContent" ><button type="button"  id="btn-grantMenus">授予权限</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="openGrantPersons();" id="btn-grantPersons">授予人员</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>	
</body>
</html>