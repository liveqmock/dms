<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<%@ page import="com.org.framework.Globals" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件采购价格管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
//var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction/searchPrice.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:850,height:310,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var url = searchUrl + "?if_null="+$("#in-if_null").val();
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"yhlb":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,url,"btn-cx",1,sCondition,"tab-pjlb");
	});

	
	//新增方法
	$("#btn-xz").bind("click",function(event){
		var options = {max:false,width:850,height:310,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo/ptbapchprice/ptbapchpriceAdd.jsp?action=1", "pjzpxz", "配件配件采购价格信息新增", options);
							      
	});		
	
	//采购价格导入 
	$('#btn-importExcel').bind('click',function(){
        importXls("PT_BA_PCH_TMP","",7,3,"/jsp/dms/oem/part/basicInfo/ptbapchprice/importSuccess.jsp");
	});   
	// 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-pjcx");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
    	
    	var diaUrl="<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction/pchPriceDownload.ajax"
    	var url=diaUrl+"?if_null="+$("#in-if_null").val();
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });		
});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbapchprice/ptbapchpriceAdd.jsp?action=2", "editPjzp", "修改配件配件采购价格", diaAddOptions);
}


var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	
	var url = deleteUrl + "?relation_id="+$(rowobj).attr("RELATION_ID")+"&status="+$(rowobj).attr("STATUS");

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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件采购价格管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="partCode" name="partCode" datasource="p.PART_CODE" datatype="1,is_null,300" operation="like" /></td>
					    <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="partName"  name="partName" datasource="p.PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>
<!--						<td><label>供货周期：</label></td>-->
<!--					    <td>-->
<!--					    	<select id="applyCycle" name="applyCycle" kind="dic" -->
<!--					    		src="T#USER_PARA_CONFIGURE:PARAKEY:PARAVALUE1:1=1 AND APPTYPE='3001' AND STATUS='100201'" -->
<!--					    		datasource="rl.apply_cycle" datatype="1,is_null,6" operation="=" >-->
<!--					    		<option value="-1" selected>-----</option>-->
<!--					    	</select>-->
<!--					    </td>-->
					</tr>
					<tr>
						<td><label>供应商代码：</label></td>
					    <td><input type="text" id="supplierCode" name="supplierCode" datasource="s.SUPPLIER_CODE" datatype="1,is_null,300" operation="like" /></td>
					    <td><label>供应商名称：</label></td>					   
					    <td><input type="text" id="supplierName"  name="supplierName" datasource="s.SUPPLIER_NAME"  datatype="1,is_null,300" operation="like"  /></td>
<!--						<td><label>有效标识：</label></td>					   -->
<!--					    <td>-->
<!--					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="rl.STATUS" datatype="1,is_null,6" operation="=" >-->
<!--					    		<option value="100201" selected>有效</option>-->
<!--					    	</select>-->
<!--					    </td>-->
					</tr>
					<tr>
						<td><label>价格是否为空：</label></td>
						<td>
							<select type="text" id="in-if_null" name="in-if_null" kind="dic" src="SF"  datatype="1,is_null,30" >
					    		<option value="-1" selected>--</option>
					    	</select>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-importExcel" >导&nbsp;&nbsp;入</button></div></div></li>
						
<!--						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>-->
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>

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
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
							<th fieldname="PCH_PRICE"  refer="amountFormat" align="right">不含税采购价格</th>	
<!--							<th fieldname="STATUS" >状态</th>-->
							<th colwidth="55" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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