<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件库区与供应商关系管理</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaSupplierMngAction/search.ajax";
var searchNewSupplierUrl = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaSupplierMngAction/searchNewSupplier.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaSupplierMngAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:400,height:170,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchOldpartWarehouseAreaSupplier");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-oldpartWarehouseAreaSupplierList");
	});
	
	//批量新增按钮
	$("#btn-batchAdd").bind("click", function(event){ 
		
		//先清空val0的内容
		$("#val0").val("");
		$("#tab-oldpartWarehouseAreaSupplierList_content input[type=checkbox]").each(function(){
 			$(this).attr("checked",false);
  		});
  	
		var diaNewSupplierOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/oldpartwarehouseareasupplier/oldpartWarehouseAreaSupplierBatchAdd.jsp?action=1", "查询未绑定供应商", "查询未绑定供应商", diaNewSupplierOptions);
	});
	
	//批量修改按钮
	$("#btn-batchUpdate").bind("click", function(event){ 
		
		//通过全选取值
		var relationIds=$("#val0").val();
		if (relationIds) 
		{
			//通过全选取值                                                                   
			var diaUpdateOptions = {max:false,width:400,height:140,mask:true,mixable:true,minable:true,resizable:true,drawable:true};                                                             
			$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/oldpartwarehouseareasupplier/oldpartWarehouseAreaSupplierBatchUpdate.jsp?relationIds="+relationIds, "修改供应商旧件库区", "修改供应商旧件库区", diaAddOptions);
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 	
	});
	
	//批量删除按钮
	$("#btn-batchDelete").bind("click", function(event){
		var relationIds = $("#val0").val();
		if (relationIds) 
		{
			var url = deleteUrl + "?relationIds="+relationIds;
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
	 	
	 	//清空val0的内容
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//单个编辑
function doUpdate(rowobj)
{   
    //先清空val0的内容
	$("#val0").val("");
	$("#tab-oldpartWarehouseAreaSupplierList_content input[type=checkbox]").each(function(){
 		$(this).attr("checked",false);
  	});
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	$("#tab-oldpartWarehouseAreaSupplierList_content input[type=checkbox]").each(function(){
 		$("#val0").val($(this).val());
  	});
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/oldpartwarehouseareasupplier/oldpartWarehouseAreaSupplierUpdate.jsp?action=2", "修改供应商旧件库区", "修改供应商旧件库区", diaAddOptions);
}

var $row;
function doDelete(rowobj)
{
	$("#val0").val("");
	$("#tab-oldpartWarehouseAreaSupplierList_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	});
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	
	$row = $(rowobj);
	$("#val0").val($(rowobj).attr("RELATION_ID"));
	var url = deleteUrl + "?relationIds="+$(rowobj).attr("RELATION_ID");
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
		    
		    //先清空val0的内容
			$("#val0").val("");
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 供应商旧件库区管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchOldpartWarehouseAreaSupplier">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchOldpartWarehouseAreaSupplier">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>旧件库区代码：</label></td>
					    <td>
					    	<input type="text" id="areaCode"  name="areaCode" datasource="AREA_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>旧件库区名称：</label></td>
					    <td>
					    	<input type="text" id="areaName" name="areaName" datasource="AREA_NAME" datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
					<tr>
						<td><label>供应商代码：</label></td>
					    <td>
					    	<input type="text" id="supplierCode"  name="supplierCode" datasource="SUPPLIER_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>供应商名称：</label></td>
					    <td colspan="3">
					    	<input type="text" id="supplierName"  name="supplierName" datasource="SUPPLIER_NAME"  datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
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
		<div id="page_oldpartWarehouseAreaSupplierList" >
			<table style="display:none;width:100%;" id="tab-oldpartWarehouseAreaSupplierList" name="tablist" ref="page_oldpartWarehouseAreaSupplierList" refQuery="tab-searchOldpartWarehouseAreaSupplier" >
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="RELATION_ID" ></th>
							<th fieldname="AREA_CODE" >旧件库区代码</th>
							<th fieldname="AREA_NAME" >旧件库区名称</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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
		
	</div>
	</div>
</div>	
</body>
</html>