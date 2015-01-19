<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_jjckxx" style="width:100%;">
	<div class="page">
	<div class="pageContent">
		<form method="post" id="di-jjxhmx" class="editForm" >
			<div align="left">
				<fieldset>
				<table class="editTable" id="jjxhmxTable">
					<tr>
					    <td><label>供应商代码：</label></td>
						<td><input type="text" id="di_gysdm" name="di_gysdm" value="供应商代码1" readonly="readonly"/></td>
						<td><label>供应商名称：</label></td>
						<td><input type="text" id="di_gysmc" name="di_gysmc" value="供应商名称1" readonly="readonly"/></td>
				 	</tr>
					<tr>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="di_pjdm" name="di_pjdm" value="配件代码1" readonly="readonly"/></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="di_pjmc" name="di_pjmc" value="配件名称1" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>当前库存数量：</label></td>
						<td><input type="text" id="DQKCSL" name="DQKCSL" value="7" readonly="readonly"/></td>
						<td><label>出库数量：</label></td>
						<td><input type="text" id="DI_CKSL" name="DI_CKSL" value="7" datatype="0,is_digit,100" /></td>
					</tr>
					<tr>
						<td><label>出库类型：</label></td>
						<td><select type="text" id="DI_CKLX" name="DI_CKLX" class="combox" kind="dic" src="E#1=旧件销毁:2=其他出库" >
								<option value=2>其他出库</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>备注：</label></td>
					    <td colspan="3"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
					</tr>
				</table>
				</fieldset>
			</div>
		</form>
		<div class="formBar" >
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
var dialog = $("body").data("jjkcmx");
$(function()
{
	$("#di_jjckmxlb").jTable();
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
function doSave(){
	var kcsl=$("#DQKCSL").val();
	var cksl=$("#DI_CKSL").val();
	if(cksl - kcsl > 0){
		alertMsg.info("出库数量不能大于当前库存数量");
		return false;
	}
	alertMsg.info("保存成功");
}

</script>