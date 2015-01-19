<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>技术科审核</title>
<style type="text/css">
.errorInput {
	border-color: red;
}
</style>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件管理 &gt; 配件信息维护申请管理 &gt; 技术科审核
		</h4>
		<div class="page">
			<div class="pageHeader">
				<form method="post" id="fm-searchContract">
					<!-- 定义隐藏域条件 -->
					<!-- 技术科审核查询:查询状态为已提交的 -->
					<input type="hidden" id="APPLICATION_STATUS" name="APPLICATION_STATUS" dataSource="APPLICATION_STATUS" value="<%=DicConstant.PJWHSQZT_02%>" />
					<div class="searchBar" align="left">
						<table class="searchContent">
							<!-- 定义查询条件 -->
							<tr>
								<td><label>申请单号</label></td>
								<td><input type="text" id="APPLICATION_NO" name="APPLICATION_NO" datatype="1,is_digit_letter,30" dataSource="APPLICATION_NO" operation="like" /></td>
								<td><label>车辆识别码</label></td>
								<td><input type="text" id="VIN" name="VIN" datatype="1,is_digit_letter,30" dataSource="VIN" operation="=" /></td>
							</tr>
							<tr>
								<td><label>申请单类型</label></td>
								<td>
									<select type="text" id="APPLICATION_TYPE" name="APPLICATION_TYPE" datasource="APPLICATION_TYPE"datatype="1,is_null,30"
											kind="dic" src="PJWHSQLX" filtercode="<%=DicConstant.PJWHSQLX_01%>|<%=DicConstant.PJWHSQLX_02%>|<%=DicConstant.PJWHSQLX_03%>|<%=DicConstant.PJWHSQLX_04%>" >
											<option value=-1>--</option>
									</select>	
								</td>
								<td><label>申请时间</label></td>
								<td>
									<input type="text" id="APPLICATION_TIME_BEGIN" name="APPLICATION_TIME_BEGIN" style="width: 75px;" datasource="APPLICATION_TIME" datatype="1,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="APPLICATION_TIME_END" name="APPLICATION_TIME_END" style="width: 75px; margin-left: -30px;"
                                    datasource="APPLICATION_TIME" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                </td>
							</tr>
							<tr>
								<td><label>申请单位代码</label></td>
								<td>
									<input type="text" id="CODE" name="CODE" datatype="1,is_null,100" dataSource="O.CODE" operation="like" />
								</td>
								<td><label>申请单位名称</label></td>
								<td>
									<input type="text" id="APPLICATION_WORK" name="APPLICATION_WORK" datatype="1,is_null,100" dataSource="APPLICATION_WORK" operation="like" />
								</td>
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
				<div id="page_contract">
					<table style="display:none; width: 100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="fm-searchContract">
						<thead>
							<tr>
								<th type="single" name="APPLICATION_ID" style="display: none;"></th>
								<th fieldname="APPLICATION_NO" refer="showDetails"  colwidth="160px">申请单号</th>
								<th fieldname="APPLICATION_WORK">申请单位</th>
								<th fieldname="APPLICATION_INFOMATION">申请人联系方式</th>
								<th fieldname="APPLICATION_TYPE" colwidth="120px">申请单类型</th>
								<th fieldname="APPLICATION_STATUS" colwidth="90px">申请单状态</th>
								<th fieldname="APPLICATION_TIME" colwidth="130px">申请时间</th>
								<th colwidth="60" type="link" title="[审核]" action="doAudit">操作</th>
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
<script type="text/javascript">
$(function(){

    $("#btn-search").click(function(){
    	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/applicationSelectByAudit.ajax";
        var $f = $("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
    });

});

//点击盛情单号显示详情
function showDetails(cellObj){
	return "<a href='#' onclick='javascript:openDetilsPage(this)' style='color:red'>" + cellObj.text() + "</a>"
}

//打开详细信息页面
function openDetilsPage(obj){
	$tr = $(obj).parent().parent().parent();
	$tr.find("input:radio").prop("checked","true");
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/dealer/part/mainTenanceApplication/mainTenanceApplicationForDetails.jsp", "forDetailsPage", "申请单详情", options);
}

function doAudit(rowObj){
	$("input:radio",rowObj).prop("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/dealer/part/mainTenanceApplication/mainTenanceApplicationForEDAudit.jsp", "forDetailsPage", "申请单详情", options);
}
</script>
</html>
