<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单打印</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 销售相关&gt; 订单打印</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                    	<td><label>渠道商：</label></td>
                        <td>
                        	<input type="text" id="dia-orgName" name="dia-orgName" datatype="0,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
							<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="T.ORG_ID" operation="="/>
                        </td>
                        <td><label>关单日期：</label></td>
                        <td>
                            <input type="text" group="startDate3,endDate3"  id="startDate3" kind="date" name="startDate3" style="width:70px;" datasource="T.CLOSE_DATE" datatype="0,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate3,endDate3"  id="endDate3" kind="date" name="endDate3" style="width:70px;margin-left:-30px;" datasource="T.CLOSE_DATE" datatype="0,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-print">打印</button></div></div></li>
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
                        <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">订单金额(元)</th>
                        <th fieldname="REAL_AMOUNT"  refer="amountFormat" align="right">实发金额(元)</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
 	<form id="searchAmountFm" method="post" style="display:none">
		<input type="hidden" id="paramsAmount" name="data" datasource="data" />
	</form>
</body>
</html>
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/searchOrgPrint.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
         });
         $("#btn-print").click(function(){
        	 var orgId = $("#orgId").val();
        	 var BEGIN_DATE = $("#startDate3").val();
        	 var END_DATE = $("#endDate3").val();
             var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/printOrderPartPdf.do?ORG_ID="+orgId+"&BEGIN_DATE="+BEGIN_DATE+"&END_DATE="+END_DATE;
             window.open(queryUrl);
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
        $("#paramsAmount").val(sCondition);		// 保存查询条件值至查询汇总的Form参数中
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
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

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

    //列表审核链接
/*     function doPrint(rowobj){
    	$row = $(rowobj);
    	$("#orderId").val($(rowobj).attr("ORDER_ID"));
    	$("#dia-ORDER_NO").val($(rowobj).attr("ORDER_NO"));
    	$("#dia-ORG_NAME").val($(rowobj).attr("ORG_NAME"));
    	$("#dia-ORDER_AMOUNT").val($(rowobj).attr("ORDER_AMOUNT"));
    	$("#dia-APPLY_DATE").val($(rowobj).attr("APPLY_DATE_sv"));
    	window.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printOrderDtl.jsp");
    } */
    
    // 操作列的回调函数
    function showBtn(obj){
        var $tr = $(obj).parent();
        var forcastStatus = $tr.find("td").eq(11).attr("code");
        if(forcastStatus == <%=DicConstant.DDZT_06 %>){
        	obj.html("<A class=op title=[打印] onclick=doPrint1(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>[打印]</A>");
        }
    }

    function doPrint(rowobj) {
    	$row = $(rowobj);
        var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/printPdf.do?ORDER_ID="+$(rowobj).attr("ORDER_ID");
        window.open(queryUrl);
    }
    function doPrint1(rowobj) {
    	$row = $(rowobj);
        var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/printProofPdf.do?ORDER_ID="+$(rowobj).attr("ORDER_ID");
        window.open(queryUrl);
    }
    
    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }

</script>