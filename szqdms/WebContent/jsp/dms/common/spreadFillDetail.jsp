<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String fillId = request.getParameter("fillId");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件价差应补查询明细</title>
<script type="text/javascript">
	var fillId='<%=fillId%>';
	var searchUrl = "<%=request.getContextPath()%>/part/financeMng/searchInfo/PartSpreadFillQueryMngAction/searchOrderDtl.ajax";
    // 初始化方法
     $(function(){
    	$("#fillId").val(fillId);
		searchOrder();
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
    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
        	<input type="hidden" id="fillId" name="fillId" datasource="FILL_ID" operation="=" value=""/>
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                	
                </table>
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
                        <th fieldname="PRICE_DATE" colwidth="130">调价时间</th>
                        <th fieldname="OLD_PRICE" refer="amountFormat" align="right">原销售价格</th>
                        <th fieldname="NEW_PRICE" refer="amountFormat" align="right">调整后价格</th>
                        <th fieldname="SPREAD" refer="amountFormat" align="right">价差(元)</th>
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