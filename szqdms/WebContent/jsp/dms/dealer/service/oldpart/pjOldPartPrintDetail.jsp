<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String applyId = request.getParameter("applyId");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件打印</title>
<style type="text/css">  
    .cusTable td {  
         font-size:15px;  
         padding: 3px 0px 3px 8px;  
         white-space:nowrap;
     }  
</style>
<script type="text/javascript">
//弹出窗体
var dialog = parent.$("body").data("oldPartPrint");
$(function(){
	
	$("#print").bind("click",function(){
		window.print();
	});
	
	//关闭
	$("button.close").click(function(){
		window.close();
		return false;
	});
	
	// oldPartList是父窗体表格ID，oldPartList_content是查询结果动态生成填充内容TBodyID，由控件生成
	var selectedRows=window.opener.$("#oldPartList_content").getSelectedRows();
	
    $("#orgCode").text(selectedRows[0].attr("ORG_CODE")); 				// 渠道代码
	$("#orgName").text(selectedRows[0].attr("ORG_NAME")); 				// 渠道名称
	$("#applyNo").text(selectedRows[0].attr("APPLY_NO")); 				// 申请单号
	$("#partCode").text(selectedRows[0].attr("PART_CODE"));				// 配件代码
	$("#partName").text(selectedRows[0].attr("PART_NAME"));				// 配件名称
	$("#claimCount").text(selectedRows[0].attr("CLAIM_COUNT"));			// 索赔数量
	$("#supplierCode").text(selectedRows[0].attr("SUPPLIER_CODE"));		// 供应商代码
	$("#supplierName").text(selectedRows[0].attr("SUPPLIER_NAME"));		// 供应商名称
	$("#paultConditons").text(selectedRows[0].attr("FAULT_CONDITONS")).css("word-wrap", "break-word"); // 故障情况
	
	
	$("#ewm").html("<img src='<%=request.getContextPath()%>/jsp/dms/dealer/service/oldpart/image.jsp?applyId=<%=applyId%>' alt='二维图' style='width:240px;height:240px;'/>");


});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-oldPartForm" class="editForm" >
			<div align="center">
			<fieldset>
			<table class="cusTable" id="dia-oldPartTable" style="table-layout: fixed; width: 99%; word-break : break-all;">
			    <tr>
					<td width="15%">渠道商代码：</td>
					<td id="orgCode" width="45%"></td>
					<td rowspan="9" id="ewm" width="40%" >
						<!-- 二维码 -->
					</td>
				</tr>	
				<tr>
					<td>渠道商名称：</td>
					<td id="orgName"></td>
				</tr>
				<tr>
					<td>申请单号：</td>
					<td id="applyNo"></td>
				</tr>
			    <tr>
					<td>配件代码：</td>
					<td id="partCode"></td>
				</tr>	
				<tr>
					<td>配件名称：</td>
					<td id="partName"></td>
				</tr>
				<tr>
					<td>索赔数量：</td>
					<td id="claimCount"></td>
				</tr>
				<tr>
					<td>供应商代码：</td>
					<td id="supplierCode"></td>
				</tr>
				<tr>
					<td>供应商名称：</td>
					<td id="supplierName"></td>
				</tr>	
				<tr>
					<td>故障情况：</td>
					<td style="white-space: pre-line;">
						<div id="paultConditons">
						
						</div>
					</td>
				</tr>	
			</table>
			</fieldset>
			</div>
		</form>
	</div>
		<div class="formBar" id="opBtn">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button"  id="print">打&nbsp;&nbsp;印</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
</body>
</html>