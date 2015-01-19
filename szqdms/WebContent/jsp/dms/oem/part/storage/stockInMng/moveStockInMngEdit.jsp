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
<div class="page">
    <div class="tabContent">
        <!--配件入库清单 begin-->
            <form method="post" id="fm-searchInBillPart" class="editForm">
                <table class="searchContent" id="tab-searchInBillPart"></table>
            </form>
            <div id="div-inBillPartList" style="overflow:auto;">
                <table style="display:none;width:100%;" id="tab-inBillPartList" name="tablist" multivals="div-selectedPart" layoutH="500px" ref="div-inBillPartList" refQuery="tab-searchInBillPart">
                    <thead>
                    <tr>
                        <th type="multi" name="XH" unique="PART_ID" style="display:none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="PART_NO" style="display:none">参图号</th>
                        <th fieldname="UNIT" colwidth="45">单位</th>
                        <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                        <th fieldname="AVAILABLE_AMOUNT" colwidth="80">库存数量</th>
                        <th fieldname="OUT_AMOUNT" colwidth="80">出库数量</th>
                        <th fieldname="" colwidth="155" refer="createInputBox3">库位/入库数量</th>
                        <th fieldname="REMARKS" refer="createInputBox4">备注</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <form id="fm-partInfo">
            	<input type="hidden" id="warehouseType" name="warehouseType" datasource="WAREHOUSE_TYPE"/>
                <input type="hidden" id="warehouseId" name="warehouseId" datasource="WAREHOUSE_ID"/>
                <input type="hidden" id="warehouseCode" name="warehouseCode" datasource="WAREHOUSE_CODE"/>
                <input type="hidden" id="warehouseName" name="warehouseName" datasource="WAREHOUSE_NAME"/>
                <input type="hidden" id="outId" name="outId" datasource="OUT_ID"/>
                <input type="hidden" id="outNo" name="outNo" datasource="OUT_NO"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PART_IDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PART_CODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PART_NAMES"/>
                <input type="hidden" id="curInAmounts" name="curInAmounts" datasource="CURINAMOUNTS"/>
                <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
                <input type="hidden" id="supplierIds" name="supplierIds" datasource="SUPPLIER_IDS"/>
                <input type="hidden" id="supplierCodes" name="supplierCodes" datasource="SUPPLIER_CODES"/>
                <input type="hidden" id="supplierNames" name="supplierNames" datasource="SUPPLIER_NAMES"/>
                <input type="hidden" id="userAcc" name="userAcc" datasource="USERACC"/>
            </form>
            <div class="formBar">
             <ul>
                 <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockIn">入&nbsp;&nbsp;库</button></div></div></li>
                 <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
             </ul>
         	</div>
        <!--配件入库清单 end-->
    </div>
</div>

<script type="text/javascript">
// 仓库类型
var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockInMng/MoveStockInMngAction";
var diaAction = "<%=action%>";
var flag = true;
$(function () {
    var iH = document.documentElement.clientHeight;
    //$("#div-inBillPartList").height(iH - 70);
    //保存基本信息按钮响应
    $('#btn-save').bind('click', function () {
        //获取需要提交的form对象
        var $f = $("#fm-inBillInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        if (diaAction == 1)	//新增
        {
            var addUrl = diaSaveAction + "/insertInBill.ajax";
            doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
        } else	//更新
        {
            var updateUrl = diaSaveAction + "/updateInBill.ajax";
            doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
        }
    });
    if (diaAction != "1") {//修改
        var selectedRows = $("#tab-inBillList").getSelectedRows();
        setEditValue("fm-inBillInfo", selectedRows[0].attr("rowdata"));
        $("#warehouseType").val(selectedRows[0].attr("WAREHOUSE_TYPE"));
        $("#warehouseId").val(selectedRows[0].attr("WAREHOUSE_ID"));
        $("#warehouseCode").val(selectedRows[0].attr("WAREHOUSE_CODE"));
        $("#warehouseName").val(selectedRows[0].attr("WAREHOUSE_NAME"));
        $("#outId").val(selectedRows[0].attr("OUT_ID"));
        $("#outNo").val(selectedRows[0].attr("OUT_NO"));
        searchInBillPart();
    }
    //入库按钮响应
    $('#btn-stockIn').bind('click',function(){
    	
    	//设置各隐藏域值
        var partIds="",partCodes="",partNames="",partNos="",curInAmounts="";
        var supplierIds="",supplierCodes="",supplierNames="";
        var inAmounts="",splitDtlIds="",remarks="";
        $("tr",$("#tab-inBillPartList")).each(function(){
        	var $tr = $(this);
        	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        	if($checkbox.is(":checked"))
        	{
        		partIds += partIds.length?"," + $tr.attr("PART_ID"):$tr.attr("PART_ID");
        		partCodes += partCodes.length?"," + $tr.attr("PART_CODE"):$tr.attr("PART_CODE");
        		partNames += partNames.length?"," + $tr.attr("PART_NAME"):$tr.attr("PART_NAME");
        		partNos += partNos.length?"," + $tr.attr("PART_NO"):$tr.attr("PART_NO");
        		
        		supplierIds += supplierIds.length?"," + $tr.attr("SUPPLIER_ID"):$tr.attr("SUPPLIER_ID");
        		supplierCodes += supplierCodes.length?"," + $tr.attr("SUPPLIER_CODE"):$tr.attr("SUPPLIER_CODE");
        		supplierNames += supplierNames.length?"," + $tr.attr("SUPPLIER_NAME"):$tr.attr("SUPPLIER_NAME");
        		inAmounts += inAmounts.length?"," + $tr.attr("IN_AMOUNT"):$tr.attr("IN_AMOUNT");
        		splitDtlIds += splitDtlIds.length?"," + $tr.attr("DETAIL_ID"):$tr.attr("DETAIL_ID");
        		
        		//库位+本库位入库数量，库位id|库位code|库位name|数量 #...
        		var curInAmountsTmp = "";
        		$tr.find("div[name='WPS']").each(function(){
        			var $span = $(this).find("span:first");
        			var $input = $(this).find("input:first");
        			if($input.val())
        			{
        				if(curInAmountsTmp.length == 0)
        					curInAmountsTmp = $span.attr("wpId")+"@"+$span.attr("wpCode")+"@"+$span.attr("wpName")+"@"+$input.val();
            			else
            				curInAmountsTmp = curInAmountsTmp + "#" + $span.attr("wpId")+"@"+$span.attr("wpCode")+"@"+$span.attr("wpName")+"@"+$input.val();        				
        			}
        		});
        		curInAmounts += curInAmounts.length?"," + curInAmountsTmp:curInAmountsTmp;
        		//备注
        		var remarkTmp = $tr.find("input[name='REMARK']");
        		if(remarkTmp.val()){
        			remarks += remarks.length?"," + remarkTmp.val():remarkTmp.val();
        		}else{
        			remarks += remarks.length?"," + "myNull":"myNull";
        		}
        	}
        });
        if(partIds.length==0){
        	alertMsg.info("请输入入库数量.");
        	return false;
        }
        //配件
        $('#partIds').val(partIds);
        $('#partCodes').val(partCodes);
        $('#partNames').val(partNames);
        $('#curInAmounts').val(curInAmounts);
        $('#remarks').val(remarks);
        $('#inAmounts').val(inAmounts);
        $('#splitDtlIds').val(splitDtlIds);//拆分单明细id
        //供应商
        $('#supplierIds').val(supplierIds);
        $('#supplierCodes').val(supplierCodes);
        $('#supplierNames').val(supplierNames);
        $('#userAcc').val($('#PERSON_NAME').attr('code'));
        var partStockInUrl = diaSaveAction+'/partStockIn.ajax';
        //获取需要提交的form对象
        var $f = $("#fm-partInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, partStockInUrl, "btn-stockIn", sCondition, partStockInCallBack);
    });
});

function partStockInCallBack(){
	$.pdialog.closeCurrent();
	var $f = $("#fm-searchInBill");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-inBillList");
}

//查询入库单备件
function searchInBillPart() {
    var searchInBillPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/MoveStockInMngAction/searchInBillPart.ajax?outId=" + $("#outId").val()+"&warehouseId="+$("#warehouseId").val()+"&warehouseType="+$("#warehouseType").val()+"&account="+$('#PERSON_NAME').attr('code');;
    var $f = $("#fm-searchInBillPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchInBillPartUrl, "", 1, sCondition, "tab-inBillPartList");
}

//将库位字段渲染为文本框
function createInputBox3(obj)
{
    var $obj = $(obj);
    var $tr = $obj.parent();
    var partId = $tr.attr('PART_ID');
    var warehouseType = $("#warehouseType").val();
    //设置库位选择
    var pIds = $tr.attr("POSITION_IDS");
    var pCodes = $tr.attr("POSITION_CODES");
    var pNames = $tr.attr("POSITION_NAMES");
    var s = "";
    if(pIds){
    	var pId = pIds.split(",");
        var pName = pNames.split(",");
        var pCode = pCodes.split(",");
    	if(pId.length==1){
	    	for(var i=0;i<pId.length;i++)
	        {
	    		if(warehouseType=="100101"){
	    			s += "<div ondblclick='doMyInputDblclick(this)' name='WPS' style='line-height:23px;width:99%;border-bottom:1px solid red'>";
	    		}else{
	    			s += "<div  name='WPS' style='line-height:23px;width:99%;border-bottom:1px solid red'>";
	    		}
	        	s += "<span id='WP_'"+partId+"_"+pId[i]+" name='WAREHOUSE_POSITION_CODE' wpId='"+pId[i]+"' wpCode='"+pCode[i]+"' wpName='"+pName[i]+"' style='float:left;padding-top:3px;margin-right:2px;'>"+pCode[i]+"</span>&nbsp;&nbsp;";
	        	s += "<input id='WPS_'"+partId+"_"+pId[i]+"' disabled='true' onblur='doWpBlur(this);' name='WAREHOUSE_POSITION_CODE_COUNT' value='"+$tr.attr("OUT_AMOUNT")+"' style='width:30px;padding-top:-5px;background:#dfe3e5;' /></div>";
	        }
	    	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	    	$checkbox.attr("checked",true);
        }else{
        	for(var i=0;i<pId.length;i++)
	        {
	        	s += "<div name='WPS' style='line-height:23px;width:99%;border-bottom:1px solid red'>";
	        	s += "<span id='WP_'"+partId+"_"+pId[i]+" name='WAREHOUSE_POSITION_CODE' wpId='"+pId[i]+"' wpCode='"+pCode[i]+"' wpName='"+pName[i]+"' style='float:left;padding-top:3px;margin-right:2px;'>"+pCode[i]+"</span>&nbsp;&nbsp;";
	        	s += "<input id='WPS_'"+partId+"_"+pId[i]+"' onblur='doWpBlur(this);' name='WAREHOUSE_POSITION_CODE_COUNT' style='width:30px;padding-top:-5px;' /></div>";
	        }
        }
    	$tr.height(28*(pId.length));
   	    $tr.css("line-height",26*(pId.length));
    }
    //return '<input type="text" id="WP_'+partId+'" name="WAREHOUSE_POSITION_CODE" kind="dic" src="" datatype="1,is_null,30"/>';
    return "<div>"+s+"</div>";
}
function doMyInputDblclick(obj)
{
	var $obj = $(obj);
	$obj.find("input:first").attr("disabled",false);
    $obj.find("input:first").css("background","#fff");
    $obj.find("input:first").focus();
}
//库位框移开事件
function doWpBlur(obj)
{
	var $obj = $(obj);
	var $tr = $obj;
	while($tr.get(0).tagName != "TR")
		$tr = $tr.parent();
	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
	if($obj.val().length > 0)
	{
		if(!isNumV($obj.val())){
			alertMsg.warn("请正确输入的入库数量.");
			return false;
		}
	}
	//校验输入数量：库位数量 <= 已验收数 - 已入库数
	var $div = $tr.find("div[name='WPS']");
	//出库数量
	var accCount = $tr.attr("OUT_AMOUNT");
	var count = 0;
	$("input",$div).each(function(){
		if($(this).val())
		{
			count = parseInt(count,10) + parseInt($(this).val(),10);
		}
	});
	if(count > accCount)
	{
		alertMsg.warn("入库数量不能大于出库数量!");
		return false;
	}
	if($obj.val() > 0)
		$checkbox.attr("checked",true);
	else
	{
		//判断当前行是否库位数量都是空，如果是空，则取消checkbox选中
		var $div = $tr.find("div[name='WPS']");
		var f = true;
		$("input",$div).each(function(){
			if($(this).val())
			{
				f = false;
				return false;
			}
		});
		if(f == true)
			$checkbox.attr("checked",false);
	}
}
function isNumV(val)
{
    var reg = /^[1-9]\d*$/;
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}

//将备注字段渲染为文本框
function createInputBox4(obj)
{
    return '<input type="text" maxlength="500" name="REMARK" value="'+obj.text()+'" />';
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