<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdgs" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fm-fwhdgs" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdgsTable">
					<tr>
						<td><label>工时代码：</label></td>
						<td><input type="text" id="DI_GSDM" name="DI_CXDM" datasource="TIME_CODE" datatype="1,is_null,30"  value="" operation="like" /></td>
						<td><label>工时名称：</label></td>
						<td><input type="text" id="DI_GSMC" name="DI_CXMC" datasource="TIME_NAME" datatype="1,is_null,30" value="" operation="like" /></td>
					</tr>
				</table>
				 <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" id="di_searchTime">查&nbsp;&nbsp;询</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="dia-gssave">确&nbsp;&nbsp;定</button>
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
	<form id="di-mainTimelb" method="post">
		<div id="mainTime">
					<table style="display:none;width:100%;" id="mainTimelb" name="mainTimelb" multivals="timeVal" ref="mainTime" refQuery="di_fwhdgsTable" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="AMOUNT_ID"></th>
								<th fieldname="TIME_CODE" >工时代码</th>
								<th fieldname="TIME_NAME" >工时名称</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				  <fieldset id="selectedTime" style="display: none">
              	    <legend align="left" >&nbsp;[已选定配件]</legend>
					  <div id="timeVal">
						<table style="width:100%;">
							<tr>
								<td><textarea id="time-valo" name="multivals" style="width:400px;height:10px;display: none" column="1" ></textarea>
									<textarea style="width:99%;height:50px;" id="time-val1" name="multivals" readOnly></textarea></td>
							</tr>
						</table>
					 </div>
			      </fieldset>
			   </form>
			  <form id="fm-GsInfo">
                <input type="hidden" id="taskTimeId" name="taskTimeId" datasource="TASK_TIME_ID"/>
                <input type="hidden" id="activityIdHI" name="activityIdHI" datasource="ACTIVITYID"/>
                <input type="hidden" id="activityCodeHI" name="activityCodeHI" datasource="ACTIVITYCODE"/>
                <input type="hidden" id="activityNameHI" name="activityNameHI" datasource="ACTIVITYNAME"/>
            </form>
		</div>
	</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityTypeMngAction";
function diaInsertCallBack(res)
{
	try
	{
		var $f = $("#fm-fwhdxmgs");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceGsUrl =diaSaveAction+"/searchServiceTime.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceGsUrl,"searchWorkH",1,sCondition,"fwhdxmgslb");
		var $f = $("#di-fm-fwhdgs");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchTime.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchUrl,"di_searchTime",1,sCondition,"mainTimelb");
		$("#time-val0").val("");
		$("#time-val1").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
//弹出窗体
var dialog = $("body").data("serviceTime");
$(function()
{
	$("#mainTimelb").attr("layoutH",document.documentElement.clientHeight-220);
	//查询按钮响应
	$("#di_searchTime").bind("click", function(event){
		var $f = $("#di-fm-fwhdgs");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchTime.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchUrl,"di_searchTime",1,sCondition,"mainTimelb");
		$("#time-val0").val("");
		$("#time-val1").val("");
		$('#selectedTime').show();
	});
	$("#di_searchTime").click(function(){
		if($("#di_fwhdxmgslb").is(":hidden")){
			$("#di_fwhdxmgslb").show();
			$("#di_fwhdxmgslb").jTable();
		}
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#dia-gssave").bind("click", function(event)
			{
				var mxids=$("#time-valo").val();
			    if(mxids=="")
			    {
			    	alertMsg.warn("请选择工时！");
			    	return true;
			    }else{
		    	    $('#taskTimeId').val(mxids);
	                $('#activityIdHI').val($("#serviceActivityInfoId").val());
	                $('#activityCodeHI').val($("#DI_HDDM").val());
	                $('#activityNameHI').val($("#DI_HDMC").val());
	                var $f = $("#fm-GsInfo");
	                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
	                if (submitForm($f) == false) return false;
	                var sCondition = {};
	                //将需要提交的内容拼接成json
	                sCondition = $f.combined(1) || {};
	                var xzgsUrl =diaSaveAction+"/insertTimes.ajax";
	                doNormalSubmit($f, xzgsUrl, "dia-gssave", sCondition, diaInsertCallBack);
			    }
			});
});
</script>
</div>