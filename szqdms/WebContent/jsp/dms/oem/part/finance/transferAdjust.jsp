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
	<form method="post" id="fm-transferInfo" class="editForm" >
		<input type="hidden" id="dia-TRANSFER_ID" name="dia-TRANSFER_ID" datasource="TRANSFER_ID" />
		<input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-transfer_Edit_Info">
				<tr>
				    <td><label>渠道代码：</label></td>
				    <td><input type="text" id="dia-CODE" name="dia-CODE" datasource="CODE"  readonly="true"/></td>
					<td><label>渠道名称：</label></td>
				    <td><input type="text" id="dia-ONAME" name="dia-ONAME"  datasource="ONAME" readonly="true"/></td>
				</tr>
				<tr>
				    <td><label>转出账户类型：</label></td>
				    <td><input type="text" id="dia-OUT_TYPE" name="dia-OUT_TYPE" value="返利账户"  readonly="true"/></td>
					<td><label>转入账户类型：</label></td>
				    <td><input type="text" id="dia-IN_TYPE" name="dia-IN_TYPE" value="材料费"  readonly="true"/></td>
				</tr>
				<tr>
					<td><label>账户余额：</label></td>
				    <td><input type="text" id="dia-BALANCE_AMOUNT" name="dia-BALANCE_AMOUNT" datasource="BALANCE_AMOUNT"  readonly="true"/></td>
					<td><label>转账金额：</label></td>
				    <td>
				    	<input type="text" id="dia-AMOUNT" name="dia-AMOUNT" datasource="AMOUNT" readonly="true"/>
				    </td>
				</tr>
				<tr>
				    <td><label>转账原因：</label></td>
				    <td colspan="5">
					  <textarea id="dia-REMARKS" style="width:450px;height:40px;" name="dia-REMARKS" datasource="REMARKS" readonly="true"></textarea>
				    </td>
				</tr>
				<tr>
				    <td><label>审核意见：</label></td>
				    <td colspan="5">
					  <textarea id="dia-AUDIT_REMARK" style="width:450px;height:40px;" name="dia-AUDIT_REMARK" datasource="AUDIT_REMARK" datatype="1,is_null,500"></textarea>
				    </td>
				</tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-pass">审核通过</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" id="btn-rejected">审核驳回</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>

<script type="text/javascript">
/* 取父页面传过的参数确定新增或者修改 */
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/financeMng/transfer/TransferReportAdjustMngAction";
    $(function () {
    	$("#btn-pass").bind("click", function(event){
			var $f = $("#fm-transferInfo");
			var sCondition = {};
			sCondition = $("#fm-transferInfo").combined(1) || {};
			var aduitUrl = diaUrl + "/transferPass.ajax";
			doNormalSubmit($f,aduitUrl,"btn-pass",sCondition,diaAduitCallBack);
		});
    	$("#btn-rejected").bind("click", function(event){
			var $f = $("#fm-transferInfo");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-transferInfo").combined(1) || {};
			var aduitUrl = diaUrl + "/transferRejected.ajax";
			doNormalSubmit($f,aduitUrl,"btn-rejected",sCondition,diaAduitCallBack);
		});
    	//关闭按钮响应
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
		//修改页面赋值
        if (diaAction != "1") {
            var selectedRows = $("#tab-transfer_info").getSelectedRows();
            setEditValue("fm-transferInfo", selectedRows[0].attr("rowdata"));
            
            var TRANSFER_ID = $("#dia-TRANSFER_ID").val();
         	var url = mngUrl+"/getBalanceAmount.ajax?TRANSFER_ID="+TRANSFER_ID;
       	 	sendPost(url,"","",addTransferCallBack,"false"); 
        } 
    })
    var row;
    function diaAduitCallBack(){
    	var selectedRows = $("#tab-transfer_info").getSelectedRows();
		$row =  selectedRows[0];
		$("#tab-transfer_info").removeResult($row);
		$.pdialog.closeCurrent();
    }
	function addTransferCallBack(res) {
	    try {
		        var rows = res.getElementsByTagName("ROW");
		        var BALANCE_AMOUNT = getNodeText(rows[0].getElementsByTagName("BALANCE_AMOUNT").item(0));
		    	$("#dia-BALANCE_AMOUNT").val(BALANCE_AMOUNT);
		    } catch (e) {
		        alertMsg.error(e);
		        return false;
		    }
		    return true;
	}

</script>