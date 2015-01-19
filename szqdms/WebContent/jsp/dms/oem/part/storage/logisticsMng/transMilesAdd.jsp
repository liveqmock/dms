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
	<form method="post" id="fm-transMiles" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-milesId" name="dia-milesId" datasource="MILES_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-transMilesInfo">
				<tr>
				    <td><label>出发地点：</label></td>
				    <td>
				    	<input type="text" id="dia-birthlandName"  name="dia-birthlandName" datasource="BIRTHLAND_NAME" datatype="0,is_null,60" />
				    	<input type="hidden" id="dia-birthlandCode"  name="dia-birthlandCode" datasource="BIRTHLAND_CODE" />
				    </td>
				    <td><label>省　　份：</label></td>
				    <td>
				    	<input type="text" id="dia-provinceCode" name="dia-provinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" 
				    		dicwidth="320" datasource="PROVINCE_CODE" datatype="0,is_null,30"/>
				    	<input type="hidden" id="dia-provinceName" name="dia-provinceName" datasource="PROVINCE_NAME"/>
				    </td>
				</tr>
				<tr>
				    <td><label>城　　市：</label></td>
				    <td>
				    	<input type="text" id="dia-cityCode"  name="dia-cityCode" kind="dic" dicwidth="320" datasource="CITY_CODE" datatype="0,is_null,30" />
				    	<input type="hidden" id="dia-cityName" name="dia-cityName" datasource="CITY_NAME"/>
				    </td>
				    <td><label>区　　县：</label></td>
				    <td>
				    	<input type="text" id="dia-countryCode" name="dia-countryCode" kind="dic" dicwidth="320" datasource="COUNTRY_CODE" datatype="0,is_null,30" />
				    	<input type="hidden" id="dia-countryName" name="dia-countryName" datasource="COUNTRY_NAME"/>
				    </td>
				</tr>
				<tr>
					<td><label>详细地址：</label></td>
					<td colspan="3">
						<input type="text" id="dia-address" style="width:88%;" name="dia-address" datasource="ADDRESS"  datatype="1,is_null,300" />
					</td>
				</tr>
				<tr>
				    <td><label>运输里程：</label></td>
				    <td>
				    	<input type="text" id="dia-transMiles"  name="dia-transMiles" datasource="TRANS_MILES" datatype="0,is_null,30" />
				    </td>
				    <td><label>运输单价：</label></td>
				    <td><input type="text" id="dia-unitPrice"  name="dia-unitPrice" datasource="UNIT_PRICE" datatype="0,is_null,30" /></td>
				</tr>
				<tr>
					<td><label>有 效 标 识：</label></td>
					<td colspan="3">
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" >
					    	<option value="-1" >----</option>
					    </select>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/TransMilesAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 
		//默认显示有效标识
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");
		
		//出发地默认为陕西省西安市
		$("#dia-birthlandCode").val("610100");
		$("#dia-birthlandName").val("陕西省西安市");
		$("#dia-birthlandName").attr("readonly", true);

	} else {  //修改初始化
		var selectedRows = $("#tab-transMilesList").getSelectedRows();
		setEditValue("fm-transMiles",selectedRows[0].attr("rowdata"));
		
		//将表字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		//仓库名称回显
		var warehouseId = getNodeValue(objXML, "WAREHOUSE_ID", 0);
		var warehouseCode = getNodeValue(objXML, "WAREHOUSE_CODE", 0);
		var warehouseName = getNodeValue(objXML, "WAREHOUSE_NAME", 0);
		$("#dia-warehouseCode").val(warehouseName);
		$("#dia-warehouseCode").attr("code",warehouseCode);
		
		//省市名称回显
		var provinceCode = getNodeValue(objXML, "PROVINCE_CODE", 0);
		var provinceName = getNodeValue(objXML, "PROVINCE_NAME", 0);
		$("#dia-provinceCode").val(provinceName);
		$("#dia-provinceCode").attr("code",provinceCode);
	}
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-transMiles");
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
		$("#tab-transMilesList").insertResult(res,0);
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
		var selectedIndex = $("#tab-transMilesList").getSelectedIndex();
		$("#tab-transMilesList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>