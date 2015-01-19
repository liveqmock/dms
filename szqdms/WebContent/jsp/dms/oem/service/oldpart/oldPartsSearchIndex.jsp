<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>应返旧件查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		var rorgCode=$("#R_ORGCODE").attr("code");
		var sCondition = {};
		if(rorgCode==undefined){
    		rorgCode=0;
    	}
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/oldPartOemSearch.ajax?rorgCode="+rorgCode;
		doFormSubmit($f,url,"search",1,sCondition,"oldPartForm");
	});
	$("#export").bind("click",function(){
		var $f = $("#claimSearchform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction/partDownload.do");
		$("#exportFm").submit();
	});
});
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;应返旧件查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimSearchTable">
						<tr>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="CLAIM_NO" name="CLAIM_NO" datasource="CLAIM_NO" datatype="1,is_null,30" operation="like"  value="" /></td>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="OLD_PART_CODE" name="OLD_PART_CODE" datasource="OLD_PART_CODE" datatype="1,is_null,30" operation="like"  value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="OLD_PART_NAME" name="OLD_PART_NAME" datasource="OLD_PART_NAME" datatype="1,is_null,30"  operation="like" value="" /></td>
						</tr>
						<tr>
							<td><label>旧件产生时间：</label></td>
							<td><input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" datasource="C.CHECKPASS_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
							<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="C.CHECKPASS_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
							</td>
							<td><label>旧件集中点名称：</label></td>
							<td><input type="text" id="R_ORGCODE" name="R_ORGCODE"  kind="dic"   src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS='100201' AND IS_IC = 100101"   datatype="1,is_null,100" value="" /></td>
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
			<table style="display:none;width:100%;" layoutH="250" id="oldPartForm" name="oldPartForm" ref="preAuth" refQuery="claimSearchTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CLAIM_NO"  ordertype='local' class="desc">索赔单号</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="MILEAGE">行驶里程</th>
							<th fieldname="WXCS">维修次数</th>
							<th fieldname="OLD_PART_CODE">配件代码</th>
							<th fieldname="OLD_PART_NAME">配件名称</th>
							<th fieldname="OLD_PART_COUNT">配件数量</th>
							<th fieldname="REPAY_COSTS" align="right" refer="amountFormat">追偿材料费</th>
							<th fieldname="CLAIM_COSTS" align="right" refer="amountFormat">结算材料费</th>
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="MAIN_SUPP_CODE">责任供应商代码</th>
							<th fieldname="MAIN_SUPP_NAME">责任供应商名称</th>
							<th fieldname="FAULT_REASON">质损原因</th>
							<th fieldname="REMARKS"style="display:none">备注</th>
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
</form>
</body>
</html>