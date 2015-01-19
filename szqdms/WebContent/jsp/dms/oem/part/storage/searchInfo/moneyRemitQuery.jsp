<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>回款信息查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 回款信息查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>办事处：</label></td>
									<td><input type="text" id="AGENCY_NAME" name="AGENCY_NAME" datasource="AGENCY_NAME"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
									<td><label>办事处编号：</label></td>
									<td><input type="text" id="AGENCY_CODE" name="AGENCY_CODE" datasource="AGENCY_CODE"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>配送中心：</label></td>
									<td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="ORG_NAME"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
									<td><label>配送中心编号：</label></td>
									<td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="ORG_CODE"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>票据种类：</label></td>
									<td>
										<select class="combox" name="AMOUNT_TYPE" id="AMOUNT_TYPE" datasource="AMOUNT_TYPE"  kind="dic" 
												src="<%=DicConstant.ZJZHLX%>" operation="=" datatype="1,is_null,6" 
												filtercode="<%=DicConstant.ZJZHLX_01%>|<%=DicConstant.ZJZHLX_02%>"
												>
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
									<td><label>录入日期：</label></td>
									<td>
										<input type="text" id="FILIING_DATE_B" name="FILIING_DATE_B" style="width: 75px;" datasource="FILIING_DATE" datatype="1,is_null,30" 
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'FILIING_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="FILIING_DATE_E" name="FILIING_DATE_E" style="width: 75px; margin-left: -30px;" datasource="FILIING_DATE" datatype="1,is_null,30" 
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'FILIING_DATE_B\')}'})" kind ="date"  operation="<="/>
									</td>
								</tr>
								<tr>
									<td><label>入账日期：</label></td>
									<td>
										<input type="text" id="TO_ACCOUNT_DATE_B" name="TO_ACCOUNT_DATE_B" style="width: 75px;" datasource="TO_ACCOUNT_DATE" datatype="1,is_null,30" 
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'TO_ACCOUNT_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="TO_ACCOUNT_DATE_E" name="TO_ACCOUNT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="TO_ACCOUNT_DATE" datatype="1,is_null,30" 
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'TO_ACCOUNT_DATE_B\')}'})" kind ="date"  operation="<="/>
									</td>
									<td><label>金额：</label></td>
									<td>
										<input type="text" id="BILL_AMOUNT_B" name="BILL_AMOUNT_B" style="width: 75px;" datasource="BILL_AMOUNT" datatype="1,is_digit,30"operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="BILL_AMOUNT_E" name="BILL_AMOUNT_E" style="width: 75px; margin-left: -30px;" datasource="BILL_AMOUNT" datatype="1,is_digit,30" operation="<="/>	
									</td>
								</tr>
								<tr>
									<td><label>入账状态：</label></td>
									<td colspan="3">
										<select class="combox" name="REMIT_STATUS" id="REMIT_STATUS" datasource="REMIT_STATUS" operation="=" datatype="1,is_null,6" >
											<option value="-1" selected="selected">--</option>
											<option value="<%=DicConstant.DKZT_02%>">未入账</option>
											<option value="<%=DicConstant.DKZT_03%>">已入账</option>
										</select>
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
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
									<th fieldname="REMIT_ID" >流水号</th>
									<th fieldname="AGENCY_CODE" >办事处编号</th>
									<th fieldname="AGENCY_NAME" >办事处</th>
									<th fieldname="ORG_CODE" >配送中心编号</th>
									<th fieldname="ORG_NAME" >配送中心</th>
									<th fieldname="AMOUNT_TYPE" >票据种类</th>
									<th fieldname="REMIT_STATUS_NAME" >入账状态</th>
									<th fieldname="DRAFT_NO" >承兑汇票号</th>
									<th fieldname="BILL_AMOUNT" >票据金额</th>
									<th fieldname="TO_ACCOUNT_DATE" >入账日期</th>
									<th fieldname="FILIING_DATE" >录入日期</th>
									<th fieldname="UPDATE_USER" >确认人</th>
									<th fieldname="UPDATE_TIME" >确认日期</th>
									<th fieldname="REMARK" >备注</th>
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
		<input type="hidden" id="data" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/MoneyRemitQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	// 导出按钮绑定
    $("#btn-export-index").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/MoneyRemitQueryAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
})
</script>