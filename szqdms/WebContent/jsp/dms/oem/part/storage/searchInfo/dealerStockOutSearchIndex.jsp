<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String orgId = user.getOrgId();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>渠道订单查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：渠道信息查询   &gt;销售相关   &gt; 渠道订单查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="A.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>订单类型：</label></td>
                        <td>
                            <select type="text" id="ORDER_TYPE" name="ORDER_TYPE" kind="dic" src="DDLX" datasource="A.ORDER_TYPE"
                            	filtercode="<%=DicConstant.DDLX_01%>|<%=DicConstant.DDLX_02%>|<%=DicConstant.DDLX_03%>|<%=DicConstant.DDLX_04%>|<%=DicConstant.DDLX_05%>|<%=DicConstant.DDLX_06%>|<%=DicConstant.DDLX_11%>|<%=DicConstant.DDLX_12%>"  datatype="1,is_null,30">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>订单状态：</label></td>
                        <td>
                            <select type="text" id="ORDER_STATUS" name="ORDER_STATUS" kind="dic" src="DDZT" datasource="A.ORDER_STATUS"
                            	filtercode="<%=DicConstant.DDZT_02%>|<%=DicConstant.DDZT_03%>|<%=DicConstant.DDZT_04%>|<%=DicConstant.DDZT_05%>|<%=DicConstant.DDZT_06%>|<%=DicConstant.DDZT_07%>" datatype="1,is_null,30">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>提报单位代码：</label></td>
                        <td>
                       		<input type="text" id="ORG_CODE" name="ORG_CODE" datasource="A.ORG_CODE" datatype="1,is_null,300000" kind="dic" src="" readonly="true" operation="="/>
                        </td>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" id="APPLY_DATE_B"  name="APPLY_DATE_B" operation=">="  dataSource="A.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" id="APPLY_DATE_E"  name="APPLY_DATE_E" operation="<=" dataSource="A.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                    <tr>
                    	<td><label>提报单位名称：</label></td>
                        <td>
                       		<input type="text" id="ORG_NAME" name="ORG_NAME" datasource="A.ORG_NAME" datatype="1,is_null,300000"  operation="like"/>
                        </td>
                        <td><label>关闭日期：</label></td>
                        <td>
                            <input type="text" id="CLOSE_DATE_B"  name="CLOSE_DATE_B" operation=">="  dataSource="A.CLOSE_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" id="CLOSE_DATE_E"  name="CLOSE_DATE_E" operation="<=" dataSource="A.CLOSE_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index" >
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-index" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ORDER_NO" colwidth="140" refer="showLink">订单编号</th>
                            <th fieldname="ORDER_TYPE" colwidth="70" >订单类型</th>
                            <th fieldname="ORDER_STATUS" colwidth="70">订单状态</th>
                            <th fieldname="ORG_NAME" >提报单位</th>
                            <th fieldname="APPLY_DATE" colwidth="70">提报时间</th>
                            <th fieldname="CLOSE_DATE" colwidth="70">关闭时间</th>
                            <th fieldname="ORDER_AMOUNT" refer="formatAmount" align="right">订单金额(元)</th>
                            <th fieldname="SHOULD_AMOUNT" refer="formatAmount" align="right">应发金额(元)</th>
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
var orgId='<%=orgId%>';
    //初始化方法
    $(function(){

    	$("#ORG_CODE").attr("src","T#TM_ORG A,PT_BA_SERVICE_DC B:CODE:ONAME{CODE,ONAME}:1=1 AND A.ORG_ID = B.ORG_ID AND B.DC_ID='"+orgId+"' AND A.STATUS='100201' ORDER BY CODE")
        // 查询按钮绑定
        $("#btn-search-index").bind("click",function(event){
            // 查询销售订单方法
            searchSaleOrder();
        });
        // 导出按钮绑定
        $("#btn-export-index").click(function(){
            var $f = $("#fm-index");
            if (submitForm($f) == false) {
                return false;
            }
            var sCondition = {};
            sCondition = $f.combined() || {};
            $("#data").val(sCondition);
            var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/DealerOutSearchMngAction/download.do");
            window.location.href = url;
            $("#exportFm").attr("action",url);
            $("#exportFm").submit();
        });
    });

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询销售订单
    function searchSaleOrder() {
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/search/DealerOutSearchMngAction/searchSaleOrder.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }
    var row;
    function toDetail(){
    	 row = $(rowobj);
         $("td input[type=radio]", $(rowobj)).attr("checked", true);
  		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
   	    $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/searchInfo/dealerSaleStockOrderDetail.jsp?orderId="+$(rowobj).attr('ORDER_ID'), "orderWin", "销售订单详细信息", options);
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
    function afterDicItemClick(id,$row){
    	var ret = true;
    	if(id == "ORG_CODE")
    	{
    		$("#ORG_CODE").val($row.attr("CODE"));
    		$("#ORG_CODE").attr("code",$row.attr("CODE"));
    	}
    	return ret;
    }
</script>