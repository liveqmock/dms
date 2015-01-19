<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="diaDetail">
	<div class="tabs" eventType="click" id="dia-tabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li><a href="javascript:void(0)"><span>审核信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page">
				<div class="pageContent" style="" >
					<form class="editForm">
					<div align="left">
				       <fieldset>
					<legend align="right"><a onclick="onTitleClick('diaDetailTable')">&nbsp;索赔单车辆信息&gt;&gt;</a></legend>
				    <div id="dia-shgj">
						<table width="100%" id="diaDetailTable" name="diaDetailTable" style="" class="editTable" >
				       		<tr>
				       			<td><label>渠道商代码：</label></td>
								<td><input type="text" id="diaOrgCode" name="diaOrgCode" datasource="ORG_CODE" value="" readonly="readonly"  /></td>
								<td><label>渠道商名称：</label></td>
								<td colspan="3"><input type="text" id="diaOrgName" name="diaOrgName" datasource="ORG_NAME" value="" readonly="readonly"  /></td>
				       		</tr>
				       		<tr>
								<td><label>索赔单号：</label></td>
								<td><a id="diaClaimNo" href="###" style="color: red" onclick="doDetail()"></a></td>
								<td><label>VIN：</label></td>
								<td><input type="text" id="diaVin" name="diaVin" datasource="VIN" value="" readonly="readonly"  /></td>
								<td><label>发动机号：</label></td>
								<td><input type="text" id="diaEngineno" name="diaEngineno" datasource="ENGINE_NO" value="" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>车辆型号：</label></td>
								<td><input type="text" id="diaModelsCode" name="diaModelsCode"  datasource="MODELS_CODE" value="" readonly="readonly" /></td>
								<td><label>发动机型号：</label></td>
								<td><input type="text" id="diaEngineType" name="diaEngineType"  datasource="ENGINE_TYPE" value="" readonly="readonly" /></td>
								<td><label>首保日期：</label></td>
								<td><input type="text" id="diaMaintenanceDate" name="diaMaintenanceDate"  datasource="MAINTENANCE_DATE" value=""  readonly="readonly"  /></td>
							</tr>
							<tr>
								<td><label>用户类型：</label></td>
								<td><input type="text" id="diaUserType" name="diaUserType"  datasource="USER_TYPE" value="" readonly="readonly"  /></td>
								<td><label>车辆用途：</label></td>
								<td><input type="text" id="diaVehicleUse" name="diaVehicleUse"  datasource="VEHICLE_USE" value="" readonly="readonly"  /></td>
								<td><label>驱动形式：</label></td>
								<td><input type="text" id="diaDriveForm" name="diaDriveForm"  datasource="DRIVE_FORM" value="" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>购车日期：</label></td>
								<td><input type="text" id="diaBuyDate" name="diaBuyDate"  datasource="BUY_DATE" value="" readonly="readonly" /></td>
								<td><label>行驶里程：</label></td>
								<td><input type="text" id="diaMileage" name="diaMileage"  datasource="MILEAGE" value=""  readonly="readonly" /></td>
								<td><label>保修卡号：</label></td>
								<td><input type="text" id="diaGuaranteeNo" name="diaGuaranteeNo"  datasource="GUARANTEE_NO" value="" readonly="readonly"  /> </td>
							</tr>
							<tr>
								<td><label>申请意见：</label></td>
								<td colspan="5"><textarea id="diaRepplyReason" style="width:450px;height:40px;" name="diaRepplyReason" datasource="REAPPLY_REASON"  datatype="1,is_null,1000"  readonly="readonly" ></textarea></td>
							</tr>
						</table>
					</div>
				 	</fieldset>
				 	</div>
				 	</form>
				       <form method="post" id="dia-in-DetailTableFm" class="editForm" style="width:100%;">
				       	<div align="left">
							<input type="hidden" id="dia_claimId" name="dia_claimId" datasource="CLAIM_ID"/>
							<table class="editTable" id="dia-in-DetailTable">
								<tr>
									<td><label>审核意见：</label></td>
									<td ><textarea id="diaCheckOpinion" style="width:450px;height:40px;" name="diaCheckOpinion" datasource="CHECK_OPINION"  datatype="1,is_null,1000"></textarea></td>
								</tr>
							</table>
				       	 </div>
					</form>		
				</div>
				<div class="formBar">
					<ul>
					    <li><div class="button"><div class="buttonContent"><button type="button" id="dia_viewAtt">查看附件</button></div></div></li>
					    <li><div class="button"><div class="buttonContent"><button type="button" id="dia_pass">审核通过</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia_reject" >审核驳回</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			<div class="page" >
				<div class="pageContent" >  
					<form method="post" id="dia_fm_his"></form>
					<div align="left">
	                <fieldset>
					<div id="dia-audi_his">
						<table width="100%" id="dia_hislb" name="dia_hislb" ref="dia-audi_his" style="display: none" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:none;"></th>
									<th fieldname="CHECK_USER" >审核人</th>
									<th fieldname="CHECK_DATE" >审核时间</th>
									<th fieldname="CHECK_RESULT" >审核结果</th>
									<th fieldname="CHECK_REMARKS" maxlength="40" >审核意见</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					</fieldset>
	            	</div> 
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimReApplyCheckAction";
var $true=true;
$(function(){
	var selectedRows = $("#claimCheckList").getSelectedRows();
	setEditValue("diaDetailTable",selectedRows[0].attr("rowdata"));
	$("#diaClaimNo").html(selectedRows[0].attr("CLAIM_NO"));
	$("#dia_claimId").val(selectedRows[0].attr("CLAIM_ID"));
	//查看附件
	$("#dia_viewAtt").bind("click",function(){
		var claimId=$("#dia_claimId").val();
		$.filestore.view(claimId);
	});
	
	//审核通过
	$("#dia_pass").bind("click",function(){
		var $f = $("#dia-in-DetailTableFm");
		var reason =$("#diaCheckOpinion").val();
		if(reason.length==0){
			alertMsg.warn("审核意见不能为空，请填写审核意见.");
			return false;
		}
		var sCondition = {};
		sCondition = $("#dia-in-DetailTableFm").combined(1) || {};
		var updateUrl = diaSaveAction + "/claimPassUpdate.ajax";
		doNormalSubmit($f,updateUrl,"dia_pass",sCondition,diaUpdateCallBack);
	});
	//审核驳回
	$("#dia_reject").bind("click",function(){
		var $f = $("#dia-in-DetailTableFm");
		var reason =$("#diaCheckOpinion").val();
		if(reason.length==0){
			alertMsg.warn("审核意见不能为空，请填写审核意见！");
			return false;
		}
		var sCondition = {};
		sCondition =$f.combined(1) || {};
		var updateUrl = diaSaveAction + "/claimRejectUpdate.ajax";
		doNormalSubmit($f,updateUrl,"dia_reject",sCondition,diaUpdateCallBack);
	});
	
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
	});
	//下一步
	$("button[name='btn-next']").bind("click", function(event){
		var $tabs = $("#dia-tabs");
		switch (parseInt($tabs.attr("currentIndex"))) 
		{
			case 0:
				//点击下一步第一次查询，再次点击不查询
				if($true){
					var $f = $("#dia_fm_his");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var fileSearchUrl1 =diaSaveAction+"/hisCheckSearch.ajax?claimId="+$("#dia_claimId").val(); 
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia_hislb");
				}
				$true=false;
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
				default:
					break;
			}
		})
		(parseInt($tabs.attr("currentIndex")));
	});
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
});
function diaUpdateCallBack(res){
	try
	{
		var $row = $("#claimCheckList").getSelectedRows();//选择行
		if($row[0]){
			$("#claimCheckList").removeResult($row[0]);//移除选择行
			$.pdialog.closeCurrent();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function doDetail(){
	var claimId=$("#dia_claimId").val();
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/claimDetail.jsp?claimId="+claimId, "claimDetail", "索赔单明细", options,true);
}
</script>

