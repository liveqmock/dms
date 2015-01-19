<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>销售出库明细查询</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 财务相关   &gt; 销售出库明细查询</h4>
		<div class="page">
			<div class="pageHeader" >
					<form method="post" id="fm-searchContract">
						<!-- 定义隐藏域条件 -->
						<div class="searchBar" align="left" >
							<table class="searchContent" id="tab-htcx">
								<!-- 定义查询条件 -->
								<tr>
								
									<td><label>订单关闭日期：</label></td>
									<td>
										<input type="text" id="CLOSE_DATE_B" name="CLOSE_DATE_B" style="width: 75px;" datasource="O.CLOSE_DATE" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}', minDate:'#F{$dp.$D(\'CLOSE_DATE_E\', {M:-1})}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                    <input type="text" id="CLOSE_DATE_E" name="CLOSE_DATE_E" style="width: 75px; margin-left: -20px;" datasource="O.CLOSE_DATE" datatype="0,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}', maxDate:'#F{$dp.$D(\'CLOSE_DATE_B\', {M:1})}'})" kind ="date"  operation="<="/>
									</td>
									
									<td><label>出库日期：</label></td>
									<td>
										<input type="text" id="OUT_DATE_B" name="OUT_DATE_B" style="width: 75px;" datasource="SO.OUT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'OUT_DATE_E\')}', minDate:'#F{$dp.$D(\'OUT_DATE_E\', {M:-1})}'})" kind ="date" operation=">="/> 
	                                    <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                    <input type="text" id="OUT_DATE_E" name="OUT_DATE_E" style="width: 75px; margin-left: -20px;" datasource="SO.OUT_DATE" datatype="1,is_null,30"
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'OUT_DATE_B\')}', maxDate:'#F{$dp.$D(\'OUT_DATE_B\', {M:1})}'})" kind ="date"  operation="<="/>
									</td>

								</tr>
								<tr>
									<td><label>出库单号：</label></td>
									<td><input type="text" id="OUT_NO" name="OUT_NO" datasource="SO.OUT_NO"  operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>销售单号：</label></td>
									<td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="SO.ORDER_NO" operation="like" datatype="1,is_digit_letter,30" /></td>
								</tr>
								<tr>
									<td><label>客户代码：</label></td>
									<td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="O.ORG_CODE"  operation="like" datatype="1,is_digit_letter,30" /></td>
									<td><label>客户名称：</label></td>
									<td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="O.ORG_NAME"  operation="like" datatype="1,is_digit_letter_cn,30" /></td>
								</tr>
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PART_NO" name="PART_NO" datasource="I.PART_CODE"  operation="like" datatype="1,is_null,600" /></td>
									<td><label>出库仓库：</label></td>
									<td>										
										<input type="text" id="W_CODE" name="W_CODE" datasource="SO.WAREHOUSE_CODE" datatype="1,is_digit_letter_cn,30" 
											   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
											   operation="=" isreload="true" kind="dic"
										/>
									</td>
								</tr>
								<tr>
			                        <td><label>出库金额（元）：</label></td>
			                        <td><input type="text" id="orderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
			                    	<td><label>经销商金额（元）：</label></td>
			                        <td colspan="3"><input type="text" id="sendOrderAmountSum"  readonly="readonly" style="text-align: right;"/></td>
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
									<th fieldname="PART_CODE">配件代码</th>
									<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
									<th fieldname="WAREHOUSE_NAME" colwidth="150px">出库仓库</th>
									<th fieldname="ORDER_NO" colwidth="160px">销售订单号</th>
									<th fieldname="OUT_NO" colwidth="170px">出库单号</th>
									<th fieldname="OUT_TYPE" colwidth="60px" >出库类型</th>
									<th fieldname="OUT_DATE" colwidth="130px" >出库日期</th>
									<th fieldname="CLOSE_DATE" colwidth="130px">订单关闭日期</th>
									<th fieldname="OUT_AMOUNT" colwidth="50px">出库数量</th>
									<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价</th>
									<th fieldname="PLAN_AMOUNT" refer="amountFormat" align="right">出库金额</th>
									<th fieldname="SALE_PRICE" refer="amountFormat" align="right">经销商价格</th>
									<th fieldname="SALE_AMOUNT" refer="amountFormat" align="right">经销商金额</th>
									<th fieldname="ORG_CODE" colwidth="60px">客户代码</th>
									<th fieldname="ORG_NAME" >客户名称</th>
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
	<form id="searchAmountFm" method="post" style="display:none">
		<input type="hidden" id="paramsAmount" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		if($("#CLOSE_DATE_B").val() == "" || $("#CLOSE_DATE_E").val() == ""){
			alertMsg.warn("请选择订单关闭日期");
			return;
		}
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/OrderStockOutQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		$("#paramsAmount").val(sCondition);	
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
		// 导出
	$("#btn-export").click(function(){
		var $f = $("#fm-searchContract");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/OrderStockOutQueryAction/exportExcel.do");
		$("#exportFm").submit();
	})
	
})

function callbackSearch(responseText, tabId){
   	 	var searchAmountUrl = "<%=request.getContextPath()%>/part/storageMng/search/OrderStockOutQueryAction/getAmount.ajax";
   		sendPost(searchAmountUrl,"", $("#paramsAmount").val() ,callbackShowDetailsInfo,null,null);										   // 调用后台查询Action
   	}
function callbackShowDetailsInfo(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
            for(var i=0;i<rows.length;i++){
            	var unitAmount = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
            	var planAmount = getNodeText(rows[i].getElementsByTagName("SALE_AMOUNT").item(0));
            	$("#orderAmountSum").val(unitAmount);
            	$("#sendOrderAmountSum").val(planAmount);
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>