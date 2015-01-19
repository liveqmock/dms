<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgId = user.getOrgId();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>其他入库</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction/searchInBill.ajax";
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction/deleteInBill.ajax";
        var completeUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction/completeInBill.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	searchStockIn();
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                searchStockIn();
            });
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/otherStockInMngEdit.jsp?action=1", "editWin", "新增入库单", diaEditOptions);
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/otherStockInMngEdit.jsp?action=2", "editWin", "修改入库单", diaEditOptions);
        }

        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?inId=" + $(rowobj).attr("IN_ID");
            sendPost(url, "", "", deleteCallBack, "true");
        }
        //删除回调方法
        function deleteCallBack(res) {
            try {
                if ($row)
                    $("#tab-inBillList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }
        // 查询入库单
        function searchStockIn() {
        	var $f = $("#fm-searchInBill");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-inBillList");
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 入库管理 &gt; 其他入库</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchInBill">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchInBill">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>入库单号：</label></td>
                            <td><input type="text" id="IN_NO" name="IN_NO" datasource="A.IN_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>入库仓库：</label></td>
                            <td>
                                <input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="A.WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> AND ORG_ID = <%=orgId %> ORDER BY WAREHOUSE_CODE" datatype="1,is_null,30"/>
                            </td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-search">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-add">新&nbsp;&nbsp;增</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-inBillList">
                <table style="display:none;width:100%;" id="tab-inBillList" name="tablist" ref="div-inBillList"
                       refQuery="tab-searchInBill">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="IN_NO" colwidth="135">入库单号</th>
                        <th fieldname="IN_TYPE" >入库类型</th>
                        <th fieldname="WAREHOUSE_NAME">入库仓库</th>
                        <th type="link" title="[编辑]"  action="doUpdate" >操作</th>
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