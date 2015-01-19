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
<title>三包规则</title>
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
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/RuleMngAction";
var fileSearchUrl = "<%=request.getContextPath()%>/service/basicinfomng/RuleTypeMngAction/fileSearch.ajax";
var rulePartAction = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction";

$(function(){
	//初始化页面，根据Index页面传过来action的值确定执行哪段逻辑
	if(action != "1")
	{  
	 $("#pjxxh").show();
	 $("#dia-nextH1").show();
		//进入修改页面需要根据是否索赔与是否固定费用确定需要显示的tab页面
		
	
		var selectedRows = parent.$("#tab-Rule").getSelectedRows();
		setEditValue("fm-RuleInfo",selectedRows[0].attr("rowdata"));
	}
	else{
		$("#pjxxh").hide();
		$("#dia-nextH1").hide();
	}
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-RuleInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false){
			return false;
		} 
	
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-RuleInfo").combined(1) || {};
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
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=rulePart.xls");
        window.location.href = url;
    });
	//导入
	$("#dia-imp").bind("click",function(){
		var activityId=$("#dia_RULE_ID").val();
        importXls("SE_BA_RULE_PART_TMP","",8,3,"/jsp/dms/oem/service/basicinfomng/rule/importSuccess.jsp");
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	$("#dia_se_rule_id").val($("#dia_RULE_ID").val());
    	var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
	$("#searchRulePart").click(function(){
		var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceProtUrl =rulePartAction+"/search.ajax?&ruleId="+$("#dia_RULE_ID").val();
		doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
	}); 
	 
	$("#deleteVin").bind("click", function(event)
	{
		var mxids=$("#val3").val();
	    if(mxids=="")
	    {
	    	 alertMsg.warn("请选择规则！");
	    	return false;
	    }else{
	    	var scgsUrl =rulePartAction+"/deleteRulePart.ajax?mxids="+mxids;
			sendPost(scgsUrl,"deleteVin","",deletePartCallBack,"true");
	    }
	});
	 
/* 	$("#searchPart").click(function(){
		var $f = $("#fm-fwhdxmpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =rulePartAction+"/searchPart.ajax?&ruleId="+$("#dia_RULE_ID").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
	}); */
 
	//上传附件
	$("#addAtt").bind("click",function(){
		var activityId=$("#dia_RULE_ID").val();
		$.filestore.open(activityId,{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
	//新增配件页面
	$("#addPart").bind("click",function(){
		var options = {max:false,width:800,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/rule/RulePartAdd.jsp", "RulePart", "三包规则配件新增", options);
	});
	//jsp关闭,与DIV不同
	var dialog = parent.$("body").data("Rule");
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
			    	var searchServiceProtUrl =rulePartAction+"/search.ajax?&ruleId="+$("#dia_RULE_ID").val();
					doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
				}
					$vinTrue=false;
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
//导入回调方法
function impCall(){
	var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	//combined()：实现将页面条件按规则拼接成json
	sCondition = $f.combined() || {};
	var searchServiceProtUrl =rulePartAction+"/searchPart.ajax?&ruleId="+$("#dia_RULE_ID").val();
	doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
}
//附件回调方法
function fjUpCallBack(fjid){
	var $f = $("#dia_fm_atta");
	var sCondition = {};
	sCondition = $f.combined() || {};
	var fileSearchUrl1 =fileSearchUrl+"?ruleId="+$("#dia_RULE_ID").val();
	doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
}
 
function diaInsertCallBack(res)
{
	try
	{	
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var activityId =getNodeText(rows[0].getElementsByTagName("RULE_ID").item(0));
			var dia_RULE_CODE =getNodeText(rows[0].getElementsByTagName("RULE_CODE").item(0));
			$("#dia_RULE_ID").val(activityId);
			$("#dia_RULE_CODE").val(dia_RULE_CODE);
		}else
		{
			return false;
		}
		parent.$("#tab-Rule").insertResult(res,0);
		if(parent.$("#tab-Rule_content").size()>0){
			$("td input[type=radio]",parent.$("#tab-Rule_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",parent.$("#tab-Rule").find("tr").eq(0)).attr("checked",true);
		}
		action=2;
		 $("#pjxxh").show();
		 $("#dia-nextH1").show();
		
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
			var activityId =getNodeText(rows[0].getElementsByTagName("RULE_ID").item(0));
			var dia_RULE_CODE =getNodeText(rows[0].getElementsByTagName("RULE_CODE").item(0));
			$("#dia_RULE_ID").val(activityId);
			$("#dia_RULE_CODE").val(dia_RULE_CODE);
			var $f = $("");//获取页面提交请求的form对象
			var sCondition = {};//定义json条件对象
			//combined()：实现将页面条件按规则拼接成json
	    	sCondition = $f.combined() || {};
	    	var searchServiceProtUrl =diaSaveAction+"/search.ajax?&rule_id="+$("#dia_RULE_ID").val();
			//doFormSubmit($f,searchServiceProtUrl,"",1,sCondition,"fwhdcllb");fwhdxmpjlb
	    	doFormSubmit($f,searchServiceProtUrl,"",1,sCondition,"tablist");
		}else
		{
			return false;
		}
		var selectedIndex = parent.$("#tab-Rule").getSelectedIndex();
		parent.$("#tab-Rule").updateResult(res,selectedIndex); 
		
		$("#pjxxh").show();
<%-- 		if($("#DI_SFSP").val()==<%=DicConstant.SF_01%>){
			projectShow();
		}
		if($("#SFGDFY").val()==<%=DicConstant.SF_01%>){
			projectHide();
		} --%>
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
 
function  deletePartCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServiceProtUrl =rulePartAction+"/search.ajax?&ruleId="+$("#dia_RULE_ID").val();
		doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
		$("#val3").val("");
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

//列表复选
function doCheckbox(checkbox){  
	var $t = $(checkbox);
	var arr = [];
   	while($t.get(0).tagName != "TABLE"){
		$t = $t.parent();
	} 
	if($t.attr("id").indexOf("fwhdxmpjlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("RELATION_ID"));
		arr.push($tr.attr("PART_CODE"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#pjDeleteVal"));
	} 
	if($t.attr("id").indexOf("tab-partList")==0){
	     
		var $tr = $(checkbox).parent().parent().parent();	
        var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
        var s = "";
        if($tr.find("td").eq(4).find("input:first").size()>0)
            s = $tr.find("td").eq(4).find("input:first").val();
        else
            s = $tr.find("td").eq(4).text();
     
        var mileages = "";
        if($tr.find("td").eq(5).find("input:first").size()>0)
            mileages = $tr.find("td").eq(5).find("input:first").val();
        else
            mileages = $tr.find("td").eq(5).text();	         
        
     
        
       //  var checkObj = $("input:first",$tr.find("td").eq(0));
       //  var obj=$("input[id$='"+$t.attr("id")+"']")[0];
         if(s==""&&mileages==""){
		   alertMsg.warn("请输入输入月份和历程！");
		   $tr.find("td input[type=checkbox]").attr("checked",false);
		   //$("td input[type=checkbox]",$tr).attr("checked",false);
		  return false;
         }else if(s==""||mileages==""){   
         $tr.find("td input[type=checkbox]").attr("checked",false);
         } 
		 
            
        var arr = [];
       
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push(s);
        arr.push(mileages);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        var $checkbox2 = $tr.find("td").eq(2).find("input[type='checkbox']:first");
        if(s!=""&&mileages!=""){
          // $tr.find("td input[type=checkbox]").attr("checked",true); 
           multiSelected($(checkbox), arr,$("#partVals"));
           
        if(!$tr.find("td input[type=checkbox]").attr("checked")){
         $tr.find("td").eq(4).html("<div><input type='text' name='MONTHS' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
          $tr.find("td").eq(5).html("<div><input type='text' name='MILEAGE' onblur='doMyInputBlur(this);' maxlength='6' value='"+mileages+"'/></div>");
        }else{
          $tr.find("td").eq(4).html("<div>"+s+"</div>");
            $tr.find("td").eq(5).html("<div>"+mileages+"</div>");
        
        }     
/*         //设置input框显示或文本只读
        if($checkbox.is(":checked")){
           $tr.find("td").eq(4).html("<div>"+s+"</div>");
        }
         
        else
        {
            $tr.find("td").eq(4).html("<div><input type='text' name='MONTHS' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
           
        } */
        
     /*       //设置input框显示或文本只读
        if($checkbox2.is(":checked")){
           $tr.find("td").eq(5).html("<div>"+mileages+"</div>");
        }
         
        else
        {
            $tr.find("td").eq(5).html("<div><input type='text' name='MILEAGE' onblur='doMyInputBlur(this);' maxlength='6' value='"+mileages+"'/></div>");
           
        }  */
        
        }
     
	} 
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
					<li><a href="javascript:void(0)"><span>三包规则信息</span></a></li>
					<li id="pjxxh"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
		<!-- 三包规则TAB -->
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-RuleInfo" class="editForm" >
					<input type="hidden" id="dia_RULE_ID" name="dia_RULE_ID" datasource="RULE_ID" />
					<div align="left">
					<fieldset>
					<table class="editTable" id="RuleInfo">
					    <tr>
							<td><label>三包规则代码：</label></td>
							<td><input type="text" id="dia_RULE_CODE" name="dia_RULE_CODE" datasource="RULE_CODE"  datatype="0,is_digit_letter,30" /></td>
							<td><label>三包规则名称：</label></td>
							<td colspan="5"><input type="text" id="dia_RULE_NAME" name="dia_RULE_NAME" datasource="RULE_NAME" datatype="0,is_null,30" /></td>
						</tr>
					
						<tr>
					<td><label>用户类别：</label></td>
					<td>
					<select type="text" class="combox" id="dia-USER_TYPE" name="dia-USER_TYPE" kind="dic" src="CLYHLX" datasource="USER_TYPE" datatype="0,is_null,10" >

						 <option value="-1" selected>--</option>	
						</select>
					</td> 	
					<td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
				     <option value="-1" selected>--</option>				 
				    </select>
			    	</td>
						</tr>
					
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="5"><textarea class="" rows="2" id="dia-REMARKS" name="dia-REMARKS" datasource="REMARKS" style="width:100%" datatype="1,is_null,1000"></textarea></td>
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
			<!-- 三包规则配件信息TAB -->
			<div class="page">
				<div class="pageHeader">
					<form id="fm-fwhdvinfy" method="post">
					<input type="hidden" id="dia_se_rule_id" name="dia_se_rule_id" datasource="T.RULE_ID" />
						<div class="searchBar" align="left">
							<table class="searchContent" id="fwhdvinfyTable">
								<tr>
									<td><label>配件代码：</label></td>
						<td><input type="text" id="dia_partCode" name="dia_partCode" datasource="T.PART_CODE" operation="like" datatype="1,is_null,100"  value="" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="dia_partName" name="dia_partName" datasource="T.PART_NAME" operation="like" datatype="1,is_null,100"  value="" /></td>
								</tr>
							</table>
							<div class="subBar">
								<ul>
									<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="searchRulePart">查&nbsp;&nbsp;询</button></div></div></li>
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
						<li><a class="delete" href="javascript:void(0);" id="deleteVin" title="确定要删除吗?"><span>批量删除</span></a></li>
						<li class="line">line</li>
						<li><a class="icon" href="javascript:void(0);" id="download" title="确定要下载导入模版吗?"><span>下载导入模板</span></a></li>
						<li class="line">line</li>
						<li><a class="icon" href="javascript:void(0);" id="btn-export-index" title="确定要导出吗?"><span>导出数据</span></a></li>
						<li class="line">line</li>
						<li><a class="add" href="javascript:void(0);" id="dia-imp" title="确定要导入吗?"><span>导入数据</span></a></li>
					</ul>
				</div>
				<div id="fwhdxm">
					<table width="100%" id="fwhdxmpjlb" name="fwhdxmpjlb" multivals="pjDeleteVal" ref="fwhdxm"  style="display: none"  refQuery="fwhdxmqtfyTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="RELATION_ID"></th>
								<th fieldname="RULE_CODE" >规则代码</th>
								<th fieldname="RULE_NAME" >规则名称</th>
								<!-- 
								<th fieldname="PART_ID" >配件主键</th> -->
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="MONTHS" >月份</th>
								<th fieldname="MILEAGE" >里程</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			 
                <legend align="left" >&nbsp;[已选定三包规则]</legend>
				<div id="pjDeleteVal">
					<table style="display:">
						<tr>
							<td><textarea id="val3" name="multivals" style="width:400px;height:10px; display:none" column="1" style="" ></textarea></td>
							<td><textarea id="val4" name="multivals" style="width:400px;height:10px"  style="" ></textarea></td>
						</tr>
					</table>
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
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>