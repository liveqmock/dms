<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		
		$("#outbuy").val($("#outBuy").val());
		var outbuyStatus=$("#outbuy").val();
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/claimSearch.ajax?&outbuyStatus="+outbuyStatus;
		doFormSubmit($f,url,"search",1,sCondition,"claimSearchlist");
	});
	 //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#claimSearchform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	}); 
	$("#export").bind("click",function(){
		var $f = $("#claimSearchform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction/oemDownload.do");
		$("#exportFm").submit();
	});
});
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
//vin连接
function claimInfoLink(obj)
{
	var $row=$(obj).parent();
	if($row.attr("CLAIM_ID"))
	{
		return "<a href='#' onclick=claimInfoLink1("+$row.attr("CLAIM_ID")+") class='op'>"+$row.attr("CLAIM_NO")+"</a>";
	}else
	{
		return "";
	}
}
//服务活动连接
function activityInfoLink(obj)
{
	var $row=$(obj).parent();
	if($row.attr("ACTIVITY_ID"))
	{
		return "<a href='#' onclick=doOpen("+$row.attr("ACTIVITY_ID")+") class='op'>"+$row.attr("ACTIVITY_CODE")+"</a>";
	}else
	{
		return "";
	}
}
function showVehicleInfo(vehicleId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/showVehicleInfo.jsp?vehicleId="+vehicleId, "showVehicleInfo", "车辆明细信息", options);
}
function claimInfoLink1(claimId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
}
function doOpen(activityId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityScopeMngDetail.jsp?activityId="+activityId, "fwhdxxmx", "服务活动明细", options,true);
} 
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：索赔管理&gt;索赔管理&gt;索赔单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimSearchTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="SC.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo" datasource="SC.CLAIM_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>索赔状态：</label></td>
							<td><select type="text" id="claimStatus" name="claimStatus" datasource="SC.CLAIM_STATUS" datatype="1,is_null,6" class="combox" kind="dic" src="SPDZT" filtercode="301002|301003|301004|301005|301006|301007|301009|301015">
									<option value=-1>--</option>
								</select>
							</td>
						</tr>	
						<tr>
						<td>
							<label>派工单号：</label></td>
							<td><input type="text" id="workNo" name="workNo" datasource="SO.WORK_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>VIN：</label></td>
							<td><input type="text" id="vin" name="vin" datasource="SC.VIN" datatype="1,is_null,17" value=""  operation="like" /></td>
							<td><label>提报日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="SC.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq"  group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="SC.APPLY_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
						</tr>
						<tr>
							<td><label>索赔类型：</label></td>
							<td><select type="text" id="claimType" name="claimType" datasource="SC.CLAIM_TYPE" datatype="1,is_null,6" class="combox" kind="dic" src="SPDLX" >
									<option value=-1>--</option>
								</select>
							</td>
							<td><label>办事处审核中：</label></td>
							<td><select type="text" id="stockMeet" name="stockMeet" datasource="SC.STOCK_MEET" datatype="1,is_null,6" class="combox" kind="dic" src="E#100102=是:100101=否" >
									<option value=-1>--</option>
								</select>
							</td>
							<td><label>是否外采单：</label></td>
							<td><select type="text" id="outBuy" name="outBuy"  datatype="1,is_null,6" class="combox" kind="dic" src="E#1=是:2=否" >
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>办事处代码：</label></td>
							<td><input type="text" id="bscCode" name="bscCode" datasource="G1.CODE" datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>办事处名称：</label></td>
							<td><input type="text" id="bscOname" name="bscOname" datasource="G1.ONAME" datatype="1,is_null,30" value=""  operation="like" /></td>
						</tr>
						<tr>
						<td><label>初审通过时间：</label></td>
					    <td >
				    		<input type="text" id="in-ckrq1"  group="in-ckrq1,in-jsrq1" style="width:75px;" name="in-ckrq" datasource="SC.CHECKPASS_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq1"  group="in-ckrq1,in-jsrq1" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.CHECKPASS_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
				   		 </td>
						 <td><label>终审通过时间：</label></td>
					     <td >
				    	 	<input type="text" id="in-ckrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;" name="in-ckrq" datasource="SC.OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
				    	 	<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jsrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
				   		 </td>
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="claimSearchlist" name="claimSearchlist" ref="preAuth" refQuery="claimSearchTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_CODE" >渠道商代码</th>
							<th fieldname="ORG_NAME" >渠道商名称</th>
							<th fieldname="CODE" >办事处代码</th>
							<th fieldname="ONAME" >办事处名称</th>
							<th fieldname="WORK_NO" >派工单号</th>
							<th fieldname="CLAIM_NO" refer="claimInfoLink" >索赔单号</th>
							<th fieldname="ACTIVITY_CODE" refer="activityInfoLink" >服务活动代码</th>
							<th fieldname="CLAIM_TYPE" >索赔类型</th>
							<th fieldname="VIN" refer="vehicleInfoLink">VIN</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="STOCK_STATUS" >办事处审核</th>
							<th fieldname="STOCK_MEET" >是否外采</th>
							<th fieldname="APPLY_DATE" ordertype='local' class="desc">提报时间</th>
							<th fieldname="FAULT_DATE" ordertype='local' class="desc">故障时间</th>
							<th fieldname="APPLY_REPAIR_DATE" ordertype='local' class="desc">报修时间</th>
							<th fieldname="REPAIR_DATE" ordertype='local' class="desc">检修时间</th>
							<th fieldname="REJECT_DATE" ordertype='local' class="desc">驳回时间</th>
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
<form id="claimNoForm" method="post" style="display:none">
	<input type="hidden" id="outbuy" name="data"></input>
</form>
</body>
</html>