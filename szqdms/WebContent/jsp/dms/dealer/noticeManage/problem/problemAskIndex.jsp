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

var searchUrl = "<%=request.getContextPath()%>/service/noticeManage/ProblemMngAction/dealerSearch.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:750,height:250,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchProblemAsk");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-problemAskList");
	});
	//提问按钮响应
	$("#btn-ask").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/dealer/noticeManage/problem/problemAsk.jsp", "ask", "反馈问题", diaAddOptions);
	});
});

//查看问题回复
function doSearch(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/dealer/noticeManage/problem/problemSearch.jsp", "search", "查看问题回复", diaAddOptions);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：通知通告&gt;反馈问题</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchProblemAsk" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchProblemAsk">
						<tr>
							<td><label>问题类型：</label></td>
							<td><input type="text" id="problemCode" name="problemCode" datasource="PROBLEM_CODE" operation="like" kind="dic" src="T#MAIN_PROBLEM_TYPE:PROBLEM_CODE:PROBLEM_NAME{ID}:TYPE_STATUS=100201" datatype="1,is_null,30" /> 
								<input id="out1" type="hidden"/>
							</td>	
							<td><label>问题状态：</label></td>
							<td>
		        				<select type="text" id="problemStatus"  name="problemStatus" datasource="PROBLEM_STATUS" kind="dic" src="WTHFZT" datatype="1,is_null,6" >
				    				<option value="-1">--</option>
				    			</select>
		        			</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-ask">提&nbsp;&nbsp;问</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_problemAskList">
				<table style="display:none;width:100%;" id="tab-problemAskList" name="tablist" ref="page_problemAskList" refQuery="tab-searchProblemAsk">					
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PROBLEM_NAME" >问题类型</th>
							<th fieldname="PROBLEM_DESCRIBE" maxlength="4">问题描述</th>
							<th fieldname="APPLY_DATE">提报时间</th>
							<th fieldname="FEEDBACK_REMARKS" maxlength="4">反馈意见</th>
							<th fieldname="FEEDBACK_DATE">反馈时间</th>
							<th fieldname="PROBLEM_STATUS">回复状态</th>
							<th colwidth="75" type="link" title="[查看]"  action="doSearch">操作</th>
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