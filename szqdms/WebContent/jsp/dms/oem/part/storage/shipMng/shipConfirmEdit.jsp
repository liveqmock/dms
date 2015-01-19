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
        <div class="pageContent"  id="shipPage" style="overflow:auto;">
        	<form id="exportFm" method="post" style="display:none">
		        <input type="hidden" id="params" name="data">
		    </form>
		    <form method="post" id="fm-carrierInfo" >
		        <input type="hidden" id="SHIP_ID" datasource="SHIP_ID"/>
		        <input type="hidden" id="DEL_ID" datasource="DEL_ID"/>
		        <input type="hidden" id="VEHICLE_ID" datasource="VEHICLE_ID"/>
		        <input type="hidden" id="LICENSE_PLATE" datasource="LICENSE_PLATE"/>
		        <input type="hidden" id="DRIVER_ID" datasource="DRIVER_ID"/>
		        <input type="hidden" id="DRIVER_NAME" datasource="DRIVER_NAME"/>
		        <input type="hidden" id="DRIVER_PHONE" datasource="DRIVER_PHONE"/>
		    </form>
            <form method="post" id="fm-shipInfo" class="editForm">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="dia-SHIP_ID" name="dia-SHIP_ID" datasource="SHIP_ID"/>
                <div align="left">
                    <fieldset>
                        <legend align="right"><a onclick="onTitleClick('tab-shipInfo')">&nbsp;发运单编辑&gt;&gt;</a>
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
                                    <input type="text" id="dia-CARRIER_ID" name="dia-CARRIER_ID" datasource="CARRIER_ID" readonly/>
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
                        <legend align="right"><a onclick="onTitleClick('tab-carrierInfo')">&nbsp;承运信息编辑&gt;&gt;</a>
                        </legend>

                        <form method="post" id="fm-searchCarrier" class="editForm">
                            <table class="searchContent" id="tab-searchCarrier"></table>
                        </form>
                        <div id="div-carrierList" style="">
                            <table style="display:none;width:100%;" id="tab-carrierList" name="tablist" ref="div-carrierList" refQuery="tab-searchCarrier" edit="true">
                                <thead>
                                <tr>
                                    <th type="single" name="XH" style="" append="plus|addRow"></th>
                                    <th fieldname="LICENSE_PLATE" ftype="input" fdatatype="0,is_null,30" fkind="dic" fsrc="">承运车辆</th>
                                    <th fieldname="DRIVER_NAME" freadonly="true">承运司机</th>
                                    <th fieldname="DRIVER_PHONE" freadonly="true">司机电话</th>
                                    <th colwidth="105" type="link" title="[编辑]|[删除]"  action="doDiaCarrierSave|doDiaCarrierDelete">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                        <table class="editTable" id="tab-carrierInfo">
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="6"><textarea class="" rows="3" id="dia-CARRIER_REMARKS" name="dia-CARRIER_REMARKS" datasource="CARRIER_REMARKS" style="width:500px;" datatype="1,is_null,500"></textarea></td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </form>
	        <div class="formBar">
	            <ul>
	                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export">运输清单下载</button></div></div></li>
	                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-submit">提&nbsp;&nbsp;交</button></div></div></li>
	                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
	            </ul>
	        </div>
	        <form method="post" id="fm-searchShipDtl" class="editForm">
	            <table class="searchContent" id="tab-searchShipDtl"></table>
	        </form>
	        <div id="div-shipDtlList" style="">
	            <table style="display:none;width:95%;" id="tab-shipDtlList" name="tablist" ref="div-shipDtlList" refQuery="tab-searchShipDtl">
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
	                    <th fieldname="PHONE" colwidth="85">联系电话</th>
	                </tr>
	                </thead>
	            </table>
	        </div>
        </div>
    </div>
</div>

<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipConfirmAction";
$(function(){
	$("#shipPage").height(document.documentElement.clientHeight-100);
    var selectedRows = $("#tab-shipList").getSelectedRows();
    setEditValue("fm-shipInfo", selectedRows[0].attr("rowdata"));
    searchShipDtl();
    searchCarrier();
    var iH = document.documentElement.clientHeight;
    $("#tab-carrierList").attr("layoutH",iH-220);
    
    $("#tab-carrierList").find("th").eq(1).attr("fsrc","T#PT_BA_VEHICLE:LICENSE_PLATE:VIN{VEHICLE_ID,LICENSE_PLATE,DRIVER_NAME,PHONE}:1=1 AND CARRIER_ID ="+selectedRows[0].attr("CARRIER_ID")+" AND STATUS=<%=DicConstant.YXBS_01 %>");
<%--    $("#tab-carrierList").find("th").eq(2).attr("fsrc","T#PT_BA_DRIVER:DRIVER_NAME:DRIVER_NO{DRIVER_ID,DRIVER_NAME,PHONE}:1=1 AND CARRIER_ID ="+selectedRows[0].attr("CARRIER_ID")+" AND STATUS=<%=DicConstant.YXBS_01 %>"); --%>

    //提交
    $('#btn-submit').bind('click', function () {
    	
    	if($("#tab-carrierList_content").find("tr").size()==0){
			alertMsg.warn("请添加车辆信息.");
			return false;
		}else if($("#tab-carrierList_content").find("tr").size() == 1)
		{
			if($("#tab-carrierList_content").find("tr").eq(0).find("td").size() == 1)
			{
				alertMsg.warn("请添加车辆信息.");
    			return false;
			}
		}
    	
        var $f = $("#fm-shipInfo");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var updateUrl = diaSaveAction + "/submitShip.ajax";
        doNormalSubmit($f, updateUrl, "btn-submit", sCondition, diaSubmitCallBack);
    });

    //导出运输清单
    $('#btn-export').bind('click',function(){
        $("#exportFm").attr("action",diaSaveAction+"/exportTransDtl.ajax?SHIP_ID="+$('#dia-SHIP_ID').val());
        $("#exportFm").submit();
    });

    $("#tab-shipDtlList").show();
    $("#tab-shipDtlList").jTable();

    $("#tab-carrierList").show();
    $("#tab-carrierList").jTable();


})

function diaSubmitCallBack(){
    var editWin = $("body").data("editWin");
    $.pdialog.close(editWin);
    $('#btn-search').click();
}

//查询发运单明细
function searchShipDtl() {
    var searchShipDtlUrl = diaSaveAction+"/searchShipDtl.ajax?shipId=" + $('#dia-SHIP_ID').val();
    var $f = $("#fm-searchShipDtl");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchShipDtlUrl, "", 1, sCondition, "tab-shipDtlList");
}

//查询承运信息
function searchCarrier() {
    var searchCarrierUrl = diaSaveAction+"/searchCarrier.ajax?shipId=" + $('#dia-SHIP_ID').val();
    var $f = $("#fm-searchCarrier");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchCarrierUrl, "", 1, sCondition, "tab-carrierList");
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
    //$row 行对象
    //selIndex 字典中第几行

    if(id.indexOf("LICENSE_PLATE") == 0)
    {
        var curRow = $("#"+id);
        while(curRow.get(0).tagName != "TR")
        {
            curRow = curRow.parent();
        }
        curRow.attr("VEHICLE_ID",$row.attr("VEHICLE_ID"));
        curRow.find("td").eq(2).find("input:first").val($row.attr("LICENSE_PLATE"));
        curRow.find("td").eq(3).html($row.attr("DRIVER_NAME"));
        curRow.find("td").eq(4).html($row.attr("PHONE"));
    }
    return true;
}
function formatAmount(obj){
    return amountFormatNew($(obj).html());
}
function addRow()
{
	var $tabs = $("#tab-carrierList_content");
	while($tabs.get(0).tagName != "TABLE")
        $tabs = $tabs.parent();
    $tabs.parent().parent().attr("style","overflow:hidden;");
	$tabs.createRow();
}
//行编辑 保存
function doDiaCarrierSave(row){
    var ret = true;
    try
    {
        $("td input[type=radio]",$(row)).attr("checked",true);
        var $f = $("#fm-carrierInfo");
        var SHIP_ID = $('#dia-SHIP_ID').val();
        var DEL_ID = $(row).attr("DEL_ID");
        var VEHICLE_ID = $(row).attr("VEHICLE_ID");
        var DRIVER_ID = $(row).attr("DRIVER_ID");
        //设置隐藏域
        $("#SHIP_ID").val(SHIP_ID);
        $("#VEHICLE_ID").val(VEHICLE_ID);
        $("#LICENSE_PLATE").val($(row).find("td").eq(2).find("input:first").val());
/*         $("#DRIVER_ID").val(DRIVER_ID); */
        $("#DRIVER_NAME").val($(row).find("td").eq(3).text());
        $("#DRIVER_PHONE").val($(row).find("td").eq(4).text());
        $("#DEL_ID").val(DEL_ID);
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        //需要将隐藏域或者列表只读域传给后台
        if(DEL_ID)
        {
            var url = diaSaveAction + "/updateCarrier.ajax";
            sendPost(url,"",sCondition,diaListSaveCallBack,"true");
        }else
        {
            var url = diaSaveAction + "/insertCarrier.ajax";
            sendPost(url,"",sCondition,diaListSaveCallBack,"false");
        }
    }catch(e){
        alertMsg.error(e);ret = false;
    }
    return ret;
}
//行编辑保存回调
function diaListSaveCallBack(res){
	searchCarrier();
/*     var selectedIndex = $("#tab-carrierList").getSelectedIndex();
    $("#tab-carrierList").updateResult(res,selectedIndex);
    $("#tab-carrierList").clearRowEdit($("#tab-carrierList").getSelectedRows()[0],selectedIndex); */
    return true;

}
//行编辑 删除
var $row;
function doDiaCarrierDelete(row){
    $row =$(row);
    $("td input[type=radio]",$(row)).attr("checked",true);
    var DEL_ID = $(row).attr("DEL_ID");
    if(DEL_ID){
        var url = diaSaveAction + "/deleteCarrier.ajax?DEL_ID="+DEL_ID;
        sendPost(url,"delete","",diadeleteCallBack,"true");
    }else{
        $("#tab-carrierList").removeResult($row);
    }
}
//行编辑删除回调方法
function  diadeleteCallBack(res)
{
    try
    {
        if($row)
            $("#tab-carrierList").removeResult($row);
    }catch(e)
    {
        alertMsg.error(e);
        return false;
    }
    return true;
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
</script>