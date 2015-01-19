<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:车辆信息管理
	 Version:1.0
     Author：suoxiuli 2014-08-21
     Remark：driver
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆信息管理</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/VehicleAction/search.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:true,width:720,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchVehicle");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-vehicleList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/logisticsMng/vehicleAdd.jsp?action=1", "addVehicle", "新增车辆信息", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/storage/logisticsMng/vehicleAdd.jsp?action=2", "updateVehicle", "修改车辆信息", diaAddOptions);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	
	<div id="progressBar1" class="progressBar">loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 仓储管理   &gt; 物流信息管理   &gt; 车辆信息管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchVehicle">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchVehicle">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>承 运 商：</label></td>
					    <td>
					    	<input type="text" id="carrierCode"  name="carrierCode" kind="dic" 
					    		src="T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID}:1=1 AND STATUS='100201' " 
					    		datasource="CARRIER_CODE" datatype="1,is_null,30" />
					    	<input type="hidden" id="carrierName"  name="carrierName" datasource="CARRIER_NAME" />
					    	<input type="hidden" id="carrierId"  name="carrierId" datasource="CARRIER_ID" />
					    </td>
						<td><label>车辆类型：</label></td>
					    <td>
					    	<select class="combox" id="vehicleType" name="vehicleType" kind="dic" src="CYCX" datasource="VEHICLE_TYPE" datatype="1,is_null,6">
						    	<option value="-1" >----</option>
						    </select>
					    </td>
					</tr>
					<tr>
						<td><label>牌       照：</label></td>
					    <td>
					    	<input type="text" id="licensePlate"  name="licensePlate" datasource="LICENSE_PLATE"  datatype="1,is_carno,30" operation="like" />
					    </td>
					    <td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						
					</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_vehicleList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-vehicleList" name="tablist" ref="page_vehicleList" refQuery="tab-searchVehicle" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="CARRIER_CODE" >承运商代码</th>
							<th fieldname="CARRIER_NAME" >承运商名称</th>
							<th fieldname="VEHICLE_TYPE" >车辆类型</th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="LICENSE_PLATE" >牌照</th>
							<th fieldname="ENGINE_NO" >发动机号</th>
							<th fieldname="DRIVER_NAME" >司机姓名</th>
							<th fieldname="DRIVER_NO" >身份证号</th>
							<th fieldname="SEX" >性别</th>
							<th fieldname="PHONE" >手机</th>
							<th fieldname="FIXED_LINE" >固定电话</th>
							<th fieldname="EMAIL" >电子邮箱</th>
							<th fieldname="ADDRESS" >地址</th>
							<th fieldname="STATUS" >有效标识</th>
							<th fieldname="CREATE_USER" >添加人</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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

<script type="text/javascript">

function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("carrierCode") == 0)
	{  
		$("#carrierName").val($("#"+id).val());
		
		if($row.attr("CARRIER_ID")){
			$("#carrierId").val($row.attr("CARRIER_ID"));
		}
		return true;
	}
	
	if(id.indexOf("dia-carrierCode") == 0)
	{  
		$("#dia-carrierName").val($("#"+id).val());
		
		if($row.attr("CARRIER_ID")){
			$("#dia-carrierId").val($row.attr("CARRIER_ID"));
		}
		return true;
	}

	return true;
}

</script>

</html>