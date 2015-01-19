<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String account = user.getAccount();
	String name = user.getPersonName();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />

<title>采购订单查询</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/DirectOrderSearchAction";
$(function(){
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/supOrderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	});
});
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("SALE_ORDER_NO")+"</a>";
}
function openDetail(ORDER_ID){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/dirSaleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}

function showLink2(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail2("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
}
function openDetail2(ORDER_ID){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询   &gt; 采购相关   &gt; 直发订单查询(供)</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<tr>
				    	<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,3000"/></td>
					    <td><label>采购订单号：</label></td>
						<td>
					    	<input type="text" id="ORDER_NO" name="ORDER_NO" datasource="T.ORDER_NO" operation="like"  datatype="1,is_null,300"/>
						</td>
					</tr>
					<tr>
					    <td><label>提报单位代码：</label></td>
					    <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="T.ORG_CODE" operation="like"  datatype="1,is_null,300"/></td>
					    <td><label>销售订单号：</label></td>
						<td>
					    	<input type="text" id="SALE_ORDER_NO" name="SALE_ORDER_NO" datasource="T.SALE_ORDER_NO" operation="like"  datatype="1,is_null,300"/>
						</td>
					</tr>
					<tr>
					    <td><label>采购订单状态：</label></td>
					    <td><select type="text" class="combox" id="ORDER_STATUS" name="ORDER_STATUS" kind="dic" src="CGDDZT" datasource="T.ORDER_STATUS" datatype="1,is_null,6" filtercode="<%=DicConstant.CGDDZT_02 %>|<%=DicConstant.CGDDZT_03 %>|<%=DicConstant.CGDDZT_04 %>|<%=DicConstant.CGDDZT_05 %>" readonly="readonly">
					    		<option value="<%=DicConstant.CGDDZT_02 %>" selected>已提报</option>
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
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" refQuery="fm-searchPchOrder" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORDER_NO" refer="showLink2">订单编号</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="SELECT_MONTH" >所属月度</th>
							<th fieldname="ORDER_STATUS" >采购订单状态</th>
							<th fieldname="SALE_ORDER_NO" refer="showLink">销售订单编号</th>
							<th fieldname="ORG_CODE" >提报单位代码</th>
							<th fieldname="ORG_NAME" >提报单位名称</th>
							<th fieldname="APPLY_DATE" >销售订单提报日期</th>
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