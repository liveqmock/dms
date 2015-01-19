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
						<th colwidth="60" >服务商代码</th>
						<th colwidth="60" >服务商名称</th>
						<th colwidth="100" align="right">返利金额</th>
						<th colwidth="140" >备注</th>
					</tr>
				</thead>
				<tbody>
				   <tr>
					<td class="rownums"><div>1</div></td>
					<td><div>配送中心1</div></td>
					<td><div>配送中心1</div></td>
					<td><div>1,000.00</div></td>
					<td><div>...</div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>2</div></td>
					<td><div>配送中心2</div></td>
					<td><div>配送中心2</div></td>
				    <td><div>2,000.00</div></td>
					<td><div></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>3</div></td>
					<td><div>配送中心3</div></td>
					<td><div>配送中心3</div></td>
				    <td><div>3,000.00</div></td>
					<td><div></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>4</div></td>
					<td><div>配送中心4</div></td>
					<td><div>配送中心4</div></td>
				    <td><div>4,000.00</div></td>
					<td><div></div></td>
				</tr>
				 <tr>
					<td class="rownums"><div>5</div></td>
					<td><div>配送中心5</div></td>
					<td><div>配送中心5</div></td>
				    <td><div>5,000.00</div></td>
					<td><div></div></td>
				</tr>
				</tbody>
			</table>
			</div>
			</form>
			 <form method="post" id="dia-fm-htsh" class="editForm" style="width:99%;">
		          	<div align="left">
					  <fieldset>
							<table class="editTable" id="dia-tab-shxx">
									
									<tr>
									    <td><label>审核意见：</label></td>
									    <td >
								 		 <textarea id="dia-shyj" style="width:450px;height:40px;" name="dia-shyj"  datatype="0,is_null,100"></textarea>
							           </td>
									</tr>
								</table>
					  </fieldset>
					</div>
				</form>		
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave(1);">审核通过</button></div></div>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave(2);">审核驳回</button></div></div>
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
function doDiaCgdSave(value)
{
	if(value==1)
	{
		alertMsg.info("审核通过");
		$.pdialog.closeCurrent();
	}else
	{
		alertMsg.info("审核驳回");
		$.pdialog.closeCurrent();
	}
	
}
</script>
</div>

