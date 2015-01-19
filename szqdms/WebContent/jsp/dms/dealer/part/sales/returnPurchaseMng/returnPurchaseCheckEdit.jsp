<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
    <div class="page">
    <div class="pageContent">
        <form method="post" id="dia-fm-htwh" class="editForm" style="width:100%;">
          <div align="left">
              <fieldset>
                        <table class="editTable" id="dia-tab-htxx">
                            <tr><td><label>退件单号：</label></td>
                                <td><input type="text" id="dia-returnNo-edit" datasource="RETURN_NO" readonly="readonly"/></td>
                                <td><label>接收方：</label></td>
                                <td><input type="text" id="dia-receiveOrgName-edit" name="dia-receiveOrgName-edit" datasource="APPLY_ORG_NAME" readonly="readonly"/></td>
                                <td><label>申请日期：</label></td>
                                <td><input type="text" id="dia-applyDate-edit" name="dia-applyDate-edit" datasource="APPLY_DATE" readonly="readonly"/></td>
                            </tr>
                        </table>
              </fieldset>
            </div>
        </form>
        <div id="page_edit">
            <table style="display:none;width:99%;" id="dia-tab-edit" name="tablist" ref="page_edit">
                <thead>
                    <tr>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="SUPPLIER_NAME">供应商</th>
                        <th fieldname="UNIT" colwidth="60">计量单位</th>
                        <th fieldname="" refer="appendPack_Unit">最小包装</th>
                        <th fieldname="RETURN_COUNT">退货数</th>
                        <th fieldname="SALE_PRICE" refer="formatAmount" align="right">经销商价格(元)</th>
                        <th fieldname="AMOUNT" refer="formatAmount" align="right">金额(元)</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
             </table>
         </div>
         <br/>
         <form  method="post" id="dia-fm-edit-returnId">
             <input type="hidden" id="dia-returnId-edit" name="dia-returnId-edit" datasource="RETURN_ID"/>
         </form>
         <form  method="post" id="dia-fm-edit">
             <div align="left">
                 <fieldset>
                     <table class="editTable">
                         <tr>
                             <td><label>审核意见：</label></td>
                             <td>
                                 <input type="text" id="dia-remarks-edit" name="dia-remarks-edit" datatype="0,is_null,1000" datasource="CHECK_REMARKS" style="width:450px;height:40px;"/>
                                 <input type="hidden" id="dia-returnId-remarks-edit" datasource="RETURN_ID"/>
                             </td>
                         </tr>
                     </table>
                 </fieldset>
             </div>
         </form>
         <div class="formBar">
             <ul>
                 <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-doCheckPass">审核通过</button></div></div></li>
                 <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-doCheckReturn">审核驳回</button></div></div></li>
                 <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
             </ul>
         </div>
    </div>
    </div>
<script type="text/javascript">
    $(function(){
        $("#dia-tab-edit").attr("layoutH",document.documentElement.clientHeight-270);
        var selectedRows = $("#tab-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("dia-tab-htxx",selectedRows[0].attr("rowdata"));
        // 退件订单ID
        $("#dia-returnId-remarks-edit").val(selectedRows[0].attr("RETURN_ID"));
        $("#dia-returnId-edit").val(selectedRows[0].attr("RETURN_ID"));

        // 查询退件申请明细
        var edit_searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseCheckMngAction/searchReturnPurchaseApplyDtl.ajax";
        var $f = $("#dia-fm-edit-returnId");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, edit_searchUrl, "btn_id", 1, sCondition, "dia-tab-edit");

        // 审核通过按钮绑定
        $("#dia-doCheckPass").click(function(){
            var doCheckPassUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseCheckMngAction/doCheckPass.ajax";
            var $f = $("#dia-fm-edit");
            var sCondition = {};
            sCondition = $f.combined(1) || {};
            if (submitForm($f) == false) {
                return false;
            }
            doNormalSubmit($f, doCheckPassUrl, "dia-doCheckPass", sCondition, doCheckPassCallBack);
        });

        // 审核驳回按钮绑定
        $("#dia-doCheckReturn").click(function(){
            var doCheckReturnUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseCheckMngAction/doCheckReturn.ajax";
            var $f = $("#dia-fm-edit");
            var sCondition = {};
            if (submitForm($f) == false) {
                return false;
            }
            sCondition = $f.combined(1) || {};
            doNormalSubmit($f, doCheckReturnUrl, "dia-doCheckReturn", sCondition, doCheckReturnCallBack);
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    // 审核通过回调函数
    function doCheckPassCallBack() {

        // 查询退件申请方法
        searchReturnPurchaseApply();
        $.pdialog.closeCurrent();
        return false;
    }

    // 审核驳回回调函数
    function doCheckReturnCallBack(res) {

        // 查询退件申请方法
        searchReturnPurchaseApply();
        $.pdialog.closeCurrent();
        return false;
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
</div>
