<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>发运单维护</title>
    <script type="text/javascript">
        
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/deleteShip.ajax";
        var submitUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/submitShip.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	var $f = $("#fm-searchShip");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var type = $("#SHIP_STATUS").val();
            var searchUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/searchShip.ajax?STATUS="+type;
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-shipList");
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchShip");
                var sCondition = {};
                sCondition = $f.combined() || {};
                var type = $("#SHIP_STATUS").val();
                var searchUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipMngAction/searchShip.ajax?STATUS="+type;
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-shipList");
            });
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/shipMngEdit.jsp?action=1", "editWin", "新增发运单", diaEditOptions);
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/shipMngEdit.jsp?action=2", "editWin", "修改发运单", diaEditOptions);
        }

        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?shipId=" + $(rowobj).attr("SHIP_ID");
            sendPost(url, "", "", deleteCallBack, "true");
        }
        //删除回调方法
        function deleteCallBack(res) {
            try {
                if ($row)
                    $("#tab-shipList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }

        //提交方法，rowobj：行对象，非jquery类型
        function doSubmit(rowobj) {
            $row = $(rowobj);
            var url = submitUrl + "?shipId=" + $(rowobj).attr("SHIP_ID");
            sendPost(url, "", "", submitCallBack, "true");
        }
        //提交回调方法
        function submitCallBack(res) {
            try {
                if ($row)
                    $("#tab-shipList").removeResult($row);
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
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 发运管理 &gt; 发运单维护</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchShip">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchShip">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>发运单号：</label></td>
                            <td><input type="text" id="SHIP_NO" name="SHIP_NO" datasource="SHIP_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>承运单位：</label></td>
                            <td><input type="text" id="CARRIER_NAME" name="CARRIER_NAME" datasource="CARRIER_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                        	<td><label>发运单状态：</label></td>
                            <td><select type="text" class="combox" id="SHIP_STATUS" name="SHIP_STATUS" kind="dic" src="FYDZT"  filtercode="<%=DicConstant.FYDZT_01%>|<%=DicConstant.FYDZT_02%>" datatype="0,is_null,6" readonly="readonly">
							    	<option value="<%=DicConstant.FYDZT_01%>" selected>已保存</option>
					    	</select>
					    </td>
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
            <div id="div-shipList">
                <table style="display:none;width:100%;" id="tab-shipList" name="tablist" ref="div-shipList"
                       refQuery="tab-searchShip">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="SHIP_NO">发运单号</th>
                        <th fieldname="CARRIER_NAME">承运单位</th>
                        <th fieldname="IF_ARMY">是否军品</th>
                        <th fieldname="LINK_MAN">联系人</th>
                        <th fieldname="PHONE">联系电话</th>
                        <th fieldname="CREATE_USER">制单人</th>
                        <th fieldname="CREATE_TIME" colwidth="120">制单日期</th>
                        <th colwidth="150" type="link" title="[编辑]|[提交]" action="doUpdate|doSubmit">操作</th>
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