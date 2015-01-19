<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>问题清单</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/noticeManage/ProblemMngAction/oemSearch.ajax";

//定义弹出窗口样式
var diaAnswerOptions = {max:false,width:750,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchProblemAnswer");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-problemAnswerList");
	});
});

//查看问题
function doAnswer(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/dealer/noticeManage/problem/problemAnswer.jsp", "answer", "回复问题", diaAnswerOptions);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：通知通告&gt;回复问题</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchProblemAnswer" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchProblemAnswer">
						<tr>
							<td><label>问题类型：</label></td>
							<td><input type="text" id="problemCode" name="problemCode" datasource="PROBLEM_CODE" operation="like" kind="dic" src="T#MAIN_PROBLEM_TYPE:PROBLEM_CODE:PROBLEM_NAME:TYPE_STATUS=100201" datatype="1,is_null,30" /> 
								<input id="out1" type="hidden"/>
							</td>
							<td><label>提报渠道：</label></td>
							<td><input type="text" id="applyUser" name="applyUser" datasource="APPLY_USER" operation="like" datatype="1,is_null,30" /></td>	
							<td><label>问题状态：</label></td>
							<td>
		        				<select type="text" id="problemStatus"  name="problemStatus" datasource="PROBLEM_STATUS" kind="dic" src="WTHFZT" datatype="1,is_null,6" >
				    				<option value="<%=DicConstant.WTHFZT_01 %>" selected>未回复</option>
				    				<option value="<%=DicConstant.WTHFZT_02 %>" >已回复</option>
				    				<option value="-1">--</option>
				    			</select>
		        			</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_problemAnswerList">
				<table style="display:none;width:100%;" id="tab-problemAnswerList" name="tablist" ref="page_problemAnswerList" refQuery="tab-searchProblemAnswer">					
					<thead>
						<tr>							
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PROBLEM_NAME" >问题类型</th>
							<th fieldname="PROBLEM_DESCRIBE" maxlength="4">问题描述</th>
							<th fieldname="APPLY_USER">提报渠道</th>
							<th fieldname="APPLY_DATE">提报时间</th>
							<th fieldname="FEEDBACK_REMARKS" maxlength="4">反馈意见</th>
							<th fieldname="FEEDBACK_USER">反馈人</th>
							<th fieldname="FEEDBACK_DATE">反馈时间</th>
							<th fieldname="PROBLEM_STATUS">回复状态</th>
							<th colwidth="85" type="link" title="[查看]|"  action="doAnswer" >操作</th>
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