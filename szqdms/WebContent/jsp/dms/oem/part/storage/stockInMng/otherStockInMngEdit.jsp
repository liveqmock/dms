<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgId = user.getOrgId();
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
                    <li><a href="javascript:void(0);"><span>入库单信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>配件入库清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--入库单信息 begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="fm-inBillInfo" class="editForm">
                    <%--隐藏域查询条件--%>
                    <input type="hidden" id="dia-IN_ID" name="dia-IN_ID" datasource="IN_ID"/>
                    <input type="hidden" id="dia-IN_TYPE" name="dia-IN_TYPE" datasource="IN_TYPE" value="<%=DicConstant.RKLX_04%>"/>
                    <input type="hidden" id="dia-IN_STATUS" name="dia-IN_STATUS" datasource="IN_STATUS" value="<%=DicConstant.RKDZT_01%>"/>
                    <input type="hidden" id="dia-PRINT_STATUS" name="dia-PRINT_STATUS" datasource="PRINT_STATUS" value="<%=DicConstant.DYZT_01%>"/>
                    <div align="left">
                        <fieldset>
                            <legend align="right"><a onclick="onTitleClick('tab-inBillInfo')">&nbsp;入库单信息编辑&gt;&gt;</a>
                            </legend>
                            <table class="editTable" id="tab-inBillInfo">
                                <tr>
                                    <td><label>入库单号：</label></td>
                                    <td>
                                        <input type="text" id="dia-IN_NO" name="dia-IN_NO" datasource="IN_NO" readonly value="自动生成"/>
                                    </td>
                                    <td><label>入库仓库：</label></td>
                                    <td>
                                        <input type="hidden" id="dia-WAREHOUSE_ID" name="dia-WAREHOUSE_ID" datasource="WAREHOUSE_ID"/>
                                        <input type="hidden" id="dia-WAREHOUSE_NAME" name="dia-WAREHOUSE_NAME" datasource="WAREHOUSE_NAME"/>
                                        <input type="hidden" id="dia-WAREHOUSE_TYPE" name="dia-WAREHOUSE_TYPE" datasource="WAREHOUSE_TYPE"/>
                                        <input type="text" id="dia-WAREHOUSE_CODE" name="dia-WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME,WAREHOUSE_TYPE}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND ORG_ID = <%=orgId%> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> ORDER BY WAREHOUSE_CODE" datatype="0,is_null,30"/>
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
                                    <button type="button" id="btn-save">保&nbsp;&nbsp;存</button>
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
            <!--入库单信息 end-->
            <!--配件入库清单 begin-->
            <div>
                <form method="post" id="fm-searchInBillPart" class="editForm">
                    <table class="searchContent" id="tab-searchInBillPart"></table>
                </form>
                <div id="div-inBillPartList" style="height:100px;">
                    <table style="display:none;width:80%;" id="tab-inBillPartList" name="tablist" layoutH="500px" multivals="div-selectedPart" ref="div-inBillPartList" refQuery="tab-searchInBillPart">
                        <thead>
                        <tr>
                            <th type="multi" name="XH" unique="DETAIL_ID" style="" append="plus|openPartSelWin"></th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="PART_NAME" refer="toSubStr">配件名称</th>
                            <th fieldname="PART_NO" style="display: none">配件图号</th>
                            <th fieldname="UNIT">单位</th>
                            <th fieldname="MIN_PACK" colwidth="52" refer="toAppendStr">最小包装</th>
                            <th fieldname="AMOUNT" colwidth="52">库存数量</th>
                            <th fieldname="IN_AMOUNT" colwidth="66">已入库数量</th>
                            <th fieldname="" colwidth="180" refer="createInputBox3">库位</th>
                            <th fieldname="REMARKS" colwidth="150" refer="createInputBox4">备注</th>
                            <th fieldname="SUPPLIER_NAME" refer="ctrlShow">供应商</th>
                            <th colwidth="85" type="link" title="[删除]"  action="doDiaDelete" >操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <form id="fm-partInfo">
                    <input type="hidden" id="inId" name="inId" datasource="IN_ID"/>
                    <input type="hidden" id="inNo" name="inNo" datasource="IN_NO"/>
                    <input type="hidden" id="flag" name="flag" datasource="FLAG"/>
                    <input type="hidden" id="warehouseId" name="warehouseId" datasource="WAREHOUSE_ID"/>
                    <input type="hidden" id="warehouseCode" name="warehouseCode" datasource="WAREHOUSE_CODE"/>
                    <input type="hidden" id="warehouseName" name="warehouseName" datasource="WAREHOUSE_NAME"/>
                    <input type="hidden" id="detailIds" name="detailIds" datasource="DETAILIDS"/>
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
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockIn">入库</button></div></div></li>
                        <!-- <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockIn-End">入库完成</button></div></div></li> -->
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

    var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction";
    var diaAction = "<%=action%>";
    var flag = true;
    $(function () {
        var iH = document.documentElement.clientHeight;
        $(".tabsContent").height(iH - 70);
        
        // 上一步按钮绑定
        $("button[name='btn-pre']").bind("click",function(event){
            $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
        });
    
        // 下一步按钮绑定
        $("button[name='btn-next']").bind("click",function(event){
            var $tabs = $("#dia-tabs");
            $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
            //跳转后实现方法
            switch(parseInt($tabs.attr("currentIndex")))
            {
                case 1:
                    if (!$('#dia-IN_ID').val()) {
                        alertMsg.warn('请先保存入库单信息!');
                        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                    } else {
                        if(flag){
                            if ($("#tab-inBillPartList").is(":hidden")) {
                                searchInBillPart();
                            }
                        }
                        flag = false;
                    }
                    break;
                default:
                    break;
            }
        });
    
        //保存基本信息按钮响应
        $('#btn-save').bind('click', function () {
            //获取需要提交的form对象
            var $f = $("#fm-inBillInfo");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $f.combined(1) || {};
            if (diaAction == 1)    //新增
            {
                var addUrl = diaSaveAction + "/insertInBill.ajax";
                doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
            } else    //更新
            {
                var updateUrl = diaSaveAction + "/updateInBill.ajax";
                doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
            }
        });
    
        if (diaAction != "1") {//修改
            var selectedRows = $("#tab-inBillList").getSelectedRows();
            setEditValue("fm-inBillInfo", selectedRows[0].attr("rowdata"));
    
            $('#dia-WAREHOUSE_CODE').attr('readOnly','true');
            $('#dia-WAREHOUSE_CODE').attr('src','');
            $('#dia-WAREHOUSE_CODE').attr('datatype','1,is_null,30');
        } else {//新增
        }
    
        //入库按钮响应
        $('#btn-stockIn').bind('click',function(){
            stockIn();
        });

        //入库完成按钮响应
        $('#btn-stockIn-End').bind('click',function(){
            $("#flag").val("true");
            stockIn();
        });
    })
    
    function stockIn () {
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
                splitDtlIds += splitDtlIds.length?"," + $tr.attr("DETAIL_ID"):$tr.attr("DETAIL_ID");
                
                //库位+本库位入库数量，库位id|库位code|库位name|数量 #...
                var curInAmountsTmp = "";
                $tr.find("span[name='WAREHOUSE_POSITION_CODE']").each(function(){
                    var $span = $(this);
                    var $input = $(this).next();
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
        if (curInAmounts=="") {
        	alertMsg.warn('请填入库数量!');
            return;
        }
    
        $('#inId').val($('#dia-IN_ID').val());
        $('#inNo').val($('#dia-IN_NO').val());
    
        $('#warehouseId').val($('#dia-WAREHOUSE_ID').val());
        $('#warehouseCode').val($('#dia-WAREHOUSE_CODE').val());
        $('#warehouseName').val($('#dia-WAREHOUSE_NAME').val());
    
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
    
        var partStockInUrl = diaSaveAction+'/partStockIn.ajax';
        //获取需要提交的form对象
        var $f = $("#fm-partInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, partStockInUrl, "btn-stockIn", sCondition, partStockInCallBack);
    }
    function partStockInCallBack(){
        var editWin = $("body").data("editWin");
        // 查询入库单
        searchStockIn();
        $.pdialog.close(editWin);
    }
    
    //新增回调函数
    function diaInsertCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            if (rows && rows.length > 0) {
                //获取新增入库单ID 并设置到隐藏域中
                var inId = getNodeText(rows[0].getElementsByTagName("IN_ID").item(0));
                var inNo = getNodeText(rows[0].getElementsByTagName("IN_NO").item(0));
                $('#dia-IN_ID').val(inId);
                $('#dia-IN_NO').val(inNo);
            }
    //        $('#btn-save').hide();
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
    //弹出配件选择列表
    function openPartSelWin(){
        var options = {max: true,mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/otherStockInMngPartSel.jsp", "partSelWin", "配件信息查询", options,true);
    }
    
    //查询入库单备件
    function searchInBillPart() {
        var searchInBillPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/OtherStockInMngAction/searchInBillPart.ajax?IN_ID=" + $('#dia-IN_ID').val() + "&WAREHOUSE_ID="+$('#dia-WAREHOUSE_ID').val();
        var $f = $("#fm-searchInBillPart");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchInBillPartUrl, "", 1, sCondition, "tab-inBillPartList");
    }
    
    function callbackSearch(responseText,tabId,sParam) {
        switch(tabId) {
            case "tab-inBillPartList":
                 setStyle('tab-inBillPartList_content');
                 $('#tab-inBillPartList').find('th').eq(0).html('<div class="plus" style="height:20px;width:22px" onclick="openPartSelWin()"></div>');
            break;
        }
    }
    
    //表选字典回调方法
    function afterDicItemClick(id, $row, selIndex){
        var ret = true;
        //$row 行对象
        //selIndex 字典中第几行
        if(id == 'dia-WAREHOUSE_CODE'){
            $('#dia-WAREHOUSE_ID').val($row.attr('WAREHOUSE_ID'));
            $('#dia-WAREHOUSE_TYPE').val($row.attr('WAREHOUSE_TYPE'));
            $('#dia-WAREHOUSE_NAME').val($row.attr('WAREHOUSE_NAME'));
        }
        if(id.indexOf('WA_') == 0){
            var $obj = $('#'+id);
            var $tr = $obj.parent().parent().parent();
            var partId = $tr.attr('PART_ID');
            var areaId = $row.attr('A.AREA_ID');
            var dicSql = 'T#PT_BA_WAREHOUSE_PART_RL A,PT_BA_WAREHOUSE_POSITION B:A.POSITION_CODE:A.POSITION_NAME{A.POSITION_ID,A.POSITION_NAME}:1=1';
            dicSql+=' AND A.POSITION_ID = B.POSITION_ID';
            dicSql+=' AND B.AREA_ID = '+areaId;
            dicSql+=' AND A.PART_ID = '+partId+' ORDER BY B.POSITION_CODE';
            var $wp = $('#WP_'+id.substr(3));
            $wp.attr('code','');
            $wp.attr('value','');
            $wp.attr('POSITION_ID','');
            $wp.attr('src',dicSql);
            $obj.attr('AREA_ID',areaId);
        }
        if(id.indexOf('WP_')==0){
            var $obj = $('#'+id);
            var positionId = $row.attr('A.POSITION_ID');
            $obj.attr('POSITION_ID',positionId);
        }
        return ret;
    }
    
  //将库位字段渲染为文本框
    function createInputBox3(obj)
    {
        var $obj = $(obj);
        var $tr = $obj.parent();
        var partId = $tr.attr('PART_ID');
        var waitCount = $tr.attr('IN_AMOUNT');
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
    	        	s += "<input id='WPS_'"+partId+"_"+pId[i]+"' onblur='doWpBlur(this);' name='WAREHOUSE_POSITION_CODE_COUNT' value='"+waitCount+"' style='width:30px;padding-top:-5px;background:#dfe3e5;' /></div>";
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
        function doWpBlur(obj) {
            var $obj = $(obj);
            var $tr = $obj;
            while($tr.get(0).tagName != "TR")
                $tr = $tr.parent();
            var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
            if($obj.val().length > 0) {
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
                if($(this).val()) {
                    count = parseInt(count,10) + parseInt($(this).val(),10);
                }
            });
            if(count > (accCount - storCount)) {
                $checkbox.attr("checked",false);
                alertMsg.warn("入库数量不能大于出库数量+已入库数量!");
                return false;
            }
            if($obj.val() > 0){
                $checkbox.attr("checked",true);
            } else {
                //判断当前行是否库位数量都是空，如果是空，则取消checkbox选中
                var $div = $tr.find("div[name='WPS']");
                var f = true;
                $("input",$div).each(function(){
                    if($(this).val()) {
                        f = false;
                        return false;
                    }
                });
                if(f == true) {
                    $checkbox.attr("checked",false);
                }
            }
        }
        function isNumV(val) {
            var reg = /^[1-9]\d*$/;
            if(reg.test(val)) {
                return true;
            } else {
                return false;
            }
        }

        function isNumeric(val) {
            var reg = /^[1-9]\d*$/;
            if(reg.test(val)) {
                return true;
            } else {
                return false;
            }
        }
    //将备注字段渲染为文本框
    function createInputBox4(obj) {
        return '<input type="text" maxlength="1000" name="REMARK" value="'+obj.text()+'"/>';
    }
    
    function ctrlShow(obj){
        var $tr =$(obj).parent();
        if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
            return "";
        }else{
            return $(obj).text();
        }
    }
    
    function toAppendStr(obj){
        var $tr =$(obj).parent();
        return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
    }
    
    var $diaRow;
    function doDiaDelete(rowobj) {
        $diaRow = $(rowobj);
        var url = diaSaveAction+"/deleteInBillPart.ajax" + "?DETAIL_ID=" + $(rowobj).attr("DETAIL_ID");
        sendPost(url, "", "", diaDeleteCallBack, "true");
    }
    function diaDeleteCallBack(res) {
        try {
        	searchInBillPart();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function toSubStr(obj){
   	 var $obj = $(obj);
   	 var $tr = $obj.parent();
   	 var partName = $tr.attr('PART_NAME');
   	 if(partName.length>15){
   		 return "<div style='width:150px;'>"+partName.substring(0,15)+"...</div>";
   	 }else{
   		 return "<div style='width:150px;'>"+partName+"</div>";
   	 }
   }
</script>