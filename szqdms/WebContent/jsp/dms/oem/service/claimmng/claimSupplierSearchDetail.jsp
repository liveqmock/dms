<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔明细</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var $fileTrue=true;
var diaCheckAction = "<%=request.getContextPath()%>/service/claimmng/ClaimCheckAction";
var diaSaveAction= "<%=request.getContextPath()%>/service/claimmng/ClaimSupplierCheckAction";
$(function() 
{
	$("#dia-partInfo").height(document.documentElement.clientHeight-70);
	var selectedRows = parent.$("#claimList").getSelectedRows();
	$("#diaClaimId").val(selectedRows[0].attr("CLAIM_ID"));
	$("#diaWorkId").val(selectedRows[0].attr("WORK_ID"));
	
	//查询索赔单信息，回显
	var searchClaimUrl = diaCheckAction + "/searchClaimInfo.ajax?claimId="+$("#diaClaimId").val();
	sendPost(searchClaimUrl,"","",diaSearchClaimBack,"false");
	
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
			    	var fileSearchUrl1 =diaCheckAction+"/fileSearch.ajax?workId="+$("#diaWorkId").val(); 
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
				}
				$fileTrue=false;
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
	$("button.close").click(function() 
	{
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
//索赔单查询回显
function diaSearchClaimBack(res){
	try{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var claimType =getNodeText(rows[0].getElementsByTagName("CLAIM_TYPE").item(0));
			var activityCode =getNodeText(rows[0].getElementsByTagName("ACTIVITY_CODE").item(0));
			var authorNo =getNodeText(rows[0].getElementsByTagName("AUTHOR_NO").item(0));
			var dispatchNo =getNodeText(rows[0].getElementsByTagName("DISPATCH_NO").item(0));
			setEditValue("dia-fm-wxdawh",rows[0]);
			$("#dia-in-fwhdh").text(activityCode);//服务活动单号
			$("#dia-in-ysqh").text(authorNo);//预授权单号
			$("#dia-in-sbjjdd").text(dispatchNo);//三包急件
			setClaimType(claimType);
			searchClaimPattern();
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
	//首保时，系统屏蔽掉故障地点、故障时间、故障分析、检修时间、报修时间、报修地址
	if (value != "301402") 
	{
		$("#xzsbfy").hide();
		$("#diaJxsjTr").show();
		$("#diaBxdzL").show();
		$("#diaBxdzT").show();
	} else {
		$("#xzsbfy").show();
		$("#diaJxsjTr").hide();
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
		$("#diaGzsjL").hide();
		$("#diaGzsjT").hide();
	}else
	{
		$("#xzaqjc").hide();
		if(value != 301402){
			$("#diaGzsjL").show();
			$("#diaGzsjT").show();
		}
	}
	if (value!= "301408") //照顾性保修
	{
		if(value==301401){
			if($("#dia-in-ysqh").text() == ''){
				$("#xzysq").hide();
			}else{
				$("#xzysq").show();
			}
		}
	} else {
		$("#xzysq").show();
	}
}
//故障信息列表，查询
function searchClaimPattern()
{
	var $f = $("#dialog-fm-download");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var searchClaimPatternUrl=diaSaveAction+"/searchClaimPattern.ajax?claimId="+$("#diaClaimId").val();
	doFormSubmit($f,searchClaimPatternUrl,"",1,sCondition,"dia-tab-cliamPattrent");
}
//打开配件信息
function doPart(rowobj)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/service/claimmng/claimSupplierCheckPartInfo.jsp?claimDtlId="+$(rowobj).attr("CLAIM_DTL_ID"), "claimPart", "配件信息", options);
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
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
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
				    <li><a href="javascript:void(0)"><span>维修信息</span></a></li>
					<li><a href="javascript:void(0)"><span>附件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page" >
				<div class="pageContent" id="dia-partInfo" style="overflow-y:auto;overflow-x:hidden">
					<form method="post" id="dia-fm-partInfo" class="editForm" style="width: 99%;">
						<input type="hidden" id="diaClaimId" name="diaClaimId" datasource="CLAIM_ID"/>
						<input type="hidden" id="diaWorkId" name="diaWorkId" datasource="WORK_ID"/>
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
									<td><label>检修人：</label></td>
									<td><input type="text" id="dia-in-jxr" name="dia-in-jxr" value=""  datasource="REPAIR_USER" readonly="readonly"/></td>
									<td><label>检修地址：</label></td>
									<td colspan="3"><textarea id="dia-in-jxdz" style="width: 150px; height: 30px;" name="dia-in-jxdz"  datasource="REPAIR_ADDRESS" readonly="readonly"></textarea></td>
								</tr>
								<tr>
									<td><label>备注：</label></td>
									<td colspan="5"><textarea id="dia-in-bxbz" style="width: 450px; height: 50px;" datasource="REMARKS" name="dia-in-bxbz"  readonly="readonly"></textarea></td>
								</tr>
							</table>
							</fieldset>
						</div>
						<div align="left">
						<div id="dia-div-cliamPattrent" >
							<table style="width:100%;" id="dia-tab-cliamPattrent" name="tablist" ref="dia-div-cliamPattrent">
								<thead>
									<tr>
										<th type="single" name="XH" style="display:none"></th>
										<th fieldname="FAULT_CODE" colwidth="100">故障代码</th>
										<th fieldname="FAULT_NAME" colwidth="200">故障名称</th>
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
	             		</div>
             		</form>
		        </div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next" name="btn-next">下一步</button></div></div></li>
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
					<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files"  >
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre" name="btn-pre">上一步</button></div></div></li>
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