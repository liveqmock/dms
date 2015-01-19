<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<div id="dia-layout">
    <div >
    <div >
    <form method="post" id="fm-model_Info" class="editForm" >
        <div align="left">
            <fieldset>
            <table class="editTable" id="tab-cash_Edit_Info">
            <input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" />
                <tr>
                    <td><label>供应商代码：</label></td>
                    <td><input type="text" id="dia-SUPPLIER_CODE" name="dia-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  readonly="true"/></td>
                    <td><label>供应商名称：</label></td>
                    <td><input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME" datasource=SUPPLIER_NAME  readonly="true"/></td>
                </tr>
                <tr>
                	<td><label>结算月度：</label></td>
                    <td><input type="text" id="dia-INVOICE_MONTH" name="dia-INVOICE_MONTH" datasource="INVOICE_MONTH"  readonly="true"/></td>
                    <td><label>账户类型：</label></td>
                    <td><input type="text" id="dia-ACCOUNT_TYPE" name="dia-ACCOUNT_TYPE" datasource="ACCOUNT_TYPE" readonly="true" /></td>
                </tr>
                <tr>
                	<td><label>调整金额：</label></td>
                    <td><input type="text" id="dia-AMOUNT" name="dia-AMOUNT" datasource="AMOUNT" datatype="1,is_money,10" /></td>
                </tr>
                <tr>
                    <td><label>备注：</label></td>
                    <td colspan="5"><textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
                </tr>
            </table>
            </fieldset>
        </div>
    </form>
    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
        </ul>
    </div>
    </div>
    </div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/settlement/SupplierSettlementAction";
    $(function () {

        var selectedRows = $("#tab-invoice_info").getSelectedRows();
        var SUM_ID = selectedRows[0].attr("SUM_ID");
        $("#dia-SUM_ID").val(SUM_ID);
        setEditValue("tab-cash_Edit_Info", selectedRows[0].attr("rowdata"));
        //关闭按钮响应
        $("#btn-save").bind("click", function(event){
        	var $f = $("#fm-model_Info");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            sCondition = $("#fm-model_Info").combined(1) || {};
            var updateUrl = diaUrl + "/settleUpdate.ajax?SUM_ID=" + SUM_ID;
            doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
        });
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

    });
    function diaUpdateCallBack(res) {

        var rows = res.getElementsByTagName("ROW");
        try {
            doSearch();
            $.pdialog.closeCurrent();
        } catch(e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>