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
    <title>出库单打印</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/searchOutBill.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	var $f = $("#fm-searchOutBill");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchOutBill");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
            });
        });
        /* var $row;
        //列表打印链接
        function doPrint(rowobj) {
            $row = $(rowobj);
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            window.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/outBillPrintEdit.jsp");
        } */
        var $row;
        //列表打印链接
        function doPrint(rowobj) {
             $row = $(rowobj);
             /*            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            window.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/inBillPrintEdit.jsp"); */
            var queryUrl = "";
            var type= $row.attr("OUT_TYPE");
            if(type == <%=DicConstant.CKLX_01%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printSaleInPdf.do?OUT_ID="+$row.attr("OUT_ID")+"&flag=1";
            }else if(type == <%=DicConstant.CKLX_02%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printDirSaleInPdf.do?OUT_ID="+$row.attr("OUT_ID")+"&flag=1";
            }else if(type == <%=DicConstant.CKLX_03%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printMovePdf.do?OUT_ID="+$row.attr("OUT_ID")+"&flag=1";
            }else if(type == <%=DicConstant.CKLX_04%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printPchRetPdf.do?OUT_ID="+$row.attr("OUT_ID")+"&flag=1";
            }else if(type == <%=DicConstant.CKLX_05%>){
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printOhterPdf.do?OUT_ID="+$row.attr("OUT_ID")+"&flag=1";
            }else{
            	queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printDirSaleInPdf.do?OUT_ID="+$row.attr("OUT_ID")+"&flag=1";
            }
            window.open(queryUrl);
        }
/*         function showLink(obj)
        {
        	var $row=$(obj).parent();
       		return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
        }
        function openDetail(ORDER_ID){
        	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
        } */
     // 入库单号超链接
        function showNoInfo($cell){
        	$tr = $cell.parent();
        	return "<a style='color:red' href='javascript:openDetailPage(\""+$tr.attr("OUT_ID")+"\")'>"+$tr.attr("OUT_NO")+"</a>";
        }

        // 打开详细页面
        function openDetailPage(outId){
        	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/stockOutQueryForDetails.jsp?outId="+outId, "forDetailsPage", "出库单详细信息", options);
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 出库管理 &gt; 出库单打印</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchOutBill">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchOutBill">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>出库单号：</label></td>
                            <td><input type="text" id="OUT_NO" name="OUT_NO" datasource="OUT_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>出库仓库：</label></td>
                            <td>
                                <input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" kind="dic" dicwidth="260" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> ORDER BY WAREHOUSE_CODE" datatype="1,is_null,30"/>
                            </td>
                            <td><label>收货仓库：</label></td>
                            <td><input type="text" id="RECV_UNIT" name="RECV_UNIT" datasource="RECV_UNIT" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                        	<td><label>出库类型：</label></td>
                            <td>
                                <select type="text" id="OUT_TYPE" name="OUT_TYPE" datasource="OUT_TYPE" kind="dic" src="CKLX" datatype="1,is_null,30">
                                	<option value="-1">--</option>
                                </select>
                            </td>
                            <td><label>出库日期：</label></td>
                            <td>
                                <input  type="text" name="START_OUT_DATE" id="START_OUT_DATE" style="width:75px;" class="Wdate" operation=">=" group="START_OUT_DATE,END_OUT_DATE" datasource="OUT_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="END_OUT_DATE" id="END_OUT_DATE" style="width:75px;margin-left:-28px;;" class="Wdate" operation="<=" group="START_OUT_DATE,END_OUT_DATE" datasource="OUT_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                        	<td><label>订单号：</label></td>
                            <td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                        	<td><label>订单生成日期：</label></td>
                            <td>
                                <input  type="text" name="APPLY_DATE_B" id="APPLY_DATE_B" style="width:75px;" class="Wdate" operation=">=" group="APPLY_DATE_B,APPLY_DATE_E" datasource="APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="APPLY_DATE_E" id="APPLY_DATE_E" style="width:75px;margin-left:-28px;;" class="Wdate" operation="<=" group="APPLY_DATE_B,APPLY_DATE_E" datasource="APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
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
            <div id="div-outBillList">
                <table style="display:none;width:100%;" id="tab-outBillList" name="tablist" ref="div-outBillList"
                       refQuery="tab-searchOutBill">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="OUT_NO" colwidth="150" refer = showNoInfo>出库单号</th>
                        <th fieldname="OUT_TYPE" >出库类型</th>
                        <th fieldname="WAREHOUSE_NAME">出库仓库</th>
                        <th fieldname="ORDER_NO" colwidth="135">原单编号</th>
                        <th fieldname="RECV_UNIT">收货仓库</th>
                        <!-- <th fieldname="RECV_WAREHOUSE">出库仓库</th> -->
                        <%--<th fieldname="PURCHASE_AMOUNT">采购金额</th>--%>
                        <%--<th fieldname="PLAN_AMOUNT">计划金额</th>--%>
                        <th fieldname="OUT_DATE" colwidth="130">出库时间</th>
                        <th fieldname="CHECK_USER" >制单人</th>
                        <th fieldname="CREATE_TIME" colwidth="70">制单日期</th>
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