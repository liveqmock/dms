<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权规则维护</title>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorRuleMngAction/searchLevelCode.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorRuleMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:false,width:760,height:700,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
var levelRows;
//查询按钮响应方法
 $(function(){
   //查询规则级别数据
   search();
//查询按钮响应
	$("#btn-search").bind("click", function(event){
		doSearch();
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
 
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/preauthorrule/PreauthorRuleAdd.jsp?action=1", "PreauthorRule", "预授权规则新增", options);
	});
});

function  search(){
			var $f = $("#fm-search");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
		var searchLevelUrl = "<%=request.getContextPath()%>/service/basicinfomng/PreauthorLevelMngAction/search.ajax";
		// alert(searchUrl);
	    //doNormalSubmit($f,addUrl,"",sCondition,searchPartTabsBack);
	    sendPost(searchLevelUrl,"","",searchLevelTabsBack,"false");
}
function searchLevelTabsBack(res)
{

	
	try
	{
		levelRows = res.getElementsByTagName("ROW");	
		 
	 	if(levelRows && levelRows.length > 0)
		{
/* 			var newDiv=$("<div id='"+id+"_content'></div>");
			var newForm=$("<form id='"+id+"_form' ></form>");
			var newTable=$("<table id='"+id+"_table' refer='true' class='editTable' ref='"+id+"_content' style='width:100%;'></table>");
			*/
			var newTable=$("<table width='100%' id='tab-list' name='tab-list' style='display: none' ref='page-list' refQuery='tab-search' pageRows='10'></table>");
			var newthead=$("<thead></thead>");	 
			var newTr2=$("<tr></tr>");	 
			var newTh0=$("<th type='single' name='XH' style='display:none'></th>");	
			var newTh1=$(" <th colwidth='85' type='link' title='[编辑]'  action='doUpdate' >操作</th>");				
			var newTh4=$("<th fieldname='PREAUTHO_NAME' >授权规则</th>");	
			var newtbody=$("<tbody></tbody>");			
		    newTr2.append(newTh0);	
		    newTr2.append(newTh4);	
		    var tb_length=0;   
			for(var i=0;i<levelRows.length;i++)
			{  
			  			//var objXML;
				if (typeof(levelRows[i]) == "object") objXML = levelRows[i];
				else objXML = $.parseXML(levelRows[i]);
				 
			    var levelName =getNodeValue(objXML, "LEVEL_NAME", 0);//授权级别名称
				var levelCode =getNodeValue(objXML, "LEVEL_CODE", 0);//授权级别代码	
				//var newTh1=$("<th>"+LEVEL_NAME+"</th>");	
				var newTh3=$("<th fieldname='LEVELCODE"+levelCode+ "'  refer='createInputSt'>"+levelName+"</th>");	
				newTr2.append(newTh3);
			
				tb_length++;
				   
			} 
			newTr2.append(newTh0);
			newTr2.append(newTh1);
			newthead.append(newTr2);
			newTable.append(newthead);
			newTable.append(newtbody);
			$("#page-list").append(newTable);
		}  
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}	

function createInputSt(obj)
{
	if(obj.text())
	{
		return "<input type='checkbox' name='QUANTITY' width='10px'  checked='true'   disabled='disabled' / >";
	}else
	{
		return "<input type='checkbox' name='QUANTITY' width='10px'     disabled='disabled' / >";
	}
    
}
   
function doUpdate(rowobj){
	$("td input[type='radio']",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/preauthorrule/PreauthorRuleAdd.jsp?action=2", "PreauthorRule", "索赔基本参数编辑", options);

}

//查询方法(汇总)
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
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
		//doSubmitCall(url,$("queryBtn"),1,sCondition,"tabList2",columns_1);
		//sendPost(searchUrl,"","",searchPartTabsBack,"false");
}  

</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;预授权规则维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
					<tr>
						<td><label>授权规则：</label></td>
						<td><input type="text" id="SQGZ" datatype="1,is_null,100"/></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
						</li>
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
			
		</div>
	</div>
	</div>
</div>
</body>
</html>