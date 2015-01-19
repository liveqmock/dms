<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>问题类型维护</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/noticeManage/ProblemTypeMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/noticeManage/ProblemTypeMngAction/delete.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchProblemType");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-problemTypeList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/dealer/noticeManage/problemType/problemTypeAdd.jsp?action=1", "add", "新增问题类型", diaAddOptions);
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/dealer/noticeManage/problemType/problemTypeAdd.jsp?action=2", "update", "修改问题类型", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?id="+$(rowobj).attr("ID");
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
			$("#tab-problemTypeList").removeResult($row);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：通知通告&gt;问题类型维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchProblemType" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchProblemType">
						<tr>
							<td><label>问题类型代码：</label></td>
							<td><input type="text" id="problemCode" name="problemCode" datasource="PROBLEM_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>问题类型名称：</label></td>
							<td><input type="text" id="problemName" name="problemName" datasource="PROBLEM_NAME" operation="like" datatype="1,is_null,300" /></td>
							<td><label>有效标示：</label></td>
							<td>
		        				<select type="text" id="typeStatus"  name="typeStatus" datasource="TYPE_STATUS" kind="dic" src="YXBS" datatype="1,is_null,6" >
				    			<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    			<option value="<%=DicConstant.YXBS_02 %>" >无效</option>
				    			<option value="-1">--</option>
				    			</select>
		        			</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_problemTypeList">
				<table style="display:none;width:100%;" id="tab-problemTypeList" name="tablist" ref="page_problemTypeList" refQuery="tab-searchProblemType">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PROBLEM_CODE" >问题类型代码</th>
							<th fieldname="PROBLEM_NAME" >问题类型名称</th>
							<th fieldname="TYPE_STATUS">状态</th>
							<th fieldname="CREATE_USER">创建人</th>
							<th fieldname="CREATE_TIME">创建时间</th>
							<th fieldname="UPDATE_USER">修改人</th>
							<th fieldname="UPDATE_TIME">修改时间</th>
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