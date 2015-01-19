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
		<form method="post" id="fm-extendWarrantyInfo" class="editForm" >
			<!-- 隐藏域 -->
		    <input type="hidden" id="dia-warrantyId" name="dia-warrantyId" datasource="WARRANTY_ID" />
		    <input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		    <input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-extendWarrantyInfo">
				<tr>
					<td><label>延保代码：</label></td>
					<td><input type="text" id="dia-warrantyCode" name="dia-warrantyCode" datasource="WARRANTY_CODE" datatype="0,is_digit_letter,30" /></td>
					<td><label>延保名称：</label></td>
					<td><input type="text" id="dia-warrantyName" name="dia-warrantyName" datasource="WARRANTY_NAME" datatype="0,is_null,30" /></td>
			    </tr>
				<tr>
					<td><label>有效标示：</label></td>
					<td>
		        		<select id="dia-status"  name="dia-status" datasource="STATUS" kind="dic" src="YXBS" datatype="0,is_null,6" >
				    		<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    	</select>
		        	</td>
				</tr>		   
                <tr>
					<td><label>延保说明：</label></td>
					<td colspan="3">
						<textarea id="dia-remarks" style="width:89%;height:40px;" name="dia-remarks" datasource="REMARKS"  datatype="0,is_null,1000"></textarea>
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


var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-extendWarrantyInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-extendWarrantyInfo").combined(1) || {};
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
	
	//绑定重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#fm-extendWarrantyInfo")[0].reset();
	}); 
	
	//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-extendWarrantyList").getSelectedRows();
		setEditValue("fm-extendWarrantyInfo",selectedRows[0].attr("rowdata"));
		//设置置灰值
		$("#dia-warrantyCode").attr("readonly",true);
	}else
	{}
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-extendWarrantyList").insertResult(res,0);
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
		var selectedIndex = $("#tab-extendWarrantyList").getSelectedIndex();
		$("#tab-extendWarrantyList").updateResult(res,selectedIndex);
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