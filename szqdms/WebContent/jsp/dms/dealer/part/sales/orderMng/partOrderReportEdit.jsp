<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100202");
    String paraValue1 = userPara.getParavalue1();
    String paraValue2 = userPara.getParavalue2();
    String paraValue3 = userPara.getParavalue3();
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String orgId = user.getOrgId();
    String orgType = user.getOrgDept().getOrgType();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>配件清单</span></a></li>
                    <li id="orderPay"><a href="javascript:void(0);"><span>付款订购</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" >
            <div style="height:auto;overflow:hidden;" id="editDiv">
                <form method="post" id="diaOrderInfoFm" class="editForm" style="width:99%;">
                    <input type="hidden" id="diaOrderId" name="diaOrderId" datasource="ORDER_ID"/>
                    <input type="hidden" id="dia-orderType" name="dia-orderType"/>
                    <input type="hidden" id="diaOrderAmount" name="diaOrderAmount" datasource="ORDER_AMOUNT"/>
                    <input type="hidden" id="diaFirstLetter" name="diaFirstLetter" datasource="FIRST_LETTER"/>
                    <input type="hidden" id="diaProvinceName" name="diaProvinceName" datasource="PROVINCE_NAME"/>
                    <input type="hidden" id="diaCityName" name="diaCityName" datasource="CITY_NAME"/>
                    <input type="hidden" id="diaCountryName" name="diaCountryName" datasource="COUNTRY_NAME"/>
                    <div align="left">
                        <table class="editTable" id="diaOrderInfoTab">
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><div id="diaOrderNo">系统自动生成</div></td>
                                <td><label id="diaMoneyTd">现金可用：</label></td>
                                <td><div id="diaMoney"></div></td>
                                <td><label id="diaQuotaTd">额度可用：</label></td>
                                <td><div id="diaQuota"></div></td>
                            </tr>
                            <tr>
                                <td><label>订单类型：</label></td>
                                <td>
                                    <input type="text" id="diaOrderType"  name="diaOrderType" kind="dic" src="DDLX" datasource="ORDER_TYPE" datatype="0,is_null,30" value=""/>
                                </td>
                                <td><label>供货配件库：</label></td>
                                <td>
                                    <input type="text" id="diaWarehouseCode"  name="diaWarehouseCode" kind="dic" src="" dicwidth="260" datasource="WAREHOUSE_CODE" datatype="0,is_null,100" value=""/>
                                    <input type="hidden" id="diaWarehouseId" name="diaWarehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="hidden" id="diaWarehouseName" name="diaWarehouseName" datasource="WAREHOUSE_NAME"/>
                                </td>
                                <td><label>期望交货日期：</label></td>
                                <td>
                                    <input type="text" id="diaExpectDate" name="diaExpectDate" datasource="EXPECT_DATE" datatype="1,is_date,30" onclick="WdatePicker({minDate:'%y-%M-%d'})" />
                                </td>
                             </tr>
                             <tr>
                                 <td><label>运输方式：</label></td>
                                <td>
                                    <input type="text" id="diaTransType"  name="diaTransType"  kind="dic" src="FYFS" fitercode="" datasource="TRANS_TYPE" datatype="0,is_null,30" value=""/>
                                </td>
                                <td id="td-addrType1"><label>交货地点：</label></td>
                                <td colspan="3" id="td-addrType2">
                                    <input type="text" style="width:88%;" id="diaAddrType"  name="diaAddrType" datasource="ADDR_TYPE" dicWidth="500" kind="dic" src=""  datatype="0,is_null,300" value="" />
                                </td>    
                            </tr>
                            <tr id="tr-addr1" style="display:none;">
<!--                                 <td><label>省：</label></td> -->
<!--                                 <td> -->
<!--                                     <input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" dicwidth="320" datasource="PROVINCE_CODE" datatype="0,is_null,300" value=""/> -->
<!--                                 </td> -->
<!--                                 <td><label>市：</label></td> -->
<!--                                 <td> -->
<!--                                     <input type="text" id="diaCityCode"  name="diaCityCode" kind="dic" dicwidth="320" datasource="CITY_CODE" datatype="0,is_null,300" value=""/> -->
<!--                                 </td> -->
<!--                                 <td><label>区县：</label></td> -->
<!--                                 <td> -->
<!--                                     <input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic" dicwidth="320" datasource="COUNTRY_CODE" datatype="0,is_null,300" value=""/> -->
<!--                                 </td> -->
                                    <td><label>省：</label></td>
									<td>
									    <input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src=""   datasource="PROVINCE_CODE" value="" datatype="0,is_null,100" />
									</td>
									<td><label>市：</label></td>
									<td>
									    <input type="text" id="diaCityCode" name="diaCityCode" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,100" />
									</td>
									<td><label>区县：</label></td>
									<td>
									    <input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic"  datasource="COUNTY_CODE" value="" datatype="0,is_null,300" />
									</td>
                            </tr>
                            <tr id="tr-addr2" style="display:none;">
                                <td><label>详细地址：</label></td>
                                <td colspan="4"><input type="text" style="width:88%;" id="diaDeliveryAddr"  name="diaDeliveryAddr" datasource="DELIVERY_ADDR" datatype="0,is_null,100" value=""/></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><label>联系人：</label></td>
                                <td><input type="text" id="diaLinkMan"  name="diaLinkMan" datasource="LINK_MAN" datatype="0,is_null,30" value=""/></td>
                                <td><label>联系方式：</label></td>
                                <td><input type="text" id="diaPhone"  name="diaPhone" datasource="PHONE" datatype="0,is_null,30" value=""/></td>
                                <td><label>邮政编码：</label></td>
                                <td><input type="text" id="diaZipCode"  name="diaZipCode" datasource="ZIP_CODE" datatype="1,is_digit,6" value=""/></td>
                            </tr>
                            <tr style="display:none" id="tr-progrom">
                                <td><label>促销活动：</label></td>
                                <td colspan="3">
                                    <input type="text" style="width:88%;"  id="diaPromId"  name="diaPromId" kind="dic" src="" datasource="PROM_ID" dicWidth="300" datatype="0,is_null,200" value="" /><input type="hidden" id="diaIfFree" name="diaIfFree" datasource="IF_TRANS"/>
                                </td>
                                <td></td>
                            </tr>
                            <tr style="display:none" id="tr-supplier">
                                <td><label>直发类型：</label></td>
                                <td>
                                    <input type="text" id="diaDirectTypeCode"  name="diaDirectTypeCode" kind="dic" src="T#PT_BA_DIRECT_TYPE:TYPE_CODE:TYPE_NAME{TYPE_ID}:1=1 AND STATUS=<%=DicConstant.YXBS_01%>" datasource="DIRECT_TYPE_CODE" dicWidth="300" datatype="0,is_null,100" value=""/>
                                    <input type="hidden" id="diaDirectTypeId" name="diaDirectTypeId" datasource="DIRECT_TYPE_ID"/>
                                    <input type="hidden" id="diaDirectTypeName" name="diaDirectTypeName" datasource="DIRECT_TYPE_NAME"/>
                                </td>
                                <!-- <td><label>供应商：</label></td>
                                <td colspan="3">
                                    <input type="text" style="width:88%;" id="diaSupplierCode"  name="diaSupplierCode" kind="dic" src="T#PT_BA_SUPPLIER:SUPPLIER_CODE:SUPPLIER_NAME{SUPPLIER_ID}:1=1 AND PART_IDENTIFY=<%=DicConstant.YXBS_01%>" datasource="SUPPLIER_CODE" dicWidth="300" datatype="0,is_null,100" value=""/>
                                    <input type="hidden" id="diaSupplierId" name="diaSupplierId" datasource="SUPPLIER_ID"/>
                                    <input type="hidden" id="diaSupplierName" name="diaSupplierName" datasource="SUPPLIER_NAME"/>
                                </td> -->
                            </tr>
                            <tr>
                                <td><label>订单总金额：</label></td>
                                <td><div id="divOrderAmount"></div></td>
                                <td id="ifType1"><label>是否免运费：</label></td>
                                <td id="ifType2">
                                    <input type="text" id="diaIfTrans" style="width:80px;" name="diaIfTrans" kind="dic" src="SF" datasource="IF_TRANS"  datatype="0,is_null,30"/>
                                </td>
                                <td id="ifCredit1"><label>是否用额度：</label></td>
                                <td id="ifCredit2">
                                    <input type="text" id="diaIfCredit" style="width:80px;" name="diaIfCredit" kind="dic" src="SF" datasource="IF_CREDIT"  datatype="0,is_null,30" isreload="true"/>
                                    <input type="hidden" id="diaIfCredit1" name="diaIfCredit1"/>
                                </td>
                            </tr>
                            <tr style="display:none" id="Tr_diaChassisNo">
                                <td><label>底盘号：</label></td>
                                <td><input type="text" id="diaChassisNo"  name="diaChassisNo" datasource="VIN" datatype="0,is_vin,17"/></td>
                                <td><label>所属总成：</label></td>
                                <td>
                                    <select type="text" id="dia-belong_assembly"  name="dia-belong_assembly" kind="dic" src="PJZCLB" datasource="BELONG_ASSEMBLY"  datatype="1,is_null,30" filtercode="200701|200702|200703|200704|200705|200706|200707" operation="=" >
                                        <option value="-1" selected>--</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="5"><textarea id="diaRemarks" style="width:94%;height:40px;" name="diaRemarks" datasource="REMARKS"  datatype="1,is_null,100"></textarea></td>
                            </tr>
                        </table>
                    </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btnDiaSave">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button id="serverId" class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <div>
                <form method="post" id="diaOrderPartFm" class="editForm">
                    <table class="editTable" id="tabOrderPartSearch" >
                    </table>
                    <div style="margin-left:65px;margin-bottom:7px;font-size:14px;">订单总金额：
                        <span id="divOrderAmount1" style="font-size:14px;color:red;"></span>
                    </div>
                </form>
                <form id="exportFm" method="post" style="display:none">
	                <input type="hidden" id="data" name="data"></input>
                </form>
                <div id="div-orderPartList" style="overflow:hidden;">
                    <table style="display:none;width:100%;" id="tab-orderPartList" name="tablist" edit="true" ref="div-orderPartList" refQuery="tabOrderPartSearch">
                        <thead>
                        <tr>
                            <th type="single" name="XH" style="display:" append="plus|addOrderPart" ></th>
                            <th fieldname="PART_CODE" freadonly="true">配件代码</th>
                            <th fieldname="PART_NAME" freadonly="true">配件名称</th>
                            <th fieldname="UNIT" colwidth="50" freadonly="true">计量单位</th>
                            <th fieldname="MIN_PACK" colwidth="50" refer="toAppendStr" freadonly="true">最小包装</th>
                            <th fieldname="UNIT_PRICE" colwidth="60" refer="amountFormatStr" align="right" freadonly="true">经销商价</th>
                            <th fieldname="IF_SUPPLIER" colwidth="90" style="display:none" freadonly="true">是否指定供应商</th>
                            <th fieldname="SUPPLIER_NAME" refer="supplierShow" freadonly="true">供应商名称</th>
                            <th fieldname="ORDER_COUNT" colwidth="50" fdatatype="1,is_digit,30">订购数量</th>
                            <th fieldname="AMOUNT" refer="amountFormatStr" align="right" freadonly="true">小计</th>
                            <th fieldname="REMARKS" colwidth="90">备注</th>
                            <th fieldname="AVAILABLE_AMOUNT" colwidth="70" fdatatype="1,is_digit,30" freadonly="true">可用库存数量</th>
                            <th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaPartSave|doDiaPartDelete">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <form method="post" id="diaSaveFm">
                    <input type="hidden" id="saveOrderId" name="saveOrderId" datasource="ORDER_ID"/>
                    <input type="hidden" id="saveDtlId" name="saveDtlId" datasource="DTL_ID"/>
                    <input type="hidden" id="saveOrderCount" name="saveOrderCount" datasource="ORDER_COUNT"/>
                    <input type="hidden" id="saveRemarks" name="saveRemarks" datasource="REMARKS"/>
                    <input type="hidden" id="saveUnitPrice" name="saveUnitPrice" datasource="UNIT_PRICE"/>
                </form>
                <div class="formBar">
                    <ul>
                        <li id="button3"><div class="button"><div class="buttonContent"><button type="button"  id="diaTotail" name="diaTotail">汇总渠道延期订单</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button"  id="diaDelete" name="diaDelete">全部清除</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button"  id="btnExp" name="btnExp">配件模版导出</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button"  id="btnImpOrderPart" name="btnImpOrderPart">配件明细导入</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                        <li id="button1"><div class="button"><div class="buttonContent"><button type="button" id="dia-doReport">提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
                        <li id="button2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button id="dcId" class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <div>
                <form method="post" id="diaFundsFm" class="editForm" style="width:99%;">
                    <div align="left">
                        <table class="editTable" id="diaOrderFundsInfo">
                        </table>
                        <div style="margin-left:65px;margin-bottom:7px;font-size:14px;">
                            <table style="width:90%;">
                                <tr>
                                    <td style="width:30%;">订单总金额：<span id="divOrderAmount2" style="font-size:14px;color:red;"></span></td>
                                    <td style="width:30%;">已付总金额：<span id="divOrderAmount3" style="font-size:14px;color:red;"></span></td>
                                    <td style="width:30%;">应付总金额：<span id="divOrderAmount4" style="font-size:14px;color:red;"></span></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </form>
                <form method="post" id="diaOrderFundsFm" class="editForm">
                    <table class="searchContent" id="tabOrderFundsSearch"></table>
                </form>
                <form method="post" id="fm-orderFunds" style="display:">
                    <input type="hidden" id="accountId" datasource="ACCOUNT_ID"/>
                    <input type="hidden" id="accountType" datasource="ACCOUNT_TYPE"/>
                    <input type="hidden" id="payAmount" datasource="PAY_AMOUNT"/>
                    <input type="hidden" id="payOrderId" datasource="ORDER_ID"/>
                    <input type="hidden" id="payId" datasource="PAY_ID"/>
                </form>
                <div id="div-orderFundsList" style="overflow:hidden;text-align:center;">
                    <table style="display:none;width:80%;" id="tab-orderFundsList" name="tablist" ref="div-orderFundsList" refQuery="tabOrderFundsSearch" edit="true">
                            <thead>
                                <tr>
                                    <th type="single" name="XH" style="display:" append="plus|addFunds"></th>
                                    <th fieldname="ACCOUNT_TYPE" ftype="input" fkind="dic" fisreload="true" fsrc="" fdatatype="0,is_null,30">账户类型</th>
                                    <th fieldname="AVAILABLE_AMOUNT" freadonly="true" align="right">可用余额(元)</th>
                                    <th fieldname="PAY_AMOUNT" ftype="input" fonblur="checkAmount(this)" fdatatype="0,is_money,30" align="right">本次使用金额(元)</th>
                                    <th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaFkSave|doDiaFkDelete">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                    </table>
                </div>
                <div class="formBar">
                    <ul>
                        <!-- <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btnFundsSave">保存付款信息</button></div></div></li> -->
                        <li id="dia-report-btn"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var diaAction='<%=action%>';
var diaUrl = "<%=contentPath%>/part/salesMng/orderMng/PartOrderReportAction";
$("#editDiv").attr("layoutH",document.documentElement.clientHeight-400);
function doNextTab($tabs) {
   $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
   (function (ci) {
       switch (parseInt(ci)) {
           case 1:
               if (!$("#diaOrderId").val()) {
                   $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                   alertMsg.warn('请先保存订单基本信息.');
               } else {
                   if ($("#tab-orderPartList").is(":hidden")) {
                       orderPartSearch();
                   }
               }
               break;
           case 2:
               if (!$('#diaOrderId').val()) {
                   $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                   alertMsg.warn('请先保存订单基本信息.');
               } else {
                   orderFundsSearch();
               }
               break;
           default:
               break;
       }
   })(parseInt($tabs.attr("currentIndex")));
}
function doPreTab($tabs) 
{
     $tabs.switchTab($tabs.attr("currentIndex") - 1);
}
(function ($) {
    $("button[name='btn-next']").bind("click", function () {
        var $tabs = $("#dia-tabs");
        doNextTab($tabs);
    });
    $("button[name='btn-pre']").bind("click", function () {
        var $tabs = $("#dia-tabs");
        doPreTab($tabs);
    });
    $.partOrderMng = {
        init: function () {
            var iH = document.documentElement.clientHeight;
            //$(".tabsContent").height(iH - 70);
            $("#tab-orderPartList").attr("layoutH",iH-220);
        }
    };
})(jQuery);
//页面初始化方法
$(function () {
    $.partOrderMng.init();
    //订单基本信息保存
    $("#btnDiaSave").bind("click", function () {
        var $f = $("#diaOrderInfoFm");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        if (diaAction == 1)
        {
            var addUrl = diaUrl + "/orderInfoInsert.ajax";
            doNormalSubmit($f, addUrl, "btnDiaSave", sCondition, diaInsertCallBack);
        } else {
    		var updateUrl = diaUrl + "/orderInfoUpdate.ajax";
    	    doNormalSubmit($f, updateUrl, "btnDiaSave", sCondition, diaUpdateCallBack);
        	alertMsg.confirm("是否确认保存？",{okCall:doApproveOk1});
        }
    });
    //订单付款信息保存
    $("#btnFundsSave").bind("click", function () {
        var $f = $("#diaFundsFm");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var updateUrl = diaUrl + "/orderFundsInfoUpdate.ajax?&orderId="+$("#diaOrderId").val();
        doNormalSubmit($f, updateUrl, "btnFundsSave", sCondition, diaFundsUpdateCallBack);
    });
    //配送中心订单提报
    $("#dia-report").bind("click",function(){
        if($("#tab-orderPartList_content").find("tr").length==0){
            alertMsg.warn("请添加订单所需订购的配件信息.");
            return false;
        }
        if(!reportCheckAmount()){
            alertMsg.warn("使用金额与订单总金额不相等,请调整后重新提报.");
            return false;
        }
        var reportUrl = diaUrl + "/partOrderReport.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+$("#dia-orderType").val()+"&orderAmount="+$("#diaOrderAmount").val();
        sendPost(reportUrl, "dia-report", "", diaReportCallBack, "true");
    });
    $("#diaDelete").bind("click",function(){
        var reportUrl = diaUrl + "/partOrderDtlDelete.ajax?&orderId="+$("#diaOrderId").val();
        sendPost(reportUrl, "", "", diaOrderPartDeleteCallBack, "true");
    });
    //渠道订单提报
    $("#dia-doReport").bind("click",function(){
        if ($("#diaOrderAmount").val() <= 0 || !$("#diaOrderAmount").val()) {
            alertMsg.warn("请添加订单所需订购的配件信息.");
            return false;
        }
        var reportUrl = diaUrl + "/partOrderReport.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+$("#dia-orderType").val()+"&orderAmount="+$("#diaOrderAmount").val();
        sendPost(reportUrl, "", "", diaReportCallBack, "true");
    });
    //配件模版导出按钮响应
    $("#btnExp").bind("click", function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=orderPartImp.xls");
        window.location.href = url;
    });
    //配件明细导入按钮响应
    $("#btnImpOrderPart").bind("click", function () {
        if(!$("#diaOrderId").val()){
            alertMsg.warn('请先保存订单基本信息.');
            return;
        }
        importXls("PT_BU_SALE_ORDER_DTL_TMP",$("#diaOrderId").val(),5,3,"/jsp/dms/dealer/part/sales/orderMng/importSuccess.jsp");
    });
    // 导出按钮绑定
    $("#btn-export-index").click(function(){
        var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/download.do?ORDER_ID="+$("#diaOrderId").val());
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
    //渠道订单汇总
    $("#diaTotail").bind("click",function(){
        if(!$("#diaOrderId").val()){
            alertMsg.warn('请先保存订单基本信息.');
            return;
        }
        var totailDelayOrderUrl = diaUrl + "/totailDelayOrder.ajax?&orderType="+$("#dia-orderType").val()+"&orderId="+$("#diaOrderId").val()+"&promId="+$("#diaPromId").attr("code")+"&directTypeId="+$("#diaDirectTypeId").val();
        sendPost(totailDelayOrderUrl, "", "", totailDelayOrderCallback, "true");
    });
    if(orgType=="<%=DicConstant.ZZLB_09%>"){
    	// 配送中心
        $("#diaOrderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>");
        $("#diaWarehouseCode").attr("src","T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID}:1=1 AND WAREHOUSE_TYPE=100101 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01%>");
        //账户可用余额查询
        var accountSearchUrl = diaUrl + "/accountSearch.ajax";
        sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
        $("#button1").hide();
        $("#button3").show();
        $("#button2").show();
    }
    if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
    	// 服务站,经销商
        $("#diaOrderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_07%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>");
        $("#diaMoneyTd").hide();
        $("#diaQuotaTd").hide();
        $("#ifCredit1").hide();
        $("#ifCredit2").hide();
        $("#orderPay").hide();
        $("#button3").hide();
        $("#button1").show();
        $("#button2").hide();
    }

    //修改
    if (diaAction != "1") {
        var selectedRows = $("#tab-searchList").getSelectedRows();
        setEditValue("diaOrderInfoFm", selectedRows[0].attr("rowdata"));
        var orderType=selectedRows[0].attr("ORDER_TYPE");
        var addrType=selectedRows[0].attr("ADDR_TYPE");
        // 是否用额度
        $("#diaIfCredit1").val(selectedRows[0].attr("IF_CREDIT"));
        $("#diaOrderType").attr("disabled",true);
        $("#dia-orderType").val(orderType);
        $("#diaOrderNo").html(selectedRows[0].attr("ORDER_NO"));
        if(orderType==<%=DicConstant.DDLX_06%>){
            $("#tr-progrom").show();
            var dicSql="T#PT_BU_PROMOTION A,PT_BU_PROMOTION_AREA B:A.PROM_ID:A.PROM_NAME{A.IF_TRANS_FREE}:1=1";
            dicSql+=" AND A.PROM_ID = B.PROM_ID AND A.START_DATE <= SYSDATE AND A.END_DATE >= SYSDATE ";
            dicSql+=" AND A.PROM_STATUS =<%=DicConstant.CXHDZT_02%> AND B.AREA_ID = (SELECT PID FROM TM_ORG WHERE ORG_ID = <%=orgId%>)";
            $("#diaPromId").attr("src",dicSql);
            $("#diaPromId").attr("code",selectedRows[0].attr("PROM_ID"));
            $("#diaPromId").val(selectedRows[0].attr("PROM_NAME"));
            if($("#diaOrderAmount").val()>0){
                $("#diaPromId").attr("disabled",true);
            }
            $("#diaIfFree").val(selectedRows[0].attr("IF_TRANS"));
            $("#diaIfTrans").attr("filtercode",selectedRows[0].attr("IF_TRANS"));
            $("#diaIfTrans").attr("code",selectedRows[0].attr("IF_TRANS"));
            $("#diaIfTrans").val("code",selectedRows[0].attr("IF_TRANS_sv"));
        }
        if(orderType==<%=DicConstant.DDLX_05%>){
            $("#tr-supplier").show();
            if (selectedRows[0].attr("ORDER_AMOUNT")!=0) {
	            $("#diaDirectTypeCode").attr("disabled",true);
            }
            $("#diaDirectTypeCode").attr("code",selectedRows[0].attr("DIRECT_TYPE_CODE"));
            $("#diaDirectTypeCode").val(selectedRows[0].attr("DIRECT_TYPE_NAME"));
            $("#diaDirectTypeId").val(selectedRows[0].attr("DIRECT_TYPE_ID"));
            $("#diaDirectTypeName").val(selectedRows[0].attr("DIRECT_TYPE_NAME"));
            $("#ifType1").hide();
            $("#ifType2").hide();
//             $("#button3").hide();
        }
        if(orderType==<%=DicConstant.DDLX_04%>){
            $("#Tr_diaChassisNo").show();
            $("#diaChassisNo").attr("datatype","0,is_null,17");
            $("#dia-belong_assembly").attr("datatype","0,is_null,30");
        }
        $("#divOrderAmount").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
        $("#divOrderAmount1").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
        $("#divOrderAmount2").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
        $("#diaWarehouseId").val(selectedRows[0].attr("WAREHOUSE_ID"));
        $("#diaWarehouseCode").attr("code",selectedRows[0].attr("WAREHOUSE_CODE"));
        $("#diaWarehouseCode").val(selectedRows[0].attr("WAREHOUSE_NAME"));
        $("#diaWarehouseCode").attr("disabled",true);
          //订单规则查询：是否免运费、地址是否可选、是否允许自提、是否校验资金
        var typeSearchUrl = diaUrl + "/orderTypeRuleSearch.ajax?&orderType="+orderType;
        sendPost(typeSearchUrl, "", "", typeSearchCallback, "false");
        if(addrType==<%=DicConstant.JHDDLX_01%>){
            $("#diaAddrType").attr("code",selectedRows[0].attr("ADDR_TYPE"));
            $("#diaAddrType").val(selectedRows[0].attr("DELIVERY_ADDR"));
            $("#diaProvinceCode").attr("readonly",true);
            $("#diaCityCode").attr("readonly",true);
            $("#diaCountryCode").attr("readonly",true);
            $("#diaLinkMan").attr("readonly",true);
            $("#diaPhone").attr("readonly",true);
        }else{
        	if (""!=selectedRows[0].attr("ADDR_TYPE")) {
	            $("#diaAddrType").attr("code",selectedRows[0].attr("ADDR_TYPE"));
	            $("#diaAddrType").val("其他");
        	}
            $("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
            $("#tr-addr1").show();
            $("#tr-addr2").show();
        }
        if (selectedRows[0].attr("TRANS_TYPE")==<%=DicConstant.FYFS_02%>) {
        	$("#diaTransType").attr("code","<%=DicConstant.FYFS_02%>");
        	$("#diaTransType").val("自提");
        	$("#td-addrType1").hide();
            $("#td-addrType2").hide();
            $("#tr-addr1").hide();
            $("#tr-addr2").hide();
            $("#ifType1").hide();
            $("#ifType2").hide();
        } else {
        	$("#diaTransType").attr("code","<%=DicConstant.FYFS_01%>");
        	$("#diaTransType").val("发运");
        	$("#td-addrType1").show();
            $("#td-addrType2").show();
        }
        $("#diaProvinceCode").attr("code",selectedRows[0].attr("PROVINCE_CODE"));
        $("#diaProvinceCode").val(selectedRows[0].attr("PROVINCE_NAME"));
        $("#diaCityCode").attr("code",selectedRows[0].attr("CITY_CODE"));
        $("#diaCityCode").val(selectedRows[0].attr("CITY_NAME"));
        $("#diaCountryCode").attr("code",selectedRows[0].attr("COUNTRY_CODE"));
        $("#diaCountryCode").val(selectedRows[0].attr("COUNTRY_NAME"));
        $("#diaIfCredit").attr("code",selectedRows[0].attr("IF_CREDIT"));
        $("#diaIfCredit").val(selectedRows[0].attr("IF_CREDIT_sv"));
        $("#diaIfTrans").attr("code",selectedRows[0].attr("IF_TRANS"));
        $("#diaIfTrans").val(selectedRows[0].attr("IF_TRANS_sv"));
        $("#diaRemarks").val(selectedRows[0].attr("REMARKS"));
        orderFundsSearch();
    }
});
//汇总渠道延期订单回调
function totailDelayOrderCallback(res){
    try {
        orderPartSearch();
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length>0){
            var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
            $("#diaOrderAmount").val(amount);
            $("#divOrderAmount").html(amountFormatNew(amount)+"元");
            $("#divOrderAmount1").html(amountFormatNew(amount)+"元");
            $("#divOrderAmount2").html(amountFormatNew(amount)+"元");
            var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/partOrderSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
        }
        if($("#dia-orderType").val()==<%=DicConstant.DDLX_06%>){
            $("#diaPromId").attr("disabled",true);
        }
        if($("#dia-orderType").val()==<%=DicConstant.DDLX_05%>){
            $("#diaDirectTypeCode").attr("disabled",true);
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//新增回调函数
function diaInsertCallBack(res) {
    try {
        var rows = res.getElementsByTagName("ROW");
        if (rows && rows.length > 0) {
            var orderId = getNodeText(rows[0].getElementsByTagName("ORDER_ID").item(0));
            var orderNo = getNodeText(rows[0].getElementsByTagName("ORDER_NO").item(0));
            $('#diaOrderId').val(orderId);
            $("#diaOrderNo").html(orderNo);
            $("#diaOrderType").attr("disabled",true);
            $("#diaWarehouseCode").attr("disabled",true);
            diaAction = "2";
        }
        var $f = $("#searchForm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/partOrderSearch.ajax";
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
        if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
               $("#tab-orderFundsList").find("th").eq(1).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%>");
           }else{
               $("#tab-orderFundsList").find("th").eq(1).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>)");
           }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//修改回调函数
function diaUpdateCallBack(res) {
    try {
         var $f = $("#searchForm");
         var sCondition = {};
         sCondition = $f.combined() || {};
         var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/partOrderSearch.ajax";
         doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
         if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
                $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%>");
            }else{
                   $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>)");
            }
         if ($("#diaIfCredit1").val()!=$("#diaIfCredit").attr("code")) {
     		var url = diaUrl + "/orderFundsDeleteOrderId.ajax?orderId="+$("#diaOrderId").val();
             sendPost(url,"delete","",diaDeleteCallBack,"false");
     	}
         $("#diaIfCredit1").val($("#diaIfCredit").attr("code"));
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//修改付款主信息回调
function diaFundsUpdateCallBack(res){
    try {
        var $f = $("#searchForm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/partOrderSearch.ajax";
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
           if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
               $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%>");
           }else{
               $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>)");
           }
           orderFundsSearch();
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//提报回调
function diaReportCallBack(res){
    try {
    	$("#dia-report-btn").attr("style","display:none");
    	$("#button1").attr("style","display:none");
        var $f = $("#searchForm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/partOrderSearch.ajax";
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
        $("#serverId").click();
        $("#dcId").click();
//         $.pdialog.closeCurrent();
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
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
//字典回调
function afterDicItemClick(id,$row,selIndex){
    var ret = true;
    //资金账户表选回调：判断id中是否包含ACCOUNT_TYPE的值
    if(id.indexOf("ACCOUNT_TYPE") == 0)
    {
        var curRow = $("#"+id);
        while(curRow.isTag("tr") == false)
        {
            curRow = curRow.parent();
        }
        var $tab = $("#tab-orderFundsList_content");
        while($tab.get(0).tagName != "TABLE")
            $tab = $tab.parent();
        var flag = false;
        var arr = new Array();
        curRow.attr("ACCOUNT_ID",$row.attr("a.account_id"));
        curRow.find("td").eq(3).text($row.attr("A.AVAILABLE_AMOUNT"));
        $("tr",$tab).each(function(){
            var $tr = $(this);
            var accountIdCheck = $tr.attr("ACCOUNT_ID");
            if (accountIdCheck != "undefined" && accountIdCheck != "") {
                arr.push($tr.attr("ACCOUNT_ID"));
            }
        });
         if( arr.length==$.unique( arr ).length ){
             flag=false;
         }else{
             flag=true;
         }
        if(flag){
            curRow.find("td").eq(2).find("input").attr("code","");
            curRow.find("td").eq(2).find("input").val("");
            curRow.attr("ACCOUNT_ID","");
            curRow.find("td").eq(3).text("");
            alertMsg.warn("账户类型不能重复选择.");
            return false;
        }
    }
    //订单类型
    if(id == "diaOrderType")
    {
        var orderType = $("#diaOrderType").attr("code");
        $("#dia-orderType").val(orderType);
        //订单类型与供货库联动
        if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
            if(orderType!="<%=DicConstant.DDLX_07%>"){
                $("#diaWarehouseCode").attr("code","");
                $("#diaWarehouseCode").val("");
                $("#diaWarehouseCode").attr("src","T#TM_ORG A,PT_BA_SERVICE_DC B:A.CODE:A.ONAME{A.ORG_ID}:1=1 AND A.ORG_ID = B.DC_ID AND B.STATUS =<%=DicConstant.YXBS_01%> AND B.ORG_ID =<%=orgId%>");
            }else{
                $("#diaWarehouseId").val("<%=paraValue1%>");
                $("#diaWarehouseCode").attr("code","<%=paraValue2%>");
                $("#diaWarehouseCode").val("<%=paraValue3%>");
                $("#diaWarehouseName").val("<%=paraValue3%>");
                $("#diaWarehouseCode").attr("src","T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND IS_DS=<%=DicConstant.SF_01%>");
            }
        }
        //促销订单显示促销活动
        if(orderType==<%=DicConstant.DDLX_06%>){
            $("#tr-progrom").show();
            var dicSql="T#PT_BU_PROMOTION A,PT_BU_PROMOTION_AREA B:A.PROM_ID:A.PROM_NAME{A.IF_TRANS_FREE}:1=1";
            dicSql+=" AND A.PROM_ID = B.PROM_ID AND A.START_DATE <= SYSDATE AND A.END_DATE >= SYSDATE ";
            dicSql+=" AND A.PROM_STATUS =<%=DicConstant.CXHDZT_02%> AND B.AREA_ID = (SELECT PID FROM TM_ORG WHERE ORG_ID = <%=orgId%>)";
            $("#diaPromId").attr("src",dicSql);
        }else{
            $("#tr-progrom").hide();
            $("#diaPromId").attr("code","");
            $("#diaPromId").val("");
        }
        if(orderType==<%=DicConstant.DDLX_05%>){
            $("#tr-supplier").show();
            $("#button3").hide();
        }else{
            $("#tr-supplier").hide();
            $("#diaDirectCode").attr("code","");
            $("#diaDirectCode").val("");
            $("#diaDirectId").val("");
            $("#diaDirectName").val("");
        }
        if(orderType==<%=DicConstant.DDLX_04%>){
            $("#Tr_diaChassisNo").show();
            $("#diaChassisNo").attr("datatype","0,is_null,17");
            $("#dia-belong_assembly").attr("datatype","0,is_null,30");
        } else {
            $("#Tr_diaChassisNo").hide();
            $("#diaChassisNo").attr("datatype","1,is_null,17");
            $("#dia-belong_assembly").attr("datatype","1,is_null,30");
        }
        //订单规则查询：是否免运费、地址是否可选、是否允许自提、是否校验资金
        var typeSearchUrl = diaUrl + "/orderTypeRuleSearch.ajax?&orderType="+orderType;
        sendPost(typeSearchUrl, "", "", typeSearchCallback, "false");
    }
    //发运方式：地址隐藏与显示
    if(id=="diaTransType"){
        if($("#diaTransType").attr("code")!="<%=DicConstant.FYFS_01%>"){
            $("#td-addrType1").hide();
            $("#td-addrType2").hide();
            $("#tr-addr1").hide();
            $("#tr-addr2").hide();
            $("#diaAddrType").attr("code","");
            $("#diaAddrType").val("");
            $("#diaProvinceCode").attr("code","");
            $("#diaProvinceCode").val("");
            $("#diaProvinceName").val("");
            $("#diaCityCode").attr("code","");
            $("#diaCityCode").val("");
            $("#diaCityName").val("");
            $("#diaCountryCode").attr("code","");
            $("#diaCountryCode").val("");
            $("#diaCountryName").val("");
            $("#diaDeliveryAddr").val("");
            $("#ifType1").hide();
            $("#ifType2").hide();
            $("#diaIfTrans").attr("code","");
            $("#diaIfTrans").val("");
        }else{
            $("#td-addrType1").show();
            $("#td-addrType2").show();
            $("#ifType1").show();
            $("#ifType2").show();
        }
    }
    //地址类型：小库不可自定义地址、大库自定义地址
    if(id=="diaAddrType"){
        if($("#diaAddrType").attr("code")!="<%=DicConstant.JHDDLX_01%>"){
        	$("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
            $("#tr-addr1").show();
            $("#tr-addr2").show();
            $("#diaProvinceCode").attr("readonly",false);
            $("#diaCityCode").attr("readonly",false);
            $("#diaCountryCode").attr("readonly",false);
            $("#diaLinkMan").attr("readonly",false);
            $("#diaPhone").attr("readonly",false);
            
        }else{
            $("#tr-addr1").hide();
            $("#tr-addr2").hide();
            $("#diaDeliveryAddr").val($("#diaAddrType").val());
            if($row.attr("PROVINCE_CODE")&&$row.attr("PROVINCE_NAME")){
                $("#diaProvinceCode").attr("code",$row.attr("PROVINCE_CODE"));
                $("#diaProvinceCode").val($row.attr("PROVINCE_NAME"));
                $("#diaProvinceCode").attr("readonly",true);
                $("#diaProvinceName").val($row.attr("PROVINCE_NAME"));
            }
            if($row.attr("CITY_CODE")&&$row.attr("CITY_NAME")){
                $("#diaCityCode").attr("code",$row.attr("CITY_CODE"));
                $("#diaCityCode").val($row.attr("CITY_NAME"));
                $("#diaCityCode").attr("readonly",true);
                $("#diaCityName").val($row.attr("CITY_NAME"));
            }
            if($row.attr("COUNTRY_CODE")&&$row.attr("COUNTRY_NAME")){
                $("#diaCountryCode").attr("code",$row.attr("COUNTRY_CODE"));
                $("#diaCountryCode").val($row.attr("COUNTRY_NAME"));
                $("#diaCountryCode").attr("readonly",true);
                $("#diaCountryName").val($row.attr("COUNTRY_NAME"));
            }
            if($row.attr("LINK_MAN")){
                $("#diaLinkMan").val($row.attr("LINK_MAN"));
                $("#diaLinkMan").attr("readonly",true);
            }
            if($row.attr("PHONE")){
                $("#diaPhone").val($row.attr("PHONE"));
                $("#diaPhone").attr("readonly",true);
            }
            if($row.attr("ZIP_CODE")){
                $("#diaZipCode").val($row.attr("ZIP_CODE"));
            }
        }
    }
    //仓库选择后隐藏域赋值
    if(id=="diaWarehouseCode"){
        var orderType = $("#diaOrderType").attr("code");
        if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
            if(orderType=="<%=DicConstant.DDLX_07%>"){
                $("#diaWarehouseId").val($row.attr("ORG_ID"));
                $("#diaWarehouseName").val($("#diaWarehouseCode").val());
            }else{
                $("#diaWarehouseId").val($row.attr("A.ORG_ID"));
                $("#diaWarehouseName").val($("#diaWarehouseCode").val());
            }
        }else{
            $("#diaWarehouseId").val($row.attr("WAREHOUSE_ID"));
            $("#diaWarehouseName").val($("#diaWarehouseCode").val());
        }
    }
    if(id == "diaProvinceCode")
	{
        $("#diaProvinceName").val($("#diaProvinceCode").val());
		$("#diaCityCode").val("");
		$("#diaCityCode").attr("code","");
		$("#diaCountryCode").val("");
		$("#diaCountryCode").attr("code","");
		var privinceCode = $("#"+id).attr("code").substr(0,2);
		$("#diaCityCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+privinceCode+"%' AND LX='30' ");
		$("#diaCityCode").attr("isreload","true");
		//$("#dia-city_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
	}
	if(id == "diaCityCode")
	{
		$("#diaCityName").val($("#diaCityCode").val());
		$("#diaCountryCode").val("");
		$("#diaCountryCode").attr("code","");
		var cityCode = $("#"+id).attr("code").substr(0,4);
		$("#diaCountryCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+cityCode+"%' AND LX='40' ");
		$("#diaCountryCode").attr("isreload","true");
		$("#diaCountryCode").attr("dicwidth","300");
		//$("#dia-county_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
	}
//     if(id=="diaProvinceCode"){
//         $("#diaProvinceName").val($("#diaProvinceCode").val());
//         $("#diaCityCode").attr("src","XZQH");
//         $("#diaCityCode").attr("isreload","true");
//         $("#diaCityCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
//     }
//     if(id=="diaCityCode"){
//         $("#diaCityName").val($("#diaCityCode").val());
//         $("#diaCountryCode").attr("src","XZQH");
//         $("#diaCountryCode").attr("isreload","true");
//         $("#diaCountryCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
//     }
    if(id=="diaCountryCode"){
        $("#diaCountryName").val($("#diaCountryCode").val());
    }
    //if(id=="diaSupplierCode"){
        //$("#diaSupplierId").val($row.attr("SUPPLIER_ID"));
        //$("#diaSupplierName").val($("#diaSupplierCode").val());
    //}
    if(id=="diaDirectTypeCode"){
        $("#diaDirectTypeId").val($row.attr("TYPE_ID"));
        $("#diaDirectTypeName").val($("#diaDirectTypeCode").val());
    }
    //促销活动选择与是否免运费联动
    if(id=="diaPromId"){
        if($row.attr("A.IF_TRANS_FREE")&&$row.attr("A.IF_TRANS_FREE")==<%=DicConstant.SF_01%>){
            $("#diaIfTrans").attr("filtercode","<%=DicConstant.SF_01%>");
            $("#diaIfFree").val(<%=DicConstant.SF_01%>);
        }else{
            $("#diaIfTrans").attr("filtercode","<%=DicConstant.SF_02%>");
        }
    }
    if(id=="diaIfTrans"){
        $("#diaIfFree").val($("#diaIfTrans").attr("code"));
    }
//     // 是否用额度
//     if (id=="diaIfCredit") {
//     	var url = diaUrl + "/orderFundsDeleteOrderId.ajax?orderId="+$("#diaOrderId").val();
//         sendPost(url,"delete","",diaDeleteCallBack,"true");
//     }
    return ret;
}
//订单规则查询回调
function typeSearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
            var ifChooseAddr = getNodeText(rows[0].getElementsByTagName("IF_CHOOSEADDR").item(0));
            var ifFunds = getNodeText(rows[0].getElementsByTagName("IF_FUNDS").item(0));
            var ifOwnpick = getNodeText(rows[0].getElementsByTagName("IF_OWNPICK").item(0));
            var ifFree = getNodeText(rows[0].getElementsByTagName("IF_FREE").item(0));
            var firstLetter = getNodeText(rows[0].getElementsByTagName("FIRST_LETTER").item(0));
            $("#diaFirstLetter").val(firstLetter);
            if(ifOwnpick==<%=DicConstant.SF_01%>){
                $("#diaTransType").attr("filtercode","<%=DicConstant.FYFS_01%>|<%=DicConstant.FYFS_02%>");
                if(diaAction == 1){
	                $("#diaTransType").attr("code","");
	                $("#diaTransType").val("");
                }
            }else{
                $("#diaTransType").attr("filtercode","<%=DicConstant.FYFS_01%>");
                if(diaAction == 1){
	                $("#diaTransType").attr("code","");
					$("#diaTransType").val("");
                }
            }
            if(ifChooseAddr==<%=DicConstant.SF_01%>){
                $("#diaAddrType").attr("src","T#PT_BA_TRANSPORT_ADDRESS:ADDR_TYPE:PROVINCE_NAME||CITY_NAME||COUNTRY_NAME||ADDRESS{PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,LINK_MAN,PHONE,ZIP_CODE}:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND ORG_ID =<%=orgId%> OR ORG_ID=99999999 ORDER BY ADDR_TYPE ASC");
            }else{
                $("#diaAddrType").attr("src","T#PT_BA_TRANSPORT_ADDRESS:ADDR_TYPE:PROVINCE_NAME||CITY_NAME||COUNTRY_NAME||ADDRESS{PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,LINK_MAN,PHONE,ZIP_CODE}:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND ORG_ID =<%=orgId%>");
            }
            if($("#dia-orderType").val()!=<%=DicConstant.DDLX_06%>){
                if(ifFree==<%=DicConstant.SF_01%>){
                    var transFreeTimesSearchUrl = diaUrl + "/transFreeTimesSearch.ajax?&orderType="+$("#dia-orderType").val();
                    sendPost(transFreeTimesSearchUrl, "", "", transFreeTimesSearchCallback, "false");
                }else{
                    $("#diaIfTrans").attr("filtercode","<%=DicConstant.SF_02%>");
                    $("#diaIfTrans").attr("code","<%=DicConstant.SF_02%>");
                    $("#diaIfTrans").val("否");
                }
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//是否可免运费查询
function transFreeTimesSearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0){
            var ifFree = getNodeText(rows[0].getElementsByTagName("IF_FREE").item(0));
            if(ifFree==<%=DicConstant.SF_01%>){
                if($("#diaOrderType").attr("code")==<%=DicConstant.DDLX_05%>){
                    $("#diaIfTrans").attr("filtercode","<%=DicConstant.SF_01%>");
                    $("#diaIfTrans").attr("code","<%=DicConstant.SF_01%>");
                    $("#diaIfTrans").val("是");
                    $("#ifType1").hide();
                    $("#ifType2").hide();
                }else{
                    $("#diaIfTrans").attr("filtercode","");
                    $("#diaIfTrans").attr("code","<%=DicConstant.SF_01%>");
                    $("#diaIfTrans").val("是");
                }
            }else{
                $("#diaIfTrans").attr("filtercode","<%=DicConstant.SF_02%>");
                $("#diaIfTrans").attr("code","<%=DicConstant.SF_02%>");
                $("#diaIfTrans").val("否");
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//弹出配件选择页
function addOrderPart(){
    var options = {max:true,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
    $.pdialog.open(webApps + "/jsp/dms/dealer/part/sales/orderMng/orderPartSel.jsp", "addOrderPartSel", "配件信息查询", options);
}
//查询配件列表
function orderPartSearch() {
	
	// begin 2015-01-08 by fuxiao 查询方法修改：如果配件供应商code=9XXXXXX时，则配件供应商名称为''，防止行编辑时出现配件供应商信息
    // var orderPartSearchUrl = diaUrl+"/orderPartSearch.ajax?orderId="+$("#diaOrderId").val();
    var orderPartSearchUrl = diaUrl+"/dealerOrderPartSearch.ajax?orderId="+$("#diaOrderId").val();
    // end
    
    var $f = $("#diaOrderPartFm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, orderPartSearchUrl, "", 1, sCondition, "tab-orderPartList");
}
//查询资金列表
function orderFundsSearch(){
    var orderFundsSearchUrl = diaUrl+"/orderFundsSearch.ajax?orderId="+$("#diaOrderId").val();
    var $f = $("#diaOrderFundsFm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, orderFundsSearchUrl, "", 1, sCondition, "tab-orderFundsList");
}
//最小包装
function toAppendStr(obj){
    var $tr =$(obj).parent();
    return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
}
//金额格式化
function amountFormatStr(obj){
    return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}
//配件删除方法
var $rowpart;
function doDiaPartDelete(rowobj){
    $rowpart = $(rowobj);
    var deleteUrl = diaUrl + "/orderPartDelete.ajax?dtlId=" + $(rowobj).attr("DTL_ID")+"&orderId="+$("#diaOrderId").val();
    sendPost(deleteUrl, "", "", deletePartCallBack, "true");
}
//配件删除回调
function deletePartCallBack(res){
    try {
        if ($rowpart)
            $("#tab-orderPartList").removeResult($rowpart);
        if($("#dia-orderType").val()==<%=DicConstant.DDLX_06%>){
            if($("#tab-orderPartList_content").find("tr").length==0)
                $("#diaPromId").attr("disabled",false);
        }
        if($("#tab-orderPartList_content").find("tr").length==0) {
        	$("#diaDirectTypeCode").attr("disabled",false);
        }
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length>0){
            var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
            $("#diaOrderAmount").val(amount);
            $("#divOrderAmount").html(amountFormatNew(amount)+"元");
            $("#divOrderAmount1").html(amountFormatNew(amount)+"元");
            $("#divOrderAmount2").html(amountFormatNew(amount)+"元");
            var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/partOrderSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//查询回调
function callbackSearch(res,tabId,sParam)
{
    switch(tabId)
    {
        case "tab-partList":
             //setStyle('tab-partList_content');
        break;
        case "tab-orderPartList_content":
            //setStyle('tab-orderPartList_content');
        break;
        case "tab-orderFundsList":
            setStyle('tab-orderFundsList_content');
            var rows = res.getElementsByTagName("ROW");
            if(rows.length>0){
                var accountType ="";
                for(var i=0;i<rows.length;i++){
                    if(accountType==""){
                        accountType = getNodeText(rows[i].getElementsByTagName("ACCOUNT_TYPE").item(0));
                    }else{
                        accountType = accountType+","+getNodeText(rows[i].getElementsByTagName("ACCOUNT_TYPE").item(0));
                    }
                }
                if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
                       $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%> AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
                   }else{
                       $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>) AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
                   }
//                     $("#diaIfCredit").attr("disabled",true);
            }else{
                if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
                       $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%>");
                   }else{
                       $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>)");
                   }
//                     $("#diaIfCredit").attr("disabled",false);
            }
            var allfunds=0;
            $("#tab-orderFundsList_content").find("tr").each(function(){
                var funds=$(this).find("td").eq(4).text();
                allfunds = parseFloat(allfunds,10)+parseFloat(funds,10);
            });
            $("#divOrderAmount3").html(amountFormatNew(allfunds)+"元");
            var payfunds = parseFloat($("#diaOrderAmount").val(),10).toFixed(2)-parseFloat(allfunds,10).toFixed(2);
            if(payfunds==0){
                $("#divOrderAmount4").html(0.00+"元");
            }else{
                $("#divOrderAmount4").html(amountFormatNew(payfunds)+"元");
            }
            
        break;
    }
}
//添加账户
function addFunds()
{
    var $tab = $("#tab-orderFundsList");
    var i=0;
    $("#tab-orderFundsList_content").find("tr").each(function(){
        var $tr = $(this);
        var $tabs = $tr;
        while($tabs.get(0).tagName != "TABLE")
            $tabs = $tabs.parent();
        $tabs.parent().parent().attr("style","overflow:hidden;");
        var $td4 = $tr.find("td").eq(4);
        if($td4.find("input").size==1)
            i++;
        else if($td4.text().length>0)
            i++;
    });
    if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
        if(i==1){
            alertMsg.warn("额度账户已添加,不能重复添加.");
            return false;
        }
       }else{
           if(i==3){
               alertMsg.warn("现金、承兑、材料费账户已添加,不能重复添加.");
            return false;
        }
       }
    $tab.createRow();    
//     $("#diaIfCredit").attr("disabled",true);
}

//行编辑保存
function doDiaFkSave(row){
    var ret = true;
    try
    {
        if(!checkAmount($(row).find("td").eq(4).find("input:first").get(0))) return false;
        $("td input[type=radio]",$(row)).attr("checked",true);
        var $f = $("#fm-orderFunds");
        var payId=$(row).attr("PAY_ID");
        $("#payId").val(payId);
        $("#accountId").val($(row).attr("ACCOUNT_ID"));
        $("#accountType").val($(row).find("td").eq(2).find("input:first").attr("code"));
        $("#payAmount").val($(row).find("td").eq(4).find("input:first").val());
        $("#payOrderId").val($("#diaOrderId").val());
        if (submitForm($(row)) == false) 
            return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        if(payId)
        {
            var url = diaUrl + "/orderFundsUpdate.ajax";
            sendPost(url,"",sCondition,diaListSaveCallBack,"true");
        }else
        {
            var url = diaUrl + "/orderFundsInsert.ajax";
            sendPost(url,"",sCondition,diaListSaveCallBack,"false");
        }
    }catch(e){
        alertMsg.error(e);
        ret = false;
    }
    return ret;
}
//行编辑保存回调
function diaListSaveCallBack(res){
    var selectedIndex = $("#tab-orderFundsList").getSelectedIndex();
    $("#tab-orderFundsList").updateResult(res,selectedIndex);
    $("#tab-orderFundsList").clearRowEdit($("#tab-orderFundsList").getSelectedRows()[0],selectedIndex);
    var allfunds=0;
    var accountType="";
    $("#tab-orderFundsList_content").find("tr").each(function(){
        var $tr = $(this);
        var $td4 = $tr.find("td").eq(4);
        if($td4.find("input").size() > 0)
            allfunds = parseFloat(allfunds,10)+parseFloat($td4.find("input:first").val(),10);
        else
            allfunds = parseFloat(allfunds,10)+parseFloat($td4.text(),10);
        if(accountType==""){
            accountType=$(this).attr("ACCOUNT_TYPE");
        }else{
            accountType=accountType+","+$(this).attr("ACCOUNT_TYPE");
        }
    });
    if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
        if(accountType){
            $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%> AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
        }else{
            $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%>");
        }
       }else{
           if(accountType){
               $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>) AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
        }else{
            $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>)");
        }
            
       }
    $("#divOrderAmount3").html(amountFormatNew(allfunds)+"元");
    allfunds =allfunds.toFixed(2);
    var payfunds = parseFloat($("#diaOrderAmount").val(),10)-parseFloat(allfunds,10);
    if(payfunds==0){
        $("#divOrderAmount4").html(0.00+"元");
    }else{
        $("#divOrderAmount4").html(amountFormatNew(payfunds)+"元");
    }
    return true;
}
//行编辑 删除
var $rowFunds;
function doDiaFkDelete(row){
    $rowFunds =$(row);
    $("td input[type=radio]",$(row)).attr("checked",true);
    var payId = $(row).attr("PAY_ID");
    if(payId){
        var url = diaUrl + "/orderFundsDelete.ajax?payId="+payId;
        sendPost(url,"delete","",diaDeleteCallBack,"true");
    }else{
        $("#tab-orderFundsList").removeResult($rowFunds);
//            if($("#tab-orderFundsList_content").find("tr").eq(4).find("input:first").length==0)
//                $("#diaIfCredit").attr("disabled",false);
    }
}
//行编辑删除回调方法
function diaDeleteCallBack(res)
{
    try
    {    
        if($rowFunds)
        $("#tab-orderFundsList").removeResult($rowFunds);
//            if($("#tab-orderFundsList_content").find("tr").eq(4).find("input:first").length==0){
//                $("#diaIfCredit").attr("disabled",false);
//            }
           var allfunds=0;
        var accountType="";
        $("#tab-orderFundsList_content").find("tr").each(function(){
            var $tr = $(this);
            var $td4 = $tr.find("td").eq(4);
            if($td4.find("input").size() > 0)
                allfunds = parseFloat(allfunds,10)+parseFloat($td4.find("input:first").val(),10);
            else
                allfunds = parseFloat(allfunds,10)+parseFloat($td4.text(),10);
            if(accountType==""){
                accountType=$(this).attr("ACCOUNT_TYPE");
            }else{
                accountType=accountType+","+$(this).attr("ACCOUNT_TYPE");
            }
        });
        if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
            if(accountType){
                $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%> AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
            }else{
                $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE=<%=DicConstant.ZJZHLX_04%>");
            }
           }else{
               if(accountType){
                   $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>) AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
            }else{
                $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>,<%=DicConstant.ZJZHLX_03%>)");
            }
                
           }
        $("#divOrderAmount3").html(amountFormatNew(allfunds)+"元");
        var payfunds = parseFloat($("#diaOrderAmount").val())-parseFloat(allfunds);
        if(payfunds==0){
            $("#divOrderAmount4").html(0.00+"元");
        }else{
            $("#divOrderAmount4").html(amountFormatNew(payfunds)+"元");
        }
    }catch(e)
    {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//行记录上校验金额
function checkAmount(input){
    var aMountSum = 0;
    var $tr = $(input);
    while($tr.get(0).tagName != "TR")
        $tr = $tr.parent();
    $(input).val($(input).val().replace(",",""));
    var v = $(input).val();
    var vv = $tr.find("td").eq(3).text();
    //判断本次使用不能大于账户可用金额
    if(parseFloat(v,10) > parseFloat(vv,10))
    {
        alertMsg.warn("本次使用金额不能大于可用余额.");
        return false;
    }
    //判断本次使用合计金额不能大于订单总金额
    var $tab = $tr;
    while($tab.get(0).tagName != "TABLE")
        $tab = $tab.parent();
    $("tr",$tab).each(function(){
        var $tr = $(this);
        var $td4 = $tr.find("td").eq(4);
        if($td4.find("input").size() > 0)
            aMountSum = parseFloat(aMountSum,10)+parseFloat($td4.find("input:first").val(),10);
        else
            aMountSum = parseFloat(aMountSum,10)+parseFloat($td4.text(),10);
    });
    var orderAmount = $("#diaOrderAmount").val();
    aMountSum = aMountSum.toFixed(2);
    if(orderAmount)
    {
        if(parseFloat(aMountSum,10)> parseFloat(orderAmount,10))
        {
            alertMsg.warn("使用金额已大于订单总金额.");
            return false;
        }
    }
    return true;
}
//提报时金额校验
function reportCheckAmount(){
    var aMountSum = 0;
    var $tab = $("#tab-orderFundsList_content");
    while($tab.get(0).tagName != "TABLE")
        $tab = $tab.parent();
    $("tr",$tab).each(function(){
        var $tr = $(this);
        var $td4 = $tr.find("td").eq(4);
        if($td4.find("input").size() > 0)
            aMountSum = parseFloat(aMountSum,10)+parseFloat($td4.find("input:first").val(),10);
        else
            aMountSum = parseFloat(aMountSum,10)+parseFloat($td4.text(),10);
    });
    var orderAmount = $("#diaOrderAmount").val();
    if(parseFloat(aMountSum,10)!= parseFloat(orderAmount,10))
    {
        return false;
    }
    return true;
}
function supplierShow(obj){
    var $tr = $(obj).parent();
    var ifSuply= $tr.attr("IF_SUPPLIER");
    var str="";
    if(ifSuply=='<%=DicConstant.SF_01%>'){
        str= $tr.attr("SUPPLIER_NAME");
    }else{
        str = "<div></div>";
    }
    return str;
}
// function myOrderCount(obj){
//     return "<input type='text' style='width:50px;' name='myCount' value='"+$(obj).html()+"' onblur='doMyCountBlur(this)' maxlength='6'/>";
// }

// // 备注
// function myRemark(obj){
//     return "<input type='text' name='myCount' value='"+$(obj).html()+"'/>";
// }
// function doMyCountBlur(obj){
//     var $obj = $(obj);
//     if($obj.val() == "")
//         return false;
//     if($obj.val() && !isCount($obj))
//     {
//         alertMsg.warn("请正确输入订购数量！");
//         return false;
//     }
//     var $tr = $(obj).parent().parent().parent();
//     <%-- if(orgType==<%=DicConstant.ZZLB_09%>){ --%>
//         var minPack= $tr.attr("MIN_PACK");
//         if(($obj.val()%minPack)){
//             alertMsg.warn("订购数量应为最小包装倍数,请重新输入.");
//             return false;
//         }
//     }
//     var $obj0 = $tr.find("td").eq(9).find("input:first");
//     var $obj1 = $tr.find("td").eq(11).find("input:first");
//     $rowpart = $tr;
//     var orderCount = $tr.find("td").eq(9).find("input:first").val();
//     var unitPrice = $tr.find("td").eq(6).text();
//     var amount = parseInt(orderCount)*removeAmountFormat(unitPrice);
//     $tr.find("td").eq(10).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
    
//     // 订单主键
//     $("#saveOrderId").val($("#diaOrderId").val());
//     // 订单明细主键
//     $("#saveDtlId").val($tr.attr("DTL_ID"));
//     // 订单数量
//     $("#saveOrderCount").val($obj0.val());
//     // 备注
//     $("#saveRemarks").val($obj1.val());
    
//     var $f = $("#diaSaveFm");
//     if (submitForm($f) == false) return false;
//     var sCondition = {};
//     sCondition = $tr.combined(1) || {};
//     sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack,false);

// }

//校验订购数量
function isCount($obj)
{
    var reg = /^\+?[1-9][0-9]*$/;
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}

function doDiaPartSave(rowobj){
    $rowpart = $(rowobj);
    $("td input[type=radio]",$(rowobj)).attr("checked",true);
    // 订单数量
    var $obj = $(rowobj).find("td").eq(9).find("input:first");
    // 备注
    var $obj1 = $(rowobj).find("td").eq(11).find("input:first");
    if($obj.val()=="" || !isCount($obj)){
        alertMsg.warn("请正确输入订购数量！");
        return false;
    }
    if(orgType==<%=DicConstant.ZZLB_09%>){
        // 配送中心提报
        var minPack= $rowpart.attr("MIN_PACK");
        if(($obj.val()%minPack)){
            alertMsg.warn("订购数量应为最小包装倍数,请重新输入.");
            return false;
        }
    }
    // 订单主键
    $("#saveOrderId").val($("#diaOrderId").val());
    // 订单明细主键
    $("#saveDtlId").val($(rowobj).attr("DTL_ID"));
    // 经销商价
    $("#saveUnitPrice").val($(rowobj).attr("UNIT_PRICE"));
    // 订单数量
    $("#saveOrderCount").val($obj.val());
    // 备注
    $("#saveRemarks").val($obj1.val());
    
    var $f = $("#diaSaveFm");
    if (submitForm($f) == false) return false;
    var sCondition = {};
    sCondition = $f.combined(1) || {};
    //doNormalSubmit($f, diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
    sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
}
//配件保存回调
function savePartCountCallBack(res){
    try {
        var selectedIndex = $("#tab-orderPartList").getSelectedIndex();
        var selectedRow = $("#tab-orderPartList").getSelectedRows()[0];
        selectedRow.find("td").eq(9).html(selectedRow.find("td").eq(9).find("input:first").val());
        selectedRow.find("td").eq(11).html(selectedRow.find("td").eq(11).find("input:first").val());
         $("#tab-orderPartList").updateResult(res,selectedIndex);
         $("#tab-orderPartList").clearRowEdit(selectedRow,selectedIndex);
         var orderCount = selectedRow.find("td").eq(9).text();
        var unitPrice = selectedRow.find("td").eq(6).text();
        var amount = parseInt(orderCount,10)*removeAmountFormat(unitPrice);
        selectedRow.find("td").eq(10).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length>0){
            var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
            $("#diaOrderAmount").val(amount);
            $("#divOrderAmount").html(amountFormatNew(amount)+"元");
            $("#divOrderAmount1").html(amountFormatNew(amount)+"元");
            $("#divOrderAmount2").html(amountFormatNew(amount)+"元");
        }

    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function diaOrderPartDeleteCallBack(res){
    try {
           $("#diaOrderAmount").val(0);
           $("#divOrderAmount").html(0.00+"元");
           $("#divOrderAmount1").html(0.00+"元");
           $("#divOrderAmount2").html(0.00+"元");
           orderPartSearch();
           	$("#diaDirectTypeCode").attr("disabled",false);
           var $f = $("#searchForm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/partOrderSearch.ajax";
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
</script>