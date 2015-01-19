<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>转账申请</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String ACCOUNT_ID = request.getParameter("ACCOUNT_ID");
%>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/cashAccountMng/CashAccountMngAction";
var ACCOUNT_ID = <%=ACCOUNT_ID%>
$(function()
{
	var searchUrl = mngUrl+"/occupationDetail.ajax?ACCOUNT_ID="+ACCOUNT_ID;
	var $f = $("#fm-searchCash");
	var sCondition = {};
   	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"",1,sCondition,"tab-cashOccupation_info");
});
function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
}
function openDetail(ORDER_ID){
	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchCash">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-cashOccupationSearch">
				</table>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_cashOccupation" >
			<table style="display:none;width:100%;" id="tab-cashOccupation_info" name="tablist" ref="page_cashOccupation" refQuery="fm-searchCash" >
					<thead>
						<tr>
							<th type="single" name="XH" display="none"></th>
							<th fieldname="ORG_NAME" >渠道名称</th>
							<th fieldname="ORG_CODE" >渠道代码</th>
							<th fieldname="ORDER_NO" refer="showLink">订单号</th>
							<th fieldname="ORDER_TYPE" >订单类型</th>
							<th fieldname="OCCUPY_FUNDS"refer="formatAmount" align="right" >占用金额</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>