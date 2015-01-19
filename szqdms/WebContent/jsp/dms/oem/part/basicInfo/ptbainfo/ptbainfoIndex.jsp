<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件档案维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:true,width:850,height:510,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
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
		var options = {max:false,width:850,height:510,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/ptbainfo/ptbainfoAdd.jsp?action=1", "pjdawh", "配件档案新增", options);
	});		
	
	
	//配件模版导出按钮响应
    $('#btn-expTpl').bind('click',function(){
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=partFileTemplet.xls");
        window.location.href = url;
    });     
    
    $('#btn-importExcel').bind('click',function(){
	//配件档案导入按钮响应
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页                               
        importXls("PT_BA_INFO_TMP","",24,3,"/jsp/dms/oem/part/basicInfo/ptbainfo/importSuccess.jsp");
	});   
	
	  
	//导出
	$("#btn-downLoad").click(function(){
		var $f = $("#fm-pjcx");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
    	
        $("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/download.ajax");
        $("#exportFm").submit();
    });
    
});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbainfo/ptbainfoAdd.jsp?action=2", "editPjda", "修改配件档案", options);
}


var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	
	var url = deleteUrl + "?partid="+$(rowobj).attr("PART_ID");

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
//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
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
					    <td><input type="text" id="in-pjdm" name="in-pjdm" datasource="PART_CODE" datatype="1,is_null,300" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <!-- 表选字典 -->
					    <td><input type="text" id="in-pjgys"  name="in-pjgys" datasource="PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>
						 <td><label>配件类型：</label></td>
					    <td><input type="text" id="in-pjlx"  name="in-pjlx" kind="dic" src="PJLB" datasource="PART_TYPE"  datatype="1,is_null,300" /></td>
					</tr>
					<tr>
					   
					    <td><label>配件属性：</label></td>
					    <td><input type="text" id="in-pjsx" name="in-pjsx" kind="dic" src="PJSX" datasource="ATTRIBUTE" datatype="1,is_null,300" /></td>
					    <td><label>配件状态：</label></td>
					    <td>
					    	<select type="text" id="in-pjzt"  name="in-pjzt" datasource="PART_STATUS" kind="dic" src="PJZT" datatype="1,is_null,300" >
					    		<option value="200601" selected>有效</option>
					    	</select>
					    </td>
					    <td><label>是否保外：</label></td>
					    <td>
					    	<select type="text" id="in-sfbw" name="in-sfbw" kind="dic" src="SF" datasource="IF_OUT" datatype="1,is_null,300" >
					    		<option value="-1" selected>--</option>
					    	</select>
					    </td>
					</tr>
<!--					<tr>-->
<!--						<td><label>有效标识：</label></td>			   		 	-->
<!--			   		 	<td>-->
<!--				    	<select type="text" class="combox" id="dia-STATUS" name="dia-STATUS" kind="dic" src="YXBS" datasource="STATUS" datatype="1,is_null,10" >	-->
<!--				     		<option value="-1" selected>--</option>				 -->
<!--				    	</select>-->
<!--					</tr>-->
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-expTpl" >模&nbsp;&nbsp;板</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-importExcel" >导&nbsp;&nbsp;入</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-downLoad" >导&nbsp;&nbsp;出</button></div></div></li>
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
							<th fieldname="PART_TYPE" >配件类型</th>
							<th fieldname="UNIT" >计量单位</th>
							<th fieldname="PART_STATUS" >配件状态</th>
							<th fieldname="POSITION_NAME" >一级总成</th>
							<th fieldname="F_POSITION_NAME" >二级总成</th>
							<th fieldname="UPDATE_USER" >修改人</th>
							<th fieldname="UPDATE_TIME" >修改时间</th>
<!--							<th fieldname="STATUS" >状态</th>-->
							
							
<!--							<th fieldname="UNIT" colwidth="50">计量单位</th>-->
<!--							<th fieldname="PART_TYPE" >配件类型</th>-->
<!--							<th fieldname="BELONG_ASSEMBLY" >所属七大总成</th>-->
<!--							<th fieldname="IF_STREAM" >配件流水号</th>-->
<!--							<th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价格</th>-->
<!--							<th fieldname="ARMY_PRICE" refer="amountFormat" align="right">军品价格</th>-->
<!--							<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>-->
<!--							<th fieldname="PCH_PRICE" refer="amountFormat" align="right">采购价格</th>-->
<!--							<th fieldname="RETAIL_PRICE" refer="amountFormat" align="right">零售价</th>-->
<!--							<th fieldname="CREATE_TIME" align="right">创建时间</th>-->
<!--							<th fieldname="STATUS" >状态</th>-->
							<th colwidth="100" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>