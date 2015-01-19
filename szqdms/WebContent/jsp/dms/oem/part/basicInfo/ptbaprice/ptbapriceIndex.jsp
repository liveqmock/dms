<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件价格管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/selectThirdPrice.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/delete.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
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
	
	 $('#btn-importExcel').bind('click',function(){
	//配件档案导入按钮响应
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页                               
        importXls("PT_BA_PRICE_TMP","",7,3,"/jsp/dms/oem/part/basicInfo/ptbaprice/importSuccess.jsp");
	});   
	
	//导出
	$("#btn-downLoad").click(function(){
		var $f = $("#fm-pjcx");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	
    	$("#params").val(sCondition);
        //var url = encodeURI("<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/downloadPrice.do");
       // window.location.href = url;
        $("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/downloadThirdPrice.ajax");
        $("#exportFm").submit();
    });
});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbaprice/ptbapriceAdd.jsp?action=2", "editPjda", "修改配件价格", diaAddOptions);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件价格管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>						
					    <td><label>配件代码：</label></td>					   
					    <td><input type="text" id="in-part_code"  name="in-part_code" datasource="PART_CODE"  datatype="1,is_null,300" operation="like"  /></td>
						 <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="in-part_name"  name="in-part_name" datasource="PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>
<!--					    <td><label>供应商名称：</label></td>					   -->
<!--					    <td><input type="text" id="in-part_name"  name="in-part_name" datasource="C.SUPPLIER_NAME"  datatype="1,is_null,300" operation="like"  /></td>-->
					</tr>						
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
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
<!--							<th fieldname="PART_ID" >配件主键</th>-->
							<th fieldname="PART_CODE" >配件代码</th>
							<th fieldname="PART_NAME" >配件名称</th>		
<!--							<th fieldname="PCH_PRICE"  refer="amountFormat" align="right">采购价格</th>	-->
							<th fieldname="PLAN_PRICE" refer="amountFormat" align="right">计划价格</th>							
							<th fieldname="SALE_PRICE" refer="amountFormat" align="right">销售价格</th>		
							<th fieldname="RETAIL_PRICE" refer="amountFormat" align="right">零售价格</th>	
<!--							<th fieldname="SUPPLIER_NAME" >供应商名称</th>	-->
							<th colwidth="50" type="link" title="[编辑]"  action="doUpdate" >操作</th>
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