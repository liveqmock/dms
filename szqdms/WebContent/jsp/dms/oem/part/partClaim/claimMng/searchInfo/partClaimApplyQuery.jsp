<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包申请查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关&gt;配件三包申请查询</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="exportFm" method="post" style="display:none">
           <input type="hidden" id="data" name="data"></input>
        </form>
        <form id="fm-index" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="pjsbsqTable">
                    <tr>
                        <td><label>三包申请单号：</label></td>
                        <td><input type="text" id="applyNo-index" name="applyNo-index" datatype="1,is_null,100" datasource="APPLY_NO" operation="like"/></td>
                        <td><label>状态：</label></td>
                        <td><select type="text" id="applyStatus-index" name="applyStatus-index" datatype="1,is_null,30" filtercode="<%=DicConstant.PJSBSPZT_02%>|<%=DicConstant.PJSBSPZT_03%>|<%=DicConstant.PJSBSPZT_04%>|<%=DicConstant.PJSBSPZT_05%>" kind="dic" class="combox" datasource="APPLY_STATUS" src="PJSBSPZT">
                                <option value=-1 selected>--</option>
                            </select>
                        </td>
                        <td><label>渠道商：</label></td>
                        <td><input type="text" id="dia-orgName" name="dia-orgName" datasource="ORG_CODE" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,1,2)" operation="="/></td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode-index" name="partCode-index" datatype="1,is_null,100" datasource="PART_CODE" operation="like"/></td>
                        <td><label>配件名称：</label></td>
                        <td colspan="3"><input type="text" id="partName-index" name="partName-index" datatype="1,is_null,100" datasource="PART_NAME" operation="like" /></td>
                    </tr>
                    <tr>
						<td><label>申请时间：</label></td>
						<td>
							<input type="text" id="APPLY_DATE_B" name="APPLY_DATE_B" style="width: 75px;" datasource="APPLY_DATE" datatype="1,is_null,30"
                            onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                            <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                            <input type="text" id="APPLY_DATE_E" name="APPLY_DATE_E" style="width: 75px; margin-left: -30px;"
                            datasource="APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                        </td>
						<td><label>审核日期：</label></td>
						<td colspan="3">
							<input type="text" id="CHECK_DATE_B" name="CHECK_DATE_B" style="width: 75px;" datasource="CHECK_DATE" datatype="1,is_null,30"
                            onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                            <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                            <input type="text" id="CHECK_DATE_E" name="CHECK_DATE_E" style="width: 75px; margin-left: -30px;"
                            datasource="CHECK_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导出数据</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid">
            <table width="100%" id="tab-index" ref="page_grid" style="display:none" >
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="APPLY_NO" refer="showLink">三包申请单号</th>
                        <th fieldname="APPLY_STATUS">状态</th>
                        <th fieldname="ORG_CODE">申请单位编号</th>
                        <th fieldname="ORG_NAME">申请单位名称</th>
                        <th fieldname="CHECK_ORG_NAME_1">审核部门</th>
                        <th fieldname="IN_ORDER_NO">入库单号</th>
                        <th fieldname="OUT_ORDER_NO">出库单号</th>
                        <th fieldname="SOURCE_IN_NO">经销商入库单号</th>
                        <th fieldname="SOURCE_OUT_NO">经销商出库单号</th>
                        <th fieldname="PART_CODE">配件编号</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="UNIT">单位</th>
                        <th fieldname="CLAIM_COUNT">数量</th>
                        <th fieldname="SALE_PRICE">经销商价</th>
                        <th fieldname="AMOUNT">合计金额</th>
                        <th fieldname="SUPPLIER_NAME">生产厂家</th>
                        <th fieldname="CUSTOMER_NAME">购货单位</th>
                        <th fieldname="LINK_MAN">联系人</th>
                        <th fieldname="PHONE">联系电话</th>
                        <th fieldname="FAULT_CONDITONS">故障情况</th>
                        <th fieldname="REMARKS">经销商意见</th>
                        <th fieldname="CHECK_REMARK_1">配送中心意见</th>
                        <th fieldname="CHECK_REMARK_2">配件销售科意见</th>
                        <th fieldname="APPLY_DATE">申请时间</th>
                        <th fieldname="OUT_DATE">出库时间</th>
                        <th fieldname="CHECK_DATE_3">作废时间</th>
                        <th fieldname="APPLY_DATE">提交至配送中心时间</th>
                        <th fieldname="CHECK_DATE_1">提交至配件销售科时间</th>
                        <th fieldname="CHECK_DATE_1">初审通过时间</th>
                        <th fieldname="CHECK_USER_1">初审人</th>
                        <!-- <th fieldname="APPLY_NO">旧件标签打印时间</th> -->
                        <th fieldname="CHECK_DATE_2">终审时间</th>
                        <th fieldname="CHECK_USER_2">终审人</th>
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
<script type="text/javascript">

     $(function(){

        // 查询按钮响应方法
        $("#btn-search-index").click(function(){
            // 查询配件三包申请
            searchClaimCyclApply();
        });

        // 导出按钮绑定
        $("#btn-export-index").click(function(){
            var $f = $("#fm-index");
            if (submitForm($f) == false) {
                return false;
            }
            var sCondition = {};
            sCondition = $f.combined() || {};
            $("#data").val(sCondition);
            var url = encodeURI("<%=request.getContextPath()%>/part/partClaimMng/claimCycl/searchInfo/ClaimCyclApplyQueryMngAction/download.do");
            window.location.href = url;
            $("#exportFm").attr("action",url);
            $("#exportFm").submit();
        });
    });

     // 查询配件三包申请
     function searchClaimCyclApply() {
         var searchclaimCyclApply = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/searchInfo/ClaimCyclApplyQueryMngAction/searchClaimCyclApplyOem.ajax";
         var $f = $("#fm-index");
         var sCondition = {};
         sCondition = $f.combined() || {};
         doFormSubmit($f, searchclaimCyclApply, "btn-search-index", 1, sCondition, "tab-index");
     }

    
  //金额格式化
    function amountFormat(obj){
        return amountFormatNew($(obj).html());
    }
    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("APPLY_ID")+") class='op'>"+$row.attr("APPLY_NO")+"</a>";
    }
    function openDetail(APPLY_ID){
    	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    	$.pdialog.open(webApps+"/jsp/dms/common/partClaimApply.jsp?APPLY_ID="+APPLY_ID, "claimCyclCheckEdit", "三包申请单明细", options,true);
    }

</script>
</html>