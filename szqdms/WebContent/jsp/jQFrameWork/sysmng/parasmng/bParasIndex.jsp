<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" /> 
<title>参数管理</title>
<script type="text/javascript">
	var url = "<%=request.getContextPath()%>/parasmng/UserParaConfigureAction/search.ajax";
	var deleteUrl = "<%=request.getContextPath()%>/parasmng/UserParaConfigureAction/delete.ajax";
	var syncUrl = "<%=request.getContextPath()%>/parasmng/UserParaConfigureAction/synchronize.ajax";
	//定义弹出窗口样式
	var diaEditOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$(function()
	{
		$("#btn-search").click(function()
		{
			var $form = $("#fm-paraInfo");
			var condition = {};
			condition = $form.combined() || {};
			doFormSubmit($form, url, "btn-search", 1, condition, "tab-paralist");
		});	
		$("#btn-add").bind("click",function(){
			$.pdialog.open(webApps + "/jsp/jQFrameWork/sysmng/parasmng/bParasEdit.jsp?action=1", "paraEdit", "新增参数", diaEditOptions);
		})
	});
	function doUpdate(rowobj)
	{
		$("td input[type=radio]:first", $(rowobj)).attr("checked", true);
		$.pdialog.open(webApps + "/jsp/jQFrameWork/sysmng/parasmng/bParasEdit.jsp?action=2", "paraEdit", "修改参数", diaEditOptions);
	}
	var $row;
	function doDelete(rowobj)
	{
		$row = $(rowobj);
		var url = deleteUrl + "?sn="+$(rowobj).attr("SN");
		sendPost(url,"","",deleteCallBack,"true");
	}
	//删除回调方法
	function  deleteCallBack(res)
	{
		try
		{
			if($row) 
				$("#tab-paralist").removeResult($row);
			//同步集群节点（业务功能不需要）
			var sUrl = syncUrl + "?type=3&paraKey="+$row.attr("PARAKEY");
			sendPost(sUrl,"","","","false");
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
	<div id="layout" width="100%">
		<div id="bg" class="background"></div>
		<div id="peogressBar1" class="progressBar">数据加载中...请稍后...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：系统管理 &gt;参数管理&gt;业务参数管理</h4>
		<div class="page">
			<div class="pageHeader">
				<form id ="fm-paraInfo" method="post">
					<div class="searchBar" align="legt">
						<table class="seatchContent" id="tab-paraInfo">
							<tr>
								<td><label>业务类型：</label></td>
								<td><input type="text" id="BUS_TYPE" name="BUS_TYPE" datasource="BUS_TYPE" kind="dic" src="<%=DicConstant.YWLX %>" datatype="1,is_null,30"  ></input></td>
								<td><label>参数类型名称：</label></td>
								<td><input type="text" id="TYPENAME" name="TYPENAME" datasource="TYPENAME" datatype="1,is_null,30" operation="like" ></input></td>
								<td><label>参数名称：</label></td>
								<td><input type="text" id="PARANAME" name="PARANAME" datasource="PARANAME" datatype="1,is_null,30" operation="like"></input></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="btn-add">新&nbsp;&nbsp;增</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="page-paralist">
					<table width="100%" id="tab-paralist"  ref="page-paralist" refQuery="tab-paraInfo" style="display:none">
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="APPTYPE">参数类型代码</th>
								<th fieldname="TYPENAME">参数类型名称</th>
								<th fieldname="PARAKEY">参数代码</th>
								<th fieldname="PARANAME">参数名称</th>
								<th fieldname="PARAVALUE1">参数值1</th>
								<th fieldname="PARAVALUE2">参数值2</th>
								<th fieldname="PARAVALUE3">参数值3</th>
								<th fieldname="PARAVALUE4">参数值4</th>
								<th fieldname="BUS_TYPE">业务类型</th>
								<th fieldname="STATUS">状态</th>
								<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>