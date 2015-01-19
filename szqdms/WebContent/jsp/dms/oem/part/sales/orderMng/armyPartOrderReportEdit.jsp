<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    String action = request.getParameter("action");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String date = format.format(Pub.getCurrentDate());
    if(action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0)"><span>基本信息</span></a></li>
                    <li><a href="javascript:void(0)"><span>配件清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent" >
            <div>
                <form method="post" id="dia-fm-save" class="editForm" style="width:99%;">
                    <div align="left">
                        <table class="editTable" id="dia-tab-Edit">
                            <input type="hidden" id="diaOrderId" name="diaOrderId" datasource="ORDER_ID"/>
                            <input type="hidden" id="diaOrderNo" name="diaOrderNo" datasource="ORDER_NO"/>
                            <input type="hidden" id="diaOrderAmount" name="diaOrderAmount" datasource="ORDER_AMOUNT"/>
                            <input type="hidden" id="diaProvinceName" name="diaProvinceName" datasource="PROVINCE_NAME"/>
                            <input type="hidden" id="diaCityName" name="diaCityName" datasource="CITY_NAME"/>
                            <input type="hidden" id="diaCountryName" name="diaCountryName" datasource="COUNTRY_NAME"/>
                            <input type="hidden" id="dia-orderType" name="dia-orderType" datasource="ORDER_TYPE" value="<%=DicConstant.DDLX_08%>"/>
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><div id="dia-ddbh">系统自动生成</div></td>
                                <td><label>订单类型：</label></td>
                                <td><div>军品订单</div></td>
                                <td><label>额度可用：</label></td>
                                <td><div id="diaMoney"></div></td>
                            </tr>
                            <tr>
                                <td><label>供货配件库：</label></td>
                                <td>
                                    <input type="text" id="diaWarehouseCode"  name="diaWarehouseCode" kind="dic" dicwidth="260" datasource="WAREHOUSE_CODE" datatype="0,is_null,30"
                                           src="T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND WAREHOUSE_TYPE=100102 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01%>" />
                                    <input type="hidden" id="diaWarehouseId" name="diaWarehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="hidden" id="diaWarehouseName" name="diaWarehouseName" datasource="WAREHOUSE_NAME"/>
                                </td>
                                <td><label>客户名称：</label></td>
                                <td>
                                    <input type="text" id="dia-in-custormName"  name="dia-in-custormName" datasource="CUSTORM_NAME"  datatype="1,is_null,30" />
                                </td>
                                <td><label>期望交货日期：</label></td>
                                <td>
                                    <input type="text" id="dia-in-expectDate" name="dia-in-expectDate" datasource="EXPECT_DATE" datatype="1,is_null,30" onclick="WdatePicker({minDate:'<%=date%>'})"/>
                                </td>
                            </tr>
                            <tr>
                                <td><label>运输方式：</label></td>
                                <td>
                                    <input type="text" id="dia-trans-type" name="dia-trans-type" kind="dic" src="FYFS" datasource="TRANS_TYPE"  datatype="0,is_null,30" operation="="/>
                                </td>
                                <td id="td-addrType3"><label>联系人：</label></td>
                                <td id="td-addrType5"><input type="text" id="dia-in-linkMan"  name="dia-in-linkMan" datasource="LINK_MAN" datatype="0,is_null,30"/></td>
                                <td id="td-addrType4"><label>联系方式：</label></td>
                                <td id="td-addrType6"><input type="text" id="dia-in-phone"  name="dia-in-phone" datasource="PHONE" datatype="0,is_null,30"/></td>
                            </tr>
                            <tr id="td-addrType1">
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
<!--                                 <td><label>省：</label></td> -->
<!--                                 <td><input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" datasource="PROVINCE_CODE" value="" datatype="0,is_null,30" /></td> -->
<!--                                 <td><label>市：</label></td> -->
<!--                                 <td><input type="text" id="diaCityCode" name="diaCityCode" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,30" /></td> -->
<!--                                 <td><label>区县：</label></td> -->
<!--                                 <td><input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic"  datasource="COUNTRY_CODE" value="" datatype="0,is_null,30" /></td> -->
                            </tr>
                            <tr id="td-addrType2">
                                <td><label>邮政编码：</label></td>
                                <td><input type="text" id="dia-in-zipCode"  name="dia-in-zipCode" datasource="ZIP_CODE" datatype="1,is_digit,15"/></td>
                                <td><label>详细地址：</label></td>
                                <td colspan="3"><input type="text" style="width:87%;" id="dia-in-deliveryAddr"  name="dia-in-deliveryAddr" datasource="DELIVERY_ADDR" datatype="0,is_null,100"/></td>
                            </tr>
                            <tr>
                                <td><label>订单总金额：</label></td>
                                <td><div id="divOrderAmount"></div></td>
                                <td id="ifType1"><label>是否免运费：</label></td>
                                <td id="ifType2">
                                    <input type="text" id="diaIfTrans" style="width:80px;" name="diaIfTrans" kind="dic" src="SF" datasource="IF_TRANS"  datatype="0,is_null,30"/>
                                </td>
                                <td><label>合同号：</label></td>
                                <td><input type="text" id="dia-armyContNo"  name="dia-armyContNo" datasource="ARMY_CONT_NO" datatype="0,is_null,300"/></td>
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
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <div>
                <form method="post" id="diaOrderPartFm" class="editForm">
                    <table class="editTable" id="tabOrderPartSearch" >
                    </table>
                    <div style="margin-left:65px;margin-bottom:7px;font-size:14px;">订单总金额：
                        <span id="divOrderAmount1" style="font-size:14px;color:red;"></span>
                    </div>
                </form>
                <div id="dia-pjmx" style="overflow:hidden;">
                    <table style="display:none;width:80%;" id="tab-orderPartList" edit="true" name="tablist" ref="dia-pjmx">
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
                                <th fieldname="PART_CODE" freadonly="true">配件代码</th>
                                <th fieldname="PART_NAME" freadonly="true">配件名称</th>
                                <th fieldname="UNIT" freadonly="true" colwidth="50">计量单位</th>
                                <th fieldname="MIN_PACK" freadonly="true" colwidth="50" refer="appendPack_Unit">最小包装</th>
                                <th fieldname="UNIT_PRICE" freadonly="true" refer="formatAmount" colwidth="70" align="right">军品价格(元)</th>
                                <th fieldname="SUPPLIER_NAME" freadonly="true" refer="supplierShow" freadonly="true">供应商名称</th>
                                <th fieldname="ORDER_COUNT" colwidth="50" fdatatype="1,is_digit,30">订购数量</th>
                                <th fieldname=""  refer="acount" align="right" freadonly="true">小计(元)</th>
                                <th fieldname="REMARKS" colwidth="90">备注</th>
                                <th colwidth="105" type="link" title="[编辑]|[删除]"  action="doDiaPartSave|doDiaPartDelete">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <form method="post" id="diaSaveFm">
                    <input type="hidden" id="saveOrderId" name="saveOrderId" datasource="ORDER_ID"/>
                    <input type="hidden" id="saveDtlId" name="saveDtlId" datasource="DTL_ID"/>
                    <input type="hidden" id="saveOrderCount" name="saveOrderCount" datasource="ORDER_COUNT"/>
                    <input type="hidden" id="saveRemarks" name="saveRemarks" datasource="REMARKS"/>
                    <input type="hidden" id="saveUnitPrice" name="saveUnitPrice" datasource="UNIT_PRICE"/>
                </form>
	            <div class="formBar">
	                <ul>
	                	<li><div class="button"><div class="buttonContent"><button type="button"  id="diaDelete" name="diaDelete">全部清除</button></div></div></li>
	                    <li><div class="button"><div class="buttonContent"><button type="button"  id="btnExp" name="btnExp">配件模版导出</button></div></div></li>
	                    <li><div class="button"><div class="buttonContent"><button type="button"  id="btnImp" name="btnImp">配件明细导入</button></div></div></li>
	                    <li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
	                    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
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
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
    var action = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/ArmyPartOrderReportAction";
    $(function(){
        $("#tab-orderPartList").attr("layoutH",document.documentElement.clientHeight-240);
        
        if (action != "1") {
            // 修改操作
            var selectedRows = $("#tab-searchList").getSelectedRows();
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
            // 运输方式(发运)
            if (transType == <%=DicConstant.FYFS_01%>) {
            	$("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
                $("#trans1").show();
                $("#trans2").show();
            }
            $("#diaProvinceCode").attr("code",selectedRows[0].attr("PROVINCE_CODE"));
            $("#diaProvinceCode").val(selectedRows[0].attr("PROVINCE_NAME"));
            $("#diaCityCode").attr("code",selectedRows[0].attr("CITY_CODE"));
            $("#diaCityCode").val(selectedRows[0].attr("CITY_NAME"));
            $("#diaCountryCode").attr("code",selectedRows[0].attr("COUNTRY_CODE"));
            $("#diaCountryCode").val(selectedRows[0].attr("COUNTRY_NAME"));
            $("#diaIfTrans").attr("code",selectedRows[0].attr("IF_TRANS"));
            $("#diaIfTrans").val(selectedRows[0].attr("IF_TRANS_sv"));
            $("#diaRemarks").val(selectedRows[0].attr("REMARKS"));
        }
       	var getWarehouse = diaUrl + "/getWarehouse.ajax";
        sendPost(getWarehouse, "", "", getWarehouseSearchCallback, "false");
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
                var insertUrl = diaUrl + "/orderInfoInsert.ajax";
                var $f = $("#dia-fm-save");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "dia-save", sCondition, saveCallBack);
            } else {
                // 修改操作
                var insertUrl = diaUrl + "/orderInfoUpdate.ajax";
                var $f = $("#dia-fm-save");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "dia-save", sCondition, updateCallBack);
            }
        });

        // 配件模版导出按钮响应
        $("#btnExp").bind("click", function () {
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=orderPartImp.xls");
            window.location.href = url;
        });

        //配件明细导入按钮响应
        $("#btnImp").bind("click", function () {
            importXls("PT_BU_ASALE_ORDER_DTL_TMP",$("#diaOrderId").val(),5,3,"/jsp/dms/oem/part/sales/orderMng/armyImportSuccess.jsp");
        });

        //提报
        $("#dia-report").bind("click",function(){
            if($("#tab-orderPartList_content").find("tr").size()==0){
                alertMsg.warn("请添加配件.");
                return false;
            }
            var reportUrl = diaUrl + "/partOrderReport.ajax?&orderId="+$("#diaOrderId").val()+"&orderAmount="+$("#diaOrderAmount").val();
            $("#dia-report").attr("disabled","true");
            sendPost(reportUrl, "dia-report", "", diaReportCallBack, "true");
        });
        $("#diaDelete").bind("click",function(){
            var reportUrl = diaUrl + "/partOrderDtlDelete.ajax?&orderId="+$("#diaOrderId").val();
            sendPost(reportUrl, "", "", diaOrderPartDeleteCallBack, "true");
        })
    });

    //提报回调
    function diaReportCallBack(res){
        try {
            // 查询方法
            var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/partOrderSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
            $.pdialog.closeCurrent();
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

	//经销商价
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
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
/*设定默认仓库为军品库  */
    function getWarehouseSearchCallback(res){
        try {
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length > 0)
            {
                    var WAREHOUSE_ID = getNodeText(rows[0].getElementsByTagName("WAREHOUSE_ID").item(0));
                    var WAREHOUSE_CODE = getNodeText(rows[0].getElementsByTagName("WAREHOUSE_CODE").item(0));
                    var WAREHOUSE_NAME = getNodeText(rows[0].getElementsByTagName("WAREHOUSE_NAME").item(0));
                    $('#diaWarehouseCode').attr('code',WAREHOUSE_CODE);
                    $('#diaWarehouseCode').val(WAREHOUSE_NAME);
                    $('#diaWarehouseName').val(WAREHOUSE_NAME);
                    $('#diaWarehouseId').val(WAREHOUSE_ID);
                    $('#diaWarehouseCode').attr("src","");
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
           }
       })(parseInt($tabs.attr("currentIndex")));
    }

    function doPreTab($tabs) {
         $tabs.switchTab($tabs.attr("currentIndex") - 1);
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
            doSearch();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 保存按钮回调函数(编辑链接)
    function updateCallBack(res) {
        try {
        	doSearch();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    //弹出配件选择列表，支持复选 
    function addPjmx() {
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/ArmyOrderPartSearch.jsp", "armyPartSelWin", "配件信息查询", options);
    }

    // 列表删除
    function doDiaPartDelete(rowobj) {
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
    	var ret = true;
        // 发运方式选择
        if(id == "dia-trans-type") {
            if($("#dia-trans-type").attr("code") == <%=DicConstant.FYFS_01%>) {
            	$("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
                $("#td-addrType1").show();
                $("#td-addrType2").show();
                $("#td-addrType3").show();
                $("#td-addrType4").show();
                $("#td-addrType5").show();
                $("#td-addrType6").show();
            } else {
            	$("#td-addrType1").hide();
                $("#td-addrType2").hide();
                $("#td-addrType3").hide();
                $("#td-addrType4").hide();
                $("#td-addrType5").hide();
                $("#td-addrType6").hide();
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
        return ret;
    }
    function doDiaPartSave(rowobj){
        $rowpart = $(rowobj);
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        // 订单数量
        var $obj = $(rowobj).find("td").eq(8).find("input:first");
        // 备注
        var $obj1 = $(rowobj).find("td").eq(10).find("input:first");
        if($obj.val()=="" || !isCount($obj)){
            alertMsg.warn("请正确输入订购数量！");
            return false;
        }
        // 订单主键
        $("#saveOrderId").val($("#diaOrderId").val());
        // 订单明细主键
        $("#saveDtlId").val($(rowobj).attr("DTL_ID"));
        // 经销商价
        $("#saveUnitPrice").val($(rowobj).attr("UNIT_PRICE"));
        // 订单数量
        $("#saveOrderCount").val($obj.val());
        // 备注
        $("#saveRemarks").val($obj1.val());
        
        var $f = $("#diaSaveFm");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        //doNormalSubmit($f, diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
        sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
    }
    //配件保存回调
    function savePartCountCallBack(res){
        try {
            var selectedIndex = $("#tab-orderPartList").getSelectedIndex();
            var selectedRow = $("#tab-orderPartList").getSelectedRows()[0];
            selectedRow.find("td").eq(9).html(selectedRow.find("td").eq(9).find("input:first").val());
            selectedRow.find("td").eq(11).html(selectedRow.find("td").eq(11).find("input:first").val());
             $("#tab-orderPartList").updateResult(res,selectedIndex);
             $("#tab-orderPartList").clearRowEdit(selectedRow,selectedIndex);
             var orderCount = selectedRow.find("td").eq(8).text();
            var unitPrice = selectedRow.find("td").eq(6).text();
            var amount = parseInt(orderCount,10)*removeAmountFormat(unitPrice);
            selectedRow.find("td").eq(9).html("<div style='text-align:right;'>"+amountFormatNew(amount.toFixed(2))+"</div>");
            var rows = res.getElementsByTagName("ROW");
            if(rows && rows.length>0){
                var amount = getNodeText(rows[0].getElementsByTagName("ORDER_AMOUNT").item(0));
                $("#diaOrderAmount").val(amount);
                $("#divOrderAmount").html(amountFormatNew(amount)+"元");
                $("#divOrderAmount1").html(amountFormatNew(amount)+"元");
                $("#divOrderAmount2").html(amountFormatNew(amount)+"元");
            }

        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function isCount($obj)
    {
        var reg = /^\+?[1-9][0-9]*$/;
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
            return false;
        }
    }
    function diaOrderPartDeleteCallBack(res){
        try {
               $("#diaOrderAmount").val(0);
               $("#divOrderAmount").html(0.00+"元");
               $("#divOrderAmount1").html(0.00+"元");
               $("#divOrderAmount2").html(0.00+"元");
               orderPartSearch();
               var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/partOrderSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>