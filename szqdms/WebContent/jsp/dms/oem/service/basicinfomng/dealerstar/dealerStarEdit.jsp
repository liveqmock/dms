<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="fm-dealerStarInfo" class="editForm" >
			<!-- 隐藏域 -->
		    <input type="hidden" id="dia-dealerId" name="dia-dealerId" datasource="DEALER_ID" />
		    <input type="hidden" id="dia-oldDealerStar" name="dia-oldDealerStar" datasource="OLD_DEALER_STAR"/>
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-dealerStarInfo">
				<tr>
					<td><label>服务商代码：</label></td>
					<td><input type="text" id="dia-dealerCode" name="dia-dealerCode" datasource="DEALER_CODE" datatype="0,is_digit_letter,30" /></td>
					<td><label>服务商名称：</label></td>
					<td><input type="text" id="dia-dealerName" name="dia-dealerName" datasource="DEALER_NAME" datatype="0,is_null,300"  /></td>
				</tr>
				<tr>
					<td><label>星级：</label></td>
					<td><input type="text"  id="dia-dealerStar" name="dia-dealerStar" datasource="DEALER_STAR" operation="like" kind="dic" src="T#USER_PARA_CONFIGURE:PARAKEY:PARANAME:APPTYPE='2003'" datatype="0,is_null,30" /> 
						<input type="hidden" id="dia-paraName" name="dia-paraName" datasource="PARANAME"/>	
					</td>
				</tr>		   			 
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>	
</div>
<script type="text/javascript">


var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/DealerStarMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-dealerStarInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-dealerStarInfo").combined(1) || {};

			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
	});
	
	//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-dealerStarList").getSelectedRows();
		setEditValue("fm-dealerStarInfo",selectedRows[0].attr("rowdata"));

		$("#dia-dealerCode").attr("readonly",true);
		$("#dia-dealerName").attr("readonly",true);
		
		////////////////////////////////////////////////
		//将字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		//把星级名称赋给星级代码表示项		
		var dealerStar=getNodeValue(objXML, "DEALER_STAR", 0);
		var paraName=getNodeValue(objXML, "PARANAME", 0);

		$("#dia-dealerStar").val(paraName);
		$("#dia-dealerStar").attr("code",dealerStar);
		
		//标示修改前星级代码
		$("#dia-oldDealerStar").val(dealerStar);
	}else{}
});

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-dealerStarList").getSelectedIndex();
		$("#tab-dealerStarList").updateResult(res,selectedIndex);
		//关闭当前窗口
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//给隐藏域赋值
function afterDicItemClick(id, $row, selIndex) 
{   
	//给星级名称隐藏域赋值 
	if(id=="dia-dealerStar") 
	{
		$("#dia-paraName").val($("#"+id).val());
	}
	return true;
}
</script>