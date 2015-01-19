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
<div id="diaS-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-problemSearchInfo" class="editForm" >
			<!-- 隐藏域 -->			
		    <input type="hidden" id="diaS-problemId" name="diaS-problemId" datasource="PROBLEM_ID" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-problemSearchInfo">
				<tr>
					<td><label>问题类型：</label></td>
					<td><input type="text" id="diaS-problemName" name="diaS-problemName" datasource="PROBLEM_NAME"  readonly/></td>
			    </tr>
				<tr>
					<td><label>提报日期：</label></td>
					<td><input type="text" id="diaS-applyDate" name="diaS-applyDate" datasource="APPLY_DATE"  readonly/></td>
					<td><label>反馈日期：</label></td>
					<td><input type="text" id="diaS-feedbackDate" name="diaS-feedbackDate" datasource="FEEDBACK_DATE"  readonly/></td>
				</tr>		   
                <tr>
					<td><label>问题描述：</label></td>
					<td colspan="3">
						<textarea id="diaS-problemDescribe" style="width:90%;height:40px;" name="diaS-problemDescribe" datasource="PROBLEM_DESCRIBE"   datatype="1,is_null,1000" readonly></textarea>
					</td>			
				</tr>
				<tr>
					<td><label>反馈意见：</label></td>
					<td colspan="3">
						<textarea id="diaS-feedbackRemarks" style="width:90%;height:40px;" name="diaS-feedbackRemarks" datasource="FEEDBACK_REMARKS"   datatype="0,is_null,1000" readonly></textarea>
					</td>			
				</tr>				 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li id="diaS-searchAtt"><div class="button"><div class="buttonContent"><button type="button" id="btn-downAtt">查看附件</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script type="text/javascript">

//初始化
$(function(){
	//给弹出框赋值
	var selectedRows = $("#tab-problemAskList").getSelectedRows();
	setEditValue("fm-problemSearchInfo",selectedRows[0].attr("rowdata"));
	
	//查看附件
	$("#btn-downAtt").bind("click", function(event){
		var problemId =$("#diaS-problemId").val();
		$.filestore.view(problemId);
	})
});

</script>