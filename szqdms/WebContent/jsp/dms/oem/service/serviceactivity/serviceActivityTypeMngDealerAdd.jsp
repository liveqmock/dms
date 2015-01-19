<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdcx" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fm-fwhdcx" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdcxTable">
					<tr>
						<td><label>车型代码：</label></td>
						<td><input type="text" id="DI_CXDM" name="DI_CXDM" datasource="MODELS_CODE" datatype="1,is_null,30"  value="" operation="like" /></td>
						<td><label>车型名称：</label></td>
						<td><input type="text" id="DI_CXMC" name="DI_CXMC" datasource="MODELS_NAME" datatype="1,is_null,30" value="" operation="like" /></td>
					</tr>
			  </table>
				 <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" id="di_searchModel">查&nbsp;&nbsp;询</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="dia-modelSave">确&nbsp;&nbsp;定</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button class="close" type="button">关&nbsp;&nbsp;闭</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
			</div>
		</form>
	</div>
	<div class="pageContent">
	<form id="di-mainModellb" method="post">
		<div id="mainModel">
					<table style="display:none;width:100%;" id="mainModellb" name="mainModellb" multivals="modelVals" ref="mainModel" refQuery="di_fwhdcxTable" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="MODELS_ID"></th>
								<th fieldname="MODELS_CODE" >车型代码</th>
								<th fieldname="MODELS_NAME" >车型名称</th>
							</tr>
						</thead>
					</table>
				</div>
			<fieldset id="selectedModel" style="display: none">
				<legend align="left" >&nbsp;[已选定项目]</legend>
				   <div id="modelVals">
						<table style="width:100%;">
							<tr><td>
								<textarea id="model-val0" name="multivals" style="width:80%;height:26px;display:none " column="1" ></textarea>
								<textarea style="width:99%;height:50px;" id="model-val1" name="multivals" readOnly></textarea>
							</td></tr>
						</table>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction";
function diaInsertCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
	  	sCondition = $f.combined() || {};
		var searchServiceModelUrl =diaSaveAction+"/searchServiceModel.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceModelUrl,"searchServiceModel",1,sCondition,"fwhdcxlb");
		var $f = $("#di-fm-fwhdcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchModel.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchUrl,"di_searchModel",1,sCondition,"mainModellb");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
//列表复选
function doCheckbox(checkbox)
{
	var $tr = $(checkbox).parent().parent().parent();
	var arr = [];
	var $checkbox = $(checkbox);
	var mxid = $(checkbox).val();
	arr.push(mxid);
	arr.push($tr.attr("MODELS_NAME"));
	multiSelected($checkbox,arr,$("#modelVals"));
}
//弹出窗体
var dialog = $("body").data("serviceModel");
$(function()
{
	//查询按钮响应
	$("#di_searchModel").bind("click", function(event){
		var $f = $("#di-fm-fwhdcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchModel.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchUrl,"di_searchModel",1,sCondition,"mainModellb");
		$('#selectedModel').show();
	});

	
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#dia-modelSave").bind("click", function(event)
			{
				var mxids=$("#model-val0").val();
			    if(mxids=="")
			    {
			    	alertMsg.warn("请选择车型！");
			    	return true;
			    }else{
			    	var xzcxUrl =diaSaveAction+"/insertModels.ajax?mxids="+mxids+"&activityId="+$("#serviceActivityInfoId").val()+"&activityCode="+$("#DI_HDDM").val()+"&activityName="+$("#DI_HDMC").val();
					sendPost(xzcxUrl,"dia-modelSave","",diaInsertCallBack,"true");
			    }
			});
});
</script>
</div>