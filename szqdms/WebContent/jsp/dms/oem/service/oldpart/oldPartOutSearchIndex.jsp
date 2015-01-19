<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件出库查询</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartOutSearchAction/oldPartOutSearch.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldPartOutform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartOutList");
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件出库查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartOutform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartOutTable">
						<tr>
							<td><label>供应商代码：</label></td>
							<td><input type="text" id="supplierCode" name="supplierCode" datasource="T.SUPPLIER_CODE" datatype="1,is_null,100" value="" operation="like"/></td>
							<td><label>供应商名称：</label></td>
							<td><input type="text" id="supplierName" name="supplierName" datasource="T.SUPPLIER_NAME"  datatype="1,is_null,100" value="" operation="like"/></td>
							<td><label>出库日期：</label></td>
					    	<td>
					    		<input type="text" group="outDateStart,outDateEnd"  id="outDateStart" kind="date" name="outDateStart" style="width:75px;" operation=">=" datasource="T.OUT_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" group="outDateStart,outDateEnd"  id="outDateEnd" kind="date" name="outDateEnd" style="width:75px;margin-left:-30px;" operation="<=" datasource="T.OUT_DATE" datatype="1,is_date,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
				   		 	</td>
						</tr>
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="T.PART_CODE" datatype="1,is_null,100" value="" operation="like"/></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="T.PART_NAME"  datatype="1,is_null,100" value="" operation="like"/></td>
							<td><label>出库类型：</label></td>
							<td><select type="text" id="outType" name="outType"  class="combox" kind="dic" src="JJCKLX" datasource="T.OUT_TYPE" datatype="1,is_null,6" value="" >
									<option value="-1" >--</option>
								</select>
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
							<th fieldname="PART_CODE"  ordertype='local' class="desc">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="OUT_AMOUNT">出库数量</th>
							<th fieldname="OUT_TYPE">出库类型</th>
							<th fieldname="OUT_DATE">出库日期</th>
							<th fieldname="REMARKS">备注</th>
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