<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="SelectPerson">
		<!-- 定义查询条件 -->
		<input type="hidden" id="yxbs" name="yxbs" datasource="FLAG" value="1"></input>
		<div class="searchBar" align="left" >
			<table class="searchContent" id="SelectPersonCx">
				<tr><td><label>用户账号：</label></td>
						    <td><input type="text" id="selectYhzh" name="selectYhzh" datasource="ACCOUNT" datatype="1,is_null,30" operation="like" value=""/></td>
							<td><label>姓名：</label></td>
						    <td><input type="text" id="selectYhmc" name="selectYhmc" datasource="PERSON_NAME" operation="like" datatype="1,is_null,30" value=""/></td>
						</tr>
						<tr>
						    <td><label>所属组织：</label></td>
						    <td><input type="text" id="selectSsbm"  name="selectSsbm" datasource="DEPARTMENT"  kind="dic" src="ZZJG"  datatype="1,is_null,30" value=""/>
						    </td>
						</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="SelectPersonCxBtn">查询</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
		<br/>
	</div>
	<div class="pageContent">
		<div id="page_SelectPersonJglb" style="height:280px;">
			<table style="display:none" width="100%"  layoutH="100" id="SelectPersonJglb" name="tablist" ref="page_SelectPersonJglb" refQuery="SelectPersonCx" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ROWNUMS" style="width:28px;">序号</th>
							<th fieldname="ACCOUNT">用户账号</th>
							<th fieldname="PERSON_NAME">姓名</th>
							<th fieldname="SEX" >性别</th>
							<th fieldname="COMPANY_ID" >所属公司</th>
							<th fieldname="DEPARTMENT" >所属部门</th>
							<th fieldname="PERSON_KIND" >用户类型</th>
							<th type="link" title="操作" action="doOk" class="btnSelect">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//查询提交方法
var selectPersonUrl = "<%=request.getContextPath()%>/CommonDialogs/DialogManager.do?method=selectPerson";
$(function()
{
	$("#SelectPersonCxBtn").click(function(){
		var $f = $("#SelectPerson");
		var sCondition = {};
    	sCondition = $("#SelectPerson").combined() || {};
		doFormSubmit($f.eq(0),selectPersonUrl,"SelectPersonCxBtn",1,sCondition,"SelectPersonJglb");
	});
	
});

function doOk(rowobj)
{
	var lookupId = $.pdialog._current.data("id");
	var account = rowobj.getAttribute("ACCOUNT");
	var name = rowobj.getAttribute("NAME");
	$("#"+lookupId).val(name);
	$("#"+lookupId).attr("code",account);
	try
	{
		personCallBack(rowobj);	
	}catch(e){}
	$.pdialog.closeCurrent();
}
</script>