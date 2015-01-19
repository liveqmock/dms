<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var action = "<%=action%>";
var $modelTrue=true;
var $CLTrue=true;
var $partTrue=true;
var $timeTrue=true;
var $vinTrue=true;
var $fileTrue=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction";
var fileSearchUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction/fileSearch.ajax";

$(function(){
	//初始化页面，根据Index页面传过来action的值确定执行哪段逻辑
	if(action != "1")
	{
		//进入修改页面需要根据是否索赔与是否固定费用确定需要显示的tab页面
		$("#hddmT").show();
		$("#hddmI").show();
		$("#dia-nextH4").hide();
		$("#dia-nextH6").hide();
		$("#ClNext-id").hide();
		projectHide();
		$("#SFGDFY").change(function(){
		    var sfgdfyVal=$(this).val();
		    if(sfgdfyVal==<%=DicConstant.SF_02%>){
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
				$("#la_rzlx").hide();
				$("#DI_rzlx").hide();
				$("#dia-nextH4").show();
				$("#dia-nextH6").show();
				$("#ClNext-id").show();
				projectShow();
		    }
			if(sfgdfyVal==<%=DicConstant.SF_01%>){
				$("#la_hdfy").show();
				$("#DI_HDFY").show();
				$("#la_rzlx").show();
				$("#DI_rzlx").show();
				$("#dia-nextH4").hide();
				$("#dia-nextH6").hide();
				$("#ClNext-id").hide();
				projectHide();
			};
		});
		var selectedRows = parent.$("#tab-serviceActivity").getSelectedRows();
		setEditValue("fm-serviceActivityInfo",selectedRows[0].attr("rowdata"));
		if($("#SFGDFY").val()==<%=DicConstant.SF_01%>){
			$("#la_hdfy").show();
			$("#DI_HDFY").show();
			$("#la_rzlx").show();
			$("#DI_rzlx").show();
		}else if($("#SFGDFY").val()==<%=DicConstant.SF_02%>){
			projectShow();
			$("#dia-nextH4").show();
			$("#dia-nextH6").show();
			$("#ClNext-id").show();	
			$("#la_hdfy").hide();
			$("#DI_HDFY").hide();
			$("#la_rzlx").hide();
			$("#DI_rzlx").hide();
		}
	}else{
		//进入新增页面默认所有Tab页都是隐藏的。
		//是否固定费用可以控制，活动费用这个输入框的显示隐藏。
		$("#cxxxH").hide();
		$("#clxxH").hide();
		$("#fjxxH").hide();
		$("#vinxxH").hide();
		$("#dia-nextH1").hide();
		$("#la_hdfy").hide();
		$("#DI_HDFY").hide();
		$("#la_rzlx").hide();
		$("#DI_rzlx").hide();
		projectHide();
		$("#SFGDFY").change(function(){
		    var sfgdfyVal=$(this).val();
		    if(sfgdfyVal==<%=DicConstant.SF_02%>){
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
				$("#la_rzlx").hide();
				$("#DI_rzlx").hide();
		    }
			if(sfgdfyVal==<%=DicConstant.SF_01%>){
				$("#la_hdfy").show();
				$("#DI_HDFY").show();
				$("#la_rzlx").show();
				$("#DI_rzlx").show();
				$("#rzlx").val(-1);
			}    
		});
	}
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-serviceActivityInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false){
			return false;
		} 
		var sfgdfyVal = $("#SFGDFY").val();
		if(sfgdfyVal==<%=DicConstant.SF_01%>){
			var hdfy = $("#DI_HDFY").val();
			if(hdfy==""||hdfy==null){
			    alertMsg.warn("请填写活动费用！");
		    	return false;
			}
		}
		if(sfgdfyVal==<%=DicConstant.SF_01%>){
			
			var rzlxVal= $("#rzlx").val();
			if(rzlxVal==-1){
				alertMsg.warn("固定费用的服务活动需要选择入账类型！");
				return false;
			}
		}
		var kslc =$("#DI_KSLC").val();
		var jslc =$("#DI_JSLC").val();
		if(jslc==""){
			jslc=0;
		}
		if(kslc==""){
			kslc=0;
		}
		if(kslc-jslc>0){
			alertMsg.warn("开始里程不可大于结束里程！");
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-serviceActivityInfo").combined(1) || {};
		if($("#SFGDFY").val()==100102){
			$("#DI_HDFY").val("");
		}
		if(action == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	//下载模板
	$('#download').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=serviceActivityVIN.xls");
        window.location.href = url;
    });
	//导入
	$("#dia-imp").bind("click",function(){
		var activityId=$("#serviceActivityInfoId").val();
        importXls("SE_BU_ACTIVITY_VEHICLE_TMP",activityId,2,3,"/jsp/dms/oem/service/serviceactivity/importSuccess1.jsp");
	});
	$("#deleteModel").bind("click", function(event)
	{
		var mxids=$("#val0").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择车型！");
	    	return false;
	    }else{
	    	var sccxUrl =diaSaveAction+"/deleteModels.ajax?mxids="+mxids;
			sendPost(sccxUrl,"deleteModel","",deleteCXCallBack,"true");
	    }
	});
	$("#deleteWorkH").bind("click", function(event)
	{
		var mxids=$("#val1").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择工时！");
	    	return false;
	    }else{
	    	var scgsUrl =diaSaveAction+"/deleteTimes.ajax?mxids="+mxids;
			sendPost(scgsUrl,"deleteWorkH","",deleteGSCallBack,"true");
	    }
	});
	$("#deletePart").bind("click", function(event)
	{
		var mxids=$("#val2").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择配件！");
	    	return false;
	    }else{
	    	var scgsUrl =diaSaveAction+"/deleteParts.ajax?mxids="+mxids;
			sendPost(scgsUrl,"deletePart","",deletePartCallBack,"true");
	    }
	});
	$("#deleteVin").bind("click", function(event)
	{
		var mxids=$("#val3").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择VIN！");
	    	return false;
	    }else{
	    	var scgsUrl =diaSaveAction+"/deleteVIN.ajax?mxids="+mxids;
			sendPost(scgsUrl,"deleteVin","",deleteVINCallBack,"true");
	    }
	});
	//查询按钮响应
	$("#searchServiceModel").bind("click", function(event){
		var $f = $("#fm-fwhdcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchServiceModelUrl =diaSaveAction+"/searchServiceModel.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceModelUrl,"searchServiceModel",1,sCondition,"fwhdcxlb");
		});
	$("#searchWorkH").bind("click", function(event){
		var $f = $("#fm-fwhdxmgs");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceGsUrl =diaSaveAction+"/searchServiceTime.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceGsUrl,"searchWorkH",1,sCondition,"fwhdxmgslb");
		});
	$("#searchPart").click(function(){
		var $f = $("#fm-fwhdxmpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchServiceParts.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
	});
	$("#searchVin").click(function(){
		var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVin.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdvinlb");
	});
	//上传附件
	$("#addAtt").bind("click",function(){
		var activityId=$("#serviceActivityInfoId").val();
		$.filestore.open(activityId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	//新增车型页面
	$("#addModel").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityTypeMngCXAdd.jsp?", "serviceModel", "服务活动车型新增", options);
	});
	//新增工时页面
	$("#addWorkH").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityTypeMngGSAdd.jsp","serviceTime", "服务活动工时新增", options);
	});
	//新增配件页面
	$("#addPart").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityTypeMngPartAdd.jsp", "servicePart", "服务活动配件新增", options);
	});
	//新增项目页面
	$("#addVin").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceactivity/serviceActivityTypeMngVINAdd.jsp", "serviceVIN", "服务活动VIN新增", options);
	});
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("ServiceActivityType");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
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
				if($vinTrue){
					var $f = $("");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
					//combined()：实现将页面条件按规则拼接成json
			    	sCondition = $f.combined() || {};
			    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVin.ajax?&activityId="+$("#serviceActivityInfoId").val();
					doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdvinlb");
				}
					$vinTrue=false;
					break;
			case 1:
				//点击下一步第一次查询，再次点击不查询
				if($modelTrue){
					//查询车型
					var $f = $("");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
				  	sCondition = $f.combined() || {};
					var searchServiceModelUrl =diaSaveAction+"/searchServiceModel.ajax?&activityId="+$("#serviceActivityInfoId").val();
					doFormSubmit($f,searchServiceModelUrl,"",1,sCondition,"fwhdcxlb");
				}
				$modelTrue=false;
				break;
			case 2: 
				if($CLTrue){
					var $f = $("");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVehage.ajax?&activityId="+$("#serviceActivityInfoId").val();
					doFormSubmit($f,searchServiceProtUrl,"",1,sCondition,"fwhdcllb");   
				}
				$CLTrue=false;
				break;
			case 3: 
					if($fileTrue){
						var $f = $("");
						var sCondition = {};
						sCondition = $f.combined() || {};
						var fileSearchUrl1 =fileSearchUrl+"?&activityId="+$("#serviceActivityInfoId").val();
						doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
						}
						$fileTrue=false;
						break;
			case 4: 
				if($timeTrue){
					var $f = $("");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
			    	sCondition = $f.combined() || {};
			    	var searchServiceGsUrl =diaSaveAction+"/searchServiceTime.ajax?&activityId="+$("#serviceActivityInfoId").val();
					doFormSubmit($f,searchServiceGsUrl,"searchWorkH",1,sCondition,"fwhdxmgslb");
				}
					$timeTrue=false;
					break;
			 case 5: 
					if($partTrue){
						var $f = $("");//获取页面提交请求的form对象
						var sCondition = {};//定义json条件对象
						//combined()：实现将页面条件按规则拼接成json
				    	sCondition = $f.combined() || {};
				    	var searchServicePartUrl =diaSaveAction+"/searchServiceParts.ajax?&activityId="+$("#serviceActivityInfoId").val();
						doFormSubmit($f,searchServicePartUrl,"",1,sCondition,"fwhdxmpjlb");
					}
						$partTrue=false;
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
				case 2://第3个tab页
					break;
				case 3://第4个tab页					
					break;
				case 4://第5个tab页
					break;
				case 5://第6个tab页					
					break;
				default:
					break;
			}
		})
		(parseInt($tabs.attr("currentIndex")));
	});
});
//导入回调方法
function impCall(){
	var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	//combined()：实现将页面条件按规则拼接成json
	sCondition = $f.combined() || {};
	var searchServiceProtUrl =diaSaveAction+"/searchServiceVin.ajax?&activityId="+$("#serviceActivityInfoId").val();
	doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdvinlb");
}
//附件回调方法
function fjUpCallBack(fjid){
	var $f = $("#dia_fm_atta");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var fileSearchUrl1 =fileSearchUrl+"?activityId="+$("#serviceActivityInfoId").val();
	doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
}
//行编辑 保存
function doDiaClSave(row){
	var ret = true;
	var row;
	try
	{
		
		$("td input[type=radio]",$(row)).attr("checked",true);
		var $f = $("#fm_Cl");
		var relationId = $(row).attr("RELATION_ID");
		//设置隐藏域
		$("#relationId").val(relationId);
	 	$("#startDate").val($(row).find("td").eq(2).find("input:first").val());
		$("#endDate").val($(row).find("td").eq(3).find("input:first").val()); 
		if($("#startDate").val()>$("#endDate").val()){
			 alertMsg.warn("开始日期不可大于结束日期！");
			 return false;
		}
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		//需要将隐藏域或者列表只读域传给后台
		var url = diaSaveAction + "/saveCl.ajax";
		sendPost(url,"",sCondition,diaListSaveCallBack,"true");
	}catch(e){alertMsg.error(e);ret = false;}
	return ret;
}
//行编辑保存回调
function diaListSaveCallBack(res){
	var $f = $("");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	//combined()：实现将页面条件按规则拼接成json
	sCondition = $f.combined() || {};
	var searchServiceProtUrl =diaSaveAction+"/searchServiceVehage.ajax?&activityId="+$("#serviceActivityInfoId").val();
	doFormSubmit($f,searchServiceProtUrl,"",1,sCondition,"fwhdcllb");
}
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
function diaInsertCallBack(res)
{
	try
	{	
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var activityId =getNodeText(rows[0].getElementsByTagName("ACTIVITY_ID").item(0));
			var DI_HDDM =getNodeText(rows[0].getElementsByTagName("ACTIVITY_CODE").item(0));
			$("#serviceActivityInfoId").val(activityId);
			$("#DI_HDDM").val(DI_HDDM);
		}else
		{
			return false;
		}
		parent.$("#tab-serviceActivity").insertResult(res,0);
		if(parent.$("#tab-serviceActivity_content").size()>0){
			$("td input[type=radio]",parent.$("#tab-serviceActivity_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#tab-serviceActivity").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		if($("#SFGDFY").val()==<%=DicConstant.SF_01%>){
			$("#la_hdfy").show();
			$("#DI_HDFY").show();
			$("#la_rzlx").show();
			$("#DI_rzlx").show();
			
		}else if($("#SFGDFY").val()==<%=DicConstant.SF_02%>){
			projectShow();
			$("#dia-nextH4").show();
			$("#dia-nextH6").show();
			$("#ClNext-id").show();	
			$("#la_hdfy").hide();
			$("#DI_HDFY").hide();
			$("#la_rzlx").hide();
			$("#DI_rzlx").hide();
		}
		//不显示结果集的情况下，插入一行
		$("#hddmT").show();
		$("#hddmI").show();
		$("#cxxxH").show();
		$("#clxxH").show();
		$("#fjxxH").show();
		$("#vinxxH").show();
		$("#dia-nextH1").show();
		$("#SFGDFY").change(function(){
		    var sfgdfyVal=$(this).val();
		    if(sfgdfyVal==<%=DicConstant.SF_02%>){
				$("#la_hdfy").hide();
				$("#DI_HDFY").hide();
				$("#la_rzlx").hide();
				$("#DI_rzlx").hide();
				$("#dia-nextH4").show();
				$("#dia-nextH6").show();
				$("#ClNext-id").show();
				projectShow();
		    }
			if(sfgdfyVal==<%=DicConstant.SF_01%>){
				$("#la_hdfy").show();
				$("#DI_HDFY").show();
				$("#la_rzlx").show();
				$("#DI_rzlx").show();
				$("#dia-nextH4").hide();
				$("#dia-nextH6").hide();
				$("#ClNext-id").hide();
				projectHide();
			};
		});
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			//var objxml = res.documentElement;
			var activityId =getNodeText(rows[0].getElementsByTagName("ACTIVITY_ID").item(0));
			var DI_HDDM =getNodeText(rows[0].getElementsByTagName("ACTIVITY_CODE").item(0));
			$("#serviceActivityInfoId").val(activityId);
			$("#DI_HDDM").val(DI_HDDM);
			var $f = $("");//获取页面提交请求的form对象
			var sCondition = {};//定义json条件对象
			//combined()：实现将页面条件按规则拼接成json
	    	sCondition = $f.combined() || {};
	    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVehage.ajax?&activityId="+$("#serviceActivityInfoId").val();
			doFormSubmit($f,searchServiceProtUrl,"",1,sCondition,"fwhdcllb");
		}else
		{
			return false;
		}
		var selectedIndex = parent.$("#tab-serviceActivity").getSelectedIndex();
		parent.$("#tab-serviceActivity").updateResult(res,selectedIndex); 
		$("#hddmT").show();
		$("#hddmI").show();
		$("#cxxxH").show();
		$("#clxxH").show();
		$("#fjxxH").show();
		$("#vinxxH").show();
		if($("#DI_SFSP").val()==<%=DicConstant.SF_01%>){
			projectShow();
		}
		if($("#SFGDFY").val()==<%=DicConstant.SF_01%>){
			projectHide();
		}
		$("#fwhdcllb").show();
		$("#fwhdcllb").jTable(); //车龄
		$("#fwhdfjlb").show();
		$("#fwhdfjlb").jTable(); //附件
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//删除回调方法
function  deleteCXCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchServiceModelUrl =diaSaveAction+"/searchServiceModel.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceModelUrl,"searchServiceModel",1,sCondition,"fwhdcxlb");
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function  deleteGSCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdxmgs");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceGsUrl =diaSaveAction+"/searchServiceTime.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceGsUrl,"searchWorkH",1,sCondition,"fwhdxmgslb");
		$("#val1").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function  deletePartCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdxmpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchServiceParts.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
		$("#val2").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function  deleteVINCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVin.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdvinlb");
		$("#val3").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function projectShow(){
	$("#gsxxH").show();
	$("#pjxxH").show();
}
function projectHide(){
	$("#gsxxH").hide();
	$("#pjxxH").hide();
}
//列表复选
function doCheckbox(checkbox)
{
	var $checkbox = $(checkbox);
	var arr = [];
	
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("fwhdcxlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#modelDeletVal"));
	}
	if($checkbox.attr("id").indexOf("fwhdxmgslb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#timeDeletVal"));
	}
	if($checkbox.attr("id").indexOf("fwhdxmpjlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#partDeleteVal"));
	}
	if($checkbox.attr("id").indexOf("fwhdvinlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#VINDeleteVal"));
	}
	if($checkbox.attr("id").indexOf("mainTimelb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("AMOUNT_ID"));
		arr.push($tr.attr("TIME_NAME"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$('#timeVal'));
	}
	if($checkbox.attr("id").indexOf("mainModellb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("MODELS_ID"));
		arr.push($tr.attr("MODELS_NAME"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#modelVals"));
	}
	if($checkbox.attr("id").indexOf("mainVINlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("VEHICLE_ID"));
		arr.push($tr.attr("SVIN"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#VINVals"));
	}
	if($checkbox.attr("id").indexOf("tab-partList")==0){
		var $tr = $(checkbox).parent().parent().parent();
        var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
        var s = "";
        if($tr.find("td").eq(4).find("input:first").size()>0)
            s = $tr.find("td").eq(4).find("input:first").val();
        else
            s = $tr.find("td").eq(4).text();
        if (!s || !reg.test(s)){
            alertMsg.warn("请输入正确的数量!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push(s);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$("#partVals"));
        //设置input框显示或文本只读
        if($checkbox.is(":checked"))
            $tr.find("td").eq(4).html("<div>"+s+"</div>");
        else
        {
            $tr.find("td").eq(4).html("<div><input type='text' name='QUANTITY' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
        }
	}
}
function doDeleteAtta(rowobj)
{
	$rowAtta = $(rowobj);
	var url = diaSaveAction + "/attaDelete.ajax?fjid="+$(rowobj).attr("FJID");
	sendPost(url,"","",deleteAttaCallBack,"true");
}
//删除回调方法
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
					<li><a href="javascript:void(0)"><span>活动信息</span></a></li>
					<li id="vinxxH"><a href="javascript:void(0)"><span>VIN信息</span></a></li>
					<li id="cxxxH"><a href="javascript:void(0)"><span>车型信息</span></a></li>
					<li id="clxxH"><a href="javascript:void(0)"><span>车龄信息</span></a></li>
					<li id="fjxxH"><a href="javascript:void(0)"><span>附件信息</span></a></li>
					<li id="gsxxH"><a href="javascript:void(0)"><span>工时信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
		<!-- 服务活动TAB -->
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-serviceActivityInfo" class="editForm" >
					<input type="hidden" id="serviceActivityInfoId" name="serviceActivityInfoId" datasource="ACTIVITY_ID" />
					<div align="left">
					<fieldset>
					<table class="editTable" id="serviceActivityInfo">
					    <tr>
							<td id="hddmT" style="display:none"><label>活动代码：</label></td>
							<td id="hddmI" style="display:none"><input type="text" id="DI_HDDM" name="DI_HDDM" datasource="ACTIVITY_CODE"  datatype="1,is_digit_letter,30" readonly="readonly"/></td>
							<td><label>活动名称：</label></td>
							<td colspan="5"><input type="text" style="width:500px" id="DI_HDMC" name="DI_HDMC" datasource="ACTIVITY_NAME" datatype="0,is_null,300" /></td>
						</tr>
						<tr>	
							<td><label>活动类别：</label></td>
							<td><select  type="text" id="DI_HDLB" name="DI_HDLB" kind="dic" class="combox" datasource="ACTIVITY_TYPE" src="HDLB"  datatype="0,is_null,8" value="" >
									<option value="<%=DicConstant.HDLB_01%>" selected>整改</option>
								</select>
							</td>
							<td><label>处理方式：</label></td>
							<td><select type="text" id="DI_CLFS" name="DI_CLFS"  kind="dic" datasource="MANAGE_TYPE" class="combox" src="CLFS" datatype="1,is_null,8" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
							
						</tr>
						<tr>
							<td><label>开始里程(公里)：</label></td>
							<td><input type="text" id="DI_KSLC" name="DI_XSLC" datasource="BEGIN_MILEAGE" datatype="1,is_double,10"/></td>
						    <td><label>结束里程(公里)：</label></td>
							<td><input type="text" id="DI_JSLC" name="DI_XSLC" datasource="END_MILEAGE" datatype="1,is_double,10"/></td>
						</tr>
						<tr>
						    <td><label>活动日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ckrq" group="in-ckrq,in-jsrq"  style="width:75px;" name="in-ckrq" datasource="START_DATE" datatype="1,is_date,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" group="in-ckrq,in-jsrq"  style="width:75px;margin-left:-30px;" name="in-jsrq" datasource="END_DATE" datatype="1,is_date,30" onclick="WdatePicker()" />
					   		 </td>
					 	</tr>
						<tr>
							<td><label>是否追偿：</label></td>
							<td><select  type="text" id="DI_SFSP" name="DI_SFSP" kind="dic" datasource="IF_CLAIM" class="combox" src="SF"  datatype="1,is_null,6" value="" >
									<option value="<%=DicConstant.SF_02%>" selected>否</option>
								</select>
							</td>
							<td id="la_gdfy"><label>是否固定费用：</label></td>
							<td id="DI_SFGDFY"><select  type="text" id="SFGDFY" name="SFGDFY" datasource="IF_FIXCOSTS" kind="dic" class="combox" src="SF"  datatype="1,is_null,6" value="" >
									<option value="<%=DicConstant.SF_02%>" >否</option>
								</select>
							</td>
							<td id="la_hdfy"><label>活动费用(元)：</label></td>
							<td><input type="text" id="DI_HDFY" name="DI_HDFY" datasource="ACTIVITY_COSTS" datatype="1,is_money,100"/></td>
						</tr>
						<tr>
							<td id="la_sfrgsp"><label>是否需人工审批：</label></td>
							<td id="DI_SFRGSP"><select  type="text" id="SFRGSP" name="SFRGSP" datasource="IF_PERSON_CHECK" kind="dic" class="combox" src="SF"  datatype="1,is_null,6" value="" >
										<option value="<%=DicConstant.SF_02%>" >否</option>
									</select>
							</td>
							<td id="la_rzlx"><label>入账类型：</label></td>
							<td id="DI_rzlx"><select  type="text" id="rzlx" name="rzlx" datasource="IN_ACCOUNT_TYPE" kind="dic" class="combox" src="RZLX"  datatype="1,is_null,6" value="" >
									<option value=-1>--</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>解决方案：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="BZ" name="BZ" datasource="SOLUTION" style="width:100%" datatype="1,is_null,1000"></textarea></td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="BZ" name="BZ" datasource="REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			<!-- 服务活动VIN信息TAB -->
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdvinfy" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="fwhdvinfyTable">
								<tr>
									<td><label>VIN：</label></td>
									<td><input type="text" id="vin" name="vin" datasource="MV.VIN" datatype="1,is_null,17" value="" operation="like" /></td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchVin">查&nbsp;&nbsp;询</button></div></div></li>
								    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre5" name="btn-pre">上一步</button></div></div></li>
								    <li id="dia-nextHVin"><div class="button"><div class="buttonContent"><button type="button" id="dia-nextVin" name="btn-next">下一步</button></div></div></li>
								    <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
				</div>
				<div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="addVin" title=""><span>批量新增</span></a></li>
						<li class="line">line</li>
						<li><a class="delete" href="javascript:void(0);" id="deleteVin" title="确定要删除吗?"><span>批量删除</span></a></li>
						<li class="line">line</li>
						<li><a class="icon" href="###" id="download" ><span>下载导入模板</span></a></li>
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="dia-imp" title="确定要导入吗?"><span>导入数据</span></a></li>
					</ul>
				</div>
				<div id="fwhdxm">
					<table width="100%" id="fwhdvinlb" name="fwhdvinlb" multivals="VINDeleteVal" ref="fwhdxm"  style="display: none"  refQuery="fwhdxmqtfyTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="VIN" >SVIN</th>
								<th fieldname="ENGINE_NO" >发动机型号</th>
								<th fieldname="BUY_DATE" >销售日期</th>
								<th fieldname="FACTORY_DATE" >生产日期</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="VINDeleteVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val3" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
						</tr>
					</table>
				</div>
			</div>
			<!-- 服务活动车型TAB -->
			<div class="page">
			<div class="pageHeader">
				<form id="fm-fwhdcx" method="post">
					<div class="searchBar" align="left">
						<table class="searchContent" id="fwhdcxTable">
							<tr>
								<td><label>车型代码：</label></td>
								<td><input type="text" id="CXDM" name="CXDM" datasource="MODELS_CODE" datatype="1,is_null,100"  value="" operation="like" /></td>
								<td><label>车型名称：</label></td>
								<td><input type="text" id="CXMC" name="CXMC" datasource="MODELS_NAME" datatype="1,is_null,100" value="" operation="like"  /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchServiceModel">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
								<li id="dia-nextH2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="panelBar">
				<ul class="toolBar">
					<li class="line">line</li>
					<li><a class="add" href="javascript:void(0);" id="addModel" title=""><span>批量新增</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" href="javascript:void(0);" id="deleteModel" title="确定要删除吗?"><span>批量删除</span></a></li>
				</ul>
			</div>
			<div class="pageContent">
				<div id="fwhdcx">
					<table style="display:none;width:100%;" id="fwhdcxlb" name="fwhdcxlb" moltivals="modelDeletVal" ref="fwhdcx" refQuery="fwhdcxTable" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="MODELS_CODE" >车型代码</th>
								<th fieldname="MODELS_NAME" >车型名称</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="modelDeletVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
						</tr>
					</table>
				</div>
			</div>
			</div>
		<!-- 服务活动车龄TAB -->
			<div class="page">
			<div class="pageContent">
				<div align="left">
				    <form method="post" id="dia-fm-item"></form>
				    <fieldset>
					<div id="fwhdcl">
						<table width="100%" id="fwhdcllb" name="fwhdcllb" edit="true" style="display:none"  ref="fwhdcl">
							<thead>
								<tr>
									<th type="single" name="XH" style="display:none"></th>
									<th fieldname="START_DATE" colWidth="80" ftype="input" fonclick="WdatePicker()" fdatasource="START_DATE">起始日期</th>
									<th fieldname="END_DATE" colWidth="80" ftype="input" fonclick="WdatePicker()" fdatasource="END_DATE">结束日期</th>
									<th fieldname="VEHAGE_TYPE" freadonly="true" colWidth="80" fsrc="RQLX">日期类型</th>
									<th colwidth="85" type="link" title="[编辑]"  action="doDiaClSave">操作</th>
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
					<li id="dia-nextH3"><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</div>
		<!-- 服务活动附件TAB -->
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdfj" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="fm-fwhdfjTable">
							</table>
						</div>
					</form>
				</div>
				<div class="pageContent">  
					<form method="post" id="dia_fm_atta" class="editForm" >
					</form>
					<div align="left">
	              	<fieldset>
					<legend align="right"><a onclick="onTitleClick('dia-spdfj')">&nbsp;已传附件列表&gt;&gt;</a></legend>
						<div id="dia-files">
							<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
								<thead>
									<tr>
										<th type="single" name="XH" style="display:none"></th>
										<th fieldname="FJMC" >附件名称</th>
										<th fieldname="CJR" >上传人</th>
										<th fieldname="CJSJ" >上传时间</th>
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
						<li><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上&nbsp;&nbsp;传</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
						<li id="dia-nextH4"><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		<!-- 服务活动工时TAB -->
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdxmgs" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="fwhdxmgsTable">
								<tr>
									<td><label>工时代码：</label></td>
									<td><input type="text" id="GSDM" name="GSDM" datasource="TASK_TIME_CODE" datatype="1,is_null,100"  value="" operation="like" /></td>
									<td><label>工时名称：</label></td>
									<td><input type="text" id="GSMC" name="GSMC" datasource="TASK_TIME_NAME" datatype="1,is_null,100" value="" operation="like" /></td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchWorkH">查&nbsp;&nbsp;询</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
									<li id="dia-nextH5"><div class="button"><div class="buttonContent"><button type="button" id="dia-next4" name="btn-next">下一步</button></div></div></li>
									<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
								</ul>
							</div>
						</div>
					</form>
				</div>
				<div class="panelBar">
					<ul class="toolBar">
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="addWorkH" title=""><span>批量新增</span></a></li>
						<li class="line">line</li>
						<li><a class="delete" href="javascript:void(0);" id="deleteWorkH" title="确定要删除吗?"><span>批量删除</span></a></li>
					</ul>
				</div>
				<div class="pageContent">
					<div id="fwhdxmgs">
						<table width="100%" id="fwhdxmgslb" name="fwhdxmgslb" ref="fwhdxmgs" multivals="timeDeletVal" style="display: none" refQuery="fwhdxmgsTable" >
							<thead>
								<tr>
									<th type="multi" name="XH" unique="RELATION_ID"></th>
									<th fieldname="TASK_TIME_CODE" >工时代码</th>
									<th fieldname="TASK_TIME_NAME" >工时名称</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div id="timeDeletVal">
						<table style="display:none">
							<tr>
								<td><textarea id="val1" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		<!-- 服务活动配件TAB -->
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdxmpj" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="fm-fwhdxmpjTable">
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
									<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre5" name="btn-pre">上一步</button></div></div></li>
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
				<div id="fwhdxmpj">
					<table width="100%" id="fwhdxmpjlb" name="fwhdxmpjlb" multivals="partDeleteVal" ref="fwhdxmpj"  style="display: none"  refQuery="fm-fwhdxmpjTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="QUANTITY" >数量</th>
								<th fieldname="AMOUNT" refer="amountFormat" align="right">金额(元)</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="partDeleteVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val2" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
						</tr>
					</table>
				</div>
				</div>
			</div>
		</div>
	</div>
	<form method="post" id="fm_Cl" style="display:">
		<input type="hidden" id="relationId" datasource="RELATION_ID"/>
		<input type="hidden" id="startDate" datasource="START_DATE"/>
		<input type="hidden" id="endDate" datasource="END_DATE"/>
	</form>
	<form id="dialog-fm-download"style="display:none"></form>
</div>
</body>
</html>