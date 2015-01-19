<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    String orgCode = user.getOrgCode();
    if (action == null)
        action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购订单验收</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/PchStockAcceptMngAction";
var orgCode = "<%=orgCode%>";
$(function()
{
	
	if(orgCode=='SQ10500'){
		$("#PURCHASE_TYPE").attr("filtercode","<%=DicConstant.CGDDLX_04%>");
	}else{
		$("#PURCHASE_TYPE").attr("filtercode","<%=DicConstant.CGDDLX_01%>|<%=DicConstant.CGDDLX_02%>|<%=DicConstant.CGDDLX_03%>|<%=DicConstant.CGDDLX_06%>|<%=DicConstant.CGDDLX_07%>");
	} 
	
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/orderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	});
	$("#btn-search").trigger("click");
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/storage/stockInMng/purchaseOrderAcceptEdit.jsp?action=2", "shippingWin", "采购验收明细", diaAddOptions);
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
	var options = {max:true,width:940,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/purchaseOrderInfoDetail.jsp?SPLIT_ID="+SPLIT_ID, "pchOrderDetail", "采购拆分单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 入库管理   &gt; 采购订单验收</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>采购单号：</label></td>
					    <td><input type="text" id="SPLIT_NO" name="SPLIT_NO" datatype="1,is_digit_letter,30" dataSource="SPLIT_NO" operation="like" /></td>
					    <td><label>采购类型：</label></td>
					    <td>
					    	<select type="text" class="combox" id="PURCHASE_TYPE" name="PURCHASE_TYPE" kind="dic" src="CGDDLX"  datasource="PURCHASE_TYPE" datatype="1,is_null,6" readonly="readonly">
						    	<option value="-1" selected>--</option>
						    </select>
						</td>
					</tr>
					<tr>
						<td><label>计配号：</label></td>
					    <td><input type="text" id="PLAN_DISTRIBUTION" name="PLAN_DISTRIBUTION" datatype="1,is_digit_letter,30" dataSource="PLAN_DISTRIBUTION" operation="like" /></td>
						<td><label>制单日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
					<tr>
						<td><label>配件代码：</label></td>
					    <td>
				    		<input type="text" id="in-partCode"  name="in-partCode" action="show" operation="like"  dataSource="PART_CODE" datatype="1,is_null,300" />
				   		</td>
				   		<td><label>配件名称：</label></td>
					    <td>
				    		<input type="text" id="in-partName"  name="in-partName" action="show" operation="like"  dataSource="PART_NAME" datatype="1,is_null,300" />
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
							<th fieldname="SPLIT_NO" refer="showLink" colwidth="150">订单编号</th>
							<th fieldname="PURCHASE_TYPE" colwidth="75">采购类型</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="WILL_ACCEPT_COUNT" colwidth="90">配件待验收数量</th>
							<th fieldname="APPLY_DATE" colwidth="75">制单日期</th>
							<th fieldname="APPLY_USER" >制单人</th>
							<th fieldname="PLAN_DISTRIBUTION" >计配号</th>
							<th colwidth="45" type="link" title="[验收]"  action="doUpdate" >操作</th>
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