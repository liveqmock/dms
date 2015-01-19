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
    <div class="page">
        <div class="pageContent" id="shipPage" style="overflow:auto;">
            <form method="post" id="fm-shipInfo" class="editForm">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="dia-SHIP_ID" name="dia-SHIP_ID" datasource="SHIP_ID"/>
                <div align="left">
                    <fieldset>
                        <legend align="right"><a onclick="onTitleClick('tab-shipInfo')">&nbsp;发运单信息&gt;&gt;</a>
                        </legend>
                        <table class="editTable" id="tab-shipInfo">
                            <tr>
                                <td><label>发运单号：</label></td>
                                <td>
                                    <input type="text" id="dia-SHIP_NO" name="dia-SHIP_NO" datasource="SHIP_NO" readonly value="自动生成"/>
                                </td>
                                <td><label>承运单位：</label></td>
                                <td>
                                    <input type="text" id="dia-CARRIER_NAME" name="dia-CARRIER_NAME" datasource="CARRIER_NAME" readonly/>
                                </td>

                            </tr>
                            <tr>
                                <td><label>联系人：</label></td>
                                <td><input type="text" id="dia-LINK_MAN" name="dia-LINK_MAN" datasource="LINK_MAN" readonly/></td>
                                <td><label>联系电话：</label></td>
                                <td><input type="text" id="dia-PHONE" name="dia-PHONE" datasource="PHONE" readonly/></td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="6"><textarea class="" rows="3" id="dia-REMARKS" name="dia-REMARKS" datasource="REMARKS" style="width:500px;" readonly></textarea></td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset>
                        <form method="post" id="fm-searchCarrier" class="editForm" style="display:none">
                            <table class="searchContent" id="tab-searchCarrier"></table>
                        
                        <div id="div-carrierList" style="">
                            <table style="display:none;width:100%;" id="tab-carrierList" name="tablist" ref="div-carrierList" refQuery="tab-searchCarrier">
                                <thead>
                                <tr>
                                    <th type="single" name="XH" style="display: none"></th>
                                    <th fieldname="LICENSE_PLATE" >承运车辆</th>
                                    <th fieldname="DRIVER_NAME"colwidth="80">承运司机</th>
                                    <th fieldname="DRIVER_PHONE" >司机电话</th>
                                    <th colwidth="355" type="link" title="[添加发运信息]|[上传装箱信息]|[下载装箱信息]|[打印运输回执]"  action="doAddTranInfo|doUpLoadBoxNo|doDownLoadBoxNo|doPrintBack">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                        <table class="editTable" id="tab-carrierInfo">
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="6"><textarea class="" rows="3" id="dia-CARRIER_REMARKS" name="dia-CARRIER_REMARKS" datasource="CARRIER_REMARKS" style="width:500px;" readonly></textarea></td>
                            </tr>
                        </table>
                        </form>
                    </fieldset>
                </div>
            </form>
	        <div class="formBar">
	            <ul>
	                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-shipOut">发运出库</button></div></div></li>
	                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-addAtt">添加附件</button></div></div></li>
	                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-downAtt">下载附件</button></div></div></li>
	                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
	            </ul>
	        </div>
	        <form method="post" id="fm-searchShipDtl" class="editForm">
	            <table class="searchContent" id="tab-searchShipDtl"></table>
	        </form>
	        <form id="exportFm" method="post" style="display:none">
		        <input type="hidden" id="params" name="data">
		    </form>
	        <div id="div-shipDtlList">
	            <table style="display:none;width:98%;" id="tab-shipDtlList" name="tablist" ref="div-shipDtlList" refQuery="tab-searchShipDtl">
	                <thead>
	                <tr>
	                    <th type="single" name="XH" style="display: none"></th>
	                    <th fieldname="ORDER_NO" refer="showLink">订单编号</th>
	                    <th fieldname="ORDER_TYPE">订单类型</th>
	                    <th fieldname="ORG_NAME">提报单位</th>
	                    <th fieldname="COUNT" colwidth="85">配件数量</th>
	                    <th fieldname="AMOUNT" colwidth="85" refer="formatAmount" align="right">配件金额</th>
	                    <th fieldname="DELIVERY_ADDR" colwidth="150">收货地址</th>
	                    <th fieldname="LINK_MAN">联系人</th>
	                    <th fieldname="PHONE" colwidth="95">联系电话</th>
	                    <th colwidth="55" type="link" title="[下载]"  action="doDownloadOrder">操作</th>
	                </tr>
	                </thead>
	            </table>
	        </div>
        </div>
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction";
$(function(){
	$("#shipPage").height(document.documentElement.clientHeight-50);
    var selectedRows = $("#tab-shipList").getSelectedRows();
    setEditValue("fm-shipInfo", selectedRows[0].attr("rowdata"));
    searchShipDtl();
    searchCarrier();
    //提交
    $('#btn-shipOut').bind('click', function () {
        var $f = $("#fm-shipInfo");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var updateUrl = diaSaveAction + "/shipOut.ajax";
        doNormalSubmit($f, updateUrl, "btn-shipOut", sCondition, diaShipOutCallBack);
    });

    //上传附件
    $("#btn-addAtt").bind("click", function () {
        var SHIP_ID = $("#dia-SHIP_ID").val();
        $.filestore.open(SHIP_ID, {"folder": "true", "holdName": "true", "fileSizeLimit": 0, "fileTypeDesc": "All Files", "fileTypeExts": "*.*"});
    });

    //下载附件
    $("#btn-downAtt").bind("click", function () {
        var SHIP_ID = $("#dia-SHIP_ID").val();
        $.filestore.view(SHIP_ID);
    });

    $("#tab-shipDtlList").show();
    $("#tab-shipDtlList").jTable();

    $("#tab-carrierList").show();
    $("#tab-carrierList").jTable();
})

function diaShipOutCallBack(){
    var editWin = $("body").data("editWin");
    $.pdialog.close(editWin);
    var selectedRows = $("#tab-shipList").getSelectedRows();
    $("#tab-shipList").removeResult(selectedRows[0]);
}

//查询承运信息
function searchCarrier() {
    var searchCarrierUrl = diaSaveAction+"/searchCarrier.ajax?shipId=" + $('#dia-SHIP_ID').val();
    var $f = $("#fm-searchCarrier");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchCarrierUrl, "", 1, sCondition, "tab-carrierList");
}

//查询发运单明细
function searchShipDtl() {
    var searchShipDtlUrl = diaSaveAction+"/searchShipDtl.ajax?shipId=" + $('#dia-SHIP_ID').val();
    var $f = $("#fm-searchShipDtl");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchShipDtlUrl, "", 1, sCondition, "tab-shipDtlList");
}
function formatAmount(obj){
    return amountFormatNew($(obj).html());
}

function doAddBoxNo(rowobj){
    var options = {max:false,width:970,height:500,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/orderBoxNoSel.jsp?VEHICLE_ID="+$(rowobj).attr("VEHICLE_ID"), "addOrderPartSel", "装箱信息查询", options);
}

function doPrintBack(rowobj){
	    var date = $(rowobj).attr("EXPECT_DATE");
		if(date){
			var v_id = $(rowobj).attr("VEHICLE_ID");
			var s_id = $(rowobj).attr("SHIP_ID");
			var queryUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction/printPdf.do?VEHICLE_ID="+v_id+"&SHIP_ID="+s_id;
	        window.open(queryUrl);
		}else{
			alertMsg.warn('尚未维护预计到达日期及箱数等信息,请核对.');
			return false;
		}
}

function createLink(obj){
	   obj.html("<A class=op onclick=openVeclDtlWin(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>"+obj.text()+"</A>");
	}
function openVeclDtlWin(rowobj){
	$("td input[type=radio]", $(rowobj)).attr("checked", true);
    var options = {max: false, width: 900, height: 450, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/shipMng/vehicleBoxDetail.jsp?VEHICLE_ID="+$(rowobj).attr("VEHICLE_ID")+"&SHIP_ID="+$('#dia-SHIP_ID').val(), "boxDetail", "装车明细", options,true);
}
function doDownloadOrder(rowobj){
	$("#exportFm").attr("action",diaSaveAction+"/downloadOrder.ajax?orderId="+$(rowobj).attr("ORDER_ID"));
    $("#exportFm").submit();
}
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
}
function openDetail(ORDER_ID){
	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
function doUpLoadBoxNo(rowobj){
	$.filestore.open($(rowobj).attr("DEL_ID"),{"folder":"true","holdName":"false","fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
}
function doDownLoadBoxNo(rowobj){
	$.filestore.view($(rowobj).attr("DEL_ID"));
}
function doAddTranInfo(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var diOption= {max:false,width:750,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/packAdd.jsp", "addPack", "添加包装信息", diOption);
}
</script>