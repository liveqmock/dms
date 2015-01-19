<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>故障模式维护</title>
<script type="text/javascript">

var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultPatternMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/FaultPatternMngAction/delete.ajax";

//定义弹出窗口样式
var diaAddOptions = {max:false,width:850,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};

//初始化
$(function()
{
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
		var $f = $("#fm-searchFaultPattern");//获取页面提交请求的form对象
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
		doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"tab-faultpatternlist");
	});
	
	//新增按钮响应
	$("#btn-add").bind("click", function(event){
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/faultpattern/faultpatternAdd.jsp?action=1", "add", "新增故障模式", diaAddOptions);
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-searchFaultPattern");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/FaultPatternMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });

});

//列表编辑连接
function doUpdate(rowobj)
{
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/faultpattern/faultpatternAdd.jsp?action=2", "update", "修改故障模式", diaAddOptions);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var url = deleteUrl + "?patternId="+$(rowobj).attr("PATTERN_ID");
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
			$("#tab-faultpatternlist").removeResult($row);
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}

//弹出车辆部位查询界面
function openVehiclePosition()
{
	var options = {max:true,mask:true,mixable:false,minable:false,resizable:true,drawable:true}; 
	var url = webApps +"/jsp/dms/oem/service/basicinfomng/vehicleposition/vehiclePositionSel.jsp";	
	$.pdialog.open(url, "vehiclePositionSel", "车辆部位查询", options);				
}

//弹出框回调方法,将车辆部位等信息带回输入框
function SelCallBack(obj)
{	
	var patternId=$("#dia-patternId").val();

	var positionCode=$(obj).attr("POSITION_CODE");
	var positionName=$(obj).attr("POSITION_NAME");
	var positionId=$(obj).attr("POSITION_ID");
	
	$("#positioncode").val(positionName);
	$("#positioncode").attr("code",positionCode);
	
	$("#dia-positioncode").val(positionName);
	$("#dia-positioncode").attr("code",positionCode);
	$("#dia-positionid").val(positionId);
	$("#dia-positionname").val(positionName);
}

</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;故障模式维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-searchFaultPattern" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tab-searchFaultPattern">
						<tr>
							<td><label>故障模式代码：</label></td>
							<td><input type="text" id="faultcode" name="faultcode" datasource="FAULT_CODE" operation="like" datatype="1,is_null,30" /></td>
							<td><label>故障模式名称：</label></td>
							<td><input type="text" id="faultname" name="faultname" datasource="FAULT_NAME" operation="like" datatype="1,is_null,30" /></td>
							<td><label>严重程度：</label></td>
							<td><select type="text" class="combox" id="severity" name="severity" datasource="SEVERITY" operation="like" kind="dic" src="T#SE_BA_CODE:CODE:NAME:CODE_TYPE=302703" datatype="1,is_null,30" >
					        		<option value="-1" selected>--</option>
					        	</select>
					        </td>		
						</tr>
						<tr>
							<td><label>故障类别：</label></td>
							<td><input type="text" id="faultpatterncode" name="faultpatterncode" datasource="FAULT_PATTERN_CODE" operation="like" kind="dic" src="T#SE_BA_CODE:CODE:NAME{CODE_ID}:CODE_TYPE=302702" datatype="1,is_null,30" /> 
								<input id="out1" type="hidden"/>
							</td>							
							<td><label>车辆部位：</label></td>
                    		<td><input type="text" id="positioncode" name="positioncode" datasource="POSITION_CODE" readonly hasBtn="true" datatype="1,is_null,30" callFunction="openVehiclePosition()"/>
							</td>
							<td><label>有效标示：</label></td>
							<td>
		        				<select type="text" id="status"  name="status" datasource="STATUS" kind="dic" src="YXBS" datatype="1,is_null,6" >
				    			<option value="<%=DicConstant.YXBS_01 %>" selected>有效</option>
				    			<option value="<%=DicConstant.YXBS_02 %>" >无效</option>
				    			<option value="-1">--</option>
				    			</select>
		        			</td>		
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="page_faultpatternlist">
				<table style="display:none;width:100%;" id="tab-faultpatternlist" name="tablist" ref="page_faultpatternlist" refQuery="tab-searchFaultPattern">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"></th>
							<th fieldname="FAULT_CODE" >故障模式代码</th>
							<th fieldname="FAULT_NAME" >故障模式名称</th>
							<th fieldname="POSITION_NAME">车辆部位</th>
							<th fieldname="FAULT_PATTERN_NAME">故障类别</th>
							<th fieldname="NAME">严重程度</th>
							<th fieldname="STATUS">状态</th>
							<th fieldname="CREATE_USER">创建人</th>
							<th fieldname="CREATE_TIME">创建时间</th>
							<th fieldname="UPDATE_USER">修改人</th>
							<th fieldname="UPDATE_TIME">修改时间</th>
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