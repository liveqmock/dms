<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件档案维护</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/search.ajax";
var syncUrl = "<%=request.getContextPath()%>/sysmng/usermng/UserMngAction/synchronize.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:430,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
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
		var options = {max:false,width:790,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/jcsjgl/jcxxgl/pjdaxz.jsp?action=1", "pjdawh", "配件档案新增", options);
	});
	
});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/jcsjgl/jcxxgl/pjdaxz.jsp?action=2", "editPjda", "修改配件档案", diaAddOptions);
}


var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	
	var url = deleteUrl + "?partid="+$(rowobj).attr("PART_ID")+"&status="+$(rowobj).attr("STATUS");

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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件档案维护</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="in-pjdm" name="in-pjdm" datasource="PART_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <!-- 表选字典 -->
					    <td><input type="text" id="in-pjgys"  name="in-pjgys" kind="dic" src="E#1=供应商1:2=供应商2:3=供应商3" datasource="PART_NAME"  datatype="1,is_null,30" operation="="  /></td>
						 <td><label>配件类型：</label></td>
					    <td><input type="text" id="in-pjlx"  name="in-pjlx" kind="dic" src="E#1=类型一:2=类型二" datasource="PART_TYPE"  datatype="1,is_null,30" /></td>
					</tr>
					<tr>
					   
					    <td><label>配件属性：</label></td>
					    <td><input type="text" id="in-pjsx" name="in-pjsx" kind="dic" src="E#1=易损件:2=常用件:3=非常用件" datasource="ATTRIBUTE" datatype="1,is_null,30" /></td>
					    <td><label>配件状态：</label></td>
					    <td>
					    	<select type="text" id="in-pjzt"  name="in-pjzt" datasource="STATUS" kind="dic" src="QYBS" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>是否直发：</label></td>
					    <td>
					    	<select type="text" id="in-sfzf"  name="in-sfzf" kind="dic" src="SF" datasource="IF_DIRECT"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><label>是否保外：</label></td>
					    <td>
					    	<select type="text" id="in-sfbw" name="in-sfbw" kind="dic" src="SF" datasource="IF_OUT" datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>是否可订：</label></td>
					    <td>
					    	<select type="text" id="in-sfkd"  name="in-sfkd" kind="dic" src="SF" datasource="IF_BOOK"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					    <td><label>是否回运：</label></td>
					    <td>
					    	<select type="text" id="in-sfkd"  name="in-sfkd" kind="dic" src="SF" datasource="IF_RETURN"  datatype="1,is_null,30" >
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
							<th fieldname="PART_NO" >参考图号</th>
							<th fieldname="UNIT" colwidth="50">计量单位</th>
							<th fieldname="MIN_PACK" colwidth="70">最小包装数</th>
							<th fieldname="MIN_UNIT" colwidth="80">最小包装单位</th>
							<th fieldname="PART_TYPE" >配件类型</th>
							<th fieldname="ATTRIBUTE" >配件属性</th>
							<th fieldname="IF_DIRECT" >是否直发</th>
							<th fieldname="IF_OUT" >是否保外</th>
							<th fieldname="IF_BOOK" >是否可订</th>
							<th fieldname="IF_RETURN" >是否回运</th>
							<th fieldname="STATUS" >状态</th>
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