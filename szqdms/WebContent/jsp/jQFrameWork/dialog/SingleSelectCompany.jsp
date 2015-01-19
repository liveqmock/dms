<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
	 Title:公司选择
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
		<form method="post" id="SingleSelectCompany">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="SingleSelectCompanyCx">
				<tr>
				    <td><label>公司名称：</label></td>
				    <td><input type="text" id="SingleSelectCompanyCxGsmc" name="SingleSelectCompanyCxGsmc" datasource="GSMC" operation="like" datatype="1,is_null,60" value=""/></td>
				</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="SingleSelectCompanyCxBtn">查询</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
		<br/>
	</div>
	<div class="pageContent">
		<div id="page_SingleSelectCompanyJglb" style="height:280px;">
			<table style="display:none" width="100%"  layoutH="100" id="SingleSelectCompanyJglb" name="tablist" ref="page_SingleSelectCompanyJglb" refQuery="SingleSelectCompanyCx" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="GSDM" >公司代码</th>
							<th fieldname="GSMC" >公司全称</th>
							<th fieldname="GSJC" >公司简称</th>
							<th fieldname="GSDZ" >公司地址</th>
							<th fieldname="LXFS" >联系方式</th>
							<th fieldname="FRXM" >法人姓名</th>
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
var compnayUrl = "<%=request.getContextPath()%>/OrgDept/OrgCompanyAction.do?method=selectCompany";
$(function()
{
	$("#SingleSelectCompanyCxBtn").click(function(){
		var $f = $("#SingleSelectCompanyCx");
		var sCondition = {};
    	sCondition = $("#SingleSelectCompanyCx").combined() || {};
		doFormSubmit($f.eq(0),compnayUrl,"SingleSelectCompanyCxBtn",1,sCondition,"SingleSelectCompanyJglb");
	});
	
});

function doOk(rowobj)
{
	var lookupId = $.pdialog._current.data("id");
	var gsid = rowobj.getAttribute("GSID");
	var gsmc = rowobj.getAttribute("GSMC");
	$("#"+lookupId).val(gsmc);
	$("#"+lookupId).attr("code",gsid);
	try
	{
		gsCallBack(rowobj);	
	}catch(e){}
	$.pdialog.closeCurrent();
}
</script>