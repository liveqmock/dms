<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>收货地址变更审核</title>
<script type="text/javascript">
    var url = "<%=request.getContextPath()%>/part/salesMng/shipMent/DeliveAddChageAction";
    var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    //初始化方法
    $(function() {
    	searchDeliveryAddrss();
        //查询方法
        $("#btn-search").bind("click", function(event) {
            // 收货地址变更查询
            searchDeliveryAddrss();
        });
        $("#btn-reset").bind("click", function(event){
    		$("#fm-searchContract")[0].reset();
    		$("#orgCode").attr("code","");
    		$("#orgCode").val("");
    	});
    });

    // 收货地址变更查询
    function searchDeliveryAddrss() {
        var $f =$("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/orderExasearch.ajax";
        doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
    }

    //查看地址详细信息
    function toChageAdd(rowobj) {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/oem/part/storage/shipMng/deliveryaddrExaupdate.jsp", "deliveryaddrExaupdate", "交货地址变更审核", diaOptions);
    }
</script>
</head>
<body>
    <div id="layout" style="width: 100%;">
        <h4 class="contentTitle" align="left">
            <img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：仓储管理 &gt; 发运管理 &gt; 收货地址变更审核
        </h4>
        <div class="page">
            <div class="pageHeader">
                <form method="post" id="fm-searchContract">
                    <!-- 定义隐藏域条件 -->
                    <div class="searchBar" align="left">
                        <table class="searchContent" id="tab-ordersearch">
                            <!-- 定义查询条件 -->
                            <tr>
                                <td><label>渠道商：</label></td>
                                <td><input type="text" id="orgCode" name="orgCode" datasource="A.ORG_ID" datatype="1,is_null,1000" readOnly hasBtn="true" callFunction="showOrgTree('orgCode',1,1)" operation="in"/></td>
                                <td><label>订单编号：</label></td>
                                <td><input type="text" id="in-ORDER_ID" name="in-ORDER_ID" datasource="PBO.ORDER_ID" datatype="1,is_digit_letter,30" operation="like" /></td>
                                <td><label>提报日期：</label></td>
                                <td>
                                    <input type="text" group="in-kstbrq,in-jstbrq" id="in-applydateq" name="in-applydateq" style="width: 75px;" datasource="APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                        <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="in-applydateh" name="in-applydateh" style="width: 75px; margin-left: -30px;" datasource="APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                </td>
                            </tr>
                        </table>
                        <div class="subBar">
                            <ul>
                                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <div class="pageContent">
                <div id="page_orderlist">
                    <table style="display: none; width: 100%;" id="tab-contract" name="tablist" ref="page_orderlist" refQuery="fm-searchContract">
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display: none;"></th>
                                <th fieldname="ORDER_NO" >订单编号</th>
                                <th fieldname="ORG_CODE">渠道代码</th>
                                <th fieldname="ORG_NAME">渠道名称</th>
                                <th fieldname="DELIVERY_ADDR">现收货地址</th>
                                <th fieldname="ADDRESS">新收货地址</th>
                                <th fieldname="CHANGE_STATUS">变更状态</th>
                                <th colwidth="40" type="link" title="[审核]" action="toChageAdd">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>