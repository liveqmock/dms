<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-pjcx">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-cx-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件图号：</label></td>
					    <td><input type="text" id="dia-in-cx-pjth" name="dia-in-cx-pjth" datasource="PJBM" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="dia-in-cx-pjmc" name="dia-in-cx-pjmc" datasource="PJMC" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-pjcx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-pjcxqd" >确&nbsp;&nbsp;定</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-pjcxgb">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-cx-pjlb" >
			<table style="display:none;width:100%;" id="tab-cx-pjlb" name="tablist" ref="page-cx-pjlb" refQuery="fm-cx-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="multi" name="CX-XH" style="display:" ></th>
							<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" freadonly="true">配件图号</th>
							<th fieldname="PJMC" colwidth="150" freadonly="true">配件名称</th>
							<th fieldname="PJID" style="display:none" freadonly="true">配件ID</th>
							<th fieldname="JLDWMC" freadonly="true" >计量单位</th>
							<th fieldname="ZXBZSL" freadonly="true" >最小包装</th>
							<th fieldname="JXSJ" colwidth="60" ftype="input" fdatatype="0,is_money,30" freadonly="true" >经销商价</th>
							<th fieldname="GYS名称" >供应商</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
							<td><div>PJ001</div></td>
							<td><div>配件名称1</div></td>
							<td style="display:none"><div>2323232323</div></td>
							<td><div>件</div></td>
							<td><div>10/包</div></td>
							<td><div>800.00</div></td>
							<td><div>供应商1</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
							<td><div>PJ002</div></td>
							<td><div>配件名称2</div></td>
							<td style="display:none"><div>2323232323</div></td>
							<td><div>件</div></td>
							<td><div>10/袋</div></td>
							<td><div>800.00</div></td>
							<td><div>供应2</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
							<td><div>PJ003</div></td>
							<td><div>配件名称3</div></td>
							<td style="display:none"><div>2323232323</div></td>
							<td><div>件</div></td>
							<td><div>10/袋</div></td>
							<td><div>800.00</div></td>
							<td><div>供应商3</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
							<td><div>PJ004</div></td>
							<td><div>配件名称4</div></td>
							<td style="display:none"><div>2323232323</div></td>
							<td><div>件</div></td>
							<td><div>10/箱</div></td>
							<td><div>800.00</div></td>
							<td><div>供应商4</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
$(function()
{
	$("#btn-pjcxgb").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#btn-pjcx").bind("click",function(){
		$("#tab-cx-pjlb").show();
		$("#tab-cx-pjlb").jTable();
	});
	$("#btn-pjcxqd").bind("click",function(){
		//复选配件列表
		alertMsg.info("多选配件列表，在配件清单中生成相应的记录");
		var $tab = $("#dia-tab-pjlb_content");
		$tab.createRow();
		$tab.createRow();
	});
});
</script>