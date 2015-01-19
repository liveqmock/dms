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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件出库查询</title>
<script type="text/javascript">
var orgType = "<%=orgType%>"
$(function(){
	if(orgType==<%=DicConstant.ZZLB_09%>){
		$("#STORAGE_TYPE").attr("filtercode","<%=DicConstant.QDCRKLX_01%>|<%=DicConstant.QDCRKLX_03%>|<%=DicConstant.QDCRKLX_05%>");
	}else{
		$("#STORAGE_TYPE").attr("filtercode","<%=DicConstant.QDCRKLX_03%>|<%=DicConstant.QDCRKLX_05%>");
	}
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerPartOutQueryAction/partOutSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	// 导出按钮绑定
    $("#btn-export-index").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/DealerPartOutQueryAction/partOutDownload.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 渠道信息查询  &gt; 仓储相关   &gt; 配件出库查询</h4>
	<div class="page" >
	<div class="pageHeader" >
	    <form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,3000" dataSource="T.PART_CODE" operation="like" /></td>
					    <td><label>配件名称</label></td>
					    <td><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,3000" dataSource="T.PART_NAME" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>单号</label></td>
					    <td><input type="text" id="OUT_NO" name="OUT_NO" datatype="1,is_null,3000" dataSource="T.OUT_NO" operation="like" /></td>
					    <td><label>出库类型</label></td>
					    <td>
                            <input type="text" id="STORAGE_TYPE"  name="STORAGE_TYPE" kind="dic" src="QDCRKLX" datasource="T.STORAGE_TYPE" datatype="1,is_null,30" value=""/>
                        </td>
					</tr>
					<tr>
						<td><label>出库日期</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="in-kscjrq"  name="in-kscjrq" operation=">="  dataSource="T.APPLY_DATE" style="width:75px;"  kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="in-jscjrq"  name="in-jscjrq" operation="<=" dataSource="T.APPLY_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="1,is_date,30" onclick="WdatePicker()" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="ORG_CODE">接收方代码</th>
							<th fieldname="ORG_NAME">接收方名称</th>
							<th fieldname="OUT_NO">单号</th>
							<th fieldname="STORAGE_TYPE" >出库类型</th>
							<th fieldname="COUNT" >数量</th>
							<th fieldname="PRICE" >单价</th>
							<th fieldname="AMOUNT">金额</th>
							<th fieldname="APPLY_DATE" >出库日期</th>
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