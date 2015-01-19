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
    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>出库单信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>配件出库清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--出库单信息 begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="fm-outBillInfo" class="editForm">
                    <input type="hidden" id="dia-ISSUE_ID" name="dia-ISSUE_ID" datasource="ISSUE_ID"/>
                    <%--隐藏域查询条件--%>
                    <input type="hidden" id="dia-OUT_ID" name="dia-OUT_ID" datasource="OUT_ID"/>
                    <input type="hidden" id="dia-OUT_STATUS" name="dia-OUT_STATUS" datasource="OUT_STATUS" value="<%=DicConstant.CKDZT_01%>"/>
                    <input type="hidden" id="dia-OUT_TYPE" name="dia-OUT_TYPE" datasource="OUT_TYPE" value="<%=DicConstant.CKLX_06%>"/>
                    <input type="hidden" id="dia-PRINT_STATUS" name="dia-PRINT_STATUS" datasource="PRINT_STATUS" value="<%=DicConstant.DYZT_01%>"/>
                    <input type="hidden" id="dia-IF_IN_FLAG" name="dia-IF_IN_FLAG" datasource="IF_IN_FLAG" value="<%=DicConstant.SF_02%>"/>
                    <div align="left">
                        <fieldset>
                            <legend align="right"><a onclick="onTitleClick('tab-outBillInfo')">&nbsp;出库单信息编辑&gt;&gt;</a>
                            </legend>
                            <table class="editTable" id="tab-outBillInfo">
                                <tr>
                                    <td><label>订单编号：</label></td>
                                    <td>
                                        <input type="hidden" id="dia-ORDER_ID" name="dia-ORDER_ID" datasource="ORDER_ID"/>
                                        <input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="ORDER_NO"  hasBtn="true" readonly datatype="0,is_null,30" callFunction="openSaleSelWin()"/>
                                    </td>
                                    <td><label>出库仓库：</label></td>
                                    <td>
                                        <input type="hidden" id="dia-WAREHOUSE_ID" name="dia-WAREHOUSE_ID" datasource="WAREHOUSE_ID"/>
                                        <input type="hidden" id="dia-WAREHOUSE_NAME" name="dia-WAREHOUSE_NAME" datasource="WAREHOUSE_NAME"/>
                                        <input type="text" id="dia-WAREHOUSE_CODE" name="dia-WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE = 100101 AND STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> ORDER BY WAREHOUSE_CODE" datatype="0,is_null,30"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label>提报单位：</label></td>
                                    <td>
                                        <input type="text" id="dia-ORG_NAME" name="dia-ORG_NAME" datasource="ORG_NAME" readonly="readonly"/>
                                    </td>
                                    <td><label>审核员：</label></td>
                                    <td>
                                        <input type="text" id="dia-CHECK_USER_SV" name="dia-CHECK_USER_SV" datasource="CHECK_USER_SV" readonly="readonly"/>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
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
                    <table style="display:none;width:100%;" id="tab-outBillPartList" name="tablist" layoutH="500px" multivals="div-selectedPart" ref="div-outBillPartList" refQuery="tab-searchOutBillPart">
                        <thead>
                        <tr>
                            <th type="multi" name="XH" unique="ISSUE_DTL_ID" style="display:none"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="UNIT">单位</th>
                            <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                            <th fieldname="POSITION_CODE">库位</th>
                            <th fieldname="SHOULD_COUNT" colwidth="80">应发数量</th>
                            <th fieldname="" colwidth="80" refer="createInputBox3">出库数量</th>
                            <th fieldname="REMARKS" colwidth="150" refer="createInputBox4">备注</th>
                            <th fieldname="SUPPLIER_NAME" refer="ctrlShow">供应商</th>
                            <th fieldname="OUT_AMOUNT" style="display:none">出库单数量</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <form id="fm-partInfo" style="display:none;">
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
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockOut">保&nbsp;&nbsp;存</button></div></div></li>
                        <li id="btn-stockOutEnd-btn"><div class="button"><div class="buttonContent"><button type="button" confirm="确认是否已全部出库完成？&nbsp;出库单将关闭。" id="btn-stockOutEnd">出库单关闭</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
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

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/GuaranteeStockOutMngAction";
var diaAction = "<%=action%>";
var flag = true;
$(function () {
    var iH = document.documentElement.clientHeight;
    $(".tabsContent").height(iH - 70);
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
                    searchOutBillPart();
                }
                break;
            default:
                break;
        }
    });

    if (diaAction != "1") {//修改
        var selectedRows = $("#tab-outBillList").getSelectedRows();
        setEditValue("fm-outBillInfo", selectedRows[0].attr("rowdata"));

        $('#dia-CHECK_USER_SV').val(selectedRows[0].attr("CHECK_USER_SV"));
        $('#dia-ORDER_NO').attr('datatype','1,is_null,30');
        $('#dia-ORDER_NO').attr('hasBtn','false');
        $('#dia-WAREHOUSE_CODE').attr('readOnly','true');
        $('#dia-WAREHOUSE_CODE').attr('src','');
        $('#dia-WAREHOUSE_CODE').attr('datatype','1,is_null,30');
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

    // 出库方法
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
            if($checkbox.is(":checked")) {
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
//         doNormalSubmit($f, partStockOutUrl, "btn-stockOut", sCondition, partStockOutCallBack);
    }
    // 出库按钮回调函数
    function partStockOutCallBack(){
//     	$.pdialog.closeCurrent();
//         if ('true'==$("#flag").val()) {
//             var selectedRows = $("#tab-outBillList").getSelectedRows();
//             $("#tab-outBillList").removeResult(selectedRows[0]);
//         }
        
    	if($("#flag").val()=='true'){
    	    var editWin = $("body").data("editWin");
    	    $.pdialog.close(editWin);
    	    var $f = $("#fm-searchOutBill");
    	    var sCondition = {};
    	    sCondition = $f.combined() || {};
    	    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
    	    $("#btn-stockOutEnd-btn").attr("style","display:none");
    	}else{
    		var $f = $("#fm-searchOutBill");
    	    var sCondition = {};
    	    sCondition = $f.combined() || {};
    	    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
    	}
       return true;
    }

    //弹出销售订单选择列表
    function openSaleSelWin(){
        if(!$('#dia-WAREHOUSE_ID').val()){
            alertMsg.warn('请选择出库仓库!');
            return;
        }
        var options = {max: false, width: 900, height: 450, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/guaranteeStockOutMngSaleSel.jsp", "saleSelWin", "销售订单查询", options);
    }

    //查询出库单备件
    function searchOutBillPart() {
        var searchOutBillPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/GuaranteeStockOutMngAction/searchOutBillPart.ajax?ISSUE_ID=" + $('#dia-ISSUE_ID').val()+"&account="+$('#PERSON_NAME').attr('code');
        var $f = $("#fm-searchOutBillPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchOutBillPartUrl, "", 1, sCondition, "tab-outBillPartList");
    }
    
    function callbackSearch(responseText,tabId,sParam) {
        switch(tabId) {
            case "tab-outBillPartList":
                 setStyle('tab-outBillPartList_content');
            break;
        }
    }
    
    //表选字典回调方法
    function afterDicItemClick(id, $row, selIndex){
        //$row 行对象
        //selIndex 字典中第几行
        if(id == 'dia-WAREHOUSE_CODE'){
            $('#dia-WAREHOUSE_ID').val($row.attr('WAREHOUSE_ID'));
            $('#dia-WAREHOUSE_NAME').val($row.attr('WAREHOUSE_NAME'));
        }
        return true;
    }
    
    //将实际出库数量字段渲染为文本框
    function createInputBox3(obj) {
    	var $obj = $(obj);
        var $tr = $(obj).parent();
        var default_no = $tr.find("td").eq(7).text();
        var outAmount = $tr.find("td").eq(11).text();
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        var checkObj = $("input:first",$tr.find("td").eq(1));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        $checkbox.attr("checked",true);
        if(outAmount){
            return '<input type="text" maxlength="6" name="OUT_COUNT" style="width:30px" value="'+outAmount+'"onblur="doMyInputCount(this)" />';
        }else{
            return '<input type="text" maxlength="6" name="OUT_COUNT" style="width:30px" value="'+default_no+'" onblur="doMyInputCount(this)"/>';
        }
        
    }
    
    // 将备注字段渲染为文本框
    function createInputBox4(obj) {
        return '<input type="text" maxlength="1000" name="REMARK" value="'+obj.text()+'"/>';
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
            remark = $tr.find("td").eq(9).text();
    
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
    
    
    //将实际出库数量字段渲染为文本框
    function createInputBox1(obj) {
        return '<input type="text" name="OUT_AMOUNT" maxlength="6" value="'+obj.text()+'"/>';
    }
    
    //将备注字段渲染为文本框
    function createInputBox2(obj) {
        return '<input type="text" maxlength="1000" name="REMARK" value="'+obj.text()+'"/>';
    }
    
    function isNumeric(val) {
        var reg = /^[0-9]\d*$/;
        if(reg.test(val)) {
            return true;
        } else {
            return false;
        }
    }
    
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
    
        var outAmount = "";
        if($tr.find("td").eq(8).find("input:first").size()>0)
            outAmount = $tr.find("td").eq(8).find("input:first").val();
        else
            outAmount = $tr.find("td").eq(8).text();
    
        var remark = "";
        if($tr.find("td").eq(9).find("input:first").size()>0)
            remark = $tr.find("td").eq(9).find("input:first").val();
        else
            remark = $tr.find("td").eq(9).text();
        if (!outAmount || !isNumeric(outAmount)){
            alertMsg.warn("请输入正确的实际出库数量!");
            $(checkbox).attr("checked",false);
            return false;
        }else{
            var shouldCount = $tr.attr('SHOULD_COUNT');//已发数量
            if(parseInt(outAmount)>parseInt(shouldCount)){
                alertMsg.warn("实际出库数量不能大于应发数量！");
                $(checkbox).attr("checked",false);
                return false;
            }else if(parseInt(outAmount)<parseInt(shouldCount)){
                if(!remark){
                    alertMsg.warn("实际出库数量小于应发数量时应输入备注!");
                    $(checkbox).attr("checked",false);
                    return false;
                }
            }
        }
    
        var arr = [];
        arr.push($tr.attr("ISSUE_DTL_ID"));
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push($tr.attr("POSITION_ID"));
        arr.push(outAmount);
        if(!remark){
            arr.push('myNull');
        }else{
            arr.push(remark);
        }
        arr.push($tr.attr("ISSUE_ID"));
        arr.push($tr.attr("ISSUE_NO"));
        arr.push($tr.attr("USER_ACCOUNT"));
        arr.push($tr.attr("SHOULD_COUNT"));
        arr.push($tr.attr("SUPPLIER_ID"));
        arr.push($tr.attr("SUPPLIER_CODE"));
        arr.push($tr.attr("SUPPLIER_NAME"));
    
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        //设置input框显示或文本只读
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(8).html("<div>"+outAmount+"</div>");
            $tr.find("td").eq(9).html("<div>"+remark+"</div>");
        } else {
            $tr.find("td").eq(8).html("<div><input type='text' name='OUT_AMOUNT'  maxlength='6' value='"+outAmount+"'/></div>");
            $tr.find("td").eq(9).html("<div><input type='text' name='REMARK'  maxlength='1000' value='"+remark+"'/></div>");
        }
    }
    
//     function customOtherValue($row,checkVal) {
//         var $inputObj1 = $row.find("td").eq(10);//实际出库数量
//         var val1;
//         if($("#val5") && $("#val5").val()) {
//             var t = $("#val5").val();
//             var pks = $("#val0").val();
//             var ss = t.split(",");
//             var pk = pks.split(",");
//             for(var i=0;i<pk.length;i++) {
//                 if(pk[i] == checkVal) {
//                     val1 = ss[i];
//                     break;
//                 }
//             }
//         }
//         if(val1)
//         {
//             $inputObj1.html("<div>"+val1+"</div>");
//         }
    
//         var $inputObj2 = $row.find("td").eq(11);//备注
//         var val2;
//         if($("#val6") && $("#val6").val())
//         {
//             var t = $("#val6").val();
//             var pks = $("#val0").val();
//             var ss = t.split(",");
//             var pk = pks.split(",");
//             for(var i=0;i<pk.length;i++)
//             {
//                 if(pk[i] == checkVal)
//                 {
//                     val2 = ss[i];
//                     break;
//                 }
//             }
//         }
//         if(val2)
//         {
//             if(val2=='myNull'){
//                 val2 = '';
//             }
//             $inputObj2.html("<div>"+val2+"</div>");
//         }
//     }

    // 最小包装数
    function toAppendStr(obj){
        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }
</script>