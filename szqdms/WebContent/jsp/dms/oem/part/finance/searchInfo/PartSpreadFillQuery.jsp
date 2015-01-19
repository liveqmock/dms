<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件价差调整查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 财务相关&gt;配件价差应补查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form action="post" id="searchForm">
                <input type="hidden" id="orderId" name="orderId"/>
                <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME"/>
                <input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO"/>
                <input type="hidden" id="dia-ORDER_AMOUNT" name="dia-ORDER_AMOUNT"/>
                <input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE"/>
        </form>
        <form id="exportFm" method="post" style="display:none">
           <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                    	<td><label>调价时间：</label></td>
                        <td>
                            <input type="text" group="startDate,endDate" id="startDate" kind="date" action="show" name="startDate" style="width:75px;" datasource="PRICE_DATE" datatype="1,is_date,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate,endDate" id="endDate" kind="date" action="show" name="endDate" style="width:75px;margin-left:-30px;" datasource="PRICE_DATE" datatype="1,is_date,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>应补类型：</label></td>
                        <td>
                            <select type="text" id="spreadType" name="spreadType" kind="dic" src="JCLX" action="show" datasource="FILL_TYPE" filtercode="" datatype="0,is_null,30" operation="=">
                                <option value="<%=DicConstant.JCLX_01%>" selected>一次应补</option>
                            </select>
                        </td>
                        </tr>
                    <tr>
                    	<td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode" name="partCode" datasource="PART_CODE" datatype="1,is_null,50" operation="like" /></td>
                        <td><label>渠道商：</label></td>
                        <td><input type="text" id="orgCode" name="orgCode" datasource="ORG_ID" datatype="1,is_null,100" readOnly hasBtn="true" callFunction="showOrgTree('orgCode',1,1,1)" operation="in"/></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid">
            <table style="display:none;width:100%;" id="tab-grid-index" name="tablist" ref="page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="FILL_TYPE">应补类型</th>
                        <th fieldname="PRICE_DATE" colwidth="130">调价时间</th>
                        <th fieldname="OLD_PRICE" refer="amountFormat" align="right">原销售价格</th>
                        <th fieldname="NEW_PRICE" refer="amountFormat" align="right">调整后价格</th>
                        <th fieldname="SPREAD" refer="amountFormat" align="right">价差(元)</th>
                        <th fieldname="DELIVERY_COUNT" colwidth="60">发货数量</th>
                        <th fieldname="CHANEL_COUNT" refer="showLink" colwidth="65">出渠道数量</th>
                        <th fieldname="SALE_COUNT" colwidth="65">出终端数量</th>
                        <th fieldname="FILL_COUNT" colwidth="60">应补数量</th>
                        <th fieldname="FILL_AMOUNT" refer="amountFormat" align="right">应补金额(元)</th>
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
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/financeMng/searchInfo/PartSpreadFillQueryMngAction/searchOrder.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
         });

         // 导出按钮绑定
         $("#btn-export-index").click(function(){
             var $f = $("#fm-condition");
             if (submitForm($f) == false) {
                 return false;
             }
             var sCondition = {};
             sCondition = $f.combined() || {};
             $("#data").val(sCondition);
             var url = encodeURI("<%=request.getContextPath()%>/part/financeMng/searchInfo/PartSpreadFillQueryMngAction/download.do");
             window.location.href = url;
             $("#exportFm").attr("action",url);
             $("#exportFm").submit();
         });

    });

    // 订单查询
    function searchOrder() {
        var $f = $("#fm-condition");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
    }

    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("FILL_ID")+") class='op'>"+$row.attr("CHANEL_COUNT")+"</a>";
    }
    function openDetail(fillId){
    	var options = {max:false,width:1024,height:480,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/spreadFillDetail.jsp?&fillId="+fillId, "spreadFillDetail", "价差应补明细", options,true);
    }

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
</script>