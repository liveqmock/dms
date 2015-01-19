<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动车辆查询</title>
<script type="text/javascript">

var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction";
$(function(){
	//查询按钮响应
	$("#search").bind("click", function(event){
		var $f = $("#serviceActivityVinform");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchVehicel = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction/searchOemVehicel.ajax";
		doFormSubmit($f,searchVehicel,"search",1,sCondition,"tab-serviceActivity");
	});
	$("#export").bind("click",function(){
		var $f = $("#serviceActivityVinform");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction/download.do");
		$("#exportFm").submit();
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
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动车辆查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="serviceActivityVinform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="serviceActivityVinform">
						<tr>
                            <td><label>活动代码：</label></td>
                            <td><input type="text" id="dia-ACTIVITY_CODE" name="dia-ACTIVITY_CODE" datasource="T.ACTIVITY_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>活动名称：</label></td>
                            <td><input type="text" id="dia-ACTIVITY_NAME" name="dia-ACTIVITY_NAME" datasource="T.ACTIVITY_NAME" datatype="1,is_null,30" operation="like"/></td>
                     	    <td><label>登记日期：</label></td>
						    <td colspan="5">
					    		<input kind="date" type="text" id="in-ckrq" group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input kind="date" type="text" id="in-jsrq" group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
                        </tr>
						<tr>
							<td><label>VIN：</label></td>
      						<td><input type="text" id="vin-id" name="vin"   datatype="1,is_null,17" datasource="T.VIN" operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
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
								<th fieldname="CODE" >渠道商代码</th>
								<th fieldname="ONAME" >渠道商名称</th>
								<th fieldname="ACTIVITY_NAME" >活动名称</th>
								<th fieldname="VEHICLE_ID" style="display:none">车辆ID</th>
								<th fieldname="ACTIVITY_ID" style="display:none">活动ID</th>
								<th fieldname="VIN" >VIN</th>
								<th fieldname="CLAIM_USER">是否已报单</th>
								<th fieldname="APPLY_DATE" >登记日期</th>
								<th fieldname="SOLUTION" >方案</th>
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