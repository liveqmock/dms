<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="pjsbshxx" style="width:100%;">
    <div class="page">
    <div class="pageHeader" >
        <div class="searchBar" align="left">
            <fieldset>
                <table class="editTable" id="tab-Edit">
                    <tr>
                        <td><label>三包申请单号：</label></td>
                        <td>
                            <input type="hidden" id="applyId-Edit" name="applyId-Edit" datasource="APPLY_ID"/>
                            <input type="text" id="applyOrder-Edit" name="applyOrder-Edit" datasource="APPLY_NO" readonly="readonly"/>
                        </td>
                        <td><label>系统入库单：</label></td>
                        <td>
                            <input type="text" id="inStockOrderNo-Edit" name="inStockOrderNo-Edit" datasource="IN_ORDER_NO" readOnly/>
                            <input type="hidden" id="inStockOrderId-Edit" name="inStockOrderId-Edit" datasource="IN_ORDER_ID"/>
                        </td>
                        <td><label>系统出库单：</label></td>
                        <td>
                            <input type="text" id="outStockOrderNo-Edit" name="outStockOrderNo-Edit" datasource="OUT_ORDER_NO" readOnly/>
                            <input type="hidden" id="outStockOrderId-Edit" name="outStockOrderId-Edit" datasource="OUT_ORDER_ID"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>出库日期：</label></td>
                        <td><input type="text" id="outStockDate-Edit" name="outStockDate-Edit" datasource="OUT_DATE" readonly="readonly"/></td>
                        <td><label>客户名称：</label></td>
                        <td>
                            <input type="text" id="customerName-Edit" name="customerName-Edit" datasource="CUSTOMER_NAME" readonly="readonly" />
                            <input type="hidden" id="linkMan-Edit" name="linkMan-Edit" datasource="LINK_MAN" readonly="readonly" />
                        </td>
                        <td><label>联系电话 ：</label></td>
                        <td><input type="text" id="linkPhone-Edit" name="linkPhone-Edit" datasource="PHONE" readonly="readonly" /></td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td>
                            <input type="text" id="partCode-Edit" name="partCode-Edit" datasource="PART_CODE" readonly="readonly"/>
                            <input type="hidden" id="partId-Edit" name="partId-Edit" datasource="PART_ID"/>
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
                        <td>
                            <input type="text" id="supplierName-Edit" name="supplierName-Edit" datasource="SUPPLIER_NAME" readonly="readonly" datatype="0,is_null,30"/>
                            <input type="hidden" id="supplierId-Edit" name="supplierId-Edit" datasource="SUPPLIER_ID"/>
                            <input type="hidden" id="supplierCode-Edit" name="supplierCode-Edit" datasource="SUPPLIER_CODE"/>
                        </td>
                        <td id="source"><label>原入库单：</label></td>
                        <td id="source1">
	                        <input type="text" id="sourceInNo-Edit" name="sourceInNo-Edit" datatype="1,is_null,100" datasource="SOURCE_IN_NO" readOnly hasBtn="true" callFunction="openSourceOrder()"/>
	                        <input type="hidden" id="sourceInId-Edit" name="sourceInId-Edit" datasource="SOURCE_IN_ID"/>
	                    </td>
                    </tr>
                    <tr>
                    	<td><label>提报单位代码：</label></td>
                        <td><input type="text" id="orgCode-Edit" name="orgCode-Edit" datasource="ORG_CODE" readonly="readonly"/></td>
                        <td><label>提报单位名称：</label></td>
                        <td><input type="text" id="orgName-Edit" name="orgName-Edit" datasource="ORG_NAME" readonly="readonly"/></td>
                    	<td id="source2"><label>原出库单：</label></td>
                        <td id="source3">
	                        <input type="text" id="sourceOutNo-Edit" name="sourceOutNo-Edit" datatype="0,is_null,100" datasource="SOURCE_OUT_NO" readOnly />
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
                    <tr>
                        <td><label>审核意见：</label></td>
                        <td colspan="5"><textarea id="checkRemark-Edit" name="checkRemark-Edit" style="width:450px;height:40px;" datatype="1,is_null,500" datasource="CHECK_REMARK" ></textarea></td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <form method="post" id="fm-Edit" class="editForm" >
            <input type="hidden" id="fm-applyId-Edit" name="fm-applyId-Edit" datasource="APPLY_ID"/>
            <input type="hidden" id="fm-applyStatus-Edit" name="fm-applyStatus-Edit" datasource="APPLY_STATUS"/>
            <input type="hidden" id="fm-checkRemark-Edit" name="fm-checkRemark-Edit" datasource="CHECK_REMARK"/>
            <input type="hidden" id="fm-sourceInNo-Edit" name="fm-applyStatus-Edit" datasource="SOURCE_IN_NO"/>
            <input type="hidden" id="fm-sourceInId-Edit" name="fm-checkRemark-Edit" datasource="SOURCE_IN_ID"/>
        </form>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-pass-edit">审核通过</button></div></div></li>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-return-edit">审核驳回</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var dialog = $("body").data("claimCyclCheckEdit");
    $(function() {
        var selectedRows = $("#tab-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("tab-Edit",selectedRows[0].attr("rowdata"));
        var type = selectedRows[0].attr("ORG_TYPE");
        if(type==<%=DicConstant.ZZLB_09%>){
        	$("#source").hide();
        	$("#source1").hide();
        	$("#source2").hide();
        	$("#source3").hide();
        }else{
        	$("#source").show();
        	$("#source1").show();
        	$("#source2").show();
        	$("#source3").show();
        }
        $("#fm-applyStatus-Edit").val(selectedRows[0].attr("APPLY_STATUS"));
        // 申请单ID
        $("#fm-applyId-Edit").val(selectedRows[0].attr("APPLY_ID"));
        $("#sourceOutNo-Edit").val(selectedRows[0].attr("IN_ORDER_NO"));

        var actionUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclCheckMngAction/claimCyclApplyCheck.ajax";

        // 审核意见文本框绑定
        $("#checkRemark-Edit").change(function(){
            // 审核意见
            $("#fm-checkRemark-Edit").val($("#checkRemark-Edit").val());
        });

        // 审核通过按钮绑定
        $("#btn-pass-edit").click(function(){
            // 审核通过
            var status = <%=DicConstant.PJSBSPZT_04%>;
            $("#fm-applyStatus-Edit").val(status);
            S_NO = $("#sourceInNo-Edit").val();
            S_ID = $("#sourceInId-Edit").val();
            if(!S_ID){
            	 alertMsg.warn('请选择原入库单!');
            	 return false;
            }
            $("#fm-sourceInNo-Edit").val(S_NO);
            $("#fm-sourceInId-Edit").val(S_ID);
            //获取需要提交的form对象
            var $f = $("#fm-Edit");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            //将需要提交的内容拼接成json
            var sCondition = $("#fm-Edit").combined() || {};
            // 审核通过操作
            doNormalSubmit($f, actionUrl, "btn-pass-edit", sCondition, claimCyclApplyCheck);
        });

        // 审核驳回按钮绑定
        $("#btn-return-edit").click(function(){
            // 审核驳回
            var status = <%=DicConstant.PJSBSPZT_03%>;
            $("#fm-applyStatus-Edit").val(status);
            //获取需要提交的form对象
            var $f = $("#fm-Edit");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            //将需要提交的内容拼接成json
            var sCondition = $("#fm-Edit").combined() || {};
            // 审核驳回操作
            doNormalSubmit($f, actionUrl, "btn-return-edit", sCondition, claimCyclApplyCheck);
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });
    });

    // 审核回调函数
    function claimCyclApplyCheck(res) {
        try {
            // 查询配件三包审核
            searchApply();
            $.pdialog.close(dialog);
            return false;
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function openSourceOrder(){
        var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/partClaim/partClaimMng/searchSourceOrder.jsp?partId="+$("#partId-Edit").val(), "searchSourceOrder", "原入库单查询", options);
    }
</script>