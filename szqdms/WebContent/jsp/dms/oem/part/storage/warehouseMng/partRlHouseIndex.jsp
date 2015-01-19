<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>配件与库位关系维护</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PartRlHouseMngAction/searchPartRlHouse.ajax";
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PartRlHouseMngAction/deletePartRlHouse.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: false, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                // 查询方法
                search();
            });
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/partRlHouseEdit.jsp?action=1", "editWin", "新增配件与库位关系", diaEditOptions);
            });
        });

        // 查询方法
        function search() {
            var $f = $("#fm-condition");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-grid");
        }
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/warehouseMng/partRlHouseEdit.jsp?action=2", "editWin", "修改配件与库位关系", diaEditOptions);
        }

        var $row;

        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?relationId=" + $(rowobj).attr("RELATION_ID");
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
            // 库区表选SRC
            var comm_areaSrc_first = "T#PT_BA_WAREHOUSE_AREA:AREA_CODE:AREA_NAME{AREA_ID,AREA_NAME,AREA_CODE}:1=1 AND WAREHOUSE_CODE='";
            var comm_areaSrc_last = "' AND AREA_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY AREA_CODE";
            // 库位表选SRC
            var comm_positionSrc_first = "T#PT_BA_WAREHOUSE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME,POSITION_CODE}:1=1 AND AREA_CODE='";
            var comm_positionSrc_last = "' AND POSITION_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY POSITION_CODE";
            // index页面,仓库表选
            if(id=="warehouseCode"){
                $("#warehouseId").val($row.attr("WAREHOUSE_ID"));
                $("#warehouseName").val($row.attr("WAREHOUSE_NAME"));
                // 库区文本框设置为""
                $('#areaCode').val("");
                // 库位文本框设置为""
                $('#positionCode').val("");
                var warehousecodeVal = $('#warehouseCode').attr("CODE");
                // 库区SRC属性
                var areaSrc = comm_areaSrc_first+warehousecodeVal+comm_areaSrc_last;
                $('#areaCode').attr("src",areaSrc);
            }
            // index页面,库区表选
            if (id=="areaCode") {
                $("#areaId").val($row.attr("AREA_ID"));
                $("#areaName").val($row.attr("AREA_NAME"));
                // 库位文本框设置为""
                $('#positionCode').val("");
                var areaCodeVal = $('#areaCode').attr("CODE");
                // 库位SRC属性
                var positionSrc = comm_positionSrc_first+areaCodeVal+comm_positionSrc_last;
                $('#positionCode').attr("src",positionSrc);
            }
            // Edit页面,仓库表选
            if(id=="dia-warehouseCode"){
                $("#dia-warehouseId").val($row.attr("WAREHOUSE_ID"));
                $("#dia-warehouseName").val($row.attr("WAREHOUSE_NAME"));
                // 库区文本框设置为""
                $('#dia-areaCode').val("");
                // 库位文本框设置为""
                $('#dia-positionCode').val("");
                var dia_warehousecodeVal = $('#dia-warehouseCode').attr("CODE");
                $("#dia-partCode").val();
                var accountSearchUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PartRlHouseMngAction/getArea.ajax?&PART_CODE="+$("#dia-partCode").val()+"&WAREHOUSE_ID="+ $("#dia-warehouseId").val();
                sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
  /*               // 库区SRC属性
                var dia_areaSrc = comm_areaSrc_first+dia_warehousecodeVal+comm_areaSrc_last;
                $('#dia-areaCode').attr("src",dia_areaSrc); */
            }
/*             // Edit页面,库区表选
            if (id=="dia-areaCode") {
                $("#dia-areaId").val($row.attr("AREA_ID"));
                $("#dia-areaName").val($row.attr("AREA_NAME"));
                $('#dia-areaCode').val($row.attr("AREA_NAME"));
                $('#dia-areaCode').attr("CODE",$row.attr("AREA_CODE"));
                // 库位文本框设置为""
                $('#dia-positionCode').val("");
                var dia_areaCodeVal = $('#dia-areaCode').attr("CODE");
                // 库位SRC属性
                var dia_positionSrc = comm_positionSrc_first+dia_areaCodeVal+comm_positionSrc_last;
                $('#dia-positionCode').attr("src",dia_positionSrc);
            } */
            // Edit页面,库位表选
            if (id=="dia-positionCode") {
                $("#dia-positionId").val($row.attr("POSITION_ID"));
                $("#dia-positionName").val($row.attr("POSITION_NAME"));
                $("#dia-positionCode").val($row.attr("POSITION_NAME"));
                $("#dia-positionCode").attr("CODE",$row.attr("POSITION_CODE"));
                
            }
            return true;
        }
        function accountSearchCallback(res){
            try {
            	var comm_positionSrc_first = "T#PT_BA_WAREHOUSE_POSITION:POSITION_CODE:POSITION_NAME{POSITION_ID,POSITION_NAME,POSITION_CODE}:1=1 AND AREA_CODE='";
                var comm_positionSrc_last = "' AND POSITION_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY POSITION_CODE";
                var rows = res.getElementsByTagName("ROW");
                if(rows && rows.length > 0)
                {
                    for(var i=0;i<rows.length;i++){
                        var AREA_ID = getNodeText(rows[i].getElementsByTagName("AREA_ID").item(0));
                        var AREA_NAME = getNodeText(rows[i].getElementsByTagName("AREA_NAME").item(0));
                        var AREA_CODE = getNodeText(rows[i].getElementsByTagName("AREA_CODE").item(0));
                        $("#dia-areaId").val(AREA_ID);
                        $("#dia-areaName").val(AREA_NAME);
                        $('#dia-areaCode').val(AREA_NAME);
                        $('#dia-areaCode').attr("CODE",AREA_CODE);
                        $('#dia-positionCode').val("");
                        var dia_areaCodeVal =AREA_CODE;
                        // 库位SRC属性
                        var dia_positionSrc = comm_positionSrc_first+dia_areaCodeVal+comm_positionSrc_last;
                        $('#dia-positionCode').attr("src",dia_positionSrc);
                    }
                }else{
                	alertMsg.warn('请维护库管员属性!');
                	return false;
                }
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
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：配件管理 &gt; 仓储管理 &gt; 仓库管理 &gt; 配件与库位关系维护</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-condition">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-condition">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="partCode" name="partCode" datasource="PBWPR.PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="partName" name="partName" datasource="PBWPR.PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>状态：</label></td>
                            <td>
                                <select type="text" id="status"  name="status" datasource="PBWPR.STATUS" kind="dic" src="YXBS" filtercode="" datatype="1,is_null,30" operation="=">
                                    <option value="-1" selected>--</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label>所属仓库：</label></td>
                            <td><input type="text" id="warehouseCode" name="warehouseCode" datasource="PBW.WAREHOUSE_CODE" datatype="1,is_null,30" operation="like" isreload="true"
                                       src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME,WAREHOUSE_CODE}:1=1 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" kind="dic"/>
                            </td>
                            <td><label>所属库区：</label></td>
                            <td><input type="text" id="areaCode" name="areaCode" datasource="PBWA.AREA_CODE" datatype="1,is_null,30" operation="like" isreload="true" src="" kind="dic"/></td>
                            <td><label>所属库位：</label></td>
                            <td><input type="text" id="positionCode" name="positionCode" datasource="PBWP.POSITION_CODE" datatype="1,is_null,30" operation="like" isreload="true" src="" kind="dic"/>
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
            <div id="page_grid">
                <table style="display:none;width:100%;" id="tab-grid" name="tablist" ref="page_grid" refQuery="tab-condition">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="WAREHOUSE_NAME">所属仓库</th>
                        <th fieldname="AREA_NAME">所属库区</th>
                        <th fieldname="POSITION_NAME">所属库位</th>
                        <th fieldname="STATUS">状态</th>
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