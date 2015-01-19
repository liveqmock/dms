<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>付款明细查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 财务相关   &gt; 付款明细查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>供应商代码：</label></td>
									<td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SU.SUPPLIER_CODE"  operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>供应商名称：</label></td>
									<td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datasource="SU.SUPPLIER_NAME"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>付款形式：</label></td>
									<td>
										<select id="ACCOUNT_TYPE" name="ACCOUNT_TYPE"  datasource="ACCOUNT_TYPE" kind="dic" src="<%=DicConstant.GYSZHLX %>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
									<td><label>月度：</label></td>
									<td>
										<input type="text" id="INVOICE_MONTH_B" name="INVOICE_MONTH_B" style="width: 75px;" datasource="INVOICE_MONTH" datatype="1,is_null,30" 
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'INVOICE_MONTH_E\')}'})" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="INVOICE_MONTH_E" name="INVOICE_MONTH_E" style="width: 75px; margin-left: -30px;" datasource="INVOICE_MONTH" datatype="1,is_null,30" 
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'INVOICE_MONTH_B\')}'})" operation="<="/>
									</td>
								</tr>
								<tr>
								    <td><label>付款金额：</label></td>
									<td>
										<input type="text" id="SETTLE_AMOUNT_B" name="SETTLE_AMOUNT_B" style="width: 75px;" datasource="SETTLE_AMOUNT" datatype="1,is_digit,30"  operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="SETTLE_AMOUNT_E" name="SETTLE_AMOUNT_E" style="width: 75px; margin-left: -30px;" datasource="SETTLE_AMOUNT" datatype="1,is_digit,30"  operation="<="/>
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
			</div>
			<div class="pageContent">
				<div id="page_contract" >
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<tr>
									<th fieldname="INVOICE_MONTH" colwidth="130px">月度</th>
									<th fieldname="SUPPLIER_CODE" colwidth="70px">供应商编码</th>
									<th fieldname="SUPPLIER_NAME">供应商名称</th>
									<th fieldname="ACCOUNT_TYPE" colwidth="60px">付款形式</th>
									<th fieldname="SETTLE_AMOUNT">付款金额</th>
								</tr>
							</thead>
							<tbody>
		                    </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
 	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/SummaryDtlQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
		// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/SummaryDtlQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})

</script>