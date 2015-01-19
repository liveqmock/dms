<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购订单查询</title>
<script type="text/javascript">
$(function(){
	
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/PurchaseOrderSearchAction/queryPurchareOrderInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"purchaseOrderTable");
	})
})
// 明细查询方法
function doDetail(rowobj){
    $("td input:first",$(rowobj)).attr("checked",true);
    var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/purchaseOrderInfoDetails.jsp", "", "车厂库存明细", options ,true);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 车厂库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>采购单号</label></td>
					    <td><input type="text" id="ORDER_NO" name="ORDER_NO" datatype="1,is_digit_letter,30" dataSource="ORDER_NO" operation="like" /></td>
					    <td><label>采购类型</label></td>
					    <td>
					   		<select type="text" id="PURCHASE_TYPE"  name="PURCHASE_TYPE" datasource="PURCHASE_TYPE" kind="dic" src="CGLX" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select> 
					    </td>
					    <td><label>采购类别</label></td>
					    <td>
						    <select type="text" id="PURCHASE_CATEGORY"  name="PURCHASE_CATEGORY" datasource="PURCHASE_CATEGORY" kind="dic" src="CGLB" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>订单类型</label></td>
					    <td>
					    	<select type="text" id="ORDER_TYPE"  name="ORDER_TYPE" datasource="ORDER_TYPE" kind="dic" src="CGDDLX" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><label>月度</label></td>
					    <td><input type="text" id="SELECT_MONTH" name="SELECT_MONTH" datatype="1,is_null,30" dataSource="SELECT_MONTH" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',doubleCalendar:false})" /></td>
					    <td><label>订单状态</label></td>
					    <td>
					    	<select type="text" id="ORDER_STATUS"  name="ORDER_STATUS" datasource="ORDER_STATUS" kind="dic" src="CGDDZT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>供应商名称</label></td>
					    <td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datatype="1,is_digit_letter_cn,30" dataSource="SUPPLIER_NAME" operation="like" /></td>
					    <td><label>供应商代码</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datatype="1,is_digit_letter,30" dataSource="SUPPLIER_CODE" operation="like" /></td>
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
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="purchaseOrderTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" unique="PURCHASE_ID" fieldname="PURCHASE_ID" style="display:none;"></th>
							<th fieldname="ORDER_NO" >采购单号</th>
							<th fieldname="SELECT_MONTH" >月度</th>
							<th fieldname="PURCHASE_TYPE" >采购类型</th>
							<th fieldname="PURCHASE_CATEGORY" >采购类别</th>
							<th fieldname="ORDER_TYPE" >订单类型</th>
							<th fieldname="ORDER_STATUS" >订单状态</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th colwidth="45" type="link" title="[明细]"  action="doDetail" >操作</th>
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