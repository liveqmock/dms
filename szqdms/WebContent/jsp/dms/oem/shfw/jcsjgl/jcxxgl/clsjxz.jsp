<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<div class="pageContent">  
				<div id="dia-shgj">
						<table width="100%" id="dia-spclxx" name="dia-spclxx" style="display: none" class="editTable" >
			                 <tr>
								<td><label>VIN：</label></td>
								<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="vin1" datasource="VIN" datatype="0,is_digit_letter,17" operation="like" />(请输入后8位或者17位)</td>
								<td><label>发动机号：</label></td>
								<td ><input type="text" id="dia-in-engineno" name="dia-in-engineno" value="发动机号1" datasource="ENGINE_NO" datatype="0,is_digit_letter,30" operation="like" /></td>
								<td><label>车辆状态：</label></td>
								<td>
									<select type="text" id="dia-in-clzt" name="dia-in-clzt" kind="dic" src="E#1=有效:2=无效" datatype="0,is_null,30" value="">
										<option value="1" selected>有效</option>
									</select>
								</td>
							</tr>
							<tr>
								<td><label>车辆型号：</label></td>
								<td><input type="text" id="dia-in-clxh" name="dia-in-clxh" value="车辆型号1" datatype="0,is_digit_letter,30" /></td>
								<td><label>合格证号：</label></td>
								<td><input type="text" id="dia-in-hgzh" name="dia-in-hgzh" value="合格证号1" datatype="0,is_digit_letter,30"  /></td>
								<td><label>发动机型号：</label></td>
								<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="发动机型号1" datatype="0,is_digit_letter,30"  /></td>
							</tr>
							<tr>
								<td><label>用户类型：</label></td>
								<td>
									<select type="text" id="dia-in-yhlx" name="dia-in-yhlx" kind="dic" src="E#1=民车:2=军车" datatype="0,is_null,30" value="">
										<option value="1" selected>民车</option>
									</select>
								</td>
								<td><label>车辆用途：</label></td>
								<td>
									<select type="text" id="dia-in-clyt" name="dia-in-clyt" kind="dic" src="E#1=非公路用车:2=公路用车" datatype="0,is_null,30" value="" >
										<option value="1" selected>非公路用车</option>
									</select>
								</td>
								<td><label>驱动形式：</label></td>
								<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="4*2" kind="dic" src="E#1=4*2:2=6*2:3=6*4" datatype="0,is_null,30" /></td>
							</tr>
							<tr>
								<td><label>购车日期：</label></td>
								<td><input type="text" id="dia-in-clxsrq" name="dia-in-clxsrq" value="2013-01-01" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
								<td><label>行驶里程：</label></td>
								<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="3100" datatype="1,is_number,30" /></td>
								<td><label>保修卡号：</label></td>
								<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="保修卡号1" datatype="1,is_number,30" /> </td>
							</tr>
							<tr >
								<td><label>出厂日期：</label></td>
								<td><input type="text" id="dia-in-ccrq" name="dia-in-ccrq" value="2013-01-01" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
								<td><label>首保日期：</label></td>
								<td><input type="text" id="dia-in-sbrq" name="dia-in-xslc" value="2013-04-01" datatype="1,is_number,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
								<td><label>车辆销售状态：</label></td>
								<td>
									<select type="text" id="dia-in-yhlx" name="dia-in-yhlx" kind="dic" src="E#1=未销售:2=已销售" datatype="1,is_null,30" value="">
										<option value="-1" selected>--</option>
									</select>
								</td>
							</tr>
							<tr>
								<td><label>车牌号码：</label></td>
								<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="车牌号01" datatype="1,is_number,30" /></td>
								<td><label>用户名称：</label></td>
								<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="张三" datatype="1,is_number,30" /></td>
								<td><label>身份证号：</label></td>
								<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" datatype="1,is_number,30" /></td>

							</tr>
							<tr>
								<td><label>联系人：</label></td>
								<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="李四" datatype="1,is_number,30" /></td>
								<td><label>电话：</label></td>
								<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" datatype="1,is_number,30" /></td>
								<td><label>用户地址：</label></td>
								<td><textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" datatype="1,is_null,100">地址</textarea></td>
							</tr>
						</table>
				</div>      	
			</div>
			    <div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaSave();">保&nbsp;&nbsp;存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-spclxx").show();
});
function doDiaSave()
{
	alertMsg.info("修改成功");
	$.pdialog.closeCurrent();
}
</script>
</div>

