<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>邮寄信息管理</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/financeMng/invoiceMng/InvoiceExpressAction/searchSaleOrder.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: false, width: 1100, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchSaleOrder");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-saleOrderList");
            });
            $("#btn-search").trigger("click");
        });

        function doExpress(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/finance/invoiceMng/invoiceExpressEdit.jsp", "editWin", "邮寄信息", diaEditOptions);
        }
        function formatAmount(obj){
            return amountFormatNew($(obj).html());
        }
        function showLink(obj)
        {
        	var $row=$(obj).parent();
            return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
        }
        function openDetail(ORDER_ID){
        	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 财务管理 &gt; 开票管理 &gt; 邮寄信息管理</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchSaleOrder">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchSaleOrder">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>渠道商代码：</label></td>
                            <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="A.ORG_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>渠道商名称：</label></td>
                            <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="A.ORG_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>订单号：</label></td>
                            <td>
                            <td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="A.ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                            </td>
                        </tr>
                        <tr>
                            <td><label>开票单号：</label></td>
                            <td><input type="text" id="INVOICE_NO" name="INVOICE_NO" datasource="B.INVOICE_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>开票日期：</label></td>
                            <td>
                                <input  type="text" name="INVOICE_DATE" id="INVOICE_DATE" style="width:100px;" class="Wdate" datasource="B.INVOICE_DATE" kind="date" operation="=" datatype="1,is_date,20" onclick="WdatePicker()"/>
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
                        <th fieldname="ORG_CODE">渠道商代码</th>
                        <th fieldname="ORG_NAME">渠道商名称</th>
                        <th fieldname="ORDER_NO" colwidth="135" refer="showLink">订单号</th>
                        <th fieldname="ORDER_STATUS">订单状态</th>
                        <th fieldname="INVOICE_STATUS">开票状态</th>
                        <th fieldname="REAL_AMOUNT" refer="formatAmount" align="right">金额</th>
                        <th fieldname="INVOICE_NO">开票单号</th>
                        <th fieldname="INVOICE_AMOUNT" refer="formatAmount" align="right">开票金额</th>
                        <th fieldname="INVOICE_DATE">开票日期</th>
                        <th colwidth="85" type="link" title="[邮寄]" action="doExpress">操作</th>
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