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
						<tr><td><label>退件单号：</label></td>
						    <td><input type="text" id="dia-thdh" value="退货单号"  readonly="readonly"/></td>
						    <td><label>接收方：</label></td>
						    <td><input type="text" id="dia-cgdh"  name="dia-cgdh" value="陕汽配件销售公司" readonly="readonly"/></td>
						    <td><label>申请日期：</label></td>
						    <td><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="2014-05-20 "  readonly="readonly"/></td>
						</tr>
						<tr>
						    <td><label>审核员：</label></td>
						    <td><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="张三"  readonly="readonly"/></td>
						    <td><label>审核日期：</label></td>
						    <td><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="2014-05-20 "  readonly="readonly"/></td>
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
										<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" freadonly="true">配件代码</th>
										<th fieldname="PJMC" ftype="input" fdatatype="0,is_null,30"  freadonly="true">配件名称</th>
										<th fieldname="JLDW" ftype="input" fdatatype="0,is_null,30" freadonly="true">计量单位</th>
										<th fieldname="ZXBZ" ftype="input" fdatatype="0,is_null,30"  freadonly="true">最小包装</th>
										<th fieldname="JHS"  ftype="input" fdatatype="0,is_null,30"  colwidth="60" freadonly="true" >退货数量</th>
										<th fieldname="JHS"  ftype="input" fdatatype="0,is_null,30" colwidth="60" freadonly="true" >出库数量</th>
										<th fieldname="JHJG" ftype="input" fdatatype="0,is_null,30"  freadonly="true" >经销商价格</th>
										<th fieldname="JE"   ftype="input" fdatatype="0,is_null,30"freadonly="true">金额</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="rownums"><div>1</div></td>
										<td><div>配件代码1</div></td>
										<td><div>配件名称1</div></td>
										<td><div>件</div></td>
										<td><div>10/件</div></td>
										<td><div>10</div></td>
										<td><div><input type="text" style="width:50px;" value="10"/></div></td>
										<td><div>10.00</div></td>
										<td><div align="right">100.00</div></td>
									</tr>
									<tr>
										<td class="rownums"><div>2</div></td>
										<td><div>配件代码2</div></td>
										<td><div>配件名称2</div></td>
										<td><div>件</div></td>
										<td><div>10/件</div></td>
										<td><div>20</div></td>
										<td><div><input type="text" style="width:50px;" value="20"/></div></td>
										<td><div>10.00</div></td>
										<td><div align="right">200.00</div></td>
									</tr>
								</tbody>
						</table>
					</div>
					<br/>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doSubmit();">退货出库</button></div></div></li>
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
function doSubmit()
{
	alertMsg.info("操作成功.");
	$.pdialog.closeCurrent();
}

</script>
</div>

