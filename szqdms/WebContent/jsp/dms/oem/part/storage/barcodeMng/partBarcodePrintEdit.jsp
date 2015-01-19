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
<style type="text/css">  
    table {
            border: 1px solid #B1CDE3;
            padding: 0;
            margin: 0 auto;
            border-collapse: collapse;
        }
        td {
            border: 1px solid #B1CDE3;
            background: #fff;
            font-size: 18px;
            padding: 3px 0px 3px 8px;
            color: black;
        } 
</style>
<script type="text/javascript">
//弹出窗体
<%-- $(function(){
	//var selectedRows = parent.$("#oldPartList").getSelectedRows();
	var selectedRows=window.opener.$("#tab-partList_content").getSelectedRows();
	$("#E_PART_CODE").text(selectedRows[0].attr("PART_CODE"));
	$("#E_PART_NAME").text(selectedRows[0].attr("PART_NAME"));
	$("#E_SUPPLIER_NAME").text(selectedRows[0].attr("SUPPLIER_NAME"));
	var PART_ID=selectedRows[0].attr("PART_ID");
	var SUPPLIER_ID = selectedRows[0].attr("SUPPLIER_ID");
	$("#barCode").html("<img src='<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp?PART_ID="+PART_ID+"&SUPPLIER_ID="+SUPPLIER_ID+"' alt='二维图' style='width:160px;height:160px;'/>");
	$("#print").bind("click",function(){
		$("#opBtn").hide();
	});
	//关闭
	$("button.close").click(function(){
		window.close();
		return false;
	});
}); --%>
var PART_ID = <%=PART_ID%>;
var SUPPLIER_ID = <%=SUPPLIER_ID%>;
var CS = <%=CS%>;
$(function(){
    var PART_CODE=opener.row.attr("PART_CODE");
    if(PART_CODE)
        $("[name='dia-PART_CODE']").html("配件代码："+PART_CODE);
    var PART_NAME=opener.row.attr("PART_NAME");
    if(PART_NAME)
    {
    	var i = 17*3 - 6;
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
    	}
    	$("[name='dia-PART_NAME']").html("<table style='border:0px;float:left;'><tr style='height:15px;'><td style='margin-left:-8px;border:0px;white-space:nowrap;float:left;vertical-align:top;'>配件名称：</td><td style='border:0px;vertical-align:top;'>"+str+"</td></tr></table>");    	
    }
    var SUPPLIER_CODE=opener.row.attr("SUPPLIER_CODE");
    if(SUPPLIER_CODE)
        $("[name='dia-SUPPLIER_CODE']").html("供应商代码："+SUPPLIER_CODE);
    //for(var i=1;i<=CS;i++){
    //	alert(i);
    	//$("#barcode"+i+"").attr("src","<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp?PART_ID="+PART_ID+"&SUPPLIER_ID="+SUPPLIER_ID);	
    //}
    
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
        /* parent.$.pdialog.close(dialog); */
        window.close();
        return false;
    });
});
</script>
</head>
<body>
<div id="layout" style="overflow: auto;">
	<div id="printDiv" style="width:450px;">
	<% for(int i=1;i<=dytmcs;i++){
		if(i==1){
	%>
	<div style="height:18px;">&nbsp;</div>
	<%}else{ %>
	<div style="height:15px;">&nbsp;</div>
	<%} %>
		<div style="width:430px;align:center;margin-left:10px;" >
			</br>
			<table width="430px" style="margin-top:20px;" id="dia-dylb" name="dia-dylb" cellpadding="0" cellspacing="0">
				<tr>
					<td width="150px" height="150px" align="center" rowspan="6">
						<img src="<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp?PART_ID=<%=PART_ID%>&SUPPLIER_ID=<%=SUPPLIER_ID%>&cs=<%=i%>" alt="二维码" width='150px' height='150px' id="barcode<%=i%>" /></td>
					<td width="280px" style="text-align:left;height:15px;"></td>
				</tr>
				<tr>
					<td width="280px" style="text-align:left;height:25px;" name="dia-PART_CODE"></td>
				</tr>
				<tr>
					<td width="280px" style="text-align:left;height:45px; vertical-align:top;" name="dia-PART_NAME"></td>
				</tr>
				<tr>
					<td width="280px" style="text-align:left;height:25px;" name="dia-SUPPLIER_CODE"></td>
				</tr>
				<tr>
					<td width="280px" style="text-align:left;height:25px;">服务热线：400-880-9818</td>
				</tr>
				<tr>
					<td width="280px" style="text-align:left;height:15px;"></td>
				</tr>
			</table>
			</br>
		</div>
		<%if(i!=dytmcs){%>
			<div style="height:26px;">&nbsp;</div>
		<%}}%>
		</div>
		<div class="formBar"  class="noprint">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button class="close" type="button">关&nbsp;&nbsp;闭</button>
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
</html>