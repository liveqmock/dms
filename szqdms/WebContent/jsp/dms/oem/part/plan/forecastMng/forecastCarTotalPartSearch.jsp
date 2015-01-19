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
                        <li><div class="button"><div class="buttonContent"><button type="button" id="di_searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="dia_page_grid">
            <table style="display:none;width:100%;" id="dia-tab-grid" multivals="div-selectedPart-search" name="tablist" ref="dia_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="multi" name="XH" unique="PART_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="COUNT" refer="createInputBox">数量</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <fieldset id="fie-selectedPart" style="display: ">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart-search">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;display:none" id="search-val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="search-val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="search-val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="search-val3" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="diafm-partInfo" method="post">
                <input type="hidden" id="diaforcastMonths" name="diaforcastMonths" datasource="FORCASTMONTH"/>
                <input type="hidden" id="diapartIds" name="diapartIds" datasource="PARTIDS"/>
                <input type="hidden" id="diapartCodes" name="diapartCodes" datasource="PARTCODES"/>
                <input type="hidden" id="diapartNames" name="diapartNames" datasource="PARTNAMES"/>
                <input type="hidden" id="diacounts" name="diacounts" datasource="COUNTS"/>
            </form>
    </div>
    </div>
</div>
<script type="text/javascript">
    var partSearchUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/searchPart.ajax";
    // 弹出窗体
    var dialog = $("body").data("partSearch");
    // 初始化函数
    $(function() {
    	$("#dia-tab-grid").attr("layoutH",document.documentElement.clientHeight-270);
        // 初始化查询(查询配件)
        searchPart();

        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            // 查询配件
            searchPart();
        });

        // 确定按钮绑定
        $('#btn-confirmPart').bind('click',function(){
            // 添加预测配件明细URL
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/insertTotalPartReportDetail.ajax";
            var partIds = $('#search-val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                // 配件预测主键ID
                $('#diaforcastMonths').val($('#edit-forecastMonth').val());
                $('#diapartIds').val($('#search-val0').val());
                $('#diapartCodes').val($('#search-val1').val());
                $('#diapartNames').val($('#search-val2').val());
                $('#diacounts').val($('#search-val3').val());

                var $f = $("#diafm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-confirmPart", sCondition, searchPartCallBack);
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
             $("#tab-grid-Edit tr:not(:first)").remove();
             $("#tab-grid-Edit").hide();
             $.pdialog.close(dialog);
             // 查询预测配件
             searchPromPart();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    // 查询配件
    function searchPart() {
        var $f = $("#dia-fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, partSearchUrl + "?forcastMonth=" + $('#edit-forecastMonth').val(), "di_searchPart", 1, sCondition, "dia-tab-grid");
        $('#fie-selectedPart').show();
    }
    // 将数量字段渲染为文本框
    function createInputBox(obj) {
        return '<input type="text" style="width:100px;" name="COUNT" onblur="doMyInputBlur(this)"/ >';
    }

    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj) {
        var $obj = $(obj);
        if($obj.val() == "") {//为空直接返回
            return ;
        }
        if($obj.val() && !isAmount($obj)) {//不为空并且数量不正确
            alertMsg.warn("请输入正确的数量！");
            return;
        }
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        if(s) {
            checkObj.attr("checked", true);
        }
        doSelectedBefore($tr,checkObj,1);
    }

    // 验证数字
    function isAmount($obj) {
        var reg = /^\+?[1-9][0-9]*$/;
        if(reg.test($obj.val())) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * $tr:当前行对象jquery 步骤二
     * @param $obj:checkbox的jQuery对象
     * @param type:
     */
    function doSelectedBefore($tr,$obj,type) {
        var $input = $tr.find("td").eq(4).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT") {
            s = $input.val();
        } else {
            s = $tr.find("td").eq(4).text();
        }
        doCheckbox($obj.get(0));
    }

    // 列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^\+?[1-9][0-9]*$/;//数量正则(最多两位小数)
        var s = "";
        if($tr.find("td").eq(4).find("input:first").size()>0) {
            s = $tr.find("td").eq(4).find("input:first").val();
        } else {
            s = $tr.find("td").eq(4).text();
        }
        if (!s || !reg.test(s)) {//为空或者不是数量
            alertMsg.warn("请输入正确的数量!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        // 配件ID
        arr.push($tr.attr("PART_ID"));
        // 配件代码
        arr.push($tr.attr("PART_CODE"));
        // 配件名称
        arr.push($tr.attr("PART_NAME"));
        // 数量
        arr.push(s);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart-search'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(4).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(4).html("<div><input type='text' name='myCount' style='width:100px;' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /*
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(4);
        var val;
        if($("#search-val3") && $("#search-val3").val()) {
            var t = $("#search-val3").val();
            var pks = $("#search-val0").val();
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