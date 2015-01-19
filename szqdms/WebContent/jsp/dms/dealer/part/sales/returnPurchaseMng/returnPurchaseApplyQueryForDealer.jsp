<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>退件申请</title>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 销售管理  &gt; 退货管理   &gt; 退件申请</h4>
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
                            <input type="text" id="returnCode-index" name="returnCode-index" datasource="RECEIVE_ORG_NAME" datatype="1,is_null,600" operation="like" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>退件申请状态：</label></td>
                        <td colspan="3">
							<select class="combox" name="APPLY_SATUS" id="APPLY_SATUS" datasource="APPLY_SATUS"  kind="dic" 
									src="<%=DicConstant.TJSQDZT%>" operation="=" datatype="1,is_null,6" 
									>
								<option value="-1" selected="selected">--</option>
							</select>
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
        <div id="page_tab_index">
            <table style="display:none;width:100%;" id="tab-index" name="tablist" ref="page_tab_index">
                    <thead>
                        <tr>
                            <th type="single" name="XH" style="display:none"></th>
                            <th fieldname="RETURN_NO" colwidth="120" ordertype='local' class="desc">退件单号</th>
                            <th fieldname="RECEIVE_ORG_NAME" >接收方</th>
                            <th fieldname="APPLY_ORG_CODE" >退件单位代码</th>
                            <th fieldname="APPLY_ORG_NAME" >退件单位名称</th>
                            <th fieldname="CHECK_REMARKS" >审核意见</th>
                            <th fieldname="APPLY_SATUS" >退件申请状态</th>
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
<script type="text/javascript">

    // 初始化方法
    $(function(){

        // 查询按钮绑定
        $("#btn-search-index").bind("click",function(event) {
            // 查询退件申请
            searchReturnPurchaseApply();
        });

      
    });

    // 查询退件申请
    function searchReturnPurchaseApply() {
        var searchUrl = "<%=request.getContextPath()%>/part/salesMng/returnPurchaseMng/ReturnPurchaseApplyMngAction/searchReturnPurchaseApplyQueryForDealer.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-index");
    }

    
    // 数据字典回调函数
    function afterDicItemClick(id,$row){
        if(orgType=="<%=DicConstant.ZZLB_10%>"||orgType=="<%=DicConstant.ZZLB_11%>"){
            if(id=="dia-code-edit") {
                $("#dia-orgId-edit").val($row.attr("A.ORG_ID"));
                $("#dia-oName-edit").val($row.attr("A.ONAME"));
            } else {
                $("#orgId-index").val($row.attr("A.ORG_ID"));
                $("#oName-index").val($row.attr("A.ONAME"));
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
    
</script>