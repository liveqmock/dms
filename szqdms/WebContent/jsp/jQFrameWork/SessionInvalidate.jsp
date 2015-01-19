<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
     Object o = request.getAttribute("statusCode");
	 String statusCode = "";
	 if(o != null)
	    statusCode = o.toString();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>会话失效</title>
<style>
a:hover { text-decoration:underline;}
body 
{
	background:#EAEEF5;
}
</style>
</head>
<body>
当前会话已失效，请重新<a href="javascript:top.location.href='<%=request.getContextPath()%>/index.jsp'" target="dialog"  width="600" ref="qhxt"><font color="red" ><strong>登录</strong></font></a>!
</body>
</html>