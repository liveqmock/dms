<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:库管员属性管理
	 Version:1.0
     Author：suoxiuli 2014-07-15
     Remark：Warehouse Keeper
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>库管员属性管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction/search.ajax";
var searchNewPartUrl = "<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction/searchNewPart.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:740,height:170,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//设置高度
	$("#tab-warehouseKeeperList").attr("layoutH",document.documentElement.clientHeight-70);
	
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchWarehouseKeeper");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-warehouseKeeperList");
	});
	
	//批量新增按钮
	$("#btn-batchAdd").bind("click", function(event){ 
		$("#val0").val("");
		var diaNewPartOptions = {max:false,width:800,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/warehouseKeeper/warehouseKeeperBatchAdd.jsp?action=1", "查询新配件", "查询新配件", diaNewPartOptions);
		//true:代表的是打开的是一个jsp
		//$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/warehouseKeeper/warehouseKeeperBatchAdd.jsp?action=1", "查询新配件", "查询新配件", diaNewPartOptions,true);
	});
	
	//库管员批量更新
	$("#btn-batchUpdate").bind("click", function(event){ 
		
		//通过全选取值
		var keeperIds=$("#val0").val();
		if (keeperIds) 
		{
			//通过全选取值                                                                   
			var diaUpdateOptions = {max:false,width:400,height:140,mask:true,mixable:true,minable:true,resizable:true,drawable:true};                                                             
			$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/warehouseKeeper/warehouseKeeperBatchUpdate.jsp?keeperIds="+keeperIds, "编辑库管员属性", "编辑库管员属性", diaUpdateOptions);
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 	
	});
	
	//批量删除按钮
	$("#btn-batchDelete").bind("click", function(event){
		var keeperIds = $("#val0").val();
		if (keeperIds) 
		{
			var url = deleteUrl + "?keeperIds="+keeperIds;
			sendPost(url,"btn-batchDelete","",batchDeleteCallBack,"true");                                                           
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	}); 
	$('#btn-export').bind('click',function(){
		var $f = $("#fm-searchWarehouseKeeper");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/WarehouseKeeperAction/keeperDownload.do");
		$("#exportFm").submit();
    });
	$('#btn-import').bind('click',function(){
        //13:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("PT_BA_WAREHOUSE_KEEPER_TMP",0,7,3,"/jsp/dms/oem/part/basicInfo/warehouseKeeper/warehouseKeeperimportSuccess.jsp");
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

//单个编辑链接
function doUpdate(rowobj)
{   
	$("#val0").val("");
	$("#tab-warehouseKeeperList_content input[type=checkbox]").each(function(){
 		$(this).attr("checked",false);
  	});
	
	$("td input[type=checkbox]",$(rowobj)).attr("checked",true);
	$("#tab-warehouseKeeperList_content input[type=checkbox]").each(function(){
 		$("#val0").val($(this).val());
  	});
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/warehouseKeeper/warehouseKeeperAdd.jsp?action=2", "修改库管员属性", "修改库管员属性", diaAddOptions);
}

var $row;
function doDelete(rowobj)
{
	$("#val0").val("");
	$("#tab-warehouseKeeperList_content input[type=checkbox]").each(function(){
		$(this).attr("checked", false);
	})
	
	$row = $(rowobj);
	$("#val0").val($(rowobj).attr("KEEPER_ID"));
	var url = deleteUrl + "?keeperIds="+$(rowobj).attr("KEEPER_ID");
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

//放大镜回调
function SelCallBack(obj)
{
	$("#partCode").val($(obj).attr("PART_CODE"));		//配件代码
	$("#partName").val($(obj).attr("PART_NAME"));
}
function doSearch(){
	var $f = $("#fm-searchWarehouseKeeper");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	//combined()：实现将页面条件按规则拼接成json
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-warehouseKeeperList");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 库管员属性管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchWarehouseKeeper">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchWarehouseKeeper">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td>
					    	<input type="text" id="partCode"  name="partCode" datasource="PART_CODE"  datatype="1,is_null,300" 
					    		operation="like" />
					    </td>
					    <td><label>配件名称：</label></td>
					    <td>
					    	<input type="text" id="partName" name="partName" datasource="PART_NAME" datatype="1,is_null,400" operation="like" />
					    </td>
					    <td><label>库管员姓名：</label></td>
					    <td>
					    	<input type="text" id="userAccount"  name="userAccount" kind="dic" 
				    			src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000063' AND STATUS='100201'" 
				    			datasource="USER_ACCOUNT" datatype="1,is_null,30" operation="like" />
				    		<input type="hidden" id="dia-userName"  name="dia-userName" datasource="USER_NAME" />
					    </td>
					</tr>
					<tr>
					    <td><label>仓库：</label></td>
						<td colspan="3">										
							<input type="text" id="WAREHOUSE_NAME" name="WAREHOUSE_NAME" datatype="1,is_null,3000" datasource="WAREHOUSE_CODE"
								   src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_CODE,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE IN (100101, 100102, 100103, 100104, 100105, 100110, 100111) AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" 
								   operation="=" isreload="true" kind="dic"
							/>
						</td>
					</tr>
					<!-- tr>
						<td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
					    	</select>
					    </td>
					    <td><label>库管员账号：</label></td>
					    <td colspan="3">
					    	<input type="text" id="userAccount"  name="userAccount" datasource="USER_ACCOUNT"  datatype="1,is_null,32" operation="like" />
					    </td>
					</tr-->
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchAdd" >批量新增</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchUpdate" >批量更新</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchDelete" >批量删除</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export" >导出模板</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-import" >导入数据</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			<form id="exportFm" method="post" style="display:none">
				<input type="hidden" id="params" name="data"></input>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_warehouseKeeperList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-warehouseKeeperList" name="tablist" ref="page_warehouseKeeperList" refQuery="tab-searchWarehouseKeeper" >
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="KEEPER_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="WAREHOUSE_NAME" >仓库名称</th>
							<th fieldname="USER_NAME" >库管员姓名</th>
							<th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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