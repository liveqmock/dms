<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<%@ page import="com.org.mvc.context.ActionContext"%>
<%@ page import="com.org.framework.base.BaseDAO"%>
<%@ page import="com.org.dms.dao.part.mainTenanceApplication.MainTenanceApplicationDao"%>
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	ActionContext atx = ActionContext.getContext();
	String orgName = MainTenanceApplicationDao.getInstance(atx).getOrgName(user.getOrgId());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>维护申请</title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件管理 &gt; 配件信息维护申请管理 &gt; 配件编号明细导出
		</h4>
		<div class="page">
			<div class="pageHeader">
				<form method="post" id="fm-searchContract">
					<!-- 申请单类型 -->
					<input type="hidden" id="APPLICATION_TYPE" name="APPLICATION_TYPE" dataSource="APPLICATION_TYPE" value="<%=DicConstant.PJWHSQLX_01%>" />
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
								<td><label>申请时间</label></td>
								<td>
									<input type="text" id="APPLICATION_TIME_BEGIN" name="APPLICATION_TIME_BEGIN" style="width: 75px;" datasource="APPLICATION_TIME" datatype="1,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="APPLICATION_TIME_END" name="APPLICATION_TIME_END" style="width: 75px; margin-left: -30px;"
                                    datasource="APPLICATION_TIME" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                </td>
								<td><label>审核完成时间</label></td>
								<td>
									<input type="text" id="PURCHASING_DEPARTMENT_DATE_BEGIN" name="PURCHASING_DEPARTMENT_DATE_BEGIN" style="width: 75px;" datasource="PURCHASING_DEPARTMENT_DATE" datatype="1,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="PURCHASING_DEPARTMENT_DATE_END" name="PURCHASING_DEPARTMENT_DATE_END" style="width: 75px; margin-left: -30px;"
                                    datasource="PURCHASING_DEPARTMENT_DATE" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
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
				<div id="page_contract">
					<table style="display:none; width: 100%;" id="tab-contract" name="tablist" ref="page_contract" refQuery="fm-searchContract">
						<thead>
							<tr>
								<th type="multi" name="APPLICATION_ID" unique="APPLICATION_ID"></th>
								<th fieldname="APPLICATION_NO" refer="showDetails"  colwidth="130px">申请单号</th>
								<th fieldname="APPLICATION_WORK">申请单位</th>
								<th fieldname="APPLICATION_INFOMATION">申请人联系方式</th>
								<th fieldname="APPLICATION_TYPE" colwidth="120px">申请单类型</th>
								<th fieldname="APPLICATION_STATUS" colwidth="80px">申请单状态</th>
								<th fieldname="APPLICATION_TIME" colwidth="120px">申请时间</th>
								<th fieldname="ENGINEERING_DEPARTMENT_REMARK">技术科审核备注</th>
								<th filedname="PURCHASING_DEPARTMENT_REMARK">采供科审核备注</th>
								<th fieldname="PURCHASING_DEPARTMENT_DATE" colwidth="130px">审核完成时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<textarea id="val0" name="multivals" column="1" style="display:none" ></textarea>
	</div>
</body>
<form id="applicationForm_I" style="display:none" method="post">
	<!-- 申请人 -->
	<input type="hidden" name="APPLICATION_ID_I" id="APPLICATION_ID_I" datasource="APPLICATION_ID_I" val=""/>
	<input type="button" id="tempSubBtn" value="提交"/>
</form>

 <form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="APPLICATION_ID_S_E" name="APPLICATION_ID_S_E"></input>
</form>

<script type="text/javascript">
$(function(){

    $("#btn-search").click(function(){
    	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationAction/partInfoApplicationQuery.ajax";
        var $f = $("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
    });

    // 导出
    $("#btn-export").click(function(){
    	var $checkboxSelectArray = $("input:checked","#tab-contract_content tbody");
    	if($checkboxSelectArray.size() == 0 ){
    		parent.alertMsg.warn("请选择需要导出的申请单");
    		return;
    	}else if($checkboxSelectArray.size() > 50){
    		parent.alertMsg.warn("导出申请单数量不能超过50单");
    		return;
    	}
    	$("#APPLICATION_ID_S_E").val($("#val0").val());
    	$("#exportFm").attr("action","<%=request.getContextPath()%>/service/part/mainTenanceApplication/MainTenanceApplicationExportAction/download.do");
    	$("#exportFm").submit();
    });
    
	//下载
	$("#btn-download").bind("click",function(){
		var $f = $("#oldPartform");
		if (submitForm($f) == false) return false;
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
		$("#exportFm").attr("action","<%=request.getContextPath()%>/service/oldpartMng/OldPartSearchAction/download.do");
		$("#exportFm").submit();
	});
});

// 点击盛情单号显示详情
function showDetails(cellObj){
	var applicationId = $(cellObj).parent().attr("APPLICATION_ID");
	return "<a href='#' onclick='javascript:openDetilsPage(" + applicationId + ")' style='color:red'>" + cellObj.text() + "</a>"
}

function doCheckbox(obj){
	var arr = [];
	var $checkbox = $(obj);
	var $tr= $checkbox.parent().parent().parent();
	var APPLICATION_ID = $tr.attr("APPLICATION_ID");
	arr.push(APPLICATION_ID);
	multiSelected($checkbox,arr);
}

// 打开详细信息页面
function openDetilsPage(applicationId){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
    $.pdialog.open(webApps+"/jsp/dms/dealer/part/mainTenanceApplication/mainTenanceApplicationForExportDetails.jsp?applicationId="+applicationId, "forDetailsPage", "申请单详情", options);
}

</script>
</html>
