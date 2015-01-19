<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.OrgDept" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<% 
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String orgId = user.getOrgId();
	String orgType = user.getOrgDept().getOrgType();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件入库查询</title>
<script type="text/javascript">
$(function(){
	$("#btn-search").click(function(){
		var queryAction = "<%=request.getContextPath()%>/part/storageMng/search/DealerPartChangeSearchAction/partChangeSearch.ajax";
		var $f = $("#fm-searchContract");
		var sCondition = {};
		sCondition = $f.combined() || {};
		doFormSubmit($f,queryAction,"btn-search",1,sCondition,"invertoryTable");
	});
	$("#btn-export").click(function(){
        var $f = $("#fm-searchContract");
        if (submitForm($f) == false) {
            return false;
        }
        var sCondition = {};
        sCondition = $f.combined() || {};
        $("#data").val(sCondition);
        var BEGIN_DATE = $("#BEGIN_DATE").val();
        var END_DATE = $("#END_DATE").val();
        var url = encodeURI("<%=request.getContextPath()%>/part/storageMng/search/DealerPartChangeSearchAction/download.do?BEGIN_DATE="+BEGIN_DATE+"&END_DATE="+END_DATE);
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
})
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 销售相关   &gt; 配件出入库查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form id="exportFm" method="post" style="display:none">
           <input type="hidden" id="data" name="data"></input>
        </form>
		<form method="post" id="fm-searchContract">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-htcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>渠道代码</label></td>
					    <td><input type="text" id="orgCode" name="orgCode" datasource="T.ORG_CODE" datatype="0,is_null,300000" kind="dic" src="T#TM_ORG:CODE:ONAME:1=1 AND ORG_TYPE IN(200005,200006,200007) AND STATUS='100201' ORDER BY CODE"/></td>
					    <td><label>渠道名称</label></td>
					    <td><input type="text" id="ORG_NAME" name="ORG_NAME" dataSource="T.ORG_NAME"  datatype="1,is_null,3000" operation="like" /></td>
					</tr>
					<tr>
					    <td><label>配件代码</label></td>
					    <td><input type="text" id="PART_CODE" name="PART_CODE" datatype="1,is_null,3000" dataSource="T.PART_CODE" operation="like" /></td>
					    <td><label>配件名称</label></td>
					    <td><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,3000" dataSource="T.PART_NAME" operation="like" /></td>
					</tr>
					<tr>
						<td><label>起止日期：</label></td>
					    <td>
				    		<input type="text" group="in-kscjrq,in-jscjrq"  id="BEGIN_DATE"  name="BEGIN_DATE" operation=">="  dataSource="BEGIN_DATE" style="width:75px;"  kind="date" datatype="0,is_date,30" onclick="WdatePicker()" action="show" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" group="in-kscjrq,in-jscjrq"  id="END_DATE"  name="END_DATE" operation="<=" dataSource="END_DATE" style="width:75px;margin-left:-30px;" kind="date" datatype="0,is_date,30" onclick="WdatePicker()" action="show" />
				   		 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-export">导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_contract" >
			<table style="display:none;width:100%;" id="invertoryTable" name="tablist" ref="page_contract" refQuery="fm-searchContract" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="ORG_CODE" >配件代码</th>
							<th fieldname="ORG_NAME" >配件名称</th>
							<th fieldname="BEGIN_COUNT" >期初库存数</th>
							<th fieldname="PERIOD_IN_COUNT" >本期入库数</th>
							<th fieldname="PERIOD_OUT_COUNT" >本期出库数</th>
							<th fieldname="END_COUNT" >期末库存数</th>
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