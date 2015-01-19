<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String orgCode = user.getOrgCode();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>订单关闭</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var orgCode = "<%=orgCode%>";
var url = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction";
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
    //查询方法
    $("#btn-search").bind("click",function(event){
        doSearch();
    });
    if(orgCode=='J029002'){
    	$("#ORG_CODE").hide();
    	$("#ORG_CODE1").hide();
    	$("#btn-reset").hide();
    	$("#btn-reset1").hide();
    	$("#btn-add").bind("click", function(event){
    		$.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printInvoiceEntrustEdit1.jsp?action=1", "addEntrust", "新增委托单", diaAddOptions);
    	});
    }else{
    	$("#ORG_CODE").show();
    	$("#ORG_CODE1").show();
    	$("#btn-reset").show();
    	$("#btn-reset1").show();
    	$("#btn-add").bind("click", function(event){
    		$.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printInvoiceEntrustEdit.jsp?action=1", "addEntrust", "新增委托单", diaAddOptions);
    	});
    	$("#btn-reset").bind("click", function(event){
    		$("#orgCode").attr("code","");
    		$("#orgCode").val("");
    	});
    }
    
    $("#btn-search").trigger("click");

    
});
function doSearch(){
    var $f = $("#searchForm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    var searchUrl = url+"/partOrderEntrustSearch.ajax";
    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "orderList");
}

function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	if(orgCode=='J029002'){
		$.pdialog.open(webApps+"/jsp/dms/oem/part/sales/orderMng/printInvoiceEntrustEdit1.jsp?action=2", "addEntrust", "修改委托单", diaAddOptions);
	}else{
		$.pdialog.open(webApps+"/jsp/dms/oem/part/sales/orderMng/printInvoiceEntrustEdit.jsp?action=2", "addEntrust", "修改委托单", diaAddOptions);
	}
	
	
}
//列表审核链接
function doPrint(rowobj){
	
	var $row = $(rowobj);
	var ENTRUST_ID = $(rowobj).attr("ENTRUST_ID");
	if(orgCode=='J029002'){
		var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction/printPdf1.do?ENTRUST_ID="+ENTRUST_ID+"&flag=1";
	}else{
		var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderEntrustAction/printPdf.do?ENTRUST_ID="+ENTRUST_ID+"&flag=1";
	}
    window.open(queryUrl); 
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
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 发票委托单打印</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="searchForm">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="searchTab">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订委托单编号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datasource="T.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td id="ORG_CODE"><label >渠道商：</label></td>
                        <td id="ORG_CODE1"><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_CODE" readOnly datatype="1,is_null,10000" hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" operation="in"/></td>
                    	<td><label>月度：</label></td>
					    <td >
				    		<input type="text" id="in-kscjrq"  name="in-kscjrq" operation="="  dataSource="SELECT_MONTH" style="width:75px;"   datatype="1,is_null,30" onclick="WdatePicker({dateFmt:'yyyy-MM'})" />
				   		 </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
                        <li id="btn-reset1"><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="orderListDiv" >
            <table style="display:none;width:100%;" id="orderList" name="orderList" ref="orderListDiv" refQuery="searchTab">
                    <thead>
                        <tr>
                            <th fieldname="ROWNUMS" style="display:"></th>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ENTRUST_NO">委托单号</th>
                            <th fieldname="ORG_CODE">渠道商代码</th>
                            <th fieldname="ORG_NAME">渠道商名称</th>
                            <th fieldname="SELECT_MONTH">委托月份</th>
                            <th fieldname="ADDRESS">详细地址</th>
                            <th fieldname="TELEPHONE">联系电话</th>
                            <th fieldname="TARIFF_TYPE">发票类型</th>
                            <th fieldname="TARIFF_NO">税号</th>
                            <th fieldname="OPEN_BANK">开户行</th>
                            <th fieldname="BANK_ACCOUNT">银行账户</th>
                            <th fieldname="AMOUNT" refer="amountFormat" align="right">总金额(元)</th>
                            <th fieldname=USER_ACCOUNT >经办人</th>
                            <th fieldname="PRINT_STATUS" >打印状态</th>
                            <th type="link" colwidth="115" title="[打印委托单]|[修改]" action="doPrint|doUpdate" >操作</th>
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