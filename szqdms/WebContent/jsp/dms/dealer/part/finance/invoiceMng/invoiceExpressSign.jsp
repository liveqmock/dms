<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
%>
<div id="dia-layout">
    <div style="height:auto;overflow:hidden;">
        <form method="post" id="fm-invoiceInfo" class="editForm">
            <%--隐藏域查询条件--%>
            <input type="hidden" id="dia-SUM_ID" name="dia-SUM_ID" datasource="SUM_ID"/>
            <div align="left">
                <fieldset>
                    <legend align="right"><a onclick="onTitleClick('tab-invoiceInfo')">&nbsp;邮寄信息编辑&gt;&gt;</a>
                    </legend>
                    <table class="editTable" id="tab-invoiceInfo">
                        <tr>
                            <td><label>配送中心代码：</label></td>
                            <td>
                                <input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE" readOnly="true"/>
                            </td>
                            <td><label>配送中心名称：</label></td>
                            <td>
                                <input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" readOnly="true"/>
                            </td>
                            <td><label>开票状态：</label></td>
                            <td>
                                <input type="text" id="dia-INVOICE_STATUS" name="dia-INVOICE_STATUS" datasource="INVOICE_STATUS" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>开票单号：</label></td>
                            <td>
                                <input type="text" id="dia-INVOICE_NO" name="dia-INVOICE_NO" datasource="INVOICE_NO" readOnly="true"/>
                            </td>
                            <td><label>开票日期：</label></td>
                            <td>
                                <input  type="text" name="INVOICE_DATE" id="INVOICE_DATE" datasource="INVOICE_DATE" readOnly="true" />
                            </td>
                            <td><label>开票金额：</label></td>
                            <td>
                                <input type="text" id="dia-INVOICE_AMOUNT" name="dia-INVOICE_AMOUNT" datasource="INVOICE_AMOUNT" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>开票备注：</label></td>
                            <td colspan="6"><textarea readonly rows="3" id="dia-INVOICE_REMARKS" name="dia-INVOICE_REMARKS" datasource="INVOICE_REMARKS" style="width:500px;" datatype="1,is_null,500"></textarea></td>
                        </tr>
                        <tr>
                        	<td><label>快递公司：</label></td>
                            <td>
                                <input type="text" id="dia-EXPRESS_COMPANY" name="dia-EXPRESS_COMPANY" datasource="EXPRESS_COMPANY" datatype="1,is_null,30" readOnly="true"/>
                            </td>
                            <td><label>快递单号：</label></td>
                            <td>
                                <input type="text" id="dia-EXPRESS_NO" name="dia-EXPRESS_NO" datasource="EXPRESS_NO" datatype="1,is_null,300" readOnly="true"/>
                            </td>
                            <td><label>收件人：</label></td>
                            <td>
                                 <input  type="text" name="RECIVE_USER" id="RECIVE_USER" datasource="RECIVE_USER" datatype="1,is_null,300" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>电话：</label></td>
                            <td>
                                <input type="text" id="dia-TEL" name="dia-TEL" datasource="TEL" datatype="1,is_null,300" readOnly="true"/>
                            </td>
                            <td><label>地址：</label></td>
                            <td>
                                <input type="text" id="dia-ADDRESS" name="dia-ADDRESS" datasource="ADDRESS" datatype="1,is_null,300" readOnly="true"/>
                            </td>
                            <td><label>邮寄人：</label></td>
                            <td>
                                <input type="text" id="dia-SENDER" name="dia-SENDER" datasource="SENDER" datatype="1,is_null,300" readOnly="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>邮寄备注：</label></td>
                            <td colspan="6"><textarea class="" rows="3" id="dia-EXPRESS_REMARKS" name="dia-EXPRESS_REMARKS" datasource="EXPRESS_REMARKS" style="width:500px;" datatype="1,is_null,500" readOnly="true"></textarea></td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </form>
        <div class="formBar">
            <ul>
            	<li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="button" id="btn-save">签&nbsp;&nbsp;收</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var diaSaveAction = "<%=request.getContextPath()%>/part/financeMng/invoiceMng/DealerInvoiceMngAction";
    $(function () {
        //移位按钮响应
        $('#btn-save').bind('click', function () {
        	var $f = $("#fm-invoiceInfo");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $f.combined(1) || {};
            var expressUrl = diaSaveAction + "/invoiceSign.ajax?SUM_ID="+$("#dia-SUM_ID").val();
            doNormalSubmit($f, expressUrl, "btn-save", sCondition, diaExpressCallBack);
        });
        var selectedRows = $("#tab-saleOrderList").getSelectedRows();
        setEditValue("fm-invoiceInfo", selectedRows[0].attr("rowdata"));
    })
    var editWin = $("body").data("editWin");
    //邮寄回调函数
    function diaExpressCallBack(res) {
        try {
            var selectedRows = $("#tab-saleOrderList").getSelectedRows();
            $("#tab-saleOrderList").removeResult(selectedRows[0]);
            $.pdialog.close(editWin);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function formatAmount(obj){
        return amountFormatNew($(obj).html());
    }
    function diaSaveCallBack(res){
    	try {
    		doSearch();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>