<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String type = request.getParameter("type");
	if(type == null || "".equals(type))
		type = "1";
%>
<!-- 
	 Title:角色选择
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->

<div>
	<div class="page">
	<div class="pageContent" style="width:100%;float:left">
	<table style="width:100%;float:left;" class="normal">
		<tr>
			<td><h2 class="contentTitle" style="width:180px;">未授予角色</h2></td>
			<td>&nbsp;</td>
			<td><h2 class="contentTitle" style="width:180px;">已授予角色</h2></td>
		</tr>
		<tr><td style="width:210px;">
				<div id="unGrantedRoles" class="sortDrag" style="width:210px;border:1px solid #e66;margin:5px;float:left;height:330px;overflow:auto;"></div>
			</td>
			<td style="width:30px"></td>
			<td style="width:210px;">
				<div id="grantedRoles" class="sortDrag" style="width:210px;border:1px solid #e66;margin:5px;float:left;height:330px;overflow:auto;"></div>
			</td>
		</tr>
	</table>
	</div>
		
	<div class="formBar" id="selectRoleBar">
		<ul>
			<%
				if("1".equals(type))
				{
			%>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSelectedRolesOk();" id="selectedRolesOk">授&nbsp;&nbsp;予</button></div></div></li>
			<%
				}
			%>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	
	<form id="grantedRolesFm" style="display:none">
		<input type="hidden" datasource="ROLEIDS" id="rolelistId" />
		<input type="hidden" datasource="ROLECODES" id="rolelistCode" />
		<input type="hidden" datasource="ACCOUNT" id="roleAccount" />
		<input type="hidden" datasource="USERID" id="roleUserId" />
	</form>
	</div>
</div>
<script type="text/javascript">
//查询提交方法
var selectRoleUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction";
var saveRoleUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/saveRoleUser.ajax";
var syncRoleAction = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/synchronize.ajax";
var type = "<%=type%>";
$(function()
{
	if(type == "1")
	{
		$("#roleAccount").val($("#dia-account").val());
		$("#roleUserId").val($("#dia-userId").val());
	}
	else
	{
		var selectedRows = $("#tab-userlist").getSelectedRows();
		$("#roleAccount").val(selectedRows[0].attr("ACCOUNT"));
		$("#roleUserId").val(selectedRows[0].attr("USER_ID"));
	}
	var unGrantedRoleUrl = selectRoleUrl + "/selectUnGrantedRole.ajax?userId="+$("#roleUserId").val();
	sendPost(unGrantedRoleUrl,"","",unGrantedRoleCallback,"false");
	var grantedRoleUrl = selectRoleUrl + "/selectGrantedRole.ajax?userId="+$("#roleUserId").val();
	sendPost(grantedRoleUrl,"","",grantedRoleCallback,"false");
});

function unGrantedRoleCallback(res)
{
	var list = res.getElementsByTagName("ROW");
	var $div = $("#unGrantedRoles");
	if(list != null && list.length >0)
	{
		for(var i=0;i<list.length;i++)
		{
			var d = $("<DIV style='border:1px solid #B8D0D6;padding:5px;margin:5px;width:170px;' name='roles'></DIV>");
			d.html(getNodeText(list[i].getElementsByTagName("RNAME").item(0)));
			d.attr("c", getNodeText(list[i].getElementsByTagName("CODE").item(0)));
			d.attr("i", getNodeText(list[i].getElementsByTagName("ROLE_ID").item(0)));
			$div.append(d);
		}
	}
	$div.sortDrag({replace:false});
}

function grantedRoleCallback(res)
{
	var list = res.getElementsByTagName("ROW");
	var $div = $("#grantedRoles");
	$div.addClass("sortDrag");
	if(list != null && list.length >0)
	{
		for(var i=0;i<list.length;i++)
		{
			var d = $("<DIV style='border:1px solid #B8D0D6;padding:5px;margin:5px;width:170px;' name='roles'></DIV>");
			d.html(getNodeText(list[i].getElementsByTagName("RNAME").item(0)));
			d.attr("c", getNodeText(list[i].getElementsByTagName("CODE").item(0)));
			d.attr("i", getNodeText(list[i].getElementsByTagName("ROLE_ID").item(0)));
			$div.append(d);
		}
	}
	$div.sortDrag({replace:false});
}

function doSelectedRolesOk()
{
	var rC="",rI="";
	$("div[name='roles']", $("#grantedRoles")).each(function(){
		var $this = $(this);
		if(rC)
		{
			rC += "," + $this.attr("c");
			rI += "," + $this.attr("i");
		}
		else
		{
			rC = $this.attr("c");
			rI = $this.attr("i");
		}
	});
	$("#rolelistCode").val(rC);
	$("#rolelistId").val(rI);
	var sCondition = {};
	sCondition = $("#grantedRolesFm").combined(1) || {};
	doNormalSubmit($("#grantedRolesFm"),saveRoleUrl,"selectedRolesOk",sCondition,saveRolePsnCallBack);
}
function saveRolePsnCallBack(res)
{
	//同步集群节点（业务功能不需要）
	var syncUrl = syncRoleAction + "?type=4&account="+$("#roleAccount").val();
	//alert(syncUrl);
	sendPost(syncUrl,"","","","false");
	return true;
}
</script>