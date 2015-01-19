<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.frameImpl.Constant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>故障模式和工时定额关系</title>
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
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
/* 	$("#search").click(function(){
		if($("#gzmslb").is(":hidden"))
		{
			$("#gzmslb").show();
			$("#gzmslb").jTable();
		}
	}); */
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
	
		//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/faulttasktime/FaultTasktimeAdd.jsp?action=1", "FaultTasktime", "新增故障模式和工时定额关系", options);
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/FaultTasktimeMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });

	
   //导入按钮响应
   $('#btn-impProm').bind('click', function () {
       //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
       //最后一个参数表示 导入成功后显示页                    
       importXls("SE_BA_FAULT_TASKTIME_TMP","",7,3,"/jsp/dms/oem/service/basicinfomng/faulttasktime/importSuccess.jsp");
   }); 	
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/faulttasktime/FaultTasktimeAdd.jsp?action=2", "FaultTasktime", "故障模式和工时定额关系", options);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var status=<%=Constant.YXBS_02%>;
	var url = deleteUrl + "?relationId="+$(rowobj).attr("RELATION_ID")+"&status="+status;
	/**
	 * sendPost:提交一般请求方法
	 * @url:提交请求url路径
	 * @"delete":提交请求句柄id
	 * @"":提交请求参数，可为空（json格式）
	 * @deleteCallBack：提交请求后台执行成功后，页面回调函数
	 * @"true":是否需要confirm确认对话框，"true"为弹出确认框
	 */
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
	 	$("#tab-list").removeResult($row);		
		
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;故障模式和工时定额关系</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>故障模式代码：</label></td>
						<td><input type="text" id="FAULT_PATTERN_CODE" name="FAULT_PATTERN_CODE" datasource="T.FAULT_PATTERN_CODE" operation="left_like"  datatype="1,is_null,100" value="" /></td>
						<td><label>故障模式名称：</label></td>
						<td><input type="text" id="FAULT_PATTERN_NAME" name="FAULT_PATTERN_NAME" datasource="T.FAULT_PATTERN_NAME" operation="like"  datatype="1,is_null,100" value="" /></td>
						 <td><label>有效标识：</label></td>
			   		 <td>
				      <select type="text" class="combox" id="STATUS" name="STATUS" kind="dic" src="YXBS" datasource="T.STATUS" datatype="1,is_null,10" >
				    	 <option value="-1" selected>--</option>	
				    </select>
			    	</td> 
					</tr>
					<tr>
						<td><label>工时代码：</label></td>
						<td><input type="text" id="timeCode" name="timeCode" datasource="T.TIME_CODE" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>工时名称：</label></td>
						<td><input type="text" id="timeName" name="timeName" datasource="T.TIME_NAME" operation="like"  datatype="1,is_null,100" value="" /></td>
						<td><label>部位名称：</label></td>
						<td><input type="text" id="positionName" name="positionName" datasource="B.POSITION_NAME" operation="like"  datatype="1,is_null,100" value="" /></td>
					</tr>
						<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="partCode" name="partCode" datasource="PART_CODE" datatype="1,is_digit_letter,30" operation="like" action="show" /></td>
					    <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="partName"  name="partName" datasource="PART_NAME"  datatype="1,is_null,30" operation="like" action="show"  /></td>
		
		            </tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
						
						</li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-impProm" >明细导入</button></div></div></li>
	
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
									
							<th fieldname="FAULT_PATTERN_CODE" >故障模式代码</th>
							<th fieldname="FAULT_PATTERN_NAME" >故障模式名称</th>
							<th fieldname="POSITION_NAME" >部位名称</th>
							<th fieldname="TIME_CODE" >工时代码</th>
							<th fieldname="TIME_NAME" >工时名称</th>	
							<th fieldname="AMOUNT_SET" >工时定额</th>			
							<th fieldname="STATUS" >状态</th>
					        <th fieldname="CREATE_USER" >创建人</th>
					        <th fieldname="CREATE_TIME" >创建时间</th>
					        <th fieldname="UPDATE_USER" >更新人</th>
					        <th fieldname="UPDATE_TIME" >更新时间</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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