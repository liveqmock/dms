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
    <div class="tabs" id="dia-tabs">
        <div class="tabsContent">
            <!--配件入库清单 begin-->
            <div>
                <form method="post" id="fm-searchInBillPart" class="editForm">
                    <table class="searchContent" id="tab-searchInBillPart"></table>
                </form>
                <div id="div-inBillPartList" style="">
                    <table style="display:none;width:100%;" id="tab-inBillPartList" name="tablist" layoutH="500px" limitH="false" multivals="div-selectedPart" ref="div-inBillPartList" refQuery="tab-searchInBillPart">
                        <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style="display:none"></th>
                            <th fieldname="PART_CODE" colwidth="180">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="240">配件名称</th>
                            <th fieldname="UNIT" colwidth="45">单位</th>
                            <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                            <th fieldname="AMOUNT" colwidth="80">库存数量</th>
                            <th fieldname="RETURN_COUNT" colwidth="80">退货数量</th>
                            <th fieldname="IN_AMOUNT" colwidth="80">已入库数量</th>
                            <th fieldname="" colwidth="180" refer="createInputBox3">库位</th>
                            <th fieldname="REMARKS" refer="createInputBox4">备注</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <form id="fm-partInfo">
<!--                     <input type="hidden" id="inId" name="inId" datasource="IN_ID"/> -->
<!--                     <input type="hidden" id="inNo" name="inNo" datasource="IN_NO"/> -->
                    <input type="hidden" id="orgId" name="orgId" datasource="ORG_ID"/>
                    <input type="hidden" id="orgCode" name="orgCode" datasource="ORG_CODE"/>
                    <input type="hidden" id="orgName" name="orgName" datasource="ORG_NAME"/>
                    <input type="hidden" id="splitId" name="splitId" datasource="SPLIT_ID"/>
                     <input type="hidden" id="splitNo" name="splitNo" datasource="SPLIT_NO"/>
                    <input type="hidden" id="warehouseId" name="warehouseId" datasource="WAREHOUSE_ID"/>
                    <input type="hidden" id="warehouseCode" name="warehouseCode" datasource="WAREHOUSE_CODE"/>
                    <input type="hidden" id="warehouseName" name="warehouseName" datasource="WAREHOUSE_NAME"/>
                    <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                    <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                    <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                    <input type="hidden" id="partNos" name="partNos" datasource="PARTNOS"/>
                    <input type="hidden" id="curInAmounts" name="curInAmounts" datasource="CURINAMOUNTS"/>
                    <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
                    <input type="hidden" id="inAmounts" name="inAmounts" datasource="INAMOUNTS"/>
                    <input type="hidden" id="splitDtlIds" name="splitDtlIds" datasource="SPLITDTLIDS"/>
                    <input type="hidden" id="supplierIds" name="supplierIds" datasource="SUPPLIERIDS"/>
                    <input type="hidden" id="supplierCodes" name="supplierCodes" datasource="SUPPLIERCODES"/>
                    <input type="hidden" id="supplierNames" name="supplierNames" datasource="SUPPLIERNAMES"/>
                    <input type="hidden" id="userAcc" name="userAcc" datasource="userAcc"/>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockIn">入&nbsp;&nbsp;库</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--配件入库清单 end-->
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleRetStockInMngAction";
    var diaAction = "<%=action%>";
    var flag = true;

    // 销售退件订单ID
    var returnId = "";
    // 销售退件订单号
    var returnNo = "";
    // 仓库ID
    var warehouseId = "";
    // 仓库CODE
    var warehouseCode = "";
    // 仓库NAME
    var warehouseName = "";
    // 渠道ID
    var orgId = "";
    // 渠道CODE
    var orgCode = ""; 
    // 渠道NAEM
    var orgName = "";

    $(function () {
        var iH = document.documentElement.clientHeight;
        $(".tabsContent").height(iH - 20);
        $("#div-inBillPartList").height(iH-90);
        if ($("#tab-inBillPartList").is(":hidden")) {
             $("#tab-inBillPartList").show();
             //$("#tab-inBillPartList").jTable();
        }
        
        var selectedRows = $("#tab-inBillList").getSelectedRows();
        
        // 销售退件订单ID
        returnId = selectedRows[0].attr("RETURN_ID");
        // 销售退件订单号
        returnNo = selectedRows[0].attr("RETURN_NO");
        // 仓库ID
        warehouseId = selectedRows[0].attr("RECEIVE_ORG_ID");
        // 仓库CODE
        warehouseCode = selectedRows[0].attr("RECEIVE_ORG_CODE");
        // 仓库NAME
        warehouseName = selectedRows[0].attr("RECEIVE_ORG_NAME");
        // 渠道ID
        orgId = selectedRows[0].attr("APPLY_ORG_ID");
        // 渠道CODE
        orgCode = selectedRows[0].attr("APPLY_ORG_CODE");
        // 渠道NAME
        orgName = selectedRows[0].attr("APPLY_ORG_NAME");

         searchInBillPart();
         $('#fie-selectedPart').show();

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
                	flag = false;
                    partIds += partIds.length?"," + $tr.attr("PART_ID"):$tr.attr("PART_ID");
                    partCodes += partCodes.length?"," + $tr.attr("PART_CODE"):$tr.attr("PART_CODE");
                    partNames += partNames.length?"," + $tr.attr("PART_NAME"):$tr.attr("PART_NAME");
                    partNos += partNos.length?"," + $tr.attr("PART_NO"):$tr.attr("PART_NO");
                    
                    supplierIds += supplierIds.length?"," + $tr.attr("SUPPLIER_ID"):$tr.attr("SUPPLIER_ID");
                    supplierCodes += supplierCodes.length?"," + $tr.attr("SUPPLIER_CODE"):$tr.attr("SUPPLIER_CODE");
                    supplierNames += supplierNames.length?"," + $tr.attr("SUPPLIER_NAME"):$tr.attr("SUPPLIER_NAME");
                    inAmounts += inAmounts.length?"," + $tr.attr("IN_AMOUNT"):$tr.attr("IN_AMOUNT");
                    splitDtlIds += splitDtlIds.length?"," + $tr.attr("DTL_ID"):$tr.attr("DTL_ID");
                    
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

            if (flag) {
                alertMsg.warn('请填入库数量!');
                return;
            }

            // 销售退件订单ID
            $('#splitId').val(returnId);
            // 销售退件订单号
            $('#splitNo').val(returnNo);

            // 仓库ID
            $('#warehouseId').val(warehouseId);
            // 仓库CODE
            $('#warehouseCode').val(warehouseCode);
            // 仓库NAME
            $('#warehouseName').val(warehouseName);

            //配件
            $('#partIds').val(partIds);
            $('#partCodes').val(partCodes);
            $('#partNames').val(partNames);
            $('#partNos').val(partNos);
            $('#curInAmounts').val(curInAmounts);

            // 备注
            $('#remarks').val(remarks);
            // 入库数量
            $('#inAmounts').val(inAmounts);
            // 销售退件明细ID
            $('#splitDtlIds').val(splitDtlIds);

            //供应商
            $('#supplierIds').val(supplierIds);
            $('#supplierCodes').val(supplierCodes);
            $('#supplierNames').val(supplierNames);

            // 渠道
            $('#orgId').val(orgId);
            $('#orgCode').val(orgCode);
            $('#orgName').val(orgName);
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

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });

    })

    // 入库回调方法
    function partStockInCallBack(){
        $('#btn-stockIn').attr('disabled',false);
        searchInBillPart();
    }

    function setDiaDefaultValue() {
    
    }

    //新增回调函数
    function diaInsertCallBack(res) {

        try {
            var rows = res.getElementsByTagName("ROW");
            if (rows && rows.length > 0) {
                //获取新增入库单ID 并设置到隐藏域中
                var inId = getNodeText(rows[0].getElementsByTagName("IN_ID").item(0));
                $('#dia-IN_ID').val(inId);
            }
            //不显示结果集的情况下，插入一行
            $("#tab-inBillList").insertResult(res,0);
            if($("#tab-inBillList_content").size()>0){
                $("td input[type=radio]",$("#tab-inBillList_content").find("tr").eq(0)).attr("checked",true);
            }else{
                $("td input[type=radio]",$("#tab-inBillList").find("tr").eq(0)).attr("checked",true);
            }
    
            diaAction = 2;
            $('#dia-WAREHOUSE_CODE').attr('readOnly','true');
            $('#dia-WAREHOUSE_CODE').attr('src','');
            $('#dia-WAREHOUSE_CODE').parent().find('span').hide();
            $('#dia-ORDER_NO').parent().find('img').hide();
            $('#dia-ORDER_NO').parent().find('span').hide();
            
            
            
            var $tab = $("#tab-inBillPartList");
            $("#tab-inBillPartList").find("tr").each(function(){
                var $tr = $(this);
                var $tabs = $tr;
                while($tabs.get(0).tagName != "TABLE")
                    $tabs = $tabs.parent();
                $tabs.parent().parent().attr("style","overflow:hidden;");
            });
            
            
    
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //更新回调函数
    function diaUpdateCallBack(res) {

        try {
            var selectedIndex = $("#tab-inBillList").getSelectedIndex();
            $("#tab-inBillList").updateResult(res, selectedIndex);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //弹出销售退件单选择列表
    function openRetSelWin(){

        if(!$('#dia-WAREHOUSE_ID').val()){
            alertMsg.warn('请先选择入库仓库!');
            return;
        }
        var options = {max: false, width: 900, height: 450, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/saleRetStockInMngRetSel.jsp", "retSelWin", "销售退件单查询", options);
    }

    //查询入库单备件
    function searchInBillPart() {

        // 获取行数据
        var selectedRows = $("#tab-inBillList").getSelectedRows();
        // 订单主键
        var returnId = selectedRows[0].attr("RETURN_ID");
        // 仓库主键
        var warehouseId =  selectedRows[0].attr("RECEIVE_ORG_ID");
        
        var searchInBillPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleRetStockInMngAction/searchInBillPart.ajax?IN_ID=3 &ORDER_ID="+returnId+"&WAREHOUSE_ID="+warehouseId+"&account="+$('#PERSON_NAME').attr('code');
        var $f = $("#fm-searchInBillPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchInBillPartUrl, "", 1, sCondition, "tab-inBillPartList");
    }

    function callbackSearch(responseText,tabId,sParam) {

        switch(tabId) {
            case "tab-inBillPartList":
                 setStyle('tab-inBillPartList_content');
            break;
        }
    }

  //将库位字段渲染为文本框
    function createInputBox3(obj)
    {
        var $obj = $(obj);
        var $tr = $obj.parent();
        var partId = $tr.attr('PART_ID');
        var waitCount = $tr.attr('RETURN_COUNT');
        //设置库位选择
        var pIds = $tr.attr("POSITION_IDS");
        var pCodes = $tr.attr("POSITION_CODESS");
        var pNames = $tr.attr("POSITION_NAMES");
        var s = "";
        if(pIds){
        	var pId = pIds.split(",");
            var pName = pNames.split(",");
            var pCode = pCodes.split(",");
            if(pId.length==1){
    	    	for(var i=0;i<pId.length;i++)
    	        {
    	        	s += "<div ondblclick='doMyInputDblclick(this)' name='WPS' style='line-height:23px;width:99%;border-bottom:1px solid red'>";
    	        	s += "<span id='WP_'"+partId+"_"+pId[i]+" name='WAREHOUSE_POSITION_CODE' wpId='"+pId[i]+"' wpCode='"+pCode[i]+"' wpName='"+pName[i]+"' style='float:left;padding-top:3px;margin-right:2px;'>"+pCode[i]+"</span>&nbsp;&nbsp;";
    	        	s += "<input id='WPS_'"+partId+"_"+pId[i]+"' disabled='true' onblur='doWpBlur(this);' name='WAREHOUSE_POSITION_CODE_COUNT' value='"+waitCount+"' style='width:30px;padding-top:-5px;background:#dfe3e5;' /></div>";
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
            	$checkbox.attr("checked",false);
                alertMsg.warn("请正确输入的入库数量.");
                return false;
            }
        }
        //校验输入数量：库位数量 <= 已验收数 - 已入库数
        var $div = $tr.find("div[name='WPS']");
        //出库数量
        var accCount = $tr.attr("RETURN_COUNT");
        //已入库数
        var storCount = $tr.attr("IN_AMOUNT");
        var count = 0;
        $("input",$div).each(function(){
            if($(this).val())
            {
                count = parseInt(count,10) + parseInt($(this).val(),10);
            }
        });
        if(count > (accCount - storCount))
        {
        	$checkbox.attr("checked",false);
            alertMsg.warn("入库数量不能大于出库数量+已入库数量!");
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

    //将备注字段渲染为文本框
    function createInputBox4(obj) {

        return '<input type="text" maxlength="1000" name="REMARK" value="'+obj.text()+'"/>';
    }

    function isNumeric(val) {

        var reg = /^[1-9]\d*$/
        if(reg.test(val)) {
            return true;
        } else {
            return false;
        }
    }

    // CHECKBOX事件
//     function doCheckbox(checkbox) {

//         var $tr = $(checkbox).parent().parent().parent();
//         var curInAmount = "";
//         if($tr.find("td").eq(9).find("input:first").size()>0) {
//             curInAmount = $tr.find("td").eq(9).find("input:first").val();
//         } else {
//             curInAmount = $tr.find("td").eq(9).text();
//         }

//         var waId = "";
//         var waCode = "";
//         var waName = "";
//         waId = $tr.find("td").eq(10).find("input:first").attr('AREA_ID');
//         waCode = $tr.find("td").eq(10).find("input:first").attr('code');
//         waName = $tr.find("td").eq(10).find("input:first").val();
    
//         var wpId = "";
//         var wpCode = "";
//         var wpName = "";
//         wpId = $tr.find("td").eq(11).find("input:first").attr('POSITION_ID');
//         wpCode = $tr.find("td").eq(11).find("input:first").attr('code');
//         wpName = $tr.find("td").eq(11).find("input:first").val();
    
//         var remark = "";
//         if($tr.find("td").eq(12).find("input:first").size()>0) {
//             remark = $tr.find("td").eq(12).find("input:first").val();
//         } else {
//             remark = $tr.find("td").eq(12).text();
//         }

//         if (!curInAmount || !isNumeric(curInAmount)){
//             alertMsg.warn("请输入正确的本次入库数量!");
//             $(checkbox).attr("checked",false);
//             return false;
//         } else {
//             var returnCount = $tr.attr('RETURN_COUNT');//退货数量
//             var inAmount = $tr.attr('IN_AMOUNT');//已入库数量
//             if(parseInt(curInAmount)>(parseInt(returnCount)-parseInt(inAmount))){
//                 alertMsg.warn("本次入库数量不能大于退货数量与已入库数量之差！");
//                 $(checkbox).attr("checked",false);
//                 return false;
//             }
//             if(!waId){
//                 alertMsg.warn("请选择库区!");
//                 $(checkbox).attr("checked",false);
//                 return false;
//             }else{
//                 if(!wpId){
//                     alertMsg.warn("请选择库位!");
//                     $(checkbox).attr("checked",false);
//                     return false;
//                 }
//             }
//         }
//         var arr = [];
//         arr.push($tr.attr("DTL_ID"));
//         arr.push($tr.attr("PART_ID"));
//         arr.push($tr.attr("PART_CODE"));
//         arr.push($tr.attr("PART_NAME"));
//         arr.push($tr.attr("PART_NO"));
//         arr.push(curInAmount);
//         arr.push(waId);
//         arr.push(waCode);
//         arr.push(waName);
//         arr.push(wpId);
//         arr.push(wpCode);
//         arr.push(wpName);
//         if(!remark){
//             arr.push('myNull');
//         }else{
//             arr.push(remark);
//         }
//         arr.push($tr.attr("IN_AMOUNT"));
//         arr.push($tr.attr("SUPPLIER_ID"));
//         arr.push($tr.attr("SUPPLIER_CODE"));
//         arr.push($tr.attr("SUPPLIER_NAME"));
//         var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
//         multiSelected($checkbox, arr,$('#div-selectedPart'));
//         //设置input框显示或文本只读
//         if($checkbox.is(":checked")) {
//             $tr.find("td").eq(9).html("<div>"+curInAmount+"</div>");
//             $tr.find("td").eq(10).find("input:first").attr('disabled',true);
//             $tr.find("td").eq(11).find("input:first").attr('disabled',true);
//             $tr.find("td").eq(12).html("<div>"+remark+"</div>");
//         } else {
//             $tr.find("td").eq(9).html("<div><input type='text' name='CUR_IN_AMOUNT'  maxlength='6' value='"+curInAmount+"'/></div>");
//             $tr.find("td").eq(10).find("input:first").attr('disabled',false);
//             $tr.find("td").eq(11).find("input:first").attr('disabled',false);
//             $tr.find("td").eq(12).html("<div><input type='text' name='REMARK'  maxlength='1000' value='"+remark+"'/></div>");
//         }
//     }

//     function customOtherValue($row,checkVal) {

//         var $inputObj1 = $row.find("td").eq(9);//本次入库数量
//         var val1;
//         if($("#val4") && $("#val4").val()) {
//             var t = $("#val4").val();
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
//         if(val1) {
//             $inputObj1.html("<div>"+val1+"</div>");
//         }

//         var $inputObj2 = $row.find("td").eq(12);//备注
//         var val2;
//         if($("#val11") && $("#val11").val()) {
//             var t = $("#val11").val();
//             var pks = $("#val0").val();
//             var ss = t.split(",");
//             var pk = pks.split(",");
//             for(var i=0;i<pk.length;i++) {
//                 if(pk[i] == checkVal) {
//                     val2 = ss[i];
//                     break;
//                 }
//             }
//         }
//         if(val2) {
//             if(val2=='myNull'){
//                 val2 = '';
//             }
//             $inputObj2.html("<div>"+val2+"</div>");
//         }
    
//         var $inputObj3 = $row.find("td").eq(10);//库区
//         var waId;
//         var waCode;
//         var waName;
//         if($("#val5") && $("#val5").val()) {
//             var waIds = $("#val5").val();
//             var waCodes = $("#val6").val();
//             var waNames = $("#val7").val();
//             var pks = $("#val0").val();
//             var waIdArr = waIds.split(",");
//             var waCodeArr = waCodes.split(",");
//             var waNameArr = waNames.split(",");
//             var pk = pks.split(",");
//             for(var i=0;i<pk.length;i++) {
//                 if(pk[i] == checkVal) {
//                     waId = waIdArr[i];
//                     waCode = waCodeArr[i];
//                     waName = waNameArr[i];
//                     break;
//                 }
//             }
//         }
//         if(waId) {
//             var waInput = $inputObj3.find('input').eq(0);
//             waInput.attr('AREA_ID',waId);
//             waInput.attr('code',waCode);
//             waInput.attr('value',waName);
//             waInput.attr('disabled',true);
//         }
    
//         var $inputObj4 = $row.find("td").eq(11);//库位
//         var wpId;
//         var wpCode;
//         var wpName;
//         if($("#val8") && $("#val8").val()) {
//             var wpIds = $("#val8").val();
//             var wpCodes = $("#val9").val();
//             var wpNames = $("#val10").val();
//             var pks = $("#val0").val();
//             var wpIdArr = wpIds.split(",");
//             var wpCodeArr = wpCodes.split(",");
//             var wpNameArr = wpNames.split(",");
//             var pk = pks.split(",");
//             for(var i=0;i<pk.length;i++) {
//                 if(pk[i] == checkVal) {
//                     wpId = wpIdArr[i];
//                     wpCode = wpCodeArr[i];
//                     wpName = wpNameArr[i];
//                     break;
//                 }
//             }
//         }
//         if(wpId) {
//             var wpInput = $inputObj4.find('input').eq(0);
//             wpInput.attr('POSITION_ID',wpId);
//             wpInput.attr('code',wpCode);
//             wpInput.attr('value',wpName);
//             wpInput.attr('disabled',true);
//         }
//     }

    function toAppendStr(obj){

        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }

//     function ctrlShow(obj){

//         var $tr =$(obj).parent();
//         if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
//             return "";
//         }else{
//             return $(obj).text();
//         }
//     }
</script>