<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdcx" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fm-fwhdVIN" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdcxTable">
					<tr>
						<td><label>VIN：</label></td>
						<td><input type="text" id="vin" name="vin" datasource="MV.VIN" datatype="1,is_null,17"  value="" operation="like" /></td>
						<td><label>发动机型号：</label></td>
						<td><input type="text" id="engineNo" name="engineNo" datasource="ENGINE_NO" datatype="1,is_null,30" value="" operation="like" /></td>
					</tr>
			  </table>
				 <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" id="di_searchVIN">查&nbsp;&nbsp;询</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="dia-VINSave">确&nbsp;&nbsp;定</button>
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
	<form id="di-mainVINlb" method="post">
		<div id="mainVin">
					<table style="display:none;width:100%;" id="mainVINlb" name="mainVINlb" multivals="VINVals" ref="mainVin" refQuery="di_fwhdcxTable" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="VEHICLE_ID"></th>
								<th fieldname="SVIN" >SVIN</th>
								<th fieldname="ENGINE_NO" >发动机型号</th>
								<th fieldname="BUY_DATE" >销售日期</th>
								<th fieldname="FACTORY_DATE" >生产日期</th>
							</tr>
						</thead>
					</table>
				</div>
			<fieldset id="selectedVIN" style="display: none">
				<legend align="left" >&nbsp;[已选定VIN]</legend>
				   <div id="VINVals">
						<table style="width:100%;">
							<tr><td>
								<textarea id="VIN-val0" name="multivals" style="width:80%;height:26px;display:none " column="1" ></textarea>
								<textarea style="width:99%;height:50px;" id="VIN-val1" name="multivals" readOnly></textarea>
							</td></tr>
						</table>
					</div>
				</fieldset>
			</form>
			<form id="fm-VINInfo">
                <input type="hidden" id="vehicelId" name="vehicelId" datasource="VEHICLE_ID"/>
                <input type="hidden" id="vins" name="vins" datasource="VIN"/>
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
		//刷新本页面
		var $f = $("#di-fm-fwhdVIN");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchVIN.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchUrl,"di_searchVIN",1,sCondition,"mainVINlb");
		$('#selectedVIN').show();
		//刷新父页面
		var $f = $("#fm-fwhdvinfy");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	var searchServiceProtUrl =diaSaveAction+"/searchServiceVin.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchServiceProtUrl,"searchPart",1,sCondition,"fwhdvinlb");
		$("#VIN-val0").val("");
		$("#VIN-val1").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
//弹出窗体
var dialog = $("body").data("serviceVIN");
$(function()
{
	$("#mainVINlb").attr("layoutH",document.documentElement.clientHeight-240);
	//查询按钮响应
	$("#di_searchVIN").bind("click", function(event){
		var $f = $("#di-fm-fwhdVIN");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchVIN.ajax?&activityId="+$("#serviceActivityInfoId").val();
		doFormSubmit($f,searchUrl,"di_searchVIN",1,sCondition,"mainVINlb");
		$('#selectedVIN').show();
	});
	//关闭页面
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#dia-VINSave").bind("click", function(event)
	{
		var mxids=$("#VIN-val0").val();
		var vins=$("#VIN-val1").val();
	    if(mxids=="")
	    {
	    	alertMsg.warn("请选择VIN！");
	    	return true;
	    }else{
               $('#vehicelId').val(mxids);
               $('#vins').val(vins);
               $('#activityIdHI').val($("#serviceActivityInfoId").val());
               $('#activityCodeHI').val($("#DI_HDDM").val());
               $('#activityNameHI').val($("#DI_HDMC").val());
               var $f = $("#fm-VINInfo");
               //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
               if (submitForm($f) == false) return false;
               var sCondition = {};
               //将需要提交的内容拼接成json
               sCondition = $f.combined(1) || {};
               var xzcxUrl =diaSaveAction+"/insertVINs.ajax";
               doNormalSubmit($f, xzcxUrl, "dia-VINSave", sCondition, diaInsertCallBack);
	    }
	});
});
</script>
</div>