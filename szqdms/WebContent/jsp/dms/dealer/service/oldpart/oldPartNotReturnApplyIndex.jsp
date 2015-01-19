<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    String year = yearFormat.format(Pub.getCurrentDate());
    String monthDate = monthFormat.format(Pub.getCurrentDate());
    Integer month = Integer.valueOf(monthDate) - 1;
    String date = year + "-" + String.valueOf(month);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件不回运申请</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnApplyAction/oldPartNotReturnSearch.ajax?checkDate="+checkDate;
var applyUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnApplyAction/oldPartNotReturnApply.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#notReturnform");
		var sCondition = {};
		var shrq=$("#in-shrq").val();
		$("#checkDateVal").val(shrq);
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"notReturnList");
	});
	//申请
	$("#doUpdate").bind("click",function(){
		var $f = $("#checkDateFom");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doNormalSubmit($f,applyUrl,"doUpdate",sCondition,applyCallBack);
	});
});

//编辑
var notbackId;
var claimId;
function doEdit(rowobj){
	notbackId=$(rowobj).attr("NOTBACK_ID");
	claimId=$(rowobj).attr("CLAIM_ID");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartNotReturnApplyEdit.jsp", "oldPartNotReturnEdit", "不回运旧件明细", options,true);
}
//申请回调
function applyCallBack(res){
	try
	{
		$("#search").click();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件不回运申请</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="notReturnform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="notReturnTable">
						<tr>
							<td><label>审核月份：</label></td>
							<td><input type="text" id="in-shrq"  name="in-shrq" operation=""  dataSource="CHECK_DATE" style="width:75px;"   datatype="0,is_null,30" onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy-MM'})" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doUpdate">申&nbsp;&nbsp;请</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="notReturn">
				<table style="display:none;width:100%;" id="notReturnList" name="notReturnList" ref="notReturn" refQuery="notReturnTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CLAIM_NO"  ordertype='local' class="desc" refer="doOpenDetail">索赔单号</th>
							<th fieldname="CLAIM_ID" style="display:none">索赔ID</th>
							<th fieldname="CLAIM_TYPE">索赔类型</th>
							<th fieldname="NOTBACK_ID" style="display:none">旧件不回运主表ID</th>
							<th fieldname="APPLY_USER" >申请人</th>
							<th fieldname="APPLY_DATE">申请时间</th>
							<th fieldname="APPLY_STATUS">申请状态</th>
							<th fieldname="REMARKS">备注</th>
							<th colwidth="85" type="link"  title="[旧件明细]" action="doEdit">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<form method="post" id="checkDateFom" >
		<input type="hidden" id="checkDateVal" datasource="CHECK_DATE"/>
		</form>
	</div>
</div>
</body>
</html>