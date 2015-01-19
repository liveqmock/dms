<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件供应商认领</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartSupGetAction/oldPartSupGetSearch.ajax";
var options = {max:false,width:700,height:220,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldPartSupform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartSupList");
	});
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/oldpart/oldPartSupGetDetail.jsp", "supGet", "供应商认领", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;供应商旧件认领</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldPartSupform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldPartSupTable">
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="T.PART_CODE" datatype="1,is_null,100" value="" operation="like"/></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName" name="partName" datasource="T.PART_NAME"  datatype="1,is_null,100" value="" operation="like"/></td>
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
			<div id="oldPartSup">
				<table style="display:none;width:100%;" id="oldPartSupList" name="oldPartSupList" ref="oldPartSup" refQuery="oldPartSupTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PART_CODE"  ordertype='local' class="desc">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="SURPLUS_AMOUNT">库存数量</th>
							<th colwidth="45" type="link"  title="[认领]" action="doUpdate">操作</th>
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