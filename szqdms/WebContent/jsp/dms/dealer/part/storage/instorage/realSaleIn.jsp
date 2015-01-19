<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
    <div class="page">
        <div class="pageHeader" >
            <form method="post" id="dib-fm-contract">
                <input  type="hidden"  id="sale_id" name="sale_id" datasource="SALE_ID"/>
                <table class="editTable" id="order_info">
                    <tr>
                        <td><label>实销单号：</label></td>
                        <td><input type="text" id="in-saleno" readonly name="in-saleno" datasource="SALE_NO" datatype="1,is_null,30" /></td>
                        <td><label>实销类型：</label></td>
                        <td>实销出库</td>
                    </tr>
                    <tr>
                       <td><label>客户名称：</label></td>
                       <td><input type="text" id="in-cusname" name="in-cusname" readonly datasource="CUSTOMER_NAME" datatype="1,is_null,30" /></td>
                          <td><label>联系电话：</label></td>
                       <td><input type="text" id="in-phone" name="in-phone" readonly datasource="LINK_PHONE" datatype="1,is_null,30"/></td>
                          <td><label>联系地址：</label></td>
                        <td>
                            <input type="text" id="in-addr"  name="in-addr" readonly style="width:75px;" datasource="LINK_ADDR" datatype="1,is_null,30"/>
                          </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="pageHeader" >
         <form method="post" id="dib-fmserch-contract">
            
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
                            <th fieldname="" refer="appendPack_Unit" >最小包装</th>
                            <th fieldname="UNIT">单位</th>
                            <th fieldname="SALE_COUNT" >实销出库数量</th>
                            <th fieldname="COUNT" refer="createInputBox"  colwidth="70" >退件数量</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount" align="right">零售价(元)</th>
                            <th fieldname="TJAMOUNT" refer="formatAmount" align="right">金额(元)</th>
                            <th fieldname="STOCK_ID" style="display: none;" >仓储ID</th>
                            
                        </tr>
                    </thead>
            </table>
        </div>
        
        <fieldset id="fie-selectedPart" style="display: none;">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;" id="tab-selectedPart">
                        <tr>
                            <td>
                                <textarea style="width:80%;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val3" name="multivals" readOnly></textarea>
                                
                            </td>
                        </tr>
                    </table>
                </div>
         </fieldset>
             <form id="fm-partInfo" >
                <input type="hidden" id="saleId" name="saleId" datasource="SALEID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="counts" name="counts" datasource="SALECOUNTS"/>
                <input type="hidden" id="amounts" name="amounts" datasource="AMOUNTS"/>
                <input type="hidden" id="stockids" name="stockids" datasource="STOCKIDS"/>
            </form>
            
            <div class="formBar">

            <ul>
                <li><div class="button"><div class="buttonContent"><button id="dib-save" type="button">实销退件入库</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" id="dib-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    //变量定义
    //查询提交方法
    var partSearchUrl = "<%=request.getContextPath()%>/part/storageMng/enterStorage/RealSaleInAction/realSaleDtlsearch.ajax";
    //初始化方法
    $(function(){
        var selectedRows = $("#tab-saleOut").getSelectedRows();
        setEditValue("dib-fm-contract",selectedRows[0].attr("rowdata"));
        var sale_id= $("#sale_id").val();
        
        var $f = $("#dib-fmserch-contract");
        var sCondition = {};
        sCondition = $("#dib-fmserch-contract").combined() || {};
        var searchurl =partSearchUrl+"?saleId="+$("#sale_id").val(); 
        doFormSubmit($f.eq(0),searchurl,"btn-search",1,sCondition,"tab-partList");        
        
        $('#dib-save').bind('click',function(){
             //订单URL
             var insertrealSalebackUrl = "<%=request.getContextPath()%>/part/storageMng/enterStorage/RealSaleInAction/realSalebackInsert.ajax";
             var partIds = $('#val0').val();
             if (!partIds) {
                 alertMsg.warn('请选择配件!');
             } else {
                 // 配件主键ID
                 $('#saleId').val($('#sale_id').val());
                 $('#partIds').val($('#val0').val());
                 $('#counts').val($('#val1').val());
                 $('#amounts').val($('#val2').val());
                 $('#stockids').val($('#val3').val());
                 var $f = $("#fm-partInfo");
                 var sCondition = {};
                 sCondition = $f.combined() || {};
                 //doFormSubmit($f, insertrealSalebackUrl, "dib-save", 1, sCondition, "");
                 doNormalSubmit($f, insertrealSalebackUrl, "dib-save", sCondition, insertOrderCallBack);
    
                 $.pdialog.close(dialog);
                
             }
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
    
    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
    
    function createInputBox(obj) {
         var $obj = $(obj);
          var $tr =  $obj.parent();
        return '<input type="text" name="COUNT" onblur="doMyInputBlur(this)"   />';
    }
    
    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")//为空直接返回
            return ;
        if($obj.val() && !isAmount($obj))//不为空并且金额不正确
        {
            alertMsg.warn("请输入正确的数量！");
            return;
        }
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        var price = $tr.find("td").eq(8).text();
        var a = $tr.find("td").eq(6).text();
        if(parseInt(s)>parseInt(a)){
            alertMsg.warn("超出出库数量");
            return;
       }
        var b = price*s;
        checkObj.attr("checked",true);
        $tr.find("td").eq(7).html("<div>"+s+"</div>");
        $tr.find("td").eq(9).html("<div>"+b+"</div>") 
        doCheckbox(checkObj.get(0));
    }
    
    function isAmount($obj)
    {
        var reg = /^(([1-9]\d*)|0)(\.\d{1,2})?$/
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
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
        var a = $tr.find("td").eq(9).text();
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push(s);
        arr.push(a);
        arr.push($tr.attr("STOCK_ID"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(7).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(7).html("<div><input type='text' name='mycount' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /* 
     * 翻页回显方法:步骤四
     */
     function customOtherValue($row,checkVal){
        var $inputObj = $row.find("td").eq(7);
        var val;
        if($("#val1") && $("#val1").val()) {
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
    
    
    function insertOrderCallBack(res){
            var rows = res.getElementsByTagName("ROW");
                if(rows && rows.length > 0)
                {
                    $.pdialog.close(orderCheckIn);
                    $("#btn-search").trigger("click");
                    
                }
                else{
                    
                }
    }
    
    var orderCheckIn = $("body").data("orderCheckIn");

</script>