<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>旧件回运审核</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartCheckAction/oldPartSearch.ajax";
//查询按钮响应方法
$(function(){
	$("#search").bind("click",function(){
		var $f = $("#oldpartform");
		var sCondition = {};
  		sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartList");
	});
});
function open(){
	alertMsg.info("弹出服务商树！");
}
function doUpdate(rowobj){
	$("td input:first",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/service/oldpart/oldPartCheckDetail.jsp", "oldPart", "旧件回运审核", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;旧件回运审核</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="oldpartform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="oldpartTable">
						<tr>
							<td><label>渠道商代码：</label></td>
							<td><input type="text" id="orgCode" name="orgCode" datasource="O.ORG_ID" datatype="1,is_null,100" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
							<td><label>回运单号：</label></td>
							<td><input type="text" id="orederNo" name="orederNo"  datasource="O.ORDER_NO" operation="like" datatype="1,is_null,100"  value="" /></td>
							<td><label>运输方式：</label></td>
							<td><select  type="text" id="transType" name="transType" datasource="O.TRANS_TYPE" kind="dic" class="combox" src="HYDYSFS"  datatype="1,is_null,6" value="" >
									<option value="-1" selected>--</option>
								</select>
							</td>
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
			<div id="oldpart">
				<table style="display:none;width:100%;" id="oldPartList" name="oldPartList" ref="oldpart" refQuery="oldpartTable" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ORG_CODE">渠道商代码</th>
							<th fieldname="ORG_NAME">渠道商名称</th>
							<th  fieldname="ORDER_NO" ordertype='local' class="desc">回运单号</th>
							<th  fieldname="CLAIMCOUNT">索赔单数量</th>
							<th  fieldname="PARTCOUNT">回运旧件数量</th>
							<th  fieldname="AMOUNT">装箱总数</th>
							<th  fieldname="TRANS_TYPE">运输方式</th>
							<th fieldname="RETURN_DATE">渠道回运日期</th>
							<th colwidth="85" type="link"  title="[审核]" action="doUpdate">操作</th>
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