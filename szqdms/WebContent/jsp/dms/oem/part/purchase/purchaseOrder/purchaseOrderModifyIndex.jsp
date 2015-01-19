<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>采购订单调整</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderModifyAction";
$(function()
{
		var searchUrl = mngUrl+"/orderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/orderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var type = $(rowobj).attr("ORDER_TYPE");
	if(type!=<%=DicConstant.CGDDLX_04%>){
		$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/purchaseOrderModifyEdit.jsp?action=2", "orderModify", "订单调整", diaAddOptions);
	}else{
		$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrder/armyPurchaseOrderModifyEdit.jsp?action=2", "订单调整", "订单调整", diaAddOptions);
	}
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购订单管理   &gt; 采购订单调整</h4>
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
				    	<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="T.SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,3000"/></td>
					    <td><label>制单日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
					<tr>
					    <td><label>采购类型：</label></td>
					    <td><select type="text" class="combox" id="PURCHASE_TYPE" name="PURCHASE_TYPE" kind="dic" src="CGDDLX" datasource="T.PURCHASE_TYPE" filtercode="<%=DicConstant.CGDDLX_01%>|<%=DicConstant.CGDDLX_02%>|<%=DicConstant.CGDDLX_03%>|<%=DicConstant.CGDDLX_04%>" datatype="1,is_null,6" readonly="readonly">
								    	<option value="-1" selected>--</option>
								    </select></td>
					    <td><label>月度：</label></td>
					    <td >
				    		<input type="text" id="in-kscjrq"  name="in-kscjrq" operation="="  dataSource="T.SELECT_MONTH" style="width:75px;"   datatype="1,is_null,30" onclick="WdatePicker({dateFmt:'yyyy-MM'})" />
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
							<th fieldname="SPLIT_NO" refer="showLink" >订单编号</th>
							<th fieldname="PURCHASE_TYPE" >采购类型</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="SUPPLIER_DATE" >供应商确认时间</th>
							<th fieldname="REPUIREMENT_TIME" >要求完成时间</th>
							<th fieldname="APPLY_DATE" >制单日期</th>
							<th fieldname="APPLY_USER" >制单人</th>
							<th colwidth="140" type="link" title="[调整]"  action="doUpdate" >操作</th>
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