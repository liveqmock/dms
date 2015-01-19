<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车厂库存查询——详情</title>


</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
		<div class="pageContent" style="" >
			<form method="post" id="dia-oldpartfm" class="editForm" >
				<div align="left">
				<table class="editTable" id="dia-oldpartTab">
				    <tr>
						<td ><label>采购单号：</label></td>
						<td colspan="3"><input type="text" id="ORDER_NO"  value="" readonly="readonly"/></td>
						<td ><label>年月：</label></td>
						<td colspan="3"><input type="text" id="SELECT_MONTH"  value="" readonly="readonly"/></td>
						<td ><label>采购类型：</label></td>
						<td colspan="3">
							<input type="text" id="PURCHASE_TYPE"  value="" readonly="readonly"/>
						</td>
						<td ><label>采购类别：</label></td>
						<td colspan="3">
							<input type="text" id="PURCHASE_CATEGORY"  value="" readonly="readonly"/>
					   </td>
					</tr>
				    <tr>
						<td ><label>供应商代码：</label></td>
						<td colspan="3"><input type="text" id="SUPPLIER_CODE"  value="" readonly="readonly"/></td>
						<td ><label>供应商名称：</label></td>
						<td colspan="3"><input type="text" id="SUPPLIER_NAME"  value="" readonly="readonly"/></td>
					</tr>
				</table>
				</div>
			</form>
		</div>
		
		<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="purchasePartInfo" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH"  style="display:none;"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="UNIT" >计量单位</th>
							<th fieldname="MIN_PACK" >最小包装数</th>
							<th fieldname="PCH_COUNT" >采购数量</th>
							<th fieldname="PCH_PRICE" >采购单价</th>
							<th fieldname="PCH_AMOUNT" >金额</th>
							<th fieldname="DELIVERY_CYCLE" >交货周期</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
		
		<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button" id="closeButton">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
</div>
</body>
<script type="text/javascript">

$(function(){
	
	// 获取Index页面审核行对象
	var purchaseId = parent.$("#purchaseOrderTable").getSelectedRows()[0].attr("PURCHASE_ID");
	var selectRowObj = parent.$("#purchaseOrderTable").getSelectedRows()[0];
	
	var getPartInfoAction = "<%=request.getContextPath()%>/part/storageMng/search/PurchaseOrderSearchAction/queryPurchaseOrderPartInfo.ajax?purchaseId="+purchaseId;
	var $f = $("#fm-searchContract");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,getPartInfoAction,"btn-search",1,sCondition,"purchasePartInfo");
	
	// 关闭按钮
	$("#closeButton").click(function(){
		parent.$.pdialog.closeCurrent();
	});
	
	
	// 警告：此方法使用硬编码获取父页面中第三列采购类型，第四列采购类别的值，所以如果父页面调整顺序，则此处代码也需要修改
	$("body").find("input").each(function(index,obj){
		var valStr = selectRowObj.attr($(obj).attr("id"))
		if(valStr){
			if($(obj).attr("id") == "PURCHASE_TYPE"){
				valStr = $(selectRowObj.children()[4]).text();
			}else if($(obj).attr("id") == "PURCHASE_CATEGORY"){
				valStr = $(selectRowObj.children()[5]).text();
			}
			$(obj).val(valStr);
		}
	});
})
</script>
</html>