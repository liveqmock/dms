<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" style="width:100%;">
	   		<div align="left">
			<fieldset>
			<input type="hidden" id="dia-WORK_ID" name="dia-WORK_ID" datasource="WORK_ID" />
			<input type="hidden" id="dia-WORK_TYPE" name="dia-WORK_TYPE" datasource="WORK_TYPE" />
			<input type="hidden" id="dia-ifout" name="dia-ifout" />
			<input type="hidden" id="dia-WORK_STATUS" name="dia-WORK_STATUS" datasource="WORK_STATUS"  value=""/>
			<table class="editTable" id="dia-tb-option">
				<tr>
					<td><label>派工单号：</label></td>
					<td><input type="text" id="dia-WORK_NO" name="dia-WORK_NO" datasource="WORK_NO" datatype="1,is_null,30" readonly="readonly"/></td>
					<td><label>客户姓名：</label></td>
					<td colspan="3"><input type="text" id="dia-APPLY_USER" name="dia-APPLY_USER" datasource="APPLY_USER" style="width:87%" datatype="1,is_null,300" readonly="readonly"/></td>
				</tr>
				<tr>
				    <!-- <td><label>派工人员：</label></td>
				    <td> <input type="text" id="dia-REPAIR_USER" name="dia-REPAIR_USER" datasource="REPAIR_USER"  readonly="readonly"  hasBtn="true" callFunction="openFault()" datatype="0,is_null,30" /></td> -->
				    <td><label>派工人员：</label></td>
				    <td> <input type="text" id="dia-REPAIR_USER" name="dia-REPAIR_USER" datasource="REPAIR_USER"  readonly="readonly"   datatype="1,is_null,30" /></td>
				    <td><label>人员电话：</label></td>
				    <td><input type="text" id="dia-REP_USER_TEL" name="dia-REP_USER_TEL" datasource="REP_USER_TEL" readonly="readonly" datatype="1,is_null,30"/></td>
					<td><label>联系电话：</label></td>
					<td><input type="text" id="dia-APPLY_MOBIL" name="dia-APPLY_MOBIL" datasource="APPLY_MOBIL" datatype="1,is_null,30" readonly="readonly" /></td>
				</tr>
				<tr id="out1">
					<td><label>出发时间：</label></td>
					<td><input type="text"  id="dia-GO_DATE"  name="dia-GO_DATE"   dataSource="GO_DATE" width="30px"  kind="date" datatype="0,is_null,30" readonly="readonly"/></td>
					<td><label>到达时间：</label></td>
					<td><input type="text"  id="dia-ARRIVE_DATE"  name="dia-ARRIVE_DATE"   dataSource="ARRIVE_DATE"   kind="date" datatype="0,is_null,30" readonly="readonly"/></td>
				</tr>
				<tr>
					<td><label>维修时间：</label></td>
					<td><input type="text"  id="dia-REPAIR_DATE"  name="dia-REPAIR_DATE"   dataSource="REPAIR_DATE"  kind="date" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					<td><label>维修完成时间：</label></td>
					<td><input type="text"  id="dia-COMPLETE_DATE"  name="dia-COMPLETE_DATE"   dataSource="COMPLETE_DATE"  kind="date" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				</tr>
				<tr id="out2">
				    <td><label>出发经度：</label></td>
				    <td><input type="text" id="dia-START_LONGITUDE" name="dia-START_LONGITUDE" datasource="START_LONGITUDE" datatype="1,is_null,30" readonly="readonly"/></td>
					 <td><label>到达经度：</label></td>
				     <td><input type="text" id="dia-END_LONGITUDE" name="dia-END_LONGITUDE" datasource="END_LONGITUDE" datatype="1,is_null,30"  readonly="readonly"/></td>
				</tr>
				<tr id="out3">
				    <td><label>出发纬度：</label></td>
					<td><input type="text" id="dia-START_LATITUDE" name="dia-START_LATITUDE" datasource="START_LATITUDE" datatype="1,is_null,30"  readonly="readonly"/></td>
				    <td><label>到达纬度：</label></td>
				    <td><input type="text" id="dia-END_LATITUDE" name="dia-END_LATITUDE" datasource="END_LATITUDE" datatype="1,is_null,30"  readonly="readonly"/></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>		
		<div class="formBar">
			<ul>
			 	<!--      <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-ju" onclick="doDiaJuSave();">拒&nbsp;&nbsp;绝</button></div></div></li> -->
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-pg" onclick="doDiaPgSave();">维修完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchMngAction";
var diaAction = "<%=action%>";
$(function(){
	$("#dia-pg").bind("click", function(event){
	 	$("#dia-WORK_STATUS").val("302203"); 
	 	$("#dia-WORK_STATUS").attr("code","302203"); 
		var $f = $("#dia-fm-option");
		if (submitForm($f) == false) return false;
		
		var ifOut =$("#dia-ifout").val();
		if(ifOut==100101){
			if($("#dia-GO_DATE").val()==''){
				alertMsg.info("出发时间在手机端完成.");
				return false;
			}
			if($("#dia-ARRIVE_DATE").val()==''){
				alertMsg.info("到达时间在手机端完成.");
				return false;
			}
			var arriveDate=$("#dia-ARRIVE_DATE").val();//到达时间
			var completeDate= $("#dia-COMPLETE_DATE").val();//维修完成时间
			if(arriveDate > completeDate){
				alertMsg.info("完成时间不能小于到达时间.");
				return false;
			}
		}
		var sCondition = {};
		sCondition = $("#dia-fm-option").combined(1) || {};
		var updateUrl = diaSaveAction + "/updatesertWorkOrder.ajax?flag=1";
		doNormalSubmit($f,updateUrl,"dia-pg",sCondition,diaUpdateCallBack);
	});
   /* 	$("#dia-ju").bind("click", function(event){
	 	$("#dia-WORK_STATUS").val("302205"); 
	 	$("#dia-WORK_STATUS").attr("code","302205"); 
		var $f = $("#dia-fm-option");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#dia-fm-option").combined(1) || {};
		var updateUrl = diaSaveAction + "/updatesertWorkOrder.ajax";
		doNormalSubmit($f,updateUrl,"dia-ju",sCondition,diaUpdateCallBack);
	}); */
	if(diaAction != "1")
	{
	   	var selectedRows = $("#tab-list").getSelectedRows();	   
		setEditValue("dia-fm-option",selectedRows[0].attr("rowdata"));
		var ifout=selectedRows[0].attr("IF_OUT");
		$("#dia-ifout").val(ifout);
		if(ifout==100102){
			$("#out1").hide();
			$("#out2").hide();
			$("#out3").hide();
		}
	}
});
function openFault()
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/dms/dealer/service/claimmng/WorkDispatchUserSel.jsp";	
	$.pdialog.open(url, "WorkDispatchUserSel", "派工人员", options);				
}
function SelCallBack(obj)
{
		$("#dia-REPAIR_USER").val($(obj).attr("USER_NAME"));
		$("#dia-REP_USER_TEL").val($(obj).attr("MOBIL"));
}
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		$.pdialog.closeCurrent();
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
		var $row = $("#tab-list").getSelectedRows();
		if($row[0]){
			$("#tab-list").removeResult($row[0]);
			$.pdialog.closeCurrent();
			}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	
	//判断id中是否包含dia-D_ORGCODE的值
	if(id.indexOf("dia-VIN") == 0)
	{
		if($row.attr("VEHICLE_ID")){
		
		$("#dia-VEHICLE_ID").val($row.attr("VEHICLE_ID"));
		}
	   $("#dia-LICENSE_PLATE").val($("#"+id).val());
		return true;
	}
 
	return true;
}
</script>
</div>

