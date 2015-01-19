<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="cdrqxx" style="width:100%;">
	<div class="page">
			<div class="pageHeader">
		<form id="dia-fm-search" method="post">	
		<td><input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" /></td>	
		</form>
		</div>
	<div class="pageContent" style="" >
		<form method="post" id="dia-fm-option" class="editForm" >
			 <td><input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID" /></td>	
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-tb-option">
				 <td><input type="hidden" id="dia-EXCEED_ID" name="dia-EXCEED_ID" datasource="EXCEED_ID" /></td>	
	<!-- 			<tr>
						<td><label>服务商代码：</label></td>
						<td><input type="text" id="dia-DEALER_CODE" name="dia-DEALER_CODE"  datasource="DEALER_CODE"  datatype="1,is_null,100" value="" readOnly/></td>
						<td><label>索赔单号：</label></td>
						<td><input type="text" id="dia-CLAIM_NO" name="dia-CLAIM_NO" datasource="CLAIM_NO" datatype="1,is_null,100" value="" /></td>
					</tr>	 -->
				<tr>
				    <td><label>索赔单号：</label></td>
						<td><input type="text" id="dia-CLAIM_NO" name="dia-CLAIM_NO" datasource="CLAIM_NO" datatype="1,is_null,100" value="" /></td>	
					<td><label>超单天数：</label></td>
					<td><input type="text"  id="dia-OVERDUE_DAYS" name="dia-OVERDUE_DAYS" datasource="OVERDUE_DAYS" datatype="1,is_null,100" value="" /></td>
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
		<!-- <form  method="post" id="dia-fm-option-tb">
			<div id="dia-page-list">
			<table width="100%" id="dia-tab-list" name="dia-tab-list" style="display: none" ref="dia-page-list" refQuery="dia-tb-option" pageRows="10">
				<thead>
					<tr>
							 <th type="single" name="XH" style="display:" append="plus|addClaim"></th>
		 
	 
								<th fieldname="CLAIM_ID" >索赔主键</th>
							<th fieldname="CLAIM_NO" >索赔单号</th>
							<th fieldname="OVERDUE_DAYS" >超单天数</th>
							<th fieldname="EXCEED_DAYS" >索赔单天数</th>
							
							
							<th colwidth="85" type="link" title="[删除]"  action="doDelete" >操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		</form> -->
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/claimmng/ClaimMngAction";
var searchClaimUrl = "<%=request.getContextPath()%>/service/serviceparamng/ExceedClaimMngAction/searchClaim.ajax";
var diaAction = "<%=action%>";
//初始化
$(function(){
 
   //绑定保存按钮
	$("#btn-save").bind("click", function(event){

			//获取需要提交的form对象
		var $f = $("#dia-fm-option");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-option").combined(1) || {};
		
		if(diaAction == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	
	});
/*     //重置按钮
	$("#btn-reset").bind("click", function(event){
		$("#dia-fm-option")[0].reset();
	});  */
    
	//修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));
	
		 
		$("#dia-OVERDUE_DAYS").attr("readonly",true);	
			//服务商代码不可修改
		$("#dia-DEALER_CODE").attr("readonly",true);	
	 
	    $("#dia-CLAIM_NO").attr("readonly",true);			
	/*  	$("#dia-tab-list").show();
		$("#dia-tab-list").jTable(); */
		
	/* 	//查询超期索赔单
		doSearch();
		 */
	}else
	{
		
		setDiaDefaultValue();
	}
   }
);


function setDiaDefaultValue()
{

   
}


//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
	$("#tab-list").insertResult(res,0);	
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{		
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


/* function openDia(){
	var id="dia-DEALER_CODE";
	var busType=2;
	showOrgTree(id,busType);
}

function addClaim(){
	var options = {max:false,width:720,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/service/serviceparamng/exceeddate/ExceedClaimEdit.jsp?flag=1", "ExceedClaim", "索赔单超单日期新增", options);
} */
<%-- var action = "<%=action%>";
$(function(){
	if(action != "1"){
		$("#DI_FWSDM").val("服务商代码1");
		$("#DI_FWSMC").val("服务商名称1");
		$("#DI_CDTS").val("7");
		$("#DI_FWSDM").attr("readonly",true);
		$("#DI_FWSMC").attr("readonly",true);
		$("#spdlb").show();
		$("#spdlb").jTable();
	}
});
function addClaim(){
	var options = {max:false,width:720,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqspdxz.jsp?flag=1", "cdrqspd", "索赔单超单日期新增", options);
}
/* function doUpdateClaim(){
	var options = {max:false,width:720,height:200,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/shfw/jcsjgl/ywcsgl/cdrqspdxz.jsp?flag=2", "cdrqspd", "索赔单超单日期编辑", options);
} */
function doDeleteClaim(){
	alertMsg.info("删除成功");
}
function doSave(){
	alertMsg.info("保存成功！");
}
//弹出窗体
var dialog = $("body").data("cdrqxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
}); --%>
</script>