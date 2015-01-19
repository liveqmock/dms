<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgId = user.getOrgId();
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>发运单确认</title>
    <script type="text/javascript">
        
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
            	var type = $("#SHIP_STATUS").val();
            	var searchUrl = "<%=request.getContextPath()%>/part/storageMng/shipMng/ShipConfirmAction/searchShip.ajax?STATUS="+type;
                var $f = $("#fm-searchShip");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-shipList");
            });
            $("#btn-search").trigger("click");
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/shipMng/shipConfirmEdit.jsp", "editWin", "承运信息维护", diaEditOptions);
        }
        function ctrlShow(obj){
            if(obj.text()=='已提交'){
                return "待确认";
            }else{
                return obj.text();
            }
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 发运管理 &gt; 发运单确认</h4>

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
                        	<td><label>发运单状态：</label></td>
                            <td><select type="text" class="combox" id="SHIP_STATUS" name="SHIP_STATUS" kind="dic" src="FYDZT"  filtercode="<%=DicConstant.FYDZT_02%>|<%=DicConstant.FYDZT_03%>" datatype="0,is_null,6" readonly="readonly">
							    	<option value="<%=DicConstant.FYDZT_02%>" selected>待确认</option>
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
                        <th fieldname="LINK_MAN">联系人</th>
                        <th fieldname="PHONE">联系电话</th>
                        <th fieldname="CREATE_USER">制单人</th>
                        <th fieldname="CREATE_TIME" colwidth="120">制单日期</th>
                        <th fieldname="SHIP_STATUS" refer="ctrlShow">发运单状态</th>
                        <th colwidth="150" type="link" title="[承运信息维护]" action="doUpdate">操作</th>
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