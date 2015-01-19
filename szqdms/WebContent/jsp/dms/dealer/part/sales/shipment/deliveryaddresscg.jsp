<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>收货地址变更</title>
<script type="text/javascript">
    //变量定义
    //查询提交方法
    var url = "<%=request.getContextPath()%>/part/salesMng/shipMent/DeliveAddChageAction";
    var diaOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    //初始化方法
    $(function() {

        // 查询方法
        $("#btn-search").bind("click", function(event) {
            // 查询收货地址
            deliveryaddresscg();
        });

        // 新增方法
        $("#btn-add").bind("click", function(event) {
            $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/shipment/deliveryaddressupdate.jsp?action=1", "deliveryaddressupdate", "交货地址变更", diaOptions);
        });
    }); 

    // 查询收货地址
    function deliveryaddresscg() {
        var $f =$("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/searchDeliveryaddr.ajax";
        doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-contract");
    }

    // 编辑方法
    function doUpdate(rowobj) {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/shipment/deliveryaddressupdate.jsp?action=2", "deliveryaddressupdate", "交货地址变更", diaOptions);
    }

    // 删除方法
    function doDelete(rowobj){
        var deleteUrl = "<%=request.getContextPath()%>/part/salesMng/shipMent/DeliveAddChageAction/deleteChangeShipAddress.ajax";
        $row = $(rowobj);
        var url = deleteUrl + "?addId=" + $(rowobj).attr("ADD_ID");
        sendPost(url, "", "", deleteCallBack, "true");
    }

    //删除回调方法
    function deleteCallBack(res) {
        try {
            if ($row)
                $("#tab-contract").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>
</head>
<body>
    <div id="layout" style="width: 100%;">
        <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：销售管理 &gt; 发运管理 &gt; 收货地址变更申请</h4>
        <div class="page">
            <div class="pageHeader">
                <form method="post" id="fm-searchContract">
                    <!-- 定义隐藏域条件 -->
                    <div class="searchBar" align="left">
                        <table class="searchContent" id="tab-ordersearch">
                            <!-- 定义查询条件 -->
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><input type="text" id="in-ORDER_ID" name="in-ORDER_ID" datasource="A.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                                <td><label>订单提报日期：</label></td>
                                <td>
                                    <input type="text" group="in-kstbrq,in-jstbrq" id="in-applydateq" name="in-applydateq" style="width: 75px;" datasource="B.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/>
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="in-applydateh" name="in-applydateh" style="width: 75px; margin-left: -30px;" datasource="B.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                </td>
                            </tr>
                        </table>
                        <div class="subBar">
                            <ul>
                                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
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
                                <th fieldname="ADDRESS">现交货地址</th>
                                <th fieldname="CHANGE_STATUS">变更状态</th>
                                <th colwidth="80" type="link"   title="[编辑]|[删除]" action="doUpdate|doDelete">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>