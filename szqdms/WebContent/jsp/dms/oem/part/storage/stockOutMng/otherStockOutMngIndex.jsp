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
    <title>其他出库</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OtherStockOutMngAction/searchOutBill.ajax";
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OtherStockOutMngAction/deleteOutBill.ajax";
        var stockOutUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OtherStockOutMngAction/partStockOutInIndex.ajax";
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
                $('#WAREHOUSE_CODE').attr("src","T#PT_BA_WAREHOUSE A,TM_USER B:A.WAREHOUSE_CODE:A.WAREHOUSE_NAME:1=1 AND A.STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> AND A.ORG_ID = B.ORG_ID AND B.ACCOUNT = '"+userAccount+"' ORDER BY WAREHOUSE_CODE");
            }
            //查询按钮响应
            $("#btn-search").bind("click", function (event) {
                search1();
            });
            $("#btn-search").trigger("click");
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
            	var account = $("#PERSON_NAME").attr("code");
            	if(!account){
            		alertMsg.warn("请选择库管员");
                    return false;
            	}
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/otherStockOutMngEdit.jsp?action=1", "editWin", "新增出库单", diaEditOptions);
            });
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/otherStockOutMngEdit.jsp?action=2", "editWin", "修改出库单", diaEditOptions);
        }

        function search1() {
        	var $f = $("#fm-searchOutBill");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-outBillList");
        }
        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?outId=" + $(rowobj).attr("OUT_ID") +"&warehouseId="+$(rowobj).attr("WAREHOUSE_ID");
            sendPost(url, "", "", deleteCallBack, "true");
        }
        //删除回调方法
        function deleteCallBack(res) {
            try {
                if ($row)
                    $("#tab-outBillList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }

        //出库链接，rowobj：行对象，非jquery类型
        function doStockOut(rowobj) {
            $row = $(rowobj);
            var url = stockOutUrl + "?outId=" + $(rowobj).attr("OUT_ID")+"&warehouseId="+$(rowobj).attr("WAREHOUSE_ID");
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
                
                $('#WAREHOUSE_CODE').attr("src","T#PT_BA_WAREHOUSE A,TM_USER B:A.WAREHOUSE_CODE:A.WAREHOUSE_NAME:1=1 AND A.STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> AND A.ORG_ID = B.ORG_ID AND B.ACCOUNT = '"+ACCOUNT+"' ORDER BY WAREHOUSE_CODE");
            }
            return ret;
        }
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：
        配件管理 &gt; 仓储管理 &gt; 出库管理 &gt; 其他出库</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchOutBill">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchOutBill">
                        <!-- 定义查询条件 -->
                        <tr>
                        	<td><label>库管员：</label></td>
                            <td><input type="text" id="PERSON_NAME" name="PERSON_NAME" action="show" datasource="USER_ACCOUNT" kind="dic" src="" datatype="1,is_null,300" readonly="true"/></td>
                        </tr>
                        <tr>
                            <td><label>出库单号：</label></td>
                            <td><input type="text" id="OUT_NO" name="OUT_NO" datasource="A.OUT_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>出库仓库：</label></td>
                            <td>
                                <input type="text" id="WAREHOUSE_CODE" name="WAREHOUSE_CODE" datasource="A.WAREHOUSE_CODE" kind="dic" src="" datatype="1,is_null,30"/>
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
            <div id="div-outBillList">
                <table style="display:none;width:100%;" id="tab-outBillList" name="tablist" ref="div-outBillList" refQuery="tab-searchOutBill">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="OUT_NO" colwidth="135">出库单号</th>
                        <th fieldname="OUT_TYPE" >出库类型</th>
                        <th fieldname="WAREHOUSE_NAME">出库仓库</th>
                        <th fieldname="OTHER_OUT_TYPE">其他出库出库类型</th>
                        <th colwidth="150" type="link" title="[编辑]|[出库]"  action="doUpdate|doStockOut" >操作</th>
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