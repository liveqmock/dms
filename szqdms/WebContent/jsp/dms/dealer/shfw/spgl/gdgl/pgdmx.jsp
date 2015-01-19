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
							<tr><td><label>派工单号：</label></td>
							    <td><input type="text" id="dia-pgdh" value="派工单号01"  readonly="readonly"/></td>
							    <td><label>客户姓名：</label></td>
							    <td><input type="text" id="dia-khxm" value="张三"  readonly="readonly"/></td>
							    <td><label>联系电话：</label></td>
							    <td><input type="text" id="dia-dz" value="XXX" readonly="readonly" /></td>
							    
							</tr>
							<tr>
							    <td><label>选择派工人员：</label></td>
							    <td> <input type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#13756531926=维修工程师1:13756531926=维修工程师2" datatype="0,is_null,30" value=""/></td>
							    <td><label>人员电话：</label></td>
							    <td><input type="text" id="dia-in-rydh" name="dia-in-rydh"  value=""/></td>
							    <td><label>是否需外出：</label></td>
							    <td>
								  <select type="text" id="dia-in-out"  name="dia-in-out"  kind="dic" src="E#1=是:0=否" datatype="0,is_null,30" >
					    				<option value="1" selected>是</option>
					    			</select>
							    </td>
							</tr>
							<tr>
							    <td><label>是否需维修车辆：</label></td>
							    <td>
								  <select type="text" id="dia-in-vehicle"  name="dia-in-vehicle"  kind="dic" src="E#1=是:0=否" datatype="0,is_null,30" >
					    				<option value="1" selected>是</option>
					    			</select>
							    </td>
							    <td><label>选择服务车辆：</label></td>
							    <td colspan="3"> <input type="text" id="dia-in-fwcl0" name="dia-in-fwcl0" kind="dic" src="E#1=车牌1:2=车牌2" datatype="0,is_null,30" value=""/><input id="dia-test" type="hidden"/></td>
							</tr>
							<tr>
							    <td><label>意见：</label></td>
							    <td colspan="5">
								  <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-bz"  datatype="1,is_null,100">输入拒绝原因</textarea>
							    </td>
							</tr>
						</table>
			  </fieldset>
			</div>
			</form>		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-ju" onclick="doDiaJuSave();">拒&nbsp;&nbsp;绝</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-pg" onclick="doDiaPgSave();">派&nbsp;&nbsp;工</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
});
function doDiaJuSave()
{
	alertMsg.info("拒绝派工");
	$.pdialog.closeCurrent();
}
function doDiaPgSave()
{
	alertMsg.info("派工完成");
	$.pdialog.closeCurrent();

}
</script>
</div>

