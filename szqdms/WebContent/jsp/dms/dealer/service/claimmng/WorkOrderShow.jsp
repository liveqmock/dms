<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
	String workId = request.getParameter("workId");
	if(workId=="")
	{
		workId="1";
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工单信息</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $checkHisTrue=true;
var $fileTrue=true;
var authorStatus=null;
var action = "<%=action%>";
var diaWorkId='<%=workId%>';
var dia_dialog = parent.$("body").data("WorkOrderShow");
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchMngAction";
var diaSaveAction = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction";
var itemSearchUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/itemSearch.ajax";
var fileSearchUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/fileSearch.ajax";
$(function() 
{
	if(action== "3"){
		/* var selectedRows = parent.$("#tab-list").getSelectedRows();
		setEditValue("dia-fm-workOrder",selectedRows[0].attr("rowdata")); */
		var searchVehicleUrl = searchUrl +"/searchWorkOrder.ajax?workId="+diaWorkId;
		sendPost(searchVehicleUrl,"","",diasearchCallBack,"false");
		
	}
	
	//设置高度
	$("#preAuthBasic").height(document.documentElement.clientHeight-30);
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("workOrder");
		return false;
	});
});
//新增回调函数
function diasearchCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			
			var objxml = res.documentElement;
			setEditValue("dia-fm-workOrder",objxml);
			$("#dia-vin").attr("readonly",true);	
			$("#dia-applyUser").attr("readonly",true);	
			$("#dia-applMobil").attr("readonly",true);	
			$("#dia-applyDate").attr("readonly",true);
			$("#dia-WorkType").attr("readonly",true);
			$("#dia-ifOut").attr("readonly",true);	
			$("#dia-applyAddress").attr("readonly",true);		
		}	
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
<div id="dia-layout">
		<div class="page" id="preAuthBasic" style="overflow:auto;">
			<div class="pageContent">
				<form method="post" id="dia-fm-workOrder" class="editForm" style="width: 99%;">
					<input type="hidden" id="workId" datasource="WORK_ID"/>
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="modelsId" datasource="MODELS_ID"/>
           		    <input type="hidden" id="dia-user_type_code" name="dia-user_type_code" datasource="USER_TYPE" value="" readonly="readonly" />	
					<input type="hidden" id="dia-vehicle_use_code" name="dia-vehicle_use_code"  datasource="VEHICLE_USE" value="" readonly="readonly" />
					<div align="left">
						<fieldset>
						<legend align="right">
							<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;工单维护基本信息编辑&gt;&gt;</a>
						</legend>
						<table class="editTable" id="sbjjxx">
						<tr>
							<td><label>工单编号：</label></td>
							<td><input type="text" id="dia-workNo" name="dia-workNo" datasource="WORK_NO"  readonly="readonly" /></td>
							 <td><label>工单VIN：</label></td>
							 <td><input type="text" id="dia-vin" name="dia-vin" datasource="WORK_VIN" datatype="1,is_vin,17" value="" operation="like" /></td>
							 
						 </tr>
						 <tr>
						   	 <td><label>报修人：</label></td>
							 <td><input type="text" id="dia-applyUser" name="dia-applyUser" datasource="APPLY_USER" value="" datatype="1,is_null,30" /></td>
				             <td><label>报修人电话：</label></td>
				             <td><input type="text" id="dia-applMobil" name="dia-applMobil" datasource="APPLY_MOBIL" value="" datatype="1,is_digit,30" /></td>
				        	 <td><label>报修时间：</label></td>
				             <td><input type="text" id="dia-applyDate" name="dia-applyDate" value="" datasource="APPLY_DATE" datatype="1,is_null,30" /></td>
				         </tr>
				        <!--  <tr>
						   	 <td><label>维修人：</label></td>
							 <td><input type="text" id="dia-repairUser" name="dia-repairUser" datasource="REPAIR_USER" value="" datatype="1,is_null,30" /></td>
				             <td><label>维修人电话：</label></td>
				             <td><input type="text" id="dia-repUserTel" name="dia-repUserTel" datasource="REP_USER_TEL" value="" datatype="0,is_digit,30" /></td>
				        	 <td><label>维修时间：</label></td>
				             <td><input type="text" id="dia-repairDate" name="dia-repairDate" value="" datasource="REPAIR_DATE" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
				         </tr> -->
				         <tr >
				         	 <td><label>工单类型：</label></td>
						     <td><input type="text" id="dia-WorkType" name="dia-WorkType"   kind="dic" datasource="WORK_TYPE" datatype="1,is_null,8" value=""/ >
									
							 </td>
				         	 <td><label>是否外出：</label></td>
						     <td><input type="text" id="dia-ifOut" name="dia-ifOut"  kind="dic" datasource="IF_OUT"  datatype="1,is_null,8" value="" />
								 
							 </td>
				          </tr>
			              <tr>
				              <td><label>报修地址：</label></td>
				              <td colspan="5"><textarea id="dia-applyAddress" style="width: 450px; height: 50px;" datasource="APPLY_ADDRESS" name="APPLY_ADDRESS" datatype="1,is_null,300"></textarea></td>
				            </tr>
				          </table>
						</fieldset>
					</div>
				</form>
				<div class="formBar">
					<ul>
				<!-- 		<li id="dia-save-li"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
 -->						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
</div>
</body>
</html>