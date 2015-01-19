<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>自动审核规则维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/AutoCheckMngAction/search.ajax";
var resetStatusUrl = "<%=request.getContextPath()%>/service/basicinfomng/AutoCheckMngAction/resetStatus.ajax";
//查询按钮响应方法
 $(function(){
		doSearch();
});
function doSearch()
{
	var $f = $("#fm-search");//获取页面提交请求的form对象
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
    	
		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-list");
}
function doStartOrStop(ruId,ruleStatus){
	var url = resetStatusUrl + "?ruleId="+ruId+"&ruleStatus="+ruleStatus;
	sendPost(url,"resetStatus","",doCallBack,"true");
}
function mylink(rowObj){
	var $tr = $(rowObj).parent();
	var ruleStatus=$tr.attr("RULE_STATUS");
	var ruleId=$tr.attr("RULE_ID");
	var s;
if(ruleStatus==302802)
{
	ruleStatus=302801;
 	s= "<a href='###' onclick='doStartOrStop("+ruleId+","+ruleStatus+")'>[停止]</a>";
}else
{
	ruleStatus=302802;
	s= "<a href='###' onclick='doStartOrStop("+ruleId+","+ruleStatus+")'>[启动]</a>";
}
	
	return s;
	
}

function  doCallBack(res)
{
	try
	{		
		doSearch();
		
		
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;自动审核规则维护</h4>
		<div class="pageHeader">
		<form id="fm-search" method="post">	
		</form>
		</div>
	<div class="pageContent">
	<div id="page-list">
				<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list"   refQuery="tab-search" pageRows="10">
					<thead>
					<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="RULE_CODE" >规则代码</th>
							<th fieldname="RULE_NAME" >规则名称</th>
							<th fieldname="RULE_TYPE" >规则类型</th>
							<th fieldname="RULE_STATUS" >规则状态</th>											 
							<th colwidth="85"  refer="mylink">操作</th> 
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
	</div>
	</div>
</div>
</body>
</html>