<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.org.dms.dao.part.salesMng.orderMng.PartOrderDtlQueryMngDao"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	PartOrderDtlQueryMngDao dao = new PartOrderDtlQueryMngDao();
	Connection conn = dao.getConnection();
	boolean isCheckUser = dao.checkUserIsCheckOrder(user);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>销售明细查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：信息查询 &gt; 销售相关 &gt; 销售明细查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="ycsbTable">
                    <tr>
	                    <td><label>渠道商：</label></td>
	                    <td>
	                        <input type="text" id="orgName" name="orgName" datasource="" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/>
	                        <input type="hidden" id="orgId" name="orgId" datatype="1,is_null,30" datasource="T.ORG_ID" operation="="/>
	                    </td>
                        <td><label>订单关闭日期：</label></td>
                        <td>
                            <input type="text" group="startDate1,endDate1"  id="startDate1" kind="date" name="startDate1" style="width:75px;" datasource="T.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="startDate1,endDate1"  id="endDate1" kind="date" name="endDate1" style="width:75px;margin-left:-30px;" datasource="T.CLOSE_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
                        </td>
                    </tr>
                    <%
                    	if(!isCheckUser){
                    %>
                     <tr>
                        <td><label>审核员：</label></td>
                        <!-- 20141117:需求变更，审核员非必填 -->
                        <td colspan="3"><input type="text" id="saleuserName" name="saleuserName" datatype="1,is_null,30" datasource="T.CHECK_USER" operation="like"/></td>
                    </tr>               
                    <%
                    	}
                    %>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-clear">重&nbsp;&nbsp;置</button></div></div></li>
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
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="UNIT_PRICE" refer="amountFormat" align="right" colwidth="80px">单价(元)</th>
<!--                         <th fieldname="SALE_PRICE" refer="amountFormat" align="right" colwidth="80px">经销商价(元)</th> -->
                        <th fieldname="ORDER_NO" colwidth="150px">订单号</th>
                        <th fieldname="CHECK_USER">审核员</th>
                        <th fieldname="CLOSE_DATE" colwidth="130px">订单关闭日期</th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="ORDER_COUNT" colwidth="60px">订单数量</th>
                        <th fieldname="DELIVERY_COUNT" colwidth="60px">实发数量</th>
                        <th fieldname="UNIT" colwidth="60px">计量单位</th>
                        <th fieldname="AMOUNT" refer="amountFormat" align="right">实发金额(元)</th>
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
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderDtlQueryMngAction/searchOrderDtl.ajax";
    // 初始化方法
     $(function(){

         // 查询按钮响应方法
         $("#btn-search-index").click(function(){
             // 订单查询
             searchOrder();
         });
         
         // 清除按钮
         $("#btn-search-clear").click(function(){
        	 $("input", "#ycsbTable").each(function(index, obj){
        		$(obj).val(""); 
        	 });
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
             var url = encodeURI("<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderDtlQueryMngAction/download.do");
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

    // 
    function showCountBtn(obj){
        obj.html("<a href='#' onclick='doDetail(this)' class='op'>" + obj.html() + "</a>");
    }

    // 订单明细
    function doDetail(obj){
        var $obj = $(obj);
        var $tr = $obj.parent().parent().parent();
    	$tr.find("td input:first").attr("checked",true);
        var orderId = $tr.attr("ORDER_ID");
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/sales/orderMng/searchInfo/partorderQueryDtl.jsp?orderId="+orderId, "orderDtl", "订单明细", options);
    }

    //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }

    // 组织树的回调函数
    function showOrgTreeCallBack (res) {
    	// 渠道名称
    	$("#orgName").val($(res).attr("orgName"));
    	// 渠道代码
    	$("#orgId").val($(res).attr("orgId"));
    }

</script>