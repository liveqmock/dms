<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
					<li><a href="javascript:void(0)"><span>配件清单</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page">
			<div class="pageContent" >
				<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
					<div align="left">
						<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;订单信息编辑&gt;&gt;</a></legend>
						<table class="editTable" id="dia-tab-pjxx">
							<tr>
							    <td><label>订单编号：</label></td>
							    <td><div id="dia-ddbh">系统自动生成</div></td>
							    <td><label>现金可用：</label></td>
							    <td><div id="dia-xjye">57,626.78 元</div></td>
							</tr>
							<tr>
							    <td><label>订单类型：</label></td>
							    <td><div>直营订单</div></td>
							    <td><label>供货配件库：</label></td>
							    <td><div>陕重汽配件销售公司</div></td>
							    <td><label>期望交货日期：</label></td>
							    <td>
							    	<input type="text" id="dia-in-qwjhrq" name="dia-in-qwjhrq" datasource="QWJHRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
							    </td>
							 </tr>
							 <tr>
							 	<td><label>是否网内：</label></td>
							    <td>
							    	<select type="text" id="dia-in-sfwn"  name="dia-in-sfwn" kind="dic" src="SF" datasource="SFWN" datatype="0,is_null,30" value="">
							    		<option value="0" selected>否</option>
							    	</select>
							    </td>
								<td><label>客户名称：</label></td>
							    <td colspan="3">
							    	<input type="text" id="dia-in-khmc" style="display:none;" name="dia-in-khmc" kind="dic" datasource="JHDD"  datatype="0,is_null,30" value="" />
							    	<input type="text" id="dia-in-khmc2"  name="dia-in-khmc2" datasource="KHMC"  datatype="1,is_null,30" value="" />
							    </td>	
							</tr>
							 <tr>
							 	<td><label>运输方式：</label></td>
							    <td>
							    	<select type="text" id="dia-in-ysfs"  name="dia-in-ysfs" kind="dic" src="E#1=发运:2=自提" datasource="YSFS" datatype="0,is_null,30" value="">
							    		<option value="1" selected>发运</option>
							    	</select>
							    </td>
								<td><label>交货地点：</label></td>
							    <td colspan="3">
							    	<input type="text" style="width:89%;" id="dia-in-jhdd"  name="dia-in-jhdd" datasource="JHDD"  datatype="0,is_null,30" value="" />
							    </td>	
							</tr>
							<tr id="qtfydz" style="display:none;">
								<td><label>省市区：</label></td>
							    <td><input type="text" id="dia-in-ssq"  name="dia-in-ssq" kind="dic" src="XZQH" datasource="SSQ" datatype="1,is_null,30" value=""/></td>
								<td><label>详细地址：</label></td>
							    <td colspan="3"><input type="text" style="width:89%;" id="dia-in-yzbm"  name="dia-in-yzbm" datasource="YZBM" datatype="1,is_null,100" value=""/></td>
							</tr>
							<tr>
							    <td><label>联系人：</label></td>
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="1,is_null,30" value=""/></td>
							    <td><label>联系方式：</label></td>
							    <td><input type="text" id="dia-in-lxfs"  name="dia-in-lxfs" datasource="LXFS" datatype="10,is_null,30" value=""/></td>
								<td><label>邮政编码：</label></td>
							    <td><input type="text" id="dia-in-yzbm"  name="dia-in-yzbm" datasource="YZBM" datatype="1,is_digit,15" value=""/></td>
						    </tr>
						    <tr>
								<td><label>备注：</label></td>
								<td colspan="3"><textarea id="dia-ta-bz" style="width:89%;height:40px;" name="dia-ta-bz" datasource="BZ"  datatype="1,is_null,100"></textarea></td>
							</tr>
						</table>
						</fieldset>	
					</div>
				</form>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save">保&nbsp;&nbsp;存</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			</div>
			<div class="page">
			<div class="pageContent" >
				<div id="dia-pjmx" style="height:380px;overflow:hidden;">
					<table style="display:none;width:100%;" id="dia-tab-pjlb" layoutH="350" name="tablist" ref="dia-pjmx" pageRows="15" edit="true" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:" append="plus|addPjmx"></th>
									<th fieldname="PJDM" ftype="input" fdatatype="0,is_null,30" freadonly="true">配件图号</th>
									<th fieldname="PJMC" colwidth="150" freadonly="true">配件名称</th>
									<th fieldname="PJID" style="display:none" freadonly="true">配件ID</th>
									<th fieldname="JLDWMC" freadonly="true" colwidth="50">计量单位</th>
									<th fieldname="ZXBZSL" freadonly="true" colwidth="70" >最小包装</th>
									<th fieldname="JXSJ" colwidth="60" ftype="input" fdatatype="0,is_money,30" freadonly="true" >经销商价</th>
									<th fieldname="SFZDGYS" freadonly="true" colwidth="80">是否指定供应商</th>
									<th fieldname="GYSMC" freadonly="true" >供应商名称</th>
									<th fieldname="SL" colwidth="60" fdatatype="0,is_digit,5">订购数量</th>
									<th fieldname="ZJG" colwidth="60" fdatatype="0,is_money,10" freadonly="true">小计</th>
									<th colwidth="85" type="link" title="[编辑]|[删除]"  action="doDiaListSave|doDiaListDelete">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
					</table>
				</div>
			</div>
			<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">配件模版导出</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">配件明细导入</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button"  id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
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
     	   		case 1://第2个tab页
	     	   		//校验是否保存订单信息，如没保存，则提示首先需保存订单信息
	     	   		//对于修改，需查询配件明细
	     	   		if(action == "1")
	     	   		{
	     	   			if($("#dia-tab-pjlb").is(":hidden"))
	     	   			{
	     	   				$("#dia-tab-pjlb").show();
	     	   				$("#dia-tab-pjlb").jTable();
	     	   			}
	     	   		}else
	     	   		{
	     	   		}
     	   			break;
     	   		case 2://第3个tab页
	     	   		if(action == "1")
	     	   		{
	     	   			if($("#dia-tab-fklb").is(":hidden"))
	     	   			{
	     	   				$("#dia-tab-fklb").show();
	     	   				$("#dia-tab-fklb").jTable();
	     	   			}
	     	   		}else
	     	   		{
	     	   		}
     	   			break;
     	   		default:
     	   			break;
     	   }
 	   })(parseInt($tabs.attr("currentIndex")));
	});
	//提报
	$("#dia-report").bind("click",function(){
		alertMsg.info("提报前需校验各账户本次使用金额合计是否等于订单总金额.");
	});
});
//弹出配件选择列表，支持复选 
function addPjmx()
{
	var options = {max:false,width:900,height:450,mask:true,mixable:true,minable:true,resizable:false,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/dealer/pjxs/xsgl/ddgl/pjcx.jsp?", "pjcx", "配件信息查询", options);
	//var $tab = $("#dia-tab-pjlb_content");
	//$tab.createRow();
}
//列表保存
function doDiaListSave(row)
{
	var ret = true;
	$("td input[type=radio]",$(row)).attr("checked",true);
	//提交保存方法
	
	//在提交保存的回调方法中取消edit状态
	diaListSaveCallBack();
	return ret;
}
function diaListSaveCallBack(res)
{
	var selectedIndex = $("#dia-tab-pjlb_content").getSelectedIndex();
	//$("#dia-tab-pjlb_content").updateResult(res,selectedIndex);
	$("#dia-tab-pjlb_content").clearRowEdit($("#dia-tab-pjlb_content").getSelectedRows()[0],selectedIndex);
	return true;
}
//列表删除
function doDiaListDelete()
{
}
//付款方式 
function addFkfs()
{
	var $tab = $("#dia-tab-fklb_content");
	$tab.createRow();
}
//列表保存
function doDiaFkSave(row)
{
	var ret = true;
	$("td input[type=radio]",$(row)).attr("checked",true);
	//提交保存方法
	
	//在提交保存的回调方法中取消edit状态
	diaFkSaveCallBack();
	return ret;
}
function diaFkSaveCallBack(res)
{
	var selectedIndex = $("#dia-tab-fklb_content").getSelectedIndex();
	//$("#dia-tab-pjlb_content").updateResult(res,selectedIndex);
	$("#dia-tab-fklb_content").clearRowEdit($("#dia-tab-fklb_content").getSelectedRows()[0],selectedIndex);
	return true;
}
//列表删除
function doFkDelete()
{
}
//字典选择回调方法
var mfyfcs = -1;
function afterDicItemClick(id,$row,selIndex)
{
	var ret =  true;
	//发运地点选择后，自动带出联系人、联系方式、邮政编码
	if(id == "dia-in-jhdd")
	{
		if($("#"+id).val() != "其他")
		{
			$("#qtfydz").hide();
			$("#dia-in-lxr").val("张三");
			$("#dia-in-lxfs").val("1111111111");
			$("#dia-in-yzbm").val("130000");
		}else
		{
			$("#qtfydz").show();
		}
		
	}
	//是否网内，当点击是的时候，客户名称为服务站列表表选模式
	if(id == "dia-in-sfwn")
	{
		if($("#"+id).attr("code") == "1")
		{
			$("#dia-in-khmc").show();
			$("#dia-in-khmc2").hide();
			$("#dia-in-khmc").attr("kind", "dic");
			$("#dia-in-khmc").attr("src", "E#1=经销商一:2=经销商2");
		}else
		{
			$("#dia-in-khmc").hide();
			$("#dia-in-khmc2").show();
		}
	}
	return ret;
}
</script>