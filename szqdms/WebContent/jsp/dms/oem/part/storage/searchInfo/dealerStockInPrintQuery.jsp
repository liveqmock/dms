<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>入库单打印</title>
<script type="text/javascript">
    //初始化方法
    $(function(){
        // 查询按钮绑定
        $("#btn-search-index").bind("click",function(event){
            // 查询销售订单方法
            searchSaleOrder();
        });
    });

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询销售订单
    function searchSaleOrder() {
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DealerOutPrintMngAction/printInSearch.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }
    function doPrint(rowobj) {
        $row = $(rowobj);
        /*            $("td input[type=radio]", $(rowobj)).attr("checked", true);
       window.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/inBillPrintEdit.jsp"); */
       var queryUrl = "";
       var type= $row.attr("ORDER_TYPE");
       if(parseInt(type)==1){
       		queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DealerOutPrintMngAction/printSaleInPdf.do?ORDER_ID="+$row.attr("IN_ID");
       }else if(parseInt(type)==2){
       		queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DealerOutPrintMngAction/printSaleRetPdf.do?ORDER_ID="+$row.attr("IN_ID");
       }else{
    	   queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DealerOutPrintMngAction/printRealSaleRetPdf.do?ORDER_ID="+$row.attr("IN_ID");
       }
       window.open(queryUrl);
   }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 仓储相关   &gt; 入库单查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="IN_NO" name="IN_NO" datasource="IN_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                    	<td><label>发送方：</label></td>
                        <td><input type="text" id="SENDER" name="SENDER" datasource="SENDER" datatype="1,is_digit_letter,30" operation="like" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index" >查&nbsp;&nbsp;询</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index" >
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-index" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="IN_NO" colwidth="120">订单编号</th>
                            <th fieldname="IN_TYPE" >入库类型</th>
                            <th fieldname="SENDER" >发送方</th>
                            <th fieldname="AMOUNT" refer="formatAmount">订单金额</th>
                            <th colwidth="80" type="link" title="[打印]"action="doPrint">操作</th>
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