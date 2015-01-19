<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPart1">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="T.PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="T.PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                    		<td><input type="hidden" id="deliveryCycle" datasource="T1.APPLY_CYCLE" /></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-partList" multivals="div-selectedPart" name="tablist" ref="div-partList"
                       refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="MIN_PACK">最小包装</th>
                            <th fieldname="PCH_COUNT" refer="createInputBox">采购数量</th>
                            <th fieldname="PCH_PRICE">采购价格(元)</th>
                            <th fieldname="PCH_AMOUNT">金额(元)</th>
                            <th fieldname="APPLY_CYCLE">供货周期(天)</th>
                            <th fieldname="REMARK" refer="createInput">备注</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="fie-selectedPart" style="display: none">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val5" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val6" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none" id="val7" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="splitId" name="splitId" datasource="SPLITID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="pchCounts" name="pchCounts" datasource="PCHCOUNTS"/>
                <input type="hidden" id="pchPrices" name="pchPrices" datasource="PCHPRICES"/>
                <input type="hidden" id="pchAmounts" name="pchAmounts" datasource="PCHAMOUNTS"/>
                <input type="hidden" id="deliveryCycles" name="deliveryCycles" datasource="DELIVERYCYCLES"/>
                <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    var url = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/PurchaseOrderModifyAction";
    $(function () {
    	$("#deliveryCycle").val($("#dia-DELIVERY_CYCLE").val());
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            //查询配件URL
            var $f = $("#fm-searchPart1");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchPartUrl = url+"/searchPart.ajax?SPLIT_ID="+$("#dia-SPLIT_ID").val()+"&SUPPLIER_ID="+$("#dia-SUPPLIER_ID").val();
            doFormSubmit($f, searchPartUrl, "btn-searchPart", 1, sCondition, "tab-partList");
            $('#fie-selectedPart').show();
        });
        //确定按钮响应
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var insertPromPartUrl = url+"/updateOrderSplitPart.ajax";
            var partIds = $('#val0').val();
            if(!partIds){
                alertMsg.warn('请选择配件!');
            }else{
                $('#splitId').val($('#dia-SPLIT_ID').val());
                $('#partIds').val(partIds);
                $('#partNames').val($('#val1').val());
                $('#partCodes').val($('#val2').val());
                $('#pchCounts').val($('#val3').val());
                $('#pchPrices').val($('#val4').val());
                $('#pchAmounts').val($('#val5').val());
                $('#deliveryCycles').val($('#val6').val());
                $('#remarks').val($('#val7').val());
                var $f = $("#fm-partInfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-confirmPart", sCondition, insertPromPartCallBack); 
            }
        });
    });

    //新增促销配件回调
    function insertPromPartCallBack(){
        $.pdialog.close(partSelWin);
        searchPart();
    }

    //将本次促销价字段渲染为文本框
    function createInputBox(obj)
    {
        return '<input type="text" name="PCH_COUNT" onblur="doMyInputBlur(this)"/ >';
    }
    
    function createInput(obj)
    {
        return '<input type="text" name="REMARK" onblur="doMyInputCheck(this)"/ >';
    }
    function doMyInputCheck(obj){
   	   var $obj = $(obj);
   	    if($obj.val() == "")//为空直接返回
   	        return ;
   	    var $tr = $obj.parent().parent().parent();
   	    var checkObj = $("input:first",$tr.find("td").eq(1));
   	    var bz = $obj.val();
   	    if(bz)
   	    {
   	        checkObj.attr("checked", true);
   	    }
   	    doSelectedBefore1($tr,checkObj,1);
 };
 

    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")//为空直接返回
            return ;
        if($obj.val() && !isNum($obj))//不为空并且金额不正确
        {
            alertMsg.warn("请输入正确的数量！");
            $(obj).val("");
            return;
        }
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        var count = $tr.find("td").eq(7).text();
        var a = count*s;
        if(s)
        {
            checkObj.attr("checked", true);
        }
        doSelectedBefore($tr,checkObj,1);
    }
    function isNum($obj)
    {
        var reg = /^[1-9]\d*$/
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
            return false;
        }
    }
    /**
     * $tr:当前行对象jquery 步骤二
     * @param $obj:checkbox的jQuery对象
     * @param type:
     */
    function doSelectedBefore($tr,$obj,type)
    {
        var $input = $tr.find("td").eq(6).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT"){
            s = $input.val();
            
        }else
        {
            s = $tr.find("td").eq(6).text();
        }
        doCheckbox($obj.get(0));
    }
    function doSelectedBefore1($tr,$obj,type)
    {
        var $input = $tr.find("td").eq(6).find("input:first");

        var bz = "";
        if($input && $input.get(0).tagName=="INPUT")
        	bz = $input.val();
        doCheckbox($obj.get(0));
    }
    //列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^[1-9]\d*$/;
        var s = "";
        if($tr.find("td").eq(6).find("input:first").size()>0)
            s = $tr.find("td").eq(6).find("input:first").val();
        else
            s = $tr.find("td").eq(6).text();
        if (!s || !reg.test(s)){
            alertMsg.warn("请输入正确的数量!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var count = $tr.find("td").eq(7).text();
        var a = count*s;
        var r = $tr.find("td").eq(10).find("input:first").val();
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_NAME"));
        arr.push($tr.attr("PART_CODE"));
        arr.push(s);
        arr.push($tr.attr("PCH_PRICE"));
        arr.push(a);
        arr.push($tr.attr("APPLY_CYCLE"));
        if(r.length==0){
	    	arr.push("anull");
	    }else{
	    	arr.push(r);
	    }
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        
        //设置input框显示或文本只读
        if($checkbox.is(":checked")){
            $tr.find("td").eq(8).html("<div>"+a+"</div>");
        }else
        {
            $tr.find("td").eq(6).html("<div><input type='text' name='myCount' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /*
     * 翻页回显方法:步骤四
     */
    function customOtherValue($row,checkVal)
    {
        var $inputObj = $row.find("td").eq(6);
        var $inputObj1 = $row.find("td").eq(8);
        var $inputObj2 = $row.find("td").eq(10);
        var val;
        var val1;
        var val2;
        if($("#val3") && $("#val3").val())
        {
            var counts = $("#val3").val();
            var pks = $("#val0").val();
            var count = counts.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val = count[i];
                    break;
                }
            }
        }
        if(val)
        {
            $inputObj.html("<div>"+val+"<div/ >");
        }
        if($("#val5") && $("#val5").val())
        {
            var amounts = $("#val5").val();
            var pks = $("#val0").val();
            var amount = amounts.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val1 = amount[i];
                    break;
                }
            }
        }
        if(val1)
        {
            $inputObj1.html("<div>"+val1+"<div/>");
        }
        if($("#val7") && $("#val7").val())
        {
            var remarks = $("#val7").val();
            var pks = $("#val0").val();
            var remark = remarks.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val2 = remark[i];
                    break;
                }
            }
        }
        if(val2)
        {
            $inputObj2.html("<input type='text' name='REMARK' onblur='doMyInputCheck(this)' value='"+val2+"'/ >");
        }
    }
</script>