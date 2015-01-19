<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<div id="dia-layout">
    <form method="post" id="fm-saleOrderInfo" class="editForm">
        <%--隐藏域查询条件--%>
        <input type="hidden" id="dia-ORDER_ID" name="dia-ORDER_ID" datasource="ORDER_ID"/>
        <input type="hidden" id="dia-WAREHOUSE_ID" name="dia-WAREHOUSE_ID" datasource="WAREHOUSE_ID"/>
        <div align="left">
            <fieldset>
<!--                 <legend align="right"><a onclick="onTitleClick('tab-saleOrderInfo')">&nbsp;订单基本信息&gt;&gt;</a>
                </legend> -->
                <table class="editTable" id="tab-saleOrderInfo">
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="ORDER_NO" readonly datatype="1,is_null,30"/></td>
                        <td><label>订单类型：</label></td>
                        <td><input type="text" id="dia-ORDER_TYPE" name="dia-ORDER_TYPE" datasource="ORDER_TYPE" readonly datatype="1,is_null,30"/></td>
                        <td><label>提报日期：</label></td>
                        <td><input type="text" id="dia-APPLY_DATE" name="dia-APPLY_DATE" datasource="APPLY_DATE" readonly datatype="1,is_null,30"/></td>
                    </tr>
                    <tr>
                        <td><label>提报单位：</label></td>
                        <td><input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" readonly datatype="1,is_null,100"/></td>
                        <td><label>审核员：</label></td>
                        <td><input type="text" id="dia-CHECK_USER" name="dia-CHECK_USER" datasource="CHECK_USER" readonly datatype="1,is_null,30"/></td>
                        <td><label>审核日期：</label></td>
                        <td><input type="text" id="dia-CHECK_DATE" name="dia-CHECK_DATE" datasource="CHECK_DATE" readonly datatype="1,is_null,30"/></td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </form>

    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>发料信息</span></a></li>
                    <li><a href="javascript:handleTabClick()"><span>发料单打印</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--发料信息 begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="fm-searchSaleOrderPart" class="editForm">
                    <table class="searchContent" id="tab-searchSaleOrderPart"></table>
                </form>
                <div id="div-saleOrderPartList" style="">
                    <table style="display:none;width:100%;" id="tab-saleOrderPartList" name="tablist" limitH="false" ref="div-saleOrderPartList" refQuery="tab-searchSaleOrderPart">
                        <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none" colwidth="15"></th>
                            <th fieldname="PART_CODE" colwidth="180">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="180">配件名称</th>
                            <th fieldname="UNIT" colwidth="80">单位</th>
                            <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                            <th fieldname="IF_SUPPLIER" colwidth="80">是否指定供应商</th>
                            <th fieldname="SUPPLIER_NAME" colwidth="110" refer="ctrlShow">供应商</th>
                            <th fieldname="ORDER_COUNT">订购数量</th>
                            <th fieldname="AUDIT_COUNT">审核数量</th>
                            <th fieldname="USER_NAME">库管员</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="formBar">
                    <ul>
                        <li id="btn-createIssueOrder">
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button">生成发料单</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <!--发料信息 end-->
            <!--发料单打印 begin-->
            <div>
                <form method="post" id="fm-searchIssueOrder" class="editForm">
                    <table class="searchContent" id="tab-searchIssueOrder"></table>
                </form>
                <div id="div-issueOrderList" style="">
                    <table style="display:none;width:100%;" id="tab-issueOrderList" name="tablist" ref="div-issueOrderList" refQuery="tab-searchIssueOrder">
                        <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none"></th>
                            <th fieldname="ISSUE_NO" refer="createLink">发料单号</th>
                            <th fieldname="PRINT_STATUS" refer="myPrintStatus">打印状态</th>
                            <th fieldname="USER_NAME">库管员</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="formBar">
                    <ul>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <!--发料单打印 end-->
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction";
var flag = true;
$(function(){
    var iH = document.documentElement.clientHeight;
    $(".tabsContent").height(iH - 160);

    var selectedRows = $("#tab-saleOrderList").getSelectedRows();
    setEditValue("fm-saleOrderInfo", selectedRows[0].attr("rowdata"));
    $("#tab-saleOrderPartList").show();
    $("#tab-saleOrderPartList").jTable();
    $("#tab-issueOrderList").show();
    $("#tab-issueOrderList").jTable();

    var searchSaleOrderPartUrl = diaSaveAction+"/searchSaleOrderPart.ajax?ORDER_ID="+$('#dia-ORDER_ID').val();
    var $f = $("#fm-searchSaleOrderPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchSaleOrderPartUrl, "", 1, sCondition, "tab-saleOrderPartList");
    $('#btn-createIssueOrder').bind('click',function(){
        var $f = $("#fm-saleOrderInfo");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var updateUrl = diaSaveAction + "/createIssueOrder.ajax";
        doNormalSubmit($f, updateUrl, "btn-createIssueOrder", sCondition, diaCreateIssueOrderCallBack);
    });

    //发料单已生成 则隐藏生成发料单按钮
    var SHIP_STATUS = selectedRows[0].attr("SHIP_STATUS");
    if(SHIP_STATUS=='<%=DicConstant.DDFYZT_02%>'){
        $('#btn-createIssueOrder').hide();
    }
});

function diaCreateIssueOrderCallBack(){
	$('#btn-createIssueOrder').hide();
    $("#btn-search").click();
    searchIssueOrder();
    $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
}

function searchIssueOrder(){
    var searchIssueOrderUrl = diaSaveAction+"/searchIssueOrder.ajax?ORDER_ID="+$('#dia-ORDER_ID').val();
    var $f = $("#fm-searchIssueOrder");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchIssueOrderUrl, "", 1, sCondition, "tab-issueOrderList");
}

function toAppendStr(obj){
    var $tr =$(obj).parent();
    return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
}
function createLink(obj){
   obj.html("<A class=op onclick=openIssueDtlWin(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>"+obj.text()+"</A>");
}
function doPrintTitle(rowobj) {
	$row = $(rowobj);
    $("td input[type=radio]", $(rowobj)).attr("checked", true);
    window.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printPartTitle.jsp");
}
function openIssueDtlWin(rowobj){
	$("td input[type=radio]", $(rowobj)).attr("checked", true);
    var options = {max: false, width: 900, height: 450, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printIssueIssueDtl.jsp", "issueDtlWin", "发料单明细", options);
}
function handleTabClick(){
    if(flag) {
        searchIssueOrder();
    }
    flag = false;
}
function ctrlShow(obj){
    var $tr =$(obj).parent();
    if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
        return "";
    }else{
        return $(obj).text();
    }
}
//输入框
function myPrintStatus(obj){
    var $tr =$(obj).parent();
    var printStatus = $tr.attr("PRINT_STATUS");
    // 其他订单
    if(printStatus==<%=DicConstant.DYZT_02%>){
         $tr.attr("style","background-color:#f09393");
    }
}
</script>