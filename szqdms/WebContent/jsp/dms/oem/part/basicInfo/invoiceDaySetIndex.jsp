<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>结算日设置</title>
    <script type="text/javascript">
        var url = "<%=request.getContextPath()%>/part/basicInfoMng/InvoiceDaySetAction";
        var diaEditOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                doSearch();
            });
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/invoiceDaySetEdit.jsp?action=1", "editWin", "新增结算日", diaEditOptions);
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
        	
        	// 添加判断：当结算日期状态为已确定状态则不可编辑
        	if($(rowobj).attr("DAY_STATUS") == <%=DicConstant.JSRQZT_01%>){
        		alertMsg.warn("此结算日期已确定，不可编辑");
    			return;
        	}
        	
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/invoiceDaySetEdit.jsp?action=2", "editWin", "修改结算日", diaEditOptions);
        }
		function doSearch(){
			var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/invoiceDaySetSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
		}
        function showAnNiu(obj){
/*         	 var $tr = $(obj).parent();
             var tjzt = $tr.find("td").eq(6).attr("code");
             var sf = $tr.find("td").eq(8).attr("code"); */
             var $tr = $(obj).parent();
             var tjzt = $tr.attr("INVOICE_STATUS");
             var sf = $tr.attr("FLAG");
             if(tjzt==206601){
                 obj.html("<A class=op title=[编辑] onclick='doUpdate(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[编辑]</A><A class=op title=[统计] onclick='doInvoice(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[统计]</A>");
             }else if(tjzt==206602&& sf==100102){
            	 obj.html("<A class=op title=[清除] onclick='delInvoice(this.parentElement.parentElement.parentElement)' href='javascript:void(0);'>[清除]</A>");
             }else{
            	 obj.html("");
             }
        }
        function doInvoice(rowobj){
        	 var invoiceUrl = url + "/invoice.ajax?&dayId=" + $(rowobj).attr("DAY_ID");
             sendPost(invoiceUrl, "", "", invoiceCallBack, "true");
        }
        function invoiceCallBack(res) {
            try {
            	doSearch();
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }
        function delInvoice(rowobj){
       	 	var dleInvoiceUrl = url + "/delInvoice.ajax?&dayId=" + $(rowobj).attr("DAY_ID");
            sendPost(dleInvoiceUrl, "", "", delInvoiceCallBack, "true");
       }
       function delInvoiceCallBack(res) {
           try {
           	doSearch();
           } catch (e) {
               alertMsg.error(e);
               return false;
           }
           return true;
       }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：配件管理 &gt; 基础数据管理 &gt; 基本信息管理 &gt;结算日设置</h4>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="searchForm">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="searchTable">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>结算日期：</label></td>
                        <td>
                            <input type="text" group="startDate,endDate"  id="startDate" kind="date" name="startDate" style="width:70px;" datasource="START_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate,endDate"  id="endDate" kind="date" name="endDate" style="width:70px;margin-left:-30px;" datasource="END_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                            <td><label>结算类型：</label></td>
                            <td>
                            	<select class="combox" id="INVOICE_TYPE" name="INVOICE_TYPE" kind="dic" src="PJJSLX" datasource="INVOICE_TYPE" operation="=" datatype="1,is_null,10">
                                    <option value="-1">--</option>
                                </select>
							</td>
							<td><label>统计状态：</label></td>
                            <td>
                            	<select class="combox" id="INVOICE_STATUS" name="INVOICE_STATUS" kind="dic" src="TJZT" datasource="INVOICE_STATUS" operation="=" datatype="1,is_null,10">
                                    <option value="-1">--</option>
                                </select>
							</td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-invoiceDaySave">结算日确定</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-searchList">
                <table style="display:none;width:100%;" id="tab-searchList" name="tablist" ref="div-searchList" refQuery="tab-searchTable">
                    <thead>
                    <tr>
                        <th type="single" name="XH"></th>
                        <th fieldname="INVOICE_TYPE">结算类型</th>
                        <th fieldname="INVOICE_MONTH" style="display:none">结算月份</th>
                        <th fieldname="START_DATE">开始日期</th>
                        <th fieldname="END_DATE">结束日期</th>
                        <th fieldname="INVOICE_STATUS">统计状态</th>
                        <th fieldname="INVOICE_DATE">统计时间</th>
                        <th fieldname="FLAG">是否已发生业务</th>
                        <th fieldname="DAY_STATUS">结算日期状态</th>
                        <th colwidth="85" type="link" refer="showAnNiu" title="[编辑]|[统计]|[清除]" action="doUpdate|doInvoice|delInvoice">操作</th>
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
<script type="text/javascript">
$(function(){
	
	// 结算日确定按钮
	$("#btn-invoiceDaySave").click(function(){
		var $selectRadioObj = $(":checked", "#tab-searchList_content"); // 获取选择到的radio对象
		if(!$selectRadioObj.size()){ // 判断对象是否被选择，如果没有选择结算日期对象，则报警告
			alertMsg.warn("请选择需要设定的结算日期");
			return;
		}
		var $selectRow = $selectRadioObj.parent().parent().parent(); // 获取日期行对象
		if($selectRow.attr("DAY_STATUS") == <%=DicConstant.JSRQZT_01%>){ // 判断结算日期状态是否已设置为已确定状态
			alertMsg.warn("此结算日期已确定");
			return;
		}
		alertMsg.confirm("结算日期确定后不可更改，是否确定？",{
            okCall: function(){
            	receivablesDay();
            }}
		);
	});
	
	/*
	 * 结算日确定
	 */
	function receivablesDay(){
		var dayId = $("#tab-searchList").getSelectedRows()[0].attr("DAY_ID");// 获取选中行的DayID
		if(!dayId){
			alertMsg.warn("请选择需要设定的结算日期");
			return;
		}
	    sendPost('<%=request.getContextPath()%>/part/basicInfoMng/DealerReceivablesAction/receivablesDay.ajax?dayId=' + dayId , '', '', receivablesDayCallback);
	} 
	
	/*
	 * 结算日期确定回调函数
	 */
	 function receivablesDayCallback(res){
		 try {
	        	doSearch();
	        } catch (e) {
	            alertMsg.error(e);
	            return false;
	        }
	        return true;
	}
});
</script>
</html>