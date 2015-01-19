<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<jsp:include page="/head.jsp"/>
<title>仓库管理</title>
<script type="text/javascript">
    var searchUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/WarehouseMngAction/searchWareHouse.ajax";
    var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/WarehouseMngAction/deleteWareHouse.ajax";
    // 定义弹出窗口样式
    var diaEditOptions = {max: false, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
    // 初始化
    $(function () {
        // 查询按钮响应
        $("#btn-search").bind("click", function (event) {
            // 查询仓库
            searchWareHouse();
        });
        // 新增按钮响应
        $("#btn-add").bind("click", function (event) {
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/warehouseEdit.jsp?action=1", "editWin", "新增仓库", diaEditOptions);
        });
    });

    // 查询仓库
    function searchWareHouse() {
    	var $f = $("#fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-grid");
    }
    // 列表编辑连接
    function doUpdate(rowobj) {
        $("td input[type=radio]", $(rowobj)).attr("checked", true);
        $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/warehouseEdit.jsp?action=2", "editWin", "修改仓库", diaEditOptions);
    }

    var $row;
    // 删除方法，rowobj：行对象，非jquery类型
    function doDelete(rowobj) {
        $row = $(rowobj);
        var url = deleteUrl + "?warehouseId=" + $(rowobj).attr("WAREHOUSE_ID")+"&warehousecode=" + $(rowobj).attr("WAREHOUSE_CODE");
        sendPost(url, "", "", deleteCallBack, "true");
    }
    // 删除回调方法
    function deleteCallBack(res) {
        try {
            if ($row)
                $("#tab-grid").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 仓库管理 &gt; 仓库管理</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-condition">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-condition">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>仓库代码：</label></td>
                            <td><input type="text" id="warehouseCode" name="warehouseCode" datasource="WAREHOUSE_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>仓库名称：</label></td>
                            <td><input type="text" id="warehouseName" name="warehouseName" datasource="WAREHOUSE_NAME" datatype="1,is_null,30" operation="like"/></td>
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
            <div id="page_grid">
                <table style="display:none;width:100%;" id="tab-grid" name="tablist" ref="page_grid" refQuery="tab-condition">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="WAREHOUSE_CODE">仓库代码</th>
                        <th fieldname="WAREHOUSE_NAME">仓库名称</th>
                        <th fieldname="WAREHOUSE_STATUS">仓库状态</th>
                        <th fieldname="PARAVALUE1">仓库类型</th>
                        <th fieldname="CODE">组织代码</th>
                        <th fieldname="ONAME">组织名称</th>
                        <th colwidth="85" type="link" title="[编辑]" action="doUpdate">操作</th>
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