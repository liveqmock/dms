<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>油品采购量统计</title>
<script type="text/javascript">
var searchUrl1 = "<%=request.getContextPath()%>/part/salesMng/search/DealerOilSaleInfoSearchMngAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimOutBuyform");
		$("#claimNoInfo").val($("#claimNo").val());
		var claimNo=$("#claimNoInfo").val();
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl1+"/saleSearch.ajax";
		doFormSubmit($f,url,"search",1,sCondition,"claimOutBuylist");
	});
	$("#export").bind("click",function(){
		var $f = $("#claimOutBuyform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/search/DealerOilSaleInfoSearchMngAction/saleDownload.do");
		$("#exportFm").submit();
	});
});
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#dia-orgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#ORG_CODE").val($(res).attr("orgCode"));
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询&gt;销售相关&gt;渠道商油品采购量统计</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimOutBuyform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimOutBuyTable">
						<tr>
							<td><label>渠道商名称：</label></td>
						    <td>
								<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,'',2)" operation="="/>
								<input type="hidden" id="ORG_CODE" name="ORG_CODE" datatype="1,is_null,30" datasource="ORG_CODE" action = "show" operation="="/>
							</td>
							<td><label>渠道类别：</label></td>
							<td>
								<select name="ORG_TYPE" id="ORG_TYPE" datatype="1,is_null,1000" operation="="  datasource="B.ORG_TYPE">
									<option value="-1">--</option>
									<option value="<%=DicConstant.ZZLB_09%>">配送中心</option>
									<option value="<%=DicConstant.ZZLB_10%>">配件经销商</option>
									<option value="<%=DicConstant.ZZLB_11%>">服务商</option>
								</select>
							</td>
							<td><label>起止时间：</label></td>
							<td>
								<input type="text" id="IN_OUT_DATE_B" name="IN_OUT_DATE_B" datasource="BEGIN_DATE" style="width: 75px;" datatype="0,is_null,30"
	                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'IN_OUT_DATE_E\')}'})" kind ="date" action="show" operation=">="/> 
	                                  <span style="float: left; margin-left: -40px; margin-top: 5px;">至</span>
	                                  <input type="text" id="IN_OUT_DATE_E" name="IN_OUT_DATE_E" datasource="END_DATE" style="width: 75px; margin-left: -20px;"  datatype="0,is_null,30"
	                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'IN_OUT_DATE_B\')}', maxDate: '%y-%M-%d'})" kind ="date" action="show" operation="<="/>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="claimOutBuylist" name="claimOutBuylist" ref="preAuth" refQuery="claimOutBuyTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ONAME" >渠道商名称</th>
							<th fieldname="AMOUNT" refer="amountFormat" align="right">金额</th>
							<th fieldname="BEGIN_DATE" >开始时间</th>
							<th fieldname="END_DATE" >结束时间</th>
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
	<input type="hidden" id="params" name="data"></input>
	<input type="hidden" id="claimNoInfo" name="data"></input>
</form>
<form id="exportFm1" method="post" style="display:none">
	<input type="hidden" id="params1" name="data"></input>
	<input type="hidden" id="claimNoInfo1" name="data"></input>
</form>
</body>
</html>