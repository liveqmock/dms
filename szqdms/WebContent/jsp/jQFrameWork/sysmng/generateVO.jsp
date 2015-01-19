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
<title>生成VO页</title>
<script type="text/javascript">
/**
 * url
 */
var url = "<%=request.getContextPath()%>/ActionUtil/generateVo.ajax";


//保存
function doSave()
{
	var $f = $("#jbxx");
	if (submitForm($f) == false) return false;
	url += "?tablename="+$("#tablename").val() + "&key=" +$("#key").val();
	sendPost(url,"save",'','',"false");
}

</script>
</head>
<body>
<h2 class="contentTitle">生成VO</h2>
<div id='background1' class='background'></div>
<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
<div id="layout" width="100%" height="100%">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="jbxx" class="editForm" >
		<div align="left">
		<table class="editTable" id="jbxxsr">
			<!-- hidden域 -->
			<input type="hidden"/>
			<tr><td><label>数据库表名：</label></td>
			    <td><input type="text" id="tablename" size="30" alt="" name="tablename" datasource="tablename" datatype="0,is_null,30"  value=""/></td>
			    <td><label>主键字段名：</label></td>
			    <td><input type="text" id="key" size="30" name="key" datasource="key" kind="text" size="30" datatype="0,is_null,200" value=""/>
			    </td>
			</tr>
		</table>
		<br>
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