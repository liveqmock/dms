<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String account = user.getAccount();
	String name = user.getPersonName();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购计划制定</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchasePlanMngAction";
$(function()
{
	var userAccount = '<%=account%>';
	var name = '<%=name%>';
	if(userAccount=='ADMIN'){
		$('#PERSON_NAME').attr("src","T#TM_USER A,TM_ORG B:A.ACCOUNT:A.PERSON_NAME{A.ACCOUNT,A.PERSON_NAME}:1=1 AND A.STATUS=<%=DicConstant.YXBS_01 %> AND A.ORG_ID = B.ORG_ID AND B.CODE='XS10905'");
	}else{
		$('#PERSON_NAME').attr('code',userAccount);
        $('#PERSON_NAME').val(name);
        $('#PERSON_NAME').attr("src","");
	}
	var account = $('#PERSON_NAME').attr('code');
	$("#page_order").height(document.documentElement.clientHeight-110);
	$("#btn-reSearch").bind("click", function(event){
		var type = $("#PURCHASE_TYPE").val();
		var searchUrl = mngUrl+"/partSearch.ajax?type="+type+"&account="+account;
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		sendPost(searchUrl, "btn-reSearch", sCondition, doSearchCallBack, "");
	});
	$("#btn-search").bind("click", function(event){
		var type = $("#PURCHASE_TYPE").val();
		var searchUrl = mngUrl+"/partReSearch.ajax?type="+type+"&account="+account;
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		sendPost(searchUrl, "btn-search", sCondition, doSearchCallBack, "");
	});
	$("#btn-download").bind("click",function(){
		var type = $("#PURCHASE_TYPE").val();
		var $f = $("#fm-searchPchOrder");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action",mngUrl+"/download.do?type="+type);
		$("#exportFm").submit();
	});
	$('#btn-add').bind('click',function(){
        //添加促销配件URL
        var userAcc = $("#PERSON_NAME").attr("code");
        if(!userAcc){
        	alertMsg.warn('请选择采购员!');
            return false;
        }
        var type = $("#PURCHASE_TYPE").val();
        var shippingUrl = mngUrl+"/createPurchaseOrder.ajax?type="+type+"&account="+userAcc;
        var partIds="";
        var supplierIds="";
        var myCounts="";
        var remarks="";
        var checkObjs="";
        $("#tab-order_info").find("tbody").find("tr").each(function(){
        	var checkObj=$(this).find("td").find("input[type='checkbox']:first");
            if(checkObj.is(":checked")){
            	var $checkbox=$(this).find("td").find("input[type='checkbox']:first");
/*             	var partId=$(this).find("td").eq(0).find("input:first").val();
                var supplierId=$(this).find("td").find("input[name='supplierIds']:first").val();
                var myCount=$(this).find("td").find("input[name='myCounts']:first").val(); */
            	var partId=$checkbox.attr("partId");
                var supplierId=$checkbox.attr("supplierId");
                var myCount=$(this).find("td").find("input[name='myCounts']:first").val();
                if(!myCount){
                	myCount="anull";
                }
                var remark=$(this).find("td").find("input[name='remarks']:first").val();
                if(!remark){
                	remark="anull";
                }
            	if(checkObjs.length==0){
            		checkObjs = checkObj.val();
            	}else{
            		checkObjs = checkObjs+","+checkObj.val();
            	}
            	if(partIds.length==0) {
            		partIds = partId;
                } else {
                	partIds = partIds+","+partId;
                }
            	if(supplierIds.length==0) {
            		supplierIds = supplierId;
                } else {
                	supplierIds = supplierIds+","+supplierId;
                }
            	if(myCounts.length==0) {
            		myCounts = myCount;
                } else {
                	myCounts = myCounts+","+myCount;
                }
            	if(remarks.length==0) {
            		remarks = remark;
                } else {
                	remarks = remarks+","+remark;
                }
            }else
            	return true;
        });
        if(checkObjs.length==0){
            alertMsg.warn('请选择配件!');
            return false;
        }
        var myCountses = myCounts.split(",");
        for(var i=0;i<myCountses.length;i++){
        	if(!isNumV(myCountses[i])){
        		alertMsg.warn("请正确输入采购数量.");
        		return false;
        	}
        }
        $('#partIds').val(partIds);
        $('#counts').val(myCounts);
        $('#supplierIds').val(supplierIds);
        $('#sremark').val(remarks);
        var $f = $("#fm-partInfo");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, shippingUrl, "btn-add", sCondition, addPartCallBack); 
    });
	$('#btn-saveTmp').bind('click',function(){
		if($("#tab-order_info").find("tr").size() <= 1)
		{
			alertMsg.warn("请先计算缺件或查询已暂存的的采购计划.");
			return false;
		}
		var userAcc = $("#PERSON_NAME").attr("code");
        if(!userAcc){
        	alertMsg.warn('请选择采购员!');
            return false;
        }
        var type = $("#PURCHASE_TYPE").val();
        var shippingUrl = mngUrl+"/savePurchaseOrderTmp.ajax?type="+type+"&account="+userAcc;
        var partIds="";
        var supplierIds="";
        var myCounts="";
        var remarks="";
        
        var partCodes = "";
        var partNos = "";
        var partNames = "";
        var units = "";
        var minPacks = "";
        var minUnits = "";
        var ifSbs = "";
        var ifSuplys = "";
        var supplierCodes = "";
        var supplierNames = "";
        var rowspans = "";
        var planPrices = "";
        var orderCounts = "";
        var allPrices = "";
        var availableAmounts = "";
        var upperLimits = "";
        var lowerLimits = "";
        var positionNames = "";
        var applyCycles = "";
        
        var checkObjs="";
        $("#tab-order_info").find("tbody").find("tr").each(function(){
            var $checkbox=$(this).find("td").find("input[type='checkbox']:first");
           	var partId=$checkbox.attr("partId");
            var supplierId=$checkbox.attr("supplierId");
            var myCount=$(this).find("td").find("input[name='myCounts']:first").val();
               if(!myCount){
               	myCount="anull";
               }
            var remark=$(this).find("td").find("input[name='remarks']:first").val();
            if(!remark){
            	remark="anull";
            }
            var partCode = $checkbox.attr("partCode");
            var partNo = $checkbox.attr("partNo") || "anull";
            var partName = $checkbox.attr("partName");
            var unit = $checkbox.attr("unit");
            var partName = $checkbox.attr("partName");
            var minPack = $checkbox.attr("minPack");
            var minUnit = $checkbox.attr("minUnit");
            var ifSb = $checkbox.attr("ifSb");
            var ifSuply = $checkbox.attr("ifSuply");
            var supplierCode = $checkbox.attr("supplierCode");
            var supplierName = $checkbox.attr("supplierName");
            var rowspan = $checkbox.attr("rowSpans");
            var planPrice = $checkbox.attr("planPrice");
            var orderCount = $checkbox.attr("orderCount");
            var allPrice = $checkbox.attr("allPrice");
            var availableAmount = $checkbox.attr("availableAmount") || "anull";
            var upperLimit = $checkbox.attr("upperLimit") || "anull";
            var lowerLimit = $checkbox.attr("lowerLimit") || "anull";
            var positionName = $checkbox.attr("positionName") || "anull";
            var applyCycle = $checkbox.attr("applyCycle");
           	if(partIds.length==0) {
           		partIds = partId;
               } else {
               	partIds = partIds+","+partId;
               }
           	if(supplierIds.length==0) {
           		supplierIds = supplierId;
               } else {
               	supplierIds = supplierIds+","+supplierId;
               }
           	if(myCounts.length==0) {
           		myCounts = myCount;
               } else {
               	myCounts = myCounts+","+myCount;
               }
           	if(remarks.length==0) {
           		remarks = remark;
               } else {
               	remarks = remarks+","+remark;
               }
           	
           	if(partCodes.length==0) {
           		partCodes = partCode;
               } else {
            	   partCodes = partCodes+","+partCode;
               }
           	if(partNos.length==0) {
           		partNos = partNo;
               } else {
            	   partNos = partNos+","+partNo;
               }
           	if(partNames.length==0) {
           		partNames = partName;
               } else {
            	   partNames = partNames+","+partName;
               }
           	if(units.length==0) {
           		units = unit;
               } else {
            	   units = units+","+unit;
               }
           	if(minPacks.length==0) {
           		minPacks = minPack;
               } else {
            	   minPacks = minPacks+","+minPack;
               }
           	if(minUnits.length==0) {
           		minUnits = minUnit;
               } else {
            	   minUnits = minUnits+","+minUnit;
               }
           	if(ifSbs.length==0) {
           		ifSbs = ifSb;
               } else {
            	   ifSbs = ifSbs+","+ifSb;
               }
           	if(ifSuplys.length==0) {
           		ifSuplys = ifSuply;
               } else {
            	   ifSuplys = ifSuplys+","+ifSuply;
               }
           	if(supplierCodes.length==0) {
           		supplierCodes = supplierCode;
               } else {
            	   supplierCodes = supplierCodes+","+supplierCode;
               }
           	if(supplierNames.length==0) {
           		supplierNames = supplierName;
               } else {
            	   supplierNames = supplierNames+","+supplierName;
               }
           	if(rowspans.length==0) {
           		rowspans = rowspan;
               } else {
            	   rowspans = rowspans+","+rowspan;
               }
           	if(planPrices.length==0) {
           		planPrices = planPrice;
               } else {
            	   planPrices = planPrices+","+planPrice;
               }
           	if(orderCounts.length==0) {
           		orderCounts = orderCount;
               } else {
            	   orderCounts = orderCounts+","+orderCount;
               }
           	if(allPrices.length==0) {
           		allPrices = allPrice;
               } else {
            	   allPrices = allPrices+","+allPrice;
               }
           	if(availableAmounts.length==0) {
           		availableAmounts = availableAmount;
               } else {
            	   availableAmounts = availableAmounts+","+availableAmount;
               }
           	if(upperLimits.length==0) {
           		upperLimits = upperLimit;
               } else {
            	   upperLimits = upperLimits+","+upperLimit;
               }
           	if(lowerLimits.length==0) {
           		lowerLimits = lowerLimit;
               } else {
            	   lowerLimits = lowerLimits+","+lowerLimit;
               }
           	if(positionNames.length==0) {
           		positionNames = positionName;
               } else {
            	   positionNames = positionNames+","+positionName;
               }
           	if(applyCycles.length==0) {
           		applyCycles = applyCycle;
               } else {
            	   applyCycles = applyCycles+","+applyCycle;
               }
        });
        var myCountses = myCounts.split(",");
        for(var i=0;i<myCountses.length;i++){
        	if(myCountses[i] == "anull") continue;
        	if(!isNumV(myCountses[i])){
        		alertMsg.warn("请正确输入采购数量.");
        		return false;
        	}
        }
        //赋值
        $('#partIds2').val(partIds);
        $('#counts2').val(myCounts);
        $('#supplierIds2').val(supplierIds);
        $('#sremark2').val(remarks);
        
        $('#partCodes2').val(partCodes);
        $('#partNames2').val(partNames);
        $('#partNos2').val(partNos);
        $('#units2').val(units);
        $('#minPacks2').val(minPacks);
        $('#minUnits2').val(minUnits);
        $('#ifSbs2').val(ifSbs);
        $('#ifSuplys2').val(ifSuplys);
        $('#supplierCodes2').val(supplierCodes);
        $('#supplierNames2').val(supplierNames);
        $('#rowspans2').val(rowspans);
        $('#planPrices2').val(planPrices);
        $('#allPrices2').val(allPrices);
        $('#availableAmounts2').val(availableAmounts);
        $('#upperLimits2').val(upperLimits);
        $('#lowerLimits2').val(lowerLimits);
        $('#positionNames2').val(positionNames);
        $('#applyCycles2').val(applyCycles);
        $('#orderCounts2').val(orderCounts);
        var $f = $("#fm-partInfo2");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, shippingUrl, "btn-saveTmp", sCondition, savePartPlanCallBack); 
    });
});
function savePartPlanCallBack(res)
{
	return true;
}
function doSearchCallBack(res){
	$("#partInfo").html("");
	$("#tab-order_info").show();
	var rows = res.getElementsByTagName("ROW");
    if(rows && rows.length > 0)
    {
        $tab =$("#partInfo");
        var objXML;
        var partId="";
        var len =1;
        var xh = 0;
        for(var i=0;i<rows.length;i++){
            if (typeof(rows[i]) == "object") objXML = rows[i];
            else objXML = $.parseXML(rows[i]);
            len = getNodeText(rows[i].getElementsByTagName("ROWSPAN").item(0));
            var $row = $("<tr></tr>"); 
            var ifSuply = getNodeText(rows[i].getElementsByTagName("IF_SUPLY").item(0));
            if(ifSuply==<%=DicConstant.SF_01%>)
            {
            	xh++;
            	var $td0 = $("<td rowspan='"+len+"'><input type='hidden' name='partIds' value='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"'/>"+xh+"</td>");
                $row.append($td0);
                //将row的各列值赋值到checkbox的attr中
                var s = "<td style='display:none;'><input type='checkbox' checked='true' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'";
                    s += " tmpId='"+getNodeText(rows[i].getElementsByTagName("ID").item(0))+"' ";
                    s += " partId='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"' ";
                    s += " partCode='"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"' ";
                    s += " partName='"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"' ";
                    s += " partNo='"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"' ";
                    s += " unit='"+getNodeText(rows[i].getElementsByTagName("UNIT").item(0))+"' ";
                    s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                    s += " minUnit='"+getNodeText(rows[i].getElementsByTagName("MIN_UNIT").item(0))+"' ";
                    s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                    s += " ifSb='"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"' ";
                    s += " ifSuply='"+getNodeText(rows[i].getElementsByTagName("IF_SUPLY").item(0))+"' ";
                    s += " supplierId='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"' ";
                    s += " supplierCode='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_CODE").item(0))+"' ";
                    s += " supplierName='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"' ";
                    s += " rowSpans='"+getNodeText(rows[i].getElementsByTagName("ROWSPAN").item(0))+"' ";
                    s += " planPrice='"+getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0))+"' ";
                    s += " orderCount='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"' ";
                    s += " pchCount='"+getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0))+"' ";
                    s += " allPrice='"+getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0))+"' ";
                    s += " availableAmount='"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"' ";
                    s += " upperLimit='"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"' ";
                    s += " lowerLimit='"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"' ";
                    s += " positionName='"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"' ";
                    s += " applyCycle='"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"' ";
                    s += " remark='"+getNodeText(rows[i].getElementsByTagName("REMARK").item(0))+"' ";
                    
            	var $td1 = $(s + "/></td>");
                $row.append($td1);
            	var $td2 = $("<td>"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"</td>");
            	$row.append($td2);
                var $td3 = $("<td>"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"</td>");
                $row.append($td3);
                var $td4 = $("<td>"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"</td>");
                $row.append($td4);
                var $td5 = $("<td>"+getAttribValue(objXML, "UNIT","sv", 0)+"</td>");
                $row.append($td5);
                var $td6 = $("<td>"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"/"+getAttribValue(objXML, "MIN_UNIT","sv", 0)+"</td>");
                $row.append($td6);
                var $td7 = $("<td>"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"</td>");
                $row.append($td7);
                var $td8 = $("<td>"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"</td>");
                $row.append($td8);
            	var $td13= $("<td>"+amountFormatNew(getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0)))+"</td>");
            	$row.append($td13);
            	var $td14= $("<td>"+amountFormatNew(getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0)))+"</td>");
            	$row.append($td14);
            	var $td15= $("<td>"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"</td>");
            	$row.append($td15);
            	var $td16= $("<td>"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"</td>");
            	$row.append($td16);
                var $td17= $("<td>"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"</td>");
                $row.append($td17);
                var $td18= $("<td>"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"</td>");
                $row.append($td18);
                var $td9 = $("<td>"+getAttribValue(objXML, "IF_SUPLY","sv", 0)+"</td>");
                $row.append($td9);
                var $td10 = $("<td><input type='hidden' name='supplierIds' value='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"'/>"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"</td>");
                $row.append($td10);
                var $td101 = $("<td>"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"</td>");
                $row.append($td101);
                var pchCount = getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0));
                var remark = getNodeText(rows[i].getElementsByTagName("REMARK").item(0));
                if(pchCount){
                	var $td11 = $("<td ondblclick='doMyInputDblclick(this)' ><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+pchCount+"'/></td>");
                	$row.append($td11);
                }else{
                	 var $td11 = $("<td ondblclick='doMyInputDblclick(this)' ><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"'/></td>");
                 	 $row.append($td11);
                }
                if(remark){
                	var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value='"+remark+"'></td>");
                	$row.append($td12);
                }else{
                	var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value=''></td>");
                	$row.append($td12);
                }
            }else{
            	if(partId!= getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))){
                	partId = getNodeText(rows[i].getElementsByTagName("PART_ID").item(0));
                	xh++;
                	var $td0 = $("<td rowspan='"+len+"'><input type='hidden' name='partIds' value='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"'/>"+xh+"</td>");
                    $row.append($td0);
                	if(len == 1)
                	{
                		//var $td1 = $("<td style='display:none;'><input type='checkbox' checked='true' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'/></td>");
                        //$row.append($td1);
                		//将row的各列值赋值到checkbox的attr中
                        var s = "<td style='display:none;'><input type='checkbox' checked='true' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'";
                            s += " tmpId='"+getNodeText(rows[i].getElementsByTagName("ID").item(0))+"' ";
                            s += " partId='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"' ";
                            s += " partCode='"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"' ";
                            s += " partName='"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"' ";
                            s += " partNo='"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"' ";
                            s += " unit='"+getNodeText(rows[i].getElementsByTagName("UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " minUnit='"+getNodeText(rows[i].getElementsByTagName("MIN_UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " ifSb='"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"' ";
                            s += " ifSuply='"+getNodeText(rows[i].getElementsByTagName("IF_SUPLY").item(0))+"' ";
                            s += " supplierId='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"' ";
                            s += " supplierCode='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_CODE").item(0))+"' ";
                            s += " supplierName='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"' ";
                            s += " rowSpans='"+getNodeText(rows[i].getElementsByTagName("ROWSPAN").item(0))+"' ";
                            s += " planPrice='"+getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0))+"' ";
                            s += " orderCount='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"' ";
                            s += " pchCount='"+getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0))+"' ";
                            s += " allPrice='"+getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0))+"' ";
                            s += " availableAmount='"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"' ";
                            s += " upperLimit='"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"' ";
                            s += " lowerLimit='"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"' ";
                            s += " positionName='"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"' ";
                            s += " applyCycle='"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"' ";
                            s += " remark='"+getNodeText(rows[i].getElementsByTagName("REMARK").item(0))+"' ";
                            
                    	var $td1 = $(s + "/></td>");
                        $row.append($td1);
                	}else
                	{
                		//var $td1 = $("<td style='display:none;'><input type='checkbox' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'/></td>");
                        //$row.append($td1);
                		//将row的各列值赋值到checkbox的attr中
                		var pchCount = getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0));
                		var t="";
                		if(pchCount){
                			t="checked='chekced'";
                		}
                        var s = "<td style='display:none;'><input type='checkbox' name='keyIds' "+t+" onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'";
                            s += " tmpId='"+getNodeText(rows[i].getElementsByTagName("ID").item(0))+"' ";
                            s += " partId='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"' ";
                            s += " partCode='"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"' ";
                            s += " partName='"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"' ";
                            s += " partNo='"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"' ";
                            s += " unit='"+getNodeText(rows[i].getElementsByTagName("UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " minUnit='"+getNodeText(rows[i].getElementsByTagName("MIN_UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " ifSb='"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"' ";
                            s += " ifSuply='"+getNodeText(rows[i].getElementsByTagName("IF_SUPLY").item(0))+"' ";
                            s += " supplierId='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"' ";
                            s += " supplierCode='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_CODE").item(0))+"' ";
                            s += " supplierName='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"' ";
                            s += " rowSpans='"+getNodeText(rows[i].getElementsByTagName("ROWSPAN").item(0))+"' ";
                            s += " planPrice='"+getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0))+"' ";
                            s += " orderCount='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"' ";
                            s += " pchCount='"+getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0))+"' ";
                            s += " allPrice='"+getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0))+"' ";
                            s += " availableAmount='"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"' ";
                            s += " upperLimit='"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"' ";
                            s += " lowerLimit='"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"' ";
                            s += " positionName='"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"' ";
                            s += " applyCycle='"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"' ";
                            s += " remark='"+getNodeText(rows[i].getElementsByTagName("REMARK").item(0))+"' ";
                            
                    	var $td1 = $(s + "/></td>");
                        $row.append($td1);
                	}
                	var $td2 = $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"</td>");
                	$row.append($td2);
                    var $td3 = $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"</td>");
                    $row.append($td3);
                    var $td4 = $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"</td>");
                    $row.append($td4);
                    var $td5 = $("<td rowspan='"+len+"'>"+getAttribValue(objXML, "UNIT","sv", 0)+"</td>");
                    $row.append($td5);
                    var $td6 = $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"/"+getAttribValue(objXML, "MIN_UNIT","sv", 0)+"</td>");
                    $row.append($td6);
                    var $td7 = $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"</td>");
                    $row.append($td7);
                    var $td8 = $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"</td>");
                    $row.append($td8);
                	var $td13= $("<td rowspan='"+len+"'>"+amountFormatNew(getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0)))+"</td>");
                	$row.append($td13);
                	var $td14= $("<td rowspan='"+len+"'style='text-align:right;'>"+amountFormatNew(getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0)))+"</td>");
                	$row.append($td14);
                	var $td15= $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"</td>");
                	$row.append($td15);
                	var $td16= $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"</td>");
                	$row.append($td16);
                    var $td17= $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"</td>");
                    $row.append($td17);
                    var $td18= $("<td rowspan='"+len+"'>"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"</td>");
                    $row.append($td18);
                    var $td9 = $("<td rowspan='"+len+"'>"+getAttribValue(objXML, "IF_SUPLY","sv", 0)+"</td>");
                    $row.append($td9);
                    var $td10 = $("<td><input type='hidden' name='supplierIds' value='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"'/>"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"</td>");
                    $row.append($td10);
                    var $td101 = $("<td>"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"</td>");
                    $row.append($td101);
                    var pchCount = getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0));
                    var remark = getNodeText(rows[i].getElementsByTagName("REMARK").item(0));
                	if(len==1){
                		if(pchCount){
                			 var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+pchCount+"'/></td>");
                          	$row.append($td11);
                		}else{
                			 var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"'/></td>");
                          	$row.append($td11);
                		}
                		if(remark){
                			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value='"+remark+"'></td>");
                        	$row.append($td12);
	               		}else{
	               			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value=''></td>");
	                    	$row.append($td12);
	               		}
                		
                	}else{
                		if(pchCount){
               			 	var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+pchCount+"'/></td>");
                         	$row.append($td11);
	               		}else{
	               			 var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value=''/></td>");
	                         	$row.append($td11);
	               		}
                		if(remark){
                			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value='"+remark+"'></td>");
                        	$row.append($td12);
	               		}else{
	               			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value=''></td>");
	                    	$row.append($td12);
	               		}
                	}
                	
                }else{
                	if(len == 1)
                	{
                		//var $td1 = $("<td style='display:none;'><input type='checkbox' checked='true' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'/></td>");
                        //$row.append($td1);
                		//将row的各列值赋值到checkbox的attr中
                        var s = "<td style='display:none;'><input type='checkbox' checked='true' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'";
                            s += " tmpId='"+getNodeText(rows[i].getElementsByTagName("ID").item(0))+"' ";
                            s += " partId='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"' ";
                            s += " partCode='"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"' ";
                            s += " partName='"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"' ";
                            s += " partNo='"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"' ";
                            s += " unit='"+getNodeText(rows[i].getElementsByTagName("UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " minUnit='"+getNodeText(rows[i].getElementsByTagName("MIN_UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " ifSb='"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"' ";
                            s += " ifSuply='"+getNodeText(rows[i].getElementsByTagName("IF_SUPLY").item(0))+"' ";
                            s += " supplierId='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"' ";
                            s += " supplierCode='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_CODE").item(0))+"' ";
                            s += " supplierName='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"' ";
                            s += " rowSpans='"+getNodeText(rows[i].getElementsByTagName("ROWSPAN").item(0))+"' ";
                            s += " planPrice='"+getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0))+"' ";
                            s += " orderCount='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"' ";
                            s += " pchCount='"+getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0))+"' ";
                            s += " allPrice='"+getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0))+"' ";
                            s += " availableAmount='"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"' ";
                            s += " upperLimit='"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"' ";
                            s += " lowerLimit='"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"' ";
                            s += " positionName='"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"' ";
                            s += " applyCycle='"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"' ";
                            s += " remark='"+getNodeText(rows[i].getElementsByTagName("REMARK").item(0))+"' ";
                            
                    	var $td1 = $(s + "/></td>");
                        $row.append($td1);
                	}else
                	{
                		//var $td1 = $("<td style='display:none;'><input type='checkbox' name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'/></td>");
                        //$row.append($td1);
                		//将row的各列值赋值到checkbox的attr中
                		var pchCount = getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0));
                		var t="";
                		if(pchCount){
                			t="checked='chekced'";
                		}
                        var s = "<td style='display:none;'><input type='checkbox' "+t+" name='keyIds' onclick='doCheckbox(this);' value='"+getNodeText(rows[i].getElementsByTagName("KEY_ID").item(0))+"'";
                            s += " tmpId='"+getNodeText(rows[i].getElementsByTagName("ID").item(0))+"' ";
                            s += " partId='"+getNodeText(rows[i].getElementsByTagName("PART_ID").item(0))+"' ";
                            s += " partCode='"+getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0))+"' ";
                            s += " partName='"+getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0))+"' ";
                            s += " partNo='"+getNodeText(rows[i].getElementsByTagName("PART_NO").item(0))+"' ";
                            s += " unit='"+getNodeText(rows[i].getElementsByTagName("UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " minUnit='"+getNodeText(rows[i].getElementsByTagName("MIN_UNIT").item(0))+"' ";
                            s += " minPack='"+getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0))+"' ";
                            s += " ifSb='"+getNodeText(rows[i].getElementsByTagName("IF_SB").item(0))+"' ";
                            s += " ifSuply='"+getNodeText(rows[i].getElementsByTagName("IF_SUPLY").item(0))+"' ";
                            s += " supplierId='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"' ";
                            s += " supplierCode='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_CODE").item(0))+"' ";
                            s += " supplierName='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"' ";
                            s += " rowSpans='"+getNodeText(rows[i].getElementsByTagName("ROWSPAN").item(0))+"' ";
                            s += " planPrice='"+getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0))+"' ";
                            s += " orderCount='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"' ";
                            s += " pchCount='"+getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0))+"' ";
                            s += " allPrice='"+getNodeText(rows[i].getElementsByTagName("ALL_PRICE").item(0))+"' ";
                            s += " availableAmount='"+getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0))+"' ";
                            s += " upperLimit='"+getNodeText(rows[i].getElementsByTagName("UPPER_LIMIT").item(0))+"' ";
                            s += " lowerLimit='"+getNodeText(rows[i].getElementsByTagName("LOWER_LIMIT").item(0))+"' ";
                            s += " positionName='"+getNodeText(rows[i].getElementsByTagName("POSITION_NAME").item(0))+"' ";
                            s += " applyCycle='"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"' ";
                            s += " remark='"+getNodeText(rows[i].getElementsByTagName("REMARK").item(0))+"' ";
                            
                    	var $td1 = $(s + "/></td>");
                        $row.append($td1);
                	}
                	var pchCount = getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0));
                    var remark = getNodeText(rows[i].getElementsByTagName("REMARK").item(0));
                	var $td10 = $("<td><input type='hidden' name='supplierIds' value='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"'/>"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0))+"</td>");
                    $row.append($td10);
                    var $td101 = $("<td>"+getNodeText(rows[i].getElementsByTagName("APPLY_CYCLE").item(0))+"</td>");
                    $row.append($td101);
                    if(len==1){
                		if(pchCount){
                			 var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+pchCount+"'/></td>");
                          	$row.append($td11);
                		}else{
                			 var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0))+"'/></td>");
                          	$row.append($td11);
                		}
                		if(remark){
                			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value='"+remark+"'></td>");
                        	$row.append($td12);
	               		}else{
	               			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value=''></td>");
	                    	$row.append($td12);
	               		}
                		
                	}else{
                		if(pchCount){
               			 	var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value='"+pchCount+"'/></td>");
                         	$row.append($td11);
	               		}else{
	               			 var $td11 = $("<td ondblclick='doMyInputDblclick(this)'><input type='text' disabled='true' style='width:50px;background:#dfe3e5' name='myCounts' onBlur='doMyInputBlur(this)' id='"+getNodeText(rows[i].getElementsByTagName("SUPPLIER_ID").item(0))+"_"+(i+1)+"' value=''/></td>");
	                         $row.append($td11);
	               		}
                		if(remark){
                			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value='"+remark+"'></td>");
                        	$row.append($td12);
	               		}else{
	               			var $td12= $("<td><input type='text' style='width:85px;' name='remarks' value=''></td>");
	                    	$row.append($td12);
	               		}
                	}
                }
            }
            $tab.append($row);
        }
    }
	return true;
}
function addPartCallBack(){
	$("#partInfo").html("");
	$("#btn-add").attr("disabled",false);
	var type = $("#PURCHASE_TYPE").val();
	var searchUrl = mngUrl+"/partSearch.ajax?type="+type;
	var $f = $("#fm-searchPchOrder");
	var sCondition = {};
	sCondition = $f.combined() || {};
	sendPost(searchUrl, "btn-search", sCondition, doSearchCallBack, "");
}
//双击input框事件
function doMyInputDblclick(obj)
{
	var $obj = $(obj);
	$obj.find("input:first").attr("disabled",false);
    $obj.find("input:first").css("background","#fff");
    $obj.find("input:first").focus();
}
function doMyInputBlur(obj){
    var $obj = $(obj);
  	//设置颜色
    $obj.css("background","#dfe3e5");
  	$obj.attr("disabled",true);
    var $tr = $obj;
    while($tr.get(0).tagName != "TR")
    	$tr = $tr.parent();
    var checkObj = $tr.find("input[type='checkbox']:first");
    if($obj.val() == ""){
    	checkObj.attr("checked",false);
        return false;
    }
    if($obj.val() && !isNum($obj))
    {
        alertMsg.warn("请输入大于零的整数.");
        $obj.val("");
        checkObj.attr("checked",false);
        return false;
    }
    checkObj.attr("checked",true);
}
function isNum($obj)
{
    var reg = /^[1-9]\d*$/;
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}
function isNumV(val)
{
    var reg = /^[1-9]\d*$/;
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}
function checkAll(checkbox){
	$("#tab-order_info").find("tbody").find("tr").each(function(){
        var checkObj=$(this).find("td").eq(1).find("input:first");
        if($(checkbox).is(":checked")){
        	checkObj.attr("checked",true);
        }else{
        	checkObj.attr("checked",false);
        }
	});
}
function afterDicItemClick(id,$row){
	var ret = true;
	if(id == "PERSON_NAME")
	{
		var ACCOUNT = $row.attr("A.ACCOUNT");
		var NAME = $row.attr("A.PERSON_NAME")
		$('#PERSON_NAME').attr('code',ACCOUNT);
        $('#PERSON_NAME').val(NAME);
	}
	return ret;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 采购管理  &gt; 采购订单管理   &gt; 采购计划制定</h4>
	<div class="page">
	<div class="pageHeader" >
		<form method="post" id="fm-searchPchOrder">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>采购员：</label></td>
						<td>
					    	<input type="text" id="PERSON_NAME" name="PERSON_NAME" action="show" datasource="T1.USER_ACCOUNT" kind="dic" src="" datatype="1,is_null,300" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td><label>计划类型：</label></td>
					    <td><select type="text" class="combox" id="PURCHASE_TYPE" name="PURCHASE_TYPE" kind="dic" src="CGDDLX"  filtercode="<%=DicConstant.CGDDLX_02%>|<%=DicConstant.CGDDLX_01%>|<%=DicConstant.CGDDLX_06%>|<%=DicConstant.CGDDLX_07%>" datatype="0,is_null,6" readonly="readonly">
							    	<option value="<%=DicConstant.CGDDLX_02%>" selected>缺件</option>
					    </select></td>
				    	<td><label>配件代码：</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="T.PART_CODE"  operation="like"  datatype="1,is_null,30"/></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="T.PART_NAME"  operation="like"  datatype="1,is_null,30"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-reSearch" >重新计算缺件</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-search" >查询已暂存计划</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-saveTmp" >暂存本次调整</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >生成采购单</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
		<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_order" style="overflow:auto;">
			<table style="display:none;width:100%;"class="dlist" id="tab-order_info" name="tablist" ref="page_order">
					<thead>
						<tr>	
							<th>序号</th>
							<th style="display:none;"><input type="checkbox" id="checkall" onclick="checkAll(this);" /></th>
                            <th>配件代码</th>
                            <th>参图号</th>
                            <th>配件名称</th>
                            <th>单位</th>
                            <th>最小包装</th>
                            <th>是否三包</th>
                            <th style="width:45px;display:none">采购员</th>
                            <th>缺件数</th>
                            <th>计划价</th>
                            <th style="text-align:right;">计划金额</th>
                            <th>库区</th>
                            <th>库存数</th>
                            <th>库上限</th>
                            <th>库下限</th>
                            <th>指定供应商</th>
                            <th>供应商名称</th>
                            <th style='width:30px;'>采购周期</th>
                            <th>采购数<br/><span style="color:red;">双击修改</span></th>
                            <th style="width:85px;">备注</th>
                            
						</tr>
					</thead>
					<tbody id="partInfo">
                    </tbody>
			</table>
		</div>
	</div>
      <form id="fm-partInfo">
        <input type="hidden" id="partIds" datasource="PARTIDS"/>
        <input type="hidden" id="counts"  datasource="COUNTS"/>
        <input type="hidden" id="supplierIds" datasource="SUPPLIERS"/>
        <input type="hidden" id="sremark" datasource="REMARKS"/>
   	  </form>
   	  <form id="fm-partInfo2">
        <input type="hidden" id="partIds2" datasource="PARTIDS"/>
        <input type="hidden" id="counts2"  datasource="PCH_COUNTS"/>
        <input type="hidden" id="supplierIds2" datasource="SUPPLIERS"/>
        <input type="hidden" id="sremark2" datasource="REMARKS"/>
        
        <input type="hidden" id="partCodes2" datasource="PART_CODE"/>
        <input type="hidden" id="partNames2" datasource="PART_NAME"/>
        <input type="hidden" id="partNos2" datasource="PART_NO"/>
        <input type="hidden" id="units2" datasource="UNIT"/>
        <input type="hidden" id="minPacks2" datasource="MIN_PACK"/>
        <input type="hidden" id="minUnits2" datasource="MIN_UNIT"/>
        <input type="hidden" id="ifSbs2" datasource="IF_SB"/>
        <input type="hidden" id="ifSuplys2" datasource="IF_SUPLY"/>
        <input type="hidden" id="orderCounts2"  datasource="ORDER_COUNT"/>
        <input type="hidden" id="supplierCodes2" datasource="SUPPLIER_CODE"/>
        <input type="hidden" id="supplierNames2" datasource="SUPPLIER_NAME"/>
        <input type="hidden" id="rowspans2" datasource="ROWSPAN"/>
        <input type="hidden" id="planPrices2" datasource="PLAN_PRICE"/>
        <input type="hidden" id="allPrices2" datasource="ALL_PRICE"/>
        <input type="hidden" id="availableAmounts2" datasource="AVAILABLE_AMOUNT"/>
        <input type="hidden" id="upperLimits2" datasource="UPPER_LIMIT"/>
        <input type="hidden" id="lowerLimits2" datasource="LOWER_LIMIT"/>
        <input type="hidden" id="positionNames2" datasource="POSITION_NAME"/>
        <input type="hidden" id="applyCycles2" datasource="APPLY_CYCLE"/>
   	  </form>
	</div>
</div>
</body>
</html>