<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>销售出库</title>
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
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/SaleOutMngAction/searchSaleOrder.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    // 查看订单详细信息
    function toUpdate(rowobj) {
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/outstorage/saleOutEdit.jsp", "saleOutEdit", "销售出库明细", options);
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 仓储管理  &gt; 出库管理   &gt; 销售出库</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="orderNo-index" name="orderNo-index" datasource="A.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>订单类型：</label></td>
                        <td>
                            <select type="text" id="orderType-index" name="orderType-index" kind="dic" src="DDLX" filtercode="<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>" datasource="A.ORDER_TYPE" datatype="1,is_null,30">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>提报单位：</label></td>
                        <td><input type="text" id="orgName-index" name="orgName-index" datasource="A.ORG_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="A.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="A.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
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
                            <th fieldname="ORDER_NO" ordertype='local' class="desc" colwidth="120">订单编号</th>
                            <th fieldname="ORDER_TYPE" >订单类型</th>
                            <th fieldname="ORG_NAME" >提报单位</th>
                            <th fieldname="APPLY_DATE">提报时间</th>
                            <th fieldname="ORDER_AMOUNT" refer="formatAmount">订单金额</th>
                            <th fieldname="SHOULD_AMOUNT" refer="formatAmount">应发金额</th>
                            <th colwidth="80" type="link" title="[销售出库]"  action="toUpdate" >操作</th>
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