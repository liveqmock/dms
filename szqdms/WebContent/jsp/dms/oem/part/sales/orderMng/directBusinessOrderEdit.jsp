<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String orgId = user.getOrgId();
    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100200");
    String paraValue1 = userPara.getParavalue1();
    String paraValue2 = userPara.getParavalue2();
    String paraValue3 = userPara.getParavalue3();
    String action = request.getParameter("action");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String date = format.format(Pub.getCurrentDate());
    if(action == null)
        action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript">
	var action = "<%=action%>";
	var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/DirectBusinessOrderMngAction";
	
	//弹出窗体ddxxwh
	var dia_dialog = $("body").data("ddxxwhxz");
	if(action != 1){
		dia_dialog = $("body").data("ddxxwhxg");
	}
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<div id="dia-layout">
    <div class="tabs" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0)"><span>基本信息</span></a></li>
                    <li><a href="javascript:void(0)"><span>配件清单</span></a></li>
                    <li id="orderPay"><a href="javascript:void(0);"><span>付款订购</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" >
            <div class="page">
            <div class="pageContent" >
                <form method="post" id="dia-fm-save" class="editForm" style="width:99%;">
                    <div align="left">
                        <table class="editTable" id="dia-tab-Edit">
                            <input type="hidden" id="diaOrderId" name="diaOrderId" datasource="ORDER_ID"/>
                            <input type="hidden" id="diaOrderNo" name="diaOrderNo" datasource="ORDER_NO"/>
                            <input type="hidden" id="diaOrderAmount" name="diaOrderAmount" datasource="ORDER_AMOUNT"/>
                            <input type="hidden" id="diaProvinceName" name="diaProvinceName" datasource="PROVINCE_NAME"/>
                            <input type="hidden" id="diaCityName" name="diaCityName" datasource="CITY_NAME"/>
                            <input type="hidden" id="diaCountryName" name="diaCountryName" datasource="COUNTRY_NAME"/>
                            <input type="hidden" id="dia-orderType" name="dia-orderType" datasource="ORDER_TYPE" value="<%=DicConstant.DDLX_07%>"/>
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><div id="dia-ddbh">系统自动生成</div></td>
                                <td><label>订单类型：</label></td>
                                <td><div>直营订单</div></td>
                                <td><label>现金可用：</label></td>
                                <td><div id="diaMoney"></div></td>
                            </tr>
                            <tr>
                                <td><label>供货配件库：</label></td>
                                <td>
                                    <input type="text" id="diaWarehouseCode"  name="diaWarehouseCode" kind="dic" dicwidth="260" datasource="WAREHOUSE_CODE" datatype="0,is_null,30"
                                           src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE=100101 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01%>" />
                                    <input type="hidden" id="diaWarehouseId" name="diaWarehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="hidden" id="diaWarehouseName" name="diaWarehouseName" datasource="WAREHOUSE_NAME"/>
                                </td>
                                <td><label>客户名称：</label></td>
                                <td>
                                    <input type="text" id="dia-in-custormName"  name="dia-in-custormName" datasource="CUSTORM_NAME"  datatype="1,is_null,30" />
                                </td>
<!--                                 <td><label>期望交货日期：</label></td> -->
<!--                                 <td> -->
<%--                                     <input type="text" id="dia-in-expectDate" name="dia-in-expectDate" datasource="EXPECT_DATE" datatype="1,is_null,30" onclick="WdatePicker({minDate:'<%=date%>'})"/> --%>
<!--                                 </td> -->
                            </tr>
                            <tr>
                                <td><label>运输方式：</label></td>
                                <td>
                                    <input type="text" id=dia-trans-type name="dia-trans-type" kind="dic" src="FYFS" datasource="TRANS_TYPE" filtercode="" datatype="0,is_null,30" operation="="/>
                                </td>
                                 <td><label>联系人：</label></td>
                                <td><input type="text" id="dia-in-linkMan"  name="dia-in-linkMan" datasource="LINK_MAN" datatype="0,is_null,30"/></td>
                                <td><label>联系方式：</label></td>
                                <td><input type="text" id="dia-in-phone"  name="dia-in-phone" datasource="PHONE" datatype="0,is_null,30"/></td>
                            </tr>
                            <tr id="trans1" style="display:none;">
<!--                                 <td><label>省：</label></td> -->
<!--                                 <td><input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" datasource="PROVINCE_CODE" value="" datatype="0,is_null,300" /></td> -->
<!--                                 <td><label>市：</label></td> -->
<!--                                 <td><input type="text" id="diaCityCode" name="diaCityCode" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,300" /></td> -->
<!--                                 <td><label>区县：</label></td> -->
<!--                                 <td><input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic"  datasource="COUNTRY_CODE" value="" datatype="0,is_null,300" /></td> -->
                                <td><label>省：</label></td>
								<td>
								    <input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src=""   datasource="PROVINCE_CODE" value="" datatype="0,is_null,100" />
<!--                                        <input type="hidden" id="diaProvinceName" name="diaProvinceName" datasource="PROVINCE_NAME"/> -->
								</td>
								<td><label>市：</label></td>
								<td>
								    <input type="text" id="diaCityCode" name="diaCityCode" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,100" />
<!--                                        <input type="hidden" id="diaCityName" name="diaCityName" datasource="CITY_NAME"/> -->
								</td>
								<td><label>区县：</label></td>
								<td>
								    <input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic"  datasource="COUNTY_CODE" value="" datatype="0,is_null,300" />
<!--                                        <input type="hidden" id="diaCountryName" name="diaCountryName" datasource="COUNTRY_NAME"/> -->
								</td>
                            </tr>
                            <tr id="trans2" style="display:none;">
                                <td><label>邮政编码：</label></td>
                                <td><input type="text" id="dia-in-zipCode"  name="dia-in-zipCode" datasource="ZIP_CODE" datatype="1,is_digit,15"/></td>
                                <td><label>详细地址：</label></td>
                                <td colspan="4"><input type="text" style="width:86%;" id="dia-in-deliveryAddr"  name="dia-in-deliveryAddr" datasource="DELIVERY_ADDR" datatype="0,is_null,100"/></td>
                            </tr>
                            <tr>
                                <td><label>订单总金额：</label></td>
                                <td><div id="divOrderAmount"></div></td>
                            </tr>
                            <tr>
                                <td><label>备注：</label></td>
                                <td colspan="3"><textarea id="dia-in-remarks" name="dia-in-remarks" style="width:89%;height:40px;" datasource="REMARKS"  datatype="1,is_null,100"></textarea></td>
                            </tr>
                        </table>
                    </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-close-btn">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            </div>
            <div class="page">
            <div class="pageContent" >
                <form method="post" id="diaOrderPartFm" class="editForm">
                    <table class="editTable" id="tabOrderPartSearch" >
                    </table>
                    <div style="margin-left:65px;margin-bottom:7px;font-size:14px;">订单总金额：
                        <span id="divOrderAmount1" style="font-size:14px;color:red;"></span>
                    </div>
                </form>
                <div id="dia-pjmx" style="height:200px;overflow:hidden;">
                    <table style="display:none;width:80%;" id="tab-orderPartList" name="tablist" ref="dia-pjmx">
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
                                <th fieldname="PART_CODE">配件代码</th>
                                <th fieldname="PART_NAME">配件名称</th>
                                <th fieldname="UNIT">计量单位</th>
                                <th fieldname="" refer="appendPack_Unit">最小包装</th>
                                <!-- <th fieldname="UNIT_PRICE" refer="formatAmount" align="right">经销商价(元)</th> -->
                                <th fieldname="UNIT_PRICE" refer="formatAmount" align="right">零售价(元)</th>
                                <th fieldname="SUPPLIER_NAME">供应商</th>
                                <th fieldname="ORDER_COUNT">订购数量</th>
                                <th fieldname=""  refer="acount" align="right">小计(元)</th>
                                <th colwidth="85" type="link" title="[删除]" action="doDiaListDelete">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            
            
            
            <div class="formBar">
                <ul>
                    <li><div class="button"><div class="buttonContent"><button type="button"  id="btnExp" name="btnExp">配件模版导出</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button type="button"  id="btnImp" name="btnImp">配件明细导入</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
                    <li id="button2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
<!--                     <li><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li> -->
                    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-close-btn2">关&nbsp;&nbsp;闭</button></div></div></li>
                </ul>
            </div>
            </div>
        
        <div>
                <form method="post" id="diaFundsFm" class="editForm" style="width:99%;">
                    <div align="left">
                        <table class="editTable" id="diaOrderFundsInfo">
                        </table>
                        <div style="margin-left:65px;margin-bottom:7px;font-size:14px;">
                            <table style="width:90%;">
                                <tr>
                                    <td style="width:30%;">订单总金额：<span id="divOrderAmount2" style="font-size:14px;color:red;"></span></td>
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
                    <table style="display:none;width:80%;" id="tab-orderFundsList" name="tablist" ref="div-orderFundsList" refQuery="tabOrderFundsSearch" edit="true">
                            <thead>
                                <tr>
                                    <th type="single" name="XH" style="display:" append="plus|addFunds"></th>
                                    <th fieldname="ACCOUNT_TYPE" ftype="input" fkind="dic" fisreload="true" fsrc="" fdatatype="0,is_null,30">账户类型</th>
                                    <th fieldname="AVAILABLE_AMOUNT" freadonly="true" align="right">可用余额(元)</th>
                                    <th fieldname="PAY_AMOUNT" ftype="input" fonblur="checkAmount(this)" fdatatype="0,is_money,30" align="right">本次使用金额(元)</th>
                                    <th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaFkSave|doDiaFkDelete">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                    </table>
                </div>
                <div class="formBar">
                    <ul>
                        <!-- <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btnFundsSave">保存付款信息</button></div></div></li> -->
                        <li id="dia-report-btn"><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
        
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>
</body>
</html>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        $("#tab-orderPartList").attr("layoutH",document.documentElement.clientHeight-140);
        // 账户类型设置
        $("#tab-orderFundsList").find("th").eq(1).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>)");
        if (action != "1") {
            // 修改操作
            var selectedRows = $("#tab-index").getSelectedRows();
            var transType = selectedRows[0].attr("TRANS_TYPE");
            setEditValue("dia-tab-Edit",selectedRows[0].attr("rowdata"));
            $("#dia-ddbh").html(selectedRows[0].attr("ORDER_NO"));
            $("#diaWarehouseCode").attr("disabled",true);
            // 订单总金额
            $("#divOrderAmount").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
            $("#divOrderAmount1").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
            $("#divOrderAmount2").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
            // 供货配件库
            $("#diaWarehouseCode").attr("code",selectedRows[0].attr("WAREHOUSE_CODE"));
            $("#diaWarehouseCode").val(selectedRows[0].attr("WAREHOUSE_NAME"));
            // 运输方式
            $("#diatransType").val(selectedRows[0].attr("TRANS_TYPE_sv"));
            $("#diatransType").attr("code",transType);
            // 省
            $("#diaProvinceCode").attr("code",selectedRows[0].attr("PROVINCE_CODE"));
            $("#diaProvinceCode").val(selectedRows[0].attr("PROVINCE_NAME"));
            // 市
            $("#diaCityCode").attr("code",selectedRows[0].attr("CITY_CODE"));
            $("#diaCityCode").val(selectedRows[0].attr("CITY_NAME"));
            // 区
            $("#diaCountryCode").attr("code",selectedRows[0].attr("COUNTRY_CODE"));
            $("#diaCountryCode").val(selectedRows[0].attr("COUNTRY_NAME"));
            // 运输方式(发运)
            if (transType == <%=DicConstant.FYFS_01%>) {
            	$("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
                $("#trans1").show();
                $("#trans2").show();
            }
        } else {
        	
            // 发运方式(发运)
//             $("#dia-trans-type").val("自提");
<%--             $("#dia-trans-type").attr("code","<%=DicConstant.FYFS_02%>"); --%>
            $("#diaWarehouseId").val("<%=paraValue1%>");
            $("#diaWarehouseCode").attr("code","<%=paraValue2%>");
            $("#diaWarehouseCode").val("<%=paraValue3%>");
            $("#diaWarehouseName").val("<%=paraValue3%>");
        }
        //账户可用余额查询
        var accountSearchUrl = diaUrl + "/accountSearch.ajax";
        sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");

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

        // 保存按钮响应
        $("#dia-save").bind("click", function () {
            if (action == "1") {
                // 新增操作
                var insertUrl = diaUrl + "/insertDirectBusinessOrder.ajax";
                var $f = $("#dia-fm-save");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "dia-save", sCondition, saveCallBack);
            } else {
                // 修改操作
                var insertUrl = diaUrl + "/updateDirectBusinessOrder.ajax";
                var $f = $("#dia-fm-save");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "dia-save", sCondition, updateCallBack);
            }
        });
        
        // 关闭
        $("#dia-close-btn").click(function(){
        	$("#btn-search").click();
        	$.pdialog.close(dia_dialog);
    		return false;
        });
        $("#dia-close-btn2").click(function(){
        	$("#btn-search").click();
        	$.pdialog.close(dia_dialog);
    		return false;
        });

        // 配件模版导出按钮响应
        $("#btnExp").bind("click", function () {
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=orderPartImp.xls");
            window.location.href = url;
        });

        //配件明细导入按钮响应
        $("#btnImp").bind("click", function () {
            importXls("PT_BU_SALE_ORDER_DTL_TMP",$("#diaOrderId").val(),5,3,"/jsp/dms/dealer/part/sales/orderMng/importSuccess.jsp?flag=1");
        });

        //提报
        $("#dia-report").bind("click",function(){
            if($("#tab-orderPartList").find("tr").size()==0){
                alertMsg.warn("请添加配件.");
                return false;
            }
            if(!reportCheckAmount()){
                alertMsg.warn("使用金额与订单总金额不相等,请调整后重新提报.");
                return false;
            }
            var reportUrl = diaUrl + "/partOrderReport.ajax?&orderId="+$("#diaOrderId").val()+"&orderAmount="+$("#diaOrderAmount").val();
            sendPost(reportUrl, "dia-report", "", diaReportCallBack, "true");
        });
    });

    //提报回调
    function diaReportCallBack(res){
        try {
            // 查询方法
            searchDirectBusinessOrder();
            $("#btn-search").click();
            $("#dia-close-btn2").click();
//         	$.pdialog.close(dia_dialog);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 小计
    function acount (obj) {
        var $tr = $(obj).parent();
        return "<div style='text-align:right;'>"+amountFormatNew($tr.attr("UNIT_PRICE")*$tr.attr("ORDER_COUNT"))+"</div>";
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

    //账户可用余额查询回调
    function accountSearchCallback(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length > 0) {
                var availableAmount = getNodeText(rows[0].getElementsByTagName("AVAILABLE_AMOUNT").item(0));
                $("#diaMoney").html(amountFormatNew(availableAmount)+"元");
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    function doNextTab($tabs) {
       $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
       (function (ci) {
           switch (parseInt(ci)) {
               case 1:
                   if (!$("#diaOrderId").val()) {
                       alertMsg.warn('请先保存订单基本信息.');
                       $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                   } else {
                       if ($("#tab-orderPartList").is(":hidden")) {
                           $("#tab-orderPartList").show();
                           $("#tab-orderPartList").jTable();
                           orderPartSearch();
                       }
                   }
                   break;
               case 2:
                   if (!$('#diaOrderId').val()) {
                       $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                       alertMsg.warn('请先保存订单基本信息.');
                   } else {
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

    //查询资金列表
    function orderFundsSearch(){
        var orderFundsSearchUrl = diaUrl+"/orderFundsSearch.ajax?orderId="+$("#diaOrderId").val();
        var $f = $("#diaOrderFundsFm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, orderFundsSearchUrl, "", 1, sCondition, "tab-orderFundsList");
    }

    //查询配件列表
    function orderPartSearch() {
        var orderPartSearchUrl = diaUrl+"/orderPartSearch.ajax?orderId="+$("#diaOrderId").val();
        var $f = $("#diaOrderPartFm");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, orderPartSearchUrl, "", 1, sCondition, "tab-orderPartList");
    }

    // 保存按钮回调函数(新增链接)
    function saveCallBack(res) {
        try {
            var rows = res.getElementsByTagName("ROW");
            var orderId = getNodeText(rows[0].getElementsByTagName("ORDER_ID").item(0));
            var orderNo = getNodeText(rows[0].getElementsByTagName("ORDER_NO").item(0));
            $("#diaOrderId").val(orderId);
            $("#dia-ddbh").html(orderNo);
            $("#diaWarehouseCode").attr("disabled",true);
            action = "2";
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 保存按钮回调函数(编辑链接)
    function updateCallBack(res) {
        try {
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //弹出配件选择列表，支持复选 
    function addPjmx() {
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/directBusinessOrderPartSearch.jsp", "directBusinessOrderPartSearch", "配件信息查询", options);
    }

    // 列表删除
    function doDiaListDelete(rowobj) {
        $rowpart = $(rowobj);
        var deleteUrl = diaUrl + "/orderPartDelete.ajax?dtlId=" + $(rowobj).attr("DTL_ID")+"&orderId="+$("#diaOrderId").val();
        sendPost(deleteUrl, "", "", deletePartCallBack, "true");
    }

    //配件删除回调
    function deletePartCallBack(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length>0){
                var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
                $("#diaOrderAmount").val(amount);
                $("#divOrderAmount").html(amountFormatNew(amount)+"元");
                $("#divOrderAmount1").html(amountFormatNew(amount)+"元");
                $("#divOrderAmount2").html(amountFormatNew(amount)+"元");
                orderPartSearch();
            }
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 字典选择回调方法
    function afterDicItemClick(id,$row,selIndex) {
    	if(id.indexOf("ACCOUNT_TYPE") == 0)
        {
            var curRow = $("#"+id);
            while(curRow.isTag("tr") == false)
            {
                curRow = curRow.parent();
            }
            var $tab = $("#tab-orderFundsList_content");
            while($tab.get(0).tagName != "TABLE")
                $tab = $tab.parent();
            var flag = false;
            var arr = new Array();
            curRow.attr("ACCOUNT_ID",$row.attr("a.account_id"));
            curRow.find("td").eq(3).text($row.attr("A.AVAILABLE_AMOUNT"));
            $("tr",$tab).each(function(){
                var $tr = $(this);
                var accountIdCheck = $tr.attr("ACCOUNT_ID");
                if (accountIdCheck != "undefined" && accountIdCheck != "") {
                    arr.push($tr.attr("ACCOUNT_ID"));
                }
            });
             if( arr.length==$.unique( arr ).length ){
                 flag=false;
             }else{
                 flag=true;
             }
            if(flag){
                curRow.find("td").eq(2).find("input").attr("code","");
                curRow.find("td").eq(2).find("input").val("");
                curRow.attr("ACCOUNT_ID","");
                curRow.find("td").eq(3).text("");
                alertMsg.warn("账户类型不能重复选择.");
                return false;
            }
        }
        // 发运方式选择
        if(id == "dia-trans-type") {
            if($("#dia-trans-type").attr("code") == <%=DicConstant.FYFS_01%>) {
            	$("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
                $("#trans1").show();
                $("#trans2").show();
            } else {
                $("#trans1").hide();
                $("#trans2").hide();
            }
            
        }
        if(id == "diaProvinceCode")
    	{
            $("#diaProvinceName").val($("#diaProvinceCode").val());
    		$("#diaCityCode").val("");
    		$("#diaCityCode").attr("code","");
    		$("#diaCountryCode").val("");
    		$("#diaCountryCode").attr("code","");
    		var privinceCode = $("#"+id).attr("code").substr(0,2);
    		$("#diaCityCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+privinceCode+"%' AND LX='30' ");
    		$("#diaCityCode").attr("isreload","true");
    		//$("#dia-city_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
    	}
    	if(id == "diaCityCode")
    	{
    		$("#diaCityName").val($("#diaCityCode").val());
    		$("#diaCountryCode").val("");
    		$("#diaCountryCode").attr("code","");
    		var cityCode = $("#"+id).attr("code").substr(0,4);
    		$("#diaCountryCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+cityCode+"%' AND LX='40' ");
    		$("#diaCountryCode").attr("isreload","true");
    		$("#diaCountryCode").attr("dicwidth","300");
    		//$("#dia-county_Code").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
    	}
//         // 选择省
//         if(id == "diaProvinceCode") {
//             $("#diaCityCode").attr("src","XZQH");
//             $("#diaCityCode").attr("isreload","true");
//             $("#diaCityCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
//             // 省名称
//             $("#diaProvinceName").val($("#diaProvinceCode").val());
//         }
//         // 选择市
//         if(id == "diaCityCode") {
//             $("#diaCountryCode").attr("src","XZQH");
//             $("#diaCountryCode").attr("isreload","true");
//             $("#diaCountryCode").attr("dicwidth","300");
//             $("#diaCountryCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
//             // 市名称
//             $("#diaCityName").val($("#diaCityCode").val());
//         }
        
        // 选择区
        if(id == "diaCountryCode") {
            // 区名称
            $("#diaCountryName").val($("#diaCountryCode").val());
        }
        // 供货配件库选择
        if (id== "diaWarehouseCode") {
            $("#diaWarehouseId").val($row.attr("WAREHOUSE_ID"));
            $("#diaWarehouseName").val($row.attr("WAREHOUSE_NAME"));
        }
        return true;
    }
    
  //查询回调
    function callbackSearch(res,tabId,sParam)
    {
        switch(tabId)
        {
            case "tab-partList":
                 //setStyle('tab-partList_content');
            break;
            case "tab-orderPartList_content":
                //setStyle('tab-orderPartList_content');
            break;
            case "tab-orderFundsList":
                setStyle('tab-orderFundsList_content');
                var rows = res.getElementsByTagName("ROW");
                if(rows.length>0){
                    var accountType ="";
                    for(var i=0;i<rows.length;i++){
                        if(accountType==""){
                            accountType = getNodeText(rows[i].getElementsByTagName("ACCOUNT_TYPE").item(0));
                        }else{
                            accountType = accountType+","+getNodeText(rows[i].getElementsByTagName("ACCOUNT_TYPE").item(0));
                        }
                    }
                           $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>) AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
//                         $("#diaIfCredit").attr("disabled",true);
                }else{
                           $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>)");
//                         $("#diaIfCredit").attr("disabled",false);
                }
                var allfunds=0;
                $("#tab-orderFundsList_content").find("tr").each(function(){
                    var funds=$(this).find("td").eq(4).text();
                    allfunds = parseFloat(allfunds,10)+parseFloat(funds,10);
                });
                $("#divOrderAmount3").html(amountFormatNew(allfunds)+"元");
                var payfunds = parseFloat($("#diaOrderAmount").val(),10).toFixed(2)-parseFloat(allfunds,10).toFixed(2);
                if(payfunds==0){
                    $("#divOrderAmount4").html(0.00+"元");
                }else{
                    $("#divOrderAmount4").html(amountFormatNew(payfunds)+"元");
                }
                
            break;
        }
    }
  //添加账户
    function addFunds()
    {
        var $tab = $("#tab-orderFundsList");
        var i=0;
        $("#tab-orderFundsList_content").find("tr").each(function(){
            var $tr = $(this);
            var $tabs = $tr;
            while($tabs.get(0).tagName != "TABLE")
                $tabs = $tabs.parent();
            $tabs.parent().parent().attr("style","overflow:hidden;");
            var $td4 = $tr.find("td").eq(4);
            if($td4.find("input").size==1)
                i++;
            else if($td4.text().length>0)
                i++;
        });
        if($("#diaIfCredit").attr("code")==<%=DicConstant.SF_01%>){
            if(i==1){
                alertMsg.warn("额度账户已添加,不能重复添加.");
                return false;
            }
           }else{
               if(i==3){
                   alertMsg.warn("现金、承兑、材料费账户已添加,不能重复添加.");
                return false;
            }
           }
        $tab.createRow();    
//         $("#diaIfCredit").attr("disabled",true);
    }

    //行编辑保存
    function doDiaFkSave(row){
        var ret = true;
        try
        {
            if(!checkAmount($(row).find("td").eq(4).find("input:first").get(0))) return false;
            $("td input[type=radio]",$(row)).attr("checked",true);
            var $f = $("#fm-orderFunds");
            var payId=$(row).attr("PAY_ID");
            $("#payId").val(payId);
            $("#accountId").val($(row).attr("ACCOUNT_ID"));
            $("#accountType").val($(row).find("td").eq(2).find("input:first").attr("code"));
            $("#payAmount").val($(row).find("td").eq(4).find("input:first").val());
            $("#payOrderId").val($("#diaOrderId").val());
            if (submitForm($(row)) == false) 
                return false;
            var sCondition = {};
            sCondition = $f.combined(1) || {};
            if(payId)
            {
                var url = diaUrl + "/orderFundsUpdate.ajax";
                sendPost(url,"",sCondition,diaListSaveCallBack,"true");
            }else
            {
                var url = diaUrl + "/orderFundsInsert.ajax";
                sendPost(url,"",sCondition,diaListSaveCallBack,"false");
            }
        }catch(e){
            alertMsg.error(e);
            ret = false;
        }
        return ret;
    }
    //行编辑保存回调
    function diaListSaveCallBack(res){
        var selectedIndex = $("#tab-orderFundsList").getSelectedIndex();
        $("#tab-orderFundsList").updateResult(res,selectedIndex);
        $("#tab-orderFundsList").clearRowEdit($("#tab-orderFundsList").getSelectedRows()[0],selectedIndex);
        var allfunds=0;
        var accountType="";
        $("#tab-orderFundsList_content").find("tr").each(function(){
            var $tr = $(this);
            var $td4 = $tr.find("td").eq(4);
            if($td4.find("input").size() > 0)
                allfunds = parseFloat(allfunds,10)+parseFloat($td4.find("input:first").val(),10);
            else
                allfunds = parseFloat(allfunds,10)+parseFloat($td4.text(),10);
            if(accountType==""){
                accountType=$(this).attr("ACCOUNT_TYPE");
            }else{
                accountType=accountType+","+$(this).attr("ACCOUNT_TYPE");
            }
        });
        if(accountType){
               $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>) AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
        }else{
            $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>)");
        }
        $("#divOrderAmount3").html(amountFormatNew(allfunds)+"元");
        allfunds =allfunds.toFixed(2);
        var payfunds = parseFloat($("#diaOrderAmount").val(),10)-parseFloat(allfunds,10);
        if(payfunds==0){
            $("#divOrderAmount4").html(0.00+"元");
        }else{
            $("#divOrderAmount4").html(amountFormatNew(payfunds)+"元");
        }
        return true;
    }
    //行编辑 删除
    var $rowFunds;
    function doDiaFkDelete(row){
        $rowFunds =$(row);
        $("td input[type=radio]",$(row)).attr("checked",true);
        var payId = $(row).attr("PAY_ID");
        if(payId){
            var url = diaUrl + "/orderFundsDelete.ajax?payId="+payId;
            sendPost(url,"delete","",diaDeleteCallBack,"true");
        }else{
            $("#tab-orderFundsList").removeResult($rowFunds);
//                if($("#tab-orderFundsList_content").find("tr").eq(4).find("input:first").length==0)
//                    $("#diaIfCredit").attr("disabled",false);
        }
    }
    //行编辑删除回调方法
    function diaDeleteCallBack(res)
    {
        try
        {    
            if($rowFunds)
            $("#tab-orderFundsList").removeResult($rowFunds);
//                if($("#tab-orderFundsList_content").find("tr").eq(4).find("input:first").length==0){
//                    $("#diaIfCredit").attr("disabled",false);
//                }
               var allfunds=0;
            var accountType="";
            $("#tab-orderFundsList_content").find("tr").each(function(){
                var $tr = $(this);
                var $td4 = $tr.find("td").eq(4);
                if($td4.find("input").size() > 0)
                    allfunds = parseFloat(allfunds,10)+parseFloat($td4.find("input:first").val(),10);
                else
                    allfunds = parseFloat(allfunds,10)+parseFloat($td4.text(),10);
                if(accountType==""){
                    accountType=$(this).attr("ACCOUNT_TYPE");
                }else{
                    accountType=accountType+","+$(this).attr("ACCOUNT_TYPE");
                }
            });
            if(accountType){
                   $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>) AND A.ACCOUNT_TYPE NOT IN("+accountType+")");
            }else{
                $("#tab-orderFundsList").find("th").eq(2).attr("fsrc","T#PT_BU_ACCOUNT A,DIC_TREE B:A.ACCOUNT_TYPE:B.DIC_VALUE{A.ACCOUNT_ID,A.AVAILABLE_AMOUNT}:1=1 AND A.ACCOUNT_TYPE = B.ID AND A.ORG_ID = <%=orgId%> AND A.ACCOUNT_TYPE IN(<%=DicConstant.ZJZHLX_01%>,<%=DicConstant.ZJZHLX_02%>)");
            }
            $("#divOrderAmount3").html(amountFormatNew(allfunds)+"元");
            var payfunds = parseFloat($("#diaOrderAmount").val())-parseFloat(allfunds);
            if(payfunds==0){
                $("#divOrderAmount4").html(0.00+"元");
            }else{
                $("#divOrderAmount4").html(amountFormatNew(payfunds)+"元");
            }
        }catch(e)
        {
            alertMsg.error(e);
            return false;
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
        return true;
    }
    //提报时金额校验
    function reportCheckAmount(){
        var aMountSum = 0;
        var $tab = $("#tab-orderFundsList_content");
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
        if(parseFloat(aMountSum,10)!= parseFloat(orderAmount,10))
        {
            return false;
        }
        return true;
    }
</script>