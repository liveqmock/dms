<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.org.dms.common.DicConstant"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工单转索赔单</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/searchWorkOrder.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/deleteWorkOrder.ajax";
//初始化方法
$(function(){
	doSearch();
	//查询方法
	$("#btn-search").bind("click",function(event){
		doSearch();
	});
});
//初始化查询
function doSearch(){
	var $f = $("#fm-searchWorkOrder");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-workOrderList");
}
//列表编辑链接(修改)
var $row;
var workClaimId;
function doReport(rowobj)
{
	$row = $(rowobj);
	workClaimId=$(rowobj).attr("CLAIM_ID");
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	if(workClaimId)
	{
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimAdd.jsp?action=2", "cliamAdd", "索赔单信息维护", options,true);
	}else
	{
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimAdd.jsp?action=1", "cliamAdd", "索赔单信息维护", options,true);
	}
	
}
//vin连接
function vehicleInfoLink(obj)
{
	var $row=$(obj).parent();
	if($row.attr("VIN"))
	{
		return "<a href='#' onclick=showVehicleInfo("+$row.attr("VEHICLE_ID")+") class='op'>"+$row.attr("VIN")+"</a>";
	}else
	{
		return "";
	}
    
}
function showVehicleInfo(vehicleId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/showVehicleInfo.jsp?vehicleId="+vehicleId, "showVehicleInfo", "车辆明细信息", options);
}

function showAnNiu(obj){
    var $tr = $(obj).parent();
    var claimId = $tr.attr("CLAIM_ID");
    if(claimId){
   		obj.html("<A class=op title=[工单转索赔单] onclick='doReport(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[工单转索赔单]</A><A class=op title=[作废] onclick='doDelete(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[作废]</A>");
    }else{
    	obj.html("<A class=op title=[工单转索赔单] onclick='doReport(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[工单转索赔单]</A>");
    }
}
//作废
var $dRow;
function doDelete(rowobj){
	$dRow = $(rowobj);
	var url = deleteUrl + "?claimId="+$(rowobj).attr("CLAIM_ID")+"&workId="+$(rowobj).attr("WORK_ID");
	sendPost(url,"","",deleteCallBack,"true");
}
//作废回调方法
function  deleteCallBack(res)
{
	try
	{
		if($dRow) 
			$("#btn-search").trigger("click");
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 工单管理   &gt; 工单转索赔单</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchWorkOrder">
			<!-- 定义隐藏域条件 -->
			<input type="hidden" id="in-workStatus" name="in-workStatus" datasource="T.WORK_STATUS" value="<%=DicConstant.PGDZT_03%>" />
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-workOrderSearch">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>派工单号：</label></td>
					    <td><input type="text" id="IN_WORK_NO" name="IN_WORK_NO" datasource="T.WORK_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
				    	<td><label>维修完成日期：</label></td>
					    <td >
				    		<input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" style="width:75px;" datasource="T.COMPLETE_DATE" datatype="1,is_date,30" onclick="WdatePicker()"  kind="date" operation=">="/>
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  datasource="T.COMPLETE_DATE" datatype="1,is_date,30" kind="date" operation="<=" onclick="WdatePicker()" />
				   		 </td>
				   		<td><label>维修人：</label></td>
					    <td><input type="text" id="IN_REPAIR_USER" name="IN_REPAIR_USER" datasource="T.REPAIR_USER" datatype="1,is_null,30" operation="like" /></td>
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
		<div id="page_wrokOrderList" >
			<table style="display:none;width:100%;" id="tab-workOrderList" name="tablist" ref="page_wrokOrderList" refQuery="fm-searchWorkOrder" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="WORK_NO"  ordertype='local' class="desc">派工单号</th>
							<th fieldname="WORK_VIN" >工单VIN</th>
							<th fieldname="REPAIR_USER">维修人</th>
							<th fieldname="GO_DATE" >出发时间</th>
							<th fieldname="ARRIVE_DATE" >到达时间</th>
							<th fieldname="COMPLETE_DATE" >维修完成时间</th>
							<th fieldname="WORK_TYPE" >派工类型</th>
							<th fieldname="IF_OUT" >是否有外出</th>
							<th fieldname="VIN" refer="vehicleInfoLink">索赔VIN</th>
							<th colwidth="145" type="link" title="[工单转索赔单]" refer="showAnNiu"  action="doReport" >操作</th>
						</tr>
					</thead>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>