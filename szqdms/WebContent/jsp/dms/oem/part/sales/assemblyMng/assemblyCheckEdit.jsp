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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
        <div class="page">
        <div class="pageContent" style="" >
            <form  method="post" id="fm-assemblyEdit">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="assemblyId" name="assemblyId" datasource="ASSEMBLY_ID"/>
            </form>
            <form method="post" id="fm-forecastReportEdit-W" class="editForm" >
                <%--隐藏域查询条件--%>
                <input type="hidden" id="edit-forcastId" name="edit-forcastId" datasource="FORCAST_ID"/>
                <div id="formBar" class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="doSave">保&nbsp;&nbsp;存</button></div></div></li>
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
                        <th fieldname="" refer="showCountBtn">备注</th>
                        <!-- <th colwidth="85" type="link" title="[删除]"  action="doDeleteEdit">操作</th> -->
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
            <input type="hidden" id="assemblyIds" name="assemblyIds" datasource="ASSEMBLYID"/>
            <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
            <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
            <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
            <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
        </form>
    </div>
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    var action=<%=action%>;
    var dialog = $("body").data("directBusinessOrderCheckEdit");

    // 初始化方法
    $(function(){
        $("#edit-tab").attr("layoutH",document.documentElement.clientHeight-240);
        var selectedRows = $("#tab-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        // 总成附件确认ID
        $("#assemblyId").val(selectedRows[0].attr("ASSEMBLY_ID"));
        searchPromPart();

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });

        // 保存按钮绑定
        $("#doSave").click(function() {
            var partIds = $('#edit-val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                var updatePromPartUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/updatePartReportDetail.ajax";
                // 配件预测主键ID
                $('#assemblyIds').val($('#assemblyId').val());
                $('#partIds').val($('#edit-val0').val());
                $('#partCodes').val($('#edit-val1').val());
                $('#partNames').val($('#edit-val2').val());
                $('#remarks').val($('#edit-val3').val());

                var $f = $("#fm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, updatePromPartUrl, "btn-search", 1, sCondition, updatePartCallBack);
            }
        });

    });

    // 保存回调函数
    function updatePartCallBack () {
        // 查询总成附件确认方法
        searchDirectBusinessOrder();
        $.pdialog.closeCurrent();
        return true;
    }
    // 查询预测配件明细
    function searchPromPart() {
        var searchPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/searchForecastDetail.ajax";
        var $f = $("#fm-assemblyEdit");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPromPartUrl, "btn-search", 1, sCondition, "edit-tab");
        $("#edit-tab").show();
        $("#edit-tab").jTable();
    }

    function showCountBtn(obj){
        obj.html("<input type='text' name='COUNT' onblur='doMyInputBlur(this)' style='width:300px' value='" + obj.html() +"'/>");
    }

    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj) {
        var $obj = $(obj);
        if($obj.val() == "") {//为空直接返回
            return ;
        }
        if($obj.val().indexOf(",")!='-1') {//不为空并且数量不正确
            alertMsg.warn("字符串不能包含逗号,且不能为空");
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
        if (!s || s.indexOf(",")!='-1') {//为空或者不是金额
        	alertMsg.warn("字符串不能包含逗号,且不能为空");
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
            $tr.find("td").eq(4).html("<div><input type='text' name='myCount' style='width:300px;' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
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