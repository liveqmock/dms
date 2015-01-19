<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.OrgDept" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgId = user.getOrgId();
	String orgType = user.getOrgDept().getOrgType();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>本部订单查询</title>
<script type="text/javascript">
$(function(){
	var orgType = '<%=orgType%>';
	if(orgType=="<%=DicConstant.ZZLB_09%>"){
		$("#orderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>");
<%--         		$("#warehouseCode").attr("src","T#PT_BA_WAREHOUSE:WAREHOUSE_ID:WAREHOUSE_NAME:1=1 AND WAREHOUSE_TYPE=100101 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01%>"); --%>
	}
	if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
		
		$("#orderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_07%>|<%=DicConstant.DDLX_09%>|<%=DicConstant.DDLX_10%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>");
<%--         		$("#warehouseCode").attr("src","T#TM_ORG:ORG_ID:ONAME:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND IS_DS=<%=DicConstant.SF_01%> UNION SELECT A.ORG_ID,A.ONAME,1,0,'','' FROM TM_ORG A,PT_BA_SERVICE_DC B WHERE A.ORG_ID = B.DC_ID AND B.STATUS =<%=DicConstant.YXBS_01%> AND B.ORG_ID =<%=orgId%>"); --%>
	}

	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerSalesOrderSearchAction/salesOrderQuery.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"dealerSalesOrderTable");
	})
	// 导出按钮绑定
    $("#btn-export-index").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/DealerSalesOrderSearchAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
})
// 明细查询方法
function doDetail(orderNoObj){
 	var orderId = $(orderNoObj).parent().parent().parent().attr("ORDER_ID");
 	openDetail(orderId);
}


function openDetail(ORDER_ID){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
// 订单编号格式化为超链接
function orderNoStyle(cellObj){
	return "<a href='javascript:void(0)' onclick='doDetail(this)' style='color:red;'>"+$(cellObj).html()+"</a>";
}

// 金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 订单相关   &gt; 本部订单查询</h4>
	<div class="page" >
	<div class="pageHeader" >
	    <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>订单编号</label></td>
					    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="ORDER_NO" operation="like" /></td>
					    <td><label>订单类型</label></td>
					    <td>
					    	<select type="text" id="orderType"  name="orderType" filtercode="" src="DDLX" datasource="ORDER_TYPE" kind="dic" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
                    <tr>
                    	<td><label>配件代码</label></td>
                    	<td>
                    		<input type="text" name="partCode" id="partCode" dataSource="PART_CODE" operation="like" datatype="1,is_null,300" />
                    	</td>
                    	<td><label>配件名称</label></td>
                    	<td>
                    		<input type="text" name="partName" id="partName" dataSource="PART_NAME" operation="like" datatype="1,is_null,300" />
                    	</td>
                    </tr>
					<tr>
					    <td><label>订单状态</label></td>
					    <td>
					   		<select type="text" id="ORDER_STATUS"  name="ORDER_STATUS" datasource="ORDER_STATUS" filtercode="<%=DicConstant.DDZT_02%>|<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_04%>|<%=DicConstant.DDZT_05%>|<%=DicConstant.DDZT_06%>|<%=DicConstant.DDZT_07%>" kind="dic" src="DDZT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select> 
					    </td>
					    <td><label>发运状态</label></td>
					    <td>
						    <select type="text" id="SHIP_STATUS"  name="SHIP_STATUS" datasource="SHIP_STATUS" kind="dic" src="DDFYZT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><label>开票状态</label></td>
					    <td>
					    	<select type="text" id="INVOICE_STATUS"  name="INVOICE_STATUS" datasource="INVOICE_STATUS" kind="dic" src="KPZT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>提报日期</label></td>
					    <td>
					    	<input  type="text" id="APPLY_DATE_B" name="APPLY_DATE_B" style="width: 75px;" datasource="APPLY_DATE" datatype="1,is_digit_letter,30" 
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'APPLY_DATE_E\')}'})" /> 
                            <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                            <input  type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -30px;" datasource="APPLY_DATE" datatype="1,is_digit_letter,30"
                                     kind ="date"  operation="<="
                                     onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'APPLY_DATE_B\')}'})" />
					    </td>
					</tr>
					<tr>
					    <td><label>订单关闭日期</label></td>
					    <td>
					    	<input  type="text" id="CLOSE_DATE_B" name="CLOSE_DATE_B" style="width: 75px;" datasource="CLOSE_DATE" datatype="1,is_digit_letter,30" 
			                                    kind ="date" operation=">="
			                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d1',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'CLOSE_DATE_E\')}'})" /> 
                            <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                            <input  type="text" id="CLOSE_DATE_E" name="CLOSE_DATE_E" style="width: 75px; margin-left: -30px;" datasource="CLOSE_DATE" datatype="1,is_digit_letter,30"
                                     kind ="date"  operation="<="
                                     onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,vel:'d2',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'CLOSE_DATE_B\')}'})" />
					    </td>
                        <td><label>联系电话</label></td>
                        <td><input type="text" id="PHONE" name="PHONE" datatype="1,is_null,300" datasource="T.PHONE" operation="like"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="dealerSalesOrderTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" unique="ORDER_ID" fieldname="ORDER_ID" style="display:none;"></th>
							<th fieldname="ORDER_NO" refer="orderNoStyle" colwidth="130px">订单编号</th>
							<th fieldname="ORDER_TYPE" colwidth="50px">订单类型</th>
							<th fieldname="ORDER_STATUS" colwidth="50px">订单状态</th>
							<th fieldname="SHIP_STATUS" colwidth="70px">发运状态</th>
							<th fieldname="INVOICE_STATUS" colwidth="50px">开票状态</th>
							<th fieldname="TRANS_TYPE" colwidth="50px">运输方式</th>
							<th fieldname="LINK_MAN" >联系人</th>
							<th fieldname="PHONE" >联系电话</th>
							<th fieldname="IF_DELAY_ORDER" colwidth="50px">是否延期</th>
							<th fieldname="APPLY_DATE" colwidth="130px">提报日期</th>
							<th fieldname="ORDER_AMOUNT" refer="amountFormat" colwidth="120px">订单总金额</th>
							<th fieldname="REAL_AMOUNT" refer="amountFormat" colwidth="120px">实发总金额</th>
							<th fieldname="CLOSE_DATE" colwidth="130px">关闭订单日期</th>
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