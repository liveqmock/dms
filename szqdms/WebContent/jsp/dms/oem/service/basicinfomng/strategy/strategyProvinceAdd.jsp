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
		<form method="post" id="fm-searchProvinceInfo">
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-searchProvinceInfo">
					<!-- 定义查询条件 -->
					<tr>
						<td><label>省份：</label></td>
					    <td>
					    	<input type="text" id="diaBA_MC" name="diaBA_MC" datasource="MC" datatype="1,is_null,60" operation="like" />
					    </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-provinceSearch" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-provinceSave" >保&nbsp;&nbsp;存</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>	
	</div>
	<div class="pageContent">
		<div id="page_searchProvinceList" >
			<table style="display:none;width:100%;" id="tab-searchProvinceList" multivals="newProvinceDeletVal" name="tablist" ref="page_searchProvinceList" refQuery="tab-searchProvinceInfo">
					<thead>
						<tr>
							<th type="multi" name="CX-XH" style="align:center;" unique="DM"></th>
							<th fieldname="MC" >省份</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
		<table id="newProvinceDeletVal" style="display:none">
			<tr><td>
				<textarea id="val3" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea>
			</td></tr>
		</table>
	</div>
	<form id="fm-strategyProvinceRelation">
		<input type="hidden" id="diaBA-provinceIds" name="diaBA-provinceIds" datasource="PROVINCE_IDS"/>
		<input type="hidden" id="diaPROVINCE-strategyId" name="diaPROVINCE-strategyId" datasource="STRATEGY_ID"/>   
    </form>	
	</div>
</div>	
<script type="text/javascript">
var searchProvinceUrl = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction/searchProvince.ajax";
var diaSaveAction = "<%=request.getContextPath()%>/service/basicinfomng/StrategyMngAction";
var diaAction = "<%=action%>";
//初始化
$(function()
{
	//查询省份按钮响应
	$("#btn-provinceSearch").bind("click", function(event){
		$("#val3").val("");
		var $f = $("#fm-searchProvinceInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url=searchProvinceUrl+"?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,url,"btn-provinceSearch",1,sCondition,"tab-searchProvinceList");
	});
	
	if(diaAction == 1)	//新增初始化,先执行查询
	{  
		var $f = $("#fm-searchProvinceInfo");
		var sCondition = {};
    	sCondition = $f.combined() || {};
    	var url=searchProvinceUrl+"?&strategyId="+$("#dia-strategyId").val();
		doFormSubmit($f,url,"btn-provinceSearch",1,sCondition,"tab-searchProvinceList");
	}
	
	//保存
	$("#btn-provinceSave").bind("click", function(event){ 
		var procinceIds=$("#val3").val();
		$("#diaBA-provinceIds").val(procinceIds);//省份IDs
		if(procinceIds)
		{   
			//上个页面隐藏的策略主键传到本div           
			$('#diaPROVINCE-strategyId').val($("#dia-strategyId").val());
			
		    var $f = $("#fm-strategyProvinceRelation");
			if (submitForm($f) == false) return false;
			var sCondition = {};
			sCondition = $("#fm-strategyProvinceRelation").combined(1) || {}; 
			var addStrategyProvinceUrl = diaSaveAction + "/insertProvince.ajax";
			doNormalSubmit($f,addStrategyProvinceUrl,"btn-provinceSave",sCondition,diaInsertProvinceCallBack);
			
		}else
		{
			alertMsg.info("请选择记录");
			return false;
		}
		
	});
});

//批量新增车型回调函数
function diaInsertProvinceCallBack(res)
{
	try
	{   
		$("#btn-searchStrategyProvince").trigger("click");
		$("#btn-provinceSearch").trigger("click");
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