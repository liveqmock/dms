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
    <title>打印配件标签</title>
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
        var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/printPartTitle.ajax?ISSUE_ID="+opener.$('#dia-ISSUE_ID').val()+"&printFlag=1";
        sendPost(queryUrl, "", "", queryCallback, "false");
    });
    function queryCallback(ret){
        var rows = ret.getElementsByTagName("ROW");
       // var tablist = $('#partList').append('<tr></tr>')
        if (rows && rows.length > 0) {
            for(var i = 0;i<rows.length;i++){
            	var SUPPLIER_NAME = getNodeText(rows[i].getElementsByTagName("SUPPLIER_NAME").item(0));
            	var PART_CODE = getNodeText(rows[i].getElementsByTagName("PART_CODE").item(0));
                var PART_NAME = getNodeText(rows[i].getElementsByTagName("PART_NAME").item(0));
                var COUNT = getNodeText(rows[i].getElementsByTagName("COUNT").item(0));
                
                if(i%3==0){
                	//tablist.append('<td><table border="1px" cellspacing="0" style="text-align:center;" align="left" width=300px, height=110px ><tr><td>'+SUPPLIER_NAME+'</td></tr><tr><td>'+PART_CODE+'</td></tr><tr><td>'+PART_NAME+'</td></tr><tr><td>'+COUNT+'</td></tr></table></td>');
                	$('#partList').append('<tr><td><table border="1px" cellspacing="0" style="text-align:center;" align="left" width=300px, height=110px ><tr><td>'+SUPPLIER_NAME+'</td></tr><tr><td>'+PART_CODE+'</td></tr><tr><td>'+PART_NAME+'</td></tr><tr><td>'+COUNT+'</td></tr></table></td></tr>');
                }else{
                	$('#partList').append('<tr><td><table border="1px" cellspacing="0" style="text-align:center;" align="left" width=300px, height=110px ><tr><td>'+SUPPLIER_NAME+'</td></tr><tr><td>'+PART_CODE+'</td></tr><tr><td>'+PART_NAME+'</td></tr><tr><td>'+COUNT+'</td></tr></table></td></tr>');
                }
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
    <table id="partList" border="1px" cellspacing="0" style="text-align:center;" align="center" width="80%">
    </table>
    <div align="center"><input type="button" value="打印" onclick="doPrint(this)"/></div>
</body>
</html>
