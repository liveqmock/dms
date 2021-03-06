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
                        <td><label>订单号码：</label></td>
                        <td><div><input type="text" id="orderNo-edit" name="orderNo-edit" readonly datasource="ORDER_NO" datatype="1,is_null,30"/></div></td>
                        <td><label>订单类型：</label></td>
                        <td><div><input type="text" id="orderType-edit" name="orderType-edit" readonly datasource="ORDER_TYPE" datatype="1,is_null,30"/></div></td>
                        <td><label>提报单位：</label></td>
                        <td><input type="text" id="orgName-edit" name="orgName-edit" readonly datasource="ORG_NAME" datatype="1,is_null,30"/></td>
                    </tr>
                    <tr>
                        <td><label>提报日期：</label></td>
                        <td><input type="text" id="applyDate-edit"  name="applyDate-edit" readonly datasource="APPLY_DATE" datatype="1,is_null,30"/></td>
                    </tr>
                    </table>
                </fieldset>
            </div>
        </form>
        <form  method="post" id="dia-fm-edit-orderId">
            <input type="hidden" id="dia-orderId-edit" name="dia-orderId-edit" datasource="ORDER_ID"/>
        </form>
            <div id="page_edit" style="">
                <table style="display:none;width:99%;" id="dia-tab-edit" name="tablist" ref="page_edit">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="DTL_ID"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="" refer="appendPack_Unit">最小包装</th>
                            <th fieldname="AUDIT_COUNT">应出数量</th>
                            <th fieldname="" refer="creatInput">实出数量</th>
                            <th fieldname="UNIT_PRICE" refer="formatAmount">经销商价格</th>
                            <th fieldname="AMOUNT" refer="formatAmount">金额</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <fieldset id="fie-selectedPart" style="display:none">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;display:none;" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none;" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none;" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none;" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none;" id="val5" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="orderId" name="orderId" datasource="ORDERID"/>
                <input type="hidden" id="orderNo" name="orderNo" datasource="ORDERNO"/>
                <input type="hidden" id="dtlIds" name="dtlIds" datasource="DTLIDS"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="supplierIds" name="supplierIds" datasource="SUPPLIERIDS"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="outCounts" name="outCounts" datasource="OUTCOUNTS"/>
                <input type="hidden" id="auditCounts" name="auditCounts" datasource="AUDITCOUNTS"/>
            </form>
            <div class="formBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-returnPurchaseOut-edit">销售出库</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                </ul>
            </div>
        </div>
    </div>
<script type="text/javascript">
    $(function(){
    	$("#dia-tab-edit").attr("layoutH",document.documentElement.clientHeight-210);
        var selectedRows = $("#tab-index").getSelectedRows();
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("dia-tab-htxx",selectedRows[0].attr("rowdata"));
        // 销售订单ID
        $("#dia-orderId-edit").val(selectedRows[0].attr("ORDER_ID"));

        // 查询销售订单明细
        var edit_searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/SaleOutMngAction/searchSaleOrderDtl.ajax";
        var $f = $("#dia-fm-edit-orderId");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, edit_searchUrl, "btn_id", 1, sCondition, "dia-tab-edit");

        // 销售出库按钮绑定
        $("#dia-returnPurchaseOut-edit").click(function(){
            var dtlIds = $('#val0').val();
            if (!dtlIds) {
                alertMsg.warn('请选择配件!');
                return false;
            } else {
                var doSaleOutUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/SaleOutMngAction/saleOut.ajax";
                // 销售订单ID
                $('#orderId').val($("#dia-orderId-edit").val());
                // 销售订单号
                $('#orderNo').val($("#orderNo-edit").val());
                $('#dtlIds').val($("#val0").val());
                // 配件ID
                // 应出数量
                $('#auditCounts').val($('#val2').val());
                // 实出数量
                $('#partIds').val($('#val3').val());
                $('#outCounts').val($('#val4').val());
                $('#supplierIds').val($('#val5').val());
                var $f = $("#fm-partInfo");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                $("#dia-returnPurchaseOut-edit").attr("style","display:none");
                doNormalSubmit($f, doSaleOutUrl, "dia-returnPurchaseOut-edit", sCondition, doSaleOutCallBack);
            }
         });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    // 销售出库回调函数
    function doSaleOutCallBack() {
        // 查询退件申请方法
        searchSaleOrder();
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
        return '<input type="text" name="COUNT" onblur="doMyInputBlur(this)" style="width:50px"/>';
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
        var returnCount = $tr.attr("AUDIT_COUNT");
        var s = $obj.val();
        if (parseInt(s)-parseInt(returnCount)>0) {
            alertMsg.warn("实出数量不能大于应出数量！");
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
        
        var returnCount = $tr.attr("AUDIT_COUNT");
        if (parseInt(s)-parseInt(returnCount)>0) {
            alertMsg.warn("实出数量不能大于应出数量！");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        arr.push($tr.attr("DTL_ID"));
        // 配件ID
        arr.push($tr.attr("PART_NAME"));
        // 应出数量
        arr.push($tr.attr("AUDIT_COUNT"));
        arr.push($tr.attr("PART_ID"));
        // 实出数量
        arr.push(s);
        arr.push($tr.attr("SUPPLIER_ID"));
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

