<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-agreeReview" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-reviewId" name="dia-reviewId" datasource="REVIEW_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-directTypeInfo">
				<tr>
					<td><label>组员账号：</label></td>
				    <td><input type="text" id="dia-userAccount"  name="dia-userAccount"  value="" datasource="USER_ACCOUNT" datatype="0,is_null,32" /></td>
					
				</tr>
				<tr>
					<td><label>组员名称：</label></td>
				    <td>
				    	<input type="text" id="dia-userName"  name="dia-userName" datasource="USER_NAME" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
					<td><label>评审职能：</label></td>
				    <td>
				    	<select id="dia-reviewType" name="dia-reviewType" kind="dic" src="PSZN" datasource="REVIEW_TYPE" datatype="0,is_null,6" >
					    	<option value="-1" selected>-----</option>
				    	</select>
				    </td>
				 </tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
			<!-- li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li-->
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/AgreeReviewAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 

	} else {  //修改初始化
		var selectedRows = $("#tab-agreeReviewList").getSelectedRows();
		setEditValue("fm-agreeReview",selectedRows[0].attr("rowdata"));
		$("#dia-userAccount").attr("readonly", true);
	}
	
	//重置按钮
	$("#btn-reset").bind("click", function(event){
		$('#fm-agreeReview')[0].reset();
	});
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-agreeReview");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-agreeReview").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{   
			var addUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-agreeReviewList").insertResult(res,0);
		//$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
		var selectedIndex = $("#tab-agreeReviewList").getSelectedIndex();
		$("#tab-agreeReviewList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>