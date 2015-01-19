<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>活动方案总结审核</title>
<script type="text/javascript">
//var diaAddOptions = {max:false,width:720,height:240,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var diaAddOptions = {max:true,width:1024,height:470,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProActiveSummaryCheckAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/search.ajax";
		var $f = $("#fm-searchProActiveSummaryCheck");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-proActiveSummaryCheckList");
	});
});

function doCheck(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/market/programActiveSummaryCheckAdd.jsp?action=2", "编辑活动总结审核", "编辑活动总结审核", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 市场管理  &gt; 市场活动管理   &gt; 活动总结审核</h4>
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
					    <td><label>总结时间：</label></td>
					     <td>
				    		<input type="text" id="sumReportTime1"  name="sumReportTime1" dataSource="D.SUM_REPORT_TIME" datatype="1,is_date,30" operation=">=" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="sumReportTime2"  name="sumReportTime2" dataSource="D.SUM_REPORT_TIME" datatype="1,is_date,30" operation="<=" 
								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />
				   		 </td>
					</tr>
					<tr>
				   		<td><label>预计费用：</label></td>
					    <td>
				    		<input type="text" id="planFee1" name="planFee1" datasource="B.PLAN_FEE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="planFee2" name="planFee2" datasource="B.PLAN_FEE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
				   		 </td>
				   		 
				   		 <td><label>实际费用：</label></td>
					     <td colspan=3>
				    		<input type="text" id="realFee1" name="realFee1" datasource="D.REAL_FEE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>
					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>
					    	<input type="text" id="realFee2" name="realFee2" datasource="D.REAL_FEE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>
				   		 </td>
				   		 <input type="hidden" id="summaryStatus" name="summaryStatus" datasource="D.SUMMARY_STATUS" value="206102"/>
				   		 <input type="hidden" id="status" name="status" datasource="D.STATUS" value="100201"/>
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
<!--				   		 <td><label>实际费用：</label></td>-->
<!--					     <td>-->
<!--				    		<input type="text" id="realFee1" name="realFee1" datasource="D.REAL_FEE" datatype="1,is_null,10" operation=">=" style="width:60px;"/>-->
<!--					    	<span style="float:left;margin-left:-35px;margin-top:5px;">~</span>-->
<!--					    	<input type="text" id="realFee2" name="realFee2" datasource="D.REAL_FEE" datatype="1,is_null,10" operation="<=" style="width:60px;"/>-->
<!--				   		 </td>-->
<!--				   		 <input type="hidden" id="summaryStatus" name="summaryStatus" datasource="D.SUMMARY_STATUS" value="206102"/>-->
<!--				   		 <input type="hidden" id="status" name="status" datasource="D.STATUS" value="100201"/>-->
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
		<div id="page_proActiveSummaryCheckList" >
			<table style="display:none;width:100%;" id="tab-proActiveSummaryCheckList" name="tablist" ref="page_proActiveSummaryCheckList" refQuery="tab-searchProActiveSummaryCheck" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th fieldname="ACTIVE_CODE" >活动代码</th>
						<th fieldname="ACTIVE_NAME" >活动名称</th>
						<th fieldname="CODE" >服务商代码</th>
						<th fieldname="ONAME" >服务商名称</th>
						<th fieldname="PLAN_FEE" >预计费用</th>
						<th fieldname="REAL_FEE" >实际费用</th>
						<th fieldname="SUMMARY_STATUS">总结流程状态</th>
						<th fieldname="SUM_REPORT_USER">总结人</th>
						<th fieldname="SUM_REPORT_TIME">总结时间</th>
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