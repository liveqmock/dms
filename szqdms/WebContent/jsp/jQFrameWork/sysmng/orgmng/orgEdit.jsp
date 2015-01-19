<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-orgInfo">
	<div class="page">
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-orgInfo" class="editForm" >
			<!-- 隐藏域 -->
			<input type="hidden" id="dia-orgId" name="dia-orgId" datasource="ORG_ID" />
			<div align="left">
				<table class="editTable" id="tab-orgInfo">
					<!-- hidden域 -->
					<tr>
						<td><label>组织级别：</label></td>
					    <td>
					    	<select type="text" id="dia-levelCode" name="dia-levelCode" kind="dic" src="<%=DicConstant.JGJB %>" datasource="LEVEL_CODE" datatype="0,is_null,10" >
						    	<option value="-1" selected>--</option>
						    </select>
						</td>
						<td><label>业务类型：</label></td>
					    <td>
					    	<select type="text" id="dia-levelCode" name="dia-levelCode" kind="dic" src="<%=DicConstant.YWLX %>" datasource="BUS_TYPE" datatype="0,is_null,10" >
						    	<option value="<%=DicConstant.YWLX_01 %>" selected>配件业务</option>
						    </select>
						</td>
					</tr>
					<tr>
						<td><label>所属公司：</label></td>
					    <td><input type="text" id="dia-companyId" name="dia-companyId" datasource="COMPANY_ID" datatype="0,is_null,30" kind="dic" dicwidth="280"/></td>
					    <td><label>组织类别：</label></td>
					    <td><input type="text" id="dia-orgType" name="dia-orgType" kind="dic" datasource="ORG_TYPE" datatype="0,is_null,30" /></td>
					</tr>
					<tr><td><label>组织代码：</label></td>
					    <td><input type="text" id="dia-code" name="dia-code" datasource="CODE" datatype="0,is_digit_letter,30" /></td>
					    <td><label>组织名称：</label></td>
					    <td><input type="text" id="dia-oname" name="dia-oname" datasource="ONAME" datatype="0,is_null,100" />
					    </td>
					</tr>
					<tr>
						<td><label>组织简称：</label></td>
					    <td><input type="text" id="dia-sName" name="dia-sName" datasource="SNAME" datatype="0,is_null,30" /></td>
						<td><label>上级组织：</label></td>
					    <td><input type="text" id="dia-pId" name="dia-pId" isreload="true" datasource="PID" kind="dic" src="ZZJG" tree="false" dicwidth="300" datatype="0,is_null,30" /></td>
					</tr>
					<tr>
						<td><label>ERP_ID：</label></td>
					    <td><input type="text" id="dia-erpId" name="dia-erpId" datasource="ERP_ID" datatype="1,is_null,30" /></td>
					    <td><label>所属行政区划：</label></td>
					    <td><input type="text" id="dia-dCode" name="dia-dCode" datasource="DIVISION_CODE" kind="dic" src="T#TM_DIVISION:DM:JC" dicwidth='300' datatype="1,is_null,30" value=""/></td>
					</tr>
					<tr>
					    <td><label>有效标识：</label></td>
					    <td>
						    <select type="text" class="combox" id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,10" >
						    	<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
						    </select>
					    </td>
					    <td><label>业务状态：</label></td>
					    <td>
						    <select type="text" class="combox" id="dia-busStatus" name="dia-busStatus" kind="dic" src="ZZYWZT" datasource="BUS_STATUS" datatype="0,is_null,10" >
						    	<option value="<%=DicConstant.ZZYWZT_01 %>" selected>运营</option>
						    </select>
					    </td>
					</tr>
					<tr style="display:none;" id="dia-tr-extendAttr">
					    <td><label>是否直营店：</label></td>
					    <td>
						    <select type="text" class="combox" id="dia-isDc" name="dia-isDc" kind="dic" src="SF" datasource="IS_DS" datatype="1,is_null,10" >
						    	<option value="<%=DicConstant.SF_02 %>" selected>否</option>
						    </select>
					    </td>
					    <td><label>是否集中点：</label></td>
					    <td>
						    <select type="text" class="combox" id="dia-isIc" name="dia-isIc" kind="dic" src="SF" datasource="IS_IC" datatype="1,is_null,10" >
						    	<option value="<%=DicConstant.SF_02 %>" selected>否</option>
						    </select>
					    </td>
					</tr>
					<tr style="display:none;" id="dia-tr-extendAttr2">
					    <td><label>是否军品店：</label></td>
					    <td>
						    <select type="text" class="combox" id="dia-isAm" name="dia-isAm" kind="dic" src="SF" datasource="IS_AM" datatype="1,is_null,10" >
						    	<option value="<%=DicConstant.SF_02 %>" selected>否</option>
						    </select>
					    </td>
					</tr>
				</table>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-diaSave">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>	
<script type="text/javascript">
var action = "<%=action%>";
/**
 * 保存url
 */
var diaSaveAction = "<%=request.getContextPath()%>/sysmng/orgmng/OrgMngAction";

//初始化
$(function(){
	//修改操作
	if(action != "1")
	{
		var selectedRows = $("#tab-orgList").getSelectedRows();
		setEditValue("dia-fm-orgInfo",selectedRows[0].attr("rowdata"));
		$("#dia-code").attr("readonly",true);
		if($("#dia-levelCode").attr("code") == "<%=DicConstant.JGJB_05%>" || $("#dia-levelCode").attr("code") == "<%=DicConstant.JGJB_06%>")
		{
			$("#dia-companyId").attr("src","T#TM_COMPANY:COMPANY_ID:SNAME:COMPANY_TYPE='<%=DicConstant.GSLX_02%>' AND STATUS='<%=DicConstant.YXBS_01%>' ");
			$("#dia-orgType").attr("src","E#200005=配送中心:200007=服务商:200006=配件经销商");
			//判断类别是否是配送中心
			if($("#dia-orgType").attr("code") == "<%=DicConstant.ZZLB_09%>")
			{
				$("#dia-tr-extendAttr").show();
				$("#dia-tr-extendAttr2").show();
			}else
			{
				$("#dia-tr-extendAttr").hide();
				$("#dia-tr-extendAttr2").hide();
			}
		}
		else
		{
			$("#dia-companyId").attr("src","T#TM_COMPANY:COMPANY_ID:SNAME:COMPANY_TYPE='<%=DicConstant.GSLX_01%>' AND STATUS='<%=DicConstant.YXBS_01%>' ");
			switch($("#dia-levelCode").attr("code"))
			{
				case "<%=DicConstant.JGJB_01%>":
					$("#dia-orgType").attr("src","E#200000=销售公司");
					$("#dia-orgType").attr("code","200000");
					$("#dia-orgType").val("销售公司");
					break;
				case "<%=DicConstant.JGJB_02%>":
					$("#dia-orgType").attr("src","E#200001=销司部门:200002=销司科室");
					break;
				case "<%=DicConstant.JGJB_06%>":
					$("#dia-orgType").attr("src","E#300001=供应商:300002=运送公司");
					break;
				case "<%=DicConstant.JGJB_03%>":
					$("#dia-orgType").attr("src","E#200003=大区");
					$("#dia-orgType").attr("code","200003");
					$("#dia-orgType").val("大区");
					break;
				case "<%=DicConstant.JGJB_04%>":
					$("#dia-orgType").attr("src","E#200004=办事处");
					$("#dia-orgType").attr("code","200004");
					$("#dia-orgType").val("办事处");
					break;
				case "<%=DicConstant.JGJB_09%>":
					$("#dia-orgType").attr("src","E#100001=集团公司");
					$("#dia-orgType").attr("code","100001");
					$("#dia-orgType").val("集团公司");
					break;
				case "<%=DicConstant.JGJB_08%>":
					$("#dia-orgType").attr("src","E#100003=集团部门:100004=集团科室");
					break;
				case "<%=DicConstant.JGJB_07%>":
					$("#dia-orgType").attr("src","E#100002=集团子公司");
					$("#dia-orgType").attr("code","100002");
					$("#dia-orgType").val("集团子公司");
					break;
			}
		}
	}
	$("#dia-pId").focus(function(){
		if(!$("#dia-companyId").val())
		{
			alertMsg.warn("请先选择所属公司");
			g_xDic.hide();
			event.preventDefault();
            event.stopPropagation();
			return false;
		}
	});
	
	$("#btn-diaSave").bind("click", function(event){
		doDiaSave();
	});
});

//点击字典回调方法
function afterDicItemClick(id)
{
	var ret =  true;
	switch(id)
	{
		case "dia-levelCode":
				var $input = $("#"+id);
				if($input.attr("code") == "<%=DicConstant.JGJB_05%>" || $input.attr("code") == "<%=DicConstant.JGJB_06%>")
				{
					$("#dia-companyId").attr("src","T#TM_COMPANY:COMPANY_ID:SNAME:COMPANY_TYPE='<%=DicConstant.GSLX_02%>' AND STATUS='<%=DicConstant.YXBS_01%>' ");
					$("#dia-orgType").attr("code","");
					$("#dia-orgType").val("");
					$("#dia-orgType").attr("src","E#200005=配送中心:200007=服务商:200006=配件经销商");
					$("#dia-tr-extendAttr").show();
					$("#dia-tr-extendAttr2").show();
				}
				else
				{
					$("#dia-companyId").attr("src","T#TM_COMPANY:COMPANY_ID:SNAME:COMPANY_TYPE='<%=DicConstant.GSLX_01%>' AND STATUS='<%=DicConstant.YXBS_01%>' ");
					switch($input.attr("code"))
					{
					case "<%=DicConstant.JGJB_01%>":
						$("#dia-orgType").attr("src","E#200000=销售公司");
						$("#dia-orgType").attr("code","200000");
						$("#dia-orgType").val("销售公司");
						break;
					case "<%=DicConstant.JGJB_02%>":
						$("#dia-orgType").attr("src","E#200001=销司部门:200002=销司科室");
						break;
					case "<%=DicConstant.JGJB_06%>":
						$("#dia-orgType").attr("src","E#300001=供应商:300002=运送公司");
						break;
					case "<%=DicConstant.JGJB_03%>":
						$("#dia-orgType").attr("src","E#200003=大区");
						$("#dia-orgType").attr("code","200003");
						$("#dia-orgType").val("大区");
						break;
					case "<%=DicConstant.JGJB_04%>":
						$("#dia-orgType").attr("src","E#200004=办事处");
						$("#dia-orgType").attr("code","200004");
						$("#dia-orgType").val("办事处");
						break;
					case "<%=DicConstant.JGJB_09%>":
						$("#dia-orgType").attr("src","E#100001=集团公司");
						$("#dia-orgType").attr("code","100001");
						$("#dia-orgType").val("集团公司");
						break;
					case "<%=DicConstant.JGJB_08%>":
						$("#dia-orgType").attr("src","E#100003=集团部门:100004=集团科室");
						break;
					case "<%=DicConstant.JGJB_07%>":
						$("#dia-orgType").attr("src","E#100002=集团子公司");
						$("#dia-orgType").attr("code","100002");
						$("#dia-orgType").val("集团子公司");
						break;
					}
					$("#dia-tr-extendAttr").hide();
					$("#dia-tr-extendAttr2").hide();
				}
					
			break;
	}
	return ret;
}

//保存
function doDiaSave()
{
	var $f = $("#dia-fm-orgInfo");
	if (submitForm($f) == false) return false;
	var sCondition = {};
		sCondition = $("#dia-fm-orgInfo").combined(1) || {};
	if(action == 1)	
	{
		var url = diaSaveAction + "/insert.ajax";
		doNormalSubmit($f,url,"btn-diaSave",sCondition,insertCallBack);
	}else
	{
		var url = diaSaveAction + "/update.ajax";
		doNormalSubmit($f,url,"btn-diaSave",sCondition,updateCallBack);
	}
	
}
//回调函数
function insertCallBack(res)
{
	try
	{
		$("#tab-orgList").insertResult(res,0);
		//重置输入框内容	
		resetEditValue("fm-orgInfo");
		//设置orgId主键域
		$("#dia-orgId").val(getNodeText(res.getElementsByTagName("ORG_ID").item(0)));
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=1&orgId="+$("#dia-orgId").val();
		sendPost(syncUrl,"","","","false");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//回调函数
function updateCallBack(res)
{
	try
	{
		var selectedIndex = $("#tab-orgList").getSelectedIndex();
		$("#tab-orgList").updateResult(res,selectedIndex);
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=2&orgId="+$("#dia-orgId").val();
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	
	return true;
}
</script>