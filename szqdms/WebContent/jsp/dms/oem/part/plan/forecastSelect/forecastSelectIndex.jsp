<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预测查询</title>
<script type="text/javascript">
    var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtbaSelectAction/searchForecast.ajax";
    $(function(){

        // 查询按钮响应方法
        $("#btn-search").click(function(){
            // 查询预测方法
            forecastReportIndex_search();
        });

        // 新增按钮响应方法
        $("#btn-add").click(function(){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps+"/jsp/dms/dealer/part/plan/forecastMng/forecastReportEdit.jsp?action=1", "forecastReport", "预测上报新增", options);
        });

    });

    // 查询预测方法
    function forecastReportIndex_search() {
        var $f = $("#fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-grid-index");
    }

    // 删除方法
    function doDelete(rowobj){
        var deleteUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/deleteForecast.ajax";
        $row = $(rowobj);
        var url = deleteUrl + "?forcastId=" + $(rowobj).attr("FORCAST_ID");
        sendPost(url, "", "", deleteCallBack, "true");
    }

    //删除回调方法
    function deleteCallBack(res) {
        try {
            if ($row)
                $("#tab-grid-index").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 修改方法
    function doUpdate(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/plan/forecastMng/forecastReportEdit.jsp?action=2", "forecastReport", "预测上报编辑", options);
    }

    // 预测明细方法
    function doDetail(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/plan/forecastSelect/forecastSelectDetail.jsp", "forecastDetail", "预测明细", options);
    }

    // 操作列的回调函数
    function showBtn(obj){
        var $tr = $(obj).parent();
        var forcastStatus = $tr.find("td").eq(3).attr("code");
        if(forcastStatus != <%=DicConstant.PJYCZT_01 %>){
        obj.html("<A class=op title=[明细] onclick=doDetail(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>[明细]</A>");
        }
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：计划相关&gt;预测查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>预测月份：</label></td>
                        <td><input type="text" id="forcastMonth" name="forcastMonth" datasource="FORCAST_MONTH" datatype="1,is_null,100" value=""  onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" /></td>
<!--                        <td><label>状态：</label></td>-->
<!--                        <td><select type="text" id="forcastStatus" name="forcastStatus" datasource="FORCAST_STATUS" datatype="1,is_null,100" value="" class="combox" kind="dic" src=<%=DicConstant.PJYCZT %>>-->
<!--                                <option value="">--</option>-->
<!--                            </select>-->
<!--                        </td>-->
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
                        </li>
<!--                        <li>-->
<!--                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div>-->
<!--                        </li>-->
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
                        <th fieldname="FORCAST_MONTH">预测月份</th>
<!--                        <th fieldname="FORCAST_STATUS">状态</th>-->
                        <th colwidth="85" type="link" refer="showBtn" title="[编辑]|[删除]"  action="doUpdate|doDelete">操作</th>
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