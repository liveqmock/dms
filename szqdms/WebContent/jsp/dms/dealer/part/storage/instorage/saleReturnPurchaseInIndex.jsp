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
<title>销售退件入库</title>
<script type="text/javascript">
    //变量定义
    //初始化方法
    $(function(){

        //查询方法
        $("#btn-search-index").bind("click",function(event){
            // 查询退件申请方法
            searchReturnPurchaseApply();
        });
    });

    // 查询退件申请
    function searchReturnPurchaseApply() {
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleReturnPurchaseInMngAction/searchReturnPurchaseApply.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    // 列表编辑链接
    function doUpdate(row) {
        $("td input:first",$(row)).attr("checked",true);
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/instorage/saleReturnPurchaseInEdit.jsp", "saleReturnPurchaseInEdit", "销售退件入库明细", options);
    }

    // 退件金额
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：仓储管理  &gt; 入库管理   &gt; 销售退件入库</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>退件单号：</label></td>
                        <td><input type="text" id="returnNo-index" name="returnNo-index" datasource="RETURN_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>渠道商：</label></td>
                        <td>
                            <input type="text" id="applyOrgCode-index" name="applyOrgCode-index" datasource="APPLY_ORG_CODE" datatype="1,is_null,30" operation="in" multi="true"
                                   src="T#PT_BA_SERVICE_DC A,TM_ORG B:A.ORG_CODE:B.ONAME{A.ORG_ID}:1=1 AND A.DC_ID=<%=orgId%> AND B.ORG_ID=A.ORG_ID" kind="dic" dicwidth="200"/></td>
                        <td><label>申请日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq-apply,in-jscjrq-apply"  id="in-kscjrq-apply"  name="in-kscjrq-apply" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq-apply,in-jscjrq-apply"  id="in-jscjrq-apply"  name="in-jscjrq-apply" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index" >查&nbsp;&nbsp;询</button></div></div></li>
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
                            <th fieldname="RETURN_ID" ordertype='local' class="desc">退件单号</th>
                            <th fieldname="RECEIVE_ORG_CODE">渠道商代码</th>
                            <th fieldname="RECEIVE_ORG_NAME">渠道商名称</th>
                            <th fieldname="APPLY_DATE">申请日期</th>
                            <th fieldname="RETURN_COUNT">退件数量</th>
                            <th fieldname="RETURN_AMOUNT" refer="formatAmount">退件金额</th>
                            <th colwidth="105" type="link" title="[销售退件入库]"  action="doUpdate" >操作</th>
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