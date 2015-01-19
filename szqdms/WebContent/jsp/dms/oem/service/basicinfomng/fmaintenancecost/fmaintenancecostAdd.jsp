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
		<form method="post" id="fm-fmaintenancecostInfo" class="editForm" >
			<!-- 隐藏域 -->
		    <input type="hidden" id="dia-costId" name="dia-costId" datasource="COST_ID" />
		    <input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		    <input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-fmaintenancecostInfo">
				<tr>
					<td><label>驱动形式：</label></td>
					<td><input type="text" id="dia-driveForm" name="dia-driveForm" datasource="DRIVE_FORM" kind="dic" src="QDXS" datatype="0,is_null,6" /></td>
					<td><label>首保费用：</label></td>
					<td><input type="text" id="dia-maintenanceCost" name="dia-maintenanceCost" datasource="MAINTENANCE_COST" datatype="0,is_null,10" /></td>
			    </tr>
				<tr>
					<td><label>有效标示：</label></td>
					<td>
		        		<select id="dia-status"  name="dia-status" datasource="STATUS" kind="dic" src="YXBS" datatype="0,is_null,6" >
				    		<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
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
				<li><div class="button"><div class="buttonContent"><button type="button" id="btn-reset">重&nbsp;&nbsp;置</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/FmaintenanceCostMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-fmaintenancecostInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-fmaintenancecostInfo").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	
	//绑定重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#fm-fmaintenancecostInfo")[0].reset();
	}); 
	
	//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-fmaintenancecostlist").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		setEditValue("fm-fmaintenancecostInfo",selectedRows[0].attr("rowdata"));
		$("#dia-driveForm").attr("readonly",true);
	}else{}
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-fmaintenancecostlist").insertResult(res,0);
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
		var selectedIndex = $("#tab-fmaintenancecostlist").getSelectedIndex();
		$("#tab-fmaintenancecostlist").updateResult(res,selectedIndex);
		//关闭当前窗口
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>