<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>时间定义</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/serviceparamng/TimeDefineMngAction/search.ajax"
var options = {max:false,width:350,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
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
/* //查询按钮响应方法
 $(function(){
	 $("#sjlb").jTable();
}); */
function doUpdate(rowobj){
	 $("td input:first",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceparamng/timeDefine/TimeDefineAdd.jsp?action=1", "TimeDefine", "时间定义编辑", options); 
}
</script>
</head>
<body>
	<div id="layout" width="100%">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;业务参数管理&gt;时间定义
		</h4>
		<!-- 	<div class="pageHeader" type="hidden">
		<form id="fm-search" method="post" >	
		</form>
		</div> -->
			<div class="pageContent">
			<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
					<thead>
					<tr>
					<th type="single" name="XH" style="display:none"></th>
								 
								 <th fieldname="TIME_TYPE" >时间定义主键</th>								
								<th fieldname="TIME_BEGIN" >开始时间</th>
								<th fieldname="TIME_END" >结束时间</th>							
								<th fieldname="CREATE_USER" >创建人</th>
								<th fieldname="CREATE_TIME" >创建时间</th>
								<th fieldname="UPDATE_USER" >更新人</th>
								<th fieldname="UPDATE_TIME" >更新时间</th>
								<th fieldname="STATUS" >状态</th>							 
																
 
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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