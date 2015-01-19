<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预测汇总上报</title>
<script type="text/javascript">
    // URL
    var indexSearchUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/searchForecast.ajax";
    // 初始化方法
     $(function(){

         // 汇总提报日验证
         //totalCheck();
         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 汇总调整查询
             forecastCarTotalReport();
         });
        // 新增汇总
        $("#btn-totalEdit").click(function(){
        	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
             $.pdialog.open(webApps+"/jsp/dms/oem/part/plan/forecastMng/forecastCarTotalReportSearch.jsp", "ychzdz", "新增汇总", options);
        });
    });

    // 汇总调整查询
    function forecastCarTotalReport() {
    	var $f = $("#fm-condition");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, indexSearchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
    }

    // 明细
    function doDetail(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/plan/forecastMng/forecastDetail.jsp", "forecastDetail", "预测明细调整", options);
    }

    // 删除操作
    function doDelete(rowobj){
        var deleteUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastTotalMngAction/deleteForecast.ajax";
        $row = $(rowobj);
        var url = deleteUrl + "?forcastId=" + $(rowobj).attr("FORCAST_ID");
        sendPost(url, "", "", deleteCallBack, "true");
    }

    // 删除回调方法
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

    // 操作列的回调函数
    function showBtn(obj){
        var $tr = $(obj).parent();
        var forcastStatus = $tr.find("td").eq(3).attr("code");
        if(forcastStatus != <%=DicConstant.PJYCZT_01 %>){
            obj.html("<A class=op title=[明细] onclick=doDetail(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>[明细]</A>");
        }
    }

    // 汇总提报日验证
    function totalCheck(){
        var doCheckReportUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastTotalMngAction/searchByReportDay.ajax";
        sendPost(doCheckReportUrl, "", "", totalCheckCallBack, "true");
    }

    // 汇总提报日验证回调方法
    function totalCheckCallBack(res) {
        var rows = res.getElementsByTagName("ROW");
        // 读取XML中的FLAG属性(FLAG:true有重复数据;)
        var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
        if (flag == "true") {
            $("div subBar li").eq(1).hide();
            return false;
        }
        $.pdialog.close(dialog);
        return false;
    }

    // 修改方法
    function doUpdate(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/plan/forecastMng/forecastCarTotalReportEdit.jsp?action=2", "totalEdit", "预测上报编辑", options);
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：预测管理&gt;预测管理&gt;预测汇总上报</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>预测月份：</label></td>
                        <td><input type="text" id="forcastMonth" name="forcastMonth" datatype="1,is_null,7" datasource="FORCAST_MONTH" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM'})" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div>
                        </li>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-totalEdit">新增汇总</button></div></div>
                        </li>
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
                        <th fieldname="FORCAST_STATUS">状态</th>
                        <th fieldname="ORG_ID" style="display:none"></th>
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