<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件供货关系管理</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";

var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction/searchService.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPartSupplierRlAction/deleteService.ajax";
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
		var options = {max:false,width:850,height:310,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/ptbapartsupplierrl/ptbapartsupplierrlAdd.jsp?action=1", "pjzpxz", "配件供货关系信息新增", options);
							      
	});		
	
	//批量更新按钮
	$("#btn-batchUpdate").bind("click", function(event){
		var relationIds = $("#val0").val();
		if (relationIds) 
		{
			//通过全选取值      
			var diaUpdateOptions = {max:false,width:400,height:140,mask:true,mixable:true,minable:true,resizable:true,drawable:true};                                                             
			$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/ptbapartsupplierrl/ptbapartsupplierrlBatchUpdate.jsp?relationIds="+relationIds, "更新服务标识", "更新服务标识", diaUpdateOptions);
		} else
		{
			alertMsg.info("请选择记录");
			return false;
		} 
	})					  
	
});


var selectRowObj = [];
//列表编辑连接
function doUpdate(rowobj)
{
	// 此处Bug：主页面行为checkbox，不能根据radio来定位选择行。
	// $("td input[type=radio]",$(rowobj)).attr("checked",true);
	// 修改为用使用变量数组保存选择行信息，供子页面使用
	selectRowObj.push($(rowobj));
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/ptbapartsupplierrl/ptbapartsupplierrlAdd.jsp?action=2", "editPjzp", "修改配件供货关系信息", diaAddOptions);
}


var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	
	var url = deleteUrl + "?relation_id="+$(rowobj).attr("RELATION_ID");

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
//列表复选
function doCheckbox(checkbox){  
	var $t = $(checkbox);
	var arr = [];
   	while($t.get(0).tagName != "TABLE"){
		$t = $t.parent();
	} 
	if($t.attr("id").indexOf("tab-pjlb")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("RELATION_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#yhw"));
	} 
}            
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件供货关系管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>配件代码：</label></td>
					    <td><input type="text" id="partCode" name="partCode" datasource="p.PART_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="partName"  name="partName" datasource="p.PART_NAME"  datatype="1,is_null,30" operation="like"  /></td>
						<td><label>供货周期：</label></td>
					    <td>
					    	<input  id="applyCycle" name="applyCycle" kind="dic" 
					    		src="T#USER_PARA_CONFIGURE:PARAVALUE2:PARAVALUE1{PARAVALUE1}:1=1 AND APPTYPE='3001' AND STATUS='100201'" 
					    	    datatype="1,is_null,6" operation="=" />
					    		
					    	<input type="hidden" id="apply_cycle" name="apply_cycle" datasource="rl.apply_cycle" />
<!--					    		<option value="-1" selected>-----</option>-->
<!--					    	</select>-->
					    </td>
					</tr>
					<tr>
						<td><label>供应商代码：</label></td>
					    <td><input type="text" id="supplierCode" name="supplierCode" datasource="s.SUPPLIER_CODE" datatype="1,is_digit_letter,30" operation="like" /></td>
					    <td><label>供应商名称：</label></td>					   
					    <td><input type="text" id="supplierName"  name="supplierName" datasource="s.SUPPLIER_NAME"  datatype="1,is_null,30" operation="like"  /></td>
						<td><label>服务标识：</label></td>
			   		 	<td>
				    	<select type="text" class="combox" id="in-se_identify" name="in-se_identify" kind="dic" src="YXBS" datasource="rl.SE_IDENTIFY" datatype="1,is_null,300" >	
				     		<option value="100201" selected>有效</option>				 
				    	</select>
			    		</td>



<!--						<td><label>有效标识：</label></td>					   -->
<!--					    <td>-->
<!--					    	<select id="status" name="status" kind="dic" src="YXBS" datasource="rl.STATUS" datatype="1,is_null,6" operation="=" >-->
<!--					    		<option value="100201" selected>有效</option>-->
<!--					    	</select>-->
<!--					    </td>-->
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-batchUpdate" >批量更新</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_yhlb" refQuery="fm-pjcx"  multivals="yhw" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
<!--							<th type="single" name="XH" style="display:none;"></th>		-->
							<th type="multi" id="CX-XH" name="CX-XH" style="align:center;" unique="RELATION_ID" ></th>
							<th fieldname="PART_CODE" >配件代码</th>							
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="SUPPLIER_CODE" >供应商代码</th>
							<th fieldname="SUPPLIER_NAME" >供应商名称</th>
<!--							<th fieldname="PLAN_PRICE"   refer="amountFormat" align="right">计划价格</th>	-->
<!--							<th fieldname="APPLY_CYCLE"  refer="amountFormat" align="right">采购价格</th>	-->
<!--							<th fieldname="IF_STREAM" >是否需要流水号</th>-->
							<th fieldname="APPLY_CYCLE" >供货周期</th>	
							<th fieldname="SE_IDENTIFY" >服务标识</th>
							<th fieldname="SE_STATUS" >服务状态</th>
							<th fieldname="UPDATE_USER" >修改人</th>	
							<th fieldname="UPDATE_TIME" >修改时间</th>	
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
						</tr>
					</thead>					
			</table>
		</div>
		<div id="yhw">
		<table style="display:none">
			<tr>
				<td>
					<textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
				</td>
			</tr>
		</table>
	</div>
	</div>
	</div>
</div>
</body>
</html>