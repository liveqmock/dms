<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>经销商订单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询  &gt; 销售相关&gt; 经销商订单查询</h4>
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
                        <td><label>订单号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datatype="1,is_null,30" datasource="T.ORDER_NO" operation="like"/></td>
                        <td><label>订单类型：</label></td>
                        <td>
                            <select type="text" id="orderType"  name="orderType" datasource="T.ORDER_TYPE" kind="dic" src="DDLX" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<td><label>配件代码</label></td>
                    	<td>
                    		<input type="text" name="partCode" id="partCode" dataSource="PART_CODE" operation="like" datatype="1,is_null,300" />
                    	</td>
                    	<td><label>配件名称</label></td>
                    	<td>
                    		<input type="text" name="partName" id="partName" dataSource="PART_NAME" operation="like" datatype="1,is_null,300" />
                    	</td>
                    </tr>
                    <tr>
                        <td><label>订单状态：</label></td>
                        <td>
                            <select type="text" id="orderStatus"  name="orderStatus" datasource="T.ORDER_STATUS" kind="dic" src="DDZT" filtercode="<%=DicConstant.DDZT_02%>|<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_04%>|<%=DicConstant.DDZT_05%>|<%=DicConstant.DDZT_06%>" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>申请日期：</label></td>
                        <td>
                            <input type="text" group="startDate1,endDate1"  id="startDate1" kind="date" name="startDate1" style="width:75px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate1,endDate1"  id="endDate1" kind="date" name="endDate1" style="width:75px;margin-left:-30px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>渠道商：</label></td>
                        <td colspan="3">
                        	<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,2,2)" operation="="/>
							<input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="T.ORG_ID" operation="="/>
                        </td>
                   </tr>
                    <tr>
                    	<td><label>订单发运状态：</label></td>
                    	<td>
	                    	<select type="text" id="SHIP_STATUS"  name="SHIP_STATUS" datasource="T.SHIP_STATUS" kind="dic" src="DDFYZT" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    	<td><label>订单发运方式：</label></td>
                    	<td>
	                    	<select type="text" id="TRANS_TYPE"  name="TRANS_TYPE" datasource="T.TRANS_TYPE" kind="dic" src="FYFS" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                    	</td>
                    </tr>
                    <tr>
                        <td><label>关单日期：</label></td>
                        <td>
                            <input type="text" group="startDate3,endDate3"  id="startDate3" kind="date" name="startDate3" style="width:75px;" datasource="T.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate3,endDate3"  id="endDate3" kind="date" name="endDate3" style="width:75px;margin-left:-30px;" datasource="T.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                        <td><label>审核日期：</label></td>
                        <td>
                            <input type="text" group="startDate0,endDate0"  id="startDate0" kind="date" name="startDate0" style="width:75px;" datasource="T1.CHECK_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate0,endDate0"  id="endDate0" kind="date" name="endDate0" style="width:75px;margin-left:-30px;" datasource="T1.CHECK_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-clear-index">重&nbsp;&nbsp;置</button></div></div></li>
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
                        <th fieldname="ORDER_NO" refer="showLink">订单号</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
                        <th fieldname="APPLY_DATE">申请日期</th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">订单金额(元)</th>
                        <th fieldname="REAL_AMOUNT"  refer="amountFormat" align="right">实发金额(元)</th>
                        <th fieldname="CHECK_DATE">订单审核日期</th>
                        <th fieldname="ORDER_STATUS">订单状态</th>
                        <th fieldname="SHIP_STATUS">订单发运状态</th>
                        <th fieldname="TRANS_TYPE">订单发运方式</th>
                        <th fieldname="COUNTRY_NAME" colwidth="150px">收货地址（省/市/区/县）</th>
                        <th fieldname="DELIVERY_ADDR">收货地址</th>
                        <th fieldname="CLOSE_DATE">订单关闭日期</th>
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
<script type="text/javascript">
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryForDealerAction/searchOrder.ajax";
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
             var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryForDealerAction/download.do");
             window.location.href = url;
             $("#exportFm").attr("action",url);
             $("#exportFm").submit();
         });

         
         // 重置
         $("#btn-clear-index").click(function(){
        	$("input","#ycsbTable").each(function(index, obj){
        		$(obj).val("");
        	});
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
    	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

    // 操作列的回调函数
    function showBtn(obj){
        var $tr = $(obj).parent();
        var forcastStatus = $tr.find("td").eq(11).attr("code");
        if(forcastStatus == <%=DicConstant.DDZT_06 %>){
        obj.html("<A class=op title=[打印] onclick=doPrint(this.parentElement.parentElement.parentElement) href='javascript:void(0);'>[打印]</A>");
        }
    }

    function doPrint(rowobj) {
    	$row = $(rowobj);
        var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/printPdf.do?ORDER_ID="+$(rowobj).attr("ORDER_ID");
        window.open(queryUrl);
    }
    
    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#dia-orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }

</script>