<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%
	User user = (User)request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<!-- 
	 Title:
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-06-26
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
	<div id="dia-layout" width="100%" height="100%">
		<div class="page">
			<div class="pageContect">
				<form method="post" id="dia-fm-paraInfo" class="editForm" >
					<div align="left">
						<input type="hidden" id="dia-sn" datasource="SN" ></input>
						<fieldset>
							<legend align="right"><a style="cursor:hand;font-weight:bold;" onclick="onTitleClick('dia-tab-paraInfo')">&nbsp;参数基本信息&gt;&gt;</a></legend>
							<table class="editTable" id="dia-tab-paraInfo">
								<tr>
									<td><label>所属业务</label></td>
									<td>
										<select type="text" id="DIA-BUSTYPE" datasource="BUS_TYPE" kind="dic" src="<%=DicConstant.YWLX %>"  datatype="0,is_null,30">
											<option value="<%=DicConstant.YWLX_01%>">配件业务</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><label>参数类型代码</label></td>
									<td><input type="text" id="DIA-APPTYPE" datasource="APPTYPE"  datatype="0,is_digit_letter,8"/></td>
				
									<td><label>参数类型名称</label></td>
									<td><input type="text" id="DIA-TYPENAME" datasource="TYPENAME"   datatype="0,is_null,30"/></td>
								</tr>
								<tr>
									<td><label>参数代码</label></td>
									<td><input type="text" id="DIA-PARAKEY" datasource="PARAKEY"  datatype="0,is_null,60"/></td>
				
									<td><label>参数名称</label></td>
									<td><input type="text" id="DIA-PARANAME" datasource="PARANAME"   datatype="0,is_null,60"/></td>
								</tr>
								<tr>
									<td><label>参数值1</label></td>
									<td><input type="text" id="DIA-PARAVALUE1" datasource="PARAVALUE1" datatype="0,is_null,60"/></td>
								
									<td><label>参数值2</label></td>
									<td><input type="text" id="DIA-PARAVALUE2" datasource="PARAVALUE2" datatype="1,is_null,60"/></td>
								</tr>
								<tr>
									<td><label>参数值3</label></td>
									<td><input type="text" id="DIA-PARAVALUE3" datasource="PARAVALUE3" datatype="1,is_null,60"/></td>
								
									<td><label>参数值4</label></td>
									<td><input type="text" id="DIA-PARAVALUE4" datasource="PARAVALUE4" datatype="1,is_null,60"/></td>
								</tr>
								<tr>
									<td><label>备注</label></td>
									<td colspan="3"><input type="text" id="DIA-PARAVALUE4" style="width:88%;" datasource="PARAVALUE4" datatype="1,is_null,60"/></td>
								</tr>
								<tr>
									<td><label>状态</label></td>
									<td>
										<select type="text" id="DIA-STATUS" datasource="STATUS" kind="dic" src="<%=DicConstant.YXBS %>"  datatype="0,is_null,30">
											<option value="<%=DicConstant.YXBS_01%>">有效</option>
										</select>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-btn-save">保存</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	var diaSaveAction =  "<%=request.getContextPath()%>/parasmng/UserParaConfigureAction";
	var diaAction = "<%=action%>";
	$(function(){
		if(diaAction != "1")//修改操作
		{
			var selectedRows = $("#tab-paralist").getSelectedRows();
			setEditValue("dia-fm-paraInfo",selectedRows[0].attr("rowdata"));
			$("#DIA-APPTYPE").attr("readonly",true);
			$("#DIA-TYPENAME").attr("readonly",true);
			$("#DIA-PARAKEY").attr("readonly",true);
			$("#DIA-PARANAMEY").attr("readonly",true);
		}
		//绑定保存按钮
		$("#dia-btn-save").bind("click", function(event){
			var $f = $("#dia-fm-paraInfo");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#dia-fm-paraInfo").combined(1) || {};
			if(diaAction == "1")	//插入动作
			{
				var addUrl = diaSaveAction + "/insert.ajax";
				doNormalSubmit($f,addUrl,"dia-btn-save",sCondition,diaInsertCallBack);
			}else	//更新动作
			{
				var updateUrl = diaSaveAction + "/update.ajax";
				doNormalSubmit($f,updateUrl,"dia-btn-save",sCondition,diaUpdateCallBack);
			}
		});
	})
	
	//新增回调函数
	function diaInsertCallBack(res)
	{
		try
		{
			$("#tab-paralist").insertResult(res,0);
			//设置主键域
			$("#dia-sn").val(getNodeText(res.getElementsByTagName("SN").item(0)));
			//同步集群节点
			var syncUrl = diaSaveAction + "/synchronize.ajax?type=1&paraKey="+$("#DIA-PARAKEY").val();
			sendPost(syncUrl,"","","","false");
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
		var selectedIndex = $("#tab-paralist").getSelectedIndex();
		$("#tab-paralist").updateResult(res,selectedIndex);
		//同步集群节点（业务功能不需要）
		var syncUrl = diaSaveAction + "/synchronize.ajax?type=2&paraKey="+$("#DIA-PARAKEY").val();
		sendPost(syncUrl,"","","","false");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
	
</script>