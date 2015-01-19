<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供货关系查询</title>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 信息查询  &gt; 采购相关   &gt; 供货关系查询</h4>
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
						<td><label>供货周期：</label></td>
					    <td>
					    	<input  id="applyCycle" name="applyCycle" kind="dic" datasource="rl.apply_cycle" src="T#USER_PARA_CONFIGURE:PARAVALUE2:PARAVALUE1{PARAVALUE1}:1=1 AND APPTYPE='3001' AND STATUS='100201'" 
					    	    datatype="1,is_null,6" operation="=" />
					    </td>
					</tr>
					<tr>
						<td><label>供应商代码：</label></td>
					    <td><input type="text" id="supplierCode" name="supplierCode" datasource="s.SUPPLIER_CODE" datatype="1,is_digit_letter,300" operation="like" /></td>
					    <td><label>供应商名称：</label></td>					   
					    <td><input type="text" id="supplierName"  name="supplierName" datasource="s.SUPPLIER_NAME"  datatype="1,is_null,300" operation="like"  /></td>
						<td><label>配件标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="in-part_identify" name="in-part_identify" kind="dic" src="YXBS" datasource="rl.PART_IDENTIFY" datatype="1,is_null,300" >	
				     		<option value="100201" selected>有效</option>				 
				    	</select>
			    		</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-clear">重&nbsp;&nbsp;置</button></div></div></li>
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
<!--							<th fieldname="PLAN_PRICE"   refer="amountFormat" align="right">计划价格</th>	-->
<!--							<th fieldname="APPLY_CYCLE"  refer="amountFormat" align="right">采购价格</th>	-->
<!--							<th fieldname="IF_STREAM" >是否需要流水号</th>-->
							<th fieldname="APPLY_CYCLE" >供货周期</th>	
							<th fieldname="PART_IDENTIFY" >配件标识</th>
							<th fieldname="PART_STATUS" >配件状态</th>
							<th fieldname="UPDATE_USER" >修改人</th>	
							<th fieldname="UPDATE_TIME" >修改时间</th>	
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction/search.ajax";
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
	
	// 重置
	$("#btn-clear").click(function(){
		$("input", "#tab-pjcx").each(function(index, obj){
			$(obj).val("");
		})
		$("#in-part_identify").val("100201");
	});
});

//金额格式化
function amountFormat(obj){
	return amountFormatNew($(obj).html());
}
        
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
    if(id == 'applyCycle'){									
        $('#apply_cycle').val($row.attr('PARAVALUE1'));
    }	
    
    if(id == 'dia-supplier_code'){									
        $('#dia-supplier_id').val($row.attr('SUPPLIER_ID'));
        $('#dia-supplier_name').val($row.attr('SUPPLIER_NAME'));
    }	
    if(id == 'dia-paravalue1'){									
        $('#dia-apply_cycle').val($row.attr('PARAVALUE1'));
    }	
	return true;
}        
</script>