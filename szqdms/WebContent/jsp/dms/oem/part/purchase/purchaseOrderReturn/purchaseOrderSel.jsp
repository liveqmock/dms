<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPurchase">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>订单编号：</label></td>
					    <td><input type="text" id="SPLIT_NO" name="SPLIT_NO" datatype="1,is_digit_letter,30" dataSource="SPLIT_NO" operation="like" /></td>
					    <td><label>采购类型：</label></td>
					    <td><select type="text" class="combox" id="PURCHASE_TYPE" name="PURCHASE_TYPE" kind="dic" src="CGDDLX" datasource="PURCHASE_TYPE" datatype="1,is_null,6" readonly="readonly">
								    	<option value="-1" selected>--</option>
								    </select></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchOrder" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_purchase" >
			<table style="display:none;width:100%;" id="tab-purchase_info" name="tablist" ref="page_purchase" refQuery="fm-searchPurchase" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="SPLIT_NO" >订单编号</th>
							<th fieldname="PURCHASE_TYPE" >采购类型</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="APPLY_DATE" >制单日期</th>
							<th fieldname="APPLY_USER" >制单人</th>
							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
<script type="text/javascript">
//查询提交方法
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction";
$(function()
{
	$("#btn-searchOrder").bind("click", function(event){
		var searchUrl = mngUrl+"/purchaseSearch.ajax";
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
});
var dialog2 = $("body").data("purchaseOrderSel");
function doOk(rowobj)
{
	try
	{
		SelCallBack(rowobj);	
	}catch(e){}
	
	$.pdialog.close(dialog2);
}
</script>