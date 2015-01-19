<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>出库单查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 三包延期订单查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>订单号：</label></td>
									<td>
										<input type="text" id="ORDER_NO" name="ORDER_NO" datasource="T.ORDER_NO" datatype="1,is_null,300" operation="like"/>
									</td>
									<td><label>订单申请日期：</label></td>
									<td>
										<input type="text" id="APPLY_DATE_B" name="APPLY_DATE_B" style="width: 75px;" datasource="T.APPLY_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'APPLY_DATE_E\')}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -30px;" datasource="T.APPLY_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'APPLY_DATE_B\')}'})" kind ="date"  operation="<="/>
									</td>
								</tr>
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_CODE" name="PART_CODE" datasource="TD.PART_CODE" operation="like" datatype="1,is_null,600" /></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PART_NAME" name="PART_NAME" datasource="TD.PART_NAME" operation="like" datatype="1,is_null,600" /></td>
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
									<th fieldname="ORDER_NO" colwidth="175px">订单号</th>
									<th fieldname="APPLY_DATE" colwidth="130px">申请日期</th>
									<th fieldname="ORG_CODE" colwidth="100px">渠道代码</th>
									<th fieldname="ORG_NAME" colwidth="175px">渠道名称</th>
									<th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">订单金额</th>
									<th fieldname="PART_CODE" colwidth="160px" refer="partStyle">配件代码</th>
									<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
									<th fieldname="ORDER_COUNT" colwidth="60px" refer="partStyle">申请数量</th>
									<th fieldname="AMOUNT" colwidth="60px">库存数量</th>
									<th fieldname="OCCUPY_AMOUNT" colwidth="60px">占用数量</th>
									<th fieldname="AVAILABLE_AMOUNT" colwidth="60px" refer="partStyle">可用数量</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/ThreeDelayOrdersQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
})

// 金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

// 配件字体变红
function partStyle(obj){
	return "<div style='color:red;'>"+$(obj).html()+"</div>";
}

</script>