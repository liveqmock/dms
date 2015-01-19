<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="di_fwhdcx" style="width:100%;">
	<div class="page">
	<div class="pageHeader">
		<form id="di-fm-oldPartNotReturn" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="di_fwhdcxTable">
					<tr>
						<td><label>旧件代码：</label></td>
						<td><input type="text" id="partCode" name="partCode" datasource="OLD_PART_CODE" datatype="1,is_null,30"  value="" operation="like" /></td>
						<td><label>旧件名称：</label></td>
						<td><input type="text" id="partName" name="partName" datasource="OLD_PART_NAME" datatype="1,is_null,30" value="" operation="like" /></td>
					</tr>
			  </table>
				 <div class="subBar">
                    <ul>
                        <li>
                            <div class="buttonActive">
                                <div class="buttonContent">
                                    <button type="button" id="di_searchOldPart">查&nbsp;&nbsp;询</button>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="button">
                                <div class="buttonContent">
                                    <button type="button" id="dia-oldPartSave">确&nbsp;&nbsp;定</button>
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
	<form id="di-mainOldPartlb" method="post">
		<div id="mainModel">
					<table style="display:none;width:100%;" id="mainOldPartlb" name="mainOldPartlb" multivals="partVals" ref="mainModel" refQuery="di_fwhdcxTable" >
						<thead>
							<tr>
								<th type="multi" name="XH" unique="FAULT_PART_ID"></th>
								<th fieldname="OLD_PART_CODE" >旧件代码</th>
								<th fieldname="OLD_PART_NAME" >旧件名称</th>
								<th fieldname="FAULT_CODE" >故障代码</th>
								<th fieldname="OLD_PART_COUNT" >旧件数量</th>
								<th fieldname="OLD_SUPPLIER_ID" style="display: none">旧件供应商ID</th>
							</tr>
						</thead>
					</table>
				</div>
			<fieldset id="selectedPart" style="display: none">
				<legend align="left" >&nbsp;[已选定旧件]</legend>
				   <div id="partVals">
						<table style="width:100%;">
							<tr><td>
								<textarea id="part-val0" name="multivals" style="width:80%;height:26px;display:none " column="1" ></textarea>
								<textarea style="width:99%;height:50px;" id="part-val1" name="multivals" readOnly></textarea>
								<textarea style="width:80%;height:26px;display:none" id="part-val2" name="multivals" readOnly></textarea>
								<textarea style="width:80%;height:26px;display:none" id="part-val3" name="multivals" readOnly></textarea>
								<textarea style="width:80%;height:26px;display:none" id="part-val4" name="multivals" readOnly></textarea>
								<textarea style="width:80%;height:26px;display:none" id="part-val5" name="multivals" readOnly></textarea>
								<textarea style="width:80%;height:26px;display:none" id="part-val6" name="multivals" readOnly></textarea>
							</td></tr>
						</table>
					</div>
				</fieldset>
			</form>
			<form id="fm-partInfo">
                <input type="hidden" id="faultPartId" name="faultPartId" datasource="FAULT_PART_ID"/>
                <input type="hidden" id="oldPartId" name="oldPartId" datasource="OLD_PART_ID"/>
                <input type="hidden" id="oldPartCode" name="oldPartCode" datasource="OLD_PART_CODE"/>
                <input type="hidden" id="oldPartName" name="oldPartName" datasource="OLD_PART_NAME"/>
                <input type="hidden" id="faultCode" name="faultCode" datasource="FAULT_CODE"/>
                <input type="hidden" id="oldPartCount" name="oldPartCount" datasource="OLD_PART_COUNT"/>
                <input type="hidden" id="oldSupplierId" name="oldSupplierId" datasource="OLD_SUPPLIER_ID"/>
                <input type="hidden" id="claimId" name="claimId" datasource="CLAIM_ID"/>
                <input type="hidden" id="notbackId" name="notbackId" datasource="NOTBACK_ID"/>
            </form>
		</div>
	</div>
<script type="text/javascript">
var nId=parent.notbackId;
var cId=parent.claimId;
var diaSaveAction = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnApplyAction"; 
var searchUrl = "<%=request.getContextPath()%>/service/oldpartMng/OldPartNotReturnApplyAction/searchParts.ajax?nId="+nId; 
function diaInsertCallBack(res)
{
	try
	{
 		//刷新父页面
		var $f = $("#oldPartNotReturn");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
		doFormSubmit($f,searchUrl,"search",1,sCondition,"oldPartReturnlb"); 
		//刷新子页面
		var $f = $("#di-fm-oldPartNotReturn");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var Url =diaSaveAction+"/searchOldPart.ajax?&cId="+cId+"&nId="+nId;
		doFormSubmit($f,Url,"di_searchOldPart",1,sCondition,"mainOldPartlb");
		$("#part-val0").val("");
		$("#part-val1").val("");
		$("#part-val2").val("");
		$("#part-val3").val("");
		$("#part-val4").val("");
		$("#part-val5").val("");
		$("#part-val6").val("");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
} 
//弹出窗体
var dialog = $("body").data("oldPartNotReturnAdd");
$(function()
{
	//查询按钮响应
	$("#di_searchOldPart").bind("click", function(event){
		var $f = $("#di-fm-oldPartNotReturn");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
    	sCondition = $f.combined() || {};
    	var searchUrl =diaSaveAction+"/searchOldPart.ajax?&cId="+cId+"&nId="+nId;
		doFormSubmit($f,searchUrl,"di_searchOldPart",1,sCondition,"mainOldPartlb");
		$('#selectedPart').show();
	});
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
	$("#dia-oldPartSave").bind("click", function(event){
		var mxids=$("#part-val0").val();
	    if(mxids=="")
	    {
	    	alertMsg.warn("请选择旧件！");
	    	return true;
	    }else{
               $('#faultPartId').val(mxids);
               $('#oldPartCode').val($("#part-val2").val());
               $('#oldPartName').val($("#part-val1").val());
               $('#faultCode').val($("#part-val3").val());
               $('#oldPartCount').val($("#part-val4").val());
               $('#oldSupplierId').val($("#part-val5").val());
               $('#oldPartId').val($("#part-val6").val());
               $('#notbackId').val(nId);
               var $f = $("#fm-partInfo");
               //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
               if (submitForm($f) == false) return false;
               var sCondition = {};
               //将需要提交的内容拼接成json
               sCondition = $f.combined(1) || {};
               var xzcxUrl =diaSaveAction+"/insertParts.ajax";
               doNormalSubmit($f, xzcxUrl, "dia-oldPartSave", sCondition, diaInsertCallBack);
	    }
	});
});
</script>
</div>