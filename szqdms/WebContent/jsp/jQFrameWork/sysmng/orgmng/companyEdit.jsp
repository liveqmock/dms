<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-companyInfo">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-companyInfo" class="editForm" >
			<!-- 隐藏域 -->
			<input type="hidden" id="dia-companyId" name="dia-companyId" datasource="COMPANY_ID" />
			<div align="left">
				<table class="editTable" id="tab-companyInfo">
					<!-- hidden域 -->
					<tr><td><label>公司类型：</label></td>
					    <td>
					    	<select type="text" id="dia-companyType" name="dia-companyType" kind="dic" src="<%=DicConstant.GSLX %>" datasource="COMPANY_TYPE" datatype="0,is_null,30">
					    		<option value="<%=DicConstant.GSLX_02%>">渠道公司</option>
					    	</select>
					    </td>
					</tr>
					<tr><td><label>公司代码：</label></td>
					    <td><input type="text" id="dia-code" name="dia-code" datasource="CODE" datatype="0,is_digit_letter,10" /></td>
					    <td><label>公司名称：</label></td>
					    <td><input type="text" id="dia-oname" name="dia-cname" datasource="CNAME" datatype="0,is_null,60" /></td>
					</tr>
					<tr>
						<td><label>公司简称：</label></td>
					    <td><input type="text" id="dia-sname" name="dia-sname" datasource="SNAME" datatype="0,is_null,30" /></td>
					    <td><label>联系方式：</label></td>
					    <td><input type="text" id="dia-contact" name="dia-contact" datasource="CONTACT" datatype="0,is_null,30" /></td>
					</tr>
					<tr>
						<td><label>法人姓名：</label></td>
					    <td><input type="text" id="dia-legalPerson" name="dia-legalPerson" datasource="LEGAL_PERSON" datatype="0,is_null,30" /></td>
					</tr>
					<tr>
					    <td><label>开票名称：</label></td>
					    <td colspan="3"><input type="text" style="width:87%;" id="dia-contact" name="dia-contact" datasource="INVOICE_NAME" datatype="0,is_null,60" /></td>
					</tr>
					<tr>
						<td><label>公司地址：</label></td>
					    <td colspan="3"><input type="text" style="width:87%;" id="dia-address" name="dia-address" datasource="ADDRESS" datatype="0,is_null,100" /></td>
					</tr>
					<tr>
					    <td><label>有效标识：</label></td>
					    <td>
						    <select type="text" class="combox" id="dia-status" name="dia-status" kind="dic" src="<%=DicConstant.YXBS %>" datasource="STATUS" datatype="0,is_null,10" >
						    	<option value="<%=Constant.YXBS_01 %>" selected>有效</option>
						    </select>
					    </td>
					</tr>
				</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-diaSave">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>	
<script type="text/javascript">
var action = "<%=action%>";
/**
 * 保存url
 */
var diaSaveAction = "<%=request.getContextPath()%>/sysmng/orgmng/CompanyMngAction";

//初始化
$(function(){
	//修改操作
	if(action != "1")
	{
		var selectedRows = $("#tab-companyList").getSelectedRows();
		setEditValue("fm-companyInfo",selectedRows[0].attr("rowdata"));
		$("#dia-code").attr("readonly",true);
	}
	
	$("#btn-diaSave").bind("click", function(event){
		doDiaSave();
	});
});

//点击字典回调方法
function afterDicItemClick(id)
{
	var ret =  true;
	switch(id)
	{
		
	}
	return ret;
}

//保存
function doDiaSave()
{
	var $f = $("#fm-companyInfo");
	if (submitForm($f) == false) return false;
	var sCondition = {};
		sCondition = $("#fm-companyInfo").combined(1) || {};
	if(action == 1)	
	{
		var url = diaSaveAction + "/insert.ajax";
		doNormalSubmit($f,url,"btn-diaSave",sCondition,insertCallBack);
	}else
	{
		var url = diaSaveAction + "/update.ajax";
		doNormalSubmit($f,url,"btn-diaSave",sCondition,updateCallBack);
	}
	
}
//回调函数
function insertCallBack(res)
{
	try
	{
		$("#tab-companyList").insertResult(res,0);
		//重置输入框内容	
		resetEditValue("fm-companyInfo");
		//设置companyId主键域
		$("#dia-companyId").val(getNodeText(res.getElementsByTagName("COMPANY_ID").item(0)));
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=1&companyId="+$("#dia-companyId").val();
		sendPost(syncUrl,"","","","false");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//回调函数
function updateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-companyList").getSelectedIndex();
		$("#tab-companyList").updateResult(res,selectedIndex);
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=2&companyId="+$("#dia-companyId").val();
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	
	return true;
}
</script>