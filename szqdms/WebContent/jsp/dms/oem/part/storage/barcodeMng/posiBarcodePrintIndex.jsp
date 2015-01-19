<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>库位条码打印</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/barcodeMng/PosiBarcodePrintAction/searchPosition.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchPosition");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-positionList");
            });
        });
        var row;
        //列表打印连接
        function doPrint(rowobj) {
            row = $(rowobj);
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
//            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/barcodeMng/posiBarcodePrintEdit.jsp?POSITION_ID="+$(rowobj).attr('POSITION_ID'), "editWin", "库位条码打印", diaEditOptions,true);
            window.open(webApps + "/jsp/dms/oem/part/storage/barcodeMng/posiBarcodePrintEdit.jsp?POSITION_ID="+$(rowobj).attr('POSITION_ID'), "newwindow", "top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 条码管理 &gt; 库位条码打印</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchPosition">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPosition">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>库位代码：</label></td>
                            <td><input type="text" id="POSITION_CODE" name="POSITION_CODE" datasource="POSITION_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>库位名称：</label></td>
                            <td><input type="text" id="POSITION_NAME" name="POSITION_NAME" datasource="POSITION_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-search">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-positionList">
                <table style="display:none;width:100%;" id="tab-positionList" name="tablist" ref="div-positionList"
                       refQuery="tab-searchPosition">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="POSITION_CODE">库位代码</th>
                        <th fieldname="POSITION_NAME">库位名称</th>
                        <th colwidth="150" type="link" title="[打印]" action="doPrint">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>