<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>活动方案制定</title>
<script type="text/javascript">
var diaAddOptions = {max:true,width:1024,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var mngUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProgramActiveAction";
$(function()
{
	$("#btn-search").bind("click", function(event){
		var searchUrl = mngUrl+"/search.ajax";
		var $f = $("#fm-searchProgramActive");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-programActiveList");
	});
	
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/part/market/programActiveAdd.jsp?action=1", "addProgramActive", "新增活动方案制定", diaAddOptions);
	});
});

function doUpdate(rowobj)
{
	var activeStatus = $(rowobj).attr("ACTIVE_STATUS");
	if (activeStatus == "206001") {
		//保存出现编辑页面
		$("td input[type=radio]",$(rowobj)).attr("checked",true);
		$.pdialog.open(webApps+"/jsp/dms/oem/part/market/programActiveAdd.jsp?action=2", "活动方案编辑", "活动方案编辑", diaAddOptions);
	} else {
		//下发出现查看页面
		$("td input[type=radio]",$(rowobj)).attr("checked",true);
		$.pdialog.open(webApps+"/jsp/dms/oem/part/market/proActiveAddQuery.jsp?action=2", "活动方案编辑", "活动方案编辑", diaAddOptions);
	}
}

var $row;
function doDelete(rowobj)
{
	$row = $(rowobj);
	var activeStatus = $(rowobj).attr("ACTIVE_STATUS");
	if (activeStatus == "206001") {
		//保存的可以删除
		var url = mngUrl + "/delete.ajax?activeID="+$(rowobj).attr("ACTIVE_ID");
		sendPost(url, "", "", deleteProActiveCallBack, "true");
	} else {
		alert("此活动方案已下发，不能删除！");
		return false;
	}
}

function  deleteProActiveCallBack(res)
{
	try
	{
		if($row) 
			$("#tab-programActiveList").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 市场管理  &gt; 市场活动管理   &gt; 活动方案制定</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchProgramActive">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchProgramActive">
					<tr>
					    <td><label>活动代码：</label></td>
					    <td>
					    	<input type="text" id="activeCode"  name="activeCode" datasource="A.ACTIVE_CODE"  datatype="1,is_null,30" operation="like" />
					    </td>
				    	<td><label>活动名称：</label></td>
					    <td>
					    	<input type="text" id="activeName"  name="activeName" datasource="A.ACTIVE_NAME"  datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>活动流程状态：</label></td>
					    <td>
					    	<select id="activeStatus" name="activeStatus" kind="dic" src="HDLCZT" datasource="A.ACTIVE_STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="-1" selected>-----</option>
					    	</select>
					    </td>
					</tr>
					<tr>
				   		<td><label>开始时间：</label></td>
					    <td>
				    		<input type="text" id="startDate1"  name="startDate1" dataSource="A.START_DATE" datatype="1,is_date,30" operation=">=" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="endDate2"  name="endDate2" dataSource="A.START_DATE" datatype="1,is_date,30" operation="<=" 
								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>结束时间：</label></td>
					     <td>
				    		<input type="text" id="endDate1"  name="endDate1" dataSource="A.END_DATE" datatype="1,is_date,30" operation=">=" 
				    			style="width:75px;"  kind="date"  onclick="WdatePicker()" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">~</span>
							<input type="text" id="endDate2"  name="endDate2" dataSource="A.END_DATE" datatype="1,is_date,30" operation="<=" 
								style="width:75px;margin-left:-30px;" kind="date" onclick="WdatePicker()" />
				   		 </td>
				   		 <td><label>有效标识：</label></td>
					     <td>
					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="A.STATUS" datatype="1,is_null,6" operation="=" >
					    	<option value="100201" selected>有效</option>
					    	</select>
					     </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_programActiveList" >
			<table style="display:none;width:100%;" id="tab-programActiveList" name="tablist" ref="page_programActiveList" refQuery="fm-searchProgramActive" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="ACTIVE_CODE" >活动代码</th>
							<th fieldname="ACTIVE_NAME" >活动名称</th>
							<th fieldname="START_DATE" >开始时间</th>
							<th fieldname="END_DATE" >结束时间</th>
							<th fieldname="ACTIVE_STATUS">活动流程状态</th>
							<th fieldname="STATUS" >有效标识</th>
							<th colwidth="145" type="link" refer="showAnNiu" title="[编辑]|[删除]" action="doUpdate|doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
                    </tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>