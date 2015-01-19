<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="wcxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-wcxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="wcxz">
					<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="XZ_WFSDM" name="XZ_WFSDM" datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="openFws();"/></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="XZ_WFSMC" name="XZ_WFSMC" datatype="1,is_null,100" value="" /></td>
					</tr>	
					<tr>
						<td><label>外出次数：</label></td>
						<td><select type="text" class="combox" id="XZ_WCCS" name="XZ_WCCS" kind="dic" src="E#1=一次外出:2=二次外出" datatype="0,is_null,10" >
				        	<option value="1" selected>一次外出</option>
				        </select>
				        </td>
						<td><label>外出时间：</label></td>
						<td><select type="text" class="combox" id="XZ_WCSJ" name="XZ_WCSJ" kind="dic" src="E#1=白天:2=夜间" datatype="0,is_null,10" >
				        	<option value="1" selected>夜间</option>
				        </select>
				        </td>
				   </tr>
				   <tr>
				        <td><label>备车类型：</label></td>
						<td><select type="text" class="combox" id="XZ_ZBCLX" name="XZ_ZBCLX" kind="dic" src="E#1=自备车:2=非自备车" datatype="0,is_null,10" >
				        	<option value="1" selected>自备车</option>
				        </select>
				        </td>
				        <td><label>状态：</label></td>
						<td>
							<select type="text" class="combox" id="XZ_ZT" name="XZ_ZT" kind="dic" src="YXBS"  datatype="0,is_null,30" value="">
						    	<option value="1">有效</option>
					   		</select>
						</td>
				   </tr>
				   <tr> 
				     	<td><label>费用：</label></td>
				        <td colspan="3"><input type="text" id="XZ_FY" name="XZ_FY" datatype="0,is_double,100"/></td>
				   </tr>
				</table>
				</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	//初始化页面
	if(action != "1")
	{
		$("#XZ_FY").val("3.5");
		$("#XZ_WFSDM").val("服务商代码1");
		$("#XZ_WFSMC").val("服务商名称1");
	}
});
function openFws(){
	alertMsg.info("弹出服务商树");
}
function doSave(){
	var $f = $("#fm-wcxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("wcxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>