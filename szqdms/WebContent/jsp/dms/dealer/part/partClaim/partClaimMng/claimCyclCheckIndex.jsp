<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
String orgId = user.getOrgId();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包审核</title>
<script type="text/javascript">

     // 初始化方法
     $(function(){

         // 查询按钮绑定方法
        $("#btn-search-index").click(function(){
            // 查询配件三包审核
            searchApply();
        });
    });

    // 查询配件三包审核
    function searchApply() {
        var searchclaimCyclApply = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclCheckMngAction/searchClaimCyclApply.ajax?flag='dealer'";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchclaimCyclApply, "btn-search-index", 1, sCondition, "tab-index");
    }
    function open(){
        alertMsg.info("弹出服务商树!");
    }

    // 配件三包审核
    function doAudit(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/partClaim/partClaimMng/claimCyclCheckEdit.jsp", "claimCyclCheckEdit", "配件三包审核", options);
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包审核</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-index" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="pjsbshTable">
                    <tr>
                        <td><label>渠道商：</label></td>
                        <td>
                            <input type="text" id="orgCode-index" name="orgCode-index" datatype="1,is_null,10000" datasource="ORG_CODE" multi="true" operation="in"
                                   src="T#PT_BA_SERVICE_DC A,TM_ORG B:A.ORG_CODE:B.ONAME{A.ORG_ID}:1=1 AND A.DC_ID=<%=orgId%> AND B.ORG_ID=A.ORG_ID" kind="dic"/>
                        </td>
                        <td><label>三包申请单号：</label></td>
                        <td><input type="text" id="applyNo-index" name="applyNo-index" datatype="1,is_null,100" datasource="APPLY_NO"/></td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode-index" name="partCode-index" datatype="1,is_null,100" datasource="PART_CODE"/></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="partName-index" name="partName-index" datatype="1,is_null,100" datasource="PART_NAME"/></td>
                        <td><label>提报日期：</label></td>
                        <td><input type="text" id="applyDate-index" name="applyDate-index" datatype="1,is_null,100" kind="date" datasource="APPLY_DATE" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})"  /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div>
                        </li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid">
            <table width="100%" id="tab-index" style="display: none" ref="page_grid" >
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ORG_CODE">渠道代码</th>
                        <th fieldname="ORG_NAME">渠道名称</th>
                        <th fieldname="APPLY_NO">三包申请单号</th>
                        <th fieldname="CUSTOMER_NAME">购货单位</th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="CLAIM_COUNT">数量</th>
                        <th fieldname="APPLY_DATE">提报日期</th>
                        <th colwidth="85" type="link" title="[审核]"  action="doAudit">操作</th>
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