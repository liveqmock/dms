<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String orgId = user.getOrgId();
    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100200");
    String paraValue1 = userPara.getParavalue1();
    String paraValue2 = userPara.getParavalue2();
    String paraValue3 = userPara.getParavalue3();
%>
<div id="dia-layout">
	<div class="tabs" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>配件清单</span></a></li>
                    <li id="orderPay"><a href="javascript:void(0);"><span>付款订购</span></a></li>
                </ul>
            </div>
        </div>
    <div class="tabsContent">

    <!-- 配件清单 -->
    <div class="pageContent">
        <form method="post" id="dia-fm-htwh" class="editForm" style="width:100%;">
          <div align="left">
              <fieldset>
                  <table class="editTable" id="dia-tab-htxx">
                      <tr><td><label>订单编号：</label></td>
                          <td><input type="text" id="dia-returnNo-edit" datasource="ORDER_NO" readonly="readonly"/></td>
                          <td><label>申请日期：</label></td>
                          <td><input type="text" id="dia-applyDate-edit" name="dia-applyDate-edit" datasource="APPLY_DATE" readonly="readonly"/></td>
                      </tr>
                  </table>
              </fieldset>
            </div>
        </form>
        <div id="page_edit">
            <table style="display:none;width:97%;" id="dia-tab-edit" name="tablist" ref="page_edit">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="DTL_ID" refer="myDtlId" style="display:none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="UNIT" colwidth="60">计量单位</th>
                        <th fieldname="" refer="appendPack_Unit">最小包装</th>
                        <th fieldname="UNIT_PRICE" refer="formatAmount" align="right">经销商价(元)</th>
                        <th fieldname="SUPPLIER_NAME">供应商</th>
                        <th fieldname="ORDER_COUNT">订购数量</th>
<!--                         <th fieldname="AVAILABLE_AMOUNT">库存可用数量</th> -->
<!--                         <th fieldname="AUDIT_COUNT" refer="myInput">审核数量</th> -->
                    </tr>
                </thead>
                <tbody>
                </tbody>
             </table>
         </div>
         <form  method="post" id="dia-fm-edit-orderId">
             <input type="hidden" id="dia-orderId-edit" name="dia-orderId-edit" datasource=""/>
         </form>
         <form  method="post" id="dia-fm-edit"  class="editForm" >
             <input type="hidden" id="accountId" datasource="ACCOUNT_ID"/>
             <input type="hidden" id="payAmount" datasource="PAY_AMOUNT"/>
             <input type="hidden" id="accountType" datasource="ACCOUNT_TYPE"/>
             <input type="hidden" id="dtlIds" datasource="DTLIDS"/>
             <input type="hidden" id="auditCounts" datasource="AUDITCOUNTS"/>
             <input type="hidden" id="diaIfDelay" name="diaIfDelay" datasource="IF_DELAY_ORDER"/>
             <div align="left">
                 <fieldset>
                     <table class="editTable">
                         <tr>
                             <td><label>供货库：</label></td>
                             <td><input type="text" id="dia-warehouseCode" name="dia-warehouseCode" datasource="WAREHOUSE_CODE" datatype="0,is_null,10" isreload="true"
                                           src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01 %> AND WAREHOUSE_TYPE =100101 ORDER BY WAREHOUSE_CODE" kind="dic" />
                                    <input type="hidden" id="dia-warehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="hidden" id="dia-warehouseName" datasource="WAREHOUSE_NAME"/>
                             </td>
                         </tr>
                         <tr>
                             <td><label>审核意见：</label></td>
                             <td>
                                 <input type="text" id="dia-remarks-edit" name="dia-remarks-edit" datatype="1,is_null,1000" datasource="CHECK_REMARKS" style="width:800px;height:40px"/>
                                 <input type="hidden" id="dia-orderId-remarks-edit" datasource="ORDER_ID"/>
                                 <input type="hidden" id="dia-orderNo-remarks-edit" datasource="ORDER_NO"/>
                                 <input type="hidden" id="dia-orderAmount-edit" datasource="ORDER_AMOUNT"/>
                             </td>
                         </tr>
                     </table>
                 </fieldset>
             </div>
         </form>
         <div class="formBar">
             <ul>
                 <li id="button2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
                 <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
             </ul>
         </div>
    </div>

    <!-- 付款订购 -->
    <div>
        <form method="post" id="diaFundsFm" class="editForm" style="width:99%;">
            <div align="left">
                <table class="editTable" id="diaOrderFundsInfo">
                </table>
                <div style="margin-left:65px;margin-bottom:7px;font-size:14px;">
                    <table style="width:90%;">
                        <tr>
                            <td style="width:30%;">订单审核通过总金额：<span id="divOrderAmount2" style="font-size:14px;color:red;"></span></td>
                            <td style="width:30%;">已付总金额：<span id="divOrderAmount3" style="font-size:14px;color:red;"></span></td>
                            <td style="width:30%;">应付总金额：<span id="divOrderAmount4" style="font-size:14px;color:red;"></span></td>
                        </tr>
                    </table>
                </div>
            </div>
        </form>
        <form method="post" id="diaOrderFundsFm" class="editForm">
            <table class="searchContent" id="tabOrderFundsSearch"></table>
        </form>
        <form method="post" id="fm-orderFunds" style="display:">
            <input type="hidden" id="accountId" datasource="ACCOUNT_ID"/>
            <input type="hidden" id="accountType" datasource="ACCOUNT_TYPE"/>
            <input type="hidden" id="payAmount" datasource="PAY_AMOUNT"/>
            <input type="hidden" id="payOrderId" datasource="ORDER_ID"/>
            <input type="hidden" id="payId" datasource="PAY_ID"/>
        </form>
        <div id="div-orderFundsList" style="overflow:hidden;text-align:center;">
            <table style="display:none;width:80%;" id="tab-orderFundsList" class="dlist" name="tablist" multivals="div-selectedPart" ref="div-orderFundsList" refQuery="tabOrderFundsSearch">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" style="display:"></th>
                            <th fieldname="ACCOUNT_TYPE" >账户类型</th>
                            <th fieldname="AVAILABLE_AMOUNT" align="right">可用余额(元)</th>
                            <th fieldname="PAY_AMOUNT" refer="myInput1" align="right">本次使用金额(元)</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
        <fieldset id="field-orderFundsList" style="display:none">
         <legend align="left">&nbsp;[已选定账户]</legend>
         <div id="div-selectedPart">
             <table style="width:100%;">
                 <tr>
                     <td>
                         <textarea style="width:80%;height:10px;display:none" id="val0" column="1" name="multivals" readOnly></textarea>
                         <textarea style="width:80%;height:10px;display:none" id="val1" name="multivals" readOnly></textarea>
                         <textarea style="width:80%;height:10px;display:none" id="val2" name="multivals" readOnly></textarea>
                         <textarea style="width:80%;height:30px;display:" id="val3" name="multivals" readOnly></textarea>
                     </td>
                 </tr>
             </table>
         </div>
        </fieldset>
	    <div class="formBar">
	        <ul>
	            <!-- <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btnFundsSave">保存付款信息</button></div></div></li> -->
	<!--                         <li id="dia-report-btn"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li> -->
	            <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
	            <li><div class="button"><div class="buttonContent"><button type="button" id="dia-doCheckPass">审核通过</button></div></div></li>
	            <li id="checkReturn"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-doCheckReturn">审核驳回</button></div></div></li>
	            <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
	        </ul>
	    </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    var checkCount = 0;
    var doCheckPassUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction";
    $(function(){

        $("#dia-tab-edit").attr("layoutH",document.documentElement.clientHeight-270);
        var selectedRows = $("#tab-index").getSelectedRows();
        setEditValue("dia-tab-htxx",selectedRows[0].attr("rowdata"));
        //selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
        // 销售订单ID
        $("#dia-orderId-remarks-edit").val(selectedRows[0].attr("ORDER_ID"));
        $("#dia-orderNo-remarks-edit").val(selectedRows[0].attr("ORDER_NO"));
        $("#dia-orderId-edit").val(selectedRows[0].attr("ORDER_ID"));

        // 延期订单flag
        var delayFlag = false;
        // 是否是延期订单
        if(<%=DicConstant.SF_01%> == selectedRows[0].attr("IF_DELAY_ORDER")) {
            delayFlag = true;
            $("#checkReturn").hide();
        }

        // 订单总金额
        $("#dia-orderAmount-edit").val(selectedRows[0].attr("ORDER_AMOUNT"));

        var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction";
        var orderPartSearchUrl = diaUrl+"/orderPartSearch.ajax?orderId="+selectedRows[0].attr("ORDER_ID");
        var $f = $("#dia-fm-edit-orderId");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, orderPartSearchUrl, "", 1, sCondition, "dia-tab-edit");

        $("#dia-warehouseId").val("<%=paraValue1%>");
        $("#dia-warehouseCode").attr("code","<%=paraValue2%>");
        $("#dia-warehouseCode").val("<%=paraValue3%>");
        $("#dia-warehouseName").val("<%=paraValue3%>");
        // 审核通过按钮绑定
        $("#dia-doCheckPass").click(function(){

        	if($("#tab-orderFundsList_content").find("tr").size()==0){
                alertMsg.warn("请添加付款明细.");
                return false;
            }

        	if ($("#divOrderAmount2").attr("code")!=$("#divOrderAmount3").attr("code")) {
//         		alertMsg.warn("审核通过金额与付款总金额不一致.");
                alertMsg.warn("订单金额与付款总金额不一致.");
                return false;
        	}
        	$("#payAmount").val($("#val0").val());
        	$("#accountId").val($("#val1").val());
        	$("#accountType").val($("#val2").val());
        	
        	var $f = $("#dia-fm-edit");
            var sCondition = {};
            sCondition = $f.combined(1) || {};
            var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=1";
            doNormalSubmit($f, passUrl, "dia-doCheckPass", sCondition, doCheckPassCallBack);

//         	// 延期标识
//             var flag = false;
//             // 审核验证标识
//             var checkFlag = false;
//             var checkFlag1 = false;
//             var checkFlag2 = false;
//             var dtlIds = "";
//             // 审核数量
//             var auditCounts="";
//             if($("#dia-tab-edit_content").size()==0){
//                 $("#dia-tab-edit").find("tbody").find("tr").each(function(){
//                     var dtlId=$(this).find("td").eq(2).find("input:first").val();
//                     var orderCount=$(this).find("td").eq(9).text();
//                     var stockCount=$(this).find("td").eq(10).text();
//                     var auditCount=$(this).find("td").eq(11).find("input:first").val();
//                     if(!isAmount(auditCount)) {
//                     	checkFlag1 = true;
//                     }
//                     if(dtlIds.length==0) {
//                         dtlIds = dtlId;
//                     } else {
//                         dtlIds = dtlIds+","+dtlId;
//                     }
//                     // 审核数量
//                     if(auditCounts.length==0) {
//                         auditCounts = auditCount;
//                     } else {
//                         auditCounts = auditCounts+","+auditCount;
//                     }
//                     if(parseInt(auditCount)<parseInt(orderCount)){
//                         flag = true;
//                     }
//                     if(parseInt(auditCount) > parseInt(stockCount)) {
// //                         alertMsg.warn("审核数量不能大于可用库存.");
//                         checkFlag2 = true;
//                         return false;
//                     }
//                     if (parseInt(auditCount)>parseInt(orderCount)) {
//                         checkFlag = true;
//                         return false;
//                     }
//                 });
//             }else{
//                 $("#dia-tab-edit_content").find("tr").each(function(){
//                     var dtlId=$(this).find("td").eq(2).find("input:first").val();
//                     var orderCount=$(this).find("td").eq(9).text();
//                     var stockCount=$(this).find("td").eq(10).text();
//                     var auditCount=$(this).find("td").eq(11).find("input:first").val();
//                     if(!isAmount(auditCount)) {
//                     	checkFlag1 = true;
//                     }
//                     if(dtlIds.length==0) {
//                         dtlIds = dtlId;
//                     } else {
//                         dtlIds = dtlIds+","+dtlId;
//                     }
//                     // 审核数量
//                     if(auditCounts.length==0) {
//                         auditCounts = auditCount;
//                     } else {
//                         auditCounts = auditCounts+","+auditCount;
//                     }
//                     if(parseInt(auditCount)<parseInt(orderCount)){
//                         flag = true;
//                     }
//                     if(parseInt(auditCount) > parseInt(stockCount)) {
// //                         alertMsg.warn("审核数量不能大于可用库存.");
//                         checkFlag2 = true;
//                         return false;
//                     }
//                     if (parseInt(auditCount)>parseInt(orderCount)) {
//                         checkFlag = true;
//                         return false;
//                     }
//                 });
//             }
//             if(checkFlag1) {
//                 alertMsg.warn("请正确输入审核数量.");
//                 return false;
//             }
//             if (checkFlag2) {
//                 alertMsg.warn("审核数量不能大于可用库存.");
//                 return false;
//             }
//             if (checkFlag) {
//                 alertMsg.warn("审核数量不能大于订购数量.");
//                 return false;
//             }
//             var auditCountses = auditCounts.split(',');
//             var count = 0;
//             for(var i=0;i<auditCountses.length;i++){
//                 count = count + parseInt(auditCountses[i]);
//             }
//             if (delayFlag) {
//                 if (count == 0 || isNaN(count)) {
//                     alertMsg.warn("审核数量合计不能为0！");
//                     return false;
//                 }
//             }
//             // 审核数量
//             checkCount = count;
//             $("#dtlIds").val(dtlIds);
//             // 审核数量
//             $("#auditCounts").val(auditCounts);
//             var orderType = $("#diaOrderType1").val();
//             if(flag) {
<%--                 if(orderType!="<%=DicConstant.DDLX_05%>"){ --%>
//                     alertMsg.confirm("有部分配件库存不满足，是否生成延期订单？",{okCall:doApproveOk1,cancelCall:doApproveOk2});
//                 }
//             } else {
//             	var $f = $("#dia-fm-edit");
//                 var sCondition = {};
//                 sCondition = $f.combined(1) || {};
//                 var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=1";
//                 doNormalSubmit($f, passUrl, "dia-doCheckPass", sCondition, doCheckPassCallBack);
//             }

<%--             var doCheckPassUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction/partOrderCheckPass.ajax"; --%>
//             var $f = $("#dia-fm-edit");
//             var sCondition = {};
//             if (submitForm($f) == false) {
//                 return false;
//             }
//             sCondition = $f.combined(1) || {};
//             doNormalSubmit($f, doCheckPassUrl, "dia-doCheckPass", sCondition, doCheckPassCallBack);
        });

        // 下一步按钮绑定
        $("button[name='btn-next']").bind("click", function () {
            var $tabs = $("#dia-tabs");
            doNextTab($tabs);
        });

        // 上一步按钮绑定
        $("button[name='btn-pre']").bind("click", function () {
            var $tabs = $("#dia-tabs");
            doPreTab($tabs);
        });

        // 审核驳回按钮绑定
        $("#dia-doCheckReturn").click(function(){
            if (!$("#dia-remarks-edit").val()) {
                alertMsg.warn("请输入审核意见！");
                return false;
            }
            var doCheckReturnUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction/partOrderCheckApproveBack.ajax";
            var $f = $("#dia-fm-edit");
            var sCondition = {};
            if (submitForm($f) == false) {
                return false;
            }
            sCondition = $f.combined(1) || {};
            doNormalSubmit($f, doCheckReturnUrl, "dia-doCheckReturn", sCondition, doCheckReturnCallBack);
        });

        // 关闭按钮绑定
        $("button.close").click(function(){
            $.pdialog.closeCurrent();
            return false;
        });
    });

    function doNextTab($tabs) {
        $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
        (function (ci) {
            switch (parseInt(ci)) {
                case 1:
                	if($("#tab-orderFundsList_content").find("tr").size()==0){
                	    orderFundsSearch();
                    }
                    break;
                default:
                    break;
            }
        })(parseInt($tabs.attr("currentIndex")));
     }

     function doPreTab($tabs) {
          $tabs.switchTab($tabs.attr("currentIndex") - 1);
     }

     function orderFundsSearch() {
    	 var orderCheckAmount = "";
//     	 $("#dia-tab-edit").find("tbody").find("tr").each(function(){
//              var salePrice=$(this).find("td").eq(7).text().replace(",","");
//              var auditCount=$(this).find("td").eq(11).find("input:first").val();
//              orderCheckAmount=(salePrice*auditCount)*1+orderCheckAmount*1;
//          });
    	 $("#field-orderFundsList").show();
    	 $("#divOrderAmount2").attr("code",$("#dia-orderAmount-edit").val());
    	 $("#divOrderAmount3").attr("code","0");
    	 $("#divOrderAmount4").attr("code",$("#dia-orderAmount-edit").val());
    	 $("#divOrderAmount2").html($("#dia-orderAmount-edit").val()+"元");
    	 $("#divOrderAmount3").html(0+"元");
    	 $("#divOrderAmount4").html($("#dia-orderAmount-edit").val()+"元");
    	 // 账户类型设置
        // $("#tab-orderFundsList").find("th").eq(1).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>)");
    	 var orderFundsSearchUrl = doCheckPassUrl+"/accountSearch1.ajax";
         var $f = $("#diaOrderFundsFm");
         var sCondition = {};
         sCondition = $f.combined() || {};
         doFormSubmit($f, orderFundsSearchUrl, "", 1, sCondition, "tab-orderFundsList");
     }
    // 生成延期订单
    function doApproveOk1() {
    	var $f = $("#dia-fm-edit");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=2";
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
        var passUrl = doCheckPassUrl + "/partOrderCheckPass.ajax?flag=1";
        doNormalSubmit($f, passUrl, "dia-approve1", sCondition, doCheckPassCallBack);
    }

    // 小计
    function acount (obj) {
        var $tr = $(obj).parent();
        return "<div style='text-align:right;'>"+amountFormatNew($tr.attr("UNIT_PRICE")*$tr.attr("ORDER_COUNT"))+"</div>";
    }

    function myDtlId(obj){
        var $tr =$(obj).parent();
        return "<input type='text' id='"+$tr.attr("DTL_ID")+"' name='dtlId' value='"+$tr.attr("DTL_ID")+"'>";
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
        searchDirectBusinessOrder();
        $.pdialog.closeCurrent();
        return false;
    }

    // 审核驳回回调函数
    function doCheckReturnCallBack(res) {

        // 查询方法
        searchDirectBusinessOrder();
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
  
    //行记录上校验金额
    function checkAmount(input){
        var aMountSum = 0;
        var $tr = $(input);
        while($tr.get(0).tagName != "TR")
            $tr = $tr.parent();
        $(input).val($(input).val().replace(",",""));
        var v = $(input).val();
        var vv = $tr.find("td").eq(3).text();
        //判断本次使用不能大于账户可用金额
        if(parseFloat(v,10) > parseFloat(vv,10))
        {
            alertMsg.warn("本次使用金额不能大于可用余额.");
            return false;
        }
        //判断本次使用合计金额不能大于订单总金额
        var $tab = $tr;
        while($tab.get(0).tagName != "TABLE")
            $tab = $tab.parent();
        $("tr",$tab).each(function(){
            var $tr = $(this);
            var $td4 = $tr.find("td").eq(4);
            if($td4.find("input").size() > 0)
                aMountSum = parseFloat(aMountSum,10)+parseFloat($td4.find("input:first").val(),10);
            else
                aMountSum = parseFloat(aMountSum,10)+parseFloat($td4.text(),10);
        });
        var orderAmount = $("#diaOrderAmount").val();
        aMountSum = aMountSum.toFixed(2);
        if(orderAmount)
        {
            if(parseFloat(aMountSum,10)> parseFloat(orderAmount,10))
            {
                alertMsg.warn("使用金额已大于订单总金额.");
                return false;
            }
        }
        var $input1 = $(input);
        var $tr1 = $input1.parent().parent().parent();
        var checkObj = $("input:first",$tr1.find("td").eq(1));
        var s = $input1.val();
        if(s) {
            checkObj.attr("checked", true);
        }
        doSelectedBefore($tr1,checkObj,1);
    }
    
    //订购数量输入框与文本切换
    function doSelectedBefore($tr,$obj,type) {
        var $input = $tr.find("td").eq(4).find("input:first");
        var s = "";
        if($input && $input.get(0).tagName=="INPUT") {
            s = $input.val();
        } else {
            s = $tr.find("td").eq(10).text();
        }
        doCheckbox($obj.get(0));
    }
    //本次使用金额
    function myInput1(obj) {
        return "<input type='text' style='width:200px;' name='myCount' onblur='checkAmount(this)' maxlength='12' dataType='1,is_double,12' decimal='2'/>";
    }
    
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var s = "";
        if($tr.find("td").eq(4).find("input:first").size()>0)
            s = $tr.find("td").eq(4).find("input:first").val();
        else
            s = $tr.find("td").eq(4).text();
        if (s=="0"||s==""){
            alertMsg.warn("请正确输入金额.");
            $(checkbox).attr("checked",false);
            return false;
        }
//         var assembly = $tr.attr("BELONG_ASSEMBLY");
//         if (!assembly&&assembly!="") {
//             // 所属总成
//             var assemblys = $('#val9').val().split(",");
//             for (var i =0;i<assemblys.length();i++) {
//                 if (assemblys[i] == assembly) {
//                     alertMsg.warn("总成件只能选一种!");
//                     $(checkbox).attr("checked",false);
//                     return false;
//                 }
//             }
//         }
        var arr = [];
        arr.push(s);
        arr.push($tr.attr("ACCOUNT_ID"));
        arr.push($tr.attr("ACCOUNT_TYPE"));
        arr.push($tr.attr("ACCOUNT_TYPE_sv"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart'));
        var orderCheckAmount = "0";
    	$("#tab-orderFundsList_content").find("tr").each(function(){
    		var $tr = $(this);
        	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        	if($checkbox.is(":checked")) {
                if ($tr.find("td").eq(4).find("input:first").val()) {
        		    orderCheckAmount = $tr.find("td").eq(4).find("input:first").val()*1+orderCheckAmount*1;
                } else {
                	orderCheckAmount = $tr.find("td").eq(4).text()*1+orderCheckAmount*1;
                }
        	}
    	});
    	$("#divOrderAmount3").attr("code",orderCheckAmount);
    	$("#divOrderAmount4").attr("code",$("#divOrderAmount2").attr("code")*1-orderCheckAmount*1);
       	$("#divOrderAmount3").html(orderCheckAmount+"元");
       	$("#divOrderAmount4").html($("#divOrderAmount2").attr("code")*1-orderCheckAmount*1+"元");
        if($checkbox.is(":checked")) {
            $tr.find("td").eq(4).html("<div>"+s+"</div>");
        } else {
            $tr.find("td").eq(4).html("<div><input type='text' style='width:200px;' name='myCount' onblur='checkAmount(this);' maxlength='6' value='"+s+"'/></div>");
        }
    }
</script>
</div>
