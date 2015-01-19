<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%> 
<%
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="qtfyxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-tb-option">
			 <input type="hidden" id="dia-COST_ID" name="dia-COST_ID" datasource="COST_ID" />
			    <tr>
					<td><label>项目代码：</label></td>
					<td><input type="text" id="dia-COSTS_CODE" name="dia-COSTS_CODE" datasource="COSTS_CODE" datatype="1,is_null,100" value="" /></td>
				</tr>	
				<tr>
					<td><label>项目名称：</label></td>
					<td><input type="text" id="dia-COSTS_NAME" name="dia-COSTS_NAME" datasource="COSTS_NAME" datatype="1,is_null,100" value="" /></td>
				</tr>
				<tr> <td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >	
				     <option value="-1" selected>--</option>				 
				    </select>
			    	</td>
			    </tr>
				<tr>
					<td colspan="2"><font color="red">*注:只维护项目，费用是在索赔单中填写的</font></td>
				</tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button"  id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/OtherCostMngAction";
var diaAction = "<%=action%>";
$(function(){

	$("#btn-save").bind("click", function(event){

			//获取需要提交的form对象
		var $f = $("#dia-fm-option");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-option").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{
			var updateUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaInsertCallBack);

		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
		
	});

	  //修改操作
	if(diaAction != "1")
	{
	   var selectedRows = $("#tab-list").getSelectedRows();	   
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));	 
	
	 }else
	{
		
		setDiaDefaultValue();
	}
});

function setDiaDefaultValue()
{


	//有效标示
	$("#dia-STATUS").val("<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").attr("code","<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").find("option").val("<%=DicConstant.YXBS_01%>");
	$("#dia-STATUS").find("option").text("有效");
	$("#dia-STATUS").attr("readonly",true);
	//$("#dia-STATUS1").attr("disabled",true);

}

//新增回调函数
function diaInsertCallBack(res)
{
	 
	try
	{
	$("#tab-list").insertResult(res,0);	
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{		
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>