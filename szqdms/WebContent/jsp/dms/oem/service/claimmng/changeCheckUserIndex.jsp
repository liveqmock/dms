<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>转让审核权</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ChangeCheckUserAction/checkUserSearch.ajax";
var changeUrl = "<%=request.getContextPath()%>/service/claimmng/ChangeCheckUserAction";
$(function(){
	$("#search").bind("click",function(event){
		var $f = $("#fm-claimCheck");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"claimCheckList");
	});
});
function doChange(rowobj){
	var account=$(rowobj).attr("OPERATE_USER");
	var url =changeUrl+"/changeClaimCheck.ajax?account="+account;
	sendPost(url,"","",changeCallBack,"true");
}

function changeCallBack(res){
	var $f = $("#fm-claimCheck");
	var sCondition = {};
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"search",1,sCondition,"claimCheckList");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 转让审核权</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-claimCheck">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-claimCheck">
					<tr>
						<td><label>审核人：</label></td>
					    <td><input type="text" id="userName" name="userName" datasource="U.USER_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="claimCheck" >
			<table style="display:none;width:100%;" id="claimCheckList" name="claimCheckList" ref="claimCheck" refQuery="tab-claimCheck" >
					<thead>
						<tr>
							<th type="multi" name="XH" style="display:none;"></th>
							<th fieldname="USER_NAME"  >审核人</th>
							<th fieldname="CLAIM_COUNT" >索赔单数量</th>
							<th colwidth="65" type="link" title="[转让]" action="doChange" >操作</th>
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