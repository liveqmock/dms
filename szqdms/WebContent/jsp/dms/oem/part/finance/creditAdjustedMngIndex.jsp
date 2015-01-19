<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<%@ page import="com.org.dms.common.DicConstant" %>
<title>额度调整</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/financeMng/creditMng/CreditAdjustedMngAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		searchCredit();
	});
	$("#btn-search").trigger("click");

	$("#btn-reset").bind("click", function(event){
			$("#fm-searchCredit")[0].reset();
			$("#dealerCode").attr("code","");
			$("#dealerCode").val("");
		});
});
function searchCredit(){
	var searchUrl = mngUrl+"/searchCredit.ajax";
	var $f = $("#fm-searchCredit");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-credit_info");
}
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/finance/creditAdjustedEdit.jsp?action=2", "额度调整", "额度调整", diaAddOptions);
}
function formatAmount(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 财务管理  &gt; 信用账户管理   &gt; 额度调整</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchCredit">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-creditSearch">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道代码：</label></td>
					    <td>
					    	<input type="text" id="dealerCode" name="dealerCode" datasource="ORG_ID" datatype="1,is_null,300000" hasBtn="true" callFunction="showOrgTree('dealerCode',1,1,1);" readonly="true" operation="in"/>
					    </td>
					    <td><label>渠道名称：</label></td>
					    <td>
					    	<input type="text" id="ORG_NAME" name="ORG_NAME" datasource="ORG_NAME"  operation="like"/>
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_credit" >
			<table style="display:none;width:100%;" id="tab-credit_info" name="tablist" ref="page_credit" refQuery="fm-searchCredit" >
					<thead>
						<tr>
							<!-- <th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORG_CODE" >渠道代码</th>
							<th fieldname="ORG_NAME" >渠道名称</th>
							<th fieldname="NOW_LIMIT" refer="formatAmount" align="right">信用额度</th>
							<th fieldname="OCCUPY_FUNDS" refer="formatAmount" align="right">欠款金额</th>
							<th colwidth="45" type="link" title="[调整]"  action="doUpdate" >操作</th> -->
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ORG_CODE" >渠道代码</th>
							<th fieldname="ORG_NAME" >渠道名称</th>
							<th fieldname="CREDIT_TYPE" >额度类型</th>
							<th fieldname="NOW_LIMIT" refer="formatAmount" align="right">信用额度</th>
							<th fieldname="YS_DATE" >有效日期</th>
							<th fieldname="CREATE_USER" >添加人</th>
							<th fieldname="CREATE_TIME" >添加日期</th>
							<th fieldname="REMARKS" >备注</th>
							<th colwidth="45" type="link" title="[调整]"  action="doUpdate" >操作</th>
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