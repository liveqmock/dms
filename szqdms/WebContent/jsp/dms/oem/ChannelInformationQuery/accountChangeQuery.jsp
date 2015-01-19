<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>账户异动查询</title>
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/channel/channelInfo/AccountChangeQueryAction/queryListInfoOem.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
         });

         // 导出按钮绑定
         $("#btn-export-index").click(function(){
             var $f = $("#fm-condition");
             if (submitForm($f) == false) {
                 return false;
             }
             var sCondition = {};
             sCondition = $f.combined() || {};
             $("#data").val(sCondition);
             var url = encodeURI("<%=request.getContextPath()%>/channel/channelInfo/AccountChangeQueryAction/downloadOem.do");
             window.location.href = url;
             $("#exportFm").attr("action",url);
             $("#exportFm").submit();
         });
    });

    // 订单查询
    function searchOrder() {
        var $f = $("#fm-condition");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
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

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

    //列表审核链接
    function doPrint(rowobj){
    	$row = $(rowobj);
    	$("#orderId").val($(rowobj).attr("ORDER_ID"));
    	$("#dia-ORDER_NO").val($(rowobj).attr("ORDER_NO"));
    	$("#dia-ORG_NAME").val($(rowobj).attr("ORG_NAME"));
    	$("#dia-ORDER_AMOUNT").val($(rowobj).attr("ORDER_AMOUNT"));
    	$("#dia-APPLY_DATE").val($(rowobj).attr("APPLY_DATE_sv"));
    	window.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printOrderDtl.jsp");
    }

    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt;财务相关&gt;渠道账户异动查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form action="post" id="searchForm">
                <input type="hidden" id="orderId" name="orderId"/>
                <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME"/>
                <input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO"/>
                <input type="hidden" id="dia-ORDER_AMOUNT" name="dia-ORDER_AMOUNT"/>
                <input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE"/>
        </form>
        <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
                    	<td><label>渠道商：</label></td>
	                    <td>
		                    <input type="text" id="dia-orgName" name="dia-orgName"  datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
		                    <input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="B.ORG_ID" operation="="/>
	                    </td>
                        <td><label>账户类型：</label></td>
                        <td>
                            <select type="text" id="accountType"  name="accountType" datasource="B.ACCOUNT_TYPE" kind="dic" src="ZJZHLX" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>操作类型：</label></td>
                        <td>
                            <select type="text" id="logType"  name="logType" datasource="A.LOG_TYPE" kind="dic" src="ZJYDLX" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<td><label>入账日期：</label></td>
                        <td>
                            <input  type="text" name="inStartDate" id="inStartDate" style="width:70px;" class="Wdate" operation=">=" group="inStartDate,inEndDate" datasource="A.IN_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input  type="text" name="inEndDate" id="inEndDate" style="width:70px;margin-left:-28px;;" class="Wdate" operation="<=" group="inStartDate,inEndDate" datasource="A.IN_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                        </td>
                        <td><label>操作人：</label></td>
                        <td><input type="text" id="PERSON_NAME" name="PERSON_NAME" datasource="E.PERSON_NAME" operation="like" datatype="1,is_null,300" /></td>
                        <td><label>操作时间：</label></td>
                        <td>
                            <input  type="text" name="sel-APPLY_START_DATE" id="sel-APPLY_START_DATE" style="width:70px;" class="Wdate" operation=">=" group="sel-APPLY_START_DATE,sel-APPLY_END_DATE" datasource="A.CREATE_TIME" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input  type="text" name="sel-APPLY_END_DATE" id="sel-APPLY_END_DATE" style="width:70px;margin-left:-28px;;" class="Wdate" operation="<=" group="sel-APPLY_START_DATE,sel-APPLY_END_DATE" datasource="A.CREATE_TIME" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid">
            <table style="display:none;width:100%;" id="tab-grid-index" name="tablist" ref="page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="AGENCY_CODE">办事处代码</th>
                        <th fieldname="AGENCY_NAME">办事处名称</th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="ACCOUNT_TYPE">账户类型</th>
                        <th fieldname="LOG_TYPE">操作类型</th>
                        <th fieldname="AMOUNT" refer="amountFormat" align="right">异动金额</th>
                        <th fieldname="SOURCEACCOUNT_TYPE">资金来源</th>
                        <th fieldname="IN_DATE">入账日期</th>
                        <th fieldname="REMARKS">备注</th>
                        <th fieldname="CREATE_USER">操作人</th>
                        <th fieldname="CREATE_TIME">操作时间</th>
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