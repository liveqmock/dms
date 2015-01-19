<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>库位管理</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PositionHouseMngAction/searchPositionHouse.ajax";
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PositionHouseMngAction/deletePositionHouse.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: false, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                // 查询库位
                searchPositionHouse();
            });
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/positionHouseEdit.jsp?action=1", "editWin", "新增库位", diaEditOptions);
            });
        });

        // 查询库位
        function searchPositionHouse() {
            var $f = $("#fm-condition");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-grid");
        }
        
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/positionHouseEdit.jsp?action=2", "editWin", "修改库位", diaEditOptions);
        }

        var $row;

        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?positionId=" + $(rowobj).attr("POSITION_ID");
            sendPost(url, "", "", deleteCallBack, "true");
        }

        //删除回调方法
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

        // 数据字典回调函数
        function afterDicItemClick(id,$row){
            if(id=="warehouseCode"){
                $("#warehouseId").val($row.attr("WAREHOUSE_ID"));
                $("#warehouseName").val($row.attr("WAREHOUSE_NAME"));
                $('#areaCode').val("");
                var warehousecodeVal = $('#warehouseCode').attr("CODE");
                var areaSrc = "T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME}:1=1 AND WAREHOUSE_CODE='"+warehousecodeVal+"' AND AREA_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY AREA_CODE";
                $('#areaCode').attr("src",areaSrc);
            }
            if(id=="dia-warehouseCode"){
                $("#dia-warehouseId").val($row.attr("WAREHOUSE_ID"));
                $("#dia-warehouseName").val($row.attr("WAREHOUSE_NAME"));
                $('#dia-areaCode').val("");
                var warehousecodeVal = $('#dia-warehouseCode').attr("CODE");
                var dia_areaSrc = "T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME}:1=1 AND WAREHOUSE_CODE='"+warehousecodeVal+"' AND AREA_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY AREA_CODE";
                $('#dia-areaCode').attr("src",dia_areaSrc);
            }
            if (id=="dia-areaCode") {
                $("#dia-areaId").val($row.attr("AREA_ID"));
                $("#dia-areaName").val($row.attr("AREA_NAME"));
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
        配件管理 &gt; 仓储管理 &gt; 仓库管理 &gt; 库位管理</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-condition">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-condition">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>库位代码：</label></td>
                            <td><input type="text" id="positionCode" name="positionCode" datasource="PBSP.POSITION_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>库位名称：</label></td>
                            <td><input type="text" id="positionName" name="positionName" datasource="PBSP.POSITION_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                            <td><label>所属仓库：</label></td>
                            <td><input type="text" id="warehouseCode" name="warehouseCode" datasource="PBSA.WAREHOUSE_CODE" datatype="1,is_null,30" operation="like" isreload="true"
                                       src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" kind="dic"/>
                            </td>
                            <td><label>所属库区：</label></td>
                            <td><input type="text" id="areaCode" name="areaCode" datasource="PBSP.AREA_CODE" datatype="1,is_null,30" operation="like" isreload="true" src="" kind="dic"/></td>
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
                        <th fieldname="POSITION_CODE">库位代码</th>
                        <th fieldname="POSITION_NAME">库位名称</th>
                        <th fieldname="WAREHOUSE_NAME">所属仓库</th>
                        <th fieldname="AREA_NAME">所属库区</th>
                        <th fieldname="POSITION_STATUS">库位状态</th>
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