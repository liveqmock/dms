<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件价格查询</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/search.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:850,height:510,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){
	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"btn-cx",1,sCondition,"tab-pjlb");
	});

	  
	//导出
	$("#btn-downLoad").click(function(){
		var $f = $("#fm-pjcx");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/part/basicInfoMng/PtBaInfoAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
    
});


//金额格式化
function amountFormat(obj){
    return amountFormatNew($(obj).html());
}
 
//最小包装
function toAppendStr(obj){
    var $tr =$(obj).parent();
    return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt;  销售相关   &gt; 配件价格查询</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="in-PART_CODE" name="in-PART_CODE" datasource="PART_CODE" datatype="1,is_null,300" operation="like" /></td>
					    <td><label>配件名称：</label></td>
					    <td><input type="text" id="in-PART_NAME"  name="in-PART_NAME" datasource="PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>
						<td><label>配件状态：</label></td>
					    <td>
					    	<select type="text" id="in-pjzt"  name="in-pjzt" datasource="PART_STATUS" kind="dic" src="PJZT" datatype="1,is_null,30" >
					    		<option value="200601" selected>有效</option>
					    	</select>
					    </td>
					</tr>
					<tr>
						<td><label>直发类型：</label></td>
					    <td>
						    <input type="text" id="in-DIRECT_TYPE_NAME" name="in-DIRECT_TYPE_NAME" datasource="DIRECT_TYPE_CODE" datatype="1,is_null,300" operation="=" 
						    	isreload="true" kind="dic"
						    	src="T#PT_BA_DIRECT_TYPE:TYPE_CODE:TYPE_NAME{TYPE_CODE,TYPE_NAME}:1=1 AND STATUS = 100201"
						    />
					    </td>
					    <td><label>所属总成：</label></td>
					    <td>
					    	<select type="text" id="in-BELONG_ASSEMBLY"  name="in-BELONG_ASSEMBLY" datasource="BELONG_ASSEMBLY" kind="dic" src="PJZCLB" datatype="1,is_null,30" >
					    		<option value="-1" selected="selected">--</option>
					    	</select>
					    </td>
						<td><label>返利类别：</label></td>
					    <td>
							<input type="text" id="in-REBATE_TYPE" name="in-REBATE_TYPE" datasource="REBATE_TYPE" datatype="1,is_null,300" operation="=" 
						    	isreload="true" kind="dic"
						    	src="T#USER_PARA_CONFIGURE:PARAKEY:PARAKEY{PARAKEY,PARAKEY}:1=1 AND STATUS = 100201 AND APPTYPE = 'FLLB'"
						    />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
<!--						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-downLoad" >导&nbsp;&nbsp;出</button></div></div></li>-->
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
							<th fieldname="PART_NAME" colwidth="300">配件名称</th>
							<th fieldname="UNIT" colwidth="50" freadonly="true">计量单位</th>
                            <th fieldname="MIN_PACK" colwidth="50" refer="toAppendStr" freadonly="true">最小包装</th>
<!--							<th fieldname="PLAN_PRICE"  refer="amountFormat" align="right">计划价格</th>-->
							<th fieldname="SALE_PRICE"  refer="amountFormat" align="right">销售价格</th>
							<th fieldname="RETAIL_PRICE"  refer="amountFormat" align="right">零售价</th>
							<th fieldname="DIRECT_TYPE_NAME" >直发类型</th>
							<th fieldname="BELONG_ASSEMBLY" >所属总成</th>
							<th fieldname="REBATE_TYPE" >返利类别</th>
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