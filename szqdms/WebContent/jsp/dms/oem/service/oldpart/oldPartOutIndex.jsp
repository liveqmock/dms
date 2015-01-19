<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件出库</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartOutAction/oldPartOutSearch.ajax";
var options = {max:false,width:710,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldPartOutform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartOutList");
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/oldPartOutDetail.jsp", "oldPartOut", "旧件出库", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件出库</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartOutform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartOutTable">
						<tr>
							<td><label>供应商代码：</label></td>
							<td><input type="text" id="supplierCode" name="supplierCode" datasource="T.SUPPLIER_CODE" datatype="1,is_null,30" value=""  operation="like"/></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="T.SUPPLIER_NAME"  datatype="1,is_null,30" value=""  operation="like"/></td>
							<!-- <td><label>库区名称：</label></td>
							<td><input type="text" id="warehouseName" name="warehouseName" datasource="T.WAREHOUSE_NAME"  datatype="1,is_null,30" value=""  operation="like"/></td> -->
						</tr>
						<tr>
							<!-- <td><label>库区代码：</label></td>
							<td><input type="text" id="warehouseCode" name="warehouseCode" datasource="T.WAREHOUSE_CODE" datatype="1,is_null,30" value=""  operation="like"/></td> -->
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="T.PART_CODE" datatype="1,is_null,30" value="" operation="like"/></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="T.PART_NAME"  datatype="1,is_null,30" value="" operation="like"/></td>
						</tr>
						<tr>
						<td><label>入库时间：</label></td>
						<td>
							<input type="text" group="settleDateStart,settleDateEnd"  id="settleDateStart" kind="date" name="settleDateStart" style="width:75px;" operation=">=" datasource="T.STORAGE_MONTH" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						    <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
						    <input type="text" group="settleDateStart,settleDateEnd"  id="settleDateEnd" kind="date" name="settleDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.STORAGE_MONTH" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldPartOut">
				<table style="display:none;width:100%;" id="oldPartOutList" name="oldPartOutList" ref="oldPartOut" refQuery="oldPartOutTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SUPPLIER_CODE"  ordertype='local' class="desc">供应商代码</th>
							<th fieldname="SUPPLIER_NAME">供应商名称</th>
							<!-- <th fieldname="WAREHOUSE_CODE">库区代码</th>
							<th fieldname="WAREHOUSE_NAME">库区名称</th> -->
							<th fieldname="PART_CODE"  ordertype='local' class="desc">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="SURPLUS_AMOUNT">库存数量</th>
							<th colwidth="45" type="link"  title="[出库]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>