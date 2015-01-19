<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant" %>
<%
	String accountId = request.getParameter("accountId");
	String accountType = request.getParameter("accountType");
%>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract-details">
						<input type="hidden" id="ACCOUNT_ID" datasource="ACCOUNT_ID" value="<%=accountId%>" operation="=" />
						<input type="hidden" id="ACCOUNT_TYPE" datasource="ACCOUNT_TYPE" value="<%=accountType%>" operation="=" />
						<div class="searchBar" align="left" >
						<table class="searchContent" id="tab-htcx">
							<!-- 定义查询条件 -->
							<tr>
								<td><label>订单编号：</label></td>
								<td>										
									<input type="text" id="ORDER_NO" name="ORDER_NO"  datasource="O.ORDER_NO"  datatype="1,is_null,100" operation="like"/>
								</td>
								<td><label>订单提报日期：</label></td>
								<td>										
									<input type="text" id="APPLY_DATE_B" name="APPLY_DATE_B" style="width: 75px;" datasource="O.APPLY_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -30px;" datasource="O.APPLY_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
								</td>
							</tr>
							<tr>
								<td><label>订单关闭日期：</label></td>
								<td>										
									<input type="text" id="CLOSE_DATE_B" name="CLOSE_DATE_B" style="width: 75px;" datasource="O.CLOSE_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" id="CLOSE_DATE_E" name="CLOSE_DATE_E" style="width: 75px; margin-left: -30px;" datasource="O.CLOSE_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
								</td>
								<td><label>订单状态：</label></td>
								<td>
									<select name="ORDER_STATUS" id="ORDER_STATUS" datasource="ORDER_STATUS" action="show" datatype="1,is_null,100" operation="=">
										<option value="-1">--</option>
										<option value="<%=DicConstant.DDZT_06 %>">已关闭</option>
										<option value="9999">未关闭</option>
									</select>
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search_details" >查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export_details" >导&nbsp;&nbsp;出</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
					</form>
			</div>
			<div class="pageContent">
				<div id="page_contract_details" >
					<table style="display:none;width:100%;" id="detailsTable" name="tablist" ref="page_contract_details" refQuery="fm-searchContract-details" >
							<thead>
								<tr>
									<th fieldname="ORDER_NO">订单编号</th>
									<th fieldname="ORDER_TYPE">订单类型</th>
									<th fieldname="APPLY_DATE">提报日期</th>
									<th fieldname="CLOSE_DATE">关单日期</th>
									<!-- <th fieldname="OCCUPY_FUNDS" refer="amountFormat">占用金额</th> -->
									<th fieldname="OCCUPY_FUNDS" refer="amountFormat" align="right">已占用</th>
									<th fieldname="REPAY_AMOUNT" refer="amountFormat" align="right">已回款</th>
									<th fieldname="SUM_OCCUPY_FUNDS" refer="amountFormat" align="right">剩余占用</th>
									<th fieldname="ORDER_STATUS">关单状态</th>
								</tr>
							</thead>
							<tbody>
		                    </tbody>
					</table>
				</div>
			</div>
		</div>
<form id="exportFm_details" method="post" style="display:none">
	<input type="hidden" id="params_details" name="data" datasource="data" />
</form>
<script type="text/javascript">
$(function(){

	// 查询
	$("#btn-search_details").click(function(){
		var queryAction = "<%=request.getContextPath()%>/channel/channelInfo/AccountBalanceQueryAction/queryListDetailsInfo.ajax";
		var $f = $("#fm-searchContract-details");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"detailsTable");
	});
	
	// 导出
	$("#btn-export_details").click(function(){
		var $f = $("#fm-searchContract-details");
		if (submitForm($f) == false) return false;
		var sCondition = $f.combined() || {};
        $("#params_details").val(sCondition);
		$("#exportFm_details").attr("action","<%=request.getContextPath()%>/part/storageMng/search/DealerLineOfCreditAction/exportExcel.do");
		$("#exportFm_details").submit();
	});
});
</script>