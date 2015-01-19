<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:索赔报单信息细表
	 Version:1.0
     Collator：baixiaoliangn@sxqc.com
     Date：2014-07
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<jsp:include page="/head.jsp" />
<title>索赔报单信息细表</title>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：/service/basicinfomng/RulePartMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * RulePartMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/service/reportForms/ClaimReportAction/search.ajax";
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		
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
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/reportForms/ClaimReportAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});


//金额格式化
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：报表管理&gt;索赔报单信息细表</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">	
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				   <tr>
						<td>
							<label>索赔单号：</label></td>
							<td><input type="text" id="CLAIM_NO" name="CLAIM_NO" datasource="T.CLAIM_NO" datatype="1,is_null,30" value=""  operation="like" /></td>
							<td><label>VIN：</label></td>
							<td><input type="text" id="vin" name="vin" datasource="T.VIN" datatype="1,is_null,17" value=""  operation="like" /></td>
							<td><label>提报日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq"  group="in-ckrq,in-jsrq" style="width:75px;" name="in-ckrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" onclick="WdatePicker()" kind="date" operation = ">=" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq"  group="in-ckrq,in-jsrq" style="width:75px;margin-left:-30px;" name="in-jsrq" datasource="T.OLDPART_FINAL_DATE" datatype="1,is_date,30" kind="date" onclick="WdatePicker()" operation = "<=" />
					   		 </td>
						</tr>
				
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
				
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
								<th fieldname="CLAIM_NO" >索赔单号</th>
								<th fieldname="VIN" >底盘号</th>
								<th fieldname="MODELS_CODE" >车辆型号</th>
								<th fieldname="ENGINE_NO" >发动机号</th>
								<th fieldname="VEHICLE_SUPP" >生成单位</th>
								<th fieldname="BUY_DATE" >购车日期</th>
								<th fieldname="F_FAULT_DATE" >首次故障日期</th> 
								<th fieldname="FAULT_DATE" >故障日期</th> 
								<th fieldname="F_MILEAGE" align="right">首次故障里程</th> 
								<th fieldname="T_MILEAGE" align="right">故障里程</th> 
								<th fieldname="CODE" >服务站代码</th> 
								<th fieldname="ONAME" >服务站名称</th> 
								<th fieldname="USER_NAME" >用户姓名</th> 
								<th fieldname="USER_ADDRESS" >用户地址</th> 
								<th fieldname="PHONE" >联系电话</th> 
								<th fieldname="MILEAGE" align="right">强保里程</th> 
								<th fieldname="FAULT_CODE" >故障编号</th> 
								<th fieldname="FAULT_NAME" >故障名称</th> 
								<th fieldname="OLD_PART_CODE" >故障件编号</th> 
								<th fieldname="OLD_PART_NAME" >故障件名称</th> 
								<th fieldname="OLD_SUPPLIER" >故障件生成厂家</th> 
								<th fieldname="WORK_COSTS" refer="amountFormat" align="right" >工时费</th> 
								<th fieldname="CLAIM_COSTS" refer="amountFormat" align="right">材料费</th> 
								<th fieldname="SEVEH_COSTS" refer="amountFormat" align="right" >出车费</th> 
								<th fieldname="D_MILEAGE" align="right">出车里程</th> 
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>