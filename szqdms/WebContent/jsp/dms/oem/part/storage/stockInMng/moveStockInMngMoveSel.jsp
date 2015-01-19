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
            <form method="post" id="fm-searchMove">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchMove">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>移库出库单号：</label></td>
                            <td><input type="text" id="sel-OUT_NO" name="sel-OUT_NO" datasource="OUT_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>出库仓库：</label></td>
                            <td>
                                <input type="text" id="sel-OUT_WAREHOUSE_CODE" name="sel-OUT_WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> ORDER BY WAREHOUSE_CODE" datatype="1,is_null,30"/>
                            </td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchMove">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closeMove">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-moveList">
                <table style="display:none;width:100%;" id="tab-moveList" name="tablist" ref="div-moveList"
                       refQuery="tab-searchMove">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="OUT_NO" colwidth="120">移库出库单号</th>
                            <th fieldname="OUT_WAREHOUSE_CODE">出库仓库代码</th>
                            <th fieldname="OUT_WAREHOUSE_NAME">出库仓库名称</th>
                            <th fieldname="OUT_CREATE_USER">出库制单人</th>
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
    var moveSelWin = $("body").data("moveSelWin");
    $(function () {
        $("#btn-closeMove").click(function () {
            $.pdialog.close(moveSelWin);
            return false;
        });
        $("#btn-searchMove").bind("click", function (event) {
            var searchMoveUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/MoveStockInMngAction/searchMove.ajax?warehouseId="+$('#dia-WAREHOUSE_ID').val();
            var $f = $("#fm-searchMove");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchMoveUrl, "btn-searchMove", 1, sCondition, "tab-moveList");
        });
    });

    function doSel(rowobj){
        $('#dia-ORDER_ID').val($(rowobj).attr('OUT_ID'));
        $('#dia-ORDER_NO').val($(rowobj).attr('OUT_NO'));
        $('#dia-OUT_CREATE_USER').val($(rowobj).attr('OUT_CREATE_USER'));
        $('#dia-OUT_CREATE_USER_SV').val($(rowobj).attr('OUT_CREATE_USER_SV'));
        $('#dia-OUT_WAREHOUSE_ID').val($(rowobj).attr('OUT_WAREHOUSE_ID'));
        $('#dia-OUT_WAREHOUSE_CODE').val($(rowobj).attr('OUT_WAREHOUSE_CODE'));
        $('#dia-OUT_WAREHOUSE_NAME').val($(rowobj).attr('OUT_WAREHOUSE_NAME'));
        $.pdialog.close(moveSelWin);
    }
</script>