<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:配件三包规则查询
	 Version:1.0
     Collator：baixiaoliangn@sxqc.com
     Date：2014-07
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<jsp:include page="/head.jsp" />
<title>配件三包规则查询</title>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：/service/basicinfomng/RulePartMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * RulePartMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/resetStatus.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:270,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		   	//判断id中是否包含RULE_CODE的值
		if($("#RULE_CODE").val() == 0)
		{
		
			$("#RULE_ID").val("");
	
		}
		
			//判断id中是否包含dia-PART_CODE的值
		if($("#PART_ID").val() == 0)
		{
			
			$("#PART_ID").val("");
				 
		}
		var $f = $("#fm-search");//获取页面提交请求的form对象
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
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-list");
	});
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/rulepart/RulePartAdd.jsp?action=1", "RulePart", "新增配件三包规则", diaAddOptions);
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/RulePartMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });
});
//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/rulepart/RulePartAdd.jsp?action=2", "RulePart", "修改配件三包规则", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var status=<%=DicConstant.YXBS_02%>;
	var url = deleteUrl + "?amountId="+$(rowobj).attr("AMOUNT_ID")+"&status="+status;
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
			$("#tab-list").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典

	//判断id中是否包含dia-RULE_CODE的值
	if(id.indexOf("RULE_CODE") == 0)
	{
		if($row.attr("RULE_ID")){
		
		$("#RULE_ID").val($row.attr("RULE_ID"));
		}
	

	}
	
		//判断id中是否包含dia-PART_CODE的值
	if(id.indexOf("PART_CODE") == 0)
	{
		
		if($row.attr("PART_ID")){
		
		$("#PART_ID").val($row.attr("PART_ID"));
		}
			 
	}
	return ret;
}
function openPurchase(flag)
{
//	if(!$("#dia-SUPPLIER_ID").val()){
//		alertMsg.warn('请选择配件!')
//	}else{
		var options = {width:750,height:400,max:false,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
		var url = webApps +"/jsp/dms/oem/part/basicInfo/ptbapicture/ptbapictureSel.jsp?flag="+flag;	
		$.pdialog.open(url, "ptbapictureSel", "配件查询", options);		
//	}
	
}
function SelCallBack(obj,flag)
{
		$("#PART_ID").val($(obj).attr("PART_ID"));			//配件主键
		$("#PART_CODE").val($(obj).attr("PART_CODE"));		//配件代码
		//$("#dia-part_name").val($(obj).attr("PART_NAME"));		//配件名称
}
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;配件三包规则查询</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">	
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				   <input type="hidden" id="RULE_ID" name="RULE_ID" datasource="T.RULE_ID" />
				   <input type="hidden" id="PART_ID" name="PART_ID" datasource="T.PART_ID" />
				<!-- 	<tr>
					 	<td><label>三包规则类型：</label></td>
						  <td> <select type="text" class="combox" id="USER_TYPE" name="USER_TYPE" operation="right_like"  datasource="T.USER_TYPE" kind="dic" src="E#1=系统规则:2=业务规则" datatype="1,is_null,10" >
								 <option value="-1" selected>--</option>	
							</select>
						</td> 
						</tr> -->
					<tr>
						<td><label>三包规则代码：</label></td>					
						<td>
            			  <input type="text" id="RULE_CODE"  name="RULE_CODE" datasource="T.RULE_CODE" kind="dic" src="T#SE_BA_RULE A: A.RULE_CODE:A.RULE_NAME{RULE_ID}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="1,is_null,100" operation="=" value="" disabledCode="true"/>
         				 </td> 
         				
						<td><label>配件代码：</label></td>
						<td>
						<input type="text" id="PART_CODE"  name="PART_CODE" datasource="T.PART_CODE" hasBtn="true" callFunction="openPurchase(1);"  dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
						</td>
				    <td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="STATUS" name="STATUS" kind="dic" src="YXBS" datasource="T.STATUS" datatype="1,is_null,10" >
				    	 <option value="-1" selected>--</option>	
				    </select>
			    	</td>	
			    	<td></td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
				
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table width="100%" id="tab-list" name="tab-list" style="display: none" ref="page-list" refQuery="tab-search" pageRows="10">
				<thead>
					<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="RULE_CODE" >规则代码</th>
								<th fieldname="RULE_NAME" >规则名称</th>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="MONTHS" >三包月份</th>
								<th fieldname="MILEAGE" >三包里程</th>
								<th fieldname="STATUS" >状态</th> 
					</tr>
				</thead>
				<tbody>
				</tbody>
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