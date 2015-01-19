<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class="page">
    <form method="post" id="fm-issueOrderInfo" class="editForm">
        <input type="hidden" id="dia-ISSUE_ID" name="dia-ISSUE_ID" datasource="ISSUE_ID"/>
        <div align="left">
            <fieldset>
                <legend align="right"><a onclick="onTitleClick('tab-saleOrderInfo')">&nbsp;发料单基本信息&gt;&gt;</a>
                </legend>
                    <table class="editTable" id="tab-issueOrderInfo">
                        <tr>
                            <td><label>发料单号：</label></td>
                            <td><input type="text" id="dia-ISSUE_NO" name="dia-ISSUE_NO" datasource="ISSUE_NO" readonly datatype="1,is_null,30"/></td>
                            <td><label>库管员：</label></td>
                            <td><input type="text" id="dia-USER_NAME" name="dia-USER_NAME" datasource="USER_NAME" readonly datatype="1,is_null,30"/></td>
                        </tr>
                    </table>
            </fieldset>
        </div>
    </form>
    <div class="pageHeader">
        <form method="post" id="fm-searchIssueDtl">
            <div class="searchBar" align="left">
                <table class="searchContent" id="tab-searchIssueDtl">
                </table>
            </div>
        </form>
    </div>
    <div class="pageContent">
        <div id="div-issueDtlList">
            <table style="display:none;width:100%;" id="tab-issueDtlList" name="tablist" ref="div-issueDtlList" refQuery="tab-searchIssueDtl">
                <thead>
                    <tr>
                        <th type="single" name="XH" style="display: none"></th>
                        <th fieldname="PART_CODE">配件代码</th>
                        <th fieldname="PART_NAME"  colwidth="150">配件名称</th>
                        <th fieldname="UNIT">单位</th>
                        <th fieldname="MIN_PACK" colwidth="80" refer="toAppendStr">最小包装</th>
                        <th fieldname="POSITION_CODE">库位</th>
                        <th fieldname="SHOULD_COUNT">应发数量</th>
                        <th fieldname="REAL_COUNT" colwidth="80">实发数量</th>
                       </tr>
                </thead>
            </table>
        </div>
        <div class="formBar">
            <ul>
            	<li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" id="btn-printTitle">打印标签</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" id="btn-printIssue">打印</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button class="close" id="btn-closeIssueDtl" type="button">关&nbsp;&nbsp;闭</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var issueDtlWin = $("body").data("issueDtlWin");
    $(function () {

        var selectedRows = $("#tab-issueOrderList").getSelectedRows();
        setEditValue("fm-issueOrderInfo", selectedRows[0].attr("rowdata"));
        var issueId = selectedRows[0].attr('ISSUE_ID');

        $("#btn-closeIssueDtl").click(function () {
        	searchIssueOrder();
            $.pdialog.close(issueDtlWin);
            return false;
        });

        $('#btn-printTitle').click(function(){
        	/*window.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printPartTitle.jsp");*/ 
        	 var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/printTitlePdf.do?ISSUE_ID="+issueId;
            window.open(queryUrl);
        });
        $('#btn-printIssue').click(function(){
        	//window.open(webApps + "/jsp/dms/oem/part/storage/stockIssueMng/printIssuePrint.jsp");
        	var queryUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/printPdf.do?ISSUE_ID="+issueId+"&flag=1";
            window.open(queryUrl);
        });
        var searchIssueDtlUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/searchIssueDtl.ajax?ISSUE_ID="+issueId;
        var $f = $("#fm-searchIssueDtl");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchIssueDtlUrl, "btn-searchIssueDtl", 1, sCondition, "tab-issueDtlList");
    });
    function queryCallback(res){
    	return true;
    }
    function doIssSearch(){
    	var searchIssueDtlUrl = "<%=request.getContextPath()%>/part/storageMng/stockIssueMng/PrintIssueAction/searchIssueDtl.ajax?ISSUE_ID="+issueId;
        var $f = $("#fm-searchIssueDtl");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchIssueDtlUrl, "btn-searchIssueDtl", 1, sCondition, "tab-issueDtlList");
    }
</script>