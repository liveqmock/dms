<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件三包期设置</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">

    //查询按钮响应方法
    $(function(){
        $("#btn-search-index").click(function(){
            // 查询配件三包期设置
            searchClaimCyclSet();
        });

        // 新增按钮绑定
        $("#btn-add-index").click(function(){
            var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
            $.pdialog.open(webApps+"/jsp/dms/oem/part/partClaim/claimMng/claimCyclSetAdd.jsp", "claimCyclSetAdd", "配件三包期新增", options);
        });

        // 导入按钮绑定
        $("#btn-import-index").click(function(){
            //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
            //最后一个参数表示 导入成功后显示页
            importXls('PT_BA_CLAIM_CYCLE_SET_TMP',$('#partCode-index').val(),5,3,"/jsp/dms/oem/part/partClaim/claimMng/importSuccess.jsp");
        });

        // 导出按钮绑定
        $("#btn-export-index").click(function(){
<%--             var url = encodeURI("<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclSetMngAction/download.do"); --%>
//             window.location.href = url;
//             $("#fm-index").attr("action",url);
//             $("#fm-index").submit();
            var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=sbqszdrmb.xls");
            window.location.href = url;
        });
    });

    // 查询配件三包期设置
    function searchClaimCyclSet() {
        var searchUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclSetMngAction/searchClaimCyclSet.ajax";
        var $f = $("#fm-index");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchUrl, "btn-search-index", 1, sCondition, "tab-grid-index");
    }

    // 编辑链接
    function doUpdate(rowobj){
        $("td input:first",$(rowobj)).attr("checked",true);
        var options = {max:false,width:700,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $.pdialog.open(webApps+"/jsp/dms/oem/part/partClaim/claimMng/claimCyclSetUpdate.jsp", "claimCyclSetUpdate", "配件三包期编辑", options);
    }

    // 删除链接
    function doDelete(rowobj){
        var deleteUrl = "<%=request.getContextPath()%>/part/partClaimMng/claimCycl/ClaimCyclSetMngAction/deleteClaimCyclSet.ajax";
        $row = $(rowobj);
        var url = deleteUrl + "?cycleId=" + $(rowobj).attr("CYCLE_ID");
        sendPost(url, "", "", deleteCallBack, "true");
    }
    // 删除回调方法
    function deleteCallBack(res) {
        try {
            if ($row)
                $("#tab-grid-index").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件索赔管理&gt;索赔管理&gt;配件三包期设置</h4>
    <div class="page">
    <div class="pageHeader">
        <form id="fm-index" method="post">
            <div class="searchBar" align="left">
                <table class="searchContent" id="pjsbqTable">
                    <tr>
                        <td><label>配件代码：</label></td>
                        <td><input type="text" id="partCode-index" name="partCode-index" datatype="1,is_null,100" datasource="PART_CODE"/></td>
                        <td><label>配件名称：</label></td>
                        <td><input type="text" id="partName-index" name="partName-index" datatype="1,is_null,100" datasource="PART_NAME"/></td>
                    </tr>
                </table>
                <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search-index">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-import-index">导&nbsp;&nbsp;入</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add-index">新&nbsp;&nbsp;增</button></div></div></li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="page_grid_index">
            <table style="display:none;width:100%;" id="tab-grid-index" name="tablist" ref="page_grid_index">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME">配件名称</th>
                        <th fieldname="CLAIM_MONTH">三包月份</th>
                        <th fieldname="EXTENSION_MONTH">延保月份</th>
                        <th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete">操作</th>
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