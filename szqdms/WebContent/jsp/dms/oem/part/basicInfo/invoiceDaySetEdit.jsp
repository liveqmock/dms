<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null) {
        action = "1";
    }
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent" style="">
            <form method="post" id="dia-fm-invoiceDaySet" class="editForm">
                <input type="hidden" id="diaDayId" name="diaDayId" datasource="DAY_ID"/>
                <div align="left">
                      <table class="editTable" id="dia-tab-invoiceDaySet">
                           <tr>
                           	  <td><label>结算类型：</label></td>
                              <td><input type="text" id="diaInvoiceType" name="diaInvoiceType" kind="dic" src="PJJSLX" datasource="INVOICE_TYPE" datatype="0,is_null,30"/></td>
                              <td><label>结算周期：</label></td>
                              <td>
                              	<input type="text" group="diaStartDate,diaEndDate"  id="diaStartDate" kind="date" name="diaStartDate" style="width:70px;" datasource="START_DATE" datatype="0,is_null,30" onclick="WdatePicker()"/>
                            	<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            	<input type="text" group="diaStartDate,diaEndDate"  id="diaEndDate" kind="date" name="diaEndDate" style="width:70px;margin-left:-30px;" datasource="END_DATE" datatype="0,is_null,30" onclick="WdatePicker()"/>
                              </td>
                          </tr>
                      </table>
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
    var diaUrl = "<%=request.getContextPath()%>/part/basicInfoMng/InvoiceDaySetAction";
    var actionFlag = "<%=action%>";
    //初始化
    $(function () {
        $("#btn-save").bind("click", function (event) {
            var $f = $("#dia-fm-invoiceDaySet");
            if (submitForm($f) == false) return false;
            var sCondition = {};
            sCondition = $("#dia-fm-invoiceDaySet").combined(1) || {};
            if (actionFlag == 1) {
                var addUrl = diaUrl + "/invoiceDaySetInsert.ajax";
                doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
            } else {
                var updateUrl = diaUrl + "/invoiceDaySetUpdate.ajax";
                doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
            }
        });
		if(actionFlag!="1"){
            var selectedRows = $("#tab-searchList").getSelectedRows();
            setEditValue("dia-fm-invoiceDaySet", selectedRows[0].attr("rowdata"));
            $("#diaInvoiceType").attr("readonly","readonly");
        }
    });
    //新增回调函数
    function diaInsertCallBack(res) {
        try {
        	var rows = res.getElementsByTagName("ROW");
    		if(rows && rows.length > 0)
    		{
    			$("#diaDayId").val(getNodeText(rows[0].getElementsByTagName("DAY_ID").item(0)));
    			$("#diaInvoiceType").attr("readonly","readonly");
    			 var url = "<%=request.getContextPath()%>/part/basicInfoMng/InvoiceDaySetAction/invoiceDaySetSearch.ajax";
    			 var $f = $("#searchForm");
                 var sCondition = {};
                 sCondition = $f.combined() || {};
                 doFormSubmit($f, url, "btn-search", 1, sCondition, "tab-searchList");
    		}
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {
        try {
        	var url = "<%=request.getContextPath()%>/part/basicInfoMng/InvoiceDaySetAction/invoiceDaySetSearch.ajax";
			var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, url, "btn-search", 1, sCondition, "tab-searchList");
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>