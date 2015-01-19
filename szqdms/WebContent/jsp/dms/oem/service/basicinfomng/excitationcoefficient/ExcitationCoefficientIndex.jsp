<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.frameImpl.Constant"%>        
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>激励系数维护</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/ExcitationCoefficientMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/ExcitationCoefficientMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:false,width:900,height:260,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
/* 	$("#search").click(function(){
		if($("#gzmslb").is(":hidden"))
		{
			$("#gzmslb").show();
			$("#gzmslb").jTable();
		}
	}); */
	//查询按钮响应
	$("#btn-search").bind("click", function(event){
	
 
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
		$.pdialog.open(webApps + "/jsp/dms/oem/service/basicinfomng/excitationcoefficient/ExcitationCoefficientAdd.jsp?action=1", "ExcitationCoefficient", "激励系数新增", options);
	});
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/ExcitationCoefficientMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });

	
   //导入按钮响应
   $('#btn-impProm').bind('click', function () {
       //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
       //最后一个参数表示 导入成功后显示页                    
       importXls("SE_BA_EXCITATION_TMP","",9,3,"/jsp/dms/oem/service/basicinfomng/excitationcoefficient/importSuccess.jsp");
   }); 		
});
function doUpdate(rowobj){
	$("td input[type=radio]",$(rowobj)).attr("checked",true);
	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/excitationcoefficient/ExcitationCoefficientAdd.jsp?action=2", "ExcitationCoefficient", "激励系数编辑", options);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	var status=<%=Constant.YXBS_02%>;
	var url = deleteUrl + "?coefficientId="+$(rowobj).attr("COEFFICIENT_ID")+"&status="+status;
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
function open(){
	var id="ORG_CODE";
	var busType=2;
	/* 	showOrgTree(id,busType); */
		
	//最后一个参数1表示复选；2表示单选
	showOrgTree(id,busType,'',"2");
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
}
function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	 
	   $("#ORG_CODE").attr("code",orgCode); 
	   $("#ORG_ID").val(orgId);
	  $("#ORG_NAME").val(orgName);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;激励系数维护</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fm-search" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="tb-search">
						<tr>
							<!-- <td><label>服务商代码：</label></td>
						<td><input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="T.ORG_CODE"  datatype="1,is_null,100" value="" readOnly hasBtn="true" callFunction="open();"/></td>
				      	<td><input type="hidden" id="ORG_ID" name="ORG_ID" datasource="T.ORG_ID" datatype="1,is_null,100" value="" /></td>
				 -->				<td><label>激励类别：</label></td>
							<td><select type="text" id="COEFFICIENT_TYPE" name="COEFFICIENT_TYPE"  datasource="T.COEFFICIENT_TYPE" class="combox" kind="dic" src="JLLX" datatype="1,is_null,100" >
								<option value=-1 selected>--</option>
							</select>
							</td>
							 <td><label>有效标识：</label></td>
			   		 <td>
				    <select type="text" class="combox" id="STATUS" name="STATUS" kind="dic" src="YXBS" datasource="T.STATUS" datatype="1,is_null,10" >
				    	 <option value="-1" selected>--</option>	
				    </select>
			    	</td>
			    		<td><label>车型代码：</label></td>
						<td><input type="text" id="MODELS_CODE" name="MODELS_CODE" datasource="C.MODELS_CODE" operation="like"  datatype="1,is_null,100" /></td>
			
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-impProm" >明细导入</button></div></div></li>
	
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
						    <th fieldname="MODELS_CODE" >车型代码</th>
							<th fieldname="MODELS_NAME" >车型</th>
							<th fieldname="TYPE_NAME" >发动机型号</th>
							<th fieldname="COEFFICIENT_TYPE" >激励类型</th>
							<th fieldname="COEFFICIENT_RADIO" >激励系数</th>
							<th fieldname="START_DATE" >销售起始日期</th>
							<th fieldname="END_DATE" >销售截止日期</th>
						
							<th fieldname="REMARKS" >备注</th>
							<th fieldname="UPDATE_USER" >更新人</th>
							<th fieldname="UPDATE_TIME" >更新时间</th>
							<th fieldname="STATUS" >状态</th> 
							
							<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doUpdate|doDelete" >操作</th>
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