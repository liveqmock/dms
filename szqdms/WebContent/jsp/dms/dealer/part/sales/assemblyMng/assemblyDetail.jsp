<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_ycmx" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
        <form  method="post" id="fm-forecastDetail">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="dtl-assemblyId" name="dtl-assemblyId" datasource="ASSEMBLY_ID"/>
            </form>
        <form  method="post" id="partform">
            <div id="dtl_page_grid">
            <table style="display:none;width:100%;" id="dtl-tab-id" ref="dtl_page_grid" refQuery="tab-condition">
                    <thead>
                    <tr>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="REMARKS">备注</th>
                        <th fieldname="A_PART_CODE">附件代码</th>
                        <th fieldname="A_PART_NAME">附件名称</th>
                        <th fieldname="A_REMARKS">附件备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
            </div>
        </form>
        <div class="formBar" id="opBtn" >
        <ul>
            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
        </ul>
    </div>
    </div>
    </div>    
</div>
<script type="text/javascript">
    //弹出窗体
    var dialog = $("body").data("forecastDetail");
    $(function(){
    	$("#dtl-tab-id").attr("layoutH",document.documentElement.clientHeight-270);
        var selectedRows = $("#tab-grid-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        $("#dtl-assemblyId").val(selectedRows[0].attr("ASSEMBLY_ID"));
        var dtlSearchUrl = "<%=request.getContextPath()%>/part/salesMng/assemblyMng/AssemblySearchMngAction/searchForecastDetail.ajax?ID="+selectedRows[0].attr("ASSEMBLY_ID");
         var $f = $("#fm-forecastDetail");
         var sCondition = {};
         sCondition = $f.combined() || {};
         doFormSubmit($f, dtlSearchUrl, "btn-search", 1, sCondition, "dtl-tab-id");

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });
    });
</script>