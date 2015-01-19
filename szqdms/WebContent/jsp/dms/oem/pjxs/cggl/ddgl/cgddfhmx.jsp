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
							<tr><td><label>采购单号：</label></td>
							    <td><input type="text" id="dia-cgdh" value="采购单号"  readonly="readonly"/></td>
							    <td><label>年月：</label></td>
							    <td><input type="text" id="dia-year"  name="dia-year" value="2014年6月" readonly="readonly"/></td>
							    <td><label>供应商：</label></td>
							    <td><input type="text" id="in-ddzt"  name="in-ddzt" value="供应商1" readonly="readonly"/></td>
							</tr>
							<tr>
							    <td><label>采购类型：</label></td>
							    <td><input type="text" id="dia-cglx"  name="dia-cglx" value="月度计划" readonly="readonly"/></td>
							    <td><label>采购类别：</label></td>
							    <td><input type="text" id="dia-cglb"  name="dia-cglb" value="采购" readonly="readonly"/></td>
							    <td><label>提报日期：</label></td>
							    <td><input type="text" id="dia-in-zdrq" name="dia-in-zdrq" value="2014-05-28 "  readonly="readonly"/></td>
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
										<th fieldname="JHS"  ftype="input" fdatatype="0,is_null,30" colwidth="40" freadonly="true" >采购数量</th>
										<th fieldname="JHJG" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true" >采购单价</th>
										<th fieldname="JE"   ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">金额</th>
										<th fieldname="JHZQ" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">交货周期</th>
										<th fieldname="JHZQ" ftype="input" fdatatype="0,is_null,30" colwidth="50" freadonly="true">已发数量</th>
										<th fieldname="BZ"   ftype="input" fdatatype="1,is_null,30" colwidth="50" freadonly="false">本次发货数量</th>
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
										<td><div align="right">100.00</div></td>
										<td><div>20天</div></td>
										<td><div>5</div></td>
										<td><input id="fhsl1" value="5" name="fhsl1"/></td>
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
										<td><div align="right">200.00</div></td>
										<td><div>20天</div></td>
										<td><div>10</div></td>
										<td><input id="fhsl1" value="10" name="fhsl1"/></td>
									</tr>
								</tbody>
						</table>
					</div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave(1);">保&nbsp;&nbsp;存</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave(2);">发货完成</button></div></div></li>
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
function doDiaCgdSave(value)
{
	if(value==1)
	{
		alertMsg.info("保存成功");
	}else
	{
		alertMsg.info("发货完成");
		$.pdialog.closeCurrent();
	}
	
}

</script>
</div>

