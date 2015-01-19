<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_ycmx" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
        <form  method="post" id="fm-dtl">
                <%--隐藏域查询条件--%>
                <input type="hidden" id="dtl-orderId" name="dtl-orderId" datasource="A.ORDER_ID"/>
        </form>
        <form  method="post" id="partform">
            <div id="dtl_page_grid">
            <table style="display:none;width:100%;" id="dtl-tab-id" ref="dtl_page_grid" refQuery="tab-condition">
                    <thead>
                    <tr>
                         <th fieldname="PART_CODE" freadonly="true">配件代码</th>
                         <th fieldname="PART_NAME" freadonly="true">配件名称</th>
                         <th fieldname="UNIT" colwidth="50" freadonly="true">计量单位</th>
                         <th fieldname="MIN_PACK" colwidth="50" refer="appendPack_Unit" freadonly="true">最小包装</th>
                         <th fieldname="UNIT_PRICE" colwidth="60" refer="amountFormat" align="right" freadonly="true">经销商价</th>
                         <th fieldname="IF_SUPPLIER" colwidth="90" style="display:none" freadonly="true">是否指定供应商</th>
                         <th fieldname="SUPPLIER_NAME" refer="supplierShow" freadonly="true">供应商名称</th>
                         <th fieldname="ORDER_COUNT" colwidth="50" fdatatype="1,is_digit,30">订购数量</th>
                         <th fieldname="AMOUNT" refer="amountFormat" align="right" freadonly="true">小计</th>
                         <th fieldname="REMARKS" colwidth="90">备注</th>
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
    var dialog = $("body").data("orderDtl");
    var dtlSearchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderQueryMngAction/searchOrderDtl.ajax";
    $(function(){

    	$("#dtl-tab-id").attr("layoutH",document.documentElement.clientHeight-270);
        var selectedRows = $("#tab-grid-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        $("#dtl-orderId").val(selectedRows[0].attr("ORDER_ID"));
         var $f = $("#fm-dtl");
         var sCondition = {};
         sCondition = $f.combined() || {};
         doFormSubmit($f, dtlSearchUrl, "btn-search", 1, sCondition, "dtl-tab-id");

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });
    });

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

    function supplierShow(obj){
        var $tr = $(obj).parent();
        var ifSuply= $tr.attr("IF_SUPPLIER");
        var str="";
        if(ifSuply=='<%=DicConstant.SF_01%>'){
            str= $tr.attr("SUPPLIER_NAME");
        }else{
            str = "<div></div>";
        }
        return str;
    }
</script>