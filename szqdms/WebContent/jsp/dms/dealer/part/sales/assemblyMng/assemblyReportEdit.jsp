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
            <form  method="post" id="fm-assemblyId">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="assemblyId" name="assemblyId" datasource="ASSEMBLY_ID"/>
            </form>
            <form method="post" id="fm-forecastReportEdit-W" class="editForm" >
                <div id="formBar" class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-btn-search">新增配件</button></div></div></li>
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
                        <th type="multi" name="XH" unique="PART_ID" style="display:none;"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th colwidth="85" type="link" title="[删除]"  action="doDeleteEdit">操作</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
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
            $("#assemblyId").val(selectedRows[0].attr("ASSEMBLY_ID"));
            searchPromPart();
        } else {
            // ------ 新增操作
                var insertForecastUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/insertForecast.ajax";
                var url = insertForecastUrl;
                var $f = $("#fm-forecastReportEdit-W");
                if (submitForm($f) == false) {
                    return false;
                }
                sendPost(url, "", "", insertCallBack, "false");
        }

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
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
            var doApplyUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/updateForecast.ajax";
            var url = doApplyUrl + "?assemblyId=" + $('#assemblyId').val();
            sendPost(url, "", "", doApplyCallBack, "true");
        });

        // 新增配件按钮绑定
        $("#dia-btn-search").bind("click", function (event) {
            var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
            $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/assemblyMng/assemblyPartSearch.jsp", "partSearch", "总成配件信息", options);
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
            $("#assemblyId").val(getNodeText(rows[0].getElementsByTagName("ASSEMBLY_ID").item(0)));
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 删除方法
    function doDeleteEdit (rowObj) {
        $row = $(rowObj);
        var deleteForecastUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/deleteForecastDetail.ajax";
        var url = deleteForecastUrl + "?assemblyId=" + $('#assemblyId').val() +"&partCode=" + $(rowObj).attr("PART_CODE");
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
        var searchPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/searchForecastDetail.ajax";
        var $f = $("#fm-assemblyId");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPromPartUrl, "btn-search", 1, sCondition, "edit-tab");
        $("#edit-tab").show();
        $("#edit-tab").jTable();
    }
</script>