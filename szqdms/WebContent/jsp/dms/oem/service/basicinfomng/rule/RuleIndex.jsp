<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>三包规则管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/RuleMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/RuleMngAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-Rule");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-Rule");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/rule/RuleAdd.jsp?action=1", "Rule", "三包规则新增", diaAddOptions,true);
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/rule/RuleAdd.jsp?action=2", "Rule", "三包规则修改", diaAddOptions,true);
}
	var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?ruleId="+$(rowobj).attr("RULE_ID")+"&ruleStatus="+$(rowobj).attr("RULE_STATUS");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-Rule").removeResult($row);
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
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础信息管理&gt;三包规则管理</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-Rule" method="post">
				<input type="hidden" id="RULE_ID" name="RULE_ID" datasource="T.RULE_ID" />
				<div class="searchBar" align="left">
					<table class="searchContent" id="RuleTable">
						<tr>
							<td><label>规则代码：</label></td>
							<td><input type="text" id="RULE_CODE" name="RULE_CODE" datasource="T.RULE_CODE" datatype="1,is_digit_letter,30"  value="" operation="like" /></td>
							<td><label>规则名称：</label></td>
							<td><input type="text" id="RULE_NAME" name="RULE_NAME" datasource="T.RULE_NAME" datatype="1,is_null,30" value="" operation="like" /></td>
						</tr>
						<tr>
							<td><label>用户类别：</label></td>
							<td><select type="text" class="combox" id="USER_TYPE" name="USER_TYPE" kind="dic" src="CLYHLX" datasource="USER_TYPE" datatype="1,is_null,10" >
									<option value=-1 selected>--</option>
								</select>
							</td>							
							<td><label>规则状态：</label></td>
							<td><select type="text" id="RULE_STATUS" name="RULE_STATUS" datasource="T.RULE_STATUS" kind="dic" class="combox" src="GZZT" datatype="1,is_null,8" value="" >
									<option value=-1 selected>--</option>
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
			<div id="page_Rule" >
				<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
				<table style="display:none;width:100%;" id="tab-Rule" name="tablist" ref="page_Rule" refQuery="tab-Rule" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="RULE_NAME" >规则名称</th>
								<th fieldname="USER_TYPE" >用户类别</th>
								<th fieldname="REMARKS" >备注</th>
								<th fieldname="STATUS" >状态</th>
								 <th fieldname="CREATE_USER" >创建人</th>
					        <th fieldname="CREATE_TIME" >创建时间</th>
					        <th fieldname="UPDATE_USER" >更新人</th>
					        <th fieldname="UPDATE_TIME" >更新时间</th>
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