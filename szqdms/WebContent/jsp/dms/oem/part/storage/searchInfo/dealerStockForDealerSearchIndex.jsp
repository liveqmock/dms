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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 仓储相关   &gt; 库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,300" dataSource="I.PART_CODE" operation="like" /></td>
					    <td><label>配件名称</label></td>
					    <td><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_digit_letter_cn,30" dataSource="I.PART_NAME" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>供应商代码</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="SUPPLIER_CODE" datatype="1,is_digit_letter,30" dataSource="T.SUPPLIER_CODE" operation="like" /></td>
					    <td><label>供应商名称</label></td>
					    <td><input type="text" id="SUPPLIER_NAME" name="SUPPLIER_NAME" datatype="1,is_digit_letter_cn,30" dataSource="T.SUPPLIER_NAME" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export">导出数据</button></div></div></li>
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
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="AMOUNT" >库存数量</th>
							<th fieldname="OCCUPY_AMOUNT" >占用数量</th>
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
							<th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价格(元)</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data" datasource="data" />
</form>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerStockForDealerQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	
    // 导出按钮绑定
    $("#btn-export").click(function(){
    	var $f = $("#fm-searchContract");
    	if (submitForm($f) == false) return false;
    	var sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
    	$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/DealerStockForDealerQueryAction/download.do");
    	$("#exportFm").submit();
    });

})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

</script>