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
	<form method="post" id="fm-transAddress" class="editForm" >
	    <!-- 隐藏域 -->
		<input type="hidden" id="dia-addressId" name="dia-addressId" datasource="ADDRESS_ID" />
		<input type="hidden" id="dia-createUser" name="dia-createUser" datasource="CREATE_USER" /><!-- 更新使用 -->
		<input type="hidden" id="dia-createTime" name="dia-createTime" datasource="CREATE_TIME" /><!-- 更新使用 -->
		<input type="hidden" id="dia-addrType" name="dia-addrType" datasource="ADDR_TYPE" /><!-- 更新使用 -->
		
		<div align="left">
			<fieldset>
			<table class="editTable" id="tab-transAddressInfo">
				<tr>
					<td><label>渠道商名称：</label></td>
				    <td>
				    	<input type="text" id="dia-orgCode"  name="dia-orgCode" kind="dic" 
				    		src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND STATUS='100201' AND ORG_TYPE in ('200005','200006','200007')" 
				    		datasource="ORG_CODE" datatype="0,is_null,100" dicwidth="300"/>
				    	<input type="hidden" id="dia-orgName"  name="dia-orgName" datasource="ORG_NAME"  />
				    	<input type="hidden" id="dia-orgId"  name="dia-orgId" datasource="ORG_ID"  />
				    </td>
				    <td><label>联  系  人：</label></td>
				    <td>
				    	<input type="text" id="dia-linkMan"  name="dia-linkMan" datasource="LINK_MAN" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
				    <td><label>联系电话：</label></td>
				    <td>
				    	<input type="text" id="dia-phone"  name="dia-phone" datasource="PHONE" datatype="0,is_null,30" />
				    </td>
				    <td><label>移动电话：</label></td>
				    <td>
				    	<input type="text" id="dia-mobile"  name="dia-mobile" datasource="MOBILE" datatype="0,is_null,30" />
				    </td>
				</tr>
				<tr>
				    <td><label>传　　真：</label></td>
				    <td>
				    	<input type="text" id="dia-fax"  name="dia-fax" datasource="FAX" datatype="1,is_null,30" />
				    </td>
				    <td><label>邮　　编：</label></td>
				    <td>
				    	<input type="text" id="dia-zipCode"  name="dia-zipCode" datasource="ZIP_CODE" datatype="1,is_null,30" />
				    </td>
				</tr>
				<tr>
					<td><label>邮　　箱：</label></td>
					<td colspan="3">
						<input type="text" id="dia-eMail" style="width:88%;" name="dia-eMail" datasource="E_MAIL" datatype="1,is_email,30"/>
					</td>
				</tr>
				<tr>
					<!-- <td><label>省　  　份：</label></td>
				    <td>
				    	<input type="text" id="dia-provinceCode" name="dia-provinceCode" kind="dic" src="XZQH" filtercode="\d{2}0000$" 
				    		dicwidth="320" datasource="PROVINCE_CODE" datatype="0,is_null,30"/>
				    	<input type="hidden" id="dia-provinceName" name="dia-provinceName" datasource="PROVINCE_NAME"/>
				    </td> -->
				    <td><label>省　  　份：</label></td>
				    <td>
				    	<input type="text" id="dia-provinceCode" name="dia-provinceCode" kind="dic" dicwidth="320" datasource="PROVINCE_CODE" datatype="0,is_null,30"/>
				    	<input type="hidden" id="dia-provinceName" name="dia-provinceName" datasource="PROVINCE_NAME"/>
				    </td>
				    <td><label>城　　市：</label></td>
				    <td>
				    	<input type="text" id="dia-cityCode"  name="dia-cityCode" kind="dic" dicwidth="320" datasource="CITY_CODE" datatype="0,is_null,100" />
				    	<input type="hidden" id="dia-cityName" name="dia-cityName" datasource="CITY_NAME"/>
				    </td>
				</tr>
				<tr>
				    <td><label>区　  　县：</label></td>
				    <td>
				    	<input type="text" id="dia-countryCode" name="dia-countryCode" kind="dic" dicwidth="320" datasource="COUNTRY_CODE" datatype="0,is_null,100" />
				    	<input type="hidden" id="dia-countryName" name="dia-countryName" datasource="COUNTRY_NAME"/>
				    </td>
				    <td><label>有效标识：</label></td>
				    <td>
				    	<select class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,6" >
					    	<option value="-1" >--</option>
					    </select>
				    </td>
				</tr>
				<tr>
					<td><label>发运地址：</label></td>
					<td colspan="3">
						<textarea id="dia-address" style="width:89%;height:40px;" name="dia-address" datasource="ADDRESS"  datatype="0,is_null,1000"></textarea>
					</td>
				</tr>
<!--				<tr>-->
<!--				    <td><label>地址类型：</label></td>-->
<!--				    <td　 colspan = 3>-->
<!--				    	<select class="combox" id="dia-addrType" name="dia-addrType" kind="dic" src="JHDDLX" datasource="ADDR_TYPE" datatype="1,is_null,6" >-->
<!--					    	<option value="-1" >--</option>-->
<!--					    </select>-->
<!--				    </td>-->
<!--				</tr>-->
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
var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/TransAddressAction";
var diaAction = "<%=action%>";

$(function(){ 
	$("#dia-provinceCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '%0000' ORDER BY DM ");
	if(diaAction == 1)	//新增初始化
	{ 
		//默认显示有效标识
		$("#dia-status").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").attr("dic_code","<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").val("<%=DicConstant.YXBS_01%>");
		$("#dia-status").find("option").text("有效");
		
	} else {  //修改初始化
		var selectedRows = $("#tab-transAddressList").getSelectedRows();
		setEditValue("fm-transAddress",selectedRows[0].attr("rowdata"));
		
		//将表字典里查出的名称回显到修改页面代码对应的名称项中
		var rowdata=selectedRows[0].attr("rowdata");	
		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;
		else objXML = $.parseXML(rowdata);
		
		//渠道商名称回显
		var orgCode = getNodeValue(objXML, "ORG_CODE", 0);
		var orgName = getNodeValue(objXML, "ORG_NAME", 0);
		$("#dia-orgCode").val(orgName);
		$("#dia-orgCode").attr("code",orgCode);
		
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
		
		var $f = $("#fm-transAddress");
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm-transAddress").combined(1) || {};
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
		$("#tab-transAddressList").insertResult(res,0);
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
		var selectedIndex = $("#tab-transAddressList").getSelectedIndex();
		$("#tab-transAddressList").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
function afterDicItemClick(id,$row,selIndex){
	var ret = true;
	if(id =="dia-orgCode"){
		$("#dia-orgName").val($("#dia-orgCode").val());
		$("#dia-orgId").val($row.attr("ORG_ID"));
	}
	if(id == "dia-provinceCode")
	{
        $("#dia-provinceName").val($("#dia-provinceCode").val());
		$("#dia-cityCode").val("");
		$("#dia-cityCode").attr("code","");
		$("#dia-countryCode").val("");
		$("#dia-countryCode").attr("code","");
		var privinceCode = $("#"+id).attr("code").substr(0,2);
		$("#dia-cityCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+privinceCode+"%' AND LX='30' ");
		$("#dia-cityCode").attr("isreload","true");
	}
	if(id=="dia-cityCode")
	{
		$("#dia-cityName").val($("#dia-cityCode").val());
		$("#dia-countryCode").val("");
		$("#dia-countryCode").attr("code","");
		var cityCode = $("#"+id).attr("code").substr(0,4);
		$("#dia-countryCode").attr("src","T#TM_DIVISION:DM:JC:1=1 AND DM like '"+cityCode+"%' AND LX='40' ");
		$("#dia-countryCode").attr("isreload","true");
		$("#dia-countryCode").attr("dicwidth","300");
	}
    if(id=="dia-countryCode"){
        $("#dia-countryName").val($("#dia-countryCode").val());
    }
    return ret;
}
</script>