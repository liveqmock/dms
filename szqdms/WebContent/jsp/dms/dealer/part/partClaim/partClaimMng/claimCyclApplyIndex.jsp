<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包申请</title>
<script type="text/javascript">

     $(function(){

        // 查询按钮响应方法
        $("#btn-search-index").click(function(){
            // 查询配件三包申请
            searchClaimCyclApply();
        });

        // 新增按钮绑定
        $("#btn-add-index").click(function(){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps+"/jsp/dms/dealer/part/partClaim/partClaimMng/claimCyclApplyEdit.jsp?action=1", "claimCyclApplyEdit", "配件三包申请新增", options);
        });
    });

     // 查询配件三包申请
     function searchClaimCyclApply() {
         var searchclaimCyclApply = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclApplyMngAction/searchClaimCyclApply.ajax";
         var $f = $("#fm-index");
         var sCondition = {};
         sCondition = $f.combined() || {};
         doFormSubmit($f, searchclaimCyclApply, "btn-search-index", 1, sCondition, "tab-index");
     }

     // 编辑操作
    function doUpdate(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/dealer/part/partClaim/partClaimMng/claimCyclApplyEdit.jsp?action=2", "claimCyclApplyEdit", "配件三包申请编辑", options);
    }

     // 删除操作
    function doDelete(rowobj){
        var deleteClaimCyclApply = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclApplyMngAction/deleteClaimCyclApply.ajax";
        $row = $(rowobj);
        var url = deleteClaimCyclApply + "?applyId=" + $(rowobj).attr("APPLY_ID");
        sendPost(url, "", "", deleteCallBack, "true");
    }

    // 删除回调方法
    function  deleteCallBack(res) {
        try {
            if ($row)
                $("#tab-index").removeResult($row);
        } catch(e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
    function doReportIndex(rowobj){
    	var deleteClaimCyclApply = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclApplyMngAction/applyClaimCyclApply.ajax";
        $row = $(rowobj);
        var url = deleteClaimCyclApply + "?applyId=" + $(rowobj).attr("APPLY_ID");
        sendPost(url, "", "", deleteCallBack, "true");
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包申请</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-index" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="pjsbsqTable">
                    <tr>
                        <td><label>三包申请单号：</label></td>
                        <td><input type="text" id="applyNo-index" name="applyNo-index" datatype="1,is_null,100" datasource="APPLY_NO" operation="like"/></td>
                        <td><label>状态：</label></td>
                        <td><select type="text" id="applyStatus-index" name="applyStatus-index" datatype="1,is_null,100" kind="dic" class="combox" datasource="APPLY_STATUS" src="SBJJSQZT">
                                <option value=-1>--</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode-index" name="partCode-index" datatype="1,is_null,100" datasource="PART_CODE" operation="like"/></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="partName-index" name="partName-index" datatype="1,is_null,100" datasource="PART_NAME" operation="like" /></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div>
                        </li>
                        <li>
                            <div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add-index">新&nbsp;&nbsp;增</button></div></div>
                        </li>
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
                        <th fieldname="APPLY_NO">三包申请单号</th>
                        <th fieldname="CUSTOMER_NAME">购货单位</th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="CLAIM_COUNT">数量</th>
                        <th fieldname="APPLY_DATE">提报日期</th>
                        <th fieldname="APPLY_STATUS">状态</th>
                        <th fieldname="CHECK_DATE">驳回日期</th>
                        <th fieldname="CHECK_REMARK">审核意见</th>
                        <th colwidth="85" type="link" title="[编辑]|[提报]"  action="doUpdate|doReportIndex">操作</th>
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