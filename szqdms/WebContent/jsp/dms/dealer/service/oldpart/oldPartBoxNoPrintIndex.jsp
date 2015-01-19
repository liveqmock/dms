<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>装箱号打印</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction/oldPartBoxNoSearch.ajax";
$(function(){
	//打印
	$("#print").bind("click",function(){
		var $f = $("#boxNoFm");
		if (submitForm($f) == false) return false;
		window.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartBoxNoPrintDetail.jsp", "newwindow", "height=300, width=550, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 旧件管理  &gt; 旧件管理   &gt; 装箱号打印</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form id="boxNoFm" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="boxNoTab">
					<tr>
						<td><label>箱号：</label></td>
						<td><input type="text" id="boxNo" name="boxNo" datasource="BOX_NO" datatype="0,is_null,100"  value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="print">打&nbsp;&nbsp;印</button></div></div></li>
						<!-- <li><div class="button"><div class="buttonContent"><button type="button" id="exp">下&nbsp;&nbsp;载</button></div></div></li> -->
					</ul>
				</div>
			</div>
		</form>
	</div>
	</div>
</div>
</body>
</html>