<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单驳回</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var $fileTrue=true;
var $hisTrue=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimCheckAction";
var diaRejectAction = "<%=request.getContextPath()%>/service/claimmng/ClaimRejectAction";
var costUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimCheckAction/claimCostsSearch.ajax";
$(function() 
{
	//设置高度
	$("#dia-costInfo").height(document.documentElement.clientHeight-30);
	var selectedRows = parent.$("#claimRejectList").getSelectedRows();
	$("#diaClaimId").val(selectedRows[0].attr("CLAIM_ID"));
	$("#diaWorkId").val(selectedRows[0].attr("WORK_ID"));
	//初始化费用赋值 
	var url = costUrl +"?claimId="+$("#diaClaimId").val();
	sendPost(url,"","",searchCallBack,"false");
	
	//驳回
	$("#dia-reject").bind("click",function(){
		var reason=$("#diaCheckRemarks").val();
		var $f = $("#dia-fm-costInfo");
		if(reason.length==0){
			alertMsg.warn("审核意见不能为空，请填写审核意见.");
			return false;
		}
		var sCondition = {};
		sCondition =$f.combined(1) || {};
		var checkUrl = diaRejectAction + "/claimRejectUpdate.ajax";
		doNormalSubmit($f,checkUrl,"dia-reject",sCondition,diaCheckCallBack);
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
//审核回调
function diaCheckCallBack(res){
	try{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result='1'){
			var $row = parent.$("#claimRejectList").getSelectedRows();//选择行
			if($row[0]){
				parent.$("#claimRejectList").removeResult($row[0]);//移除选择行
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
				    <li><a href="javascript:void(0)"><span>费用信息</span></a></li>
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
					    </div>
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
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-reject">审核驳回</button></div></div></li>
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
								<th fieldname="CHECK_REMARKS" maxlength="20">审核意见</th>
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