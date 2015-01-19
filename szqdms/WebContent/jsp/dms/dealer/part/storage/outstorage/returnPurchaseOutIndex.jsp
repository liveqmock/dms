<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/ReturnPurchaseOutMngAction/searchReturnPurchaseApply.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    // 金额formcat
    function formatAmount(obj){
        return "<div style='text-align:right;'>"+amountFormatNew($(obj).html())+"</div>";
    }

    // 列表编辑链接
    function doUpdate(row) {
        $("td input:first",$(row)).attr("checked",true);
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/dealer/part/storage/outstorage/returnPurchaseOutEdit.jsp", "returnPurchaseOutEdit", "退货出库明细", options);
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：仓储管理  &gt; 出库管理   &gt; 退货出库</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>退件单号：</label></td>
                        <td><input type="text" id="returnNo-index" name="in-ddbh" datasource="RETURN_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
                        <td><label>申请日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq-apply,in-jscjrq-apply"  id="in-kscjrq-apply"  name="in-kscjrq-apply" operation=">="  dataSource="APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq-apply,in-jscjrq-apply"  id="in-jscjrq-apply"  name="in-jscjrq-apply" operation="<=" dataSource="APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                        </td>
                        <td><label>审核日期：</label></td>
                        <td>
                            <input type="text" group="in-kscjrq-check,in-jscjrq-check"  id="in-kscjrq-check"  name="in-kscjrq-check" operation=">="  dataSource="CHECK_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
                            <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                            <input type="text" group="in-kscjrq-check,in-jscjrq-check"  id="in-jscjrq-check"  name="in-jscjrq-check" operation="<=" dataSource="CHECK_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
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
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_index" refQuery="fm-index" >
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none;"></th>
                            <th fieldname="RETURN_NO" ordertype='local' class="desc">退件单号</th>
                            <th fieldname="RECEIVE_ORG_NAME">接收方</th>
                            <th fieldname="APPLY_DATE">申请日期</th>
                            <th fieldname="CHECK_DATE">审核日期</th>
                            <th fieldname="CHECK_USER">审核员</th>
                            <th fieldname="RETURN_COUNT">退货数量</th>
                            <th fieldname="RETURN_AMOUNT" refer="formatAmount" align="right">退货金额(元)</th>
                            <th colwidth="105" type="link" title="[退货出库]"  action="doUpdate" >操作</th>
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