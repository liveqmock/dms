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
<title>预授权审核</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var $trueItem=true;
var $trueHis=true;
var $fileTrue=true;
var $partTrue=true;
var itemSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/itemSearch.ajax";
var checkPassUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthCheckAction/preAuthCheckPass.ajax";
var checkRejectUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthCheckAction/preAuthCheckReject.ajax";
var authorHisSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/authorHisSearch.ajax";
var partSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/authPartSearch.ajax";
var fileSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/fileSearch.ajax";

$(function() 
{
	var selectedRows = parent.$("#preAuthlist").getSelectedRows();
	setEditValue("dia-fm-preAuthBasic",selectedRows[0].attr("rowdata"));
	var authorType=selectedRows[0].attr("AUTHOR_TYPE");
	if(authorType=="300302"){
		$("#gzdzL").show();
		$("#gzdzT").show();
	}else if(authorType=="300303"){
		$("#gzdzL").show();
		$("#gzdzT").show();
		$("#out1").show();
		$("#out2").show();
	}
	$("#dia-pass").bind("click",function(){
		//获取需要提交的form对象
		var $f = $("#dia-fm-preAuthBasic");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-preAuthBasic").combined(1) || {};
		doNormalSubmit($f,checkPassUrl,"dia-pass",sCondition,checkPassCallBack);
	});
	$("#dia-reject").bind("click",function(){
		//获取需要提交的form对象
		var $f = $("#dia-fm-preAuthBasic");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-preAuthBasic").combined(1) || {};
		doNormalSubmit($f,checkRejectUrl,"dia-reject",sCondition,checkRejectCallBack);
	});
	//设置高度
	$("#preAuthBasic").height(document.documentElement.clientHeight-30);
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
				if($trueItem){
					var $f = $("#dia-fm-item");
					var sCondition = {};
					sCondition = $f.combined() || {};
					var itemSearchUrl1 =itemSearchUrl+"?authorId="+$("#authorId").val(); 
					doFormSubmit($f,itemSearchUrl1,"",1,sCondition,"dia-itemlb");
				}
				$trueItem=false;
				break;
			case 1:
				if($fileTrue){
					var $f = $("#dia_fm_atta");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var fileSearchUrl1 =fileSearchUrl+"?authorId="+$("#authorId").val(); 
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
				}
				$fileTrue=false;
				break;
				break;
			case 2:
				if($partTrue){
					var $f = $("#dia_fm_part");
					var sCondition = {};
					sCondition = $f.combined() || {};
					var partSearchUrl1 =partSearchUrl+"?authorId="+$("#authorId").val(); 
					doFormSubmit($f,partSearchUrl1,"",1,sCondition,"partAuthList");
				}
				$partTrue=false;
				break;
			case 3:
				if($trueHis){
					var $f = $("#dia_fm_his");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var authorHisSearchUrl1 =authorHisSearchUrl+"?authorId="+$("#authorId").val(); 
					doFormSubmit($f,authorHisSearchUrl1,"",1,sCondition,"dia_hislb");
				}
				$trueHis=false;
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
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
function checkPassCallBack(res){
	try
	{
		var $row = parent.$("#preAuthlist").getSelectedRows();//选择行
		if($row[0]){
			parent.$("#preAuthlist").removeResult($row[0]);//移除选择行
			parent.$.pdialog.closeCurrent();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function checkRejectCallBack(res){
	try
	{
		var $row = parent.$("#preAuthlist").getSelectedRows();//选择行
		if($row[0]){
			parent.$("#preAuthlist").removeResult($row[0]);//移除选择行
			parent.$.pdialog.closeCurrent();
			return false;
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//附件下载
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
//金额格式化
function amountFormat(obj){
  return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li><a href="javascript:void(0)"><span>工时信息</span></a></li>
					<li><a href="javascript:void(0)"><span>附件信息</span></a></li>
					<li><a href="javascript:void(0)"><span>配件信息</span></a></li>
					<li><a href="javascript:void(0)"><span>审核信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
		<div class="page" id="preAuthBasic" style="overflow:auto;">
			<div class="pageContent">
				<form method="post" id="dia-fm-preAuthBasic" class="editForm" style="width: 99%;">
					<input type="hidden" id="authorId" datasource="AUTHOR_ID"/>
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="modelsId" datasource="MODELS_ID"/>
					<div align="left">
						<fieldset>
						<legend align="right">
							<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;基本信息编辑&gt;&gt;</a>
						</legend>
						<table class="editTable" id="dia-tab-preAuthBasic">
							
							<tr>
						 		<td><label>预授权单号：</label></td>
						 		<td><input type="text" id="dia-preAuthNo" name="dia-preAuthNo" datasource="AUTHOR_NO" value="" readonly="readonly"/></td>
								<td><label>预授权类型：</label></td>
								<td colspan="3"><input type="text" id="dia-author_type" name="dia-author_type" datasource="AUTHOR_TYPE" value="" readonly="readonly"/></td>
							</tr>
							<tr>
								<td><label>VIN：</label></td>
								<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN"  value="" readonly="readonly"/></td>
								<td><label>发动机号：</label></td>
								<td colspan="3"><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO"  value="" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>车辆型号：</label></td>
								<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly" /></td>
								<!-- <td><label>合格证号：</label></td>
								<td><input type="text" id="dia-certificate" name="dia-certificate"  datasource="CERTIFICATE" value="" readonly="readonly" /></td> -->
								<td><label>发动机型号：</label></td>
								<td><input type="text" id="dia-di-engine_type" name="dia-di-engine_type" datasource="ENGINE_TYPE" value="" readonly="readonly" /></td>
								<td><label>首保日期：</label></td>
								<td><input type="text" id="dia-maintenance_date" name="dia-maintenance_date" datasource="MAINTENANCE_DATE" value=""  readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>用户类型：</label></td>
								<td><input type="text" id="dia-user_type" name="dia-user_type" datasource="USER_TYPE" value="" readonly="readonly" /></td>
								<td><label>车辆用途：</label></td>
								<td><input type="text" id="dia-vehicle_use" name="dia-vehicle_use"  datasource="VEHICLE_USE" value="" readonly="readonly" /></td>
								<td><label>驱动形式：</label></td>
								<td><input type="text" id="dia-drive_form" name="dia-drive_form" datasource="DRIVE_FORM" value="" readonly="readonly" /></td>
							</tr>
							<tr >
								<td><label>购车日期：</label></td>
								<td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
								<td><label>行驶里程：</label></td>
								<td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value=""  readonly="readonly" /></td>
								<td><label>保修卡号：</label></td>
								<td><input type="text" id="dia-guarantee_no" name="dia-guarantee_no" datasourc="GUARANTEE_NO" value=""  readonly="readonly" /> </td>
							</tr>
							<!-- <tr >
								<td><label>出厂日期：</label></td>
								<td><input type="text" id="dia-factory_date" name="dia-factory_date" datasource="FACTORY_DATE" value=""  readonly="readonly" /></td>
								
							</tr> -->
							<tr>
								<td><label>车牌号码：</label></td>
								<td><input type="text" id="dia-license_plate" name="dia-license_plate" datasource="LICENSE_PLATE" value=""  readonly="readonly"  /></td>
								<td><label>用户名称：</label></td>
								<td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value=""  readonly="readonly" /></td>
								<td><label>身份证号：</label></td>
								<td><input type="text" id="dia-user_no" name="dia-user_no"  datasource="USER_NO" value=""  readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>联系人：</label></td>
								<td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value=""  readonly="readonly"  /></td>
								<td><label>电话：</label></td>
								<td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value=""   readonly="readonly" /></td>
								<td><label>用户地址：</label></td>
								<td><textarea id="dia-user_address" style="width: 150px; height: 30px;" name="dia-user_address" datasource="USER_ADDRESS"  readonly="readonly" ></textarea></td>
							</tr>
							<tr id="jeTr">
								<td><label>金额：</label></td>
								<td><input type="text" id="dia_amount" name="dia_amount" value="" datasource="AMOUNT"  datatype="1,is_null,30" readonly="readonly" /></td>
							</tr>
							<tr>
								<td><label>固定电话</label></td>
								<td><input type="text" id="dia_telephone" name="dia_telephone" datasource="TELEPHONE" value="" datatype="1,is_null,30" readonly="readonly" /></td>
								<td id="gzdzL" style="display:none"><label>故障地址：</label></td>
								<td id="gzdzT" style="display:none" colspan="3"><textarea id="dia_faultLocation" style="width: 150px; height: 30px;" name="dia_faultLocation" datasource="FAULTLOCATION" datatype="1,is_null,300" readonly="readonly"></textarea></td>
							</tr>
							<tr id="out1" style="display:none">
								<td><label>外出时间：</label></td>
								<td><input type="text" id="dia-goDate" name="dia-goDate" datasource="GO_DATE"   kind="date" value="" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
								<td><label>到达时间：</label></td>
								<td><input type="text" id="dia-arriveDate" name="dia-arriveDate" datasource="ARRIVE_DATE"   kind="date" value="" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly"/></td>
								<td><label>外出人数：</label></td>
								<td><input type="text" id="dia-outUcount" name="dia-outUcount"  datasource="OUT_UCOUNT" value="" datatype="1,is_digit_0,10" readonly="readonly"/></td>
							</tr>
							<tr id="out2" style="display:none">
								<td><label>外出人员：</label></td>
								<td><input type="text" id="dia-outUser" name="dia-outUser" datasource="OUT_USER" value="" datatype="1,is_null,100" readonly="readonly"/></td>
								<td><label>拖车费金额：</label></td>
								<td><input type="text" id="dia-trailerCost" name="dia-trailerCost" datasource="TRAILER_COST" value="" datatype="1,is_money,300" readonly="readonly"/></td>
								<td><label>GPS车牌照：</label></td>
								<td><input type="text" id="dia-gpsLicensePlate" name="dia-gpsLicensePlate"  datasource="GPS_LICENSE_PLATE" value="" datatype="1,is_null,30" readonly="readonly"/></td>
							</tr>
							<tr>
								<td><label>故障分析及备注说明：</label></td>
								<td colspan="5"><textarea id="preAuthRamarks" style="width: 450px; height: 40px;" name="preAuthRamarks" datasource="PREAUTH_REMARKS" datatype="1,is_null,1000"></textarea></td>
							</tr>
							<tr>
								<td><label>审核意见：</label></td>
								<td colspan="5"><textarea id="dia-ramarks" style="width: 450px; height: 25px;" name="dia-ramarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
							</tr>
						</table>
						</fieldset>
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pass">审核通过</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-reject">审核驳回</button></div></div></li>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="page">
			<div class="pageContent">  
				<div align="left">
				<form method="post" id="dia-fm-item"></form>
                <fieldset>
				<div id="dia-item">
					<table style="display:none;width:100%;" id="dia-itemlb" name="dia-itemlb" ref="dia-item" >
						<thead>
							<tr>
								<th fieldname="ROWNUMS" style="display:" colwidth="20"></th>
								<th type="single" name="XH" style="display:none;"></th>
								<!-- <th fieldname="PROJECT_TYPE" >工时类型</th> -->
								<th fieldname="TIME_CODE"  >工时代码</th>
								<th fieldname="TIME_NAME" >工时名称</th>
								<th fieldname="AMOUNT_SET" >工时定额</th>
								<th fieldname="REMARKS" colwidth="100" >备注</th>
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
		<div class="page">
			<div class="pageContent">  
				<div align="left">
				<form method="post" id="dia_fm_atta" class="editForm" ></form>
					<div id="dia-files">
					<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="FJMC" >附件名称</th>
								<th fieldname="CJR" >上传人</th>
								<th fieldname="CJSJ">上传时间</th>
								<th colwidth="45" type="link" title="[下载]"  action="doDownloadAtta">操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					</div>
             	</div> 
			</div>
			<div class="formBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
					<li id="dia-nextH2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="page">
				<div class="pageHeader">
					<form id="dia_fm_part" method="post">
					</form>
				</div>
				<div class="pageContent">
					<div id="partAuth">
						<table  style="display: none" width="100%" id="partAuthList" name="partAuthList" multivals="partAuth" ref="partAuth"  refQuery="dia_partTable">
							<thead>
								<tr>
									<th fieldname="PART_CODE" >配件代码</th>
									<th fieldname="PART_NAME" >配件名称</th>
									<th fieldname="QUANTITY" >数量</th>
									<th fieldname="UNIT_PRICE" align="right" refer="amountFormat">单价</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
						<li id="dia-nextH3"><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一步</button></div></div></li>
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
								<th fieldname="ROWNUMS" style="display:" colwidth="20"></th>
								<th type="single" name="XH" style="display:none;"></th>
								<th fieldname="PERSON_NAME" >审核人</th>
								<th fieldname="CHECK_DATE" >审核时间</th>
								<th fieldname="CHECK_TYPE" >审核类型</th>
								<th fieldname="CHECK_RESULT" >审核结果</th>
								<th fieldname="REMAKS" >审核意见</th>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
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