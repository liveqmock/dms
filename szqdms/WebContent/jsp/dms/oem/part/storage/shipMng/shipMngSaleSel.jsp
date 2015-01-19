<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchSale">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchSale">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>订单编号：</label></td>
                            <td><input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="A.ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchSale">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-confirmSale">确&nbsp;&nbsp;定</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeSale">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-saleList">
                <table style="display:none;width:100%;" id="tab-saleList" name="tablist" ref="div-saleList"
                       refQuery="tab-searchSale">
                    <thead>
                    <tr>
                        <th type="multi" name="XH" unique="ORDER_ID" style=""></th>
                        <th fieldname="ORDER_NO">订单编号</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
                        <th fieldname="ORG_NAME">提报单位</th>
                        <th fieldname="COUNT" colwidth="60">配件数量</th>
                        <th fieldname="AMOUNT" refer="formatAmount" align="right">配件金额</th>
                        <th fieldname="DELIVERY_ADDR" colwidth="150">收货地址</th>
                        <th fieldname="LINK_MAN">联系人</th>
                        <th fieldname="PHONE">联系电话</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedSale" style="display: none">
                <legend align="left" >&nbsp;[已选定订单]</legend>
                <div>
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display: none" id="val0" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val1" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-saleInfo">
                <input type="hidden" id="shipId" name="shipId" datasource="SHIPID"/>
                <input type="hidden" id="orderIds" name="orderIds" datasource="ORDERIDS"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var saleSelWin = $("body").data("saleSelWin");
    $(function () {
        $("#btn-closeSale").click(function () {
            $.pdialog.close(saleSelWin);
            return false;
        });
        $("#btn-searchSale").bind("click", function (event) {
        	var ifArmy = $('#dia-ARMY').val();
            //查询范围URL
            var searchSaleUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/searchSale.ajax?ifArmy="+ifArmy;
            var $f = $("#fm-searchSale");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchSaleUrl, "btn-search", 1, sCondition, "tab-saleList");
            $('#selectedSale').show();
        });
        //确定按钮响应
        $('#btn-confirmSale').bind('click',function(){
            //添加发运单明细URL
            var insertShipDtlUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/insertShipDtl.ajax";
            var orderIds = $('#val0').val();
            if(!orderIds){
                alertMsg.warn('请选择订单!')
            }else{
                $('#shipId').val($('#dia-SHIP_ID').val());//销售订单ID
                $('#orderIds').val(orderIds);

                //获取需要提交的form对象
                var $f = $("#fm-saleInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertShipDtlUrl, "btn-confirmSale", sCondition, insertShipDtlCallBack);
            }
        });
    });

    //添加发运单明细回调
    function insertShipDtlCallBack(){
        $.pdialog.close(saleSelWin);
        searchShipDtl();
    }
    //列表复选
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        arr.push($tr.attr("ORDER_ID"));
        arr.push($tr.attr("ORDER_NO"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");
        multiSelected($checkbox, arr);
    }
    function formatAmount(obj){
        return amountFormatNew($(obj).html());
    }
</script>