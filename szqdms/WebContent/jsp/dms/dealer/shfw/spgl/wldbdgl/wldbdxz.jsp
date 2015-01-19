<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="wldbxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-wldbxz" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="wldbxz">
					<tr>
						<td><label>VIN：</label></td>
						<td><input type="text" id="dia-vin" name="dia-vin"  datatype="0,is_digit_letter,17" />(请输入后8位或者17位)</td>
						<td><label>发动机号：</label></td>
						<td><input type="text" id="dia-engineno" name="dia-engineno"  datatype="0,is_digit_letter,30" /></td>
						<td colspan="2"><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin" onclick="checkVin()">验&nbsp;&nbsp;证</button></div></div></td>
					</tr>
					<tr>
						<td><label>车辆型号：</label></td>
						<td><input type="text" id="dia-clxh" name="dia-clxh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
						<td><label>合格证号：</label></td>
						<td><input type="text" id="dia-hgzh" name="dia-hgzh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
						<td><label>发动机型号：</label></td>
						<td><input type="text" id="dia-fdjxh" name="dia-fdjxh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
					</tr>
					<tr>
						<td><label>用户类型：</label></td>
						<td>
							<select type="text" id="dia-yhlx" name="dia-yhlx" kind="dic" src="E#1=民车:2=军车" datatype="0,is_null,30" >
								<option value="1" selected>民车</option>
							</select>
						</td>
						<td><label>车辆用途：</label></td>
						<td>
							<select type="text" id="dia-clyt" name="dia-clyt" kind="dic" src="E#1=非公路用车:2=公路用车" datatype="0,is_null,30" >
								<option value="1" selected>非公路用车</option>
							</select>
						</td>
						<td><label>驱动形式：</label></td>
						<td><input type="text" id="dia-qdxs" name="dia-qdxs" value="校验车辆信息后自动带出" readonly="readonly" /></td>
					</tr>
					<tr >
						<td><label>购车日期：</label></td>
						<td><input type="text" id="dia-gcrq" name="dia-gcrq" value="2013-01-01" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
						<td><label>行驶里程：</label></td>
						<td><input type="text" id="dia-shlc" name="dia-shlc" value="3100" datatype="1,is_null,30" /></td>
						<td><label>保修卡号：</label></td>
						<td><input type="text" id="dia-bxkh" name="dia-bxkh" value="保修卡号1" datatype="1,is_null,30" /> </td>
					</tr>
					<tr>
						<td><label>车牌号码：</label></td>
						<td><input type="text" id="dia-cphm" name="dia-cphm" value="车牌号01" datatype="0,is_null,30" /></td>
						<td><label>客户名称：</label></td>
						<td><input type="text" id="dia-yhmc" name="dia-yhmc" value="张三" datatype="0,is_null,30" /></td>
						<td><label>身份证号：</label></td>
						<td><input type="text" id="dia-sfzh" name="dia-sfzh" value="010382188205235871" datatype="0,is_number,30" /></td>

					</tr>
					<tr>
						<td><label>联系人：</label></td>
						<td><input type="text" id="dia-lxr" name="dia-lxr" value="李四" datatype="0,is_null,30" /></td>
						<td><label>联系电话：</label></td>
						<td ><input type="text" id="dia-lxdh" name="dia-lxdh" value="13504312528" datatype="0,is_number,30" /></td>
						<td><label>定保次数：</label></td>
						<td ><select type="text" id="dia-dbcs" name="dia-dbcs" kind="dic" src="E#1=1次:2=2次:3=3次" datatype="0,is_null,30" value="">
								<option value="1" selected>1次</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>用户地址：</label></td>
						<td colspan="5"><textarea id="dia-dz" style="width: 450px; height: 30px;" name="dia-dz" datatype="1,is_null,100">地址</textarea></td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
						<td colspan="5"><textarea id="dia-bz" style="width: 450px; height: 50px;" name="dia-bz" datatype="1,is_null,100"></textarea></td>
					</tr>
				</table>
				</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doReport()" id="report">提&nbsp;&nbsp;报</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">
var action=<%=action%>;
$(function(){
	/* if(action != "1"){
		
	}else{
		$("#repBtn").hide();
	} */
});
function doSave(){
	alertMsg.info("保存成功");
	$("#repBtn").show();
}
function doReport(){
	alertMsg.info("提报成功");
}
//弹出窗体
var dialog = $("body").data("wldbdxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>