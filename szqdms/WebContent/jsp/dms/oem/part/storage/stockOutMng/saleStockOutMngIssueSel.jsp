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
            <form method="post" id="fm-searchIssue">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchIssue">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>发料单号：</label></td>
                            <td><input type="text" id="sel-ISSUE_NO" name="sel-ISSUE_NO" datasource="ISSUE_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>采购订单号：</label></td>
                            <td><input type="text" id="sel-SALE_ORDER_NO" name="sel-SALE_ORDER_NO" datasource="ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchIssue">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeIssue">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-issueList">
                <table style="display:none;width:100%;" id="tab-issueList" name="tablist" ref="div-issueList"
                       refQuery="tab-searchIssue">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ISSUE_NO" colwidth="120">发料单号</th>
                            <th fieldname="USER_NAME">库管员</th>
                            <th fieldname="ORDER_NO">采购订单号</th>
                            <th fieldname="ORG_NAME">提报单位</th>
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
    var issueSelWin = $("body").data("issueSelWin");
    $(function () {
        $("#btn-closeIssue").click(function () {
            $.pdialog.close(issueSelWin);
            return false;
        });
        $("#btn-searchIssue").bind("click", function (event) {
            var searchIssueUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/SaleStockOutMngAction/searchIssue.ajax?WAREHOUSE_ID="+$('#dia-WAREHOUSE_ID').val()+"&WAREHOUSE_TYPE="+$('#dia-WAREHOUSE_TYPE').val();
            var $f = $("#fm-searchIssue");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchIssueUrl, "btn-searchIssue", 1, sCondition, "tab-issueList");
        });
    });

    function doSel(rowobj){
        $('#dia-ORDER_ID').val($(rowobj).attr('ISSUE_ID'));
        $('#dia-ORDER_NO').val($(rowobj).attr('ISSUE_NO'));
        $('#dia-ORG_NAME').val($(rowobj).attr('ORG_NAME'));
        $('#dia-USER_NAME').val($(rowobj).attr('USER_NAME'));
        $.pdialog.close(issueSelWin);
    }
</script>