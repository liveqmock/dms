<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包策略管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchStrategy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-strategyList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/strategy/strategyAdd.jsp?action=1", "Strategy", "三包策略新增", diaAddOptions,true);
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/strategy/strategyAdd.jsp?action=2", "Strategy", "三包策略修改", diaAddOptions,true);
}
	var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?strategyId="+$(rowobj).attr("STRATEGY_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-strategyList").removeResult($row);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;三包策略维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchStrategy" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchStrategy">
						<tr>
							<td><label>三包策略代码：</label></td>
							<td><input type="text" id="strategyCode" name="strategyCode" datasource="STRATEGY_CODE" datatype="1,is_digit_letter,30"  value="" operation="like" /></td>
							<td><label>三包策略名称：</label></td>
							<td><input type="text" id="strategyName" name="strategyName" datasource="STRATEGY_NAME" datatype="1,is_null,30" value="" operation="like" /></td>
							<td><label>有效标示：</label></td>
							<td>
		        				<select type="text" id="status"  name="status" datasource="STATUS" kind="dic" src="YXBS" datatype="1,is_null,6" >
				    			<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    			<option value="<%=DicConstant.YXBS_02 %>" >无效</option>
				    			<option value="-1">--</option>
				    			</select>
		        			</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_strategyList" >
				<table style="display:none;width:100%;" id="tab-strategyList" name="tablist" ref="page_strategyList" refQuery="tab-searchStrategy" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="STRATEGY_CODE" >三包策略代码</th>
								<th fieldname="STRATEGY_NAME" >三包策略名称</th>
								<th fieldname="RULE_CODE">三包规则代码</th>
								<th fieldname="BEGIN_DATE" >开始日期</th>
								<th fieldname="END_DATE" >结束日期</th>
								<th fieldname="CREATE_USER" >录入人</th>
								<th fieldname="CREATE_TIME" >录入时间</th>
								<th fieldname="STATUS" >状态</th>
								<th colwidth="85" type="link" title="[修改]|[删除]"  action="doUpdate|doDelete" >操作</th>
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