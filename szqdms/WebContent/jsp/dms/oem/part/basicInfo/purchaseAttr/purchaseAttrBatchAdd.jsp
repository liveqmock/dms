<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page" >
	<div class="pageHeader" >
		<!-- 提交查询请求form -->
		<form method="post" id="fm-newPartPchAttribute">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-newPartPchAttribute">
				<!-- 定义查询条件 -->
				<tr>
					<td><label>配件代码：</label></td>
				    <td>
				    	<input type="text" id="diaN_partCode" name="diaN_partCode" datasource="PART_CODE" datatype="1,is_null,30" operation="like" />
				    </td>
				    <td><label>配件名称：</label></td>
				    <td>
				    	<input type="text" id="diaN_partName" name="diaN_partName" datasource="PART_NAME" datatype="1,is_null,30" operation="like" />
				    </td>
				</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-newPartSearch" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_searchNewPartList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-searchNewPartList" name="tablist" ref="page_searchNewPartList" refQuery="tab-newPartPchAttribute" >
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="PART_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table style="display:none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
	</div>
		<form method="post" id="fm-addPurchaseAttr">
				<table class="editTable" id="tab-addPurchaseAttrInfo">
					<tr>
					    <td><label>采购员姓名：</label></td>
					    <td><input type="text" id="diaN-userAccount"  name="diaN-userAccount" kind="dic" 
					    src="T#TM_USER:ACCOUNT:PERSON_NAME:1=1 AND ORG_ID='10000065' AND STATUS=100201" datasource="USER_ACCOUNT" datatype="0,is_null,32" />
					    <input type="hidden" id="diaN-personName"  name="diaN-personName" datasource="PERSON_NAME"  />
					    </td>
					</tr>
				</table>
					<div class="searchBar" align="left" >
						<div class="subBar">
							<ul>
								<li><div class="button"><div class="buttonContent"><button type="button" id="btn-purchaseAdd" >分配采购员</button></div></div></li>
							</ul>
						</div>
					</div>
		</form>
	</div>
</div>
<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/basicInfoMng/PchAttributeAction";
var diaAction = "<%=action%>";

$(function(){ 
	//设置高度
	$("#tab-searchNewPartList").attr("layoutH",document.documentElement.clientHeight-400);
	//查询按钮响应
	$("#btn-newPartSearch").bind("click", function(event){
		var $f = $("#fm-newPartPchAttribute");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var searchNewPartUrl = diaSaveAction + "/searchNewPart.ajax";
		doFormSubmit($f,searchNewPartUrl,"btn-newPartSearch",1,sCondition,"tab-searchNewPartList");
	});
	
	if(diaAction == 1)	//新增初始化
	{ 
		var $f = $("#fm-newPartPchAttribute");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var searchNewPartUrl = diaSaveAction + "/searchNewPart.ajax";
		doFormSubmit($f,searchNewPartUrl,"btn-batchAdd",1,sCondition,"tab-searchNewPartList");
	}

	$("#btn-purchaseAdd").bind("click", function(event){
		var partIds=$("#val0").val();
		if(partIds) {
			var $f = $("#fm-addPurchaseAttr");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-addPurchaseAttr").combined(1) || {};
	   
			var addUrl = diaSaveAction + "/batchInsert.ajax?partIds=" + partIds;
			doNormalSubmit($f,addUrl,"btn-purchaseAdd",sCondition,diaBatchInsertCallBack);
		}else {
			alertMsg.info("请选择记录");
			return false;
		}
	});
})

//批量新增回调函数
function diaBatchInsertCallBack(res)
{
	try
	{	
		$("#btn-search").trigger("click");
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//列表复选
function doCheckbox(checkbox)
{
	var arr = [];
	var $checkbox = $(checkbox);
	var mxid = $(checkbox).val();
	arr.push(mxid);
	multiSelected($checkbox,arr);
}

//表选字典赋值
function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="diaN-userAccount")
	{
		$("#diaN-personName").val($("#"+id).val());
	
	}
	return true;
}
</script>