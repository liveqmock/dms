<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆与驱动形式关系</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDriveMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDriveMngAction/resetStatus.ajax";
var diaAddOptions = {max:false,width:720,height:270,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
	$("#btn-search").bind("click", function(event){
		
		var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/vehicledrive/vehicleDriveRelationAdd.jsp?action=1", "vehicleDriveRelation", "新增车辆与驱动形式关系", diaAddOptions);
	});
});
//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/vehicledrive/vehicleDriveRelationAdd.jsp?action=2", "vehicleModel", "修改车辆与驱动形式关系", diaAddOptions);
}

var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?rlId="+$(rowobj).attr("RL_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-list").removeResult($row);
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
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;车辆与驱动形式关系</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">	
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				   <input type="hidden" id="status" name="status" datasource="STATUS" value="100201" />
					<tr>
						<td><label>车辆标识位：</label></td>
						<td><input type="text" id="vin" name="vin" datasource="VIN" operation="like"  datatype="1,is_null,30" value="" /></td>
						<td><label>驱动形式：</label></td>
						<td>
							<select type="text" class="combox" id="driveType" name="driveType" datasource="DRIVE_TYPE" operation="=" kind="dic" src="QDXS" datatype="1,is_null,6" >
					        	<option value="-1" selected>--</option>
					        </select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="VIN" >车辆标识位</th>
							<th fieldname="DRIVE_TYPE">驱动形式</th>
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