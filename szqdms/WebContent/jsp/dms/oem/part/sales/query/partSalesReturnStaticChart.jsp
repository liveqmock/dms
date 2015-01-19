<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.*"%>
<%@page import="com.org.dms.action.part.salesMng.search.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contentPath+"/";
%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="/head.jsp" />
	<base href="<%=basePath%>">
	<title>配件销售回款统计图</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配件销售回款统计表</h4>
	<div class="page" >
	  <%
	  	String statisticId = request.getParameter("statisticId");
		FusionCharts  fusionCharts = new FusionCharts();
		PartSalesReturnStaticQueryAction  fusionChartsAction = new PartSalesReturnStaticQueryAction();
		String[] strXML = fusionChartsAction.dataXml(statisticId);
		//柱形图
	 	String chartHTMLCode1 = fusionCharts.createChartHTML("images/chart/Column3D.swf", "", strXML[0], "myFirst", 600, 300, false);
	 	//扇形图
	 	String chartHTMLCode2 = fusionCharts.createChartHTML("images/chart/Doughnut3D.swf", "", strXML[1], "myFirst", 600, 300, false);
	  %>
	  <%=chartHTMLCode1%>
	  <%=chartHTMLCode2%>
	</div>
</div>
</body>
</html>
