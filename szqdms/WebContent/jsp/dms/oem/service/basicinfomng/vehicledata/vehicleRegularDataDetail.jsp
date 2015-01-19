<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-vehicle" class="editForm" style="width: 100%;">
			<input type="hidden" id="dia-vehicleId" datasource="VEHICLE_ID"/>
			<div id="dia-vehicle">
			<table width="100%" id="dia-vehicleTable" name="dia-vehicleTable" class="editTable" >
                 <tr>
					<td><label>VIN：</label></td>
					<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="" datasource="VIN" datatype="0,is_vin,17" /></td>
					<td><label>发动机号：</label></td>
					<td ><input type="text" id="dia-in-engineno" name="dia-in-engineno" value="" datasource="ENGINE_NO" datatype="0,is_fdjh,30" operation="like" /></td>
					<td><label>车辆状态：</label></td>
					<td>
						<select type="text" id="dia-in-status" name="dia-in-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,6" value="">
							<option value="100101" selected>有效</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>车辆型号：</label></td>
					<td><input type="text" id="dia-in-modelsCode" name="dia-in-modelsCode" value="" datasource="MODELS_CODE" datatype="0,is_null,30" /></td>
					<td><label>合格证号：</label></td>
					<td><input type="text" id="dia-in-certificate" name="dia-in-certificate" value="" datasource="CERTIFICATE" datatype="1,is_null,30"  /></td>
					<td><label>发动机型号：</label></td>
					<td><input type="text" id="dia-in-engineType" name="dia-in-engineType" value="" datasource="ENGINE_TYPE" kind="dic" src="T#SE_BA_ENGINE_TYPE:TYPE_CODE:TYPE_NAME:STATUS=100201 " datatype="0,is_null,30"  /></td>
				</tr>
				<tr>
					<td><label>用户类型：</label></td>
					<td>
						<select type="text" id="dia-in-userTpe" name="dia-in-userTpe" kind="dic" src="CLYHLX" datasource="USER_TYPE" datatype="0,is_null,6" value="">
							<option value="300101" selected>民车</option>
						</select>
					</td>
					<td><label>车辆用途：</label></td>
					<td>
						<select type="text" id="dia-in-vehicleUse" name="dia-in-vehicleUse" kind="dic" src="CLYT" datasource="VEHICLE_USE" datatype="0,is_null,6" value="" >
							<option value="300201" selected>非公路用车</option>
						</select>
					</td>
					<td><label>驱动形式：</label></td>
					<td><select type="text" id="dia-in-driveForm" name="dia-in-driveForm" value="" datasource="DRIVE_FORM" class="combox" kind="dic" src="QDXS" datatype="0,is_null,6" >
					    	<option value="-1" >--</option>
					    </select>
					</td>
				</tr>
				<tr>
					<td><label>购车日期：</label></td>
					<td><input type="text" id="dia-in-buyDate" name="dia-in-buyDate" value="" datasource="BUY_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td><label>出厂日期：</label></td>
					<td><input type="text" id="dia-in-factoryDate" name="dia-in-factoryDate" value="" datasource="FACTORY_DATE" datatype="1,is_date,30"  kind="date" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td><label>首保日期：</label></td>
					<td><input type="text" id="dia-in-maintenanceDate" name="dia-in-maintenanceDate" value="" datasource="MAINTENANCE_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
				</tr>
				<tr >
					<td><label>合格发证日期：</label></td>
					<td><input type="text" id="dia-in-certificatedate" name="dia-in-certificatedate" value="" datasource="CERTIFICATEDATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td><label>保修卡号：</label></td>
					<td><input type="text" id="dia-in-guaranteeNo" name="dia-in-guaranteeNo" value="" datasource="GUARANTEE_NO" datatype="1,is_null,30" /> </td>
					<td><label>车辆销售状态：</label></td>
					<td>
						<select type="text" id="dia-in-saleStatus" name="dia-in-saleStatus" datasource="SALE_STATUS" kind="dic" src="CLXSZT" datatype="1,is_null,6" value="">
							<option value="300801" selected>未销售</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>车牌号码：</label></td>
					<td><input type="text" id="dia-in-licensePlate" name="dia-in-licensePlate" value="" datasource="LICENSE_PLATE" datatype="1,is_null,30" /></td>
					<td><label>用户名称：</label></td>
					<td><input type="text" id="dia-in-userName" name="dia-in-userName" value="" datasource="USER_NAME" datatype="1,is_null,30" /></td>
					<td><label>身份证号：</label></td>
					<td><input type="text" id="dia-in-userNo" name="dia-in-userNo" value="" datasource="USER_NO" datatype="1,is_idcard,30" /></td>
				</tr>
				<tr>
					<td><label>联系人：</label></td>
					<td><input type="text" id="dia-in-linkMan" name="dia-in-linkMan"  value="" datasource="LINK_MAN" datatype="1,is_null,30" /></td>
					<td><label>电话：</label></td>
					<td><input type="text" id="dia-in-phone" name="dia-in-phone" value="" datasource="PHONE" datatype="1,is_phone,30" /></td>
					<td><label>用户地址：</label></td>
					<td><textarea id="dia-in-userAddress" style="width: 150px; height: 30px;"  name="dia-in-userAddress" datasource="USER_ADDRESS" datatype="1,is_null,3	00"></textarea></td>
				</tr>
				<tr>
					<td><label>内部车型编码：</label></td>
					<td><input type="text" id="dia-in-insidecode" name="dia-in-insidecode"  value="" datasource="INSIDECODE" datatype="1,is_null,30" /></td>
					<td><label>车辆配置：</label></td>
					<td><input type="text" id="dia-in-configure" name="dia-in-configure" value="" datasource="CONFIGURE" datatype="1,is_phone,30" /></td>
					<td><label>合同号：</label></td>
					<td><input type="text" id="dia-in-contractareano" name="dia-in-contractareano" value="" datasource="CONTRACTAREANO" datatype="1,is_phone,30" /></td>
				</tr>
				<tr>
					<td><label>黑名单</label></td>
					<td>
						<select type="text" id="dia-in-blackListFlag" name="dia-in-blackListFlag" datasource="BLACKLISTFLAG" kind="dic" src="SF" datatype="1,is_null,6" value="">
							<option value="-1" selected>--</option>
						</select>
					</td>
					<td><label>系列：</label></td>
					<td><input type="text" id="dia-in-productlinecode" name="dia-in-productlinecode" value="" datasource="PRODUCTLINECODE" datatype="1,is_null,30" /></td>
					<td><label>生产厂家：</label></td>
					<td><input type="text" id="dia-in-vehicle_spp" name="dia-in-vehicle_spp" value="" datasource="VEHICLE_SUPP" datatype="1,is_null,30" /></td>
				</tr>
				<tr>
					<td><label>车辆保修政策享受日期</label></td>
					<td><input type="text" id="dia-in-repairDate" name="dia-in-repairDate" value="" datasource="REPAIR_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td><label>定保次数：</label></td>
					<td><input type="text" id="dia-in-gCount" name="dia-in-gCount" value="" datasource="G_COUNT" datatype="1,is_digit_0,6" /> </td>
				</tr>
			</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="dia-save" >保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>    	
	</div>
</div>
</div>
<script type="text/javascript">
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/VehicleDataAction";
$(function(){
	if(action != "1"){
		var selectedRows = $("#vehicleList").getSelectedRows();
		setEditValue("dia-fm-vehicle",selectedRows[0].attr("rowdata"));
	}else{
	}
	$("#dia-save").bind("click",function(event){
		var $fm = $("#dia-fm-vehicle");
		if (submitForm($fm) == false) {
			return false;
		}
		var sbrq=$("#dia-in-maintenanceDate").val();
		var xsrq=$("#dia-in-buyDate").val();
		if(""==sbrq){
		}else{
			if(xsrq>sbrq){
				$("#dia-in-buyDate").val("");
				$("#dia-in-maintenanceDate").val("");
				alertMsg.warn("申请的销售日期不可以大于首保日期。");
				return false; 
			}
		}
		var sCondition = {};
		sCondition = $fm.combined(1) || {};
		if(action == 1){
			sCondition = $fm.combined(1) || {};
			var addUrl = diaSaveAction + "/vehicleInsert.ajax";
			doNormalSubmit($fm,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else{	
			sCondition = $fm.combined(1) || {};
			var updateUrl = diaSaveAction + "/vehicleUpdate.ajax";
			doNormalSubmit($fm,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
		}
	});
	
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	
});
//新增回调函数 
function diaInsertCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			alertMsg.warn("VIN已存在,不可添加。");
		}else{
	 		var rows = res.getElementsByTagName("ROW");
	 		var vId =getNodeText(rows[0].getElementsByTagName("VEHICLE_ID").item(0));
			$("#vehicleList").insertResult(res,0);
			$("td input[type=radio]",$("#vehicleList_content").find("tr").eq(0)).attr("checked",true);
			if($("#vehicleList_content").size()>0){
				$("td input[type=radio]",$("#vehicleList_content").find("tr").eq(0)).attr("checked",true);			
			}else{
				$("td input[type=radio]",$("#vehicleList").find("tr").eq(0)).attr("checked",true);
			}
			action=2;
			$("#dia-vehicleId").val(vId);
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//保存回调
function diaUpdateCallBack(res){
	try
	{
		var selectedIndex = $("#vehicleList").getSelectedIndex();
		$("#vehicleList").updateResult(res,$("#vehicleList").getSelectedIndex());
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>

