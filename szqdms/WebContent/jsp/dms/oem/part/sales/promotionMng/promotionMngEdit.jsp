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
                    <li><a href="javascript:void(0);"><span>促销范围</span></a></li>
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
                            <legend align="right"><a onclick="onTitleClick('tab-promInfo')">&nbsp;促销活动信息编辑&gt;&gt;</a>
                            </legend>
                            <table class="editTable" id="tab-promInfo">
                                <tr>
                                    <td><label>活动代码：</label></td>
                                    <td><div id="diaOrderNo">系统自动生成</div></td>
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
                                        <select class="combox" id="dia-IF_TRANS_FREE" name="dia-IF_TRANS_FREE" kind="dic" src="SF" datasource="IF_TRANS_FREE" datatype="0,is_null,10">
                                            <option value="-1" selected>--</option>
                                        </select>
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
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" id="btn-saveBaseInfo">保存基本信息</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="btn-addAtt">添加附件</button>
                                </div>
                            </div>
                        </li>
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
                            <th fieldname="PROM_PRICE"  colwidth="60" style="text-align:right;" refer="myOrderCount">促销价</th>
                            <th colwidth="105" type="link" title="[保存]|[删除]"  action="doDiaPartSave|doPartDelete">操作</th>
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
                                    <button type="button" name="btn-next">下一步</button>
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
            <!--促销配件Tab end-->
            <!--促销区域Tab begin-->
            <div>
                <form method="post" id="fm-searchPromArea" class="editForm">
                    <table class="searchContent" id="tab-searchPromArea"></table>
                </form>
                <div id="div-promAreaList" style="height:340px;overflow:hidden;">
                    <table style="display:none;width:100%;" id="tab-promAreaList" layoutH="350" name="tablist"
                           ref="div-promAreaList" refQuery="tab-searchPromArea">
                        <thead>
                        <tr>
                            <th type="single" name="XH" append="plus|openAreaSelWin"></th>
                            <th fieldname="AREA_CODE">办事处代码</th>
                            <th fieldname="AREA_NAME">办事处名称</th>
                            <th colwidth="85" type="link" title="[删除]" action="deletePromArea">操作</th>
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
            <!--促销区域Tab end-->
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
                        if (!$('#dia-PROM_ID').val()) {
                            alertMsg.warn('请先保存基本信息!');
                        } else {
                            if ($("#tab-promPartList").is(":hidden")) {
                                $("#tab-promPartList").show();
                                $("#tab-promPartList").jTable();
                            }
                            searchPromPart();
                        }
                        break;
                    case 2://第3个tab页
                        if (!$('#dia-PROM_ID').val()) {
                            alertMsg.warn('请先保存基本信息!')
                        } else {
                            if ($("#tab-promAreaList").is(":hidden")) {
                                $("#tab-promAreaList").show();
                                $("#tab-promAreaList").jTable();
                            }
                            searchPromArea();
                        }
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
    //保存基本信息按钮响应
    $('#btn-saveBaseInfo').bind('click', function () {
        //获取需要提交的form对象
        var $f = $("#fm-promInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        if (diaAction == 1)	//插入动作
        {
            var addUrl = diaSaveAction + "/insertPromotion.ajax";
            doNormalSubmit($f, addUrl, "btn-saveBaseInfo", sCondition, diaInsertCallBack);
        } else	//更新动作
        {
            var updateUrl = diaSaveAction + "/updatePromotion.ajax";
            doNormalSubmit($f, updateUrl, "btn-saveBaseInfo", sCondition, diaUpdateCallBack);
        }
    });

    //上传附件
    $("#btn-addAtt").bind("click", function () {
        var promId = $("#dia-PROM_ID").val();
        if(!promId){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        $.filestore.open(promId, {"folder": "true", "holdName": "false", "fileSizeLimit": 0, "fileTypeDesc": "All Files", "fileTypeExts": "*.*"});
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

    //配件模版导出按钮响应
    $('#btn-downTpl').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=cxpjdrmb.xls");
        window.location.href = url;
    });

    //配件明细导入按钮响应
    $('#btn-impPromPart').bind('click', function () {
        if(!$('#dia-PROM_ID').val()){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("PT_BU_PROMOTION_PART_TMP",$('#dia-PROM_ID').val(),3,3,"/jsp/dms/oem/part/sales/promotionMng/importSuccess.jsp");
    });

    if (diaAction != "1") {//修改
        //#tab-promotionList：父页面列表的表格id
        //getSelectedRows():获取列表选中行对象，返回选中行数组
        var selectedRows = $("#tab-promotionList").getSelectedRows();
        
        //setEditValue：设置输入框赋值方法
        //"fm-promInfo"：要赋值区域的id
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        setEditValue("fm-promInfo", selectedRows[0].attr("rowdata"));
        $("#diaOrderNo").html(selectedRows[0].attr("PROM_CODE"));
    } else {//新增
        setDiaDefaultValue();
    }
});
function toAppend(obj){
	var $tr = $(obj).parent();
	return $tr.attr("MIN_PACK")+"/"+ $tr.attr("MIN_UNIT_sv");
}
function doImportConfirm()
{
    var impUrl = diaSaveAction +'/importPromPart.ajax?PROM_ID='+$('#dia-PROM_ID').val();
    sendPost(impUrl,"","",impPromPartCallback);
}

function impPromPartCallback(res){
    try
    {
        searchPromPart();
    }catch(e)
    {
        alertMsg.error(e.description);
        return false;
    }
    return true;
}

function setDiaDefaultValue() {
    //有效标示
    $("#dia-IF_TRANS_FREE").val("<%=Constant.SF_02%>");
    $("#dia-IF_TRANS_FREE").attr("code", "<%=Constant.SF_02%>");
    $("#dia-IF_TRANS_FREE").find("option").val("<%=Constant.SF_02%>");
    $("#dia-IF_TRANS_FREE").find("option").text("否");
}

//新增回调函数
function diaInsertCallBack(res) {
    try {
        var rows = res.getElementsByTagName("ROW");
        if (rows && rows.length > 0) {
            //获取新增的促销活动ID 并设置到隐藏域中 以便后续选择配件
            var promId = getNodeText(rows[0].getElementsByTagName("PROM_ID").item(0));
            var promNo = getNodeText(rows[0].getElementsByTagName("PROM_CODE").item(0));
            $('#dia-PROM_ID').val(promId);
            $("#diaOrderNo").html(promNo);
        }
        if ($("#tab-promotionList").is(":visible") == false) {
            //不显示结果集的情况下，插入一行
            $("#tab-promotionList").insertResult(res, 0);
            $("#tab-promotionList").jTable();
        } else
        //显示结果集的情况下，插入一行
            $("#tab-promotionList").insertResult(res, 0);
        diaAction = "2";
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

//更新回调函数
function diaUpdateCallBack(res) {
    try {
    	if($("#tab-promotionList_content").size()>0){
			$("td input[type=radio]",$("#tab-promotionList_content").find("tr").eq(0)).attr("checked",true);			
		}else{
			$("td input[type=radio]",$("#tab-promotionList").find("tr").eq(0)).attr("checked",true);
		}
        var selectedIndex = $("#tab-promotionList").getSelectedIndex();
        $("#tab-promotionList").updateResult(res, selectedIndex);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
//弹出配件选择列表，支持复选
function openPartSelWin() {
    var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/promotionMng/promotionMngPartSel.jsp", "partSelWin", "配件信息查询", options);
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
var $row;

//删除方法，rowobj：行对象，非jquery类型
function deletePromPart(rowobj) {
    $row = $(rowobj);
    var deleteUrl = diaSaveAction + "/deletePromPart.ajax?relationId=" + $(rowobj).attr("RELATION_ID");
    sendPost(deleteUrl, "", "", deletePromPartCallBack, "true");
}
//删除回调方法
function deletePromPartCallBack(res) {
    try {
        if ($row)
            $("#tab-promPartList").removeResult($row);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

//删除方法，rowobj：行对象，非jquery类型
function deletePromArea(rowobj) {
    $row = $(rowobj);
    var deleteUrl = diaSaveAction + "/deletePromArea.ajax?relationId=" + $(rowobj).attr("RELATION_ID");
    sendPost(deleteUrl, "", "", deletePromAreaCallBack, "true");
}
//删除回调方法
function deletePromAreaCallBack(res) {
    try {
        if ($row)
            $("#tab-promAreaList").removeResult($row);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

//弹出办事处选择列表，支持复选
function openAreaSelWin() {
    var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/promotionMng/promotionMngAreaSel.jsp", "areaSelWin", "办事处信息查询", options);
}

function formatAmount(obj){
	return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
}

//促销活动促销价格生成INPUT方法
function myOrderCount(obj){
	return "<input type='text' style='width:50px;' name='myCount' value='"+$(obj).html()+"' onblur='doMyCountBlur(this)' maxlength='6'/>";
}
function doMyCountBlur(obj){
	var $obj = $(obj);
    if($obj.val() == "")
        return false;
    if($obj.val() && !isAmount($obj))
    {
        alertMsg.warn("请正确输入促销价格！");
        return false;
    }
}
//促销活动价格修改行编辑方法
var $rowpart;
function doDiaPartSave(rowobj){
	$rowpart = $(rowobj);
	var $obj = $(rowobj).find("td").eq(8).find("input:first");
	if($obj.val() && !isAmount($obj))
    {
        alertMsg.warn("请正确输入促销价格！");
        return false;
    }
    $("#PRO_ID").val($("#dia-PROM_ID").val());
    $("#DTL_ID").val($(rowobj).attr("RELATION_ID"));
    $("#N_PRICE").val($obj.val());
    
    var $f = $("#diaSaveFm");
    if (submitForm($f) == false) return false;
    var sCondition = {};
    sCondition = $f.combined(1) || {};
    sendPost(diaSaveAction + "/proPriceSave.ajax", "btnDiaSave", sCondition, savePriceCallBack);
}
function savePriceCallBack(res){
	try {
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
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
var $row;
function doPartDelete(rowobj)
{
	$row = $(rowobj);
	var url = diaSaveAction + "/deletePromPart.ajax?relationId="+$(rowobj).attr("RELATION_ID");
	sendPost(url, "", "", deletePriceCallBack, "true");
}
//删除配件回调方法
function  deletePriceCallBack(res)
{
	try
	{
		searchPromPart();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>