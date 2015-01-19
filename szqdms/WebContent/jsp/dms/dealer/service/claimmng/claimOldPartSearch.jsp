<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String id = request.getParameter("id");
	String selectType = request.getParameter("selectType");
	String faultType = request.getParameter("faultType");
%>
<div id="dia-layout"  style="overflow:auto;">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-dia-oldpart-search">
			<input type="hidden" id="dia-in-partType" datasource="T1.PART_TYPE" operation="in"/>
			<input type="hidden" id="dia-in-seStatus" datasource="T1.SE_STATUS" value="100201"/>
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-dia-odlpart-search">
					 <tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-in-partcode" name="dia-in-partcode" datasource="T1.PART_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-in-partname" name="dia-in-partname" datasource="T1.PART_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>供应商代码：</label></td>
					    <td><input type="text" id="dia-in-suppliercode" name="dia-in-suppliercode" datasource="T2.SUPPLIER_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>供应商名称：</label></td>
					    <td><input type="text" id="dia-in-suppliername" name="dia-in-suppliername" datasource="T2.SUPPLIER_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>车辆部位：</label></td>
					    <td><input type="text" id="dia-in-positionName" name="dia-in-positionName" datasource="N.POSITION_NAME" datatype="1,is_null,30" operation="like" /></td>
						<td><label>实物供应商编码：</label></td>
					    <td><input type="text" id="dia-in-realNo" name="dia-in-realNo" datasource="T2.REAL_NO" datatype="1,is_null,30" operation="like" /></td>
						
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-dia-part-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dia-part" >
			<table style="display:none;width:100%;" id="tab-oldpart-list" name="tablist" ref="page-dia-part" refQuery="fm-dia-oldpart-search" >
					<thead>
						<tr>
							<th type="single" style="align:center;display:none"></th>
							<th fieldname="PART_CODE" colwidth="120">配件代码</th>
							<th fieldname="PART_NAME"  colwidth="120">配件名称</th>
							<th fieldname="SUPPLIER_CODE" colwidth="120">供应商代码</th>
						    <th fieldname="SUPPLIER_NAME" colwidth="120">供应商名称</th>
						    <th fieldname="POSITION_NAME" colwidth="120">车辆部位名称</th>
						    <th fieldname="PART_TYPE" colwidth="80">配件类别</th>
						    <th fieldname="BRIDGE_STATUS" style="display:none" colwidth="80">是否校验桥编码</th>
						   <%-- 无论新件旧件都显示索赔单价 <%if("1".equals(selectType)){%>
						    <th fieldname="SE_REPRICE" colwidth="70" align="right" refer="salePriceFormat">零件单价</th>
						    <%}else{%> --%>
						    <th fieldname="SE_CLPRICE" colwidth="70" align="right" refer="salePriceFormat">零件单价</th>
						  <%--   <%}%> --%>
						    <th fieldname="REAL_NO" colwidth="120" maxlength="20">真实编号</th>
						    <th colwidth="60" type="link" title="[确定]"  action="doSelectPart">操作</th>
						</tr>
					</thead>
			</table>
			</div>
	     </div>
	</div>
<script type="text/javascript">
var diaClaimOldPartUrl="<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
var diaOldPartDia=$("body").data("claimOldPartSearch");//当前页面
var htmlId="<%=id%>";
var selectType="<%=selectType%>";//1表示选择旧件,2表示选择新件
var diafaultType=<%=faultType%>;
$(function(){
	if($("#dia-user_typeId").val()==300101){
		$("#dia-in-partType").val("200401,200402");//民品
	}else{
		$("#dia-in-partType").val("200401,200403");//军品
	}
	$("#btn-dia-part-search").bind("click",function(event){
		var $f = $("#fm-dia-oldpart-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var diaClaimOldPartSearchUrl=diaClaimOldPartUrl+"/searchOldPart.ajax?claimDtlId="+claimDtlId+"&selectType="+selectType+"&claimType="+partClaimType+"&claimId="+diaCliamId+"&faultType="+diafaultType;
		doFormSubmit($f,diaClaimOldPartSearchUrl,"btn-dia-part-search",1,sCondition,"tab-oldpart-list");
	});
	//关闭当前页面
	$("button.close").click(function() 
	{
	    $.pdialog.close(diaOldPartDia);
		return false;
	});
});
function doSelectPart(rowobj)
{
	var partId=$(rowobj).attr("PART_ID");
	var partCode=$(rowobj).attr("PART_CODE");
	var partName=$(rowobj).attr("PART_NAME");
	var supplierId=$(rowobj).attr("SUPPLIER_ID");
	var supplierCode=$(rowobj).attr("SUPPLIER_CODE");
	var partType=$(rowobj).attr("PART_TYPE");
	var partTypeName=$(rowobj).attr("PART_TYPE_SV");
	var seClPrice=$(rowobj).attr("SE_CLPRICE");//索赔价格
	var seRePrice=$(rowobj).attr("SE_REPRICE");//追偿价格
	var ifReturn=$(rowobj).attr("IF_RETURN");
	var vehicleMax=$(rowobj).attr("VEHICLE_MAX");//单车最大装车件数
	var ifStream=$(rowobj).attr("IF_STREAM");
	var bridgeStatus=$(rowobj).attr("BRIDGE_STATUS");
	var ifWorkMultiple=$(rowobj).attr("IF_WORK_MULTIPLE");
	if(selectType==1)//旧件
	{
		if(partId) $("#"+htmlId+"_oldPartId").val(partId);
		if(partCode) $("#"+htmlId+"_oldPartCode").val(partCode);
		if(partName) $("#"+htmlId+"_oldPartName").val(partName);
		if(supplierId) $("#"+htmlId+"_oldSupplierId").val(supplierId);
		if(supplierCode) $("#"+htmlId+"_oldSupplierCode").val(supplierCode);
		if(partType) $("#"+htmlId+"_partType").val(partType);
		if(partTypeName) $("#"+htmlId+"_partTypeName").val(partTypeName);
		if(seRePrice) $("#"+htmlId+"_repayUprice").val(seRePrice);
		if(ifReturn) $("#"+htmlId+"_ifReturn").val(ifReturn);
		$("#"+htmlId+"_bridgeStatus").val(bridgeStatus);//是否校验桥编码
		$("#"+htmlId+"_vehicleMax").val(vehicleMax); //单车最大装车数量 有可能 为0
		$("#"+htmlId+"_ifWorkMultiple").val(ifWorkMultiple); //配件属性 是否工时倍数
		if(ifStream==100101){
			$("#"+htmlId+"_oldIfStream").val(ifStream);
		}
	}else //新件
	{
		if(partId) $("#"+htmlId+"_newPartId").val(partId);
		if(partCode) $("#"+htmlId+"_newPartCode").val(partCode);
		if(partName) $("#"+htmlId+"_newPartName").val(partName);
		if(supplierId) $("#"+htmlId+"_newSupplierId").val(supplierId);
		if(supplierCode) $("#"+htmlId+"_newSupplierCode").val(supplierCode);
		if(seClPrice){
			//以点开始 前面加个0
			if(seClPrice.indexOf(".")==0){
				$("#"+htmlId+"_claimUprice").val("0"+seClPrice);
			}else{
				$("#"+htmlId+"_claimUprice").val(seClPrice);	
			}
		} 
		if(ifStream==100101){
			$("#"+htmlId+"_newIfStream").val(ifStream);
		}
		goChangeClaimCosts(htmlId);
	}
	try{
		$.pdialog.close(diaOldPartDia);
	}catch(e){
		alert(e);
	}
}
//金额格式化
function salePriceFormat(obj){
	 return amountFormatNew($(obj).html());
}
</script>
</div>

