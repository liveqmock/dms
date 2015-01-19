<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
    <div class="page">
    <div class="pageContent">
        <form method="post" id="fm-Edit" class="editForm" >
            <div align="left">
            <fieldset>
            <table class="editTable" id="tab-Edit">
                <tr>
                    <td><label>三包申请单号：</label></td>
                    <td>
                        <input type="hidden" id="applyId-Edit" name="applyId-Edit" datasource="APPLY_ID"/>
                        <input type="text" id="applyOrder-Edit" name="applyOrder-Edit" datatype="1,is_null,100" datasource="APPLY_NO" readonly="readonly" value="系统自动生成"/>
                    </td>
                    <td><label>系统入库单：</label></td>
                    <td>
                        <input type="text" id="inStockOrderNo-Edit" name="inStockOrderNo-Edit" datatype="1,is_null,100" datasource="IN_ORDER_NO" readOnly hasBtn="true" callFunction="openInStockOrder()"/>
                        <input type="hidden" id="inStockOrderId-Edit" name="inStockOrderId-Edit" datasource="IN_ORDER_ID"/>
                    </td>
                    <td><label>系统出库单：</label></td>
                    <td>
                        <input type="text" id="outStockOrderNo-Edit" name="outStockOrderNo-Edit" datatype="1,is_null,100" datasource="OUT_ORDER_NO" readOnly hasBtn="true" callFunction="openOutStockOrder()"/>
                        <input type="hidden" id="outStockOrderId-Edit" name="outStockOrderId-Edit" datasource="OUT_ORDER_ID"/>
                    </td>
                </tr>
                <tr>
                    <td><label>出库日期：</label></td>
                    <td><input type="text" id="outStockDate-Edit" name="outStockDate-Edit" datatype="1,is_null,100" datasource="OUT_DATE" readonly="readonly"/></td>
                    <td><label>客户名称：</label></td>
                    <td>
                        <input type="text" id="customerName-Edit" name="customerName-Edit" datatype="1,is_null,100" datasource="CUSTOMER_NAME" readonly="readonly" />
                        <input type="hidden" id="linkMan-Edit" name="linkMan-Edit" datatype="1,is_null,100" datasource="LINK_MAN" readonly="readonly" />
                    </td>
                    <td><label>联系电话 ：</label></td>
                    <td><input type="text" id="linkPhone-Edit" name="linkPhone-Edit" datatype="1,is_null,200" datasource="PHONE" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td><label>配件代码：</label></td>
                    <td>
                        <input type="text" id="partCode-Edit" name="partCode-Edit" class="combox" kind="dic" readOnly datatype="0,is_null,100" datasource="PART_CODE"
                               src="" />
                        <input type="hidden" id="partId-Edit" name="partId-Edit" datasource="PART_ID"/>
                    </td>
                    <td><label>配件名称：</label></td>
                    <td><input type="text" id="partName-Edit" name="partName-Edit" datasource="PART_NAME" readonly="readonly" /></td>
                    <td><label>单位 ：</label></td>
                    <td><input type="text" id="unit-Edit" name="unit-Edit" datasource="UNIT" readonly="readonly" /></td>
                </tr>
                 <tr>
                    <td><label>数量：</label></td>
                    <td><input type="text" id="saleCount-Edit" name="saleCount-Edit" datatype="0,is_double,100" datasource="CLAIM_COUNT" /></td>
                    <td><label>经销价：</label></td>
                    <td><input type="text" id="unitPrice-Edit" name="unitPrice-Edit" datasource="SALE_PRICE" readonly="readonly"/></td>
                    <td><label>金额 ：</label></td>
                    <td><input type="text" id="amount-Edit" name="amount-Edit" datasource="AMOUNT" readonly="readonly" /></td>
                </tr>
                 <tr>
                    <td><label>出库数量：</label></td>
                    <td><input type="text" id="outStockCount-Edit" name="outStockCount-Edit" datatype="1,is_null,100" datasource="OUT_COUNT" readonly="readonly"/></td>
                    <td><label>生产厂家：</label></td>
                    <td colspan="5">
                        <input type="text" id="supplierName-Edit" name="supplierName-Edit" datasource="SUPPLIER_NAME" readonly="readonly"/>
                        <input type="hidden" id="supplierId-Edit" name="supplierId-Edit" datasource="SUPPLIER_ID"/>
                        <input type="hidden" id="supplierCode-Edit" name="supplierCode-Edit" datasource="SUPPLIER_CODE"/>
                    </td>
                </tr>
                <tr>
                    <td><label>故障情况：</label></td>
                    <td colspan="5"><textarea id="faultConditons-Edit" name="faultConditons-Edit" datasource="FAULT_CONDITONS"  style="width:450px;height:40px;" datatype="1,is_null,500" ></textarea></td>
                </tr>
                <tr>
                    <td><label>备注：</label></td>
                    <td colspan="5"><textarea id="remarks-Edit" name="remarks-Edit" datasource="REMARKS" style="width:450px;height:40px;" datatype="1,is_null,500" ></textarea></td>
                </tr>
            </table>
            </fieldset>
            </div>
        </form>
        <div id="formBar" class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save-edit">保&nbsp;&nbsp;存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-apply-edit">提&nbsp;&nbsp;报</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    var action=<%=action%>;
    var dialog = $("body").data("claimCyclApplyEdit");
    $(function(){

        var actionUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclApplyMngAction/";
        if (action == '1') {
            // 保存操作
            // 隐藏提报按钮
            $("#formBar").find("li:eq(1)").hide();
        } else {
            // 修改操作
            var selectedRows = $("#tab-index").getSelectedRows();
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
            setEditValue("tab-Edit",selectedRows[0].attr("rowdata"));
            var srcVal = "T#PT_BU_SALE_ORDER_DTL A,PT_BU_REAL_SALE_DTL B,DIC_TREE C:B.PART_CODE:B.PART_NAME"
                + "{A.UNIT_PRICE,B.PART_CODE,B.PART_ID,B.PART_NAME,C.DIC_CODE,C.DIC_VALUE,B.SALE_COUNT,B.AMOUNT,B.SUPPLIER_ID,"
                + "B.SUPPLIER_CODE,B.SUPPLIER_NAME}:1=1 "
                + " AND B.UNIT=C.ID"
                + " AND A.PART_ID = B.PART_ID "
                + " AND A.ORDER_ID='" + $("#inStockOrderId-Edit").val() + "' AND B.SALE_ID='" + $("#outStockOrderId-Edit").val() + "'"
                + "   AND B.PART_ID || B.SUPPLIER_ID || '" + $("#outStockOrderId-Edit").val() + "'  NOT IN"
                + "     (SELECT C.PART_ID || C.SUPPLIER_ID || C.OUT_ORDER_ID FROM PT_BU_CLAIM_APPLY C)";
            $("#partCode-Edit").attr("src",srcVal);
        }

        // 保存按钮绑定
        $("#btn-save-edit").click(function(){
            // 数量
            var saleCount = $("#saleCount-Edit").val();
            // 出库数量
            var outCount = $("#outStockCount-Edit").val();
            if (parseInt(saleCount)-parseInt(outCount)>0) {
                alertMsg.warn("请输入正确的数量！");
                return false;
            }
            //获取需要提交的form对象
            var $f = $("#fm-Edit");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            //将需要提交的内容拼接成json
            var sCondition = $("#fm-Edit").combined() || {};
            if (action == '1') {
                // 保存操作
                doNormalSubmit($f, actionUrl+"insertClaimCyclApply.ajax", "btn-save-edit", sCondition, diaInsertCallBack);
                action='2';
            } else {
                // 修改操作
                doNormalSubmit($f, actionUrl+"updateClaimCyclApply.ajax", "btn-save-edit", sCondition, diaUpdateCallBack);
            }
        });

        // 提报按钮绑定
        $("#btn-apply-edit").click(function(){
            var url = actionUrl+"applyClaimCyclApply.ajax?applyId="+$("#applyId-Edit").val();
            sendPost(url, "", "", diaApplyCallBack, "true");
        });

        // 数量文本框绑定
        $("#saleCount-Edit").blur(function(){
            // 数量
            var saleCount = $("#saleCount-Edit").val();
            // 出库数量
            var outCount = $("#outStockCount-Edit").val();
            // 经销商价
            var unitPrice = $("#unitPrice-Edit").val();
            var reg = /^[0-9]*$/;
            if(reg.test(saleCount) == false) {
                alertMsg.warn("请输入正确的数量！");
                return false;
            }
             // 数量>出库数量
            if (parseInt(saleCount)-parseInt(outCount)>0) {
                alertMsg.warn("请输入正确的数量！");
                return false;
            }  
            $("#amount-Edit").val(saleCount*unitPrice);
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            parent.$.pdialog.close(dialog);
            return false;
        });
    });

    // 保存回调函数
    function diaInsertCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true有重复数据;)
            $("#applyOrder-Edit").val(getNodeText(rows[0].getElementsByTagName("APPLY_NO").item(0)));
            $("#applyId-Edit").val(getNodeText(rows[0].getElementsByTagName("APPLY_ID").item(0)));
            $("#tab-index").insertResult(res,0);
            // 显示提报按钮
            $("#formBar").find("li:eq(1)").show();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 提报回调函数
    function diaApplyCallBack(res) {
        try {
            // 查询配件三包申请
            searchClaimCyclApply();
            $.pdialog.closeCurrent();
            return false;
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 修改回调函数
    function diaUpdateCallBack() {
        try {
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 入库单查询
    function openInStockOrder(){
        var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/partClaim/partClaimMng/searchInStockOrder.jsp", "searchInStockOrder", "入库单查询", options);
    }

    // 出库单查询
    function openOutStockOrder(){
        var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/partClaim/partClaimMng/searchOutStockOrder.jsp", "searchOutStockOrder", "出库单查询", options);
    }

    // 数据字典回调函数
    function afterDicItemClick(id,$row){
    	//配件代码
    	$("#partCode-Edit").val($row.attr("B.PART_CODE"));
    	$("#partCode-Edit").attr("code",$row.attr("B.PART_CODE"));
        // 配件名称
        $("#partId-Edit").val($row.attr("B.PART_ID"));
        // 配件名称
        $("#partName-Edit").val($row.attr("B.PART_NAME"));
        // 出库数量
        $("#outStockCount-Edit").val($row.attr("B.SALE_COUNT"));
        // 单位
        $("#unit-Edit").val($row.attr("C.DIC_VALUE"));
        $("#unit-Edit").attr("code",($row.attr("C.DIC_CODE")));
        // 经销商价
        $("#unitPrice-Edit").val($row.attr("A.UNIT_PRICE"));
        // 生产厂家id
        $("#supplierId-Edit").val($row.attr("B.SUPPLIER_ID"));
        // 生产厂家code
        $("#supplierCode-Edit").val($row.attr("B.SUPPLIER_CODE"));
        // 生产厂家名
        $("#supplierName-Edit").val($row.attr("B.SUPPLIER_NAME"));
        return true;
    }

    // 数据清空方法
    function clearPart() {
        var srcVal = "T#PT_BU_SALE_ORDER_DTL A,PT_BU_REAL_SALE_DTL B,DIC_TREE C:B.PART_CODE:B.PART_NAME"
            + "{A.UNIT_PRICE,B.PART_ID,B.PART_CODE,B.PART_NAME,C.DIC_CODE,C.DIC_VALUE,B.SALE_COUNT,B.AMOUNT,B.SUPPLIER_ID,"
            + "B.SUPPLIER_CODE,B.SUPPLIER_NAME}:1=1 "
            + " AND B.UNIT=C.ID"
            + " AND A.PART_ID = B.PART_ID"
            + " AND A.ORDER_ID='" + $("#inStockOrderId-Edit").val() + "' AND B.SALE_ID='" + $("#outStockOrderId-Edit").val() + "'"
            + "   AND B.PART_ID || B.SUPPLIER_ID || '" + $("#outStockOrderId-Edit").val() + "'  NOT IN"
            + "     (SELECT C.PART_ID || C.SUPPLIER_ID || C.OUT_ORDER_ID FROM PT_BU_CLAIM_APPLY C)";
        $("#partCode-Edit").attr("src",srcVal);
        // 配件ID
        $("#partId-Edit").val('');
        // 配件代码
        $("#partCode-Edit").val('');
        // 配件名称
        $("#partName-Edit").val('');
        // 出库数量
        $("#saleCount-Edit").val('');
        // 出库数量
        $("#outStockCount-Edit").val('');
        // 单位
        $("#unit-Edit").val('');
        $("#unit-Edit").attr("code",'');
        // 经销商价
        $("#unitPrice-Edit").val('');
        // 金额
        $("#amount-Edit").val('');
        // 生产厂家id
        $("#supplierId-Edit").val('');
        // 生产厂家code
        $("#supplierCode-Edit").val('');
        // 生产厂家名
        $("#supplierName-Edit").val('');
    }
</script>