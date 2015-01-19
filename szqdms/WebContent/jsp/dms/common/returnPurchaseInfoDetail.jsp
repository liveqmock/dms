<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String contentPath = request.getContextPath();
    String RETURN_ID = request.getParameter("RETURN_ID");
%>
<div id="dia-layout">
    <div class="page">
        <div class="pageContent">
            <form method="post" id="dia-fm-edit" class="editForm" style="width:99%;">
                <input type="hidden" id="dia-sourceOrderId-edit" readonly="readonly" datasource="SOURCE_ORDER_ID"/>
                <div align="left">
                    <fieldset>
                        <table class="editTable" id="dia-tab-edit1">
                            <tr>
                                <td><label>退件单号：</label></td>
                                <td><input type="text" id="dia-returnNo-edit" name="dia-returnNo-edit" datasource="RETURN_NO" readonly="readonly"/></td>
                                <td><label>接收方：</label></td>
                                <td><input type="text" id="dia-oName-edit" datasource="RECEIVE_ORG_NAME" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="5"><textarea id="dia-remarks-edit" name="dia-remarks-edit" datatype="0,is_null,100"  readonly="readonly" datasource="REMARKS" style="width:450px;height:40px;"></textarea></td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </form>
            <div id="formBar" class="formBar">
               <ul>
                   <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
                   <li><div class="button"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
               </ul>
            </div>
            <form id="exportFm" method="post" style="display:none">
                <input type="hidden" id="params" name="data"></input>
            </form>
            <form  method="post" id="dia-fm-edit-oderId">
                <input type="hidden" id="dia-returnId-edit" name="dia-returnId-edit" datasource="RETURN_ID"/>
            </form>
            <div id="dia-div-edit">
                <table style="display:none;width:95%;" id="dia-tab-edit" name="tablist" ref="dia-div-edit">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="DTL_ID" style="display:none;"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="SUPPLIER_NAME">供应商</th>
                            <th fieldname="UNIT" colwidth="80">计量单位</th>
                            <th fieldname="" refer="appendPack_Unit">最小包装</th>
                            <th fieldname="RETURN_COUNT">退货数</th>
                            <th fieldname="IN_COUNT">入库数</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount" align="right">经销商价格(元)</th>
                            <th fieldname="AMOUNT" refer="formatAmount" align="right">金额(元)</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<form id="sFrom" method="post" style="display:none">
	<input type="hidden" id="RETURN_ID" name="RETURN_ID" datasource="RETURN_ID"  value="<%=RETURN_ID%>"/>
</form>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data" datasource="data" />
</form>
<script type="text/javascript">
    var RETURN_ID = <%=RETURN_ID%>
    // 弹出窗体
    var dialog = $("body").data("returnPurchaseApplyEdit");
    $(function(){
    	
    	$("#dia-returnId-edit").val(RETURN_ID);
        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
        
        // 导出
        $("#btn-export").click(function(){
        	var $f = $("#sFrom");
        	if (submitForm($f) == false) return false;
        	var sCondition = $f.combined() || {};
            $("#params").val(sCondition);
        	$("#exportFm").attr("action","<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/exportExcel.do");
        	$("#exportFm").submit();
        })

    });
    searchReturnPurchaseApply();
    // 查询退件申请明细
    searchReturnPurchaseApplyDtl();

    // 经销商价
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询退件申请
    function searchReturnPurchaseApply() {
        var edit_searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchReturnPurchaseApply1.ajax?RETURN_ID="+RETURN_ID;
    	var $f = $("");
    	var sCondition = {};
       	sCondition = $f.combined() || {};
       	sendPost(edit_searchUrl,"",sCondition,searchContractCallBack,"false");
    }
    function searchContractCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	setEditValue("dia-tab-edit1",res.documentElement);
    	var returnNo = getNodeText(rows[0].getElementsByTagName("RETURN_NO").item(0));
    }
    // 查询退件申请明细
    function searchReturnPurchaseApplyDtl() {
        var edit_searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchReturnPurchaseApplyDtl.ajax";
        var $f = $("#dia-fm-edit-oderId");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, edit_searchUrl, "", 1, sCondition, "dia-tab-edit");
    }

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
</script>
