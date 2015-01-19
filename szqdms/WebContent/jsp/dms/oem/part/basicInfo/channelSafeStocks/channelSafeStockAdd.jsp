<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.dms.common.DicConstant"%>
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
	<form method="post" id="fm-channelSafeStock" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-saftyId" name="dia-saftyId" datasource="SAFTY_ID" />
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-channelSafeStockInfo">
				<tr>
					<td><label>渠道商名称：</label></td>
				    <td>
				    	<input type="text" id="dia-orgName"  name="dia-orgName" datasource="ORG_NAME" datatype="1,is_null,100" />
				    	<input type="hidden" id="dia-orgCode"  name="dia-orgCode" datasource="ORG_CODE"  />
				    	<input type="hidden" id="dia-orgId"  name="dia-orgId" datasource="ORG_ID"  />
				    	<input type="hidden" id="dia-status"  name="dia-status" datasource="STATUS"  />
				    </td>
					<td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="dia-partName"  name="dia-partName" datasource="PART_NAME" datatype="1,is_null,300" />
				    	<input type="hidden" id="dia-partCode"  name="dia-partCode" datasource="PART_CODE"  />
				    	<input type="hidden" id="dia-partId"  name="dia-partId" datasource="PART_ID"  />
				    </td>
				</tr>
				<tr>
					<td><label>库存下限：</label></td>
				    <td>
				    	<input type="text" id="dia-lowerLimit"  name="dia-lowerLimit"  value="" datasource="LOWER_LIMIT" datatype="0,is_null,10" />
				    	<input type="hidden" id="dia-addLowerLimit"  name="dia-addLowerLimit" datasource="ADD_LOWER_LIMIT"  />
				    </td>
					<td><label>库存上限：</label></td>
				    <td>
				    	<input type="text" id="dia-upperLimit"  name="dia-upperLimit" datasource="UPPER_LIMIT" datatype="0,is_null,10" />
				    	<input type="hidden" id="dia-addUpperLimit"  name="dia-addUpperLimit" datasource="ADD_UPPER_LIMIT"  />
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
/**
 * 保存样例
 */
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/ChannelSafeStockAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 

	} else {  //修改初始化
		var selectedRows = $("#tab-channelSafeStockList").getSelectedRows();
		setEditValue("fm-channelSafeStock",selectedRows[0].attr("rowdata"));
		
		//查看是新增还是修改
		var lowerLimitValue = $("#dia-lowerLimit").val();
		var upperLimitValue = $("#dia-upperLimit").val();
		$("#dia-addLowerLimit").val(lowerLimitValue);
		$("#dia-addUpperLimit").val(upperLimitValue);
		
		//仓库名称  和 配件名称不能改变
		$("#dia-orgName").attr("readonly", true);
		$("#dia-partName").attr("readonly", true);
	}
})

$(function(){

	$("#btn-save").bind("click", function(event){
		var $f = $("#fm-channelSafeStock");
		if (submitForm($f) == false) return false;
		
		var lower = $("#dia-lowerLimit").val();
		var upper = $("#dia-upperLimit").val();
		if (parseInt(lower) > parseInt(upper))
		{
			alert("库存下限不能大于库存上限！");
			return false;
		}
		
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $f.combined(1) || {};
		if(diaAction == 1)	//插入动作
		{   
			
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
});

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
		var selectedIndex = $("#tab-channelSafeStockList").getSelectedIndex();
		$("#tab-channelSafeStockList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>