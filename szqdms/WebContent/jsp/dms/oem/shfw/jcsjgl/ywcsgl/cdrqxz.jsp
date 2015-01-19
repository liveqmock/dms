<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="cdrqxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="di-cdrqxx" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="cdrqxz">
				<tr>
					<td><label>服务商代码：</label></td>
					<td><input type="text" id="DI_FWSDM" name="DI_FWSDM" value=""/></td>
					<td><label>服务商名称：</label></td>
					<td><input type="text" id="DI_FWSMC" name="DI_FWSMC" value=""/></td>
				</tr>
				<tr>	
					<td><label>超单天数：</label></td>
					<td><input type="text"  id="DI_CDTS" name="DI_CDTS" value="" /></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		<form  method="post" id="claimform">
			<div id="spd">
			<table style="display:none" width="100%"  id="spdlb" name="spdlb" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:" append="plus|addClaim"></th>
							<th>索赔单号</th>
							<th>超单天数</th>
							<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="radio" name="ra" /></td>
							<td>索赔单号1</td>
							<td>8</td>
							<td><a href="#" onclick="doDeleteClaim()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td><input  type="radio" name="ra"/></td>
							<td>索赔单号2</td>
							<td>8</td>
							<td><a href="#" onclick="doDeleteClaim()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td><input  type="radio" name="ra" /></td>
							<td>索赔单号3</td>
							<td>8</td>
							<td><a href="#" onclick="doDeleteClaim()" class="op">[删除]</a></td>
						</tr>
						<tr>
							<td><input  type="radio" name="ra"/></td>
							<td>索赔单号4</td>
							<td>8</td>
							<td><a href="#" onclick="doDeleteClaim()" class="op">[删除]</a></td>
						</tr>
					</tbody>
			</table>
			</div>
		</form>
	</div>
	</div>	
</div>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	if(action != "1"){
		$("#DI_FWSDM").val("服务商代码1");
		$("#DI_FWSMC").val("服务商名称1");
		$("#DI_CDTS").val("7");
		$("#DI_FWSDM").attr("readonly",true);
		$("#DI_FWSMC").attr("readonly",true);
		$("#spdlb").show();
		$("#spdlb").jTable();
	}
});
function addClaim(){
	var options = {max:false,width:720,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqspdxz.jsp?flag=1", "cdrqspd", "索赔单超单日期新增", options);
}
/* function doUpdateClaim(){
	var options = {max:false,width:720,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqspdxz.jsp?flag=2", "cdrqspd", "索赔单超单日期编辑", options);
} */
function doDeleteClaim(){
	alertMsg.info("删除成功");
}
function doSave(){
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("cdrqxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>