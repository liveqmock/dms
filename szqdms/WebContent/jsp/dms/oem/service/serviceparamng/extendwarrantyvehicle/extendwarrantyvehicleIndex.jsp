<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>延保策略与车辆关系</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyVehicleMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyVehicleMngAction/delete.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:150,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchExtendWarrantyVehicle");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-extendWarrantyVehicleList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/serviceparamng/extendwarrantyvehicle/extendwarrantyvehicleAdd.jsp?action=1", "add", "新增延保策略与车辆关系", diaAddOptions);
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceparamng/extendwarrantyvehicle/extendwarrantyvehicleAdd.jsp?action=2", "update", "修改延保策略与车辆关系", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?relationId="+$(rowobj).attr("RELATION_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}

//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-extendWarrantyVehicleList").removeResult($row);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;延保策略与车辆关系</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchExtendWarrantyVehicle" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchExtendWarrantyVehicle">
						<tr>
							<td><label>延保代码：</label></td>
							<td><input type="text" id="warrantyCode" name="warrantyCode" datasource="WARRANTY_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>延保名称：</label></td>
							<td><input type="text" id="warrantyName" name="warrantyName" datasource="WARRANTY_NAME" operation="like" datatype="1,is_null,30" /></td>
							<td><label>VIN：</label></td>
							<td><input type="text" id="vin" name="vin" datasource="VIN" operation="like" datatype="1,is_null,17" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_extendWarrantyVehicleList">
				<table style="display:none;width:100%;" id="tab-extendWarrantyVehicleList" name="tablist" ref="page_extendWarrantyVehicleList" refQuery="tab-searchExtendWarrantyVehicle">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="WARRANTY_CODE" >延保代码</th>
							<th fieldname="WARRANTY_NAME">延保名称</th>
							<th fieldname="VIN">VIN</th>
							<th fieldname="CREATE_USER">创建人</th>
							<th fieldname="CREATE_TIME">创建时间</th>
							<th fieldname="UPDATE_USER">修改人</th>
							<th fieldname="UPDATE_TIME">修改时间</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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