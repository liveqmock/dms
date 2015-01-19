<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="dia-fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="di_ycpjTable">
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="dia-partCode-Search" name="dia-orderNo-Search" datasource="ORDER_NO" datatype="1,is_null,100" operation="like" /></td>
                        <td><label>订单类型：</label></td>
                        <td><input type="text" id="dia-partName-Search" name="dia-orderType-Search" datasource="ORDER_TYPE" datatype="1,is_null,100" operation="like" /></td>
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
                        <th fieldname="ORDER_NO">订单编号</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
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
    var partSearchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchInStockOrder.ajax";
    // 弹出窗体
    var dialog = $("body").data("salesOrderSearch");
    // 初始化函数
    $(function() {

        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            var $f = $("#dia-fm-condition");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, partSearchUrl, "di_searchPart", 1, sCondition, "dia-tab-grid");
        });
    });

    // 单选按钮回调函数
    function doRadio(res){
        // getSelectedRows():获取列表选中行对象，返回选中行数组
        var selectedRows = $("#dia-tab-grid").getSelectedRows();
        $("#dia-sourceOrderId-edit").val(selectedRows[0].attr("ORDER_ID"));
        $("#dia-orderId-edit").val(selectedRows[0].attr("ORDER_ID"));
        $("#dia-sourceOrderNo-edit").val(selectedRows[0].attr("ORDER_NO"));
        // 关闭窗口
        $.pdialog.close(dialog);
        return false;
    }
</script>