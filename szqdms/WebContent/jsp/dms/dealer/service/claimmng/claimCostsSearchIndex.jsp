<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>报单费用查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction";
var searchFinalUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction";
//查询按钮响应方法
$(function(){
	//初审查询方法
	$("#search").bind("click",function(event){
		$("#zsDiv").hide();
		$("#csDiv").show();
		var activityCode=$("#activityCode").val();
		var modelsCode=$("#modelsCode").val();
		var engineType=$("#engineType").val();
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/claimCostsSearch.ajax?&activityCode="+activityCode+"&modelsCode="+modelsCode+"&engineType="+engineType;
		doFormSubmit($f,url,"search",1,sCondition,"claimCostslist");
	});
	//终审查询方法
	$("#finalSearch").bind("click",function(event){
		$("#csDiv").hide();
		$("#zsDiv").show();
		var $f = $("#claimSearchform");
		var activityCode=$("#activityCode").val();
		var modelsCode=$("#modelsCode").val();
		var engineType=$("#engineType").val();
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url =searchUrl+"/claimFinalCostsSearch.ajax?&activityCode="+activityCode+"&modelsCode="+modelsCode+"&engineType="+engineType;
		doFormSubmit($f,url,"search",1,sCondition,"claimFinalCostslist");
	});
	
	//初审导出
	$("#exp").bind("click",function(){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		var activityCode=$("#activityCode").val();
		var modelsCode=$("#modelsCode").val();
		var engineType=$("#engineType").val();
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction/passDownload.do?activityCode="+activityCode+"&modelsCode="+modelsCode+"&engineType="+engineType);
		$("#exportFm").submit();
	});
	
	//终审导出
	$("#finalExp").bind("click",function(){
		var $f = $("#claimSearchform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		var activityCode=$("#activityCode").val();
		var modelsCode=$("#modelsCode").val();
		var engineType=$("#engineType").val();
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/claimmng/ClaimSearchAction/finalDownload.do?activityCode="+activityCode+"&modelsCode="+modelsCode+"&engineType="+engineType);
		$("#exportFm").submit();
	});
});
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：索赔管理&gt;索赔管理&gt;报单费用查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="claimSearchform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="claimCostsTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,100" value="" operation="in" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>渠道商名称：</label></td>
							<td><input type="text" id="orgName" name="orgName" datasource="T.ORG_NAME"  datatype="1,is_null,100" value="" /></td>
							<td><label>索赔单号：</label></td>
							<td><input type="text" id="claimNo" name="claimNo" datasource="T.CLAIM_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
						</tr>
						<tr>
							<td><label>VIN：</label></td>
							<td><input type="text" id="vin" name="vin" datasource="T.VIN" datatype="1,is_null,17" value=""  operation="like" /></td>
							<td><label>索赔类型：</label></td>
							<td><select type="text" id="claimType" name="claimType" datasource="T.CLAIM_TYPE" datatype="1,is_null,6" class="combox" kind="dic" src="SPDLX" >
									<option value=-1>--</option>
								</select>
							</td>
							<td><label>用户类型：</label></td>
							<td><select type="text" id="userType" name="userType" datasource="T.USER_TYPE" datatype="1,is_null,6" class="combox" kind="dic" src="CLYHLX" >
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
						<tr>	
							<td><label>初审通过时间：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq1"  group="in-ckrq1,in-jsrq1" style="width:75px;" name="in-ckrq" datasource="T.CHECKPASS_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq1"  group="in-ckrq1,in-jsrq1" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.CHECKPASS_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
							<td><label>终审通过时间：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq2"  group="in-ckrq2,in-jsrq2" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
							<td><label>故障时间：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq3"  group="in-ckrq3,in-jsrq3" style="width:75px;" name="in-ckrq" datasource="T.FAULT_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq3"  group="in-ckrq3,in-jsrq3" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.FAULT_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
						</tr>
						<tr>
							<td><label>检修时间：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq4"  group="in-ckrq4,in-jsrq4" style="width:75px;" name="in-ckrq" datasource="T.REPAIR_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq4"  group="in-ckrq4,in-jsrq4" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.REPAIR_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
					   		 <td><label>活动代码：</label></td>
							 <td><input type="text" id="activityCode" name="activityCode" datatype="1,is_null,30" value=""  operation="like" /></td>
					   		 <td><label>车辆型号：</label></td>
							 <td><input type="text" id="modelsCode" name="modelsCode"  datatype="1,is_null,30" value=""  operation="like" /></td>
						</tr>
						<tr>
							<td><label>购车时间：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq5"  group="in-ckrq5,in-jsrq5" style="width:75px;" name="in-ckrq" datasource="T.BUY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq5"  group="in-ckrq5,in-jsrq5" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.BUY_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
							 <td><label>发动机号：</label></td>
							 <td><input type="text" id="modelsCode" name="modelsCode" datasource="T.ENGINE_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
							 <td><label>发动机型号：</label></td>
							 <td><input type="text" id="engineType" name="engineType"  datatype="1,is_null,30" value=""  operation="like" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">初审查询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="exp">初审导出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="finalSearch">终审查询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="finalExp">终审导出</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
		<div id="csDiv"  style="display: none">
		<div id="costs" >
			<table style="display:none;width:100%;" id="claimCostslist" name="claimCostslist" ref="costs" refQuery="claimCostsTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔类型</th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="CLAIM_AMOUNT" align="right"  refer="amountFormat">合计</th>
							<th fieldname="MATERIAL_COSTS" align="right"  refer="amountFormat">材料费</th>
							<th fieldname="OUT_AMOUNT" align="right"  refer="amountFormat">外出总费用</th>
							<th fieldname="OUT_COSTS" align="right"  refer="amountFormat">一次外出费</th>
							<th fieldname="SEC_VEH_COSTS" align="right"  refer="amountFormat">二次外出费</th>
							<th fieldname="MEALS_COSTS" align="right"  refer="amountFormat">在途补助</th>
							<th fieldname="SEVEH_COSTS" align="right"  refer="amountFormat">服务车费</th>
							<th fieldname="TRAVEL_COSTS" align="right"  refer="amountFormat">差旅费</th>
							<th fieldname="OTHER_COSTS" align="right"  refer="amountFormat">其他费</th>
							<th fieldname="WORK_COSTS" align="right"  refer="amountFormat">工时费</th>
							<th fieldname="MAINTENANCE_COSTS" align="right"  refer="amountFormat">首保费</th>
							<th fieldname="SERVICE_COST" align="right"  refer="amountFormat">服务活动费</th>
							<th fieldname="SAFE_COSTS" align="right"  refer="amountFormat">安全检查费</th>
							<th fieldname="TRAIN_COSTS" align="right"  refer="amountFormat">售前培训费</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			</div>
			<div id="zsDiv"  style="display: none">
			<div id="zsCosts">
				<table style="display:none;width:100%;" id="claimFinalCostslist" name="claimFinalCostslist" ref="zsCosts" refQuery="claimCostsTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔类型</th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="CLAIM_AMOUNT" align="right"  refer="amountFormat">合计</th>
							<th fieldname="MATERIAL_COSTS" align="right"  refer="amountFormat">材料费</th>
							<th fieldname="OUT_AMOUNT" align="right"  refer="amountFormat">外出总费用</th>
							<th fieldname="WORK_COSTS" align="right"  refer="amountFormat">工时费</th>
							<th fieldname="MAINTENANCE_COSTS" align="right"  refer="amountFormat">首保费</th>
							<th fieldname="SERVICE_COST" align="right"  refer="amountFormat">服务活动费</th>
							<th fieldname="SAFE_COSTS" align="right"  refer="amountFormat">安全检查费</th>
							<th fieldname="TRAIN_COSTS" align="right"  refer="amountFormat">售前培训费</th>
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
</div>
</body>
</html>