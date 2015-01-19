<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>账户余额查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 财务相关   &gt; 账户余额查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
						<%--
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>订单关闭时间：</label></td>
									<td>
										<input type="text" id="CLOSE_DATE_B" name="CLOSE_DATE_B" style="width: 75px;" datasource="CLOSE_DATE" datatype="1,is_null,30" readonly="readonly"
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}'})" />
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="CLOSE_DATE_E" name="CLOSE_DATE_E" style="width: 75px; margin-left: -30px;" datasource="CLOSE_DATE" datatype="1,is_null,30" readonly="readonly"
			                                     kind ="date"  operation="<="
			                                     onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}'})" />
									</td>
									<td><label>订单类型：</label></td>
									<td>
										<select class="combox" name="ORDER_TYPE" id="ORDER_TYPE" datasource="ORDER_TYPE"  kind="dic" 
												src="<%=DicConstant.DDLX%>" operation="=" datatype="1,is_null,6" 
												>
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>客户名称：</label></td>
									<td colspan="3">
										<input type="text" id="ORG_NAME" name="ORG_NAME" datasource="ORG_NAME"  operation="like" datatype="1,is_digit_letter_cn,30" />
									</td>
								</tr>
							</table> 
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >扣款明细</button></div></div></li>
								</ul>
							</div>
							--%>
						</div>
					</form>
			</div>
			<div class="pageContent">
				<div id="page_contract" >
					<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
							<thead>
								<tr>
									<th fieldname="ACCOUNT_TYPE">账务类型</th>
									<th fieldname="CLOSE_AMOUNT" refer="amountFormat" align="right">期初结转欠款</th>
									<th fieldname="PAY_CLOSE_AMOUNT" refer="amountFormat" align="right">期初欠款已还</th>
									<th fieldname="BALANCE_AMOUNT" refer="amountFormat" align="right">余额</th>
									<!-- <th fieldname="OCCUPY_AMOUNT" refer="amountFormat">占用</th> -->
									<th fieldname="SUM_CLOSE_ORDER_AMOUNT" colwidth="130px" refer="amountFormat" align="right">占用（已关）</th>
									<th fieldname="SUM_OTHER_ORDER_AMOUNT" colwidth="130px" refer="amountFormat" align="right">占用（未关）</th>	
									<th fieldname="AVAILABLE_AMOUNT" refer="amountFormat" align="right">可用余额</th>
									<th type="link" colwidth="85" title="[占用详情]" action="openDetailsInfo" >操作</th>
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
	var queryAction = "<%=request.getContextPath()%>/channel/channelInfo/AccountBalanceQueryAction/queryListInfo.ajax";
	var $f = $("#fm-searchContract");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
})

// 打开详情页面
function openDetailsInfo(row){
	var options = {max:true,mask:true,width:800,height:500,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/ChannelInformationQuery/accountBalanceDetails.jsp?accountId="+$(row).attr("ACCOUNT_ID")+"&accountType="+$(row).attr("ACCOUNT_TYPE"), "accountBalanceDetails", "占用详情", options);
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>