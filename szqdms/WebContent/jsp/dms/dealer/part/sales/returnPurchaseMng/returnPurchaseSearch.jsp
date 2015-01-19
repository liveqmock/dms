<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
        <div class="pageHeader">
            <form id="dia-fm-condition" method="post">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="di_ycpjTable">
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia-partCode-Search" name="dia-partCode-Search" datasource="B.PART_CODE" datatype="1,is_null,100" operation="like" /></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-partName-Search" name="dia-partName-Search" datasource="B.PART_NAME" datatype="1,is_null,100" operation="like" /></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="dia_page_grid">
                <table style="display:none;width:100%;" id="dia-tab-grid" multivals="div-selectedPart" name="tablist" ref="dia_page_grid" refQuery="tab-condition">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID"></th>
                            <th fieldname="PART_CODE" maxlength="40">配件代码</th>
                            <th fieldname="PART_NAME" maxlength="40">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th refer="appendPack_Unit">最小包装</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount" align="right">经销商价格(元)</th>
                            <th fieldname="AVAILABLE_AMOUNT">库存数量</th>
                            <th fieldname="SUPPLIER_NAME">供应商</th>
                            <th fieldname="RETURN_COUNT" refer="createInputBox" colwidth="100">退货数</th>
                            <th fieldname="IF_RETURN">是否可退</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <fieldset id="fie-selectedPart" style="display:">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:100%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:100%;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:100%;height50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none;" id="val5" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val6" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val7" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none" id="val8" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none" id="val9" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="returnId" name="returnId" datasource="RETURNID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="supplierIds" name="supplierIds" datasource="SUPPLIERIDS"/>
                <input type="hidden" id="supplierCodes" name="supplierCodes" datasource="SUPPLIERCODES"/>
                <input type="hidden" id="supplierNames" name="supplierNames" datasource="SUPPLIERNAMES"/>
                <input type="hidden" id="units" name="units" datasource="UNITS"/>
                <input type="hidden" id="returnCounts" name="returnCounts" datasource="RETURNCOUNTS"/>
                <input type="hidden" id="salePrices" name="salePrices" datasource="SALEPRICES"/>
                <input type="hidden" id="amounts" name="amounts" datasource="AMOUNTS"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var partSearchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchReturnPurchase.ajax";
    var returnId = $("#dia-returnId-edit").val();
    // 弹出窗体
    var dialog = $("body").data("returnPurchaseSearch");
    // 初始化函数
    $(function() {

    	$("#dia-tab-grid").attr("layoutH",document.documentElement.clientHeight-240);
        // 初始化查询方法
        retrnPurchaseSearch();

        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            // 查询方法
            retrnPurchaseSearch();
        });

        // 确定按钮绑定
        $('#btn-confirmPart').bind('click',function(){
            // 添加预测配件明细URL
            var insertReturnApplyDtlUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/insertReturnApplyDtl.ajax";
            var partIds = $('#val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                // 退件申请单ID
                $('#returnId').val($('#dia-returnId-edit').val());
                // 配件ID
                $('#partIds').val($('#val0').val());
                // 配件代码
                $('#partCodes').val($('#val1').val());
                // 配件名称
                $('#partNames').val($('#val2').val());
                // 供应商ID
                $('#supplierIds').val($('#val3').val());
                // 供应商代码
                $('#supplierCodes').val($('#val4').val());
                // 供应商名称
                $('#supplierNames').val($('#val5').val());
                // 单位
                $('#units').val($('#val6').val());
                // 退货数量
                $('#returnCounts').val($('#val7').val());
                // 经销商价
                $('#salePrices').val($('#val8').val());
                // 金额
                $('#amounts').val($('#val9').val());

                var $f = $("#fm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doNormalSubmit($f, insertReturnApplyDtlUrl, "btn-confirmPart", sCondition, savePartCallBack);
            }
        });

        // 关闭按钮绑定
        $("button.close").click(function() {
            $.pdialog.close(dialog);
            return false;
        });
    });

    // 确定按钮回调方法
    function savePartCallBack () {
        try {
            $("#edit-tab tr:not(:first)").remove();
            $("#edit-tabl").hide();
            // 查询退件申请明细
            searchReturnPurchaseApplyDtl();
            $.pdialog.closeCurrent();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 查询方法
    function retrnPurchaseSearch () {
    	var $f = $("#dia-fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, partSearchUrl+"?returnId="+returnId, "di_searchPart", 1, sCondition, "dia-tab-grid");
    }

    // 将数量字段渲染为文本框
    function createInputBox(obj) {
        return '<input type="text" style="width:100px;" name="COUNT" onblur="doMyInputBlur(this)"/ >';
    }

    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj) {
        var $obj = $(obj);
        var $tr = $obj.parent().parent().parent();
        // 库存数量
        var availableAmount = $tr.attr("AVAILABLE_AMOUNT");
        // 退件数量
        var s = $obj.val();
        if($obj.val() == "") {//为空直接返回
            return ;
        }
        if(!isAmount($obj) || $obj.val()=="0") {//不为空并且金额不正确
            alertMsg.warn("请输入正确的数量！");
            return;
        }
        if (parseInt(s)>parseInt(availableAmount)) {
            alertMsg.warn("不能大于库存数量！");
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
        var $input = $tr.find("td").eq(9).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT") {
            s = $input.val();
        } else {
            s = $tr.find("td").eq(9).text();
        }
        doCheckbox($obj.get(0));
    }

    // 列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^[0-9]*$/;//金额正则(最多两位小数)
        var s = "";
        if($tr.find("td").eq(9).find("input:first").size()>0) {
            s = $tr.find("td").eq(9).find("input:first").val();
        } else {
            s = $tr.find("td").eq(9).text();
        }
        if ($tr.attr("IF_RETURN")=="100102") {//为空或者不是金额
            alertMsg.warn("此配件无库存不可退!");
            $(checkbox).attr("checked",false);
            return false;
        }
        if (!s || !reg.test(s)) {//为空或者不是金额
            alertMsg.warn("请输入正确的金额!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        // 配件ID
        arr.push($tr.attr("PART_ID"));
        // 配件代码
        arr.push($tr.attr("PART_CODE"));
        // 配件名称
        arr.push($tr.attr("PART_NAME"));
        // 供应商ID
        arr.push($tr.attr("SUPPLIER_ID"));
        // 供应商代码
        arr.push($tr.attr("SUPPLIER_CODE"));
        // 供应商名称
        arr.push($tr.attr("SUPPLIER_NAME"));
        // 单位
        arr.push($tr.attr("UNIT"));
        // 退货数量
        arr.push(s);
        // 经销商价
        arr.push($tr.attr("SALE_PRICE"));
        // 金额
        arr.push(s*$tr.attr("SALE_PRICE"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(9).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(9).html("<div><input type='text' name='myCount' style='width:100px;' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /*
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(9);
        var val;
        if($("#val9") && $("#val9").val()) {
            var t = $("#val9").val();
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