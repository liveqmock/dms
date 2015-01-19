<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String contextPath = request.getContextPath();
%>
<div id="dia-editField">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="dia-fm-editField" class="editForm" >
		<div align="left">
			<fieldset style="float:left;background:#f8f7f4;">
			<legend align="left">&nbsp;表格属性</legend>
			<div style="display:block;margin-top:20px;background:#f8f7f4; margin-left:1px;overflow:hidden; width:400px; height:250px; border:solid 0px #CCC; line-height:21px;">
				<table class="editTable" id="dia-tab-editField" >
					<tr>
						<td><label>显示名称：</label></td>
	   					<td colspan="3"><input type="text" class="middleInput" style="width:95%;" id="dia-in-showName" name="dia-in-showName" datasource="SHOW_NAME" datatype="0,is_null,20" value="" /></td>
					</tr>
					<tr>
						<td><label>字段定义：</label></td>
					    <td colspan="3"><input type="text" class="middleInput" style="width:95%;" id="dia-in-fieldName" name="dia-in-fieldName" datasource="FIELD_NAME" kind="dic" dicWidth="420"  datatype="1,is_null,60" value="" /></td>
					</tr>  
					<tr>
					    <td><label>行列转换：</label></td>
					    <td >
					    	<input type="radio" name="rcRadios" checked="true" value="1"/>&nbsp;&nbsp;行&nbsp;&nbsp;
					    	<input type="radio" name="rcRadios" value="2"/>&nbsp;&nbsp;列
					    </td>
					</tr>
					<tr>
					    <td><label>字段类型：</label></td>
					    <td>
					    	<select type="text" class="combox" id="dia-in-fieldType"  name="dia-in-fieldType" kind="dic" src="E#0=字符:1=数字" datasource="FIELD_TYPE"  datatype="1,is_null,60">
					    		<option value="0" selected>字符</option>
					    	</select>
					    </td>
					    <td><label>是否字典：</label></td>
					    <td>
					    	<div>
					    	<input type="radio" name="dicRadios" checked="true" value="1"/>&nbsp;&nbsp;否&nbsp;&nbsp;
					    	<input type="radio" name="dicRadios" value="2"/>&nbsp;&nbsp;是
					    	<input type="text" id="dia-in-dicField" name="dia-in-dicField" kind="dic" style="float:right;margin-right:7px;width:130px;display:none;"/>
					    	</div>
					    </td>
					</tr>  
					<tr id="dia-tr03">
						<td><label>对齐方式：</label></td>
					    <td>
					    	<select type="text" class="combox" id="dia-in-fieldAlign"  name="dia-in-fieldAlign" kind="dic" src="E#1=居左:2=居中:3=居右" datasource="FIELD_ALIGN"  datatype="1,is_null,60">
					    		<option value="1" selected>居左</option>
					    	</select>
					    </td>
					    <td colspan="2" id="dia-tr03-td03"><ul>
					    		<li style="float:left;padding-top:5px;padding-left:4px;">宽：</li>
					    		<li style="float:left;"><div><input type="text" style="width:30px;" id="dia-in-fieldWidth"  name="dia-in-fieldWidth" datasource="FIELD_WIDTH" datatype="1,is_digit,60" value="0"/><span>%</span></div></li>
					    		<li style="float:left;padding-top:5px;padding-left:10px;">高：</li>
					    		<li style="float:left;"><div><input type="text" style="width:30px;" id="dia-in-fieldHeight" name="dia-in-fieldHeight" datasource="FIELD_HEIGHT" datatype="1,is_digit,20" value="0"/><span>px</span></div></li>
					    	</ul>
	   					</td>
					</tr>
					<tr id="dia-tr04">
						<td><label>背景颜色：</label></td>
						<td colspan="3">
							<input id="dia-in-bgColorSel" type="hidden" />
							<input id="dia-in-bgColor" name="dia-in-bgColor" type="text" style="width:60px;margin-right:10px;" value="#ffffff" datatype="1,is_null,10" datasource="FIELD_BGCOLOR" />
						</td>
					</tr>
					<tr id="dia-tr05">
					    <td ><label>备注样式：</label></td>
					    <td colspan="3">
					    	<textarea style="width:95%;height:30px;overflow:auto;" id="dia-in-fieldFunc"  name="dia-in-fieldFunc" datasource="FIELD_FUNC" datatype="1,is_null,60" value="" readonly="true" hasBtn="true"></textarea>
					    </td>
					</tr>
				</table>
				</div>
			</fieldset>
			<fieldset style="float:left;background:#f8f7f4;">
			<legend align="left">&nbsp;字体属性</legend>
			<div style="display:block;margin-top:20px;background:#f8f7f4; margin-left:1px;overflow:hidden; width:300px; height:250px; border:solid 0px #CCC; line-height:21px;">
				<table class="editTable" id="dia-tab-editField" >
					<tr>
						<td><label>字体字号：</label></td>
	   					<td >
	   						<select type="text" class="combox" id="dia-in-fontSize" name="dia-in-fontSize" kind="dic" src="E#9=9.0:10=10.0:11=11.0:12=12.0:13=13.0:14=14.0:16=16.0:18=18.0"  datasource="FIELD_FONTSIZE" datatype="1,is_null,20" >
	   							<option value="9" selected>9.0</option>
	   						</select>pt
	   					</td>
	   				</tr>
	   				<tr>	
						<td><label>字体颜色：</label></td>
						<td colspan="3">
							<input id="dia-in-fontColorSel" type="hidden" />
							<input id="dia-in-fontColor" name="dia-in-fontColor" type="text" style="width:60px;margin-right:10px;" value="#000000" />
						</td>
					</tr>
					<tr>
						<td><label>粗体显示：</label></td>
	   					<td colspan="3">
	   						<input type="radio" id="dia-radios-normal" name="dia-radios-bold" datasource="SHOW_NAME" datatype="1,is_null,20" value="" checked="true"></input>&nbsp;&nbsp;常规&nbsp;&nbsp;&nbsp;&nbsp;
	   						<input type="radio" id="dia-radios-bold" name="dia-radios-bold" datasource="SHOW_NAME" datatype="1,is_null,20" value="" ></input>&nbsp;&nbsp;粗体
	   					</td>
					</tr>
					<tr>
						<td><label>显示边距：</label></td>
	   					<td colspan="3" >
	   						<ul style="width:120px;">
					    		<li style="float:left;padding-top:5px;">上</li>
					    		<li style="float:left;"><div><input type="text" style="width:20px;" id="dia-in-fieldPTop"  name="dia-in-fieldPTop" datasource="FIELD_PTOP" datatype="1,is_digit,60" value="0"/><span>px</span></div></li>
					    		<li style="float:left;padding-top:5px;padding-left:10px;">右</li>
					    		<li style="float:left;"><div><input type="text" style="width:20px;" id="dia-in-fieldPRight" name="dia-in-fieldPRight" datasource="FIELD_PRIGHT" datatype="1,is_digit,20" value="0"/><span>px</span></div></li>
					    		<li style="float:left;padding-top:16px;padding-left:0px;">下</li>
					    		<li style="float:left;padding-top:10px;"><div><input type="text" style="width:20px;" id="dia-in-fieldPButtom" name="dia-in-fieldPButtom" datasource="FIELD_PBOTTOM" datatype="1,is_digit,20" value="0"/><span>px</span></div></li>
					    		<li style="float:left;padding-top:16px;padding-left:10px;">左</li>
					    		<li style="float:left;padding-top:10px;"><div><input type="text" style="width:20px;" id="dia-in-fieldPLeft" name="dia-in-fieldPLeft" datasource="FIELD_PLEFT" datatype="1,is_digit,20" value="0"/><span>px</span></div></li>
					    	</ul>
					    </td>	
					</tr>
				</table>
				</div>
			</fieldset>		
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-btn-save">确&nbsp;&nbsp;定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
var diaDialog = $("body").data("dia-editField");
$(function()
{
	$.editfield.init();
});

</script>