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
                            <th fieldname="QUANTITY" colwidth="60" refer="createInputBox" >配件数量</th>
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
                                <textarea style="width:400px;height:10px;display: none" id="part-val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display: none" id="part-val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="part-val2" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display: none" id="part-val3" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="quantity" name="promPrices" datasource="QUANTITY"/>
                <input type="hidden" id="activityIdHI" name="activityIdHI" datasource="ACTIVITYID"/>
                <input type="hidden" id="activityCodeHI" name="activityCodeHI" datasource="ACTIVITYCODE"/>
                <input type="hidden" id="activityNameHI" name="activityNameHI" datasource="ACTIVITYNAME"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction";
    //弹出窗体
    var partSelWin = $("body").data("servicePart");
    $(function () {
    	$("#tab-partList").attr("layoutH",document.documentElement.clientHeight-240);
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            //查询配件URL
            var $f = $("#fm-searchPart");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl =diaSaveAction+"/searchPart.ajax?&activityId="+$("#serviceActivityInfoId").val();
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-partList");
            $('#selectedPart').show();
        });
        //确定按钮响应
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var partIds = $('#part-val0').val();
            if(!partIds){
                alertMsg.warn('请选择配件!')
            }else{
                $('#promId').val($('#dia-PROM_ID').val());//促销活动ID
                $('#partIds').val(partIds);
                $('#partCodes').val($('#part-val1').val());
                $('#partNames').val($('#part-val2').val());
                $('#quantity').val($('#part-val3').val());
                $('#activityIdHI').val($("#serviceActivityInfoId").val());
                $('#activityCodeHI').val($("#DI_HDDM").val());
                $('#activityNameHI').val($("#DI_HDMC").val());
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
        var $f = $("#fm-fwhdxmpj");
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchServicePartUrl =diaSaveAction+"/searchServiceParts.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
 	    var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl =diaSaveAction+"/searchPart.ajax?&activityId="+$("#serviceActivityInfoId").val();
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-partList");
        $('#selectedPart').show();
        $("#part-val0").val("");
        $("#part-val1").val("");
        $("#part-val2").val("");
        $("#part-val3").val("");
    }

    //将本次促销价字段渲染为文本框
    function createInputBox(obj)
    {
        return '<input type="text" name="QUANTITY" onblur="doMyInputBlur(this)"/ >';
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

    function isNum($obj)
    {
    	var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
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
        var $input = $tr.find("td").eq(4).find("input:first");

        var s = "";
        if($input && $input.get(0).tagName=="INPUT")
            s = $input.val();
        else
        {
            s = $tr.find("td").eq(4).text();
        }
        doCheckbox($obj.get(0));
    }
    
    /*
     * 翻页回显方法:步骤四
     */
    function customOtherValue($row,checkVal)
    {
        var $inputObj = $row.find("td").eq(4);
        var val;
        if($("#part-val3") && $("#part-val3").val())
        {
            var t = $("#part-val3").val();
            var pks = $("#part-val0").val();
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