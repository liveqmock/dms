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
								<td><label>选择账户：</label></td>
							    <td>
							    	<select type="text" id="dia-in-zhlx"  name="dia-in-zhlx" datasource="ZHLX" kind="dic" src="E#1=现金:2=承兑:3=材料费" datatype="0,is_null,30" >
					    				<option value="1" selected>现金</option>
					    			</select>
							    </td>
							    <td><label>选择操作类型：</label></td>
							    <td>
							    	<select type="text" id="dia-in-czlx"  name="dia-in-czlx" datasource="ZHLX" kind="dic" src="E#1=扣款" datatype="0,is_null,30" >
					    				<option value="1" selected>扣款</option>
					    			</select>
							    </td>
							</tr>
							<tr>
							    <td><label>当前可用金额：</label></td>
							    <td><input type="text" id="dia-dqkyje" value="90,000.00"  readonly="readonly"/></td>
							    <td><label>输入金额：</label></td>
							    <td><input type="text" id="dia-in-je" value=""  datatype="0,is_money,30"/></td>
							</tr>
							<tr>
							    <td><label>备注：</label></td>
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
});
function doDiaCgdSave()
{
	alertMsg.info("确认完成");
	$.pdialog.closeCurrent();
}
function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="dia-in-zhlx")
	{
		if($("#"+id).attr("code") == "1")
		{
			$("#dia-dqkyje").val("90,000.00");
		}else if($("#"+id).attr("code") == "2")
		{
			$("#dia-dqkyje").val("1,000.00");
		}else
		{
			$("#dia-dqkyje").val("0");
		}
		
		return true;
	}
	return true;
}
</script>
</div>

