<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="T.PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="T.PART_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-partList" multivals="div-selectedPart" name="tablist" ref="div-partList" refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="KEY_ID" style=""></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT" colwidth="50">计量单位</th>
                            <th fieldname="MIN_PACK" colwidth="50" refer="toAppend">最小包装</th>
                            <th fieldname="SALE_PRICE" colwidth="60" refer="formatAmount" align="right">军品销售价</th>
                            <th fieldname="STOCK">库存</th>
                            <th fieldname="IF_SUPLY" style="display:none" colwidth="90">是否指定供应商</th>
                            <th fieldname="SUPPLIER_NAME" refer="showSupplier">供应商</th>
                            <th fieldname="ORDER_COUNT" colwidth="50" refer="myInput">订购数量</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="fie-selectedPart" style="display:none">
                <legend align="left">&nbsp;[已选定配件]</legend>
                <div id="div-selectedPart">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:10px;display: none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:30px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val4" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val5" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val6" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val7" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:10px;display: none" id="val8" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo" method="post">
                <input type="hidden" id="orderId" name="orderId" datasource="ORDERID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="supplierIds" name="supplierIds" datasource="SUPPLIERIDS"/>
                <input type="hidden" id="supplierCodes" name="supplierCodes" datasource="SUPPLIERCODES"/>
                <input type="hidden" id="supplierNames" name="supplierNames" datasource="SUPPLIERNAMES"/>
                <input type="hidden" id="orderCounts" name="orderCounts" datasource="ORDERCOUNTS"/>
                <input type="hidden" id="salePrices" name="salePrices" datasource="SALEPRICES"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
	var armyPartSelWin = $("body").data("armyPartSelWin");
	//配件查询
	function diaAddPartSearch(){
	    var searchPartUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/ArmyPartOrderReportAction/orderPartAddSearch.ajax?orderId="+$("#diaOrderId").val()+"&warehouseId="+$("#diaWarehouseId").val();
	    var $f = $("#fm-searchPart");
	    var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
	    $('#fie-selectedPart').show();
	}
    $(function () {
    	$("#tab-partList").attr("layoutH",document.documentElement.clientHeight-220);
        $("#btn-closePart").click(function () {
            $.pdialog.close(armyPartSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            var searchPartUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/ArmyPartOrderReportAction/orderPartAddSearch.ajax?orderId="+$("#diaOrderId").val()+"&warehouseId="+$("#diaWarehouseId").val()+"&orderType="+$("#dia-orderType").val()+"&promId="+$("#diaPromId").attr("code")+"&directTypeId="+$("#diaDirectTypeId").val();
            var $f = $("#fm-searchPart");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
            $('#fie-selectedPart').show();
        });
        $('#btn-confirmPart').bind('click',function(){
            var insertPartUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/PartOrderReportAction/addPartSubmit.ajax";
            var keyIds = $('#val0').val();
            if(!keyIds){
                alertMsg.warn('请选择配件!');
            }else{
                $('#orderId').val($('#diaOrderId').val());
                $('#partIds').val($('#val8').val());
                $('#partCodes').val($('#val1').val());
                $('#partNames').val($('#val2').val());
                $('#supplierIds').val($('#val3').val());
                $('#supplierCodes').val($('#val4').val());
                $('#supplierNames').val($('#val5').val());
                $('#orderCounts').val($('#val6').val());
                $('#salePrices').val($('#val7').val());
                var $f = $("#fm-partInfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPartUrl, "btn-confirmPart", sCondition, insertPartCallBack);
            }
        });
        diaAddPartSearch();
    });
	//配件新增回调
    function insertPartCallBack(res){
    	orderPartSearch();
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length>0){
			var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
			$("#diaOrderAmount").val(amount);
			$("#divOrderAmount").html(amountFormatNew(amount)+"元");
			$("#divOrderAmount1").html(amountFormatNew(amount)+"元");
        	$("#divOrderAmount2").html(amountFormatNew(amount)+"元");
			var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/partOrderSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
		}
		if($("#dia-orderType").val()==<%=DicConstant.DDLX_06%>){
			$("#diaPromId").attr("disabled",true);
		}
		if($("#dia-orderType").val()==<%=DicConstant.DDLX_05%>){
			$("#diaDirectTypeCode").attr("disabled",true);
		}
		//关闭窗口
        $("#btn-closePart").trigger("click");
    }
	//最小包装
	function toAppend(obj){
		 var $tr = $(obj).parent();
		 return $tr.attr("MIN_PACK")+"/"+ $tr.attr("MIN_UNIT_sv");
	}
	//供应商显示
	function showSupplier(obj){
		var $tr = $(obj).parent();
		var ifSuply= $tr.attr("IF_SUPLY");
		var str="";
		if(ifSuply=='<%=DicConstant.SF_01%>'){
			str = $tr.attr("SUPPLIER_NAME");
		}else{
			str = "<div></div>";
		}
		return str;
	}
	//订购数量
	function myInput(obj)
    {
        return "<input type='text' style='width:50px;' name='myCount' onblur='doMyInputBlur(this)' maxlength='6'/>";
    }
	//经销商价
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
	//订购数量焦点离开
    function doMyInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")
            return false;
        if($obj.val() && !isAmount($obj))
        {
            alertMsg.warn("请正确输入订购数量！");
            return false;
        }
        var $tr = $obj.parent().parent().parent();
        if(orgType==<%=DicConstant.ZZLB_09%>){
        	var minPack= $tr.attr("MIN_PACK");
        	if(($obj.val()%minPack)){
        		alertMsg.warn("订购数量应为最小包装倍数,请重新输入.");
        		return false;
        	}
        }
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        if(s)
        {
            checkObj.attr("checked", true);
        }
        doSelectedBefore($tr,checkObj,1);
    }
	//校验订购数量
    function isAmount($obj)
    {
        var reg = /^\+?[1-9][0-9]*$/;
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
            return false;
        }
    }
	//订购数量输入框与文本切换
    function doSelectedBefore($tr,$obj,type)
    {
        var $input = $tr.find("td").eq(10).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT")
            s = $input.val();
        else
        {
            s = $tr.find("td").eq(10).text();
        }
        doCheckbox($obj.get(0));
    }
	//复选隐藏域赋值控制
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^\+?[1-9][0-9]*$/;
        var s = "";
        if($tr.find("td").eq(10).find("input:first").size()>0)
            s = $tr.find("td").eq(10).find("input:first").val();
        else
            s = $tr.find("td").eq(10).text();
        if (!s || !reg.test(s)){
            alertMsg.warn("请正确输入订购数量!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        arr.push($tr.attr("KEY_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push($tr.attr("SUPPLIER_ID"));
        arr.push($tr.attr("SUPPLIER_CODE"));
        arr.push($tr.attr("SUPPLIER_NAME"));
        arr.push(s);
        arr.push($tr.attr("SALE_PRICE"));
        arr.push($tr.attr("PART_ID"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        if($checkbox.is(":checked")){
        	$tr.find("td").eq(10).html("<div>"+s+"</div>");
        	//$tr.find("td").eq(9).find("input:first").attr("disabled",true);
        }else{
            $tr.find("td").eq(10).html("<div><input type='text' style='width:50px;' name='myCount' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
            //$tr.find("td").eq(9).find("input:first").attr("disabled",false);
        }
    }
	//翻页复选回显
    function customOtherValue($row,checkVal)
    {
        var $inputObj = $row.find("td").eq(10);
        var val;
        if($("#val6") && $("#val6").val())
        {
            var t = $("#val6").val();
            var pks = $("#val0").val();
            var ss = t.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    val = ss[i];
                    break;
                }
            }
        }
        if(val)
        {
            $inputObj.html("<div>"+val+"</div>");
        }
    }
</script>