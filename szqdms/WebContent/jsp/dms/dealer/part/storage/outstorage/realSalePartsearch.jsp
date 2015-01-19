<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-partsearch">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-partsearch">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="in-part_code" name="in-part_code" datasource="T.PART_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="in-part_name" name="in-part_name" datasource="T.PART_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
                        
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="part-btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-subMitart">确&nbsp;&nbsp;定</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_partList" >
            <table style="display:none;width:100%;" id="tab-partList" name="tab-partList" ref="page_partList" refQuery="fm-partsearch" >
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="SUPPLIER_NAME" refer="showSupplier">供应商</th>
                            <th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount" align="right">单价(元)</th>
                            <th fieldname="SALE_COUNT" refer="createInputBox"  colwidth="70" >实销数量</th>
                            <th fieldname="UNIT" style="display:none; ">单位</th>
                            <th fieldname="SUPPLIER_ID"   style="display: none;">供应商id</th>
                            <th fieldname="SUPPLIER_CODE"  style="display: none;">供应商code</th>
                            <th fieldname="SUPPLIER_NAME" style="display: none;">供应商name</th>
                            <th fieldname="STOCK_ID" style="display:none; ">仓储ID</th>
                            
                        </tr>
                    </thead>
            </table>
        </div>
        
        <fieldset id="fie-selectedPart" style="display:;">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;" id="tab-selectedPart">
                        <tr>
                            <td>
                                <textarea style="width:80%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none" id="val5" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val6" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none" id="val7" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val8" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val9" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;display:none" id="val10" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val11" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val12" name="multivals" readOnly></textarea>
                                
                            </td>
                        </tr>
                    </table>
                </div>
         </fieldset>
             <form id="fm-partInfo" >
                <input type="hidden" id="saleId" name="saleId" datasource="SALEID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="prices" name="prices" datasource="PRICES"/>
                <input type="hidden" id="counts" name="counts" datasource="SALECOUNTS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="units" name="units" datasource="UNITS"/>
                <input type="hidden" id="supplieIds" name="supplieIds" datasource="SUPPLIERIDS"/>
                <input type="hidden" id="supplieCodes" name="supplieCodes" datasource="SUPPLIERCODES"/>
                <input type="hidden" id="supplieNames" name="supplieNames" datasource="SUPPLIERNAMES"/>
                <input type="hidden" id="amounts" name="amounts" datasource="AMOUNTS"/>
                <input type="hidden" id="occamouts" name="occamouts" datasource="OCCUPY_AMOUNTS"/>
                <input type="hidden" id="avaamouts" name="avaamouts" datasource="AVAILABLE_AMOUNTS"/>
                 <input type="hidden" id="stockids" name="stockids" datasource="STOCK_IDS"/>
                
            </form>
    </div>
    </div>
</div>
<script type="text/javascript">
    //变量定义
    //查询提交方法
    var url = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/RealSaleOutAction";
    //弹出窗体
    var dialog = $("body").data("partsearch");
    //初始化方法
    $(function(){

        $("#tab-partList").attr("layoutH",document.documentElement.clientHeight-270);
        // 查询方法
        searchRealSalePart();

        // 查询配件方法
        $("#part-btn-search").bind("click",function(event){
            // 查询方法
            searchRealSalePart();
        });

        // 确定按钮绑定
        $('#btn-subMitart').bind('click',function(){
            // 添加实销明细单
            var insertSalePartUrl =  "<%=request.getContextPath()%>/part/storageMng/stockOutMng/RealSaleOutAction/realSaleDtlinsert.ajax";
            var partIds = $('#val0').val();
            if (!partIds) {
                alertMsg.warn('请选择配件!');
            } else {
                // 配件主键ID
                $('#saleId').val($('#dib-Sale_ID').val());
                $('#partIds').val($('#val0').val());
                $('#prices').val($('#val1').val());
                $('#partNames' ).val($('#val2').val());
                $('#counts').val($('#val3').val());
                $('#partCodes' ).val($('#val4').val());
                $('#units' ).val($('#val5').val());
                $('#supplieIds' ).val($('#val6').val());
                $('#supplieCodes' ).val($('#val7').val());
                $('#supplieNames' ).val($('#val8').val());
                $('#amounts' ).val($('#val9').val());
                $('#occamouts' ).val($('#val10').val());
                $('#avaamouts' ).val($('#val11').val());
                $('#stockids' ).val($('#val12').val());
                var $f = $("#fm-partInfo");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doNormalSubmit($f, insertSalePartUrl, "btn-subMitart", sCondition, searchPartCallBack);
            }
        });
        
    });

    // 供应商显示
    function showSupplier(obj){
        var $tr = $(obj).parent();
        var ifSuply= $tr.attr("IF_SUPLY");
        var str="";
        if(ifSuply=='<%=DicConstant.SF_01%>'){
            return "<div>"+$tr.attr("SUPPLIER_NAME")+"</div>";
        }else{
            return str;
        }
    }

    // 查询配件
    function searchRealSalePart() {
        var $f =$("#fm-partsearch");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl = url+"/realSalePartsearch.ajax";
        doFormSubmit($f,searchUrl,"part-btn-search",1,sCondition,"tab-partList");
    }
    // 确定按钮回调方法
    function searchPartCallBack () {
        try {
             // 查询实销配件
             searchPromPart();
             $.pdialog.close(dialog);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    function createInputBox(obj) {

         var $obj = $(obj);
          var $tr =  $obj.parent();
        return '<input type="text" name="SALE_COUNT" style="width:50px;" onblur="doMyInputBlur(this)"   />';
    }

    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")//为空直接返回
            return ;
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        if($obj.val() && !isAmount($obj))//不为空并且金额不正确
        {
            alertMsg.warn("请输入正确的数量！");
            checkObj.attr("checked",false);
            return;
        }
        checkObj.attr("checked",true);
        var s = $obj.val();
        var a = $tr.find("td").eq(5).text();
        if(parseInt(s)>parseInt(a)){
             alertMsg.warn("可用数量不足");
             checkObj.attr("checked",false);
             return;
        }
        $tr.find("td").eq(7).html("<div>"+s+"</div>");
        doCheckbox(checkObj.get(0));
    }

    function isAmount($obj) {
        var reg = /^(([1-9]\d*)|0)(\.\d{1,2})?$/
        if(reg.test($obj.val())) {
            return true;
        } else {
            return false;
        }
    }

    // 列表复选,步骤三
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^[0-9]*$/;//金额正则(最多两位小数)
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
        var availableAmount=$tr.find("td").eq(5).text();
        if(parseInt(availableAmount)<parseInt(s)){
        	alertMsg.warn("实销数量不能大于可用数量!");
        	$(checkbox).attr("checked",false);
        	return false;
        }
        var arr = [];
        // 配件ID
        arr.push($tr.attr("PART_ID"));
        //单价
        arr.push($tr.attr("SALE_PRICE"));
        // 配件名称
        arr.push($tr.attr("PART_NAME"));
        // 数量
        arr.push(s);
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("UNIT"));
        arr.push($tr.attr("SUPPLIER_ID"));
        arr.push($tr.attr("SUPPLIER_CODE"));
        arr.push($tr.attr("SUPPLIER_NAME"));
        arr.push($tr.attr("AMOUNT"));
        arr.push($tr.attr("OCCUPY_AMOUNT"));
        arr.push($tr.attr("AVAILABLE_AMOUNT"));
        arr.push($tr.attr("STOCK_ID"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(7).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(7).html("<div><input type='text' name='mycount' style='width:50px;' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /* 
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(7);
        var val;
        if($("#val3") && $("#val3").val()) {
            var t = $("#val3").val();
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