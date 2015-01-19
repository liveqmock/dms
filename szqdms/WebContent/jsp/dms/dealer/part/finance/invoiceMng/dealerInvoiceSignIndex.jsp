<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>发票签收</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceMng/DealerInvoiceMngAction/searchSign.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: false, width: 1100, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        var diaOptions = {max: true, width: 1100, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchSaleOrder");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-saleOrderList");
            });
        });
        function doSign(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/dealer/part/finance/invoiceMng/invoiceExpressSign.jsp", "editWin", "发票签收", diaEditOptions);
        }
        function doDetail(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/finance/invoiceMng/dealerInvoiceDetail.jsp", "editWin1", "开票明细", diaOptions);
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        渠道配件 &gt; 财务管理 &gt; 开票管理 &gt; 发票签收</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchSaleOrder">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchSaleOrder">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>快递单号：</label></td>
                            <td><input type="text" id="EXPRESS_NO" name="EXPRESS_NO" datasource="T.EXPRESS_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>开票单号：</label></td>
                            <td><input type="text" id="INVOICE_NO" name="INVOICE_NO" datasource="T.INVOICE_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>开票日期：</label></td>
                            <td>
                                <input  type="text" name="INVOICE_DATE" id="INVOICE_DATE" style="width:100px;" class="Wdate" datasource="T.INVOICE_DATE" kind="date" operation="=" datatype="1,is_date,20" onclick="WdatePicker()"/>
                            </td>
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
            <div id="div-saleOrderList">
                <table style="display:none;width:100%;" id="tab-saleOrderList" name="tablist" ref="div-saleOrderList" refQuery="tab-searchSaleOrder">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display: none"></th>
                        <th fieldname="INVOICE_NO">发票号</th>
                        <th fieldname="INVOICE_MONTH">开票月份</th>
                        <th fieldname="INVOICE_AMOUNT" refer="formatAmount" align="right">开票金额</th>
                        <th fieldname="INVOICE_DATE">开票日期</th>
                        <th fieldname="EXPRESS_NO">快递单号</th>
                        <th fieldname="EXPRESS_COMPANY">快递公司</th>
                        <th fieldname="SENDER">寄件人</th>
                        <th colwidth="85" type="link" title="[签收]|[明细]" action="doSign|doDetail">操作</th>
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