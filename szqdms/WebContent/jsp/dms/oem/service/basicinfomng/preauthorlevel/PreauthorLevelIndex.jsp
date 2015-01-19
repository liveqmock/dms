<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权级别维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorLevelMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorLevelMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:false,width:750,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//查询按钮响应方法
 $(function(){
/*  $("#btn-search").bind("click", function(event){
		doSearch();
		}); */
	doSearch();	
		//新增按钮响应
	$("#btn-add").bind("click", function(event){
 
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/preauthorlevel/PreauthorLevelAdd.jsp?action=1", "PreauthorLevel", "预授权级别新增", options);
	});
});
function doSearch()
{
	var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
    	
		doFormSubmit($f,searchUrl,"",1,sCondition,"tab-list");
}

function doUpdate(rowobj){
	$("td input",$(rowobj)).attr("checked",true);

	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/preauthorlevel/PreauthorLevelAdd.jsp?action=2", "PreauthorLevel", "预授权级别编辑", options);
}
</script>
</head>
<body>
	<div id="layout" width="100%">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left">
			<img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;预授权级别维护
		</h4>
	<!-- 		<div class="pageHeader">
		<form id="fm-search" method="post">	
		</form>
		</div> -->
		
		<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
		<!-- 			<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="T.ORG_CODE"  datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
					</tr>	 -->				
				</table>
				<div class="subBar">
					<ul>
			<!-- 	     <li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
						</li> -->
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
			<div class="pageContent">
				<div id="page-list">
				<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list"   refQuery="tab-search" pageRows="10">
						<thead>
							<tr>
							<th type="single" name="XH" style="display:none"></th>
					 
							<th fieldname="LEVEL_CODE" >预授权级别代码</th>
							<th fieldname="LEVEL_NAME" >预授权级别名称</th>
													 
								<th colwidth="85" type="link" title="[修改]"  action="doUpdate">操作</th>
							</tr>
						</thead>
						<tbody>
						
						</tbody>
					</table>
				</div>
			</div>
		</div>
</body>
</html>