<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
			<form method="post" id="">
			</form>
		</div>
		<div class="pageContent" >
			<div id="dia-fldmxxx" style="height:300px;overflow:auto;">
				<table style="width:100%;" id="dia-fldmxlb" name="tablist"  ref="dia-fldmxxx" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" style="display:"></th>
						<th type="multi" name="CX-XH" style="display:none;" ></th>
						<th fieldname="PJDM">发料单号</th>
						<th fieldname="PJDM">配件图号</th>
						<th fieldname="PJMC">配件名称</th>
						<th fieldname="PJMC">库位</th>
						<th fieldname="JLDWMC">单位</th>
						<th fieldname="JLDWMC" colwidth="80">是否指定供应商</th>
						<th fieldname="JLDWMC">供应商</th>
						<th fieldname="JLDWMC">应发数量</th>
						<th fieldname="JLDWMC">实发数量</th>
						<th fieldname="KW" freadonly="true" >备注</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="rownums"><div>1</div></td>
						<td><div>YD20140519001001</div></td>
						<td><div>PJ001</div></td>
						<td><div>配件名称1</div></td>
						<td><div>A01-01</div></td>
						<td><div>件</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div></div></td>
						<td><div></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>2</div></td>
						<td><div>YD20140519001001</div></td>
						<td><div>PJ002</div></td>
						<td><div>配件名称2</div></td>
						<td><div>A01-02</div></td>
						<td><div>件</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div></div></td>
						<td><div></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>3</div></td>
						<td><div>YD20140519001001</div></td>
						<td><div>PJ003</div></td>
						<td><div>配件名称3</div></td>
						<td><div>A01-02</div></td>
						<td><div>件</div></td>
						<td><div>是</div></td>
						<td><div>供应商一</div></td>
						<td><div>20</div></td>
						<td><div></div></td>
						<td><div></div></td>
					</tr>
				</tbody>
		</table>
		</div>
		<div class="formBar">
			<ul>
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
	$("#dia-print").click(function(){
		alertMsg.info("打印发料单.");
		return false;
	});
	$("#dia-printJt").click(function(){
		alertMsg.info("打印配件胶贴.");
		return false;
	});
});
</script>