<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String flag = request.getParameter("flag");
	
	if(flag == null)
		flag = "1";
%>
<div id="cdrqspxx" style="width:100%;">
	<div class="page">
	<div class="pageHeader" >
		<form method="post" id="dia-fm-claim">
			<table class="searchContent" id="dia-tab-claim">
				<td><input type="hidden" id="dia-op-ORG_ID" name="dia-op-ORG_ID" datasource="ORG_ID" /></td>	
			</table>
		</form>	
	</div>
	<div class="pageContent" style="" >
		<div id="page_searchClaimList" >
		<table style="display:none;width:100%;" id="tab-searchClaimList" name="tab-searchClaimList" ref="page_searchClaimList" refQuery="dia-tab-claim">
					<thead>
						<tr>
							<th type="multi" name="CX-XH" style="align:center;" unique="CLAIM_ID"></th>
							<th fieldname="CLAIM_ID" >索赔主键</th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="OVERDUE_DAYS" >超单天数</th>
							<th fieldname="EXCEED_DAYS" >索赔单天数</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		</div>
		<table style="display:none">
			<tr><td>
				<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
			</td></tr>
		</table>
	
	<form method="post" id="fm-newPartWarehouseKeeper">
			<table class="editTable" id="tab-newPartWarehouseKeeperInfo">
				<tr>
				    <td><label>超单天数：</label></td>
				    <td><input type="text" id="diaN-OVERDUE_DAYS"  name="diaN-OVERDUE_DAYS"  
				   datasource="OVERDUE_DAYS" datatype="0,is_null,32" />				 
				    </td>
				</tr>
			</table>
				<div class="searchBar" align="left" >
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-keeperAdd" >超期天数变更</button></div></div></li>
						</ul>
					</div>
				</div>
	</form>
	
<!-- 		<form method="post" id="dia-fm-claim" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id=dia-tab-claim>
				<tr>
					<td><label>索赔单号：</label></td>
					<td><input type="text" id="dia-op-CLAIM_ID" name="dia-op-CLAIM_ID"  datasource="CLAIM_ID" value="" readOnly hasBtn="true" callFunction="openClaim();"/></td>
					<td><label>超单天数：</label></td>
					<td><input type="text"  id="dia-op-OVERDUE_DAYS" name="dia-op-OVERDUE_DAYS" datasource="OVERDUE_DAYS" value="" /></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form> -->
<!-- 		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSaveClaim()" id="save1">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div> -->
	</div>
	</div>	

<script type="text/javascript">
var searchClaimRtUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExceedClaimMngAction/searchClaim.ajax";
var flag = "<%=flag%>";
$(function(){
	
	if(flag = "1"){
	/* 	$("#DI_SPDH").val("索赔单号1");
		$("#DI_SPDH").attr("readonly",true);
		$("#DI_SPCDTS").val("8"); */		
		openClaim();
	
	}
});
function openClaim(){
		var $f = $("#dia-fm-claim");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
    
		doFormSubmit($f,searchClaimRtUrl,"",1,sCondition,"tab-searchClaimList");
}

//批量新增回调函数
function diaInsertCallBack(res)
{
	try
	{   
	
	$("#dia-tab-list").insertResult(res,0);	
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var mxid = $(checkbox).val();
	arr.push(mxid);
	multiSelected($checkbox,arr);
}

</script>