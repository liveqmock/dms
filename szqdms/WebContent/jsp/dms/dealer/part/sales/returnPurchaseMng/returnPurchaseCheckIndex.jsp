<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>退件审核</title>
<script type="text/javascript">
    var searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseCheckMngAction/searchReturnPurchaseApply.ajax";
    //变量定义
    //初始化方法
    $(function(){

        // 查询按钮绑定
        $("#btn-search-index").bind("click",function(event){
            // 查询退件申请方法
            searchReturnPurchaseApply();
        });
    });

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 查询退件申请
    function searchReturnPurchaseApply() {
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    // 列表编辑链接
    function doUpdate(row) {
        $("td input:first",$(row)).attr("checked",true);
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/sales/returnPurchaseMng/returnPurchaseCheckEdit.jsp", "returnPurchaseCheckEdit", "退件审核明细", options);
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 退货管理   &gt; 退件申请</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>退件单号：</label></td>
                        <td><input type="text" id="returnNo-index" name="returnNo-index" datasource="RETURN_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>退件申请单位</label></td>
                        <td><input type="text" id="applyOrgName-index" name="applyOrgName-index" datasource="APPLY_ORG_NAME" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>申请日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
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
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="RETURN_NO" ordertype='local' class="desc">退件单号</th>
                            <th fieldname="RECEIVE_ORG_NAME" >接收方</th>
                            <th fieldname="APPLY_ORG_CODE" >退件单位代码</th>
                            <th fieldname="APPLY_ORG_NAME" >退件单位名称</th>
                            <th fieldname="APPLY_DATE" >申请日期</th>
                            <th colwidth="40" type="link" title="[审核]"  action="doUpdate" >操作</th>
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