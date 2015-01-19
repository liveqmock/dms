<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-pjdawh" class="editForm" >
			<div align="left">
				<fieldset>
				<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;配件档案信息编辑&gt;&gt;</a></legend>
				<table class="editTable" id="dia-tab-pjxx">
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-in-pjdm"  name="dia-in-pjdm" datasource="PJDM" datatype="0,is_digit_letter,30" value=""/></td>
					     <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-in-pjmc"  name="dia-in-pjmc" datasource="PJMC" datatype="0,is_null,30" value=""/></td>
					</tr>
					<tr>
					    <td><label>参图号：</label></td>
					    <td><input type="text" id="dia-in-cth"  name="dia-in-cth" datasource="CTH" datatype="1,is_digit_letter,30" value=""/></td>
					    <td><label>计量单位：</label></td>
					    <td><input type="text" id="dia-in-jldw"  name="dia-in-jldw" kind="dic" src="E#1=件:2=包:3=桶" datasource="JLDW" datatype="0,is_null,30" value=""/></td>
					</tr>
					<tr>
					    <td><label>配件类型：</label></td>
					    <td><input type="text" id="dia-in-pjlx"  name="dia-in-pjlx" kind="dic" src="E#1=类型一:2=类型二" datasource="PJLX" datatype="0,is_null,30" value=""/></td>
					    <td><label>配件属性：</label></td>
					    <td><input type="text" id="dia-in-pjsx"  name="dia-in-pjsx" datasource="PJSX" kind="dic" src="E#1=易损件:2=常用件:3=非常用件"  datatype="0,is_null,30" value=""/></td>
					</tr>
					<tr>
					    <td><label>最小包装数：</label></td>
					    <td><input type="text" id="dia-in-zxbzs"  name="dia-in-zxbzs" datasource="ZXBZS" datatype="0,is_digit,30" value=""/></td>
					    <td><label>最小包装单位：</label></td>
					    <td><input type="text" id="dia-in-zxbzdw"  name="dia-in-zxbzdw" kind="dic" src="E#1=件:2=包:3=桶" datasource="ZXBZDW" datatype="0,is_null,30" value=""/></td>
					</tr>
					<tr>
					    <td><label>是否直发：</label></td>
					    <td>
					    	<select type="text" id="dia-in-sfzf"  name="dia-in-sfzf" kind="dic" src="SF" datasource="SFZF"  datatype="0,is_null,30" >
					    		<option value="1" selected>否</option>
					    	</select>
					    </td>
						<td><label>是否保外：</label></td>
					    <td>
					    	<select type="text" id="dia-in-sfbw" name="dia-in-sfbw" kind="dic" src="SF" datasource="SFBW" datatype="0,is_null,30" >
					    		<option value="1" selected>否</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><label>是否可订：</label></td>
					    <td>
					    	<select type="text" id="dia-in-sfkd"  name="dia-in-sfkd" kind="dic" src="SF" datasource="SFKD"  datatype="0,is_null,30" >
					    		<option value="1" selected>是</option>
					    	</select>
					    </td>
						<td><label>是否回运：</label></td>
					    <td>
					    	<select type="text" id="dia-in-sfhy"  name="dia-in-sfhy" kind="dic" src="SF" datasource="SFHY"  datatype="0,is_null,30" >
					    		<option value="1" selected>是</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>是否大总成：</label></td>
						<td>
					    	<select type="text" id="dia-in-sfdzc"  name="dia-in-sfdzc" kind="dic" src="SF" datasource="SFDZC"  datatype="0,is_null,30" >
					    		<option value="0" selected>否</option>
					    	</select>
					    </td>
					    <td><label>所属总成：</label></td>
						<td>
					    	<select type="text" id="dia-in-sszc"  name="dia-in-sszc" kind="dic" src="E#1=发动机总称:2=桥总成" datasource="SSZC"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    
					</tr>
					<tr>
						<td><label>是否扫码件：</label></td>
						<td>
					    	<select type="text" id="dia-in-sfsmj"  name="dia-in-sfsmj" kind="dic" src="SF" datasource="SFSMJ"  datatype="0,is_null,30" >
					    		<option value="0" selected>否</option>
					    	</select>
					    </td>
						<td><label>是否指定供应商：</label></td>
						<td>
					    	<select type="text" id="dia-in-sfzdgys"  name="dia-in-sfzdgys" kind="dic" src="SF" datasource="SFZDGYS"  datatype="0,is_null,30" >
					    		<option value="0" selected>否</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>配件状态：</label></td>
					    <td>
					    	<select type="text" id="dia-in-pjzt"  name="dia-in-pjzt" kind="dic" src="QYBS" datasource="PJZT"  datatype="0,is_null,30" >
					    		<option value="1" selected>启用</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
						<td colspan="3"><textarea id="dia-ta-bz" style="width:89%;height:40px;" name="dia-ta-bz" datasource="BZ"  datatype="1,is_null,100"></textarea></td>
					</tr>
				</table>
				</fieldset>	
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doDiaSave();" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>