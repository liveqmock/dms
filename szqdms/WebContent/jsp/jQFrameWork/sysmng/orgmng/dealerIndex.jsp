<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:渠道管理
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2014-09
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<jsp:include page="/head.jsp" />
<title>渠道管理</title>
<script type="text/javascript">
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/sysmng/dealermng/DealerMngAction/search.ajax";
var saveUrl = "<%=request.getContextPath()%>/sysmng/dealermng/DealerMngAction/save.ajax";
$(function()
{
	$("#btn-search").click(function(){
		var $f = $("#fm-dealerSearch");
		var sCondition = {};
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-dealerList");
	});
	$("#btn-export").bind("click",function(){
		var $f = $("#fm-dealerSearch");
		if (submitForm($f) == false) return false;
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/sysmng/dealermng/DealerMngAction/oemDownload.do");
		$("#exportFm").submit();
	});
});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/jQFrameWork/sysmng/orgmng/dealerEdit.jsp?action=2", "editDealer", "修改渠道信息", options);
}

//数据字典回调函数
function afterDicItemClick(id,$row){
    // 选择省
    if(id == "province") {
        $("#city").attr("src","XZQH");
        $("#city").attr("isreload","true");
        $("#city").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
    }
 	// 选择省
    if(id == "dia-province") {
        $("#dia-city").attr("src","XZQH");
        $("#dia-city").attr("isreload","true");
        $("#dia-city").attr("filtercode", "^"+$("#"+id).attr("code").substr(0,2)+"([0-9][1-9]|[1-9]0)00$");
    }
    return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>数据加载中，请稍等...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 系统管理  &gt; 组织管理   &gt; 渠道管理</h4>
	<div class="page">
	<div class="pageHeader">
		<form method="post" id="fm-dealerSearch">
		<!-- 定义查询条件 -->
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-dealerSearch">
				<tr>
					<td><label>渠道代码：</label></td>
				    <td><input type="text" id="dealerCode" name="dealerCode"  datasource="DEALER_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
				    <td><label>渠道名称：</label></td>
				    <td><input type="text" id="dealerName" name="dealerName" datasource="DEALER_NAME" operation="like" datatype="1,is_null,60" /></td>
					<td><label>渠道类型：</label></td>
				    <td>
				    	<select type="text" id="dealerType" name="dealerType" kind="dic" src="E#200004=办事处:200005=配送中心:200006=配件经销商:200007=服务商" datasource="DEALER_TYPE" datatype="1,null,30" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				</tr>
				<tr>
					<td><label>渠道星级：</label></td>				    
				    <td>
				    	<select type="text" id="dealerStar" name="dealerStar" kind="dic" src="T#user_para_configure:PARAKEY:PARANAME:1=1 AND apptype='2003'" datasource="DEALER_STAR" datatype="1,is_null,60" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
					<td><label>所在省份：</label></td>
				    <td><input type="text" id="province" name="province" datasource="PROVINCE" kind="dic" src="XZQH" filtercode="\d{2}0000$" datatype="1,is_null,10" /></td>
				    <td><label>所属市县区：</label></td>
				    <td><input type="text" id="city" name="city" datasource="CITY" kind="dic" src="XZQH"  dicwidth="300" datatype="1,is_null,30"  /></td>
				</tr>
				<tr>
					<td><label>有效标识：</label></td>
				    <td>
				    	<select type="text" id="status" name="status" datasource="STATUS" kind="dic" src="YXBS" datatype="1,is_null,10" >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				    <td><label>渠道状态：</label></td>
				    <td>
				    	<select type="text" id="dealerStatus" name="dealerStatus" datasource="DEALER_STATUS" kind="dic" src="ZZYWZT" datatype="1,is_null,30"  >
				    		<option value="-1">--</option>
				    	</select>
				    </td>
				</tr>
			</table>
			
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export">导&nbsp;&nbsp;出</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-tab-dealerList" >
			<table style="display:none" id="tab-dealerList" name="tablist" ref="page-tab-dealerList" refQuery="tab-dealerSearch" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="DEALER_CODE" ordertype='local' class="desc">渠道代码</th>
							<th fieldname="DEALER_NAME" >渠道全称</th>
							<th fieldname="DEALER_SHORTNAME" >渠道简称</th>
							<th fieldname="DEALER_TYPE" >渠道类型</th>
							<th fieldname="DEALER_STATUS" >渠道状态</th>
							<th fieldname="DEALER_STAR" >渠道星级</th>
							<th fieldname="OVERDUE_DAYS" >超单天数</th>
							<th fieldname="BELONG_OFFICE" >所属办事处</th>
							<th fieldname="STATUS" >有效标识</th>
							<th type="link" title="[编辑]" action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>	
<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data"></input>
</form>
</body>
</html>