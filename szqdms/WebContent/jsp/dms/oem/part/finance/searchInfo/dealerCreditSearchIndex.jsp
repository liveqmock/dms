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

<title>配送中心可用额度查询</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/searchInfo/DealerCreditInfoSearchAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/creditSearch.ajax";
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-order_info");
	});
	 $("#btn-export").click(function(){
			var $f = $("#fm-searchPchOrder");
			if (submitForm($f) == false) return false;
			var sCondition = {};
	    	sCondition = $f.combined() || {};
	    	$("#params").val(sCondition);
			$("#exportFm").attr("action","<%=request.getContextPath()%>/part/financeMng/searchInfo/DealerCreditInfoSearchAction/exportExcel.do");
			$("#exportFm").submit();
		})
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配送中心可用额度</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道代码：</label></td>
					    <td>
					    	<input type="text" id="orgCode" name="orgCode" datasource="T1.ORG_ID" datatype="1,is_null,300000"  hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" readonly="true" operation="in"/>
					    </td>
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
		<div id="page_order" >
			<table style="display:none;width:100%;" id="tab-order_info" name="tablist" ref="page_order" refQuery="fm-searchPchOrder" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="P_CODE">办事处代码</th>
	                        <th fieldname="P_NAME">办事处名称</th>
	                        <th fieldname="ORG_CODE">配送中心代码</th>
	                        <th fieldname="ORG_NAME">配送中心名称</th>
	                        <th fieldname="BALANCE_AMOUNT"  refer="formatAmount">信用额度</th>
	                        <th fieldname="OCC_AMOUNT"  refer="formatAmount">已关闭订单占用</th>
	                        <th fieldname="SHOULD_REPAY"  refer="formatAmount">未关闭订单占用</th>
	                        <th fieldname="OCCUPY_AMOUNT"  refer="formatAmount">总占用</th>
	                        <th fieldname="AVAILABLE_AMOUNT"  refer="formatAmount">总余额</th>
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