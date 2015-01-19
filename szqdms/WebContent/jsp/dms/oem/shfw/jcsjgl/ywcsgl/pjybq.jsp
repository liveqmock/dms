<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="pjxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-cljbxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="cljbxx">
				<tr>
					  <td><label>VIN：</label></td>
      			      <td><textarea name="textarea" id="di_vin" name="di_vin" cols="18" rows="3" readonly>VIN1</textarea></td>
				</tr>
				<tr>	
					<td><label>购车日期：</label></td>
					<td><input type="text"  value="2014-5-24" readonly/></td>
					<td><label>生厂日期：</label></td>
					<td><input type="text" value="2014-1-3" readonly/></td>
					<td><label>出厂日期：</label></td>
					<td><input type="text"   value="2014-4-30" readonly/></td>
			    </tr>
			    <tr>	
					<td><label>车辆型号：</label></td>
					<td><input type="text"  value="车型1" readonly/></td>
					<td><label>发动机编号：</label></td>
					<td><input type="text" value="发动机1" readonly/></td>
					<td><label>驱动形式：</label></td>
					<td><input type="text"  id="DI_CDTS" name="DI_CDTS" value="6*4" readonly/></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<form  method="post" id="claimform">
			<div id="pj">
			<table style="display:none" width="100%"  id="pjlb" name="pjlb" >
					<thead>
						<tr>
							<th type="multi" name="XH" style="display:"></th>
							<th>配件代码</th>
							<th>配件名称</th>
							<th>延保期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码1</td>
							<td>配件名称1</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码2</td>
							<td>配件名称2</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码3</td>
							<td>配件名称3</td>
							<td><input type="text" ></td>
						</tr>
						<tr>
							<td><input type ="checkbox" /></td>
							<td>配件代码4</td>
							<td>配件名称4</td>
							<td><input type="text" ></td>
						</tr>
					</tbody>
			</table>
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
$(function(){
	$("#pjlb").show();
	$("#pjlb").jTable();
});
function doSave(){
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("pjybq");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>