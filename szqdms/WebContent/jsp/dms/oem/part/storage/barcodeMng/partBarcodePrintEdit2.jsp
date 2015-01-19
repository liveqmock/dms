<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@page import="com.org.framework.common.DBFactory"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String PART_ID = request.getParameter("PART_ID");
    String SUPPLIER_ID = request.getParameter("SUPPLIER_ID");
    String CS = request.getParameter("CS");
    int dytmcs = Integer.parseInt(CS);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件二维码打印</title>
<!-- 显示样式 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/partShowCss.css"/>
<!-- 打印样式 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/partPrintCss.css" media="print" />
</head>
<body style="overflow: scroll !important;">
<div id="printDiv">
	<% 
		for(int i = 1; i <= dytmcs; i++){
	%>
		<div class="box">
			<table>
				<tr>
					<td rowspan="4" class="imgTd">
						<img src="<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp?PART_ID=<%=PART_ID%>&SUPPLIER_ID=<%=SUPPLIER_ID%>&cs=<%=i%>" alt="二维码"  id="barcode<%=i%>" />
					</td>
					<td class="titleTd">配件代码</td>
					<td class="valueTd" name="dia-PART_CODE"></td>
				</tr>
				<tr>
					<td>配件名称</td>
					<td>
						<span name="dia-PART_NAME"></span>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
<!-- 				<tr>
					<td>供应商代码</td>
					<td name="dia-SUPPLIER_CODE"></td>
				</tr>
				<tr>
					<td>服务热线</td>
					<td>400-880-9818</td>
				</tr> -->
			</table>
		</div>
		
	<%
			if(i < dytmcs){
	%>
	<br />
	<%
			}
		}
	%>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button class="close" type="button" id="closeBtn">关&nbsp;&nbsp;闭</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button onclick="doPrint();" type="button">打&nbsp;&nbsp;印</button>
						</div>
					</div></li>
			</ul>
		</div>
</div>
</body>

<script type="text/javascript">
var PART_ID = <%=PART_ID%>;
var SUPPLIER_ID = <%=SUPPLIER_ID%>;
var CS = <%=CS%>;
$(function(){
	$("#closeBtn").click(function(){
		window.close();
	});
    var PART_CODE=opener.row.attr("PART_CODE");
    if(PART_CODE)
        $("[name='dia-PART_CODE']").html(PART_CODE);
    var PART_NAME=opener.row.attr("PART_NAME");
    if(PART_NAME)
    {
/*     	var i = 17*3 - 6;
    	var ii = 0;
    	var str = "";
    	for(var j=0;j<PART_NAME.length;j++)
    	{
    		if ((PART_NAME.charCodeAt(j)>=032) && (PART_NAME.charCodeAt(j)<=126))
    		{
    			if(ii < i)
    				str += PART_NAME.charAt(j);
    			ii++;
	   		}else //汉字
    		{
	   			if(ii < i)
    				str += PART_NAME.charAt(j);
    			ii = ii + 2;	
    		}
    	} */
    	$("[name='dia-PART_NAME']").html(PART_NAME);    	
    }
    var SUPPLIER_CODE=opener.row.attr("SUPPLIER_CODE");
    if(SUPPLIER_CODE)
        $("[name='dia-SUPPLIER_CODE']").html(SUPPLIER_CODE);
    
});
function doPrint() {
	document.body.innerHTML=document.getElementById("printDiv").innerHTML;
    window.print();
}
//弹出窗体
var dialog = parent.$("body").data("editWin");
$(function()
{
    $("button.close").click(function(){
        window.close();
        return false;
    });
});
</script>
</html>