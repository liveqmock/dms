<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout" style="width:100%;">
	<div class="page">
		<div class="pageHeader" >
		<form method="post" id="fm-ddcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-ddcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>装箱单号：</label></td>
					    <td><input type="text" id="zxdh" name="zxdh" readonly datasource="DDBH" datatype="1,is_null,30" operation="like" value="XH01"/></td>
						<td><label>装箱时间：</label></td>
					    <td><input type="text" id="zxdh" name="zxdh" readonly datasource="DDBH" datatype="1,is_null,30" operation="like" value="2014-05-19 8:30:26"/></td>
					</tr>
					<tr>   
					    <td><label>装箱人：</label></td>
					    <td><input type="text" id="zxdh" name="zxdh" readonly datasource="DDBH" datatype="1,is_null,30" operation="like" value="张三"/></td>
					    <td><label>联系电话：</label></td>
					    <td><input type="text" id="zxdh" name="zxdh" readonly datasource="DDBH" datatype="1,is_null,30" operation="like" value="13756565566"/></td>
					</tr>
				</table>
			</div>
			</form>
		</div>
		<div class="pageContent" >
			<div id="dia-pjmx" style="height:300px;overflow:auto;">
				<table style="width:100%;" id="tab-pjlb" name="tablist"  ref="dia-pjmx" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" style="display:"></th>
						<th type="multi" name="CX-XH" style="display:none;" ></th>
						<th fieldname="PJDM">订单号码</th>
						<th fieldname="PJDM">配件图号</th>
						<th fieldname="PJMC">配件名称</th>
						<th fieldname="PJID" style="display:none" freadonly="true">配件ID</th>
						<th fieldname="JLDWMC">单位</th>
						<th fieldname="JLDWMC" colwidth="80">是否指定供应商</th>
						<th fieldname="JLDWMC">供应商</th>
						<th fieldname="JLDWMC">装箱数量</th>
						<th fieldname="KW" freadonly="true" >备注</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="rownums"><div>1</div></td>
						<td><div>YD20140519001</div></td>
						<td><div>PJ001</div></td>
						<td><div>配件名称1</div></td>
						<td style="display:none"><div>2323232323</div></td>
						<td><div>件</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>2</div></td>
						<td><div>YD20140519001</div></td>
						<td><div>PJ002</div></td>
						<td><div>配件名称2</div></td>
						<td style="display:none"><div>2323232323</div></td>
						<td><div>件</div></td>
						<td><div>是</div></td>
						<td><div>供应商一</div></td>
						<td><div>20</div></td>
						<td><div></div></td>
					</tr>
					<tr >
						<td class="rownums"><div>3</div></td>
						<td><div>ZD20140519001</div></td>
						<td><div>PJ003</div></td>
						<td><div>配件名称3</div></td>
						<td style="display:none"><div>2323232323</div></td>
						<td><div>件</div></td>
						<td><div>否</div></td>
						<td><div></div></td>
						<td><div>20</div></td>
						<td><div>测试</div></td>
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
	$("#tab-pjlb").show();
	$("#tab-pjlb").jTable();
	$("#dia-close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	//设置高度
	$("#ddjbxx").height(document.documentElement.clientHeight-100);
});
</script>