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
	<form method="post" id="fm-driver" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-driverId" name="dia-driverId" datasource="DRIVER_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-driverInfo">
				<tr>
					<td><label>承运商名称：</label></td>
				    <td>
				    	<input type="text" id="dia-carrierCode"  name="dia-carrierCode" kind="dic" 
				    		src="T#PT_BA_CARRIER:CARRIER_CODE:CARRIER_NAME{CARRIER_ID}:1=1 AND STATUS='100201' " 
				    		datasource="CARRIER_CODE" datatype="0,is_null,30" />
				    	<input type="hidden" id="dia-carrierName"  name="dia-carrierName" datasource="CARRIER_NAME" />
				    	<input type="hidden" id="dia-carrierId"  name="dia-carrierId" datasource="CARRIER_ID" />
				    </td>
				    <td><label>司机姓名：</label></td>
				    <td>
				    	<input type="text" id="dia-driverName"  name="dia-driverName" datasource="DRIVER_NAME" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
					<td><label>身 份 证 号：</label></td>
				    <td>
				    	<input type="text" id="dia-driverNO"  name="dia-driverNO" datasource="DRIVER_NO" datatype="0,is_null,30" />
				    </td>
				    <td><label>性　　别：</label></td>
				    <td>
				    	<select class="combox" id="dia-sex" name="dia-sex" kind="dic" src="XB" datasource="SEX" datatype="0,is_null,6" >
					    	<option value="" >----</option>
					    </select>
				    </td>
				</tr>
				<tr>
				    <td><label>固 定 电 话：</label></td>
				    <td>
				    	<input type="text" id="dia-fixedLine"  name="dia-fixedLine" datasource="FIXED_LINE" datatype="0,is_null,30" />
				    </td>
				    <td><label>手　　机：</label></td>
				    <td>
				    	<input type="text" id="dia-phone"  name="dia-phone" datasource="PHONE" datatype="0,is_phone,30" />
				    </td>
				</tr>
				<tr>
				    <td><label>有 效 标 识：</label></td>
				    <td colspan="3">
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,6" >
					    	<option value="-1" >----</option>
					    </select>
				    </td>
				</tr>
				<tr>
					<td><label>邮　　　箱：</label></td>
					<td colspan="3">
						<input type="text" id="dia-email" style="width:88%;" name="dia-email" datasource="EMAIL"  datatype="1,is_email,100" />
					</td>
				</tr>
				<tr>
					<td><label>住　　　址：</label></td>
					<td colspan="3">
						<textarea id="dia-address" style="width:88%;height:40px;" name="dia-address" datasource="ADDRESS"  datatype="1,is_null,300"></textarea>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/DriverAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 
		//默认显示有效标识
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");

	} else {  //修改初始化
		var selectedRows = $("#tab-driverList").getSelectedRows();
		setEditValue("fm-driver",selectedRows[0].attr("rowdata"));
		
		//将表字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		//省份名称回显
		var carrierCode = getNodeValue(objXML, "CARRIER_CODE", 0);
		var carrierName = getNodeValue(objXML, "CARRIER_NAME", 0);
		$("#dia-carrierCode").val(carrierName);
		$("#dia-carrierCode").attr("code",carrierCode);
	}
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-driver");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $f.combined(1) || {};
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
		$("#tab-driverList").insertResult(res,0);
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
		var selectedIndex = $("#tab-driverList").getSelectedIndex();
		$("#tab-driverList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>