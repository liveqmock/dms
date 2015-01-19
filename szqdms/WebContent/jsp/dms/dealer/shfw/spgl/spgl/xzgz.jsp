<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
					   <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-cx-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>故障代码：</label></td>
					    <td><input type="text" id="dia-in-cx-gzdm" name="dia-in-cx-gzdm" datasource="GZBM" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>故障名称：</label></td>
					    <td><input type="text" id="dia-in-cx-gzmc" name="dia-in-cx-gzmc" datasource="GZMC" datatype="1,is_digit_letter,30" operation="like" /></td>
					</tr>
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
							<th fieldname="PJDM" colwidth="60">故障代码</th>
							<th fieldname="PJMC" colwidth="150" >故障名称</th>
							<th fieldname="ZY" colwidth="60">作业</th>
						    <th fieldname="WXGS" colwidth="60">维修工时</th>
							<th fieldname="PJID"  freadonly="true">故障描述</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
							<td><div>故障代码1</div></td>
							<td><div>故障名称1</div></td>
							<td><div>作业1</div></td>
							<td><div>5</div></td>
							<td><div>描述</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div><input type="checkbox" name="in-cx-xh" /></div></td>
							<td><div>故障代码2</div></td>
							<td><div>故障名称2</div></td>
							<td><div>作业2</div></td>
							<td><div>6</div></td>
							<td><div>描述</div></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
<script type="text/javascript">
$(function(){
	$("#btn-pjcxgb").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#btn-pjcx").bind("click",function(){
		$("#tab-cx-pjlb").show();
		$("#tab-cx-pjlb").jTable();
	});
});
</script>
</div>

