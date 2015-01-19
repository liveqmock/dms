<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="dia-fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="di_ycpjTable">
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="dia-partCode-Search" name="dia-partCode-Search" datasource="PART_CODE" datatype="1,is_null,100" operation="like" /></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="dia-partName-Search" name="dia-partName-Search" datasource="PART_NAME" datatype="1,is_null,100" operation="like" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="dia_page_grid">
            <table style="display:none;width:100%;" id="dia-tab-grid" multivals="div-selectedPart" name="tablist" ref="dia_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="multi" name="XH" unique="PART_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <fieldset id="fie-selectedPart" style="display:">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="diafm-partInfo" method="post">
                <input type="hidden" id="diaforcastMonths" name="diaforcastMonths" datasource="ASSEMBLYID"/>
                <input type="hidden" id="diapartIds" name="diapartIds" datasource="PARTIDS"/>
                <input type="hidden" id="diapartCodes" name="diapartCodes" datasource="PARTCODES"/>
                <input type="hidden" id="diapartNames" name="diapartNames" datasource="PARTNAMES"/>
            </form>
    </div>
    </div>
</div>
<script type="text/javascript">
    var partSearchUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/searchPart.ajax";
    // 弹出窗体
    var dialog = $("body").data("partSearch");
    // 初始化函数
    $(function() {
    	$("#dia-tab-grid").attr("layoutH",document.documentElement.clientHeight-270);
        // 初始化查询配件
        forecastPartSearch();
        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            // 查询配件方法
            forecastPartSearch();
        });

        // 确定按钮绑定
        $('#btn-confirmPart').bind('click',function(){
            // 添加预测配件明细URL
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/insertPartReportDetail.ajax";
            var partIds = $('#val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                // 配件预测主键ID
                $('#diaforcastMonths').val($('#assemblyId').val());
                $('#diapartIds').val($('#val0').val());
                $('#diapartCodes').val($('#val1').val());
                $('#diapartNames').val($('#val2').val());

                var $f = $("#diafm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-search", sCondition, searchPartCallBack);
            }
        });

        // 关闭按钮绑定
        $("button.close").click(function() {
            $.pdialog.close(dialog);
            return false;
        });
    });

    // 确定按钮回调方法
    function searchPartCallBack () {
        try {
            $("#edit-tab tr:not(:first)").remove();
            $("#edit-tabl").hide();
             $.pdialog.close(dialog);
             // 查询预测配件
             searchPromPart();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 查询配件方法
    function forecastPartSearch() {
        var $f = $("#dia-fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, partSearchUrl + "?assemblyId=" + $('#assemblyId').val(), "di_searchPart", 1, sCondition, "dia-tab-grid");
    }

    // 列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        // 配件ID
        arr.push($tr.attr("PART_ID"));
        // 配件代码
        arr.push($tr.attr("PART_CODE"));
        // 配件名称
        arr.push($tr.attr("PART_NAME"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
    }
    /*
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(4);
        var val;
        if($("#val2") && $("#val2").val()) {
            var t = $("#val2").val();
            var pks = $("#val0").val();
            var ss = t.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++) {
                if(pk[i] == checkVal) {
                    val = ss[i];
                    break;
                }
            }
        }
        if(val) {
            $inputObj.html("<div>"+val+"</div>");
        }
    }
</script>