<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>外出费用维护</title>

<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var searchUrl = "<%=request.getContextPath()%>/service/basicinfomng/TravelCostMngAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/service/basicinfomng/TravelCostMngAction/resetStatus.ajax";
//定义弹出窗口样式
var options = {max:false,width:750,height:280,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//查询按钮响应方法
 $(function(){
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
 
		$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/travelcost/TravelCostAdd.jsp?action=1", "TravelCost", "外出费用新增", options);
	});
	
	 // 导出按钮绑定
    $("#btn-export-index").click(function(){
    	var $f = $("#fm-search");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	$("#params").val(sCondition);
        var url = encodeURI("<%=request.getContextPath()%>/service/basicinfomng/TravelCostMngAction/download.do");
        window.location.href = url;
        $("#exportFm").attr("action",url);
        $("#exportFm").submit();
    });

	 //外出费用模版导出按钮响应
   $("#btn-expTpl").bind('click', function () {
       var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=travelcost.xls");
       window.location.href = url;
   });

   //外出费用导入按钮响应
   $('#btn-impProm').bind('click', function () {
       //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
       //最后一个参数表示 导入成功后显示页                    
       importXls("SE_BA_TRAVEL_COST_TMP","",17,3,"/jsp/dms/oem/service/basicinfomng/travelcost/importSuccess.jsp");
   });            
});
//function open(){
//	var id="ORG_CODE";
	//var busType=2;
	/* 	showOrgTree(id,busType); */
		
	//最后一个参数1表示复选；2表示单选
	//showOrgTree(id,busType,'',"2");
	//页面实现回调方法：
	//jsonObj:{"orgId":"","orgCode":"","orgName":""}
//}
/* function showOrgTreeCallBack(jsonObj)
{
	  var orgId=jsonObj.orgId;
	  var orgCode=jsonObj.orgCode;
	  var orgName=jsonObj.orgName;
	    $("#ORG_CODE").val(orgCode); 
	   $("#ORG_CODE").attr("code",orgCode); 
	   $("#ORG_ID").val(orgId);
	  $("#ORG_NAME").val(orgName);
}
 */
function doDelete(rowobj){
	//alert(alertMsg.confirm("确定删除?"));
	row = $(rowobj);
	alertMsg.info("删除成功");
}
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		if(row)
			row.remove();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
 
function doUpdate(rowobj){
	$("td input",$(rowobj)).attr("checked",true);

	$.pdialog.open(webApps+"/jsp/dms/oem/service/basicinfomng/travelcost/TravelCostAdd.jsp?action=2", "TravelCost", "外出费用编辑", options);
}


//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
	//$row 行对象
	//selIndex 第几个字典
	
		//判断id中是否包含dia-D_ORGCODE的值
	if(id.indexOf("B_OIG_CODE") == 0)
	{
		if($row.attr("ORG_ID")){
		
		$("#B_ORGID").val($row.attr("ORG_ID"));
		}
	
	/* 	if($row.attr("ORG_NAME")){
		
		$("#dia-R_ORGNAME").val($row.attr("ORG_NAME"));
		}
		 */
		
			$("#B_ORGNAME").val($("#"+id).val());
		return true;
	}
 
 
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;外出费用维护</h4>
	<div class="page">
	<div class="pageHeader">
		<form id="fm-search" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="tab-search">
				    <!-- 	<input type="hidden" id="ORG_ID" name="ORG_ID" datasource="T.ORG_ID" datatype="1,is_null,100" value="" />
			   			<input type="hidden" id="B_ORGID" name="B_ORGID" datasource="T.OFFICE_ID" datatype="1,is_null,100" value="" /> -->
		<!-- 				<input type="hidden" id="B_PID" name="B_PID" datasource="F.PID" datatype="1,is_null,100" value="" />
		 -->
			   		<tr>
							<td><label>服务商代码：</label></td>
						<td><input type="text" id="ORG_CODE" name="ORG_CODE"  datasource="T.ORG_CODE" operation="left_like"  datatype="1,is_null,100" /></td>
						<td><label>服务商名称：</label></td>
						<td><input type="text" id="ORG_NAME" name="ORG_NAME" datasource="T.ORG_NAME" operation="like"  datatype="1,is_null,100" /></td>					
				     	 <td><label>所属办事处：</label></td>
					<!-- 	<td><input type="text" id="B_OIG_CODE" name="B_OIG_CODE"   kind="dic"   src="T#TM_ORG:CODE:ONAME{ORG_ID}:1=1 AND ORG_TYPE='200004' AND STATUS='100201'" datatype="1,is_null,100" value="" /></td> -->
              	    <td><input type="text" id="B_OIG_NAME" name="B_OIG_NAME"  datasource="F.ONAME"   operation="like" datatype="1,is_null,100" value="" /></td>
				   
					</tr>
					<tr>
					  	<td><label>外出次数：</label></td>
						<td><select type="text" class="combox" id="TRAVEL_TIMES" name="TRAVEL_TIMES" datasource="T.TRAVEL_TIMES" kind="dic" src="WCCS" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
						<td><label>外出时间：</label></td>
						<td><select type="text" class="combox" id="TRAVEL_DATE" name="TRAVEL_DATE" datasource="T.TRAVEL_DATE"  kind="dic" src="WCSJ" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
				        <td><label>备车类型：</label></td>
						<td><select type="text" class="combox" id="VEHICLE_TYPE" name="VEHICLE_TYPE" datasource="T.VEHICLE_TYPE" kind="dic" src="WCFS" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
				          <td><label>费用类别：</label></td>
						<td><select type="text" class="combox" id="COSTS_TYPE" name="COSTS_TYPE" datasource="T.COSTS_TYPE" kind="dic" src="FYLB" datatype="1,is_null,10" >
				        	<option value="-1" selected>--</option>
				        </select>
				        </td>
						<!-- <td><label>状态：</label></td>
						<td>
							  <select type="text" class="combox" id="STATUS" name="STATUS" kind="dic" src="YXBS" datasource="T.STATUS" datatype="1,is_null,10" >
						    	<option value="-1">--</option>
					   		</select>
						</td> -->
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-search">查&nbsp;&nbsp;询</button></div></div>
						</li>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-add" >新&nbsp;&nbsp;增</button></div></div>
						</li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-export-index">导&nbsp;&nbsp;出</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="btn-impProm" >明细导入</button></div></div></li>
						
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page-list">
			<table  id="tab-list" name="tab-list" style="display: none;width: 100%" ref="page-list" refQuery="tab-search" >
					<thead>
					<tr>
					<th type="single" name="XH" style="display:none"></th>
								<th fieldname="ORG_CODE" colwidth="60" >服务商代码</th>
								<th fieldname="ORG_NAME" colwidth="100" >服务商名称</th>
								<th fieldname="SNAME" colwidth="60" >办事处</th>								
								<th fieldname="COSTS_TYPE" colwidth="60" >费用类别</th>
								<th fieldname="TRAVEL_TIMES" colwidth="60" >外出次数</th>
								<th fieldname="TRAVEL_DATE" colwidth="60" >外出时间</th>
								<th fieldname="VEHICLE_TYPE" colwidth="60" >备车类型</th>								
								<th fieldname="START_MILES" colwidth="60" >里程（开始）</th>
								<th fieldname="END_MILES" colwidth="60" >里程（结束）</th>
								<th fieldname="COST" colwidth="60"  >费用（单价）</th>							
  							<th fieldname="STATUS" >状态</th>
					        <th fieldname="CREATE_USER" >创建人</th>
					        <th fieldname="CREATE_TIME" >创建时间</th>
					        <th fieldname="UPDATE_USER" >更新人</th>
					        <th fieldname="UPDATE_TIME" >更新时间</th>
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