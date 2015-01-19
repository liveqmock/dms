<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>字典维护</title>
<script type="text/javascript">
//查询提交方法
var url = "<%=request.getContextPath()%>/OrgPerson/OrgPersonAction.do?method=queryListByJson";
var searchUrl = "<%=request.getContextPath()%>/DicTreeAction/searchByParent.ajax";
var saveUrl = "<%=request.getContextPath()%>/DicTreeAction";
//定义弹出窗口样式
var diaSelOptions = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
$(function()
{
	$("#dicCode").parent().find("span").css("margin-left","-10px");
	$("#dicCode").parent().find("img").css("margin-left","-30px");
	$("#btn-search").click(function(){
		var $f = $("#fm-dicSearch");
		var sCondition = {}; 
    	sCondition = $f.combined() || {}; 
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-dicList");
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	var $row = $(rowobj);
	if (submitForm($row) == false) return false;
	$("td input[type=radio]:first",$row).attr("checked",true);
	if($row.attr("ID"))
	{
		var sCondition = {};
		sCondition = $row.combined(1) || {};
		var updateUrl = saveUrl + "/update.ajax?dicname="+$row.attr("DIC_NAME_CODE")+"&id="+$row.attr("ID")+"&pid="+$row.attr("PARENT_ID")+"&code="+$row.attr("DIC_CODE");
		sendPost(updateUrl,"",sCondition,updateCallBack);
	}else
	{
		var sCondition = {};
		sCondition = $row.combined(1) || {};
		var insertUrl = saveUrl + "/insert.ajax?dicname="+$("#dicCode").attr("code")+"&pid="+$("#dicCode").attr("DIC_ID");
		sendPost(insertUrl,"",sCondition,insertCallBack);
	}
}
//新增回调方法
function insertCallBack(res)
{
	var selectedIndex = $("#tab-dicList").getSelectedIndex();
 	$("#tab-dicList").updateResult(res,selectedIndex);
 	$("#tab-dicList").clearRowEdit($("#tab-dicList").getSelectedRows()[0],selectedIndex);
 	return true;
}
//更新回调方法
function updateCallBack(res)
{
	var selectedIndex = $("#tab-dicList").getSelectedIndex();
	var selectedRow = $("#tab-dicList").getSelectedRows()[0];
 	$("#tab-dicList").updateResult(res,selectedIndex);
 	$("#tab-dicList").clearRowEdit(selectedRow,selectedIndex);
 	//同步集群节点
	var syncUrl = saveUrl + "/synchronize.ajax?type=2&dicname="+selectedRow.attr("DIC_NAME_CODE")+"&pid="+selectedRow.attr("PARENT_ID")+"&code="+selectedRow.attr("DIC_CODE");
	sendPost(syncUrl,"","","","false");
 	return true;
}
function doDelete(rowobj)
{
	alertMsg.confirm("是否确认删除？");
}

function addDicItem()
{
	var $tab = $("#tab-dicList");
	var $tr = $tab.createRow();
	//$tr.find("td").eq(2).html("<input type='text' id='ID-1' name='ID' datasource='ID' datatype='0,is_digit_letter,6' />");
	//$tr.find("td").eq(3).html("<input type='text' id='DIC_CODE-2' name='DIC_CODE' datasource='DIC_CODE' datatype='0,is_digit_letter,30' />");
	//setStyle($tr);
}
function afterDicItemClick(id,$row)
{
	if(id == "dicCode")
	{
		$("#dicCode").attr("DIC_ID",$row.attr("ID"));
	}
	return true;
}
function showDicRecords()
{
	$.pdialog.open(webApps + "/jsp/jQFrameWork/sysmng/dicmng/diaSelDic.jsp", "selectDic", "字典选择", diaSelOptions);
}
</script>
</head>
<body>
<div id="layout" width="100%">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 字典管理   &gt; 字典维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="fm-dicSearch">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-dicSearch">
			<tr>
				<td><label>字典类型：</label></td>
			    <td><input type="text" id="dicCode"  name="dicCode" datasource="DIC_NAME_CODE" dicwidth="240" hasBtn="true" callFunction="showDicRecords();" kind="dic" src="T#DIC_TREE:DIC_CODE:DIC_VALUE{ID}:PARENT_ID=0" datatype="0,is_null,30" operation="=" /></td>
			</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-dicList" >
			<table style="display:none" id="tab-dicList" name="dicList" ref="page-dicList" refQuery="tab-dicSearch" edit="true">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:;" append="plus|addDicItem"></th>
							<th fieldname="ID" freadonly="false" fdatatype="0,is_digit_letter,6">ID主键</th>
							<th fieldname="DIC_CODE" ordertype='local' class="asc" freadonly="false" fdatatype="0,is_digit_letter,30">字典代码</th>
							<th fieldname="DIC_VALUE" fdatatype="0,is_null,50">字典值</th>
							<th fieldname="DIC_VALUE_SPELL" fdatatype="0,is_digit_letter,30">字典值简拼</th>
							<th fieldname="DIC_VALUE_ASPELL" fdatatype="0,is_digit_letter,100">字典值全拼</th>
							<th fieldname="ORDER_NUM" fdatatype="0,is_null,4" >显示顺序</th>
							<th colwidth="85" type="link" title="[编辑]|[删除]" action="doUpdate|doDelete" >操作</th>
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