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
    <title>打印发料单</title>
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
        $('#info').append('<tr><td>客户: '+opener.$('#dia-ORG_NAME').val()+'</td><td></td><td style="text-align: right">编号: '+opener.$('#dia-ORDER_NO').val()+'</td></tr><tr><td>订单总金额: '+opener.$('#dia-ORDER_AMOUNT').val()+'元</td><td></td><td style="text-align:right">打印日期: <%=today%></td></tr><tr></tr>');
        var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderCloseAction/partOrderDtlPrintSearch.ajax?orderId="+opener.$('#orderId').val();
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
                var ORDER_COUNT = getNodeText(rows[i].getElementsByTagName("ORDER_COUNT").item(0));
                var AMOUNT = getNodeText(rows[i].getElementsByTagName("AMOUNT").item(0));
                var REMARKS = getNodeText(rows[i].getElementsByTagName("REMARKS").item(0));
                var AUDIT_COUNT = getNodeText(rows[i].getElementsByTagName("AUDIT_COUNT").item(0));
                
                var a = MIN_PACK+'/'+UNIT;
               /*  var R_COUNT = "";
                if(REAL_COUNT){
                	R_COUNT = REAL_COUNT;
                }else{
                	R_COUNT= "____";
                }*/
                var REMARK = getNodeText(rows[i].getElementsByTagName("REMARK").item(0));
                /*var RMK = "";
                if(REMARK){
                	RMK = REAL_COUNT;
                }else{
                	RMK= "____";
                } */
                var ROWNUM = getNodeText(rows[i].getElementsByTagName("ROWNUM").item(0));
                $('#partList').find('tbody').append('<tr><td>'+(i+1)+'</td><td>'+PART_CODE+'</td><td>'+PART_NAME+'</td><td>'+UNIT+'</td><td>'+a+'</td><td>'+ORDER_COUNT+'</td><td>'+AUDIT_COUNT+'</td><td>&nbsp;'+AMOUNT+'&nbsp;</td><td>&nbsp;'+REMARKS+'&nbsp;</td></tr>');
            }
        }
    }

    function doPrint(obj){
        $(obj).hide();
        var printUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/printIssue.ajax?ISSUE_ID="+opener.$('#dia-ISSUE_ID').val()
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
    <table id="info" border="0px" cellspacing="0" style="text-align:left;" align="center" width="80%">
    </table>
    <table id="partList" border="1px" cellspacing="0" style="text-align:center;" align="center" width="80%">
        <thead>
            <th>序号</th>
            <th>配件代码</th>
            <th>配件名称</th>
            <th>单位</th>
            <th>最小包装</th>
            <th>订单数量</th>
            <th>实发数量</th>
            <th>订单金额</th>
            <th>备注</th>
        </thead>
        <tbody>
        </tbody>
    </table>
    <div align="center"><input type="button" value="打印" onclick="doPrint(this)"/></div>
</body>
</html>
