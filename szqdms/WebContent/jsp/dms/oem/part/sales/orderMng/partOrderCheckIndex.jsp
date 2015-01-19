<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单审核</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderCheckAction";
//初始化方法
$(function(){
	$("#orderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_07%>|<%=DicConstant.DDLX_08%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>");
	//查询方法
	$("#btn-search").bind("click",function(event){
		doSearch();
	});



	$("#btn-reset").bind("click", function(event){
			$("#searchForm")[0].reset();
			$("#orgCode").attr("code","");
			$("#orgCode").val("");
		});
});
function doSearch(){
	var $f = $("#searchForm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    var searchUrl = url+"/partOrderCheckSearch.ajax";
    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "orderList");
}
//列表审核链接
function doCheck(rowobj)
{	$("td input[type=radio]", $(rowobj)).attr("checked", true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/partOrderCheckDetail.jsp", "orderCheck", "订单审核明细", options);
}
function amountFormat(obj){
	return amountFormatNew($(obj).html());
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 订单审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="searchForm">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="searchTab">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>订单编号：</label></td>
						<td><input type="text" id="orderNo" name="orderNo" datasource="T.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
						<td><label>订单类型：</label></td>
						<td>
							<select type="text" id="orderType" name="orderType" kind="dic" src="DDLX" datasource="T.ORDER_TYPE" filtercode="" datatype="1,is_null,30" operation="=">
								<option value="-1" selected>--</option>
							</select>
						</td>
				    	<td><label>提报日期：</label></td>
						<td>
							<input type="text" group="startDate,endDate" id="startDate" kind="date" name="startDate" style="width:75px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
							<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="startDate,endDate" id="endDate" kind="date" name="endDate" style="width:75px;margin-left:-30px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
						</td>
					</tr>
					<tr>
						<td><label>渠道商：</label></td>
					    <td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" operation="IN"/></td>
					    <td><label>是否延期订单：</label></td>
						<td>
						<select type="text" id="delayCount" name="delayCount" kind="dic" src="SF" datasource="T.IF_DELAY_ORDER" filtercode="" datatype="1,is_null,30" operation="=">
						    <option value="-1" selected>--</option>
						</select>
						</td>
					    <td><label>延期次数：</label></td>
						<td><input type="text" id="delayCount" name="delayCount" datasource="SUBSTR(T.ORDER_NO, -1)" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>底盘号：</label></td>
						<td><input type="text" id="vin" name="vin" datasource="T.VIN" datatype="1,is_null,17" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="orderListDiv" >
			<table style="display:none;width:100%;" id="orderList" name="orderList" ref="orderListDiv" refQuery="searchTab">
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORDER_NO" colwidth="145" refer="showLink">订单编号</th>
							<th fieldname="ORDER_TYPE">订单类型</th>
							<th fieldname="IF_DELAY_ORDER">是否延期订单</th>
							<th fieldname="DELAY_COUNT">延期次数</th>
							<th fieldname="ORG_NAME">渠道商</th>
							<th fieldname="WAREHOUSE_NAME">供货库</th>
							<th fieldname="EXPECT_DATE" >期望到货日期</th>
							<th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">总金额(元)</th>
							<th fieldname="CREATE_USER" >提报人</th>
							<th fieldname="APPLY_DATE" colwidth="130">提报时间</th>
							<th fieldname="REMARKS" maxlength="20">备注</th>
							<th fieldname="VIN" maxlength="20">底盘号</th>
							<th fieldname="RATE" colwidth="85" align="right">数量满足率(%)</th>
							<th fieldname="RATE1" colwidth="85" align="right">品种满足率(%)</th>
							<th type="link" title="[审核]"  action="doCheck" >操作</th>
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