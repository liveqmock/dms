<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<title>供应商信息查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaSupplierAction/supplierInfoQuery.ajax";
var exportUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaSupplierAction/exportExcel.ajax";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchSupplierInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-supplierInfoList");
	});
	
	$("#btn-export").click(function(){
		alert(searchUrl);
		alert(exportUrl);
		var $f = $("#fm-searchSupplierInfo");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action",exportUrl);
		$("#exportFm").submit();
	})
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 采购相关   &gt; 供应商信息查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchSupplierInfo">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchSupplierInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>供应商代码：</label></td>
					    <td><input type="text" id="supplierCode" name="supplierCode" datasource="A.SUPPLIER_CODE" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %>" datatype="1,is_null,30"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_supplierInfoList" >
			<table style="display:none;width:100%;" id="tab-supplierInfoList" name="tablist" ref="page_supplierInfoList" refQuery="tab-searchSupplierInfo" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SUPPLIER_CODE" >厂家代码</th>								
							<th fieldname="SUPPLIER_NAME" >厂家名称</th>	
							<th fieldname="INVOICE_TYPE" >发票类型</th>
							<th fieldname="TAX_RATE" >税率(%)</th>			
							<th fieldname="SUPPLIER_QUALIFY" >厂家资质</th>			
							<th fieldname="LEGAL_PERSON" >厂家法人</th>			
							<th fieldname="LEGAL_PERSON_PHONE" >法人联系方式</th>			
							<th fieldname="BUSINESS_PERSON" >业务联系人</th>		
							<th fieldname="BUSINESS_PERSON_PHONE" >联系方式</th>
							<th fieldname="OPEN_ACCOUNT" >结算周期(月)</th>
							<th fieldname="EFFECT_DATE" >有效期(天)</th>
							<th fieldname="GUARANTEE_MONEY" >质保金</th>
							<th fieldname="WARRANTY_PERIOD" >质保期</th>
							<th fieldname="RECOVERY_CLAUSE" >追偿条款</th>
							<th fieldname="REMARKS" >备注</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data" datasource="data" />
</form>
</body>
</html>