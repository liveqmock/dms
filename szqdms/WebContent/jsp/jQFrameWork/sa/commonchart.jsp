<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String contextPath = request.getContextPath();
%>
<!-- 
	 Title:报表预览
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<script src="<%=contextPath %>/lib/plugins/sa/sa.core.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=contextPath %>/lib/plugins/sa/charts/css/style.css" type="text/css"></link>
<script src="<%=contextPath %>/lib/plugins/sa/charts/mine.charts.js" type="text/javascript"></script>
<script src="<%=contextPath %>/lib/plugins/sa/charts/sa.charts.js" type="text/javascript"></script>
<title>图表显示</title>
<script type="text/javascript">
var chart;
var pLeft = 0;
var pTop = 0;
var dialogCommonChart = top.$.pdialog._current;
var jdata = dialogCommonChart.data("op")["sJdata"];
$(function(){
	$.sa.charts.showChart({jdata:jdata,chartTitle:"test",chartType:"1"});
	var yItem = "";
	for(var i=0;i<jdata.length;i++)
	{
		jdata[i]
	}
	$("#d-chart-y").html(yItem);
});
</script>
</head>
<body>
<div id='background1' class='background'></div>
<div id='progressBar1' class='progressBar'>loading...</div>
<div style="width:100%;">
	<div class="tabs" eventType="click">
		<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:void(0)"><span>图表显示</span></a></li>
						<li><a href="javascript:void(0)"><span>图表设置</span></a></li>
					</ul>
				</div>
		</div>
		<div class="tabsContent">
			<div id="chartdiv" style="width: auto; height: 380px;" class="showChart"></div>
			<div style="width: auto; height: 380px;">
				<table class="editTable">
					<tr>
						<td>
							<label>图表类型：</label>
						</td>
						<td>	
							<select class="combox" id="sel-chartType" name ="sel-chartType" kind="dic" src="E#1=曲线图:2=柱状图:3=饼状图:4=心电图:5=蜘网图" datatype="1,is_null,10" datasource="CHART_TYPE">
								<option value="1" selected>曲线图</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label>显示效果：</label>
						</td>
						<td>	
							<select class="combox" id="sel-chartType" name ="sel-chartType" kind="dic" src="E#1=平铺:2=3D:3=特效" datatype="1,is_null,10" datasource="CHART_TYPE">
								<option value="1" selected>平铺</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>Y轴显示项：</label></td>
						<td><div id="d-chart-y"></div></td>
					</tr>
					<tr>
						<td><label>X轴显示项：</label></td>
						<td><div id="d-chart-x"></div></td>
					</tr>
				</table>
				<div class="buttonActive" style="margin-left:100px;"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="dia-save">设为默认</button></div></div>
				<div class="button" style="margin-left:10px;"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="dia-save">图形切换</button></div></div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
<div id="reportTable" style="display:none;">
</div>
</body>
</html>