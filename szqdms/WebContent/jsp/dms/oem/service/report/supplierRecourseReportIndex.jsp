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
<title>供应商追偿明细表</title>
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
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/supRepairSearch.ajax";
//定义弹出窗口样式
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		setTitle();
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
        var url = encodeURI("<%=request.getContextPath()%>/service/reportForms/partRepairAmountReportAction/supDownload.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});
function setTitle(){
	var year = $("#dia-year").val();
	$("#year1").text((year-1)+'年12月');
	$("#year2").text((year-2)+'年1月');
	$("#year3").text((year-1)+'年1月');
	$("#year4").text(year+'年1月');
	$("#year5").text((year-2)+'年2月');
	$("#year6").text((year-1)+'年2月');
	$("#year7").text(year+'年2月');
	$("#year8").text((year-2)+'年3月');
	$("#year9").text((year-1)+'年3月');
	$("#year10").text(year+'年3月');
	$("#year11").text((year-2)+'年4月');
	$("#year12").text((year-1)+'年4月');
	$("#year13").text(year+'年4月');
	$("#year14").text((year-2)+'年5月');
	$("#year15").text((year-1)+'年5月');
	$("#year16").text(year+'年5月');
	$("#year17").text((year-2)+'年6月');
	$("#year18").text((year-1)+'年6月');
	$("#year19").text(year+'年6月');
	$("#year20").text((year-2)+'年7月');
	$("#year21").text((year-1)+'年7月');
	$("#year22").text(year+'年7月');
	$("#year23").text((year-2)+'年8月');
	$("#year24").text((year-1)+'年8月');
	$("#year25").text(year+'年8月');
	$("#year26").text((year-2)+'年9月');
	$("#year27").text((year-1)+'年9月');
	$("#year28").text(year+'年9月');
	$("#year29").text((year-2)+'年10月');
	$("#year30").text((year-1)+'年10月');
	$("#year31").text(year+'年10月');
	$("#year32").text((year-2)+'年11月');
	$("#year33").text((year-1)+'年11月');
	$("#year34").text(year+'年11月');
	$("#year35").text((year-2)+'年12月');
	$("#year36").text((year-1)+'年12月');
	$("#year37").text(year+'年12月');
}
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
						<td><label>供应商代码：</label></td>
						<td><input type="text" id="dia-SUPPLIER_CODE" name="SUPPLIER_CODE" datasource="SUPPLIER_CODE" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>供应商名称：</label></td>
						<td><input type="text" id="dia-SUPPLIER_NAME" name="SUPPLIER_NAME" datasource="SUPPLIER_NAME" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>年份：</label></td>
						<td><input type="text" id="dia-year" name="dia-year" datasource="YEAR" datatype="0,is_null,100" onclick="WdatePicker({maxDate:'<%=date%>',dateFmt:'yyyy'})"value="" /></td>
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
						   <th fieldname="SUPPLIER_CODE" >供应商代码</th>
						   <th fieldname="SUPPLIER_NAME" >供应商名称</th>
					       <th fieldname="LAST_MONTH" id="year1"></th>
					       <th fieldname="FIRST_JAN" id="year2"></th>
					       <th fieldname="SECOND_JAN" id="year3"></th>
					       <th fieldname="THIRD_JAN" id="year4"></th>
					       <th fieldname="RATE_JAN_YOY" >同比增长率</th>
					       <th fieldname="RATE_JAN_MOM" >环比增长率</th>
					       <th fieldname="FIRST_FEB" id="year5"></th>
					       <th fieldname="SECOND_FEB" id="year6"></th>
					       <th fieldname="THIRD_FEB" id="year7"></th>
					       <th fieldname="RATE_FEB_YOY" >同比增长率</th>
					       <th fieldname="RATE_FEB_MOM" >环比增长率</th>
					       <th fieldname="FIRST_MAR" id="year8"></th>
					       <th fieldname="SECOND_MAR" id="year9"></th>
					       <th fieldname="THIRD_MAR" id="year10"></th>
					       <th fieldname="RATE_MAR_YOY" >同比增长率</th>
					       <th fieldname="RATE_MAR_MOM" >环比增长率</th>
					       <th fieldname="FIRST_APR" id="year11"></th>
					       <th fieldname="SECOND_APR" id="year12"></th>
					       <th fieldname="THIRD_APR" id="year13"></th>
					       <th fieldname="RATE_APR_YOY" >同比增长率</th>
					       <th fieldname="RATE_APR_MOM" >环比增长率</th>
					       <th fieldname="FIRST_MAY" id="year14"></th>
					       <th fieldname="SECOND_MAY" id="year15"></th>
					       <th fieldname="THIRD_MAY" id="year16"></th>
					       <th fieldname="RATE_MAY_YOY" >同比增长率</th>
					       <th fieldname="RATE_MAY_MOM" >环比增长率</th>
					       <th fieldname="FIRST_JUN" id="year17"></th>
					       <th fieldname="SECOND_JUN" id="year18"></th>
					       <th fieldname="THIRD_JUN" id="year19"></th>
					       <th fieldname="RATE_JUN_YOY" >同比增长率</th>
					       <th fieldname="RATE_JUN_MOM" >环比增长率</th>
					       <th fieldname="FIRST_JULY" id="year20"></th>
					       <th fieldname="SECOND_JULY" id="year21"></th>
					       <th fieldname="THIRD_JULY" id="year22"></th>
					       <th fieldname="RATE_JULY_YOY" >同比增长率</th>
					       <th fieldname="RATE_JULY_MOM" >环比增长率</th>
					       <th fieldname="FIRST_AUGUST" id="year23"></th>
					       <th fieldname="SECOND_AUGUST" id="year24"></th>
					       <th fieldname="THIRD_AUGUST" id="year25"></th>
					       <th fieldname="RATE_AUGUST_YOY" >同比增长率</th>
					       <th fieldname="RATE_AUGUST_MOM" >环比增长率</th>
					       <th fieldname="FIRST_SEPT" id="year26"></th>
					       <th fieldname="SECOND_SEPT" id="year27"></th>
					       <th fieldname="THIRD_SEPT" id="year28"></th>
					       <th fieldname="RATE_SEPT_YOY" >同比增长率</th>
					       <th fieldname="RATE_SEPT_MOM" >环比增长率</th>
					       <th fieldname="FIRST_OCT" id="year29"></th>
					       <th fieldname="SECOND_OCT" id="year30"></th>
					       <th fieldname="THIRD_OCT" id="year31"></th>
					       <th fieldname="RATE_OCT_YOY" >同比增长率</th>
					       <th fieldname="RATE_OCT_MOM" >环比增长率</th>
					       <th fieldname="FIRST_NOV" id="year32"></th>
					       <th fieldname="SECOND_NOV" id="year33"></th>
					       <th fieldname="THIRD_NOV" id="year34"></th>
					       <th fieldname="RATE_NOV_YOY" >同比增长率</th>
					       <th fieldname="RATE_NOV_MOM" >环比增长率</th>
					       <th fieldname="FIRST_DEC" id="year35"></th>
					       <th fieldname="SECOND_DEC" id="year36"></th>
					       <th fieldname="THIRD_DEC" id="year37"></th>
					       <th fieldname="RATE_DEC_YOY" >同比增长率</th>
					       <th fieldname="RATE_DEC_MOM">环比增长率</th>
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