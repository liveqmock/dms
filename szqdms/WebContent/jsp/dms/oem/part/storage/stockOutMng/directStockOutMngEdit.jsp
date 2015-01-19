<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
<div class="tabs" eventType="click" id="dia-tabs" currentIndex="1">
<div class="tabsHeader">
    <div class="tabsHeaderContent">
        <ul>
            <li><a href="javascript:void(0);"><span>订单信息</span></a></li>
            <li><a href="javascript:void(0);"><span>出库清单</span></a></li>
        </ul>
    </div>
</div>
<div class="tabsContent">
<!--出库单信息 begin-->
<div style="height:auto;overflow:hidden;">
    <form method="post" id="fm-outBillInfo" class="editForm">
        <%--隐藏域查询条件--%>
        <input type="hidden" id="dia-ISSUE_ID" name="dia-ISSUE_ID" datasource="ISSUE_ID"/>
    <input type="hidden" id="dia-ORDER_ID" name="dia-ORDER_ID" datasource="ORDER_ID"/>
        <div align="left">
            <fieldset>
                <table class="editTable" id="tab-outBillInfo">
                    <tr>
                            <td><label>订单编号：</label></td>
                            <td><input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="ORDER_NO" datatype="1,is_null,30" readonly="true"/></td>
                            <td><label>订单类型：</label></td>
                            <td><input type="text" id="dia-ORDER_TYPE" name="dia-ORDER_TYPE" datasource="ORDER_TYPE" datatype="1,is_null,30" readonly="true"/></td>
                        </tr>
                        <tr>
                            <td><label>提报日期：</label></td>
                            <td><input type="text" id="dia-APPLY_DATE" name="dia-APPLY_DATE" datasource="APPLY_DATE" datatype="1,is_null,30" readonly="true"/></td>
                            <td><label>提报单位代码：</label></td>
                            <td><input type="text" id="dia-ORG_CODE" name="dia-ORG_CODE" datasource="ORG_CODE" datatype="1,is_null,30" readonly="true"/></td>
                        </tr>
                        <tr>
                            <td><label>提报单位名称：</label></td>
                            <td><input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" datatype="1,is_null,30" readonly="true"/></td>
                            <td><label>审核日期：</label></td>
                            <td><input type="text" id="dia-CHECK_DATE" name="dia-CHECK_DATE" datasource="CHECK_DATE" datatype="1,is_null,30" readonly="true"/></td>
                        </tr>
                        <tr>
                            <td><label>审核员：</label></td>
                            <td><input type="text" id="sel-CHECK_USER_SV" name="sel-CHECK_USER_SV" datasource="CHECK_USER" datatype="1,is_null,30" readonly="true"/></td>
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
<!--出库单信息 end-->
<!--配件出库清单 begin-->
<div>
    <form method="post" id="fm-searchOutBillPart" class="editForm">
        <div class="searchBar" align="left">
            <table class="searchContent" id="tab-searchOutBillPart">
            </table>
        </div>
    </form>
    <div id="div-outBillPartList" style="">
        <table style="display:none;width:100%;" id="tab-outBillPartList"  name="tablist" limitH="false" layoutH="500px"  multivals="div-selectedPart" ref="div-outBillPartList" refQuery="tab-searchOutBillPart">
            <thead>
            <tr>
                <th type="multi" name="XH" unique="ISSUE_DTL_ID" style="display:none;"></th>
                <th fieldname="PART_CODE">配件代码</th>
                <th fieldname="PART_NAME">配件名称</th>
                <th fieldname="UNIT">单位</th>
                <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                <th fieldname="POSITION_CODE">库位</th>
                <th fieldname="SHOULD_COUNT" colwidth="80">应发数量</th>
                <th fieldname="" colwidth="80" refer="createInputBox3">出库数量</th>
                <th fieldname="REMARKS" colwidth="120" refer="createInputBox4">备注</th>
                <th fieldname="SUPPLIER_NAME" refer="ctrlShow">供应商</th>
                <th fieldname="OUT_AMOUNT" style="display:none">出库单数量</th>
            </tr>
            </thead>
        </table>
    </div>
    <form id="fm-partInfo">
        <input type="hidden" id="flag" name="flag" datasource="FLAG"/>
        <input type="hidden" id="issueId" name="issueId" datasource="ISSUE_ID"/>
        <input type="hidden" id="orderId" name="orderId" datasource="ORDER_ID"/>
        <input type="hidden" id="detailIds" name="detailIds" datasource="DETAILIDS"/>
        <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
        <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
        <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
        <input type="hidden" id="outAmounts" name="outAmounts" datasource="OUTAMOUNTS"/>
        <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
        <input type="hidden" id="userAcc" name="userAcc" datasource="USERACC"/>
    </form>
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
                        <button type="button" id="btn-stockOut">出&nbsp;&nbsp;库</button>
                    </div>
                </div>
            </li>
            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockOutEnd">出库完成</button></div></div></li>
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
<!--配件出库清单 end-->
</div>
<div class="tabsFooter">
    <div class="tabsFooterContent"></div>
</div>
</div>
</div>

<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DirectStockOutMngAction";
var diaAction = "<%=action%>";
var flag = true;
$(function () {
    var iH = document.documentElement.clientHeight;
    $(".tabsContent").height(iH - 70);
    $("#tab-outBillPartList").height(iH - 140);
    $("button[name='btn-pre']").bind("click",function(event){
        $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
    });
    $("button[name='btn-next']").bind("click",function(event){
        var $tabs = $("#dia-tabs");
        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
        //跳转后实现方法
        switch(parseInt($tabs.attr("currentIndex")))
        {
            case 1:
                  if ($("#tab-outBillPartList").is(":hidden")) {
                      $("#tab-outBillPartList").show();
                  }
                  searchOutBillPart();
                break;
            default:
                break;
        }
    });

    if (diaAction != "1") {//修改
        var selectedRows = $("#tab-outBillList").getSelectedRows();
        setEditValue("fm-outBillInfo", selectedRows[0].attr("rowdata"));

        $('#dia-CHECK_USER_SV').val(selectedRows[0].attr("CHECK_USER_SV"));

        $("#tab-outBillPartList").show();
       searchOutBillPart();

    } else {//新增
        setDiaDefaultValue();
    }

    //出库按钮响应
    $('#btn-stockOut').bind('click',function(){
        // 按钮标识
        $("#flag").val("false");
        // 出库方法
        stockOut();
    });

    //出库完成按钮响应
    $('#btn-stockOutEnd').bind('click',function(){
        // 按钮标识
        $("#flag").val("true");
        // 出库方法
        stockOut();
    });

    //查询出库单备件按钮响应
    $('#btn-searchOutBillPart').bind('click',function(){
        searchOutBillPart();
    });
})


function stockOut() {
    var detailIds="";
    var partIds="";
    var partCodes="";
    var partNames="";
    var outAmounts="";
    var remarks="";
    $("tr",$("#tab-outBillPartList")).each(function(){
        var $tr = $(this);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        if($checkbox.is(":checked"))
        {
            detailIds += detailIds.length?","+$checkbox.val():$checkbox.val();
            partIds += partIds.length?"," + $tr.attr("PART_ID"):$tr.attr("PART_ID");
            partCodes += partCodes.length?"," + $tr.attr("PART_CODE"):$tr.attr("PART_CODE");
            partNames += partNames.length?"," + $tr.attr("PART_NAME"):$tr.attr("PART_NAME");
            
            var outAmountsTmp = $tr.find("input[name='OUT_COUNT']");
            if(outAmountsTmp.val()){
                outAmounts += outAmounts.length?"," + outAmountsTmp.val():outAmountsTmp.val();
            }else{
                outAmounts += outAmounts.length?"," + "myNull":"myNull";
            }
            var remarksTmp = $tr.find("input[name='REMARK']");
            if(remarksTmp.val()){
                remarks += remarks.length?"," + remarksTmp.val():remarksTmp.val();
            }else{
                remarks += remarks.length?"," + "myNull":"myNull";
            }
        }
    
    });
    if(detailIds.length==0){
        alertMsg.warn("请输入出库数量.");
        return false;
    }
    //将拼接串放入隐藏form中
    $('#issueId').val($("#dia-ISSUE_ID").val());
    $('#orderId').val($("#dia-ORDER_ID").val());
    $('#detailIds').val(detailIds);
    $('#partIds').val(partIds);
    $('#partCodes').val(partCodes);
    $('#partNames').val(partNames);
    $('#outAmounts').val(outAmounts);
    $('#remarks').val(remarks);
    $('#userAcc').val($('#PERSON_NAME').attr('code'));
    var partStockOutUrl = diaSaveAction+'/partStockOutInEdit.ajax';
    //获取需要提交的form对象
    var $f = $("#fm-partInfo");
    //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
    if (submitForm($f) == false) return false;
    var sCondition = {};
    //将需要提交的内容拼接成json
    sCondition = $f.combined(1) || {};
    
    if($("#flag").val()=='true'){
    	$("#btn-stockOutEnd").attr("style","display:none");
    	doNormalSubmit($f, partStockOutUrl, "btn-stockOutEnd", sCondition, partStockOutCallBack);
    }else{
    	doNormalSubmit($f, partStockOutUrl, "btn-stockOut", sCondition, partStockOutCallBack);
    }
}
function partStockOutCallBack(){
	$.pdialog.closeCurrent();
	doOutSearch();
/*     if ('true'==$("#flag").val()) {
        var selectedRows = $("#tab-outBillList").getSelectedRows();
        $("#tab-outBillList").removeResult(selectedRows[0]);
    } */
}

function setDiaDefaultValue() {

}

//查询出库单备件
function searchOutBillPart() {
    var searchOutBillPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DirectStockOutMngAction/searchOutBillPart.ajax?ISSUE_ID=" + $('#dia-ISSUE_ID').val()+"&account="+$('#PERSON_NAME').attr('code');
    var $f = $("#fm-searchOutBillPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchOutBillPartUrl, "", 1, sCondition, "tab-outBillPartList");
}

function callbackSearch(responseText,tabId,sParam)
{
    switch(tabId)
    {
        case "tab-outBillPartList":
            setStyle('tab-outBillPartList_content');
            break;
    }
}


///将实际出库数量字段渲染为文本框
function createInputBox3(obj)
{
	var $obj = $(obj);
    var $tr = $(obj).parent();
    var default_no = $tr.find("td").eq(7).text();
    var outAmount = $tr.find("td").eq(11).text();
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    $checkbox.attr("checked",true);
    if(outAmount){
        return '<input type="text" maxlength="6" name="OUT_COUNT" value="'+outAmount+'"onblur="doMyInputCount(this)" />';
    }else{
        return '<input type="text" maxlength="6" name="OUT_COUNT" value="'+default_no+'" onblur="doMyInputCount(this)"/>';
    }
    
}

//将备注字段渲染为文本框
function createInputBox4(obj)
{
    return '<input type="text" maxlength="1000" name="REMARK" value="'+obj.text()+'"/>';
}

function isNumeric(val)
{
    var reg = /^[0-9]\d*$/
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}
function doMyInputCount(obj){
    var $obj = $(obj);
    var $tr = $obj;
    while($tr.get(0).tagName != "TR")
        $tr = $tr.parent();
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var o = $obj.val();
    var s = $tr.find("td").eq(7).text();
    if(parseInt(o)-parseInt(s)>0){
        alertMsg.warn('出库数量不能大于实发数量!');
        $obj.val(s);
        return false;
    }
    if($obj.val() > 0)
        $checkbox.attr("checked",true);
    doSelectedBefore2($tr,checkObj,1);
}
function doSelectedBefore2($tr,$obj,type)
{
    doCheckbox($obj.get(0));
}
function doCheckbox(checkbox) {
    var $tr = $(checkbox).parent().parent();
    

    var outAmount = "";
    if($tr.find("td").eq(8).find("input:first").size()>0)
        outAmount = $tr.find("td").eq(8).find("input:first").val();
    else
        outAmount = $tr.find("td").eq(8).text();

    var remark = "";
    if($tr.find("td").eq(9).find("input:first").size()>0)
        remark = $tr.find("td").eq(9).find("input:first").val();
    else
        remark = $tr.find("td").eq(11).text();

    var arr = [];
    arr.push($tr.attr("DETAIL_ID"));
    arr.push($tr.attr("PART_ID"));
    arr.push($tr.attr("PART_CODE"));
    arr.push($tr.attr("PART_NAME"));
    arr.push(outAmount);
    if(!remark){
        arr.push('myNull');
    }else{
        arr.push(remark);
    }
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    multiSelected($checkbox, arr,$('#div-selectedPart'));
}

function isNumeric(val)
{
    var reg = /^[1-9]\d*$/
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}
function toAppendStr(obj){
    var $tr =$(obj).parent();
    return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
}

function ctrlShow(obj){
    var $tr =$(obj).parent();
    if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
        return "";
    }else{
        return $(obj).text();
    }
}
</script>