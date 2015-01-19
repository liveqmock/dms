<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
    <div class="page">
    <div class="pageContent">
        <form method="post" id="dia-fm-htwh" class="editForm" style="width:100%;">
          <div align="left">
              <fieldset>
                        <table class="editTable" id="dia-tab-htxx">
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><input type="text" id="dia-returnNo-edit" datasource="ORDER_NO" readonly="readonly"/></td>
                                <td><label>申请日期：</label></td>
                                <td><input type="text" id="dia-applyDate-edit" name="dia-applyDate-edit" datasource="APPLY_DATE" readonly="readonly"/></td>
                                <td><label>购车日期：</label></td>
                                <td><input type="text" id="dia-sbBuyDate-edit" datasource="SB_BUY_DATE" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td><label>VIN：</label></td>
                                <td><input type="text" id="dia-sbVin-edit" datasource="SB_VIN" readonly="readonly"/></td>
                                <td><label>故障日期：</label></td>
                                <td><input type="text" id="dia-sbFaultDate-edit" name="dia-sbFaultDate-edit" datasource="SB_FAULT_DATE" readonly="readonly"/></td>
                                <td><label>故障分析：</label></td>
                                <td><input type="text" id="dia-sbFaultAnalyseName-edit" datasource="SB_FAULT_ANALYSE_NAME" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td><label>车型代码：</label></td>
                                <td><input type="text" id="dia-sbModelsCode-edit" name="dia-sbModelsCode-edit" datasource="SB_MODELS_CODE" readonly="readonly"/></td>
                                <td><label>联系人：</label></td>
                                <td><input type="text" id="dia-linkMan-edit" datasource="LINK_MAN" readonly="readonly"/></td>
                                <td><label>电话：</label></td>
                                <td><input type="text" id="dia-phone-edit" name="dia-phone-edit" datasource="PHONE" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td><label>收货地址：</label></td>
                                <td><input type="text" id="dia-deliveryAddr-edit" datasource="DELIVERY_ADDR" readonly="readonly"/></td>
                                <td><label>备注：</label></td>
                                <td><input type="text" id="dia-remarks-edit" datasource="REMARKS"  readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td><label>渠道代码(服)：</label></td>
                                <td><input type="text" id="dia-SERVER_ORG_CODE" datasource="SERVER_ORG_CODE" readonly="readonly"/></td>
                                <td><label>渠道名称(服)：</label></td>
                                <td><input type="text" id="dia-SERVER_ORG_NAME" datasource="SERVER_ORG_NAME"  readonly="readonly"/></td>
                            </tr>
                        </table>
              </fieldset>
            </div>
        </form>
        <form method="post" id="diaPartListFm">
            <table id="diaPartListTab"></table>
        </form>
        <div id="page_edit">
            <table style="display:none;width:99%;" id="dia-tab-edit" name="tablist" layoutH="100" ref="page_edit" refQuery="diaPartListTab">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="DTL_ID" refer="myDtlId" style="display:none"></th>
                        <th fieldname="PART_CODE" refer="myTypeCode">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="UNIT" colwidth="60">计量单位</th>
                        <th fieldname="" refer="appendPack_Unit">最小包装</th>
                        <th fieldname="UNIT_PRICE" refer="formatAmount" align="right">经销商价(元)</th>
                        <th fieldname="SUPPLIER_NAME">供应商</th>
                        <th fieldname="ORDER_COUNT">订购数量</th>
                        <th fieldname="AVAILABLE_AMOUNT">库存可用数量</th>
                        <th fieldname="AUDIT_COUNT" refer="myInput">审核数量</th>
                        <th fieldname="PART_STATUS">配件状态</th>
                        <th fieldname="MONTH_PART_COUNT">本月提报数量</th>
                        <th fieldname="DEALER_AMOUNT">配送中心库存</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
             </table>
         </div>
        <form action="post" id="searchForm">
                <input type="hidden" id="orderId" name="orderId"/>
                <input type="hidden" id="dia-ORG_NAME" name="dia-ORG_NAME"/>
                <input type="hidden" id="dia-ORDER_NO" name="dia-ORDER_NO"/>
                <input type="hidden" id="dia-ORDER_AMOUNT" name="dia-ORDER_AMOUNT"/>
                <input type="hidden" id="dia-APPLY_DATE" name="dia-APPLY_DATE"/>
        </form>
         <form  method="post" id="dia-fm-edit-orderId">
             <input type="hidden" id="dia-orderId-edit" name="dia-orderId-edit" datasource=""/>
         </form>
         <form  method="post" id="dia-fm-edit"  class="editForm" >
             <input type="hidden" id="orgId" datasource=""/>
             <input type="hidden" id="dtlIds" datasource="DTLIDS"/>
             <input type="hidden" id="auditCounts" datasource="AUDITCOUNTS"/>
             <input type="hidden" id="diaIfDelay" name="diaIfDelay" datasource="IF_DELAY_ORDER"/>
             <div align="left">
                 <fieldset>
                     <table class="editTable">
                         <tr>
                             <td><label>是否免运费：</label></td>
                            <td>
                                <input type="text" id="ifTrans" name="ifTrans" kind="dic" src="SF" datasource="IF_TRANS" filtercode="" datatype="0,is_null,30" operation="="/>
                            </td>
                         </tr>
                         <tr>
                             <td><label>审核意见：</label></td>
                             <td>
                                 <input type="text" id="dia-checkRemarks-edit" name="dia-checkRemarks-edit"  datasource="CHECK_REMARKS"  style="width:800px;height:40px"/>
                                 <input type="hidden" id="dia-orderId-remarks-edit" datasource="ORDER_ID" />
                                 <input type="hidden" id="dia-orderAmount-edit" datasource="ORDER_AMOUNT" />
                             </td>
                         </tr>
                     </table>
                 </fieldset>
             </div>
         </form>
         <div class="formBar">
             <ul>
                 <li><div class="button"><div class="buttonContent"><button type="button" id="dia-doPrint">打印订单明细</button></div></div></li>
                 <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-doCheckPass">审核通过</button></div></div></li>
                 <li id="checkReturn"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-doCheckReturn">审核驳回</button></div></div></li>
                 <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
             </ul>
         </div>
    </div>
    </div>
<script type="text/javascript">
    var checkCount = 0;
    var doCheckPassUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/claimCyclOrderReportMngAction";
    $(function(){

//         $("#dia-tab-edit").attr("layoutH",document.documentElement.clientHeight-320);
        var selectedRows = $("#tab-index").getSelectedRows();
        setEditValue("dia-tab-htxx",selectedRows[0].attr("rowdata"));
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        
        // 驳回按钮隐藏
        if (selectedRows[0].attr("DELAY_COUNT") != '0') {
            $("#checkReturn").hide();
        }
        $("#ifTrans").val("是");
        $("#ifTrans").attr("code",<%=DicConstant.SF_01%>);

        $("#orgId").val(selectedRows[0].attr("ORG_ID"));
        // 延期订单flag
        var delayFlag = false;
        // 是否是延期订单
        if(<%=DicConstant.SF_01%> == selectedRows[0].attr("IF_DELAY_ORDER")) {
            delayFlag = true;
            $("#checkReturn").hide();
        }
        // 销售订单ID
        $("#dia-orderId-remarks-edit").val(selectedRows[0].attr("ORDER_ID"));
        $("#dia-orderId-edit").val(selectedRows[0].attr("ORDER_ID"));

        // 订单总金额
        $("#dia-orderAmount-edit").val(selectedRows[0].attr("ORDER_AMOUNT"));

        var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/claimCyclOrderReportMngAction";
        var orderPartSearchUrl = diaUrl+"/orderPartSearch.ajax?orderId="+selectedRows[0].attr("ORDER_ID");
        var $f = $("#dia-fm-edit-orderId");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, orderPartSearchUrl, "", 1, sCondition, "dia-tab-edit");

        // 审核通过按钮绑定
        $("#dia-doCheckPass").click(function(){
            // 延期标识
            var flag = false;
            // 审核验证标识
            var checkFlag = false;
            var checkFlag1 = false;
            var checkFlag2 = false;
            var dtlIds = "";
            // 审核数量
            var auditCounts="";
            if($("#dia-tab-edit_content").size()==0){
                $("#dia-tab-edit").find("tbody").find("tr").each(function(){
                    var dtlId=$(this).find("td").eq(2).find("input:first").val();
                    var orderCount=$(this).find("td").eq(9).text();
                    var stockCount=$(this).find("td").eq(10).text();
                    var auditCount=$(this).find("td").eq(11).find("input:first").val();
                    if(isAmount(auditCount)==false) {
                        checkFlag1 = true;
                    }
                    if(dtlIds.length==0) {
                        dtlIds = dtlId;
                    } else {
                        dtlIds = dtlIds+","+dtlId;
                    }
                    // 审核数量
                    if(auditCounts.length==0) {
                        auditCounts = auditCount;
                    } else {
                        auditCounts = auditCounts+","+auditCount;
                    }
                    if(parseInt(auditCount)<parseInt(orderCount)){
                        flag = true;
                    }
                    if(parseInt(auditCount) > parseInt(stockCount)) {
//                         alertMsg.warn("审核数量不能大于可用库存.");
                        checkFlag2 = true;
                        return false;
                    }
                    if (parseInt(auditCount)>parseInt(orderCount)) {
                        checkFlag = true;
                        return false;
                    }
                });
            }else{
                $("#dia-tab-edit_content").find("tr").each(function(){
                    var dtlId=$(this).find("td").eq(2).find("input:first").val();
                    var orderCount=$(this).find("td").eq(9).text();
                    var stockCount=$(this).find("td").eq(10).text();
                    var auditCount=$(this).find("td").eq(11).find("input:first").val();
                    if(!isAmount(auditCount)) {
                    	checkFlag1 = true;
                    }
                    if(dtlIds.length==0) {
                        dtlIds = dtlId;
                    } else {
                        dtlIds = dtlIds+","+dtlId;
                    }
                    // 审核数量
                    if(auditCounts.length==0) {
                        auditCounts = auditCount;
                    } else {
                        auditCounts = auditCounts+","+auditCount;
                    }
                    if(parseInt(auditCount)<parseInt(orderCount)){
                        flag = true;
                    }
                    if(parseInt(auditCount) > parseInt(stockCount)) {
//                         alertMsg.warn("审核数量不能大于可用库存.");
                        checkFlag2 = true;
                        return false;
                    }
                    if (parseInt(auditCount)>parseInt(orderCount)) {
                        checkFlag = true;
                        return false;
                    }
                });
            }
            if(checkFlag1) {
                alertMsg.warn("请正确输入审核数量.");
                return false;
            }
            if (checkFlag2) {
                alertMsg.warn("审核数量不能大于可用库存.");
                return false;
            }
            if (checkFlag) {
                alertMsg.warn("审核数量不能大于订购数量.");
                return false;
            }
            var auditCountses = auditCounts.split(',');
            var count = 0;
            for(var i=0;i<auditCountses.length;i++){
                count = count + parseInt(auditCountses[i]);
            }
            if (delayFlag) {
                if (count == 0 || isNaN(count)) {
                    alertMsg.warn("审核数量合计不能为0！");
                    return false;
                }
            }
            // 审核数量
            checkCount = count;
            $("#dtlIds").val(dtlIds);
            // 审核数量
            $("#auditCounts").val(auditCounts);
            var orderType = $("#diaOrderType1").val();
            if(flag) {
                if(orderType!="<%=DicConstant.DDLX_05%>"){
                    alertMsg.confirm("有部分配件库存不满足，是否生成延期订单？",{okCall:doApproveOk1,cancelCall:doApproveOk2});
                }
            } else {
                var $f = $("#dia-fm-edit");
                var sCondition = {};
                sCondition = $f.combined(1) || {};
                var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=1&orgId="+$("#orgId").val();
                doNormalSubmit($f, passUrl, "dia-doCheckPass", sCondition, doCheckPassCallBack);
            }
        });

        // 审核驳回按钮绑定
        $("#dia-doCheckReturn").click(function(){
            if (!$("#dia-checkRemarks-edit").val()) {
                alertMsg.warn("请输入审核意见！");
                return false;
            }
            var doCheckReturnUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/claimCyclOrderReportMngAction/partOrderCheckApproveBack.ajax";
            var $f = $("#dia-fm-edit");
            var sCondition = {};
            if (submitForm($f) == false) {
                return false;
            }
            sCondition = $f.combined(1) || {};
            doNormalSubmit($f, doCheckReturnUrl, "dia-doCheckReturn", sCondition, doCheckReturnCallBack);
        });

        // 打印订单明细按钮绑定
        $("#dia-doPrint").click(function(){
             
            var selectedRows = $("#tab-index").getSelectedRows();
            //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
           /* $("#orderId").val(selectedRows[0].attr("ORDER_ID"));
            $("#dia-ORDER_NO").val(selectedRows[0].attr("ORDER_NO"));
            $("#dia-ORG_NAME").val(selectedRows[0].attr("ORG_NAME"));
            $("#dia-ORDER_AMOUNT").val(selectedRows[0].attr("ORDER_AMOUNT"));
            $("#dia-APPLY_DATE").val(selectedRows[0].attr("APPLY_DATE_sv"));
            window.open(webApps + "/jsp/dms/oem/part/sales/orderMng/printOrderDtl.jsp"); */
        	var queryUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/claimCyclOrderReportMngAction/printPdf.do?ORDER_ID="+selectedRows[0].attr("ORDER_ID");
            window.open(queryUrl);
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    // 生成延期订单
    function doApproveOk1() {
        var $f = $("#dia-fm-edit");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=2&orgId="+$("#orgId").val();
        doNormalSubmit($f, passUrl, "dia-approve1", sCondition, doCheckPassCallBack);
    }

    // 不生成延期订单
    function doApproveOk2() {
    	if(checkCount==0) {
    		alertMsg.warn("审核总数量不能为0.");
            return false;
    	}
        var $f = $("#dia-fm-edit");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=1&orgId="+$("#orgId").val();
        doNormalSubmit($f, passUrl, "dia-approve1", sCondition, doCheckPassCallBack);
    }

    function myDtlId(obj){
        var $tr =$(obj).parent();
        return "<input type='text' id='"+$tr.attr("DTL_ID")+"' name='dtlId' value='"+$tr.attr("DTL_ID")+"'>";
    }

     //输入框
    function myTypeCode(obj){
        var $tr =$(obj).parent();
        var typeCode = $tr.attr("TYPE_CODE");
        // 其他订单
        if(typeCode=="THZF"){
             $tr.find("td").eq(3).attr("style","color:red");
        }
    }
  
    //输入框
    function myInput(obj){
        var $tr =$(obj).parent();
        var orderCount = $tr.attr("ORDER_COUNT");
        var availableAmount= $tr.attr("AVAILABLE_AMOUNT");
        // 其他订单
        if(parseInt(availableAmount)<parseInt(orderCount)){
            $tr.attr("style","background-color:#f09393");
            return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+availableAmount+"' datatype='0,is_null,10'>";
        } else {
            return "<input type='text' style='width:60px;' id='auditCount"+$tr.attr("DTL_ID")+"' onblur='checkAuditCount(this)' name='auditCount' value='"+orderCount+"' datatype='0,is_null,10'>";
        }
    }

    //输入框校验
    function checkAuditCount(obj){
        var $tr =$(obj).parent().parent();
        // 订购数量
        var orderCount = $tr.attr("ORDER_COUNT");
        // 可用库存
        var availableAmount= $tr.attr("AVAILABLE_AMOUNT");
        var auditCount = $(obj).val();
        if(auditCount.length==0){
            alertMsg.warn("请输入审核数量！");
            return false;
        }  
        if($(obj).val() && !isAmount($(obj).val())) {
            alertMsg.warn("请正确输入审核数量！");
            return false;
        }
        if(parseInt(auditCount) > parseInt(orderCount)) {
            alertMsg.warn("审核数量不能大于订购数量.");
            return false;
        }
        if(parseInt(auditCount) > parseInt(availableAmount)) {
            alertMsg.warn("审核数量不能大于可用库存.");
            return false;
        }
    }

    //校验数量
    function isAmount(val) {
        var reg =/^(0|\+?[1-9][0-9]*)$/;
        if(reg.test(val))
        {
            return true;
        }else
        {
            return false;
        }
    }

    // 审核通过回调函数
    function doCheckPassCallBack() {

        // 查询方法
        //searchDirectBusinessOrder();
        if($row1) 
			$("#tab-index").removeResult($row1);
        $.pdialog.closeCurrent();
        return false;
    }

    // 审核驳回回调函数
    function doCheckReturnCallBack(res) {

        // 查询方法
//         searchDirectBusinessOrder();
        if($row1) 
			$("#tab-index").removeResult($row1);
        $.pdialog.closeCurrent();
        return false;
    }

    // 最小包装数+最小包装单位(例:10/包)
    function appendPack_Unit(obj) {
        var $tr = $(obj).parent();
        var minPack = $tr.attr("MIN_PACK");
        var minUnit = $tr.attr("MIN_UNIT_sv");
        if (minPack==null) {
            minPack="";
        }
        if (minUnit == null) {
            minUnit="";
        }
        return minPack + '/' + minUnit;
    }
    // 数据字典回调函数
    function afterDicItemClick(id,$row){
        if(id=="dia-warehouseCode"){
            $("#dia-warehouseId").val($row.attr("WAREHOUSE_ID"));
            $("#dia-warehouseName").val($row.attr("WAREHOUSE_NAME"));
        }
        return true;
    }
</script>
</div>
