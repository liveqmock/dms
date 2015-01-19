<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
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
    <title>入库单打印</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/searchInBill.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	var $f = $("#fm-searchInBill");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-inBillList");
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchInBill");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-inBillList");
            });
        });
        var $row;
        //列表打印链接
        function doPrint(rowobj) {
            $row = $(rowobj);
            var queryUrl = "";
            var type= $row.attr("IN_TYPE");
            if(type == <%=DicConstant.RKLX_01%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printInPdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=1";
            }else if(type == <%=DicConstant.RKLX_02%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printMovePdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=1";
            }else if(type == <%=DicConstant.RKLX_03%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printRetPdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=1";
            }else{
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printOtherPdf.do?IN_ID="+$row.attr("IN_ID")+"&flag=1";
            }
            window.open(queryUrl);
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 入库管理 &gt; 入库单打印</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchInBill">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchInBill">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>入库单号：</label></td>
                            <td><input type="text" id="IN_NO" name="IN_NO" datasource="IN_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>入库仓库：</label></td>
                            <td>
                                <input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> ORDER BY WAREHOUSE_CODE" datatype="1,is_null,30"/>
                            </td>
                            <td><label>入库类型：</label></td>
                            <td>
                                <select type="text" id="IN_TYPE" name="IN_TYPE" datasource="IN_TYPE" kind="dic" src="RKLX" datatype="1,is_null,30">
                                	<option value="-1">--</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>入库日期：</label></td>
                            <td>
                                <input  type="text" name="START_IN_DATE" id="START_IN_DATE" style="width:75px;" class="Wdate" operation=">=" group="START_IN_DATE,END_IN_DATE" datasource="IN_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="END_IN_DATE" id="END_IN_DATE" style="width:75px;margin-left:-28px;;" class="Wdate" operation="<=" group="START_IN_DATE,END_IN_DATE" datasource="IN_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
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
            <div id="div-inBillList">
                <table style="display:none;width:100%;" id="tab-inBillList" name="tablist" ref="div-inBillList"
                       refQuery="tab-searchInBill">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="IN_NO" colwidth="150">入库单号</th>
                        <th fieldname="IN_TYPE" >入库类型</th>
                        <th fieldname="WAREHOUSE_NAME">入库仓库</th>
                        <th fieldname="ORDER_NO" colwidth="135" style="display:none">原单编号</th>
                        <th fieldname="OUT_UNIT">发货单位</th>
                        <th fieldname="OUT_WAREHOUSE">出库仓库</th>
                        <%--<th fieldname="PURCHASE_AMOUNT">采购金额</th>--%>
                        <%--<th fieldname="PLAN_AMOUNT">计划金额</th>--%>
                        <th fieldname="IN_DATE" colwidth="150">入库日期</th>
                        <th fieldname="AMOUNT">订单金额</th>
                        <th colwidth="40" type="link" title="[打印]"  action="doPrint" >操作</th>
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