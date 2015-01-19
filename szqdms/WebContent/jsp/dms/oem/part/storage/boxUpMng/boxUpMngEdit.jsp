<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<div id="dia-layout">
    <form method="post" id="fm-saleOrderInfo" class="editForm">
        <%--隐藏域查询条件--%>
        <input type="hidden" id="dia-ORDER_ID" name="dia-ORDER_ID" datasource="ORDER_ID"/>
        <div align="left">
            <fieldset>
                <legend align="right"><a onclick="onTitleClick('tab-saleOrderInfo')">&nbsp;订单基本信息&gt;&gt;</a>
                </legend>
                <table class="editTable" id="tab-saleOrderInfo">
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="dia-ORDER_NO" name="dia-ORDER_NO" datasource="ORDER_NO" readonly datatype="1,is_null,30"/></td>
                        <td><label>订单类型：</label></td>
                        <td><input type="text" id="dia-ORDER_TYPE" name="dia-ORDER_TYPE" datasource="ORDER_TYPE" readonly datatype="1,is_null,30"/></td>
                        <td><label>提报日期：</label></td>
                        <td><input type="text" id="dia-APPLY_DATE" name="dia-APPLY_DATE" datasource="APPLY_DATE" readonly datatype="1,is_null,30"/></td>
                    </tr>
                </table>
            </fieldset>
        </div>
    </form>
    <div>
        <form method="post" id="fm-searchSaleOrderPart" class="editForm">
            <table class="searchContent" id="tab-searchSaleOrderPart"></table>
        </form>
        <div id="div-saleOrderPartList" style="">
            <table style="display:none;width:100%;" id="tab-saleOrderPartList" limitH="false" multivals="div-selectedPart" name="tablist" ref="div-saleOrderPartList" refQuery="tab-searchSaleOrderPart">
                <thead>
                <tr>
                    <th type="multi" name="XH" unique="PART_ID" style="display: none"></th>
                    <th fieldname="ISSUE_NO">发料单号</th>
                    <th fieldname="PART_CODE">配件代码</th>
                    <th fieldname="PART_NAME" colwidth="135">配件名称</th>
                    <th fieldname="UNIT">单位</th>
                    <th fieldname="IF_SUPPLIER" colwidth="100" style="display: none">是否指定供应商</th>
                    <th fieldname="SUPPLIER_NAME" colwidth="80">供应商</th>
                    <th fieldname="SHOULD_COUNT" colwidth="70">应发数量</th>
                    <th fieldname="REAL_COUNT" colwidth="70">实发数量</th>
                    <th fieldname="BOX_NO" refer="createInputBox1" colwidth="50">箱号</th>
                    <th fieldname="COUNT" refer="createInputBox2" colwidth="60">数量</th>
                    <th fieldname="REMARKS" style="display: none" refer="createInputBox3">备注</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export">导出清单</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-import">导入装箱记录</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" id="btn-complete">装箱完成</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
            </ul>
        </div>
        <fieldset id="fie-selectedPart" style="">
            <div id="div-selectedPart">
                <table style="width:100%;">
                    <tr>
                        <td>
                            <textarea style="width:70%;height:26px;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val1" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val2" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val3" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val4" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val5" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val6" name="multivals" readOnly></textarea>
                            <textarea style="width:70%;height:26px;display:none" id="val7" name="multivals" readOnly></textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <form id="fm-partInfo">
            <input type="hidden" id="orderId" name="orderId" datasource="ORDER_ID"/>
            <input type="hidden" id="orderNo" name="orderNo" datasource="ORDER_NO"/>
            <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
            <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
            <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
            <input type="hidden" id="boxNos" name="boxNos" datasource="BOXNOS"/>
            <input type="hidden" id="counts" name="counts" datasource="COUNTS"/>
            <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
            <input type="hidden" id="issueIds" name="issueIds" datasource="ISSUEIDS"/>
            <input type="hidden" id="issueNos" name="issueNos" datasource="ISSUENOS"/>
        </form>
    </div>
    <form id="exportFm" method="post" style="display:none">
        <input type="hidden" id="params" name="data">
    </form>
</div>

<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/boxUpMng/BoxUpMngAction";
$(function(){
	
	var iH = document.documentElement.clientHeight;
/*     $(".tabsContent").height(iH - 70); */
    //$("#div-saleOrderPartList").height(iH-130);
	
/* 	$("#div-saleOrderPartList").attr("layoutH",document.documentElement.clientHeight-100); */
    $("#div-saleOrderPartList").height(document.documentElement.clientHeight-180);
 

    var selectedRows = $("#tab-saleOrderList").getSelectedRows();
    setEditValue("fm-saleOrderInfo", selectedRows[0].attr("rowdata"));
/*     $("#tab-saleOrderPartList").show(); */
    //$("#tab-saleOrderPartList").jTable();

    var searchSaleOrderPartUrl = diaSaveAction+"/searchSaleOrderPart.ajax?ORDER_ID="+$('#dia-ORDER_ID').val();
    var $f = $("#fm-searchSaleOrderPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchSaleOrderPartUrl, "", 1, sCondition, "tab-saleOrderPartList");

    // 保存按钮绑定事件
    $('#btn-save').bind('click',function(){
/*         var flag = false;
        $("#tab-saleOrderPartList").find("tr").each(function(){
            var $checkbox = $("#tab-saleOrderPartList").find("tr").find("td").eq(1).find("input[type='checkbox']:first");
            if($checkbox.is(":checked")){
            } else {
                flag = true;
            }
        });
        if (flag==false) {
            alertMsg.warn("请选中多选框后保存!");
            return false;
        } */
        /* $('#orderId').val($('#dia-ORDER_ID').val());
        $('#orderNo').val($('#dia-ORDER_NO').val());
        $('#partIds').val($('#val0').val());
        $('#partCodes').val($('#val1').val());
        $('#partNames').val($('#val2').val());
        $('#boxNos').val($('#val3').val());
        $('#counts').val($('#val4').val());
        $('#remarks').val($('#val5').val());
        $('#issueIds').val($('#val6').val());
        $('#issueNos').val($('#val7').val());

        var saveDtlUrl = diaSaveAction+'/saveBoxUp.ajax';
        //获取需要提交的form对象
        var $f = $("#fm-partInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, saveDtlUrl, "btn-save", sCondition, saveBoxUpCallBack); */
        
        
        
    	var partIds="";
    	var partCodes="";
    	var partNames="";
    	var boxNos="";
    	var counts = "";
    	var issueIds = "";
    	var issueNos = "";
    	var remarks="";
    	$("tr",$("#tab-saleOrderPartList")).each(function(){
        	var $tr = $(this);
        	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        	if($checkbox.is(":checked"))
        	{
        		partIds += partIds.length?"@" + $tr.attr("PART_ID"):$tr.attr("PART_ID");
        		partCodes += partCodes.length?"@" + $tr.attr("PART_CODE"):$tr.attr("PART_CODE");
        		partNames += partNames.length?"@" + $tr.attr("PART_NAME"):$tr.attr("PART_NAME");
        		issueIds += issueIds.length?"@" + $tr.attr("ISSUE_ID"):$tr.attr("ISSUE_ID");
        		issueNos += issueNos.length?"@" + $tr.attr("ISSUE_NO"):$tr.attr("ISSUE_NO");
        		var boxUpNoTmp = $tr.find("input[name='B_NO']");
        		if(boxUpNoTmp.val()){
        			boxNos += boxNos.length?"@" + boxUpNoTmp.val():boxUpNoTmp.val();
        		}else{
        			boxNos += boxNos.length?"@" + "myNull":"myNull";
        		}
        		
        		var countTmp = $tr.find("input[name='B_COUNT']");
        		if(countTmp.val()){
        			counts += counts.length?"@" + countTmp.val():countTmp.val();
        		}else{
        			alertMsg.warn("请正确输入的数量.");
       				return false;
        		}
        		var remarksTmp = $tr.find("input[name='RMK']");
        		if(remarksTmp.val()){
        			remarks += remarks.length?"@" + remarksTmp.val():remarksTmp.val();
        		}else{
        			remarks += remarks.length?"@" + "myNull":"myNull";
        		}
        		
        	}
        
        });
    	var c_l = counts.split("@");
        var b_l = boxNos.split("@");
        for(var i = 0;i<c_l.length;i++){
        	var c01 = c_l[i].split(",");
        	var b01 = b_l[i].split(",");
        	if(c01.length!=b01.length){
        		alertMsg.warn("输入箱号数量与包装数量不相符!");
                return false;
        	}
/*         	var reg = /^\+?[0-9][0-9]*$/;
        	if(!c01|| !reg.test(c01)){
        		alertMsg.warn("请输入正确的数字!");
                return false;
        	} */
        	
        }
    	//将拼接串放入隐藏form中
    	$('#orderId').val($('#dia-ORDER_ID').val());
        $('#orderNo').val($('#dia-ORDER_NO').val());
        $('#partIds').val(partIds);
        $('#partCodes').val(partCodes);
        $('#partNames').val(partNames);
        $('#boxNos').val(boxNos);
        $('#counts').val(counts);
        $('#remarks').val(remarks);
        $('#issueIds').val(issueIds);
        $('#issueNos').val(issueNos);
        var saveDtlUrl = diaSaveAction+'/saveBoxUp.ajax';
        //获取需要提交的form对象
        var $f = $("#fm-partInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, saveDtlUrl, "btn-save", sCondition, saveBoxUpCallBack);
        
    });

    $('#btn-complete').bind('click',function(){
        $('#orderId').val($('#dia-ORDER_ID').val());
        $('#orderNo').val($('#dia-ORDER_NO').val());
        $('#partIds').val($('#val0').val());
        $('#partCodes').val($('#val1').val());
        $('#partNames').val($('#val2').val());
        $('#boxNos').val($('#val3').val());
        $('#counts').val($('#val4').val());
        $('#remarks').val($('#val5').val());
        $('#issueIds').val($('#val6').val());
        $('#issueNos').val($('#val7').val());

        var saveDtlUrl = diaSaveAction+'/completeBoxUp.ajax';
        //获取需要提交的form对象
        var $f = $("#fm-partInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, saveDtlUrl, "btn-complete", sCondition, completeBoxUpCallBack);
    });

    //导出装箱记录
    $('#btn-export').bind('click',function(){
        $("#exportFm").attr("action",diaSaveAction+"/exportBoxUp.ajax?ORDER_ID="+$('#dia-ORDER_ID').val());
        $("#exportFm").submit();
    });

   //导入装箱记录
    $('#btn-import').bind('click',function(){
        //13:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("PT_BU_BOX_UP_TMP",$('#dia-ORDER_ID').val(),11,3,"/jsp/dms/oem/part/storage/boxUpMng/importSuccess.jsp");
    });

})

//查询销售订单配件
function searchSaleOrderPart(){
    var searchSaleOrderPartUrl = diaSaveAction+"/searchSaleOrderPart.ajax?ORDER_ID="+$('#dia-ORDER_ID').val();
    var $f = $("#fm-searchSaleOrderPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchSaleOrderPartUrl, "", 1, sCondition, "tab-saleOrderPartList");
}

function saveBoxUpCallBack(){
    $('#btn-save').attr('disabled',false);
    searchSaleOrderPart();
    $('#val0').val('');
    $('#val1').val('');
    $('#val2').val('');
    $('#val3').val('');
    $('#val4').val('');
    $('#val5').val('');
    $('#val6').val('');
    $('#val7').val('');
}

function completeBoxUpCallBack(){
    var editWin = $("body").data("editWin");
    $.pdialog.close(editWin);
    var selectedRows = $("#tab-saleOrderList").getSelectedRows();
    $("#tab-saleOrderList").removeResult(selectedRows[0]);
}

//将箱号字段渲染为文本框
function createInputBox1(obj)
{
	var $obj = $(obj);
	var $tr = $(obj).parent();
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	var no = $tr.attr("BOX_NO");
	if(no){
		$checkbox.attr("checked",true);
	}else{
		$checkbox.attr("checked",false);
	}
    return '<input type="text" name="B_NO" style="width:50px" maxlength="1000" value="'+obj.text()+'" onblur="doNoBlur(this)"/>';
}

//将数量字段渲染为文本框
function createInputBox2(obj)
{
	var $obj = $(obj);
	var $tr = $(obj).parent();
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
/* 	$checkbox.attr("checked",true); */
	var default_no = $tr.find("td").eq("9").text();
	if(obj.text()){
		return '<input type="text" maxlength="1000" name="B_COUNT" style="width:60px"  value="'+obj.text()+'" onblur="doMyInputCount(this)"/>';
	}else{
		return '<input type="text" maxlength="1000" name="B_COUNT" style="width:60px" value="'+default_no+'" onblur="doMyInputCount(this)"/>';
	}
    
}

//将备注字段渲染为文本框
function createInputBox3(obj)
{
    return '<input type="text" maxlength="1000" name="RMK" value="'+obj.text()+'" onblur="doMyInputRemark(this)"/>';
}

function doNoBlur(obj)
{
	var $obj = $(obj);
	var $tr = $obj;
	while($tr.get(0).tagName != "TR")
		$tr = $tr.parent();
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	/*$checkbox.attr("checked",true); */
	 /* if($obj.val().length == 0)
	{
			
			$checkbox.attr("checked",false);
			return false;
	}else if(!isComma($obj.val())){
			
			$checkbox.attr("checked",false);
	}else{
		$checkbox.attr("checked",true);
		return true;
	}  */
	if($obj.val()){
		if(!isComma($obj.val())){
			alertMsg.warn("请正确输入的箱号.不能以,结尾");
			$obj.val(s);
		}
	}
	
	
	var checkObj = $("input:first",$tr.find("td").eq(1));
	
	doSelectedBefore($tr,checkObj,1);
}
function doMyInputCount(obj){
	var $obj = $(obj);
	var $tr = $obj;
	while($tr.get(0).tagName != "TR")
		$tr = $tr.parent();
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	var checkObj = $("input:first",$tr.find("td").eq(1));
	var s = $tr.find("td").eq(9).text();
	 if($obj.val()){
  		if(!isComma($obj.val())){
			alertMsg.warn("请正确输入的数量.不能以,结尾");
			$obj.val(s);
		} 
		array  = $obj.val().split(",");
		var b = 0;
		for(var i=0;i<array.length;i++){
			b=parseInt(b)+parseInt(array[i]);
		}
		if(parseInt(b)-parseInt(s)>0){
			alertMsg.warn("装箱数量不能大于实发数量");
			$obj.val(s);
		}
	} 
	/* $checkbox.attr("checked",true); */
	doSelectedBefore2($tr,checkObj,1);
}
function doMyInputRemark(obj){
	var $obj = $(obj);
	var $tr = $obj;
	while($tr.get(0).tagName != "TR")
		$tr = $tr.parent();
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	var checkObj = $("input:first",$tr.find("td").eq(1));
	if($obj.val() > 0)
		$checkbox.attr("checked",true);
	doSelectedBefore3($tr,checkObj,1);
}
function doSelectedBefore($tr,$obj,type)
{
	var $input = $tr.find("td").eq(10).find("input:first");
    var s = "";
    if($input && $input.get(0).tagName=="INPUT")
        s = $input.val();
    else
    {
        s = $tr.find("td").eq(9).text();
    }
    doCheckbox($obj.get(0));
}
function doSelectedBefore2($tr,$obj,type)
{
    doCheckbox($obj.get(0));
}
function doSelectedBefore3($tr,$obj,type)
{
    doCheckbox($obj.get(0));
}

function doCheckbox(checkbox) {
    var $tr = $(checkbox).parent().parent();

    // 实发数量
    var realCount = "";
    if($tr.find("td").eq(9).find("input:first").size()>0)
    	realCount = $tr.find("td").eq(10).find("input:first").val();
    else
    	realCount = $tr.find("td").eq(9).text();
    
    // 箱号
    var boxNo = "";
    if($tr.find("td").eq(10).find("input:first").size()>0)
        boxNo = $tr.find("td").eq(10).find("input:first").val();
    else
        boxNo = $tr.find("td").eq(10).text();

    // 数量
    var count = "";
    if($tr.find("td").eq(11).find("input:first").size()>0)
        count = $tr.find("td").eq(11).find("input:first").val();
    else
        count = $tr.find("td").eq(11).text();
/*     if(boxNo.length==0||count.length==0){
        $(checkbox).attr("checked",false);
        return false;
    } */ 
/*     var c_l = count.split(",").length;
    var b_l = boxNo.split(",").length;
    if(parseInt(c_l)!=parseInt(b_l)){
        $(checkbox).attr("checked",false);
        return false;
    }  */
    // 备注
    var remark = "";
    if($tr.find("td").eq(12).find("input:first").size()>0)
        remark = $tr.find("td").eq(12).find("input:first").val();
    else
        remark = $tr.find("td").eq(12).text();

/*      if (!count ||!isNumComma(count)){
        $(checkbox).attr("checked",false);
        return false;
    }  */
	var c = count.split(",");
	var s = 0;
	for(var i =0;i<c.length;i++){
		s= parseInt(s)+parseInt(c[i]);
	}
     if (parseInt(s)>parseInt(realCount)) {
        $(checkbox).attr("checked",false);
        return false;
    } 
    var arr = [];
    arr.push($tr.attr("PART_ID"));
    arr.push($tr.attr("PART_CODE"));
    arr.push($tr.attr("PART_NAME"));
    arr.push(boxNo.replace(/,/, "/"));
    arr.push(count);
    if(!remark){
        arr.push('myNull');
    }else{
        arr.push(remark);
    }
    arr.push($tr.attr("ISSUE_ID"));
    arr.push($tr.attr("ISSUE_NO"));
    $(checkbox).attr("checked",true);
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    multiSelected($checkbox, arr,$('#div-selectedPart'));
}
/*校验数字  */
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
/*校验以,结尾  */
function isComma(val){
	var a = val.substr(-1);
	var reg = /,$/;
	if(!reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}
/*数字和,组成的字符串，且不能以,结尾  */
function isNumComma(val){
	var reg = /^\d+(,\d+)*$/;
	if(reg.test(val)){
		return true;
	}else{
		return false;
	}
}
function customOtherValue($row,checkVal)
{
    var $inputObj1 = $row.find("td").eq(10);//箱号
    var val1;
    if($("#val3") && $("#val3").val())
    {
        var t = $("#val3").val();
        var pks = $("#val0").val();
        var ss = t.split(",");
        var pk = pks.split(",");
        for(var i=0;i<pk.length;i++)
        {
            if(pk[i] == checkVal)
            {
                val1 = ss[i];
                break;
            }
        }
    }
    if(val1)
    {
        $inputObj1.html("<div>"+val1.replace(/!/g,',')+"</div>");
    }

    var $inputObj2 = $row.find("td").eq(11);//数量
    var val2;
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
                val2 = ss[i];
                break;
            }
        }
    }
    if(val2)
    {
        $inputObj2.html("<div>"+val2+"</div>");
    }

    var $inputObj3 = $row.find("td").eq(12);//备注
    var val3;
    if($("#val5") && $("#val5").val())
    {
        var t = $("#val5").val();
        var pks = $("#val0").val();
        var ss = t.split(",");
        var pk = pks.split(",");
        for(var i=0;i<pk.length;i++)
        {
            if(pk[i] == checkVal)
            {
                val3 = ss[i];
                break;
            }
        }
    }
    if(val3)
    {
        if(val3=='myNull'){
            val3= '';
        }
        $inputObj3.html("<div>"+val3+"</div>");
    }
}
</script>