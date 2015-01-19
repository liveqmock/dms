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
<title>旧件不回运审核</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnCheckAction/oldPartNotReturnSearch.ajax";
var passUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnCheckAction/oldPartNotReturnPass.ajax";
var rejectUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnCheckAction/oldPartNotReturnReject.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#notReturnform");
		var sCondition = {};
		$("#applyMonth").val($("#in-shrq").val());
		if($("#orgCode").attr("code")==""&&$("#orgCode").attr("code")==null){
			$("#orgId").val("anull");
		}else{
			$("#orgId").val($("#orgCode").attr("code"));
			
		}
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"notReturnList");
	});
	//审核通过
	$("#doPass").bind("click",function(){
		var $f = $("#checkDateFom");
		var sCondition = {};
			sCondition = $f.combined() || {};
		doNormalSubmit($f,passUrl,"doPass",sCondition,CallBack);
	});
	$("#doReject").bind("click",function(){
		var $f = $("#checkDateFom");
		var sCondition = {};
			sCondition = $f.combined() || {};
		doNormalSubmit($f,rejectUrl,"doReject",sCondition,CallBack);
	});
	$("#btn-reset").bind("click", function(event){
		$("#notReturnform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
});
var notbackId;
function doEdit(rowobj){
	notbackId=$(rowobj).attr("NOTBACK_ID");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/oldpart/oldPartNotReturnCheckEdit.jsp", "oldPartNotReturnEdit", "不回运旧件明细", options,true);
}
function CallBack(res){
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件不回运审核</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="notReturnform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="notReturnTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" operation="in" datatype="1,is_null,10000" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>申请月份：</label></td>
							<td><input type="text" id="in-shrq"  name="in-shrq" operation=""  dataSource="APPLY_MONTH" style="width:75px;"   datatype="0,is_null,30" onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy-MM'})" /></td>
							<td><label>审核状态：</label></td>
							<td><select type="text" id="checkStatus" name="checkStatus" datasource="T.APPLY_STATUS" datatype="1,is_null,6" class="combox" kind="dic" src="BHYSQZT" >
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doPass">通&nbsp;&nbsp;过</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doReject">驳&nbsp;&nbsp;回</button></div></div></li>
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
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th fieldname="CLAIM_NO"  ordertype='local' class="desc" refer="doOpenDetail">索赔单号</th>
							<th fieldname="CLAIM_TYPE">索赔类型</th>
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
		<input type="hidden" id="applyMonth" datasource="APPLY_MONTH"/>
		<input type="hidden" id="orgId" datasource="ORG_ID"/>
		</form>
	</div>
</div>
</body>
</html>