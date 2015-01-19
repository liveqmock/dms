<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/head.jsp" />
<title>生成DicConstant页</title>
<script type="text/javascript">
/**
 * url
 */
var url = "<%=request.getContextPath()%>/ActionUtil/generateDic.ajax";


//保存
function doSave()
{
	var $f = $("#fm-generate");
	if (submitForm($f) == false) return false;
	sendPost(url,"save",'','',"false");
}

</script>
</head>
<body>
<h2 class="contentTitle">生成字典类</h2>
<div id='background1' class='background'></div>
<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
<div id="layout" width="100%" height="100%">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-generate" class="editForm" >
			<div align="left">
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave();" id="save">生成</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>	
</body>
</html>