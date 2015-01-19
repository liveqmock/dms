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
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<script type="text/javascript">
var action = "<%=action%>";
var $modelsTrue=true;
var $provinceTrue=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction";
var fileSearchUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction/fileSearch.ajax";

$(function(){
	//初始化页面，根据Index页面传过来action的值确定执行哪段逻辑
	if(action != "1")
	{
	 $("#dia-strategyCode").attr("readonly",true);  
	 $("#cxxxH").show();
	 $("#sfxxH").show();
	 $("#dia-nextH1").show();
	
		var selectedRows = parent.$("#tab-strategyList").getSelectedRows();
		setEditValue("fm-strategyBaInfo",selectedRows[0].attr("rowdata"));
	}
	else{
		$("#cxxxH").hide();
		$("#sfxxH").hide();
		$("#dia-nextH1").hide();
	}
	
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-strategyBaInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false){
			return false;
		} 
		
		//开始时间不能大于结束时间
		var kssj =$("#dia-beginDate").val();
		var jssj =$("#dia-endDate").val();
		if(jssj==""){
			jssj=0;
		}
		if(kssj==""){
			kssj=0;
		}
		if(kssj>jssj){
			alertMsg.warn("开始日期不可大于结束日期！");
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-strategyBaInfo").combined(1) || {};
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
	
	//三包车型批量删除按钮响应
	$("#deleteModels").bind("click", function(event)
	{
		var relationIds=$("#val0").val();
	    if(relationIds=="")
	    {
	    	alertMsg.warn("请选择车型！");
	    	return false;
	    }else{
	    	var modelsUrl =diaSaveAction+"/deleteModels.ajax?relationIds="+relationIds;
			sendPost(modelsUrl,"deleteModels","",deleteModelsCallBack,"true");
	    }
	});
	
	//三包省份批量删除按钮响应
	$("#deleteProvince").bind("click", function(event)
	{
		var relationIds=$("#val1").val();
	    if(relationIds=="")
	    {
	    	alertMsg.warn("请选择车型！");
	    	return false;
	    }else{
	    	var provinceUrl =diaSaveAction+"/deleteProvince.ajax?relationIds="+relationIds;
			sendPost(provinceUrl,"deleteProvince","",deleteProvinceCallBack,"true");
	    }
	});

	//三包车型查询按钮响应 
	$("#btn-searchStrategyModels").bind("click", function(event){
		var $f = $("#fm-searchStrategyModelsInfo");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchStrategyModelsUrl =diaSaveAction+"/searchStrategyModels.ajax?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,searchStrategyModelsUrl,"btn-searchStrategyModels",1,sCondition,"strategyModelsList");
		});
		
	//三包省份查询按钮响应 
	$("#btn-searchStrategyProvince").bind("click", function(event){
		var $f = $("#fm-searchStrategyProvinceInfo");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchStrategyProvinceUrl =diaSaveAction+"/searchStrategyProvince.ajax?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,searchStrategyProvinceUrl,"btn-searchStrategyProvince",1,sCondition,"strategyProvinceList");
		});

	//新增车型页面
	$("#addModels").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/strategy/strategyModelsAdd.jsp", "addModels", "三包策略车型新增", options);
	});
	
	//新增省份页面
	$("#addProvince").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/strategy/strategyProvinceAdd.jsp", "addProvince", "三包策略省份新增", options);
	});

	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("Strategy");
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
				//点击下一步第一次查询，再次点击不查询
				if($modelsTrue){
					//查询车型
					var $f = $("");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
				  	sCondition = $f.combined() || {};
					var searchStrategyModelsUrl =diaSaveAction+"/searchStrategyModels.ajax?&strategyId="+$("#dia-strategyId").val();
					doFormSubmit($f,searchStrategyModelsUrl,"btn-searchStrategyModels",1,sCondition,"strategyModelsList");
				}
				$modelsTrue=false;
				break;
			case 1: 
				if($provinceTrue){
					var $f = $("");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
					//combined()：实现将页面条件按规则拼接成json
			    	sCondition = $f.combined() || {};
			    	var searchStrategyProvinceUrl =diaSaveAction+"/searchStrategyProvince.ajax?&strategyId="+$("#dia-strategyId").val();
					doFormSubmit($f,searchStrategyProvinceUrl,"btn-searchStrategyProvince",1,sCondition,"strategyProvinceList");
				}
				$provinceTrue=false;
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

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{	
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var strategyId =getNodeText(rows[0].getElementsByTagName("STRATEGY_ID").item(0));
			//var strategyCode =getNodeText(rows[0].getElementsByTagName("STRATEGY_CODE").item(0));
			$("#dia-strategyId").val(strategyId);
			$("#dia-strategyCode").attr("readonly",true); 
			//$("#dia-strategyCode").val(strategyCode);
			$("#cxxxH").show();
		    $("#sfxxH").show();
		    $("#dia-nextH1").show();
		}else
		{
			return false;
		}
		parent.$("#tab-strategyList").insertResult(res,0);
		if(parent.$("#tab-strategyList_content").size()>0){
			$("td input[type=radio]",parent.$("#tab-strategyList_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#tab-strategyList").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
 
//更新回调函数,对于jsp,回调方法用parent.$
function diaUpdateCallBack(res)
{
	try
	{
		var selectedIndex = parent.$("#tab-strategyList").getSelectedIndex();
		parent.$("#tab-strategyList").updateResult(res,selectedIndex);			
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//车型批量删除回调方法
function  deleteModelsCallBack(res)
{
	try
	{
		var $f = $("#fm-searchStrategyModelsInfo");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchStrategyModelsUrl =diaSaveAction+"/searchStrategyModels.ajax?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,searchStrategyModelsUrl,"btn-searchStrategyModels",1,sCondition,"strategyModelsList");
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//省份批量删除回调方法
function  deleteProvinceCallBack(res)
{
	try
	{		
		var $f = $("#fm-searchStrategyProvinceInfo");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchStrategyProvinceUrl =diaSaveAction+"/searchStrategyProvince.ajax?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,searchStrategyProvinceUrl,"btn-searchStrategyProvince",1,sCondition,"strategyProvinceList");	
		$("#val1").val("");
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
	var $checkbox = $(checkbox);
	var arr = [];
	
	while($checkbox[0].tagName != "TABLE"){
		$checkbox = $checkbox.parent();
	}
	if($checkbox.attr("id").indexOf("strategyModelsList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#modelDeletVal"));
	}
	if($checkbox.attr("id").indexOf("strategyProvinceList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#provinceDeletVal"));
	}
	if($checkbox.attr("id").indexOf("tab-searchModelsList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("MODELS_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#newModelsDeletVal"));
	}
	if($checkbox.attr("id").indexOf("tab-searchProvinceList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("DM"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#newProvinceDeletVal"));
	}
}

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   

	//给三包规则隐藏域赋值
	if(id=="dia-ruleCode") 
	{
		$("#dia-ruleId").val($row.attr("RULE_ID"));
	}
	return true;
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
					<li><a href="javascript:void(0)"><span>三包策略信息</span></a></li>
					<li id="cxxxH"><a href="javascript:void(0)"><span>车型信息</span></a></li>
					<li id="sfxxH"><a href="javascript:void(0)"><span>省份信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
		<!-- 三包策略基础TAB -->
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-strategyBaInfo" class="editForm" >
					<input type="hidden" id="dia-strategyId" name="dia-strategyId" datasource="STRATEGY_ID" />
					<div align="left">
					<fieldset>
					<table class="editTable" id="tab-strategyBaInfo">
					    <tr>
							<td><label>三包策略代码：</label></td>
							<td><input type="text" id="dia-strategyCode" name="dia-strategyCode" datasource="STRATEGY_CODE" datatype="0,is_digit_letter,30" /></td>
							<td><label>三包策略名称：</label></td>
							<td><input type="text" id="dia-strategyName" name="dia-strategyName" datasource="STRATEGY_NAME" datatype="0,is_null,30" /></td>
						</tr>
						<tr>
						    <td><label>策略日期：</label></td>
						    <td>
					    		<input type="text" id="dia-beginDate" group="dia-beginDate,dia-endDate"  style="width:75px;" name="dia-beginDate" datasource="BEGIN_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="dia-endDate" group="dia-beginDate,dia-endDate"  style="width:75px;margin-left:-30px;" name="dia-endDate" datasource="END_DATE" datatype="0,is_date,30" onclick="WdatePicker()" />
					   		</td>
					 	</tr>
					 	<tr>
                   	 		<td><label>三包规则：</label></td>
							<td><input type="text" id="dia-ruleCode" name="dia-ruleCode" datasource="RULE_CODE" operation="like" kind="dic" src="T#SE_BA_RULE:RULE_CODE:RULE_NAME{RULE_ID}:STATUS=100201" datatype="0,is_null,30" /> 
								<input type="hidden" id="dia-ruleId" name="dia-ruleId" datasource="RULE_ID"/>						
							</td>
                   	 		<td><label>有效标示：</label></td>
							<td>
		        				<select id="dia-status"  name="dia-status" datasource="STATUS" kind="dic" src="YXBS" datatype="0,is_null,6" >
				    			<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    			</select>
		        			</td>
                   	 	</tr>
		                <tr>
							<td><label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label></td>
							<td colspan="3">
								<textarea id="dia-remarks" style="width:100%" name="dia-remarks" datasource="REMARKS"  datatype="1,is_null,1000"></textarea>
							</td>			
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
			
			<!-- 三包策略车型TAB -->
			<div class="page">
			<div class="pageHeader">
				<form id="fm-searchStrategyModelsInfo" method="post">
					<div class="searchBar" align="left">
						<table class="searchContent" id="tab-searchStrategyModelsInfo">
							<tr><td><label>车型代码：</label></td>
								<td><input type="text" id="dia-modelsCode" name="dia-modelsCode" datasource="MODELS_CODE" datatype="1,is_null,30" operation="like" /></td>
								<td><label>车型名称：</label></td>
								<td><input type="text" id="dia-modelsName" name="dia-modelsName" datasource="MODELS_NAME" datatype="1,is_null,30" value="" operation="like"  /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchStrategyModels">查&nbsp;&nbsp;询</button></div></div></li>
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
					<li><a class="add" href="javascript:void(0);" id="addModels" title=""><span>批量新增</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" href="javascript:void(0);" id="deleteModels" title="确定要删除吗?"><span>批量删除</span></a></li>
				</ul>
			</div>
			<div class="pageContent">
				<div id="strategyModels">
					<table style="display:none;width:100%;" id="strategyModelsList" name="strategyModelsList" moltivals="modelDeletVal" ref="strategyModels" refQuery="tab-searchStrategyModelsInfo" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="STRATEGY_CODE" >三包代码</th>
								<th fieldname="STRATEGY_NAME" >三包名称</th>
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
			
			<!-- 三包策略省份TAB -->
			<div class="page">
			<div class="pageHeader">
				<form id="fm-searchStrategyProvinceInfo" method="post">
					<div class="searchBar" align="left">
						<table class="searchContent" id="tab-searchStrategyProvinceInfo">
							<tr>
								<td><label>省份：</label></td>
								<td><input type="text" id="dia-mc" name="dia-mc" datasource="MC" datatype="1,is_null,60" operation="like" /></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchStrategyProvince">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="panelBar">
				<ul class="toolBar">
					<li class="line">line</li>
					<li><a class="add" href="javascript:void(0);" id="addProvince" title=""><span>批量新增</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" href="javascript:void(0);" id="deleteProvince" title="确定要删除吗?"><span>批量删除</span></a></li>
				</ul>
			</div>
			<div class="pageContent">
				<div id="strategyProvince">
					<table style="display:none;width:100%;" id="strategyProvinceList" name="strategyProvinceList" moltivals="provinceDeletVal" ref="strategyProvince" refQuery="tab-searchStrategyProvinceInfo" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="MC" >省份</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="provinceDeletVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val1" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
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
	<form id="dialog-fm-download"style="display:none">
	</form>
</div>
</body>
</html>