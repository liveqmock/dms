<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant"%>        
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>超单日期设置</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimMngAction/searchClaim.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimMngAction";
//定义弹出窗口样式
var options = {max:false,width:720,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
/* 	$("#search").click(function(){
		if($("#gzmslb").is(":hidden"))
		{
			$("#gzmslb").show();
			$("#gzmslb").jTable();
		}
	}); */
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
	
 
		var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});

	
	
		//批量新增
	$("#btn-Add-claim").bind("click", function(event){ 
		var claimIds=$("#val0").val();
		if(claimIds)
		{              
		    var $f = $("#fm-new-claim");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-new-claim").combined(1) || {};
			var addUrl = diaSaveAction + "/updateAll.ajax?claimIds=" + claimIds;
			doNormalSubmit($f,addUrl,"btn-Add-claim",sCondition,diaUpdateCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
	
		
/* 		//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/serviceparamng/exceeddate/ExceedDateAdd.jsp?action=1", "ExceedDate", "超单日期新增", options);
	}); */
});

//修改回调
function diaUpdateCallBack(res){
	try
	{		
	/* 	var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex); */
	$("#btn-search").trigger("click");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceparamng/exceeddate/ExceedDateAdd.jsp?action=2", "ExceedDate", "超单日期编辑", options);
}
//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var mxid = $(checkbox).val();
	arr.push(mxid);
	multiSelected($checkbox,arr);
}

function open(){
	var id="ORG_CODE";
	var busType=2;
	showOrgTree(id,busType);
}

/* //查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#cdrqlb").is(":hidden")){
			$("#cdrqlb").show();
			$("#cdrqlb").jTable();
		}
	});
});
function doDelete(){
	alertMsg.info("删除成功！");
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqxz.jsp?action=2", "cdrqxx", "超单日期编辑", options);
}
function doAdd(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqxz.jsp?action=1", "cdrqxx", "超单日期新增", options);
}
function open(){
	alertMsg.info("弹出服务商树");
} */
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;超单日期设置</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-search" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tb-search">
						<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="T.ORG_ID"  datatype="1,is_null,100" value=""  hasBtn="true" callFunction="open();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="T.ORG_NAME" datatype="1,is_null,100" readOnly value="" /></td>
					</tr>	
					<td><input type="hidden" id="IF_OVERDUE" name="IF_OVERDUE" datasource="T.IF_OVERDUE"  value="100101" /></td>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
<!-- 							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li> -->
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
					 
		 		            <th type="multi" name="CX-XH" style="align:center;" unique="CLAIM_ID"></th>
	  
								<th fieldname="ORG_ID" >服务商名称</th>
								<th fieldname="CLAIM_ID" >索赔主键</th>
							    <th fieldname="CLAIM_NO" >索赔单号</th>
								<th fieldname="OVERDUE_DAYS" >超单天数</th>
								<th fieldname="EXCEED_DAYS" >索赔单天数</th>							
								 
							
							<!-- <th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th> -->
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
			<table style="display:none">
			<tr><td>
				<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
			</td></tr>
		</table>
			<form method="post" id="fm-new-claim">
			<table class="editTable" id="tab-claim">
				<tr>
				    <td><label>超单天数：</label></td>
				    <td><input type="text" id="diaN-OVERDUE_DAYS"  name="diaN-OVERDUE_DAYS"  
				   datasource="OVERDUE_DAYS" datatype="0,is_null,32" />				 
				    </td>
				</tr>
			</table>
				<div class="searchBar" align="left" >
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-Add-claim" >超期天数变更</button></div></div></li>
						</ul>
					</div>
				</div>
		</form>
		</div>
	</div>
</div>
</body>
</html>