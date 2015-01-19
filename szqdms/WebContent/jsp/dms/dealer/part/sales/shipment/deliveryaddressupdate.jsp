<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.framework.Globals"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgId = user.getOrgId();
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
    <div class="page">
    <div class="pageContent" style="" >
            <form method="post" id="dib-fm-deliveryaddr" class="editForm" >
            <fieldset>
                <table class="editTable" id="order_info">
                    <input type="hidden" id="diaProvinceName" name="diaProvinceName" datasource="PROVINCE_NAME"/>
                    <input type="hidden" id="diaCityName" name="diaCityName" datasource="CITY_NAME"/>
                    <input type="hidden" id="diaCountryName" name="diaCountryName" datasource="COUNTRY_NAME"/>
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td>
                            <input type="text" id="in-orderNo" name="in-orderNo" readonly datasource="ORDER_NO" datatype="0,is_null,30" readOnly hasBtn="true" callFunction="openOrder()"/>
                            <input type="hidden"  id="in-addId" name="in-addId" datasource="ADD_ID"/>
                            <input type="hidden"  id="in-orderId" name="in-orderId" datasource="ORDER_ID"/>
                            <input type="hidden" id="in-provinceCode" name="in-provinceCode" readonly  datasource="PROVINCE_CODE" datatype="1,is_null,30" />
                        </td>
                        <td><label>订单类型：</label></td>
                        <td><input type="text" id="in-orderType" name="in-orderType" readonly  datasource="ORDER_TYPE" datatype="1,is_null,30" /></td>
                    </tr>
                    <tr>
                        <td><label>提报日期：</label></td>
                        <td><input type="text" id="in-applyDate"  name="in-applyDate" readonly style="width:75px;" datasource="APPLY_DATE" datatype="1,is_null,30"/></td>
                        <td><label>交货地址：</label></td>
                        <td><input type="text" id="in-oldaddr" name="in-oldaddr" readonly datasource="DELIVERY_ADDR" datatype="1,is_null,600"  style="width: 300px;"/></td>
                    </tr>
                    <tr>
                        <td><label>省：</label></td>
                        <td><input type="text" id="diaProvinceCode" name="diaProvinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" datasource="PROVINCE_CODE" value="" datatype="0,is_null,30" /></td>
                        <td><label>市：</label></td>
                        <td><input type="text" id="diaCityCode" name="diaCityCode" kind="dic" datasource="CITY_CODE" value="" datatype="0,is_null,30" /></td>
                    </tr>
                    <tr>
                        <td><label>区县：</label></td>
                        <td><input type="text" id="diaCountryCode" name="diaCountryCode" kind="dic"  datasource="COUNTRY_CODE" value="" datatype="0,is_null,30" /></td>
                        <td><label>新交货地址：</label></td>
                        <td><input type="text"  id="in-address" name="in-address" datasource="ADDRESS" style="width: 300px;" datatype="0,is_null,600"/></td>
                    </tr>
                    <tr>
                        <td><label>邮编：</label></td>
                        <td><input type="text" id="in-zipCode" name="in-zipCode" datasource="ZIP_CODE" datatype="0,is_digit,30" /></td>
                        <td><label>联系人：</label></td>
                        <td><input type="text" id="in-linkMan" name="in-linkMan" datasource="LINK_MAN" datatype="0,is_null,30" /></td>
                    </tr>
                    <tr>
                        <td><label>手机：</label></td>
                        <td><input type="text" id="in-phone" name="in-phone" datasource="PHONE" datatype="0,is_phone,30" /></td>
                        <td><label>固定电话：</label></td>
                        <td><input type="text" id="in-telephone" name="in-telephone" datasource="TELEPHONE" datatype="0,is_null,30" /></td>
                    </tr>
                </table>
                </fieldset>
            </form>
            <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-apply">提&nbsp;&nbsp;报</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
            </div>
        </div>
        </div>
</div>
<script type="text/javascript">
    var deliveryAddr = $("body").data("deliveryaddressupdate");
    var diaSaveAction = "<%=request.getContextPath()%>/part/salesMng/shipMent/DeliveAddChageAction";
    var action=<%=action%>;
    //初始化方法
    $(function(){

        if (action != '1') {
            // 修改操作
            var selectedRows = $("#tab-contract").getSelectedRows();
            setEditValue("dib-fm-deliveryaddr",selectedRows[0].attr("rowdata"));
            $("#in-applyDate").val(selectedRows[0].attr("APPLY_DATE_sv"));
            //省
            $("#diaProvinceCode").attr("code",selectedRows[0].attr("PROVINCE_CODE"));
            $("#diaProvinceCode").val(selectedRows[0].attr("PROVINCE_NAME"));
            // 市
            $("#diaCityCode").attr("code",selectedRows[0].attr("CITY_CODE"));
            $("#diaCityCode").val(selectedRows[0].attr("CITY_NAME"));
            // 区
            $("#diaCountryCode").attr("code",selectedRows[0].attr("COUNTRY_CODE"));
            $("#diaCountryCode").val(selectedRows[0].attr("COUNTRY_NAME"));
        }

        // 保存按钮绑定方法
        $("#dia-save").bind("click",function(event){

            if (action == '1') {
                // 获取需要提交的form对象
                var $f = $("#dib-fm-deliveryaddr");
                if (submitForm($f) == false) {
                    return false;
                }
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $("#dib-fm-deliveryaddr").combined(1) || {};
                var updateUrl = diaSaveAction + "/deliveryaddrAdd.ajax";
                doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
            } else {
                // 获取需要提交的form对象
                var $f = $("#dib-fm-deliveryaddr");
                if (submitForm($f) == false) {
                    return false;
                }
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $("#dib-fm-deliveryaddr").combined(1) || {};
                var updateUrl = diaSaveAction + "/deliveryaddrUpdate.ajax";
                doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
            }
        });

        
        // 提报按钮绑定方法
        $("#dia-apply").bind("click",function(event){
            var url = diaSaveAction + "/deliveryaddrApply.ajax" + "?addId=" + $('#in-addId').val();
            sendPost(url, "", "", doApplyCallBack, "true");
        });
    });

    // 提报按钮回调函数
    function doApplyCallBack(res) {
        // 查询收货地址
        deliveryaddresscg();
        $.pdialog.closeCurrent();
        return true;
    }

    // 订单查询
    function openOrder(){
        var options = {max: false, width: 900, height: 500, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/sales/shipment/searchOrder.jsp", "searchOrder", "订单查询", options);
    }

    //修改回调
    function diaUpdateCallBack(res){
        try {
            $.pdialog.close(deliveryAddr);
            $("#btn-search").trigger("click");
        } catch(e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 数据字典回调函数
    function afterDicItemClick(id,$row){
        // 选择省
        if(id == "diaProvinceCode") {
            $("#diaCityCode").attr("src","XZQH");
            $("#diaCityCode").attr("isreload","true");
            $("#diaCityCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
            // 省名称
            $("#diaProvinceName").val($("#diaProvinceCode").val());
        }
        // 选择市
        if(id == "diaCityCode") {
            $("#diaCountryCode").attr("src","XZQH");
            $("#diaCountryCode").attr("isreload","true");
            $("#diaCountryCode").attr("dicwidth","300");
            $("#diaCountryCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
            // 市名称
            $("#diaCityName").val($("#diaCityCode").val());
        }
        
        // 选择区
        if(id == "diaCountryCode") {
            // 区名称
            $("#diaCountryName").val($("#diaCountryCode").val());
        }
        return true;
    }
</script>