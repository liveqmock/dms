<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
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
            <div style="overflow:auto;" id="div-partOrderDetaillist1">
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
                            <tr id="army" style="display:none">
                            	<td><label>客户名称：</label></td>
                                <td>
                                    <input type="text" id="dia-in-custormName"  name="dia-in-custormName" datasource="CUSTORM_NAME"  datatype="1,is_null,30"  readOnly/>
                                </td>
                                <td><label>合同号：</label></td>
                                	<td><input type="text" id="dia-armyContNo"  name="dia-armyContNo" datasource="ARMY_CONT_NO" datatype="1,is_null,300" readOnly/></td>
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
            <div>
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
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="DTL_ID" refer="myDtlId" style="display:none"></th>
                            <th fieldname="PART_CODE" colwidth="40">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="40">配件名称</th>
                            <th fieldname="UNIT" colwidth="70">计量单位</th>
                            <th fieldname="MIN_PACK" refer="appendStr"  colwidth="70">最小包装</th>
                            <th fieldname="UNIT_PRICE" refer="amountFormatStr" style="text-align:right;">经销商价</th>
                            <th fieldname="IF_SUPPLIER" colwidth="90" style="display:none">是否指定供应商</th>
                            <th fieldname="SUPPLIER_NAME"  refer="supplierShow">供应商</th>
                            <th fieldname="ORDER_COUNT">订购数量</th>
                            <th fieldname="AMOUNT">总数量</th>
                            <th fieldname="OCCUPY_AMOUNT">锁定库存</th>
                            <th fieldname="AVAILABLE_AMOUNT">可用库存</th>
                            <th fieldname="AUDIT_COUNT" refer="myInput">审核数量</th>
                            <th fieldname="PLAN_PRODUCE_NO" style="display:none" refer="myplanProduceNo">计生号</th>
                            <th fieldname="REMARKS">备注</th>
                            <th fieldname="USER_ACCOUNT">库管员</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="formBar" style="width:98%;">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-approve1">审核通过</button></div></div></li>
                    <li id="checkReturn"><div class="button"><div class="buttonContent"><button type="button" id="dia-approveback">审核驳回</button></div></div></li>
                    <li id="export"><div class="button"><div class="buttonContent"><button type="button" id="dia-export">导出明细</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                </ul>
                <div style="text-align:center;">
                    <span>审核意见：</span><input type="text" id="checkInfo" name="checkInfo" datasource="REMARKS" style="height:18px;width:40%;" datatype="1,is_null,100"/>
                    <span id="planProduceNoName" datatype="1,is_null,30" style="display:none">计生号：</span><input type="text" id="planProduceNo" name="planProduceNo" datasource="PLAN_PRODUCE_NO" style="display:none;height:18px;width:10%;" datatype="0,is_null,100"/>
                </div>
            </div>
            </form>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderCheckAction";
    var orderTypeFlag = true;
    var checkCount = 0;
    $(function() {
        
        //设置高度
        $("#div-partOrderDetaillist1").height(document.documentElement.clientHeight-150);
        $("#divPartOrderDetail").height(document.documentElement.clientHeight-150);
        //$("#divPartOrderInfo").attr("layoutH",document.documentElement.clientHeight-500);
        var selectedRows = $("#orderList").getSelectedRows();
        setEditValue("diaPartOrderInfoFm", selectedRows[0].attr("rowdata"));
        $("#diaOrderNo").html(selectedRows[0].attr("ORDER_NO"));
        $("#diaOrderType1").val(selectedRows[0].attr("ORDER_TYPE"));
        // 延期订单flag
        var delayFlag = false;
        // 是否是延期订单
        if(<%=DicConstant.SF_01%> == selectedRows[0].attr("IF_DELAY_ORDER")) {
            delayFlag = true;
            $("#checkReturn").hide();
        }
        if($("#diaOrderType1").val()==<%=DicConstant.DDLX_04%>){
            // 总成订单
            $("#planProduceNo").show();
            $("#planProduceNoName").show();
        }
        if($("#diaOrderType1").val()==<%=DicConstant.DDLX_05%>){
            // 直发订单
            orderTypeFlag = false;
            // 显示供应商
            $("#tr-supplier").show();
        }
        if($("#diaOrderType1").val()==<%=DicConstant.DDLX_07%>){
            // 直发订单
            orderTypeFlag = false;
        }
        if($("#diaOrderType1").val()==<%=DicConstant.DDLX_06%>){
            $("#tr-progrom").show();
        }
        if($("#diaOrderType1").val()==<%=DicConstant.DDLX_08%>){
            $("#army").show();
        }
        $("#diaIfDelay").val(selectedRows[0].attr("IF_DELAY_ORDER"));
        $("#diaOrderAmount").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
         //账户可用余额查询
        var accountSearchUrl = diaUrl + "/accountSearch.ajax?&orgId="+selectedRows[0].attr("ORG_ID");
        sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
        //订单资金使用查询
        var fundsSearchUrl = diaUrl + "/orderFundsListSearch.ajax?&orderId="+selectedRows[0].attr("ORDER_ID");
        sendPost(fundsSearchUrl, "", "", fundsSearchCallback, "false");
        //订单审核记录查询
        var checkSearchUrl = diaUrl + "/orderCheckListSearch.ajax?&orderId="+selectedRows[0].attr("ORDER_ID");
        sendPost(checkSearchUrl, "", "", checkSearchCallback, "false");
        partOrderDetailSearch();
    
        // 关闭按钮绑定
        $("#dia-close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    
        // 审核通过按钮绑定
        $("#dia-approve1").bind("click",function(){
            var $f = $("#saveFm");
            if (submitForm($f) == false) return false;
            // 延期标识
            var flag = false;
            // 审核验证标识
            var checkFlag = false;
            var checkFlag1 = false;
            var checkFlag2 = false;
            var dtlIds = "";
            // 审核数量
            var auditCounts="";
//             // 计生号
//             var planProduceNos = "";
            if($("#partOrderDetailList_content").size()==0){
                $("#partOrderDetailList").find("tbody").find("tr").each(function(){
                    var dtlId=$(this).find("td").eq(2).find("input:first").val();
                    var orderCount=$(this).find("td").eq(10).text();
                    var stockCount=$(this).find("td").eq(13).text();
                    var auditCount=$(this).find("td").eq(14).find("input:first").val();
                    if(!isAmount(auditCount)) {
                    	checkFlag1 = true;
                    }
//                     var planProduceNo= "";
//                     if ($(this).find("td").eq(13).find("input:first").val()!="undefined") {
//                         planProduceNo =$(this).find("td").eq(13).find("input:first").val();
//                     }
                    if(dtlIds.length==0) {
                        dtlIds = dtlId;
                    } else {
                        dtlIds = dtlIds+","+dtlId;
                    }
                    // 审核数量
                    if(auditCounts.length==0) {
                        auditCounts = auditCount;
                    } else {
                        auditCounts = auditCounts+","+auditCount;
                    }
//                     // 计生号
//                     if(planProduceNos.length==0) {
//                         planProduceNos = planProduceNo;
//                     } else {
//                         planProduceNos = planProduceNos+","+planProduceNo;
//                     }
                    if (orderTypeFlag) {
                        // 非直发订单
                        if(parseInt(auditCount)<parseInt(orderCount)){
                            flag = true;
                        }
                    }
                    var orderType = $("#diaOrderType1").val();
                    if(orderType!="<%=DicConstant.DDLX_05%>"){
                        if(parseInt(auditCount) > parseInt(stockCount)) {
                        	checkFlag2 = true;
//                             alertMsg.warn("审核数量不能大于可用库存.");
                            return false;
                        }
                    }
                    if (parseInt(auditCount)>parseInt(orderCount)) {
                        checkFlag = true;
                    }
                });
            }else{
                $("#partOrderDetailList_content").find("tr").each(function(){
                    var dtlId=$(this).find("td").eq(2).find("input:first").val();
                    var orderCount=$(this).find("td").eq(10).text();
                    var stockCount=$(this).find("td").eq(13).text();
                    var auditCount=$(this).find("td").eq(14).find("input:first").val();
                    if(!isAmount(auditCount)) {
                    	checkFlag1 = true;
                    }
//                     var planProduceNo= "";
//                     if ($(this).find("td").eq(13).val()!="undefined") {
//                         planProduceNo=$(this).find("td").eq(13).find("input:first").val();
//                     }
                    if(dtlIds.length==0) {
                        dtlIds = dtlId;
                    } else {
                        dtlIds = dtlIds+","+dtlId;
                    }
                    // 审核数量
                    if(auditCounts.length==0) {
                        auditCounts = auditCount;
                    } else {
                        auditCounts = auditCounts+","+auditCount;
                    }
//                     // 计生号
//                     if(planProduceNos.length==0) {
//                         planProduceNos = planProduceNo;
//                     } else {
//                         planProduceNos = planProduceNos+","+planProduceNo;
//                     }
                    if (orderTypeFlag) {
                        // 非直发订单
                        if(parseInt(auditCount)<parseInt(orderCount)){
                            flag = true;
                        }
                    }
                    var orderType = $("#diaOrderType1").val();
                    if(orderType!="<%=DicConstant.DDLX_05%>"){
                        if(parseInt(auditCount) > parseInt(stockCount)) {
                        	checkFlag2 = true;
//                          alertMsg.warn("审核数量不能大于可用库存.");
                         return false;
                        }
                    }
                    if (parseInt(auditCount)>parseInt(orderCount)) {
                        checkFlag = true;
                        return false;
                    }
                });
            }
            if(checkFlag1) {
                alertMsg.warn("请正确输入审核数量.");
                return false;
            }
            if(checkFlag2) {
                alertMsg.warn("审核数量不能大于可用库存.");
                return false;
            }
            if (checkFlag) {
                alertMsg.warn("审核数量不能大于订购数量.");
                return false;
            }
            var auditCountses = auditCounts.split(',');
            var count = 0;
            for(var i=0;i<auditCountses.length;i++){
                count = count + parseInt(auditCountses[i]);
            }
            if (delayFlag) {
                if (count == 0 || isNaN(count)) {
                    alertMsg.warn("延期订单审核数量合计不能为0！");
                    return false;
                }
            }
            // 审核数量
            checkCount = count;
            $("#dtlIds").val(dtlIds);
            // 审核数量
            $("#auditCounts").val(auditCounts);
//             // 计生号
//             $("#planProduceNos").val(planProduceNos);
            var orderType = $("#diaOrderType1").val();
            if(flag) {
                if(orderType!="<%=DicConstant.DDLX_05%>"){
                    alertMsg.confirm("有部分配件库存不满足，是否生成延期订单？",{okCall:doApproveOk1,cancelCall:doApproveOk2});
                }
            } else {
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                var passUrl = diaUrl + "/partOrderCheckPass.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+orderType+"&flag=1";
                doNormalSubmit($f, passUrl, "dia-approve1", sCondition, diaPassCallBack);
            }
        });

        // 审核驳回按钮绑定
        $("#dia-approveback").bind("click",function(){
            if($("#checkInfo").val().length<=0){
                alertMsg.warn("请填写审核意见.");
                return false;
            }
            var $f = $("#saveFm");
            var sCondition = {};
            sCondition = $f.combined(1) || {};
            var approveBackUrl = diaUrl + "/partOrderCheckApproveBack.ajax?&orderId="+$("#diaOrderId").val();
            doNormalSubmit($f, approveBackUrl, "dia-approveback", sCondition, diaApproveBackCallBack);
        });
        $('#dia-export').bind('click',function(){
            $("#exportFm").attr("action",diaUrl+"/download.ajax?orderId="+$('#diaOrderId').val());
            $("#exportFm").submit();
        });
    });

    // 生成延期订单
    function doApproveOk1() {
        var $f = $("#saveFm");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var passUrl = diaUrl + "/partOrderCheckPass.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+$("#diaOrderType1").val()+"&flag=2";
        doNormalSubmit($f, passUrl, "dia-approve1", sCondition, diaPassCallBack);
    }

    // 不生成延期订单
    function doApproveOk2() {
    	if(checkCount==0) {
    		alertMsg.warn("审核总数量不能为0.");
            return false;
    	}
        var $f = $("#saveFm");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var passUrl = diaUrl + "/partOrderCheckPass.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+$("#diaOrderType1").val()+"&flag=1";
        doNormalSubmit($f, passUrl, "dia-approve1", sCondition, diaPassCallBack);
    }

    //订单审核通过回调
    function diaPassCallBack(res){
        try {
            var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = diaUrl+"/partOrderCheckSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "orderList");
            $.pdialog.closeCurrent();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //订单审核驳回回调
    function diaApproveBackCallBack(res){
        try {
            var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = diaUrl+"/partOrderCheckSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "orderList");
            $.pdialog.closeCurrent();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //订单配件列表查询
    function partOrderDetailSearch(){
        var $f = $("#diaPartListFm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var detailSearchUrl = diaUrl+"/partOrderDetailSearch.ajax?&orderId="+$("#diaOrderId").val();  
        doFormSubmit($f, detailSearchUrl, "", 1, sCondition, "partOrderDetailList");
    }

    //最小包装
    function appendStr(obj){
        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }

    //金额格式化
    function amountFormatStr(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    function myDtlId(obj){
        var $tr =$(obj).parent();
        return "<input type='text' id='"+$tr.attr("DTL_ID")+"' name='dtlId' value='"+$tr.attr("DTL_ID")+"'>";
    }

    // 计生号
    function myplanProduceNo(obj) {
        var $tr =$(obj).parent();
        return "<input type='text' id='planProduce"+$tr.attr("DTL_ID")+"' name='planProduce"+$tr.attr("DTL_ID")+"' style='width:60px;' datatype='0,is_null,30'>";
    }

    //输入框
    function myInput(obj){
        var $tr =$(obj).parent();
        var orderCount = $tr.attr("ORDER_COUNT");
        var availableAmount= $tr.attr("AVAILABLE_AMOUNT");
        var orderType = $("#diaOrderType1").val();
        // 直发订单
        if(orderType=="<%=DicConstant.DDLX_05%>"){
            return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+orderCount+"' datatype='0,is_null,10'>";
        }
        // 其他订单
        if(parseInt(availableAmount)<parseInt(orderCount)){
            $tr.attr("style","background-color:#f09393");
            return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+availableAmount+"' datatype='0,is_null,10'>";
        } else {
            return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+orderCount+"' datatype='0,is_null,10'>";
        }
    }

    //输入框校验
    function checkAuditCount(obj){
        var $tr =$(obj).parent().parent();
        // 订购数量
        var orderCount = $tr.attr("ORDER_COUNT");
        // 可用库存
        var availableAmount= $tr.attr("AVAILABLE_AMOUNT");
        var auditCount = $(obj).val();
        if(auditCount.length==0){
            alertMsg.warn("请输入审核数量！");
            return false;
        }  
        if($(obj).val() && !isAmount($(obj).val()))
        {
            alertMsg.warn("请正确输入审核数量！");
            return false;
        }
        if(parseInt(auditCount) > parseInt(orderCount))
        {
            alertMsg.warn("审核数量不能大于订购数量.");
            return false;
        }
        var orderType = $("#diaOrderType1").val();
        if(orderType!="<%=DicConstant.DDLX_05%>"){
            if(parseInt(auditCount) > parseInt(availableAmount)) {
                alertMsg.warn("审核数量不能大于可用库存.");
                return false;
            }
        }
        
    }

    //校验数量
    function isAmount(val) {
        var reg =/^(0|\+?[1-9][0-9]*)$/;
        if(reg.test(val))
        {
            return true;
        }else
        {
            return false;
        }
    }

    //查询回调
    function callbackSearch(res,tabId,sParam){
        switch(tabId)
        {
            case "partOrderDetailList":
                setStyle('partOrderDetailList_content');
            break;
        }
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

    //订单资金使用查询回调
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

    //订单审核记录查询回调
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
</script>