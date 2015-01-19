<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>采购订单信息</title>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String ORDER_ID = request.getParameter("ORDER_ID");
%>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var mngUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderUnlockAction";
var ORDER_ID = "<%=ORDER_ID%>";
$(function(){
	$("#div-partOrderDetaillist").height(document.documentElement.clientHeight-100);
	$("button.close").click(function(){
		var dialog = parent.$("body").data("saleOrderDetail");
		parent.$.pdialog.close(dialog);
		return false;
	});
	
	$('input').attr("readonly","readonly");
	var $f = $("");
	var sCondition = {};
   	sCondition = $f.combined() || {};
   	var search = mngUrl+"/saleOrderInfoSearch.ajax?ORDER_ID="+ORDER_ID;
   	sendPost(search,"",sCondition,searchContractCallBack,"false");
	
	
	//设置高度
    
   
});
function partOrderDetailSearch(){
    var $f = $("#diaPartListFm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    var detailSearchUrl = mngUrl+"/partOrderDetailSearch.ajax?&orderId="+ORDER_ID;  
    doFormSubmit($f, detailSearchUrl, "", 1, sCondition, "partOrderDetailList");
}
function searchContractCallBack(res)
{
	var rows = res.getElementsByTagName("ROW");
	setEditValue("diaPartOrderInfoTab",res.documentElement);
    var orderNO = getNodeText(rows[0].getElementsByTagName("ORDER_NO").item(0));
    var orderType = getNodeText(rows[0].getElementsByTagName("ORDER_TYPE").item(0));
    $("#diaOrderNo").html(orderNO);
    $("#diaOrderType1").val(orderType);
    
    // 是否是延期订单
    if(<%=DicConstant.SF_01%> == getNodeText(rows[0].getElementsByTagName("IF_DELAY_ORDER").item(0))) {
        $("#checkReturn").hide();
    }
    if($("#diaOrderType1").val()==<%=DicConstant.DDLX_04%>){
        // 总成订单
        $("#planProduceNo").show();
        $("#planProduceNoName").show();
    }
    if($("#diaOrderType1").val()==<%=DicConstant.DDLX_05%>){
        // 直发订单
        // 显示供应商
        $("#tr-supplier").show();
    }
    if($("#diaOrderType1").val()==<%=DicConstant.DDLX_06%>){
        $("#tr-progrom").show();
    }
    $("#diaIfDelay").val(getNodeText(rows[0].getElementsByTagName("IF_DELAY_ORDER").item(0)));
    $("#diaOrderAmount").html(getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0))+"元");
    
    //账户可用余额查询
    var accountSearchUrl = mngUrl + "/accountSearch.ajax?&orgId="+getNodeText(rows[0].getElementsByTagName("ORG_ID").item(0));
    sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
    //订单资金使用查询
    var fundsSearchUrl = mngUrl + "/orderFundsListSearch.ajax?&orderId="+ORDER_ID;
    sendPost(fundsSearchUrl, "", "", fundsSearchCallback, "false");
    //订单审核记录查询
    var checkSearchUrl = mngUrl + "/orderCheckListSearch.ajax?&orderId="+ORDER_ID;
    sendPost(checkSearchUrl, "", "", checkSearchCallback, "false");
    
    
    partOrderDetailSearch();
	return true;
}
//账户可用余额查询回调
function accountSearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
            for(var i=0;i<rows.length;i++){
                var type = getNodeText(rows[i].getElementsByTagName("TYPE").item(0));
                var availableAmount = getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0));
                if(type=="1"){
                    $("#diaMoney").html(amountFormatNew(availableAmount)+"元");
                }
                if(type=="2"){
                    $("#diaQuota").html(amountFormatNew(availableAmount)+"元");
                }
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function fundsSearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
            $tab =$("#fundsTab");
            for(var i=0;i<rows.length;i++){
                var $row = $("<tr></tr>"); 
                var $td1 = $("<td>"+(i+1)+"</td>");
                var $td2 = $("<td>"+getNodeText(rows[i].getElementsByTagName("ACCOUNT_TYPE").item(0))+"</td>");
                var $td3 = $("<td>"+amountFormatNew(getNodeText(rows[i].getElementsByTagName("AVAILABLE_AMOUNT").item(0)))+"</td>");
                var $td4 = $("<td>"+amountFormatNew(getNodeText(rows[i].getElementsByTagName("PAY_AMOUNT").item(0)))+"</td>");
                $row.append($td1);
                $row.append($td2); 
                $row.append($td3); 
                $row.append($td4); 
                $tab.append($row); 
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function checkSearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
            $tab =$("#checkTab");
            var objXML;
            for(var i=0;i<rows.length;i++){
                if (typeof(rows[i]) == "object") objXML = rows[i];
                else objXML = $.parseXML(rows[i]);
                var $row = $("<tr></tr>"); 
                var $td1 = $("<td>"+(i+1)+"</td>");
                var $td2 = $("<td>"+getAttribValue(objXML, "CHECK_DATE","sv", 0)+"</td>");
                var $td3 = $("<td>"+getNodeText(rows[i].getElementsByTagName("CHECK_RESULT").item(0))+"</td>");
                var $td4 = $("<td>"+getNodeText(rows[i].getElementsByTagName("CHECK_ORG").item(0))+"</td>");
                var $td5 = $("<td>"+getNodeText(rows[i].getElementsByTagName("CHECK_USER").item(0))+"</td>");
                var $td6 = $("<td>"+getNodeText(rows[i].getElementsByTagName("REMARKS").item(0))+"</td>");
                $row.append($td1);
                $row.append($td2); 
                $row.append($td3); 
                $row.append($td4); 
                $row.append($td5); 
                $row.append($td6);
                $tab.append($row); 
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function supplierShow(obj){
    var $tr = $(obj).parent();
    var ifSupplier = $tr.attr("IF_SUPPLIER");
    if(ifSupplier==<%=DicConstant.SF_01%>){
        return $tr.attr("SUPPLIER_NAME");
    }else{
        return "";
    }
}
function myplanProduceNo(obj) {
    var $tr =$(obj).parent();
    return "<input type='text' id='planProduce"+$tr.attr("DTL_ID")+"' name='planProduce"+$tr.attr("DTL_ID")+"' style='width:60px;' datatype='0,is_null,30'>";
}
</script>
</head>
<body>
<div id="layout">
<div class="tabs" eventType="click" currentIndex="1" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0)"><span>订单信息</span></a></li>
                    <li><a href="javascript:void(0)"><span>配件清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <div>
	            <form id="exportFm" method="post" style="display:none">
			        <input type="hidden" id="params" name="data">
			    </form>
                <form method="post" id="diaPartOrderInfoFm" class="editForm">
                    <input type="hidden" id="diaOrderId" name="diaOrderId" datasource="ORDER_ID"/>
                    <input type="hidden" id="diaOrderType1" name="diaOrderType1"/>
                    <div id="divPartOrderInfo" style="overflow:auto;">
                        <fieldset>
                        <legend align="right"><a onclick="onTitleClick('diaPartOrderInfoTab')">&nbsp;基本信息&gt;&gt;</a></legend>
                        <table class="editTable" id="diaPartOrderInfoTab">
                            <tr >
                                <td><label>所属单位：</label></td>
                                <td><input type="text" id="diaOrgName"  name="diaOrgName" datasource="ORG_NAME" datatype="1,is_null,30" value="" readOnly/></td>
                                <td><label>提报时间：</label></td>
                                <td><input type="text" id="diaApplyDate"  name="diaApplyDate" datasource="APPLY_DATE" datatype="1,is_null,30" value="" readOnly/></td>
                                <td><label>提报人：</label></td>
                                <td><input type="text" id="diaCreateUser"  name="diaCreateUser" datasource="CREATE_USER" datatype="1,is_null,30" value="" readOnly/></td>
                            </tr>
                            <tr style="color:red;">
                                <td><label>订单编号：</label></td>
                                <td><div id="diaOrderNo"></div></td>
                                <td><label>现金可用：</label></td>
                                <td><div id="diaMoney"></div></td>
                                <td><label>额度可用：</label></td>
                                <td><div id="diaQuota"></div></td>
                            </tr>
                            <tr>
                                <td><label>订单类型：</label></td>
                                <td>
                                    <input type="text" id="diaOrderType"  name="diaOrderType" kind="dic" src="" datasource="ORDER_TYPE" datatype="1,is_null,30" readOnly value=""/>
                                </td>
                                <td><label>供货配件库：</label></td>
                                <td>
                                    <input type="text" id="diaWarehouseName"  name="diaWarehouseName" datasource="WAREHOUSE_NAME" datatype="1,is_null,30" value="" readOnly/>
                                </td>
                                <td><label>期望交货日期：</label></td>
                                <td>
                                    <input type="text" id="diaExpectDate" name="diaExpectDate" datasource="EXPECT_DATE" datatype="1,is_null,30" value="" readOnly />
                                </td>
                             </tr>
                             <tr>
                                 <td><label>运输方式：</label></td>
                                <td>
                                    <input type="text" id="diaTransType"  name="diaTransType" kind="dic" src="" datasource="TRANS_TYPE" datatype="1,is_null,30" value="" readOnly />
                                </td>
                                <td><label>交货地点：</label></td>
                                <td colspan="3">
                                    <input type="text" style="width:89%;" id="diaDeliveryAddr"  name="diaDeliveryAddr" datasource="DELIVERY_ADDR" datatype="1,is_null,30" value="" readOnly />
                                </td>    
                            </tr>
                            <tr>
                                <td><label>联系人：</label></td>
                                <td><input type="text" id="diaLinkMan"  name="diaLinkMan" datasource="LINK_MAN" datatype="1,is_null,30" value="" readOnly/></td>
                                <td><label>联系方式：</label></td>
                                <td><input type="text" id="diaPhone"  name="diaPhone" datasource="PHONE" datatype="1,is_null,30" value="" readOnly/></td>
                                <td><label>邮政编码：</label></td>
                                <td><input type="text" id="diaZipCode"  name="diaZipCode" datasource="ZIP_CODE" datatype="1,is_digit,15" value="" readOnly/></td>
                            </tr>
                            <tr style="display:none" id="tr-progrom">
                                <td><label>促销活动：</label></td>
                                <td colspan="3">
                                    <input type="text" style="width:89%;"  id="diaPromId"  name="diaPromId" datasource="PROM_NAME" datatype="1,is_null,30" readOnly value="" />
                                </td>
                                <td></td>
                            </tr>
                            <tr style="display:none" id="tr-supplier">
                                <td><label>供应商：</label></td>
                                <td colspan="3">
                                    <input type="text" style="width:89%;" id="diaSupplierCode"  name="diaSupplierCode" datasource="SUPPLIER_NAME"  datatype="1,is_null,30" readOnly value=""/>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><label>订单金额：</label></td>
                                <td style="width:195px;"><div id="diaOrderAmount"></div></td>
                                <td><label>是否免运费：</label></td>
                                <td>
                                    <input type="text" style="width:50px;" id="diaIfTrans"  name="diaIfTrans" kind="dic" src="" datasource="IF_TRANS"  datatype="1,is_null,30" readOnly value=""/>
                                </td>
                                <td><label>是否使用额度：</label></td>
                                <td>
                                    <input type="text" style="width:50px;" id="diaIfCredit"  name="diaIfCredit" kind="dic" src="" datasource="IF_CREDIT"  datatype="1,is_null,30" readOnly value="" />
                                </td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="5"><textarea id="diaRemarks" style="width:89%;height:40px;" name="diaRemarks" datasource="REMARKS"  datatype="1,is_null,100" readOnly></textarea></td>
                            </tr>
                        </table>
                        </fieldset>
                        <fieldset >
                        <legend align="right"><a onclick="onTitleClick('divPartOrderFuns')">&nbsp;付款信息&gt;&gt;</a></legend>
                        <div id="divPartOrderFuns">
                        <div id="divPartOrderFunsList" style="width:95%;text-align:center;">
                            <table style="width:99%;" id="diaPratOrderFundsList" name="diaPratOrderFundsList" class="dlist" ref="divPartOrderFunsList">
                                <thead>
                                    <tr>
                                        <th type="single" name="XH" style="display:;" ></th>
                                        <th fieldname="ACCOUNT_TYPE">账户类型名称</th>
                                        <th fieldname="AVAILABLE">可用余额(元)</th>
                                        <th fieldname="PAY_AMOUNT">本次使用金额(元)</th>
                                    </tr>
                                </thead>
                                <tbody id="fundsTab">
                                </tbody>
                            </table>
                        </div>
                        </div>
                        </fieldset>
                        <fieldset >
                        <legend align="right"><a onclick="onTitleClick('divPartOrderCheckInfo')">&nbsp;审核信息&gt;&gt;</a></legend>
                        <div id="divPartOrderCheckInfo">
                        <div id="partOrderCheckInfoDiv" style="width:95%;text-align:center;">
                            <table style="width:99%;" id="diaPartOrderCheckList" name="diaPartOrderCheckList" class="dlist" ref="partOrderCheckInfoDiv">
                                <thead>
                                    <tr>
                                        <th type="single" name="XH" style="display:" ></th>
                                        <th fieldname="ZHLXDM" >审核时间</th>
                                        <th fieldname="ZHLXMC" >审核结果</th>
                                        <th fieldname="KYYE">审核部门</th>
                                        <th fieldname="BCSYJE" >审核人</th>
                                        <th fieldname="BCSYJE" >审核意见</th>
                                    </tr>
                                </thead>
                                <tbody id="checkTab">
                                </tbody>
                            </table>
                        </div>
                        </div>
                        </fieldset>
                    </div>
                </form>
            </div>
            <div style="overflow:auto;" id="div-partOrderDetaillist">
                <form method="post" id="diaPartListFm">
                    <table id="diaPartListTab"></table>
                </form>
                <form method="post" id="saveFm">
                <input type="hidden" id="dtlIds" datasource="DTLIDS"/>
                <input type="hidden" id="auditCounts" datasource="AUDITCOUNTS"/>
<!--                 <input type="hidden" id="planProduceNos" datasource="PLAN_PRODUCE_NOS"/> -->
                <input type="hidden" id="diaIfDelay" name="diaIfDelay" datasource="IF_DELAY_ORDER"/>
                <div id="divPartOrderDetail" style="overflow:auto;">
                    <table style="display:none;width:98%;" id="partOrderDetailList" name="partOrderDetailList" limitH="false" ref="divPartOrderDetail" refQuery="diaPartListTab">
                    <thead>
                        <tr>
                            <th fieldname="ROWNUMS" style="display:"></th>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="DTL_ID" refer="myDtlId" style="display:none"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="170">配件名称</th>
                            <th fieldname="UNIT" colwidth="70">计量单位</th>
                            <th fieldname="MIN_PACK" refer="appendStr"  colwidth="70">最小包装</th>
                            <th fieldname="UNIT_PRICE" refer="amountFormatStr" style="text-align:right;">经销商价</th>
                            <th fieldname="IF_SUPPLIER" colwidth="90" style="display:none">是否指定供应商</th>
                            <th fieldname="SUPPLIER_NAME"  refer="supplierShow">供应商</th>
                            <th fieldname="ORDER_COUNT">订购数量</th>
                            <th fieldname="AMOUNT">总数量</th>
                            <th fieldname="OCCUPY_AMOUNT">锁定库存</th>
                            <th fieldname="AVAILABLE_AMOUNT">可用库存</th>
                            <th fieldname="AUDIT_COUNT" >审核数量</th>
                            <th fieldname="PLAN_PRODUCE_NO" style="display:none" refer="myplanProduceNo">计生号</th>
                            <!-- 
                            <th fieldname="USER_ACCOUNT">库管员</th>
                             -->
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="formBar" style="width:98%;">
                <ul>
                    <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                </ul>
            </div>
            </form>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>	
</body>
</html>