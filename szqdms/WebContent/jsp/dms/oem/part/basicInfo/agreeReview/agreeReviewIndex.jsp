<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:合同评审组管理
	 Version:1.0
     Author：suoxiuli 2014-07-26
     Remark：agree review
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>合同评审组</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/AgreeReviewAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/AgreeReviewAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:400,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchAgreeReview");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-agreeReviewList");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/agreeReview/agreeReviewAdd.jsp?action=1", "新增合同评审组成员", "新增合同评审组成员", diaAddOptions);
	});                           
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/agreeReview/agreeReviewAdd.jsp?action=2", "修改合同评审组成员", "修改合同评审组成员", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?reviewId="+$(rowobj).attr("REVIEW_ID");
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
		{   
		    $("#btn-search").trigger("click");
		}
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：  配件管理  &gt; 基础数据管理   &gt; 基础信息管理   &gt; 合同评审组管理</h4>
	<div class="page" >
	<div class="pageHeader" >
			<!-- 提交查询请求form -->
			<form method="post" id="fm-searchAgreeReview">
				<div class="searchBar" align="left" >
					<table class="searchContent" id="tab-searchAgreeReview">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>组员账号：</label></td>
					    <td><input type="text" id="userAccount"  name="userAccount" datasource="USER_ACCOUNT"  datatype="1,is_null,32" operation="like" /></td>
					    <td><label>组员名称：</label></td>
					    <td><input type="text" id="userName" name="userName" datasource="USER_NAME" datatype="1,is_null,30" operation="like" /></td>
					</tr>
					<tr>
						<td><label>评审职能：</label></td>
					    <td>
					    	<select id="reviewType" name="reviewType" kind="dic" src="PSZN" datasource="REVIEW_TYPE" datatype="1,is_null,6" operation="=" >
					    	<option value="-1" selected>-----</option>
					    	</select>
					    </td>
					    <td><label>有效标识：</label></td>
					    <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
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
		<div id="page_agreeReviewList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-agreeReviewList" name="tablist" ref="page_agreeReviewList" refQuery="tab-searchAgreeReview" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="REVIEW_TYPE" >评审职能</th>
							<th fieldname="USER_NAME" >组员名称</th>
							<th fieldname="CREATE_USER" >创建人</th>
							<th fieldname="CREATE_TIME" >创建时间</th>
							<th fieldname="STATUS" >有效标识</th>
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