<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>促销活动维护</title>
    <script type="text/javascript">
    var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/PromotionQueryAction/promotionSearch.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchPromotion");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, queryAction, "btn-search", 1, sCondition, "tab-promotionList");
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/searchInfo/promotionSearchDetail.jsp?action=2", "editWin", "促销活动明细", diaEditOptions);
        }

        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?promotionId=" + $(rowobj).attr("PROM_ID");
            sendPost(url, "", "", deleteCallBack, "true");
        }
        //删除回调方法
        function deleteCallBack(res) {
            try {
                if ($row)
                    $("#tab-promotionList").removeResult($row);
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
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 订单相关   &gt; 促销活动查询</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchPromotion">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPromotion">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>活动名称：</label></td>
                            <td><input type="text" id="PROM_NAME" name="PROM_NAME" datasource="PROM_NAME" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>活动日期：</label></td>
                            <td>
                                <input  type="text" name="START_DATE" id="START_DATE" style="width:100px;" class="Wdate" operation=">=" group="START_DATE,END_DATE" datasource="START_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="END_DATE" id="END_DATE" style="width:100px;margin-left:-28px;;" class="Wdate" operation="<=" group="START_DATE,END_DATE" datasource="END_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                            <td><label>活动类型：</label></td>
                            <td><select type="text" class="combox" id="PROM_TYPE" name="PROM_TYPE" kind="dic" src="CXHDLX" datasource="PROM_TYPE"  datatype="1,is_null,6" readonly="readonly">
							    	<option value="-1" selected>--</option>
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
            <div id="div-promotionList">
                <table style="display:none;width:100%;" id="tab-promotionList" name="tablist" ref="div-promotionList"
                       refQuery="tab-searchPromotion">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="PROM_CODE">活动代码</th>
                        <th fieldname="PROM_NAME">活动名称</th>
                        <th fieldname="PROM_TYPE">活动类型</th>
                        <th fieldname="START_DATE">活动开始时间</th>
                        <th fieldname="END_DATE">活动结束时间</th>
                        <th fieldname="IF_TRANS_FREE">是否免运费</th>
                        <th fieldname="PROM_STATUS">促销活动状态</th>
                        <th fieldname="REMARKS">备注</th>
                        <th colwidth="85" type="link" title="[明细]" action="doUpdate">操作</th>
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