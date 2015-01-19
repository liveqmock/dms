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
            <table style="display:none;width:100%;" id="edit-tab" name="tablist" ref="edit_page_grid" refQuery="tab-condition">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none;"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="" refer="showCountBtn">备注</th>
                        <th colwidth="85" type="link" title="[添加附件]"  action="doEdit">操作</th>
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
    var dialog = $("body").data("directBusinessOrderCheckEdit");
    var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

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
        	var updatePromPartUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblyMngAction/updatePartReportDetail.ajax?ID="+$("#assemblyId").val();
        	var $f = $("#fm-partInfo");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, updatePromPartUrl, "btn-search", 1, sCondition, updatePartCallBack);
        });

    });

    // 保存回调函数
    function updatePartCallBack () {
        // 查询总成附件确认方法
        searchDirectBusinessOrder();
        $.pdialog.close(dialog);
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
	function doEdit(rowobj){
		$("td input[type=radio]",$(rowobj)).attr("checked",true);
		$.pdialog.open(webApps+"/jsp/dms/oem/part/sales/assemblyMng/annexAdd.jsp?DTL_ID="+$(rowobj).attr("DTL_ID"), "addWin", "添加附件", diaAddOptions);
	}

</script>