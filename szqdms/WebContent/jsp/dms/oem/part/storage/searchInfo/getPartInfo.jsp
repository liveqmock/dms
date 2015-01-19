<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgType = user.getOrgDept().getOrgType();
%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="dia-fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="di_ycpjTable">
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="PART_CODE" datatype="1,is_null,100" operation="like" /></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="dia-PART_NAME" name="dia-PART_CODE" datasource="PART_NAME" datatype="1,is_null,100" operation="like" /></td>
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
                        <th type="single" name="XH" fieldname="PART_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
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
    var searchUrl = "<%=request.getContextPath()%>/part/storageMng/search/PartStockInOrOutSearchMngAction/searchPart.ajax";
    // 弹出窗体
    var dialog = $("body").data("getPartInfo");
    // 初始化函数
    $(function() {
        $("#di_searchPart").click(function() {
            // 入库单查询
            searchInStock();
        });
        $("#di_searchPart").trigger("click");
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
        $("#PART_ID").val(selectedRows[0].attr("PART_ID"));
        $("#PART_CODE").val(selectedRows[0].attr("PART_CODE"));
        $("#PART_NAME").val(selectedRows[0].attr("PART_NAME"));
        // 关闭窗口
        $.pdialog.close(dialog);
        return false;
    }
</script>