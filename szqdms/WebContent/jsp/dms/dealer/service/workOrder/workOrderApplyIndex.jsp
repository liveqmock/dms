<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工单维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/workOrderDelete.ajax";
var reportUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/workOrderReport.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#dispatchPartform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"workOrderlist");
	});
});
function doAdd(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/workOrder/workOrderEdit.jsp?action=1", "workOrder", "工单维护", options,true); 
}
function doDelete(rowobj)
{	
	$row = $(rowobj);
	var workId=$(rowobj).attr("WORK_ID");
	var url = deleteUrl + "?workId="+workId;
	sendPost(url,"delete","",deleteCallBack,"true");
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/workOrder/workOrderEdit.jsp?action=2", "workOrder", "工单维护", options,true); 
}
function doReport(rowobj){
	
	$row = $(rowobj);
	var workId=$(rowobj).attr("WORK_ID");
	var url = reportUrl + "?workId="+workId+"&flag="+1;
	sendPost(url,"","",reportCallBack,"true");
}
function reportCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			if($row) 
				$("#workOrderlist").removeResult($row);
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			if($row) 
				$("#workOrderlist").removeResult($row);
		}
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：索赔管理&gt;工单管理&gt;工单维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="dispatchPartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="workOrderTable">
						<tr>
							<td><label>工单编号：</label></td>
							<td><input type="text" id="dia-workNo" name="dia-workNo" datasource="WORK_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>工单VIN：</label></td>
							<td><input type="text" id="dia-workVin" name="dia-workVin" datasource="WORK_VIN" datatype="1,is_null,17" value=""  operation="like" /></td>
							<td><label>报修日期：</label></td>
						    <td colspan="3">
					    		<input type="text" id="in-ckrq" group="in-ckrq,in-jsrq"style="width:75px;" name="in-ckrq" datasource="APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" group="in-ckrq,in-jsrq"style="width:75px;margin-left:-30px;" name="in-jsrq" datasource="APPLY_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="workOrder" >
			<table style="display:none;width:100%;" layoutH="250" id="workOrderlist" name="workOrderlist" ref="workOrder" refQuery="workOrderTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="WORK_NO">工单号</th>
							<th fieldname="WORK_VIN" refer="vehicleInfoLink">工单VIN</th>
							<th fieldname="APPLY_USER" >报修人</th>
							<th fieldname="REPAIR_USER" >维修人</th>
							<th fieldname="IF_OUT" >是否外出</th>
							<th fieldname="WORK_TYPE" >工单类型</th>
							<th fieldname="APPLY_DATE" ordertype='local' class="desc">报修时间</th>
							<th fieldname="REPAIR_DATE" ordertype='local' class="desc">维修时间</th>
							<th colwidth="130" type="link" title="[提报]|[编辑]|[删除]"  action="doReport|doUpdate|doDelete" >操作</th>
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