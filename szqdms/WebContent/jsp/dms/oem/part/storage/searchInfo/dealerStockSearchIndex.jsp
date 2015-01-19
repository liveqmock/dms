<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgType = user.getOrgDept().getOrgType();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>渠道库存查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 仓储相关   &gt; 渠道库存查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>服务商：</label></td>
						<td>
						<%
							// 如果用户属于服务站，配送中心，经销商，任意一种类型，则只有配件信息是必输项
							if(orgType.equals(DicConstant.ZZLB_09) || orgType.equals(DicConstant.ZZLB_10) || orgType.equals(DicConstant.ZZLB_11) ){
						%>
							<input type="text" id="dia-orgName" name="dia-orgName" datasource="ORG_CODE" 
									datatype="1,is_null,10000" operation="=" isreload="true" kind="dic"
									src="T#TM_ORG T:T.CODE:T.SNAME{CODE,SNAME}:1=1 AND EXISTS(SELECT 1 FROM TM_ORG O WHERE O.ORG_ID = '<%=user.getOrgId()%>' AND O.PID = T.PID) AND T.ORG_TYPE='200006' AND T.STATUS=100201 ORDER BY T.ORG_ID"
							/>
						<%
							}else{
						%>
							<input type="text" id="dia-orgName" name="dia-orgName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showOrgTree('dia-orgName',1,'',2)" operation="="/><span style="color:red;">*</span>
							<input type="hidden" id="ORG_CODE" name="ORG_CODE" datatype="1,is_null,30" datasource="O.CODE" operation="="/>
						<%
							}
						%>
                        </td>
					    <td><label>服务商所属级别：</label></td>
						<td>
                        	   <select name="ORG_TYPE" id="ORG_TYPE" dataSource="O.ORG_TYPE" kind="dic" src="<%=DicConstant.ZZLB %>" operation="=" datatype="1,is_null,6"
								    	filtercode="<%=DicConstant.ZZLB_09%>|<%=DicConstant.ZZLB_10%>|<%=DicConstant.ZZLB_11%>"
								    >
									<option value="-1" selected="selected">--</option>
								</select>

                        </td>
					    <td><label>配件信息：</label></td>
						<td>
                        	<input type="text" id="partName" name="partName" datatype="1,is_null,10000" readOnly hasBtn="true" callFunction="showPartInfo({isSingle: 0, showAllPart: 1})" operation="="/><span style="color:red;">*</span>
							<input type="hidden" id="partId" name="partId" datatype="1,is_null,30" datasource="T.PART_ID" operation="in"/>
                        </td>
					</tr>
					<tr>
					    <td><label>供应商代码：</label></td>
					    <td><input type="text" id="SUPPLIER_CODE" name="POSITION_CODE" datatype="1,is_digit_letter,30" dataSource="S.SUPPLIER_CODE" operation="like" /></td>
					    <td><label>供应商名称：</label></td>
					    <td><input type="text" id="SUPPLIER_NAME" name="POSITION_NAME" datatype="1,is_digit_letter_cn,30" dataSource="S.SUPPLIER_NAME" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear" >重置查询条件</button></div></div></li>
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
							<th fieldname="ORG_CODE" >服务商代码</th>
							<th fieldname="ORG_NAME" >服务商名称</th>
							<th fieldname="ORG_TYPE" >服务商级别</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
						<%
							// 如果用户属于服务站，配送中心，经销商，任意一种类型，则只显示可用数量
							if(orgType.equals(DicConstant.ZZLB_09) || orgType.equals(DicConstant.ZZLB_10) || orgType.equals(DicConstant.ZZLB_11) ){
						%>
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
						<%
							}else{
						%>
							<th fieldname="AMOUNT" >库存数量</th>
							<th fieldname="OCCUPY_AMOUNT" >占用数量</th>
							<th fieldname="AVAILABLE_AMOUNT" >可用数量</th>
							<th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价格</th>
						
						<%
							}
						%>
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
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
	<%
		// 如果用户属于服务站，配送中心，经销商，任意一种类型，则只有配件是必输项
		if(orgType.equals(DicConstant.ZZLB_09) || orgType.equals(DicConstant.ZZLB_10) || orgType.equals(DicConstant.ZZLB_11) ){
	%>
		if($("#partName").val() === ""){
			alertMsg.error("请选择配件信息");
			return;
		}
	<%
		}else{
	%>
		if($("#dia-orgName").val() === "" && $("#partName").val() === ""){
			alertMsg.error("请选择服务商或配件信息");
			return;
		}
	<%
		}
	%>		
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerStockQueryAction/queryListInfo.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	
	// 重置
	$("#btn-clear").click(function(){
		$("input", "#tab-htcx").each(function(index, obj){
			$(obj).val("");
		});
	});
})
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
// 组织树的回调函数
function showOrgTreeCallBack (res) {
	// 渠道名称
	$("#dia-orgName").val($(res).attr("orgName"));
	// 渠道代码
	$("#ORG_CODE").val($(res).attr("orgCode"));
}

// 配件选择回调函数
// res = {"PART_ID" : "", "PART_CODE": "", "PART_NAME": ""}
function showPartInfoCallback(res){
	$("#partName").val(res["PART_NAME"]);
	$("#partId").val(res["PART_ID"]);
}
</script>