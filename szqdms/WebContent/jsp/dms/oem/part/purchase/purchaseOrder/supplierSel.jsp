<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String ACCOUNT = request.getParameter("ACCOUNT");
	String TYPE = request.getParameter("TYPE");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPurchase">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
				</table>
				<div class="subBar" style="display:none">
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
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="ERP_NO" >ERP编码</th>
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
var ACCOUNT = "<%=ACCOUNT%>"
var TYPE = "<%=TYPE%>"
$(function()
{
	$("#btn-searchOrder").bind("click", function(event){
		var searchUrl ="";
		if(TYPE != "2"){
			searchUrl = mngUrl+"/supplierSearch.ajax?ACCOUNT="+ACCOUNT;
		}else{
			searchUrl = mngUrl+"/armySupplierSearch.ajax?ACCOUNT="+ACCOUNT;
		}
		var $f = $("#fm-searchPurchase");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	});
	$("#btn-searchOrder").trigger("click");
});
var dialog2 = $("body").data("supplierSel");
function doOk(rowobj)
{
	try
	{
		SelCallBack(rowobj);	
	}catch(e){}
	
	$.pdialog.close(dialog2);
}

</script>