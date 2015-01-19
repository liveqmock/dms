<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.frameImpl.Constant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    String year = yearFormat.format(Pub.getCurrentDate());
    String date = year ;
%>
<jsp:include page="/head.jsp" />
<title>零件保修更换数量台帐</title>
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
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/partRepairSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction/resetStatus.ajax";
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
	 // 导出按钮绑定
    $("#btn-except").click(function(){
    	var $f = $("#fm-search");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;配件信息查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="dia-PART_CODE" name="PART_CODE" datasource="PART_CODE" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="dia-PART_NAME" name="PART_NAME" datasource="PART_NAME" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>更换年份：</label></td>
						<td><input type="text" id="dia-year" name="dia-year" datasource="APPLY_DATE" datatype="0,is_null,100" onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy'})"value="" /></td>
  					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-except">导&nbsp;&nbsp;出</button></div></div></li>
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
							<th fieldname="F_POSITION_NAME" >配件部位</th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="B_OLD_PART_COUNT01" >上一年1月</th>
							<th fieldname="OLD_PART_COUNT01" >1月</th>
							<th fieldname="TB01" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT02" >上一年2月</th>
							<th fieldname="OLD_PART_COUNT02" >2月</th>
							<th fieldname="TB02" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT03" >上一年3月</th>
							<th fieldname="OLD_PART_COUNT03" >3月</th>
							<th fieldname="TB03" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT04" >上一年4月</th>
							<th fieldname="OLD_PART_COUNT04" >4月</th>
							<th fieldname="TB04" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT05" >上一年5月</th>
							<th fieldname="OLD_PART_COUNT05" >5月</th>
							<th fieldname="TB05" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT06" >上一年6月</th>
							<th fieldname="OLD_PART_COUNT06" >6月</th>
							<th fieldname="TB06" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT07" >上一年7月</th>
							<th fieldname="OLD_PART_COUNT07" >7月</th>
							<th fieldname="TB07" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT08" >上一年8月</th>
							<th fieldname="OLD_PART_COUNT08" >8月</th>
							<th fieldname="TB08" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT09" >上一年9月</th>
							<th fieldname="OLD_PART_COUNT09" >9月</th>
							<th fieldname="TB09" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT10" >上一年10月</th>
							<th fieldname="OLD_PART_COUNT10" >10月</th>
							<th fieldname="TB10" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT11" >上一年11月</th>
							<th fieldname="OLD_PART_COUNT11" >11月</th>
							<th fieldname="TB11" >同比增长率%</th>
							<th fieldname="B_OLD_PART_COUNT12" >上一年12月</th>
							<th fieldname="OLD_PART_COUNT12" >12月</th>
							<th fieldname="TB12" >同比增长率%</th>		
							<th fieldname="ZSL" >1-12月总量</th>	
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<form id="exportFm" method="post" style="display:none">
	       <input type="hidden" id="data" name="data"></input>
        </form>
	</div>
	</div>
</div>
</body>
</html>