<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<jsp:include page="/head.jsp"/>
<title>旧件集中点与旧件仓库关系</title>
<script type="text/javascript">
    var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaMngAction/returnDealerSearch.ajax";
	var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaMngAction/deleteOrg.ajax";
    // 定义弹出窗口样式
    var diaAddOptions = {max: false, width: 720, height: 230, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
    // 初始化
    $(function () {
        // 查询按钮响应
        $("#btn-search").bind("click", function (event) {
            var $f = $("#fm-searchOldpartWarehouseArea");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-oldpartWarehouseArealist");
        });
        // 新增按钮响应
        $("#btn-add").bind("click", function (event) {
			$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/oldpartWarehouseCentrostigmaEdit.jsp?action=1", "add", "新增仓库与旧件中心", diaAddOptions);
        });
    });
    // 列表编辑连接
    function doUpdate(rowobj) {
        $("td input[type=radio]", $(rowobj)).attr("checked", true);
        $.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/oldpartWarehouseCentrostigmaEdit.jsp?action=2", "update", "修改仓库与旧件中心", diaAddOptions);
    }

    var $row;
    // 删除方法，rowobj：行对象，非jquery类型
    function doDelete(rowobj) {
        $row = $(rowobj);
        var url = deleteUrl + "?relationId=" + $(rowobj).attr("RELATION_ID");
        sendPost(url, "delete", "", deleteCallBack, "true");
    }
    // 删除回调方法
    function deleteCallBack(res) {
        try {
            if ($row)
                $("#tab-oldpartWarehouseArealist").removeResult($row);
        } catch (e) {
            alertMsg.error(e);
            return false;
        }
        return true;
    }
  //表选字典回调方法
    function afterDicItemClick(id, $row, selIndex){
    	if(id.indexOf("R_ORGCODE") == 0)
    	{
    		if($row.attr("ORG_ID")){
    		$("#R_ORGID").val($row.attr("ORG_ID"));
    		}
    		$("#R_ORGNAME").val($("#"+id).val());
    		return true;
    	}
    	return true;
    }
</script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：基础数据管理 &gt; 基础信息管理 &gt; 旧件集中点与旧件仓库关系</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchOldpartWarehouseArea">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchOldpartWarehouseArea">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>旧件集中点名称：</label></td>
						<td><input type="text" id="R_ORGCODE" name="R_ORGCODE" datasource="G.CODE" kind="dic"   src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS='100201' AND IS_IC = 100101" operation="like"  datatype="1,is_null,100" value="" /></td>
                            <td><label>仓库代码：</label></td>
                            <td><input type="text" id="warehouseCode" name="warehouseCode" datasource="WAREHOUSE_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>仓库名称：</label></td>
                            <td><input type="text" id="warehouseName" name="warehouseName" datasource="WAREHOUSE_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-search">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-add">新&nbsp;&nbsp;增</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="page_oldpartWarehouseArealist">
                <table style="display:none;width:100%;" id="tab-oldpartWarehouseArealist" name="tablist" ref="page_oldpartWarehouseArealist" refQuery="tab-searchOldpartWarehouseArea">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="RELATION_ID" style="display:none">关系主键</th>
                        <th fieldname="CODE">旧件集中点代码</th>
                        <th fieldname="ONAME">旧件集中点名称</th>
                        <th fieldname="WAREHOUSE_CODE">仓库代码</th>
                        <th fieldname="WAREHOUSE_NAME">仓库名称</th>
						<th fieldname="WAREHOUSE_TYPE">用户所属仓库类型</th>
						<th fieldname="CREATE_USER">创建人</th>
						<th fieldname="CREATE_TIME">创建时间</th>
						<th fieldname="UPDATE_USER">修改人</th>
						<th fieldname="UPDATE_TIME">修改时间</th>
						<th fieldname="STATUS">状态</th>
                        <th colwidth="85" type="link" title="[编辑]|[删除]" action="doUpdate|doDelete">操作</th>
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