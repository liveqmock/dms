<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String contextPath = request.getContextPath();
%>
<!-- 
	 Title:报表预览
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<script src="<%=contextPath %>/lib/plugins/sa/sa.core.js" type="text/javascript"></script>
<title>报表管理</title>
<script type="text/javascript">
$(function(){
	$.sa.preViewReport();
});
</script>
</head>
<body>
	<form id="fm-preViewReport" name="fm-preViewReport" method="post" style="display:none;">
		<input type="text" id="in-ds" name="in-ds" />
		<input type="text" id="in-reportDom" name="in-reportDom" />
		<input type="text" id="in-reportSql" name="in-reportSql" />
		<input type="hidden" id="in-reportType" name="in-reportType" />
		<input type="hidden" id="in-rcColumns" name="in-rcColumns" />
		<input type="hidden" id="in-sumColumns" name="in-sumColumns" />
		<input type="hidden" id="in-uColIndex" name="in-uColIndex" />
	</form>
</body>
</html>