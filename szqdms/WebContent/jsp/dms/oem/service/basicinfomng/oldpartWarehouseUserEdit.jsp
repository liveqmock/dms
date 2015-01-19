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
		<form method="post" id="fm-oldpartWarehouseAreaInfo" class="editForm" >
			<!-- 隐藏域 -->
		    <input type="hidden" id="dia-RELATION_ID" name="dia-RELATION_ID" datasource="RELATION_ID" />
		    <input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		    <input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-oldpartWarehouseAreaInfo">
			    <tr>
			    	<td><label>选择库管：</label></td>
					<td><input type="text" class="combox" id="dia-PERSON_NAME" name="dia-PERSON_NAME" datasource="PERSON_NAME" operation="like" kind="dic" src="T#TM_USER T,TR_ROLE_USER_MAP M,TM_ROLE R:T.PERSON_NAME:T.ACCOUNT{T.USER_ID}:T.USER_ID = M.USER_ID AND M.ROLE_ID = R.ROLE_ID AND R.CODE = 'F_JJKGJS'" datatype="0,is_null,30" /> 
						<input type="hidden" id="dia-ACCOUNT" name="dia-ACCOUNT" datasource="T.ACCOUNT"/>
						<input type="hidden" id="dia-USER_ID" name="dia-USER_ID" datasource="T.USER_ID"/>
					</td>
			    	<td><label>旧件仓库：</label></td>
					<td><input type="text" class="combox" id="dia-warehouseCode" name="dia-warehouseCode" datasource="WAREHOUSE_CODE" operation="like" kind="dic" src="T#SE_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID}:STATUS=100201" datatype="0,is_null,30" /> 
						<input type="hidden" id="dia-warehouseName" name="dia-warehouseName" datasource="WAREHOUSE_NAME"/>
						<input type="hidden" id="dia-warehouseId" name="dia-warehouseId" datasource="WAREHOUSE_ID"/>
					</td>
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

var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/OldpartWarehouseAreaMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-oldpartWarehouseAreaInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-oldpartWarehouseAreaInfo").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insertUser.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/updateUser.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
	
	//绑定重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#fm-oldpartWarehouseAreaInfo")[0].reset();
	}); 
	
	//修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-oldpartWarehouseArealist").getSelectedRows();
		//setEditValue：设置输入框赋值方法
		setEditValue("fm-oldpartWarehouseAreaInfo",selectedRows[0].attr("rowdata"));
		//设置置灰值
		$("#dia-areaCode").attr("readonly",true);
		
		//将字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		//把旧件仓库的名称赋给旧件仓库代码表示项		
		var warehouseCode=getNodeValue(objXML, "WAREHOUSE_CODE", 0);
		var warehouseName=getNodeValue(objXML, "WAREHOUSE_NAME", 0);
		$("#dia-warehouseCode").val(warehouseName);
		$("#dia-warehouseCode").attr("code",warehouseCode);	
	}else
	{}
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#btn-search").trigger("click");
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
		var selectedIndex = $("#tab-oldpartWarehouseArealist").getSelectedIndex();
		$("#tab-oldpartWarehouseArealist").updateResult(res,selectedIndex);
		//关闭当前窗口
		$.pdialog.closeCurrent();
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
	//给旧件仓库隐藏域赋值
	if(id=="dia-warehouseCode") 
	{
		$("#dia-warehouseName").val($("#"+id).val());
		$("#dia-warehouseId").val($row.attr("WAREHOUSE_ID"));
	}
	if(id=="dia-PERSON_NAME") 
	{
		$("#dia-ACCOUNT").val($("#"+id).val());
		$("#dia-USER_ID").val($row.attr("T.USER_ID"));
	}
	return true;
}

</script>