<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配送中心订单统计(按类别)</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配送中心订单统计(按类别)</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
									<td><label>配送中心：</label></td>
			                        <td>
			                        	<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
										<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="O.ORG_ID" operation="="/>
			                        </td>
			                        <td><label>关单日期：</label></td>
									<td>
										<input type="text" id="CLOSE_DATE_B" name="CLOSE_DATE_B" style="width: 75px;" datasource="CLOSE_DATE_B" action="show" datatype="1,is_null,30" 
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}'})" />
	                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
	                                    <input type="text" id="CLOSE_DATE_E" name="CLOSE_DATE_E" style="width: 75px; margin-left: -30px;" datasource="CLOSE_DATE_E" action="show" datatype="1,is_null,30" 
			                                   kind ="date"  operation="<="
		                                       onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}'})" />
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
									<th fieldname="ORG_CODE" colwidth="80px" >配送中心代码</th>
									<th fieldname="ORG_NAME" >配送中心名称</th>
									<th fieldname="LT_MM_AMOUNT" colwidth="120px" refer="amountFormat" align="right">轮胎类当月销售累计</th>
									<th fieldname="LT_YYYY_AMOUNT" colwidth="120px" refer="amountFormat" align="right">轮胎类年销量累计</th>
									<th fieldname="LHQ_MM_AMOUNT" colwidth="130px" refer="amountFormat" align="right">离合器类当月销售累计</th>
									<th fieldname="LHQ_YYYY_AMOUNT" colwidth="130px" refer="amountFormat" align="right">离合器类年销售累计</th>
									<th fieldname="YP_MM_AMOUNT" colwidth="120px" refer="amountFormat" align="right">油品类当月销售累计</th>
									<th fieldname="YP_YYYY_AMOUNT" colwidth="120px" refer="amountFormat" align="right">油品类年销售累计</th>
									<th fieldname="LX_MM_AMOUNT" colwidth="120px" refer="amountFormat" align="right">滤芯类当月销售累计</th>
									<th fieldname="LX_YYYY_AMOUNT" colwidth="120px" refer="amountFormat" align="right">滤芯类年销售累计</th>
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
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/OrderCompletionByTypeQueryAction/queryListInfo.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/OrderCompletionByTypeQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

//组织树的回调函数
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#dia-orgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#orgId").val($(res).attr("orgId"));
}
</script>