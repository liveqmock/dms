<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent" style="">
            <form method="post" id="fm-shipInfo" class="editForm">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="dia-SHIP_ID" name="dia-SHIP_ID" datasource="SHIP_ID"/>
                <input type="hidden" id="dia-SHIP_STATUS" name="dia-SHIP_STATUS" datasource="SHIP_STATUS" value="<%=DicConstant.FYDZT_01%>"/>
                <input type="hidden" id="dia-CREATE_USER" name="dia-CREATE_USER" datasource="CREATE_USER"/>
                <input type="hidden" id="dia-CREATE_TIME" name="dia-CREATE_TIME" datasource="CREATE_TIME"/>
                <input type="hidden" id="dia-ARMY" name="dia-ARMY"/>
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
                                <td><label>是否军品：</label></td>
							    <td >	
								    <input type="text" id="dia-IF_ARMY"  name="dia-IF_ARMY" kind="dic" src="SF" datasource="IF_ARMY" datatype="0,is_null,30" value=""/>
								</td>
                                <td><label>承运单位：</label></td>
                                <td>
                                    <input type="hidden" id="dia-CARRIER_ID"  name="dia-CARRIER_ID" datasource="CARRIER_ID"/>
                                    <input type="hidden" id="dia-CARRIER_NAME" name="dia-CARRIER_NAME" datasource="CARRIER_NAME"/>
                                    <input type="text" id="dia-CARRIER_CODE" name="dia-CARRIER_CODE" datasource="CARRIER_CODE" kind="dic" src="" datatype="0,is_null,30" readonly="true"/>
                                </td>

                            </tr>
                            <tr>
                                <td><label>联系人：</label></td>
                                <td><input type="text" id="dia-LINK_MAN" name="dia-LINK_MAN" datasource="LINK_MAN" readonly datatype="1,is_null,30"/></td>
                                <td><label>联系电话：</label></td>
                                <td><input type="text" id="dia-PHONE" name="dia-PHONE" datasource="PHONE" readonly datatype="1,is_phone,11"/></td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="6"><textarea class="" rows="3" id="dia-REMARKS" name="dia-REMARKS" datasource="REMARKS" style="width:500px;" datatype="1,is_null,500"></textarea></td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </form>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export">运输清单下载</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-submit">提&nbsp;&nbsp;交</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
        <form method="post" id="fm-searchShipDtl" class="editForm">
            <table class="searchContent" id="tab-searchShipDtl"></table>
        </form>
        <div id="div-shipDtlList" style="height:340px;overflow:hidden;">
            <table style="display:none;width:100%;" id="tab-shipDtlList" layoutH="350" name="tablist" ref="div-shipDtlList" refQuery="tab-searchShipDtl">
                <thead>
                <tr>
                    <th type="single" name="XH" append="plus|openSaleSelWin"></th>
                    <th fieldname="ORDER_NO" refer="showLink">订单编号</th>
                    <th fieldname="ORDER_TYPE">订单类型</th>
                    <th fieldname="ORG_NAME">提报单位</th>
                    <th fieldname="COUNT">配件数量</th>
                    <th fieldname="AMOUNT" refer="formatAmount" align="right">配件金额</th>
                    <th fieldname="DELIVERY_ADDR">收货地址</th>
                    <th fieldname="LINK_MAN">联系人</th>
                    <th fieldname="PHONE">联系电话</th>
                    <th colwidth="85" type="link" title="[删除]" action="deleteShipDtl">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <form id="exportFm" method="post" style="display:none">
        <input type="hidden" id="params" name="data">
    </form>
</div>

<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction";
var diaAction = "<%=action%>";
$(function(){
    if (diaAction != "1") {//修改
        var selectedRows = $("#tab-shipList").getSelectedRows();
        setEditValue("fm-shipInfo", selectedRows[0].attr("rowdata"));
        $("#dia-CARRIER_ID").val(selectedRows[0].attr("CARRIER_ID"));
        $("#dia-CARRIER_NAME").val(selectedRows[0].attr("CARRIER_NAME"));
        $("#dia-CARRIER_CODE").attr("code",selectedRows[0].attr("CARRIER_CODE"));
        $("#dia-CARRIER_CODE").val(selectedRows[0].attr("CARRIER_NAME"));
        var ifArmy = $("#dia-IF_ARMY").attr("code");
        $("#dia-ARMY").val(ifArmy);
        if(ifArmy==<%=DicConstant.SF_01%>){
        	<%-- $('#dia-CARRIER_CODE').attr('code',$row.attr('CARRIER_CODE'));
            $('#dia-CARRIER_CODE').val($row.attr('CARRIER_NAME'));
            $('#dia-CARRIER_NAME').val($row.attr('CARRIER_NAME'));
            $('#dia-CARRIER_ID').val($row.attr('CARRIER_ID'));
        	$("#dia-CARRIER_CODE").attr("src","T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID,CARRIER_NAME,LINK_MAN,PHONE}:1=1 AND IF_ARMY = <%=DicConstant.SF_01%> AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY CARRIER_CODE"); --%>
         	var getCarrier = diaSaveAction + "/getCarrier.ajax";
            sendPost(getCarrier, "", "", getCarrierearchCallback, "false");
        }else{
        	$("#dia-CARRIER_CODE").attr("src","T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID,CARRIER_CODE,CARRIER_NAME,LINK_MAN,PHONE}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY CARRIER_CODE");
        }
        searchShipDtl();
    } else {//新增

    }
    $('#btn-save').bind('click', function () {
        var $f = $("#fm-shipInfo");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        if (diaAction == 1)	//插入动作
        {
            var addUrl = diaSaveAction + "/insertShip.ajax";
            doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
        } else	//更新动作
        {
            var updateUrl = diaSaveAction + "/updateShip.ajax";
            doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
        }
    });

    //导出运输清单
    $('#btn-export').bind('click',function(){
        var shipId = $('#dia-SHIP_ID').val();
        if(!shipId){
            alertMsg.warn('请先保存出库单信息!');
            return;
        }
        
        if($("#tab-shipDtlList_content").find("tr").size()==0){
			alertMsg.warn("请维护发运单明细!");
			return false;
		}else if($("#tab-shipDtlList_content").find("tr").size() == 1)
		{
			if($("#tab-shipDtlList_content").find("tr").eq(0).find("td").size() == 1)
			{
				alertMsg.warn("请维护发运单明细!");
    			return false;
			}
		}
        
        $("#exportFm").attr("action",diaSaveAction+"/exportTransDtl.ajax?SHIP_ID="+$('#dia-SHIP_ID').val());
        $("#exportFm").submit();
    });

    //提交
    $('#btn-submit').bind('click',function(){
        var shipId = $('#dia-SHIP_ID').val();
        if(!shipId){
            alertMsg.warn('请先保存出库单信息!');
            return;
        }
        var submitUrl = diaSaveAction + "/submitShip.ajax?shipId="+shipId;
        sendPost(submitUrl, "", "", diaSubmitCallBack, "true");
    });

    $("#tab-shipDtlList").show();
    $("#tab-shipDtlList").jTable();
})

function diaSubmitCallBack(){
    var editWin = $("body").data("editWin");
    $.pdialog.close(editWin);
    var selectedRows = $("#tab-shipList").getSelectedRows();
    $("#tab-shipList").removeResult(selectedRows[0]);
}

//新增回调函数
function diaInsertCallBack(res) {
    try {
        var rows = res.getElementsByTagName("ROW");
        if (rows && rows.length > 0) {
            var shipId = getNodeText(rows[0].getElementsByTagName("SHIP_ID").item(0));
            var shipNo = getNodeText(rows[0].getElementsByTagName("SHIP_NO").item(0));
            var CREATE_USER = getNodeText(rows[0].getElementsByTagName("CREATE_USER").item(0));
            var CREATE_TIME = getNodeText(rows[0].getElementsByTagName("CREATE_TIME").item(0));
            $('#dia-SHIP_ID').val(shipId);
            $('#dia-SHIP_NO').val(shipNo);
            $('#dia-CREATE_USER').val(CREATE_USER);
            $('#dia-CREATE_TIME').val(CREATE_TIME);
        }

        $("#tab-shipList").insertResult(res,0);
        if($("#tab-shipList_content").size()>0){
            $("td input[type=radio]",$("#tab-shipList_content").find("tr").eq(0)).attr("checked",true);
        }else{
            $("td input[type=radio]",$("#tab-shipList").find("tr").eq(0)).attr("checked",true);
        }
        diaAction = 2;
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

//更新回调函数
function diaUpdateCallBack(res) {
    try {
        var selectedIndex = $("#tab-shipList").getSelectedIndex();
        $("#tab-shipList").updateResult(res, selectedIndex);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
    //$row 行对象
    //selIndex 字典中第几行
    
    
    if(id == "dia-IF_ARMY")
    {
        var ifArmy = $("#dia-IF_ARMY").attr("code");
        $("#dia-ARMY").val(ifArmy);
        if(ifArmy==<%=DicConstant.SF_01%>){
        	<%-- $('#dia-CARRIER_CODE').attr('code',$row.attr('CARRIER_CODE'));
            $('#dia-CARRIER_CODE').val($row.attr('CARRIER_NAME'));
            $('#dia-CARRIER_NAME').val($row.attr('CARRIER_NAME'));
            $('#dia-CARRIER_ID').val($row.attr('CARRIER_ID'));
        	$("#dia-CARRIER_CODE").attr("src","T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID,CARRIER_NAME,LINK_MAN,PHONE}:1=1 AND IF_ARMY = <%=DicConstant.SF_01%> AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY CARRIER_CODE"); --%>
         	var getCarrier = diaSaveAction + "/getCarrier.ajax";
            sendPost(getCarrier, "", "", getCarrierearchCallback, "false");
        }else{
        	
        	$("#dia-CARRIER_CODE").attr("src","T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID,CARRIER_CODE,CARRIER_NAME,LINK_MAN,PHONE}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY CARRIER_CODE");
        }
        
    }    
    
    if(id == 'dia-CARRIER_CODE'){
    	$('#dia-CARRIER_CODE').attr('code',$row.attr('CARRIER_CODE'));
        $('#dia-CARRIER_CODE').val($row.attr('CARRIER_NAME'));
        $('#dia-CARRIER_NAME').val($row.attr('CARRIER_NAME'));
        $('#dia-CARRIER_ID').val($row.attr('CARRIER_ID'));
        $('#dia-LINK_MAN').val($row.attr('LINK_MAN'));
        $('#dia-PHONE').val($row.attr('PHONE'));
    }
    return true;
}
function getCarrierearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
       		if(diaAction == "1"){
                var CARRIER_ID = getNodeText(rows[0].getElementsByTagName("CARRIER_ID").item(0));
                var CARRIER_CODE = getNodeText(rows[0].getElementsByTagName("CARRIER_CODE").item(0));
                var CARRIER_NAME = getNodeText(rows[0].getElementsByTagName("CARRIER_NAME").item(0));
                var LINK_MAN = getNodeText(rows[0].getElementsByTagName("LINK_MAN").item(0));
                var PHONE = getNodeText(rows[0].getElementsByTagName("PHONE").item(0));
/*             	$('#dia-CARRIER_ID').val(CARRIER_ID);
            	$('#dia-CARRIER_CODE').val(CARRIER_CODE);
            	$('#dia-CARRIER_NAME').val(CARRIER_NAME);
            	$('#dia-LINK_MAN').val(LINK_MAN);
                $('#dia-PHONE').val(PHONE);  */
                $('#dia-CARRIER_CODE').attr('code',CARRIER_CODE);
                $('#dia-CARRIER_CODE').val(CARRIER_NAME);
                $('#dia-CARRIER_NAME').val(CARRIER_NAME);
                $('#dia-CARRIER_ID').val(CARRIER_ID);
                $('#dia-LINK_MAN').val(LINK_MAN);
                $('#dia-PHONE').val(PHONE);  
            	$("#dia-CARRIER_CODE").attr("src","T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID,CARRIER_NAME,LINK_MAN,PHONE}:1=1 AND IF_ARMY = <%=DicConstant.SF_01%> AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY CARRIER_CODE");
       		}else{
       			$("#dia-CARRIER_CODE").attr("src","T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID,CARRIER_NAME,LINK_MAN,PHONE}:1=1 AND IF_ARMY = <%=DicConstant.SF_01%> AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY CARRIER_CODE");
       		}
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//弹出销售订单选择列表，支持复选
function openSaleSelWin() {
    if(!$('#dia-SHIP_ID').val()){
        alertMsg.warn('请先保存发运单信息!');
        return;
    }
    var options = {max: false, width: 920, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/shipMngSaleSel.jsp", "saleSelWin", "销售订单查询", options);
}

//查询发运单明细
function searchShipDtl() {
    var searchShipDtlUrl = diaSaveAction+"/searchShipDtl.ajax?shipId=" + $('#dia-SHIP_ID').val();
    var $f = $("#fm-searchShipDtl");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchShipDtlUrl, "", 1, sCondition, "tab-shipDtlList");
}

var $row;

//删除方法，rowobj：行对象，非jquery类型
function deleteShipDtl(rowobj) {
    $row = $(rowobj);
    var deleteUrl = diaSaveAction + "/deleteShipDtl.ajax?dtlId=" + $(rowobj).attr("DTL_ID")+"&orderId="+$(rowobj).attr("ORDER_ID");
    sendPost(deleteUrl, "", "", deleteShipDtlCallBack, "true");
}
//删除回调方法
function deleteShipDtlCallBack(res) {
    try {
        if ($row)
            $("#tab-shipDtlList").removeResult($row);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function formatAmount(obj){
    return amountFormatNew($(obj).html());
}
function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
}
function openDetail(ORDER_ID){
	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}
</script>