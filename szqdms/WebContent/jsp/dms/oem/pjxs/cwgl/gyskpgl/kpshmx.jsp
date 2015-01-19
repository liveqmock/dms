<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >	
				<div id="dia-lpmx" style="">
						<table style="display:none;width:99%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx"  edit="false">
									<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:" colwidth="20" ></th>
							<th fieldname="" colwidth="60" >单号</th>
							<th fieldname="" colwidth="60" >原采购单号</th>
							<th fieldname="" colwidth="60" >类别</th>
							<th fieldname="" colwidth="60" >供应商代码</th>
							<th fieldname="" colwidth="60" >供应商名称</th>
							<th fieldname="" colwidth="40" >数量</th>
							<th fieldname="" align="right" colwidth="60" >金额</th>
							<th fieldname="" colwidth="60" >发票号</th>
							<th fieldname="" colwidth="60" >备注</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td><div>采购单号1</div></td>
							<td><div></div></td>
							<td><div>采购</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><div>20</div></td>
							<td><div>7,245.80</div></td>
							<td><div>发票01</div></td>
							<td><div>...</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td><div>退货单号1</div></td>
							<td><div>采购单号1</div></td>
							<td><div>采购退货</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><div>15</div></td>
							<td><div>444.20</div></td>
							<td><div>发票01</div></td>
							<td><div>...</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>3</div></td>
							<td><div>采购单号2</div></td>
							<td><div></div></td>
							<td><div>采购</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><div>20</div></td>
							<td><div>7,245.80</div></td>
							<td><div>发票02</div></td>
							<td><div>...</div></td>
						</tr>
						<tr>
							<td class="rownums"><div>4</div></td>
							<td><div>退货单号2</div></td>
							<td><div>采购单号2</div></td>
							<td><div>采购退货</div></td>
							<td><div>供应商代码1</div></td>
							<td><div>供应商名称1</div></td>
							<td><div>15</div></td>
							<td><div>444.20</div></td>
							<td><div>发票02</div></td>
							<td><div>...</div></td>
						</tr>
					</tbody>
						</table>
					</div>
				<div class="formBar">
					<ul>
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
	$("#dia-tab-pjlb").show();
	$("#dia-tab-pjlb").jTable();
});
</script>
</div>

