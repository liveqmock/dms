<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String BOX_NO = request.getParameter("BOX_NO");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="/head.jsp" />
    <title>条码打印</title>
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
            font-size: 24px;
            padding: 3px 0px 3px 8px;
            color: #4f6b72;
            white-space: nowrap;
        }
    </style>
    <script type="text/javascript">
        var BOX_NO = '<%=BOX_NO%>';
        $(function(){
            $("[name='dia-BOX_NO']").html("箱号："+BOX_NO);
            $("#barcode").attr("src","<%=contentPath%>/part/storageMng/barcodeMng/BoxNoBarcodePrintAction/createBarcode.do?BOX_NO="+BOX_NO);
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
                parent.$.pdialog.close(dialog);
                return false;
            });
        });
    </script>
</head>
<body>
    <div id="dia-layout">
        <div id="printDiv">
            <br />
            <div style="width: 44px;">
                <table width="430px" style="margin-top: 25px;" id="dia-printList" name="dia-printList" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="160px" height="100%" align="center" rowspan="3">
                            <img src="" alt="二维码" width='155px' height='155px' id="barcode" />
                        </td>
                        <td width="220px" style="text-align: left; height: 100%;" name="dia-BOX_NO"></td>
                    </tr>
                </table>
            </div>
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
