<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>促销配件</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--基本信息Tab begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="fm-promInfo" class="editForm">
                    <%--隐藏域查询条件--%>
                    <input type="hidden" id="dia-PROM_ID" name="dia-PROM_ID" datasource="PROM_ID"/>
                    <div align="left">
                        <fieldset>
                            <legend align="right"><a onclick="onTitleClick('tab-promInfo')">&nbsp;促销活动信息明细&gt;&gt;</a>
                            </legend>
                            <table class="editTable" id="tab-promInfo">
                                <tr>
                                    <td><label>活动代码：</label></td>
                                    <td><div id="diaOrderNo"></div></td>
                                    <td><label>活动名称：</label></td>
                                    <td><input type="text" id="dia-PROM_NAME" name="dia-PROM_NAME" datasource="PROM_NAME" datatype="0,is_null,30"/></td>
                                </tr>
                                <tr>
                                    <td><label>活动类型：</label></td>
                                    <td>
                                        <input type="text" id="dia-PROM_TYPE" name="dia-PROM_TYPE" src="CXHDLX" datasource="PROM_TYPE" kind="dic" datatype="0,is_null,30" value=""/>
                                    </td>
                                    <td><label>促销通知号：</label></td>
                                    <td><input type="text" id="dia-ANNOUNCEMENT_NO" name="dia-ANNOUNCEMENT_NO" datasource="ANNOUNCEMENT_NO" datatype="0,is_null,100"/></td>
                                </tr>
                                <tr>
                                    <td><label>活动开始日期：</label></td>
                                    <td>
                                        <input name="dia-START_DATE" id="dia-START_DATE" group="dia-START_DATE,dia-END_DATE" kind="date" class="Wdate" datasource="START_DATE" group="START_DATE,END_DATE" datatype="0,is_date,20" onclick="WdatePicker()" value="" type="text"/>
                                    </td>
                                    <td><label>活动结束日期：</label></td>
                                    <td>
                                        <input name="dia-END_DATE" id="dia-END_DATE" group="dia-START_DATE,dia-END_DATE" kind="date" class="Wdate" datasource="END_DATE" group="START_DATE,END_DATE" datatype="0,is_date,20" onclick="WdatePicker()" value="" type="text"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label>是否免运费：</label></td>
                                    <td>
                                    	<input type="text" id="dia-IF_TRANS_FREE" name="dia-IF_TRANS_FREE" datasource="IF_TRANS_FREE" datatype="0,is_null,100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label>备注：</label></td>
                                    <td colspan="6"><textarea class="" rows="3" id="dia-remarks" name="dia-remarks" datasource="REMARKS" style="width:500px;" datatype="1,is_null,500"></textarea></td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="btn-downAtt">下载附件</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" name="btn-next">下&nbsp;一&nbsp;步</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <!--基本信息Tab end-->
            <!--促销配件Tab begin-->
            <div>
                <form method="post" id="fm-searchPromPart" class="editForm">
                    <table class="searchContent" id="tab-searchPromPart"></table>
                </form>
                <form id="diaSaveFm" method="post">
		                <input type="hidden" id="PRO_ID" name="PRO_ID" datasource="PRO_ID"/>
		                <input type="hidden" id="DTL_ID" name="DTL_ID" datasource="DTL_ID"/>
		                <input type="hidden" id="N_PRICE" name="N_PRICE" datasource="N_PRICE"/>
		            </form>
                <div id="div-promPartList">
                    <table style="display:none;width:100%;" id="tab-promPartList" name="tablist"
                           ref="div-promPartList" refQuery="tab-searchPromPart">
                        <thead>
                        <tr>
                            <th type="single" name="XH" append="plus|openPartSelWin"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="PART_NO" style="display:none">配件图号</th>
                            <th fieldname="UNIT" colwidth="60" >计量单位</th>
                            <th fieldname="MIN_PACK" colwidth="60"  refer="toAppend">最小包装</th>
                            <th fieldname="SALE_PRICE"  colwidth="60" style="text-align:right;" refer="formatAmount">经销商价</th>
                            <th fieldname="PROM_PRICE"  colwidth="60" style="text-align:right;" >促销价</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="formBar">
                    <ul>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="btn-downTpl">配件模版导出</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="btn-impPromPart">配件明细导入</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" name="btn-pre">上一步</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//注册上一步/下一步按钮响应
(function ($) {
    $.promotionMng = {
        /**
         * 初始化方法
         */
        init: function () {
            //设置页面默认值
            var $tabs = $("div.tabs:first");
            var iH = document.documentElement.clientHeight;
            $(".tabsContent").height(iH - 70);
            $("button[name='btn-next']").bind("click", function () {
                $.promotionMng.doNextTab($tabs);
            });
            $("button[name='btn-pre']").bind("click", function () {
                $.promotionMng.doPreTab($tabs);
            });
        },
        doNextTab: function ($tabs) {
            $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
            (function (ci) {
                switch (parseInt(ci)) {
                    case 1://第2个tab页
                            searchPromPart();
                        break;
                    default:
                        break;
                }
            })(parseInt($tabs.attr("currentIndex")));
        },
        doPreTab: function ($tabs) {
            $tabs.switchTab($tabs.attr("currentIndex") - 1);
        }
    };
})(jQuery);

var diaSaveAction = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction";
var diaAction = "<%=action%>";

$(function () {
	$("#tab-promPartList").attr("layoutH",document.documentElement.clientHeight-200);
    $.promotionMng.init();

    //上传附件
    $("#btn-addAtt").bind("click", function () {
        var promId = $("#dia-PROM_ID").val();
        if(!promId){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        $.filestore.open(promId, {"folder": "true", "holdName": "true", "fileSizeLimit": 0, "fileTypeDesc": "All Files", "fileTypeExts": "*.*"});
    });

    //下载附件
    $("#btn-downAtt").bind("click", function () {
        var promId = $("#dia-PROM_ID").val();
        if(!promId){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        $.filestore.view(promId);
    });


    if (diaAction != "1") {//修改
        var selectedRows = $("#tab-promotionList").getSelectedRows();
        
        setEditValue("fm-promInfo", selectedRows[0].attr("rowdata"));
        $("#diaOrderNo").html(selectedRows[0].attr("PROM_CODE"));
        
        $("input").attr("readonly",true);
    }
});
function toAppend(obj){
	var $tr = $(obj).parent();
	return $tr.attr("MIN_PACK")+"/"+ $tr.attr("MIN_UNIT_sv");
}


function setDiaDefaultValue() {
    //有效标示
    $("#dia-IF_TRANS_FREE").val("<%=Constant.SF_02%>");
    $("#dia-IF_TRANS_FREE").attr("code", "<%=Constant.SF_02%>");
    $("#dia-IF_TRANS_FREE").find("option").val("<%=Constant.SF_02%>");
    $("#dia-IF_TRANS_FREE").find("option").text("否");
}


//查询促销配件
function searchPromPart() {
    var searchPromPartUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction/searchPromPart.ajax?promotionId=" + $('#dia-PROM_ID').val();
    var $f = $("#fm-searchPromPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchPromPartUrl, "", 1, sCondition, "tab-promPartList");
}
//查询促销范围
function searchPromArea() {
    var searchPromAreaUrl = "<%=request.getContextPath()%>/part/salesMng/promotionMng/PromotionMngAction/searchPromArea.ajax?promotionId=" + $('#dia-PROM_ID').val();
    var $f = $("#fm-searchPromArea");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchPromAreaUrl, "", 1, sCondition, "tab-promAreaList");
}

function formatAmount(obj){
	return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}

//促销活动促销价格生成INPUT方法
function myOrderCount(obj){
	return "<input type='text' style='width:50px;' name='myCount' value='"+$(obj).html()+"' onblur='doMyCountBlur(this)' maxlength='6'/>";
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
</script>