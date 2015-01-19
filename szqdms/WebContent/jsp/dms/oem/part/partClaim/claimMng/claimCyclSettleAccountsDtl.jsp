<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="pjsbmx" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
    <form method="post" id="di-pjsbmx" class="editForm" >
        <div align="left">
            <fieldset>
                <table class="editTable" id="tab-Edit">
                    <tr>
                        <td><label>三包申请单号：</label></td>
                        <td>
                            <input type="text" id="applyOrder-Edit" name="applyOrder-Edit" datasource="APPLY_NO" readonly="readonly"/>
                        </td>
                        <td><label>系统入库单：</label></td>
                        <td>
                            <input type="text" id="inStockOrderNo-Edit" name="inStockOrderNo-Edit" datasource="IN_ORDER_NO" readOnly/>
                        </td>
                        <td><label>系统出库单：</label></td>
                        <td>
                            <input type="text" id="outStockOrderNo-Edit" name="outStockOrderNo-Edit" datasource="OUT_ORDER_NO" readOnly/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>出库日期：</label></td>
                        <td><input type="text" id="outStockDate-Edit" name="outStockDate-Edit" datasource="OUT_DATE" readonly="readonly"/></td>
                        <td><label>客户名称：</label></td>
                        <td>
                            <input type="text" id="customerName-Edit" name="customerName-Edit" datasource="CUSTOMER_NAME" readonly="readonly" />
                        </td>
                        <td><label>联系电话 ：</label></td>
                        <td><input type="text" id="linkPhone-Edit" name="linkPhone-Edit" datasource="PHONE" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td>
                            <input type="text" id="partCode-Edit" name="partCode-Edit" datasource="PART_CODE" readonly="readonly"/>
                        </td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="partName-Edit" name="partName-Edit" datasource="PART_NAME" readonly="readonly" /></td>
                        <td><label>单位 ：</label></td>
                        <td><input type="text" id="unit-Edit" name="unit-Edit" datasource="UNIT" readonly="readonly" /></td>
                    </tr>
                     <tr>
                        <td><label>数量：</label></td>
                        <td><input type="text" id="saleCount-Edit" name="saleCount-Edit" datasource="CLAIM_COUNT" readonly="readonly" /></td>
                        <td><label>经销价：</label></td>
                        <td><input type="text" id="unitPrice-Edit" name="unitPrice-Edit" datasource="SALE_PRICE" readonly="readonly"/></td>
                        <td><label>金额 ：</label></td>
                        <td><input type="text" id="amount-Edit" name="amount-Edit" datasource="AMOUNT" readonly="readonly" /></td>
                    </tr>
                     <tr>
                        <td><label>出库数量：</label></td>
                        <td><input type="text" id="outStockCount-Edit" name="outStockCount-Edit" datasource="OUT_COUNT" readonly="readonly"/></td>
                        <td><label>生产厂家：</label></td>
                        <td colspan="5">
                            <input type="text" id="supplierName-Edit" name="supplierName-Edit" datasource="SUPPLIER_NAME" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>故障情况：</label></td>
                        <td colspan="5"><textarea id="faultConditons-Edit" name="faultConditons-Edit" datasource="FAULT_CONDITONS"  style="width:450px;height:40px;" datatype="1,is_null,500" readonly="readonly" ></textarea></td>
                    </tr>
                    <tr>
                        <td><label>备注：</label></td>
                        <td colspan="5"><textarea id="remarks-Edit" name="remarks-Edit" datasource="REMARKS" style="width:450px;height:40px;" datatype="1,is_null,500" readonly="readonly" ></textarea></td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
        </form>
    </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var dialog = $("body").data("claimCyclSettleAccountsDtl");
    $(function() {
        
        // 修改操作
        var selectedRows = $("#tab-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("tab-Edit",selectedRows[0].attr("rowdata"));

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });
    });
</script>