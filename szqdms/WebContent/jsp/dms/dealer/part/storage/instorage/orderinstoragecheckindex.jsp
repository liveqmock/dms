<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单验收入库</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/part/storageMng/enterStorage/InStorageAction";
var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    //初始化方法
    $(function() {
        //查询方法
        $("#btn-search").bind("click", function(event) {
            doSearch();
        });
    }); 
    //查看订单详细信息
    function toCkeckIn(rowobj) {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/storage/instorage/orderCheckIn.jsp", "orderCheckIn", "订单验收入库", diaOptions);
    }
    function doSearch() {
        var $f =$("#fm-searchContract");    
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/ordersearch.ajax";        
        doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
    }

    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    }
    function openDetail(ORDER_ID){
    	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }
</script>
</head>
<body>
    <div id="layout" style="width: 100%;">
        <div id='background1' class='background'></div>
        <div id='progressBar1' class='progressBar'>loading...</div>
        <h4 class="contentTitle" align="left">
            <img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：仓储管理 &gt; 入库管理 &gt; 订单验收入库
        </h4>
        <div class="page">
            <div class="pageHeader">
                <form method="post" id="fm-searchContract">
                    <!-- 定义隐藏域条件 -->
                    <div class="searchBar" align="left">
                        <table class="searchContent" id="tab-ordersearch">
                            <!-- 定义查询条件 -->
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><input type="text" id="in-ORDER_ID" name="in-ORDER_ID" datasource="T.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                                <td><label>订单类型：</label></td>
                                <td><select type="text" id="in-ordertype" name="in-ordertype" kind="dic" src="DDLX" datasource="T.ORDER_TYPE" datatype="1,is_null,30">
                                        <option value="-1" selected>--</option>
                                </select></td>
                            </tr>
                            <tr>
                                <td><label>提报日期：</label></td>
                                <td><input type="text" group="in-kstbrq,in-jstbrq" id="in-applydateq" name="in-applydateq" kind="date" style="width: 75px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/>
                                        <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="in-applydateh" name="in-applydateh" kind="date" style="width: 75px; margin-left: -30px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/></td>
                                <td><label>发运单号：</label></td>
                                <td><input type="text" id="in-shipno" name="in-shipno" datasource="SHIP_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                                <td><label>发运日期：</label></td>
                                <td><input type="text" group="in-kstbrq,in-jstbrq" id="in-kstbrq" name="in-kstbrq" kind="date" style="width:75px;" datasource="T.SHIP_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-kstbrq,in-jstbrq" id="in-jstbrq" name="in-jstbrq" kind="date" style="width:75px; margin-left:-30px;" datasource="T.SHIP_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
                            </tr>
                        </table>
                        <div class="subBar">
                            <ul>
                                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" onclick="doSearch()">查&nbsp;&nbsp;询</button></div></div></li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="pageContent">
                <div id="page_orderlist">
                    <table style="display: none; width: 100%;" id="tab-contract"
                        name="tablist" ref="page_orderlist" refQuery="fm-searchContract">
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display: none;"></th>
                                <th fieldname="ORDER_NO" colwidth="155" refer="showLink">订单编号</th>
                                <th fieldname="ORDER_TYPE">订单类型</th>
                                <th fieldname="WAREHOUSE_NAME">供货配件库</th>
                                <th fieldname="ORDER_AMOUNT" colwidth="80" refer="amountFormat" align="right">订单总金额(元)</th>
                                <th fieldname="REAL_AMOUNT" colwidth="80" refer="amountFormat" align="right">实发金额(元)</th>
                                <th fieldname="SHIP_NO">发运单号</th>
                                <th fieldname="SHIP_DATE">发运日期</th>
                                <th fieldname="CARRIER_NAME">承运商</th>
                                <th fieldname="LINK_MAN">联系人</th>
                                <th fieldname="PHONE">联系电话</th>
                                <th colwidth="80" type="link" title="[验收入库]" action="toCkeckIn">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>