<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String companyId = user.getCompanyId();
	String orgId = user.getOrgId();
%>
<!-- 
	 Title:人员选择
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->

<div id="" width="100%">
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>未授予人员</span></a></li>
					<li><a href="javascript:void(0)"><span>已授予人员</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page" >
			<div class="pageHeader" >
			<form method="post" id="UnGrantedPersonFm">
			<!-- 定义查询条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="UnGrantedPersonCx">
						<tr><td><label>用户账号：</label></td>
						    <td><input type="text" id="w_yhzh" name="w_yhzh" datasource="p.ACCOUNT" datatype="1,is_null,30" operation="like" value=""/></td>
							<td><label>姓名：</label></td>
						    <td><input type="text" id="w_yhmc" name="w_yhmc" datasource="p.PERSON_NAME" operation="like" datatype="1,is_null,30" value=""/></td>
						    <td><label>所属组织：</label></td>
						    <td><input type="text" id="w_szbm"  name="w_szbm" datasource="p.ORG_ID"  kind="dic" src="ZZJG"  datatype="1,is_null,30" value=""/>
						    </td>
						</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="UnGrantedPersonsCxBtn">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
			</form>
			</div>
			<div class="pageContent">
				<div id="page_UnGrantedPersonsJglb" style="width:100%;">
					<table style="display:none" width="98%" multivals="w_grantFm" limitH="false" id="UnGrantedPersonsJglb" name="tablist" ref="page_UnGrantedPersonsJglb" refQuery="UnGrantedPersonCx" >
							<thead>
								<tr>
									<th type="multi" name="XH" unique="ACCOUNT"></th>
									<th fieldname="ACCOUNT">用户账号</th>
									<th fieldname="PERSON_NAME">姓名</th>
									<th fieldname="SEX" >性别</th>
									<th fieldname="ORG_ID" >所属部门</th>
									<th fieldname="PERSON_KIND" >用户类型</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
					</table>
				</div>
			</div>
			<div class="formBar" id="w_grantBar" style="display:none">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="w_grant">确认</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
				</ul>
			</div>
			<div style="display:none">
				<form id="w_grantFm" method="post">
					<table>
						<tr><td>
							<textarea  id="w_val0" name="multivals" datasource="ACCOUNTS" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
							<textarea  id="w_val1" name="multivals" datasource="USERIDS" style="width:400px;height:10px"style="display:none" ></textarea>
						</td></tr>
					</table>
				</form>
			</div>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page" >
			<div class="pageHeader" >
			<form method="post" id="GrantedPersonFm">
			<!-- 定义查询条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="GrantedPersonCx">
						<tr><td><label>用户账号：</label></td>
						    <td><input type="text" id="y_yhzh" name="y_yhzh" datasource="p.ACCOUNT" datatype="1,is_null,30" operation="like" value=""/></td>
							<td><label>姓名：</label></td>
						    <td><input type="text" id="y_yhmc" name="y_yhmc" datasource="p.PERSON_NAME" operation="like" datatype="1,is_null,30" value=""/></td>
						    <td><label>所属组织：</label></td>
						    <td><input type="text" id="y_szbm"  name="y_szbm" datasource="p.ORG_ID"  kind="dic" src="ZZJG"  datatype="1,is_null,30" value=""/>
						    </td>
						</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="GrantedPersonsCxBtn">查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
			</form>
			</div>
			<div class="pageContent">
				<div id="page_GrantedPersonsJglb" >
					<table style="display:none" width="100%" multivals="y_grantFm" id="GrantedPersonsJglb" name="tablist" ref="page_GrantedPersonsJglb" refQuery="GrantedPersonCx" >
							<thead>
								<tr>
									<th type="multi" name="XH" unique="ACCOUNT"></th>
									<th fieldname="ACCOUNT">用户账号</th>
									<th fieldname="PERSON_NAME">姓名</th>
									<th fieldname="SEX" >性别</th>
									<th fieldname="ORG_ID" >所属部门</th>
									<th fieldname="PERSON_KIND" >用户类型</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
					</table>
				</div>
			</div>
			<div class="formBar" id="y_grantBar" style="display:none">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="y_grant">删除</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
				</ul>
			</div>
			<div style="display:none">
				<form id="y_grantFm" method="post">
					<table>
						<tr><td>
							<textarea  id="y_val0" name="multivals" datasource="ACCOUNTS" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
							<textarea  id="y_val1" name="multivals" datasource="USERIDS" style="width:400px;height:10px"style="display:none" ></textarea>
						</td></tr>
					</table>
				</form>
			</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//查询提交方法
var unGrantedUrl = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/selectUnGrantedPerson.ajax";
var grantedUrl = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/selectGrantedPerson.ajax";
var saveRoleUserMapUrl = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/saveRoleUserMap.ajax";
var removeRoleUserMapUrl = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/removeRoleUserMap.ajax";
var syncRoleUserMapUrl = "<%=request.getContextPath()%>/sysmng/rolemng/RoleMngAction/synchronize.ajax";
$(function()
{
	$("#page_UnGrantedPersonsJglb").parent().height(document.documentElement.clientHeight - 200);
	$("#page_GrantedPersonsJglb").parent().height(document.documentElement.clientHeight - 200);
	$("#UnGrantedPersonsCxBtn").click(function(){
		var $f = $("#UnGrantedPersonFm");
		var sCondition = {};
    	sCondition = $("#UnGrantedPersonCx").combined() || {};
    	var sUrl = unGrantedUrl + "?roleId=" + $("#dia-roleId").val();
		doFormSubmit($f,sUrl,"UnGrantedPersonsCxBtn",1,sCondition,"UnGrantedPersonsJglb");
		$("#w_grantBar").show();
	});
	
	$("#GrantedPersonsCxBtn").click(function(){
		var $f = $("#GrantedPersonFm");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var sUrl = grantedUrl + "?roleId=" + $("#dia-roleId").val();
		doFormSubmit($f,sUrl,"GrantedPersonsCxBtn",1,sCondition,"GrantedPersonsJglb");
		$("#y_grantBar").show();
	});
	
	$("#w_grant").click(function(){
		if(!$("#w_val0").val()){ alertMsg.info("请选择人员。");return false;}
		var $f = $("#w_grantFm");
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		var url = saveRoleUserMapUrl + "?roleId=" + $("#dia-roleId").val();
		doNormalSubmit($f,url,"w_grant",sCondition,unGrantedCallBack);
	});
	
	$("#y_grant").click(function(){
		if(!$("#y_val0").val()){ alertMsg.info("请选择人员。");return false;}
		var $f = $("#y_grantFm");
		var sCondition = {};
		sCondition = $f.combined(1) || {};
		var url = removeRoleUserMapUrl + "?roleId=" + $("#dia-roleId").val();
		doNormalSubmit($f,url,"y_grant",sCondition,grantedCallBack);
	});
	
});

function unGrantedCallBack()
{
	var $f = $("#UnGrantedPersonsCx");
	var sCondition = {};
	sCondition = $("#UnGrantedPersonsCx").combined() || {};
	var sUrl = unGrantedUrl + "?roleId=" + $("#dia-roleId").val();
	doFormSubmit($f,sUrl,"UnGrantedPersonsCxBtn",1,sCondition,"UnGrantedPersonsJglb");
	//同步集群节点（业务功能不需要）
	var syncUrl = syncRoleUserMapUrl + "?type=4&roleId="+$("#dia-roleId").val();
	sendPost(syncUrl,"","","","false");
	return true;
}
function grantedCallBack()
{
	var $f = $("#GrantedPersonFm");
	var sCondition = {};
	sCondition = $("#GrantedPersonsCx").combined() || {};
	var sUrl = grantedUrl + "?roleId=" + $("#dia-roleId").val();
	doFormSubmit($f,sUrl,"GrantedPersonsCxBtn",1,sCondition,"GrantedPersonsJglb");
	//同步集群节点（业务功能不需要）
	var syncUrl = syncRoleUserMapUrl + "?type=4&roleId="+$("#dia-roleId").val();
	sendPost(syncUrl,"","","","false");
	return true;
}
function formatCheck($cell)
{
	var account = $cell.parent().find("td").eq(1).text();
	var s = "<input type='checkbox' name='persons' value='" + account + "' onclick=doCheckbox(this) />";
	return s;
}
function doCheckbox(checkbox)
{
	var $tr = $(checkbox).parent().parent().parent();
	var arr = [];
	arr.push($tr.attr("ACCOUNT"));
	arr.push($tr.attr("USER_ID"));
	$(checkbox).val($tr.attr("ACCOUNT"));
	var $tab = $tr;
	while($tab.get(0).tagName != "TABLE")
		$tab = $tab.parent();
	if($tab.attr("id").indexOf("GrantedPersonsJglb") == 0)
		multiSelected($(checkbox),arr,$("#y_grantFm"));
	else
		multiSelected($(checkbox),arr,$("#w_grantFm"));
}

</script>