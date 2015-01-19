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
							<tr><td><label>服务站代码：</label></td>
							    <td><input type="text" id="dia-fwzdm" value="配送中心1"  readonly="readonly"/></td>
							    <td><label>服务站名称：</label></td>
							    <td><input type="text" id="dia-fwzmc"  name="dia-fwzmc" value="配送中心" readonly="readonly"/></td>
							</tr>
							<tr>
								<td><label>选择额度类型：</label></td>
							    <td>
							    	<select type="text" id="dia-in-zhlx"  name="dia-in-zhlx" datasource="ZHLX" kind="dic" src="E#1=固定:2=临时" datatype="0,is_null,30" >
					    				<option value="1" selected>固定</option>
					    			</select>
							    </td>
							    <td><label>当前金额（元）：</label></td>
							    <td><input type="text" id="dia-in-je" value="100,000.00"  readonly="readonly"/></td>
							</tr>
							<tr>
							    <td><label>调整后金额（元）：</label></td>
							    <td colspan="3"><input type="text" id="dia-in-je" value=""  datatype="0,is_money,30"/></td>
							</tr>
							<tr id="dia-yxqx" style="display: none">
							    <td><label>有效期限：</label></td>
							    <td colspan="3"><input type="text" group="in-kstbrq,in-jstbrq"  id="in-kstbrq"  name="in-kstbrq" value="2014-06-01" style="width:75px;"  datatype="1,is_null,30" onclick="WdatePicker()" />
				    				<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
									<input type="text" group="in-kstbrq,in-jstbrq"  id="in-jstbrq"  name="in-jstbrq" style="width:75px;margin-left:-30px;"  value="2014-12-31"  datatype="1,is_null,30" onclick="WdatePicker()" /></td>
							</tr>
							<tr>
							    <td><label>调整原因：</label></td>
							    <td colspan="3">
							    <textarea id="dia-in-bz" style="width:200px;height:40px;" name="dia-in-bz" datasource="BZ"  datatype="1,is_null,100"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaCgdSave();">调&nbsp;&nbsp;整</button></div></div></li>
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
});
function doDiaCgdSave()
{
	alertMsg.info("调整完成");
	$.pdialog.closeCurrent();
}
function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="dia-in-zhlx")
	{
		if($("#"+id).attr("code") == "1")
		{
			$("#dia-yxqx").hide();
		}else
		{
			$("#dia-yxqx").show();
		}
		
		return true;
	}
	return true;
}
</script>
</div>

