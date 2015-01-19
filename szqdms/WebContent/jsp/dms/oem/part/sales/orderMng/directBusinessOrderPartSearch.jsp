<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="di_ycpj" style="width:100%;">
    <div class="page">
    <div class="pageHeader">
        <form id="dia-fm-condition" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="di_ycpjTable">
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="dia-partCode-Search" name="dia-partCode-Search" datasource="T.PART_CODE" datatype="1,is_null,100" operation="like" /></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="dia-partName-Search" name="dia-partName-Search" datasource="T.PART_NAME" datatype="1,is_null,100" operation="like" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li style="display:none;"><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_showTextBtn">添&nbsp;&nbsp;加</button></div></div></li>
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
                        <th type="multi" name="XH" unique="KEY_ID"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="UNIT">计量单位</th>
                        <th fieldname="" refer="appendPack_Unit">最小包装</th>
                        <!-- <th fieldname="SALE_PRICE" refer="formatAmount" align="right">经销商价(元)</th> -->
                        <th fieldname="RETAIL_PRICE" refer="formatAmount" align="right">零售价(元)</th>
                        <th fieldname="STOCK">库存</th>
                        <th fieldname="IF_SUPLY" style="display:none" colwidth="90">是否指定供应商</th>
                        <th fieldname="SUPPLIER_NAME" refer="showSupplier">供应商</th>
                        <th fieldname="" refer="createInputBox">订购数量</th>
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
                                <textarea style="width:80%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val5" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val6" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val7" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val8" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val9" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="diafm-partInfo" method="post" style="display:none">
                <input type="hidden" id="diaorderIds" name="diaorderIds" datasource="ORDERID"/>
                <input type="hidden" id="diapartIds" name="diapartIds" datasource="PARTIDS"/>
                <input type="hidden" id="diapartCodes" name="diapartCodes" datasource="PARTCODES"/>
                <input type="hidden" id="diapartNames" name="diapartNames" datasource="PARTNAMES"/>
                <input type="hidden" id="diaifSuplys" name="diaifSuplys" datasource="IFSUPPLIERS"/>
                <input type="hidden" id="diasupplierIds" name="diasupplierIds" datasource="SUPPLIERIDS"/>
                <input type="hidden" id="diasupplierCodes" name="diasupplierCodes" datasource="SUPPLIERCODES"/>
                <input type="hidden" id="diasupplierNames" name="diasupplierNames" datasource="SUPPLIERNAMES"/>
                <input type="hidden" id="diaunitPrices" name="diaunitPrices" datasource="UNITPRICES"/>
                <input type="hidden" id="diaamounts" name="diaamounts" datasource="AMOUNTS"/>
                <input type="hidden" id="diaorderCounts" name="diaorderCounts" datasource="ORDERCOUNTS"/>
            </form>
    </div>
    </div>
</div>
<script type="text/javascript">
    var partSearchUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction/searchPart.ajax";
    // 弹出窗体
    var dialog = $("body").data("directBusinessOrderPartSearch");
    // 初始化函数
    $(function() {
        $("#dia-tab-grid").attr("layoutH",document.documentElement.clientHeight-270);
        // 初始化查询配件
        forecastPartSearch();
        // 查询按钮绑定
        $("#di_searchPart").click(function() {
            // 查询配件方法
            forecastPartSearch();
        });

        // 确定按钮绑定
        $('#btn-confirmPart').bind('click',function(){
            // 添加预测配件明细URL
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction/insertSaleOrderDetail.ajax";
            var partIds = $('#val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
            	var arrayObj = new Array();
            	arrayObj = $('#val7').val().split(",");
            	var planPriceCheck = "";
            	for (var i=0;i<arrayObj.length;i++) {
            		if (arrayObj[i]==0||arrayObj[i]=="") {
            		    planPriceCheck+=planPriceCheck.length?"、"+$('#val1').val().split(",")[i]:$('#val1').val().split(",")[i];
            		}
            	}
            	if (planPriceCheck.length>0) {
            		var errorMsg = "请联系管理员维护配件零售价:"+planPriceCheck;
            		alertMsg.warn(errorMsg);
            		return false;
            	}
                // 订单主键ID
                $('#diaorderIds').val($('#diaOrderId').val());
                $('#diapartIds').val($('#val0').val());
                $('#diapartCodes').val($('#val1').val());
                $('#diapartNames').val($('#val2').val());
                $('#diaifSuplys').val($('#val3').val());
                $('#diasupplierIds').val($('#val4').val());
                $('#diasupplierCodes').val($('#val5').val());
                $('#diasupplierNames').val($('#val6').val());
                $('#diaunitPrices').val($('#val7').val());
                $('#diaamounts').val($('#val8').val());
                $('#diaorderCounts').val($('#val9').val());

                var $f = $("#diafm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doNormalSubmit($f, insertPromPartUrl, "di_showTextBtn", sCondition, searchPartCallBack);
            }
        });

        // 关闭按钮绑定
        $("button.close").click(function() {
            $.pdialog.close(dialog);
            return false;
        });
    });

    // 供应商显示
    function showSupplier(obj){
        var $tr = $(obj).parent();
        var ifSuply= $tr.attr("IF_SUPLY");
        var str="";
        if(ifSuply=='<%=DicConstant.SF_01%>') {
            str = $tr.attr("SUPPLIER_NAME");
        } else {
            str = "<div></div>";
        }
        return str;
    }

    // 确定按钮回调方法
    function searchPartCallBack (res) {
        try {
            $("#edit-tab tr:not(:first)").remove();
            $("#edit-tabl").hide();
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length>0){
                var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
                $("#diaOrderAmount").val(amount);
                $("#diaOrderAmount").val(amount);
                $("#divOrderAmount").html(amountFormatNew(amount)+"元");
                $("#divOrderAmount1").html(amountFormatNew(amount)+"元");
                $("#divOrderAmount2").html(amountFormatNew(amount)+"元");
            }
             $.pdialog.close(dialog);
             // 查询预测配件
             orderPartSearch();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 查询配件方法
    function forecastPartSearch() {
        var $f = $("#dia-fm-condition");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, partSearchUrl+"?orderId="+$("#diaOrderId").val()+"&warehouseId="+$("#diaWarehouseId").val(), "di_searchPart", 1, sCondition, "dia-tab-grid");
    }
    // 将数量字段渲染为文本框
    function createInputBox(obj) {
        return '<input type="text" style="width:50px;" name="COUNT" onblur="doMyInputBlur(this)"/ >';
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
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        if(s) {
            checkObj.attr("checked", true);
        }
        doSelectedBefore($tr,checkObj,1);
    }

    // 验证数字
    function isAmount($obj) {
        var reg = /^\+?[1-9][0-9]*$/;
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
        var $input = $tr.find("td").eq(10).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT") {
            s = $input.val();
        } else {
            s = $tr.find("td").eq(10).text();
        }
        doCheckbox($obj.get(0));
    }

    // 列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^\+?[1-9][0-9]*$/;//数量正则(最多两位小数)
        var s = "";
        if($tr.find("td").eq(10).find("input:first").size()>0) {
            s = $tr.find("td").eq(10).find("input:first").val();
        } else {
            s = $tr.find("td").eq(10).text();
        }
        if (!s || !reg.test(s)) {//为空或者不是数量
            alertMsg.warn("请输入正确的数量!");
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
        // 是否指定供应商
        arr.push($tr.attr("IF_SUPLY"));
        // 供应商ID
        arr.push($tr.attr("SUPPLIER_ID"));
        // 供应商代码
        arr.push($tr.attr("SUPPLIER_CODE"));
        // 供应商名称
        arr.push($tr.attr("SUPPLIER_NAME"));
        // 单价
        // arr.push($tr.attr("SALE_PRICE"));
        arr.push($tr.attr("RETAIL_PRICE"));
        // 金额
        // arr.push($tr.attr("SALE_PRICE")*s);
        arr.push($tr.attr("RETAIL_PRICE")*s);
        // 数量
        arr.push(s);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(10).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(10).html("<div><input type='text' name='myCount' style='width:50px;' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /*
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(10);
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