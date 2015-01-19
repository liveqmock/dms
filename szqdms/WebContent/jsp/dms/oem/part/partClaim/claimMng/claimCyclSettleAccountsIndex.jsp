<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包结算</title>
<script type="text/javascript">

    // 初始化方法
    $(function(){

        // 查询配件三包审核
        searchApply();
        // 查询按钮响应方法
        $("#btn-search-index").click(function(){
            // 查询配件三包审核
            searchApply();
        });

        $("#btn-reset").bind("click", function(event){
    		$("#fm-index")[0].reset();
    		$("#orgCode-index").attr("code","");
    		$("#orgCode-index").val("");
    	});
        // 结算按钮响应方法
        $("#btn-settleAccounts-index").click(function(){
            var insertPromPartUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclSettleAccountsMngAction/updateClaimCyclApply.ajax?IDS="+$('#edit-val0').val();
            $('#ids').val($('#edit-val0').val());
            var $f = $('#fm-partInfo');
            var sCondition = {};
            sCondition = $f.combined() || {};
            doNormalSubmit($f, insertPromPartUrl, "btn-settleAccounts-index", 1, sCondition, doApplyCallBack);
            // 查询配件三包审核
            
        });
    });

    function doApplyCallBack(){
    	searchApply();
    }
    // 查询配件三包审核
    function searchApply() {
        var searchclaimCyclApply = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclSettleAccountsMngAction/searchClaimCyclApply.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchclaimCyclApply, "btn-search-index", 1, sCondition, "tab-index");
    }

    function showCountBtn(obj){
        obj.html("<a href='#' onclick='doDetail(this)' class='op'>" + obj.html() + "</a>");
    }

    // 配件三包明细
    function doDetail(obj){
        var $obj = $(obj);
        var $tr = $obj.parent().parent().parent();
        var applyId = $tr.attr("APPLY_ID");
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/partClaim/claimMng/claimCyclSettleAccountsDtl.jsp?applyId="+applyId, "claimCyclSettleAccountsDtl", "配件三包明细", options);
    }

    // CHECKBOX回调方法
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var arr = [];
        arr.push($tr.attr("APPLY_ID"));
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        multiSelected($checkbox, arr,$('#div-selectedPart-Edit'));
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包结算</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-index" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="pjsbjsTable">
                    <tr>
                        <td><label>渠道商：</label></td>
                        <td><input type="text" id="orgCode-index" name="orgCode-index" datatype="1,is_null,100" datasource="ORG_CODE" readOnly hasBtn="true" callFunction="showOrgTree('orgCode-index',1,1,1);" readonly="true" operation="in"/></td>
                        <td><label>三包申请单号：</label></td>
                        <td><input type="text" id="applyNo-index" name="applyNo-index" datatype="1,is_null,100" datasource="APPLY_NO" operation="like"/></td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode-index" name="partCode-index" datatype="1,is_null,100" datasource="PART_CODE" operation="like"/></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="partName-index" name="partName-index" datatype="1,is_null,100" datasource="PART_NAME" operation="like"/></td>
                        <td><label>审核人：</label></td>
                        <td><input type="text" id="checkUser-index" name="checkUser-index" datatype="1,is_null,100" datasource="CHECK_USER" operation="like"/></td>
                    </tr>
                    <tr>
                        <td><label>提报日期：</label></td>
                        <td><input type="text" id="applyDate-index" name="applyDate-index" datatype="1,is_null,100" datasource="APPLY_DATE" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" operation="like"/></td>
                        <td><label>审核日期：</label></td>
                        <td><input type="text" id="checkDate-index" name="checkDate-index" datatype="1,is_null,100" datasource="CHECK_DATE" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" operation="like"/></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-settleAccounts-index">结&nbsp;&nbsp;算</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_index">
            <table width="100%" id="tab-index" style="display: none" ref="page_index" >
                <thead>
                    <tr>
                        <th type="multi" name="XH" unique="APPLY_ID"></th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="APPLY_NO" >三包申请单号</th>
                        <th fieldname="CUSTOMER_NAME">购货单位</th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="CLAIM_COUNT">数量</th>
                        <th fieldname="AMOUNT" align="right">金额</th>
                        <th fieldname="CHECK_USER">审核人</th>
                        <th fieldname="CHECK_DATE">审核日期</th>
                        <th fieldname="APPLY_DATE">提报日期</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <fieldset id="fie-selectedPart" style="display:">
            <legend align="left" >&nbsp;[已选定单号]</legend>
            <div id="div-selectedPart-Edit">
                <table style="width:100%;">
                    <tr>
                        <td>
                            <textarea style="width:80%;display:none" id="edit-val0" column="1" name="multivals" readOnly></textarea>
                            <textarea style="width:80%;display:" id="edit-val1" name="multivals" readOnly></textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
        <form id="fm-partInfo">
            <input type="hidden" id="ids" name="ids" datasource="APPLYIDS"/>
        </form>
    </div>
    </div>
</div>
</body>
</html>