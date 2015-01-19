<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<!-- 延保策略与车辆关系，弹出VIN选择框 -->
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchSelVin">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchSelVin">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>VIN：</label></td>
					    <td><input type="text" id="sel_vin" name="sel_vin" datatype="1,is_null,17" dataSource="VIN" operation="like" /></td>
					    <td><label>车型：</label></td>
					    <td><input type="text" id="sel_modelsCode" name="sel_modelsCode" datatype="1,is_null,30" dataSource="MODELS_CODE" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>购车日期：</label></td>
						<td>
							<input type="text" id="sel-buyDate" name="sel-buyDate" style="width:75px;" datasource="BUY_DATE" datatype="1,is_null,30" onclick="WdatePicker()"  kind="date"/>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchSelVin" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_selVinList" >
			<table style="display:none;width:100%;" id="tab-selVinList" name="tablist" ref="page_selVinList" refQuery="tab-searchSelVin" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="MODELS_CODE" >车型</th>
							<th fieldname="BUY_DATE" >购车日期</th>						
							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
<script type="text/javascript">
//查询提交方法
var searchSelUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyVehicleMngAction/vinSearch.ajax";
$(function()
{
	//新增初始化,先执行查询  
	var $f = $("#fm-searchSelVin");
	var sCondition = {};
    sCondition = $f.combined() || {};
	doFormSubmit($f,searchSelUrl,"btn-searchSelVin",1,sCondition,"tab-selVinList");

	$("#btn-searchSelVin").bind("click", function(event){
		var $f = $("#fm-searchSelVin");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchSelUrl,"btn-searchSelVin",1,sCondition,"tab-selVinList");
	});
});
var dialog = $("body").data("extendwarrantyvehicleSel");

function doOk(rowobj)
{
	try
	{
		SelCallBack(rowobj);	
	}catch(e){}
	
	$.pdialog.close(dialog);
}
</script>
</div>
</body>
</html>
