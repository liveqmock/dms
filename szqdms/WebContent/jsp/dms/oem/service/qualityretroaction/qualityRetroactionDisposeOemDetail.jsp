<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>质量反馈新增</title>
<style type="text/css">  
nr {  
    border: 1px solid #B1CDE3; 
    padding:0;   
    margin:0 auto;  
    border-collapse: collapse;  
}  
  
td {  
    border: 1px solid #B1CDE3; 
    background: #fff;  
    font-size:12px;  
    padding: 3px 0px 3px 8px;  
    white-space:nowrap;
}  
</style>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $pjtrue=true;
var authorStatus=null;
var diaSaveAction = "<%=request.getContextPath()%>/service/qualityretroaction/QualityretroactionMngAction";
$(function() 
{
	$("#layout").height(document.documentElement.clientHeight-30);
	$("#sbjjpjlb").height(document.documentElement.clientHeight-30);
		var selectedRows = parent.$("#qualityRetroactionlist").getSelectedRows();
		setEditValue("dia-fm-qualityRetroactionReport",selectedRows[0].attr("rowdata"));
		$("#dia-vin").attr("readonly",true);
		$("#dia-engine_no").attr("readonly",true);
		$("#pjxxH").show();
		$("#dia-fm-qualityRetroactionReport").find("input[name='gcsl']").each(function(){
			var $this = $(this);
			var cusBuyCount = selectedRows[0].attr("CUS_BUY_COUNT");
			if($this.val()==cusBuyCount){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='jsyzt']").each(function(){
			var $this = $(this);
			var driStatus = selectedRows[0].attr("DRI_STATUS");
			if($this.val()==driStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='clyt']").each(function(){
			var $this = $(this);
			var vehicleUseType = selectedRows[0].attr("VEHICLE_USE_TYPE");
			if($this.val()==vehicleUseType){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='rzysj']").each(function(){
			var $this = $(this);
			var dailyWork = selectedRows[0].attr("DAILY_WORK");
			if($this.val()==dailyWork){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='gzdd']").each(function(){
			var $this = $(this);
			var faultAddress = selectedRows[0].attr("FAULT_ADDRESS");
			if($this.val()==faultAddress){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='rcdlzk']").each(function(){
			var $this = $(this);
			var dailyRoad = selectedRows[0].attr("DAILY_ROAD");
			if($this.val()==dailyRoad){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='byzk']").each(function(){
			var $this = $(this);
			var maintenanceStatus = selectedRows[0].attr("MAINTENANCE_STATUS");
			if($this.val()==maintenanceStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='clqk']").each(function(){
			var $this = $(this);
			var vehicleStatus = selectedRows[0].attr("VEHICLE_STATUS");
			if($this.val()==vehicleStatus){
				$this.attr("checked",true);
			}
		});
		$("#dia-fm-qualityRetroactionReport").find("input[name='fktj']").each(function(){
			var $this = $(this);
			var fbackApproace = selectedRows[0].attr("FBACK_APPROACE");
			if($this.val()==fbackApproace){
				$this.attr("checked",true);
			}
		});
		$("input[name='gcsl']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='jsyzt']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='clyt']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='rzysj']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='gzdd']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='rcdlzk']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='byzk']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='clqk']").each(function(){ $(this).attr("disabled",true); });
		$("input[name='fktj']").each(function(){ $(this).attr("disabled",true); });
	 	 
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("qualityRetroactionDisposeDetail");
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
				if($pjtrue){
					var $f = $("#fm-sbjjpj");//获取页面提交请求的form对象
					var sCondition = {};//定义json条件对象
			    	sCondition = $f.combined() || {};
			    	var searchServicePartUrl =diaSaveAction+"/searchQualityParts.ajax?&fbackId="+$("#fbackId").val();
					doFormSubmit($f,searchServicePartUrl,"searchPart",1,sCondition,"sbjjpjlb");
				}
				$pjtrue=false;
				break;
			case 1: 
				if($true){
					var $f = $("");
					var sCondition = {};
			    	sCondition = $f.combined() || {};
			    	var searchDealRemarksUrl =diaSaveAction+"/searchRejectRemarks.ajax?&fbackId="+$("#fbackId").val();
					doFormSubmit($f,searchDealRemarksUrl,"",1,sCondition,"clyjlb");   
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
	//处理
	$("#dispose").bind("click",function(){
		var fbackId=$("#fbackId").val();
		//获取需要提交的form对象
		var $f = $("#dia-fm-qualityRetroactionReport");
		if (submitForm($f) == false) {
			return false;
		}
		var dealRemark =$("#dealRemarks").val();
		if(dealRemark==null||dealRemark==""){
			alertMsg.warn("请填写处理意见!");
			return false;
		}
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-qualityRetroactionReport").combined(1) || {};
		var reportUrl = diaSaveAction + "/qualityretroactionDispose.ajax?fbackId="+fbackId+"";
		doNormalSubmit($f,reportUrl,"dispose",sCondition,diadisposeCallBack);
	});
});
//提报回调方法
function diadisposeCallBack(res){
	try
	{
		var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
		if(result=='1'){
			var $row =  parent.$("#qualityRetroactionlist").getSelectedRows();
			if($row[0]){
				parent.$("#qualityRetroactionlist").removeResult($row[0]);
				parent.$.pdialog.close("qualityRetroactionDisposeDetail");
			}
		}
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//金额格式化
function amountFormat(obj){
  return amountFormatNew($(obj).html());
}
</script>
</head>
<body>
<div id="layout" style="width:100%;overflow:auto;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<!-- tab页  -->
	<div class="tabs" eventType="click" id="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>质量反馈基本信息</span></a></li>
					<li id="pjxxH"><a href="javascript:void(0)"><span>配件信息</span></a></li>
					<li id="clyjH"><a href="javascript:void(0)"><span>驳回意见</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" style="" >
				<form method="post" id="dia-fm-qualityRetroactionReport" class="editForm" style="width: 99%;">
					<input type="hidden" id="vehicleId" datasource="VEHICLE_ID"/>
					<input type="hidden" id="fbackId" datasource="FBACK_ID"/>
					<input type="hidden" id="cusBuyCount" datasource="CUS_BUY_COUNT"/>
					<input type="hidden" id="driStatus" datasource="DRI_STATUS"/>
					<input type="hidden" id="vehicleUseType" datasource="VEHICLE_USE_TYPE"/>
					<input type="hidden" id="dailyWork" datasource="DAILY_WORK"/>
					<input type="hidden" id="faultAddress" datasource="FAULT_ADDRESS"/>
					<input type="hidden" id="dailyRoad" datasource="DAILY_ROAD"/>
					<input type="hidden" id="maintenanceStatus" datasource="MAINTENANCE_STATUS"/>
					<input type="hidden" id="vehicleStatus" datasource="VEHICLE_STATUS"/>
					<input type="hidden" id="fbackApproace" datasource="FBACK_APPROACE"/>
					<div align="left">
					<fieldset>
					<table style="width: 100%" id="nr">
						<tr>
							<td rowspan="2" colspan="2">信息</td>
							<td><label>单位名称：</label></td>
							<td colspan="3"><input type="text" id="comName" name="comName" datasource="COM_NAME" value="" readonly="readonly" /></td>
							<td><label>填报时间：</label></td>
							<td><input type="text" id="writeDate" name="writeDate" datasource="WRITE_DATE"  readonly="readonly"/></td>
						</tr>
						<tr>
							<td><label>姓名：</label></td>
							<td><input type="text" id="dia-name" name="dia-name" datasource="NAME" readonly="readonly"/></td>
							<td><label>电话：</label></td>
							<td><input type="text" id="dia-tel" name="tel" datasource="TEL" readonly="readonly"/></td>
							<td><label>传真：</label></td>
							<td><input type="text" id="dia-fax" name="dia-fax" datasource="FAX" readonly="readonly" /></td>
						</tr>
						<tr>
							<td rowspan="3"  colspan="2">车辆信息</td>
							<td><label>发动机号：</label></td>
							<td><input type="text" id="dia-engine_no" name="engineNo" datasource="ENGINE_NO"readonly="readonly"/></td>
							<td><label>VIN编码：</label></td>
							<td colspan="3"><input type="text" id="dia-vin" name="vin" datasource="VIN"readonly="readonly"/></td>
						</tr>
						<tr>
							<td ><label>车辆型号：</label></td>
							<td><input type="text" id="dia-models_code" name="dia-models_code" datasource="MODELS_CODE" value="" readonly="readonly" /></td>
							<td><label>发动机型号：</label></td>
							<td><input type="text" id="dia-engine_type" name="dia-engine_type" datasource="ENGINE_TYPE" readonly="readonly" value=""  /></td>
							<td><label>发动机订货号：</label></td>
							<td ><input type="text" id="dia-engineBookNo" name="dia-engineBookNo" datasource="ENGINE_BOOK_NO" readonly="readonly" /></td>
						</tr>
						<tr>
							<td ><label>购车日期：</label></td>
							<td><input type="text" id="dia-buy_date" name="dia-buy_date" datasource="BUY_DATE" readonly="readonly"/></td>
							<td><label>故障日期：</label></td>
							<td><input type="text" id="dia-faultDate" name="dia-faultDate" datasource="FAULT_DATE" readonly="readonly"/></td>
							<td><label>故障里程：</label></td>
							<td ><input type="text" id="dia-mileage" name="dia-mileage" datasource="FAULT_MIELES" readonly="readonly"/></td>
						</tr>
						<tr>
							<td rowspan="2"  colspan="2">客户信息</td>
							<td><label>公司名称：</label></td>
							<td><input type="text" id="dia-cusComName" name="dia-cusComName" datasource="CUS_COM_NAME" readonly="readonly"/></td>
							<td><label>购车数量：</label></td>
							<td colspan="3">
								
								<input type="radio" name="gcsl" value="<%=DicConstant.GCSL_01%>"/>50辆以上 
								<input type="radio"  name="gcsl" value="<%=DicConstant.GCSL_02%>"/>10-50辆间
								<input type="radio"  name="gcsl" value="<%=DicConstant.GCSL_03%>"/>10辆以下
								<input type="radio"  name="gcsl" value="<%=DicConstant.GCSL_04%>"/>1辆
							</td>
						</tr>
						<tr>
							<td><label>联系人：</label></td>
							<td><input type="text" id="dia-cusLinkMan" name="dia-cusLinkMan" datasource="CUS_LINK_MAN"readonly="readonly"/></td>
							<td><label>电话：</label></td>
							<td ><input type="text" id="dia-cusTel" name="dia-cusTel" datasource="CUS_TEL"readonly="readonly" /></td>
							<td><label>传真：</label></td>
							<td ><input type="text" id="dia-cusFax" name="dia-cusFax" datasource="CUS_FAX" readonly="readonly"/></td>
						</tr>
						<tr>
							<td  colspan="2">驾驶员信息</td>
							<td><label>姓名：</label></td>
							<td><input type="text" id="dia-driName" name="dia-driName" datasource="DRI_NAME" readonly="readonly"/></td>
							<td><label>电话：</label></td>
							<td ><input type="text" id="dia-driTel" name="dia-driTel" datasource="DRI_TEL" readonly="readonly"/></td>
							<td><label>状态：</label></td>
							<td >
								<input type="radio" name="jsyzt" value="<%=DicConstant.JSYZT_01%>"/>固定
								<input type="radio" name="jsyzt" value="<%=DicConstant.JSYZT_02%>"/>不固定
							</td>
						</tr>
						<tr>
							<td  colspan="2">车辆用途</td>
							<td colspan="2">
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_01%>"/>运输
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_02%>"/>工程
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_03%>"/>专用 
								<input type="radio" name="clyt" value="<%=DicConstant.FKCLYT_04%>"/>其他
							</td>
							<td ><label>日作业时间：</label></td>
							<td colspan="3" style="width:300px">
								 <input type="radio"  name="rzysj" value="<%=DicConstant.RZYSJ_01%>"/>8小时以上
							 	 <input type="radio"  name="rzysj" value="<%=DicConstant.RZYSJ_02%>"/>4-8小时间
							 	 <input type="radio"  name="rzysj" value="<%=DicConstant.RZYSJ_03%>"/>4小时一下
							</td>
						</tr>
						<tr>
							<td  colspan="2">车辆故障地点</td>
							<td colspan="2">
								<input type="radio" name="gzdd" value="<%=DicConstant.CLGZDD_01%>"/>公路
								<input type="radio" name="gzdd" value="<%=DicConstant.CLGZDD_02%>"/>非公路
								<input type="radio" name="gzdd" value="<%=DicConstant.CLGZDD_03%>"/>矿
							</td>
							<td ><label>日常道路状况：</label></td>
							<td colspan="3" style="width:300px">
								 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_01%>"/>高速
							 	 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_02%>"/>普通
							 	 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_03%>"/>非硬化路面
							 	 <input type="radio" name="rcdlzk" value="<%=DicConstant.RCDLZK_04%>"/>坡道
							</td>
						</tr>
						<tr>
							<td  colspan="2">保养状况</td>
							<td colspan="2">
								<input type="radio" name="byzk" value="<%=DicConstant.BYZK_01%>"/>良好
								<input type="radio" name="byzk" value="<%=DicConstant.BYZK_02%>"/>一般
								<input type="radio" name="byzk" value="<%=DicConstant.BYZK_03%>"/>差
							</td>
							<td ><label>车辆状况：</label></td>
							<td colspan="3" style="width:300px">
								 <input type="radio" name="clqk" value="<%=DicConstant.CLZK_01%>"/>标载
							 	 <input type="radio" name="clqk" value="<%=DicConstant.CLZK_02%>"/>超载
							 	 <input type="radio" name="clqk" value="<%=DicConstant.CLZK_03%>"/>严重超载
							</td>
						</tr>
						<tr>
							<td  colspan="2">途径</td>
							<td><label>反馈途径：</label></td>
							<td ><input type="radio" name="fktj" value="<%=DicConstant.FKTJ_01%>"/>已发邮箱
							</td>
							<td ><label>数量：</label></td>
							<td colspan="3" style="width:300px">
								 <font>共</font><input type="text" id="dia-amount" name="dia-amount" datasource="AMOUNT" readonly="readonly" /><font>张</font>
							</td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
						    <td colspan="7"><textarea class="" rows="2" id="dia-remarks" name="dia-remarks" datasource="REMARKS" style="width:100%" readonly="readonly"></textarea></td>
						</tr>
						<tr>
							<td><label>处理意见：</label></td>
							<td colspan="7"><textarea class="" rows="2" id="dealRemarks" name="dealRemarks" datasource="DEAL_REMARKS" style="width:100%" datatype="0,is_null,1000" ></textarea></td>
						</tr>
					</table>
					<br/>
		
				</fieldset>
					</div>
				</form>
			</div>
			<div class="formBar">
					<ul>
						<li id="dia-nextH1"><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
			</div>
			</div>
			<!-- 配件TAB -->
			<div class="page">
			<div class="pageHeader">
			<form id="fm-sbjjpj" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
							<li id="dia-nextH2"><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						    
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent">
				<div id=sbjjpjl>
					<table width="100%" id="sbjjpjlb" name="sbjjpjlb" multivals="partDeleteVal" ref="sbjjpjl"  style="display: none"  refQuery="fm-sbjjpjTable">
						<thead>
							<tr>
								<th fieldname="PART_CODE" >配件代码</th>
								<th fieldname="PART_NAME" >配件名称</th>
								<th fieldname="SUPPLIER_NAME" >供应商名称</th>
								<th fieldname="COUNT" >数量</th>
								<th fieldname="UNIT_PRICE" refer="amountFormatStr"  align="right">单价(元)</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
				</table>
				</div>
				</div>
			</div>
			<!-- 驳回意见TAB -->
			<div class="page">
			<div class="pageHeader">
			<form id="fm-clyj" method="post">
				<div class="searchBar" align="left">
					<div class="subBar">
						<ul>
							<li id="dia-dispose"><div class="button"><div class="buttonContent"><button type="button" id="dispose">处&nbsp;&nbsp;理</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						    <li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
			</div>
			<div class="pageContent">
				<div id=clyj>
					<table width="100%" id="clyjlb" name="clyjlb" ref="clyj"  style="display: none" >
						<thead>
							<tr>
								<th type="single" name="XH" style="display:none"></th>
								<th fieldname="REJECT_REMARKS" >驳回意见</th>
								<th fieldname="REJECT_DATE" >驳回时间</th>
							</tr>
						</thead>
				</table>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>