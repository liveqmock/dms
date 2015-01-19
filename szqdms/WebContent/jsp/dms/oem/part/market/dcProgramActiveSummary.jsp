<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgId = user.getOrgId();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>活动总结提报</title>
<script type="text/javascript">
var diaAddOptions = {max:false,width:720,height:270,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveSummaryAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/search.ajax?orgId="+<%=orgId%>;
		var $f = $("#fm-searchProActiveSummary");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-proActiveSummaryList");
	});
});

function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/market/programActiveSummaryAdd.jsp?action=2", "编辑活动总结提报", "编辑活动总结提报", diaAddOptions);
}

function doReport(rowobj)
{
	$row = $(rowobj);
	var summaryId = $(rowobj).attr("SUMMARY_ID");
	if(summaryId){
		var url = mngUrl + "/proActiveSummaryReport.ajax?summaryId="+summaryId;
		sendPost(url,"","",reportProActiveDealCallBack,"true");
	}else{
		alertMsg.warn('请先操作编辑按钮，编辑活动方案总结的实际费用和总结内容!');
	}
}

function reportProActiveDealCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-proActiveSummaryList").removeResult($row);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 市场管理  &gt; 市场活动管理   &gt; 活动总结提报</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchProActiveSummary">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchProActiveSummary">
					<tr>
					    <td><label>活动代码：</label></td>
					    <td>
					    	<input type="text" id="activeCode"  name="activeCode" datasource="A.ACTIVE_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
				    	<td><label>活动名称：</label></td>
					    <td>
					    	<input type="text" id="activeName"  name="activeName" datasource="A.ACTIVE_NAME"  datatype="1,is_null,30" operation="like" />
					    </td>
     					<td><label>预计费用：</label></td>
					    <td>
				    		<input type="text" id="planFee1" name="planFee1" datasource="B.PLAN_FEE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="planFee2" name="planFee2" datasource="B.PLAN_FEE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
				   		 </td>
					</tr>
					<tr>
				   		<td><label>开始时间：</label></td>
					    <td>
				    		<input type="text" id="startDate1"  name="startDate1" dataSource="A.START_DATE" datatype="1,is_date,30" operation=">=" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="endDate2"  name="endDate2" dataSource="A.START_DATE" datatype="1,is_date,30" operation="<=" 
								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>结束时间：</label></td>
					     <td>
				    		<input type="text" id="endDate1"  name="endDate1" dataSource="A.END_DATE" datatype="1,is_date,30" operation=">=" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="endDate2"  name="endDate2" dataSource="A.END_DATE" datatype="1,is_date,30" operation="<=" 
								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>实际费用：</label></td>
					     <td>
				    		<input type="text" id="realFee1" name="realFee1" datasource="D.REAL_FEE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="realFee2" name="realFee2" datasource="D.REAL_FEE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_proActiveSummaryList" >
			<table style="display:none;width:100%;" id="tab-proActiveSummaryList" name="tablist" ref="page_proActiveSummaryList" refQuery="tab-searchProActiveSummary" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th fieldname="ACTIVE_CODE" >活动代码</th>
						<th fieldname="ACTIVE_NAME" >活动名称</th>
						<th fieldname="START_DATE" >开始时间</th>
						<th fieldname="END_DATE" >结束时间</th>
						<th fieldname="PLAN_FEE" >预计费用</th>
						<th fieldname="REAL_FEE" >实际费用</th>
						<th fieldname="SUMMARY_CON" >总结内容</th>
						<th fieldname="SUMMARY_STATUS">总结流程状态</th>
						<th colwidth="140" type="link" title="[编辑]|[提报]"  action="doUpdate|doReport" >操作</th>
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