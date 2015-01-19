<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-searchModelsInfo">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchModelsInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>车型代码：</label></td>
					    <td>
					    	<input type="text" id="diaBA_modelsCode" name="diaBA_modelsCode" datasource="MODELS_CODE" datatype="1,is_null,30" operation="like" />
					    </td>
					    <td><label>车型名称：</label></td>
					    <td>
					    	<input type="text" id="diaBA_modelsName" name="diaBA_modelsName" datasource="MODELS_NAME" datatype="1,is_null,30" operation="like" />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-modelsSearch" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-modelsSave" >保&nbsp;&nbsp;存</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>	
	</div>
	<div class="pageContent">
		<div id="page_searchModelsList" >
			<table style="display:none;width:100%;" id="tab-searchModelsList" multivals="newModelsDeletVal" name="tablist" ref="page_searchModelsList" refQuery="tab-searchModelsInfo">
					<thead>
						<tr>
							<th type="multi" name="CX-XH" style="align:center;" unique="MODELS_ID"></th>
							<th fieldname="MODELS_CODE" >车型代码</th>
							<th fieldname="MODELS_NAME" >车型名称</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table id="newModelsDeletVal" style="display:none">
			<tr><td>
				<textarea id="val2" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
			</td></tr>
		</table>
	</div>
	<form id="fm-strategyModelsRelation">
		<input type="hidden" id="diaBA-modelsIds" name="diaBA-modelsIds" datasource="MODELS_IDS"/>
		<input type="hidden" id="diaMODELS-strategyId" name="diaMODELS-strategyId" datasource="STRATEGY_ID"/>   
    </form>	
	</div>
</div>	
<script type="text/javascript">
var searchModelsUrl = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction/searchModels.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction";
var diaAction = "<%=action%>";
//初始化
$(function()
{
	//查询车型按钮响应
	$("#btn-modelsSearch").bind("click", function(event){
		$("#val2").val("");
		var $f = $("#fm-searchModelsInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url=searchModelsUrl+"?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,url,"btn-modelsSearch",1,sCondition,"tab-searchModelsList");
	});
	
	if(diaAction == 1)	//新增初始化,先执行查询
	{  
		var $f = $("#fm-searchModelsInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url=searchModelsUrl+"?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,url,"btn-modelsSearch",1,sCondition,"tab-searchModelsList");
	}
	
	//保存
	$("#btn-modelsSave").bind("click", function(event){ 
		var modelsIds=$("#val2").val();
		$("#diaBA-modelsIds").val(modelsIds);//车型IDs
		if(modelsIds)
		{   
			//上个页面隐藏的策略主键传到本div           
			$('#diaMODELS-strategyId').val($("#dia-strategyId").val());
			
		    var $f = $("#fm-strategyModelsRelation");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-strategyModelsRelation").combined(1) || {}; 
			var addStrategyModelsUrl = diaSaveAction + "/insertModels.ajax";
			doNormalSubmit($f,addStrategyModelsUrl,"btn-modelsSave",sCondition,diaInsertModelsCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
});

//批量新增车型回调函数
function diaInsertModelsCallBack(res)
{
	try
	{   
		$("#btn-searchStrategyModels").trigger("click");
		$("#btn-modelsSearch").trigger("click");
		
		//$.pdialog.closeCurrent();
		
		//清空的内容
		$("#val0").val("");
		$("#val1").val("");
		$("#val2").val("");
		$("#val3").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>