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
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-problemAskInfo" class="editForm" >
			<!-- 隐藏域 -->			
		    <input type="hidden" id="dia-problemId" name="dia-problemId" datasource="PROBLEM_ID" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-problemAskInfo">
				<tr>
					<td><label>问题类型：</label></td>
					<td><input type="text" id="dia-problemCode" name="dia-problemCode" datasource="PROBLEM_CODE" operation="like" kind="dic" src="T#MAIN_PROBLEM_TYPE:PROBLEM_CODE:PROBLEM_NAME:TYPE_STATUS=100201" datatype="0,is_null,30" />  
						<input type="hidden" id="dia-problemName" name="dia-problemName" datasource="PROBLEM_NAME"/>
					</td>
				</tr>
				<tr>
					<td><label>问题描述：</label></td>
					<td width="100%">
						<textarea id="dia-problemDescribe" style="width:90%;height:60px;" name="dia-problemDescribe" datasource="PROBLEM_DESCRIBE"   datatype="0,is_null,1000"></textarea>
					</td>			
				</tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li id="dia-addAtt"><div class="button"><div class="buttonContent"><button type="button" id="addAtt">上传附件</button></div></div></li>
				<li id="dia-save"><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/noticeManage/ProblemMngAction";

//初始化
$(function(){

	//保存前先隐藏上传按钮
	$("#dia-addAtt").hide();

	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-problemAskInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-problemAskInfo").combined(1) || {};

		var askUrl = diaSaveAction + "/ask.ajax";
		doNormalSubmit($f,askUrl,"btn-save",sCondition,diaAskCallBack);
	});
	
	//上传附件按钮
	$("#addAtt").bind("click",function(){
		var problemId = $("#dia-problemId").val();
		$.filestore.open(problemId,{"folder":"true","holdName":"false","uploadLimit":4,"fileSizeLimit":0,"fileTypeDesc":"All Files","fileTypeExts":"*.*"});
	});
});

//问题反馈回调函数
function diaAskCallBack(res)
{
	try
	{
		var rows = res.getElementsByTagName("ROW");
		if(rows && rows.length > 0)
		{
			var problemId =getNodeText(rows[0].getElementsByTagName("PROBLEM_ID").item(0));//保存成功后,传递主键
			$("#dia-problemId").val(problemId);	
		}else
		{
			return false;
		}
		//第二个参数0表示插入到第几行
		$("#tab-problemAskList").insertResult(res,0);
		//保存成功后,显示上传附件按钮,隐藏保存按钮
		$("#dia-addAtt").show();
		$("#dia-save").hide();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   
	//给问题类型隐藏域赋值
	if(id=="dia-problemCode") 
	{
		$("#dia-problemName").val($("#"+id).val());
	}
	return true;
}
</script>