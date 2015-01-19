<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant"%>
<div class="view">
<h2 class="contentTitle">角色基本信息查看</h2>
	<form method="post" id="fm-roleInfo" class="editForm" >
		<!-- 隐藏域 -->
		<input type="hidden" id="dia-roleId" name="dia-roleId" datasource="ROLE_ID" />
		<table class="editTable" id="tab-roleInfo">
			<tr><td><label>角色代码：</label></td>
			    <td><input type="text" id="dia-code" alt="" name="dia-code" datasource="CODE" datatype="0,is_digit_letter,30"  value=""/></td>
			    <td><label>角色名称：</label></td>
			    <td><input type="text" id="dia-rname" name="dia-rname" datasource="RNAME" datatype="0,is_name,30" value=""/>
			    </td>
			</tr>
			<tr>
				<td><label>角色级别：</label></td>
			    <td>
			    	<select type="text" class="combox" id="dia-levelCode" name="dia-levelCode" kind="dic" src="JGJB" datasource="LEVEL_CODE" datatype="0,is_null,10" >
				    	<option value="-1" selected>--</option>
				    </select>
				</td>
				<td><label>所属组织：</label></td>
			    <td><input type="text" id="dia-orgId" name="dia-orgId" datasource="ORG_ID" kind="dic" src="ZZJG"  datatype="0,is_null,30" value=""/></td>
			</tr>
			<tr>
			    <td><label>有效标识：</label></td>
			    <td>
				    <select type="text" class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >
				    	<option value="<%=Constant.YXBS_01%>" selected>有效</option>
				    </select>
			    </td>
			    <td><label>角色描述：</label></td>
			    <td>
			    	<input type="text" id="dia-roleMark" name="dia-roleMark" datasource="ROLE_REMARK" datatype="0,is_null,50" value=""/>
			    </td>
			</tr>
		</table>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="openMenus(1);" id="grantMenus">查看权限</button></div></div></li>
		</ul>
	</div>
</div>	
<script type="text/javascript">
$(function()
{
	try
	{
		var selectedRows = $("#tab-rolelist").getSelectedRows();
		setEditValue("fm-roleInfo",selectedRows[0].attr("rowdata"));
		$("button.close").click(function(){
			$.pdialog.close($("body").data("viewRole"));		
			return false;
		});
	}catch(e){alert(e);}
	

});
</script>