<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>实销出库单信息</span></a></li>
					<li><a href="javascript:void(0)"><span>配件出库清单</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" >
				<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
				<div align="left">
					<fieldset>
					<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;实销出库单维护&gt;&gt;</a></legend>
					<table class="editTable" id="dia-tab-pjxx">
						<tr>
						    <td><label>实销单号：</label></td>
						    <td><div id="dia-sxdh">系统自动生成</div></td>
						    <td><label>实销类型：</label></td>
						    <td><div id="dia-rkbh">实销出库</div></td>
						</tr>
						<tr id="cgrk" >
							<td><label>客户名称：</label></td>
						    <td><input type="text" id="mx-khmc" name="mx-khmc" datasource="DDBH" datatype="0,is_null,30"  value=""/></td>
						    <td><label>联系电话：</label></td>
						    <td><input type="text" id="mx-lxdh"  name="mx-lxdh" datasource="DDLX" datatype="0,is_null,30"  value=""/></td>
						    <td><label>联系地址：</label></td>
						    <td><input type="text" id="mx-lxdz" name="mx-lxdz"  datasource="DDBH" datatype="0,is_null,30" value=""/></td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
							<td colspan="5">
								<textarea id="mx-bz" name="mx-bz" style="width:460px;" datasource="DDBH" datatype="1,is_null,30"></textarea>
							</td>
						</tr>
					</table>
					</fieldset>	
				</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save0">保&nbsp;&nbsp;存</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close0" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
			<div class="page">
				<div class="pageContent" >
					<div id="dia-pjmx" style="height:380px;overflow:hidden;">
						<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" >
								<thead>
									<tr>
										<th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
										<th fieldname="PJDM">配件图号</th>
										<th fieldname="PJMC">参图号</th>
										<th fieldname="JLDWMC">配件名称</th>
										<th fieldname="JLDWMC">单位</th>
										<th fieldname="ZXBZSL">最小包装</th>
										<th fieldname="SL" colwidth="80">出库数量</th>
										<th fieldname="">零售价</th>
										<th fieldname="ZJG">金额</th>
										<th type="link" title="[删除]" action="doDelete">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><div>1</div></td>
										<td><div>PJDM001</div></td>
										<td><div></div></td>
										<td><div>配件名称1</div></td>
										<td><div>件</div></td>
										<td><div>10件/包</div></td>
										<td><div>30</div></td>
										<td><div align="right">100.00</div></td>
										<td><div align="right">5,000.00</div></td>
										<td><div><a style="color:red;" onclick="doDelete();">[删除]</a></div></td>
									</tr>
									<tr>
										<td><div>2</div></td>
										<td><div>PJDM002</div></td>
										<td><div></div></td>
										<td><div>配件名称2</div></td>
										<td><div>件</div></td>
										<td><div>10件/包</div></td>
										<td><div>20</div></td>
										<td><div align="right">100.00</div></td>
										<td><div align="right">3,000.00</div></td>
										<td><div><a style="color:red;" onclick="doDelete();">[删除]</a></div></td>
									</tr>
								</tbody>
						</table>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-submit" name="btn-out">实销出库</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close1" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
var action = "<%=action%>";
$(function(){
	if(action==2){
		$("#dia-scdh").html("实销单号1");
		$("#mx-khmc").val("客户名称1");
		$("#mx-lxdh").val("13200110099");
		$("#mx-lxdz").val("测试地址1");
		$("#mx-bz").val("测试");
	}
	$("button[name='btn-pre']").bind("click",function(event){
		$("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
	});
	$("button[name='btn-next']").bind("click",function(event){
		var $tabs = $("#dia-tabs");
		switch(parseInt($tabs.attr("currentIndex")))
 	    {
 	   	   case 0:
 	   		   break;
 	   	   case 1:
 	   		   break;
 	    }
 	   $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
 	   //跳转后实现方法
 	   (function(ci){
 		   switch(parseInt(ci))
     	   {
     	   		case 1:
     	   			if($("#dia-tab-pjlb").is(":hidden"))
     	   			{
     	   				$("#dia-tab-pjlb").show();
     	   				$("#dia-tab-pjlb").jTable();
     	   			}
     	   			break;
     	   }
 	   })(parseInt($tabs.attr("currentIndex")));
	});
	$("#dia-close0").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-close1").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-save0").click(function(){
		alertMsg.info("操作成功!");
		return false;
	});
	$("#dia-submit").click(function(){
		alertMsg.confirm("确认实销出库?",{okCall:doApproveOk1,cancelCall:doApproveOk2});
	});
	
});
function doApproveOk1(){
	alertMsg.info("操作成功.");
}
function doApproveOk2(){
	return false;
}
//弹出配件选择列表，支持复选 
function addPjmx()
{
	var options = {max:false,width:900,height:450,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/pjcx.jsp?", "pjcx", "配件信息查询", options);
}
function selectOrder(){
	var options = {max:false,width:900,height:450,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/pjxs/ccgl/rkgl/rkcgdcx.jsp?", "ddcx", "采购订单查询", options);
}
//字典选择回调方法
function afterDicItemClick(id)
{
	var ret =  true;
	if(id == "mx-rklx")
	{
		switch($("#"+id).attr("code"))
		{
			case "CGRK":
				$("#cgrk").show();
				$("#ykrk").hide();
				$("#xstjrk").hide();
				break;
			case "YKRK":
				$("#cgrk").hide();
				$("#ykrk").show();
				$("#xstjrk").hide();
				break;
			case "XSTJRK":
				$("#cgrk").hide();
				$("#ykrk").hide();
				$("#xstjrk").show();
				break;
			case "QTRK":
				$("#cgrk").hide();
				$("#ykrk").hide();
				$("#xstjrk").hide();
				break;
		}
	}
	return ret;
}
function doDelete(){
	alertMsg.info("操作成功.");
}
function addPjmx(){
	alertMsg.info("弹出配件添加明细页，选择库存配件，输入数量进行添加.");
}
</script>
