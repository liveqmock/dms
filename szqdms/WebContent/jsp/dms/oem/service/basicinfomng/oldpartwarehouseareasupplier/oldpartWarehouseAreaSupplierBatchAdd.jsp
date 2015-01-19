<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchNewSupplierInfo">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchNewSupplierInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>供应商代码：</label></td>
					    <td>
					    	<input type="text" id="diaBA_supplierCode" name="diaBA_supplierCode" datasource="SUPPLIER_CODE" datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>供应商名称：</label></td>
					    <td>
					    	<input type="text" id="diaBA_supplierName" name="diaBA_supplierName" datasource="SUPPLIER_NAME" datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-newSupplierSearch" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>	
	</div>
	<div class="pageContent">
		<div id="page_searchNewSupplierList" >
			<table style="display:none;width:100%;" id="tab-searchNewSupplierList" name="tablist" ref="page_searchNewSupplierList" refQuery="tab-searchNewSupplierInfo">
					<thead>
						<tr>
							<th type="multi" name="CX-XH" style="align:center;" unique="SUPPLIER_ID"></th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
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
	<form method="post" id="fm-newOldpartWarehouseAreaSupplier">
			<table class="editTable" id="tab-newOldpartWarehouseAreaSupplierInfo">
				<tr>
				    <td><label>旧件库区名称：</label></td>
				    <td><input type="text" class="combox" id="diaBA-areaCode" name="diaBA-areaCode" datasource="AREA_CODE" operation="like" kind="dic" src="T#SE_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID}:STATUS=100201" datatype="0,is_null,30" />
					    <input type="hidden" id="diaBA-areaId" name="diaBA-areaId" datasource="AREA_ID"/>
					    <input type="hidden" id="diaBA-areaName" name="diaBA-areaName" datasource="AREA_NAME"/>	
				    </td>			    
				</tr>
			</table>
				<div class="searchBar" align="left" >
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-newOldpartWarehouseAreaAdd" >旧件库区新增</button></div></div></li>
						</ul>
					</div>
				</div>
	</form>
	</div>
</div>	
<script type="text/javascript">
var searchNewSupplierUrl = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaSupplierMngAction/searchNewSupplier.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaSupplierMngAction";
var diaAction = "<%=action%>";
//初始化
$(function()
{
	//设置高度
	$("#tab-searchNewSupplierList").attr("layoutH",document.documentElement.clientHeight-222);

	//查询按钮响应
	$("#btn-newSupplierSearch").bind("click", function(event){
		var $f = $("#fm-searchNewSupplierInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchNewSupplierUrl,"btn-newSupplierSearch",1,sCondition,"tab-searchNewSupplierList");
	});
	
	if(diaAction == 1)	//新增初始化,先执行一次查询，查出新供应商 
	{  
		var $f = $("#fm-searchNewSupplierInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchNewSupplierUrl,"btn-newSupplierSearch",1,sCondition,"tab-searchNewSupplierList");
	}
	
	//旧件库区批量新增
	$("#btn-newOldpartWarehouseAreaAdd").bind("click", function(event){ 
		var supplierIds=$("#val0").val();
		if(supplierIds)
		{              
		    var $f = $("#fm-newOldpartWarehouseAreaSupplier");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-newOldpartWarehouseAreaSupplier").combined(1) || {};
			var addUrl = diaSaveAction + "/batchInsert.ajax?supplierIds=" + supplierIds;
			doNormalSubmit($f,addUrl,"btn-newOldpartWarehouseAreaAdd",sCondition,diaInsertCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
});

//批量新增回调函数
function diaInsertCallBack(res)
{
	try
	{   
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
		
		//清空val0的内容
		$("#val0").val("");		
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

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   
	//给旧件库区隐藏域赋值
	if(id=="diaBA-areaCode") 
	{
		$("#diaBA-areaName").val($("#"+id).val());;
		$("#diaBA-areaId").val($row.attr("AREA_ID"));
	}
	return true;
}
</script>