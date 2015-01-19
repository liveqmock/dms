<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-rywh" class="editForm" style="width:99%;">
		  <div align="left">
			  <fieldset>
						<table class="editTable" id="dia-tab-ryxx">
							<tr><td><label>人员姓名：</label></td>
							    <td><input type="text" id="dia-ryxm" value="张三"  datatype="0,is_null,30"/></td>
							    <td><label>手机号码：</label></td>
							    <td><input type="text" id="dia-lxdh" value="手机号码1"  datatype="0,is_number,30"/></td>
							    <td><label>住址：</label></td>
							    <td><input type="text" id="dia-dz" value="地址" datatype="1,is_null,60"/></td>
							    
							</tr>
							<tr>
							    <td><label>状态：</label></td>
							    <td>
							    	<select type="text" id="dia-in-zt"  name="dia-in-zt" kind="dic" src="E#1=有效:2=无效" datatype="0,is_null,30" value="">
								    		<option value="1" selected>有效</option>
								    </select>
								  </td>
							    <td><label>备注：</label></td>
							    <td colspan="3">
								  <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-bz"  datatype="1,is_null,100"></textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaRySave();">保&nbsp;&nbsp;存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
var action = "<%=action%>";
var ryxz_dialog = $("body").data("ryxxwh");
$(function(){
	$("button.close").click(function(){
		$.pdialog.close(ryxz_dialog);
		return false;
	});
});
function doDiaRySave()
{
	if(action==1)
	{
		alertMsg.info("新增成功。");
		$.pdialog.close(ryxz_dialog);
	}else
	{
		alertMsg.info("修改成功。");
		$.pdialog.close(ryxz_dialog);
	}
}
</script>
</div>

