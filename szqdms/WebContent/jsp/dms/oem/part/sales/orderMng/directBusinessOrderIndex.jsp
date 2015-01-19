<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>直营订单提报</title>
<script type="text/javascript">
    //查询提交方法
    var url = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction";
    //初始化方法
    $(function(){

        // 查询按钮绑定
        $("#btn-search").bind("click",function(event){
            // 查询方法
            searchDirectBusinessOrder();
        });
        $("#btn-search").trigger("click");
        //新增方法
        $("#btn-xz").bind("click",function(event){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/directBusinessOrderEdit.jsp?action=1", "ddxxwhxz", "订单信息新增", options);
        });
    });

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询直营订单
    function searchDirectBusinessOrder() {
        var $f = $("#fm-index");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, url+"/searchDirectBusinessOrder.ajax?flag=false", "btn-search", 1, sCondition, "tab-index");
    }

    //提报方法
    function doReport(rowobj){
        $row = $(rowobj);
        if($row.attr("ORDER_AMOUNT").length==0){
            alertMsg.warn("请添加订单所需订购的配件信息后,再进行提报.");
            return false;
        }
        var reportUrl = url + "/partOrderReport.ajax?&orderId="+$row.attr("ORDER_ID")+"&orderType="+"&orderAmount="+$row.attr("ORDER_AMOUNT");
        sendPost(reportUrl, "", "",reportCallBack, "true");
    }

  //提报回调方法
    function reportCallBack(res) {
        try {
            if ($row)
                $("#tab-index").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //列表编辑链接
    function doUpdate(rowobj) {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/directBusinessOrderEdit.jsp?action=2", "ddxxwhxg", "订单信息维护", options);
    }

//     // 删除方法
//     function doDelete(rowobj){
<%--         var deleteUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction/deleteDirectBusinessOrder.ajax"; --%>
//         $row = $(rowobj);
//         var url = deleteUrl + "?orderId=" + $(rowobj).attr("ORDER_ID");
//         sendPost(url, "", "", deleteCallBack, "true");
//     }

//     //删除回调方法
//     function deleteCallBack(res) {
//         try {
//             if ($row)
//                 $("#tab-index").removeResult($row);
//         } catch (e) {
//             alertMsg.error(e);
//             return false;
//         }
//         return true;
//     }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 直营订单提报</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datasource="ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>订单状态：</label></td>
                        <td>
                            <select type="text" id="orderStatus"  name="orderStatus" datasource="ORDER_STATUS" kind="dic" src="DDZT" filtercode="202201|202204" datatype="1,is_null,30" >
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index" >
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-pjcx" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ORDER_NO" ordertype='local' class="desc" colwidth="135">订单编号</th>
                            <th fieldname="ORDER_TYPE" >订单类型</th>
                            <th fieldname="WAREHOUSE_NAME" >供货配件库</th>
<!--                             <th fieldname="EXPECT_DATE" >期望到货日期</th> -->
                            <th fieldname="ORDER_AMOUNT" align="right" refer="formatAmount">总金额(元)</th>
                            <th fieldname="ORDER_STATUS" >订单状态</th>
                            <th fieldname="CREATE_USER" >提报人</th>
                            <th fieldname="APPLY_DATE" colwidth="130">提报时间</th>
                            <th colwidth="85" type="link" title="[提报]|[编辑]"  action="doReport|doUpdate" >操作</th>
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