<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String ORDER_NO = request.getParameter("ORDER_NO");
	String TYPE = request.getParameter("TYPE");
%>
<div id="layout1" style="width:100%;">
	<div class="page" >
	<div >
		<form method="post" id="fm-searchPchOrder1">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
						<tr>
						    <td><label>客户名称：</label></td>
						    <td><div id="supplierName"></div></td>
						    <td><label>出库单号：</label></td>
						    <td ><div id="inNo"></div></td>
						</tr>
				</table>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_order1" style="">
			<table style="width:100%;" id="tab-purchase_info" name="tablist" ref="page_order1" refQuery="fm-searchPchOrder1" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="COUNT" >出库数量</th>
							<th fieldname="UNIT" >计量单位</th>
							<th fieldname="STORAGE_TYPE" >出库类型</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	<div class="subBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
</div>
<script type="text/javascript">
var orderUrl = "<%=request.getContextPath()%>/part/storageMng/search/DealerPartStockInOrOutSearchMngAction";
var ORDER_NO = "<%=ORDER_NO%>";
var dialog = $("body").data("outWin");
$(function()
{
	var accountSearchUrl = orderUrl + "/outOrderInfo.ajax?&ORDER_NO="+ORDER_NO;
    sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
	
	var searchUrl = orderUrl+"/outOrderSearch.ajax?ORDER_NO="+ORDER_NO;
	var $f = $("#tab-orderSearch");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-searchOrder",1,sCondition,"tab-purchase_info");
	
	$("button.close").click(function(){
        parent.$.pdialog.close(dialog);
        return false;
    });
});
function accountSearchCallback(res){
    try {
        var rows = res.getElementsByTagName("ROW");
        if(rows && rows.length > 0)
        {
            for(var i=0;i<rows.length;i++){
                var name = getNodeText(rows[i].getElementsByTagName("ORG_NAME").item(0));
                var no = getNodeText(rows[i].getElementsByTagName("ORDER_NO").item(0));
                $("#supplierName").html(name);
                $("#inNo").html(no);
            }
        }
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
</script>
