<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String orgId = user.getOrgId();
    String orgType = user.getOrgDept().getOrgType();
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if(action == null)
        action = "1";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>退件申请</title>
<script type="text/javascript">
var orgId = '<%=orgId%>';
var orgType = '<%=orgType%>';

    // 初始化方法
    $(function(){

        var srcVal="";
        if(orgType=="<%=DicConstant.ZZLB_09%>"){
            // 配送中心登录
            srcVal = "T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %>";
        }
        if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
            // 服务站登录
            srcVal = "T#TM_ORG A,PT_BA_SERVICE_DC B:A.CODE:A.ONAME{A.ORG_ID,A.ONAME}:1=1 AND A.ORG_ID = B.DC_ID AND B.STATUS=<%=DicConstant.YXBS_01 %> AND B.ORG_ID=<%=user.getOrgId() %> ";
<%--             srcVal = "T#TM_ORG:CODE:ONAME{ORG_ID,ONAME}:1=1 AND ORG_ID IN(SELECT DC_ID FROM PT_BA_SERVICE_DC WHERE STATUS=<%=DicConstant.YXBS_01 %> AND ORG_ID=<%=user.getOrgId() %> )"; --%>
        }

        // 接收方表选设置
        $("#returnCode-index").attr("src",srcVal);

        // 查询按钮绑定
        $("#btn-search-index").bind("click",function(event) {
            // 查询退件申请
            searchReturnPurchaseApply();
        });

        // 新增按钮绑定
        $("#btn-insert-index").bind("click",function(event) {
            var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps + "/jsp/dms/dealer/part/sales/returnPurchaseMng/returnPurchaseApplyEdit.jsp?action=1", "returnPurchaseApplyEdit", "退件申请新增", options);
        });
    });

    function showLink(obj)
    {
    	var $row=$(obj).parent();
        return "<a href='#' onclick=openDetail("+$row.attr("RETURN_ID")+") class='op'>"+$row.attr("RETURN_NO")+"</a>";
    }
    //列表编辑链接
    function openDetail(RETURN_ID) {
        var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps + "/jsp/dms/common/returnPurchaseInfoDetail.jsp?RETURN_ID="+RETURN_ID, "returnPurchaseApplyEdit", "退件申请明细", options);
    }

    // 查询退件申请
    function searchReturnPurchaseApply() {
        var searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/searchInfo/ReturnApplyQueryMngAction/searchReturnPurchaseApply.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    
    // 数据字典回调函数
    function afterDicItemClick(id,$row){
        if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
            if(id=="dia-code-edit") {
                $("#dia-orgId-edit").val($row.attr("ORG_ID"));
                $("#dia-oName-edit").val($row.attr("ONAME"));
            } else {
                $("#orgId-index").val($row.attr("ORG_ID"));
                $("#oName-index").val($row.attr("ONAME"));
            }
        }
        if(orgType=="<%=DicConstant.ZZLB_09%>"){
            if(id=="dia-code-edit") {
                $("#dia-orgId-edit").val($row.attr("WAREHOUSE_ID"));
                $("#dia-oName-edit").val($row.attr("WAREHOUSE_NAME"));
            } else {
            	$("#orgId-index").val($row.attr("WAREHOUSE_ID"));
                $("#oName-index").val($row.attr("WAREHOUSE_NAME"));
            }
        }
        return true;
    }
    
//     function showLink(obj)
//     {
// //     	var $row=$(obj).parent();
//         return "<a href='#' onclick=openDetail("+obj+") class='op'>"+$row.attr("RETURN_NO")+"</a>";
//     }
//     function openDetail(rowobj){
// //     	var options = {max:false,width:1024,height:600,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
// //     	$.pdialog.open(webApps+"/jsp/dms/common/saleOrderInfoDetail.jsp?ORDER_ID="+ORDER_ID, "saleOrderDetail", "销售订单明细", options,true);
//         $("td input:first",$(rowobj)).attr("checked",true);
//         var options = {max:true,mask:true,width:300,height:150,mixable:true,minable:true,resizable:true,drawable:true};
//         $.pdialog.open(webApps + "/jsp/dms/dealer/part/sales/returnPurchaseMng/searchInfo/returnPurchaseApplyEdit.jsp?action=2", "returnPurchaseApplyEdit", "退件申请维护", options);
//     }

</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 订单相关  &gt; 退件申请查询</h4>
    <div class="page" >
    <div class="pageHeader" >
        <form method="post" id="fm-index">
            <!-- 定义隐藏域条件 -->
            <div class="searchBar" align="left" >
                <table class="searchContent" id="tab-ddcx">
                    <!-- 定义查询条件 -->
                    <tr>
                        <td><label>退件单号：</label></td>
                        <td><input type="text" id="returnNo-index" name="returnNo-index" datasource="RETURN_NO" operation="like" /></td>
                        <td><label>接收方：</label></td>
                        <td>
                            <input type="text" id="returnCode-index" name="returnCode-index" datasource="RECEIVE_ORG_CODE" datatype="1,is_null,30" operation="like" isreload="true" src="" kind="dic"/>
                            <input type="hidden" id="orgId-index" datasource="RECEIVE_ORG_ID"/>
                            <input type="hidden" id="oName-index" datasource="RECEIVE_ORG_NAME"/>
                        </td>
                        <td><label>申请状态：</label></td>
                        <td>
                            <select type="text" id="applyStatus"  name="applyStatus" datasource="APPLY_SATUS" kind="dic" src="TJSQDZT" filtercode="<%=DicConstant.TJSQDZT_02%>|<%=DicConstant.TJSQDZT_03%>|<%=DicConstant.TJSQDZT_04%>|<%=DicConstant.TJSQDZT_05%>|<%=DicConstant.TJSQDZT_06%>" datatype="1,is_null,30" operation="=">
                                <option value="-1" selected>--</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index" >查&nbsp;&nbsp;询</button></div></div></li>
<!--                         <li><div class="button"><div class="buttonContent"><button type="button" id="btn-insert-index" >新&nbsp;&nbsp;增</button></div></div></li> -->
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_tab_index">
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_tab_index">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none"></th>
                            <th fieldname="RETURN_NO" refer="showLink">退件单号</th>
                            <th fieldname="RECEIVE_ORG_NAME" >接收方</th>
                            <th fieldname="APPLY_ORG_CODE" >退件单位代码</th>
                            <th fieldname="APPLY_ORG_NAME" >退件单位名称</th>
                            <th fieldname="CHECK_REMARKS" >审核意见</th>
                            <th fieldname="APPLY_SATUS" >状态</th>
<!--                             <th colwidth="85" type="link" title="[明细]"  action="openDetail" >操作</th> -->
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