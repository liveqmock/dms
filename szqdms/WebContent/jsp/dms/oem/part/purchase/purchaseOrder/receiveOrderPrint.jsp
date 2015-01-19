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
    <title>打印领料票</title>
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
        $('#title').text('陕西重型汽车有限公司（ '+opener.$row.attr('SUPPLIER_NAME')+'）材料领用单');
        $('#info').append('<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td style="text-align: right">编号: '+opener.$row.attr('REC_NO')+'</td></tr><tr></tr>');
        $('#end').append('<tr><td>主管：</td><td>&nbsp;</td><td>仓库记账：</td><td>&nbsp;</td><td>发料人：</td><td>&nbsp;</td><td>领料人：</td><td>&nbsp;</td><td>制单人：</td><td>&nbsp;'+opener.$row.attr('CREATE_USER_sv')+'</td><td>接收员	：</td><td>&nbsp;</td></tr>');
        var queryUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction/getPrintInfo.ajax?REC_ID="+opener.$row.attr('REC_ID');
        sendPost(queryUrl, "", "", queryCallback, "false");
    });
    function queryCallback(ret){
        var rows = ret.getElementsByTagName("ROW");
        if (rows && rows.length > 0) {
            for(var i = 0;i<rows.length;i++){
                var PART_CODE = getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0));
                var PART_NAME = getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0));
                var UNIT = rows[i].getElementsByTagName("UNIT").item(0).getAttribute('sv');
                var MIN_PACK = getNodeText(rows[i].getElementsByTagName("MIN_PACK").item(0));
                var PCH_COUNT = getNodeText(rows[i].getElementsByTagName("PCH_COUNT").item(0));
                var a = MIN_PACK+'/'+UNIT;
                $('#partList').find('tbody').append('<tr><td>'+(i+1)+'</td><td>&nbsp;</td><td>'+PART_CODE+'</td><td>'+PART_NAME+'</td><td>'+a+'</td><td>'+PCH_COUNT+'</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>');
            }
        }
    }

    function doPrint(obj){
        $(obj).hide();
        var printUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderMngAction/printReceive.ajax?REC_ID="+opener.$row.attr('REC_ID');
        $.ajax({
            url: printUrl,
            type: 'GET',
            dataType: 'html',
            success: function(result){
                window.print();
            }
        })
        opener.searchIssueOrder();
    }
    </script>
</head>
<body style="text-align: center;">
    <div style="font-size: 30px;" id="title" align="center"></div>
    <br>
    <br>
    <table id="info" border="0px" cellspacing="0" style="text-align:left;" align="center" width="80%">
    </table>
    <table id="partList" border="1px" cellspacing="0" style="text-align:center;" align="center" width="80%">
        <thead>
            <th>序号</th>
            <th>计配号</th>
            <th>配件代码</th>
            <th>配件名称</th>
            <th>单位</th>
            <th>应发</th>
            <th>实发</th>
            <th>单价</th>
            <th>金额</th>
        </thead>
        <tbody>
        </tbody>
    </table>
    <table id="end" border="0px" cellspacing="0" style="text-align:left;" align="center" width="80%">
    </table>
    <div align="center"><input type="button" value="打印" onclick="doPrint(this)"/></div>
</body>
</html>
