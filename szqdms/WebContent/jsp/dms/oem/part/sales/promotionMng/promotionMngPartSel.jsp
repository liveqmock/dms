<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                            <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="PART_CODE"
                                       datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="PART_NAME"
                                       datatype="1,is_null,30" operation="like"/></td>
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
                            <th fieldname="PART_NO" style="display:none">配件图号</th>
                            <th fieldname="UNIT">计量单位</th>
                            <th fieldname="MIN_PACK" refer="toappendStr">最小包装</th>
                            <th fieldname="SALE_PRICE" refer="formatAmount">经销商价</th>
                            <th fieldname="PROM_PRICE" colwidth="60" refer="createInputBox" >本次促销价</th>
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
                                <textarea style="width:80%;height:26px;display: none" id="val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val4" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="promId" name="promId" datasource="PROMID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="partNos" name="partNos" datasource="PARTNOS"/>
                <input type="hidden" id="promPrices" name="promPrices" datasource="PROMPRICES"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    $(function () {
    	$("#tab-partList").attr("layoutH",document.documentElement.clientHeight-240);
    	tosearchPart();
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            //查询配件URL
            tosearchPart();
        });
        //确定按钮响应
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction/insertPromPart.ajax";
            var partIds = $('#val0').val();
            if(!partIds){
                alertMsg.warn('请选择配件!');
            }else{
                $('#promId').val($('#dia-PROM_ID').val());//促销活动ID
                $('#partIds').val(partIds);
                $('#partCodes').val($('#val1').val());
                $('#partNames').val($('#val2').val());
                $('#promPrices').val($('#val4').val());

                //获取需要提交的form对象
                var $f = $("#fm-partInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertPromPartUrl, "btn-confirmPart", sCondition, insertPromPartCallBack);
            }
        });
    });
	function tosearchPart(){
		var searchPartUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction/searchPart.ajax?promotionId="+$('#dia-PROM_ID').val();
        var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
        $('#fie-selectedPart').show();
	}
    //新增促销配件回调
    function insertPromPartCallBack(){
        $.pdialog.close(partSelWin);
        //查询促销配件
        searchPromPart();
    }

    function formatAmount(obj){
        return amountFormatNew($(obj).html());
    }
	function toappendStr(obj){
		var $tr = $(obj).parent();
		return $tr.attr("MIN_PACK")+"/"+ $tr.attr("MIN_UNIT_sv");
	}
    //将本次促销价字段渲染为文本框
    function createInputBox(obj)
    {
        return '<input type="text" name="PROM_PRICE" onblur="doMyInputBlur(this)"/ >';
    }

    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj){
        var $obj = $(obj);
        if($obj.val() == "")//为空直接返回
            return ;
        if($obj.val() && !isAmount($obj))//不为空并且金额不正确
        {
            alertMsg.warn("请输入正确的金额！");
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

    function isAmount($obj)
    {
    	var reg = /\d+\.?\d{0,9}/;
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
        var $input = $tr.find("td").eq(8).find("input:first");

        var s = "";
        if($input && $input.get(0).tagName=="INPUT")
            s = $input.val();
        else
        {
            s = $tr.find("td").eq(8).text();
        }
        doCheckbox($obj.get(0));
    }
    //列表复选,步骤三
    function doCheckbox(checkbox) {

        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^\d+\.?\d{0,9}/;//金额正则(最多两位小数)
        //var reg = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
        var s = "";
        if($tr.find("td").eq(8).find("input:first").size()>0)
            s = $tr.find("td").eq(8).find("input:first").val();
        else
            s = $tr.find("td").eq(8).text();
        if (!s || !reg.test(s)){//为空或者不是金额
            alertMsg.warn("请输入正确的金额!");
            $(checkbox).attr("checked",false);
            return false;
        }
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push(s);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked"))
            $tr.find("td").eq(8).html("<div>"+s+"</div>");
        else
        {
            $tr.find("td").eq(8).html("<div><input type='text' name='myCount' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'class='mini_txt'/></div>");
        }
    }
    /*
     * 翻页回显方法:步骤四
     */
    function customOtherValue($row,checkVal)
    {
        var $inputObj = $row.find("td").eq(8);
        var val;
        if($("#val4") && $("#val4").val())
        {
            var t = $("#val4").val();
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