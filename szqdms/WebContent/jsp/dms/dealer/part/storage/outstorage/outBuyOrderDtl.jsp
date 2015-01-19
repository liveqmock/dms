<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>外采单明细</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var action = "<%=action%>";
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimOutBuyMngAction";
$(function() 
{
	if(action == "1"){
		var selectedRows = parent.$("#claimOutBuylist").getSelectedRows();
		setEditValue("dia-fm-hurryPartReport",selectedRows[0].attr("rowdata"));
	}
	$("#searchPart").bind("click",function(){
		var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchOutParts.ajax?&buyId="+$("#buyId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
	});
	//审核通过
	$("#dia-report").bind("click",function(){
		var buyId=$("#buyId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-hurryPartReport");
		if (submitForm($f) == false) {
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-hurryPartReport").combined(1) || {};
		var reportUrl = diaSaveAction + "/OutPartReport.ajax?buyId="+buyId+"&flag="+2;
		doNormalSubmit($f,reportUrl,"dia-report",sCondition,diaReportCallBack);
	});
	//审核驳回
	$("#dia-reject").bind("click",function(){
		var buyId=$("#buyId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-hurryPartReport");
		var reason = $('#dia-shyj').val();
		if (submitForm($f) == false) {
			return false;
		}
		if(reason.length==0){
			alertMsg.warn("审核意见不能为空，请填写审核意见.");
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-hurryPartReport").combined(1) || {};
		var rejectUrl = diaSaveAction + "/OutPartReject.ajax?buyId="+buyId+"&flag="+2;
		doNormalSubmit($f,rejectUrl,"dia-reject",sCondition,diaReportCallBack);
	});
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("claimDetail");
		return false;
	});
	//上一步
	$("button[name='btn-pre']").bind("click",function(event){
		$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex")) - 1);
	});
	//下一步
	$("button[name='btn-next']").bind("click", function(event){
		var $tabs = $("#tabs");
		switch (parseInt($tabs.attr("currentIndex"))) 
		{
			case 0:
				if($true==true){
					var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
			    	sCondition = $f.combined() || {};
			    	var searchServicePartUrl =diaSaveAction+"/searchOutParts.ajax?&buyId="+$("#buyId").val();
					doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
				}
				$true=false;
				break;
		};
		$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
		//跳转后实现方法
		(function(ci) 
		{
			switch (parseInt(ci)) 
			{
				case 1://第2个tab页					
					break;
				default:
					break;
			}
		})
		(parseInt($tabs.attr("currentIndex")));
	});
});

//提报回调方法
function diaReportCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $row =  parent.$("#claimOutBuylist").getSelectedRows();
			if($row[0]){
				parent.$("#claimOutBuylist").removeResult($row[0]);
				parent.$.pdialog.close("claimDetail");
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="dia-fm-hurryPartReport" class="editForm" style="width: 99%;">
					<input type="hidden" id="buyId" datasource="BUY_ID"/>
					<input type="hidden" id="claimId" datasource="CLAIM_ID"/>
					<div align="left">
						<fieldset>
						<legend align="right">
							<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;外采单信息&gt;&gt;</a>
						</legend>
						<table class="editTable" id="sbjjxx">
						<tr>
							 <td><label>外购单号：</label></td>
							 <td><input type="text" id="dispatchNo" name="dispatchNo" datasource="BUY_NO" readonly="readonly" /></td>
							 <td><label>索赔单号：</label></td>
				             <td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="CLAIM_NO" readonly="readonly" />
				             <input type="hidden" id="dia-mileage" name="dia-mileage"  datasource="CLAIM_ID" readonly="readonly" /></td>
						 </tr>
						 <tr>	 
				             <td><label>外采日期：</label></td>
				             <td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
				              <td><label>客户名称：</label></td>
							 <td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="CUSTOMER_NAME"  readonly="readonly"/></td>
						   	 <td><label>联系电话：</label></td>
							 <td><input type="text" id="dia-models_code" name="dia-models_code" datasource="LINK_PHONE" value="" readonly="readonly" /></td>
				          </tr>
			              <tr>
				              <td><label>联系地址：</label></td>
				              <td colspan="5"><textarea id="SQRBZ" style="width: 450px; height: 50px;" datasource="LINK_ADDR" name="SQRBZ" readonly="readonly"></textarea></td>
				          </tr>
			              <tr>
				         	  <td><label>审核意见：</label></td>
				              <td colspan="5"><textarea id="dia-shyj" style="width: 450px; height: 50px;" datasource="CHECK_REMARKS" readonly="readonly" name="shyj"></textarea></td>
				          </tr>
				          </table>
						</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
					<ul>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
			</div>
			</div>
			<!-- 配件TAB -->
			<div class="page">
			<div class="pageHeader">
			</div>
			<div class="pageContent">
				<div id=sbjjpjl>
					<table width="100%" id="sbjjpjlb" name="sbjjpjlb" multivals="partDeleteVal" ref="sbjjpjl"  style="display: none"  refQuery="fm-sbjjpjTable">
						<thead>
							<tr>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="UNIT" >单位</th>
								<th fieldname="BUY_COUNT" >数量</th>
								<th fieldname="BUY_PRICE" refer="amountFormat" align="right">零售价</th>
								<th fieldname="AMOUNT"  refer="amountFormat" align="right">金额</th>
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
</div>
</body>
</html>