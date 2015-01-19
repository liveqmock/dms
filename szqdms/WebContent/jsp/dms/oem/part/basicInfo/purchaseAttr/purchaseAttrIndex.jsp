<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:采购员属性管理
	 Version:1.0
     Author：suoxiuli 2014-07-23
     Remark：buyer attribute
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购员属性管理</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PchAttributeAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PchAttributeAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:740,height:170,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//设置高度
	$("#tab-pchAttributeList").attr("layoutH",document.documentElement.clientHeight-170);
	
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchPchAttribute");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-pchAttributeList");
	});
	
	//批量新增按钮
	$("#btn-batchAdd").bind("click", function(event){
		$("#val0").val("");
		var diaNewPartOptions = {max:false,width:800,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/purchaseAttr/purchaseAttrBatchAdd.jsp?action=1", "查询新配件", "查询新配件", diaNewPartOptions);
	});  
	
	//批量更新按钮
	$("#btn-batchUpdate").bind("click", function(event){
		var pchAttributeIds = $("#val0").val();
		if (pchAttributeIds) 
		{
			//通过全选取值      
			var diaUpdateOptions = {max:false,width:400,height:140,mask:true,mixable:true,minable:true,resizable:true,drawable:true};                                                             
			$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/purchaseAttr/purchaseAttrBatchUpdate.jsp?pchAttributeIds="+pchAttributeIds, "更新采购员", "更新采购员", diaUpdateOptions);
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	})
	
	//批量删除按钮
	$("#btn-batchDelete").bind("click", function(event){
		var pchAttributeIds = $("#val0").val();
		if (pchAttributeIds) 
		{
			var url = deleteUrl + "?pchAttributeIds="+pchAttributeIds;
			sendPost(url,"btn-batchDelete","",batchDeleteCallBack,"true");                                                           
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	});                           
});

//批量删除回调函数
function batchDeleteCallBack(res)
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

//列表单个编辑连接
function doUpdate(rowobj)
{
	$("#val0").val("");
	$("#tab-pchAttributeList_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	})
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	$("#tab-pchAttributeList_content input[type=checkbox][checked]").each(function(){
		$("#val0").val($(this).val());
	});
	
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/purchaseAttr/purchaseAttrAdd.jsp?action=2", "修改采购员属性", "修改采购员属性", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$("#val0").val("");
	$("#tab-pchAttributeList_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	})
	
	$row = $(rowobj);
	$("#val0").val($(rowobj).attr("PCHATTRIBUTE_ID"));
	var url = deleteUrl + "?pchAttributeIds="+$(rowobj).attr("PCHATTRIBUTE_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
		{   
		    $("#btn-search").trigger("click");
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：  配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 采购员属性管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<!-- 提交查询请求form -->
		<form method="post" id="fm-searchPchAttribute">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchPchAttribute">
				<!-- 定义查询条件 -->
				<tr>
					<td><label>配件代码：</label></td>
				    <td>
				    	<input type="text" id="partCode"  name="partCode" datasource="PART_CODE"  datatype="1,is_null,30" operation="like" />
				   	</td>
				    <td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="partName" name="partName" datasource="PART_NAME" datatype="1,is_null,30" operation="like" /></td>
				    <td><label>采购员姓名：</label></td>
				    <td>
				    	<input type="text" id="userAccount"  name="userAccount" kind="dic" 
				    		src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000065' AND STATUS=100201" datasource="USER_ACCOUNT" datatype="1,is_null,32" />
				    	<input type="hidden" id="userName" name="userName" datasource="USER_NAME" />
				    </td>
				    <!-- td><label>有效标识：</label></td>
				    <td>
				    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
				    	<option value="100201" selected>有效</option>
				    	</select>
				    </td-->
				</tr>
<!--				<tr>-->
<!--					<td><label>采购员账号：</label></td>-->
<!--				    <td>-->
<!--				    	<input type="text" id="userAccount"  name="userAccount" datasource="USER_ACCOUNT"  datatype="1,is_null,32" operation="like" />-->
<!--				    </td>-->
<!--				    <td><label>采购员姓名：</label></td>-->
<!--				    <td>-->
<!--				    	<input type="text" id="userName" name="userName" datasource="USER_NAME" datatype="1,is_null,30" operation="like" />-->
<!--				    </td>-->
<!--				</tr>-->
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchAdd" >批量新增</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchUpdate" >批量更新</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchDelete" >批量删除</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_pchAttributeList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-pchAttributeList" name="tablist" ref="page_pchAttributeList" refQuery="tab-searchPchAttribute" >
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="PCHATTRIBUTE_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" maxlength="65">配件名称</th>
							<th fieldname="USER_NAME" colwidth="75"  >采购员姓名</th>
							<th fieldname="STATUS" >状态</th>
							<th colwidth="75" type="link" title="[编辑]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table style="display:none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	</div>
</div>	
</body>
</html>