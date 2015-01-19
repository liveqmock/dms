<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<div id="di_ychzdz" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
        <form method="post" id="fm-forecastTotalEdit" class="editForm" >
            <div align="left">
            <fieldset>
            <table class="editTable" id="di_ychzdzT">
                <tr>
                    <td><label>预测月份：</label></td>
                    <td><input type="text"  id="edit-forecastMonth" name="edit-forecastMonth" datasource="FORCAST_MONTH" readonly="readonly"/></td>
                </tr>
            </table>
            </fieldset>
            </div>
        </form>
        <form  method="post" id="partform">
        <div id="edit_page_grid">
        <table style="display:none;width:100%;" id="tab-grid-Edit" multivals="div-selectedPart" name="tablist" ref="edit_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="multi" name="XH" unique="PART_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="COUNT"  refer="showCountBtn">数量</th>
                        <th colwidth="85" type="link" title="[删除]" action="doDeleteEdit">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
            </div>
        </form>
        <fieldset id="fie-selectedPart" style="display:">
            <legend align="left" >&nbsp;[已选定配件]</legend>
            <div id="div-selectedPart">
                <table style="width:100%;">
                    <tr>
                        <td>
                            <textarea style="width:80%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                            <textarea style="width:80%;display:none" id="val1" name="multivals" readOnly></textarea>
                            <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                            <textarea style="width:80%;display:none" id="val3" name="multivals" readOnly></textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <form id="fm-partInfo" method="post">
            <input type="hidden" id="forcastMonths" name="forcastMonths" datasource="FORCASTMONTH"/>
            <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
            <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
            <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
            <input type="hidden" id="counts" name="counts" datasource="COUNTS"/>
        </form>
        <div class="formBar" id="opBtn" >
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-btn-search">新增配件</button></div></div></li>
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="saveTotal">保&nbsp;&nbsp;存</button></div></div></li>
            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doApplyTotal">提&nbsp;&nbsp;报</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
        </ul>
    </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    var action=<%=action%>;
    // URL
    var searchUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/searchForecastDetailByMonth.ajax";
    // 弹出窗体
    var dialog = $("body").data("totalEdit");
    $(function() {
    	$("#tab-grid-Edit").attr("layoutH",document.documentElement.clientHeight-240);
        // 修改操作
        if(action != "1"){
            var selectedRows = $("#tab-grid-index").getSelectedRows();
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
            // 预测月份赋值
            $("#edit-forecastMonth").val(selectedRows[0].attr("FORCAST_MONTH"));
        } else {
            // 预测月份赋值
            $("#edit-forecastMonth").val($("#search-forcastMonth").val());
        }
        // 查询预测配件明细
        searchPromPart();

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });

        // 新增配件按钮绑定
        $("#dia-btn-search").bind("click", function (event) {
            var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
            $.pdialog.open(webApps+"/jsp/dms/oem/part/plan/forecastMng/forecastCarTotalPartSearch.jsp", "partSearch", "预测配件信息", options);
       });

        // 保存按钮绑定
        $("#saveTotal").click(function(){
            var partIds = $('#val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                var insertPromPartUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/insertTotalPartReportDetail.ajax";
                // 配件预测主键ID
                $('#forcastMonths').val($('#edit-forecastMonth').val());
                $('#partIds').val($('#val0').val());
                $('#partCodes').val($('#val1').val());
                $('#partNames').val($('#val2').val());
                $('#counts').val($('#val3').val());

                var $f = $("#fm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doNormalSubmit($f, insertPromPartUrl, "saveTotal", sCondition, savePartCallBack);
            }
        });

        // 提报按钮
        $("#doApplyTotal").click(function(){
            var doApplyTotalUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastCarTotalMngAction/updateForecast.ajax";
            doApplyTotalUrl = doApplyTotalUrl + "?forecastMonth=" + $("#edit-forecastMonth").val();
            sendPost(doApplyTotalUrl, "", "", doApplyTotalCallBack, "true");
        });
    });

    // 确定按钮回调方法
    function savePartCallBack () {
        try {
            $("#edit-tab tr:not(:first)").remove();
            $("#edit-tabl").hide();
            // 查询预测配件
            searchPromPart();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 提报按钮回调函数
    function doApplyTotalCallBack(res) {
        var rows = res.getElementsByTagName("ROW");
        // 读取XML中的FLAG属性(FLAG:true今天不在提报日内;)
        var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
        if (flag == "true") {
            alertMsg.info("今天不在提报日内!");
            return false;
        }
        // 查询汇总调整
        forecastCarTotalReport();
        $.pdialog.closeCurrent();
        return false;
    }

    function showCountBtn(obj){
        obj.html("<input type='text' name='COUNT' onblur='doMyInputBlur(this)' style='width:100px' value='" + obj.html() +"'/>");
    }

    // 查询预测配件明细
    function searchPromPart(){

        var $f = $("#fm-forecastTotalEdit");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "", 1, sCondition, "tab-grid-Edit");
        $("#tab-grid-Edit").show();
        $("#tab-grid-Edit").jTable();
    }
    
    // 删除方法
    function doDeleteEdit (rowObj) {
        $row = $(rowObj);
        var deleteForecastUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastTotalMngAction/deleteForecastDetail.ajax";
        var url = deleteForecastUrl + "?forecastMonth=" + $('#edit-forecastMonth').val() +"&partCode=" + $(rowObj).attr("PART_CODE");
        sendPost(url, "", "", doDeleteEditCallBack, "true");
    }

    // 删除回调方法
    function doDeleteEditCallBack(res) {
        try {
            if ($row)
                $("#tab-grid-Edit").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
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
        var reg = /^[0-9]*$/;
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
        var reg = /^[0-9]*$/;//数字正则
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
        multiSelected($checkbox, arr,$('#div-selectedPart'));
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
        if($("#val3") && $("#val3").val()) {
            var t = $("#val3").val();
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