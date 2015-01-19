<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>实销单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：渠道信息查询  &gt; 仓储相关    &gt;  实销单查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-SaleOutSearch">
            <!-- 定义隐藏域条件 -->
            <input type="hidden" id="salestauts" name="salestauts" datasource="SALE_STATUS" datatype="1,is_digit_letter,30" operation="like" />
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-salesSearch">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>实销单号：</label></td>
                        <td><input type="text" id="out-saleNo" name="out-saleNo" datasource="T.SALE_NO" datatype="1,is_null,30" operation="like" /></td>
                        <td><label>客户名称：</label></td>
                        <td><input type="text" id="out-custName" name="out-custName" datasource="T.CUSTOMER_NAME" datatype="1,is_null,30" operation="like" /></td>
                    </tr>
                    <tr>
                    	<td><label>配件代码：</label></td>
                        <td><input type="text" id="out-partName" name="out-partName" datasource="PART_CODE" datatype="1,is_null,300" action="show" operation="like" /></td>
                    	<td><label>实销类型：</label></td>
                        <td>
                            <input type="text" id="OUT_TYPE" name="OUT_TYPE" datasource="T.OUT_TYPE" kind="dic" src="SXLX" datatype="1,is_null,30"/>
                        </td>
                    </tr>
                    <tr>
                    	<td><label>实销日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.SALE_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.SALE_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_SaleOutlb" >
            <table style="display:none;width:100%;" id="tab-saleOut" name="tablist" ref="page_SaleOutlb" refQuery="fm-SaleOutSearch" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none"></th>
                            <th fieldname="SALE_NO" colwidth="160" refer="showLink">实销单号</th>
                            <th fieldname="CUSTOMER_NAME">客户名称</th>
                            <th fieldname="LINK_PHONE">联系电话</th>
                            <th fieldname="LINK_ADDR">联系地址</th>
                            <th fieldname="SALE_STATUS" colwidth="60">实销状态</th>
                            <th fieldname="AMOUNT" refer="formatAmount" align="right" colwidth="80">实销金额</th>
                            <th fieldname="OUT_TYPE" colwidth="60">实销类型</th>
                            <th fieldname="SALE_DATE" colwidth="100">实销日期</th>
                        </tr>
                    </thead>
                        <tbody>
                        </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data" datasource="data" />
</form>
<script type="text/javascript">
    //变量定义
    //初始化方法
    var url = "<%=request.getContextPath()%>/part/storageMng/search/DealerRaleSaleSearchMngAction";
    var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $(function(){
        //查询方法
        $("#btn-search").bind("click",function(event){
            // 查询实销单
            searchRealSalesOut();
        });
        
        // 导出
        $("#btn-export").click(function(){
        	var $f = $("#fm-SaleOutSearch");
        	if (submitForm($f) == false) return false;
        	var sCondition = $f.combined() || {};
        	$("#params").val(sCondition);
        	$("#exportFm").attr("action","<%=request.getContextPath()%>/part/storageMng/search/DealerRaleSaleSearchMngAction/exportExcel.do");
        	$("#exportFm").submit();
        })

    });

    // 查询实销单
    function searchRealSalesOut() {

        var $f =$("#fm-SaleOutSearch");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/realSalesearch.ajax";
        doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-saleOut");
    }

    function doDetail(rowobj) { 
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/storage/outstorage/realSaleDetail.jsp", "orderCheckIn", "实销单明细", diaOptions);
    }


    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("SALE_ID")+") class='op'>"+$row.attr("SALE_NO")+"</a>";
    }
    function openDetail(SALE_ID){
    	var options = {max:true,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/realSaleOrderInfoDetail.jsp?SALE_ID="+SALE_ID, "saleOrderDetail", "实销单明细", options,true);
    }
    
</script>
</body>
</html>