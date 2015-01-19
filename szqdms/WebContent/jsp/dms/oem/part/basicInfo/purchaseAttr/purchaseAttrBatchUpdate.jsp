<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String pchAttributeIds = request.getParameter("pchAttributeIds");
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-EditPurchaseAttr" class="editForm" >
	    
		<div align="left">
			<fieldset>
			<table class="editTable" id="tabEdit-EditPurchaseAttr">
				<tr>
				    <td><label>采购员姓名：</label></td>
				    <td><input type="text" id="diaBU-userAccount"  name="diaBU-userAccount" kind="dic" 
				    src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000065' AND STATUS=100201" datasource="USER_ACCOUNT" datatype="0,is_null,32" />
				    <input type="hidden" id="diaBU-personName"  name="diaBU-personName" datasource="PERSON_NAME"  />
				    </td>
				</tr>
			</table>
			</fieldset>
		</div>
	</form>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-BatchUpdateSave">保&nbsp;&nbsp;存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
	</div>
</div>
<script type="text/javascript">
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PchAttributeAction";
var pchAttributeIds = "<%=pchAttributeIds%>";
$(function(){

	$("#btn-BatchUpdateSave").bind("click", function(event){
		var $f = $("#fm-EditPurchaseAttr");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#fm-EditPurchaseAttr").combined(1) || {};
		var batchUpdateUrl = diaSaveAction + "/batchUpdate.ajax?pchAttributeIds="+pchAttributeIds;
		doNormalSubmit($f,batchUpdateUrl,"btn-BatchUpdateSave",sCondition,diaUpdateCallBack);
	});
});

//批量更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
	 	$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="diaBU-userAccount")
	{
		$("#diaBU-personName").val($("#"+id).val());
	}
	return true;
}
</script>