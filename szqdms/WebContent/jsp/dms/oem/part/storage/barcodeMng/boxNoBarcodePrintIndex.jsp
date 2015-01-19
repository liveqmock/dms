<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>箱号条码打印</title>
    <script type="text/javascript">
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //打印按钮响应
            $("#btn-print").bind("click", function (event) {
                var $f = $("#fm-boxNO");
                if (submitForm($f) == false) return false;
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/barcodeMng/boxNoBarcodePrintEdit.jsp?BOX_NO="+$('#dia-BOX_NO').val(), "editWin", "箱号条码打印", diaEditOptions,true);
            });
        });
    </script>
</head>
<body>
    <div id="layout" style="width:100%;">
        <div id='background1' class='background'></div>
        <div id='progressBar1' class='progressBar'>loading...</div>
        <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
            配件管理 &gt; 仓储管理 &gt; 条码管理 &gt; 箱号条码打印</h4>

        <div class="page">
            <div class="pageHeader">
                <form method="post" id="fm-boxNO" class="editForm">
                    <div class="searchBar">
                        <div align="left">
                            <table class="editTable" id="tab-boxNO">
                                <tr>
                                    <td><label>箱号：</label></td>
                                    <td>
                                        <input type="text" id="dia-BOX_NO" name="dia-BOX_NO" datasource="BOX_NO" datatype="0,is_digit_letter,30"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="subBar">
                            <ul>
                                <li>
                                    <div class="buttonActive">
                                        <div class="buttonContent">
                                            <button type="button" id="btn-print">打&nbsp;&nbsp;印</button>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>