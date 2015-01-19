<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
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
	<form method="post" id="fm-extendWarrantyPart" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-relationId" name="dia-relationId" datasource="RELATION_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		<input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-extendWarrantyPartInfo">
				<tr>
					<td><label>延保代码：</label></td>
				    <td>
				    	<input type="text" id="dia-warrantyCode"  name="dia-warrantyCode" datasource="WARRANTY_CODE" datatype="1,is_null,30" />
				    	<input type="hidden" id="dia-warrantyId"  name="dia-warrantyId" datasource="WARRANTY_ID" />
				    </td>
				    <td><label>延保名称：</label></td>
				    <td>
				    	<input type="text" id="dia-warrantyName"  name="dia-warrantyName" datasource="WARRANTY_NAME" datatype="1,is_null,30" />
				    </td>
				</tr>
				<tr>
					<td><label>配件代码：</label></td>
				    <td>
				    	<input type="text" id="dia-partCode"  name="dia-partCode" datasource="PART_CODE" datatype="1,is_null,300" />
				    	<input type="hidden" id="dia-partId"  name="dia-partId" datasource="PART_ID" />
				    </td>
				    <td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="dia-partName"  name="dia-partName" datasource="PART_NAME" datatype="1,is_null,400" />
				    </td>
				</tr>
				<tr>
					<td><label>延保月份：</label></td>
					<td><input type="text" id="dia-warrantyMonth"  name="dia-warrantyMonth" onblur="doInputStBlur1(this)" datasource="WARRANTY_MONTH" datatype="0,is_null,30" /></td>
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

var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyPartMngAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction != 1)	//新增初始化
	{
		//修改初始化
		var selectedRows = $("#tab-extendWarrantyPartList").getSelectedRows();
		setEditValue("fm-extendWarrantyPart",selectedRows[0].attr("rowdata"));
				
		$("#dia-warrantyCode").attr("readonly",true);
		$("#dia-warrantyName").attr("readonly",true);
		$("#dia-partCode").attr("readonly",true);
		$("#dia-partName").attr("readonly",true);
	}
	
})

$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-extendWarrantyPart");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-extendWarrantyPart").combined(1) || {};
		
		var updateUrl = diaSaveAction + "/update.ajax";
		doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);		
	});
});

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
		//var selectedIndex = $("#tab-extendWarrantyPartList").getSelectedIndex();
		//$("#tab-extendWarrantyPartList").updateResult(res,selectedIndex);
		
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
		
		//清空val的内容
		$("#val0").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function doInputStBlur1(obj){
	var $obj = $(obj);
    //if($obj.val() == "")//为空直接返回
    //    return ;	
    if($obj.val() && !isNum($obj))//
    {
        alertMsg.warn("请输入正确的数量！");
        //$obj.val("");
        return;
    }
}

function isNum($obj)
{
	var reg = /^[1-9][0-9]*$/;//正则表达式(数字)
    if(reg.test($obj.val()))
    {
        return true;
    }else
    {
        return false;
    }
}
</script>