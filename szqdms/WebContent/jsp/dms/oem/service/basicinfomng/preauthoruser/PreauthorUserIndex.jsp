<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权人员维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorUserMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorUserMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:false,width:550,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
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
	
	   	//判断id中是否包含LEVEL_CODE的值
		if($("#LEVEL_CODE").val() == 0)
		{
		
			$("#LEVEL_ID").val("");
	
		}
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
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/preauthoruser/PreauthorUserAdd.jsp?action=1", "PreauthorUser", "新增其他费用类型", options);
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/preauthoruser/PreauthorUserAdd.jsp?action=2", "PreauthorUser", "其他费用类型", options);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var status=<%=Constant.YXBS_02%>;
	var url = deleteUrl + "?userId="+$(rowobj).attr("USER_ID")+"&status="+status;
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


//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典

	//判断id中是否包含dia-LEVEL_CODE的值
	if(id.indexOf("LEVEL_CODE") == 0)
	{
		if($row.attr("LEVEL_ID")){
		
		$("#LEVEL_ID").val($row.attr("LEVEL_ID"));
		}
	

	}
	
	 
	return true;
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;预授权人员维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					   <td><input type="hidden" id="LEVEL_ID" name="LEVEL_ID" datasource="T.LEVEL_ID" /></td>
					<tr>
						<td><label>预授权级别：</label></td>
						<td>  <input type="text" id="LEVEL_CODE"  name="LEVEL_CODE" datasource="T.LEVEL_CODE" kind="dic" src="T#SE_BA_PREAUTHOR_LEVEL A: A.LEVEL_CODE:A.LEVEL_NAME{LEVEL_ID}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
				        </td>
				        <td><label>状态：</label></td>
						<td>
							  <select type="text" class="combox" id="STATUS" name="STATUS" kind="dic" src="YXBS" datasource="T.STATUS" datatype="1,is_null,10" >
						    	<option value="-1">--</option>
					   		</select>
						</td> 
					</tr>
					<tr>
						<td><label>员工代码：</label></td>
						<td><input type="text" id="USER_CODE" name="USER_CODE" datasource="T.USER_CODE" operation="right_like"  datatype="1,is_null,100" value="" /></td>
						<td><label>员工名称：</label></td>
						<td><input type="text" id="USER_NAME" name="USER_NAME" datasource="T.USER_NAME" operation="right_like"  datatype="1,is_null,100" value="" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
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
						 
							
						 
							<th fieldname="USER_CODE" >预授权人员代码</th>
							<th fieldname="USER_NAME" >预授权人员名称</th>
							<th fieldname="LEVEL_CODE" >预授权级别代码</th>
							<th fieldname="LEVEL_NAME" >预授权级别名称</th>						
							<th fieldname="CREATE_USER" >创建人</th>
							<th fieldname="CREATE_TIME" >创建时间</th>
							<th fieldname="UPDATE_USER" >更新人</th>
							<th fieldname="UPDATE_TIME" >更新时间</th>
							<th fieldname="STATUS" >状态</th>
							
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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