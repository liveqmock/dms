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
	<form method="post" id="fm-orderCheck" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-checkerId" name="dia-checkerId" datasource="CHECKER_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<input type="hidden" id="dia-status" name="dia-status" datasource="STATUS" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-directTypeInfo">
				<tr>
					<td><label>办事处名称：</label></td>
				    <td>
				    	<input type="text" id="dia-orgCode"  name="dia-orgCode" kind="dic" 
				    		src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND ORG_TYPE='200004' AND STATUS='100201'" 
				    		datasource="ORG_CODE" datatype="0,is_null,100" />
				    	<input type="hidden" id="dia-orgName"  name="dia-orgName" datasource="ORG_NAME"  />
				    	<input type="hidden" id="dia-old-orgCode"  name="dia-old-orgCode" datasource="OLD_ORG_CODE"  />
				    </td>
				    
				 </tr>
				<tr>
					<td><label>审核员名称：</label></td>
				    <td>
				    	<input type="text" id="dia-userAccount"  name="dia-userAccount" kind="dic" 
				    		src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000063' AND STATUS='100201'" 
				    		datasource="USER_ACCOUNT" datatype="0,is_null,32" />
				   		<input type="hidden" id="dia-userName"  name="dia-userName" datasource="USER_NAME"  />
				    </td>
				</tr>
				<tr>
					<td><label>有 效 标 识：</label></td>
				    <td>
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,6" >
					    	<option value="-1" >--</option>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/OrderCheckAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 

	} else {  //修改初始化
		var selectedRows = $("#tab-orderCheckList").getSelectedRows();
		setEditValue("fm-orderCheck",selectedRows[0].attr("rowdata"));
		
		//将表字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		//审核员名称回显
		var userAccount = getNodeValue(objXML, "USER_ACCOUNT", 0);
		var userName = getNodeValue(objXML, "USER_NAME", 0);
		$("#dia-userAccount").val(userName);
		$("#dia-userAccount").attr("code",userAccount);
		
		//服务商名称回显
		var orgCode = getNodeValue(objXML, "ORG_CODE", 0);
		var orgName = getNodeValue(objXML, "ORG_NAME", 0);
		$("#dia-orgCode").val(orgName);
		$("#dia-orgCode").attr("code",orgCode);
		//修改前的服务商账号
		$("#dia-old-orgCode").val(orgCode);
		$("#dia-orgCode").attr("readonly",true);
	}
	
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-orderCheck");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-orderCheck").combined(1) || {};
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
		$("#tab-orderCheckList").insertResult(res,0);
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
		var selectedIndex = $("#tab-orderCheckList").getSelectedIndex();
		$("#tab-orderCheckList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function afterDicItemClick(id, $row, selIndex) 
{
	if(id.indexOf("dia-orgCode") == 0)
	{  
		$("#dia-orgName").val($("#"+id).val());
		return true;
	}
	
	if(id.indexOf("dia-userAccount") == 0)
	{    
	   	$("#dia-userName").val($("#"+id).val());
	   	return true;
	}
	
	return true;
}
</script>