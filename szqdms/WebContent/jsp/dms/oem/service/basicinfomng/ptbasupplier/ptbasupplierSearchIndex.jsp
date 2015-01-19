<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供应商信息管理</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaSupplierAction/partSearch.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaSupplierAction/deleteService.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:true,width:850,height:450,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-pjlb");
	});
	 $("#btn-cx").trigger("click");
});


</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 供应商信息管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>供应商代码：</label></td>					   
					    <td><input type="text" id="in-SUPPLIER_CODE"  name="in-SUPPLIER_CODE" datasource="SUPPLIER_CODE"  datatype="1,is_null,300" operation="like"  /></td>
						<td><label>供应商名称：</label></td>
					    <td><input type="text" id="in-SUPPLIER_NAME" name="in-SUPPLIER_NAME" datasource="SUPPLIER_NAME" datatype="1,is_null,300" operation="like" /></td>					    
						<td><label>服务标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="in-se_identify" name="in-se_identify" kind="dic" src="YXBS" datasource="SE_IDENTIFY" datatype="1,is_null,300" >	
				     		<option value="100201" selected>有效</option>				 
				    	</select>
			    		</td>
					</tr>
					
					
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
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
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>								
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>	
							<th fieldname="ERP_NO" >ERP编码</th>		
							<th fieldname="SE_IDENTIFY" >服务标识</th>						
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>