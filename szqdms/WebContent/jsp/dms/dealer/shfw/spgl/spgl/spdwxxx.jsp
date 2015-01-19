<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if (action == null)
		action = "2";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔维修信息</title>
<script type="text/javascript">
var action = "<%=action%>";
var spdwxxx_dialog = parent.$("body").data("spdwxxx");
$(function() 
{
	//设置高度
	$("#dia-div-wxxxbj").height(document.documentElement.clientHeight-30);
	$("#gclibxk").show();
	$("button[name='btn-pre']").bind("click",function(event) 
		{
				$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex")) - 1);
		});
	$("button[name='btn-next']").bind("click", function(event) 
		{
				var $tabs = $("#dia-tabs");
				switch (parseInt($tabs.attr("currentIndex"))) 
				{
					case 0:
						break;
					case 1:
						break;
				}
			$tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
			//跳转后实现方法
			(function(ci) 
			{
				switch (parseInt(ci)) 
				{
					case 1://第2个tab页					
						break;
					case 2://第3个tab页
						break;
					default:
						break;
				}
			})
			(parseInt($tabs.attr("currentIndex")));
	 });
	$("button.close").click(function() 
	{
		parent.$.pdialog.closeCurrent();
		return false;
	});

	if(action==1)
	{
		$("#dia-spdzt").val("未提报");
		$("#dia-tab-pjlb").jTable();
	}else{
		$("#dia-spdzt").val("自动审核驳回");
		$("#sudfjlb").show();
		$("#sudfjlb").jTable(); //附件
		$("#sudshgj").show();
		$("#sudshgj").jTable(); //审核轨迹
		$("#dia-tab-pjlb").jTable();
	}	
});
function afterDicItemClick(id, $row, selIndex) 
{
	if(id=="dia-in-fwlx")
	{
		if($("#"+id).attr("code") != "1")
		{
			$("#dia-div-wcxx").show();
			$("#dia-con-ecwczs").hide();
		}else
		{
			$("#dia-div-wcxx").hide();
			$("#dia-in-ecwc").hide();
		}
		return true;
	}
	if(id=="dia-in-wccs")
	{
		if($("#"+id).attr("code") != "1")
		{
			$("#dia-con-ecwczs").show();
			$("#dia-in-ecwc").show();
		}else
		{
			$("#dia-con-ecwczs").hide();
			$("#dia-in-ecwc").hide();
		}
		return true;
	}
	if (id == "dia-in-sfysq") 
	{
		if ($("#" + id).attr("code") != "1") 
		{
			$("#xzysq").hide();
		} else {
			$("#xzysq").show();
		}
		return true;
	}
	if (id == "dia-in-splx") 
	{
		if ($("#" + id).attr("code") != "3") 
		{
			$("#xzfwhd").hide();
		} else {
			$("#xzfwhd").show();
		}
		if ($("#" + id).attr("code") == "4" || $("#" + id).attr("code") == "6" || $("#" + id).attr("code") == "7") 
		{
			$("#gclibxk").hide();
		} else {
			$("#gclibxk").show();
		}
		if ($("#" + id).attr("code") != "2") 
		{
			$("#xzsbfy").hide();
		} else {
			$("#xzsbfy").show();
		}
		if ($("#" + id).attr("code") == "6" || $("#" + id).attr("code") == "7") 
		{
			$("#xzaqjc").show();
		} else {
			$("#xzaqjc").hide();
		}
		if ($("#" + id).attr("code") != "5") 
		{
			$("#xzdb").hide();
		} else {
			$("#xzdb").show();
		}
		if ($("#" + id).attr("code") != "8") 
		{
			$("#xzsbjjdd").hide();
		} else {
			$("#xzsbjjdd").show();
		}
		return true;
	}
	if(id=="dia-in-clcs0")
	{
		if ($("#" + id).attr("code") == "2")
		{
			$("#dia-xjkz1").hide();
			$("#dia-xjjgkz1").hide();
			$("#dia-in-spclf0").val("0");
		} else
		{
			$("#dia-xjkz1").show();
			$("#dia-xjjgkz1").show();
			$("#dia-in-spclf0").val("索赔单价*件数");
		}
	}
	return true;
}
function checkVin() 
{
	var options = {max : true,mask : true,mixable : true,minable : true,resizable : true,drawable : true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/spgl/xzcl.jsp","xzcl", "处理单信息维护-校验车辆", options);
}
function saveTabs()
{

}
function checkVinCallBack()
{
	$("#dia-in-vin").val("VIN1");
	$("#dia-in-engineno").val("发动机1");
	$("#dia-in-clxh").val("车辆型号1");
	$("#dia-in-hgzh").val("合格证号1");
	$("#dia-in-fdjxh").val("发动机型号1");
	$("#dia-in-fdjxh").val("6*2");
	return true;
}
function selectPreauth()
{
	alertMsg.info("弹出选择预授权页面");
	return true;
}
function selectActive()
{
	alertMsg.info("弹出选择服务活动页面");
	return true;
}
function selectPartOrder()
{
	alertMsg.info("弹出选择配件订单页面");
	return true;
}
function selectOldPart(partIndex)
{
	alertMsg.info("弹出选择旧件页面");
	return true;
}
function selectNewPart(partIndex)
{
	alertMsg.info("弹出选择新件页面");
	return true;
}
function selectQiao(partIndex)
{	
	alertMsg.info("弹出选择桥编码页面");
	return true;
}
function selectZy(partIndex)
{	
	alertMsg.info("弹出选择作业页面");
	return true;
}
function doAddAtt()
{
	alertMsg.info("选择文件后点击上传");
	return true;
}
function selectGzms(partIndex)
{
	alertMsg.info("弹出选择故障模式页面");
	return true;
}
function selectOther()
{
	alertMsg.info("弹出选择其他项目费用窗口");
	return true;
}
function doDiaSpdSave()
{
	alertMsg.info("保存成功。");
	return true;
}
function doDiaSpdReport()
{
	alertMsg.info("提报成功。");
	return true;
}
function doDiaListSave()
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/spgl/spdpjgl.jsp", "spdpjgl", "配件维护", options);
}
function addGzdm()
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/spgl/xzgz.jsp", "xzgz", "故障维护", options);
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					    <li><a href="javascript:void(0)"><span>外出信息</span></a></li>
						<li><a href="javascript:void(0)"><span>维修信息</span></a></li>
						<li><a href="javascript:void(0)"><span>附件信息</span></a></li>
						<li><a href="javascript:void(0)"><span>审核信息</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div class="page" id="dia-div-wcxxlb" style="overflow:auto;">
					<div class="pageContent">
						<form method="post" id="dia-fm-wxdawh" class="editForm" style="width: 99%;">
							<div align="left">
						<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-tab-wxxx')">&nbsp;索赔单信息编辑&gt;&gt;</a></legend>
						<table class="editTable" id="dia-tab-wxxx">
							<tr><td><label>派工单号：</label></td>
							    <td><input type="text" id="dia-pgdh" value="派工单号01" readonly="readonly"/></td>
							    <td><label>索赔单号：</label></td>
							    <td><input type="text" id="dia-spdh" value="系统自动产生" readonly="readonly"/></td>
							    <td><label>服务站名称：</label></td>
							    <td><input type="text" id="dia-dealerId" value="服务站1" readonly="readonly"/></td>
							    
							</tr>
							<tr>
							    <td><label>索赔单状态：</label></td>
							    <td><input type="text" id="dia-spdzt" value="" readonly="readonly"/></td>
							    <td><label>服务类型：</label></td>
							    <td colspan="3">
								    <select type="text" id="dia-in-fwlx"  name="dia-in-fwlx" kind="dic" src="E#1=送修:2=救援:3=跟踪" datasource="SERVICE_TYPE" datatype="0,is_null,30" value="">
								    		<option value="1" selected>送修</option>
								    </select>
							    </td>
							</tr>
						</table>
						</fieldset>	
					  </div>
						</form>
						<div id="dia-div-wcxx" style="display: none">
					<div class="tabs" currentIndex="0" eventType="click">
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
								<li><a href="javascript:void(0)"><span>外出信息</span></a></li>
					            <li id="dia-con-ecwczs"><a href="javascript:void(0)"><span>二次外出信息</span></a></li>
								</ul>
							</div>
						</div>
					 <div class="tabsContent" >
					     <div class="page">
							<table class="editTable" id="dia-tab-wcxx">
								<tr><td><label>外出次数：</label></td>
								    <td>
								         <select type="text" id="dia-in-wccs"  name="dia-in-wccs" kind="dic" src="E#1=1次外出:2=2次外出" datasource="OUT_COUNT" datatype="0,is_null,30" value="">
									    		<option value="1" selected>1次</option>
									    </select>
								   </td> 
								   <td><label>外出总费用：</label></td>
								   <td ><input type="text" id="dia-in-wczfy" value="一次外出费用+二次外出费用" readonly="readonly"/></td>
								   <td><label>一次外出费用：</label></td>
								   <td ><input type="text" id="dia-in-wczfy" value="在途补助+服务车费+差旅费+车船费+其他费用" readonly="readonly"/></td>     
								</tr>
								<tr>
								    <td><label>外出方式：</label></td>
								    <td> 
								        <select type="text" id="dia-in-wcfs"  name="dia-in-wcfs" kind="dic" src="E#1=自备车:2=非自备车" datasource="OUT_TYPE" datatype="0,is_null,30" value="">
									    		<option value="1" selected>自备车</option>
									    </select>
									</td>
								    <td><label>外出人数：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"  datatype="0,is_null,30" value=""/>
								    </td>
								    <td><label>外出人员：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcry"  name="dia-in-wcry"  datatype="1,is_null,30" value=""/>
								    </td>
								</tr>
								<tr>
								    <td><label>出发时间：</label></td>
								    <td> 
								        <input type="text" id="dia-in-cfsj"  name="dia-in-cfsj"  datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" />
									</td>
								    <td><label>有效里程：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"  datatype="0,is_number,30" value=""/>
								    </td>
								    <td><label>服务车牌号：</label></td>
								    <td>
									    <input type="text" id="dia-in-fwcph"  name="dia-in-fwcph"  datatype="1,is_null,30" value=""/>
								    </td>
								</tr>
								<tr>
								    
									<td><label>到达时间：</label></td>
								    <td > 
								        <input type="text" id="dia-in-ddsj"  name="dia-in-ddsj"  datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" />
									</td>
									<td><label>离开时间：</label></td>
								    <td colspan="3"> 
								        <input type="text" id="dia-in-lksj"  name="dia-in-lksj"  datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" />
									</td>
									
								</tr>
								<tr>
									<td><label>有效天数：</label></td>
								    <td> 
								        <input type="text" id="dia-in-yxts"  name="dia-in-yxts" value="离开时间-出发时间计算" datatype="0,is_null,30" value=""/>
									</td>
									<td><label>外出时间：</label></td>
								    <td colspan="3"> 
								        <select type="text" id="dia-in-wcsj"  name="dia-in-wcsj" kind="dic" src="E#1=白天:2=黑天" datasource="OUT_TIME" datatype="0,is_null,30" value="">
									    		<option value="1" selected>白天</option>
									    </select>
									    由时间设定规则确定出白天还是黑天
									</td>
								</tr>
								<tr>
								    <td><label>在途餐补：</label></td>
								    <td>
									    <input type="text" id="dia-in-ztcb"  name="dia-in-ztcb"  datatype="1,is_null,30" value="人数*有效天数*（人/天单价）    只针对自备车" readonly="readonly"/>
								    </td>
									<td><label>服务车费：</label></td>
								    <td> 
								         <input type="text" id="dia-in-fwcf"  name="dia-in-fwcf"  datatype="1,is_null,30" value="有效里程*系统设定的单价  只针对自备" readonly="readonly"/>
									</td>
								    <td><label>车船费：</label></td>
								    <td>
									     <input type="text" id="dia-in-ccf"  name="dia-in-ccf"  datatype="1,is_null,30" value="人数*有效天数*（人/天单价）    只针对非自备车 " readonly="readonly"/>
								    </td>
								</tr>
								<tr>
								    <td><label>差旅费：</label></td>
								    <td>
									     <input type="text" id="dia-in-clf"  name="dia-in-clf"  datatype="1,is_null,30" value="人数*有效天数*（人/天单价）    只针对非自备车" readonly="readonly"/>
								    </td>
								    <td ><label>其他费：</label></td>
								    <td colspan="3">
									     <input type="text" id="dia-in-qtf"  name="dia-in-qtf"  datatype="1,is_null,30" value="需手工填写的费用" readonly="readonly" hasBtn="true" callFunction="selectOther()"/>
								    </td>
								</tr>
								<tr>
								    <td><label>其他说明：</label></td>
								    <td colspan="5">
									    <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-bz" datasource="BZ"  datatype="1,is_null,100"></textarea>
								    </td>
								</tr>
							</table>
						</div>	
						<div class="page">
						   <div class="pageContent" id="dia-in-ecwc"  style="display: none" >
							  <table class="editTable" id="dia-tab-dcwc">
							  <tr>
								    <td><label>外出费用合计（二次）：</label></td>
								    <td colspan="5"><input type="text" id="dia-in-wczfy" value="服务车费+其他费用" readonly="readonly"/></td> 
			                   </tr>
								<tr>
								    <td><label>外出方式（二次）：</label></td>
								    <td> 
								        <select type="text" id="dia-in-wcfs"  name="dia-in-wcfs" kind="dic" src="E#1=自备车:2=非自备车" datasource="OUT_TYPE" datatype="0,is_null,30" value="">
									    		<option value="1" selected>自备车</option>
									    </select>
									</td>
								    <td><label>外出人数（二次）：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"  datatype="0,is_null,30" value=""/>
								    </td>
								    <td><label>外出人员（二次）：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcry"  name="dia-in-wcry"  datatype="1,is_null,30" value=""/>
								    </td>
								</tr>
								<tr>
								    <td><label>出发时间（二次）：</label></td>
								    <td> 
								        <input type="text" id="dia-in-cfsj"  name="dia-in-cfsj"  datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" />
									</td>
								    <td><label>有效里程（二次）：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"  datatype="0,is_number,30" value=""/>
								    </td>
								    <td><label>服务车牌号（二次）：</label></td>
								    <td>
									    <input type="text" id="dia-in-fwcph"  name="dia-in-fwcph"  datatype="1,is_null,30" value=""/>
								    </td>
								</tr>
								<tr>
								    
									<td><label>到达时间（二次）：</label></td>
								    <td > 
								        <input type="text" id="dia-in-ddsj"  name="dia-in-ddsj"  datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" />
									</td>
									<td><label>离开时间（二次）：</label></td>
								    <td colspan="3"> 
								        <input type="text" id="dia-in-lksj"  name="dia-in-lksj"  datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" />
									</td>
									
								</tr>
								<tr>
									<td><label>有效天数（二次）：</label></td>
								    <td> 
								        <input type="text" id="dia-in-yxts"  name="dia-in-yxts" value="离开时间-出发时间计算" datatype="0,is_null,30" value=""/>
									</td>
									<td><label>外出时间（二次）：</label></td>
								    <td colspan="3"> 
								        <select type="text" id="dia-in-wcsj"  name="dia-in-wcsj" kind="dic" src="E#1=白天:2=黑天" datasource="OUT_TIME" datatype="0,is_null,30" value="">
									    		<option value="1" selected>白天</option>
									    </select>
									    由时间设定规则确定出白天还是黑天
									</td>
								</tr>
								<tr>
									<td><label>服务车费（二次）：</label></td>
								    <td> 
								         <input type="text" id="dia-in-fwcf"  name="dia-in-fwcf"  datatype="1,is_null,30" value="有效里程*系统设定的单价  只针对自备" readonly="readonly"/>
									</td>
									 <td ><label>其他费（二次）：</label></td>
								    <td colspan="3">
									     <input type="text" id="dia-in-qtf"  name="dia-in-qtf"  datatype="1,is_null,30" value="需手工填写的费用" readonly="readonly" hasBtn="true" callFunction="selectOther()"/>
								    </td>
								</tr>
								<tr>
								    <td><label>其他说明：</label></td>
								    <td colspan="5">
									    <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-bz" datasource="BZ"  datatype="1,is_null,100"></textarea>
								    </td>
								</tr>
							</table>
						   </div>	
						</div>
					</div>	
				</div>	
		  </div>
						<div class="formBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaSpdSave();">保&nbsp;&nbsp;存</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-report" onclick="doDiaSpdReport();">提&nbsp;&nbsp;报</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="page" id="dia-div-wxxxbj" style="overflow:auto;">
					<div class="pageContent">
						<form method="post" id="dia-fm-wxdawh" class="editForm" style="width: 99%;">
							<div align="left">
								<fieldset>
									<legend align="right">
										<a onclick="onTitleClick('dia-tab-wxxx')">&nbsp;维修信息编辑&gt;&gt;</a>
									</legend>
									<table class="editTable" id="dia-tab-wxxx">
										<tr>
											<td><label>VIN：</label></td>
											<td><input type="text" id="dia-in-vin" name="dia-in-vin" datasource="VIN" datatype="0,is_digit_letter,17" operation="like" />(请输入后8位或者17位)</td>
											<td><label>发动机号：</label></td>
											<td><input type="text" id="dia-in-engineno" name="dia-in-engineno" datasource="ENGINE_NO" datatype="0,is_digit_letter,30" operation="like" /></td>
											<td colspan="2"><div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin"onclick="checkVin()">验&nbsp;&nbsp;证</button></div></div></td>
										</tr>
										<tr>
											<td><label>车辆型号：</label></td>
											<td><input type="text" id="dia-in-clxh" name="dia-in-clxh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
											<td><label>合格证号：</label></td>
											<td><input type="text" id="dia-in-hgzh" name="dia-in-hgzh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
											<td><label>发动机型号：</label></td>
											<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
										</tr>
										<tr>
											<td><label>用户类型：</label></td>
											<td>
												<select type="text" id="dia-in-yhlx" name="dia-in-yhlx" kind="dic" src="E#1=民车:2=军车" datatype="0,is_null,30" value="">
													<option value="1" selected>民车</option>
												</select>
											</td>
											<td><label>车辆用途：</label></td>
											<td>
												<select type="text" id="dia-in-clyt" name="dia-in-clyt" kind="dic" src="E#1=非公路用车:2=公路用车" datatype="0,is_null,30" value="" disabled="disabled">
													<option value="1" selected>非公路用车</option>
												</select>
											</td>
											<td><label>驱动形式：</label></td>
											<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="校验车辆信息后自动带出" readonly="readonly" /></td>
										</tr>
										<tr id="gclibxk" style="display: none">
											<td><label>购车日期：</label></td>
											<td><input type="text" id="dia-in-clxsrq" name="dia-in-clxsrq" value="2013-01-01" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
											<td><label>行驶里程：</label></td>
											<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="3100" datatype="0,is_number,30" /></td>
											<td><label>保修卡号：</label></td>
											<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="保修卡号1" datatype="0,is_number,30" /> </td>
										</tr>
										<tr >
											<td><label>出厂日期：</label></td>
											<td><input type="text" id="dia-in-ccrq" name="dia-in-ccrq" value="2013-01-01" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" /></td>
											<td><label>首保日期：</label></td>
											<td colspan="3"><input type="text" id="dia-in-sbrq" name="dia-in-xslc" value="2013-04-01" datatype="0,is_number,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
											</td>
										</tr>
										<tr>
											<td><label>车牌号码：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="车牌号01" datatype="0,is_number,30" /></td>
											<td><label>用户名称：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="张三" datatype="0,is_number,30" /></td>
											<td><label>身份证号：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" datatype="0,is_number,30" /></td>

										</tr>
										<tr>
											<td><label>联系人：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="李四" datatype="0,is_number,30" /></td>
											<td><label>电话：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" datatype="0,is_number,30" /></td>
											<td><label>用户地址：</label></td>
											<td><textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" datatype="1,is_null,100">地址</textarea></td>
										</tr>
										<tr>
											<td><label>处理单号：</label></td>
											<td><div id="dia-spdh">系统自动生成</div></td>
											<td><label>处理单类型：</label></td>
											<td>
												<select type="text" id="dia-in-splx" name="dia-in-splx" kind="dic" src="E#1=正常保修:2=首保:3=服务活动:4=售前维修:5=定保:6=售前培训检查:7=安全检查:8=三包急件索赔" datasource="CLAIM_TYPE" datatype="0,is_null,30" value="">
														<option value="1" selected>正常保修</option>
												</select>
											</td>
											<td><label>是否预授权：</label></td>
											<td>
												<select type="text" id="dia-in-sfysq" name="dia-in-sfysq" kind="dic" src="E#0=否:1=是" datasource="SF_PREAUTH" datatype="0,is_null,30" value="">
														<option value="0" selected>否</option>
												</select>
											</td>
										</tr>
										<tr id="xzysq" style="display: none">
											<td><label>选择预授权：</label></td>
											<td colspan="5"><input type="text" id="dia-in-ysqh" name="dia-in-ysqh" datatype="0,is_null,30" value="预授权编号1" hasBtn="true" callFunction="selectPreauth()" /><input type="hidden" id="dia-in-ysqid" name="dia-in-ysqid" datasource="PREAUTH_ID" datatype="1,is_null,30" value="1" /></td>
										</tr>
										<tr id="xzfwhd" style="display: none">
											<td><label>选择服务活动：</label></td>
											<td colspan="5"><input type="text" id="dia-in-fwhdh" name="dia-in-fwhdh" datatype="0,is_null,30" value="服务活动编号1" hasBtn="true" callFunction="selectActive()" /><input type="hidden" id="dia-in-fwhid" name="dia-in-fwhid" datasource="ACTIVE_ID" datatype="1,is_null,30" value="1" /></td>
										</tr>
										<tr id="xzsbfy" style="display: none">
											<td><label>首保费用：</label></td>
											<td colspan="5"><input type="text" id="dia-in-sbfy" name="dia-in-sbfy" datatype="0,is_null,30" value="首保金额" /></td>
										</tr>
										<tr id="xzaqjc" style="display: none">
											<td><label>安全检查/售前培训费用：</label></td>
											<td colspan="5"><input type="text" id="dia-in-aqjc" name="dia-in-aqjc" datatype="0,is_null,30" value="安全检查费用" /></td>
										</tr>
										<tr id="xzdb" style="display: none">
											<td><label>选择定保次数：</label></td>
											<td colspan="5"><select type="text" id="dia-in-xzdbcs" name="dia-in-xzdbcs" kind="dic" src="E#1=1次:2=2次:3=3次" datatype="0,is_null,30" value="">
													<option value="1" selected>1次</option>
												</select>
											</td>
										</tr>
										<tr id="xzsbjjdd" style="display: none">
											<td><label>选择三包急件订单：</label></td>
											<td colspan="5"><input type="text" id="dia-in-sbjjdd" name="dia-in-sbjjdd" datatype="0,is_null,30" value="三包急件订单" hasBtn="true" callFunction="selectPartOrder()" /><input type="hidden" id="dia-in-partOrderid" name="dia-in-partOrderid" datatype="1,is_null,30" value="1" /></td>
										</tr>
										<tr>
											<td><label>检修人：</label></td>
											<td><input type="text" id="dia-in-jxr" name="dia-in-jxr" value="XXX" datatype="0,is_null,40" /></td>
											<td><label>检修时间：</label></td>
											<td><input type="text" id="dia-in-jxrq" name="dia-in-jxrq" value="2014-05-28 10:00" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
											<td><label>检修地址：</label></td>
											<td><textarea id="dia-in-jxdz" style="width: 150px; height: 30px;" name="dia-in-jxdz" datatype="1,is_null,100">地址</textarea></td>
										</tr>
										<tr>
											<td><label>报修人：</label></td>
											<td><input type="text" id="dia-in-bxr" name="dia-in-bxr" datatype="0,is_null,40" value="王五"/></td>
											<td><label>报修时间：</label></td>
											<td><input type="text" id="dia-in-bxrq" name="dia-in-bxrq" value="2014-05-28 9:00" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
											<td><label>报修人电话：</label></td>
											<td><input type="text" id="dia-in-bxrdh" name="dia-in-bxrdh" value="xxx" datatype="0,is_null,40" /></td>
										</tr>
										<tr>
											<td><label>报修人类型：</label></td>
											<td><select type="text" id="dia-in-bxrlx" name="dia-in-bxrlx" kind="dic" src="E#0=车主:1=家人:2=司机:3=其他" datatype="0,is_null,30" value="">
														<option value="0" selected>车主</option>
												</select>
											</td>
											<td><label>报修地址：</label></td>
											<td ><textarea id="dia-in-bxdz" style="width: 150px; height: 30px;" name="dia-in-bxdz" datatype="1,is_null,100">地址</textarea></td>
											<td><label>故障分析：</label></td>
											<td >
											 <input type="text" id="dia-in-hsj0" name="dia-in-gzfx0"  datatype="1,is_null,30" value="故障代码1" hasBtn="true" callFunction="selectGzms(0)"/>
											</td>
										</tr>
										<tr>
											<td><label>故障信息来源：</label></td>
											<td><input type="text" id="dia-in-gzxxly" name="dia-in-gzxxly" datatype="1,is_null,40" value="电话" /></td>
											<td><label>故障地点：</label></td>
											<td ><input type="text" id="dia-in-gzdd" name="dia-in-gzdd" value="地点1" datatype="1,is_null,40" /></td>
											<td><label>故障时间：</label></td>
											<td><input type="text" id="dia-in-gzsj" name="dia-in-gzsj" value="2014-05-28 8:00" datatype="0,is_null,30" onclick="WdatePicker({isShowWeek:false,doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm'})" /></td>
										</tr>
										<tr>
											<td><label>备注：</label></td>
											<td colspan="5"><textarea id="dia-in-bxbz" style="width: 450px; height: 50px;" name="dia-in-bxbz" datatype="1,is_null,100"></textarea></td>
										</tr>
									</table>
								</fieldset>
							</div>
						</form>
						<div id="dia-lpmx" style="height:380px;overflow:hidden;">
							<table style="display:;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-lpmx" pageRows="15" edit="false" >
									<thead>
										<tr>
											<th type="single" name="XH" style="display:" append="plus|addGzdm" colwidth="20"></th>
											<th fieldname="GZDM" colwidth="60">故障代码</th>
											<th fieldname="GZMC" colwidth="60">故障名称</th>
											<th fieldname="ZY" colwidth="60">作业</th>
											<th fieldname="WXGS" colwidth="60">维修工时</th>
											<th fieldname="GSDJ" colwidth="60">工时单价</th>
											<th fieldname="XJDJ" colwidth="60">星级单价</th>
											<th fieldname="JLDJ" colwidth="60">激励单价</th>
											<th fieldname="GSF" colwidth="80">工时费</th>
											<th colwidth="120" type="link" title="[维护配件]|[删除]"  action="doDiaListSave|doDiaListDelete">操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="display:;"><div><input type="radio" name="in-xh" /></div></td>
											<td><div>故障代码01</div></td>
											<td><div>故障代码01</div></td>
											<td><div>作业1</div></td>
											<td><div>5</div></td>
											<td><div>36</div></td>
											<td><div>42</div></td>
											<td><div>2</div></td>
											<td><div>5*(36+4+2)</div></td>
											<td><a href="#" onclick="doDiaListSave()" class="op">[维护配件]</a><a href="#" onclick="doDiaListDelete()" class="op">[删除]</a></td>
										</tr>
										<tr>
											<td style="display:;"><div><input type="radio" name="in-xh" /></div></td>
											<td><div>故障代码02</div></td>
											<td><div>故障代码02</div></td>
											<td><div>作业1</div></td>
											<td><div>5</div></td>
											<td><div>36</div></td>
											<td><div>42</div></td>
											<td><div>2</div></td>
											<td><div>5*(36+4+2)</div></td>
											<td><a href="#" onclick="doDiaListSave()" class="op">[维护配件]</a><a href="#" onclick="doDiaListDelete()" class="op">[删除]</a></td>
										</tr>
									</tbody>
							</table>
						</div>
						<div class="formBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
							</ul>
						</div>
					</div>
				</div>
	             <div class="page">
					<div class="pageContent">  
					   <form method="post" id="di_fwhdfjxz" class="editForm" >
							<div align="left">
							<fieldset>
							<table class="editTable" id="fwhdfjxz">
							    <tr>
									<td><label>文件上传：</label></td>
									<td><input type="file" id="DI_WJSC" name="DI_WJSC" /></td>
								</tr>
							</table>
							</fieldset>
								<div class="formBar">
							     <ul>
							        <li><div class="button"><div class="buttonContent"><button type="button" id="addAtt" onclick="doAddAtt()">上&nbsp;&nbsp;传</button></div></div></li>
							     </ul>
							    </div>
							</div>
						</form>
						 <div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-spdfj')">&nbsp;已传附件列表&gt;&gt;</a></legend>
						    <div id="dia-spdfj">
							<table width="100%" id="sudfjlb" name="spdfjlb" style="display: none" >
								<thead>
									<tr>
										<th  name="XH" style="display:" >序号</th>
										<th>附件名称</th>
										<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td><a href="#" >1.jsp</a></td>
										<td ><a href="#" onclick="doDeleteAtt()" class="op">[删除]</a></td>
									</tr>
									<tr>
										<td>2</td>
										<td><a href="#" >2.jsp</a></td>
										<td ><a href="#" onclick="doDeleteAtt()" class="op">[删除]</a></td>
									</tr>
								</tbody>
							</table>
						</div>
						 </fieldset>
		             </div> 
					</div>
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
				</div>
				<div class="page">
					<div class="pageContent">  
						 <div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-shgj')">&nbsp;历史审核轨迹&gt;&gt;</a></legend>
						    <div id="dia-shgj">
							<table width="100%" id="sudshgj" name="sudshgj" style="display: none" >
								<thead>
									<tr>
										<th  name="XH" style="display:" >序号</th>
										<th>审核人</th>
										<th>审核时间</th>
										<th>审核类型</th>
										<th>审核结果</th>
										<th>审核意见</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>系统用户</td>
										<td>系统自动审核</td>
										<td>2014-05-28 9:12</td>
										<td>自动审核驳回</td>
										<td >配件超三包期</td>
									</tr>
									<tr>
										<td>2</td>
										<td>张三</td>
										<td>供应商审核</td>
										<td>2014-05-28 9:20</td>
										<td>审核通过</td>
										<td >照片不太清晰</td>
									</tr>
									<tr>
										<td>3</td>
										<td>李四</td>
										<td>人工审核</td>
										<td>2014-05-28 9:30</td>
										<td>审核驳回</td>
										<td >供应商填写不正确</td>
									</tr>
								</tbody>
							</table>
						</div>
						 </fieldset>
		             </div> 
					</div>
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
					</div>
				</div>
            </div>	
	</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
</div>
</body>
</html>