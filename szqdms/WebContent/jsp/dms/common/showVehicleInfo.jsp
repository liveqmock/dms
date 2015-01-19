<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String vehicleId = request.getParameter("vehicleId");
	if(vehicleId=="")
	{
		vehicleId="1";
	}
%>    
<div id="vheicleInfo" >
<form id="dia-fm-vehicleHid" style="display: none"></form>
	<div class="tabs" eventType="click" id="dia-vehicleTabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
				    <li><a href="javascript:void(0)"><span>车辆信息</span></a></li>
					<li><a href="javascript:void(0)"><span>索赔信息</span></a></li>
				</ul>
			</div>
		</div>
	 	<div class="tabsContent">	
			<div class="page" id="dia-div-clxx" >
			<div class="pageContent" style="" >
				<form method="post" id="dia-di-vehicleInfo" class="editForm" style="width: 99%;">
					<div align="left">
					<fieldset>
					<table class="editTable" id="di_tab_vinCh">
						<tr>
							<td><label>VIN：</label></td>
							<td><input type="text" id="dia-di-vin" name="dia-di-vin" datasource="VIN"  readonly="readonly"/></td>
							<td><label>发动机号：</label></td>
							<td colspan="3"><input type="text" id="dia-di-engine_no" name="dia-di-engine_no" datasource="ENGINE_NO"  readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td><label>车辆型号：</label></td>
							<td><input type="text" id="dia-di-models_code" name="dia-di-models_code" datasource="MODELS_CODE"  readonly="readonly" /></td>
							<td><label>合格证号：</label></td>
							<td><input type="text" id="dia-di-certificate" name="dia-di-certificate"  datasource="CERTIFICATE" value=""readonly="readonly" /></td>
							<td><label>发动机型号：</label></td>
							<td><input type="text" id="dia-di-engine_type" name="dia-di-engine_type" datasource="ENGINE_TYPE" readonly="readonly" value=""/></td>
						</tr>
						<tr>
							<td><label>用户类型：</label></td>
							<td><input type="text" id="dia-di-user_type" name="dia-di-user_type" datasource="USER_TYPE" value="" readonly="readonly" /></td>
							<td><label>车辆用途：</label></td>
							<td><input type="text" id="dia-di-vehicle_use" name="dia-di-vehicle_use"  datasource="VEHICLE_USE" value="" readonly="readonly" /></td>
							<td><label>驱动形式：</label></td>
							<td><input type="text" id="dia-di-drive_form" name="dia-di-drive_form" datasource="DRIVE_FORM" value="" readonly="readonly" /></td>
						</tr>
						<tr >
							<td><label>购车日期：</label></td>
							<td><input type="text" id="dia-di-buy_date" name="dia-di-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
							<td><label>保修卡号：</label></td>
							<td><input type="text" id="dia-di-guarantee_no" name="dia-di-guarantee_no" datasourc="GUARANTEE_NO" value=""  readonly="readonly" /> </td>
							<td><label>定保次数：</label></td>
							<td><input type="text" id="dia-di-g_count" name="dia-di-g_count"  datasource="G_COUNT" value="" readonly="readonly"/></td>
						</tr>
						<tr >
							<td><label>出厂日期：</label></td>
							<td><input type="text" id="dia-di-factory_date" name="dia-di-factory_date" datasource="FACTORY_DATE" value=""  readonly="readonly" /></td>
							<td><label>首保日期：</label></td>
							<td ><input type="text" id="dia-di-maintenance_date" name="dia-di-maintenance_date" datasource="MAINTENANCE_DATE" value=""  readonly="readonly" /></td>
							<td><label>发证日期：</label></td>
							<td ><input type="text" id="dia-di-certificateDate" name="dia-di-certificateDate" datasource="CERTIFICATEDATE" value=""  readonly="readonly" /></td>
						</tr>
						<tr>
							<td><label>当前里程：</label></td>
							<td><input type="text" id="dia-di-mileage" name="dia-di-mileage"  datasource="MILEAGE" value="" readonly="readonly"/></td>
							<td><label>上次里程：</label></td>
							<td><input type="text" id="dia-di-lrunkm" name="dia-di-lrunkm"  datasource="LRUNKM" value="" readonly="readonly"/></td>
							<td><label>定保里程：</label></td>
							<td><input type="text" id="dia-di-drunkm" name="dia-di-drunkm"  datasource="DRUNKM" value="" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label>车牌号码：</label></td>
							<td><input type="text" id="dia-di-license_plate" name="dia-di-license_plate" datasource="LICENSE_PLATE" value="" readonly="readonly"/></td>
							<td><label>用户名称：</label></td>
							<td><input type="text" id="dia-di-user_name" name="dia-di-user_name" datasource="USER_NAME" value="" readonly="readonly" /></td>
							<td><label>身份证号：</label></td>
							<td><input type="text" id="dia-di-user_no" name="dia-di-user_no"  datasource="USER_NO" value="" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label>联系人：</label></td>
							<td><input type="text" id="dia-di-link_man" name="dia-di-link_man" datasource="LINK_MAN" value="" readonly="readonly"/></td>
							<td><label>电话：</label></td>
							<td><input type="text" id="dia-di-phone" name="dia-di-phone" datasource="PHONE" value="" readonly="readonly" /></td>
							<td><label>用户地址：</label></td>
							<td><textarea id="dia-di-user_address" style="width: 150px; height: 30px;" name="dia-di-user_address" datasource="USER_ADDRESS" readonly="readonly"></textarea></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
				<div class="formBar" style="padding-right:20px;">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一页</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
			<div class="page" id="dia-divpage-spxx" >
			<div class="pageContent" style="" >
				<fieldset>
					<div id="dia-div-vehicleClaim" >
						<table style="width:99%;" id="dia-tab-vehicleClaim" name="tablist" ref="dia-div-vehicleClaim" refQuery="dia-fm-vehicleHid"  >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:none" ></th>
									<th fieldname="ORG_ID" >渠道名称</th>
									<th fieldname="CLAIM_NO" >索赔单号</th>
									<th fieldname="CLAIM_TYPE" >索赔类型</th>
									<th fieldname="CLAIM_STATUS" >索赔单状态</th>
									<th fieldname="REPAIR_DATE" >提报时间</th>
									<th fieldname="MILEAGE" >里程</th>
								</tr>
							</thead>
						</table>
					</div>
				</fieldset>
				<div class="formBar" style="padding-right:20px;">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一页</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>	
	</div>		
</div>
<script type="text/javascript">
//弹出窗体
var searchVinUrl="<%=request.getContextPath()%>/service/common/VehicleInfoMngAction/";
var diaDiVehicleId='<%=vehicleId%>';
var seClaimFlag=true;
$(function()
{//设置tabs的上一步事件
	$("button[name='btn-pre']").bind("click",function(event) 
			{
				$("#dia-vehicleTabs").switchTab(parseInt($("#dia-vehicleTabs").attr("currentIndex")) - 1);
			});
			//设置tabs的下一步事件
			$("button[name='btn-next']").bind("click", function(event) 
			{
				var $tabs = $("#dia-vehicleTabs");
				switch (parseInt($tabs.attr("currentIndex"))) 
				{
					case 0:
						if(seClaimFlag)
						{
							searchVehicleClaimInfo();
							seClaimFlag=false;
						}
						break;
					case 1:
						break;
				}
				$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
				//跳转后实现方法
				(function(ci) 
				{
					switch (parseInt(ci)) 
					{
						case 1://第2个tab页					
							break;
						case 2://第3个tab页
							break;
						default:
							break;
					}
				})
				(parseInt($tabs.attr("currentIndex")));
			});
	var searchVehicleUrl = searchVinUrl +"searchVehicleInfo.ajax?vehicleId="+diaDiVehicleId;
	sendPost(searchVehicleUrl,"","",searchVehicleCallBack,"false");
});
function searchVehicleCallBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			
			var objxml = res.documentElement;
			setEditValue("dia-di-vehicleInfo",objxml);
		}	
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
function searchVehicleClaimInfo()
{
	var $f = $("#dia-fm-vehicleHid");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var vehicleClaimSearchUrl1 =searchVinUrl+"searchClaimInfo.ajax?vehicleId="+diaDiVehicleId; 
	doFormSubmit($f,vehicleClaimSearchUrl1,"",1,sCondition,"dia-tab-vehicleClaim");
}
</script>