<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="di_pjsbqxg" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
        <form method="post" id="fm-update" class="editForm" >
            <div align="left">
            <fieldset>
            <table class="editTable" id="dia-tab-update">
                <tr>
                    <td><label>配件代码：</label></td>
                    <td><input type="text" id="partCode-update" name="partCode-update" datatype="1,is_null,100" datasource="PART_CODE" readonly="readonly" /></td>
                    <td><label>配件名称：</label></td>
                    <td><input type="text" id="partName-update" name="partName-update" datatype="1,is_null,100" datasource="PART_NAME" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td><label>三包月份：</label></td>
                    <td><input type="text" id="claimMonth-update" name="claimMonth-update" datatype="0,is_digit,100" datasource="CLAIM_MONTH" /></td>
                    <td><label>延保月份：</label></td>
                    <td>
                        <input type="text" id="extensionMonth-update" name="extensionMonth-update" datatype="0,is_digit,100" datasource="EXTENSION_MONTH" />
                        <input type="hidden" id="cycle-update" name="cycle-update" datasource="CYCLE_ID" />
                    </td>
                </tr>
            </table>
            </fieldset>
            </div>
        </form>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save-update">保&nbsp;&nbsp;存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
    </div>
    </div>    
</div>
<script type="text/javascript">

    //弹出窗体
    var dialog = $("body").data("claimCyclSetUpdate");
    $(function() {
        var selectedRows = $("#tab-grid-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("dia-tab-update",selectedRows[0].attr("rowdata"));
        $("button.close").click(function(){
            $.pdialog.close(dialog);
            return false;
        });

        // 保存按钮绑定
        $("#dia-save-update").click(function(){
            var updateUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclSetMngAction/updateClaimCyclSet.ajax";
            //获取需要提交的form对象
            var $f = $("#fm-update");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $f.combined(1) || {};
            doNormalSubmit($f, updateUrl, "dia-save-update", sCondition, updateCallBack);
        });
    });

    // 修改回调方法
    function updateCallBack(res) {
        try {
            // 查询配件三包期设置
            searchClaimCyclSet();
            $.pdialog.close(dialog);
            return false;
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>