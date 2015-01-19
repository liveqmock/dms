<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动渠道商管理</title>
<%
	String activityId=request.getParameter("activityId");
	String activityCode=request.getParameter("activityCode");
%>
<script type="text/javascript">
var activityCode = "<%=activityCode%>";
var activityId = <%=activityId%>;
var activityName =parent.activityName;
var searchUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction/searchDealers.ajax?activityId="+activityId; 
var diaAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction"; 
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		var $f = $("#fwhdxgform");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"fwhdxglb");
	});
	$("#add").bind("click", function(event){
		var options = {max:false,width:800,heigth:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityScopeMngDelAdd.jsp?activityId="+activityId, "fwhdfws", "服务活动渠道商新增", options);
	});
	$("#deleteDealer").bind("click", function(event){
		var mxid=$("#val0").val();
	    if(mxid=="")
	    {
	    	 alertMsg.warn("请选择渠道商！");
	    	return false;
	    }else{
	    	var scDealerUrl =diaAction+"/deleteDealer.ajax?mxid="+mxid;
			sendPost(scDealerUrl,"deleteDealer","",deleteDelCallBack,"true");
	    }
	});
	var dialog = parent.$("body").data("fwhdfwxg");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	var $f = $("#fwhdxgform");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"search",1,sCondition,"fwhdxglb");
});
function  deleteDelCallBack(res)
{
	try
	{
		var $f = $("#fwhdxgform");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"fwhdxglb");
		parent.$("#search").trigger("click");
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("fwhdxglb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("AREA_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#orgDelete"));
	}
	if($checkbox.attr("id").indexOf("mainDealerllb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("ORG_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#dealerVals"));
	}
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<div class="page">
		<div class="pageHeader">
			<form id="fwhdxgform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdxgTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="dealerCode" name="dealerCode" datasource="T.ORG_CODE" datatype="1,is_null,30" value="" operation="like" /></td>
							<td><label>渠道商名称：</label></td>
							<td><input type="text" id="dealerName" name="dealerName" datasource="T.ORG_NAME"  datatype="1,is_null,30" value="" operation="like" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="add" title=""><span>批量新增</span></a></li>
						<li class="line">line</li>
						<li><a class="delete" href="javascript:void(0);" id="deleteDealer" title="确定要删除吗?"><span>批量删除</span></a></li>
					</ul>
				</div>
			<div id="fwhdxg">
				<table width="100%" id="fwhdxglb" name="fwhdxglb" ref="fwhdxg" multi="orgDelete" style="display: none"  refQuery="fwhdxgTable" >
					<thead>
							<tr>
								<th type="multi" name="XH" unique="AREA_ID"></th>
								<th fieldname="ORG_CODE" >渠道商代码</th>
								<th fieldname="ORG_NAME" >渠道商名称</th>
							</tr>
					</thead>
				<tbody>
				</tbody>
				</table>
			</div>
			<div id="orgDelete">
				<table style="width:100%;">
					<tr>
						<td><textarea id="val0" name="multivals" style="width:400px;height:10px;display: none" column="1" ></textarea></td>
					</tr>
				</table>
			</div>
		</div>
		</div>
</div>
</body>
</html>