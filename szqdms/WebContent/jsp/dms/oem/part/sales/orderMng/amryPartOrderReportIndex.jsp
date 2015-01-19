<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.OrgDept" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<% 
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgId = user.getOrgId();
	String orgType = user.getOrgDept().getOrgType();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>军品订单提报</title>
    <script type="text/javascript">
    	var orgId = '<%=orgId%>';
    	var orgType = '<%=orgType%>';
        var url = "<%=request.getContextPath()%>/part/salesMng/orderMng/ArmyPartOrderReportAction";
        var diaEditOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
        $(function () {
        	
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
            	doSearch();
            });
            $("#btn-search").trigger("click");
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/armyPartOrderReportEdit.jsp?action=1", "editWin", "订单新增", diaEditOptions);
            });
        });
        function doSearch(){
        	var $f = $("#searchForm");
            var sCondition = {};
            sCondition = $f.combined() || {};
            var searchUrl = url+"/partOrderSearch.ajax";
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-searchList");
        }
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/sales/orderMng/armyPartOrderReportEdit.jsp?action=2", "editWin", "订单修改", diaEditOptions);
        }

        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var deleteUrl = url + "/partOrderDelete.ajax?&orderId=" + $(rowobj).attr("ORDER_ID");
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
        //提报方法
    	function doApply(rowobj){
    		$row = $(rowobj);
    		if($row.attr("ORDER_AMOUNT").length==0){
    			alertMsg.warn("请添加订单所需订购的配件信息后,再进行提报.");
    			return false;
    		}
    		var reportUrl = url + "/partOrderReport.ajax?&orderId="+$row.attr("ORDER_ID")+"&orderType="+$row.attr("ORDER_TYPE")+"&orderAmount="+$row.attr("ORDER_AMOUNT");
            sendPost(reportUrl, "", "",reportCallBack, "true");
    	}
    	//提报回调方法
        function reportCallBack(res) {
            try {
                if ($row)
                    $("#tab-searchList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }
    	//金额格式化
        function amountFormat(obj){
        	return amountFormatNew($(obj).html());
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：渠道配件管理 &gt; 销售管理 &gt; 订单管理 &gt;军品订单提报</h4>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="searchForm">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="searchTable">
                        <!-- 定义查询条件 -->
						<tr>
							<td><label>订单编号：</label></td>
							<td><input type="text" id="orderNo" name="orderNo" datasource="T.ORDER_NO" datatype="1,is_digit_letter,30" operation="like" /></td>
							<td><label>合同号：</label></td>
							<td><input type="text" id="armyContNo" name="armyContNo" datasource="T.ARMY_CONT_NO" datatype="1,is_null,300" operation="like" /></td>
							<td><label>供货配件库：</label></td>
							<td>
								<select type="text" id="warehouseCode"  name="warehouseCode" datasource="T.WAREHOUSE_CODE" kind="dic" src="T#PT_BA_WAREHOUSE:WAREHOUSE_ID:WAREHOUSE_NAME:1=1 AND WAREHOUSE_TYPE=100102 AND WAREHOUSE_STATUS=<%=DicConstant.YXBS_01%>" datatype="1,is_null,30" operation="=">
									<option value="-1" selected>--</option>
								</select>
								<input type="hidden" id="warehouseId" name="warehouseId"/>
								<input type="hidden" id="warehouseName" name="warehouseName"/>
							</td>
						</tr>
						<tr>
							<td><label>提报日期：</label></td>
							<td>
								<input type="text" group="startDate,endDate"  id="startDate" kind="date" name="startDate" style="width:75px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation=">="/>
								<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" group="startDate,endDate"  id="endDate" kind="date" name="endDate" style="width:75px;margin-left:-30px;" datasource="T.APPLY_DATE" datatype="1,is_null,30" onclick="WdatePicker()" operation="<="/>
							</td>
							<td><label>订单状态：</label></td>
							<td>
								<select type="text" id="orderStatus"  name="orderStatus" datasource="T.ORDER_STATUS" kind="dic" src="DDZT" filtercode="202201|202204" datatype="1,is_null,30" operation="=">
									<option value="-1" selected>--</option>
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
                        <th fieldname="ORDER_NO">订单编号</th>
                        <th fieldname="ORDER_TYPE">订单类型</th>
                        <th fieldname="WAREHOUSE_NAME">供货配件库</th>
                        <th fieldname="EXPECT_DATE">期望到货日期</th>
                        <th fieldname="ORDER_AMOUNT" refer="amountFormat" align="right">总金额(元)</th>
                        <th fieldname="ORDER_STATUS">订单状态</th>
                        <th fieldname="CREATE_USER">提报人</th>
                        <th fieldname="APPLY_DATE">提报时间</th>
                        <th colwidth="85" type="link" title="[提报]|[编辑]" action="doApply|doUpdate">操作</th>
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