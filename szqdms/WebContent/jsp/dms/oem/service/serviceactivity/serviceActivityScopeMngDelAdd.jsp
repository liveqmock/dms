<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdfws" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fm-fwhdfws" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdfwsTable">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="orgCode" name="orgCode" datasource="TOG.ORG_ID" datatype="1,is_null,10000" operation="in" value="" hasBtn="true" callFunction="showOrgTree('orgCode',2)" readonly="readonly"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="di_dealerName" name="di_dealerName" datasource="TOG.ONAME"  datatype="1,is_null,30" value="" operation="like" /></td>
					</tr>
			 	 </table>
				 <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchOrgDealrs">查&nbsp;&nbsp;询</button></div></div></li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="dia-dealerSave">确&nbsp;&nbsp;定</button>
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
	<form id="di-mainDealerllb" method="post">
		<div id="mainDealer">
					<table style="display:none;width:100%;" id="mainDealerllb" name="mainDealerllb" multivals="dealerVals" ref="mainDealer" refQuery="di_fwhdfwsTable" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="ORG_ID"></th>
								<th fieldname="CODE" >渠道商代码</th>
								<th fieldname="ONAME" >渠道商名称</th>
							</tr>
						</thead>
					</table>
				</div>
			<fieldset id="selectedDealer" style="display: none">
				<legend align="left" >&nbsp;[已选定渠道商]</legend>
				   <div id="dealerVals">
						<table style="width:100%;">
							<tr><td>
								<textarea id="dealer-val0" name="multivals" style="width:80%;height:26px;display:none " column="1" ></textarea>
								<textarea style="width:99%;height:50px;" id="dealer-val1" name="multivals" readOnly></textarea>
							</td></tr>
						</table>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	 <form id="fm-serviceScopeInfo">
             <input type="hidden" id="activityName" name="activityName" datasource="ACTIVITY_NAME"/>
      </form>
</div>
<script type="text/javascript">
var diaAction = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction";
var searchDealerUrl = "<%=request.getContextPath()%>/service/serviceactivity/ServiceActivityScopeMngAction/searchDealers.ajax?activityId="+activityId; 
//弹出窗体
var dialog = $("body").data("fwhdfws");
$(function()
{
	//查询按钮响应
	 $("#di_searchOrgDealrs").bind("click", function(event){
		var $f = $("#di-fm-fwhdfws");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaAction+"/searchOrgDealrs.ajax?activityId="+activityId;
    	doFormSubmit($f,searchUrl,"di_searchOrgDealrs",1,sCondition,"mainDealerllb");
		$('#selectedDealer').show();
	});
	 $("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#dia-dealerSave").bind("click", function(event)
		{
			var mxids=$("#dealer-val0").val();
		    if(mxids=="")
		    {
		    	alertMsg.warn("请选择渠道商！");
		    	return true;
		    }else{
		        $('#activityName').val(activityName);
		        var $f = $("#fm-serviceScopeInfo");
		        if (submitForm($f) == false) return false;
		        sCondition = $f.combined(1) || {};
		    	var dealerUrl =diaAction+"/insertDealers.ajax?mxids="+mxids+"&activityId="+activityId+"&activityCode="+activityCode;
				doNormalSubmit($f, dealerUrl, "dia-dealerSave", sCondition, diaInsertCallBack,"true");
		    };
	  }); 
});
 function diaInsertCallBack(res)
{
	try
	{	
		//刷新父页面
		parent.$("#search").trigger("click");
		var $f = $("");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchDealerUrl,"search",1,sCondition,"fwhdxglb");
		//刷新本页面
		var $f = $("#di-fm-fwhdfws");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaAction+"/searchOrgDealrs.ajax?activityId="+activityId;
    	doFormSubmit($f,searchUrl,"di_searchOrgDealrs",1,sCondition,"mainDealerllb");
		$("#dealer-val0").val("");
		$("#dealer-val1").val("");
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
	arr.push($tr.attr("ONAME"));
	multiSelected($checkbox,arr,$("#dealerVals"));
} 

</script>