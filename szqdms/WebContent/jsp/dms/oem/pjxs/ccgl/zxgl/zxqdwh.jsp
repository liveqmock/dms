<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
			<form method="post" id="">
				<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>订单编号：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_digit_letter,30" value="YD20140519001" /></td>
					    <td><label>订单类型：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_digit_letter,30" value="月度订单" /></td>
					    <td><label>提报单位：</label></td>
					    <td><input type="text" id="in-ddbh" name="in-ddbh" readonly datasource="DDBH" datatype="1,is_digit_letter,30" value="河南配送中心" /></td>
					</tr>
				</table>
				</div>
			</form>
		</div>
		<div class="pageContent" >
			<div id="dia-fldmxxx" style="height:360px;overflow:auto;">
				<table style="width:100%;" id="dia-fldmxlb" name="tablist"  ref="dia-fldmxxx" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" style="display:"></th>
						<th type="multi" name="CX-XH" style="" ></th>
						<th fieldname="PJDM">发料单号</th>
						<th fieldname="PJDM">配件图号</th>
						<th fieldname="PJMC">配件名称</th>
						<th fieldname="JLDWMC">单位</th>
						<th fieldname="JLDWMC" colwidth="80">是否指定供应商</th>
						<th fieldname="JLDWMC">供应商</th>
						<th fieldname="JLDWMC">应发数量</th>
						<th fieldname="JLDWMC">实发数量</th>
						<th fieldname="JLDWMC">箱号</th>
						<th fieldname="JLDWMC">数量</th>
						<th fieldname="KW" freadonly="true" >备注</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="rownums"><div>1</div></td>
						<td><div><input type="checkbox"/></div></td>
						<td><div>YD20140519001001</div></td>
						<td><div>PJ001</div></td>
						<td><div>配件名称1</div></td>
						<td><div>件</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div>15</div></td>
						<td><div><input type="text" style="width:100px;" value="XH01,XH02"/></div></td>
						<td><div><input type="text" style="width:50px;"/></div></td>
						<td><div></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>2</div></td>
						<td><div><input type="checkbox"/></div></td>
						<td><div>YD20140519001001</div></td>
						<td><div>PJ002</div></td>
						<td><div>配件名称2</div></td>
						<td><div>件</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div>15</div></td>
						<td><div><input type="text" style="width:100px;" value="XH03"/></div></td>
						<td><div><input type="text" style="width:50px;"/></div></td>
						<td><div></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>3</div></td>
						<td><div><input type="checkbox"/></div></td>
						<td><div>YD20140519001001</div></td>
						<td><div>PJ003</div></td>
						<td><div>配件名称3</div></td>
						<td><div>件</div></td>
						<td><div>是</div></td>
						<td><div>供应商一</div></td>
						<td><div>20</div></td>
						<td><div>15</div></td>
						<td><div><input type="text" style="width:100px;" value="XH03"/></div></td>
						<td><div><input type="text" style="width:50px;"/></div></td>
						<td><div></div></td>
					</tr>
				</tbody>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button id="dia-save" type="button">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button id="dia-download" type="button">导出清单</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button id="dia-upload" type="button">导入装箱记录</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button id="dia-submit" type="button">装箱完成</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
//弹出窗体
$(function()
{
	$("#dia-fldmxlb").show();
	$("#dia-fldmxlb").jTable();
	$("#dia-close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-save").click(function(){
		alertMsg.confirm("确认保存?",{okCall:doSave,cancelCall:doSaveCell});
		return false;
	});
	$("#dia-download").click(function(){
		alertMsg.info("下载清单excel.");
		return false;
	});
	$("#dia-upload").click(function(){
		alertMsg.info("导入装箱清单excel.");
		return false;
	});
	$("#dia-submit").click(function(){
		alertMsg.confirm("确认该订单装箱已完成?",{okCall:doSave,cancelCall:doSaveCell});
		return false;
	});
});
function doSave(){
	alertMsg.info("操作成功.");
}
function doSaveCell(){
	
}
</script>