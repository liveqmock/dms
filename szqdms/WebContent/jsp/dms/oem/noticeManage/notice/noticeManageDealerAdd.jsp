<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdfws" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fm-dealer" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdfwsTable">
					<tr>
						<td><label>渠道商代码：</label></td>
						<td><input type="text" id="di_dealerCode" name="di_dealerCode" datasource="CODE" datatype="1,is_null,30" value=""  operation="like"/></td>
						<td><label>渠道商名称：</label></td>
						<td><input type="text" id="di_dealerName" name="di_dealerName" datasource="ONAME"  datatype="1,is_null,30" value=""  operation="like"/></td>
						<td><label>办事处代码：</label></td>
						<td><input type="text" id="di_bscCode" name="di_bscCode"  datatype="1,is_null,30" value="" /></td>
					</tr>
			 	 </table>
				 <div class="subBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="di_searchOrgDealrs">查&nbsp;&nbsp;询</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-allDdealerSave">全部选择</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="dia-dealerSave">确&nbsp;&nbsp;定</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">下一步</button></div></div></li>
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
								<th fieldname="ORG_TYPE" >渠道商类型</th>
							</tr>
						</thead>
					</table>
				</div>
			<fieldset id="selectedDealer" style="display: none">
				<legend align="left" >&nbsp;[已选定渠道商]</legend>
				   <div id="dealerVals">
						<table style="width:100%;">
							<tr>
								<td>
									<textarea id="dealer-val0" name="multivals" style="width:80%;height:26px;display:none" column="1" ></textarea>
									<textarea style="width:99%;height:50px;" id="dealer-val1" name="multivals" readOnly></textarea>
									<textarea style="width:99%;height:50px;display:none" id="dealer-val2" name="multivals" readOnly></textarea>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	 <form id="fm-noticeScopeInfo">
             <input type="hidden" id="orgType" name="orgType" datasource="ORG_TYPE"/>
             <input type="hidden" id="orgId" name="orgId" datasource="ORG_ID"/>
      </form>
</div>
<script type="text/javascript">
var diaAction = "<%=request.getContextPath()%>/service/noticeManage/NoticeManageAction";
//弹出窗体
var dialog = $("body").data("noticeManageDealerAdd");
$(function()
{
	//查询按钮响应
	 $("#di_searchOrgDealrs").bind("click", function(event){
		var $f = $("#di-fm-dealer");//获取页面提交请求的form对象
		var bulletin_range= $("#DI_fwlb").attr("code");
		var bscCode= $("#di_bscCode").val();
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaAction+"/searchOrgDealrs.ajax?bulletinId="+$("#bulletinId").val()+"&bulletin_range="+bulletin_range+"&bscCode="+bscCode;
    	doFormSubmit($f,searchUrl,"di_searchOrgDealrs",1,sCondition,"mainDealerllb");
		$('#selectedDealer').show();
     });
	 $("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#dia-dealerSave").bind("click", function(event){
			var mxids=$("#dealer-val0").val();
			bulletinId=$("#bulletinId").val();
		    if(mxids=="")
		    {
		    	alertMsg.warn("请选择渠道商！");
		    	return true;
		    }else{
		    	$("#orgType").val($("#dealer-val2").val());
		        var $f = $("#fm-noticeScopeInfo");
		        if (submitForm($f) == false) return false;
		        sCondition = $f.combined(1) || {};
		    	var dealerUrl =diaAction+"/insertDealers.ajax?bulletinId="+bulletinId+"&mxids="+mxids;
				doNormalSubmit($f, dealerUrl, "dia-dealerSave", sCondition, diaInsertCallBack,"true");
		    };
	  }); 
	$("#dia-allDdealerSave").bind("click", function(event){
			bulletinId=$("#bulletinId").val();
			var bulletin_range= $("#DI_fwlb").attr("code");
	    	$("#orgType").val($("#dealer-val2").val());
	        var $f = $("#fm-noticeScopeInfo");
	        if (submitForm($f) == false) return false;
	        sCondition = $f.combined(1) || {};
	    	var dealerUrl =diaAction+"/insertAllDealers.ajax?bulletinId="+bulletinId+"&bulletin_range="+bulletin_range;
			doNormalSubmit($f, dealerUrl, "dia-dealerSave", sCondition, diaInsertCallBack,"true");
	  }); 
});
 function diaInsertCallBack(res)
{
	try
	{	
		//刷新父页面
		$("#btn-searchDel").click();
		//刷新本页面
		$("#di_searchOrgDealrs").click();
		$("#dealer-val0").val("");
		$("#dealer-val1").val("");
		$("#dealer-val2").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
</script>