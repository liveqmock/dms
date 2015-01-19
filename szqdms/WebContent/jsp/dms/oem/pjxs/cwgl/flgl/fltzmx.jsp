<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-htwh" class="editForm" style="width:99%;">
		  <div align="left">
			  <table width="100%" id="dia-tab-fltzmx" name="dia-tab-fltzmx" style="display: none" >
				<thead>
					<tr>
						<th fieldname="ROWNUMS" style="display:"></th>
						<th type="multi" name="CX-XH" style="vertical-align:middle;" ></th>
						<th colwidth="60" >服务商代码</th>
						<th colwidth="60" >服务商名称</th>
						<th colwidth="100" align="right">返利金额</th>
						<th colwidth="140" >备注</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
					<td class="rownums"><div>1</div></td>
					<td style="text-align:center;vertical-align:middle;"><div ><input type="checkbox" name="in-cx-xh" /></div></td>
					<td><div>配送中心1</div></td>
					<td><div>配送中心1</div></td>
					<td><div><input id="dia-in-flje0" name="dia-in-flje0" value="1000.00" datatype="0,is_money,20"/></div></td>
					<td><div><input id="dia-in-bz0" name="dia-in-bz0" value="" datatype="1,is_null,100"/></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>2</div></td>
					<td style="text-align:center;vertical-align:middle;"><div ><input type="checkbox" name="in-cx-xh" /></div></td>
					<td><div>配送中心2</div></td>
					<td><div>配送中心2</div></td>
				    <td><div><input id="dia-in-flje1" name="dia-in-flje1" value="2000.00" datatype="0,is_money,20"/></div></td>
					<td><div><input id="dia-in-flje1" name="dia-in-bz1" value="" datatype="1,is_null,100"/></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>3</div></td>
					<td style="text-align:center;vertical-align:middle;"><div ><input type="checkbox" name="in-cx-xh" /></div></td>
					<td><div>配送中心3</div></td>
					<td><div>配送中心3</div></td>
				    <td><div><input id="dia-in-flje2" name="dia-in-flje2" value="3000.00" datatype="0,is_money,20"/></div></td>
					<td><div><input id="dia-in-bz2" name="dia-in-bz2" value="" datatype="1,is_null,100"/></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>4</div></td>
					<td style="text-align:center;vertical-align:middle;"><div ><input type="checkbox" name="in-cx-xh" /></div></td>
					<td><div>配送中心4</div></td>
					<td><div>配送中心4</div></td>
				    <td><div><input id="dia-in-flje3" name="dia-in-flje3" value="4000.00" datatype="0,is_money,20"/></div></td>
					<td><div><input id="dia-in-bz3" name="dia-in-bz3" value="" datatype="1,is_null,100"/></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>5</div></td>
					<td style="text-align:center;vertical-align:middle;"><div ><input type="checkbox" name="in-cx-xh" /></div></td>
					<td><div>配送中心5</div></td>
					<td><div>配送中心5</div></td>
				    <td><div><input id="dia-in-flje4" name="dia-in-flje4" value="5000.00" datatype="0,is_money,20"/></div></td>
					<td><div><input id="dia-in-bz4" name="dia-in-bz4" value="" datatype="1,is_null,100"/></div></td>
				</tr>
				</tbody>
			</table>
			</div>
			</form>		
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave(1);">保存</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave(2);">调整完成</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xzmb" onclick="doDownload(1)">下载模板</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-pldr" onclick="doDownload(2)">批量导入</button></div></div>
							<div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div>
						</li>
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
	$("#dia-tab-fltzmx").show();
	$("#dia-tab-fltzmx").jTable();
});
function doDiaCgdSave()
{
	alertMsg.info("调整完成");
	$.pdialog.closeCurrent();
}
</script>
</div>

