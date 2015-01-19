<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>订单类型规则维护</title>
    <script type="text/javascript">
        var url = "<%=request.getContextPath()%>/part/basicInfoMng/OrderTypeRuleMngAction";
        var diaEditOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#searchForm");
                var sCondition = {};
                sCondition = $f.combined() || {};
                var searchUrl = url+"/orderTypeRuleSearch.ajax";
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
            });
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/orderTypeRuleEdit.jsp?action=1", "editWin", "新增订单规则", diaEditOptions);
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/orderTypeRuleEdit.jsp?action=2", "editWin", "修改订单规则", diaEditOptions);
        }

        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var deleteUrl = url + "/orderTypeRuleDelete.ajax?&typeRuleId=" + $(rowobj).attr("TYPERULE_ID");
            sendPost(deleteUrl, "", "", deleteCallBack, "true");
        }
        //删除回调方法
        function deleteCallBack(res) {
            try {
                if ($row)
                    $("#tab-searchList").removeResult($row);
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
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：配件管理 &gt; 基础数据管理 &gt; 基本信息管理 &gt;订单类型规则维护</h4>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="searchForm">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="searchTable">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>订单类型：</label></td>
                            <td><input type="text" id="ORDER_TYPE" name="ORDER_TYPE" datasource="ORDER_TYPE" kind="dic" src="DDLX" datatype="1,is_null,30" operation="="/></td>
                            <td><label>状态：</label></td>
                            <td>
                            	<select class="combox" id="STATUS" name="STATUS" kind="dic" src="YXBS" datasource="STATUS" operation="=" datatype="1,is_null,10">
                                    <option value="-1">--</option>
                                </select>
							</td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-searchList">
                <table style="display:none;width:100%;" id="tab-searchList" name="tablist" ref="div-searchList" refQuery="tab-searchTable">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="TYPE_CODE" colwidth="80">订单类型代码</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
                        <th fieldname="FIRST_LETTER">首位字符</th>
                        <th fieldname="IF_CHANEL">渠道提报</th>
                        <th fieldname="IF_SUMMARY">需汇总</th>
                        <th fieldname="IF_FREE">免运费</th>
                        <th fieldname="IF_CHOOSEADDR">地址可选</th>
                        <th fieldname="IF_APPLYDATE" colwidth="80">限制提报时间</th>
                        <th fieldname="IF_FUNDS">资金限制</th>
                        <th fieldname="IF_APPLYTIMES" colwidth="80">限制提报次数</th>
                        <th fieldname="IF_STORAGE">占用库存</th>
                        <th fieldname="IF_OWNPICK">允许自提</th>
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