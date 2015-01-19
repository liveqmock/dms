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
					<li><a href="javascript:void(0)"><span>付款订购</span></a></li>
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
							    <td><label>额度可用：</label></td>
							    <td><div id="dia-edye">50,000.00 元</div></td>
							</tr>
							<tr>
							    <td><label>订单类型：</label></td>
							    <td>
							    	<select type="text" id="dia-in-ddlx"  name="dia-in-ddlx" kind="dic" src="E#YD=月度订单:LS=临时订单:ZD=周度订单:ZC=总成订单:ZF=直发订单:CX=促销订单" datasource="DDLX" datatype="0,is_null,30" value="">
							    		<option value="YD" selected>月度订单</option>
							    	</select>
							    </td>
							    <td><label>供货配件库：</label></td>
							    <td>
							    	<select type="text" id="dia-in-ghpjk"  name="dia-in-ghpjk" kind="dic" src="E#1=配送中心一:2=配送中心二:3=陕重汽配件公司" datasource="GHPJK" datatype="0,is_null,30" value="">
							    		<option value="1" selected>配送中心一</option>
							    	</select>
							    </td>
							    <td><label>期望交货日期：</label></td>
							    <td>
							    	<input type="text" id="dia-in-qwjhrq" name="dia-in-qwjhrq" datasource="QWJHRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
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
							    	<input type="text" style="width:89%;" id="dia-in-jhdd"  name="dia-in-jhdd" datasource="JHDD" dicWidth="500" kind="dic" src="E#1=吉林省长春市高新产业开发区飞跃东路200号:2=吉林省长春市高新产业开发区飞卫星路201号:99=其他"  datatype="0,is_null,30" value="" />
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
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="0,is_null,30" value=""/></td>
							    <td><label>联系方式：</label></td>
							    <td><input type="text" id="dia-in-lxfs"  name="dia-in-lxfs" datasource="LXFS" datatype="0,is_null,30" value=""/></td>
								<td><label>邮政编码：</label></td>
							    <td><input type="text" id="dia-in-yzbm"  name="dia-in-yzbm" datasource="YZBM" datatype="0,is_digit,15" value=""/></td>
						    </tr>
						    <tr style="display:none;" id="tr-cxhd">
						    	<td><label>促销活动：</label></td>
							    <td colspan="3">
							    	<input type="text" style="width:89%;"  id="dia-in-cxhd"  name="dia-in-cxhd" kind="dic" src="E#1=促销活动一:2=促销活动2" datasource="CXHD" dicWidth="300" datatype="1,is_null,30" value="" />
							    </td>
							    <td></td>
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
				<div>
					<img src="ddkz.jpg" style="width:100%;height:200px;" />
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
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
			<div class="page">
			<div class="pageContent"  >
				<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
					<div align="left">
						<fieldset >
						<legend align="right"><a onclick="onTitleClick('dia-tab-fkxx')">&nbsp;付款信息编辑&gt;&gt;</a></legend>
						<table class="editTable" id="dia-tab-fkxx">
							<tr>
							    <td><label>订单总金额：</label></td>
							    <td><div id="dia-ddzje">80,000.00 元</div></td>
							</tr>
						    <tr>
							    <td><label>是否免运费：</label></td>
							    <td>
							    	<select type="text" id="dia-in-sfmyf"  name="dia-in-sfmyf" kind="dic" src="SF" datasource="SFMYF"  datatype="0,is_null,30" >
							    		<option value="1" selected>否</option>
							    	</select>
							    </td>
							    <td><label>是否使用额度：</label></td>
							    <td>
							    	<select type="text" id="dia-in-sfsyed"  name="dia-in-sfsyed" kind="dic" src="SF" datasource="SFSYED"  datatype="0,is_null,30" >
							    		<option value="1" selected>否</option>
							    	</select>
							    </td>
							</tr>
							<tr>
								<td><label>备注：</label></td>
								<td colspan="3"><textarea id="dia-ta-bz" style="width:450px;height:40px;" name="dia-ta-bz" datasource="BZ"  datatype="1,is_null,100"></textarea></td>
							</tr>
						</table>
						</fieldset>	
					</div>
					<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-fkSave">保存付款信息</button></div></div></li>
					</ul>
				</div>
				</form>
				<div id="dia-fkfs" style="overflow:hidden;width:80%;text-align:center;">
					<table style="display:none;width:100%;" id="dia-tab-fklb" name="tablist" ref="dia-fkfs" edit="true" >
							<thead>
								<tr>
									<th type="single" name="XH" style="display:" append="plus|addFkfs"></th>
									<th fieldname="ZHLXDM" ftype="input" fkind="dic" fsrc="E#XJ=现金:CD=承兑汇票:CLF=材料费" fdatatype="0,is_null,30" >账户类型代码</th>
									<th fieldname="ZHLXMC" freadonly="true">账户类型名称</th>
									<th fieldname="KYYE" colwidth="100" freadonly="true">可用余额(元)</th>
									<th fieldname="BCSYJE" colwidth="100" freadonly="false" fdatatype="0,is_money,30">本次使用金额(元)</th>
									<th colwidth="105" type="link" title="[编辑]|[删除]"  action="doDiaFkSave|doDiaFkDelete">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
					</table>
				</div>
				<div class="formBar">
					<ul>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-report">提&nbsp;&nbsp;报</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
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
	//订单类型
	if(id == "dia-in-ddlx")
	{
		switch($("#"+id).attr("code"))
		{
			case "CX":
					$("#tr-cxhd").show();
				break;
		}
	}
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
	//是否免运费，当点击是的时候，校验免费运费次数是否还有剩余
	if(id == "dia-in-sfmyf")
	{
		var v = $("#"+id).attr("code");
		if(v == "1")
		{
			//校验免费运费次数是否满足;
			if(mfyfcs == -1) //只查询一次即可
				mfyfcs = 4;
			if(mfyfcs == 4)
			{
				alertMsg.warn("本月免费运费次数已用完.");
				$("#"+id).attr("code","0");
				$("#"+id).val("否");
				$("a[name='dia-in-sfmyf']").val("0");
				$("a[name='dia-in-sfmyf']").html("否"); 
				return false;
			}
		}
	}
	//是否使用额度
	if(id == "dia-in-sfsyed")
	{
		var v = $("#"+id).attr("code");
		//使用额度
		if(v == "1")
		{
			var $tab = $("#dia-tab-fklb");
			var $tabC = $("#dia-tab-fklb_content");
			//现金和额度只能使用一种，不能同时使用，当选择额度时，需判断是否已保存现金类型
			var trs = $tabC.find("tbody tr");
			trs.each(function(){
				var $tr = $(this);
				var $td1 = $tr.find("td").eq(1);
				if($td1.text() != "XYED")
				{
					alertMsg.warn("账户类型存在非信用额度类型，请先删除已保存账户类型.");
					$("#"+id).attr("code","0");
					$("#"+id).val("否");
					$("a[name='"+id+"']").val("0");
					$("a[name='"+id+"']").html("否"); 
					return false;
				}
			});
			var $th1 = $tab.find("thead>tr th").eq(1);
			var $th2 = $tab.find("thead>tr th").eq(2);
			$th1.attr("fkind", "");
			$th1.attr("fsrc", "");
			$th1.attr("fvalues", "XYED");
			$th2.attr("fvalue", "信用额度");
		}else
		{
			var $tabC = $("#dia-tab-fklb_content");
			//现金和额度只能使用一种，不能同时使用，当选择额度时，需判断是否已保存现金类型
			var trs = $tabC.find("tbody tr");
			trs.each(function(){
				var $tr = $(this);
				var $td1 = $tr.find("td").eq(1);
				if($td1.text() == "XYED")
				{
					alertMsg.warn("账户类型存在使用额度类型，请先删除已保存账户类型.");
					$("#"+id).attr("code","1");
					$("#"+id).val("是");
					$("a[name='"+id+"']").val("1");
					$("a[name='"+id+"']").html("是"); 
					return false;
				}
			});
			var $tab = $("#dia-tab-fklb");
			var $th1 = $tab.find("thead>tr th").eq(1);
			var $th2 = $tab.find("thead>tr th").eq(2);
			$th1.attr("fvalue", "");
			$th2.attr("fvalue", "");
			$th1.attr("fkind", "dic");
			$th1.attr("fsrc", "E#XJ=现金:CD=承兑汇票:CLF=材料费");
		}
	}
	//账户类型
	if(id.indexOf("ZHLXDM") == 0)
	{
		var curRow = $("#"+id);
		while(curRow.isTag("tr") == false)
		{
			curRow = curRow.parent();
		}
		curRow.find("td").eq(2).text($("#"+id).val());
		$("#"+id).val($("#"+id).attr("code"));
		curRow.find("td").eq(3).text("5,332,23.00");
	}
	
	return ret;
}
</script>