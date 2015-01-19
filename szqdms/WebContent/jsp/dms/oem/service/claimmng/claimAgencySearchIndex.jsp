<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单查询(办事处)</title>
<script type="text/javascript">
var searchUrl1 = "<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction/claimAgencySearch.ajax";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl1,"search",1,sCondition,"claimSearchlist");
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
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction/download.do");
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
function showVehicleInfo(vehicleId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/showVehicleInfo.jsp?vehicleId="+vehicleId, "showVehicleInfo", "车辆明细信息", options);
}
//索赔单明细
function doDetail(rowobj){
	var claimId=$(rowobj).attr("CLAIM_ID");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
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
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.CODE" datatype="1,is_null,10000" operation="like" value="" /></td>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo" datasource="SC.CLAIM_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>索赔状态：</label></td>
							<td><select type="text" id="claimStatus" name="claimStatus" datasource="SC.CLAIM_STATUS" datatype="1,is_null,6" class="combox" kind="dic" src="SPDZT" filtercode="301002|301003|301004|301005|301006|301007|301009">
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
							<th fieldname="WORK_NO" >派工单号</th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔类型</th>
							<th fieldname="VIN" refer="vehicleInfoLink">VIN</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="APPLY_DATE" ordertype='local' class="desc">提报时间</th>
							<th fieldname="REJECT_DATE" ordertype='local' class="desc">驳回时间</th>
							<th colwidth="45" type="link" title="[明细]"  action="doDetail" >操作</th>
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