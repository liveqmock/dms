<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    String yearStr1 = yearFormat.format(Pub.getCurrentDate());
    String monthStr1 = monthFormat.format(Pub.getCurrentDate());
    String orgId = user.getOrgId();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单关闭</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderUnlockAction";
var orgId = "<%=orgId%>";
//初始化方法
$(function(){
	
//    
    //查询方法
    $("#btn-search").bind("click",function(event){
        doSearch();
    });
    $("#btn-search").trigger("click");
    $("#btn-reset").bind("click", function(event){
		$("#searchForm")[0].reset();
		$("#orgCode").attr("code","");
		$("#orgCode").val("");
	});
    var checkType = url + "/getCarrier.ajax";
    sendPost(checkType, "", "", getCarrierearchCallback, "false");
    
});
function doSearch(){
    var $f = $("#searchForm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    var searchUrl = url+"/partOrderSearch.ajax";
    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "orderList");
}
//列表审核链接
function doUnlock(rowobj){
    $row = $(rowobj);
    var  reportUrl= url + "/partOrderUnlock.ajax?orderId="+$(rowobj).attr("ORDER_ID")+"&orderStatus="+$row.attr("ORDER_STATUS")+"&shipStatus="+$row.attr("SHIP_STATUS");
    sendPost(reportUrl,"","",CallBack,"true");
}

//列表审核链接
function doPrint(rowobj){
	$row = $(rowobj);
	$("#orderId").val($(rowobj).attr("ORDER_ID"));
	$("#dia-ORDER_NO").val($(rowobj).attr("ORDER_NO"));
	$("#dia-ORG_NAME").val($(rowobj).attr("ORG_NAME"));
	$("#dia-ORDER_AMOUNT").val($(rowobj).attr("ORDER_AMOUNT"));
	$("#dia-APPLY_DATE").val($(rowobj).attr("APPLY_DATE_sv"));
	window.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printOrderDtl.jsp");
}

function showLink(obj)
{
	var $row=$(obj).parent();
    return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
}
function openDetail(ORDER_ID){
	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/sales/orderMng/lockSaleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
}


function CallBack(res)
{
    try
    {
        var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
        if(result=='1'){
            if($row)
                $("#orderList").removeResult($row);
        }
    }catch(e)
    {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}

function getCarrierearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
                var flag = getNodeText(rows[0].getElementsByTagName("IS_AM").item(0));
                if(flag==<%=DicConstant.SF_01%>){
                	$("#orderType").attr("filtercode","<%=DicConstant.DDLX_08%>");
                }else{
                	$("#orderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_07%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>");
                }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 订单解锁</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="searchForm">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <input type="hidden" id="orderId" name="orderId"/>
                <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME"/>
                <input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO"/>
                <input type="hidden" id="dia-ORDER_AMOUNT" name="dia-ORDER_AMOUNT"/>
                <input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE"/>
                
                <table class="searchContent" id="searchTab">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datasource="T.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>订单类型：</label></td>
                        <td>
                            <select type="text" id="orderType" name="orderType" kind="dic" src="DDLX" datasource="T.ORDER_TYPE" filtercode="" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" group="startDate,endDate" id="startDate" kind="date" name="startDate" style="width:75px;" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate,endDate" id="endDate" kind="date" name="endDate" style="width:75px;margin-left:-30px;" datasource="T.APPLY_DATE" datatype="1,is_date,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>渠道商：</label></td>
                        <td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" readOnly datatype="1,is_null,10000" hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" operation="in"/></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="orderListDiv" >
            <table style="display:none;width:100%;" id="orderList" name="orderList" ref="orderListDiv" refQuery="searchTab">
                    <thead>
                        <tr>
                            <th fieldname="ROWNUMS" style="display:"></th>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ORDER_NO" colwidth="145" refer="showLink">订单编号</th>
                            <th fieldname="ORDER_TYPE">订单类型</th>
                            <th fieldname="ORG_NAME">渠道商</th>
                            <th fieldname="WAREHOUSE_NAME">供货库</th>
                            <th fieldname="IF_DELAY_ORDER">是否为延期订单</th>
                            <th fieldname="EXPECT_DATE" >期望到货日期</th>
                            <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">总金额(元)</th>
                            <th fieldname="CREATE_USER" >提报人</th>
                            <th fieldname="APPLY_DATE" colwidth="130">提报时间</th>
                            <th fieldname="REMARKS" maxlength="20">备注</th>
                            <th type="link" colwidth="45" title="[解锁]" action="doUnlock" >操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
</body>
</html>