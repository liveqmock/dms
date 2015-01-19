<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>价差应补管理</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/part/salesMng/spreadFill/SpreadFillAction";
//初始化方法
$(function(){
    //查询方法
    $("#btn-search").bind("click",function(event){
        doSearch();
    });

    $("#btn-reset").bind("click", function(event){
    		$("#searchForm")[0].reset();
    		$("#orgCode").attr("code","");
    		$("#orgCode").val("");
    	});
});
function doSearch(){
    var $f = $("#searchForm");
    var sCondition = {};
    sCondition = $f.combined() || {};
    var searchUrl = url+"/spreadFillSearch.ajax";
    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "spreadFillList");
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
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 价差管理   &gt; 价差应补管理</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="searchForm">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="searchTab">
                    <!-- 定义查询条件 -->
                    <tr>
                    	<td><label>调价时间：</label></td>
                        <td>
                            <input type="text" group="startDate,endDate" id="startDate" kind="date" action="show" name="startDate" style="width:75px;" datasource="START_DATE" datatype="0,is_date,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate,endDate" id="endDate" kind="date" action="show" name="endDate" style="width:75px;margin-left:-30px;" datasource="END_DATE" datatype="0,is_date,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>应补类型：</label></td>
                        <td>
                            <select type="text" id="spreadType" name="spreadType" kind="dic" src="JCLX" action="show" datasource="FILL_TYPE" filtercode="" datatype="0,is_null,30" operation="=">
                                <option value="<%=DicConstant.JCLX_01%>" selected>一次应补</option>
                            </select>
                        </td>
                        </tr>
                    <tr>
                    	<td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode" name="partCode" datasource="T.PART_CODE" datatype="1,is_null,50" operation="like" /></td>
                        <td><label>渠道商：</label></td>
                        <td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_ID" datatype="1,is_null,100" readOnly hasBtn="true" callFunction="showOrgTree('orgCode',1,1,1)" operation="in"/></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >生&nbsp;成&nbsp;应&nbsp;补</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="spreadFillDiv" >
            <table style="display:none;width:100%;" limitH="false" id="spreadFillList" name="spreadFillList" ref="spreadFillDiv" refQuery="searchTab">
                    <thead>
                        <tr>
                            <th fieldname="ROWNUMS" style="display:"></th>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ORG_CODE">渠道代码</th>
                            <th fieldname="ORG_NAME">渠道名称</th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="FILL_TYPE">应补类型</th>
                            <th fieldname="CREATE_TIME" colwidth="130">调价时间</th>
                            <th fieldname="OLD_PRICE" colwidth="130">原销售价格</th>
                            <th fieldname="NEW_PRICE" colwidth="130">调整后价格</th>
                            <th fieldname="SPREAD" refer="amountFormat" align="right">价差(元)</th>
                            <th fieldname="DELIVERY_COUNT" colwidth="60">发货数量</th>
                            <th fieldname="CHANEL_COUNT" colwidth="60">出渠道数量</th>
                            <th fieldname="SALE_COUNT" colwidth="60">出终端数量</th>
                            <th fieldname="FILL_COUNT" colwidth="60">应补数量</th>
                            <th fieldname="FILL_AMOUNT" refer="amountFormat" align="right">应补金额(元)</th>
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