<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String relationIds = request.getParameter("relationIds");
%>
<div id="dia-layout">
	<div class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-EditPurchaseAttr" class="editForm" >
	    
		<div align="left">
			<fieldset>
			<table class="editTable" id="tabEdit-EditPurchaseAttr">
				<tr>
				    <td><label>服务标识：</label></td>
			   		 	<td>
				    	<select type="text"  id="dia-se_identify" name="dia-se_identify" kind="dic" src="YXBS" datasource="SE_IDENTIFY" datatype="1,is_null,300" >	
				     		<option value="-1" selected>--</option>		 
				    	</select>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction";
var relationIds = "<%=relationIds%>";
$(function(){
		$("#dia-se_identify").val("<%=DicConstant.YXBS_01%>");
		$("#dia-se_identify").attr("code","<%=DicConstant.YXBS_01%>");
		$("#dia-se_identify").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-se_identify").find("option").text("有效");


	$("#btn-BatchUpdateSave").bind("click", function(event){alert(1);
		var $f = $("#fm-EditPurchaseAttr");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		sCondition = $("#fm-EditPurchaseAttr").combined(1) || {};
		var batchUpdateUrl = diaSaveAction + "/batchUpdateService.ajax?relationIds="+relationIds;
		doNormalSubmit($f,batchUpdateUrl,"btn-BatchUpdateSave",sCondition,diaUpdateCallBack);
	});
});

//批量更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
	 	$("#btn-cx").trigger("click");	
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