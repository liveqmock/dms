<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>

<%
	String action = request.getParameter("action");
	if (action == null)
		action = "1";
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String contentPath = request.getContextPath();
	String workId = request.getParameter("workId");
%>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>附件信息</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
		<div class="page" style="overflow-y: scroll;" id="text1">
				<form  id="dia-dia-fm-workOrder" class="editForm"  >
					<div align="left" >
						<table class="editTable" id="sbjjxx">
						<tr>
							 <td style="color:red;"><label>工单编号：</label></td>
							 <td><input type="text" id="dia-dia-workNo" name="dia-workNo" datasource="WORK_NO"  readonly="readonly" /></td>
							 <td><label>工单VIN：</label></td>
							 <td><input type="text" id="dia-dia-vin" name="dia-vin" datasource="WORK_VIN" value="" readonly="readonly" /></td>
						</tr>
						 <tr>	
							 <td><label>是否外出：</label></td>
						     <td><input type="text" id="dia-dia-ifOut" name="dia-ifOut" value="" datasource="IF_OUT" readonly="readonly" /></td>
						   	 <td><label>报修人：</label></td>
							 <td><input type="text" id="dia-dia-applyUser" name="dia-applyUser" datasource="APPLY_USER"readonly="readonly" /></td>
				          </tr>
						 <tr> 
				             <td><label>报修人电话：</label></td>
				             <td><input type="text" id="dia-dia-applMobil" name="dia-applMobil" datasource="APPLY_MOBIL"readonly="readonly" /></td>
				        	 <td><label>报修时间：</label></td>
				             <td><input type="text" id="dia-dia-applyDate" name="dia-applyDate" value="" datasource="APPLY_DATE" readonly="readonly" /></td>
						 </tr>
				         <tr>  
						   	 <td><label>工单类型：</label></td>
						     <td><input type="text" id="dia-dia-WorkType" name="dia-WorkType" value="" datasource="WORK_TYPE" readonly="readonly" /></td>
				        	 <td><label>派工时间：</label></td>
				             <td><input type="text" id="dia-dia-JOBORDER_TIME" name="dia-JOBORDER_TIME" value="" datasource="JOBORDER_TIME" readonly="readonly" /></td>
				       	 </tr>
				         <tr>  
				       	     <td style="color:red;" ><label>出发位置偏离(米)：</label></td>
						     <td><input type="text" id="dia-dia-SJDDWD" name="dia-SJDDWD" value="" datasource="CLYSJJL" readonly="readonly" /></td>
				             <td style="color:red;"><label>主修人：</label></td>
						     <td><input type="text" id="dia-dia-USER_NAME" name="dia-USER_NAME" value="" datasource="USER_NAME" readonly="readonly"  /></td>
				       	  </tr>
				         <tr>   
				       	     <td><label>主修人电话：</label></td>
				             <td><input type="text" id="dia-dia-repUserTel" name="dia-repUserTel" datasource="MOBIL" readonly="readonly"  /></td>
				      	     <td><label>维修时间：</label></td>
				             <td><input type="text" id="dia-dia-REPAIR_DATE" name="dia-REPAIR_DATE" value="" datasource="REPAIR_DATE" readonly="readonly" /></td>
				        </tr>
				         <tr>
				             <td><label>出发时间：</label></td>
				             <td><input type="text" id="dia-dia-GO_DATE" name="dia-GO_DATE" value="" datasource="GO_DATE" readonly="readonly" /></td>
				         	 <td><label>到达时间：</label></td>
				             <td><input type="text" id="dia-dia-ARRIVE_DATE" name="dia-ARRIVE_DATE" value="" datasource="ARRIVE_DATE" readonly="readonly" /></td>
				         </tr>
				         <tr>
				             <td><label>完工时间：</label></td>
				             <td><input type="text" id="dia-dia-COMPLETE_DATE" name="dia-COMPLETE_DATE" value="" datasource="COMPLETE_DATE" readonly="readonly" /></td>
			              	 <td><label>车牌号：</label></td>
						     <td ><input type="text"  id="dia-dia-CPH" name="dia-CPH" value="" datasource="CPH" readonly="readonly" /></td>
			              </tr>
				         <tr> 
			              	 <td style="color:red;"><label>有效里程(公里)：</label></td>
						     <td ><input type="text"  id="dia-dia-YXLC" name="dia-YXLC" value="" datasource="YXLC" readonly="readonly" /></td>
						 </tr>
				         <tr>  
						     <td ><label>GPS车出发经度：</label></td>
						     <td ><input type="text" id="dia-dia-CLCFJD" name="dia-CLCFJD" value="" datasource="CLCFJD" readonly="readonly" /></td>
				         	 <td ><label>GPS车出发纬度：</label></td>
						     <td ><input type="text" id="dia-dia-CLCFWD" name="dia-CLCFWD" value="" datasource="CLCFWD" readonly="readonly" /></td>
						  </tr>
				          <tr> 
						     <td style="color:red;" ><label >GPS车出发位置：</label></td>
						     <td  colspan="3"><input type="text" style="width:500px"id="dia-dia-CLCFWZ"  name="dia-CLCFWZ" value="" datasource="CLCFWZ" readonly="readonly" /></td>
				          </tr>
				          <tr>
				         	 <td ><label>GPS车到达经度：</label></td>
						     <td ><input type="text" id="dia-dia-CLDDJD" name="dia-ifOut" value="" datasource="CLDDJD" readonly="readonly" /></td>
				         	 <td ><label>GPS车到达纬度：</label></td>
						     <td ><input type="text" id="dia-dia-CLDDWD" name="dia-ifOut" value="" datasource="CLDDWD" readonly="readonly" /></td>
						 </tr>
				         <tr> 
						     <td style="color:red;" ><label>GPS车到达位置：</label></td>
						     <td colspan="3"><input type="text" style="width:500px "id="dia-dia-CLDDWZ"  name="dia-CLDDWZ" value="" datasource="CLDDWZ" readonly="readonly" /></td>
				          </tr>
				         <tr> 
						     <td ><label>手机出发经度：</label></td>
						     <td ><input type="text" id="dia-dia-SJCFJD" name="dia-SJCFJD" value="" datasource="SJCFJD" readonly="readonly" /></td>
				         	 <td ><label>手机出发纬度：</label></td>
						     <td ><input type="text" id="dia-dia-SJCFWD" name="dia-SJCFWD" value="" datasource="SJCFWD" readonly="readonly" /></td>
						  </tr>
				          <tr>  
						     <td style="color:red;" ><label>手机出发位置：</label></td>
						     <td  colspan="3"><input type="text"   style="width:500px " id="dia-dia-SJCFWZ" name="dia-SJCFWZ" value="" datasource="SJCFWZ" readonly="readonly" /></td>
				          </tr>
				          <tr>
				         	 <td ><label>手机到达经度：</label></td>
						     <td ><input type="text" id="dia-dia-SJDDJD" name="dia-SJDDJD" value="" datasource="SJDDJD" readonly="readonly" /></td>
				         	 <td ><label>手机到达纬度：</label></td>
						     <td  ><input type="text" id="dia-dia-SJDDWD" name="dia-SJDDWD" value="" datasource="SJDDWD" readonly="readonly" /></td>
						  </tr>
			              <tr> 
						     <td style="color:red;" ><label>手机到达位置：</label></td>
						     <td style="color:red;" colspan="3"><input type="text"  style="width:500px " id="dia-dia-SJDDWZ" name="dia-SJDDWZ" value="" datasource="SJDDWZ" readonly="readonly" /></td>
				          </tr>
			              <tr>
				              <td><label>报修地址：</label></td>
				              <td colspan="3"><textarea id="dia-dia-applyAddress" style="width: 450px; height: 50px;" datasource="APPLY_ADDRESS" name="APPLY_ADDRESS" readonly="readonly" ></textarea></td>
				            </tr>
				          </table>
					</div>

				</form>
				<div class="formBar">
					<ul>
						<li ><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			<!-- 配件TAB -->
			<div class="page">  
					<form method="post" id="dia_fm_atta" class="editForm" style="display:none"></form>
						<div align="left">
		            	<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-files')">&nbsp;已传附件列表&gt;&gt;</a></legend>
						    <div id="dia-files">
							<table  style="display:none;width:100%;" id="dia-fileslb" name="dia-fileslb" ref="dia-files" refQuery="dia_tab_atta" >
								<thead>
									<tr>
										<th type="single" name="XH" style="display:none"></th>
										<th fieldname="FJMC" >附件名称</th>
										<th fieldname="CJR" >上传人</th>
										<th fieldname="CJSJ">上传时间</th>
										<th colwidth="85" type="link" title="[下载]"  action="doDownloadAtta">操作</th>
									</tr>
								</thead>
							</table>
						</div>
						</fieldset>
		             	</div> 
		             	<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre5" name="btn-pre">上一步</button></div></div></li>
						</ul>
					</div>
				</div>
		</div>
	</div>
	<form id="dialog-fm-download"style="display:none"></form>
	<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#sbjjxx").height(500);
		$("#text1").height(350);
	});
	var $true=true;
	var workId = "<%=workId%>";
	var fileSearchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction/fileSearch.ajax";
	var diasearchUrl = "<%=request.getContextPath()%>/service/claimmng/WorkOrderMngAction";
	$(function() 
	{
		$("#dia-workOrder").height(document.documentElement.clientHeight-20);
		var $f = $("");
		var sCondition = {};
	   	sCondition = $f.combined() || {};
	   	var searchServiceUrl =diasearchUrl+"/queryDtl.ajax?&workId="+workId+"";
	   	sendPost(searchServiceUrl,"",sCondition,searchServiceCallBack,"false");
		//关闭当前页面
		$("button.close").click(function() 
		{
			$.pdialog.close("workNoInfoDtl");
			
			return false;
		});
		//上一步
		$("button[name='btn-pre']").bind("click",function(event){
			$("#tabs").switchTab(parseInt($("#tabs").attr("currentIndex")) - 1);
		});
		//下一步
		$("button[name='btn-next']").bind("click", function(event){
			var $tabs = $("#tabs");
			switch (parseInt($tabs.attr("currentIndex"))) 
			{
				case 0:
					if($true==true){
						var $f = $("");
						var sCondition = {};
						sCondition = $f.combined() || {};
						var fileSearchUrl1 =fileSearchUrl+"?&workId="+workId;
						doFormSubmit($f,fileSearchUrl1,"",1,sCondition,"dia-fileslb");
					}
					$true=false;
					break;
			};
			$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
			//跳转后实现方法
			(function(ci) 
			{
				switch (parseInt(ci)) 
				{
					case 1://第2个tab页					
						break;
					default:
						break;
				}
			})
			(parseInt($tabs.attr("currentIndex")));
		});
	});
	function doDownloadAtta(obj){
		var fjid = $(obj).attr("FID");
		var fjmc = $(obj).attr("FJMC");
		var wjjbs = $(obj).attr("WJJBS");
		var blwjm = $(obj).attr("BLWJM");
		$.filestore.download({"fjid":fjid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
	}
	function searchServiceCallBack(res)
	{
		setEditValue("dia-dia-fm-workOrder",res);
		return true;
	}
	</script>
