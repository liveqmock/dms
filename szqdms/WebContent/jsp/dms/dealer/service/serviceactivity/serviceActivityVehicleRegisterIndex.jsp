<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>活动车辆登记</title>
<script type="text/javascript">

var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
var searchUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction/searchPublish.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction";
$(function(){
	//查询按钮响应
	$("#search").bind("click", function(event){
		var vin= $("#vin-id").val();
		if(vin==null||vin==""){
			alertMsg.warn("请输入VIN!");
            return false;
		}
		var $f = $("#serviceActivityScopeTableform");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchVINUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction/searchVIN.ajax?vin="+vin;
		doFormSubmit($f,searchVINUrl,"search",1,sCondition,"tab-serviceActivity");
	});
});
function hyperlink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=doOpen("+$row.attr("ACTIVITY_ID")+","+$row.attr("IF_CLAIM")+","+$row.attr("IF_FIXCOSTS")+") class='op'>"+$row.attr("ACTIVITY_CODE")+"</a>";
}
function doOpen(activityId,ifClaim,ifFixcosts){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityScopeMngDetail.jsp?activityId="+activityId+"&ifClaim="+ifClaim+"&ifFixcosts="+ifFixcosts+"", "fwhdxxmx", "服务活动明细", options,true);
} 
//登记
function doRegister(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$row=$(rowobj);
    $('#activityId').val($row.attr("ACTIVITY_ID"));//促销活动ID
    $('#activityCode').val($row.attr("ACTIVITY_CODE"));
    $('#activityName').val($row.attr("ACTIVITY_NAME"));
    $('#vehicleVin').val($row.attr("VEHICLE_VIN"));
    $('#relationId').val($row.attr("RELATION_ID"));
    $('#orgCode').val($row.attr("ORG_CODE"));
    $('#orgName').val($row.attr("ORG_NAME"));
    $('#vehicleId').val($row.attr("VEHICLE_ID"));
    var $f = $("#fm-serviceVehicleInfo");
    if (submitForm($f) == false) return false;
    sCondition = $f.combined(1) || {};
    var addUrl = diaSaveAction + "/doRegister.ajax";
    doNormalSubmit($f, addUrl, "test", sCondition, updateHdztCallBack,"true");
}
//提报回调方法
function updateHdztCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result!='1'){
			/*  $("#search").click();  */
		}else{
			alertMsg.warn("该车已参加本次活动，不可重复参加。");
            return false;
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动车辆登记</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="serviceActivityVinform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="serviceActivityVinform">
						<tr>
							<td><label>VIN：</label></td>
							
      						<td><input type="text" id="vin-id" name="vin"   datatype="1,is_vin,17" datasource="VIN" operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_serviceActivityVin" >
				<table style="display:none;width:100%;" id="tab-serviceActivity" name="tablist" ref="page_serviceActivityVin" refQuery="serviceActivityVinform" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="ACTIVITY_CODE" refer="hyperlink">活动代码</th>
								<th fieldname="ACTIVITY_ID" style="display:none">活动ID</th>
								<th fieldname="RELATION_ID" style="display:none">活动车辆表ID</th>
								<th fieldname="ORG_CODE" style="display:none">服务商代码</th>
								<th fieldname="ORG_NAME" style="display:none">服务商名称</th>
								<th fieldname="VEHICLE_ID" style="display:none">车辆ID</th>
								<th fieldname="VEHICLE_VIN" style="display:none">vin</th>
								<th fieldname="ACTIVITY_NAME" >活动名称</th>
								<th fieldname="ACTIVITY_TYPE">活动类别</th>
								<th fieldname="MANAGE_TYPE" >处理方式</th>
								<th fieldname="START_DATE" >开始日期</th>
								<th fieldname="END_DATE" >结束日期</th>
								<th fieldname="ACTIVITY_STATUS" >活动状态</th>
								<th colwidth="100" type="link" title="[车辆登记]" action="doRegister">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
				</table>
			</div>
		</div>
	</div>
      <form id="fm-serviceVehicleInfo">
             <input type="hidden" id="activityId" name="activityId" datasource="ACTIVITY_ID"/>
             <input type="hidden" id="activityCode" name="activityCode" datasource="ACTIVITY_CODE"/>
             <input type="hidden" id="activityName" name="activityName" datasource="ACTIVITY_NAME"/>
             <input type="hidden" id="vehicleVin" name="vehicleVin" datasource="VEHICLE_VIN"/>
             <input type="hidden" id="orgCode" name="orgCode" datasource="ORG_CODE"/>
             <input type="hidden" id="orgName" name="orgName" datasource="ORG_NAME"/>
             <input type="hidden" id="relationId" name="relationId" datasource="RELATION_ID"/>
      </form>
</div>
</body>
</html>