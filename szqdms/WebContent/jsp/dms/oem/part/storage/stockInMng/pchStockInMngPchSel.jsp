<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPch">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPch">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>采购拆分单号：</label></td>
                            <td><input type="text" id="sel-SPLIT_NO" name="sel-SPLIT_NO" datasource="SPLIT_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>采购订单号：</label></td>
                            <td><input type="text" id="sel-ORDER_NO" name="sel-ORDER_NO" datasource="ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                            <td><label>供应商：</label></td>
                            <td><input type="text" id="sel-SUPPLIER_NAME" name="sel-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>采购员：</label></td>
                            <td><input type="text" id="sel-APPLY_USER" name="sel-APPLY_USER" datasource="APPLY_USER" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPch">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closePch">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-pchList">
                <table style="display:none;width:100%;" id="tab-pchList" name="tablist" ref="div-pchList"
                       refQuery="tab-searchPch">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="SPLIT_NO" colwidth="130">采购拆分单号</th>
                            <th fieldname="ORDER_NO" colwidth="120">采购订单号</th>
                            <th fieldname="PURCHASE_TYPE">采购类型</th>
                            <th fieldname="SUPPLIER_NAME">供应商</th>
                            <th fieldname="APPLY_USER">采购员</th>
                            <th fieldname="PURCHASE_COUNT">采购数量</th>
                            <th fieldname="PURCHASE_AMOUNT" colwidth="80" refer="formatAmount" align="right">采购金额</th>
                            <th fieldname="PLAN_AMOUNT" colwidth="80" refer="formatAmount" align="right">计划金额</th>
                            <th type="link" title="操作" action="doSel" class="btnSelect">操作</th>
                           </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var pchSelWin = $("body").data("pchSelWin");
    $(function () {
        $("#btn-closePch").click(function () {
            $.pdialog.close(pchSelWin);
            return false;
        });
        $("#btn-searchPch").bind("click", function (event) {
            var searchPchUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/PchStockInMngAction/searchPch.ajax?warehouseType="+$('#dia-WAREHOUSE_TYPE').val();
            var $f = $("#fm-searchPch");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchPchUrl, "btn-searchPch", 1, sCondition, "tab-pchList");
        });
    });

    function doSel(rowobj){
        $('#dia-ORDER_ID').val($(rowobj).attr('SPLIT_ID'));
        $('#dia-ORDER_NO').val($(rowobj).attr('SPLIT_NO'));
        $('#dia-SUPPLIER_ID').val($(rowobj).attr('SUPPLIER_ID'));
        $('#dia-SUPPLIER_CODE').val($(rowobj).attr('SUPPLIER_CODE'));
        $('#dia-SUPPLIER_NAME').val($(rowobj).attr('SUPPLIER_NAME'));
        $('#dia-BUYER').val($(rowobj).attr('APPLY_USER'));
        $('#dia-BUYER_SV').val($(rowobj).attr('APPLY_USER_SV'));
        $('#dia-PLAN_AMOUNT').val($(rowobj).attr('PLAN_AMOUNT'));
        $('#dia-PURCHASE_AMOUNT').val($(rowobj).attr('PURCHASE_AMOUNT'));
        $.pdialog.close(pchSelWin);
    }

    function formatAmount(obj){
        return amountFormatNew($(obj).html());
    }

</script>