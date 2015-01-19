<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>结算单查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/SettleInvoiceMailAction/settleInvoiceSearch.ajax";
var options = {max:false,width:800,height:400,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		var $f = $("#settleInvoiceMailform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"settleInvoiceMailList");
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/finance/settleInvoiceMailDetail.jsp", "settleInvoiceMail", "结算发票邮寄", options);
}
//金额格式化
function amountFormat(obj){
  return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：结算管理&gt;结算管理&gt;结算发票邮寄</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="settleInvoiceMailform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="settleInvoiceMailTable">
					<tr>
						<td><label>结算单号：</label></td>
						<td><input type="text" id="settleNo" name="settleNo"  operation="like" datasource="T.SETTLE_NO"  datatype="1,is_null,30" value="" /></td>
						<td><label>结算产生日期：</label></td>
						<td>
							<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" datasource="T.SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						    <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
						    <input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.SETTLE_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
				   		<td><label>结算类型：</label></td>
						<td><select type ="text" id="settleType" name="settleType" datasource="T.SETTLE_TYPE" class="combox" kind="dic" src="JSLX" datatype="1,is_null,6">
								<option value=-1>--</option>
							</select>
						</td>
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
		<div id="settleInvoiceMail">
			<table style="display:none;width:100%;" id="settleInvoiceMailList" name="settleInvoiceMailList" ref="settleInvoiceMail" refQuery="settleInvoiceMailTable">
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none" ></th>
						<th fieldname="SETTLE_NO" colwidth="60" ordertype="local" class="desc">结算单号</th>
						<th fieldname="SETTLE_DATE" colwidth="80">结算产生日期</th>
						<th fieldname="SETTLE_TYPE" colwidth="60">结算类型</th>
						<th fieldname="CLAIM_COUNT" colwidth="60">索赔单数</th>
						<th fieldname="COSTS" align="right" colwidth="80" refer="amountFormat">服务费/材料费</th>
						<th fieldname="RE_COSTS" align="right" colwidth="100" refer="amountFormat">旧件运费/配件返利</th>
						<th fieldname="POLICY_SUP" align="right" colwidth="60" refer="amountFormat">政策支持</th>
						<th fieldname="CASH_GIFT" align="right" colwidth="60" refer="amountFormat">礼金</th>
						<th fieldname="CAR_AWARD" align="right" colwidth="60" refer="amountFormat">售车奖励</th>
						<th fieldname="AP_COSTS" align="right" colwidth="60" refer="amountFormat">考核费用</th>
						<th fieldname="OTHERS" align="right" colwidth="60" refer="amountFormat">其他费用</th>
						<th fieldname="SUMMARY" align="right" colwidth="80" refer="amountFormat">索赔总金额(元)</th>
						<th colwidth="40" type="link"  title="[邮寄]" action="doUpdate">操作</th>
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