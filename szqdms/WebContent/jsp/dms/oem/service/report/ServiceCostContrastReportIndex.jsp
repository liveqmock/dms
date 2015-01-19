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
<title>服务费用对比</title>
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
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/ServiceCostContrastReportAction/search.ajax";
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
        var url = encodeURI("<%=request.getContextPath()%>/service/reportForms/ServiceCostContrastReportAction/download.do");
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;服务费用对比</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
					<td><label>年份：</label></td>
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
							<th fieldname="OFFICE_NAME" >办事处</th>
							<th fieldname="ORG_CODE" >服务编码</th>
							<th fieldname="ORG_NAME" >服务站</th>
							<th fieldname="MATERIAL_COSTS1" >1月材料费</th>
							<th fieldname="MATERIAL_COSTS2" >2月材料费</th>
							<th fieldname="MATERIAL_COSTS3" >3月材料费</th>
							<th fieldname="MATERIAL_COSTS4" >4月材料费</th>
							<th fieldname="MATERIAL_COSTS5" >5月材料费</th>
							<th fieldname="MATERIAL_COSTS6" >6月材料费</th>
							<th fieldname="MATERIAL_COSTS7" >7月材料费</th>
							<th fieldname="MATERIAL_COSTS8" >8月材料费</th>
							<th fieldname="MATERIAL_COSTS9" >9月材料费</th>
							<th fieldname="MATERIAL_COSTS10" >10月材料费</th>
							<th fieldname="MATERIAL_COSTS11" >11月材料费</th>
							<th fieldname="MATERIAL_COSTS12" >12月材料费</th>
			  <th fieldname="WORK_COSTS1" >1月工时费</th>
              <th fieldname="WORK_COSTS2" >2月工时费</th>
              <th fieldname="WORK_COSTS3" >3月工时费</th>
              <th fieldname="WORK_COSTS4" >4月工时费</th>
              <th fieldname="WORK_COSTS5" >5月工时费</th>
              <th fieldname="WORK_COSTS6" >6月工时费</th>
              <th fieldname="WORK_COSTS7" >7月工时费</th>
              <th fieldname="WORK_COSTS8" >8月工时费</th>
              <th fieldname="WORK_COSTS9" >9月工时费</th>
              <th fieldname="WORK_COSTS10" >10月工时费</th>
              <th fieldname="WORK_COSTS11" >11月工时费</th>
              <th fieldname="WORK_COSTS12" >12月工时费</th>
              <th fieldname="OUT_COSTS1" >1月外出费</th>
              <th fieldname="OUT_COSTS2" >2月外出费</th>
              <th fieldname="OUT_COSTS3" >3月外出费</th>
              <th fieldname="OUT_COSTS4" >4月外出费</th>
              <th fieldname="OUT_COSTS5" >5月外出费</th>
              <th fieldname="OUT_COSTS6" >6月外出费</th>
              <th fieldname="OUT_COSTS7" >7月外出费</th>
              <th fieldname="OUT_COSTS8" >8月外出费</th>
              <th fieldname="OUT_COSTS9" >9月外出费</th>
              <th fieldname="OUT_COSTS10" >10月外出费</th>
              <th fieldname="OUT_COSTS11" >11月外出费</th>
              <th fieldname="OUT_COSTS12" >12月外出费</th>
              <th fieldname="SUMMARY1" >1月总费用</th>
              <th fieldname="F1" >同比增长率%</th>	
              <th fieldname="E1" >环比增长率%</th>	
              <th fieldname="SUMMARY2" >2月总费用</th>
              <th fieldname="F2" >同比增长率%</th>	
              <th fieldname="E2" >环比增长率%</th>	
              <th fieldname="SUMMARY3" >3月总费用</th>
              <th fieldname="F3" >同比增长率%</th>	
              <th fieldname="E3" >环比增长率%</th>	
              <th fieldname="SUMMARY4" >4月总费用</th>
              <th fieldname="F4" >同比增长率%</th>	
              <th fieldname="E4" >环比增长率%</th>	
              <th fieldname="SUMMARY5" >5月总费用</th>
              <th fieldname="F5" >同比增长率%</th>	
              <th fieldname="E5" >环比增长率%</th>	
              <th fieldname="SUMMARY6" >6月总费用</th>
              <th fieldname="F6" >同比增长率%</th>	
              <th fieldname="E6" >环比增长率%</th>	
              <th fieldname="SUMMARY7" >7月总费用</th>
              <th fieldname="F7" >同比增长率%</th>	
              <th fieldname="E7" >环比增长率%</th>	
              <th fieldname="SUMMARY8" >8月总费用</th>
              <th fieldname="F8" >同比增长率%</th>	
              <th fieldname="E8" >环比增长率%</th>	
              <th fieldname="SUMMARY9" >9月总费用</th>
              <th fieldname="F9" >同比增长率%</th>	
              <th fieldname="E9" >环比增长率%</th>	
              <th fieldname="SUMMARY10" >10月总费用</th>
              <th fieldname="F10" >同比增长率%</th>	
              <th fieldname="E10" >环比增长率%</th>	
              <th fieldname="SUMMARY11" >11月总费用</th>
              <th fieldname="F11" >同比增长率%</th>	
              <th fieldname="E11" >环比增长率%</th>	
              <th fieldname="SUMMARY12" >12月总费用</th>		
              <th fieldname="F12" >同比增长率%</th>	
              <th fieldname="E12" >环比增长率%</th>				
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