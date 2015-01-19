<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.org.frameImpl.Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	 Title:工时定额管理
	 Version:1.0
     Collator：baixiaoliangn@sxqc.com
     Date：2014-07
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>工时定额维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
/**
 * 查询提交方法,方式为：/service/basicinfomng/TaskAmountMngAction/search.ajax
 * 其中，sysmng/usermsg/为类路径（去掉com.dms.actions）
 * TaskAmountMngAction/为提交到后台的action类名
 * search为提交请求类中需要执行的方法名
 * .ajax表示请求为ajax请求
 */
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/TaskAmountMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/TaskAmountMngAction/resetStatus.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:270,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		   	//判断id中是否包含MODELS_CODE的值
		if($("#MODELS_CODE").val() == 0)
		{
		
			$("#MODELS_ID").val("");
	
		}
		
			//判断id中是否包含dia-POSITION_CODE的值
		if($("#POSITION_ID").val() == 0)
		{
			
			$("#POSITION_ID").val("");
				 
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
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/taskamount/TaskAmountAdd.jsp?action=1", "TaskAmount", "新增工时定额", diaAddOptions);
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/TaskAmountMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });

	
   //导入按钮响应
   $('#btn-impProm').bind('click', function () {
       //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
       //最后一个参数表示 导入成功后显示页                    
       importXls("SE_BA_TASK_AMOUNT_TMP","",10,3,"/jsp/dms/oem/service/basicinfomng/taskamount/importSuccess.jsp");
   }); 
});
//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/taskamount/TaskAmountAdd.jsp?action=2", "TaskAmount", "修改工时定额", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var status=<%=Constant.YXBS_02%>;
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

	//判断id中是否包含dia-MODELS_CODE的值
	if(id.indexOf("MODELS_CODE") == 0)
	{
		if($row.attr("MODELS_ID")){
		
		$("#MODELS_ID").val($row.attr("MODELS_ID"));
		}
	

	}
	
		//判断id中是否包含dia-POSITION_CODE的值
	if(id.indexOf("POSITION_CODE") == 0)
	{
		
		if($row.attr("POSITION_ID")){
		
		$("#POSITION_ID").val($row.attr("POSITION_ID"));
		}
			 
	}
	return ret;
}
</script>
</head>
<body>
<div id="layout" style="width:100;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;工时定额维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">	
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				   <input type="hidden" id="MODELS_ID" name="MODELS_ID" datasource="T.MODELS_ID" />
				   <input type="hidden" id="POSITION_ID" name="POSITION_ID" datasource="T.POSITION_ID" />
					<tr>
					<!-- 	<td><label>用户类别：</label></td>
						 <td><select type="text" id="USER_TYPE" name="USER_TYPE" class="combox" kind="dic" src="E#1=军车:2=民车">
						  <td> <select type="text" class="combox" id="USER_TYPE" name="USER_TYPE" operation="right_like"  datasource="T.USER_TYPE" kind="dic" src="CLYHLX" datatype="1,is_null,10" >
								 <option value="-1" selected>--</option>	
							</select>
						</td> -->
						<td><label>工时代码：</label></td>
						<td><input type="text" id="TIME_CODE" name="TIME_CODE" datasource="T.TIME_CODE" operation="left_like"  datatype="1,is_null,100" value="" /></td>
						<td><label>工时名称：</label></td>
						<td><input type="text" id="TIME_NAME" name="TIME_NAME" datasource="T.TIME_NAME" operation="like" datatype="1,is_null,100" value="" /></td>
					</tr>
					<tr>
			<!-- 			<td><label>车型：</label></td>
					
						<td>
            			  <input type="text" id="MODELS_CODE"  name="MODELS_CODE" datasource="T.MODELS_CODE" kind="dic" src="T#MAIN_MODELS A: A.MODELS_CODE:A.MODELS_NAME{MODELS_ID}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
         				 </td> 
						<td><label>车辆部位：</label></td>
						<td>
						<input type="text" id="POSITION_CODE"  name="POSITION_CODE" datasource="T.POSITION_CODE" kind="dic"  src="T#SE_BA_VEHICLE_POSITION A:A.POSITION_CODE:A.POSITION_NAME{POSITION_ID}:1=1  AND A.STATUS = 100201" dicwidth="300" tree="false" datatype="1,is_null,30" operation="=" value="" disabledCode="true"/>
						</td>-->
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
							 
							<th fieldname="TIME_CODE" >工时代码</th>
							<th fieldname="TIME_NAME">工时名称</th>
							<th fieldname="AMOUNT_SET" >工时定额</th>
					        <th fieldname="REMARKS" colwidth="250" >备注</th>
					        <th fieldname="STATUS" >状态</th>
					        <th fieldname="CREATE_USER" >创建人</th>
					        <th fieldname="CREATE_TIME" >创建时间</th>
					        <th fieldname="UPDATE_USER" >更新人</th>
					        <th fieldname="UPDATE_TIME" >更新时间</th>
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