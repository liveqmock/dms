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
		<form method="post" id="fm-problemAnswerInfo" class="editForm" >
			<!-- 隐藏域 -->			
		    <input type="hidden" id="dia-problemId" name="dia-problemId" datasource="PROBLEM_ID" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-problemAnswerInfo">
				<tr>
					<td><label>问题类型：</label></td>
					<td><input type="text" id="dia-problemName" name="dia-problemName" datasource="PROBLEM_NAME"  readonly/></td>
			    </tr>
				<tr>
					<td><label>提报渠道：</label></td>
					<td><input type="text" id="dia-applyUser" name="dia-applyUser" datasource="APPLY_USER"  readonly/></td>
					<td><label>提报日期：</label></td>
					<td><input type="text" id="dia-applyDate" name="dia-applyDate" datasource="APPLY_DATE"  readonly/></td>
				</tr>		   
                <tr>
					<td><label>问题描述：</label></td>
					<td colspan="3">
						<textarea id="dia-problemDescribe" style="width:90%;height:40px;" name="dia-problemDescribe" datasource="PROBLEM_DESCRIBE"   datatype="1,is_null,1000" readonly></textarea>
					</td>			
				</tr>
				<tr>
					<td><label>反馈意见：</label></td>
					<td colspan="3">
						<textarea id="dia-feedbackRemarks" style="width:90%;height:40px;" name="dia-feedbackRemarks" datasource="FEEDBACK_REMARKS"   datatype="0,is_null,1000"></textarea>
					</td>			
				</tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li id="dia-searchAtt"><div class="button"><div class="buttonContent"><button type="button" id="btn-downAtt">查看附件</button></div></div></li>
				<li id="dia-save"><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">

var answerUrl = "<%=request.getContextPath()%>/service/noticeManage/ProblemMngAction/answer.ajax"; 

//初始化
$(function(){
	//给弹出框赋值
	var selectedRows = $("#tab-problemAnswerList").getSelectedRows();
	setEditValue("fm-problemAnswerInfo",selectedRows[0].attr("rowdata"));
	
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){		
		var $f = $("#fm-problemAnswerInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-problemAnswerInfo").combined(1) || {};
				
		doNormalSubmit($f,answerUrl,"btn-save",sCondition,diaSaveCallBack);
	});
	
	//查看附件
	$("#btn-downAtt").bind("click", function(event){
		var problemId =$("#dia-problemId").val();
		$.filestore.view(problemId);
	})
});

//保存回调函数
function diaSaveCallBack(res)
{
	try
	{
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>