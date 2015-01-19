<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" /> 
<title>参数管理</title>
<script type="text/javascript">
	var url = "<%=request.getContextPath()%>/parasmng/SysParaConfigureAction/search.ajax";
	$(function()
	{
		$("#search").click(function()
		{
			var $form = $("#csglForm");
			var condition = {};
			condition = $form.combined() || {};
			doFormSubmit($form.eq(0), url, "search", 1, condition, "csgllb");
		});	
	});
	function doUpdate(rowobj)
	{
		$("td input[type=radio]:first", $(rowobj)).attr("checked", true);
		var options = {width:680,height:300,max:false,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/jQFrameWork/xtgl/csgl/csglxg.jsp", "csgl", "修改参数", options);
	}
</script>
</head>
<body>
	<div id="layout" width="100%">
		<div id="bg" class="background"></div>
		<div id="peogressBar1" class="progressBar">数据加载中...请稍后...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：系统管理 &gt;参数管理&gt;参数管理</h4>
		<div class="page">
			<div class="pageHeader">
				<form id ="csglForm" method="post">
					<div class="searchBar" align="legt">
						<table class="seatchContent" id="csglTable">
							<tr>
								<td><label>参数名称：</label></td>
								<td><input type="text" id="PARANAME" name="PARANAME" datasource="PARANAME" datatype="1,is_null,30" operation="like" value=""></input></td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">
				<div id="csgldiv">
					<table width="100%" id="csgllb" name="csgllb" ref="csgldiv" refQuery="csglTable" style="display:none">
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="PARAKEY">参数代码</th>
								<th fieldname="PARANAME">参数名称</th>
								<th fieldname="PARAVALUE1">参数值1</th>
								<th fieldname="PARAVALUE2">参数值2</th>
								<th fieldname="PARAVALUE3">参数值3</th>
								<th fieldname="PARAVALUE4">参数值4</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>