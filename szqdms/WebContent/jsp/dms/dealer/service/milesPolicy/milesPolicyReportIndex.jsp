<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>万里定保单提报</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/milesPolicy/MilesPolicyReportAction/milesPolicyReportSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/milesPolicy/MilesPolicyReportAction/milesPolicyDelete.ajax";
var reportUrl = "<%=request.getContextPath()%>/service/milesPolicy/MilesPolicyReportAction/milesPolicyReport.ajax";

var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#fm-milesPolicy");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"milesPolicylist");
	});
	$("#add").bind("click",function(event){
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/milesPolicy/milesPolicyReportEdit.jsp?action=1", "milesPolicyReport", "定保单信息维护", options,true);
	});
});
//列表编辑链接(修改)
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/dealer/service/milesPolicy/milesPolicyReportEdit.jsp?action=2", "milesPolicyReport", "定保单信息维护", options,true);
}
var $row;
function doReport(rowobj){
	$row = $(rowobj);
	gId=$(rowobj).attr("G_ID");
	var url = reportUrl + "?gId="+$(rowobj).attr("G_ID")+"&flag="+1;
	sendPost(url,"","",reportCallBack,"true");
}
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?gId="+$(rowobj).attr("G_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#milesPolicylist").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//列表编辑链接(提报)
function reportCallBack(res)
{
	try
	{	
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			if($row) 
				$("#milesPolicylist").removeResult($row);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 定保单管理   &gt; 定保单提报</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-milesPolicy">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-preAuth">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>车牌号：</label></td>
					    <td><input type="text" id="licensePlate" name="licensePlate" datasource="SG.LICENSE_PLATE" datatype="1,is_null,30" operation="like" /></td>
					    <td><label>发动机号：</label></td>
					    <td><input type="text" id="engineNo" name="engineNo" datasource="SG.ENGINE_NO" datatype="1,is_null,30" operation="like" /> </td>
					</tr>
					<tr>
						<td ><label>VIN：</label></td> 
					    <td><input type="text" id="vin-id" name="vin"   datatype="1,is_vin,17" datasource="SG.VIN" operation="like"/></td> 
				    	<td><label>客户姓名：</label></td>
					    <td><input type="text" id="userName" name="userName" datasource="SG.USER_NAME" datatype="1,is_null,30" operation="like" /> </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="milesPolicylist" name="milesPolicylist" ref="preAuth" refQuery="tab-preAuth" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="ENGINE_NO" >发动机号</th>
							<th fieldname="LICENSE_PLATE" >车牌号</th>
							<th fieldname="MILEAGE" >里程</th>
							<th fieldname="USER_NAME" >客户姓名</th>
							<th fieldname="G_STATUS" >定保状态</th>
							<th colwidth="145" type="link" title="[提报]|[编辑]|[删除]"  action="doReport|doUpdate|doDelete" >操作</th>
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