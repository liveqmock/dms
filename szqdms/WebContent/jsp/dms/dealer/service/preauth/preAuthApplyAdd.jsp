<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="com.org.dms.action.service.common.MarkedWords"%>
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
<title>预授权新增</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $checkHisTrue=true;
var $fileTrue=true;
var $partTrue=true;
var authorStatus=null;
var action = "<%=action%>";
var dia_dialog = parent.$("body").data("preAuthApply");
var diaSaveAction = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction";
var itemSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/itemSearch.ajax";
var fileSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/fileSearch.ajax";
var partSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/authPartSearch.ajax";
var authorHisSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/authorHisSearch.ajax";
$(function() 
{
	//设置页面高度
	$("#dia-fm-preAuthBasic").height(document.documentElement.clientHeight-80);
	if(action != "1"){
		var selectedRows = parent.$("#preAuthlist").getSelectedRows();
		setEditValue("dia-fm-preAuthBasic",selectedRows[0].attr("rowdata"));
		$("#dia-vin").attr("readonly",true);
		$("#dia-engine_no").attr("readonly",true);
		authorStatus=selectedRows[0].attr("AUTHOR_STATUS");
		var authorType=selectedRows[0].attr("AUTHOR_TYPE");
		if(authorStatus=='<%=DicConstant.YSQZT_03%>'){
			$("#auditH").show();
		}else{
			$("#dia-nextH3").hide();
		}
		if(authorType=="300302"){
			$("#gzdzL").show();
			$("#gzdzT").show();
			$("#jeTr").hide();
		}else if(authorType=="300303"){
			$("#gzdzL").show();
			$("#gzdzT").show();
			$("#out1").show();
			$("#out2").show();
			$("#jeTr").hide();
		}
	}else{
		$("#itemH").hide();
		$("#attaH").hide();
		$("#partH").hide();
		$("#dia-nextH1").hide();
		$("#dia-nextH3").hide();
		$("#dia-report_li").hide();
	}
	//保存
	$("#dia-save").bind("click",function(event){
		if($("#vehicleId").val()==''){
			alertMsg.info("请点击验证按钮,验证车辆是否存在.");
			return false;
		}
		if($("#dia-user_type").attr("code")== 300102){
			alertMsg.info("军用车不能报预授权.");
			return false;
		}
		//获取需要提交的form对象
		var $f = $("#dia-fm-preAuthBasic");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-preAuthBasic").combined(1) || {};
		var authorId=$("#authorId").val();
		if(authorId == '')	//插入动作
		{
			var addUrl = diaSaveAction + "/preAuthInsert.ajax";
			doNormalSubmit($f,addUrl,"dia-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/preAuthUpdate.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//提报
	$("#dia-report").bind("click",function(){
		if($("#vehicleId").val()==''){
			alertMsg.info("请点击验证按钮,验证车辆是否存在.");
			return false;
		}
		var authorId=$("#authorId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-preAuthBasic");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-preAuthBasic").combined(1) || {};
		var reportUrl = diaSaveAction + "/preAuthReport.ajax?authorId="+authorId+"&flag="+2;
		doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	});
	//查询配件
	$("#searchAuPart").bind("click",function(){
		$("#val1").val("");//清空
		searchAuthPart();
	});
	//新增预授权配件
	$("#addPart").bind("click",function(){
		var options = {max:false,width:850,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/dealer/service/preauth/preAuthPartAdd.jsp", "authPart", "新增配件", options);
	});
	//删除配件
	$("#deletePart").bind("click",function(){
		var relIds=$("#val1").val();
	    if(relIds=="")
	    {
	    	alertMsg.warn("请选择删除配件.");
	    	return false;
	    }else{
	    	var delPartUrl =diaSaveAction+"/deleteParts.ajax?relIds="+relIds;
			sendPost(delPartUrl,"deletePart","",deletePartCallBack,"true");
	    }
	});
	//验证
	$("#dia-checkvin").bind("click",function(){
		var vinVal=$("#dia-vin").val();
		var engineVal=$("#dia-engine_no").val(); 
		if(vinVal==''){
			alertMsg.info("VIN不能为空.");
			return;
		}
		if(engineVal==''){
			alertMsg.info("发动机号不能为空.");
			return;
		}
		/* if(vinVal.length!='8' || vinVal.length!='17'){
			alertMsg.info("请输入VIN后8位或者17位！");
			return;
		} */
		var options = {max : true,mask : true,mixable : true,minable : true,resizable : true,drawable : true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/preauth/vinCheck.jsp?vinVal='"+vinVal+"'&engineVal='"+engineVal+"'","vinCheck", "预授权信息维护-校验车辆", options);
	});
	//上传附件
	$("#addAtt").bind("click",function(){
		var authorId=$("#authorId").val();
		$.filestore.open(authorId,{"fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	
	//重新填写
	$("#dia-recheckvin").bind("click",function(){
		alertMsg.confirm("清空车辆信息，确认重新填写?",{okCall:doConOk1,cancelCall:doConOk2});
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
						var $f = $("#dia-fm-item");
						var sCondition = {};
						sCondition = $f.combined() || {};
						var itemSearchUrl1 =itemSearchUrl+"?authorId="+$("#authorId").val(); 
						doFormSubmit($f,itemSearchUrl1,"",1,sCondition,"dia-itemlb");
					}
					$true=false;
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
			case 2: 
				if($partTrue){
					searchAuthPart();
				}
				$partTrue=false;
				break;
			case 3:
				if($checkHisTrue){
					if(authorStatus == '<%=DicConstant.YSQZT_03%>'){
						var $f = $("#dia_fm_his");
						var sCondition = {};
				    	sCondition = $f.combined() || {};
				    	var authorHisSearchUrl1 =authorHisSearchUrl+"?authorId="+$("#authorId").val(); 
						doFormSubmit($f,authorHisSearchUrl1,"",1,sCondition,"dia_hislb");
					}
				}
				$checkHisTrue=false;
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
		parent.$.pdialog.close(dia_dialog);
		return false;
	});
	$("#dia-itemlb").show();
	$("#dia-itemlb").jTable();
});

//查询配件
function searchAuthPart(){
	var $f = $("#dia_fm_part");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var partSearchUrl1 =partSearchUrl+"?authorId="+$("#authorId").val(); 
	doFormSubmit($f,partSearchUrl1,"",1,sCondition,"partAuthList");
}
//删除配件回调方法
function deletePartCallBack(res){
	try{
		searchAuthPart();
	}catch(e){
		alertMsg.error(e);
		return false;
	}
	return true;
}
//金额格式化
function amountFormat(obj){
  return amountFormatNew($(obj).html());
}
//附件回调方法
function fjUpCallBack(fjid){
	var $f = $("#dia_fm_atta");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var fileSearchUrl1 =fileSearchUrl+"?authorId="+$("#authorId").val(); 
	doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
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
		$("#dia-vin").val($("#dia-di-vin").val());
		$("#dia-models_code").val($("#dia-di-models_code").val());//车型代码
		/* $("#dia-certificate").val($("#dia-di-certificate").val());//合格证 */
		$("#dia-engine_type").val($("#dia-di-engine_type").val());//发动机型号
		$("#dia-user_type").val($("#dia-di-user_type").val());//用户类型
		$("#dia-vehicle_use").val($("#dia-di-vehicle_use").val());//车辆用途
		$("#dia-drive_form").val($("#dia-di-drive_form").val());//驱动形式
		$("#dia-buy_date").val($("#dia-di-buy_date").val());//购车日期
		$("#dia-mileage").val($("#dia-di-mileage").val());//行驶里程
		$("#dia-guarantee_no").val($("#dia-di-guarantee_no").val());//保修卡
		/* $("#dia-factory_date").val($("#dia-di-factory_date").val());//出厂日期 */
		$("#dia-maintenance_date").val($("#dia-di-maintenance_date").val());//首保日期
		$("#dia-license_plate").val($("#dia-di-license_plate").val());//车牌号
		$("#dia-user_name").val($("#dia-di-user_name").val());//用户名称
		$("#dia-user_no").val($("#dia-di-user_no").val());//身份证
		$("#dia-link_man").val($("#dia-di-link_man").val());//联系人
		$("#dia-phone").val($("#dia-di-phone").val());//电话
		$("#dia-user_address").text($("#dia-di-user_address").text());//地址
	}
	if(flag==2){
		vehEmpty();
		alertMsg.warn("VIN与发动机号不存在.");
	}
	return true;
}
//confirm返回true
function doConOk1(){
	$("#dia-vin").removeClass("readonly");
	$("#dia-engine_no").removeClass("readonly");
	$("#dia-vin").attr("readOnly",false);
	$("#dia-engine_no").attr("readOnly",false);
	//$("#dia-vin").val("");
	//$("#dia-engine_no").val("");
	vehEmpty();
}
//车辆信息设置为空
function vehEmpty(){
	$("#vehicleId").val("");//车辆ID
	$("#modelsId").val("");//车型ID
	$("#dia-models_code").val("");//车型代码
	/* $("#dia-certificate").val("");//合格证 */
	$("#dia-engine_type").val("");//发动机型号
	$("#dia-user_type").val("");//用户类型
	$("#dia-vehicle_use").val("");//车辆用途
	$("#dia-drive_form").val("");//驱动形式
	$("#dia-buy_date").val("");//购车日期
	$("#dia-mileage").val("");//行驶里程
	$("#dia-guarantee_no").val("");//保修卡
	/* $("#dia-factory_date").val("");//出厂日期 */
	$("#dia-maintenance_date").val("");//首保日期
	$("#dia-license_plate").val("");//车牌号
	$("#dia-user_name").val("");//用户名称
	$("#dia-user_no").val("");//身份证
	$("#dia-link_man").val("");//联系人
	$("#dia-phone").val("");//电话
	$("#dia-user_address").text("");//地址
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
		if(rows && rows.length > 0)
		{
			//var objxml = res.documentElement;
			var authorId =getNodeText(rows[0].getElementsByTagName("AUTHOR_ID").item(0));
			var authorNo =getNodeText(rows[0].getElementsByTagName("AUTHOR_NO").item(0));
			$("#authorId").val(authorId);
			$("#dia-preAuthNo").val(authorNo);
		}else
		{
			return false;
		}
		//不显示结果集的情况下，插入一行
		parent.$("#preAuthlist").insertResult(res,0);
		$("td input[type=radio]",parent.$("#preAuthlist_content").find("tr").eq(0)).attr("checked",true);
		if(parent.$("#preAuthlist_content").size()>0){
			$("td input[type=radio]",parent.$("#preAuthlist_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#preAuthlist").find("tr").eq(0)).attr("checked",true);
		}
		$("#itemH").show();
		$("#attaH").show();
		$("#partH").show();
		$("#dia-nextH1").show();
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
		var selectedIndex = parent.$("#preAuthlist").getSelectedIndex();
		parent.$("#preAuthlist").updateResult(res,selectedIndex);
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
			var $row = parent.$("#preAuthlist").getSelectedRows();//选择行
			if($row[0]){
				parent.$("#preAuthlist").removeResult($row[0]);//移除选择行
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
//添加项目
function addItem()
{
	var $tabs = $("#dia-itemlb_content");
	while($tabs.get(0).tagName != "TABLE")
        $tabs = $tabs.parent();
    $tabs.parent().parent().attr("style","overflow:hidden;");
	$tabs.createRow();
    
}
//行编辑 保存
function doDiaItemSave(row){
	var ret = true;
	try
	{
		$("td input[type=radio]",$(row)).attr("checked",true);
		if($(row).find("td").eq(2).find("input:first").attr("code")==''){
			alertMsg.warn("请表选项目类型,重新选择.");
			return false;
		}
		if($(row).find("td").eq(4).text()==''){
			alertMsg.warn("请表选项目代码,重新选择.");
			return false;
		}
		if($(row).find("td").eq(3).find("input:first").attr("code")==''){
			alertMsg.warn("请表选项目代码,重新选择.");
			return false;
		}
		var $f = $("#fm_item");
		var projectId = $(row).attr("PROJECT_ID");
		var amountId = $(row).attr("AMOUNT_ID");
		var authorId=$("#authorId").val();
		//设置隐藏域
		$("#itemAuthorId").val(authorId);
		$("#amountId").val(amountId);
		$("#projectType").val($(row).find("td").eq(1).find("input:first").attr("code"));
		$("#timeCode").val($(row).find("td").eq(2).find("input:first").val());
		$("#timeName").val($(row).find("td").eq(3).text());
		$("#amountSet").val($(row).find("td").eq(4).text());
		$("#remarks").val($(row).find("td").eq(5).find("input:first").val());
		$("#projectId").val(projectId);
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		//需要将隐藏域或者列表只读域传给后台
		if(projectId)
		{
			var url = diaSaveAction + "/itemUpdate.ajax";
			sendPost(url,"",sCondition,diaListSaveCallBack,"true");
		}else
		{
			var url = diaSaveAction + "/itemInsert.ajax";
			sendPost(url,"",sCondition,diaListSaveCallBack,"false");
		}
	}catch(e){alertMsg.error(e);ret = false;}
	return ret;
}
//行编辑保存回调
function diaListSaveCallBack(res){
	var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
	if(result!='1'){
		var selectedIndex = $("#dia-itemlb").getSelectedIndex();
		$("#dia-itemlb").updateResult(res,selectedIndex);
		$("#dia-itemlb").clearRowEdit($("#dia-itemlb").getSelectedRows()[0],selectedIndex);
	}
	return true;
}
//行编辑 删除
var $row;
function doDiaItemDelete(row){
	$row =$(row);
	$("td input[type=radio]",$(row)).attr("checked",true);
	var projectId = $(row).attr("PROJECT_ID");
	if(projectId){
		var url = diaSaveAction + "/itemDelete.ajax?projectId="+projectId;
		sendPost(url,"delete","",diadeleteCallBack,"true");
	}else{
		$("#dia-itemlb").removeResult($row);
	}
}
//行编辑删除回调方法
function  diadeleteCallBack(res)
{
	try
	{
		if($row) 
			$("#dia-itemlb").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 字典中第几行
	//判断id中以TIME_CODE开始的
	if(id.indexOf("TIME_CODE") == 0)
	{
		var curRow = $("#"+id);
		while(curRow.get(0).tagName != "TR")
		{
			curRow = curRow.parent();
		}
		//项目信息
		curRow.attr("AMOUNT_ID",$row.attr("AMOUNT_ID"));//工时ID绑定到行对象
		curRow.find("td").eq(3).text($("#"+id).val());
		curRow.find("td").eq(2).find("input:first").val(($("#"+id).attr("code")));
		curRow.find("td").eq(4).text($row.attr("AMOUNT_SET"));//工时定额
	}
	
	if(id="dia-author_type"){
		//预授权类型 -照顾性保修
		if(!($("#"+id).attr("code") == "300301" || $("#"+id).attr("code") == "300304"))
		{
			$("#gzdzL").show();//故障地址
			$("#gzdzT").show();//故障地址
			$("#jeTr").hide(); //金额
			$("#dia_amount").val("");
		}else
		{
			$("#gzdzL").hide();//故障地址
			$("#gzdzT").hide();//故障地址
			$("#jeTr").show();//金额
			$("#dia_faultLocation").text("");
		}
		//预授权类型 -多次外出
		if($("#"+id).attr("code") == "300303")
		{
			$("#out1").show();
			$("#out2").show();
		}else
		{
			$("#out1").hide();
			$("#out2").hide();
			$("#dia-goDate").val("");
			$("#dia-arriveDate").val("");
			$("#dia-outUcount").val("");
			$("#dia-outUser").val("");
			$("#dia-trailerCost").val("");
			$("#dia-gpsLicensePlate").val("");
		}
	}
	return true;
}
var $rowAtta=null;
function doDeleteAtta(obj)
{
	/* $rowAtta = $(rowobj);
	var url = diaSaveAction + "/attaDelete.ajax?fjid="+$(rowobj).attr("FJID");
	sendPost(url,"","",deleteAttaCallBack,"true"); */
	var fileId = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var fjid = $(obj).attr("FJID");
	var blwjm = $(obj).attr("BLWJM");
	diaFjid = fjid;
	diaFjDelRow = $(obj);
	$.filestore.remove({"fjid":fjid,"fileId":fileId,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm,"callback":delFjBack});
}
function delFjBack(){
	$("#dia-fileslb").removeResult(diaFjDelRow);
	try{
		fjDelCallBack(diaFjid);
	}catch(e){}
}
/* //删除附件回调方法
function  deleteAttaCallBack(res)
{
	try
	{
		if($rowAtta) 
			$("#dia-fileslb").removeResult($rowAtta);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} */
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
function createInputBox(obj)
{
    return '<input type="text" name="QUANTITY" width="10px" onblur="doInputStBlur(this)" / >';
}

function doInputStBlur(obj){
	var $obj = $(obj);
	var $tr = $obj.parent().parent().parent();
	if($obj.val() == "")//为空直接返回
	    return ;	
	if($obj.val() && !isNum($obj.val()))// 校验是否是数字
	{
	    alertMsg.warn("请输入正确的数量.");
	    $obj.val("");
	    return;
	}
	var checkObj = $("input:first",$tr.find("td").eq(1));
	var s = $obj.val();
	if(s)
	{
	    checkObj.attr("checked", true);
	}
	doSelectedBefore($tr,checkObj,1);
}
function doSelectedBefore($tr,$obj,type)
{
    var $input = $tr.find("td").eq(5).find("input:first");
    var s = "";
    if($input && $input.get(0).tagName=="INPUT")
        s = $input.val();
    else
    {
        s = $tr.find("td").eq(5).text();
    }
    doCheckbox($obj.get(0));
}
//复选
function doCheckbox(checkbox) {
	var $t=$(checkbox);
	while($t[0].tagName != "TABLE")
    {
		$t = $t.parent();
    }
	//var $t=$(checkbox).parent().parent().parent().parent().parent();
	if($t.attr("id").indexOf("dia_di_PartList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var s = "";
	    if($tr.find("td").eq(5).find("input:first").size()>0) {
	    	s = $tr.find("td").eq(5).find("input:first").val();
	    } else {
	        s = $tr.find("td").eq(5).text();
	    }
	    if (!s || !isNum(s)) { 
            alertMsg.warn("请输入正确的数量.");
            $(checkbox).attr("checked",false);
            return false;
        }
	    var arr = [];
	    arr.push($tr.attr("PART_ID"));//val0 配件ID
	    arr.push(s);//val2 配件数量
	    arr.push($tr.attr("PART_CODE"));//val3 配件代码
	    arr.push($tr.attr("PART_NAME"));//val4 配件名称
	    arr.push($tr.attr("SE_CLPRICE"));//val5 配件名称
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    multiSelected($checkbox, arr,$("#dia_di_partInfo"));
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(5).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(5).html("<div><input type='text' name='QUANTITY' width='10px' onblur='doInputStBlur(this)' value='"+s+"'/ ></div>");
        }
	}
	if($t.attr("id").indexOf("partAuthList")==0){
		var $tr = $(checkbox).parent().parent().parent();
	    var arr = [];
	    arr.push($tr.attr("REL_ID"));
	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    multiSelected($checkbox, arr,$("#partDeleteVal"));
	} 
}
/*
 * 翻页回显方法:步骤四
 */
function customOtherValue($row,checkVal)
{
	var $t=$row;
	while($t[0].tagName != "TABLE")
	{
		$t = $t.parent();
	}
	if($t.attr("id").indexOf("dia_di_PartList")==0){
		var $inputObj = $row.find("td").eq(5);
	    var val="";
	    if($("#val2") && $("#val2").val())
	    {
	        var pks = $("#val0").val();
	        var res = $("#val2").val();
	        var pk = pks.split(",");
	        var re = res.split(",");
	        for(var i=0;i<pk.length;i++)
	        {
	            if(pk[i] == checkVal)
	            {
	                val=re[i];
	                break;
	            }
	        }
	    }
	    if(val)
	    {
	        $inputObj.html("<div>"+val+"</div>");
	    }
	}
}
function isNum(val)
{
	var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
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
					<li id="itemH"><a href="javascript:void(0)"><span>工时信息</span></a></li>
					<li id="attaH"><a href="javascript:void(0)"><span>附件信息</span></a></li>
					<li id="partH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
					<li id="auditH" style="display:none"><a href="javascript:void(0)"><span>审核信息</span></a></li>
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
						 		<td><input type="text" id="dia-preAuthNo" name="dia-preAuthNo" datasource="AUTHOR_NO" value="系统自动生成" readonly="readonly"/></td>
								<td><label>预授权类型：</label></td>
								<td>
									<select type="text" id="dia-author_type" name="dia-author_type" kind="dic" src="YSQLX" datasource="AUTHOR_TYPE" datatype="0,is_null,30" value="">
											<option value="300301" selected>照顾性保修</option>
									</select>
								</td>
							</tr>
							<tr>
								<td><label>VIN：</label></td>
								<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN" datatype="0,is_vin,17" value="" operation="like" title="(请输入后8位或者17位)"/></td>
								<td><label>发动机号：</label></td>
								<td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO" datatype="0,is_fdjh,30" value="" operation="like" /></td>
								<td colspan="3">
									<div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin" >验&nbsp;&nbsp;证</button></div></div>
									<span style="float:left;margin-left:10px;margin-top:5px;"></span>
									<div class="button"><div class="buttonContent"><button type="button" id="dia-recheckvin" >重新填写</button></div></div>
								</td>
							</tr>
							<tr>
								<td><label>车辆型号：</label></td>
								<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly" /></td>
								<!-- <td><label>合格证号：</label></td>
								<td><input type="text" id="dia-certificate" name="dia-certificate"  datasource="CERTIFICATE" value="" readonly="readonly" /></td> -->
								<td><label>发动机型号：</label></td>
								<td><input type="text" id="dia-engine_type" name="dia-engine_type" datasource="ENGINE_TYPE" value="" readonly="readonly" /></td>
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
								<td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value="" datatype="0,is_digit,10" /></td>
								<td><label>保修卡号：</label></td>
								<td><input type="text" id="dia-guarantee_no" name="dia-guarantee_no" datasourc="GUARANTEE_NO" value=""  readonly="readonly" /> </td>
							</tr>
						<!-- 	<tr >
								<td><label>出厂日期：</label></td>
								<td><input type="text" id="dia-factory_date" name="dia-factory_date" datasource="FACTORY_DATE" value=""  readonly="readonly" /></td>
								
							</tr> -->
							<tr>
								<td><label>车牌号码：</label></td>
								<td><input type="text" id="dia-license_plate" name="dia-license_plate" datasource="LICENSE_PLATE" value="" datatype="0,is_carno,30" /></td>
								<td><label>用户名称：</label></td>
								<td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value="" datatype="0,is_null,300" /></td>
								<td><label>身份证号：</label></td>
								<td><input type="text" id="dia-user_no" name="dia-user_no"  datasource="USER_NO" value="" datatype="1,is_idcard,30" /></td>
							</tr>
							<tr>
								<td><label>联系人：</label></td>
								<td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value="" datatype="0,is_null,30" /></td>
								<td><label>手机号码：</label></td>
								<td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value="" datatype="0,is_phone,300" /></td>
								<td><label>用户地址：</label></td>
								<td><textarea id="dia-user_address" style="width: 150px; height: 30px;" name="dia-user_address" datasource="USER_ADDRESS" datatype="1,is_null,300"></textarea></td>
							</tr>
							<tr id="jeTr">
								<td><label>金额：</label></td>
								<td><input type="text" id="dia_amount" name="dia_amount" value="" datasource="AMOUNT"  datatype="0,is_money,30" /></td>
							</tr>
							<tr>
								<td><label>固定电话</label></td>
								<td><input type="text" id="dia_telephone" name="dia_telephone" datasource="TELEPHONE" value="" datatype="1,is_null,30" /></td>
								<td id="gzdzL" style="display:none"><label>故障地址：</label></td>
								<td id="gzdzT" style="display:none" colspan="3"><textarea id="dia_faultLocation" style="width: 150px; height: 30px;" name="dia_faultLocation" datasource="FAULTLOCATION" datatype="1,is_null,300"></textarea></td>
								
							</tr>
							<tr id="out1" style="display:none">
								<td><label>外出时间：</label></td>
								<td><input type="text" id="dia-goDate" name="dia-goDate" datasource="GO_DATE"   kind="date" value="" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								<td><label>到达时间：</label></td>
								<td><input type="text" id="dia-arriveDate" name="dia-arriveDate" datasource="ARRIVE_DATE"   kind="date" value="" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								<td><label>外出人数：</label></td>
								<td><input type="text" id="dia-outUcount" name="dia-outUcount"  datasource="OUT_UCOUNT" value="" datatype="1,is_digit_0,10" /></td>
							</tr>
							<tr id="out2" style="display:none">
								<td><label>外出人员：</label></td>
								<td><input type="text" id="dia-outUser" name="dia-outUser" datasource="OUT_USER" value="" datatype="0,is_null,100" /></td>
								<td><label>拖车费金额：</label></td>
								<td><input type="text" id="dia-trailerCost" name="dia-trailerCost" datasource="TRAILER_COST" value="" datatype="1,is_money,300" /></td>
								<td><label>GPS车牌照：</label></td>
								<td><input type="text" id="dia-gpsLicensePlate" name="dia-gpsLicensePlate"  datasource="GPS_LICENSE_PLATE" value="" datatype="0,is_null,30" /></td>
							</tr>
							<tr>
								<td><label>故障分析及备注说明：</label></td>
								<td colspan="5"><textarea id="dia-ramarks" style="width: 450px; height: 50px;" name="dia-ramarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea></td>
							</tr>
						</table>
						</fieldset>
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="dia-report_li"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
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
					<table style="display:none;width:100%;" id="dia-itemlb" name="dia-itemlb" ref="dia-item" edit="true" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:" append="plus|addItem"></th>
								<!-- <th fieldname="PROJECT_TYPE" ftype="input"  fkind="dic" fsrc="XMLX" fdatatype="0,is_null,30">工时类型</th> -->
								<th fieldname="TIME_CODE" ftype="input" fdatatype="0,is_null,30"  fkind="dic" fsrc="T#SE_BA_TASK_AMOUNT:TIME_CODE:TIME_NAME{AMOUNT_ID,AMOUNT_SET}:STATUS=100201 " >工时代码</th>
								<th fieldname="TIME_NAME" freadonly="true" >工时名称</th>
								<th fieldname="AMOUNT_SET" freadonly="true">工时定额</th>
								<th fieldname="REMARKS" colwidth="100" ftype="input" >备注</th>
								<th colwidth="105" type="link" title="[编辑]|[删除]"  action="doDiaItemSave|doDiaItemDelete">操作</th>
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
				<form method="post" id="dia_fm_atta" class="editForm" >
				</form>
				<div align="left">
              	<fieldset>
					<div id="dia-files">
					<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="FJMC" >附件名称</th>
								<th fieldname="CJR" >上传人</th>
								<th fieldname="CJSJ">上传时间</th>
								<th colwidth="85" type="link" title="[删除]|[下载]"  action="doDeleteAtta|doDownloadAtta">操作</th>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上传附件</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
		<div class="page">
				<div class="pageHeader">
					<form id="dia_fm_part" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="dia_partTable">
								<tr>
									<td><label>配件代码：</label></td>
									<td><input type="text" id="PJDM" name="PJDM" datasource="PART_CODE"  datatype="1,is_null,30"  value=""  operation="like"/></td>
									<td><label>配件名称：</label></td>
									<td><input type="text" id="PJMC" name="PJMC" datasource="PART_NAME"  datatype="1,is_null,30" value=""  operation="like"/></td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="button"><div class="buttonContent"><button type="button" id="searchAuPart" >查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
									<li id="dia-nextH3"><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一步</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
				</div>
				<div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="addPart" title=""><span>新增配件</span></a></li>
						<li class="line">line</li>
						<li><a class="delete" href="javascript:void(0);" id="deletePart" title="确定要删除吗?"><span>批量删除</span></a></li>
					</ul>
				</div>
				<div class="pageContent">
					<div id="partAuth">
						<table  style="display: none" width="100%" id="partAuthList" name="partAuthList" multivals="partAuth" ref="partAuth"  refQuery="dia_partTable">
							<thead>
								<tr>
									<th type="multi" name="XH" unique="REL_ID"></th>
									<th fieldname="PART_CODE" >配件代码</th>
									<th fieldname="PART_NAME" >配件名称</th>
									<th fieldname="QUANTITY">数量</th>
									<th fieldname="UNIT_PRICE" align="right" refer="amountFormat">单价</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div id="partDeleteVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val1" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
						</tr>
					</table>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
        </div>	
	</div>
	<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
	</div>
	<%--隐藏域 --%>
	<form method="post" id="fm_item" style="display:">
		<input type="hidden" id="amountId" datasource="AMOUNT_ID"/>
		<input type="hidden" id="timeCode" datasource="TIME_CODE"/>
		<input type="hidden" id="timeName" datasource="TIME_NAME"/>
		<!-- <input type="hidden" id="projectType" datasource="PROJECT_TYPE"/> -->
		<input type="hidden" id="remarks" datasource="REMARKS"/>
		<input type="hidden" id="itemAuthorId" datasource="AUTHOR_ID"/>
		<input type="hidden" id="projectId" datasource="PROJECT_ID"/>
		<input type="hidden" id="amountSet" datasource="AMOUNT_SET"/>
	</form>
	<form id="dialog-fm-download"style="display:none">
	</form>
</div>
</body>
</html>