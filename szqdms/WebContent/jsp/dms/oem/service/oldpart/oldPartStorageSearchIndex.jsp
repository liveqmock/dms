<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件库存查询</title>
<script type="text/javascript">
var detailSearchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartStorageSearchAction/oldPartStorageDetailSearch.ajax";
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartStorageSearchAction/oldPartStorageSearch.ajax";
//查询按钮响应方法
$(function(){
	$("#detailSearch").bind("click",function(){
		$("#oldPartStorage").hide();
		$("#oldPartStorageDetail").show();
		$("#supplierCode").attr("action","");
		$("#supplierName").attr("action","");
		var $f = $("#oldPartStorageform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,detailSearchUrl,"detailSearch",1,sCondition,"oldPartStorageDetailList");
	});
	$("#search").bind("click",function(){
		$("#oldPartStorage").show();
		$("#oldPartStorageDetail").hide();
		$("#supplierCode").attr("action","show");
		$("#supplierName").attr("action","show");
		var $f = $("#oldPartStorageform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartStorageList");
	});
	
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件库存查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartStorageform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartStorageTable">
						<tr>
							<td><label>供应商代码：</label></td>
							<td><input type="text" id="supplierCode" name="supplierCode" datasource="T.SUPPLIER_CODE" datatype="1,is_null,30" value="" title="供明细查询使用" operation="like"/></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="T.SUPPLIER_NAME"  datatype="1,is_null,30" value="" title="供明细查询使用" operation="like"/></td>
							<!-- <td><label>库区名称：</label></td>
							<td><input type="text" id="warehouseName" name="warehouseName" datasource="T.WAREHOUSE_NAME"  datatype="1,is_null,30" value="" title="供明细查询使用" operation="like"/></td> -->
						<td><label>入库时间：</label></td>
							<td>
							<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" datasource="T.STORAGE_MONTH" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						    <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
						    <input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.STORAGE_MONTH" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						</tr>
						<tr>
							<!-- <td><label>库区代码：</label></td>
							<td><input type="text" id="warehouseCode" name="warehouseCode" datasource="T.WAREHOUSE_CODE" datatype="1,is_null,30" value="" title="供明细查询使用" operation="like"/></td> -->
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="T.PART_CODE" datatype="1,is_null,30" value="" operation="like"/></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="T.PART_NAME"  datatype="1,is_null,30" value="" operation="like"/></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="detailSearch">明细查询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">汇总查询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldPartStorageDetail" style="display:none">
			<div id="oldPartStorageDetailD" >
				<table style="display:none;width:100%;" id="oldPartStorageDetailList" name="oldPartStorageDetailList" ref="oldPartStorageDetailD" refQuery="oldPartStorageTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SUPPLIER_CODE"  ordertype='local' class="desc">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<!-- <th fieldname="WAREHOUSE_CODE">库区代码</th>
							<th fieldname="WAREHOUSE_NAME">库区名称</th> -->
							<th fieldname="PART_CODE"  ordertype='local' class="desc">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="SUM_AMOUNT">库存总数量</th>
							<th fieldname="SURPLUS_AMOUNT">库存数量</th>
							<th fieldname="OUT_AMOUNT">出库数量</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			</div>
			<div id="oldPartStorage" style="display:none">
			<div id="oldPartStorageD" >
				<table style="display:none;width:100%;" id="oldPartStorageList" name="oldPartStorageList" ref="oldPartStorageD" refQuery="oldPartStorageTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PART_CODE"  ordertype='local' class="desc">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="SUM_AMOUNT">库存总数量</th>
							<th fieldname="SURPLUS_AMOUNT">库存数量</th>
							<th fieldname="OUT_AMOUNT">出库数量</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>