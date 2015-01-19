<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务站星级评定</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/BaCodeMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/BaCodeMngAction/delete.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchBaCode");//获取页面提交请求的form对象
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
    	 * @"yhlb":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-bacodelist");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/bacode/bacodeAdd.jsp?action=1", "add", "新增基础代码", diaAddOptions);
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/bacode/bacodeAdd.jsp?action=2", "update", "修改基础代码", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?codeId="+$(rowobj).attr("CODE_ID");
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
			$("#tab-bacodelist").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>








<script type="text/javascript">
//查询提交方法
var url = "<%=request.getContextPath()%>/Fwzxjpdgl/FwzxjpdglAction.do";
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#fwzxjpdlb").is(":hidden")){
			$("#fwzxjpdlb").show();
			$("#fwzxjpdlb").jTable();
		}
	});
});
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);//行记录选择项
	var options = {max:false,width:450,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/jcxxgl/fwzxjpdxz.jsp?action=2", "fwzxjpdxx", "服务站星级评定评定", options);
}
function open(){
	alertMsg.info("弹出服务商！");
}
function doExp(){
	alertMsg.info("导出模版！");
}
function doImp(){
	alertMsg.info("导入服务店的星级评定！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;服务站星级评定</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fwzxjpdform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwzxjpdTable">
						<tr>
							<td><label>服务商代码：</label></td>
							<td><input type="text" id="WFSDM" name="WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text" id="WFSMC" name="WFSMC" datatype="1,is_null,100" value="" /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">批&nbsp;&nbsp;量&nbsp;&nbsp;导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="imp" onclick="doImp()">批&nbsp;&nbsp;量&nbsp;&nbsp;导&nbsp;&nbsp;入</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="fwzxjpd">
				<table width="100%" id="fwzxjpdlb" name="fwzxjpdlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>服务站代码</th>
							<th>服务站名称</th>
							<th>星级评定</th>
							<th colwidth="85" type="link" title="[评定]"  action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>服务商代码1</td>
							<td>服务站名称1</td>
							<td></td>
							<td ><a href="#" onclick="doUpdate()" class="op">[评定]</a></td>
						</tr>
						<tr>
							<td>2</td>
							<td>服务商代码2</td>
							<td>服务站名称2</td>
							<td>3</td>
							<td><a href="#" onclick="doUpdate()" class="op">[评定]</a></td>
						</tr>
						<tr>
							<td>3</td>
							<td>服务商代码3</td>
							<td>服务站名称3</td>
							<td></td>
							<td><a href="#" onclick="doUpdate()" class="op">[评定]</a></td>
						</tr>
						<tr>
							<td>4</td>
							<td>服务商代码4</td>
							<td>服务站名称4</td>
							<td>2</td>
							<td><a href="#" onclick="doUpdate()" class="op">[评定]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>