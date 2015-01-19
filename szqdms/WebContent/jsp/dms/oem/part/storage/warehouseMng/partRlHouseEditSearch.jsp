<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia-partCode" name="dia-partCode" datasource="PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-partName" name="dia-partName" datasource="PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-partList" multivals="div-selectedPart" name="tablist" ref="div-partList" refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="single" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="" refer="appendPack_Unit">最小包装</th>
                           </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    $(function () {

        // 查询配件
        searchPart();

        // 关闭按钮绑定
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });

        // 查询按钮绑定
        $("#btn-searchPart").bind("click", function (event) {
            // 查询配件
            searchPart();
        });
    });

    // 查询配件
    function searchPart(){
        //查询配件URL
        var searchPartUrl = "<%=request.getContextPath()%>/part/storageMng/warehouseMng/PartRlHouseMngAction/searchPart.ajax";
        var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
    }
    // 最小包装数+最小包装单位(例:10/包)
    function appendPack_Unit(obj) {
        var $tr = $(obj).parent();
        var minPack = $tr.attr("MIN_PACK");
        var minUnit = $tr.attr("MIN_UNIT_sv");
        if (minPack==null) {
            minPack="";
        }
        if (minUnit == null) {
            minUnit="";
        }
        return minPack + '/' + minUnit;
    }

    // 单选按钮回调函数
    function doRadio(res){
        //getSelectedRows():获取列表选中行对象，返回选中行数组
        var selectedRows = $("#tab-partList").getSelectedRows();
        $("#dia-partId").val(selectedRows[0].attr("PART_ID"));
        $("#dia-partCode").val(selectedRows[0].attr("PART_CODE"));
        $("#dia-partName").val(selectedRows[0].attr("PART_NAME"));
        // 关闭窗口
        $.pdialog.close(partSelWin);
        return false;
    }
</script>