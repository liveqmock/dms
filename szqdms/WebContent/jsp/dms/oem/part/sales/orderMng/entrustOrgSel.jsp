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
							<th fieldname="CODE" >渠道代码</th>
							<th fieldname="ONAME" >渠道名称</th>
							<th fieldname="TARIFF" >税号</th>
							<th fieldname="OPEN_BANK" >开户银行</th>
							<th fieldname="BANK_ACCOUNT" >银行账号</th>
							<th fieldname="ADDRESS" >地址</th>
							<th fieldname="BUS_PERSON_TEL" >联系电话</th>
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
var mngUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction";
$(function()
{
	$("#btn-searchOrder").bind("click", function(event){
		var searchUrl =mngUrl+"/orgSearch.ajax";
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