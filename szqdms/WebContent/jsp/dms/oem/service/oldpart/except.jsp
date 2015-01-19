<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>帮助说明</title>
<script type="text/javascript">
 //下载索赔提报流程
$(function(){
 $("#export1").bind("click",function(){
		var $f = $("#oldPartFm");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckStorageAction/download.do");
		$("#exportFm").submit();
	});
});
</script>
</head>
<body>
<div class="pageContent">
	<form name="lForm" id="oldPartFm" method="post" >
		<div class="pageContent">
			<table class="searchContent" id="oldPartTable">
				<tr>
					<td><label>回运单号：</label></td>
					<td><input type="text" id="orederNo" name="orederNo"  datasource="D.ORDER_NO" operation="like" datatype="1,is_null,100"  value="" /></td>
					<td><label>供应商代码：</label></td>
					<td><input type="text" id="dia-supplierCode" name="supplierCode"  datasource="PROSUPPLY_CODE" operation="like" datatype="1,is_null,100"  value="" /></td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" id="export1">导出</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>