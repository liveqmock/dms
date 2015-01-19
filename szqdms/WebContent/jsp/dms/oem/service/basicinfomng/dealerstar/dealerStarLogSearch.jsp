<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>星级评定轨迹查询</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/DealerStarMngAction/searchLog.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:750,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchDealerStarLog");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};

		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-dealerStarLogList");
	});
});

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;星级评定轨迹查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchDealerStarLog" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchDealerStarLog">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="dealerCode" name="dealerCode" datasource="DEALER_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="dealerName" name="dealerName" datasource="DEALER_NAME" operation="like" datatype="1,is_null,300" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_dealerStarLogList">
				<table style="display:none;width:100%;" id="tab-dealerStarLogList" name="tablist" ref="page_dealerStarLogList" refQuery="tab-searchDealerStarLog">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="DEALER_CODE" >服务商代码</th>
							<th fieldname="DEALER_NAME" >服务商名称</th>
							<th fieldname="OLDPARANAME" >原星级</th>
							<th fieldname="NEWPARANAME" >现星级</th>
							<th fieldname="MODIFY_USER" >修改人</th>
							<th fieldname="MODIFY_TIME" >修改时间</th>
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