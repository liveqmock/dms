<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    String yearStr1 = yearFormat.format(Pub.getCurrentDate());
    String monthStr1 = monthFormat.format(Pub.getCurrentDate());
    String yearStr2 = "";
    String monthStr2 = "";
    if ("12".equals(monthStr1)) {
        Integer intYear = Integer.valueOf(yearStr1) + 1;
        // 年
        yearStr2 = String.valueOf(intYear);
        // 月
        monthStr2 = "01";
    } else {
       // 年
       yearStr2 = yearStr1;
       Integer intMonth = Integer.valueOf(monthStr1) + 1;
       // 月
       monthStr2 = String.valueOf(intMonth);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript">
    // URL
    var searchDealerUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/searchForecastByDealer.ajax";
    // 初始化方法
     $(function(){
         // 查询按钮绑定
        $("#dia-search").click(function(){
            var reportCheckUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/reportCheck.ajax";
            reportCheckUrl = reportCheckUrl + "?forcastMonth="+$("#search-forcastMonth").val();
            sendPost(reportCheckUrl, "", "", reportCheckCallBack, "false");
        });

        // 汇总按钮绑定
        $("#dia-doTotal").click(function(){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps+"/jsp/dms/oem/part/plan/forecastMng/forecastCarTotalReportEdit.jsp", "totalEdit", "预测汇总调整", options);
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    // 提报状态验证回调函数
    function reportCheckCallBack(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true该月已提报;)
            if(rows.length>0){
            	alertMsg.info("该月已汇总!");
                return false;
            }else{
            	$("#ophzBtn").show();
                var $f = $("#fm-condition-search");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchDealerUrl, "dia-search", 1, sCondition, "tab-grid-search");
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
    }

    // 修改
    function doUpdateSearch(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/plan/forecastMng/forecastCarTotalReportDetail.jsp", "totalDetail", "预测明细调整", options);
    }
</script>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition-search" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>预测月份：</label></td>
                        <td>
                            <select id="search-forcastMonth" datatype="1,is_null,10" dataSource="PBF.FORCAST_MONTH" operation="=">
                                <option value=<%=yearStr1+"-"+monthStr1%>><%=yearStr1+"-"+monthStr1%></option>
                                <option value=<%=yearStr2+"-"+monthStr2%>><%=yearStr2+"-"+monthStr2%></option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-search">渠道商汇总查询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="search_page_grid">
            <table style="display:none;width:100%;" id="tab-grid-search" name="tablist" ref="search_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="CODE">服务商代码</th>
                        <th fieldname="ONAME">服务商名称</th>
                        <th fieldname="FORCAST_MONTH">预测月份</th>
                        <th colwidth="85" type="link" title="[明细]"  action="doUpdateSearch">操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <div class="formBar" id="ophzBtn" style="display:none" >
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-doTotal">汇&nbsp;&nbsp;总</button></div></div></li>
        </ul>
    </div>
    </div>
</div>