<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contextPath = request.getContextPath();
	String userId = user.getUserId();
	String userAccount = user.getAccount();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<style>
body 
{
	background:#EAEEF5;
}
</style>
</head>
<body>
<div style="width:100%;">
	<h1 style="margin-top:20px;"><span style="font-size:18px;">请修改密码</span></h1>
	<div class="page">
	<div class="pageContent" >
		<form method="post" id="mmglFm" class="editForm" style="height:120px;">
			<div align="left">
			<input type="hidden" id="changePassUserId" datasource="USER_ID" />
			<input type="hidden" id="changePassUserAccount" datasource="ACCOUNT" />
			<table class="editTable" id="mmxx">
				<tr><td><label>原密码：</label></td>
				    <td><input type="password" id="jmm"  name="jmm" datasource="OLDPASSWORD" datatype="1,is_null,18" /></td>
				</tr>
				<tr><td><label>新密码：</label></td>
				    <td><input type="password" id="xmm"  name="xmm" datasource="NEWPASSWORD" datatype="0,is_null,18"  /></td>
				</tr>
				<tr>
				    <td><label>请再次输入新密码：</label></td>
				    <td><input type="password" id="qrmm" name="qrmm" datasource="CONFIRMPASSWORD" datatype="0,is_null,18" /></td>
				</tr>
			</table>
			<br/>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doChangePassword();" id="changePassWordBtn">保&nbsp;&nbsp;存</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>	
<script type="text/javascript">
//查询提交方法
var changePasswordUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/chgPass.ajax";
$(function()
{
	if($("#tab-userlist").size()>0)
	{
		var selectedRows = $("#tab-userlist").getSelectedRows();
		$("#changePassUserId").val(selectedRows[0].attr("USER_ID"));
		$("#changePassUserAccount").val(selectedRows[0].attr("ACCOUNT"));
	}
	else
	{
		$("#changePassUserId").val("<%=userId%>");
		$("#changePassUserAccount").val("<%=userAccount%>");
	}
		
});

function doChangePassword()
{
	var $f = $("#mmglFm");
	//if (submitForm($f) == false) return false;
	var xmm = $("#xmm").val();
	var qrmm = $("#qrmm").val();
	if(xmm.length<6 || xmm.length>18)
	{
	    alert("新密码长度应在6~18位之间");
	    $("#xmm").focus();
	    return false;
	}
	if(xmm != qrmm)
	{
		alert('两次输入的新密码不一致，请重新输入。');
		$("#xmm").val("");
		$("#qrmm").val("");
	    $("#xmm").focus();
	    return false;
	}
	var sCondition = {};
		sCondition = $f.combined(1) || {};
	doNormalSubmit($f,changePasswordUrl,"changePassWordBtn",sCondition,changePasswordCallBack);
}
function changePasswordCallBack(res)
{
	//同步集群节点（业务功能不需要）
	var syncUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction" + "/synchronize.ajax?type=2&account="+$("#changePassUserAccount").val();
	sendPost(syncUrl,"","",returnIndexFunc,"false");
}
function returnIndexFunc(res)
{
	window.location.href = "<%=request.getContextPath()%>/index.jsp";
}
</script>
</body>
</html>