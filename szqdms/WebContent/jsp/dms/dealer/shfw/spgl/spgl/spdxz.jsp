<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "2";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>索赔单修改页</title>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	//设置高度
	$("#dia-layout").height(document.documentElement.clientHeight-30);
	if($("#dia-tab-cldlb").is(":hidden"))
	{
		$("#dia-tab-cldlb").show();
		$("#dia-tab-cldlb").jTable();
	}
	$("button.close").click(function(){
		parent.$.pdialog.closeCurrent();
		return false;
	});
});
function afterDicItemClick(id,$row,selIndex)
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
}
function selectOther()
{
	alertMsg.info("弹出选择其他项目费用窗口");
	return true;
}
function addCldfs()
{
	
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/spgl/spdwxxx.jsp?action=1", "spdwxxx", "处理单信息维护", options,true);

}
function addClCallBack()
{
	var newTr = $("<tr></tr>");
	newTr.append("<td style=''><div><input type='radio' name='in-xh' /></div></td>");
	newTr.append("<td><div>处理单号02</div></td>");
	newTr.append("<td><div>VIN2</div></td>");
	newTr.append("<td><div>发动机号2</div></td>");
	newTr.append("<td><div>售前维修</div></td>");
	newTr.append("<td><a href='#' onclick='doDiaCldSave()' class='op'>[编辑]</a><a href='#' class='op' onclick='doDiaCldDelete()'>[删除]</a></td>");
	$(newTr).appendTo($("#dia-tbody-cljg"));
	$("#dia-tab-cldlb").jTable();
}
function doDiaCldSave()
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/shfw/spgl/spgl/spdwxxx.jsp?action=2", "spdwxxx", "处理单信息维护", options,true);
}
function doDiaCldDelete()
{
	alertMsg.info("删除成功。");
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
</script>
</head>
<body>
<div id="dia-layout" style="overflow:auto;">
		<form method="post" id="dia-fm-wxdawh" class="editForm" style="width:99%;">
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
							    <td><input type="text" id="dia-spdzt" value="自动审核驳回" readonly="readonly"/></td>
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
									     <input type="text" id="dia-in-ccf"  name="dia-in-ccf"  datatype="1,is_null,30" value="人数*有效天数*（人/天单价）    只针对自备车 " readonly="readonly"/>
								    </td>
								</tr>
								<tr>
								    <td><label>差旅费：</label></td>
								    <td>
									     <input type="text" id="dia-in-clf"  name="dia-in-clf"  datatype="1,is_null,30" value="人数*有效天数*（人/天单价）    只针对自备车" readonly="readonly"/>
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
		  <div align="left">
		           <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-fkfs')">&nbsp;车辆信息列表&gt;&gt;</a></legend>
		               <div id="dia-fkfs" style="overflow:hidden;width:100%;text-align:center;">
					<table style="display:none;width:100%;" id="dia-tab-cldlb" name="tablist" ref="dia-fkfs" edit="false" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:" append="plus|addCldfs"></th>
									<th fieldname="VIN" ftype="input" >VIN</th>
									<th fieldname="ENGINE_NO" freadonly="true">发动机号</th>
									<th fieldname="KYYE" colwidth="100" freadonly="true">保修类型</th>
									<th colwidth="105" type="link" title="[编辑]|[删除]"  action="doDiaCldSave|doDiaCldDelete">操作</th>
								</tr>
							</thead>
							<tbody id="dia-tbody-cljg">
							  <tr>
								<td style=""><div><input type="radio" name="in-xh" /></div></td>
								<td><div>VIN1</div></td>
								<td><div>发动机号1</div></td>
								<td><div>正常保修</div></td>
								<td><a href="#" onclick="doDiaCldSave()" class="op">[编辑]</a><a href="#" class="op" onclick="doDiaCldDelete()">[删除]</a></td>
							 </tr>	
							</tbody>
					</table>
				</div>
		  </fieldset>
		 </div> 	
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaSpdSave();">保&nbsp;&nbsp;存</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-report" onclick="doDiaSpdReport();">提&nbsp;&nbsp;报</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
			</ul>
		</div>
</div>
</body>
</html>
