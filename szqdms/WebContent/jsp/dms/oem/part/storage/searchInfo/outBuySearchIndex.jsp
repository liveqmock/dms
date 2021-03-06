<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>外采单查询</title>
<script type="text/javascript">
var searchUrl1 = "<%=request.getContextPath()%>/service/claimmng/ClaimOutBuyMngAction";
//查询按钮响应方法
$(function(){
	//查询方法
	$("#search").bind("click",function(event){
		var $f = $("#claimOutBuyform");
		$("#claimNoInfo").val($("#claimNo").val());
		var claimNo=$("#claimNoInfo").val();
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl1+"/claimOutSearchInfoPart.ajax?&claimNo="+claimNo;
		doFormSubmit($f,url,"search",1,sCondition,"claimOutBuylist");
	});
	 //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#claimOutBuyform")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
	$("#export").bind("click",function(){
		var $f = $("#claimOutBuyform");
		var claimNo=$("#claimNo").val();
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
    	$("#claimNoInfo").val(claimNo);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimOutBuyMngAction/download.do?&claimNo="+claimNo);
		$("#exportFm").submit();
	});
	$("#export1").bind("click",function(){
		var $f = $("#claimOutBuyform");
		var claimNo=$("#claimNo").val();
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params1").val(sCondition);
    	$("#claimNoInfo1").val(claimNo);
		$("#exportFm1").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimOutBuyMngAction/download1.do?&claimNo="+claimNo);
		$("#exportFm1").submit();
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
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/dealer/part/storage/outstorage/outBuyOrderDtl.jsp", "claimDetail", "外采单明细", options,true);
}
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询&gt;仓储相关&gt;外采单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimOutBuyform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimOutBuyTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.CODE" datatype="1,is_null,10000" operation="like" value=""/></td>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo"  datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>是否审核通过：</label></td>
							<td><select type="text" id="claimStatus" name="claimStatus" datasource="BUY_STATUS" datatype="1,is_null,6" class="combox" kind="dic" src="SF" >
									<option value=-1>--</option>
								</select>
							</td>
						</tr>	
						<tr>
							<td><label>外购日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="BUY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq"  group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="BUY_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="export1">明细导出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="preAuth" >
			<table style="display:none;width:100%;" layoutH="250" id="claimOutBuylist" name="claimOutBuylist" ref="preAuth" refQuery="claimOutBuyTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CLAIM_ID" style="display:none">索赔单主键</th>
							<th fieldname="CODE" >渠道商代码</th>
							<th fieldname="ONAME" >渠道商名称</th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="BUY_NO" >外采单号</th>
							<th fieldname="CUSTOMER_NAME" >客户名称</th>
							<th fieldname="BUY_COUNT" >外购数量</th>
							<th fieldname="BUY_AMOUNT" refer="amountFormat" align="right">外购金额</th>
							<th fieldname="BUY_DATE" >外购日期</th>
							<th fieldname="STATUS" >外采单状态</th>
							<th fieldname="BUY_STATUS" >是否审核通过</th>
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
	<input type="hidden" id="claimNoInfo" name="data"></input>
</form>
<form id="exportFm1" method="post" style="display:none">
	<input type="hidden" id="params1" name="data"></input>
	<input type="hidden" id="claimNoInfo1" name="data"></input>
</form>
</body>
</html>