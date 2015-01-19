<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.framework.Globals"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgType = user.getOrgDept().getOrgType();
    String orgName = user.getOrgDept().getOName();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>技术升级订单</title>
<script type="text/javascript">
    // 查询提交方法
    var url = "<%=request.getContextPath()%>/part/salesMng/orderMng/TechnologyUpgradesOrderReportCCMngAction";
    // 初始化方法
    $(function(){

//     	$("#orgOrder").attr("code","10010");
//     	$("#orgOrder").val("是");
        // 查询按钮绑定
        $("#btn-search").bind("click",function(event){
            // 查询方法
            searchDirectBusinessOrder();
        });

        // 新增方法
        $("#btn-xz").bind("click",function(event){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/technologyUpgradesOrderReportEdit.jsp?action=1", "ddxxwh", "订单信息新增", options);
        });
    });

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询技术升级订单
    function searchDirectBusinessOrder() {
        var $f = $("#fm-index");
        var sCondition = {};
        if (submitForm($f) == false) {
            return false;
        }
        sCondition = $f.combined() || {};
        doFormSubmit($f, url+"/dealerSearchClaimCyclOrder.ajax?flag=false", "btn-search", 1, sCondition, "tab-index");
    }

    // 提报方法
    function doReport(rowobj){
        $row = $(rowobj);
        if($row.attr("ORDER_AMOUNT").length==0){
            alertMsg.warn("请添加订单所需订购的配件信息后,再进行提报.");
            return false;
        }
        var reportUrl = url + "/partOrderReport.ajax?&orderId="+$row.attr("ORDER_ID")+"&orderType="+"&orderAmount="+$row.attr("ORDER_AMOUNT")+"&ifSuper="+$row.attr("IF_SUPER");
        sendPost(reportUrl, "", "",reportCallBack, "true");
    }

    // 提报回调方法
    function reportCallBack(res) {
        try {
            if ($row)
                $("#tab-index").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }

    // 列表编辑链接
    function doUpdate(rowobj) {
        $("td input[type=radio]",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/technologyUpgradesOrderReportEdit.jsp?action=2", "ddxxwh", "订单信息维护", options);
    }
    
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("ORDER_ID")+") class='op'>"+$row.attr("ORDER_NO")+"</a>";
    }
    function openDetail(ORDER_ID){
    	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
    }

    // 数据字典回调函数
    function afterDicItemClick(id,$row){
//         if(id=="orgOrder"){
//         	// 是
<%--         	if ($("#orgOrder").attr("code")==<%=DicConstant.SF_01%>) { --%>
//         		$("#orgIdSf1").show();
// 	        	$("#orgIdSf2").show();
//         	} else {
// 	        	$("#orgIdSf1").hide();
// 	        	$("#orgIdSf2").hide();
//         	}
//         }
        if(id=="ifSuper_Edit"){
            // 提渠道订单
        	if ($("#ifSuper_Edit").attr("code")==<%=DicConstant.SF_01%>) {
        		$("#orgIdSf_Edit1").show();
	        	$("#orgIdSf_Edit2").show();
	        	$("#dia-in-custormName").val("");
	        	// 渠道名称
	        	$("#diaOrgName").val("");
	        	// 渠道代码
	        	$("#diaOrgCode").val("");
	        	// 渠道ID
	        	$("#diaOrgId").val("");
	        	$("#dia-trans-type").attr("code",<%=DicConstant.FYFS_01%>);
	        	$("#dia-trans-type").val("发运");
	        	$("#trans1").show();
                $("#trans2").show();
        	} else {
        		$("#dia-in-custormName").val("<%=orgName%>");
	        	$("#orgIdSf_Edit1").hide();
	        	$("#orgIdSf_Edit2").hide();
	        	$("#dia-trans-type").attr("code",<%=DicConstant.FYFS_02%>);
	        	$("#dia-trans-type").val("自提");
	        	$("#trans1").hide();
                $("#trans2").hide();
        	}
        }
    	// 发运方式选择
        if(id == "dia-trans-type") {
            if($("#dia-trans-type").attr("code") == <%=DicConstant.FYFS_01%>) {
                $("#trans1").show();
                $("#trans2").show();
            } else {
                $("#trans1").hide();
                $("#trans2").hide();
            }
            
        }
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
        // 供货配件库选择
        if (id== "diaWarehouseCode") {
            $("#diaWarehouseId").val($row.attr("WAREHOUSE_ID"));
            $("#diaWarehouseName").val($row.attr("WAREHOUSE_NAME"));
        }
        return true;
    }
    
// 组织树的回调函数
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#diaOrgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#diaOrgCode").val($(res).attr("orgCode"));
	// 渠道ID
	$("#diaOrgId").val($(res).attr("orgId"));
	// 客户名称
	$("#dia-in-custormName").val($(res).attr("orgName"));
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 订单管理   &gt; 技术升级订单</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>订单编号：</label></td>
                        <td><input type="text" id="orderNo" name="orderNo" datasource="B.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>订单状态：</label></td>
                        <td>
                            <select type="text" id="orderStatus"  name="orderStatus" datasource="B.ORDER_STATUS" kind="dic" src="DDZT" filtercode="202201|202204" datatype="1,is_null,30" >
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                        <td><label>提报日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="B.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="B.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
<!--                     <tr> -->
<!-- 	                    <td><label>是否渠道订单：</label></td> -->
<!-- 	                    <td><input type="text" id="orgOrder" style="width:80px;" name="orgOrder" kind="dic" src="SF" datasource="IF_TRANS" datatype="0,is_null,30"/></td> -->
<!-- 	                    <td id="orgIdSf1"><label>渠道商：</label></td> -->
<!-- 	                    <td id="orgIdSf2"><input type="text" id="dia-orgCode" name="dia-orgCode" datasource="" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgCode',1,1,2)" operation="="/></td> -->
<!--                     </tr> -->
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index" >
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-pjcx" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="ORDER_NO" ordertype='local' class="desc" colwidth="135" refer="showLink">订单编号</th>
                            <th fieldname="ORDER_TYPE" >订单类型</th>
                            <th fieldname="WAREHOUSE_NAME" >供货配件库</th>
<!--                             <th fieldname="EXPECT_DATE" >期望到货日期</th> -->
                            <th fieldname="ORDER_AMOUNT" align="right" refer="formatAmount">总金额(元)</th>
                            <th fieldname="ORDER_STATUS" >订单状态</th>
                            <th fieldname="CREATE_USER" >提报人</th>
                            <th fieldname="APPLY_DATE" colwidth="130">提报时间</th>
                            <th colwidth="85" type="link" title="[提报]|[编辑]"  action="doReport|doUpdate" >操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
            </table>
        </div>
    </div>
    </div>
</div>
</body>
</html>