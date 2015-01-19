<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>追偿费用明细表</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/ClaimCheckAction/claimSearch.ajax";
$(function(){
	doSearch();
});
//初始化查询
function doSearch(){
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 索赔单审核</h4>
	<div class="page" >
	<div class="pageContent">
		<div id="claimCheck" >
			<table style="display:none;width:100%;" id="claimCheckList" name="claimCheckList" ref="claimCheck" refQuery="tab-claimCheck" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="WORK_NO"  colwidth="100">派工单号</th>
							<th fieldname="CLAIM_NO" colwidth="100">索赔单号</th>
							<th fieldname="CLAIM_TYPE" >索赔单类型</th>
							<th fieldname="CLAIM_STATUS" >索赔单状态</th>
							<th fieldname="APPLY_DATE" colwidth="130">提报时间</th>
							<th fieldname="APPLY_COUNT" >提报次数</th>
							<th colwidth="65" type="link" title="[审核]"  action="doUpdate" >操作</th>
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