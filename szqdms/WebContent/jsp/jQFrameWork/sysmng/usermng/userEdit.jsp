<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
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
	<form method="post" id="fm-userInfo" class="editForm" >
		<!-- 隐藏域 -->
		<input type="hidden" id="dia-userId" name="dia-userId" datasource="USER_ID" />
		<input type="hidden" id="dia-userAuth" name="dia-userAuth" datasource="USER_AUTH" value="2"/>
		<div align="left">
			<fieldset>
			<legend align="right"><a onclick="onTitleClick('tab-userInfo')">&nbsp;用户基本信息编辑&gt;&gt;</a></legend>
			<table class="editTable" id="tab-userInfo">
				<tr>
					<td><label>用户级别：</label></td>
				    <td>
				    	<select type="text" id="dia-personType"  name="dia-personType" datasource="PERSON_TYPE" kind="dic" src="<%=DicConstant.JGJB %>" datatype="0,is_null,60" >
				    		<option value="<%=DicConstant.JGJB_02%>" selected>部门</option>
				    	</select>
				    </td>
				    <td><label>所属组织：</label></td>
				    <td><input type="text" id="dia-orgId"  name="dia-orgId" datasource="ORG_ID" kind="dic" datatype="0,is_null,60" /></td>
				</tr>
				<tr><td><label>用户账号：</label></td>
				    <td><input type="text" id="dia-account" name="dia-account" datasource="ACCOUNT" datatype="0,is_digit_letter,30"/></td>
				    <td><label>用户工号：</label></td>
				    <td><input type="text" id="dia-userSn" name="dia-userSn" datasource="USER_SN" datatype="1,is_digit_letter,16" /></td>
				</tr>
				<tr>
					<td><label>姓名：</label></td>
				    <td><input type="text" id="dia-personName" name="dia-personName" datasource="PERSON_NAME" datatype="0,is_name,32" /></td>
				    <td><label>身份证号：</label></td>
				    <td><input type="text" id="dia-idCard" name="dia-idCard" blurback="true" datasource="IDCARD" datatype="1,is_idcard,18" /></td>
				</tr>
				<tr>
					<td><label>性别：</label></td>
				    <td>
					    <select type="text" class="combox" id="dia-sex" name="dia-sex" kind="dic" src="XB" datasource="SEX" datatype="1,is_null,10" >
					    	<option value="-1" selected>--</option>
					    </select>
					</td>
				    <td><label>出生日期：</label></td>
				    <td><input type="text" kind="date" id="dia-birthdate" name="dia-birthdate" onclick="WdatePicker()" datasource="BIRTHDATE" datatype="1,is_date,10" /></td>
				</tr>
				<tr>
					<td><label>联系方式：</label></td>
				    <td><input type="text" id="dia-contactWay" name="dia-contactWay" datasource="CONTACT_WAY" datatype="1,is_null,30" /></td>
					<td><label>邮件地址：</label></td>
				    <td><input type="text" id="dia-mailFrom" name="dia-mailFrom" datasource="MAILFROM" datatype="1,is_email,30" value=""/></td>
				</tr>
				<tr>
					<td><label>用户类型：</label></td>
				    <td>
				    	<select class="combox" id="dia-personKind" name="dia-personKind" kind="dic" src="YHLX" filtercode="[^1]" datasource="PERSON_KIND" datatype="0,is_null,10" >
				    		<option value="<%=DicConstant.YHLX_01%>" selected>普通用户</option>
				   		</select>
				    </td>
				    <td><label>数据密级：</label></td>
				    <td>
				    	<input type="text" id="dia-secretLevel" name="dia-secretLevel" kind="dic" src="SJMJ" datasource="SECRET_LEVEL" datatype="0,is_null,10" />
				    </td>
				 </tr>
				 <tr>
					<td><label>有效标识：</label></td>
				    <td>
					    <select  class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >
					    	<option value="<%=DicConstant.YXBS_01%>" selected>有效</option>
					    </select>
				    </td>
					<td><label>描述：</label></td>
				    <td><input type="text" id="dia-des" name="dia-des" datasource="DES" datatype="1,is_null,50" /></td>
				</tr>
				<!-- <tr>
					<td><label>访问权限：</label></td>
				    <td>
					    <select  class="combox" id="dia-userAuth" name="dia-userAuth" kind="dic" src="E#0=内网用户:1=外网用户:2=不限制" datasource="USER_AUTH" datatype="0,is_null,10" >
					    	<option value="2" selected>不限制</option>
					    </select>
				    </td>					
				</tr>
				<tr>
					<td><label>mac地址：</label></td>
				    <td colspan="3">
				    	<input type="text" id="dia-authMac" name="dia-authMac" style="width:87%;" datasource="AUTH_MAC" datatype="1,is_null,1000" />
				    </td>
				 </tr> -->
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" id="btn-grantRoles">授予角色</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" confirm="是否确认重置密码为000000？" id="btn-resetPwd">重置密码</button></div></div></li>
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
var diaSaveAction = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction";
var diaResetPwdAction = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/resetPass.ajax";
var diaAction = "<%=action%>";
//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-userInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-userInfo").combined(1) || {};
		if(diaAction == "1")	//插入动作
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
	
	// 用户级别发生变化时，清空子项
	$("#dia-personType").change(function(){
			$("#dia-orgId").val("").attr("code", "");
	});
	
	//授予角色按钮
	$("#btn-grantRoles").bind("click", function(event){
		openRoles("1");
	});
	//重置密码按钮
	$("#btn-resetPwd").bind("click", function(event){
		var resetPwsUrl = diaResetPwdAction + "?userId=" + $("#dia-userId").val() + "&account="+$("#dia-account").val();
		sendPost(resetPwsUrl,"btn-resetPwd","",resetCallBack,"true");
	});

	if(diaAction != "1")//修改操作
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-userlist").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		//"fm-userInfo"：要赋值区域的id
		//selectedRows[0].attr("rowdata"):行对象属性rowdata中保存了xml格式的行数据
		setEditValue("fm-userInfo",selectedRows[0].attr("rowdata"));
		$("#dia-orgId").attr("code",selectedRows[0].attr("ORG_ID"));
		$("#dia-orgId").val(selectedRows[0].attr("ORG_NAME"));
		$("#dia-account").attr("readonly",true);
		var $input = $("#dia-personType");
		$("#dia-orgId").attr("src","T#TM_ORG:ORG_ID:SNAME:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND LEVEL_CODE=" + $input.attr("code") + " ORDER BY ORG_TYPE,CODE ");
	}else
	{
		$("#btn-grantRoles").parent().parent().addClass("buttonDisabled");
		$("#btn-grantRoles").attr("disabled",true);
		$("#btn-resetPwd").parent().parent().addClass("buttonDisabled");
		$("#btn-resetPwd").attr("disabled",true);
		var $input = $("#dia-personType");
		$("#dia-orgId").attr("src","T#TM_ORG:ORG_ID:SNAME:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND LEVEL_CODE=" + $input.val() + " ORDER BY ORG_TYPE,CODE ");
	}
});
//重置密码回调方法
function resetCallBack(res)
{
	//同步集群节点（业务功能不需要）
	var syncUrl = diaSaveAction + "/synchronize.ajax?type=2&account="+$("#dia-account").val();
	sendPost(syncUrl,"","","","false");
}

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-userlist").insertResult(res,0);
		$("#btn-grantRoles").parent().parent().removeClass("buttonDisabled");
		$("#btn-grantRoles").attr("disabled",false);
		$("#btn-resetPwd").parent().parent().removeClass("buttonDisabled");
		$("#btn-resetPwd").attr("disabled",false);
		//设置userId主键域
		$("#dia-userId").val(getNodeText(res.getElementsByTagName("USER_ID").item(0)));
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=1&account="+$("#dia-account").val();
		sendPost(syncUrl,"","","","false");
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
		var selectedIndex = $("#tab-userlist").getSelectedIndex();
		$("#tab-userlist").updateResult(res,selectedIndex);
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=2&account="+$("#dia-account").val();
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function blurBack(tempId)
{
	if(!tempId) return false;
	if(tempId && tempId == "dia-idCard")
	{
		var birth = getBirthday($("#"+tempId).val());
		var sex = getSex($("#"+tempId).val());
		$("#dia-birthdate").val(birth.substr(0,4)+"-"+birth.substr(4,2)+"-"+birth.substr(6));
		switch(sex)
		{
			case '<%=DicConstant.XB_01%>':
				$("#dia-sex").val("<%=DicConstant.XB_01%>");
				$("#dia-sex").attr("code","<%=DicConstant.XB_01%>");
				$("#op_combox_dia-sex").find(".selected").removeClass("selected");
				$("#op_combox_dia-sex").find("a[value='<%=DicConstant.XB_01%>']").addClass("selected");
				$("a[name='dia-xb']").text("女");
				break;
			case '<%=DicConstant.XB_02%>':
				$("#dia-sex").val("<%=DicConstant.XB_02%>");
				$("#dia-sex").attr("code","<%=DicConstant.XB_02%>");
				$("#op_combox_dia-sex").find(".selected").removeClass("selected");
				$("#op_combox_dia-sex").find("a[value='<%=DicConstant.XB_02%>']").addClass("selected");
				$("a[name='dia-sex']").text("男");
				break;
		}	
	}
}

//点击字典回调方法
function afterDicItemClick(id)
{
	var ret =  true;
	switch(id)
	{
		case "dia-personType":
				var $input = $("#"+id);
				$("#dia-orgId").attr("src","T#TM_ORG:ORG_ID:SNAME:1=1 AND STATUS=<%=DicConstant.YXBS_01%> AND LEVEL_CODE=" + $input.attr("code") + " ORDER BY ORG_TYPE,CODE ");
			break;
	}
	return ret;
}
</script>