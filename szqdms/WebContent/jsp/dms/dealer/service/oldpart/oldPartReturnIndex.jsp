<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartReturnAction/oldPartSearch.ajax";
var returnUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartReturnAction/oldPartReturn.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldpartform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
	
	//导出	
	$("#exp").bind("click",function(){
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartReturnAction/download.do");
		$("#exportFm").submit();
	});
	
	//集中点装箱导入
	$("#imp").bind("click",function(){
		importXls("SE_BU_RETURNORDER_PACK_FOCUS","",11,3,"/jsp/dms/dealer/service/oldpart/importPackFocusSuccess.jsp");
	});
});
//回运单号反回 a标签
function doOpenDetail(obj){
	$tr=$(obj).parent();
	return "<a href='#' onclick='doOldPartDetail("+$tr.attr("ORDER_ID")+")' class='op'>"+$tr.attr("ORDER_NO")+"</a>";
}
//打开连接明细
function doOldPartDetail(orderId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartDetail.jsp?orderId="+orderId, "oldPart", "旧件回运明细", options,true);
}
//回运
var $row;
function doUpdate(rowobj){
	$row = $(rowobj);
	var url = returnUrl + "?orderId="+$(rowobj).attr("ORDER_ID");
	sendPost(url,"","",returnCallBack,"true");
}
//回运 回调
function returnCallBack(res){
	try
	{
		if($row) 
			$("#oldPartList").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件回运</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldpartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldpartTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="O.ORG_ID" datatype="1,is_null,100" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>回运单号：</label></td>
							<td><input type="text" id="orederNo" name="orederNo"  datasource="O.ORDER_NO" operation="like" datatype="1,is_null,100"  value="" /></td>
							<td><label>运输方式：</label></td>
							<td><select  type="text" id="transType" name="transType" datasource="O.TRANS_TYPE" kind="dic" class="combox" src="HYDYSFS"  datatype="1,is_null,6" value="" >
									<option value="-1" selected>--</option>
								</select>
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="exp">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="imp">装箱导入</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="oldpart">
				<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldpart" refQuery="oldpartTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th  fieldname="ORDER_NO" refer="doOpenDetail" ordertype="local" class="desc">回运单号</th>
							<th  fieldname="CLAIMCOUNT">索赔单数量</th>
							<th  fieldname="PARTCOUNT">回运旧件数量</th>
							<th  fieldname="AMOUNT">装箱总数</th>
							<th  fieldname="TRANS_TYPE">运输方式</th>
							<th fieldname="RETURN_DATE">渠道回运日期</th>
							<th colwidth="85" type="link"  title="[回运]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	 <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>