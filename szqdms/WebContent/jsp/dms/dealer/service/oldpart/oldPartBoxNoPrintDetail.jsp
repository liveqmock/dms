<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgId=user.getOrgId();
	String orgCode=user.getOrgCode();
	String orgName=user.getOrgDept().getOName();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件打印</title>
<style type="text/css">  
    .cusTable td {  
         font-size:15px;  
         padding: 3px 0px 3px 8px;  
         white-space:nowrap;
         border: 1px solid #B1CDE3;
     }  
</style>

 <script type="text/javascript">
 var diaPrintUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction";
//弹出窗体
$(function(){
	var box=window.opener.$("#boxNo").val();
	$("#boxNoContent").text(box);
	$("#orgCodeContent").text('<%=orgCode%>');
	$("#orgNameContent").text('<%=orgName%>');
	$("#ewm").attr("src","<%=request.getContextPath()%>/jsp/dms/dealer/service/oldpart/showBoxPicture.jsp?boxNo="+box);
	$("#print").bind("click",function(){
		$("#opBtn").hide();
		window.print();
	});
	//关闭
	$("button.close").click(function(){
		window.close();
		return false;
	});
});
<%-- $("#ewm").attr("src","<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction/boxNoPrintPicture.do?BOX_NO="+box); --%>
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id="dy">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-boxNoForm" class="editForm" >
			<input type="hidden" id="box" datasource="BOX"/>
			<div align="center">
			<fieldset>
			<table class="cusTable" id="dia-boxNoTable">
			    <tr>
					<!-- <td id="ewm" ></td> -->
					<td  align="center" rowspan="3">
                            <img src="" alt="二维码" width='150px' height='150px' id="ewm" /></td>
					<td id="boxNoContent"></td>
					</tr>
					<tr>
					<td id="orgCodeContent"></td>
					</tr>
					<tr>
					<td id="orgNameContent"></td>
				</tr>	
			</table>
			</fieldset>
			</div>
		</form>
	</div>
		<div class="formBar" id="opBtn">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="print">打&nbsp;&nbsp;印</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
</body>
</html>