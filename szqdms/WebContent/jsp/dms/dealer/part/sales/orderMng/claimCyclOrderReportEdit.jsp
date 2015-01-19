<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.params.UserPara.UserParaConfigureVO"%>
<%@page import="com.org.framework.params.ParaManager"%>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%

    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100200");
    String paraValue1 = userPara.getParavalue1();
    String paraValue2 = userPara.getParavalue2();
    String paraValue3 = userPara.getParavalue3();
    String action = request.getParameter("action");
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgId = user.getOrgId();
    String orgName = user.getOrgDept().getOName();
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
            <div class="page">
            <div class="pageContent" style="width:99%;height:80%;">
                <form method="post" id="dia-fm-save" class="editForm" style="width:99%;">
                    <div align="left" id="div-tab-edit">
                        <table class="editTable" id="dia-tab-Edit">
                            <input type="hidden" id="diaOrderId" name="diaOrderId" datasource="ORDER_ID"/>
                            <input type="hidden" id="diaOrderNo" name="diaOrderNo" datasource=""/>
                            <input type="hidden" id="diaOrderAmount" name="diaOrderAmount" datasource="ORDER_AMOUNT"/>
                            <input type="hidden" id="diaProvinceName" name="diaProvinceName" datasource="PROVINCE_NAME"/>
                            <input type="hidden" id="diaCityName" name="diaCityName" datasource="CITY_NAME"/>
                            <input type="hidden" id="diaCountryName" name="diaCountryName" datasource="COUNTRY_NAME"/>
                            <input type="hidden" id="dia-orderType" name="dia-orderType" datasource="ORDER_TYPE" value="<%=DicConstant.DDLX_09%>"/>
                            <input type="hidden" id="dia-sbFaultAnalyseName" name="dia-sbFaultAnalyseName" datasource="SB_FAULT_ANALYSE_NAME"/>
                            <tr>
                                <td><label>订单编号：</label></td>
                                <td><div id="dia-ddbh">系统自动生成</div></td>
                                <td><label>订单类型：</label></td>
                                <td><div>三包急件订单</div></td>
                                <td><label>订单总金额：</label></td>
                                <td><div id="divOrderAmount"></div></td>
                            </tr>
                            
                            
                            <tr>
                             <td><label>VIN：</label></td>
                             <td><input type="text" id="dia-vin" name="dia-vin" datasource="SB_VIN" datatype="0,is_vin,17" value="" operation="like" title="(请输入后8位或者17位)"/></td>
                             <td><label>发动机号：</label></td>
                             <td><input type="text" id="dia-engine_no" name="dia-engine_no" datasource="SB_ENGINE_NO" datatype="0,is_fdjh,30" value="" operation="like" /></td>
                             <td><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin" >验&nbsp;&nbsp;证</button></div></div>
                             <div class="button"><div class="buttonContent"><button type="button" id="dia-recheckvin" >重新填写</button></div></div></td>
                         </tr>
                         <tr>
                                <td><label>车辆型号：</label></td>
                             <td><input type="text" id="dia-models_code" name="dia-models_code" datasource="SB_MODELS_CODE" datatype="0,is_null,300" readonly="readonly" /></td>
                             <td><label>购车日期：</label></td>
                             <td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="SB_BUY_DATE" value=""  readonly="readonly" /></td>
                             <td><label>行驶里程：</label></td>
                             <td><input type="text" id="dia-mileage" name="dia-mileage"  datasource="SB_MILEAGE" value="" datatype="0,is_digit,10" /></td>
                         </tr>
                         <tr >
                             <td><label>车主名称：</label></td>
                             <td><input type="text" id="dia-user_name" name="dia-user_name" datasource="SB_USER_NAME" value="" datatype="0,is_null,300" /></td>
                             <td><label>联系人：</label></td>
                             <td><input type="text" id="dia-link_man" name="dia-link_man" datasource="SB_LINK_MAN" value="" datatype="0,is_null,30" /></td>
                             <td><label>电话：</label></td>
                             <td><input type="text" id="dia-phone" name="dia-phone" datasource="SB_PHONE" value="" datatype="0,is_null,300" /></td>
                          </tr>
                          <tr>
                               <td><label>故障分析：</label></td>
                             <td><input type="text" id="dia-sbFaultAnalyseCode" name="dia-sbFaultAnalyseCode"  datatype="1,is_null,30" kind="dic" src="T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302702" datasource="SB_FAULT_ANALYSE_CODE"/></td>
                               <td><label>客户地址：</label></td>
                             <td><textarea id="dia-user_address" style="width: 150px; height: 30px;" datasource="SB_USER_ADDRESS" name="KHDZ" datatype="1,is_null,100"></textarea></td>
                             <td><label>本次故障日期：</label></td>
                             <td><input type="text" id="dia-fault_date" name="BCGZRQ" value="" datasource="SB_FAULT_DATE" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})"/></td>
                          </tr>
                            
                            
                            <tr>
                                <td><label>供货配件库：</label></td>
                                <td>
                                    <input type="hidden" id="diaWarehouseCode"  name="diaWarehouseCode" datasource="WAREHOUSE_CODE"/>
                                    <input type="hidden" id="diaWarehouseId" name="diaWarehouseId" datasource="WAREHOUSE_ID"/>
                                    <input type="text" id="diaWarehouseName" name="diaWarehouseName" datasource="WAREHOUSE_NAME" readOnly/>
                                </td>
                                <td><label>客户名称：</label></td>
                                <td colspan="3">
                                    <input type="text" style="width:86%;" id="dia-in-custormName"  name="dia-in-custormName" readOnly datasource="CUSTORM_NAME" datatype="1,is_null,1000"/>
                                </td>
                            </tr>
<!--                             <tr> -->
<!--                                 <td><label>运输方式：</label></td> -->
<!--                                 <td><input type="text" id=dia-trans-type name="dia-trans-type" datasource="TRANS_TYPE" readOnly datatype="1,is_null,30"/></td> -->
<!--                                 <td id="td-addrType1"><label>交货地点：</label></td> -->
<!--                                 <td colspan="3" id="td-addrType2"> -->
<%--                                     <input type="text" style="width:86%;" id="diaAddrType"  name="diaAddrType" datasource="ADDR_TYPE" dicWidth="500" kind="dic" src="T#PT_BA_TRANSPORT_ADDRESS:ADDR_TYPE:ADDRESS{PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,LINK_MAN,PHONE,ZIP_CODE}:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND ORG_ID =<%=orgId%> OR ORG_ID=99999999 ORDER BY ADDR_TYPE ASC"  datatype="0,is_null,300" value="" /> --%>
<!--                                 </td>     -->
<!--                             </tr> -->
<!--                             <tr id="tr-addr1" style="display:none;"> -->
<!--                                 <td><label>省：</label></td> -->
<!--                                 <td> -->
<!--                                     <input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" dicwidth="320" datasource="PROVINCE_CODE" datatype="0,is_null,30" value=""/> -->
<!--                                 </td> -->
<!--                                 <td><label>市：</label></td> -->
<!--                                 <td> -->
<!--                                     <input type="text" id="diaCityCode"  name="diaCityCode" kind="dic" dicwidth="320" datasource="CITY_CODE" datatype="0,is_null,100" value=""/> -->
<!--                                 </td> -->
<!--                                 <td><label>区县：</label></td> -->
<!--                                 <td> -->
<!--                                     <input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic" dicwidth="320" datasource="COUNTRY_CODE" datatype="0,is_null,100" value=""/> -->
<!--                                 </td> -->
<!--                             </tr> -->
<!--                             <tr id="tr-addr2" style="display:none;"> -->
<!--                                 <td><label>详细地址：</label></td> -->
<!--                                 <td colspan="3"><input type="text" style="width:86%;" id="diaDeliveryAddr"  name="diaDeliveryAddr" datasource="DELIVERY_ADDR" datatype="0,is_null,100" value=""/></td> -->
<!--                                 <td></td> -->
<!--                             </tr> -->
<!--                             <tr> -->
<!--                                 <td><label>联系人：</label></td> -->
<!--                                 <td><input type="text" id="diaLinkMan"  name="diaLinkMan" datasource="LINK_MAN" datatype="0,is_null,30" value=""/></td> -->
<!--                                 <td><label>联系方式：</label></td> -->
<!--                                 <td><input type="text" id="diaPhone"  name="diaPhone" datasource="PHONE" datatype="0,is_null,30" value=""/></td> -->
<!--                                 <td><label>邮政编码：</label></td> -->
<!--                                 <td><input type="text" id="diaZipCode"  name="diaZipCode" datasource="ZIP_CODE" datatype="1,is_digit,6" value=""/></td> -->
<!--                             </tr> -->
                            <tr>
                                <td><label>运输方式：</label></td>
                                <td>
                                    <input type="text" id=dia-trans-type name="dia-trans-type" datasource="TRANS_TYPE" disabled="true" filtercode="" datatype="1,is_null,30" operation="="/>
                                </td>
                                 <td><label>联系人：</label></td>
                                <td><input type="text" id="dia-in-linkMan"  name="dia-in-linkMan" datasource="LINK_MAN" datatype="0,is_null,30"/></td>
                                <td><label>联系方式：</label></td>
                                <td><input type="text" id="dia-in-phone"  name="dia-in-phone" datasource="PHONE" datatype="0,is_null,30"/></td>
                            </tr>
                            <tr>
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
<!--                                 <td><input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" datasource="PROVINCE_CODE" value="" datatype="0,is_null,300" /></td> -->
<!--                                 <td><label>市：</label></td> -->
<!--                                 <td><input type="text" id="diaCityCode" name="diaCityCode" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,300" /></td> -->
<!--                                 <td><label>区县：</label></td> -->
<!--                                 <td><input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic"  datasource="COUNTRY_CODE" value="" datatype="0,is_null,300" /></td> -->
                            </tr>
                            <tr>
                                <td><label>邮政编码：</label></td>
                                <td><input type="text" id="dia-in-zipCode"  name="dia-in-zipCode" datasource="ZIP_CODE" datatype="1,is_digit,15"/></td>
                                <td><label>详细地址：</label></td>
                                <td colspan="3"><input type="text" style="width:75%;" id="dia-in-deliveryAddr"  name="dia-in-deliveryAddr" datasource="DELIVERY_ADDR" datatype="0,is_null,100"/><span style="color:red;">（详细地址省市必填）</span></td>
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
                <form method="post" id="diaSaveFm">
                    <input type="hidden" id="saveOrderId" name="saveOrderId" datasource="ORDER_ID"/>
                    <input type="hidden" id="saveDtlId" name="saveDtlId" datasource="DTL_ID"/>
                    <input type="hidden" id="saveOrderCount" name="saveOrderCount" datasource="ORDER_COUNT"/>
                    <input type="hidden" id="saveUnitPrice" name="saveUnitPrice" datasource="UNIT_PRICE"/>
                </form>
                <div id="dia-pjmx" style="overflow:auto;">
                    <table style="display:none;width:80%;" id="tab-orderPartList" name="tablist" ref="dia-pjmx" edit="true">
                        <thead>
                            <tr>
                                <th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
                                <th fieldname="PART_CODE" freadonly="true">配件代码</th>
                                <th fieldname="PART_NAME" freadonly="true">配件名称</th>
                                <th fieldname="UNIT"  colwidth="50" freadonly="true">计量单位</th>
                                <th fieldname="" freadonly="true" colwidth="50" refer="appendPack_Unit">最小包装</th>
                                <th fieldname="UNIT_PRICE" freadonly="true"  colwidth="65" refer="formatAmount" align="right">经销商价(元)</th>
                                <th fieldname="SUPPLIER_NAME" refer="" freadonly="true">供应商</th>
                                <th fieldname="ORDER_COUNT" >订购数量</th>
                                <th fieldname="" freadonly="true" refer="acount" align="right">小计(元)</th>
                                <th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaPartSave|doDiaListDelete">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="formBar">
                <ul>
<!--                     <li><div class="button"><div class="buttonContent"><button type="button"  id="btnExp" name="btnExp">配件模版导出</button></div></div></li> -->
<!--                     <li><div class="button"><div class="buttonContent"><button type="button"  id="btnImp" name="btnImp">配件明细导入</button></div></div></li> -->
                    <li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
                    <li><div class="button"><div class="buttonContent"><button id="closeId" class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
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
    var diaUrl = "<%=request.getContextPath()%>/part/salesMng/orderMng/claimCyclOrderReportMngAction";
    $(function(){
        $("#dia-pjmx").attr("layoutH",document.documentElement.clientHeight-300);
        $("#div-tab-edit").attr("layoutH",document.documentElement.clientHeight-350);
        $("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
        if (action != "1") {
            // 修改操作
            var selectedRows = $("#tab-index").getSelectedRows();
            setEditValue("dia-tab-Edit",selectedRows[0].attr("rowdata"));
            
            $("#dia-sbFaultAnalyseCode").attr("code",selectedRows[0].attr("SB_FAULT_ANALYSE_CODE"));
            $("#dia-sbFaultAnalyseCode").val(selectedRows[0].attr("SB_FAULT_ANALYSE_NAME"));
            
            $("#dia-ddbh").html(selectedRows[0].attr("ORDER_NO"));
            var addrType=selectedRows[0].attr("ADDR_TYPE");
            // 订单总金额
            $("#divOrderAmount").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
            $("#divOrderAmount1").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
            $("#divOrderAmount2").html(amountFormatNew(selectedRows[0].attr("ORDER_AMOUNT"))+"元");
            // 供货配件库
//             $("#diaWarehouseCode").attr("code",selectedRows[0].attr("WAREHOUSE_CODE"));
//             $("#diaWarehouseCode").val(selectedRows[0].attr("WAREHOUSE_NAME"));
            if(addrType==<%=DicConstant.JHDDLX_01%>){
                $("#diaAddrType").attr("code",selectedRows[0].attr("ADDR_TYPE"));
                $("#diaAddrType").val(selectedRows[0].attr("DELIVERY_ADDR"));
//                 $("#diaProvinceCode").attr("readonly",true);
//                 $("#diaCityCode").attr("readonly",true);
//                 $("#diaCountryCode").attr("readonly",true);
                $("#diaLinkMan").attr("readonly",true);
                $("#diaPhone").attr("readonly",true);
            }else{
                $("#diaAddrType").attr("code",selectedRows[0].attr("ADDR_TYPE"));
                $("#diaAddrType").val("其他");
                $("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
                $("#tr-addr1").show();
                $("#tr-addr2").show();
            }
            // 省
            $("#diaProvinceCode").attr("code",selectedRows[0].attr("PROVINCE_CODE"));
            $("#diaProvinceCode").val(selectedRows[0].attr("PROVINCE_NAME"));
            // 市
            $("#diaCityCode").attr("code",selectedRows[0].attr("CITY_CODE"));
            $("#diaCityCode").val(selectedRows[0].attr("CITY_NAME"));
            // 区
            $("#diaCountryCode").attr("code",selectedRows[0].attr("COUNTRY_CODE"));
            $("#diaCountryCode").val(selectedRows[0].attr("COUNTRY_NAME"));
        } else {
            // 客户名称
            $("#dia-in-custormName").val("<%=orgName%>");
            // 发运方式(发运)
            $("#dia-trans-type").val("发运");
            $("#dia-trans-type").attr("code","<%=DicConstant.FYFS_01%>");

            $("#diaWarehouseId").val("<%=paraValue1%>");
            $("#diaWarehouseCode").val("<%=paraValue2%>");
            $("#diaWarehouseName").val("<%=paraValue3%>");
        }

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
                var insertUrl = diaUrl + "/insertClaimCyclOrder.ajax";
                var $f = $("#dia-fm-save");
                var sCondition = {};
                if (submitForm($f) == false) {
                    return false;
                }
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, insertUrl, "dia-save", sCondition, saveCallBack);
            } else {
                // 修改操作
                var insertUrl = diaUrl + "/updateClaimCyclOrder.ajax";
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
            importXls("PT_BU_SALE_ORDER_DTL_TMP",$("#diaOrderId").val(),5,3,"/jsp/dms/dealer/part/sales/orderMng/importSuccess.jsp");
        });

        //提报
        $("#dia-report").bind("click",function(){
            if($("#tab-orderPartList_content").find("tr").size()==0){
                alertMsg.warn("请添加配件.");
                return false;
            }
            var reportUrl = diaUrl + "/partOrderReport.ajax?&orderId="+$("#diaOrderId").val()+"&orderAmount="+$("#diaOrderAmount").val();
            sendPost(reportUrl, "dia-report", "", diaReportCallBack, "true");
        });

        //验证
        $("#dia-checkvin").bind("click",function(){
            var vinVal=$("#dia-vin").val();
            var engineVal=$("#dia-engine_no").val(); 
            if(vinVal==''){
                alertMsg.info("VIN不能为空！");
                return;
            }
            if(engineVal==''){
                alertMsg.info("发动机号不能为空！");
                return;
            }
            var options = {max : true,mask : true,mixable : true,minable : true,resizable : true,drawable : true};
            $.pdialog.open("<%=request.getContextPath()%>/jsp/dms/dealer/part/sales/orderMng/guaranteesHurryVinCheck.jsp?vinVal='"+vinVal+"'&engineVal='"+engineVal+"'","vinCheck", "定保单信息维护-校验车辆", options);
        });

        //重新填写
        $("#dia-recheckvin").bind("click",function(){
            alertMsg.confirm("清空车辆信息，确认重新填写?",{okCall:doConOk1,cancelCall:doConOk2});
        });
    });

  //验证VIN回调
    function checkVinCallBack(flag)
    {
        if(flag==1){
            $("#dia-vin").attr("readOnly",true);
            $("#dia-vin").addClass("readonly");
            $("#dia-engine_no").attr("readOnly",true);
            $("#dia-engine_no").addClass("readonly");
            $("#vehicleId").val($("#dia-di-vehicleId").val());//车辆ID
            $("#modelsId").val($("#dia-di-modelsId").val());//车型ID
            $("#dia-models_code").val($("#dia-di-models_code").val());//车型代码
            $("#dia-certificate").val($("#dia-di-certificate").val());//合格证
            $("#dia-engine_type").val($("#dia-di-engine_type").val());//发动机型号
            $("#dia-buy_date").val($("#dia-di-buy_date").val());//购车日期
            $("#dia-mileage").val($("#dia-di-mileage").val());//行驶里程
            $("#dia-user_name").val($("#dia-di-user_name").val());//用户名称
            $("#dia-user_no").val($("#dia-di-user_no").val());//身份证
            $("#dia-link_man").val($("#dia-di-link_man").val());//联系人
            $("#dia-phone").val($("#dia-di-phone").val());//电话
            $("#dia-user_address").text($("#dia-di-user_address").val());//地址
        }
        if(flag==2){
            vehEmpty();
            alertMsg.info("VIN与发动机号不存在！");
        }
        return true;
    }

  //车辆信息设置为空
    function vehEmpty(){
        $("#vehicleId").val("");//车辆ID
        $("#modelsId").val("");//车型ID
        $("#dia-models_code").val("");//车型代码
        $("#dia-certificate").val("");//合格证
        $("#dia-engine_type").val("");//发动机型号
        $("#dia-user_type").val("");//用户类型
        $("#dia-user_type_code").val("");//用户类型
        $("#dia-vehicle_use").val("");//车辆用途
        $("#dia-vehicle_use_code").val("");//车辆用途
        $("#dia-drive_form").val("");//驱动形式
        $("#dia-buy_date").val("");//购车日期
        $("#dia-mileage").val("");//行驶里程
        $("#dia-guarantee_no").val("");//保修卡
        $("#dia-factory_date").val("");//出厂日期
        $("#dia-maintenance_date").val("");//首保日期
        $("#dia-license_plate").val("");//车牌号
        $("#dia-user_name").val("");//用户名称
        $("#dia-user_no").val("");//身份证
        $("#dia-link_man").val("");//联系人
        $("#dia-phone").val("");//电话
        $("#dia-user_address").text("");//地址
        $("#dia-vin").val($("#dia-di-vin").val());
        $("#dia-engine_no").val($("#dia-di-engine_no").val());
    }

    //confirm返回true
    function doConOk1(){
        $("#dia-vin").removeClass("readonly");
        $("#dia-engine_no").removeClass("readonly");
        $("#dia-vin").attr("readOnly",false);
        $("#dia-engine_no").attr("readOnly",false);
        $("#dia-vin").val("");
        $("#dia-engine_no").val("");
        vehEmpty();
    }

    // confirm返回true  
    function doConOk2(){
        return false;
    }

    //提报回调
    function diaReportCallBack(res){
        try {
            // 查询方法
            searchDirectBusinessOrder();
            $("#closeId").click();
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        $.pdialog.closeCurrent();
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
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/sales/orderMng/claimCyclOrderPartSearch.jsp", "directBusinessOrderPartSearch", "配件信息查询", options);
    }

    //校验订购数量
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

    // 列表编辑
    function doDiaPartSave(rowobj){
        $rowpart = $(rowobj);
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        // 订单数量
        var $obj = $(rowobj).find("td").eq(8).find("input:first");
        if($obj.val()=="" || !isCount($obj)){
            alertMsg.warn("请正确输入订购数量！");
            return false;
        }
        // 配送中心提报
        var minPack= $rowpart.attr("MIN_PACK");
        if(($obj.val()%minPack)){
            alertMsg.warn("订购数量应为最小包装倍数,请重新输入.");
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
        
        var $f = $("#diaSaveFm");
        if (submitForm($f) == false) return false;
        var sCondition = {};
        sCondition = $f.combined(1) || {};
        sendPost(diaUrl + "/orderPartCountSave.ajax", "btnDiaSave", sCondition, savePartCountCallBack);
    }

    //配件保存回调
    function savePartCountCallBack(res){
        try {
            var selectedIndex = $("#tab-orderPartList").getSelectedIndex();
            var selectedRow = $("#tab-orderPartList").getSelectedRows()[0];
            selectedRow.find("td").eq(8).html(selectedRow.find("td").eq(8).find("input:first").val());
             $("#tab-orderPartList").updateResult(res,selectedIndex);
             $("#tab-orderPartList").clearRowEdit(selectedRow,selectedIndex);
             var orderCount = selectedRow.find("td").eq(8).text();
            var unitPrice = selectedRow.find("td").eq(6).text();
            var amount = parseInt(orderCount,9)*removeAmountFormat(unitPrice);
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
        // 选择省
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
        // 选择故障分析
        if(id == "dia-sbFaultAnalyseCode") {
            // 区名称
            $("#dia-sbFaultAnalyseName").val($("#dia-sbFaultAnalyseCode").val());
        }
        // 供货配件库选择
//         if (id== "diaWarehouseCode") {
//             $("#diaWarehouseId").val($row.attr("WAREHOUSE_ID"));
//             $("#diaWarehouseName").val($row.attr("WAREHOUSE_NAME"));
//         }
        
      //地址类型：小库不可自定义地址、大库自定义地址
        if(id=="diaAddrType"){
            if($("#diaAddrType").attr("code")!="<%=DicConstant.JHDDLX_01%>"){
            	$("#diaProvinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
                $("#tr-addr1").show();
                $("#tr-addr2").show();
                $("#diaProvinceCode").attr("readonly",false);
                $("#diaCityCode").attr("readonly",false);
                $("#diaCountryCode").attr("readonly",false);
                $("#diaLinkMan").attr("readonly",false);
                $("#diaPhone").attr("readonly",false);
                
            }else{
                $("#tr-addr1").hide();
                $("#tr-addr2").hide();
                $("#diaDeliveryAddr").val($("#diaAddrType").val());
                if($row.attr("PROVINCE_CODE")&&$row.attr("PROVINCE_NAME")){
                    $("#diaProvinceCode").attr("code",$row.attr("PROVINCE_CODE"));
                    $("#diaProvinceCode").val($row.attr("PROVINCE_NAME"));
                    $("#diaProvinceCode").attr("readonly",true);
                    $("#diaProvinceName").val($row.attr("PROVINCE_NAME"));
                }
                if($row.attr("CITY_CODE")&&$row.attr("CITY_NAME")){
                    $("#diaCityCode").attr("code",$row.attr("CITY_CODE"));
                    $("#diaCityCode").val($row.attr("CITY_NAME"));
                    $("#diaCityCode").attr("readonly",true);
                    $("#diaCityName").val($row.attr("CITY_NAME"));
                }
                if($row.attr("COUNTRY_CODE")&&$row.attr("COUNTRY_NAME")){
                    $("#diaCountryCode").attr("code",$row.attr("COUNTRY_CODE"));
                    $("#diaCountryCode").val($row.attr("COUNTRY_NAME"));
                    $("#diaCountryCode").attr("readonly",true);
                    $("#diaCountryName").val($row.attr("COUNTRY_NAME"));
                }
                if($row.attr("LINK_MAN")){
                    $("#diaLinkMan").val($row.attr("LINK_MAN"));
                    $("#diaLinkMan").attr("readonly",true);
                }
                if($row.attr("PHONE")){
                    $("#diaPhone").val($row.attr("PHONE"));
                    $("#diaPhone").attr("readonly",true);
                }
                if($row.attr("ZIP_CODE")){
                    $("#diaZipCode").val($row.attr("ZIP_CODE"));
                }
            }
        }

        return true;
    }
</script>