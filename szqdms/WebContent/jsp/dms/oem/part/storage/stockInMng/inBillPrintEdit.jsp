<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String contextPath = request.getContextPath();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    String today = sdf.format(new Date());
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>打印入库单</title>
    <script src="<%=contextPath %>/lib/jquery/jquery.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/jquery/jquery.cookie.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/jquery/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.core.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.barDrag.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.drag.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.ui.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.tab.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.resize.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.dialog.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.dialogDrag.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.sortDrag.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.cssTable.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.stable.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.ajax.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.pagination.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.alertMsg.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.contextmenu.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.effects.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.panel.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.checkbox.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.history.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/core/sci.combox.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/HashMap.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/mine.rightmenu.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/mine.info.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/mine.win.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/mine.dic.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/default.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/validate/validate.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/mine.table.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/mine.dialog.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=contextPath %>/js/util.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/scisite/mine.core.js" type="text/javascript"></script>
    <script src="<%=contextPath %>/lib/plugins/scisite/mine.html.js" type="text/javascript"></script>
    <script type="text/javascript">

    $(function(){
        $('#title').text('陕重汽销司销售服务部'+opener.$row.attr('IN_TYPE_SV')+(opener.$row.attr('IN_TYPE')==<%=DicConstant.RKLX_03%>?'入库':'')+'单');
        var IN_TYPE = opener.$row.attr('IN_TYPE');//入库类型
        if(IN_TYPE == <%=DicConstant.RKLX_01%>){//采购入库单
            $('#info').append('<tr><td>供应商名称: '+opener.$row.attr('OUT_UNIT')+'</td><td style="text-align: right">编号: '+opener.$row.attr('IN_NO')+'</td></tr><tr><td rowspan="2">打印日期: <%=today%></td></tr>');
            $('#partList').find('thead').append('<tr><td rowspan="2">序号</td><td rowspan="2">配件代码</td><td rowspan="2">配件名称</td><td rowspan="2">参图号</td><td rowspan="2">单位</td><td rowspan="2">数量</td><td colspan="2">采购</td><td colspan="2">入库</td></tr><tr><td>采购价</td><td>金额</td><td>计划价</td><td>金额</td></tr>');
        }else if(IN_TYPE == <%=DicConstant.RKLX_02%>){//移库入库
            $('#info').append('<tr><td>出库单位: '+opener.$row.attr('OUT_UNIT')+'</td><td style="text-align: right">编号: '+opener.$row.attr('IN_NO')+'</td></tr><tr><td rowspan="2">打印日期: <%=today%></td></tr>');
            $('#partList').find('thead').append('<tr><td>序号</td><td>配件代码</td><td>配件名称</td><td>参图号</td><td>单位</td><td>数量</td><td>计划价</td><td>金额</td></tr>');
        }else if(IN_TYPE == <%=DicConstant.RKLX_03%>){//销售退件
            $('#info').append('<tr><td>退件单位: '+opener.$row.attr('OUT_UNIT')+'</td><td style="text-align: right">编号: '+opener.$row.attr('IN_NO')+'</td></tr><tr><td rowspan="2">打印日期: <%=today%></td></tr>');
            $('#partList').find('thead').append('<tr><td rowspan="2">序号</td><td rowspan="2">配件代码</td><td rowspan="2">配件名称</td><td rowspan="2">参图号</td><td rowspan="2">单位</td><td rowspan="2">数量</td><td colspan="2">退件</td><td colspan="2">入库</td></tr><tr><td>单价</td><td>金额</td><td>计划价</td><td>金额</td></tr>');
        }else{//其他入库
            $('#info').append('<tr><td>编号: '+opener.$row.attr('IN_NO')+'</td><td style="text-align:right">打印日期: <%=today%></td></tr>');
            $('#partList').find('thead').append('<tr><td>序号</td><td>配件代码</td><td>配件名称</td><td>参图号</td><td>单位</td><td>数量</td><td>计划价</td><td>金额</td></tr>');
        }
        if(IN_TYPE == <%=DicConstant.RKLX_01%>) {//采购入库单
            var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/queryPchInBillPart.ajax?IN_ID="+opener.$row.attr('IN_ID');
            sendPost(queryUrl, "", "", queryPchCallback, "false");
        }else if(IN_TYPE == <%=DicConstant.RKLX_03%>){//销售退件
            var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/querySaleRetInBillPart.ajax?IN_ID="+opener.$row.attr('IN_ID');
            sendPost(queryUrl, "", "", querySaleRetCallback, "false");
        }else{//其余两种类型共用一个
            var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/queryMoveAndOtherInBillPart.ajax?IN_ID="+opener.$row.attr('IN_ID');
            sendPost(queryUrl, "", "", queryMoveAndOtherCallback, "false");
        }
    });
    function queryPchCallback(ret){
        var rows = ret.getElementsByTagName("ROW");
        var sumPchAmount = 0;
        var sumPlanAmount = 0;
        if (rows && rows.length > 0) {
            for(var i = 0;i<rows.length;i++){
                var PART_CODE = getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0));
                var PART_NAME = getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0));
                var PART_NO = getNodeText(rows[i].getElementsByTagName("PART_NO").item(0));
                var UNIT = getNodeText(rows[i].getElementsByTagName("UNIT").item(0));
                var IN_AMOUNT = getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0));
                var PCH_PRICE = getNodeText(rows[i].getElementsByTagName("PCH_PRICE").item(0));
                var PCH_AMOUNT = getNodeText(rows[i].getElementsByTagName("PCH_AMOUNT").item(0));
                var PLAN_PRICE = getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0));
                var PLAN_AMOUNT = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
                var ROWNUM = getNodeText(rows[i].getElementsByTagName("ROWNUM").item(0));
                $('#partList').find('tbody').append('<tr><td>'+ROWNUM+'</td><td>'+PART_CODE+'</td><td>'+PART_NAME+'</td><td>&nbsp;'+PART_NO+'&nbsp;</td><td>'+UNIT+'</td><td>'+IN_AMOUNT+'</td><td>'+amountFormatNew(PCH_PRICE)+'</td><td>'+amountFormatNew(PCH_AMOUNT)+'</td><td>'+amountFormatNew(PLAN_PRICE)+'</td><td>'+amountFormatNew(PLAN_AMOUNT)+'</td></tr>')
                sumPchAmount += parseFloat(PCH_AMOUNT);
                sumPlanAmount += parseFloat(PLAN_AMOUNT);
            }
            $('#partList').find('tbody').append('<tr><td colspan="7">合计</td><td>'+amountFormatNew(sumPchAmount.toFixed(2))+'</td><td></td><td>'+amountFormatNew(sumPlanAmount.toFixed(2))+'</td></tr>');
        }
    }
    function querySaleRetCallback(ret){
        var rows = ret.getElementsByTagName("ROW");
        var sumRetAmount = 0;
        var sumPlanAmount = 0;
        if (rows && rows.length > 0) {
            for(var i = 0;i<rows.length;i++){
                var PART_CODE = getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0));
                var PART_NAME = getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0));
                var PART_NO = getNodeText(rows[i].getElementsByTagName("PART_NO").item(0));
                var UNIT = getNodeText(rows[i].getElementsByTagName("UNIT").item(0));
                var IN_AMOUNT = getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0));
                var SALE_PRICE = getNodeText(rows[i].getElementsByTagName("SALE_PRICE").item(0));
                var RET_AMOUNT = getNodeText(rows[i].getElementsByTagName("RET_AMOUNT").item(0));
                var PLAN_PRICE = getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0));
                var PLAN_AMOUNT = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
                var ROWNUM = getNodeText(rows[i].getElementsByTagName("ROWNUM").item(0));
                $('#partList').find('tbody').append('<tr><td>'+ROWNUM+'</td><td>'+PART_CODE+'</td><td>'+PART_NAME+'</td><td>&nbsp;'+PART_NO+'&nbsp;</td><td>'+UNIT+'</td><td>'+IN_AMOUNT+'</td><td>'+amountFormatNew(SALE_PRICE)+'</td><td>'+amountFormatNew(RET_AMOUNT)+'</td><td>'+amountFormatNew(PLAN_PRICE)+'</td><td>'+amountFormatNew(PLAN_AMOUNT)+'</td></tr>')
                sumRetAmount += parseFloat(RET_AMOUNT);
                sumPlanAmount += parseFloat(PLAN_AMOUNT);
            }
            $('#partList').find('tbody').append('<tr><td colspan="7">合计</td><td>'+amountFormatNew(sumRetAmount.toFixed(2))+'</td><td></td><td>'+amountFormatNew(sumPlanAmount.toFixed(2))+'</td></tr>');
        }
    }
    function queryMoveAndOtherCallback(ret){
        var rows = ret.getElementsByTagName("ROW");
        var sumPlanAmount = 0;
        if (rows && rows.length > 0) {
            for(var i = 0;i<rows.length;i++){
                var PART_CODE = getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0));
                var PART_NAME = getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0));
                var PART_NO = getNodeText(rows[i].getElementsByTagName("PART_NO").item(0));
                var UNIT = getNodeText(rows[i].getElementsByTagName("UNIT").item(0));
                var IN_AMOUNT = getNodeText(rows[i].getElementsByTagName("IN_AMOUNT").item(0));
                var PLAN_PRICE = getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0));
                var PLAN_AMOUNT = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
                var ROWNUM = getNodeText(rows[i].getElementsByTagName("ROWNUM").item(0));
                $('#partList').find('tbody').append('<tr><td>'+ROWNUM+'</td><td>'+PART_CODE+'</td><td>'+PART_NAME+'</td><td>&nbsp;'+PART_NO+'&nbsp;</td><td>'+UNIT+'</td><td>'+IN_AMOUNT+'</td><td>'+amountFormatNew(PLAN_PRICE)+'</td><td>'+amountFormatNew(PLAN_AMOUNT)+'</td></tr>')
                sumPlanAmount += parseFloat(PLAN_AMOUNT);
            }
            $('#partList').find('tbody').append('<tr><td colspan="7">合计</td><td>'+amountFormatNew(sumPlanAmount.toFixed(2))+'</td></tr>');
        }
    }
    function doPrint(obj){
        $(obj).hide();
        var printUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/InBillPrintAction/printInBill.ajax?IN_ID="+opener.$row.attr('IN_ID')+"&IN_TYPE="+opener.$row.attr('IN_TYPE');
        $.ajax({
            url: printUrl,
            type: 'GET',
            dataType: 'html',
            success: function(result){
                window.print();
            }
        })
        opener.$("#tab-inBillList").removeResult(opener.$row);
    }
    </script>
</head>
<body style="text-align: center;">
    <div style="font-size: 30px;" id="title" align="center"></div>
    <br>
    <table id="info" border="0px" cellspacing="0" style="text-align:left;" align="center" width="80%">
    </table>
    <table id="partList" border="1px" cellspacing="0" style="text-align:center;" align="center" width="80%">
        <thead>
        </thead>
        <tbody>
        </tbody>
    </table>
    <div align="center"><input type="button" value="打印" onclick="doPrint(this)"/></div>
</body>
</html>
