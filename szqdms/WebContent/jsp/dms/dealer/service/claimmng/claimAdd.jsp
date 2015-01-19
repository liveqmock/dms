<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.Globals"%>
<%@page import="java.util.Calendar"%>
<%
	String action = request.getParameter("action");
	String rejectFlag = request.getParameter("rejectFlag");
    UserParaConfigureVO userVo1= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("200101");
    UserParaConfigureVO userVo2= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("200102");
    UserParaConfigureVO userVo3= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("200201");
    UserParaConfigureVO userVo4= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("201701");// 驳回单子最多几天报单
    UserParaConfigureVO userVo5= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("201801");// 单车材料费
    String saftMoney="";
    String saleMoney="";
    String dayStart="";
    String dayEnd="";
    String rejectMaxdate="";
    String claimMaxCosts="";
    if(userVo1 != null){
    	saftMoney=userVo1.getParavalue1();//安全检查费用	
    } 
    if(userVo2 != null){
    	saleMoney=userVo2.getParavalue1();//售前培训费用
    }
    
    if(userVo3 != null){
    	dayStart=userVo3.getParavalue1();//白天定义的起始时间
    	dayEnd=userVo3.getParavalue2();//白天定义的结束时间
    }
    if(userVo4 != null){
    	rejectMaxdate=userVo4.getParavalue1();//最大几天报单
    }else {
    	rejectMaxdate="2";
    }
    if(userVo5 != null){
    	claimMaxCosts=userVo5.getParavalue1();//单车最大材料费
    }
    Calendar ca = Calendar.getInstance();
    int year = ca.get(Calendar.YEAR);
    int month = ca.get(Calendar.MONTH) + 1;
    int day = ca.get(Calendar.DATE);
    String m = month >=10?String.valueOf(month):"0"+month;
    String curDate = ca.getTime().toLocaleString();
	if (action == null)
		action = "1";
	if (rejectFlag == null)
		rejectFlag = "1";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔维修信息</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var action = "<%=action%>";
var rejectFlag = "<%=rejectFlag%>";//索赔单提报编辑页固定值
var $checkHisTrue=true;//审核轨迹
var flagGz=false;//控制是否可以添加故障，1不可以添加，2是可以添加
var diaOperateAction = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
var $fileTrue=true,patternTrue=true;
var ifOut;
$(function() 
{
	//设置高度,为了出现竖向的滚动条
	$("#dia-div-claimEdit").height(document.documentElement.clientHeight-30);
	$("#dia-div-wcxxlb").height(document.documentElement.clientHeight-30);
	//设置提报按钮为不可点击
	//$("#dia-hid-report").hide();
	//设置tabs的上一步事件
	$("button[name='btn-pre']").bind("click",function(event) 
	{
		$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
	});
	//设置tabs的下一步事件
	$("button[name='btn-next']").bind("click", function(event) 
	{
		var $tabs = $("#dia-tabs");
		switch (parseInt($tabs.attr("currentIndex"))) 
		{
			case 0:
				if(patternTrue)
				{
					searchClaimPattern();
					searchClaimMaxCosts();//单车材料费
				}
				patternTrue=false;
				break;
			case 1:
				if($fileTrue){
					fjUpCallBack("");
				}
				$fileTrue=false;
				break;
			case 2:
				if($checkHisTrue){
					var $f = $("#dia_fm_his");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var fileSearchUrl1 =diaOperateAction+"/hisCheckSearch.ajax?claimId="+$("#dia-claimId").val(); 
					doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia_hislb");
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
	//设置关闭按钮事件
	$("button.close").click(function() 
	{
		parent.$.pdialog.closeCurrent();
		return false;
	});
	//上传附件
	$("#addAtt").bind("click",function(){
		var workId=$("#dia-workId").val();
		$.filestore.open(workId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	//验证VIN
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
		var options = {max : true,mask : true,mixable : true,minable : true,resizable : true,drawable : true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/vinCheck.jsp?vinVal='"+vinVal+"'&engineVal='"+engineVal+"'","vinCheck", "维修信息维护-校验车辆", options);
	});
	//重新填写
	$("#dia-recheckvin").bind("click",function(){
		alertMsg.confirm("清空车辆信息，确认重新填写?",{okCall:doConOk1,cancelCall:doConOk2});
	});
	//获取父页面中的选中记录
		var rowdata=parent.$row.attr("rowdata");
		setEditValue("dia-fm-wxdawh",rowdata);
		//将选中的记录转化为XML
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		/*//索赔单牌照号
		var licensePlate=getNodeValue(objXML, "LICENSE_PLATE1", 0);
		if(licensePlate)$("#dia-license_plate").val(licensePlate);
		else $("#dia-license_plate").val("");*/
		//获取工单ID
		var workId=getNodeValue(objXML, "WORK_ID", 0);
		$("#dia-workId").val(workId);
		//提报次数
		var applyCount=getNodeValue(objXML, "APPLY_COUNT", 0);
		$("#diaApplyCount").val(applyCount);
		//驳回时间
		var rejectDate=getAttribValue(objXML, "REJECT_DATE","sv", 0);
		$("#diaRejectDate").val(rejectDate);
		//获取工单号
		var workNo=getNodeValue(objXML, "WORK_NO", 0);
		$("#dia-workNo").val(workNo);
		//服务店超单日期
		var deaOverDays=getNodeValue(objXML, "DEAR_OVER_DAYS", 0);
		$("#deaOverDays").val(deaOverDays);
		//故障来源
		var faultForm=getNodeValue(objXML, "FAULT_FROM_CODE", 0);
		$("#dia-in-gzxxly").attr("code",faultForm);
		/* //故障分析
		var faultAnlysie=getNodeValue(objXML, "FAULT_ANLYSIE_CODE", 0);
		$("#dia-in-hsj0").attr("code",faultAnlysie); */
		
		//GPS有效里程
		 var yxlc=getNodeValue(objXML, "YXLC", 0);
		//获取是否外出
		 ifOut=getNodeValue(objXML, "IF_OUT", 0);
		//获取出发时间
		var goDate=getAttribValue(objXML, "GO_DATE","sv", 0);
		//获取到达时间
		var arriveDate=getAttribValue(objXML, "ARRIVE_DATE","sv", 0);
		//获取离开时间
		var completeDate=getAttribValue(objXML, "COMPLETE_DATE","sv", 0);
		//服务车牌号
		var vehicleNo=getNodeValue(objXML, "VEHICLE_NO", 0);
		//保修人
		var applyUser=getNodeValue(objXML, "APPLY_USER", 0);
		//保修人电话
		var applyMobil=getNodeValue(objXML, "APPLY_MOBIL", 0);
		if(ifOut==100101)
		{
			
			if(goDate){
				$("#dia-in-cfsj").val(goDate);
				var goDateHour=goDate.substr(10,6);
				if(CompareDate(goDateHour,"<%=dayStart%>") && CompareDate("<%=dayEnd%>",goDateHour))//白天
				{
					$("#dia-in-wcsj").val("白天");
					$("#dia-in-wcsjdm").val("302301");
					
				}else//夜间
				{
					$("#dia-in-wcsj").val("夜间");
					$("#dia-in-wcsjdm").val("302302");
				}
			} 
			//工单转索赔单
			if(rejectFlag==1){
				if(arriveDate) $("#dia-in-ddsj").val(arriveDate);
				if(completeDate) $("#dia-in-lksj").val(completeDate);
				if(yxlc) $("#dia-gpsYxlc").val(yxlc);
				if(vehicleNo) $("#dia-in-fwcph").val(vehicleNo);
				if(goDate && arriveDate) $("#dia-in-onWayDay").val(Computation(arriveDate,goDate));
				if(goDate && completeDate) $("#dia-in-travelDay").val(Computation(completeDate,goDate));
			}
			//查询出服务站外出费用集合
			var searchDealerOutUrl = diaOperateAction + "/searchDealerOut.ajax";
			sendPost(searchDealerOutUrl,"","",diaSearchDealerOutBack,"false");
		}
	//当action=1 时表示新增页面
	if(action==1)
	{
		
		$("#dia-claimStatus").val("未提报");
		if(applyUser) $("#dia-in-bxr").val(applyUser);
		if(applyMobil) $("#dia-in-bxrdh").val(applyMobil);
		//生成索赔单基本信息（索赔单号,服务站ID,工单ID）
		var addClaimMaintisUrl = diaOperateAction + "/addClaimMaintis.ajax?workId="+workId;
		sendPost(addClaimMaintisUrl,"","",diaAddClaimMaintisBack,"false");
		//生成索赔单基本信息 结束
	}else
	{
		setEditValue("dia-fm-claimMaintis",rowdata);
		var seType=getNodeValue(objXML, "SE_TYPE", 0);
		setServiceType(ifOut,seType);
		$("#dia-claimId").val(parent.workClaimId);
		if(ifOut==100101)
		{
			var serchClaimOutUrl = diaOperateAction + "/searchClaimOut.ajax?claimId="+$("#dia-claimId").val();
			sendPost(serchClaimOutUrl,"","",serchClaimOutBack,"false");
		}
		setClaimType($("#dia-in-splx").attr("code"));
		//判断修改页面是否保存索赔单类型
		if($("#dia-in-splx").attr("code")!=''){
			flagGz=true;
			$("#dia-vin").attr("readOnly",true);
			$("#dia-vin").addClass("readonly");
			$("#dia-engine_no").attr("readOnly",true);
			$("#dia-engine_no").addClass("readonly");
			$("#dia-in-splx").attr("readOnly",true);
			$("#dia-in-splx").addClass("readonly");
			$("#dia-in-splx").attr("src","");
		}
		//索赔单提报
		if(rejectFlag==2){
			$("#auditL").show();
			$("#auditH").show();
		}
	}
	$("#dia-province_Code").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
	$("#dia-outBuySearch").bind("click",function(){
		var claimId = $("#dia-claimId").val();
		var attOptions = {max:false,width:700,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/dealer/service/claimmng/claimOutBuyDtl.jsp?claimId="+claimId, "索赔外采件查询", "索赔外采件查询", attOptions);
	});
});
function serchClaimOutBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var outTime =getNodeText(rows[0].getElementsByTagName("OUT_TIMES").item(0));
			var outDateTypeName =getNodeText(rows[0].getElementsByTagName("OUTDATE_TYPE_NAME").item(0));
			var tmp="";
			if(outDateTypeName==302301){
				tmp="白天";
			}else{
				tmp="夜间";
			}
			if(outTime==301202)
			{
				$("#ecwcfy").show();
			}
			setEditValue("dia-fm-claimOutInfo",rows[0]);
			if($("#dia-in-wcsj").val()==''){
				$("#dia-in-wcsj").val(tmp);
			}
			$("#dia-in-sfdcwc").attr("src","");
			$("#dia-in-sfdcwc").attr("readonly",true);
			$("#dia-in-sfdcwc").addClass("readonly");
			$("#diaSaveTabs").attr("confirm","");
		}	
	}catch(e) 
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
function setServiceType(ifOut,seType)
{
	if(ifOut==100101)
	{
		/* $("#dia-in-fwlx").attr("src","E#301102=救援:301103=跟踪");
		$("#dia-in-fwlx").find("option").val("301102");
		$("#dia-in-fwlx").find("option").text("救援");
		$("#dia-in-fwlx").find("option").attr("selected",true);
		if($("#combox_dia-in-fwlx"))
		{
			 if(seType&& seType==301102)
			 {
				 $("#combox_dia-in-fwlx").find("a").eq(0).text("救援");
				 $("#combox_dia-in-fwlx").find("a").eq(0).val("301102");
			 }else 
			 {
				 $("#combox_dia-in-fwlx").find("a").eq(0).text("跟踪");
				 $("#combox_dia-in-fwlx").find("a").eq(0).val("301103");
			 }
		} */
		//$("#dia-in-fwlx").attr("src","FWLX");
		$("#dia-in-fwlx").attr("filtercode", "301102|301103");
		if(!seType)
		{
			$("#dia-in-fwlx").attr("code","301102");
			$("#dia-in-fwlx").val("救援");
			$("#op_combox_dia-in-fwlx").find(".selected").removeClass("selected");
			$("#op_combox_dia-in-fwlx").find("a[value='301102']").addClass("selected");
			$("a[name='dia-in-fwlx']").text("救援");
		}
	}else
	{
		/* $("#dia-in-fwlx").attr("src","E#301101=送修");
		$("#dia-in-fwlx").find("option").val("301101");
		$("#dia-in-fwlx").find("option").text("送修");
		$("#dia-in-fwlx").find("option").attr("selected",true);
		if($("#combox_dia-in-fwlx"))
		{
			$("#combox_dia-in-fwlx").find("a").eq(0).text("送修");
			$("#combox_dia-in-fwlx").find("a").eq(0).val("301101");
		} */
		$("#dia-in-fwlx").attr("filtercode", "301101");
		$("#dia-in-fwlx").attr("code","301101");
		$("#dia-in-fwlx").val("送修");
		$("#op_combox_dia-in-fwlx").find(".selected").removeClass("selected");
		$("#op_combox_dia-in-fwlx").find("a[value='301101']").addClass("selected");
		$("a[name='dia-in-fwlx']").text("送修");
		
		$("#outL").hide();//如果没有外出，将保存按钮隐藏
	}
}
//比较时间大小
function CompareDate(t1,t2)
{
    var date = new Date();
    var a = t1.split(":");
    var b = t2.split(":");
    return date.setHours(a[0],a[1]) > date.setHours(b[0],b[1]);
}
//查询服务站外出费用回调方法
var outRows = [];
function diaSearchDealerOutBack(res)
{
	try
	{
		// outRows = res.getElementsByTagName("ROW");
		 var arrayTemp = res.getElementsByTagName("ROW");
		 if(arrayTemp != null && arrayTemp.length >0){
			 $(arrayTemp).each(function(index, obj){
				outRows[index] = obj;
			 });
		 }
		 $("#dia-div-wcxx").show();
		 //查询外出有效里程(通过GPS 获取有效里程) 在保存外出信息时取gps里程
		// var claimOutMileageUrl = diaOperateAction + "/searchClaimOutMileage.ajax?workNo="+$("#dia-workNo").val();
		// sendPost(claimOutMileageUrl,"","",claimOutMileageBack,"false");
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
//获取有效里程
/* 
function claimOutMileageBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var mileage =getNodeText(rows[0].getElementsByTagName("MILEAGE").item(0));
			if(mileage==0){
				$("#dia-in-yxlc").val("");
			}else{
				$("#dia-in-yxlc").val(mileage);
			}
		}	
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
} */
//生成索赔单基本信息 回调方法
function diaAddClaimMaintisBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			
			//var claimNo =getNodeText(rows[0].getElementsByTagName("CLAIM_NO").item(0));
			var claimId =getNodeText(rows[0].getElementsByTagName("CLAIM_ID").item(0));
			//$("#dia-claimNo").val(claimNo);
			$("#dia-claimId").val(claimId);
			setEditValue("dia-fm-claimMaintis",rows[0]);
			//设置服务类型选择内容
			setServiceType(ifOut);
			
		}	
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}
//计算外出费用
function diaCountOutCost()
{
	
	//从XML文件中获取费用单价 
	var wccs=$("#dia-in-wccs").val();//外出次数 
	var wcfs=$("#dia-in-wcfs").attr("code");//外出方式(一次)
	var yxlc=$("#dia-in-yxlc").val()==""?0:$("#dia-in-yxlc").val();//有效里程
	var wcrs=$("#dia-in-wcrs").val()==""?1:$("#dia-in-wcrs").val();//外出人数
	var ztbzts=$("#dia-in-onWayDay").val();//在途补助天数
	var clts=$("#dia-in-travelDay").val();//差旅补助天数
	var sjfw=$("#dia-in-wcsjdm").val();//时间范围
	var qtf=$("#dia-in-qtf").val()==""?0:$("#dia-in-qtf").val();//其他费
	var fwlx =$("#dia-in-fwlx").attr("code");
	var ztbz,fucf,clf,ztbzDj=0,fucfDj=0,clfDj=0,ecdj=0,ycfyh=0,ecfyh=0;
	if(outRows && outRows.length > 0)
	{
		for(var i=0;i<outRows.length;i++)
		{
			var objXML;
			if (typeof(outRows[i]) == "object") objXML = outRows[i];
			else objXML = $.parseXML(outRows[i]);
			var costs=getNodeValue(objXML, "COST", 0)==""?0:getNodeValue(objXML, "COST", 0);//单价
			var costsType=getNodeValue(objXML, "COSTS_TYPE", 0);//费用类型
			var travelTime=getNodeValue(objXML, "TRAVEL_TIMES", 0)==""?"301201,301202":getNodeValue(objXML, "TRAVEL_TIMES", 0);//外出次数
			var travelDate=getNodeValue(objXML, "TRAVEL_DATE", 0)==""?"302301,302302":getNodeValue(objXML, "TRAVEL_DATE", 0);//外出时间范围
			var vehicleType=getNodeValue(objXML, "VEHICLE_TYPE", 0)==""?"301301,301302":getNodeValue(objXML, "VEHICLE_TYPE", 0);//备车类型
			var startMiles=getNodeValue(objXML, "START_MILES", 0)==""?0:getNodeValue(objXML, "START_MILES", 0);//开始里程
			var endMiles=getNodeValue(objXML, "END_MILES", 0)==""?999999999:getNodeValue(objXML, "END_MILES", 0);//结束里程
			//一次外出的费用单价
			if(travelTime.indexOf("301201")>=0 && travelDate.indexOf(sjfw)>=0 && vehicleType.indexOf(wcfs)>=0 && ((yxlc-startMiles)>=0) && ((endMiles-yxlc)>=0))
			{
				if(costsType=="305101")
				{
					ztbzDj=costs;
				}else if(costsType=="305102")
				{
					fucfDj=costs;
				}else if(costsType=="305103")
				{
					clfDj=costs;
				}
			}
			if(wccs=="301202")
			{
				if(travelTime.indexOf("301202")>=0 && travelDate.indexOf(sjfw)>=0 && vehicleType.indexOf(wcfs)>=0 && ((yxlc-startMiles)>=0) && ((endMiles-yxlc)>=0))
				{
					if(costsType=="305102"){
						ecdj=costs;
					}
				}
			}
		}
	}
	//计算在途补助 人数*单价*天数
	ztbz=parseInt(wcrs,10)*parseInt(ztbzts,10)*parseInt(ztbzDj,10);
	$("#dia-in-ztcb").val(ztbz);
	//计算服务车费 自备车：外出里程*里程单价;   非自备车：应急救援0.5元/公里*人数*外出里程；
	if(wcfs==301301)
	{
		fucf=parseFloat(yxlc) * parseFloat(fucfDj);
	}else
	{
		fucf=parseFloat(yxlc) * parseFloat(fucfDj) * parseInt(wcrs,10);
	}
	$("#dia-in-fwcf").val(fucf);
	//计算差旅费 外出人数*外出天数*费用标准
	clf=parseInt(clts,10)*parseInt(wcrs,10)*parseInt(clfDj,10);
	if(fwlx == 301102){
		
		clf=0;
	}
	/* if(fwlx == 301103){
		
	} */
	$("#dia-in-clf").val(clf);
	//一次费用和
	ycfyh=parseInt(ztbz,10)+parseFloat(fucf,10)+parseInt(clf,10)+parseInt(qtf,10);
	//计算二次服务车费
	ecfyh=parseFloat(ecdj,10)*parseInt(yxlc,10);
	$("#dia-in-ycwczfy").val(ycfyh);
	$("#dia-in-fwcfec").val(ecfyh);
	$("#dia-in-wczfy").val(parseFloat(ycfyh,10)+parseFloat(ecfyh,10));
}
//计算天数 
//修改日期 2014.11.27 将8小时为一天改成24小时为一天
function Computation(sDate1, sDate2)
{  
	var aDate, oDate1, oDate2, iDays, aTime, timeArray = [] ;
	timeArray = sDate1.split(" ");
	aDate = (timeArray[0]).split("-");
	aTime = (timeArray[1]).split(":");
	oDate1 = new Date(aDate[0], aDate[1], aDate[2], aTime[0], aTime[1], 0);       
	timeArray = sDate2.split(" ");
	aDate = (timeArray[0]).split("-");
	aTime = (timeArray[1]).split(":"); 
	oDate2 = new Date(aDate[0], aDate[1], aDate[2], aTime[0], aTime[1], 0);
	//8小时进制
	iDays =  Math.ceil(Math.abs(oDate1.getTime() - oDate2.getTime()) / 1000 / 60 / 60 /24);    
	return iDays;  
	/* 
	var aDate, oDate1, oDate2, iDays ;   
	aDate = sDate1.split("-");    
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);       
	aDate = sDate2.split("-") ;  
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
	//8小时进制
	iDays =  Math.ceil(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /8);        
	return iDays ;   */
}
function Computation24(sDate1, sDate2)
{  
	var aDate, oDate1, oDate2, iDays ;   
 	aDate = sDate1.split("-");    
  	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);       
  	aDate = sDate2.split("-") ;  
  	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
  	iDays =  Math.ceil((oDate1 - oDate2) / 1000 / 60 / 60 /24);        
  	return iDays ; 
}
//判断dicTree发生变化后设置页面其他元素
function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="dia-in-fwlx")
	{
		if($("#"+id).attr("code") != "301101")
		{
			$("#dia-div-wcxx").show();
			diaCountOutCost();
		}else
		{
			$("#dia-div-wcxx").hide();
		}
	}
	if(id=="dia-in-sfdcwc")
	{
		if($("#"+id).attr("code") == "100101")
		{
			$("#ecwcfy").show();
			$("#dia-in-wccs").val("301202");
		}else
		{
			$("#dia-in-wccs").val("301201");
			$("#dia-in-fwcfec").val(0);
			$("#ecwcfy").hide();
		}
		diaCountOutCost();
	}
	if (id == "dia-in-splx") 
	{
		setClaimType($("#" + id).attr("code"));
	}
	if(id == "dia-province_Code")
	{
		$("#dia-city_Code").val("");
		$("#dia-city_Code").attr("code","");
		$("#dia-county_Code").val("");
		$("#dia-county_Code").attr("code","");
		var privinceCode = $("#"+id).attr("code").substr(0,2);
		$("#dia-city_Code").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+privinceCode+"%' AND LX='30' ");
		$("#dia-city_Code").attr("isreload","true");
		//$("#dia-city_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
	}
	if(id == "dia-city_Code")
	{
		$("#dia-county_Code").val("");
		$("#dia-county_Code").attr("code","");
		var cityCode = $("#"+id).attr("code").substr(0,4);
		$("#dia-county_Code").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+cityCode+"%' AND LX='40' ");
		$("#dia-county_Code").attr("isreload","true");
		$("#dia-county_Code").attr("dicwidth","300");
		//$("#dia-county_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
	}
	/* if(id == "dia-province_Code")
	{
		$("#dia-city_Code").attr("src","XZQH");
		$("#dia-city_Code").attr("isreload","true");
		$("#dia-city_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
	}
	if(id == "dia-city_Code")
	{
		$("#dia-county_Code").attr("src","XZQH");
		$("#dia-county_Code").attr("isreload","true");
		$("#dia-county_Code").attr("dicwidth","300");
		$("#dia-county_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
	} */
	return true;
}
//根据索赔单类型设置显示项目
function setClaimType(value)
{
	if (value != "301403") 
	{
		$("#xzfwhd").hide();
	} else {
		$("#xzfwhd").show();
	}
	//售前维修、售前培训检查、安全检查（不用限制）
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
	if (value == "301406" ) 
	{
		$("#xzsqpx").show();
		$("#dia-in-sqpx").val('<%=saleMoney%>');
	} else {
		$("#xzsqpx").hide();
	}
	if(value == "301407")
	{
		$("#xzaqjc").show();
		$("#dia-in-aqjc").val(<%=saftMoney%>);
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
	 if (value != "301405") 
	{
		$("#xzdb").hide();
	} else {
		$("#xzdb").show();
	} 
	//将原来的三包急件索赔去掉
	/* if (value != "301408") 
	{
		$("#xzsbjjdd").hide();
	} else {
		$("#xzsbjjdd").show();
	} */
	if (value!= "301408") 
	{
		$("#xzysq").hide();
	} else {
		$("#xzysq").show();
	}
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
		$("#dia-vin").val($("#dia-di-vin").val());//vin
		$("#modelsId").val($("#dia-di-modelsId").val());//车型ID
		$("#dia-models_code").val($("#dia-di-models_code").val());//车型代码
		$("#dia-di-engine_type").val($("#dia-di-engine_type").val());//发动机型号
		$("#dia-user_type").val($("#dia-di-user_type").val());//用户类型
		$("#dia-user_typeId").val($("#dia-di-user-typeId").val());//用户类型
		$("#dia-vehicle_use").val($("#dia-di-vehicle_use").val());//车辆用途
		$("#dia-vehicle_useId").val($("#dia-di-vehicle_useId").val());//车辆用途
		$("#dia-drive_form").val($("#dia-di-drive_form").val());//驱动形式
		$("#dia-buy_date").val($("#dia-di-buy_date").val());//购车日期
		//$("#dia-mileage").val($("#dia-di-mileage").val());//行驶里程
		$("#dia-in-xzdbcs").val($("#dia-di-gTimes").val());//定保次数
		$("#dia-guarantee_no").val($("#dia-di-guarantee_no").val());//保修卡
		$("#dia-maintenance_date").val($("#dia-di-maintenance_date").val());//首保日期
		$("#dia-license_plate").val($("#dia-di-license_plate").val());//车牌号
		$("#dia-user_name").val($("#dia-di-user_name").val());//用户名称
		$("#dia-user_no").val($("#dia-di-user_no").val());//身份证
		$("#dia-link_man").val($("#dia-di-link_man").val());//联系人
		$("#dia-phone").val($("#dia-di-phone").val());//电话
		$("#dia-user_address").text($("#dia-user_address").text());//地址
		if($("#dia-di-maintenanceCost").val())//首保费用
		{
			$("#dia-in-sbfy").val($("#dia-di-maintenanceCost").val());//首保费用
		}
	}
	if(flag==2){
		vehEmpty();
		alertMsg.info("VIN与发动机号不存在.");
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
	$("#dia-di-engine_type").val("");//发动机型号
	$("#dia-user_type").val("");//用户类型
	$("#dia-user_typeId").val("");//用户类型
	$("#dia-vehicle_use").val("");//车辆用途
	$("#dia-vehicle_useId").val("");//车辆用途
	$("#dia-drive_form").val("");//驱动形式
	$("#dia-buy_date").val("");//购车日期
	//$("#dia-mileage").val("");//行驶里程
	$("#dia-in-xzdbcs").val("");//定保次数
	$("#dia-guarantee_no").val("");//保修卡
	$("#dia-maintenance_date").val("");//首保日期
	$("#dia-license_plate").val("");//车牌号
	$("#dia-user_name").val("");//用户名称
	$("#dia-user_no").val("");//身份证
	$("#dia-link_man").val("");//联系人
	$("#dia-phone").val("");//电话
	$("#dia-user_address").text("");//地址
	$("#dia-in-sbfy").val("");
	//E#1=正常保修:2=首保:3=服务活动:4=售前维修:5=定保:6=售前培训检查:7=安全检查:8=三包急件索赔:9=照顾性保修:10=事故车维修
}
// confirm返回true  
function doConOk2(){
	return false;
}
//保存按钮
function saveOutTabs()
{
	var $f = $("#dia-fm-claimOutInfo");
	if (submitForm($f) == false) return false;
	
	var seType=$("#dia-in-fwlx").attr("code");//服务类型
	var claimType=$("#dia-in-splx").attr("code");//索赔单类型
	var userType=$("#dia-user_typeId").val();//用户类型
	var sfdcwc=$("#dia-in-sfdcwc").attr("code");//是否多次外出
	//只有民用车判断
	if(userType == 300101){
		if(sfdcwc==100101 && claimType != 301408 && claimType != ''){
			alertMsg.warn("多次外出，必须先填报预授权。");
			return;
		}
	}
	if(seType==301101){
		$("#diaSaveTabs").attr("confirm","");
	}
	//是否多次外出， 否将二次外出费用清空
	if(sfdcwc==100102){
		$("#dia-in-fwcfec").val("");
	}
	var sCondition = {};
	sCondition = $("#dia-fm-claimOutInfo").combined(1) || {};
	var insertOrUpdateOutUrl="";
	/* if($("#dia-outId").val())
	{
		insertOrUpdateOutUrl = diaOperateAction + "/updateOut.ajax?claimId="+$("#dia-claimId").val()+"&seType="+$("#dia-in-fwlx").attr("code");
	}else
	{ 
		insertOrUpdateOutUrl = diaOperateAction + "/insertOut.ajax?claimId="+$("#dia-claimId").val()+"&seType="+$("#dia-in-fwlx").attr("code");
	}*/
	//通过后台来判断是否 新增 、修改
	insertOrUpdateOutUrl = diaOperateAction + "/insertOut.ajax?claimId="+$("#dia-claimId").val()+"&seType="+$("#dia-in-fwlx").attr("code");
	doNormalSubmit($f,insertOrUpdateOutUrl,"diaSaveTabs",sCondition,diaInsertOutCallBack);
}
function diaInsertOutCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var outId =getNodeText(rows[0].getElementsByTagName("OUT_ID").item(0));
			$("#dia-outId").val(outId);
			$("#dia-in-sfdcwc").attr("readonly",true);
			$("#dia-in-sfdcwc").addClass("readonly");
			$("#dia-in-sfdcwc").attr("src","");
			$("#diaSaveTabs").attr("confirm","");
			var selectedIndex = parent.$("#tab-workOrderList").getSelectedIndex();
			parent.$("#tab-workOrderList").updateResult(res,selectedIndex);
		}	
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//选择预授权
var vehicleId;
function selectPreauth()
{
	if($("#vehicleId").val()=="")
	{
		alertMsg.info("请先选择VIN.");
		return false;
	}else
	{
		vehicleId=$("#vehicleId").val();
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimPreauthAdd.jsp", "claimPreauthAdd", "选择预授权", options);
	}
}
//选择服务活动
function selectActive()
{
	if($("#vehicleId").val()=="")
	{
		alertMsg.info("请先选择VIN.");
		return false;
	}else
	{
		vehicleId=$("#vehicleId").val();
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimActivityAdd.jsp", "claimActivityAdd", "选择服务活动", options);
	}
}
//选择三包急件订单
/* function selectPartOrder()
{
	if($("#vehicleId").val()=="")
	{
		alertMsg.info("请先选择VIN");
		return false;
	}else
	{
		vehicleId=$("#vehicleId").val();
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimPartOrderAdd.jsp", "claimPartOrderAdd", "选择三包急件订单", options);
	}
} */
//其它费用
function selectOther()
{
	var outId=$("#dia-outId").val();//外出ID
	if(outId){
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimOtherAdd.jsp", "claimOtherAdd", "选择其他项目", options);
	}else{
		alertMsg.warn("请先保存外出信息.");
		return;
	}
}
//索赔单保存
function cliamSave()
{
	if($("#vehicleId").val()==''){
		alertMsg.info("请点击验证按钮,验证车辆是否存在.");
		return false;
	}
	var claimType=$("#dia-in-splx").attr("code");//索赔单类型
	var sfdcwc=$("#dia-in-sfdcwc").attr("code");//是否多次外出
	var seType=$("#dia-in-fwlx").attr("code");
	var $f = $("#dia-fm-wxdawh");
	$("#diaClaimStatus1").val($("#dia-claimStatus").attr("code"));
	$("#diaClaimNo1").val($("#dia-claimNo").val());
	$("#diaWorkId1").val($("#dia-workId").val());
	if (submitForm($f) == false) {
		alertMsg.error("有必填项需填写.");
		return false;
	}
	var userNo=$("#dia-user_no").val();
	var maintenanceDate=$("#dia-maintenance_date").val();//首保日期
	var buyDate= $("#dia-buy_date").val(); //购车日期
	//首保必须填写身份证号
	if(claimType==301402){
		if(userNo == ''){
			alertMsg.warn("做首保,请填写身份证号.");	
			return;
		}
		//首保日期 不能在 购车日期之前
		if(buyDate > maintenanceDate){
			alertMsg.warn("强保日期需大于购车日期.");
			return;
		}
	}
	//定保的时候 必须有首保日期
	if(claimType==301405){
		if(maintenanceDate==''){
			alertMsg.warn("不做首保单，不能做任何定保单.");
			return;
		}
	}
	var userType=$("#dia-user_typeId").val();//用户类型
	if(userType == 300101){
		if(sfdcwc==100101 && claimType != 301408){
			alertMsg.warn("多次外出，必须先填报预授权。");
			return;
		}
	}
	//售前维修、售前培训检查、安全检查 的索赔单在车辆表中不应该有销售日期
	/* if(claimType==301404 || claimType==301406 || claimType==301407){
		if($("#dia-buy_date").val()==''){
			alertMsg.warn("已销售车辆不能做售前维修、售前培训检查、安全检查.");
			return;
		}
	} */
	//售前维修 的索赔单在车辆表中不应该有销售日期
	var flagBuy=0;
	if(buyDate==''){
		if(claimType==301404 ){
			flagBuy=1;	
		}
	}else{
		if(claimType==301401 || claimType==301402 || claimType==301403 || claimType==301405 || claimType==301406 || claimType==301407 || claimType==301408 || claimType==301409){
			flagBuy=1;	
		}
	}
	if(buyDate==''){
		if(flagBuy==0){
			alertMsg.warn("购车日期为空，只能提报售前维修.");
			return;
		}
	}else{
		if(flagBuy==0){
			alertMsg.warn("已销售车辆不能做售前维修.");
			return;
		}
	}
	
	// 时间限制 （故障时间，报修时间，检修时间）
	//dia-in-gzsj  dia-in-bxrq dia-in-jxrq
	var Dgzsj=$("#dia-in-gzsj").val();
	var Dbxrq=$("#dia-in-bxrq").val();
	var Djxrq=$("#dia-in-jxrq").val();
	if(Dgzsj > Dbxrq){
		alertMsg.warn("故障时间不能大于报修时间.");
		return false;
	}
	if(Dbxrq > Djxrq){
		alertMsg.warn("报修时间不能大于检修时间.");
		return false;
	}
	//索赔单类型不同，需要清空页面不必要的值
	//不等于 首保
	if(claimType!=301402){
		$("#dia-in-sbfy").val("");	
	}
	// 不等于服务活动
	if(claimType!=301403){
		$("#dia-in-fwhdh").val("");
		$("#dia-in-fwhid").val("");
		$("#dia-in-fwhfy").val("");
		$("#dia-in-fwhsfgdfy").val("");
		$("#dia-in-fwhsfgdfy").val("");
		$("#dia-in-fwhclfs").val("");
	}
	//不等于售前配件检查
	if(claimType!=301406){
		$("#dia-in-sqpx").val("");
	}
	//不等于安全检查
	if(claimType!=301407){
		$("#dia-in-aqjc").val("");
	}
	//不等于三包急件
	/* if(claimType!=301407){
		$("#dia-in-sbjjdd").val("");
		$("#dia-in-partOrderid").val("");
	} */
	//不等于预授权
	if(claimType!=301408){
		if(claimType!=301401){
			$("#dia-in-ysqh").val("");
			$("#dia-in-ysqid").val("");
		}
	}
	
	var sCondition = {};
	sCondition = $("#dia-fm-wxdawh").combined(1) || {};
	var updateUrl = diaOperateAction + "/updateClaim.ajax?claimId="+$("#dia-claimId").val()+"&seType="+seType+"&rejectFlag="+rejectFlag;
	doNormalSubmit($f,updateUrl,"dia-save",sCondition,diaUpdateCallBack);
}
function diaUpdateCallBack(res)
{
	try
	{
		flagGz=true;
		$("#dia-hid-report").show();
		$("#dia-in-splx").attr("readOnly",true);
		$("#dia-in-splx").addClass("readonly");
		$("#dia-in-splx").attr("src","");
		var selectedIndex = parent.$("#tab-workOrderList").getSelectedIndex();
		parent.$("#tab-workOrderList").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//提报操作
function doDiaSpdReport()
{
	if($("#vehicleId").val()==''){
		alertMsg.info("请点击验证按钮,验证车辆是否存在.");
		return false;
	}
	var day=$("#deaOverDays").val();//服务店设定天数
	var tbcs=$("#diaApplyCount").val();//提报次数
	var bhrq=$("#diaRejectDate").val();//驳回日期
	//需要增加校验逻辑
	var $f = $("#dia-tab-claiminfo");
	if (submitForm($f) == false) return false;
	var claimType=$("#dia-in-splx").attr("code");//索赔单类型
	var maintenanceDate=$("#dia-maintenance_date").val();//首保日期
	var userNo=$("#dia-user_no").val(); //身份证号
	var buyDate =$("#dia-buy_date").val();//购车日期
	//首保必须填写身份证号
	if(claimType==301402){
		if(userNo == ''){
			alertMsg.warn("做首保,请填写身份证号.");	
			return;
		}
		var com=Computation24("<%=curDate%>",maintenanceDate)-1;
		if(com-day>0){
			alertMsg.warn("当前日期与首保日期的差不能大于渠道商设定天数.");
			return;
		}
		//首保日期 不能在 购车日期之前
		if(buyDate > maintenanceDate){
			alertMsg.warn("强保日期需大于购车日期.");
			return;
		}
	}
	
	
	//售前维修、售前培训检查、安全检查 的索赔单在车辆表中不应该有销售日期
	var flagBuy=0;
	if(buyDate==''){
		if(claimType==301404 ){
			flagBuy=1;	
		}
	}else{
		if(claimType==301401 || claimType==301402 || claimType==301403 || claimType==301405 || claimType==301406 || claimType==301407 || claimType==301408 || claimType==301409){
			flagBuy=1;	
		}
	}
	if(buyDate==''){
		if(flagBuy==0){
			alertMsg.warn("购车日期为空，只能提报售前维修.");
			return;
		}
	}else{
		if(flagBuy==0){
			alertMsg.warn("已销售车辆不能做售前维修.");
			return;
		}
	}

	//外出人数超过3人，走预授权
	var claimType=$("#dia-in-splx").attr("code");
	var userType=$("#dia-user_typeId").val();//用户类型
	var wcrs=$("#dia-in-wcrs").val();
	if(userType == 300101){
		if(claimType != 301408 && wcrs -3 >0){
			alertMsg.warn("外出人数超过3人,选择照顾性保修.");
			return;
		}
	}
	//检修日期不能大于当前日期    （故障时间，报修时间，检修时间）
	var jxrq = $("#dia-in-jxrq").val();
	var Dgzsj=$("#dia-in-gzsj").val();
	var Dbxrq=$("#dia-in-bxrq").val();
	if(Dgzsj > Dbxrq){
		alertMsg.warn("故障时间不能大于报修时间.");
		return false;
	}
	if(Dbxrq > jxrq){
		alertMsg.warn("报修时间不能大于检修时间.");
		return false;
	}
	var com1=Computation24("<%=curDate%>",jxrq);
	if(parseInt(com1) <0){
		alertMsg.warn("检修日期不能大于当前日期.");
		return;
	}
	//第一次提报日期限定
	if(rejectFlag==1){
		var com=Computation24("<%=curDate%>",jxrq)-1;
		if(com-day>0){
			alertMsg.warn("当前日期与检修日期的差不能大于渠道商设定天数.");
			return;
		}
	}
	if(rejectFlag==2){
		var bh=Computation24("<%=curDate%>",bhrq)-1;
		if(bh-<%=rejectMaxdate%> > 0){
			alertMsg.warn("退单必须在"+<%=rejectMaxdate%>+"个工作日修改提交.");
			return;
		}
		if(tbcs==2 || tbcs==3){
			//var jxrq = $("#dia-in-jxrq").val();
			var com=Computation24("<%=curDate%>",bhrq)-1;
			
			day=day-3;
			if(com-day>0){
				alertMsg.warn("当前日期与驳回日期的差不能大于渠道商设定天数减三天.");
				return;
			}
		}
		if(tbcs-5 > 0){
			alertMsg.warn("退单次数超过5次,不能提报.");
			return;
		}
	}
	
	//不等于 首保
	if(claimType!=301402){
		$("#dia-in-sbfy").val("");	
	}
	// 不等于服务活动
	if(claimType!=301403){
		$("#dia-in-fwhdh").val("");
		$("#dia-in-fwhid").val("");
		$("#dia-in-fwhfy").val("");
		$("#dia-in-fwhsfgdfy").val("");
		$("#dia-in-fwhsfgdfy").val("");
		$("#dia-in-fwhclfs").val("");
	}
	//不等于售前配件检查
	if(claimType!=301406){
		$("#dia-in-sqpx").val("");
	}
	//不等于安全检查
	if(claimType!=301407){
		$("#dia-in-aqjc").val("");
	}
	//不等于三包急件
	/* if(claimType!=301407){
		$("#dia-in-sbjjdd").val("");
		$("#dia-in-partOrderid").val("");
	} */
	//不等于预授权
	if(claimType!=301408){
		if(claimType!=301401){
			$("#dia-in-ysqh").val("");
			$("#dia-in-ysqid").val("");
		}
	}
	var sCondition = {};
	sCondition = $("#dia-fm-wxdawh").combined(1) || {};
	var reportUrl = diaOperateAction + "/reportClaim.ajax?claimId="+$("#dia-claimId").val();
	doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	
}
//提报回调方法
function diaReportCallBack(res)
{
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result==1){
			alertMsg.error("该车已做过首保.");
		}else if(result==2){
			alertMsg.error("该车已经做过2次安全检查.");
		}else if(result==3){
			alertMsg.error("该车已做过售前培训检查.");
		}else if(result==4){
			alertMsg.error("请检查所有故障模式下必须有主损件.");
		}else if(result==5){
			alertMsg.error("联系系统管理员，请维护军车审核员.");
		}else if(result==6){
			alertMsg.error("联系系统管理员，请维护民车审核员.");
		}else if(result==7){
			alertMsg.error("单车材料费大于"+<%=claimMaxCosts%>+"请选择预授权.");
			$("#xzysq").show();//预授权显示
		}else{
			parent.$("#tab-workOrderList").removeResult(parent.$row);
			parent.$.pdialog.closeCurrent();
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//故障信息列表，修改或者添加新的配件信息
function doDiaEditPart(rowobj)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimPartAdd.jsp?claimDtlId="+$(rowobj).attr("CLAIM_DTL_ID"), "claimPartAdd", "配件维护", options);
}
//故障信息列表，添加新的故障信息
function addGzdm()
{
	//var spdlx=$("#dia-in-splx").attr("code");
	if(!flagGz)
	{
		alertMsg.info("请先保存维修信息后在添加故障信息");
		return false;
	}
	var claimType=$("#dia-in-splx").attr("code");
	//首保 、售前培训检查、安全检查
	if(claimType==301402 || claimType==301406 || claimType==301407){
		alertMsg.warn("该类型索赔单不需要填报故障模式.");
		return;
	}
	/* var claimType=$("#dia-in-splx").val();
	//首保 、售前培训检查、安全检查
	if(claimType==301402 || claimType==301406 || claimType==301407){
			alertMsg.warn("首保、售前培训检查、安全检查不需添加故障信息！");
			return;
	} */
	/* else
	{
		if(spdlx=="301409")//预授权需要先保存预授权后在添加故障
		{
			if($("#dia-in-ysqid").val=="")
			{
				alertMsg.info("选择预授权信息后在添加故障信息");
				return false;
			}
			
		}
		if(spdlx=="301403")//服务活动需要先确定服务活动类型
		{
			if($("#dia-in-fwhid").val=="")
			{
				alertMsg.info("选择服务活动后在添加故障信息");
				return false;
			}else if($("#dia-in-fwhsfgdfy").val()=="100101")
			{
				alertMsg.info("固定费用的服务活动不需添加故障信息");
				return false;
			}
		}
		if(spdlx=="301408")//三包急件订单需要选择三包急件后在添加故障
		{
			if($("#dia-in-partOrderid").val=="")
			{
				alertMsg.info("选择三包急件订单后在添加故障信息");
				return false;
			}
		} */
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/claimFaultPatternAdd.jsp", "claimFaultPatternAdd", "故障维护", options);
}
function diaSearchClaimInfoBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			setEditValue("dia-fm-wxdawh",rows[0]);
		}	
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//故障信息列表，查询
function searchClaimPattern()
{
	var $f = $("#dia-fm-hidden");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	var searchClaimPatternUrl=diaOperateAction+"/searchClaimPattern.ajax";
	doFormSubmit($f,searchClaimPatternUrl,"dia-save1",1,sCondition,"dia-tab-cliamPattrent");
}
//查询费用
function searchClaimMaxCosts(){
	var claimType=$("#dia-in-splx").attr("code");//索赔单类型 正常保修，单车材料费
	var vehicleType=$("#dia-vehicle_useId").val();//车辆类型 民用车
	//正常保修 民用车 判断材料费
	if(claimType==301401 && vehicleType ==300101){
		var claimCostsUrl = diaOperateAction + "/searchClaimMaxCosts.ajax?claimId="+$("#dia-claimId").val();
		sendPost(claimCostsUrl,"","",claimCostsBack,"false");
	}
}
function claimCostsBack(res){
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var claimCosts =getNodeText(rows[0].getElementsByTagName("CLAIM_COST").item(0));
			$("#diaClaimCost").val(claimCosts);
			if(claimCosts - <%=claimMaxCosts%> > 0){
				$("#xzysq").show();			
			}else{
				$("#xzysq").hide();
			}
		}	
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//故障信息列表，删除故障信息
var $diaRow;
function doDiaListDelete(rowobj)
{
	$diaRow = $(rowobj);
	var deleteClaimPatternUrl= diaOperateAction+"/deleteClaimPattern.ajax?claimDtlId="+$(rowobj).attr("CLAIM_DTL_ID");
	sendPost(deleteClaimPatternUrl,"","",deleteCliamPatternCallBack,"true");
}
//删除回调方法
function  deleteCliamPatternCallBack(res)
{
	try
	{
		if($diaRow){
			$("#dia-tab-cliamPattrent").removeResult($diaRow);
			searchClaimMaxCosts();
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//附件回调方法
function fjUpCallBack(fjid){
	var $f = $("#dia_fm_atta");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var fileSearchUrl1 =diaOperateAction+"/fileSearch.ajax?workId="+$("#dia-workId").val(); 
	doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
}
//下载
function doAttaOperation(obj){
	var $tr = $(obj).parent();
    var cjr = $tr.attr("CJR");
    if(cjr=='手机端用户'){
    	obj.html("<A class=op title=[下载] onclick='doDownloadAtta(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[下载]</A>");
    }else{
    	obj.html("<A class=op title=[下载] onclick='doDownloadAtta(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[下载]</A><A class=op title=[删除] onclick='doDeleteAtta(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[删除]</A>");
    }
}

//下载附件
function doDownloadAtta(obj){
	var fjid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
  //删除附件
var $rowAtta=null;
function doDeleteAtta(obj)
{
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
</script>
</head>
<body>
<div id="dia-layout">
<form method="post" id="dia-fm-hidden" class="editForm" style="display:">
 	<input type="hidden" id="dia-claimId" value="" datasource="CLAIM_ID"/>
 	<input type="hidden" id="dia-workId" value="" />
 	<input type="hidden" id="diaApplyCount" value=""/>
 	<input type="hidden" id="diaRejectDate" value=""/>
 	<input type="hidden" id="deaOverDays" value=""></input>
 	<input type="hidden" id="diaClaimCost" value=""></input>
</form>
	<div class="tabs" eventType="click" id="dia-tabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
				    <li><a href="javascript:void(0)"><span>工单信息</span></a></li>
					<li><a href="javascript:void(0)"><span>维修信息</span></a></li>
					<li><a href="javascript:void(0)"><span>附件信息</span></a></li>
					<li id="auditH" style="display:none"><a href="javascript:void(0)"><span>审核轨迹</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div class="page" id="dia-div-wcxxlb" style="overflow:auto;">
				<div class="pageContent">
					<form method="post" id="dia-fm-claimMaintis" class="editForm" style="width: 99%;"> 
						<div align="left">
						<fieldset>
							<table class="editTable" id="dia-tab-wxxx">
								<tr><td><label>派工单号：</label></td>
								    <td><input type="text" id="dia-workNo" value="" readonly="readonly"/></td>
								    <td><label>索赔单号：</label></td>
								    <td><input type="text" id="dia-claimNo" value="" datasource="CLAIM_NO" readonly="readonly"/></td>
								    <td><label>服务站名称：</label></td>
								    <td><input type="text" id="dia-dealerName" value="" datasource="ORG_ID" readonly="readonly"/></td>
								    
								</tr>
								<tr>
								    <td><label>索赔单状态：</label></td>
								    <td><input type="text" id="dia-claimStatus" value="" datasource="CLAIM_STATUS"  readonly="readonly"/></td>
								    <td><label>服务类型：</label></td>
								    <td colspan="3">
									    <select type="text" id="dia-in-fwlx" class="combox" name="dia-in-fwlx" kind="dic" src="FWLX" datasource="SE_TYPE" datatype="0,is_null,6" value="">
									    	<option value=""  selected></option>
									    </select>
								    </td>
								</tr>
							</table>
						</fieldset>	
					  	</div>
					</form>
					<div id="dia-div-wcxx" style="display: none">
						<div class="pageContent" >
					   		<form method="post" id="dia-fm-claimOutInfo" class="editForm" style="width: 99%;"> 
								<table class="editTable" id="dia-tab-wcxx">
									<tr><td><label>是否多次外出：</label></td>
								    	<td>
								    		 <input type="text"  id="dia-in-sfdcwc"  name="dia-in-sfdcwc" kind="dic" src="SF" datasource="IS_OUT_TIMES" datatype="0,is_null,30" value="否" code="100102"/>
									         <input type="hidden" id="dia-in-wccs"   name="dia-in-wccs" datasource="OUT_TIMES"  value="301201"/>
									   	</td> 
									   	<td><label>外出总费用：</label></td>
									   	<td ><input type="text" id="dia-in-wczfy" value="0" readonly="readonly" datasource="COST_AMOUNT"/></td>
									   	<td><label>一次外出费用：</label></td>
									   	<td ><input type="text" id="dia-in-ycwczfy" value="0" readonly="readonly" datasource="OUT_COSTS"/>
									   		 <input type="hidden" id="dia-outId" value="" datasource="OUT_ID" datatype="0,is_null,30"/>
									   	</td>     
									</tr>
									<tr>
									    <td><label>外出方式：</label></td>
									    <td> 
									        <select type="text" class="combox" id="dia-in-wcfs"  name="dia-in-wcfs" kind="dic" src="WCFS" datasource="OUT_TYPE" datatype="0,is_null,30" value="" onchange="diaCountOutCost();">
										    		<option value="301301" selected>自备车</option>
										    </select>
										</td>
									    <td><label>外出人数：</label></td>
									    <td>
										    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"  datatype="0,is_digit_0,10" value="" datasource="OUT_UCOUNT"  onchange="diaCountOutCost();"/>
									    </td>
									    <td><label>外出人员：</label></td>
									    <td>
										    <input type="text" id="dia-in-wcry"  name="dia-in-wcry"  datatype="1,is_null,30" datasource="OUT_USER" value=""/>
									    </td>
									</tr>
									<tr>
										<td><label>有效里程：</label></td>
									    <td>
										    <input type="text" id="dia-in-yxlc"  name="dia-in-yxlc"  datatype="0,is_digit_0,30" value="" datasource="MILEAGE" onchange="diaCountOutCost();"/>
									    </td>
									    <td><label>服务车牌号：</label></td>
									    <td>
										    <input type="text" id="dia-in-fwcph"  name="dia-in-fwcph"  datatype="1,is_null,30" value="" datasource="VEHICLE_NO" readonly="readonly" />
									    </td>
									    <td ><label>其他费：</label></td>
									    <td >
										     <input type="text" id="dia-in-qtf"  name="dia-in-qtf"  datatype="1,is_null,30" value="" datasource="OTHER_COSTS" readonly="readonly" hasBtn="true" callFunction="selectOther()"/>
									    </td>
									</tr>
									<tr>
										<td><label>在途补助天数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-onWayDay"  name="dia-in-onWayDay" value="0" datasource="ON_WAY_DAYS"  datatype="0,is_null,30" value="" readonly="readonly"/>
										</td>
										<td><label>差旅天数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-travelDay"  name="dia-in-travelDay" datasource="TRAVEL_DAYS" value="0" datatype="0,is_null,30" value="" readonly="readonly"/>
										</td>
										<td><label>外出时间：</label></td>
									    <td> 
									        <input type="text" id="dia-in-wcsj"  name="dia-in-wcsj" code=""  datatype="0,is_null,30" value="" title="由时间设定规则确定出白天还是黑天" readonly="readonly"/>
									        <input type="hidden" id="dia-in-wcsjdm"  name="dia-in-wcsjdm"  datasource="OUTDATE_TYPE"  value="" />
										</td>
									</tr>
									<tr>
									    <td><label>出发时间：</label></td>
									    <td> 
									        <input type="text" id="dia-in-cfsj"  name="dia-in-cfsj"  datatype="0,is_null,30" kind="date"  datasource="GO_DATE"  readonly="readonly" />
										</td>
									    <td><label>到达时间：</label></td>
									    <td > 
									        <input type="text" id="dia-in-ddsj"  name="dia-in-ddsj"  datatype="0,is_null,30" kind="date" datasource="ARRIVE_DATE"  readonly="readonly"/>
										</td>
										<td><label>完成时间：</label></td>
									    <td > 
									        <input type="text" id="dia-in-lksj"  name="dia-in-lksj"  datatype="0,is_null,30" kind="date" datasource="LEAVE_DATE" readonly="readonly"/>
										</td>
									</tr>
									<tr>
									    <td><label>在途补助：</label></td>
									    <td>
										    <input type="text" id="dia-in-ztcb"  name="dia-in-ztcb"  datatype="1,is_null,30" value="" datasource="MEALS_COSTS"  readonly="readonly"/>
									    </td>
										<td><label>服务车费：</label></td>
									    <td> 
									         <input type="text" id="dia-in-fwcf"  name="dia-in-fwcf"  datatype="1,is_null,30" datasource="SEVEH_COSTS" value="" readonly="readonly"/>
										</td>
										<td><label>差旅费：</label></td>
									    <td>
										     <input type="text" id="dia-in-clf"  name="dia-in-clf"  datatype="1,is_null,30"  datasource="TRAVEL_COSTS" value="" readonly="readonly"/>
									    </td>
									</tr>
									<tr id="ecwcfy" style="display: none">
									    <td ><label>服务车费（二次）：</label></td>
									    <td colspan="5"> 
									         <input type="text" id="dia-in-fwcfec"  name="dia-in-fwcfec"  datatype="1,is_null,30" value="" datasource="SEC_VEH_COSTS" readonly="readonly"/>
										</td>
									</tr>
									<tr>
									    <td><label>其他说明：</label></td>
									    <td colspan="3">
										    <textarea id="dia-in-bz" style="width:500px;height:40px;" name="dia-in-bz" datasource="REMARKS"  datatype="1,is_null,100"></textarea>
									    </td>
									    <td><label>GPS里程：</label></td>
								   	    <td><input type="text" id="dia-gpsYxlc" value="" datasource="YXLC"  readonly="readonly"/></td>
									</tr>
								</table>
							</form>	
						</div>	
		  			</div>
				</div>
				<div class="formBar">
						<ul>
							<li id="outL"><div class="buttonActive"><div class="buttonContent"><button type="button" id="diaSaveTabs" onclick="saveOutTabs();" confirm="请确认是否是多次外出？一经确认，将不可修改.">保&nbsp;&nbsp;存</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
    						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
			</div>
			<div class="page" >
				<div class="pageContent" id="dia-div-claimEdit" style="overflow-y:auto;overflow-x:hidden;">
					<form method="post" id="dia-fm-wxdawh" class="editForm" style="width: 99%;">
						<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
						<input type="hidden" id="modelsId" datasource="MODELS_ID"/>
						<input type="hidden" id="diaClaimStatus1" datasource="DIA_CLAIM_STATUS"/>
						<input type="hidden" id="diaClaimNo1" datasource="DIA_CLAIM_NO"/>
						<input type="hidden" id="diaWorkId1" datasource="DIA_WORK_ID"/>
						<div align="left">
						<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-tab-claiminfo')">&nbsp;维修信息&gt;&gt;</a></legend>
							<table class="editTable" id="dia-tab-claiminfo">
								<tr>
									<td><label>VIN：</label></td>
									<td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN" datatype="0,is_null,17" operation="like" title="(请输入后8位或者17位)"/></td>
									<td><label>发动机号：</label></td>
									<td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO" datatype="0,is_fdjh,30" operation="like" /></td>
									<td colspan="2"><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin" >验&nbsp;&nbsp;证</button></div></div>
									<span style="float:left;margin-left:10px;margin-top:5px;"></span>
									<div class="button"><div class="buttonContent"><button type="button" id="dia-recheckvin" >重新填写</button></div></div></td></td>
								</tr>
								<tr>
									<td><label>索赔单类型：</label></td>
									<td colspan="5">
										<input type="text" id="dia-in-splx"  name="dia-in-splx" kind="dic" src="SPDLX" datasource="CLAIM_TYPE" datatype="0,is_null,30" value=""/>	
									</td>
								</tr>
								<tr id="xzysq" style="display: none">
									<td><label>选择预授权：</label></td>
									<td colspan="5"><input type="text" id="dia-in-ysqh" name="dia-in-ysqh" datatype="0,is_null,30" value="" hasBtn="true" callFunction="selectPreauth()" datasource="AUTHOR_NO"  readonly="readonly"/><input type="hidden" id="dia-in-ysqid" name="dia-in-ysqid" datasource="PRE_AUTHOR_ID" datatype="1,is_null,30" value="" /></td>
								</tr>
								<tr id="xzfwhd" style="display: none">
									<td><label>选择服务活动：</label></td>
									<td colspan="5"><input type="text" id="dia-in-fwhdh" name="dia-in-fwhdh" datatype="0,is_null,30" value="" hasBtn="true" callFunction="selectActive()" datasource="ACTIVITY_CODE"  readonly="readonly"/>
									<input type="hidden" id="dia-in-fwhid" name="dia-in-fwhid" datasource="ACTIVITY_ID" datatype="1,is_null,30" value="" />
									<input type="hidden" id="dia-in-fwhfy" name="dia-in-fwhfy" datasource="SERVICE_COST" datatype="1,is_null,30" value="" />
									<input type="hidden" id="dia-in-fwhsfzc" name="dia-in-fwhsfzc" datasource="IF_RECOVERY" datatype="1,is_null,30" value="" />
									<input type="hidden" id="dia-in-fwhsfgdfy" name="dia-in-fwhsfgdfy" datasource="IF_FIXED" datatype="1,is_null,30" value="" />
									<input type="hidden" id="dia-in-fwhclfs" name="dia-in-fwhclfs" datasource="SE_METHOD" datatype="1,is_null,30" value="" />
									</td>
								</tr>
								<tr id="xzsbfy" style="display: none">
									<td><label>强保费用：</label></td>
									<td><input type="text" id="dia-in-sbfy" name="dia-in-sbfy" datatype="1,is_null,30" value=""  datasource="MAINTENANCE_COSTS" readonly="readonly"/></td>
									<td><label>强保日期：</label></td>
									<td colspan="3"><input type="text" id="dia-maintenance_date" name="dia-maintenance_date" datasource="MAINTENANCE_DATE" value="" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" datatype="0,is_null,30" /></td>
								</tr>
								<tr id="xzaqjc" style="display: none">
									<td><label>安全检查费用：</label></td>
									<td colspan="5"><input type="text" id="dia-in-aqjc" name="dia-in-aqjc" datatype="0,is_null,30" value=""  datasource="SAFE_COSTS" readonly="readonly" /></td>
								</tr>
								<tr id="xzsqpx" style="display: none">
									<td><label>售前培训费用：</label></td>
									<td colspan="5"><input type="text" id="dia-in-sqpx" name="dia-in-sqpx" datatype="0,is_null,30" value=""  datasource="TRAIN_COSTS" readonly="readonly" /></td>
								</tr>
								<tr id="xzdb" style="display: none">
									<td><label>已定保次数：</label></td>
									<td colspan="5"><input type="text" id="dia-in-xzdbcs" name="dia-in-xzdbcs" datatype="1,is_null,10"   value="" readonly="readonly"/></td>
								</tr>
								<!-- <tr id="xzsbjjdd" style="display: none">
									<td><label>选择三包急件订单：</label></td>
									<td colspan="5"><input type="text" id="dia-in-sbjjdd" name="dia-in-sbjjdd" datatype="0,is_null,30" value="" hasBtn="true" callFunction="selectPartOrder()" datasource="DISPATCH_NO" readonly="readonly"/><input type="hidden" id="dia-in-partOrderid" name="dia-in-partOrderid" datatype="1,is_null,30" datasource="DISPATCH_ID"  value="" /></td>
								</tr> -->
								<tr>
									<td><label>车辆型号：</label></td>
									<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODEL_CODE" value="" readonly="readonly" /></td>
									<td><label>发动机型号：</label></td>
									<td><input type="text" id="dia-di-engine_type" name="dia-di-engine_type" value="" datasource="ENGINE_TYPE" readonly="readonly" /></td>
									<td><label>用户类型：</label></td>
									<td><input type="text" id="dia-user_type" name="dia-user_type"  value=""  datasource="USER_TYPE_NAME" readonly="readonly" />
									<input type="hidden" id="dia-user_typeId" name="dia-user_typeId"  value=""  datasource="USER_TYPE" /></td>
								</tr>
								<tr>
									<td><label>车辆用途：</label></td>
									<td><input type="text" id="dia-vehicle_use" name="dia-vehicle_use"   value="" datasource="VEHICLE_NAME" readonly="readonly" />
									<input type="hidden" id="dia-vehicle_useId" name="dia-vehicle_useId"  value=""  datasource="VEHICLE_USE" />
									</td>
									<td><label>驱动形式：</label></td>
									<td><input type="text" id="dia-drive_form" name="dia-drive_form"  datasource="DRIVE_FORM" value="" readonly="readonly" /></td>
								</tr>
								<tr id="gclibxk" style="display: ">
									<td><label>购车日期：</label></td>
									<td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
									<td><label>行驶里程：</label></td>
									<td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value="" datatype="0,is_digit_0,10" /></td>
									<td><label>保修卡号：</label></td>
									<td><input type="text" id="dia-guarantee_no" name="dia-guarantee_no" datasourc="GUARANTEE_NO" value=""  readonly="readonly" /> </td>
								</tr>
								<tr>
									<td><label>车牌号码：</label></td>
									<td><input type="text" id="dia-license_plate" name="dia-license_plate" datasource="LICENSE_PLATE" value="" datatype="0,is_carno,30" /></td>
									<td><label>用户单位名称：</label></td>
									<td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value="" datatype="0,is_null,300" /></td>
									<td><label>身份证号：</label></td>
									<td><input type="text" id="dia-user_no" name="dia-user_no"  datasource="USER_NO" value="" datatype="1,is_idcard,18" /></td>
								</tr>
								<!-- <tr>
									<td><label>省：</label></td>
									<td><input type="text" id="dia-province_Code" name="dia-province_Code" kind="dic" src="XZQH" filtercode="\d{2}0000$" datasource="PROVINCE_CODE" value="" datatype="0,is_null,100" /></td>
									<td><label>市：</label></td>
									<td><input type="text" id="dia-city_Code" name="dia-city_Code" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,100" /></td>
									<td><label>区县：</label></td>
									<td><input type="text" id="dia-county_Code" name="dia-county_Code" kind="dic"  datasource="COUNTY_CODE" value="" datatype="0,is_null,300" /></td>
								</tr> -->
								<tr>
									<td><label>省：</label></td>
									<td><input type="text" id="dia-province_Code" name="dia-province_Code" kind="dic" src=""   datasource="PROVINCE_CODE" value="" datatype="0,is_null,100" /></td>
									<td><label>市：</label></td>
									<td><input type="text" id="dia-city_Code" name="dia-city_Code" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,100" /></td>
									<td><label>区县：</label></td>
									<td><input type="text" id="dia-county_Code" name="dia-county_Code" kind="dic"  datasource="COUNTY_CODE" value="" datatype="0,is_null,300" /></td>
								</tr>
								<tr>
									<td><label>用户地址：</label></td>
									<td><textarea id="dia-user_address" style="width: 150px; height: 30px;" name="dia-user_address" datasource="USER_ADDRESS" datatype="1,is_null,100"></textarea></td>
									<td><label>用户姓名：</label></td>
									<td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value="" datatype="0,is_null,30" /></td>
									<td><label>用户电话：</label></td>
									<td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value="" datatype="0,is_phone,11" /></td>
								</tr>
								<tr id="gzxxTr">
									<td><label>故障来源：</label></td>
									<td><input type="text" id="dia-in-gzxxly" name="dia-in-gzxxly" datatype="1,is_null,40" value="" kind="dic" src="T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302701" datasource="FAULT_FROM"/></td>
									<td><label>故障地点：</label></td>
									<td colspan="3"><input type="text" id="dia-in-gzdd" name="dia-in-gzdd" value="" style="width:85%"  datatype="0,is_null,300" datasource="FAULT_ADDRESS"/></td>
									<!-- <td><label>故障分析：</label></td>
									<td >
									 <input type="text" id="dia-in-hsj0" name="dia-in-gzfx0"  datatype="1,is_null,30" value="" kind="dic" src="T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302702" datasource="FAULT_ANLYSIE"/>
									</td> -->
								</tr>
								<tr>
									<td><label>报修人：</label></td>
									<td><input type="text" id="dia-in-bxr" name="dia-in-bxr" datatype="0,is_null,40" value=""  datasource="APPLY_USER"/></td>
									<td><label>报修人类型：</label></td>
									<td><input type="text" id="dia-in-bxrlx" name="dia-in-bxrlx" kind="dic" src="BXRLX" datatype="0,is_null,30" value="" datasource="APPLY_USER_TYPE"/>
									</td>
									<td id="diaBxdzL"><label>报修地址：</label></td>
									<td id="diaBxdzT"><textarea id="dia-in-bxdz" style="width: 150px; height: 30px;" name="dia-in-bxdz" datatype="1,is_null,300" datasource="APPLY_ADDRES"></textarea></td>
								</tr>
								
								<tr id="diaJxsjTr">
									<td id="diaGzsjL"><label>故障时间：</label></td>
									<td id="diaGzsjT"><input type="text" id="dia-in-gzsj" name="dia-in-gzsj" value="" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})"  datasource="FAULT_DATE"/></td>
									<td><label>报修时间：</label></td>
									<td><input type="text" id="dia-in-bxrq" name="dia-in-bxrq" value="" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" datasource="APPLY_REPAIR_DATE"/></td>
									<td><label>检修时间：</label></td>
									<td><input type="text" id="dia-in-jxrq" name="dia-in-jxrq" value="" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" datasource="REPAIR_DATE"/></td>
								</tr>
								<tr>
									<td><label>报修人电话：</label></td>
									<td><input type="text" id="dia-in-bxrdh" name="dia-in-bxrdh" value="" datatype="0,is_null,40" datasource="APPLY_MOBIL"/></td>
									<td><label>检修人：</label></td>
									<td><input type="text" id="dia-in-jxr" name="dia-in-jxr" value="" datatype="0,is_null,40" datasource="REPAIR_USER"/></td>
									<td><label>检修地址：</label></td>
									<td><textarea id="dia-in-jxdz" style="width: 150px; height: 30px;" name="dia-in-jxdz" datatype="1,is_null,100" datasource="REPAIR_ADDRESS"></textarea></td>
								</tr>
								<tr>
									<td><label>备注：</label></td>
									<td colspan="5"><textarea id="dia-in-bxbz" style="width: 95%; height: 50px;" datasource="REMARKS" name="dia-in-bxbz" title="最多可输入500汉字" datatype="1,is_null,1000"></textarea></td>
								</tr>
							</table>
						</fieldset>
						</div>
					</form>
					<div class="pageContent" >
						<fieldset>
						<div id="dia-div-cliamPattrent" style="">
							<table style="width:100%;" id="dia-tab-cliamPattrent" name="tablist" ref="dia-div-cliamPattrent" refQuery="dia-fm-hidden" pageRows="5000" >
								<thead>
									<tr>
										<th type="single" name="XH" style="display:" append="plus|addGzdm" colwidth="20"></th>
										<th fieldname="FAULT_CODE" >故障代码</th>
										<th fieldname="FAULT_NAME" >故障名称</th>
										<th fieldname="SEVERITY" >严重程度</th>
										<th fieldname="WORK_TIME" >维修工时</th>
										<th fieldname="WORK_TIME_UPRICE" >工时单价</th>
										<th fieldname="STAR_LEVEL_UPRICE" >星级单价</th>
										<th fieldname="ENCOURAGE_UPRICE" >激励单价</th>
										<th fieldname="WORK_MULTIPLE" >工时倍数</th>
										<th fieldname="WORK_COSTS" >工时费</th>
										<th colwidth="120" type="link" title="[维护配件]|[删除]"  action="doDiaEditPart|doDiaListDelete">操作</th>
									</tr>
								</thead>
							</table>
						</div>
						</fieldset>
					</div>	
					<div class="formBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="cliamSave();">保存维修</button></div></div></li>
							<li id="dia-hid-report" ><div class="button"><div class="buttonContent"><button type="button" id="dia-report" onclick="doDiaSpdReport();" >提&nbsp;&nbsp;报</button></div></div></li>
							<li id="dia-hid-report" ><div class="button"><div class="buttonContent"><button type="button" id="dia-outBuySearch">外采件查询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul> 
					</div>
				</div>
			</div>
	        <div class="page">
				<div class="pageContent">  
					<form method="post" id="dia_fm_atta" class="editForm" style="display:none"></form>
						<div align="left">
		            	<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-files')">&nbsp;已传附件列表&gt;&gt;</a></legend>
						    <div id="dia-files">
							<table  style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
								<thead>
									<tr>
										<th type="single" name="XH" style="display:none"></th>
										<th fieldname="FJMC" >附件名称</th>
										<th fieldname="CJR" >上传人</th>
										<th fieldname="CJSJ">上传时间</th>
										<th colwidth="85" type="link" refer="doAttaOperation" title="[下载]"  action="doDownloadAtta" >操作</th>
									</tr>
								</thead>
							</table>
						</div>
						</fieldset>
		             	</div> 
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上传附件</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
						<li id="auditL" style="display:none"><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一步</button></div></div></li>
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
	<form id="dialog-fm-download"style="display:none"></form>
</div>
</body>
</html>