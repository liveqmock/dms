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
	<form method="post" id="fm-directType" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-typeId" name="dia-typeId" datasource="TYPE_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<input type="hidden" id="dia-status" name="dia-status" datasource="STATUS" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-directTypeInfo">
				<tr>
					<td><label>类型代码：</label></td>
				    <td>
				    	<input type="text" id="dia-typeCode"  name="dia-typeCode"  datasource="TYPE_CODE" datatype="0,is_null,30" />
				    </td>
					<td><label>类型名称：</label></td>
				    <td>
				    	<input type="text" id="dia-typeName"  name="dia-typeName" datasource="TYPE_NAME" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
					<td><label>有效标识：</label></td>
				    <td colspan="3">
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" >
					    	<option value="-1" >--</option>
					    </select>
					    <input type="hidden" id="dia-oldStatus"  name="dia-oldStatus" datasource="OLD_STATUS"  />
				    </td>
				</tr>
				<tr>
					<td><label>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label></td>
					<td colspan="3"><textarea id="dia-remarks" style="width:89%;height:40px;" name="dia-remarks" datasource="REMARKS"  datatype="1,is_null,1000"></textarea></td>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/DirectTypeAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 
		//默认显示有效标识
		$("#dia-status").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").attr("dic_code","<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");

	} else {  //修改初始化
		var selectedRows = $("#tab-directTypeList").getSelectedRows();
		setEditValue("fm-directType",selectedRows[0].attr("rowdata"));
		
		$("#dia-typeCode").attr("readonly", true);
		$("#dia-oldStatus").val($("#dia-status").val());
	}
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-directType");
		if (submitForm($f) == false) return false;
		
		//类型代码必须为两位大写字母
		var pattern = /^[A-Z]{2}$/; 
		var typeCode = $("#dia-typeCode").val();
		if (!pattern.test(typeCode)) {
			alert("类型代码必须为两位大写字符！");
			return false;
		}
		
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-directType").combined(1) || {};
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
		$("#tab-directTypeList").insertResult(res,0);
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
		var selectedIndex = $("#tab-directTypeList").getSelectedIndex();
		$("#tab-directTypeList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>