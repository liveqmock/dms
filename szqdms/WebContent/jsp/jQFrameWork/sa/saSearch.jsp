<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:用户管理
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2012-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<script src="<%=request.getContextPath() %>/lib/plugins/sa/sa.core.js" type="text/javascript"></script>
<title>报表查询</title>
<script type="text/javascript">
//查询提交方法
$(function()
{
	$("#search").click(function(){
		$.sa.searchReport();
	});
});
//列表编辑连接
function doView(rowobj)
{
	$("td input:first",$(rowobj)).attr("checked",true);
	var reportId = $(rowobj).attr("ID");
	var reportName= $(rowobj).attr("REPORT_NAME");
	$.sa.viewReport({"reportId":reportId,"reportName":reportName});
}

var row;
function doDelete(rowobj)
{
	row = $(rowobj);
	var url = "<%=request.getContextPath()%>/OrgPerson/OrgPersonAction.do?method=delete&account="+$(rowobj).attr("ACCOUNT");
	sendPost(url,"delete","",deleteCallBack,"true");
}

function  deleteCallBack(res)
{
	try
	{
		if(row)
			row.remove();
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
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page" >
	<div class="pageHeader" >
			<form method="post" id="fm-reportSearch">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-reportSearch">
				<!-- 定义查询条件 -->
				<tr><td><label>报表名称：</label></td>
				    <td><input type="text" id="in-reportName" name="in-reportName" datasource="REPORT_NAME" datatype="1,is_null,30"   operation="like" value=""/></td>
				</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
			</form>
	</div>
	<div class="pageContent">
		<div id="page_tab-reportlist" >
			<table style="display:none;width:100%;" id="tab-reportlist" name="tablist" ref="page_tab-reportlist" refQuery="tab-reportSearch" pageRows="10" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="ID" >报表标识</th>
							<th fieldname="REPORT_NAME" >报表名称</th>
							<th fieldname="CREATE_BY" >创建人</th>
							<th fieldname="CREATE_DATE">创建时间</th>
							<th fieldname="MEMO">备注</th>
							<th colwidth="85" type="link" title="[查看]|[删除]"  action="doView|doDelete" text-rel="G-B-CZ">操作</th>
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