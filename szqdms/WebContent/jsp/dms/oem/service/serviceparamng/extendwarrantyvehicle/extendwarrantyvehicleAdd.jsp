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
		<form method="post" id="fm-extendWarrantyVehicleInfo" class="editForm" >
			<!-- 隐藏域 -->
			<input type="hidden" id="dia-relationId" name="dia-relationId" datasource="RELATION_ID" />
			<input type="hidden" id="dia-vehicleId" name="dia-vehicleId" datasource="VEHICLE_ID" />
		    <input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" />
		    <input type="hidden" id="dia-creatTime" name="dia-creatTime" datasource="CREATE_TIME" />
			<div align="left">
			<fieldset>
			<table class="editTable" id="tab-extendWarrantyVehicleInfo">
				<tr>
					<td><label>延保策略：</label></td>
					<td>
						<input type="text" id="dia-warrantyCode" name="dia-warrantyCode" datasource="WARRANTY_CODE" operation="like" kind="dic" src="T#SE_BA_EXTEND_WARRANTY:WARRANTY_CODE:WARRANTY_NAME{WARRANTY_ID}:STATUS=100201" datatype="0,is_null,30" />
						<input type="hidden" id="dia-warrantyId" name="dia-warrantyId" datasource="WARRANTY_ID"/>
						<input type="hidden" id="dia-warrantyName" name="dia-warrantyName" datasource="WARRANTY_NAME"/>
					</td>
                    <td><label>VIN：</label></td>
                    <td>
                        <input type="text" id="dia-vin" name="dia-vin" datasource="VIN"  hasBtn="true" readonly datatype="0,is_null,30" callFunction="openVin()"/>
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

var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/ExtendWarrantyVehicleMngAction";
var diaAction = "<%=action%>";

//初始化
$(function(){
	//绑定保存按钮
	$("#btn-save").bind("click", function(event){
		//获取需要提交的form对象
		var $f = $("#fm-extendWarrantyVehicleInfo");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-extendWarrantyVehicleInfo").combined(1) || {};
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
	
	//修改操作
	if(diaAction != "1")
	{
		//getSelectedRows():获取列表选中行对象，返回选中行数组
		var selectedRows = $("#tab-extendWarrantyVehicleList").getSelectedRows();
		setEditValue("fm-extendWarrantyVehicleInfo",selectedRows[0].attr("rowdata"));
		
		//将字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		//把延保名称赋给延保代码表示项		
		var warrantyCode=getNodeValue(objXML, "WARRANTY_CODE", 0);
		var warrantyName=getNodeValue(objXML, "WARRANTY_NAME", 0);
		$("#dia-warrantyCode").val(warrantyName);
		$("#dia-warrantyCode").attr("code",warrantyCode);
	}else
	{}
});

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
		//第二个参数0表示插入到第几行
		$("#tab-extendWarrantyVehicleList").insertResult(res,0);
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
		var selectedIndex = $("#tab-extendWarrantyVehicleList").getSelectedIndex();
		$("#tab-extendWarrantyVehicleList").updateResult(res,selectedIndex);
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
	//给车辆部位隐藏域赋值
	if(id=="dia-warrantyCode") 
	{
		$("#dia-warrantyName").val($("#"+id).val());
		$("#dia-warrantyId").val($row.attr("WARRANTY_ID"));
	}
	return true;
}

//弹出查询VIN界面
function openVin()
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/dms/oem/service/serviceparamng/extendwarrantyvehicle/extendwarrantyvehicleSel.jsp";	
	$.pdialog.open(url, "extendwarrantyvehicleSel", "VIN查询", options);				
}

//弹出框回调方法，将选定的VIN等信息带回输入框
function SelCallBack(obj)
{
	$("#dia-vehicleId").val($(obj).attr("VEHICLE_ID"));	//车辆主键
	$("#dia-vin").val($(obj).attr("VIN"));		//VIN
}
</script>