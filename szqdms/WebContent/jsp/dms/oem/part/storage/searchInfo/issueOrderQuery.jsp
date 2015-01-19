<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>发料单查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 发料单查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>发料单号：</label></td>
									<td><input type="text" id="ISSUE_NO" name="ISSUE_NO" datasource="IO.ISSUE_NO" value="" operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>订单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="IO.ORDER_NO" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PART_CODE"  value="" operation="like" datatype="1,is_null,300" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PART_NAME" value="" operation="like" datatype="1,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>发料状态：</label></td>
									<td>
										<select class="combox" name="ISSUE_STATUS" id="ISSUE_STATUS" datasource="IO.ISSUE_STATUS" kind="dic" src="<%=DicConstant.FLDFLZT%>" operation="=" datatype="1,is_null,6" >
											<option value="-1" selected="selected">--</option>
										</select>	
									</td>
									<td><label>订单状态：</label></td>
									<td>
										<select name="ORDER_STATUS" id="ORDER_STATUS" datasource="ORDER_STATUS" datasource="SO.ORDER_STATUS" kind="dic" src="<%=DicConstant.DDZT %>" operation="=" datatype="1,is_null,6">
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>是否打印：</label></td>
									<td>
										<select class="combox" name="PRINT_STATUS" id="PRINT_STATUS" datasource="IO.PRINT_STATUS" kind="dic" src="<%=DicConstant.DYZT%>" operation="=" datatype="1,is_null,6" >
											<option value="-1" selected="selected">--</option>
										</select>	
									</td>
									<td><label>订单类型：</label></td>
									<td>
										<input type="text" id="ORDER_TYPE" name="ORDER_TYPE" datasource="SO.ORDER_TYPE" datatype="1,is_null,30" 
											   src="T#DIC_TREE:ID:DIC_VALUE{ID,DIC_VALUE}:1=1 AND PARENT_ID = 203700 AND ((F_IS_AM(<%=user.getOrgId() %>) = 1 AND ID = 203708) OR (F_IS_AM(<%=user.getOrgId() %>) <> 1 AND ID <> 203708)) ORDER BY ID" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
								</tr>
								<tr>
									<td><label>销售员：</label></td>
									<td><input type="text" id="SALEUSER_NAME" name="SALEUSER_NAME" datasource="SO.SALEUSER_NAME" value="" operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>库管员：</label></td>
									<td><input type="text" id="USER_NAME" name="USER_NAME" datasource="IO.USER_NAME" value="" operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>计划年月：</label></td>
									<td>
										<input type="text" id="PALN_YMONTH_B" name="PALN_YMONTH_B" style="width: 75px;" datasource="PALN_YMONTH" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyyMM',maxDate:'#F{$dp.$D(\'PALN_YMONTH_E\')}'})" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="PALN_YMONTH_E" name="PALN_YMONTH_E" style="width: 75px; margin-left: -30px;" datasource="PALN_YMONTH" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyyMM',minDate:'#F{$dp.$D(\'PALN_YMONTH_B\')}'})" operation="<="/>
									</td>
									<td><label>打印日期：</label></td>
									<td>
										<input type="text" id="PRINT_DATE_B" name="PRINT_DATE_B" style="width: 75px;" datasource="IO.PRINT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'PRINT_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="PRINT_DATE_E" name="PRINT_DATE_E" style="width: 75px; margin-left: -30px;" datasource="IO.PRINT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'PRINT_DATE_B\')}'})" kind ="date"  operation="<="/>
									</td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
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
									<th fieldname="ISSUE_NO" colwidth="175px" refer="showNoInfo">发料单号</th>
									<th fieldname="ISSUE_STATUS" >发料状态</th>
									<th fieldname="ORDER_NO" >订单号</th>
									<th fieldname="ORDER_TYPE" >订单类型</th>
									<th fieldname="ORDER_STATUS" >订单状态</th>
									<th fieldname="ORG_CODE" >客户代码</th>
									<th fieldname="ORG_NAME" >客户名称</th>
									<th fieldname="PALN_YMONTH" >计划年月</th>
									<th fieldname="SALEUSER_NAME" >销售员</th>
									<th fieldname="USER_NAME" >库管员</th>
									<th fieldname="PRINT_STATUS" >发料单打印状态</th>
									<th fieldname="PRINT_DATE" >发料单打印日期</th>
									<th fieldname="PRINT_MAN" >打印人</th>
									<th colwidth="145" type="link" title="[标签打印]|[发料单打印]"  action="doTitlePrint|doPrint" >操作</th>
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
		<input type="hidden" id="params" name="data"></input>
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/IssueOrderQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
})

// 入库单号超链接
function showNoInfo($cell){
	$tr = $cell.parent();
	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("ISSUE_ID")+"\")'>"+$tr.attr("ISSUE_NO")+"</a>";
}

// 打开详细页面
function openDetailPage(issueId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/issueOrderQueryForDetails.jsp?issueId="+issueId, "forDetailsPage", "发料单详情", options);
}
function doPrint(rowobj) {
    $row = $(rowobj);
    var issueId = $row.attr("ISSUE_ID");
	var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/printPdf.do?ISSUE_ID="+issueId+"&flag=2";
   window.open(queryUrl);
}
function doTitlePrint(rowobj){
	$row = $(rowobj);
	var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/printTitlePdf.do?ISSUE_ID="+$row.attr("ISSUE_ID");
    window.open(queryUrl);
}
</script>