<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件不回运编辑</title>
<script type="text/javascript">
var nId=parent.notbackId;
var cId=parent.claimId;
var diaAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnCheckAction"; 
//查询按钮响应方法
$(function(){
	var $f = $("");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	var Url =diaAction+"/searchCheckParts.ajax?&nId="+nId;
	doFormSubmit($f,Url,"di_searchOldPart",1,sCondition,"oldPartReturnlb");
	
	var dialog = parent.$("body").data("oldPartNotReturnEdit");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartNotReturn" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="fwhdxg">
				<table width="100%" id="oldPartReturnlb" name="oldPartReturnlb" ref="fwhdxg" multivals="partDelete" style="display: none"  refQuery="fwhdxgTable" >
					<thead>
							<tr>
								<th fieldname="CLAIM_NO" >索赔单号</th>
								<th fieldname="PART_CODE" >旧件代码</th>
								<th fieldname="PART_NAME" >旧件名称</th>
								<th fieldname="DTL_COUNT" >旧件数量</th>
								<th fieldname="FAULT_CODE" >故障代码</th>
								<th fieldname="SUPPLIER_CODE" >旧件厂家代码</th>
								<th fieldname="SUPPLIER_NAME" >旧件厂家名称</th>
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