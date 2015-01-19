<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String bType="",bParams="";
	try
	{
		bType = (String)request.getAttribute("bType");
		bParams = (String)request.getAttribute("bParams");
	}catch(Exception e)
	{}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>成功列表</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/financeMng/StatementAdjustAction/searchImport.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/financeMng/StatementAdjustAction";
$(function(){
	var $f = $("#fm-fileImport");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"",1,sCondition,"tab-fileImportResult");
});
function doConfirm()
{
	//点击确定按钮，提交后台请求
	var url =diaSaveAction+"/settleOtherFeetsImport.ajax";
	sendPost(url,"","",importCallBack,"true");
}
function importCallBack(res){
	
	try
	{
		parent.impCall();//调用父jsp方法
		var dialog = parent.$("body").data("importXls");
		parent.$.pdialog.close(dialog);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//金额格式化
function amountFormat(obj){
  return amountFormatNew($(obj).html());
}

</script>
</head>
<body>
	<div class="page">
		<form id="fm-fileImport" method="post" />
		<div class="pageContent">
			<div  style="width:100%;height:300px;overflow:auto;" id="div-fileImportResult">
				<table style="display:none;width:100%;" id="tab-fileImportResult" name="tablist" ref="div-fileImportResult" pagerows="10">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="SETTLE_NO"  align="center">结算单号</th>
							<th fieldname="ORG_CODE"  align="center">服务站代码</th>
							<th fieldname="ORG_NAME"  align="center">服务站名称</th>
							<th fieldname="SETTLE_DATE"  align="center">结算日期</th>
							<th fieldname="SE_OTHERS" align="center" refer="amountFormat">其它费用</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<table border=0 align="center">
			 	<tr><td align=center>
			 	<input type="button" value="确定" onclick="javascript:doConfirm()" />
			 	<input type="button" value="返回" onclick="javascript:history.go(-1);" />
			 	</td></tr>
	    	</table>
		</div>
	</div>
</body>
</html>