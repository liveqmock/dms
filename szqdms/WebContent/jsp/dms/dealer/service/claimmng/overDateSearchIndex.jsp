<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>超单日期查询</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/overDateSearchAction/claimSearch.ajax";
$(function(){
	//查询方法
	var $f = $("#overDateFm");
	var sCondition = {};
   	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"search",1,sCondition,"overDateList");
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 索赔管理   &gt; 超单日期查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="overDateFm">
		</form>
	</div>
	<div class="pageContent">
		<div id="overDate" >
			<table style="display:none;width:100%;" id="overDateList" name="overDateList" ref="overDate" refQuery="overDateFm" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="OVERDUE_DAYS" >渠道超单日期</th>
							<th fieldname="CLAIM_NO" ordertype='local' class="desc">索赔单号</th>
							<th fieldname="CLAIM_TYPE" >类型</th>
							<th fieldname="FAULT_DATE" >故障日期</th>
							<th fieldname="APPLY_DATE" >提报时间</th>
							<th fieldname="CLAIM_OVERDUE_DAYS" >索赔单超单日期</th>
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