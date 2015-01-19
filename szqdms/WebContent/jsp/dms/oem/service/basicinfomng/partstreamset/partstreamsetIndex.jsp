<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.org.dms.common.DicConstant"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件流水号设置</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/PartStreamSetMngAction/search.ajax";
var setAction = "<%=request.getContextPath()%>/service/basicinfomng/PartStreamSetMngAction";

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchPartStreamSet");//获取页面提交请求的form对象
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
    	 * @"yhlb":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-partstreamsetlist");
	});
	
	//批量设置流水号为"是"
	$("#btn-batchSet").bind("click", function(event){
		var relationIds = $("#val0").val();
		if (relationIds) 
		{
			var batchSetUrl = setAction + "/batchSet.ajax?relationIds="+relationIds+"&ifStream="+'<%=DicConstant.SF_01%>';
			sendPost(batchSetUrl,"btn-batchSet","",batchSetCallBack,"true");
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	});
	
	//批量设置流水号为"否"
	$("#btn-batchSetNo").bind("click", function(event){
		var relationIds = $("#val0").val();
		if (relationIds) 
		{
			var batchSetUrl = setAction + "/batchSet.ajax?relationIds="+relationIds+"&ifStream="+'<%=DicConstant.SF_02%>';
			sendPost(batchSetUrl,"btn-batchSetNo","",batchSetCallBack,"true");
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	});	
	
	//导出Excel按钮响应
	$("#btn-expExcel").bind("click",function(){
		var $f = $("#fm-searchPartStreamSet");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/basicinfomng/PartStreamSetMngAction/download.do");
		$("#exportFm").submit();
	});
});

//设置流水号"是"方法，rowobj：行对象，非jquery类型
function doSet(rowobj)
{
	//先清空复选框
	$("#val0").val("");
	$("#tab-partstreamsetlist_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	});
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	$("#tab-partstreamsetlist_content input[type=checkbox][checked]").each(function(){
		$("#val0").val($(this).val());
	});
	
	var selectedRows = $("#tab-partstreamsetlist").getSelectedRows();
	setEditValue("fm-partstreamsetInfo",selectedRows[0].attr("rowdata"));
	
	var $f = $("#fm-partstreamsetInfo");
	//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
	if (submitForm($f) == false) return false;
	var sCondition = {};
	//将需要提交的内容拼接成json
	sCondition = $("#fm-partstreamsetInfo").combined(1) || {};
		
	var setUrl = setAction + "/set.ajax?ifStream="+'<%=DicConstant.SF_01%>';
	doNormalSubmit($f,setUrl,"set",sCondition,setCallBack);
}

//设置流水号"否"方法，rowobj：行对象，非jquery类型
function doSetNo(rowobj)
{ 
	//先清空复选框
	$("#val0").val("");
	$("#tab-partstreamsetlist_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	});

	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	$("#tab-partstreamsetlist_content input[type=checkbox][checked]").each(function(){
		$("#val0").val($(this).val());
	});
	
	var selectedRows = $("#tab-partstreamsetlist").getSelectedRows();
	setEditValue("fm-partstreamsetInfo",selectedRows[0].attr("rowdata"));
	
	var $f = $("#fm-partstreamsetInfo");
	//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
	if (submitForm($f) == false) return false;
	var sCondition = {};
	//将需要提交的内容拼接成json
	sCondition = $("#fm-partstreamsetInfo").combined(1) || {};
		
	var setUrl = setAction + "/set.ajax?ifStream=" +'<%=DicConstant.SF_02%>';
	doNormalSubmit($f,setUrl,"set",sCondition,setCallBack);
}

//单个设置流水号回调方法
function setCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-partstreamsetlist").getSelectedIndex();
		$("#tab-partstreamsetlist").updateResult(res,selectedIndex);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//批量设置回调函数,重新进行一次查询,回显到页面
function batchSetCallBack(res)
{
	try
	{   
	 	$("#btn-search").trigger("click");
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
	var mxid = $(checkbox).val();
	arr.push(mxid);
	multiSelected($checkbox,arr);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;配件流水号设置</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchPartStreamSet" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchPartStreamSet">
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="P.PART_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="P.PART_NAME" operation="like" datatype="1,is_null,30" /></td>
							<td><label>流水号设置：</label></td>
							<td>
		        				<select id="ifStream"  name="ifStream" datasource="RL.IF_STREAM" kind="dic" src="SF" datatype="1,is_null,6" >
				    				<option value="-1" selected>--</option>
				    				<option value="<%=DicConstant.SF_01 %>" >是</option>
				    				<option value="<%=DicConstant.SF_02 %>" >否</option>
				    			</select>
		        			</td>
						</tr>	
						<tr>
							<td><label>供应商代码：</label></td>
							<td><input type="text" id="supplierCode" name="supplierCode" datasource="S.SUPPLIER_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="S.SUPPLIER_NAME" operation="like" datatype="1,is_null,30" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchSet">批量选是</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchSetNo">批量选否</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expExcel">导出Excel</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_partstreamsetlist">
				<table style="display:none;width:100%;" id="tab-partstreamsetlist" name="tablist" ref="page_partstreamsetlist" refQuery="tab-searchPartStreamSet">					
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="RELATION_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_CODE">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<th fieldname="IF_STREAM">是否设置流水号</th>
							<th colwidth="85" type="link" title="[是]|[否]"  action="doSet|doSetNo" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>	
				</table>
			</div>
			
			<!-- 将复选框序号的值存放到table里 -->
			<table style="display:none">
				<tr>
					<td>
						<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
					</td>
				</tr>
			</table>
			
		</div>
	
		<!-- start******将修改后隐藏域的值传递到后台 -->
		<div id="dia-layout">
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="fm-partstreamsetInfo" class="editForm" >
		        	<!-- 隐藏域 -->
				    <tr><td><input type="hidden" id="dia-relationId" name="dia-relationId" datasource="RELATION_ID" /></td></tr>
				    <tr><td><input type="hidden" id="dia-partId" name="dia-partId" datasource="PART_ID" /></td></tr>
				    <tr><td><input type="hidden" id="dia-supplierId" name="dia-supplierId" datasource="SUPPLIER_ID" /></td></tr>
				    <tr><td><input type="hidden" id="dia-partCode" name="dia-partCode" datasource="PART_CODE" /></td></tr>
				    <tr><td><input type="hidden" id="dia-partName" name="dia-partName" datasource="PART_NAME" /></td></tr>
				    <tr><td><input type="hidden" id="dia-supplierCode" name="dia-supplierCode" datasource="SUPPLIER_CODE" /></td></tr>
				    <tr><td><input type="hidden" id="dia-supplierName" name="dia-supplierName" datasource="SUPPLIER_NAME" /></td></tr>
				    <tr><td><input type="hidden" id="dia-ifStream" name="dia-ifStream"  datasource="IF_STREAM" /></td></tr>		
				</form>	
			</div>
			</div>
		</div>
		<!-- end****** -->		
		
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>