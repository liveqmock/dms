<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="layout" style="width:100%;">
    <div class="page">
        <div class="pageHeader" >
            <form method="post" id="dib-fm-deliveryaddr-nopa">
                <input type="hidden" id="add_id" name="add_id" datasource="ADD_ID"/>
            </form>
            <form method="post" id="dib-fm-deliveryaddr-pa">
                <input type="hidden" id="pa-add_id" name="pa-add_id" datasource="ADD_ID"/>
                <input type="hidden" id="order_id" name="order_id" datasource="ORDER_ID"/>
                <input type="hidden" id="delivery_city" name="delivery_city" datasource="DELIVERY_CITY"/>
                <input type="hidden" id="city_name" name="city_name" datasource="CITY_NAME"/>
                <input type="hidden" id="phone" name="phone" datasource="PHONE"/>
                <input type="hidden" id="zip_code" name="zip_code" datasource="ZIP_CODE"/>
                <input type="hidden" id="link_man" name="link_man" datasource="LINK_MAN"/>
                <input type="hidden" id="delivery_addr" name="delivery_addr" datasource="DELIVERY_ADDR"/>
                <input type="hidden" id="addr_type" name="addr_type" datasource="ADDR_TYPE" value="<%=DicConstant.JHDDLX_01%>"/>
            </form>
            <form method="post" id="dib-fm-deliveryaddr">
                <input  type="hidden"  id="add_id" name="add_id" datasource="ADD_ID"  />
                <table class="editTable" id="order_info">
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td>
                            <input type="text" id="in-orderNo" name="in-orderNo" readonly datasource="ORDER_NO" datatype="1,is_null,30" readOnly/>
                            <input type="hidden"  id="in-addId" name="in-addId" datasource="ADD_ID"/>
                            <input type="hidden"  id="in-orderId" name="in-orderId" datasource="ORDER_ID"/>
                            <input type="hidden" id="in-provinceCode" name="in-provinceCode" readonly  datasource="PROVINCE_CODE" datatype="1,is_null,30" />
                        </td>
                        <td><label>订单类型：</label></td>
                        <td><input type="text" id="in-orderType" name="in-orderType" readonly  datasource="ORDER_TYPE" datatype="1,is_null,30" /></td>
                    </tr>
                    <tr>
                        <td><label>订单提报日期：</label></td>
                        <td><input type="text" id="in-kstbrq"  name="in-kstbrq" readonly style="width:75px;" datasource="APPLY_DATE" datatype="1,is_null,30"/></td>
                        <td><label>现交货地址：</label></td>
                        <td><input type="text" id="in-oldaddr" name="in-oldaddr" readonly datasource="DELIVERY_ADDR" datatype="1,is_null,600"  style="width: 300px;"/></td>
                    </tr>
                    <tr>
                        <td><label>省名称：</label></td>
                        <td><input type="text" id="in-provinceName" name="in-provinceName" readonly  datasource="PROVINCE_NAME" datatype="1,is_null,30" /></td>
                        <td><label>市名称：</label></td>
                        <td><input type="text" id="in-provinceName" name="in-cityName" readonly  datasource="CITY_NAME" datatype="1,is_null,30" /></td>
                    </tr>
                    <tr>
                        <td><label>区县名称：</label></td>
                        <td><input type="text" id="in-provinceName" name="in-countryName" readonly  datasource="COUNTRY_NAME" datatype="1,is_null,30" /></td>
                        <td><label>新交货地址：</label></td>
                        <td>
                            <input type="text" id="in-address" name="in-address" readonly datasource="ADDRESS" datatype="1,is_null,600" style="width: 300px;" operation="like"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>邮编：</label></td>
                        <td><input type="text" id="in-zipCode" name="in-zipCode" readonly  datasource="ZIP_CODE" datatype="1,is_null,30" /></td>
                        <td><label>联系人：</label></td>
                        <td><input type="text" id="in-linkMan" name="in-linkMan" readonly  datasource="LINK_MAN" datatype="1,is_null,30" /></td>
                    </tr>
                        <td><label>手机：</label></td>
                        <td><input type="text" id="in-phone" name="in-phone" readonly  datasource="PHONE" datatype="1,is_null,30" /></td>
                        <td><label>固定电话：</label></td>
                        <td><input type="text" id="in-telephone" name="in-telephone" readonly  datasource="TELEPHONE" datatype="1,is_null,30" /></td>
                    <tr>
                        
                    </tr>
                </table>
            </form>
            <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save_pa">通&nbsp;&nbsp;过</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save_nopa">驳&nbsp;&nbsp;回</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
            </div>
        </div>
</div>
</div>
<script type="text/javascript">
    var deliveryAddr = $("body").data("deliveryaddrExaupdate");
    var diaSaveAction = "<%=request.getContextPath()%>/part/salesMng/shipMent/DeliveAddChageAction";
    // 初始化方法
    $(function(){
        var selectedRows = $("#tab-contract").getSelectedRows();
        // 显示table设置
        setEditValue("dib-fm-deliveryaddr",selectedRows[0].attr("rowdata"));
        // 驳回表单设置
        $("#add_id").val($("#in-addId").val());

        // 通过表单设置
        $("#pa-add_id").val(selectedRows[0].attr("ADD_ID"));
        $("#order_id").val(selectedRows[0].attr("ORDER_ID"));
        $("#delivery_city").val(selectedRows[0].attr("PROVINCE_CODE"));
        $("#city_name").val(selectedRows[0].attr("PROVINCE_NAME"));
        $("#phone").val(selectedRows[0].attr("PHONE"));
        $("#zip_code").val(selectedRows[0].attr("ZIP_CODE"));
        $("#link_man").val(selectedRows[0].attr("LINK_MAN"));
        $("#delivery_addr").val(selectedRows[0].attr("ADDRESS"));
    });

    // 通过按钮绑定方法
    $("#dia-save_pa").bind("click",function(event){
        //获取需要提交的form对象
        var $f = $("#dib-fm-deliveryaddr-pa");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $("#dib-fm-deliveryaddr-pa").combined(1) || {};
        var updateUrl = diaSaveAction + "/deliveryaddrExaPassupdate.ajax";
        doNormalSubmit($f,updateUrl,"dia-save_pa",sCondition,diaUpdateExaCallBack);
    });

    // 驳回按钮绑定方法
    $("#dia-save_nopa").bind("click",function(event){
        //获取需要提交的form对象
        var $f = $("#dib-fm-deliveryaddr-nopa");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $("#dib-fm-deliveryaddr-nopa").combined(1) || {};
        var updateUrl = diaSaveAction + "/deliveryaddrExaNoPassupdate.ajax";
        doNormalSubmit($f,updateUrl,"dia-save_nopa",sCondition,diaUpdateExaCallBack);
    });

    //修改回调
    function diaUpdateExaCallBack(res){
        try {
            // 收货地址变更查询
            searchDeliveryAddrss();
            $.pdialog.close(deliveryAddr);
        } catch(e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

</script>