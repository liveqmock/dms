<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>派工人员维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchUerMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchUerMngAction/resetUSER_STATUS.ajax";
//定义弹出窗口样式
var options = {max:false,width:800,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/dealer/service/claimmng/WorkDispatchUerAdd.jsp?action=1", "WorkDispatchUer", "派工人员新增", options);
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/claimmng/WorkDispatchUerAdd.jsp?action=2", "WorkDispatchUer", "派工人员编辑", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 索赔管理  &gt; 工单管理   &gt; 派工人员维护</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>人员姓名：</label></td>
						<td><input type="text" id="USER_NAME" name="USER_NAME" datasource="T.USER_NAME" operation="like" datatype="1,is_null,100" value="" /></td>
						<td><label>手机号码：</label></td>
						<td><input type="text" id="MOBIL" name="MOBIL" datasource="T.MOBIL"  operation="like" datatype="1,is_null,100" value="" /></td>
					 	<td><label>状态：</label></td>
			   		 	<td>
				      		<select type="text" class="combox" id="USER_STATUS" name="USER_STATUS" kind="dic" src="YXBS" datasource="T.STATUS" datatype="1,is_null,10" >
				    	 		<option value="-1" selected>--</option>	
				    		</select>
			    		</td> 	
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
						<th type="single" name="XH" style="display:none"></th>
						<th fieldname="USER_NAME">人员姓名</th>
						<th fieldname="MOBIL">联系电话</th>
						<th fieldname="ADDRESS">地址</th>
						<th fieldname="STATUS">状态</th>
						<th fieldname="REMARKS">备注</th>
						<th colwidth="85" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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