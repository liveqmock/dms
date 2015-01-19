<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配送中心订单统计</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配送中心订单统计</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<td><label>配送中心：</label></td>
		                        <td>
		                        	<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
									<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="ORG_ID" operation="="/>
		                        </td>
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
									<th fieldname="ORG_CODE" colwidth="80px" >配送中心代码</th>
									<th fieldname="ORG_NAME" colwidth="90px" >配送中心名称</th>
									<th fieldname="YYYY_ORDER_AMOUNT" colwidth="110px" refer="amountFormat" align="right">年度订单累计金额</th>
									<th fieldname="YYYY_REAL_AMOUNT" colwidth="110px" refer="amountFormat" align="right">年度订单实发金额</th>
									<th fieldname="MM_ORDER_AMOUNT" colwidth="110px" refer="amountFormat" align="right">本月订单累计金额</th>
									<th fieldname="MM_REAL_AMOUNT" colwidth="110px" refer="amountFormat" align="right">本月订单实发金额</th>
									<th fieldname="MM_RATE" colwidth="70px">本月实发率</th>
									<th fieldname="WW_ORDER_AMOUNT" colwidth="110px" refer="amountFormat" align="right">本周订单累计金额</th>
									<th fieldname="WW_REAL_AMOUNT" colwidth="110px" refer="amountFormat" align="right">本周订单实发金额</th>
									<th fieldname="WW_RATE" colwidth="70px" >本周实发率</th>
									<th fieldname="BALANCE_AMOUNT" colwidth="100px" refer="amountFormat" align="right">可用信用度</th>
									<th fieldname="PART_AMOUNT" colwidth="120px" refer="amountFormat" align="right">实时库存金额</th>
									<th fieldname="BA_AMOUNT" colwidth="100px" refer="amountFormat" align="right">应收账款</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/OrderCompletionQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/OrderCompletionQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

// 组织树的回调函数
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#dia-orgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#orgId").val($(res).attr("orgId"));
}
</script>