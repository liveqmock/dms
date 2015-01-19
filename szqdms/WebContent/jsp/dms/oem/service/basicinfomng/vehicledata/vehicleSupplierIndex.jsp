<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆生产厂家维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction/vehicleProductSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction/vehicleProductDelete.ajax";
var options = {max:false,width:800,height:230,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#vehicleform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"vehicleList");		
	});
	$("#add").bind("click",function(){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/vehicledata/vehicleProductEdit.jsp?action=1", "edit", "车辆生产单位维护", options);
	});
});
//修改
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/vehicledata/vehicleProductEdit.jsp?action=2", "edit", "车辆生产单位维护", options);
}
var $row;
function doDelete(rowobj){
	$row = $(rowobj);
	var url = deleteUrl + "?vehicleProId="+$(rowobj).attr("VEHICLE_PRO_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#vehicleList").removeResult($row);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;车辆生产单位维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="vehicleform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="vehicleTable">
					<tr>
						<td><label>生产单位：</label></td>
						<td><input type="text" id="vehicleProduct" name="vehicleProduct" datasource="T.VEHICLE_PRODUCT" datatype="1,is_null,100" value="" operation="like"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="add" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="vehicle" >
			<table style="display:none;width:100%;" id="vehicleList" name="vehicleList" ref="vehicle" refQuery="vehicleTable">
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none" colwidth="10" ></th>
						<th fieldname="VIN_START" >VIN开始</th>
						<th fieldname="VIN_END" >VIN结束</th>
						<th fieldname="VEHICLE_PRODUCT" >生产单位</th>
						<th colwidth="120" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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