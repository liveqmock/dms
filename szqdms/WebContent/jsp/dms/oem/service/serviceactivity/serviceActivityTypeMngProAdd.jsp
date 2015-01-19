<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPro">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>项目代码：</label></td>
                            <td><input type="text" id="dia-COSTS_CODE" name="dia-COSTS_CODE" datasource="COSTS_CODE"
                                       datatype="1,is_null,30" operation="like"/></td>
                            <td><label>项目名称：</label></td>
                            <td><input type="text" id="dia-COSTS_NAME" name="dia-COSTS_NAME" datasource="COSTS_NAME"
                                       datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPro">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-confirmPro">确&nbsp;&nbsp;定</button>
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
                <table style="display:none;width:100%;" multivals="projectVals" id="tab-proList" name="tablist" ref="div-partList"
                       refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="COST_ID" style=""></th>
                            <th fieldname="COSTS_CODE">项目代码</th>
                            <th fieldname="COSTS_NAME">项目名称</th>
                            <th fieldname="AMOUNT" colwidth="60" refer="createInputBox" align="right">金额</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedPro" style="display: none">
                <legend align="left" >&nbsp;[已选定项目]</legend>
                <div id="projectVals">
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display:none " id="pro-val0" column="1" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none " id="pro-val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="pro-val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display:none  " id="pro-val3" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-projectInfo">
                <input type="hidden" id="costIds" name="costIds" datasource="COST_ID"/>
                <input type="hidden" id="costsCodes" name="costsCodes" datasource="COSTS_CODE"/>
                <input type="hidden" id="costsNames" name="costsNames" datasource="COSTS_NAME"/>
                <input type="hidden" id="amounts" name="amounts" datasource="AMOUNT"/>
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
    var partSelWin = $("body").data("servicePro");
    $(function () {
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPro").bind("click", function (event) {
            var $f = $("#fm-searchPro");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl =diaSaveAction+"/searchProjects.ajax?&activityId="+$("#serviceActivityInfoId").val();
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-proList");
            $('#selectedPro').show();
        });
        //确定按钮响应
        $('#btn-confirmPro').bind('click',function(){
            //添加活动URL
            var costIds = $('#pro-val0').val();
            if(!costIds){
                alertMsg.warn('请选择项目!');
            }else{
                $('#costIds').val(costIds);
                $('#costsCodes').val($('#pro-val1').val());
                $('#costsNames').val($('#pro-val2').val());
                $('#amounts').val($('#pro-val3').val());
                $('#activityIdHI').val($("#serviceActivityInfoId").val());
                $('#activityCodeHI').val($("#DI_HDDM").val());
                $('#activityNameHI').val($("#DI_HDMC").val());
                var $f = $("#fm-projectInfo");
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                var insertPartUrl =diaSaveAction+"/insertPros.ajax";
                doNormalSubmit($f, insertPartUrl, "btn-confirmPro", sCondition, insertProCallBack);
                
            }
        });
    });

    //新增活动回调
    function insertProCallBack(){
        var $f = $("#fm-fwhdxmfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceProtUrl =diaSaveAction+"/searchServiceProjects.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceProtUrl,"searchProject",1,sCondition,"fwhdxmlb");
  		var $f = $("#fm-searchPro");
        var sCondition = {};
        sCondition = $f.combined() || {};
        var searchUrl =diaSaveAction+"/searchProjects.ajax?&activityId="+$("#serviceActivityInfoId").val();
        doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-proList");
        $('#selectedPro').show();
        $("#pro-val0").val("");
        $("#pro-val1").val("");
        $("#pro-val2").val("");
        $("#pro-val3").val("");
    }

    //将字段渲染为文本框
    function createInputBox(obj)
    {
        return '<input type="text" name="AMOUNT" onblur="doMyInputBlur(this)"/ >';
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
    	var reg = /^(([1-9]\d*)|0)(\.\d{1,2})?$/;//金额正则(最多两位小数)
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
        if($("#pro-val3") && $("#pro-val3").val())
        {
            var t = $("#pro-val3").val();
            var pks = $("#pro-val0").val();
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