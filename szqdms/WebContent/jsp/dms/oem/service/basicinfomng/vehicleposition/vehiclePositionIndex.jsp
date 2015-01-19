<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆部位维护</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehiclePositionMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehiclePositionMngAction/delete.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:820,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchVehiclePosition");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-vehiclePositionList");
	});
	
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/vehicleposition/vehiclePositionAdd.jsp?action=1", "add", "新增车辆部位", diaAddOptions);
	});
	
	//导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchVehiclePosition");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/basicinfomng/VehiclePositionMngAction/download.do");
		$("#exportFm").submit();
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/vehicleposition/vehiclePositionAdd.jsp?action=2", "update", "修改车辆部位", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?positionId="+$(rowobj).attr("POSITION_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}

//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-vehiclePositionList").removeResult($row);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;车辆部位维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchVehiclePosition" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchVehiclePosition">
						<tr>
							<td><label>父级总成代码：</label></td>
							<td><input type="text" id="pCode" name="pCode" datasource="P_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>父级总成名称：</label></td>
							<td><input type="text" id="pName" name="pName" datasource="P_NAME" operation="like" datatype="1,is_null,30" /></td>
							<td><label>有效标示：</label></td>
							<td>
		        				<select type="text" id="status"  name="status" datasource="STATUS" kind="dic" src="YXBS" datatype="1,is_null,6" >
				    				<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    				<option value="<%=DicConstant.YXBS_02 %>" >无效</option>
				    				<option value="-1">--</option>
				    			</select>
		        			</td>
						</tr>
						<tr>
							<td><label>车辆部位代码：</label></td>
							<td><input type="text" id="positionCode" name="positionCode" datasource="POSITION_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>车辆部位名称：</label></td>
							<td><input type="text" id="positionName" name="positionName" datasource="POSITION_NAME" operation="like" datatype="1,is_null,30" /></td>
							<td><label>级别：</label></td>
							<td><input type="text" id="positionLevel" name="positionLevel" datasource="POSITION_LEVEL" operation="like" datatype="1,is_null,6" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expExcel">导出Excel</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_vehiclePositionList">
				<table style="display:none;width:100%;" id="tab-vehiclePositionList" name="tablist" ref="page_vehiclePositionList" refQuery="tab-searchVehiclePosition">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="P_NAME" >父级总成</th>
							<th fieldname="POSITION_LEVEL" >级别</th>
							<th fieldname="POSITION_CODE" >车辆部位代码</th>
							<th fieldname="POSITION_NAME">车辆部位名称</th>
							<th fieldname="STATUS">状态</th>
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
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>