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
	<form method="post" id="fm-carrier" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-carrierId" name="dia-carrierId" datasource="CARRIER_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-carrierInfo">
				<tr>
					<td><label>承运商代码：</label></td>
				    <td>
				    	<input type="text" id="dia-carrierCode"  name="dia-carrierCode" datasource="CARRIER_CODE" datatype="0,is_digit_letter,30" />
				    </td>
				    <td><label>承运商名称：</label></td>
				    <td>
				    	<input type="text" id="dia-carrierName"  name="dia-carrierName" datasource="CARRIER_NAME" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
				    <td><label>联　系　人：</label></td>
				    <td>
				    	<input type="text" id="dia-linkMan"  name="dia-linkMan" datasource="LINK_MAN" datatype="0,is_null,30" />
				    </td>
				    <td><label>固 定 电 话：</label></td>
				    <td>
				    	<input type="text" id="dia-fixedLine"  name="dia-fixedLine" datasource="FIXED_LINE" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
				    <td><label>有 效 标 识：</label></td>
				    <td>
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" >
					    	<option value="-1" >----</option>
					    </select>
				    </td>
				    <td><label>手　　　机：</label></td>
				    <td>
				    	<input type="text" id="dia-phone"  name="dia-phone" datasource="PHONE" datatype="0,is_phone,30" />
				    </td>
				</tr>
				<tr>
				    <td><label>是 否 军 品：</label></td>
				    <td colspan="3">
				    	<select class="combox" id="dia-ifArmy" name="dia-ifArmy" kind="dic" src="SF" datasource="IF_ARMY" datatype="0,is_null,6" >
					    	<option value="-1" >----</option>
					    </select>
				    </td>
				</tr>
				<tr>
					<td><label>邮　　　箱：</label></td>
					<td colspan="3">
						<input type="text" id="dia-email" style="width:88%;" name="dia-email" datasource="EMAIL"  datatype="0,is_email,100" />
					</td>
				</tr>
				<tr>
					<td><label>省　　　份：</label></td>
				    <td>
				    	<input type="text" id="dia-provinceCode" name="dia-provinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" 
				    		dicwidth="320" datasource="PROVINCE_CODE" datatype="0,is_null,30"/>
				    	<input type="hidden" id="dia-provinceName" name="dia-provinceName" datasource="PROVINCE_NAME"/>
				    </td>
				    <td><label>城　　　市：</label></td>
				    <td>
				    	<input type="text" id="dia-cityCode"  name="dia-cityCode" kind="dic" dicwidth="320" datasource="CITY_CODE" datatype="0,is_null,100" />
				    	<input type="hidden" id="dia-cityName" name="dia-cityName" datasource="CITY_NAME"/>
				    </td>
				</tr>
				<tr>
				    <td><label>区　　　县：</label></td>
				    <td colspan="3">
				    	<input type="text" id="dia-countryCode" name="dia-countryCode" kind="dic" dicwidth="320" datasource="COUNTRY_CODE" datatype="0,is_null,100" />
				    	<input type="hidden" id="dia-countryName" name="dia-countryName" datasource="COUNTRY_NAME"/>
				    </td>
				</tr>
				<tr>
					<td><label>详 细 地 址：</label></td>
					<td colspan="3">
						<input type="text" id="dia-address" style="width:88%;" name="dia-address" datasource="ADDRESS"  datatype="0,is_null,300" />
					</td>
				</tr>
				<tr>
					<td><label>备　　　注：</label></td>
					<td colspan="3">
						<textarea id="dia-remarks" style="width:89%;height:40px;" name="dia-remarks" datasource="REMARKS"  datatype="1,is_null,1000"></textarea>
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
var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/logisticInfo/CarrierAction";
var diaAction = "<%=action%>";

$(function(){ 
	if(diaAction == 1)	//新增初始化
	{ 
		//默认显示有效标识
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");

	} else {  //修改初始化
		var selectedRows = $("#tab-carrierList").getSelectedRows();
		setEditValue("fm-carrier",selectedRows[0].attr("rowdata"));
		
		$("#dia-carrierCode").attr("readonly", true);
		
		//将表字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		//省份名称回显
		var provinceCode = getNodeValue(objXML, "PROVINCE_CODE", 0);
		var provinceName = getNodeValue(objXML, "PROVINCE_NAME", 0);
		$("#dia-provinceCode").val(provinceName);
		$("#dia-provinceCode").attr("code",provinceCode);
		
		//城市名称回显
		var cityCode = getNodeValue(objXML, "CITY_CODE", 0);
		var cityName = getNodeValue(objXML, "CITY_NAME", 0);
		$("#dia-cityCode").val(cityName);
		$("#dia-cityCode").attr("code",cityCode);
		
		//区县名称回显
		var countryCode = getNodeValue(objXML, "COUNTRY_CODE", 0);
		var countryName = getNodeValue(objXML, "COUNTRY_NAME", 0);
		$("#dia-countryCode").val(countryName);
		$("#dia-countryCode").attr("code",countryCode);
	}
})

$(function(){

	$("#btn-save").bind("click", function(event){
		
		var $f = $("#fm-carrier");
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
		$("#tab-carrierList").insertResult(res,0);
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
		var selectedIndex = $("#tab-carrierList").getSelectedIndex();
		$("#tab-carrierList").updateResult(res,selectedIndex);
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
	if(id=="dia-provinceCode"){
		$("#dia-provinceName").val($("#dia-provinceCode").val());
		$("#dia-cityCode").attr("src","XZQH");
		$("#dia-cityCode").attr("isreload","true");
		$("#dia-cityCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
		return true;
	}
	
	if(id=="dia-cityCode"){
		$("#dia-cityName").val($("#dia-cityCode").val());
		$("#dia-countryCode").attr("src","XZQH");
		$("#dia-countryCode").attr("isreload","true");
		$("#dia-countryCode").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,4)+"([0-9][1-9]|[1-9]0)$");
		return true;
	}
	
	if(id=="dia-countryCode"){
		$("#dia-countryName").val($("#dia-countryCode").val());
		return true;
	}
	
	return true;
}

</script>