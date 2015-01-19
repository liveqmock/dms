<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>里程调整查询</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/MileageAdjustSearchAction/claimOemSearch.ajax";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-mileageAdjust");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"mileageAdjustList");
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
function showVehicleInfo(vehicleId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/showVehicleInfo.jsp?vehicleId="+vehicleId, "showVehicleInfo", "车辆明细信息", options);
}
//索赔单明细
function claimDetail(obj){
	var $tr=$(obj).parent();
	return "<a href='#' onclick=showClaimDetail("+$tr.attr("CLAIM_ID")+") class='op'>"+$tr.attr("CLAIM_NO")+"</a>";
}
function showClaimDetail(claimId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 里程调整查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-mileageAdjust">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-mileageAdjust">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
						<td><label>索赔单号：</label></td>
					    <td><input type="text" id="claimNo" name="claimNo" datasource="T.CLAIM_NO" datatype="1,is_null,30" operation="like" /></td>
						<td ><label>VIN：</label></td> 
					    <td> <input type="text" name="vin" id="vin"  datasource="T.VIN" datatype="1,is_null,30" operation="like"/></td> 
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="mileageAdjust" >
			<table style="display:none;width:100%;" id="mileageAdjustList" name="mileageAdjustList" ref="mileageAdjust" refQuery="tab-mileageAdjust" >
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none;"></th>
						<th fieldname="ORG_CODE">渠道商代码</th>
						<th fieldname="ORG_NAME" >渠道商名称</th>
						<th fieldname="CLAIM_NO" colwidth="120" ordertype='local' class="desc" refer="claimDetail">索赔单号</th>
						<th fieldname="VIN" colwidth="140" refer="vehicleInfoLink">VIN</th>
						<th fieldname="CLAIM_STATUS" >索赔单状态</th>
						<th fieldname="APPLY_DATE" colwidth="140">提报时间</th>
						<th fieldname="MILEAGE" >原里程数</th>
						<th fieldname="ENSURE_MILEAGE" >申请里程数</th>
						<th fieldname="MILEAGE_APPLY_STATUS" >里程调整状态</th>
						<th fieldname="MILEAGE_CHECK_OPINION" maxlength="40" >里程审核意见</th>
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