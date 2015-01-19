<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String IN_ID = request.getParameter("IN_ID");
%>
<div id="layout1" style="width:100%;">
	<div class="page" >
	<div >
		<form method="post" id="fm-searchPchOrder1">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-orderSearch">
						<tr>
						    <td><label>入库来源：</label></td>
						    <td><div id="supplierName"></div></td>
						    <td><label>入库单号：</label></td>
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
							<th fieldname="IN_AMOUNT" >入库数量</th>
							<th fieldname="UNIT" >计量单位</th>
							<th fieldname="IN_TYPE" >入库类型</th>
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
var orderUrl = "<%=request.getContextPath()%>/part/storageMng/search/PartStockInOrOutSearchMngAction";
var IN_ID = "<%=IN_ID%>";
var dialog = $("body").data("inWin");
$(function()
{
	var accountSearchUrl = orderUrl + "/inOrderInfo.ajax?&IN_ID="+IN_ID;
    sendPost(accountSearchUrl, "", "", accountSearchCallback, "false");
	
	var searchUrl = orderUrl+"/inOrderSearch.ajax?IN_ID="+IN_ID;
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
                var name = getNodeText(rows[i].getElementsByTagName("NAME").item(0));
                var no = getNodeText(rows[i].getElementsByTagName("NO").item(0));
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
