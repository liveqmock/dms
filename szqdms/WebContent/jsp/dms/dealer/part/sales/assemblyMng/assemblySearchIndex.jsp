<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>总成附件确认</title>
<script type="text/javascript">
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblySearchMngAction/searchForecast.ajax";
    $(function(){

        // 查询按钮响应方法
        $("#btn-search").click(function(){
            // 查询预测方法
            forecastReportIndex_search();
        });

    });

    // 查询预测方法
    function forecastReportIndex_search() {
        var $f = $("#fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-grid-index");
    }

    // 总成附件确认明细方法
    function doDetail(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/assemblyMng/assemblyDetail.jsp", "forecastDetail", "总成附件确认明细", options);
    }

    // 操作列的回调函数
    function showBtn(obj){
        var $tr = $(obj).parent();
        var forcastStatus = $tr.find("td").eq(4).attr("code");
        if(forcastStatus != <%=DicConstant.ZCFJQRZT_01 %>){
            obj.html("<A class=op title=[明细] onclick=doDetail(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>[明细]</A>");
        }
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询&gt;订单相关&gt;总成附件申请查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                        <td><label>创建时间：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq1,in-jscjrq1"  id="in-kscjrq1"  name="in-kscjrq1" operation=">="  dataSource="CREATE_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq1,in-jscjrq1"  id="in-jscjrq1"  name="in-jscjrq1" operation="<=" dataSource="CREATE_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                        <td><label>提交时间：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq2,in-jscjrq2"  id="in-kscjrq2"  name="in-kscjrq2" operation=">="  dataSource="APPLY_TIME" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq2,in-jscjrq2"  id="in-jscjrq2"  name="in-jscjrq2" operation="<=" dataSource="APPLY_TIME" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                        <td><label>状态：</label></td>
                        <td><select type="text" id="configStatus" name="configStatus" datasource="CONFIG_STATUS" datatype="1,is_null,100" class="combox" kind="dic" src=<%=DicConstant.ZCFJQRZT %>>
                                <option value="-1">--</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
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
                        <th fieldname="CREATE_TIME">创建时间</th>
                        <th fieldname="CREATE_USER">提报人</th>
                        <th fieldname="CONFIG_STATUS">状态</th>
                        <th fieldname="APPLY_TIME">提报时间</th>
                        <th colwidth="140" type="link" title="[明细]"  action="doDetail" >操作</th>
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