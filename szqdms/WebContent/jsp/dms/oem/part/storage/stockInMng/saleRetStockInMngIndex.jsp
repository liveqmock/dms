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
    <title>销售退件入库</title>
    <script type="text/javascript">
        var searchUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleRetStockInMngAction/searchInBill.ajax";
        var deleteUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleRetStockInMngAction/deleteInBill.ajax";
        var completeUrl = "<%=request.getContextPath()%>/part/storageMng/stockInMng/SaleRetStockInMngAction/completeInBill.ajax";
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
                var $f = $("#fm-searchInBill");
                var sCondition = {};
                sCondition = $f.combined() || {};
                doFormSubmit($f, searchUrl, "btn-search", 1, sCondition, "tab-inBillList");
            });
            $("#btn-search").trigger('click');
            //新增按钮响应
            $("#btn-add").bind("click", function (event) {
                $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/saleRetStockInMngEdit.jsp?action=1", "editWin", "新增入库单", diaEditOptions);
            });
            $("#btn-reset").bind("click", function(event){
//         		$("#fm-searchInBill")[0].reset();
                $("#IN_NO").val("");
        		$("#in_orgCode").attr("code","");
        		$("#in_orgCode").val("");
        	});
        });
        //列表编辑连接
        function doUpdate(rowobj) {
            $("td input[type=radio]", $(rowobj)).attr("checked", true);
            $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockInMng/saleRetStockInMngEdit.jsp?action=2", "editWin", "配件入库清单", diaEditOptions);
        }

        var $row;
        //删除方法，rowobj：行对象，非jquery类型
        function doDelete(rowobj) {
            $row = $(rowobj);
            var url = deleteUrl + "?inId=" + $(rowobj).attr("IN_ID");
            sendPost(url, "", "", deleteCallBack, "true");
        }
        //删除回调方法
        function deleteCallBack(res) {
            try {
                if ($row)
                    $("#tab-inBillList").removeResult($row);
            } catch (e) {
                alertMsg.error(e);
                return false;
            }
            return true;
        }

        //入库完成链接，rowobj：行对象，非jquery类型
        function doComplete(rowobj) {
            $row = $(rowobj);
            var url = completeUrl + "?inId=" + $(rowobj).attr("IN_ID");
            sendPost(url, "", "", completeCallBack, "true");
        }
        //入库完成回调方法
        function completeCallBack(res) {
            try {
                if ($row)
                    $("#tab-inBillList").removeResult($row);
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
    </script>
</head>
<body>
<div id="layout" style="width:100%;">
    <div id='background1' class='background'></div>
    <div id='progressBar1' class='progressBar'>loading...</div>
    <h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif"/>&nbsp;当前位置：配件管理 &gt; 仓储管理 &gt; 入库管理 &gt; 销售退件入库</h4>

    <div class="page">
        <div class="pageHeader">
            <!-- 提交查询请求form -->
            <form method="post" id="fm-searchInBill">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchInBill">
                        <!-- 定义查询条件 -->
                        <tr>
							<td><label>库管员：</label></td>
							<td>
						    	<input type="text" id="PERSON_NAME" name="PERSON_NAME" action="show" datasource="USER_ACCOUNT" kind="dic" src="" datatype="1,is_null,300" readonly="true"/>
							</td>
						</tr>
                        <tr>
                            <td><label>销售退件单号：</label></td>
                            <td><input type="text" id="IN_NO" name="IN_NO" datasource="A.RETURN_NO" datatype="1,is_null,30" operation="like"/></td>
                            <td><label>渠道商：</label></td>
                        <td><input type="text" id="in_orgCode" name="in_orgCode" datasource="A.APPLY_ORG_ID" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('in_orgCode',1,1)" operation="IN"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
                            <li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-inBillList">
                <table style="display:none;width:100%;" id="tab-inBillList" name="tablist" ref="div-inBillList" refQuery="tab-searchInBill">
                    <thead>
                    <tr>
                        <th type="single" name="XH" style="display:none"></th>
                        <th fieldname="RETURN_NO" colwidth="135">销售退件单号</th>
                        <th fieldname="APPLY_ORG_CODE">渠道代码</th>
                        <th fieldname="APPLY_ORG_NAME">渠道名称</th>
                        <th fieldname="IN_TYPE" >入库类型</th>
                        <th fieldname="RECEIVE_ORG_NAME">入库仓库</th>
                        <th fieldname="CHECK_USER">审核员</th>
                        <th fieldname="CHECK_DATE">审核日期</th>
                        <th fieldname="STORAGE_RATE" colwidth="85" align="right">数量完成率(%)</th>
                        <th fieldname="STORAGE_RATE1" colwidth="85" align="right">品种完成率(%)</th>
                        <th colwidth="45" type="link" title="[入库]"  action="doUpdate" >操作</th>
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