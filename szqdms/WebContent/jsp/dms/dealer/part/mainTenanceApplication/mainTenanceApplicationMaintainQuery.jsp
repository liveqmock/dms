<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.org.mvc.context.ActionContext"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>待维护配件查询 </title>
</head>
<body>
	<div id="layout" style="width: 100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：配件管理 &gt; 配件信息维护申请管理 &gt; 待维护配件查询 
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
								<td><label>申请单号：</label></td>
								<td><input type="text" id="APPLICATION_NO" name="APPLICATION_NO" datatype="1,is_digit_letter,30" dataSource="A.APPLICATION_NO" operation="like" /></td>
								<td><label>车辆识别码：</label></td>
								<td><input type="text" id="VIN" name="VIN" datatype="1,is_digit_letter,30" dataSource="PIE.VIN" operation="like" /></td>
							</tr>
							<tr>
								<td><label>配件代码：</label></td>
								<td><input type="text" id="PART_NO" name="PART_NO" datatype="1,is_null,300" dataSource="PART_NO" operation="like" /></td>
								<td><label>配件名称：</label></td>
								<td><input type="text" id="PART_NAME" name="PART_NAME" datatype="1,is_null,300" dataSource="PART_NAME" operation="like" /></td>
							</tr>
							<tr>
								<td><label>申请时间：</label></td>
								<td colspan="3">
									<input type="text" id="APPLICATION_TIME_BEGIN" name="APPLICATION_TIME_BEGIN" style="width: 75px;" datasource="A.APPLICATION_TIME" datatype="1,is_null,30"
                                    onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date" operation=">="/> 
                                    <span style="float: left; margin-left: -50px; margin-top: 5px;">至</span>
                                    <input type="text" group="in-applydateq,in-applydateh" id="APPLICATION_TIME_END" name="APPLICATION_TIME_END" style="width: 75px; margin-left: -30px;"
                                    datasource="A.APPLICATION_TIME" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:true,dateFmt:'yyyy-MM-dd'})" kind ="date"  operation="<="/>
                                </td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export" >导&nbsp;&nbsp;出</button></div></div></li>
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
								<th fieldname="APPLICATION_NO" colwidth="160px">申请单号</th>
								<th fieldname="APPLICATION_TIME" colwidth="130px">申请时间</th>
								<th fieldname="PART_NO" colwidth="130px">配件代码</th>
								<th fieldname="PART_NAME" colwidth="150px">配件名称</th>
								<th fieldname="VIN" colwidth="150px">车辆识别码</th>
								<th fieldname="PROCESS_ROUTE">工艺路线</th>
								<th fieldname="OWN_FIRST_LEVEL">所属一级</th>
								<th fieldname="OWN_SECOND_LEVEL">所属二级</th>
								<th fieldname="SUPPLIER_NAME">供应商名称</th>
								<th fieldname="ENGINEERING_DEPARTMENT_REMARK">技术科审核备注</th>
								<th fieldname="PURCHASING_DEPARTMENT_REMARK">采供科审核备注</th>
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

<form id="exportFm" method="post" style="display:none">
	<input type="hidden" id="params" name="data" datasource="data" />
</form>


<script type="text/javascript">
$(function(){

    $("#btn-search").click(function(){
    	var searchURL = "<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationMaintainQueryAction/maintainQuery.ajax";
        var $f = $("#fm-searchContract");
        var sCondition = {};
        sCondition = $f.combined() || {};
        doFormSubmit($f, searchURL, "btn-search", 1, sCondition, "tab-contract");
    });

    // 导出
    $("#btn-export").click(function(){
    	var $f = $("#fm-searchContract");
    	if (submitForm($f) == false) return false;
    	var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
    	$("#exportFm").attr("action","<%=request.getContextPath()%>/part/mainTenanceApplication/MainTenanceApplicationMaintainQueryAction/exportExcel.do");
    	$("#exportFm").submit();
    })

    
});

</script>
</html>
