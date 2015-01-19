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
	<form method="post" id="fm-serviceDc" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-relationId" name="dia-relationId" datasource="RELATION_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-serviceDcInfo">
				<tr>
					<td><label>配　送　中　心：</label></td>
				    <td>
				    	<input type="text" id="dia-dcName"  name="dia-dcName" datasource="DC_NAME" 
				    		datatype="0,is_null,100" hasBtn="true" readonly callFunction="openPurchase(1)" />
				    	<input type="hidden" id="dia-dcCode"  name="dia-dcCode" datasource="DC_CODE" />
				    	<input type="hidden" id="dia-dcId"  name="dia-dcId" datasource="DC_ID"  />
				    </td>
				</tr>
				<tr>
					<td><label>渠　　道　　商：</label></td>
				    <td>
				    	<input type="text" id="dia-orgName"  name="dia-orgName" datasource="ORG_NAME" 
				    		datatype="0,is_null,100" hasBtn="true" readonly callFunction="openPurchase(2)" />
				    	<input type="hidden" id="dia-orgCode"  name="dia-orgCode" datasource="ORG_CODE"  />
				    	<input type="hidden" id="dia-orgId"  name="dia-orgId" datasource="ORG_ID"  />
				    </td>
				</tr>
				<tr>
					<td><label>是否默认配送中心：</label></td>
				    <td>
				    	<select class="combox" id="dia-ifDefault" name="dia-ifDefault" kind="dic" src="SF" datasource="IF_DEFAULT" datatype="1,is_null,6" >
					    	<option value="-1" >--</option>
					    </select>
					    <input type="hidden" id="dia-oldIfDefault"  name="dia-oldIfDefault" datasource="OLD_IF_DEFAULT"  />
				    </td>
				</tr>
				<tr>
					<td><label>有　效　标　识：</label></td>
				    <td>
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" >
					    	<option value="-1" >--</option>
					    </select>
					    <input type="hidden" id="dia-oldStatus"  name="dia-oldStatus" datasource="OLD_STATUS"  />
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/ServiceDcAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 
		//默认显示有效标识
		$("#dia-status").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").attr("dic_code","<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");
		
	} else {  //修改初始化
		var selectedRows = $("#tab-serviceDcList").getSelectedRows();
		setEditValue("fm-serviceDc",selectedRows[0].attr("rowdata"));
		
		$("#dia-dcName").removeAttr("hasBtn");
		$("#dia-dcName").removeAttr("callFunction");
		$("#dia-dcName").attr("datatype", "1,is_null,100");
		
		$("#dia-oldStatus").val($("#dia-status").val());
	}
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-serviceDc");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-serviceDc").combined(1) || {};
		if(diaAction == 1)	//插入动作
		{   
			var addUrl = diaSaveAction + "/insert.ajax";
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	});
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-serviceDcList").insertResult(res,0);
		//$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//更新回调函数
function diaUpdateCallBack(res)
{
	try
	{   
		var selectedIndex = $("#tab-serviceDcList").getSelectedIndex();
		//alert(res.xml);
		$("#tab-serviceDcList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//配送中心放大镜事件
function openPurchase(flag)
{
	var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/dms/oem/part/basicInfo/serviceDc/serviceDcSel.jsp?flag="+flag;	
	$.pdialog.open(url, "serviceDcSel", "配送中心查询", options);		
}

//放大镜回调
function SelCallBack(obj,flag)
{
	if(flag=="1"){
		$("#dia-dcId").val($(obj).attr("ORG_ID"));		//配送中心主键
		$("#dia-dcCode").val($(obj).attr("CODE"));		//配送中心代码
		$("#dia-dcName").val($(obj).attr("ONAME"));		//配送中心名称
	}
	else{
		$("#dia-orgId").val($(obj).attr("ORG_ID"));		//渠道商主键
		$("#dia-orgCode").val($(obj).attr("CODE"));		//渠道商代码
		$("#dia-orgName").val($(obj).attr("ONAME"));	//渠道商名称
	}
}

</script>