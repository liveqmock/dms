<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<jsp:include page="/head.jsp" />
<title>索赔单领导审批</title>
<script type="text/javascript">
var $fileTrue=true;
var $hisTrue=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimCheckAction";
var costUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimCheckAction/claimCostsSearch.ajax";
var diaOperateAction = "<%=request.getContextPath()%>/service/common/ClaimDetailInfoAction";//查询故障配件的路径
var diaLeaderUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimLeaderCheckAction";
$(function() 
{
	//设置高度
	$("#dia-costInfo").height(document.documentElement.clientHeight-70);
	var selectedRows = parent.$("#claimCheckList").getSelectedRows();
	$("#diaClaimId").val(selectedRows[0].attr("CLAIM_ID"));
	$("#diaWorkId").val(selectedRows[0].attr("WORK_ID"));
	//初始化费用赋值 
	
	var url = costUrl +"?claimId="+$("#diaClaimId").val();
	sendPost(url,"","",searchCallBack,"false");
	
	//查询索赔单信息，回显
	var searchClaimUrl = diaSaveAction + "/searchClaimInfo.ajax?claimId="+$("#diaClaimId").val();
	sendPost(searchClaimUrl,"","",diaSearchClaimBack,"false");
	
	// 审核完成
	$("#dia-final").bind("click",function(){
		var reason=$("#diaCheckRemarks").val();
		var $f = $("#dia-fm-costInfo");
		if(reason.length==0){
			alertMsg.warn("审核意见不能为空，请填写审核意见.");
			return false;
		}
		var sCondition = {};
		sCondition =$f.combined(1) || {};
		var checkUrl = diaLeaderUrl + "/claimCheckUpdate.ajax";
		doNormalSubmit($f,checkUrl,"dia-final",sCondition,diaCheckCallBack);
	});
	//上一 步
	$("button[name='btn-pre']").bind("click",function(event) 
	{
		$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
	});
	//下一步
	$("button[name='btn-next']").bind("click", function(event) 
	{
		var $tabs = $("#dia-tabs");
		switch (parseInt($tabs.attr("currentIndex"))) 
		{
			case 0:
				if($fileTrue){
					var $f = $("#dia_fm_atta");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var fileSearchUrl1 =diaSaveAction+"/fileSearch.ajax?workId="+$("#diaWorkId").val(); 
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
				}
				$fileTrue=false;
				break;
			case 1:
				if($hisTrue){
					var $f = $("#dia_fm_his");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var fileSearchUrl1 =diaSaveAction+"/hisCheckSearch.ajax?claimId="+$("#diaClaimId").val(); 
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"hisList");
				}
				$hisTrue=false;
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
	$("button.close").click(function() 
	{
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
//费用回显
function searchCallBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var objxml = res.documentElement;
			setEditValue("dia-tab-costInfo",objxml);
		}
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
//索赔单查询回显
function diaSearchClaimBack(res){
	try{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var claimType =getNodeText(rows[0].getElementsByTagName("CLAIM_TYPE").item(0));
			var seType =getNodeText(rows[0].getElementsByTagName("SE_TYPE").item(0));
			var workId =getNodeText(rows[0].getElementsByTagName("WORK_ID").item(0));
			var activityCode =getNodeText(rows[0].getElementsByTagName("ACTIVITY_CODE").item(0));
			var authorNo =getNodeText(rows[0].getElementsByTagName("AUTHOR_NO").item(0));
			var dispatchNo =getNodeText(rows[0].getElementsByTagName("DISPATCH_NO").item(0));
			$("#dia-workId").val(workId);
			setEditValue("dia-fm-wxdawh",rows[0]);
			setClaimType(claimType);
			$("#dia-in-fwhdh").text(activityCode);//服务活动单号
			$("#dia-in-ysqh").text(authorNo);//预授权单号
			$("#dia-in-sbjjdd").text(dispatchNo);//三包急件
			if(seType==301102 || seType==301103){
				//查询外出信息，回显
				var searchClaimOutUrl = diaSaveAction + "/searchClaimOutInfo.ajax?claimId="+$("#diaClaimId").val();
				sendPost(searchClaimOutUrl,"","",diaSearchClaimOutBack,"false");
				$("#dia-div-wcxx").show();
				$("#vehicleL").show();
				$("#dia-vehicle").show();
			}
			searchClaimPattern();
		}
	}catch(e){
		alertMsg.error(e);
		return false;
	}
	return true;
}
//外出回显
function diaSearchClaimOutBack(res){
	try{
		$("#dia-div-wcxx").show();
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var outTime =getNodeText(rows[0].getElementsByTagName("OUT_TIMES").item(0));
			if(outTime==301202)
			{
				$("#ecwcfy").show();
			}
			setEditValue("dia-div-wcxx",rows[0]);
		}		
	}catch(e){
		alertMsg.error(e);
		return false;
	}
	return true;
}
//根据索赔单类型设置显示项目
function setClaimType(value)
{
	if (value != "301403") //服务活动
	{
		$("#xzfwhd").hide();
	} else {
		$("#xzfwhd").show();
		
	}
	//售前维修、售前培训检查、安全检查
	/* if (value == "301404" || value=="301406" || value == "301407") 
	{
		$("#gclibxk").hide();
	} else {
		$("#gclibxk").show();
	} */
	//首保时，系统屏蔽掉故障地点、故障时间、故障分析、检修时间、报修时间、报修地址
	if (value != "301402") 
	{
		$("#xzsbfy").hide();
		$("#diaJxsjTr").show();
		$("#gzxxTr").show();
		$("#diaBxdzL").show();
		$("#diaBxdzT").show();
	} else {
		$("#xzsbfy").show();
		$("#diaJxsjTr").hide();
		$("#gzxxTr").hide();
		$("#diaBxdzL").hide();
		$("#diaBxdzT").hide();
	}
	if (value == "301406" ) //售前培训检查
	{
		$("#xzsqpx").show();
	} else {
		$("#xzsqpx").hide();
	}
	if(value == "301407") //安全检查
	{
		$("#xzaqjc").show();
		//安全检查的时候，把故障分析、故障时间、故障地点、故障来源隐掉
		$("#gzxxTr").hide();
		$("#diaGzsjL").hide();
		$("#diaGzsjT").hide();
	}else
	{
		$("#xzaqjc").hide();
		if(value != 301402){
			$("#gzxxTr").show();
			$("#diaGzsjL").show();
			$("#diaGzsjT").show();
		}
	}
	/* if (value != "301408") //三包急件索赔
	{
		$("#xzsbjjdd").hide();
	} else {
		$("#xzsbjjdd").show();
	}*/
	if (value!= "301408") //照顾性保修
	{
		$("#xzysq").hide();
	} else {
		$("#xzysq").show();
	}
}
//故障信息列表，查询
function searchClaimPattern()
{
	var $f = $("#dia-fm-hidden");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var searchClaimPatternUrl=diaSaveAction+"/searchClaimPattern.ajax?claimId="+$("#diaClaimId").val();
	doFormSubmit($f,searchClaimPatternUrl,"",1,sCondition,"dia-tab-cliamPattrent");
}
//配件信息
function doPart(rowobj)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/common/claimPartInfo.jsp?claimDtlId="+$(rowobj).attr("CLAIM_DTL_ID"), "claimPart", "配件信息", options);
}
//审核回调
function diaCheckCallBack(res){
	try{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result='1'){
			var $row = parent.$("#claimCheckList").getSelectedRows();//选择行
			if($row[0]){
				parent.$("#claimCheckList").removeResult($row[0]);//移除选择行
				parent.$.pdialog.closeCurrent();
				return false;
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
				    <li><a href="javascript:void(0)"><span>索赔信息</span></a></li>
					<li><a href="javascript:void(0)"><span>附件信息</span></a></li>
					<li><a href="javascript:void(0)"><span>审核信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page" id="dia-costInfo" style="overflow:auto;">
				<div class="pageContent">
					<form method="post" id="dia-fm-costInfo" class="editForm" style="width: 99%;">
						<input type="hidden" id="diaClaimId" name="diaClaimId" datasource="CLAIM_ID"/>
						<input type="hidden" id="diaWorkId" name="diaWorkId" datasource="WORK_ID"/>
						<div align="left">
						<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-tab-costInfo')">&nbsp;索赔费用信息&gt;&gt;</a></legend>
						<table class="editTable" id="dia-tab-costInfo">
							<tr>
								<td><label>单子总费用：</label></td>
							    <td ><input type="text" id="diaClaimAmount" name="diaClaimAmount" datasource="CLAIM_AMOUNT" value="" readonly="readonly"/></td>
							    <td><label>材料费(汇总)：</label></td>
							    <td ><input type="text" id="diaMaterialCosts" name="diaMaterialCosts" datasource="MATERIAL_COSTS" value="" readonly="readonly"/></td>
								<td><label>外出总费：</label></td>
							    <td ><input type="text" id="diaOutAmount" name="diaOutAmount" datasource="OUT_AMOUNT" value="" readonly="readonly"/></td>
							</tr>	
							<tr>
							    <td><label>一次外出费：</label></td>
							    <td ><input type="text" id="diaOutCosts" name="diaOutCosts" datasource="OUT_COSTS" value="" readonly="readonly"/></td>
							    <td><label>二次外出费：</label></td>
							    <td ><input type="text" id="diaSecVehCosts" name="diaSecVehCosts" datasource="SEC_VEH_COSTS" value="" readonly="readonly"/></td>
								 <td><label>在途餐补：</label></td>
							    <td><input type="text" id="diaMealsCosts" name="diaMealsCosts" datasource="MEALS_COSTS"  value="" readonly="readonly"/></td>
							</tr>	
							<tr>
								<td><label>服务车费：</label></td>
							    <td><input type="text" id="diaSevehCosts" name="diaSevehCosts" datasource="SEVEH_COSTS" value="" readonly="readonly"/></td>
							    <td><label>差旅费：</label></td>
								<td><input type="text" id="diaTravelCosts" name="diaTravelCosts" datasource="TRAVEL_COSTS"  value="" readonly="readonly"/></td>
								<td ><label>其他费：</label></td>
								<td ><input type="text" id="diaOtherCosts" name="diaOtherCosts" datasource="OTHER_COSTS"   value="" readonly="readonly" /></td>
							</tr> 
							<tr>
							    <td><label>工时费（汇总）：</label></td>
								<td ><input type="text" id="diaWorkCosts" name="diaWorkCosts" datasource="WORK_COSTS" value="" readonly="readonly"/></td>
								<td><label>首保费：</label></td>
								<td ><input type="text" id="diaMaintenanceCosts" name="diaMaintenanceCosts" datasource="MAINTENANCE_COSTS"  value="" readonly="readonly"/></td>
								<td><label>服务活动费：</label></td>
								<td ><input type="text" id="diaServiceCosts" name="diaServiceCosts" datasource="SERVICE_COST"  value="" readonly="readonly"/></td>
							</tr> 
							<tr>
							    <td><label>安全检查费：</label></td>
								<td ><input type="text" id="diaSafeCosts" name="diaSafeCosts" datasource="SAFE_COSTS"  value="" readonly="readonly"/></td>
								<td><label>售前培训费：</label></td>
								<td colspan="3"><input type="text" id="diaTrainCosts" name="diaTrainCosts" datasource="TRAIN_COSTS"  value="" readonly="readonly"/></td>
							</tr>       							
						</table>
						</fieldset>
					    </div>
					    <div id="dia-div-wcxx" style="display: none">
					    	<fieldset>
							<legend align="right"><a onclick="onTitleClick('dia-tab-wcxx')">&nbsp;外出信息&gt;&gt;</a></legend>
							<table class="editTable" id="dia-tab-wcxx">
								<tr><td><label>是否多次外出：</label></td>
							    	<td>
							    		 <input type="text"  id="dia-in-sfdcwc"  name="dia-in-sfdcwc"  datasource="IS_OUT_TIMES" readonly="readonly"/>
								   	</td> 
								   	<td><label>外出总费用：</label></td>
								   	<td ><input type="text" id="dia-in-wczfy" value="0" readonly="readonly" datasource="COST_AMOUNT"/></td>
								   	<td><label>一次外出费用：</label></td>
								   	<td ><input type="text" id="dia-in-ycwczfy" value="0" readonly="readonly" datasource="OUT_COSTS"/>
								   	</td>     
								</tr>
								<tr>
								    <td><label>外出方式：</label></td>
								    <td> 
								        <input type="text" class="combox" id="dia-in-wcfs"  name="dia-in-wcfs" datasource="OUT_TYPE" value="" readonly="readonly"/>
									</td>
								    <td><label>外出人数：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"  value="" datasource="OUT_UCOUNT"  readonly="readonly"/>
								    </td>
								    <td><label>外出人员：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcry"  name="dia-in-wcry"   datasource="OUT_USER" value=""  readonly="readonly"/>
								    </td>
								</tr>
								<tr>
									<td><label>有效里程：</label></td>
								    <td>
									    <input type="text" id="dia-in-yxlc"  name="dia-in-yxlc"   value="" datasource="MILEAGE"  readonly="readonly"/>
								    </td>
								    <td><label>服务车牌号：</label></td>
								    <td>
									    <input type="text" id="dia-in-fwcph"  name="dia-in-fwcph"   value="" datasource="VEHICLE_NO" readonly="readonly" />
								    </td>
								    <td ><label>其他费：</label></td>
								    <td >
									     <input type="text" id="dia-in-qtf"  name="dia-in-qtf"   value="" datasource="OTHER_COSTS" readonly="readonly" />
								    </td>
								</tr>
								<tr>
									<td><label>在途补助天数：</label></td>
								    <td> 
								        <input type="text" id="dia-in-onWayDay"  name="dia-in-onWayDay" value="0" datasource="ON_WAY_DAYS"   value="" readonly="readonly"/>
									</td>
									<td><label>差旅天数：</label></td>
								    <td> 
								        <input type="text" id="dia-in-travelDay"  name="dia-in-travelDay" datasource="TRAVEL_DAYS"   value="" readonly="readonly"/>
									</td>
									<td><label>外出时间：</label></td>
								    <td> 
								        <input type="text" id="dia-in-wcsj"  name="dia-in-wcsj"  datasource="OUTDATE_TYPE"   value=""  readonly="readonly"/>
									</td>
								</tr>
								<tr>
								    <td><label>出发时间：</label></td>
								    <td> 
								        <input type="text" id="dia-in-cfsj"  name="dia-in-cfsj"   datasource="GO_DATE"  readonly="readonly" />
									</td>
								    <td><label>到达时间：</label></td>
								    <td > 
								        <input type="text" id="dia-in-ddsj"  name="dia-in-ddsj"  datasource="ARRIVE_DATE"  readonly="readonly"/>
									</td>
									<td><label>离开时间：</label></td>
								    <td > 
								        <input type="text" id="dia-in-lksj"  name="dia-in-lksj"   datasource="LEAVE_DATE" readonly="readonly"/>
									</td>
								</tr>
								<tr>
								    <td><label>在途补助：</label></td>
								    <td>
									    <input type="text" id="dia-in-ztcb"  name="dia-in-ztcb"  value="" datasource="MEALS_COSTS"  readonly="readonly"/>
								    </td>
									<td><label>服务车费：</label></td>
								    <td> 
								         <input type="text" id="dia-in-fwcf"  name="dia-in-fwcf"   datasource="SEVEH_COSTS" value="" readonly="readonly"/>
									</td>
									<td><label>差旅费：</label></td>
								    <td>
									     <input type="text" id="dia-in-clf"  name="dia-in-clf"  datasource="TRAVEL_COSTS" value="" readonly="readonly"/>
								    </td>
								</tr>
								<tr>
									<td><label style="color: red" >GPS有效里程：</label></td>
								    <td colspan="5"> 
								         <input type="text" id="dia-in-gpsMileage"  name="dia-in-gpsMileage" style="color: red"  datasource="GPS_MILEAGE" value="" readonly="readonly"/>
									</td>
								</tr>
								<tr id="ecwcfy" style="display: none">
								    <td ><label>服务车费（二次）：</label></td>
								    <td colspan="5"> 
								         <input type="text" id="dia-in-fwcfec"  name="dia-in-fwcfec"  value="" datasource="SEC_VEH_COSTS" readonly="readonly"/>
									</td>
								</tr>
								<tr>
								    <td><label>其他说明：</label></td>
								    <td colspan="5">
									    <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-bz" datasource="REMARKS"  readonly="readonly"></textarea>
								    </td>
								</tr>
							</table>
							</fieldset>
						</div>
						<div id="dia-fm-wxdawh">
							<fieldset>
							<legend align="right"><a onclick="onTitleClick('dia-tab-claiminfo')">&nbsp;维修信息&gt;&gt;</a></legend>
							<table class="editTable" id="dia-tab-claiminfo">
								<tr>
									<td><label>索赔单号：</label></td>
								    <td><input type="text" id="dia-claimNo" value="" datasource="CLAIM_NO" readonly="readonly"/></td>
									<td><label>索赔单类型：</label></td>
									<td>
										<input type="text" id="dia-in-splx"  name="dia-in-splx"  datasource="CLAIM_TYPE"  readonly="readonly" value=""/>	
									</td>
									<td><label>服务类型：</label></td>
								    <td>
									    <input type="text" id="dia-in-fwlx" name="dia-in-fwlx" datasource="SE_TYPE"   readonly="readonly" value=""/>
								    </td>
								</tr>
								<tr>
									<td><label>VIN：</label></td>
									<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN" readonly="readonly" /></td>
									<td><label>发动机号：</label></td>
									<td colspan="3"><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO" readonly="readonly"/></td>
								</tr>
								<tr id="xzysq" style="display: none">
									<td><label>预授权单号：</label></td>
									<td colspan="4"><a  id="dia-in-ysqh"  href="###" onclick="preAuthDetail()" style="color: red" class="op" /></td>
									<td>
									 <input type="hidden" id="dia-id-authorId" name="dia-id-authorId" datasource="PRE_AUTHOR_ID"/>
									</td>									
								</tr>
								<tr id="xzfwhd" style="display:none">
									<td><label>服务活动单号：</label></td>
									<td colspan="4"><a  id="dia-in-fwhdh"  href="###" onclick="activityDetail()" style="color: red" class="op"/>
									</td>
									<td>
									<input type="hidden" id="dia-id-activityId" datasource="ACTIVITY_ID"/>
									</td>
								</tr>
								<tr id="xzsbfy" style="display: none">
									<td><label>首保费用：</label></td>
									<td><input type="text" id="dia-in-sbfy" name="dia-in-sbfy" value=""  datasource="MAINTENANCE_COSTS" readonly="readonly"/></td>
									<td><label>首保日期：</label></td>
									<td colspan="3"><input type="text" id="dia-maintenance_date" name="dia-maintenance_date" datasource="MAINTENANCE_DATE" value="" readonly="readonly"  /></td>
								</tr>
								<tr id="xzaqjc" style="display: none">
									<td><label>安全检查费用：</label></td>
									<td colspan="5"><input type="text" id="dia-in-aqjc" name="dia-in-aqjc"  value=""  datasource="SAFE_COSTS" readonly="readonly" /></td>
								</tr>
								<tr id="xzsqpx" style="display: none">
									<td><label>售前培训费用：</label></td>
									<td colspan="5"><input type="text" id="dia-in-sqpx" name="dia-in-sqpx"  value=""  datasource="TRAIN_COSTS" readonly="readonly" /></td>
								</tr>
								<tr id="xzsbjjdd" style="display: none">
									<td><label>三包急件订单：</label></td>
									<td colspan="4"><a  id="dia-in-sbjjdd"  href="###" onclick="dispatchDetail()" style="color: red"/>
									</td>
									<td>
									<input type="hidden" id="dia-id-dispatchId" datasource="DISPATCH_ID"/>
									</td>
								</tr>
								<tr>
									<td><label>车辆型号：</label></td>
									<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODEL_CODE" value="" readonly="readonly" /></td>
									<td><label>发动机型号：</label></td>
									<td><input type="text" id="dia-di-engine_type" name="dia-di-engine_type" value="" datasource="ENGINE_TYPE" readonly="readonly" /></td>
									<td><label>用户类型：</label></td>
									<td><input type="text" id="dia-user_type" name="dia-user_type"  value=""  datasource="USER_TYPE_NAME" readonly="readonly" /></td>
								</tr>
								<tr>
									<td><label>车辆用途：</label></td>
									<td><input type="text" id="dia-vehicle_use" name="dia-vehicle_use"   value="" datasource="VEHICLE_NAME" readonly="readonly" />
									</td>
									<td><label>驱动形式：</label></td>
									<td><input type="text" id="dia-drive_form" name="dia-drive_form"  datasource="DRIVE_FORM" value="" readonly="readonly" /></td>
								</tr>
								<tr id="gclibxk" style="display: ">
									<td><label>购车日期：</label></td>
									<td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
									<td><label>行驶里程：</label></td>
									<td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value="" readonly="readonly" /></td>
									<td><label>保修卡号：</label></td>
									<td><input type="text" id="dia-guarantee_no" name="dia-guarantee_no" datasourc="GUARANTEE_NO" value=""  readonly="readonly" /> </td>
								</tr>
								<tr>
									<td><label>车牌号码：</label></td>
									<td><input type="text" id="dia-license_plate" name="dia-license_plate" datasource="LICENSE_PLATE" readonly="readonly" value="" /></td>
									<td><label>用户名称：</label></td>
									<td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value="" readonly="readonly"  /></td>
									<td><label>身份证号：</label></td>
									<td><input type="text" id="dia-user_no" name="dia-user_no"  datasource="USER_NO" value="" readonly="readonly"  /></td>
								</tr>
								<tr>
									<td><label>省：</label></td>
									<td><input type="text" id="dia-province_Code" name="dia-province_Code"  datasource="PROVINCE_CODE" value=""  readonly="readonly"/></td>
									<td><label>市：</label></td>
									<td><input type="text" id="dia-city_Code" name="dia-city_Code"  datasource="CITY_CODE" value=""  readonly="readonly"/></td>
									<td><label>区县：</label></td>
									<td><input type="text" id="dia-county_Code" name="dia-county_Code"   datasource="COUNTY_CODE" value=""  readonly="readonly"/></td>
								</tr>
								<tr>
								<td>
									<label>用户地址：</label></td>
									<td><textarea id="dia-user_address" style="width: 150px; height: 30px;" name="dia-user_address" datasource="USER_ADDRESS" readonly="readonly"></textarea></td>
									<td><label>用户姓名：</label></td>
									<td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value=""  readonly="readonly"/></td>
									<td><label>用户电话：</label></td>
									<td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value=""  readonly="readonly"/></td>
								</tr>
								<tr id="gzxxTr">
									<td><label>故障来源：</label></td>
									<td><input type="text" id="dia-in-gzxxly" name="dia-in-gzxxly" value="" readonly="readonly" datasource="FAULT_FROM"/></td>
									<td><label>故障地点：</label></td>
									<td colspan="3"><input type="text" id="dia-in-gzdd" name="dia-in-gzdd" value="" datasource="FAULT_ADDRESS" readonly="readonly"/></td>
									<!-- <td><label>故障分析：</label></td>
									<td >
									 <input type="text" id="dia-in-hsj0" name="dia-in-gzfx0"  value="" readonly="readonly" datasource="FAULT_ANLYSIE"/>
									</td> -->
								</tr>
								<tr>
									<td><label>报修人：</label></td>
									<td><input type="text" id="dia-in-bxr" name="dia-in-bxr"  value="" readonly="readonly" datasource="APPLY_USER"/></td>
									<td><label>报修人类型：</label></td>
									<td><input type="text" id="dia-in-bxrlx" name="dia-in-bxrlx" readonly="readonly"  value="" datasource="APPLY_USER_TYPE"/>
									</td>
									<td id="diaBxdzL"><label>报修地址：</label></td>
									<td id="diaBxdzT"><textarea id="dia-in-bxdz" style="width: 150px; height: 30px;" name="dia-in-bxdz" datasource="APPLY_ADDRES" readonly="readonly"></textarea></td>
								</tr>
								
								<tr id="diaJxsjTr">
									<td id="diaGzsjL"><label>故障时间：</label></td>
									<td id="diaGzsjT"><input type="text" id="dia-in-gzsj" name="dia-in-gzsj" value=""  readonly="readonly"  datasource="FAULT_DATE"/></td>
									<td><label>报修时间：</label></td>
									<td><input type="text" id="dia-in-bxrq" name="dia-in-bxrq" value="" readonly="readonly" datasource="APPLY_REPAIR_DATE"/></td>
									<td><label>检修时间：</label></td>
									<td><input type="text" id="dia-in-jxrq" name="dia-in-jxrq" value="" readonly="readonly" datasource="REPAIR_DATE"/></td>
								</tr>
								<tr>
									<td><label>报修人电话：</label></td>
									<td><input type="text" id="dia-in-bxrdh" name="dia-in-bxrdh" value=""  datasource="APPLY_MOBIL" readonly="readonly"/></td>
									<td><label>检修人：</label></td>
									<td><input type="text" id="dia-in-jxr" name="dia-in-jxr" value=""  datasource="REPAIR_USER" readonly="readonly"/></td>
									<td><label>检修地址：</label></td>
									<td><textarea id="dia-in-jxdz" style="width: 150px; height: 30px;" name="dia-in-jxdz"  datasource="REPAIR_ADDRESS" readonly="readonly"></textarea></td>
								</tr>
								<tr>
									<td><label>备注：</label></td>
									<td colspan="5"><textarea id="dia-in-bxbz" style="width: 450px; height: 50px;" datasource="REMARKS" name="dia-in-bxbz"  readonly="readonly"></textarea></td>
								</tr>
							</table>
							</fieldset>
						</div>
						<div id="dia-div-cliamPattrent" style="">
							<table style="width:100%;" id="dia-tab-cliamPattrent" name="tablist" ref="dia-div-cliamPattrent">
								<thead>
									<tr>
										<th type="single" name="XH" style="display:none"></th>
										<th fieldname="FAULT_CODE" colwidth="100">故障代码</th>
										<th fieldname="FAULT_NAME" colwidth="200">故障名称</th>
										<th fieldname="SEVERITY" >严重程度</th>
										<th fieldname="WORK_TIME" colwidth="50" >维修工时</th>
										<th fieldname="WORK_TIME_UPRICE" colwidth="50">工时单价</th>
										<th fieldname="STAR_LEVEL_UPRICE" colwidth="50">星级单价</th>
										<th fieldname="ENCOURAGE_UPRICE" colwidth="50">激励单价</th>
										<th fieldname="WORK_MULTIPLE" colwidth="50">工时倍数</th>
										<th fieldname="WORK_COSTS" colwidth="50">工时费</th>
										<th colwidth="80" type="link" title="[配件信息]"  action="doPart">操作</th>
									</tr>
								</thead>
							</table>
						</div>
						</br>
					 	<div align="left">
			            	<fieldset>
							<table class="editTable" id="dia-tab-checkOpinion">
								<tr>
								    <td><label>审核意见：</label></td>
								    <td >
									  <textarea id="diaCheckRemarks" style="width:450px;height:40px;" name="diaCheckRemarks" datasource="CHECK_REMARKS"  datatype="0,is_null,1000"></textarea>
								    </td>
								</tr>
							</table>
			       			</fieldset>
						</div>
					</form>
		        </div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-final">审核完成</button></div></div></li>
						<li id="vehicleL" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="dia-vehicle" style="display:none">服务车轨迹</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			<div class="page">
			<div class="pageContent">  
				<form method="post" id="dia_fm_atta" class="editForm" >
				</form>
				<div align="left">
              	<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-files')">&nbsp;已传附件列表&gt;&gt;</a></legend>
					<div id="dia-files">
					<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="FJMC" >附件名称</th>
								<th fieldname="CJR" >上传人</th>
								<th fieldname="CJSJ">上传时间</th>
								<th colwidth="85" type="link" title="[下载]"  action="doDownloadAtta">操作</th>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="page" >
			<div class="pageContent" >  
				<form method="post" id="dia_fm_his"></form>
				<div align="left">
                <fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-audi_his')">&nbsp;历史审核轨迹&gt;&gt;</a></legend>
				    <div id="dia-audi_his">
					<table width="100%" id="hisList" name="hisList" ref="dia-audi_his" style="display: none" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none;"></th>
								<th fieldname="CHECK_USER" >审核人</th>
								<th fieldname="CHECK_DATE" >审核时间</th>
								<th fieldname="CHECK_RESULT" >审核结果</th>
								<th fieldname="CHECK_REMARKS" maxlength="30" >审核意见</th>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
        </div>	
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
	<form id="dialog-fm-download"style="display:none">
	</form>
</div>
</body>
</html>