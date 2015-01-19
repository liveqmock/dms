<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆数据维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction/vehicleSearch.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#vehicleform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"vehicleList");		
	});
	//导出
	$("#exp").bind("click", function () {
	    var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=vehicleImp.xls");
	    window.location.href = url;
	});
	//导入
	$("#imp").bind("click",function(){
		importXls("MAIN_VEHICLE_TMP",'',23,3,"/jsp/dms/oem/service/basicinfomng/vehicledata/importSuccess1.jsp");
	});
	$("#btn-add").bind("click",function(event){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/vehicledata/vehicleDataDetail.jsp?action=1", "vehicleEdit", "车辆数据维护", options); 
	});
});
//导入回调方法
function impCall(){
	var $f = $("#vehicleform");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"search",1,sCondition,"vehicleList");	
}
//修改
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/vehicledata/vehicleDataDetail.jsp?action=2", "vehicleEdit", "车辆数据维护", options);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;车辆数据维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="vehicleform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="vehicleTable">
					<tr>
						<td><label>发动机号：</label></td>
						<td><input type="text" id="engineNo" name="engineNo" datasource="T.ENGINE_NO" datatype="1,is_null,30" value="" operation="like"/></td>
						<td><label>VIN：</label></td>
						<td><input type="text" id="vin" name="vin" datasource="T.VIN"  datatype="1,is_null,17" operation="like" value="" /></td>
					</tr>
					<tr>
						<td><label>车辆型号：</label></td>
						<td><input type="text" id="MODELS_CODE" name="MODELS_CODE" datasource="T.MODELS_CODE"  datatype="1,is_null,30" operation="like" value="" /></td>
						<td><label>车辆状态：</label></td>
						<td><select type="text" id="status" name="status" class="combox" kind="dic" src="YXBS"  datasource="T.STATUS"  datatype="1,is_null,6" >
								<option value=-1 >--</option>
							</select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="exp">导出模版</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="imp">批量导入</button></div></div></li>
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
						<th colwidth="45" type="link" title="[编辑]"  action="doUpdate" >操作</th>
						<th fieldname="VIN" >VIN</th>
						<th fieldname="VEHICLE_ID" style="display:none" >车辆ID</th>
						<th fieldname="ENGINE_NO" >发动机号</th>
						<th fieldname="ENGINE_TYPE" >发动机型号</th>
						<th fieldname="MODELS_CODE" >车辆型号</th>
						<th fieldname="DRIVE_FORM" >驱动形式</th>
						<th fieldname="USER_TYPE" >用户类型</th>
						<th fieldname="VEHICLE_USE" >车辆用途</th>
						<th fieldname="CERTIFICATE" >合格证号</th>
						<th fieldname="CERTIFICATEDATE" >合格发证日期</th>
						<th fieldname="FACTORY_DATE" >车辆出厂日期</th>
						<th fieldname="BUY_DATE" >购车日期</th>
						<th fieldname="MAINTENANCE_DATE" >首保日期</th>
						<th fieldname="SALE_STATUS" >车辆销售状态</th>
						<th fieldname="GUARANTEE_NO" >保修卡号</th>
						<th fieldname="LICENSE_PLATE" >车牌号码</th>
						<th fieldname="USER_NAME" >用户姓名</th>
						<th fieldname="USER_NO" >身份证号</th>
						<th fieldname="LINK_MAN" >联系人</th>
						<th fieldname="PHONE" >联系电话</th>
						<th fieldname="USER_ADDRESS" >地址</th>
						<th fieldname="STATUS" >车辆状态</th>
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