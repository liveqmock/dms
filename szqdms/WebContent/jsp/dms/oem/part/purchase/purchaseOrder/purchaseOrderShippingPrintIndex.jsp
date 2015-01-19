<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>采购发货单打印</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDeliveryPrintAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/orderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	});
});
function doPrint(rowobj) {
	   $row = $(rowobj);
	   var queryUrl = "";
	   var type= $row.attr("PURCHASE_TYPE");
	   if(type == <%=DicConstant.CGDDLX_05%>){
	   		queryUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDeliveryPrintAction/printDirShipPdf.do?SPLIT_ID="+$row.attr("SPLIT_ID");
	   }else{
	   		queryUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderDeliveryPrintAction/printShipPdf.do?SPLIT_ID="+$row.attr("SPLIT_ID");
	   }
	   window.open(queryUrl);
	}
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/purchaseOrderShipping.jsp?action=2", "shippingWin", "订单发货", diaAddOptions);
}
function doSearchOrder(){
	var searchUrl = mngUrl+"/orderSearch.ajax";
	var $f = $("#fm-searchPchOrder");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
}
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("SPLIT_ID")+") class='op'>"+$row.attr("SPLIT_NO")+"</a>";
}
function openDetail(SPLIT_ID){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/purchaseOrderInfoDetail.jsp?SPLIT_ID="+SPLIT_ID, "pchOrderDetail", "采购拆分单明细", options,true);
}
function showLink2(obj)
{
	var $row=$(obj).parent();
	return "<a href='#' onclick=openDetail2('"+$row.attr("ORDER_ID")+"','"+$row.attr("SPLIT_NO")+"') class='op'>"+$row.attr("SALE_ORDER_NO")+"</a>";
} 
function openDetail2(ORDER_ID,SPLIT_NO){
	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/dirSaleOrderInfoDetail.jsp?SPLIT_NO="+SPLIT_NO+"&ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购订单管理   &gt; 采购订单发货单打印</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>订单编号：</label></td>
					    <td><input type="text" id="SPLIT_NO" name="SPLIT_NO" datatype="1,is_digit_letter,30" dataSource="T.SPLIT_NO" operation="like" /></td>
					    <td><label>发货日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.LAST_SHIP_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.LAST_SHIP_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
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
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" refQuery="fm-searchPchOrder" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="SPLIT_NO"refer="showLink">采购订单编号</th>
							<th fieldname="PURCHASE_TYPE" >采购类型</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="SHIP_TIMES" >发货次数</th>
							<th fieldname="FIRST_SHIP_DATE" >首次发货时间</th>
							<th fieldname="LAST_SHIP_DATE" >末次发货时间</th>
							<th fieldname="APPLY_DATE" >制单日期</th>
							<th fieldname="APPLY_USER" >制单人</th>
							<th fieldname="SALE_ORDER_NO" refer="showLink2">销售订单号</th>
							<th colwidth="45" type="link" title="[打印]"  action="doPrint" >操作</th>
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