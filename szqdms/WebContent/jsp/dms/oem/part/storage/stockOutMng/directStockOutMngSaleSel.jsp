<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchSale">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchSale">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>订单编号：</label></td>
                            <td><input type="text" id="sel-ORDER_NO" name="sel-ORDER_NO" datasource="ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>订单类型：</label></td>
                            <td>
                                <select class="combox" id="sel-ORDER_TYPE" name="sel-ORDER_TYPE" kind="dic" src="DDLX" datasource="ORDER_TYPE" filtercode="203701|203702|203703|203704|203706|203709" datatype="1,is_null,10">
                                    <option value="-1" selected>--</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>提报日期：</label></td>
                            <td>
                                <input  type="text" name="sel-APPLY_START_DATE" id="sel-APPLY_START_DATE" style="width:85px;" class="Wdate" operation=">=" group="sel-APPLY_START_DATE,sel-APPLY_END_DATE" datasource="APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="sel-APPLY_END_DATE" id="sel-APPLY_END_DATE" style="width:85px;margin-left:-28px;;" class="Wdate" operation="<=" group="sel-APPLY_START_DATE,sel-APPLY_END_DATE" datasource="APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                            <td><label>提报单位代码：</label></td>
                            <td><input type="text" id="sel-ORG_CODE" name="sel-ORG_CODE" datasource="ORG_CODE" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                            <td><label>提报单位名称：</label></td>
                            <td><input type="text" id="sel-ORG_NAME" name="sel-ORG_NAME" datasource="ORG_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>审核日期：</label></td>
                            <td>
                                <input  type="text" name="sel-CHECK_START_DATE" id="sel-CHECK_START_DATE" style="width:85px;" class="Wdate" operation=">=" group="sel-CHECK_START_DATE,sel-CHECK_END_DATE" datasource="CHECK_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="sel-CHECK_END_DATE" id="sel-CHECK_END_DATE" style="width:85px;margin-left:-28px;;" class="Wdate" operation="<=" group="sel-CHECK_START_DATE,sel-CHECK_END_DATE" datasource="CHECK_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>审核员：</label></td>
                            <td><input type="text" id="sel-CHECK_USER_SV" name="sel-CHECK_USER_SV" datasource="C.PERSON_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchSale">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeSale">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-saleList">
                <table style="display:none;width:100%;" id="tab-saleList" name="tablist" ref="div-saleList"
                       refQuery="tab-searchSale">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ORDER_NO" colwidth="135">订单编号</th>
                        <th fieldname="ORDER_TYPE" >订单类型</th>
                        <th fieldname="ORG_NAME">提报单位</th>
                        <%--<th fieldname="APPLY_DATE">提报时间</th>--%>
                        <th fieldname="CHECK_USER" colwidth="135">审核员</th>
                        <th fieldname="CHECK_DATE">审核时间</th>
                        <th type="link" title="操作" action="doSel" class="btnSelect">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var saleSelWin = $("body").data("saleSelWin");
    $(function () {
        $("#btn-closeSale").click(function () {
            $.pdialog.close(saleSelWin);
            return false;
        });
        $("#btn-searchSale").bind("click", function (event) {
            var searchSaleUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DirectStockOutMngAction/searchSale.ajax?WAREHOUSE_ID="+$('#dia-WAREHOUSE_ID').val();
            var $f = $("#fm-searchSale");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchSaleUrl, "btn-searchSale", 1, sCondition, "tab-saleList");
        });
    });

    function doSel(rowobj){
        $('#dia-ORDER_ID').val($(rowobj).attr('ORDER_ID'));
        $('#dia-ORDER_NO').val($(rowobj).attr('ORDER_NO'));
        $('#dia-ORG_NAME').val($(rowobj).attr('ORG_NAME'));
        $('#dia-CHECK_USER_SV').val($(rowobj).attr('CHECK_USER_SV'));
        $.pdialog.close(saleSelWin);
    }
</script>