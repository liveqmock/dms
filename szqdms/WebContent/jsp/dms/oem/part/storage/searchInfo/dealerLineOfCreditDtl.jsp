<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
	<div class="page">
		<div class="pageHeader" >
			<form method="post" id="fm-searchContract_details">
				<input type="hidden" id="diaOrgId" name="diaOrgId"  datasource="ORG_ID"  datatype="1,is_null,100" value="<%=id%>"/>
				<table class="searchBar" id="dia-oldpartTab_details">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="ORG_CODE_D" datasource="ORG_CODE" readonly="readonly"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="ORG_NAME_D" datasource="ORG_NAME" readonly="readonly"/></td>
					</tr>
				</table>
			</form>
			<div class="searchBar" align="left" >
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="pageContent">
		<div id="page_contract_details" >
			<table style="display:none;width:100%;" id="detailsInfo" name="tablist" ref="page_contract_details" refQuery="dia-oldpartTab_details" >
					<thead>
						<tr>
							<th fieldname="ACCOUNT_TYPE">账户类型</th>
							<th fieldname="AVAILABLE_AMOUNT" refer="amountFormat" align="right">账户余额</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
/**
 * 主信息查询加载
 */
$(function(){
	var selectedRows = $("#invertoryTable").getSelectedRows();
    setEditValue("fm-searchContract_details", selectedRows[0].attr("rowdata"));
    accountInfoByIdSearch();
});
//查询资金列表
function accountInfoByIdSearch(){
    var accountInfoByIdSearchUrl = "<%=request.getContextPath()%>/part/storageMng/search/DealerLineOfCreditAction/queryAccountInfoById.ajax?orgId="+$("#diaOrgId").val();
    var $f = $("#fm-searchContract_details");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, accountInfoByIdSearchUrl, "", 1, sCondition, "detailsInfo");
}
</script>
</html>