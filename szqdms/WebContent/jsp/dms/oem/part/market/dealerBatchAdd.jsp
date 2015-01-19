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
		<form method="post" id="fm-searchDealerAdd">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchDealerAdd">
				<!-- 定义查询条件 -->
				<tr>
					<td><label>渠道商代码：</label></td>
				    <td>
				    	<input type="text" id="add_code" name="add_code" datasource="CODE" datatype="1,is_null,30" operation="like" />
				    </td>
				    <td><label>渠道商名称：</label></td>
				    <td>
				    	<input type="text" id="add_oname" name="add_oname" datasource="ONAME" datatype="1,is_null,30" operation="like" />
				    </td>
				</tr>
				<tr>
					<td><label>所属办事处：</label></td>
				    <td>
				    	<input type="text" id="add_pid"  name="add_pid" kind="dic" 
					    		src="T#TM_ORG:ORG_ID:ONAME{ORG_ID}:1=1 AND STATUS='100201' AND ORG_TYPE=200004 " 
					    		datasource="PID" datatype="1,is_null,30" />
				    	<input type="hidden" id="add_orgId"  name="add_orgId" datasource="ORG_ID" />
				    </td>
				</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-addDealerSearch" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-addSave">保&nbsp;&nbsp;存</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_searchDealerList" >
			<!-- table参数说明：{ref=父级div的id（page_userlist）,refQuery=与结果列表对应的查询条件table的id} -->
			<table style="display:none;width:100%;" id="tab-searchDealerList" name="tablist" ref="page_searchDealerList" refQuery="tab-searchDealerAdd" 
				multivals="dealerSelectVal">
					<thead>
						<tr>
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="ORG_ID" ></th>
							<th fieldname="CODE" >渠道商代码</th>
							<th fieldname="ONAME" >渠道商名称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<div id="dealerSelectVal">
			<table style="display:none">
				<tr>
					<td>
						<textarea id="valDealers" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
					</td>
				</tr>
			</table>
		</div>
		<form method="post" id="fm-dealerAdd">
			<input type="hidden" id="dia-dealer-activeId" name="dia-dealer-activeId" datasource="ACTIVE_ID" />
			<input type="hidden" id="dia-dealer-activeCode" name="dia-dealer-activeCode" datasource="ACTIVE_CODE" />
			<input type="hidden" id="dia-dealer-activeName" name="dia-dealer-activeName" datasource="ACTIVE_NAME" />
		</form>
	</div>
	</div>
</div>
<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProgramActiveAction";
var diaAction = "<%=action%>";

$(function(){ 
	//设置高度
	$("#tab-searchDealerList").attr("layoutH",document.documentElement.clientHeight-400);
	var activeId = $("#dia-activeId").val();
	
	if(diaAction == 3)	//新增初始化
	{ 
		var $f = $("#fm-searchDealerAdd");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var searchNewPartUrl = diaSaveAction + "/dealerSearch.ajax?activeId="+activeId;
		doFormSubmit($f,searchNewPartUrl,"btn-addDealerSearch",1,sCondition,"tab-searchDealerList");
	}
	
	//查询按钮响应
	$("#btn-addDealerSearch").bind("click", function(event){
		var bscCode = $("#add_bscCode").val();
		var $f = $("#fm-searchDealerAdd");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var searchNewPartUrl = diaSaveAction + "/dealerSearch.ajax?activeId="+activeId+"&bscCode="+bscCode;
		doFormSubmit($f,searchNewPartUrl,"btn-addDealerSearch",1,sCondition,"tab-searchDealerList");
	});
	
	//保存按钮响应
	$("#btn-addSave").bind("click", function(event){
		var activeCode = $("#dia-activeCode").val();
		var activeName = $("#dia-activeName").val();
		$("#dia-dealer-activeId").val(activeId);
		$("#dia-dealer-activeCode").val(activeCode);
		$("#dia-dealer-activeName").val(activeName);
		
		var valDealers = $("#valDealers").val();
		if (valDealers) {
			var $f = $("#fm-dealerAdd");
			var sCondition = {};
	    	sCondition = $f.combined(1) || {};
	    	var searchNewPartUrl = diaSaveAction + "/insertDealers.ajax?valDealers="+valDealers;
	    	doNormalSubmit($f,searchNewPartUrl,"btn-addSave",sCondition,diaInsertDealerCallBack);
		} else {
			alert("请选择记录！");
			return false;
		}
	});
})

//批量新增回调函数
function diaInsertDealerCallBack(res)
{
	try
	{	
		var activeId = $("#dia-activeId").val();
		var $f = $("#dia-fm-searchProActiveDealer");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var searchUrl = diaSaveAction + "/proActiveDealerSearch.ajax?activeId="+activeId;
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"dia-tab-proActiveDealerList");
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
	if(id.indexOf("carrierCode") == 0)
	{  
		$("#carrierName").val($("#"+id).val());
		
		if($row.attr("CARRIER_ID")){
			$("#carrierId").val($row.attr("CARRIER_ID"));
		}
		return true;
	}

	return true;
}
</script>