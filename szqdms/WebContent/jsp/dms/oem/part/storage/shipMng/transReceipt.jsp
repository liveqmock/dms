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
    String VEHICLE_ID = request.getParameter("VEHICLE_ID");
	String SHIP_ID = request.getParameter("SHIP_ID");
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
    var VEHICLE_ID = "<%=VEHICLE_ID%>";
    var SHIP_ID = "<%=SHIP_ID%>";
    $(function(){
        $('#title').text('陕西重型汽车有限公司配件运输回执');
        
        var queryUrl1 = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction/getTransReceipt.ajax?VEHICLE_ID="+VEHICLE_ID+"&SHIP_ID="+SHIP_ID;
        sendPost(queryUrl1, "", "", queryCallback1, "false");
        
        var queryUrl2 = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipOutAction/getTransReceipt.ajax?OUT_ID="+opener.$row.attr('OUT_ID');
        sendPost(queryUrl2, "", "", queryCallback2, "false");

    });
    function queryCallback1(ret){
        var rows = ret.getElementsByTagName("ROW");
        var NAME = getNodeText(rows[0].getElementsByTagName("NAME").item(0));
        var COMPANY_NAME = getNodeText(rows[0].getElementsByTagName("CARRIER_NAME").item(0));
        var PHONE = getNodeText(rows[0].getElementsByTagName("DRIVER_PHONE").item(0));
        var COUNT = getNodeText(rows[0].getElementsByTagName("COUNT").item(0));
        var PAPER_BOX = getNodeText(rows[0].getElementsByTagName("PAPER_BOX").item(0));
        var WOOD_BOX = getNodeText(rows[0].getElementsByTagName("WOOD_BOX").item(0));
        var NO_PACKED = getNodeText(rows[0].getElementsByTagName("NO_PACKED").item(0));
        var OTHER_PACKED = getNodeText(rows[0].getElementsByTagName("OTHER_PACKED").item(0));
        var addrs = getNodeText(rows[0].getElementsByTagName("ADDRS").item(0));
        var orderNos = getNodeText(rows[0].getElementsByTagName("ORDER_NOS").item(0)); 
        var RECEIPT_NO = getNodeText(rows[0].getElementsByTagName("RECEIPT_NO").item(0));
        $('#first').append('<tr><td style="text-align:left">回执号：'+RECEIPT_NO+'</td><td style="text-align:left">打印日期：<%=today%></td><td style="text-align:left">到货日期：___年_月_日</td></tr>');
        $('#first').append('<tr><td style="text-align:left">发运人：'+NAME+'</td><td style="text-align:left">发运单制作人：'+NAME+'</td><td style="text-align:left">承运公司：'+COMPANY_NAME+'</td></tr>');
        $('#wood').append('</span><font class="font9"><span i>&nbsp;</span>'+WOOD_BOX+'</font>');
        $('#paper').append('</span><font class="font9"><span i>&nbsp;</span>'+PAPER_BOX+'</font>');
        $('#noPack').append('</span><font class="font9"><span i>&nbsp;</span>'+NO_PACKED+'</font>');
        $('#other').append('</span><font class="font9"><span i>&nbsp;</span>'+OTHER_PACKED+'</font>');
        $('#total').append('</span><font class="font9"><span i>&nbsp;</span>'+COUNT+'</font>');
        $('#printer').append('打印人：'+NAME);
        $('#orderNos').append('订单号：<br>'+
    		    '<br>'+
    		    '<br>'+
    		    '<font class="font9">'+orderNos+'</font>') 
        $('#PHONE').append('司机电话：<br>'+
        '<span >&nbsp;&nbsp;&nbsp; </span><font class="font9"><span i>&nbsp;</span>'+PHONE+'</font>');
        $('#adrrs').append('收货人地址及电话：<br>'+
     		   '<span >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
     		 	' </span><font class="font9" >'+addrs+'</font>');
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
    <table id="first" border="0px" cellspacing="0" style="text-align:left;" align="center" width="80%"></table>
    <!-- <table id="second" border="0px" cellspacing="0" style="text-align:left;" align="center" width="80%"></table> -->
    
	<table id="partList" border="1px" cellspacing="0" style="text-align:center;" align="center" width="80%" style="word-break:break-all; word-wrap:break-all;">
		<tr >
			<td width="20%" rowspan=2 >配件包装分类和件数</td>
			<td width="10%">木箱</td>
			<td width="10%">纸箱</td>
			<td width="10%">无包装</td>
			<td width="10%">其它</td>
			<td width="20%">合计</td>
			<td colspan=2 rowspan=2 >备注</td>
		</tr>
		<tr height=42>
			<td id="wood">&nbsp;</td>
			<td id="paper">&nbsp;</td>
			<td id="noPack">&nbsp;</td>
			<td id="other">&nbsp;</td>
			<td id = "total">&nbsp;</td>
		</tr>
		<tr height=18 >
			<td colspan=5 rowspan=6 align="left" valign="top" id="adrrs" height=113 class=xl75 width=476 height:84.75pt;width:358pt'></td>
			<td rowspan=6 align="left" valign="top" id="orderNos"></td>
			<td colspan=2 rowspan=6 >　</td>
		</tr>
		<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
		<tr>
			<td colspan=2 rowspan=4 align="left" valign="top">承运人签字<span></span><br><br>
			  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
			</td>
			<td colspan=2 rowspan=4 align="left" valign="top" id="PHONE"></td>
			<td style="border-bottom:none" colspan=4 rowspan=7 align="left" valign="top">到货情况说明：</td>
		</tr><tr></tr><tr></tr><tr></tr>
		<tr height="50">
			<td colspan=2 rowspan=3 align="left" valign="top" id="printer"></td>
			<td colspan=2 align="left" valign="top">收货人签字盖章:</td>
		</tr>
		<tr style='display:none'>
			<td></td>
			<td></td>
		</tr>
		<tr style='display:none'>
			<td></td>
			<td></td>
		</tr>
		<tr style='display:none'>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
    <div align="center">
    	<input type="button" value="打印" onclick="doPrint(this)"/>
    </div>
</body>
</html>
