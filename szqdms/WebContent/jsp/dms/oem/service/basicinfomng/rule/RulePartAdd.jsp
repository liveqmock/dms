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
                                        <button type="button" id="btn-closePart" class="close" >关&nbsp;&nbsp;闭</button>
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
                            <th type="multi" name="XH"  style="" unique="PART_ID"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="MONTHS" colwidth="60" refer="intmomths" >月份</th>
                            <th fieldname="MILEAGE" colwidth="60" refer="intmileage" >里程</th>
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
                                <textarea id="part-val0" name="multivals" style="width:80%;height:26px;display:none" column="1" ></textarea>
                                <textarea style="width:400px;height:10px; display: "  id="part-val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px; display:none" id="part-val2" name="multivals" readOnly></textarea>
                                <textarea style="width:400px;height:10px;display:none" id="part-val3" name="multivals" readOnly></textarea>
                              	<textarea style="width:400px;height:10px;display:none" id="part-val4" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="monthss" name="monthss" datasource="MONTHS"/>
                <input type="hidden" id="mileages" name="mileages" datasource="MILEAGES"/>
                <input type="hidden" id="ruleIdHI" name="ruleIdHI" datasource="RULE_ID"/>
                <input type="hidden" id="rulecodehi" name="rulecodehi" datasource="RULE_CODE"/>
                <input type="hidden" id="rulenamehi" name="rulenamehi" datasource="RULE_NAME"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction";
var rulePartAction = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction";
    //弹出窗体
    var partSelWin = $("body").data("RulePart");
    $(function () {
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            //查询配件URL
            var $f = $("#fm-searchPart");
            var sCondition = {};
            sCondition = $f.combined() || {};
           var searchUrl =rulePartAction+"/searchPart.ajax?&ruleId="+$("#dia_RULE_ID").val();
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
                $('#monthss').val($('#part-val3').val());
                $('#mileages').val($('#part-val4').val());
                $('#ruleIdHI').val($("#dia_RULE_ID").val());
                $('#rulecodehi').val($("#dia_RULE_CODE").val());
                $('#rulenamehi').val($("#dia_RULE_NAME").val());
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
    	var searchServicePartUrl =diaSaveAction+"/searchServiceParts.ajax?&ruleId="+$("#dia_RULE_ID").val();
		doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"fwhdxmpjlb");
 	    var $f = $("#fm-searchPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl =diaSaveAction+"/searchPart.ajax?&ruleId="+$("#dia_RULE_ID").val();
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-partList");
        $('#selectedPart').show();
        $("#part-val0").val("");
        $("#part-val1").val("");
        $("#part-val2").val("");
        $("#part-val3").val("");
        $("#part-val4").val("");
        return true;
    }

 //月份
function intmomths(obj)
{
    return "<input type='text' style='width:50px;' id='MONTHS'  datasource='MONTHS' onblur='doMyInputBlur(this)' maxlength='6'/>";
}

//里程
function intmileage(obj)
{
    return "<input type='text' style='width:50px;' id='MILEAGE' datasource='MILEAGE' onblur='doMyInputmileage(this)' maxlength='20'/>";
}
   
    //input框焦点移开事件 步骤一
    function doMyInputBlur(obj){
    	
        var $obj = $(obj);
        if($obj.val() == "")//为空直接返回
        {
        	 return false;
        }
           
           
        if(!isNum($obj))//不为空并且金额不正确
        {
            alertMsg.warn("请输入正确的数量！");
            $(obj).val("");
            return false;
        }
        var $tr = $obj.parent().parent().parent();
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var s = $obj.val();
        if(s){
        $tr.find("td").eq(4).html("<div>"+s+"</div>");
        }else{
         $tr.find("td").eq(4).html("<div><input type='text' name='MONTHS' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
        }
        
           var m = "";
        if($tr.find("td").eq(5).find("input:first").size()>0)
            m = $tr.find("td").eq(5).find("input:first").val();
        else
            m = $tr.find("td").eq(5).text();
        
        if(s && m)
        {
        	 checkObj.attr("checked", true);
          	 doCheckbox(checkObj.get(0));
        }
       /*  if(s)
        {
            checkObj.attr("checked", true);
        } */
       // doSelectedBefore($tr,checkObj,1);
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
    
    
    
   
    //input框焦点移开事件 步骤二
    function doMyInputmileage(obj){
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
        var m;
         if($tr.find("td").eq(4).find("input:first").size()>0)
            m = $tr.find("td").eq(4).find("input:first").val();
        else
            m = $tr.find("td").eq(4).text();
        
        if(s){
          $tr.find("td").eq(5).html("<div>"+s+"</div>");
        }else{
          $tr.find("td").eq(5).html("<div><input type='text' name='MILEAGE' onblur='doMyInputBlur(this);' maxlength='6' value='"+s+"'/></div>");
        }
        if(s&&m){
         checkObj.attr("checked", true);	
         doCheckbox(checkObj.get(0));
        }
       /*  if(s)
        {
            checkObj.attr("checked", true);
        } */
       // doSelectedmileage($tr,checkObj,1);
    }
    
     function doSelectedmileage($tr,$obj,type)
    {
        var $input = $tr.find("td").eq(5).find("input:first");

        var s = "";
        if($input && $input.get(0).tagName=="INPUT")
            s = $input.val();
        else
        {
            s = $tr.find("td").eq(5).text();
        }
        doCheckbox($obj.get(0));
    }
    
</script>