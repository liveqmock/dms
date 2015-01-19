<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String sessionId = request.getSession().getId();
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<!-- 
	 Title:多附件上传
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2013-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<h2 class="contentTitle" style="display:none;">多文件上传，支持选择多个文件</h2>
<script src="<%=request.getContextPath()%>/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>
<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	width: 100%;
	height: 200px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
	background:#ffffff;
}
</style>
<div style="margin: 0 10px;background:#EAEEF5" >
	<input id="uploadify-impl" type="file" name="uploadify-impl" />
	<div id="fileQueue" class="fileQueue"></div>
	<input type="image" src="<%=request.getContextPath() %>/uploadify/img/upload.png" onclick="$('#uploadify-impl').uploadify('upload', '*');"/>
	<input type="image" src="<%=request.getContextPath() %>/uploadify/img/cancel.png" onclick="$('#uploadify-impl').uploadify('cancel', '*');"/>
	<div class="divider"></div>
</div>
<script type="text/javascript">
var dialogUploadify = $("body").data("dialog-uploadify");
$(function(){
	//alert(dialogUploadify.data("op")["uploadify"].fileTypeDesc);
	var userid = "<%=user.getAccount()%>";
	var sessionid = "<%=sessionId%>";
	var userInfo = {"userid":userid,"sessionid":sessionid};
	$.filestore.init(userInfo,dialogUploadify.data("op"));
	$("body").removeData("dialog-uploadify");
});
</script>