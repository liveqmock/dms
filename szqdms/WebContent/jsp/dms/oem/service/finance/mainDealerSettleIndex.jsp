<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>渠道结算单设置</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var options = {max:false,width:800,height:260,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/DealerSettleSetAction/mainDealerSearch.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#dealerFm");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"dealerList");
	});
	$("#btn-reset").bind("click", function(event){
		$("#settleform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	}); 

});
//设置
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/finance/mainDealerSettleDetail.jsp", "detail", "渠道结算设置", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：财务管理&gt;结算管理&gt;渠道结算单设置</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="dealerFm" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="dealerTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,100" value="" operation="in" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>渠道商名称：</label></td>
							<td><input type="text" id="orgName" name="orgName" datasource="T.DEALER_NAME" operation="like" datatype="1,is_null,100" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="dealer" >
				<table style="display:none;width:100%;" id="dealerList" name="dealerList"  ref="dealer" refQuery="dealerTable">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none" colwidth="10"  ></th>
							<th colwidth="80" fieldname="DEALER_CODE" >渠道商代码</th>
							<th colwidth="200" fieldname="DEALER_SHORTNAME" >渠道商名称</th>
							<th colwidth="80" fieldname="SETTLE_TYPE" >结算单类型</th>
							<th colwidth="120" fieldname="SETTLE_START_DATE" >结算类型执行开始日期</th>
							<th colwidth="120" fieldname="SETTLE_END_DATE" >结算类型执行结束日期</th>
							<th colwidth="120" fieldname="MAKE_START_DATE" >补足开始日期</th>
							<th colwidth="120" fieldname="MAKE_END_DATE" >补足结束日期</th>
							<th colwidth="80" fieldname="INVOICE_TYPE" >发票类型</th>
							<th colwidth="80" fieldname="IF_MAKE_AMOUNT" >是否补足金额</th>
							<th colwidth="40" type="link" title="[设置]"  action="doUpdate" >操作</th>
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