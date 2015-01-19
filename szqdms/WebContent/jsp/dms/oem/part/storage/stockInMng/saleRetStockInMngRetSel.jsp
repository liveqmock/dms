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
            <form method="post" id="fm-searchRet">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchRet">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>销售退件单号：</label></td>
                            <td><input type="text" id="sel-RETURN_NO" name="sel-RETURN_NO" datasource="RETURN_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配送中心：</label></td>
                            <td><input type="text" id="sel-APPLY_ORG_NAME" name="sel-APPLY_ORG_NAME" datasource="APPLY_ORG_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchRet">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeRet">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-retList">
                <table style="display:none;width:100%;" id="tab-retList" name="tablist" ref="div-retList"
                       refQuery="tab-searchRet">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="RETURN_NO" colwidth="120">销售退件单号</th>
                            <th fieldname="APPLY_ORG_CODE">配送中心代码</th>
                            <th fieldname="APPLY_ORG_NAME">配送中心名称</th>
                            <th fieldname="RETURN_COUNT">退件数量</th>
                            <th fieldname="RETURN_AMOUNT" refer="formatAmount" align="right">退件金额</th>
                            <th fieldname="CHECK_USER">审核员</th>
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
    var retSelWin = $("body").data("retSelWin");
    $(function () {
        $("#btn-closeRet").click(function () {
            $.pdialog.close(retSelWin);
            return false;
        });
        $("#btn-searchRet").bind("click", function (event) {
            var searchRetUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleRetStockInMngAction/searchRet.ajax?WAREHOUSE_ID="+$('#dia-WAREHOUSE_ID').val();
            var $f = $("#fm-searchRet");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchRetUrl, "btn-searchRet", 1, sCondition, "tab-retList");
        });
    });

    function doSel(rowobj){
        $('#dia-ORDER_ID').val($(rowobj).attr('RETURN_ID'));
        $('#dia-ORDER_NO').val($(rowobj).attr('RETURN_NO'));
        $('#dia-APPLY_ORG_NAME').val($(rowobj).attr('APPLY_ORG_NAME'));
        $('#dia-CHECK_USER_SV').val($(rowobj).attr('CHECK_USER_SV'));
        $.pdialog.close(retSelWin);
    }
    function formatAmount(obj){
        return amountFormatNew($(obj).html());
    }
</script>