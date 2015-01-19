<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件替换件关系</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaReplacementAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaReplacementAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:850,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象s
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
    	 * @"yhlb":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-pjlb");
	});

	
	//新增方法
	$("#btn-xz").bind("click",function(event){
//		var options = {max:true,width:850,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/ptbareplacement/ptbareplacementAdd.jsp?action=1", "pjzpxz", "配件替换件关系信息新增", options);
							      
	});							  
	
});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbareplacement/ptbareplacementEdit.jsp?action=2", "editPjzp", "修改配件替换件关系信息", diaAddOptions);
}


var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	
	var url = deleteUrl + "?replace_id="+$(rowobj).attr("REPLACE_ID")+"&status="+$(rowobj).attr("STATUS");

	/**
	 * sendPost:提交一般请求方法
	 * @url:提交请求url路径
	 * @"delete":提交请求句柄id
	 * @"":提交请求参数，可为空（json格式）
	 * @deleteCallBack：提交请求后台执行成功后，页面回调函数
	 * @"true":是否需要confirm确认对话框，"true"为弹出确认框
	 */
	sendPost(url,"delete","",deleteCallBack,"true");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if($row) 
		$("#btn-cx").trigger("click");		
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件替换件关系</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="dia-PART_CODE" name="dia-PART_CODE" datasource="PART_CODE" datatype="1,is_digit_letter,300" operation="like" /></td>
					    <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="dia-PART_NAME"  name="dia-PART_NAME" datasource="PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>					 
					</tr>
					<tr>
						<td><label>替换配件代码：</label></td>
					    <td><input type="text" id="dia-RPART_CODE" name="dia-RPART_CODE" datasource="RPART_CODE" datatype="1,is_digit_letter,300" operation="like" /></td>
					    <td><label>替换配件名称：</label></td>					   
					    <td><input type="text" id="dia-RPART_NAME"  name="dia-RPART_NAME" datasource="RPART_NAME"  datatype="1,is_null,300" operation="like"  /></td>					 
					</tr>		
					<tr>
						<td><label>有效标识：</label></td>
						<td>
				    	<select type="text" class="combox" id="in-status" name="in-status" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,100" >	
				     		<option value="-1" selected>--</option>			 
				    	</select>
			    		</td>
					</tr>		
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_yhlb" refQuery="fm-pjcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>		
							<th fieldname="PART_CODE" >配件代码</th>							
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="RPART_CODE" >替换配件代码</th>
							<th fieldname="RPART_NAME" >替换配件名称</th>
							<th fieldname="REMARKS" >备注</th>		
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>