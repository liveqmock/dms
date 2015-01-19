<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件代码：</label></td>
                            <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="PART_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="PART_NAME" datatype="1,is_null,30" operation="like"/></td>
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
                <table style="display:none;width:100%;" multivals="partVals" id="tab-partList" name="tablist" ref="div-partList"
                       refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="SE_CLPRICE"refer="amountFormat" align="right">索赔价格(元)</th>
                            <th fieldname="QUANTITY" colwidth="60" refer="createSLInputBox" >配件数量</th>
                            <th fieldname="REMARKS" colwidth="60" refer="createJEInputBox" align="right">备注</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedPart" style="display:none">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div id="partVals">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:400px;height:10px;display:none" id="part-val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display:none" id="part-val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="part-val2" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display:none" id="part-val3" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display:none" id="part-val4" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display:none" id="part-val5" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="dispatchIds" name="dispatchIds" datasource="DISPATCH_ID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="quantity" name="promPrices" datasource="QUANTITY"/>
                <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
                <input type="hidden" id="salePrice" name="salePrice" datasource="SALE_PRICE"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/guaranteesHurryPart/GuaranteesHurryPartAction";
    //弹出窗体
    var partSelWin = $("body").data("servicePart");
    $(function () {
    	$("#tab-partList").attr("layoutH",document.documentElement.clientHeight-230);
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            var $f = $("#fm-searchPart");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl =diaSaveAction+"/searchParts.ajax?&dispatchId="+$("#dispatchId").val();
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-partList");
            $('#selectedPart').show();
        });
        //确定按钮响应
        $("#btn-confirmPart").bind('click',function(){
            //添加促销配件URL
            var partIds = $('#part-val0').val();
            var dispatchId = $("#dispatchId").val();
            if(!partIds){
                alertMsg.warn('请选择配件!');
            }else{
                $('#dispatchIds').val(dispatchId);
                $('#partIds').val(partIds);
                $('#partCodes').val($('#part-val1').val());
                $('#partNames').val($('#part-val2').val());
                $('#quantity').val($('#part-val3').val());
                $('#remarks').val($("#part-val4").val());
                $('#salePrice').val($("#part-val5").val());
                //获取需要提交的form对象
                var $f = $("#fm-partInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                var insertPartUrl =diaSaveAction+"/insertParts.ajax";
                
                doNormalSubmit($f, insertPartUrl, "btn-confirmPart", sCondition, insertPartCallBack);
            }
        });
    });

    //新增促销配件回调
    function insertPartCallBack(){
        var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl =diaSaveAction+"/searchParts.ajax?&dispatchId="+$("#dispatchId").val();
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-partList");
        var $f = $("#fm-fwhdxmpj");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchHurryParts.ajax?&dispatchId="+$("#dispatchId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb"); 
        $('#selectedPart').show();
        $("#part-val0").val("");
        $("#part-val1").val("");
        $("#part-val2").val("");
        $("#part-val3").val("");
        $("#part-val4").val("");
        $("#part-val5").val("");
        $("#btn-closePart").click();
    }

    //将本次促销价字段渲染为文本框
    function createSLInputBox(obj)
    {
        return '<input type="text" name="QUANTITY" onblur="doMyInputBlur(this)"/ >';
    }
    function createJEInputBox(obj)
    {
        return '<input type="text" name="REMARKS" onblur="doMyRemarksInputBlur(this)"/ >';
    }

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
        if(s)
        {
            checkObj.attr("checked", true);
        }
        doSelectedBefore($tr,checkObj,1);
    }
    //input框焦点移开事件 步骤一
    function doMyRemarksInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")//为空直接返回
            return ;
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        if(s)
        {
            checkObj.attr("checked", true);
        }
        doSelectedBefore1($tr,checkObj,1);
    }
    function isNum($obj)
    {
    	var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
            return false;
            $("#QUANTITY").val("");
        }
    }
    /**
     * $tr:当前行对象jquery 步骤二
     * @param $obj:checkbox的jQuery对象
     * @param type:
     */
    function doSelectedBefore($tr,$obj,type)
    {
        var $input = $tr.find("td").eq(5).find("input:first");

        var sl = "";
        if($input && $input.get(0).tagName=="INPUT")
            sl = $input.val();
        else
        {
            sl = $tr.find("td").eq(5).text();
        }
        doCheckbox($obj.get(0));
    }
    /**
     * $tr:当前行对象jquery 步骤二
     * @param $obj:checkbox的jQuery对象
     * @param type:
     */
    function doSelectedBefore1($tr,$obj,type)
    {
        var $input = $tr.find("td").eq(6).find("input:first");

        var bz = "";
        if($input && $input.get(0).tagName=="INPUT")
        	bz = $input.val();
        doCheckbox($obj.get(0));
    }
    /*
     * 翻页回显方法:步骤四
     */
    function customOtherValue($row,checkVal)
    {
        var $inputSl = $row.find("td").eq(5);
        var $inputBz = $row.find("td").eq(6);
        var valsl;
        var valbz;
        if($("#part-val3") && $("#part-val3").val())
        {
            var sl = $("#part-val3").val();
            var bz = $("#part-val4").val();
            var pks = $("#part-val0").val();
            var sls = sl.split(",");
            var bzs = bz.split(",");
            var pk = pks.split(",");
            for(var i=0;i<pk.length;i++)
            {
                if(pk[i] == checkVal)
                {
                    valsl = sls[i];
                    valbz = bzs[i];
                    break;
                }
            }
        }
        if(valsl)
        {
            $inputSl.html("<div>"+valsl+"</div>");
            if(valbz=="anull"){
           	 $inputBz.html("<input type='text' name='REMARKS' width='10px' onblur='doMyRemarksInputBlur(this)' value='' />");
           }else{
           	 $inputBz.html("<input type='text' name='REMARKS' width='10px' onblur='doMyRemarksInputBlur(this)' value='"+valbz+"' />");
           }
        } 
        
    }
</script>