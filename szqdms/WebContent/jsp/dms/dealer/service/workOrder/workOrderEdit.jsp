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
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工单维护新增</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $checkHisTrue=true;
var $fileTrue=true;
var authorStatus=null;
var action = "<%=action%>";
var dia_dialog = parent.$("body").data("workOrder");
var diaSaveAction = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction";
var itemSearchUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/itemSearch.ajax";
var fileSearchUrl = "<%=request.getContextPath()%>/service/workOrder/WorkOrderMngAction/fileSearch.ajax";
$(function() 
{
	if(action != "1"){
		var selectedRows = parent.$("#workOrderlist").getSelectedRows();
		setEditValue("dia-fm-workOrder",selectedRows[0].attr("rowdata"));
	}else{
		$("#dia-report_li").hide();
	}
	//保存
	$("#dia-save").bind("click",function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-workOrder");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-workOrder").combined(1) || {};
		if(action == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/workOrderInsert.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/workOrderUpdate.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//提报
	$("#dia-report").bind("click",function(){
		var workId=$("#workId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-workOrder");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-workOrder").combined(1) || {};
		var reportUrl = diaSaveAction + "/workOrderReport.ajax?workId="+workId+"&flag="+2;
		doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	});
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
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		var workId =getNodeText(rows[0].getElementsByTagName("WORK_ID").item(0));
		var workNo =getNodeText(rows[0].getElementsByTagName("WORK_NO").item(0));
		parent.$("#workOrderlist").insertResult(res,0);
		if(parent.$("#workOrderlist_content").size()>0){
			$("td input[type=radio]",parent.$("#workOrderlist_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#workOrderlist").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		$("#workId").val(workId);
		$("#dia-workNo").val(workNo);
		$("#dia-report_li").show();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{	
		var selectedIndex = parent.$("#workOrderlist").getSelectedIndex();
		parent.$("#workOrderlist").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//提报回调方法
function diaReportCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $row =  parent.$("#workOrderlist").getSelectedRows();
			if($row[0]){
				parent.$("#workOrderlist").removeResult($row[0]);
				parent.$.pdialog.close("workOrder");
			}
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
							<td><input type="text" id="dia-workNo" name="dia-workNo" datasource="WORK_NO" value="系统自动生成" readonly="readonly" /></td>
							<td><label>工单VIN：</label></td>
							<td><input type="text" id="dia-vin" name="dia-vin" datasource="WORK_VIN" datatype="0,is_vin,17" value="" operation="like" /></td>
						 </tr>
						 <tr>
						   	 <td><label>报修人：</label></td>
							 <td><input type="text" id="dia-applyUser" name="dia-applyUser" datasource="APPLY_USER" value="" datatype="1,is_null,30" /></td>
				             <td><label>报修人电话：</label></td>
				             <td><input type="text" id="dia-applMobil" name="dia-applMobil" datasource="APPLY_MOBIL" value="" datatype="1,is_digit,30" /></td>
				        	 <td><label>报修时间：</label></td>
				             <td><input type="text" id="dia-applyDate" name="dia-applyDate" value="" datasource="APPLY_DATE" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
				         </tr>
				         <tr>
						   	 <td><label>维修人：</label></td>
							 <td><input type="text" id="dia-repairUser" name="dia-repairUser" datasource="REPAIR_USER" value="" datatype="1,is_null,30" /></td>
				             <td><label>维修人电话：</label></td>
				             <td><input type="text" id="dia-repUserTel" name="dia-repUserTel" datasource="REP_USER_TEL" value="" datatype="0,is_digit,30" /></td>
				        	 <td><label>维修时间：</label></td>
				             <td><input type="text" id="dia-repairDate" name="dia-repairDate" value="" datasource="REPAIR_DATE" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
				         </tr>
				         <tr >
				         	 <td><label>工单类型：</label></td>
						     <td><select type="text" id="dia-WorkType" name="dia-WorkType"   kind="dic" datasource="WORK_TYPE" class="combox" src="PGLX" datatype="0,is_null,8" value="" >
									<option value=-1 selected>--</option>
								</select>
							 </td>
				         	 <td><label>是否外出：</label></td>
						     <td><select type="text" id="dia-ifOut" name="dia-ifOut"  kind="dic" datasource="IF_OUT" class="combox" src="SF" datatype="0,is_null,8" value="" >
									<option value=-1 selected>--</option>
								</select>
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
						<li id="dia-save-li"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="dia-report_li"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
</div>
</body>
</html>