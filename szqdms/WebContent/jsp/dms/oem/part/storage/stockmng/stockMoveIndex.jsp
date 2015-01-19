<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>移位管理</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockMng/StockMoveAction/searchStockDtl.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: false, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchStockDtl");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-stockDtlList");
            });
        });

        function doMove(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockmng/stockMoveEdit.jsp", "editWin", "移动库位", diaEditOptions);
        }

        function ctrlShow(obj){
            var $tr =$(obj).parent();
            if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
                return "";
            }else{
                return $(obj).text();
            }
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 库存管理 &gt; 移位管理</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchStockDtl">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchStockDtl">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="PART_CODE" name="PART_CODE" datasource="PBS.PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="PART_NAME" name="PART_NAME" datasource="PBS.PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>仓库名称：</label></td>
                            <td>
                                <input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="PBS.WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> ORDER BY WAREHOUSE_CODE" datatype="1,is_null,30"/>
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
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-stockDtlList">
                <table style="display:none;width:100%;" id="tab-stockDtlList" name="tablist" ref="div-stockDtlList"
                       refQuery="tab-searchStockDtl">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display: none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="SUPPLIER_NAME" colwidth="85" refer="ctrlShow">供应商</th>
                        <th fieldname="WAREHOUSE_CODE">仓库代码</th>
                        <th fieldname="WAREHOUSE_NAME">仓库名称</th>
                        <th fieldname="AREA_CODE">库区代码</th>
                        <th fieldname="AREA_NAME">库区名称</th>
                        <th fieldname="POSITION_CODE">库位代码</th>
                        <th fieldname="POSITION_NAME">库位名称</th>
                        <th fieldname="AMOUNT" colwidth="70">库位库存</th>
                        <th fieldname="OCCUPY_AMOUNT" colwidth="70">占用库存</th>
                        <th fieldname="AVAILABLE_AMOUNT" colwidth="70">可用库存</th>
                        <th colwidth="85" type="link" title="[移位]" action="doMove">操作</th>
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