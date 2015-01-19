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
	String activityId = request.getParameter("activityId");
	String ifFixcosts = request.getParameter("ifFixcosts");
	String ifClaim = request.getParameter("ifClaim");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var fileSearchUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction/fileSearch.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction";
var activityId = "<%=activityId%>";
var sfspVal=ifClaim;
var sfgdfyVal=ifFixcosts;
var ifClaim = "<%=ifClaim%>";
var ifFixcosts = "<%=ifFixcosts%>";
var $modelTrue=true;
var $CLTrue=true;
var $partTrue=true;
var $timeTrue=true;
var $vinTrue=true;
var $fileTrue=true;

$(function(){
	//查询服务活动
	var $f = $("");
	var sCondition = {};
   	sCondition = $f.combined() || {};
   	var searchServiceUrl =diaSaveAction+"/searchService.ajax?&activityId="+activityId+"";
   	sendPost(searchServiceUrl,"",sCondition,searchServiceCallBack,"false");
	//进入修改页面需要根据是否索赔与是否固定费用确定需要显示的tab页面
	
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("fwhdxxmx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#service-tabs").switchTab(parseInt($("#service-tabs").attr("currentIndex")) - 1);
	});
	//下一步
	$("button[name='btn-next']").bind("click", function(event){
		var $tabs = $("#service-tabs");
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
					var searchServiceModelUrl =diaSaveAction+"/searchServiceModel.ajax?&activityId="+$("#serviceActivityInfoId").val()
					doFormSubmit($f,searchServiceModelUrl,"",1,sCondition,"fwhdcxlb");
				}
				$true=false;
				break;
			case 2: 
				if($CLTrue){
					var $f = $("");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVehage.ajax?&activityId="+$("#serviceActivityInfoId").val()
					doFormSubmit($f,searchServiceProtUrl,"",1,sCondition,"fwhdcllb");   
				}
				$CLTrue=false;
				break;
			case 3: 
					if($fileTrue){
						var $f = $("");
						var sCondition = {};
						sCondition = $f.combined() || {};
						var fileSearchUrl1 =fileSearchUrl+"?&activityId="+$("#serviceActivityInfoId").val()
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
				default:
					break;
			}
		})
		(parseInt($tabs.attr("currentIndex")));
	});
});
function projectShow(){
	$("#gsxxH").show();
	$("#pjxxH").show();
}
function projectHide(){
	$("#gsxxH").hide();
	$("#pjxxH").hide();
}
function searchServiceCallBack(res)
{
	setEditValue("fm-serviceActivityInfo",res);
	 var sfgdfyVal=$("#SFGDFY").attr("code");
		if(sfgdfyVal==<%=DicConstant.SF_01%>){
			$("#la_hdfy").show();
			$("#DI_HDFY").show();
			$("#la_rzlx").show();
			$("#DI_rzlx").show();
			$("#dia-nextH4").hide();
			$("#dia-nextH6").hide();
			$("#ClNext-id").hide();
			projectHide();
		}else if(sfgdfyVal==<%=DicConstant.SF_02%>){
			$("#la_hdfy").hide();
			$("#DI_HDFY").hide();
			$("#la_rzlx").hide();
			$("#DI_rzlx").hide();
			projectShow();
		};
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="service-tabs">
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
							<td id="hddmI" style="display:none"><input type="text" id="DI_HDDM" name="ACTIVITY_CODE" datasource="ACTIVITY_CODE" readonly="readonly"/></td>
							<td><label>活动名称：</label></td>
							<td colspan="5"><input type="text" id="DI_HDMC" name="ACTIVITY_NAME" datasource="ACTIVITY_NAME" readonly="readonly"/></td>
						</tr>
						<tr>	
							<td><label>活动类别：</label></td>
							<td>
								<input type="text" id="DI_HDLB" name="ACTIVITY_TYPE" datasource="ACTIVITY_TYPE" src="HDLB" readonly="readonly"/>
							</td>
							<td><label>处理方式：</label></td>
							<td>
								<input type="text" id="DI_CLFS" name="MANAGE_TYPE" datasource="MANAGE_TYPE" src="CLFS" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td><label>开始里程(公里)：</label></td>
							<td><input type="text" id="DI_KSLC" name="DI_XSLC" datasource="BEGIN_MILEAGE" readonly="readonly"/></td>
						    <td><label>结束里程(公里)：</label></td>
							<td><input type="text" id="DI_JSLC" name="DI_XSLC" datasource="END_MILEAGE" readonly="readonly"/></td>
						</tr>
						<tr>
						    <td><label>活动日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="START_DATE" datasource="START_DATE"  readonly="readonly"/>
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="END_DATE" datasource="END_DATE"  readonly="readonly"/>
					   		 </td>
					 	</tr>
						<tr>	
							<td><label>是否索赔：</label></td>
							<td>
								<input type="text" id="DI_SFSP" name="IF_CLAIM" datasource="IF_CLAIM" src="SF" readonly="readonly"/>
							</td>
							<td id="la_gdfy"><label>是否固定费用：</label></td>
							<td id="DI_SFGDFY">
								<input type="text" id="SFGDFY" name="IF_FIXCOSTS" datasource="IF_FIXCOSTS" src="SF" readonly="readonly"/>
							</td>
							<td id="la_hdfy"><label>活动费用(元)：</label></td>
							<td><input type="text" id="DI_HDFY" name="ACTIVITY_COSTS" datasource="ACTIVITY_COSTS" readonly="readonly"/></td>
						</tr>
						<tr>
							<td id="la_sfrgsp"><label>是否需人工审批：</label></td>
							<td id="DI_SFRGSP"><input type="text" id="SFRGSP" name="SFRGSP" datasource="IF_PERSON_CHECK" src="SF" readonly="readonly"/></td>
							<td id="la_rzlx"><label>入账类型：</label></td>
							<td id="DI_rzlx"><input type="text" id="rzlx" name="rzlx" datasource="IN_ACCOUNT_TYPE" src="RZLX" readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label>解决方案：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="BZ" name="SOLUTION" datasource="SOLUTION" style="width:100%" readonly="readonly" ></textarea></td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="BZ" name="REMARKS" datasource="REMARKS" style="width:100%" readonly="readonly" ></textarea></td>
						</tr>
					</table>
					</fieldset>
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
			<!-- 服务活动项目TAB -->
			<div id="xmxxC">
				<div class="page">
				<div class="pageContent">
					<div id="fwhdxm">
							<table width="100%" id="fwhdvinlb" name="fwhdvinlb" multivals="VINDeleteVal" ref="fwhdxm"  style="display: none"  refQuery="fwhdxmqtfyTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="VIN" >VIN</th>
								<th fieldname="ENGINE_NO" >发动机型号</th>
								<th fieldname="MODELS_NAME" >车辆型号</th>
								<th fieldname="BUY_DATE" >销售日期</th>
								<th fieldname="FACTORY_DATE" >生产日期</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					</div>
					<div class="formBar">
						<ul>
							 <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre5" name="btn-pre">上一步</button></div></div></li>
							 <li id="dia-nextHVin"><div class="button"><div class="buttonContent"><button type="button" id="dia-nextVin" name="btn-next">下一步</button></div></div></li>
							 <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
				</div>
				</div>
			</div>
			<!-- 服务活动车型TAB -->
			<div id="cxxxC">
				<div class="page">
				<div class="pageContent">
				<div id="fwhdcx">
					<table style="display:none;width:100%;" id="fwhdcxlb" name="fwhdcxlb" moltivals="modelDeletVal" ref="fwhdcx" refQuery="fwhdcxTable" >
						<thead>
							<tr>
								<th type="single" name="XH" unique="RELATION_ID"></th>
								<th fieldname="MODELS_CODE" >车型代码</th>
								<th fieldname="MODELS_NAME" >车型名称</th>
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
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
					<li id="dia-nextH2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
		<!-- 服务活动车龄TAB -->
		<div id="clxxC">
			<div class="">
			<div class="">
			  <div align="left">
			    <form method="post" id="dia-fm-item"></form>
			    <fieldset>
				  <div id="fwhdcl">
					 <table width="100%" id="fwhdcllb" name="fwhdcllb" edit="true" style="display:none"  ref="fwhdcl">
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="START_DATE" freadonly="true" colWidth="80" ftype="input"  fdatasource="START_DATE">起始日期</th>
								<th fieldname="END_DATE" freadonly="true" colWidth="80" ftype="input"  fdatasource="END_DATE">结束日期</th>
								<th fieldname="VEHAGE_TYPE" freadonly="true" colWidth="80" fsrc="RQLX">日期类型</th>
							</tr>
						</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					</fieldset>
					<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
						<li id="dia-nextH3"><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
				</div>
				</div>
			</div>
		</div>
		<!-- 服务活动附件TAB -->
		<div  id="fjxxC">
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdfj" method="post">
						<div class="searchBar" align="left">
							<table class="searchContent" id="fm-fwhdfjTable">
							</table>
						</div>
					</form>
				</div>
			<div class="page">
			<div class="pageContent">  
				<form method="post" id="dia_fm_atta" class="editForm" >
				</form>
				<div align="left">
              	<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-spdfj')">&nbsp;已传附件列表&gt;&gt;</a></legend>
					<div id="dia-files">
					<table style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files"  >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="FJMC" >附件名称</th>
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
					<li id="dia-nextH4"><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一步</button></div></div></li>
					<li ><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
			</div>
			</div>
		</div>
		<!-- 服务活动工时TAB -->
		<div id="gsxxC">
			<div class="page">
			<div class="pageHeader">
			</div>
			<div class="pageContent">
				<div id="fwhdxmgs">
					<table width="100%" id="fwhdxmgslb" name="fwhdxmgslb" ref="fwhdxmgs" multivals="timeDeletVal" style="display: none" refQuery="fwhdxmgsTable" >
						<thead>
							<tr>
								<th type="single" name="XH" unique="RELATION_ID"></th>
								<th fieldname="TASK_TIME_CODE" >工时代码</th>
								<th fieldname="TASK_TIME_NAME" >工时名称</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
				</table>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
						<li id="dia-nextH5"><div class="button"><div class="buttonContent"><button type="button" id="dia-next4" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
		</div>
		<!-- 服务活动配件TAB -->
		<div id="pjxxC">
			<div class="page">
			<div class="pageContent">
				<div id="fwhdxmpj">
					<table width="100%" id="fwhdxmpjlb" name="fwhdxmpjlb" multivals="partDeleteVal" ref="fwhdxmpj"  style="display: none"  refQuery="fm-fwhdxmpjTable">
						<thead>
							<tr>
								<th type="single" name="XH" unique="RELATION_ID"></th>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="QUANTITY" >数量</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
				</table>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre5" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>