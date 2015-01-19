<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.frameImpl.Constant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆维修历史查询</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：/service/basicinfomng/TaskAmountMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * TaskAmountMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction/ccVehicleReportSearch.ajax";
//定义弹出窗口样式
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	$("#export").bind("click",function(){
		var $f = $("#fm-search");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction/ccVehicleDownload.do");
		$("#exportFm").submit();
	});
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;车辆维修历史查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>VIN：</label></td>
						<td><input type="text" id="dia-VIN" name="VIN" datasource="M.VIN" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>服务站代码：</label></td>
						<td><input type="text" id="dia-orgCode" name="orgCode" datasource="G.CODE" operation="like"  datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="export">导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="gzms">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="gzms" refQuery="tab-search" pageRows="10">
					<thead>
					<tr>
						<th type="single" name="XH" style="display:none"></th>
						<th fieldname="ONAME" >渠道名称</th>		
						<th fieldname="CLAIM_NO" >索赔单号</th>		
						<th fieldname="VIN" >VIN</th>
						<th fieldname="OLD_PART_CODE" >配件代码</th>		
						<th fieldname="OLD_PART_NAME" >配件名称</th>		
						<th fieldname="MEASURES" >处理措施</th>		
						<th fieldname="OLD_SUP_NAME" >旧件供应商</th>		
						<th fieldname="NEW_SUP_NAME" >新件供应商</th>		
						<th fieldname="MILEAGE" >行驶里程</th>		
						<th fieldname="FAULT_DATE" >故障时间</th>		
						<th fieldname="REPAIR_DATE" >检修时间</th>		
						<th fieldname="WXCS" >维修次数</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data"></input>
</form>
</body>
</html>