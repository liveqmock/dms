<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String account = user.getAccount();
	String name = user.getPersonName();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/head.jsp"/>
    <title>直营出库</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DirectStockOutMngAction/searchSaleOrder.ajax";
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DirectStockOutMngAction/deleteOutBill.ajax";
        var stockOutUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/DirectStockOutMngAction/partStockOutInIndex.ajax";
        //定义弹出窗口样式
        var diaEditOptions = {max: true, width: 720, height: 430, mask: true, mixable: true, minable: true, resizable: true, drawable: true};
        //初始化
        $(function () {
        	var userAccount = '<%=account%>';
        	var name = '<%=name%>';
        	if(userAccount=='ADMIN'){
        		$('#PERSON_NAME').attr("src","T#TM_USER A,TM_ORG B:A.ACCOUNT:A.PERSON_NAME{A.ACCOUNT,A.PERSON_NAME}:1=1 AND A.STATUS=<%=DicConstant.YXBS_01 %> AND A.ORG_ID = B.ORG_ID AND B.CODE='XS10903'");
        	}else{
        		$('#PERSON_NAME').attr('code',userAccount);
                $('#PERSON_NAME').val(name);
                $('#PERSON_NAME').attr("src","");
        	}
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                var $f = $("#fm-searchOutBill");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
            });
            $("#btn-search").trigger("click");
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/directStockOutMngEdit.jsp?action=1", "editWin", "配件出库", diaEditOptions);
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/directStockOutMngEdit.jsp?action=2", "editWin", "修改出库单", diaEditOptions);
        }

        //出库链接，rowobj：行对象，非jquery类型
        function doStockOut(rowobj) {
            $row = $(rowobj);
            var url = stockOutUrl + "?outId=" + $(rowobj).attr("OUT_ID")+"&orderId="+$(rowobj).attr("ORDER_ID")+"&warehouseId="+$(rowobj).attr("WAREHOUSE_ID");
            sendPost(url, "", "", stockOutCallBack, "true");
        }
        //出库回调方法
        function stockOutCallBack(res) {
            try {
                if ($row)
                    $("#tab-outBillList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }
        function afterDicItemClick(id,$row){
        	var ret = true;
        	if(id == "PERSON_NAME")
        	{
        		var ACCOUNT = $row.attr("A.ACCOUNT");
        		var NAME = $row.attr("A.PERSON_NAME")
        		$('#PERSON_NAME').attr('code',ACCOUNT);
                $('#PERSON_NAME').val(NAME);
        	}
        	return ret;
        }
        function doOutSearch(){
        	var $f = $("#fm-searchOutBill");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 出库管理 &gt; 直营出库</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchOutBill">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchOutBill">
                    	<tr>
							<td><label>库管员：</label></td>
							<td>
						    	<input type="text" id="PERSON_NAME" name="PERSON_NAME" action="show" datasource="USER_ACCOUNT" kind="dic" src="" datatype="1,is_null,300" readonly="true"/>
							</td>
						</tr>
                        <tr>
                         <td><label>订单编号：</label></td>
                            <td><input type="text" id="ORDER_NO" name="ORDER_NO" datasource="A.ORDER_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>提报日期：</label></td>
                            <td>
                                <input  type="text" name="sel-APPLY_START_DATE" id="sel-APPLY_START_DATE" style="width:85px;" class="Wdate" operation=">=" group="sel-APPLY_START_DATE,sel-APPLY_END_DATE" datasource="A.APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="sel-APPLY_END_DATE" id="sel-APPLY_END_DATE" style="width:85px;margin-left:-28px;;" class="Wdate" operation="<=" group="sel-APPLY_START_DATE,sel-APPLY_END_DATE" datasource="A.APPLY_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                        	<td><label>提报单位代码：</label></td>
                            <td><input type="text" id="ORG_CODE" name="ORG_CODE" datasource="A.ORG_CODE" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>提报单位名称：</label></td>
                            <td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="A.ORG_NAME" datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                        <tr>
                            <td><label>审核日期：</label></td>
                            <td>
                                <input  type="text" name="sel-CHECK_START_DATE" id="sel-CHECK_START_DATE" style="width:85px;" class="Wdate" operation=">=" group="sel-CHECK_START_DATE,sel-CHECK_END_DATE" datasource="B.CHECK_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                                <span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
                                <input  type="text" name="sel-CHECK_END_DATE" id="sel-CHECK_END_DATE" style="width:85px;margin-left:-28px;;" class="Wdate" operation="<=" group="sel-CHECK_START_DATE,sel-CHECK_END_DATE" datasource="B.CHECK_DATE" kind="date" datatype="1,is_date,20" onclick="WdatePicker()" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td><label>审核员：</label></td>
                            <td><input type="text" id="sel-CHECK_USER_SV" name="sel-CHECK_USER_SV" datasource="C.PERSON_NAME" datatype="1,is_null,30" operation="like"/></td>
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
            <div id="div-outBillList">
                <table style="display:none;width:100%;" id="tab-outBillList" name="tablist" ref="div-outBillList" refQuery="tab-searchOutBill">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="ISSUE_NO" colwidth="150">发料单编号</th>
                        <th fieldname="ORDER_NO" colwidth="150">订单编号</th>
                        <th fieldname="ORDER_TYPE" >订单类型</th>
                        <th fieldname="ORG_NAME">提报单位</th>
                        <%--<th fieldname="APPLY_DATE">提报时间</th>--%>
                        <th fieldname="CHECK_USER" colwidth="75">审核员</th>
                        <th fieldname="CHECK_DATE"  colwidth="135">审核时间</th>
                        <th fieldname="RATE" colwidth="85">数量完成率(%)</th>
                        <th fieldname="RATE1" colwidth="85">品种完成率(%)</th>
                        <th colwidth="150" type="link" title="[出库]"  action="doUpdate" >操作</th>
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