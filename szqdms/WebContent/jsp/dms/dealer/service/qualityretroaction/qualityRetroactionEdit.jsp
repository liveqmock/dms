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
<title>质量反馈新增</title>
<style type="text/css">  
nr {  
    border: 1px solid #B1CDE3; 
    padding:0;   
    margin:0 auto;  
    border-collapse: collapse;  
}  
td {  
    border: 1px solid #B1CDE3; 
    background: #fff;  
    font-size:12px;  
    padding: 3px 0px 3px 8px;  
    white-space:nowrap;
}
button 
{
	text-align:center;
	margin:0 auto;
	padding: 5px 5px 0 0;
	background - position: 100 % -250px;
}  
</style>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $pjtrue=true;
var authorStatus=null;
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction";
var deleteUrl = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction/dispatchPartDelete.ajax";
$(function() 
{
	$("#qualityPage").height(document.documentElement.clientHeight-70);
	if(action != "1"){
		var selectedRows = parent.$("#qualityRetroactionlist").getSelectedRows();
		setEditValue("dia-fm-qualityRetroactionReport",selectedRows[0].attr("rowdata"));
		$("#dia-vin").attr("readonly",true);
		$("#dia-engine_no").attr("readonly",true);
		$("#pjxxH").show();
		$("#dia-fm-qualityRetroactionReport").find("input[name='gcsl']").each(function(){
			var $this = $(this);
			var cusBuyCount = selectedRows[0].attr("CUS_BUY_COUNT");
			if($this.val()==cusBuyCount){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='jsyzt']").each(function(){
			var $this = $(this);
			var driStatus = selectedRows[0].attr("DRI_STATUS");
			if($this.val()==driStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='clyt']").each(function(){
			var $this = $(this);
			var vehicleUseType = selectedRows[0].attr("VEHICLE_USE_TYPE");
			if($this.val()==vehicleUseType){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='rzysj']").each(function(){
			var $this = $(this);
			var dailyWork = selectedRows[0].attr("DAILY_WORK");
			if($this.val()==dailyWork){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='gzdd']").each(function(){
			var $this = $(this);
			var faultAddress = selectedRows[0].attr("FAULT_ADDRESS");
			if($this.val()==faultAddress){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='rcdlzk']").each(function(){
			var $this = $(this);
			var dailyRoad = selectedRows[0].attr("DAILY_ROAD");
			if($this.val()==dailyRoad){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='byzk']").each(function(){
			var $this = $(this);
			var maintenanceStatus = selectedRows[0].attr("MAINTENANCE_STATUS");
			if($this.val()==maintenanceStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='clqk']").each(function(){
			var $this = $(this);
			var vehicleStatus = selectedRows[0].attr("VEHICLE_STATUS");
			if($this.val()==vehicleStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='clqk']").each(function(){
			var $this = $(this);
			var vehicleStatus = selectedRows[0].attr("VEHICLE_STATUS");
			if($this.val()==vehicleStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='fktj']").each(function(){
			var $this = $(this);
			var fbackApproace = selectedRows[0].attr("FBACK_APPROACE");
			if($this.val()==fbackApproace){
				$this.attr("checked",true);
			}
		});
	}else{
		$("#dia-report_li").hide();
		$("#pjxxH").hide();
		$("#dia-nextH1").hide();
	}
	$("#searchPart").bind("click",function(){
	 	var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchQualityParts.ajax?&fbackId="+$("#fbackId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
	});
	//新增配件页面
	$("#addPart").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/dealer/service/qualityretroaction/qualityRetroactionPartAdd.jsp", "qualityretroactionPart", "配件新增", options);
	});
	//保存
	$("#dia-save").bind("click",function(event){
		//获取需要提交的form对象
		var gcslval=$('input:radio[name="gcsl"]:checked').val();
		var jsyztval=$('input:radio[name="jsyzt"]:checked').val();
		var clytval=$('input:radio[name="clyt"]:checked').val();
		var rzysjval=$('input:radio[name="rzysj"]:checked').val();
		var gzddval=$('input:radio[name="gzdd"]:checked').val();
		var rcdlzkval=$('input:radio[name="rcdlzk"]:checked').val();
		var byzkval=$('input:radio[name="byzk"]:checked').val();
		var clqkval=$('input:radio[name="clqk"]:checked').val();
		var fktjval=$('input:radio[name="fktj"]:checked').val();
		$('#cusBuyCount').val(gcslval);
		$('#driStatus').val(jsyztval);
		$('#vehicleUseType').val(clytval);
		$('#dailyWork').val(rzysjval);
		$('#faultAddress').val(gzddval);
		$('#dailyRoad').val(rcdlzkval);
		$('#maintenanceStatus').val(byzkval);
		$('#vehicleStatus').val(clqkval);
		$('#fbackApproace').val(fktjval);
		var vinVal=$("#dia-vin").val();
		var engineVal=$("#dia-engine_no").val(); 
		var faultDate=$("#dia-faultDate").val();
		var mileage=$("#dia-mileage").val();
		if(vinVal==''){
			alertMsg.info("VIN不能为空！");
			return;
		}
		if(faultDate==''){
			alertMsg.info("故障日期不能为空！");
			return;
		}
		if(mileage==''){
			alertMsg.info("故障里程不能为空！");
			return;
		}
		if(engineVal==''){
			alertMsg.info("发动机号不能为空！");
			return;
		}
		var $f = $("#dia-fm-qualityRetroactionReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-qualityRetroactionReport").combined(1) || {};
		if(action == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/qualityRetroactionInsert.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/qualityRetroactionUpdate.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//提报
	$("#dia-report").bind("click",function(){
		var fbackId=$("#fbackId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-qualityRetroactionReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-qualityRetroactionReport").combined(1) || {};
		var reportUrl = diaSaveAction + "/qualityretroactionReport.ajax?fbackId="+fbackId+"&flag="+2;
		doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	});
	//验证
	$("#dia-checkvin").bind("click",function(){
		var vinVal=$("#dia-vin").val();
		var engineVal=$("#dia-engine_no").val(); 
		if(vinVal==''){
			alertMsg.info("VIN不能为空！");
			return;
		}
		if(engineVal==''){
			alertMsg.info("发动机号不能为空！");
			return;
		}
		var options = {max : true,mask : true,mixable : true,minable : true,resizable : true,drawable : true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/qualityretroaction/qualityRetroactionVinCheck.jsp?vinVal='"+vinVal+"'&engineVal='"+engineVal+"'","vinCheck", "质量反馈提报-校验车辆", options);
	});
	$("#deletePart").bind("click", function(event){
		var mxids=$("#val0").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择配件！");
	    	return false;
	    }else{
	    	var Url =diaSaveAction+"/deleteParts.ajax?mxids="+mxids;
			sendPost(Url,"deletePart","",deletePartCallBack,"true");
	    }
	});
	//重新填写
	$("#dia-recheckvin").bind("click",function(){
		alertMsg.confirm("清空车辆信息，确认重新填写?",{okCall:doConOk1,cancelCall:doConOk2});
	});
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("qualityRetroaction");
		return false;
	});
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex")) - 1);
	});
	//下一步
	$("button[name='btn-next']").bind("click", function(event){
		var $tabs = $("#tabs");
		switch (parseInt($tabs.attr("currentIndex"))) 
		{
			case 0:
				if($pjtrue){
					var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
			    	sCondition = $f.combined() || {};
			    	var searchServicePartUrl =diaSaveAction+"/searchQualityParts.ajax?&fbackId="+$("#fbackId").val();
					doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb");	
				}
				$pjtrue=false;
				break;
		};
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
});
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
//验证VIN回调
function checkVinCallBack(flag)
{
	if(flag==1){
		$("#dia-vin").attr("readOnly",true);
		$("#dia-vin").addClass("readonly");
		$("#dia-engine_no").attr("readOnly",true);
		$("#dia-engine_no").addClass("readonly");
		$("#vehicleId").val($("#dia-di-vehicleId").val());//车辆ID
		$("#modelsId").val($("#dia-di-modelsId").val());//车型ID
		$("#dia-models_code").val($("#dia-di-models_code").val());//车型代码
		$("#dia-engine_type").val($("#dia-di-engine_type").val());//发动机型号
		$("#dia-buy_date").val($("#dia-di-buy_date").val());//购车日期
		$("#dia-mileage").val($("#dia-di-mileage").val());//故障里程
	}
	if(flag==2){
		vehEmpty();
		alertMsg.info("VIN与发动机号不存在！");
	}
	return true;
}
//confirm返回true
function doConOk1(){
	$("#dia-vin").removeClass("readonly");
	$("#dia-engine_no").removeClass("readonly");
	$("#dia-vin").attr("readOnly",false);
	$("#dia-engine_no").attr("readOnly",false);
	$("#dia-vin").val("");
	$("#dia-engine_no").val("");
	vehEmpty();
}
//车辆信息设置为空
function vehEmpty(){
	$("#vehicleId").val("");//车辆ID
	$("#modelsId").val("");//车型ID
	$("#dia-models_code").val("");//车型代码
	$("#comName").val("");//单位名称
	$("#dia-engine_type").val("");//发动机型号
	$("#writeDate").val("");//填报日期
	$("#dia-name").val("");//姓名
	$("#dia-tel").val("");//电话
	$("#dia-fax").val("");//传真
	$("#dia-engineBookNo").val("");//发动机订货号
	$("#dia-buy_date").val("");//购车日期
	$("#dia-mileage").val("");//故障里程
	$("#dia-guarantee_no").val("");//保修卡
	$("#dia-faultDate").val("");//故障日期
	$("#dia-cusComName").val("");//公司名称
	$("#dia-cusLinkMan").val("");//联系人
	$("#dia-cusTel").val("");//联系电话
	$("#dia-cusFax").val("");//传真
	$("#dia-driName").val("");//司机姓名
	$("#dia-driTel").val("");//司机联系电话
	$("#dia-amount").val("");//数量（张）
	$("#dia-remarks").text("");//备注
}
// confirm返回true  
function doConOk2(){
	return false;
}
//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		var fbackId =getNodeText(rows[0].getElementsByTagName("FBACK_ID").item(0));
		$("#fbackId").val(fbackId);
		parent.$("#qualityRetroactionlist").insertResult(res,0);
		if(parent.$("#qualityRetroactionlist_content").size()>0){
			$("td input[type=radio]",parent.$("#qualityRetroactionlist_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#qualityRetroactionlist").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		$("#dia-report_li").show();
		$("#pjxxH").show();
		$("#dia-nextH1").show();
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
		var selectedIndex = parent.$("#qualityRetroactionlist").getSelectedIndex();
		parent.$("#qualityRetroactionlist").updateResult(res,selectedIndex); 
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
		if(result!='1'){
			var $row =  parent.$("#qualityRetroactionlist").getSelectedRows();
			if($row[0]){
				parent.$("#qualityRetroactionlist").removeResult($row[0]);
				parent.$.pdialog.close("qualityRetroaction");
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("sbjjpjlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#partDeleteVal"));
	}
	if($checkbox.attr("id").indexOf("tab-partList")==0){
		var $tr = $(checkbox).parent().parent().parent();
        var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
        var s = "";
        if($tr.find("td").eq(7).find("input:first").size()>0)
            s = $tr.find("td").eq(7).find("input:first").val();
        else
            s = $tr.find("td").eq(7).text();
        if (!s || !reg.test(s)){
            alertMsg.warn("请输入正确的数量!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push($tr.attr("SUPPLIER_ID"));
        arr.push($tr.attr("SUPPLIER_CODE"));
        arr.push($tr.attr("SUPPLIER_NAME"));
        arr.push(s);
        arr.push($tr.attr("SALE_PRICE"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$("#partVals"));
        //设置input框显示或文本只读
        if($checkbox.is(":checked"))
            $tr.find("td").eq(7).html("<div>"+s+"</div>");
        else
        {
            $tr.find("td").eq(7).html("<div><input type='text' name='QUANTITY' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
        }
	}
}
function  deletePartCallBack(res)
{
	try
	{
		var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchQualityParts.ajax?&fbackId="+$("#fbackId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
		$("#val0").val("");
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
<div id="layout" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>质量反馈基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page" >
			<div class="pageContent" id="qualityPage" style="overflow-x:auto;overflow-y:auto;" >
				<form method="post" id="dia-fm-qualityRetroactionReport" class="editForm" style="overflow:hidden;overflow-x:auto;">
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="fbackId" datasource="FBACK_ID"/>
					<input type="hidden" id="cusBuyCount" datasource="CUS_BUY_COUNT"/>
					<input type="hidden" id="driStatus" datasource="DRI_STATUS"/>
					<input type="hidden" id="vehicleUseType" datasource="VEHICLE_USE_TYPE"/>
					<input type="hidden" id="dailyWork" datasource="DAILY_WORK"/>
					<input type="hidden" id="faultAddress" datasource="FAULT_ADDRESS"/>
					<input type="hidden" id="dailyRoad" datasource="DAILY_ROAD"/>
					<input type="hidden" id="maintenanceStatus" datasource="MAINTENANCE_STATUS"/>
					<input type="hidden" id="vehicleStatus" datasource="VEHICLE_STATUS"/>
					<input type="hidden" id="fbackApproace" datasource="FBACK_APPROACE"/>
					<div align="left">
					<fieldset>
					<table  id="nr" style="width: 99%">
						<tr>
							<td rowspan="2" colspan="2">信息</td>
							<td><label>单位名称：</label></td>
							<td ><input type="text" id="comName" name="comName" datasource="COM_NAME" value="" datatype="1,is_null,30" /></td>
							<td><label>传真：</label></td>
							<td colspan="3"><input type="text" id="dia-fax" name="dia-fax" datasource="FAX" value=""  datatype="1,is_null,30" /></td>
						</tr>
						<tr>
							<td><label>姓名：</label></td>
							<td><input type="text" id="dia-name" name="dia-name" datasource="NAME" value=""  datatype="1,is_null,30"/></td>
							<td><label>电话：</label></td>
							<td colspan="3"><input type="text" id="dia-tel" name="tel" datasource="TEL" value=""   datatype="1,is_null,30"/></td>
						</tr>
						<tr>
							<td rowspan="3"  colspan="2">车辆信息</td>
							<td><label>VIN编码：</label></td>
							<td><input type="text" id="dia-vin" name="vin" datasource="VIN" value=""  datatype="1,is_vin,17"/>&nbsp;&nbsp;<font style="color: red">*</font></td>
							<td><label>发动机号：</label></td>
							<td colspan="3">
							<input type="text" id="dia-engine_no" name="engineNo" datasource="ENGINE_NO" value=""  datatype="1,is_null,30"/>&nbsp;&nbsp;<font style="color: red">*</font>&nbsp;&nbsp;&nbsp; <button type="button" id="dia-checkvin">验&nbsp;&nbsp;证</button>
								 &nbsp;&nbsp;
								<button type="button" id="dia-recheckvin" >重新填写</button>
							</td>
						</tr>
						<tr>
							<td ><label>车辆型号：</label></td>
							<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly"datatype="0,is_null,30"/></td>
							<td><label>发动机型号：</label></td>
							<td><input type="text" id="dia-engine_type" name="dia-engine_type" datasource="ENGINE_TYPE" readonly="readonly" value=""  /></td>
							<td><label>发动机订货号：</label></td>
							<td ><input type="text" id="dia-engineBookNo" name="dia-engineBookNo" datasource="ENGINE_BOOK_NO" datatype="1,is_null,30" value=""  /></td>
						</tr>
						<tr>
							<td ><label>购车日期：</label></td>
							<td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE"  readonly="readonly"/></td>
							<td><label>故障日期：</label></td>
							<td><input type="text" id="dia-faultDate" name="dia-faultDate" datasource="FAULT_DATE" onclick="WdatePicker()"datatype="1,is_date,30"/>&nbsp;&nbsp;<font style="color: red">*</font></td>
							<td><label>故障里程：</label></td>
							<td ><input type="text" id="dia-mileage" name="dia-mileage" datasource="FAULT_MIELES" datatype="1,is_digit,10" value=""  />&nbsp;&nbsp;<font style="color: red">*</font></td>
						</tr>
						<tr>
							<td rowspan="2"  colspan="2">客户信息</td>
							<td><label>公司名称：</label></td>
							<td><input type="text" id="dia-cusComName" name="dia-cusComName" datasource="CUS_COM_NAME" datatype="1,is_null,30" value=""  /></td>
							<td><label>购车数量：</label></td>
							<td colspan="3">
								<input type="radio" name="gcsl" value="<%=DicConstant.GCSL_01%>"/>50辆以上&nbsp;&nbsp;
								<input type="radio" name="gcsl" value="<%=DicConstant.GCSL_02%>"/>10-50辆间&nbsp;&nbsp;
								<input type="radio" name="gcsl" value="<%=DicConstant.GCSL_03%>"/>10辆以下&nbsp;&nbsp;
								<input type="radio" name="gcsl" value="<%=DicConstant.GCSL_04%>"/>1辆
							</td>
						</tr>
						<tr>
							<td><label>联系人：</label></td>
							<td><input type="text" id="dia-cusLinkMan" name="dia-cusLinkMan" datasource="CUS_LINK_MAN" datatype="1,is_null,30" value=""  /></td>
							<td><label>电话：</label></td>
							<td ><input type="text" id="dia-cusTel" name="dia-cusTel" datasource="CUS_TEL" datatype="1,is_null,30" value=""  /></td>
							<td><label>传真：</label></td>
							<td ><input type="text" id="dia-cusFax" name="dia-cusFax" datasource="CUS_FAX" datatype="1,is_null,30" value=""  /></td>
						</tr>
						<tr>
							<td  colspan="2">驾驶员信息</td>
							<td><label>姓名：</label></td>
							<td><input type="text" id="dia-driName" name="dia-driName" datasource="DRI_NAME" datatype="1,is_null,30" value=""  /></td>
							<td><label>电话：</label></td>
							<td ><input type="text" id="dia-driTel" name="dia-driTel" datasource="DRI_TEL" datatype="1,is_null,30" value=""  /></td>
							<td><label>状态：</label></td>
							<td >
								<input type="radio" name="jsyzt" value="<%=DicConstant.JSYZT_01%>"/>固定&nbsp;&nbsp;
								<input type="radio" name="jsyzt" value="<%=DicConstant.JSYZT_02%>"/>不固定
							</td>
						</tr>
						<tr>
							<td  colspan="2">车辆用途</td>
							<td colspan="2">
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_01%>"/>运输&nbsp;&nbsp;
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_02%>"/>工程&nbsp;&nbsp;
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_03%>"/>专用 &nbsp;&nbsp;
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_04%>"/>其他
							</td>
							<td ><label>日作业时间：</label></td>
							<td colspan="3" style="width:300px">
								 <input type="radio"  name="rzysj" value="<%=DicConstant.RZYSJ_01%>"/>8小时以上&nbsp;&nbsp;
							 	 <input type="radio"  name="rzysj" value="<%=DicConstant.RZYSJ_02%>"/>4-8小时间&nbsp;&nbsp;
							 	 <input type="radio"  name="rzysj" value="<%=DicConstant.RZYSJ_03%>"/>4小时一下
							</td>
						</tr>
						<tr>
							<td  colspan="2">车辆故障地点</td>
							<td colspan="2">
								<input type="radio" name="gzdd" value="<%=DicConstant.CLGZDD_01%>"/>公路&nbsp;&nbsp;
								<input type="radio" name="gzdd" value="<%=DicConstant.CLGZDD_02%>"/>非公路&nbsp;&nbsp;
								<input type="radio" name="gzdd" value="<%=DicConstant.CLGZDD_03%>"/>矿
							</td>
							<td ><label>日常道路状况：</label></td>
							<td colspan="3" style="width:300px">
								 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_01%>"/>高速&nbsp;&nbsp;
							 	 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_02%>"/>普通&nbsp;&nbsp;
							 	 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_03%>"/>非硬化路面&nbsp;&nbsp;
							 	 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_04%>"/>坡道
							</td>
						</tr>
						<tr>
							<td  colspan="2">保养状况</td>
							<td colspan="2">
								<input type="radio" name="byzk" value="<%=DicConstant.BYZK_01%>"/>良好&nbsp;&nbsp;
								<input type="radio" name="byzk" value="<%=DicConstant.BYZK_02%>"/>一般&nbsp;&nbsp;
								<input type="radio" name="byzk" value="<%=DicConstant.BYZK_03%>"/>差
							</td>
							<td ><label>车辆状况：</label></td>
							<td colspan="3" style="width:300px">
								 <input type="radio" name="clqk" value="<%=DicConstant.CLZK_01%>"/>标载&nbsp;&nbsp;
							 	 <input type="radio" name="clqk" value="<%=DicConstant.CLZK_02%>"/>超载&nbsp;&nbsp;
							 	 <input type="radio" name="clqk" value="<%=DicConstant.CLZK_03%>"/>严重超载
							</td>
						</tr>
						<tr>
							<td  colspan="2">途径</td>
							<td><label>反馈途径：</label></td>
							<td ><input type="radio" name="fktj" value="<%=DicConstant.FKTJ_01%>"/>已发邮箱
							</td>
							<td ><label>数量：</label></td>
							<td colspan="3" style="width:300px">
								 <font>共</font><input type="text" id="dia-amount" name="dia-amount" datasource="AMOUNT" datatype="1,is_digit,10" value=""  /><font>张</font>
							</td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
							<td colspan="7"><textarea rows="2" id="dia-remarks" style="width: 90%;" name="dia-remarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
						</tr>
					</table>
					<br/>
		
				</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="dia-report_li"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
					</ul>
			</div>
			</div>
			<!-- 配件TAB -->
			<div class="page">
			<div class="pageHeader">
			<form id="fm-sbjjpj" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fm-sbjjpjTable">
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="PJDM" name="PJDM" datasource="PT.PART_CODE"  datatype="1,is_null,30"  value=""  operation="like"/></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PJMC" name="PJMC" datasource="PT.PART_NAME"  datatype="1,is_null,30" value=""  operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchPart">查&nbsp;&nbsp;询</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="panelBar">
				<ul class="toolBar">
					<li class="line">line</li>
					<li><a class="add" href="javascript:void(0);" id="addPart" title=""><span>批量新增</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" href="javascript:void(0);" id="deletePart" title="确定要删除吗?"><span>批量删除</span></a></li>
				</ul>
			</div>
			<div class="pageContent">
				<div id=sbjjpjl>
					<table width="100%" id="sbjjpjlb" name="sbjjpjlb" multivals="partDeleteVal" ref="sbjjpjl"  style="display: none"  refQuery="fm-sbjjpjTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="SUPPLIER_NAME" >供应商名称</th>
								<th fieldname="COUNT" >数量</th>
								<th fieldname="AMOUNT" refer="amountFormat" align="right">总价(元)</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
				</table>
				</div>
				<div id="partDeleteVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
						</tr>
					</table>
					</div>
					</div>
				</div>
		</div>
	</div>
</div>
</body>
</html>