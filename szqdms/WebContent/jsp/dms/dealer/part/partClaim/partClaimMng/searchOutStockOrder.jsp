<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String orderFlag = request.getParameter("orderFlag");
    if(orderFlag == null)
        orderFlag = "1";
%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="dia-fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="di_ycpjTable">
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="dia-orderNo-Search" name="dia-orderNo-Search" datasource="SALE_NO" datatype="1,is_null,100" operation="like" /></td>
                        <td><label>客户名称：</label></td>
                        <td><input type="text" id="dia-customerName-Search" name="dia-customerName-Search" datasource="CUSTOMER_NAME" datatype="1,is_null,100" operation="like" /></td>
                    </tr>
                    <tr>
                        <td><label>实销日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="SALE_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="SALE_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="dia_page_grid">
            <table style="display:none;width:100%;" id="dia-tab-grid" multivals="div-selectedPart" name="tablist" ref="dia_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" fieldname="ORDER_ID"></th>
                        <th fieldname="SALE_NO">订单编号</th>
                        <th fieldname="CUSTOMER_NAME">客户名称</th>
                        <th fieldname="SALE_DATE">实销日期</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    var searchUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclApplyMngAction/searchOutStockOrder.ajax";
    // 弹出窗体
    var dialog = $("body").data("searchOutStockOrder");
    var orderFlag = <%=orderFlag%>;
    // 初始化函数
    $(function() {

        // 出库单查询
        searchOutStock();

        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            // 出库单查询
            searchOutStock();
        });
    });

    // 出库单查询
    function searchOutStock() {
        var $f = $("#dia-fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        // 出库单查询
        doFormSubmit($f, searchUrl, "di_searchPart", 1, sCondition, "dia-tab-grid");
    }
    // 单选按钮回调函数
    function doRadio(res){
        // getSelectedRows():获取列表选中行对象，返回选中行数组
        var selectedRows = $("#dia-tab-grid").getSelectedRows();
        // 出库单查询
        // 销售出库单ID
        $("#outStockOrderId-Edit").val(selectedRows[0].attr("SALE_ID"));
        saleId = selectedRows[0].attr("SALE_ID");
        // 销售出库单号
        $("#outStockOrderNo-Edit").val(selectedRows[0].attr("SALE_NO"));
        // 出库日期
        $("#outStockDate-Edit").val(selectedRows[0].attr("SALE_DATE_sv"));
        // 购货单位
        $("#customerName-Edit").val(selectedRows[0].attr("CUSTOMER_NAME"));
        // 联系人
        $("#linkMan-Edit").val(selectedRows[0].attr("CUSTOMER_NAME"));
        // 联系电话
        $("#linkPhone-Edit").val(selectedRows[0].attr("LINK_PHONE"));
        // 清空配件信息
        clearPart();
        // 关闭窗口
        $.pdialog.close(dialog);
        return false;
    }
</script>