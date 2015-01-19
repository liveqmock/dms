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
    <title>打印出库单</title>
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
        $('#title').text('陕重汽销司销售服务部'+opener.$row.attr('OUT_TYPE_SV')+(opener.$row.attr('OUT_TYPE')==<%=DicConstant.CKLX_04%>?'出库':'')+'单');
        var OUT_TYPE = opener.$row.attr('OUT_TYPE');//出库类型
        if(OUT_TYPE == <%=DicConstant.CKLX_01%> || OUT_TYPE == <%=DicConstant.CKLX_02%> || OUT_TYPE == <%=DicConstant.CKLX_06%>){//销售出库单|直营出库单|三包急件出库
            $('#info').append('<tr><td>审核员: '+opener.$row.attr('CHECK_USER_SV')+'</td><td style="text-align: right">编号: '+opener.$row.attr('OUT_NO')+'</td></tr><tr><td>客户名称: '+opener.$row.attr('RECV_UNIT')+'</td><td style="text-align:right">打印日期: <%=today%></td></tr>');
        }else if(OUT_TYPE == <%=DicConstant.CKLX_03%>){//移库出库
            $('#info').append('<tr><td>目标仓库: '+opener.$row.attr('RECV_UNIT')+'</td><td style="text-align: right">编号: '+opener.$row.attr('OUT_NO')+'</td></tr><tr><td rowspan="2">打印日期: <%=today%></td></tr>');
        }else if(OUT_TYPE == <%=DicConstant.CKLX_04%>){//采购退货
            $('#info').append('<tr><td>接收单位: '+opener.$row.attr('RECV_UNIT')+'</td><td style="text-align: right">编号: '+opener.$row.attr('OUT_NO')+'</td></tr><tr><td rowspan="2">打印日期: <%=today%></td></tr>');
        }else{//其他出库
            $('#info').append('<tr><td>编号: '+opener.$row.attr('OUT_NO')+'</td><td style="text-align:right">打印日期: <%=today%></td></tr>');
        }
        $('#partList').find('thead').append('<tr><td>序号</td><td>配件代码</td><td>配件名称</td><td>参图号</td><td>单位</td><td>库区</td><td>库位</td><td>数量</td><td>计划价</td><td>金额</td><td>备注</td></tr>');

        var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/queryOutBillPart.ajax?OUT_ID="+opener.$row.attr('OUT_ID');
        sendPost(queryUrl, "", "", queryCallback, "false");

    });
    function queryCallback(ret){
        var rows = ret.getElementsByTagName("ROW");
        var sumPlanAmount = 0;
        if (rows && rows.length > 0) {
            for(var i = 0;i<rows.length;i++){
                var PART_CODE = getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0));
                var PART_NAME = getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0));
                var PART_NO = getNodeText(rows[i].getElementsByTagName("PART_NO").item(0));
                var AREA_CODE = getNodeText(rows[i].getElementsByTagName("AREA_CODE").item(0));
                var POSITION_CODE = getNodeText(rows[i].getElementsByTagName("POSITION_CODE").item(0));
                var UNIT = getNodeText(rows[i].getElementsByTagName("UNIT").item(0));
                var OUT_AMOUNT = getNodeText(rows[i].getElementsByTagName("OUT_AMOUNT").item(0));
                var PLAN_PRICE = getNodeText(rows[i].getElementsByTagName("PLAN_PRICE").item(0));
                var PLAN_AMOUNT = getNodeText(rows[i].getElementsByTagName("PLAN_AMOUNT").item(0));
                var REMARK = getNodeText(rows[i].getElementsByTagName("REMARK").item(0));
                var ROWNUM = getNodeText(rows[i].getElementsByTagName("ROWNUM").item(0));
                var j = parseInt(i)+1;
                $('#partList').find('tbody').append('<tr><td>'+j+'</td><td>'+PART_CODE+'</td><td>'+PART_NAME+'</td><td>&nbsp;'+PART_NO+'&nbsp;</td><td>'+UNIT+'</td><td>'+AREA_CODE+'</td><td>'+POSITION_CODE+'</td><td>'+OUT_AMOUNT+'</td><td>'+amountFormatNew(PLAN_PRICE)+'</td><td>'+amountFormatNew(PLAN_AMOUNT)+'</td><td>&nbsp;'+REMARK+'&nbsp;</td></tr>')
                sumPlanAmount += parseFloat(PLAN_AMOUNT);
            }
            $('#partList').find('tbody').append('<tr><td colspan="7">合计</td<td></td><td>'+amountFormatNew(sumPlanAmount.toFixed(2))+'</td><td></td></tr>');
        }
    }

    function doPrint(obj){
        $(obj).hide();
        var printUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OutBillPrintAction/printOutBill.ajax?OUT_ID="+opener.$row.attr('OUT_ID');
        $.ajax({
            url: printUrl,
            type: 'GET',
            dataType: 'html',
            success: function(result){
                window.print();
            }
        })
        opener.$("#tab-outBillList").removeResult(opener.$row);
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
