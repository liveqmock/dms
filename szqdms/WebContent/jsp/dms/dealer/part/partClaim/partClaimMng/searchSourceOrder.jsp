<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgType = user.getOrgDept().getOrgType();
    String partId = request.getParameter("partId");
%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="dia-fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="di_ycpjTable">
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="dia-orderNo-Search" name="dia-orderNo-Search" datasource="ORDER_NO" datatype="1,is_null,100" operation="like" /></td>
                        <td><label>订单类型：</label></td>
                        <td>
                            <select type="text" id="orderType" name="orderType" kind="dic" src="DDLX" datasource="ORDER_TYPE" filtercode="" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="dia_page_grid">
            <table style="display:none;width:100%;" id="dia-tab-grid" multivals="div-selectedPart" name="tablist" ref="dia_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" fieldname="ORDER_ID"></th>
                        <th fieldname="ORDER_NO">订单编号</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    var orgType = '<%=orgType%>';
    var partId = '<%=partId%>';
    var searchUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclCheckMngAction/searchSourceOrder.ajax?PART_ID="+partId;
    // 弹出窗体
    var dialog = $("body").data("searchSourceOrder");
    // 初始化函数
    $(function() {

        // 入库单查询
        searchInStock();
        if(orgType=="<%=DicConstant.ZZLB_09%>"){
            $("#orderType").attr("filtercode","<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>");
        }
        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            // 入库单查询
            searchInStock();
        });
    });

    // 入库单查询
    function searchInStock() {
        var $f = $("#dia-fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        // 入库单查询
        doFormSubmit($f, searchUrl, "di_searchPart", 1, sCondition, "dia-tab-grid");
    }
    // 单选按钮回调函数
    function doRadio(res){
        // getSelectedRows():获取列表选中行对象，返回选中行数组
        var selectedRows = $("#dia-tab-grid").getSelectedRows();
        // 入库单查询
        // 入库单ID
        $("#sourceInId-Edit").val(selectedRows[0].attr("ORDER_ID"));
        orderId = selectedRows[0].attr("ORDER_ID");
        // 入库单号
        $("#sourceInNo-Edit").val(selectedRows[0].attr("ORDER_NO"));
        $.pdialog.close(dialog);
        return false;
    }
</script>