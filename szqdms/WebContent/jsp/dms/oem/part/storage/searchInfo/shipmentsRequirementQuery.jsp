<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant" %>
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
<title>发货满足率统计</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 发货满足率统计</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>统计周期：</label></td>
									<td>
										<input type="text" id="CLOSE_DATE_B" name="CLOSE_DATE_B" style="width: 75px;" datasource="APPLY_DATE_B" datatype="0,is_null,30" 
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}'})" />
	                                    <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                    <input type="text" id="CLOSE_DATE_E" name="CLOSE_DATE_E" style="width: 75px; margin-left: -20px;" datasource="APPLY_DATE_E" datatype="0,is_null,30" 
			                                     kind ="date"  operation="<="
			                                     onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}'})" />
									</td>
									<td><label>组织类别</label></td>
									<td>
										<select name="ORG_TYPE" id="ORG_TYPE" datasource="O.ORG_TYPE" datatype="0,is_null,30" 
												kind="dic" src="ZZLB"  filtercode="200005|200006|200007"
										>
											<option value="-1">--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>订单类型：</label></td>
									<td>
										<input type="text" id="ORDER_TYPE" name="ORDER_TYPE" datasource="SO.ORDER_TYPE" datatype="1,is_null,30" 
											   src="T#DIC_TREE:ID:DIC_VALUE{ID,DIC_VALUE}:1=1 AND PARENT_ID = 203700 AND ((F_IS_AM(<%=user.getOrgId() %>) = 1 AND ID = 203708) OR (F_IS_AM(<%=user.getOrgId() %>) <> 1 AND ID <> 203708)) ORDER BY ID" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
									<td><label>订单状态</label></td>
									<td>
										<select name="ORDER_STATUS" id="ORDER_STATUS"  datasource="SO.ORDER_STATUS" kind="dic" src="<%=DicConstant.DDZT %>" operation="=" datatype="1,is_null,10"
											filtercode="<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_06%>"
										>
											<option value="-1" selected="selected">--</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>是否延期：</label></td>
									<td colspan="3">
										<select name="IF_DELAY_ORDER" id="IF_DELAY_ORDER" datasource="SO.IF_DELAY_ORDER" kind="dic" src="<%=DicConstant.SF %>" operation="=" datatype="1,is_null,10">
											<option value="-1" selected="selected">--</option>
										</select>
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
									<th fieldname="ORG_NAME">渠道名称</th>
									<th fieldname="ORDER_TYPE" colwidth="80px">订单类型</th>
									<th fieldname="PLAN_CATEGORY_COUNT" colwidth="80px">计划品种数</th>
									<th fieldname="EXE_CATEGORY_COUNT" colwidth="80px">执行品种数</th>
									<th fieldname="RATE_CATEGORY_COUNT" colwidth="80px">品种执行率</th>
									<th fieldname="PLAN_ORDER_PART_COUNT" colwidth="80px">计划数量</th>
									<th fieldname="EXE_ORDER_PART_COUNT" colwidth="80px">完成数量</th>
									<th fieldname="RATE_ORDER_PART_COUNT" colwidth="70px">数量完成率</th>
									<th fieldname="PLAN_AMOUNT" colwidth="100px" refer="amountFormat" align="right">计划金额</th>
									<th fieldname="EXE_AMOUNT" colwidth="100px" refer="amountFormat" align="right">完成金额</th>
									<th fieldname="RATE_AMOUNT" colwidth="65px">金额完成率</th>
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
	
	// 查询
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/ShipmentsRequirementAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
})

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>