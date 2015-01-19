<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-splitInfo" class="editForm" >
	<input type="hidden" id="dia-SPLIT_ID" name="dia-SPLIT_ID" datasource="SPLIT_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-transfer_Edit_Info">
				<tr>
				    <td><label>采购拆分单号：</label></td>
				    <td><input type="text" id="dia-SPLIT_NO" name="dia-SPLIT_NO" datasource="SPLIT_NO"  readonly="true"/></td>
					<td><label>计配号：</label></td>
				    <td><input type="text" id="dia-PBNO" name="dia-PBNO"  datasource="PLAN_DISTRIBUTION" datatype="0,is_null,100" /></td>
				</tr>
				<tr>
				    <td><label>供应商：</label></td>
				    <td><input type="text" id="dia-SUPPLIER_NAME" name="dia-SUPPLIER_NAME"  datasource="SUPPLIER_NAME"  readonly="true"/></td>
					<td><label>要求完成日期：</label></td>
				    <td><input type="text" id="REPUIREMENT_TIME" name="REPUIREMENT_TIME"  datasource="REPUIREMENT_TIME"  datatype="0,is_date,30" onclick="WdatePicker({minDate:'%y-%M-%d'})" /></td>
				</tr>
				<tr>
					<td><label>订单类型：</label></td>
				    <td><input type="text" id="dia-PURCHASE_TYPE" name="dia-PURCHASE_TYPE"  datasource="PURCHASE_TYPE" readonly="true"/></td>
					<td><label>制单日期：</label></td>
				    <td><input type="text" id="dia-APPLY_DATE" name="dia-APPLY_DATE"  datasource="APPLY_DATE" readonly="true"/></td>
				</tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" id="btn-save">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderPBNOMngAction";
    $(function () {
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	
    	//保存基本信息按钮响应
        $("#btn-save").bind("click", function(event){
			var $f = $("#fm-splitInfo");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-splitInfo").combined(1) || {};
			var addUrl = diaUrl + "/purchaseOrderUpdate.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		});
    	
		//修改页面赋值
        if (diaAction != "1") {
            var selectedRows = $("#tab-order_info").getSelectedRows();
            setEditValue("fm-splitInfo", selectedRows[0].attr("rowdata"));
        } 
    })
    var row;
    function diaInsertCallBack(){
    	var selectedRows = $("#tab-order_info").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-order_info").removeResult($row);
		$.pdialog.closeCurrent();
    }

</script>