<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件打印</title>
<style type="text/css">  
	#dia-oldPartTable{
		table-layout: fixed;
        word-break : break-all;  /* 强制换行; */
        width: 99%;
	}
	#dia-oldPartTable div{
		word-wrap: break-word;
	}
    .cusTable tr td {  
         font-size:12px;  
         padding: 3px 0px 3px 8px;  
     }
    
</style>
<script type="text/javascript">
//弹出窗体
var dialog = parent.$("body").data("oldPartPrint");
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartPrintAction";
$(function(){
	//var selectedRows = parent.$("#oldPartList").getSelectedRows();
	var selectedRows=window.opener.$("#oldPartList_content").getSelectedRows();
 	$("#dia_orgCode").text(selectedRows[0].attr("ORG_CODE"));
	$("#dia_orgName").text(selectedRows[0].attr("ORG_NAME"));
	$("#dia_claimNo").text(selectedRows[0].attr("CLAIM_NO"));
	$("#dia_claimType").text(selectedRows[0].attr("CLAIM_TYPE"));
	$("#dia_authorType").text(selectedRows[0].attr("AUTHOR_TYPE"));
	$("#dia_vin").text(selectedRows[0].attr("VIN"));
	$("#dia_wxcs").text(selectedRows[0].attr("WXCS"));
	$("#dia_mileage").text(selectedRows[0].attr("MILEAGE"));
	$("#dia_remarks").text(selectedRows[0].attr("REMARKS"));
	$("#dia_faultCode").text(selectedRows[0].attr("FAULT_CODE"));
	$("#dia_faultName").text(selectedRows[0].attr("FAULT_NAME"));
	$("#dia_partCode").text(selectedRows[0].attr("OLD_PART_CODE"));
	$("#dia_partName").text(selectedRows[0].attr("OLD_PART_NAME"));
	$("#dia_partCount").text("1");
	$("#dia_faultDate").text(selectedRows[0].attr("FAULT_DATE")); 
	$("#dia_supp_code").text(selectedRows[0].attr("SUPPLIER_CODE")); 
	$("#dia_supp_name").text(selectedRows[0].attr("SUPPLIER_NAME"));
	$("#dia_resSupp_code").text(selectedRows[0].attr("MAIN_SUPP_CODE"));
	$("#dia_resSupp_name").text(selectedRows[0].attr("MAIN_SUPP_NAME"));
	$("#dia_loss").text(selectedRows[0].attr("FAULT_REASON"));
	var claimId=selectedRows[0].attr("CLAIM_ID");
	var faultPartId=selectedRows[0].attr("FAULT_PART_ID");
	$("#ewm").html("<img src='<%=request.getContextPath()%>/jsp/dms/dealer/service/oldpart/showPicture.jsp?claimId="+claimId+"&faultPartId="+faultPartId+"' alt='二维图' style='width:240px;height:240px;'/>");

	$("#print").bind("click",function(){
		$("#opBtn").hide();
		//var a=document.getElementById("layout").innerHTML;
		//document.body.innerHTML=document.getElementById("dy").innerHTML;
		window.print();
		//document.body.innerHTML=a;
		var printUrl =diaSaveAction+"/oldPartPrintUpdate.ajax?faultPartId="+faultPartId;
		sendPost(printUrl,"","",printCallBack,"false");
	});
	//关闭
	$("button.close").click(function(){
		window.close();
		return false;
	});
});
function printCallBack(res){
	try
	{
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;padding-top:20px">
	<div id="dy" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-oldPartForm" class="editForm" >
			<div align="center">
			<fieldset>
			<table class="cusTable" id="dia-oldPartTable">
			    <tr>
					<td width="15%">渠道商代码：</td>
					<td id="dia_orgCode" width="45%"></td>
					<td rowspan="11" id="ewm" width="40%" class="padding-right:10px;"></td>
				</tr>	
				<tr>
					<td>渠道商名称：</td>
					<td colspan="2">
						<div id="dia_orgName"></div>
					</td>
				</tr>
				<tr>
					<td>索赔单号：</td>
					<td id="dia_claimNo"></td>
				</tr>
				<tr>
					<td>索赔单类型：</td>
					<td id="dia_claimType"></td>
				</tr>
				<tr>
					<td>预授权类型：</td>
					<td id="dia_authorType"></td>
				</tr>
				<tr>
					<td>故障日期：</td>
					<td id="dia_faultDate"></td>
				</tr>
				<tr>
					<td>底盘号：</td>
					<td id="dia_vin"></td>
				</tr>
			    <tr>
					<td>配件代码：</td>
					<td colspan="2">
						<div id="dia_partCode"></div>
					</td>
				</tr>	
				<tr>
					<td>配件名称：</td>
					<td colspan="2">
						<div id="dia_partName"></div>
					</td>
				</tr>
				<tr>
					<td>数量：</td>
					<td id="dia_partCount"></td>
				</tr>
				<tr>
					<td>维修次数：</td>
					<td id="dia_wxcs"></td>
				</tr>
				<tr>
					<td>行驶里程：</td>
					<td id="dia_mileage"></td>
				</tr>
				<tr>
					<td>旧件供应商代码：</td>
					<td colspan="2">
						<div id="dia_supp_code"></div>
					</td>
				</tr>
				<tr>
					<td>旧件供应商名称：</td>
					<td colspan="2">
						<div id="dia_supp_name"></div>
					</td>
				</tr>
				<tr>
					<td>责任供应商代码：</td>
					<td colspan="2">
						<div id="dia_resSupp_code"></div>
					</td>
				</tr>
				<tr>
					<td>责任供应商名称：</td>
					<td colspan="2">
						<div id="dia_resSupp_name"></div>
					</td>
				</tr>
				<tr>
					<td>故障代码：</td>
					<td colspan="2">
						<div id="dia_faultCode"></div>
					</td>
				</tr>
				<tr>
					<td>故障名称：</td>
					<td colspan="2">
						<div id="dia_faultName"></div>
					</td>
				</tr>
				<tr>
					<td>质损原因：</td>
					<td colspan="2">
						<div id="dia_loss"></div>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="2">
						<div id="dia_remarks"></div>
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