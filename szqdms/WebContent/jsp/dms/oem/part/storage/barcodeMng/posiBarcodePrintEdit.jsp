<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String POSITION_ID = request.getParameter("POSITION_ID");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="/head.jsp" />
    <title>条码打印</title>
    <style type="text/css">
        #dia-printList {
            /*border: 1px solid #B1CDE3;*/
            float:left;
            border:0px;
            padding: 0;
            margin: 0 auto;
            border-collapse: collapse;
            width: 8cm;
            height: 2.4cm;
        }
       #dia-printList td {
            /* border: 1px solid #B1CDE3; */
            border:0px;
            background: #fff;
            font-size: 16px;
            color: black;
            white-space: nowrap;
        }
        #printDiv div{
        	text-align: left;
        }
    </style>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/printCss.css" media="print"/>
    <script type="text/javascript">
        var POSITION_ID = <%=POSITION_ID%>;
        $(function(){
            var POSITION_CODE=opener.row.attr("POSITION_CODE");
            if(POSITION_CODE)
                $("[name='dia-POSITION_CODE']").html("库位代码："+POSITION_CODE);
            var POSITION_NAME=opener.row.attr("POSITION_NAME");
            if(POSITION_NAME)
                $("[name='dia-POSITION_NAME']").html("库位名称："+POSITION_NAME);
            $("#barcode").attr("src","<%=request.getContextPath()%>/jsp/dms/oem/part/storage/barcodeMng/showPic.jsp?POSITION_ID="+POSITION_ID);
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
    <div id="dia-layout" style="overflow:hidden;">
        <div id="printDiv" >
            <table id="dia-printList" name="dia-printList" cellpadding="0" cellspacing="0">
                <tr>
                    <td id="imgTd">
                        <img src="" alt="二维码" id="barcode" />
                    </td>
                    <td id="textTd">
                    	<ul>
                    		<li name="dia-POSITION_CODE"></li>
                    		<li name="dia-POSITION_NAME"></li>
                    	</ul>
                    </td>
                </tr>
            </table>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button onclick="doPrint();" type="button">打&nbsp;&nbsp;印</button></div></div></li>
            </ul>
        </div>
    </div>
</body>
</html>
