<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>打印发料单</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/searchSaleOrder.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	var $f = $("#fm-searchSaleOrder");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-saleOrderList");
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchSaleOrder");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-saleOrderList");
            });
        });
        //列表打印链接
        function doPrint(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printIssueEdit.jsp", "editWin", "打印发料单", diaEditOptions);
        }
        function showLink(obj)
        {
        	var $row=$(obj).parent();
            return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
        }
        function openDetail(ORDER_ID){
        	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 发料管理 &gt; 打印发料单</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchSaleOrder">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchSaleOrder">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>订单编号：</label></td>
                            <td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="A.ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>订单类型：</label></td>
                            <td>
                                <select class="combox" id="ORDER_TYPE" name="ORDER_TYPE" kind="dic" src="DDLX" datasource="A.ORDER_TYPE" datatype="1,is_null,10">
                                    <option value="-1" selected>--</option>
                                </select>
                            </td>
                            <td><label>提报日期：</label></td>
                            <td>
                                <input  type="text" name="APPLY_START_DATE" id="APPLY_START_DATE" style="width:75px;" class="Wdate" operation=">=" group="APPLY_START_DATE,APPLY_END_DATE" datasource="A.APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="APPLY_END_DATE" id="APPLY_END_DATE" style="width:75px;margin-left:-28px;;" class="Wdate" operation="<=" group="APPLY_START_DATE,APPLY_END_DATE" datasource="A.APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>提报单位代码：</label></td>
                            <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="A.ORG_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>提报单位名称：</label></td>
                            <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="A.ORG_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>审核日期：</label></td>
                            <td>
                                <input  type="text" name="CHECK_START_DATE" id="CHECK_START_DATE" style="width:75px;" class="Wdate" operation=">=" group="CHECK_START_DATE,CHECK_END_DATE" datasource="B.CHECK_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="CHECK_END_DATE" id="CHECK_END_DATE" style="width:75px;margin-left:-28px;;" class="Wdate" operation="<=" group="CHECK_START_DATE,CHECK_END_DATE" datasource="B.CHECK_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>审核员：</label></td>
                            <td><input type="text" id="CHECK_USER_SV" name="CHECK_USER_SV" datasource="C.PERSON_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-search">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-saleOrderList">
                <table style="display:none;width:100%;" id="tab-saleOrderList" name="tablist" ref="div-saleOrderList"
                       refQuery="tab-searchSaleOrder">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ORDER_NO" colwidth="135" refer="showLink">订单编号 </th>
                        <th fieldname="ORDER_TYPE" >订单类型</th>
                        <th fieldname="ORG_NAME">提报单位</th>
                        <th fieldname="APPLY_DATE" colwidth="120">提报时间</th>
                        <th fieldname="CHECK_USER" colwidth="80">审核员</th>
                        <th fieldname="CHECK_DATE" colwidth="120">审核时间</th>
                        <th fieldname="SHIP_STATUS">订单发运状态</th>
                        <th colwidth="70" type="link" title="[打印发料单]"  action="doPrint" >操作</th>
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