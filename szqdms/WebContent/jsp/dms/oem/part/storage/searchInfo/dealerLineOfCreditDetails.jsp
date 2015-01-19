<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
	<div class="page">
		<div class="pageHeader" >
				<table class="searchBar" id="dia-oldpartTab_details">
				    <tr>
						<td><label>办事处代码：</label></td>
						<td><input type="text" id="P_ORG_CODE_D" readonly="readonly"/></td>
						<td><label>办事处名称：</label></td>
						<td><input type="text" id="P_ORG_NAME_D" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="ORG_CODE_D" readonly="readonly"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="ORG_NAME_D" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td><label>信用总额：</label></td>
						<td><input type="text" id="BALANCE_AMOUNT_D" readonly="readonly"/></td>
						<td><label>占用额：</label></td>
						<td><input type="text" id="OCCUPY_AMOUNT_D" readonly="readonly"/></td>
					</tr>
				    <tr>
						<td><label>可用额：</label></td>
						<td colspan="3"><input type="text" id="AVAILABLE_AMOUNT_D" readonly="readonly"/></td>
					</tr>
				</table>
		</div>
		<br />
		<div class="pageHeader" >
				<form method="post" id="fm-searchContract_details">
					<!-- 定义隐藏域条件 -->
					<input type="hidden" id="ACCOUNT_ID" name="ACCOUNT_ID"  datasource="F.ACCOUNT_ID"  datatype="1,is_null,100" operation="=" value="<%=id%>"/>
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
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -30px;" datasource="O.APPLY_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
								</td>
								<td><label>订单状态</label></td>
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
			<table style="display:none;width:100%;" id="detailsInfo" name="tablist" ref="page_contract_details" refQuery="fm-searchContract_details" >
					<thead>
						<tr>
							<th fieldname="ORDER_NO">订单号</th>
							<th fieldname="APPLY_DATE">提报日期</th>
							<th fieldname="CLOSE_DATE">关单日期</th>
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
<form id="exportFm_details" method="post" style="display:none">
	<input type="hidden" id="params_details" name="data" datasource="data" />
</form>
<script type="text/javascript">

/**
 * 主信息查询加载
 */
$(function(){
	var id = "<%=id%>";	// 主信息ID
	var getDetailsURL = "<%=request.getContextPath()%>/part/storageMng/search/DealerLineOfCreditAction/queryInfoById.ajax"; // 查询ActionURL
	sendPost(getDetailsURL+"?id="+id,"","",callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
	
	// 查询回调函数
	function callbackShowDetailsInfo(res,sData){
		var applicationInfo;							// 此变量保存回调对象中包含的后台查询到的数据
		var explorer = window.navigator.userAgent;		// 判断浏览器
		// 包含MSIE字符则为IE浏览器，MSIE10.0为IE10，去除IE10浏览器，其他浏览器使用其他另外的读取方式
		if(explorer.indexOf("MSIE") != -1 && explorer.indexOf("MSIE 10.0") == -1){
			applicationInfo = res.text;
		}else{
			applicationInfo = res.firstChild.textContent;
		}
		
		// 调用显示主信息的函数
		showApplicationInfo(eval("(" + applicationInfo + ")"))
	}
	
	// 显示申请单主信息
	function showApplicationInfo(jsonObj){
		var rowData = jsonObj["ROW_0"];			// 获取第一行的数据
		$("input","#dia-oldpartTab_details").each(function(index,obj){
			var inputName = $(obj).attr("id")
			$(obj).val(rowData[inputName]).prop("readonly", true);
		});
		
		// 调用显示主信息的方法
		$("#btn-search_details").click();
	}
	
	// 查询
	$("#btn-search_details").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerLineOfCreditAction/queryOrderInfo.ajax";
		var $f = $("#fm-searchContract_details");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search_details",1,sCondition,"detailsInfo");
	})
	
	// 导出
	$("#btn-export_details").click(function(){
		var $f = $("#fm-searchContract_details");
		if (submitForm($f) == false) return false;
		var sCondition = $f.combined() || {};
        $("#params_details").val(sCondition);
		$("#exportFm_details").attr("action","<%=request.getContextPath()%>/part/storageMng/search/DealerLineOfCreditAction/exportExcel.do");
		$("#exportFm_details").submit();
	})

});


</script>
</html>