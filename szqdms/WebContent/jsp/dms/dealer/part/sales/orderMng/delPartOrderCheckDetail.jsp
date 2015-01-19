<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <div>
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
                            <tr>
                                <td><label>订单金额：</label></td>
                                <td style="width:195px;"><div id="diaOrderAmount"></div></td>
                                <td><label>是否免运费：</label></td>
                                <td>
                                    <input type="text" style="width:50px;" id="diaIfTrans"  name="diaIfTrans" kind="dic" src="" datasource="IF_TRANS"  datatype="1,is_null,30" readOnly value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="5"><textarea id="diaRemarks" style="width:89%;height:40px;" name="diaRemarks" datasource="REMARKS"  datatype="1,is_null,100" readOnly></textarea></td>
                            </tr>
                        </table>
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
            <div style="overflow:hidden;">
                <form method="post" id="diaPartListFm">
                    <table id="diaPartListTab"></table>
                </form>
                <form method="post" id="saveFm">
                <input type="hidden" id="dtlIds" datasource="DTLIDS"/>
                <input type="hidden" id="auditCounts" datasource="AUDITCOUNTS"/>
                <input type="hidden" id="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="supplierIds" datasource="SUPPLIERIDS"/>
                <input type="hidden" id="diaIfDelay" name="diaIfDelay" datasource="IF_DELAY_ORDER"/>
                <div id="divPartOrderDetail" style="overflow:hidden;">
                    <table style="display:none;width:98%;" id="partOrderDetailList" name="partOrderDetailList" ref="divPartOrderDetail" refQuery="diaPartListTab">
                    <thead>
                        <tr>
                            <th fieldname="ROWNUMS" style="display:"></th>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="DTL_ID" refer="myDtlId" style="display:none"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="170" refer="toSubStr">配件名称</th>
                            <th fieldname="UNIT" colwidth="45">计量单位</th>
                            <th fieldname="MIN_PACK" refer="appendStr"  colwidth="70">最小包装</th>
                            <th fieldname="UNIT_PRICE" refer="amountFormatStr" style="text-align:right;">经销商价</th>
                            <th fieldname="IF_SUPPLIER" style="display:none">是否指定供应商</th>
                            <th fieldname="SUPPLIER_NAME" refer="supplierShow">供应商</th>
                            <th fieldname="ORDER_COUNT">订购数量</th>
                            <th fieldname="AVAILABLE_AMOUNT">可用库存</th>
                            <th fieldname="AUDIT_COUNT" refer="myInput">审核数量</th>
                            <th fieldname="PART_ID" refer="myPartId" style="display:none">配件ID</th>
                            <th fieldname="SUPPLIER_ID" refer="mySupplierId" style="display:none">供应商ID</th>
                            <th fieldname="REMARKS">备注</th>
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
                    <li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                </ul>
                <div style="text-align:center;">
                    <span>审核意见：</span><input type="text" id="checkInfo" name="checkInfo" datasource="REMARKS" style="height:18px;width:50%;" datatype="1,is_null,100"></input>
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
var diaUrl ="<%=request.getContextPath()%>/part/salesMng/orderMng/DelPartOrderCheckAction";
var checkCount = 0;
$(function()
{
    //设置高度
    $("#partOrderDetailList").attr("layoutH",document.documentElement.clientHeight-170);
    $("#divPartOrderInfo").attr("layoutH",document.documentElement.clientHeight-500);
    var selectedRows = $("#orderList").getSelectedRows();
    setEditValue("diaPartOrderInfoFm", selectedRows[0].attr("rowdata"));
    $("#diaOrderNo").html(selectedRows[0].attr("ORDER_NO"));
    $("#diaOrderType1").val(selectedRows[0].attr("ORDER_TYPE"));
    $("#diaIfDelay").val(selectedRows[0].attr("IF_DELAY_ORDER"));
    var delayFlag = false;
    // 是否是延期订单
    if(<%=DicConstant.SF_01%> == selectedRows[0].attr("IF_DELAY_ORDER")) {
        delayFlag = true;
        $("#checkReturn").hide();
    }
    $("#diaOrderAmount").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
    //订单审核记录查询
    var checkSearchUrl = diaUrl + "/orderCheckListSearch.ajax?&orderId="+selectedRows[0].attr("ORDER_ID");
    sendPost(checkSearchUrl, "", "", checkSearchCallback, "false");
    partOrderDetailSearch();
    $("#dia-close").click(function(){
        $.pdialog.closeCurrent();
        return false;
    });
    $("#dia-approve1").bind("click",function(){
        var $f = $("#saveFm");
        if (submitForm($f) == false) return false;
        // 延期标识
        var flag = false;
        // 审核验证标识
        var checkFlag = false;
        // 审核验证标识
        var checkFlag1 = false;
        var checkFlag2 = false;
        var dtlIds = "";
        var partIds = "";
        var supplierIds = "";
        var auditCounts="";
        if($("#partOrderDetailList_content").size()==0){
            $("#partOrderDetailList").find("tbody").find("tr").each(function(){
                var dtlId=$(this).find("td").eq(2).find("input:first").val();
                var orderCount=$(this).find("td").eq(10).text();
                var stockCount=$(this).find("td").eq(11).text();
                var auditCount=$(this).find("td").eq(12).find("input:first").val();
                var partId=$(this).find("td").eq(13).find("input:first").val();
                var supplierId=$(this).find("td").eq(14).find("input:first").val();
                if(!isAmount(auditCount)) {
                	checkFlag1 = true;
                }
                if(dtlIds.length==0) {
                    dtlIds = dtlId;
                } else {
                    dtlIds = dtlIds+","+dtlId;
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
                if(auditCounts.length==0){
                    auditCounts = auditCount;
                }else {
                    auditCounts = auditCounts+","+auditCount;
                }
                // 非直发订单
                if(parseInt(auditCount)<parseInt(orderCount)){
                    flag = true;
                }
//                 var orderType = $("#diaOrderType1").val();
                if(parseInt(auditCount) > parseInt(stockCount)) {
                	checkFlag2 = true;
                    return false;
                }
                if (parseInt(auditCount)>parseInt(orderCount)) {
//                 	alert("订购数量");
                    checkFlag = true;
                    return false;
                }
            });
        }else{
            $("#partOrderDetailList_content").find("tr").each(function(){
                var dtlId=$(this).find("td").eq(2).find("input:first").val();
                var orderCount=$(this).find("td").eq(10).text();
                var stockCount=$(this).find("td").eq(11).text();
                var auditCount=$(this).find("td").eq(12).find("input:first").val();
                var partId=$(this).find("td").eq(13).find("input:first").val();
                var supplierId=$(this).find("td").eq(14).find("input:first").val();
                if(!isAmount(auditCount)) {
                	checkFlag1 = true;
                }
                if(dtlIds.length==0) {
                    dtlIds = dtlId;
                } else {
                    dtlIds = dtlIds+","+dtlId;
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
                if(auditCounts.length==0){
                    auditCounts = auditCount;
                }else {
                    auditCounts = auditCounts+","+auditCount;
                }
                //alert(auditCount+'--------'+orderCount);
                // 非直发订单
                if(parseInt(auditCount) < parseInt(orderCount)){
               // alert(auditCount+'--------'+orderCount);
                    flag = true;
                }
//                 var orderType = $("#diaOrderType1").val();
                if(parseInt(auditCount)>parseInt(stockCount)) {
                	checkFlag2 = true;
                    return false;
                }
                if (parseInt(auditCount)>parseInt(orderCount)) {
                    checkFlag = true;
                    return false;
                }
            });
        }
//         alert(checkFlag);
         if (checkFlag1) {
        	 alertMsg.warn("请输入正确审核数量.");
             return false;
        }
        if (checkFlag2) {
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
        if (count == 0&& delayFlag) {
            alertMsg.warn("延期订单审核数量不能全部为0！");
            return false;
        }
        // 审核数量
        checkCount = count;
        $("#dtlIds").val(dtlIds);
        $("#partIds").val(partIds);
        $("#supplierIds").val(supplierIds);
        $("#auditCounts").val(auditCounts);
        var orderType = $("#diaOrderType1").val();
        if(flag){
                alertMsg.confirm("有部分配件库存不满足，是否生成延期订单？",{okCall:doApproveOk1,cancelCall:doApproveOk2});
        }else{
            var sCondition = {};
            sCondition = $f.combined(1) || {};
            var passUrl = diaUrl + "/partOrderCheckPass.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+orderType+"&flag=1";
            doNormalSubmit($f, passUrl, "dia-approve1", sCondition, diaPassCallBack);
        }
    });
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
});
//校验数量
function isAmount(val)
{
    var reg =/^(0|\+?[1-9][0-9]*)$/;
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}
//生成延期订单
function doApproveOk1()
{
    var $f = $("#saveFm");
    if (submitForm($f) == false) return false;
    var sCondition = {};
    sCondition = $f.combined(1) || {};
    var passUrl = diaUrl + "/partOrderCheckPass.ajax?&orderId="+$("#diaOrderId").val()+"&orderType="+$("#diaOrderType1").val()+"&flag=2";
    doNormalSubmit($f, passUrl, "dia-approve1", sCondition, diaPassCallBack);
}
//不生成延期订单
function doApproveOk2()
{
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
function myPartId(obj){
    var $tr =$(obj).parent();
    return "<input type='text' id='"+$tr.attr("PART_ID")+"' name='dtlId' value='"+$tr.attr("PART_ID")+"'>";
}
function mySupplierId(obj){
    var $tr =$(obj).parent();
    return "<input type='text' id='"+$tr.attr("SUPPLIER_ID")+"' name='dtlId' value='"+$tr.attr("SUPPLIER_ID")+"'>";
}
//输入框
function myInput(obj){
    var $tr =$(obj).parent();
     var orderCount = $tr.attr("ORDER_COUNT");
    var availableAmount= $tr.attr("AVAILABLE_AMOUNT");
    if(parseInt(availableAmount)<parseInt(orderCount)){
        $tr.attr("style","background-color:#f09393");
        return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+availableAmount+"' datatype='0,is_null,10'>";
    }else{
        return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+orderCount+"' datatype='0,is_null,10'>";
    }
}
function isNum(obj){
    var auditCount = $(obj).val();
    var reg = /^[0-9][0-9]*$/;//正则表达式(数字)
    if(reg.test(auditCount)){
        return true;
    }else{
        alertMsg.warn("请输入正确审核数量.");
        return false;
    }
}
//输入框校验
function checkAuditCount(obj){
    var $tr =$(obj).parent().parent();
    var orderCount = $tr.attr("ORDER_COUNT");
    var availableAmount= $tr.attr("AVAILABLE_AMOUNT");
    var auditCount = $(obj).val();
    if(parseInt(auditCount) > parseInt(orderCount))
    {
        alertMsg.warn("审核数量不能大于订购数量.");
        return false;
    }
    if(parseInt(auditCount) > parseInt(availableAmount))
    {
        alertMsg.warn("审核数量不能大于可用库存.");
        return false;
    }
    isNum(obj);
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
    var ifSuply= $tr.attr("IF_SUPPLIER");
    var str="";
    if(ifSuply=='<%=DicConstant.SF_01%>'){
        str = $tr.attr("SUPPLIER_NAME");
    }else{
        str = "";
    }
    return str;
}
function toSubStr(obj){
	 var $obj = $(obj);
	 var $tr = $obj.parent();
	 var partName = $tr.attr('PART_NAME');
	 if(partName.length>15){
		 return "<div style='width:150px;'>"+partName.substring(0,15)+"...</div>";
	 }else{
		 return "<div style='width:150px;'>"+partName+"</div>";
	 }
}
</script>