<?xml version="1.0" encoding="UTF-8" ?>
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
            <form method="post" id="fm-searchPchRet">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPchRet">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>采购退货单号：</label></td>
                            <td><input type="text" id="sel-RETURN_NO" name="sel-RETURN_NO" datasource="A.RETURN_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>采购拆分订单号：</label></td>
                            <td><input type="text" id="sel-SPLIT_NO" name="sel-SPLIT_NO" datasource="B.SPLIT_NO" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPchRet">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closePchRet">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-pchRetList">
                <table style="display:none;width:100%;" id="tab-pchRetList" name="tablist" ref="div-pchRetList"
                       refQuery="tab-searchPchRet">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="RETURN_NO" colwidth="120">采购退货单号</th>
                            <th fieldname="SPLIT_NO" colwidth="120">采购拆分单号</th>
                            <th fieldname="SUPPLIER_NAME">供应商</th>
                            <th fieldname="ORDER_USER">制单人</th>
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
    var pchRetSelWin = $("body").data("pchRetSelWin");
    $(function () {
        $("#btn-closePchRet").click(function () {
            $.pdialog.close(pchRetSelWin);
            return false;
        });
        $("#btn-searchPchRet").bind("click", function (event) {
            var searchPchRetUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/PchRetStockOutMngAction/searchPchRet.ajax";
            var $f = $("#fm-searchPchRet");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchPchRetUrl, "btn-searchPchRet", 1, sCondition, "tab-pchRetList");
        });
    });

    function doSel(rowobj){
        $('#dia-ORDER_ID').val($(rowobj).attr('RETURN_ID'));
        $('#dia-ORDER_NO').val($(rowobj).attr('RETURN_NO'));
        $('#dia-SUPPLIER_NAME').val($(rowobj).attr('SUPPLIER_NAME'));
        $('#dia-ORDER_USER_SV').val($(rowobj).attr('ORDER_USER_SV'));
        $.pdialog.close(pchRetSelWin);
    }
</script>