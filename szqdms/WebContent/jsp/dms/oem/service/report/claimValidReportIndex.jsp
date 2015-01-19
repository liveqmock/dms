<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>有效里程差异统计表</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/claimLccySearch.ajax";
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/LccyDownload.do");
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
function showVehicleInfo(vehicleId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/showVehicleInfo.jsp?vehicleId="+vehicleId, "showVehicleInfo", "车辆明细信息", options);
}
function claimInfoLink1(claimId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：售后管理&gt;报表管理&gt;有效里程差异统计表</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimSearchTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
						    <td><label>渠道商名称：</label></td>
							<td><input type="text" id="orgName" name="orgName" datasource="G.ONAME" datatype="1,is_null,30" value=""  operation="like" /></td>
						    <td><label>办事处名称：</label></td>
							<td><input type="text" id="bscName" name="bscName" datasource="G1.ONAME" datatype="1,is_null,30" value=""  operation="like" /></td>
						</tr>	
						<tr>
						 <td><label>终审通过时间：</label></td>
					     <td >
				    	 	<input type="text" id="in-ckrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
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
							<th fieldname="CODE" >渠道商代码</th>
							<th fieldname="ONAME" >渠道商名称</th>
							<th fieldname="BSCMC" >办事处名称</th>
							<th fieldname="CLAIM_NO" refer="claimInfoLink" >索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔类型</th>
							<th fieldname="VIN" refer="vehicleInfoLink">VIN</th>
							<th fieldname="MODELS_CODE" >车辆型号</th>
							<th fieldname="BUY_DATE" >购车日期</th>
							<th fieldname="OLDPART_FINAL_DATE" >终审日期</th>
							<th fieldname="MILEAGE" >索赔单提报里程</th>
							<th fieldname="GPS_MILEAGE" >GPS调取里程</th>
							<th fieldname="LCC" >差异值</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
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
</body>
</html>