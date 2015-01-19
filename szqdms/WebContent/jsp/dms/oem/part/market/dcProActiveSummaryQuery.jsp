<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>活动信息查询</title>
<script type="text/javascript">
//var diaAddOptions = {max:false,width:720,height:240,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveSummaryCheckAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/searchQuery.ajax?orgId="+<%=orgId%>;
		var $f = $("#fm-searchProActiveSummaryCheck");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-proActiveSummaryCheckList");
	});
});

function dodetail(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	//action=1(新增)、2(编辑)、3(查看详细信息)
	$.pdialog.open(webApps+"/jsp/dms/oem/part/market/programActiveSummaryCheckAdd.jsp?action=3", "活动信息查询", "活动信息查询", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 市场活动相关   &gt; 活动信息查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchProActiveSummaryCheck">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchProActiveSummaryCheck">
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
				   		 <input type="hidden" id="summaryStatus" name="summaryStatus" datasource="D.SUMMARY_STATUS" value="206103"/>
				   		 <input type="hidden" id="status" name="status" datasource="D.STATUS" value="100201"/>
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
		<div id="page_proActiveSummaryCheckList" >
			<table style="display:none;width:100%;" id="tab-proActiveSummaryCheckList" name="tablist" ref="page_proActiveSummaryCheckList" refQuery="tab-searchProActiveSummaryCheck" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th fieldname="ACTIVE_CODE" >活动代码</th>
						<th fieldname="ACTIVE_NAME" >活动名称</th>
						<th fieldname="START_DATE" >开始时间</th>
						<th fieldname="END_DATE" >结束时间</th>
						<th fieldname="PLAN_FEE" >预计费用</th>
						<th fieldname="DEAL_CONTENT" >执行方案</th>
						<th fieldname="DEAL_STATUS">执行方案流程状态</th>
						<th colwidth="140" type="link" title="[查看]"  action="dodetail" >操作</th>
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