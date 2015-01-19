<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="di_ycmx" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
        <form method="post" id="fm-forecastTotalDetail" class="editForm" >
            <input type="hidden" id="detail-forcastId" name="detail-forcastId" datasource="FORCAST_ID"/>
        </form>
        <form method="post" class="editForm" >
            <div align="left">
                <fieldset>
                    <table class="editTable" id="di_ycmxT">
                        <tr>
                            <td><label>经销商代码：</label></td>
                            <td><input type="text" id="detail-code" name="detail-code" readonly="readonly"/></td>
                            <td><label>经销商名称：</label></td>
                            <td><input type="text" id="detail-oname" name="detail-oname" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td><label>预测月份：</label></td>
                            <td><input type="text"  id="detail-forecastMonth" name="detail-forecastMonth" readonly="readonly"/></td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </form>
        <form  method="post" id="partform">
            <div id="detail_page_grid">
                <table style="display:none;width:100%;" id="tab-grid-detail" name="tablist" ref="detail_page_grid" refQuery="tab-condition">
                        <thead>
                            <tr>
                                <th name="XH" style="display:none">序号</th>
                                <th fieldname="PART_CODE">配件代码</th>
                                <th fieldname="PART_NAME">配件名称</th>
                                <th fieldname="COUNT">数量</th>
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
    // URL
    var dtlSearchUrl = "<%=request.getContextPath()%>/part/planMng/forecast/ForecastTotalMngAction/searchForecastDetail.ajax";
    // 弹出窗体
    var dialog = $("body").data("totalDetail");
    // 初始化方法
    $(function(){
        var selectedRows = $("#tab-grid-search").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        // 经销商代码
        $("#detail-code").val(selectedRows[0].attr("CODE"));
        // 经销商名称
        $("#detail-oname").val(selectedRows[0].attr("ONAME"));
        // 预测月份
        $("#detail-forecastMonth").val(selectedRows[0].attr("FORCAST_MONTH"));
        // 预测主键(隐藏域)
        $("#detail-forcastId").val(selectedRows[0].attr("FORCAST_ID"));
        
        var $f = $("#fm-forecastTotalDetail");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, dtlSearchUrl, "btn-search", 1, sCondition, "tab-grid-detail");
        $("#tab-grid-detail").show();
        $("#tab-grid-detail").jTable();
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });
    });
</script>