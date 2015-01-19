<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>军车销售日期更改</title>
<script type="text/javascript">

var diaSearchAction = "<%=request.getContextPath()%>/service/serviceparamng/VehicleSaleDateMngAction";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-oemSearch").bind("click", function(event){
		var $f = $("#fm-searchMilitaryVehicleSaleDate");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	
    	var searchUrl = diaSearchAction + "/search.ajax?userType="+'<%=DicConstant.CLYHLX_02%>';
		doFormSubmit($f,searchUrl,"btn-oemSearch",1,sCondition,"tab-militaryVehicleSaleDateList");
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceparamng/militaryvehiclesaledate/militaryVehicleSaleDateAdd.jsp?action=2", "update", "修改军车销售日期", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;军车销售日期更改</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchMilitaryVehicleSaleDate" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchMilitaryVehicleSaleDate">
						<tr>
							<td><label>VIN：</label></td>
      					  	<td><input type="text" id="oem_vin" name="oem_vin"  datasource="VIN" operation="like" datatype="1,is_null,17"  /></td>
							<td><label>车辆型号：</label></td>
							<td><input type="text" id="oem_modelsCode" name="oem_modelsCode" datasource="MODELS_CODE" operation="like" datatype="1,is_null,30" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-oemSearch">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_militaryVehicleSaleDateList">
				<table style="display:none;width:100%;" id="tab-militaryVehicleSaleDateList" name="tablist" ref="page_militaryVehicleSaleDateList" refQuery="tab-searchMilitaryVehicleSaleDate">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="MODELS_CODE" >车型</th>
							<th fieldname="BUY_DATE">销售日期</th>
							<th colwidth="85" type="link" title="[编辑]|"  action="doUpdate" >操作</th>
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