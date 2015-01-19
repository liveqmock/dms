<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />

<title>三包急件新增</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String dispatchId = request.getParameter("dispatchId");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var dispatchId = "<%=dispatchId%>";
var $partTrue=true;
var diaSaveAction = "<%=request.getContextPath()%>/service/guaranteesHurryPart/GuaranteesHurryPartAction";
var deleteUrl = "<%=request.getContextPath()%>/service/guaranteesHurryPart/GuaranteesHurryPartAction/dispatchPartDelete.ajax";
$(function() 
{
	//查询三包急件信息
	var $f = $("");
	var sCondition = {};
   	sCondition = $f.combined() || {};
   	var searchServiceUrl =diaSaveAction+"/dispatchSearch.ajax?&dispatchId="+dispatchId+"";
   	sendPost(searchServiceUrl,"",sCondition,searchCallBack,"false");
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("sbjjmx");
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
			if($partTrue=true){
				//查询三包急件配件信息
			 	var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
				var sCondition = {};//定义json条件对象
			   	sCondition = $f.combined() || {};
			   	var searchServicePartUrl =diaSaveAction+"/searchHurryParts.ajax?&dispatchId="+dispatchId+"";
				doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
			}
				$partTrue=false;
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
function searchCallBack(res)
{
	setEditValue("dia-fm-hurryPartReport",res);
	return true;
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
					<li><a href="javascript:void(0)"><span>三包急件基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="dia-fm-hurryPartReport" class="editForm" style="width: 99%;">
					<input type="hidden" id="dispatchId" datasource="DISPATCH_ID"/>
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="modelsId" datasource="MODELS_ID"/>
					<div align="left">
						<fieldset>
						<legend align="right">
							<a onclick="onTitleClick('dia-tab-preAuthBasic')">&nbsp;三包急件基本信息编辑&gt;&gt;</a>
						</legend>
						<table class="editTable" id="sbjjxx">
						<tr>
							<td><label>急件编号：</label></td>
							<td><input type="text" id="dispatchNo" name="dispatchNo" datasource="DISPATCH_NO" value="" readonly="readonly" /></td>
						</tr>
					    <tr>
							 <td><label>VIN：</label></td>
						   	 <td><input type="text" id="dia-vin" name="dia-vin" datasource="VIN" value="" operation="like" readonly="readonly"/></td>
						 	 <td><label>发动机号：</label></td>
							 <td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="ENGINE_NO"  value="" readonly="readonly"/></td>
					 	</tr>
					 	<tr>
					    	 <td><label>车辆型号：</label></td>
						     <td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly" /></td>
			                 <td><label>购车日期：</label></td>
			                 <td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" value=""  readonly="readonly" /></td>
			                 <td><label>行驶里程：</label></td>
			                 <td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="MILEAGE" value="" readonly="readonly"/></td>
				         </tr>
				         <tr >
				             <td><label>客户名称：</label></td>
				             <td><input type="text" id="dia-user_name" name="dia-user_name" datasource="USER_NAME" value="" readonly="readonly"/></td>
				             <td><label>联系人：</label></td>
				             <td><input type="text" id="dia-link_man" name="dia-link_man" datasource="LINK_MAN" value="" readonly="readonly"/></td>
				             <td><label>电话：</label></td>
				             <td><input type="text" id="dia-phone" name="dia-phone" datasource="PHONE" value="" readonly="readonly" /></td>
				          </tr>
				          <tr>
				             <td><label>故障分析及处理：</label></td>
				             <td ><input type="text" id="dia-in-hsj0" name="dia-in-gzfx0"  datatype="1,is_null,30" value="" datasource="FAULT_ANALYSE" readonly="readonly" /></td>
				             <td><label>客户地址：</label></td>
				             <td><textarea id="dia-user_address" style="width: 150px; height: 30px;" datasource="USER_ADDRESS" name="KHDZ" readonly="readonly"></textarea></td>
				             <td><label>本次故障日期：</label></td>
				             <td><input type="text" id="dia-fault_date" name="BCGZRQ" value="" datasource="FAULT_DATE" datatype="1,is_null,30" readonly="readonly"/></td>
				          </tr>
				          <tr>
				             <td><label>收货人：</label></td>
				             <td><input type="text" id="SHR" name="SHR" value="" datasource="RECEIPT_USER" readonly="readonly" /></td>
				             <td><label>收货人电话：</label></td>
				             <td><input type="text" id="SHRDH" name="SHRDH" value="" datasource="RECEIPT_PHONE" readonly="readonly" /></td>
				             <td><label>收货地址：</label></td>
				             <td><textarea id="SHDZ" style="width: 150px; height: 30px;" name="SHDZ" datasource="RECEIPT_ADDRESS" readonly="readonly"></textarea></td>
				           </tr>
				           <tr>
				             <td ><label>申请人备注：</label></td>
				             <td colspan="3"><textarea id="SQRBZ" style="width: 90%; height: 50px;" datasource="REMARKS" name="SQRBZ" readonly="readonly"></textarea></td>
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
			<form id="fm-sbjjpj" method="post">
			</form>
			</div>
			<div class="pageContent">
				<div id=sbjjpjl>
					<table width="100%" id="sbjjpjlb" name="sbjjpjlb" multivals="partDeleteVal" ref="sbjjpjl"  style="display: none"  refQuery="fm-sbjjpjTable">
						<thead>
							<tr>
								<th type="multi" name="XH" unique="DTL_ID"></th>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="COUNT" >数量</th>
								<th fieldname="CLAIM_PRICE"  colwidth="60" align="right">索赔单价(元)</th>
								<th fieldname="REMARKS"  colwidth="60">备注</th>
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