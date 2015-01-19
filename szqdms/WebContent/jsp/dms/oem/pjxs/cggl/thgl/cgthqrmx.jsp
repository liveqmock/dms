<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-htwh" class="editForm" style="width:99%;">
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-htxx">
							<tr><td><label>退货单号：</label></td>
							    <td><input type="text" id="dia-thdh" value="退货单号"  readonly="readonly"/></td>
							    <td><label>原采购单号：</label></td>
							    <td><input type="text" id="dia-cgdh"  name="dia-cgdh" value="采购单号" readonly="readonly"/></td>
							    <td><label>选择供应商：</label></td>
							    <td><input type="text" id="in-ddzt"  name="in-ddzt" value="供应商1" readonly="readonly"/></td>
							</tr>
							<tr>
							    <td><label>退货类别：</label></td>
							    <td><input type="text" id="dia-cglb"  name="dia-cglb" value="采购退回" readonly="readonly"/></td>
							    <td><label>制单日期：</label></td>
							    <td colspan="3"><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="2014-05-28 "  readonly="readonly"/></td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
				<div id="dia-lpmx" style="">
						<table style="display:none;width:99%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx"  edit="false">
								<thead>
									<tr>
										<th  name="XH" style="display:" ></th>
										<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" colwidth="80" freadonly="true">配件代码</th>
										<th fieldname="CKTH" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">参图号</th>
										<th fieldname="PJMC" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">配件名称</th>
										<th fieldname="JLDW" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">计量单位</th>
										<th fieldname="ZXBZ" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">最小包装</th>
										<th fieldname="JHS"  ftype="input" fdatatype="0,is_null,30" colwidth="40" freadonly="true" >退货数</th>
										<th fieldname="JHJG" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" >采购价格</th>
										<th fieldname="JE"   ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">退货金额</th>
										<th fieldname="BZ"   ftype="input" fdatatype="1,is_null,30" colwidth="50" freadonly="false">备注</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="rownums"><div>1</div></td>
										<td><div>配件代码1</div></td>
										<td><div></div></td>
										<td><div>配件名称1</div></td>
										<td><div>件</div></td>
										<td><div>10/件</div></td>
										<td><div>10</div></td>
										<td><div>10.00</div></td>
										<td><div>100</div></td>
										<td></td>
									</tr>
									<tr>
										<td class="rownums"><div>2</div></td>
										<td><div>配件代码2</div></td>
										<td><div></div></td>
										<td><div>配件名称2</div></td>
										<td><div>件</div></td>
										<td><div>10/件</div></td>
										<td><div>20</div></td>
										<td><div>10.00</div></td>
										<td><div>200</div></td>
										<td></td>
									</tr>
								</tbody>
						</table>
					</div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave();">确&nbsp;&nbsp;认</button></div></div></li>
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
function doDiaCgdSave()
{
	alertMsg.info("确认完成");
	$.pdialog.closeCurrent();
}

</script>
</div>

