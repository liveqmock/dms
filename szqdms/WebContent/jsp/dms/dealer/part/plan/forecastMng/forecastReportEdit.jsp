<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    String yearStr1 = yearFormat.format(Pub.getCurrentDate());
    String monthStr1 = monthFormat.format(Pub.getCurrentDate());
    String yearStr2 = "";
    String monthStr2 = "";
    if ("12".equals(monthStr1)) {
        Integer intYear = Integer.valueOf(yearStr1) + 1;
        // 年
        yearStr2 = String.valueOf(intYear);
        // 月
        monthStr2 = "01";
    } else {
       // 年
       yearStr2 = yearStr1;
       Integer intMonth = Integer.valueOf(monthStr1) + 1;
       // 月
       monthStr2 = String.valueOf(intMonth);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
        <div class="page">
        <div class="pageContent" style="" >
            <form  method="post" id="fm-forecastReportEdit-R">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="r-edit-forcastId" name="r-edit-forcastId" datasource="FORCAST_ID"/>
            </form>
            <form method="post" id="fm-forecastReportEdit-W" class="editForm" >
                <%--隐藏域查询条件--%>
                <input type="hidden" id="edit-forcastId" name="edit-forcastId" datasource="FORCAST_ID"/>
                <div align="left">
                <fieldset>
                <table class="editTable" id="ycsbxzTable">
                    <tr>
                        <td><label>预测月份：</label></td>
                        <td>
                             <select id="edit-forcastMonth">
                                <option value=<%=yearStr1+"-"+monthStr1%>><%=yearStr1+"-"+monthStr1%></option>
                                <option value=<%=yearStr2+"-"+monthStr2%>><%=yearStr2+"-"+monthStr2%></option>
                            </select>
                        </td>
                     </tr>
                </table>
                </fieldset>
                </div>
                <div id="formBar" class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-btn-search">新增配件</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doSave">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doExp">导出模版</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doImp">批量导入</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doApply">提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                        
                    </ul>
                </div>
            </form>
        </div>
        <div id="edit_page_grid">
            <table style="display:none;width:100%;" id="edit-tab" multivals="div-selectedPart-Edit" name="tablist" ref="edit_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="multi" name="XH" unique="PART_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="COUNT" refer="showCountBtn">数量</th>
                        <th colwidth="85" type="link" title="[删除]"  action="doDeleteEdit">操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <fieldset id="fie-selectedPart" style="display:">
            <legend align="left" >&nbsp;[已选定配件]</legend>
            <div id="div-selectedPart-Edit">
                <table style="width:100%;">
                    <tr>
                        <td>
                            <textarea style="width:80%;display:none" id="edit-val0" column="1" name="multivals" readOnly></textarea>
                            <textarea style="width:80%;display:none" id="edit-val1" name="multivals" readOnly></textarea>
                            <textarea style="width:99%;height:50px;" id="edit-val2" name="multivals" readOnly></textarea>
                            <textarea style="width:80%;display:none" id="edit-val3" name="multivals" readOnly></textarea>
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
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    var action=<%=action%>;
    var dialog = $("body").data("forecastReport");

    // 初始化方法
    $(function(){
        $("#edit-tab").attr("layoutH",document.documentElement.clientHeight-240);
        if(action != "1"){
            // ------ 修改操作
            var selectedRows = $("#tab-grid-index").getSelectedRows();
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
            // 预测月份
            $("#edit-forcastMonth").val(selectedRows[0].attr("FORCAST_MONTH"));
            // 预测ID
            $("#edit-forcastId").val(selectedRows[0].attr("FORCAST_ID"));
            // 此处设定配件预测页面回显用
            $("#r-edit-forcastId").val(selectedRows[0].attr("FORCAST_ID"));
            // 预测月份为只读
            $("#edit-forcastMonth").attr("disabled",true);
            searchPromPart();
        } else {
            // ------ 新增操作
            // 隐藏 新增配件按钮
            $("#formBar").find("li:eq(0)").hide();
            // 隐藏导出模板按钮
            $("#formBar").find("li:eq(2)").hide();
            // 隐藏批量导入按钮
            $("#formBar").find("li:eq(3)").hide();
            // 隐藏提报按钮
            $("#formBar").find("li:eq(4)").hide();
        }

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });

        // 保存按钮绑定
        $("#doSave").click(function() {
            if(action != "1"){
                // ------ 修改操作
                var partIds = $('#edit-val0').val();
                if (!partIds) {
                    alertMsg.warn('请选择配件!');
                } else {
                    var insertPromPartUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/insertPartReportDetail.ajax";
                    // 配件预测主键ID
                    $('#forcastMonths').val($('#edit-forcastMonth').val());
                    $('#partIds').val($('#edit-val0').val());
                    $('#partCodes').val($('#edit-val1').val());
                    $('#partNames').val($('#edit-val2').val());
                    $('#counts').val($('#edit-val3').val());

                    var $f = $("#fm-partInfo");
                    var sCondition = {};
                    sCondition = $f.combined() || {};
                    doFormSubmit($f, insertPromPartUrl, "btn-search", 1, sCondition, "");
                }
            } else {
                // ------ 新增操作
                var insertForecastUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/insertForecast.ajax";
                var url = insertForecastUrl + "?forcastMonth=" + $('#edit-forcastMonth').val();
                var $f = $("#fm-forecastReportEdit-W");
                if (submitForm($f) == false) {
                    return false;
                }
                sendPost(url, "", "", insertCallBack, "true");
            }
        });

        // 提报按钮绑定
        $("#doApply").click(function() {
        	if($("#edit-tab_content").find("tr").size()==0){
                alertMsg.warn("请添加配件.");
                return false;
            } else if($("#edit-tab_content").find("tr").size() == 1) {
                if($("#edit-tab_content").find("tr").eq(0).find("td").size() == 1) {
                    alertMsg.warn("请添加配件.");
                    return false;
                }
            }
            var doApplyUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/updateForecast.ajax";
            var url = doApplyUrl + "?forcastId=" + $('#r-edit-forcastId').val();
            sendPost(url, "", "", doApplyCallBack, "true");
        });

        // 导出模板按钮绑定
        $("#doExp").click(function() {
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=ycpjxxdrmb.xls");
            window.location.href = url;
        });

        // 新增配件按钮绑定
        $("#dia-btn-search").bind("click", function (event) {
            var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
            $.pdialog.open(webApps+"/jsp/dms/dealer/part/plan/forecastMng/forecastPartSearch.jsp", "partSearch", "预测配件信息", options);
       });

        // 批量导入按钮绑定
        $("#doImp").bind("click", function () {
            //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            importXls('PT_BU_FORCAST_DTL_TMP',$('#edit-forcastId').val(),3,3,"/jsp/dms/dealer/part/plan/forecastMng/importSuccess.jsp");
        });
    });

    // 提报按钮回调函数
    function doApplyCallBack(res) {
        var rows = res.getElementsByTagName("ROW");
        // 读取XML中的FLAG属性(FLAG:true有重复数据;)
        var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
        if (flag == "true") {
            alertMsg.info("今天不在提报日内!");
            return false;
        }
        // 查询预测方法
        forecastReportIndex_search();
        $.pdialog.closeCurrent();
        return true;
    }

    // 新增回调函数
    function insertCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            // 读取XML中的FLAG属性(FLAG:true有重复数据;)
            var flag = getNodeText(rows[0].getElementsByTagName("FLAG").item(0));
            $("#edit-forcastId").val(getNodeText(rows[0].getElementsByTagName("FORCAST_ID").item(0)));
            $("#r-edit-forcastId").val(getNodeText(rows[0].getElementsByTagName("FORCAST_ID").item(0)));
            if (flag == "true") {
                alertMsg.info("预测月份重复!");
                return false;
            }
            action="2";
            // 预测月份为只读
            $("#edit-forcastMonth").attr("disabled",true);
            // 显示 新增配件按钮
            $("#formBar").find("li:eq(0)").show();
            // 显示导出模板按钮
            $("#formBar").find("li:eq(2)").show();
            // 显示批量导入按钮
            $("#formBar").find("li:eq(3)").show();
            // 显示提报按钮
            $("#formBar").find("li:eq(4)").show();
            $("#edit-tab").show();
            $("#edit-tab").jTable();
            alertMsg.info("保存成功！");
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 删除方法
    function doDeleteEdit (rowObj) {
        $row = $(rowObj);
        var deleteForecastUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/deleteForecastDetail.ajax";
        var url = deleteForecastUrl + "?forcastId=" + $('#edit-forcastId').val() +"&partCode=" + $(rowObj).attr("PART_CODE");
        sendPost(url, "", "", doDeleteEditCallBack, "true");
    }

    function showCountBtn(obj){
        obj.html("<input type='text' name='COUNT' onblur='doMyInputBlur(this)' style='width:100px' value='" + obj.html() +"'/>");
    }

    // 删除回调方法
    function doDeleteEditCallBack(res) {
        try {
            if ($row)
                $("#edit-tab").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 查询预测配件明细
    function searchPromPart() {
        var searchPromPartUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastMngAction/searchForecastDetail.ajax";
        var $f = $("#fm-forecastReportEdit-R");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPromPartUrl, "btn-search", 1, sCondition, "edit-tab");
        $("#edit-tab").show();
        $("#edit-tab").jTable();
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
        if (!s || !reg.test(s)) {//为空或者不是金额
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
        multiSelected($checkbox, arr,$('#div-selectedPart-Edit'));
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
        if($("#edit-val3") && $("#edit-val3").val()) {
            var t = $("#edit-val3").val();
            var pks = $("#edit-val0").val();
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