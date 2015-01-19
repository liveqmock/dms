<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务追偿价格管理</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/searchSeRePrice.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:750,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
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
	
	
	//导入
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
    	
        $("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/expSeRePrice.ajax");
        $("#exportFm").submit();
    });

});



//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbareprice/ptbarepriceAdd.jsp?action=2", "editPjda", "修改服务追偿价格", diaAddOptions);
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
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 服务追偿价格管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>						
					    <td><label>配件代码：</label></td>					   
					    <td><input type="text" id="in-PART_CODE"  name="in-PART_CODE" datasource="PART_CODE"  datatype="1,is_null,100" operation="like"  /></td>
						 <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="in-PART_NAME"  name="in-PART_NAME" datasource="PART_NAME"  datatype="1,is_null,100" operation="like"  /></td>
					</tr>						
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
<!--						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-importExcel" >导&nbsp;&nbsp;入</button></div></div></li>-->
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
							<th fieldname="SE_REPRICE" refer="amountFormat" align="right">服务追偿价格</th>
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