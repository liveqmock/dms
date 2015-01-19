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
<title>三包急件新增</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/guaranteesHurryPart/GuaranteesHurryPartAction";
var deleteUrl = "<%=request.getContextPath()%>/service/guaranteesHurryPart/GuaranteesHurryPartAction/dispatchPartDelete.ajax";
$(function() 
{
	if(action != "1"){
		var selectedRows = parent.$("#dispatchPartlist").getSelectedRows();
		setEditValue("dia-fm-hurryPartReport",selectedRows[0].attr("rowdata"));
		var faultAnalyseCode =selectedRows[0].attr("FAULT_ANALYSE_CODE");
		$("#dia-in-hsj0").attr("code",faultAnalyseCode);
		$("#dia-vin").attr("readonly",true);
		$("#dia-engine_no").attr("readonly",true);
		$("#pjxxH").show();
		
	}else{
		$("#dia-report_li").hide();
		$("#pjxxH").hide();
		$("#dia-nextH1").hide();
	}
	$("#searchPart").bind("click",function(){
	 	var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchHurryParts.ajax?&dispatchId="+$("#dispatchId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
	});
	//新增配件页面
	$("#addPart").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/dealer/service/guaranteesHurryPart/guaranteesHurryPartAdd.jsp", "servicePart", "三包急件配件新增", options);
	});
	//保存
	$("#dia-save").bind("click",function(event){
		//获取需要提交的form对象
		var $f = $("#dia-fm-hurryPartReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-hurryPartReport").combined(1) || {};
		if(action == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/hurryPartInsert.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/hurryPartUpdate.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//提报
	$("#dia-report").bind("click",function(){
		var dispatchId=$("#dispatchId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-hurryPartReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-hurryPartReport").combined(1) || {};
		var reportUrl = diaSaveAction + "/hurryPartReport.ajax?dispatchId="+dispatchId+"&flag="+2;
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
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/guaranteesHurryPart/guaranteesHurryVinCheck.jsp?vinVal='"+vinVal+"'&engineVal='"+engineVal+"'","vinCheck", "定保单信息维护-校验车辆", options);
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
		parent.$.pdialog.close("guaranteesHurryPart");
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
				if($true==true){
					var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
			    	sCondition = $f.combined() || {};
			    	var searchServicePartUrl =diaSaveAction+"/searchHurryParts.ajax?&dispatchId="+$("#dispatchId").val();
					doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
				}
				$true=false;
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
		$("#dia-certificate").val($("#dia-di-certificate").val());//合格证
		$("#dia-engine_type").val($("#dia-di-engine_type").val());//发动机型号
		$("#dia-buy_date").val($("#dia-di-buy_date").val());//购车日期
		$("#dia-mileage").val($("#dia-di-mileage").val());//行驶里程
		$("#dia-user_name").val($("#dia-di-user_name").val());//用户名称
		$("#dia-user_no").val($("#dia-di-user_no").val());//身份证
		$("#dia-link_man").val($("#dia-di-link_man").val());//联系人
		$("#dia-phone").val($("#dia-di-phone").val());//电话
		$("#dia-user_address").text($("#dia-di-user_address").val());//地址
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
	$("#dia-certificate").val("");//合格证
	$("#dia-engine_type").val("");//发动机型号
	$("#dia-user_type").val("");//用户类型
	$("#dia-user_type_code").val("");//用户类型
	$("#dia-vehicle_use").val("");//车辆用途
	$("#dia-vehicle_use_code").val("");//车辆用途
	$("#dia-drive_form").val("");//驱动形式
	$("#dia-buy_date").val("");//购车日期
	$("#dia-mileage").val("");//行驶里程
	$("#dia-guarantee_no").val("");//保修卡
	$("#dia-factory_date").val("");//出厂日期
	$("#dia-maintenance_date").val("");//首保日期
	$("#dia-license_plate").val("");//车牌号
	$("#dia-user_name").val("");//用户名称
	$("#dia-user_no").val("");//身份证
	$("#dia-link_man").val("");//联系人
	$("#dia-phone").val("");//电话
	$("#dia-user_address").text("");//地址
	$("#dia-vin").val($("#dia-di-vin").val());
	$("#dia-engine_no").val($("#dia-di-engine_no").val());
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
		var dispatchId =getNodeText(rows[0].getElementsByTagName("DISPATCH_ID").item(0));
		var dispatchNo =getNodeText(rows[0].getElementsByTagName("DISPATCH_NO").item(0));
		//因为故障分析保存之后，没有走查询的话无法回显，在次点击编辑页面进行保存数据就会有误，暂时修改为新增与修改后进行父页面查询操作。
		//parent.$("#dispatchPartlist").insertResult(res,0);
		//if(parent.$("#dispatchPartlist_content").size()>0){
		//	$("td input[type=radio]",parent.$("#dispatchPartlist_content").find("tr").eq(0)).attr("checked",true);			
		//}else{
		//	$("td input[type=radio]",parent.$("#dispatchPartlist").find("tr").eq(0)).attr("checked",true);
		//}
		parent.$("#search").trigger("click");
		action=2;
		$("#dispatchId").val(dispatchId);
		$("#dispatchNo").val(dispatchNo);
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
		//var selectedIndex = parent.$("#dispatchPartlist").getSelectedIndex();
		//parent.$("#dispatchPartlist").updateResult(res,selectedIndex); 
		//因为故障分析保存之后，没有走查询的话无法回显，在次点击编辑页面进行保存数据就会有误，暂时修改为新增与修改后进行父页面查询操作。
		parent.$("#search").trigger("click");
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
			var $row =  parent.$("#dispatchPartlist").getSelectedRows();
			if($row[0]){
				parent.$("#dispatchPartlist").removeResult($row[0]);
				parent.$.pdialog.close("guaranteesHurryPart");
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
		arr.push($tr.attr("DTL_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#partDeleteVal"));
	}
	if($checkbox.attr("id").indexOf("tab-partList")==0){
		var $tr = $(checkbox).parent().parent().parent();
	    var $inputSl = $tr.find("td").eq(5).find("input:first");
	    var $inputBz = $tr.find("td").eq(6).find("input:first");
	    var sl = "";
	    var bz = "";
	    if($inputSl && $inputSl.size()>0 && $inputSl.get(0).tagName=="INPUT")
	        sl = $inputSl.val();
	    else
	    {
	        sl = $tr.find("td").eq(5).text();
	    }
	  	//判断入库数量是否有值，如没有值，提示输入
		if(!sl)
		{
			alertMsg.warn("请输入配件数量！");
			$(checkbox).attr("checked",false);
			return false;
		}
		bz = $inputBz.val();
	  	//判断入库数量是否有值，如没有值，提示输入
	    var arr = [];
	    arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
	    arr.push(sl);
	    if(bz.length==0){
	    	arr.push("anull");
	    }else{
	    	arr.push(bz);
	    }
	    arr.push($tr.attr("SALE_PRICE"));
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    multiSelected($checkbox, arr,$("#partVals"));
	    //设置input框显示或文本只读
	    if($checkbox.is(":checked")){
	    	$tr.find("td").eq(5).html("<div>"+sl+"</div>");
	    }else
	    {
	    	$tr.find("td").eq(5).html("<div><input type='text' name='QUANTITY' onblur='doMyInputBlur(this);' maxlength='6' value='"+sl+"'/></div>");
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
    	var searchServicePartUrl =diaSaveAction+"/searchHurryParts.ajax?&dispatchId="+$("#dispatchId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="dia-fm-hurryPartReport" class="editForm" style="width: 99%;">
					<input type="hidden" id="dispatchId" datasource="DISPATCH_ID"/>
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="modelsId" datasource="MODELS_ID"/>
					<div align="left">
						<fieldset>
						<legend align="right">
							<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;三包急件基本信息编辑&gt;&gt;</a>
						</legend>
						<table class="editTable" id="sbjjxx">
						<tr>
							<td><label>急件编号：</label></td>
							<td><input type="text" id="dispatchNo" name="dispatchNo" datasource="DISPATCH_NO" value="系统自动生成" readonly="readonly" /></td>
						 </tr>
					     <tr>
							 <td><label>VIN：</label></td>
							 <td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN" datatype="0,is_vin,17" value="" operation="like" title="(请输入后8位或者17位)"/></td>
							 <td><label>发动机号：</label></td>
							 <td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO" datatype="0,is_fdjh,30" value="" operation="like" /></td>
							 <td><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin" >验&nbsp;&nbsp;证</button></div></div>
							 <div class="button"><div class="buttonContent"><button type="button" id="dia-recheckvin" >重新填写</button></div></div></td>
						 </tr>
						 <tr>
						   	 <td><label>车辆型号：</label></td>
							 <td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly" /></td>
				             <td><label>购车日期：</label></td>
				             <td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
				             <td><label>行驶里程：</label></td>
				             <td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value="" datatype="0,is_digit,10" /></td>
				         </tr>
				         <tr >
				             <td><label>客户名称：</label></td>
				             <td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value="" datatype="0,is_null,300" /></td>
				             <td><label>联系人：</label></td>
				             <td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value="" datatype="0,is_null,30" /></td>
				             <td><label>电话：</label></td>
				             <td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value="" datatype="0,is_null,300" /></td>
				          </tr>
				          <tr>
				          	 <td><label>故障分析：</label></td>
							 <td><input type="text" id="dia-in-hsj0" name="dia-in-gzfx0"  datatype="1,is_null,30" kind="dic" src="T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302702" datasource="FAULT_ANALYSE"/></td>
			          	     <td><label>客户地址：</label></td>
			                 <td><textarea id="dia-user_address" style="width: 150px; height: 30px;" datasource="USER_ADDRESS" name="KHDZ" datatype="1,is_null,100"></textarea></td>
				             <td><label>本次故障日期：</label></td>
				             <td><input type="text" id="dia-fault_date" name="BCGZRQ" value="" datasource="FAULT_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})"/></td>
				          </tr>
				          <tr>
				             <td><label>收货人：</label></td>
				             <td><input type="text" id="SHR" name="SHR" value="" datasource="RECEIPT_USER" datatype="0,is_null,30" /></td>
				             <td><label>收货人电话：</label></td>
				             <td><input type="text" id="SHRDH" name="SHRDH" value="" datasource="RECEIPT_PHONE" datatype="0,is_phone,30" /></td>
				             <td><label>收货地址：</label></td>
				             <td><textarea id="SHDZ" style="width: 150px; height: 30px;" name="SHDZ" datasource="RECEIPT_ADDRESS" datatype="1,is_null,100"></textarea></td>
			              </tr>
			              <tr>
				              <td><label>申请人备注：</label></td>
				              <td colspan="5"><textarea id="SQRBZ" style="width: 450px; height: 50px;" datasource="REMARKS" name="SQRBZ" datatype="1,is_null,100"></textarea></td>
				            </tr>
				          </table>
						</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
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
							<td><input type="text" id="PJDM" name="PJDM" datasource="PART_CODE"  datatype="1,is_null,30"  value=""  operation="like"/></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="PJMC" name="PJMC" datasource="PART_NAME"  datatype="1,is_null,30" value=""  operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchPart">查&nbsp;&nbsp;询</button></div></div></li>
						    <li id="dia-report_li"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
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
								<th type="multi" name="XH" unique="DTL_ID"></th>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="COUNT" >数量</th>
								<th fieldname="AMOUNT"  refer="amountFormat" align="right">索赔单价(元)</th>
								<th fieldname="REMARKS"  colwidth="60">备注</th>
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