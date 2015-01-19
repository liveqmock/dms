<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件管理费系数维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/SupplierClaimDateAction/oldPartManageSearch.ajax";
var options = {max:false,width:800,height:240,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#supplierform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"partList");
	});
	//导出
	$("#expPart").bind("click",function(){
		var $f = $("#supplierform");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/basicinfomng/SupplierClaimDateAction/partDownload.do");
		$("#exportFm").submit();
	});
	//导入
	$("#impPart").bind("click",function(){
		 //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页
        importXls("SE_BA_OLDPARTMANAGE_TMP",'',4,3,"/jsp/dms/oem/service/basicinfomng/supplierClaimRate/importSuccess.jsp");
	});
});
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/supplierClaimRate/oldPartManageFeeEdit.jsp", "edit", "旧件管理费系数设置", options);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;旧件管理费系数维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="supplierform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="supplierTable">
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" id="partCode" name="partCode" datasource="P.PART_CODE" operation="like" datatype="1,is_null,30" value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" id="partName"  name="partName"  dataSource="P.PART_NAME" operation="like"    datatype="1,is_null,30" value=""  /></td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="expPart">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="impPart">导&nbsp;&nbsp;入</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="supplier">
				<table style="display:none;width:100%;" id="partList" name="partList" ref="supplier" refQuery="supplierTable" >
					<thead>
						<tr>
							<!-- <th type="multi"  name="CX-XH" style="align:center;" unique="PART_ID" ></th> -->
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="PART_CODE">配件代码</th>
							<th fieldname="PART_NAME">配件名称</th>
							<th fieldname="OLD_MANAGE_FEE">旧件管理费系数</th>
							<th colwidth="85" type="link"  title="[设置]" action="doUpdate">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	 <form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>