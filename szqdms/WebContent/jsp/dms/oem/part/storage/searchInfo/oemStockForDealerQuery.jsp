<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>库存查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 仓储相关   &gt; 总部库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件信息：</label></td>
					    <td>
	                    	<input type="text" id="partName" name="partName" datatype="0,is_null,10000" readOnly hasBtn="true" callFunction="showPartInfo({showAllPart: 1})" operation="="/>
							<input type="hidden" id="partId" name="partId" datatype="1,is_null,30" datasource="I.PART_ID" operation="in"/>
					    </td>
					</tr>
<!-- 					<tr>
					    <td><label>供应商代码</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datatype="1,is_digit_letter,30" dataSource="T.SUPPLIER_CODE" operation="like" /></td>
					    <td><label>供应商名称</label></td>
					    <td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datatype="1,is_digit_letter_cn,30" dataSource="T.SUPPLIER_NAME" operation="like" /></td>
					</tr> -->
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重置查询条件</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<!-- <th fieldname="AMOUNT" >库存数量</th>
							<th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价格(元)</th>
							<th fieldname="OCCUPY_AMOUNT" >占用数量</th> -->
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
							
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
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/InventoryQueryAction/dealerQuery.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	})
	
	// 清空查询条件
	$("#btn-clear").click(function(){
		$("input","#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

//配件选择回调函数
//res = {"PART_ID" : "", "PART_CODE": "", "PART_NAME": ""}
function showPartInfoCallback(res){
	$("#partName").val(res["PART_NAME"]);
	$("#partId").val(res["PART_ID"]);
}

</script>