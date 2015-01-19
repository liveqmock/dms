<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
    <div class="page">
        <div class="pageContent" style="" >
        <form method="post" id="dia-fm-htwh" class="editForm" style="width:99%;">
            <div align="left">
                <fieldset>
                    <table class="editTable" id="dia-tab-htxx">
                        <tr>
                            <td><label>退件单号：</label></td>
                            <td><input type="text" id="returnNo-edit" datasource="RETURN_NO"  readonly="readonly"/></td>
                            <td><label>接收方：</label></td>
                            <td><input type="text" id="receiveOrgName-edit"  name="receiveOrgName-edit" datasource="RECEIVE_ORG_NAME" readonly="readonly"/></td>
                            <td><label>申请日期：</label></td>
                            <td><input type="text" id="applyDate-edit" name="applyDate-edit"  datasource="APPLY_DATE" readonly="readonly"/></td>
                        </tr>
                        <tr>
                            <td><label>审核员：</label></td>
                            <td><input type="text" id="checkUser-edit" name="checkUser-edit"  datasource="CHECK_USER" readonly="readonly"/></td>
                            <td><label>审核日期：</label></td>
                            <td><input type="text" id="checkDate-edit" name="checkDate-edit"  datasource="CHECK_DATE" readonly="readonly"/></td>
                        </tr>
                    </table>
                </fieldset>
            </div>
        </form>
        <form  method="post" id="dia-fm-edit-returnId">
            <input type="hidden" id="dia-returnId-edit" name="dia-returnId-edit" datasource="RETURN_ID"/>
        </form>
            <div id="page_edit" style="">
                <table style="display:none;width:99%;" id="dia-tab-edit" name="tablist" ref="page_edit">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="" refer="appendPack_Unit">最小包装</th>
                            <th fieldname="RETURN_COUNT">退货数量</th>
                            <th fieldname="" refer="creatInput">出库数量</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount">经销商价格</th>
                            <th fieldname="AMOUNT" refer="formatAmount">金额</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <br/>
            <fieldset id="fie-selectedPart" style="display:">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val4" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="returnIds" name="returnIds" datasource="RETURNIDS"/>
                <input type="hidden" id="outCounts" name="outCounts" datasource="OUTCOUNTS"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="returnNo" name="returnNo" datasource="RETURNNO"/>
                <input type="hidden" id="auditCounts" name="auditCounts" datasource="AUDITCOUNTS"/>
            </form>
            <div class="formBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-returnPurchaseOut-edit">退货出库</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                </ul>
            </div>
        </div>
    </div>
<script type="text/javascript">
    $(function(){

        var selectedRows = $("#tab-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("dia-tab-htxx",selectedRows[0].attr("rowdata"));
        // 退件订单ID
        $("#dia-returnId-edit").val(selectedRows[0].attr("RETURN_ID"));

        // 查询退件申请明细
        var edit_searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/ReturnPurchaseOutMngAction/searchReturnPurchaseApplyDtl.ajax";
        var $f = $("#dia-fm-edit-returnId");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, edit_searchUrl, "btn_id", 1, sCondition, "dia-tab-edit");

        // 退件出库按钮绑定
        $("#dia-returnPurchaseOut-edit").click(function(){
            var partIds = $('#val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                var doReturnPurchaseOutUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/ReturnPurchaseOutMngAction/returnPurchaseOut.ajax";
                // 退件订单号
                $('#returnNo').val($("#returnNo-edit").val());
                // 退件订单ID
                $('#returnIds').val($('#val0').val());
                // 配件ID
                $('#partIds').val($('#val1').val());
                // 实际退件
                $('#outCounts').val($('#val3').val());
                // 应出数量
                $('#auditCounts').val($('#val4').val());
                var $f = $("#fm-partInfo");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, doReturnPurchaseOutUrl, "dia-returnPurchaseOut-edit", sCondition, doReturnPurchaseOutCallBack);
            }
         });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    // 退件出库回调函数
    function doReturnPurchaseOutCallBack() {

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

    // 创建文本框
    function creatInput(obj){
        obj.html("<input type='text' name='COUNT' onblur='doMyInputBlur(this)' style='width:50px' value='" + obj.html() +"'/>");
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
        var returnCount = $tr.attr("RETURN_COUNT");
        var s = $obj.val();
        if (parseInt(s,10)>parseInt(returnCount,0)) {
            alertMsg.warn("出库数量不能大于退货数量！");
            return;
        }
        var checkObj = $("input:first",$tr.find("td").eq(1));
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
        var $input = $tr.find("td").eq(7).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT") {
            s = $input.val();
        } else {
            s = $tr.find("td").eq(7).text();
        }
        doCheckbox($obj.get(0));
    }

    // 列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^[0-9]*$/;//数字正则
        var s = "";
        if($tr.find("td").eq(7).find("input:first").size()>0) {
            s = $tr.find("td").eq(7).find("input:first").val();
        } else {
            s = $tr.find("td").eq(7).text();
        }
        if (!s || !reg.test(s)) {//为空或者不是金额
            alertMsg.warn("请输入正确的数量!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        // 退件订单ID
        arr.push($tr.attr("RETURN_ID"));
        // 配件ID
        arr.push($tr.attr("PART_ID"));
        // 配件名称
        arr.push($tr.attr("PART_NAME"));
        // 出库数量
        arr.push(s);
        // 退货数量
        arr.push($tr.attr("RETURN_COUNT"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(7).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(7).html("<div><input type='text' name='myCount' style='width:50px;' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /*
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(7);
        var val;
        if($("#val1") && $("#val1").val()) {
            var t = $("#val1").val();
            var pks = $("#val0").val();
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
</div>

