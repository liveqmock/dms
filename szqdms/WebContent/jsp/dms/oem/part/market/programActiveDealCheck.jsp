<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>活动执行方案审核</title>
<script type="text/javascript">
//var diaAddOptions = {max:false,width:720,height:240,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveDealCheckAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/searchProActiveDeal.ajax";
		var $f = $("#fm-searchProActiveDeal");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-proActiveDealList");
	});
});

function doCheck(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/market/programActiveDealCheckAdd.jsp?action=2", "活动执行方案审核", "活动执行方案审核", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 市场管理  &gt; 市场活动管理   &gt; 活动执行方案审核</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchProActiveDeal">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchProActiveDeal">
					<tr>
					    <td><label>活动代码：</label></td>
					    <td>
					    	<input type="text" id="activeCode"  name="activeCode" datasource="B.ACTIVE_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
				    	<td><label>活动名称：</label></td>
					    <td>
					    	<input type="text" id="activeName"  name="activeName" datasource="B.ACTIVE_NAME"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>提报时间：</label></td>
					    <td>
					    	<input type="text" id="reportTime1"  name="reportTime1" dataSource="B.REPORT_TIME" datatype="1,is_date,30" operation=">=" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="reportTime2"  name="reportTime2" dataSource="B.REPORT_TIME" datatype="1,is_date,30" operation="<=" 
								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />
							<input type="hidden" id="activeStatus" name="activeStatus" datasource="A.ACTIVE_STATUS" value="206002"/>
				   		 	<input type="hidden" id="status" name="status" datasource="A.STATUS" value="100201"/>
					    </td>
					</tr>
					<tr>
				   		<td><label>预计费用：</label></td>
					    <td colspan=5>
				    		<input type="text" id="planFee1" name="planFee1" datasource="B.PLAN_FEE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="planFee2" name="planFee2" datasource="B.PLAN_FEE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
				   		 </td>
					</tr>
<!--					<tr>-->
<!--				   		<td><label>开始时间：</label></td>-->
<!--					    <td>-->
<!--				    		<input type="text" id="startDate1"  name="startDate1" dataSource="A.START_DATE" datatype="1,is_date,30" operation=">=" -->
<!--				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />-->
<!--				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>-->
<!--							<input type="text" id="endDate2"  name="endDate2" dataSource="A.START_DATE" datatype="1,is_date,30" operation="<=" -->
<!--								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />-->
<!--				   		 </td>-->
<!--				   		 <td><label>结束时间：</label></td>-->
<!--					     <td>-->
<!--				    		<input type="text" id="endDate1"  name="endDate1" dataSource="A.END_DATE" datatype="1,is_date,30" operation=">=" -->
<!--				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />-->
<!--				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>-->
<!--							<input type="text" id="endDate2"  name="endDate2" dataSource="A.END_DATE" datatype="1,is_date,30" operation="<=" -->
<!--								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />-->
<!--				   		 </td>-->
<!--					</tr>-->
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
		<div id="page_proActiveDealList" >
			<table style="display:none;width:100%;" id="tab-proActiveDealList" name="tablist" ref="page_proActiveDealList" refQuery="tab-searchProActiveDeal" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th fieldname="ACTIVE_CODE" >活动代码</th>
						<th fieldname="ACTIVE_NAME" >活动名称</th>
<!--						<th fieldname="START_DATE" >开始时间</th>-->
<!--						<th fieldname="END_DATE" >结束时间</th>-->
						<th fieldname="CODE" >渠道商代码</th>
						<th fieldname="ONAME">渠道商名称</th>
						<th fieldname="PLAN_FEE" >预计费用</th>
						<th fieldname="PERSON_NUMS">参与人数</th>
						<th fieldname="REPORT_USER">执行提报人</th>
						<th fieldname="REPORT_TIME">提报时间</th>
						<th fieldname="DEAL_STATUS">执行流程状态</th>
						<th colwidth="140" type="link" title="[审核]"  action="doCheck" >操作</th>
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