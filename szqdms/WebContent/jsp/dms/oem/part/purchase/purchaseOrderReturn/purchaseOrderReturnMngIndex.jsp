
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>采购退货申请</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseReturn/PurchaseOrderReturnMngAction";
$(function()
{
		var searchUrl = mngUrl+"/returnOrderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-returnOrder_info");
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/returnOrderSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-returnOrder_info");
	});
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/purchase/purchaseOrderReturn/purchaseOrderReturnMngAdd.jsp?action=1", "新增退货单", "新增退货单", diaAddOptions);
	});
});

function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/purchase/purchaseOrderReturn/purchaseOrderReturnMngAdd.jsp?action=2", "修改退货单", "修改退货单", diaAddOptions);
}
var row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var deleteUrl = mngUrl + "/returnOrderDelete.ajax?&RETURN_ID=" + $(rowobj).attr("RETURN_ID");
	sendPost(deleteUrl,"delete","",deleteReturnCallBack,"true");
}
var row;
function  deleteReturnCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-returnOrder_info").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doRetReport(rowobj)
{
	$row = $(rowobj);
	
	
	var count = $(rowobj).attr("COUNT");
	if(count){
		var url = mngUrl + "/returnReport.ajax?RETURN_ID="+$(rowobj).attr("RETURN_ID");
		sendPost(url,"report","",retReportReturnCallBack,"true");
	}else{
		alertMsg.warn('请先维护采购订单所需配件信息!');
	}
}
function  retReportReturnCallBack(res)
{
	try
	{
		
		if($row) 
			$("#tab-returnOrder_info").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doSearchOrder(){
	var searchUrl = mngUrl+"/returnOrderSearch.ajax";
	var $f = $("#fm-searchPchOrder");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-returnOrder_info");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购退货管理   &gt; 采购退货申请</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>退货单号：</label></td>
					    <td><input type="text" id="RETURN_NO" name="RETURN_NO" datatype="1,is_digit_letter,30" dataSource="RETURN_NO" operation="like" /></td>
				    	<td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,3000"/></td>
					    <td><label>制单日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="ORDER_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="ORDER_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
					<!-- <tr>
				    	<td><label>原采购单号：</label></td>
					    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="BORDER_NO" operation="like" /></td>
					</tr> -->
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-returnOrder_info" name="tablist" ref="page_order" refQuery="fm-searchPchOrder" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="RETURN_NO" >退货单号</th>
							<!-- <th fieldname="PURCHASE_NO" >原采购单号</th> -->
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="ORDER_DATE" >制单日期</th>
							<th fieldname="ORDER_USER" >制单人</th>
							<th fieldname="WAREHOUSE_ID" style="display:none">仓库ID</th>
							<th fieldname="RETURN_ID" style="display:none">退货ID</th>
							<th colwidth="140" type="link" title="[提报]|[编辑]|[删除]"  action="doRetReport|doUpdate|doDelete" >操作</th>
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