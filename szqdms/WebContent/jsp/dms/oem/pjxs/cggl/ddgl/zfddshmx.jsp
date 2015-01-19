<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<div id="dia-layout" style="width:100%;">
	<div class="tabs" eventType="click" currentIndex="1" id="dia-tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:void(0)"><span>订单信息</span></a></li>
					<li><a href="javascript:void(0)"><span>配件清单</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
			<div class="page" id="ddjbxx" style="overflow:auto;" >
			<div class="" >
				<form method="post" id="dia-fm-pjdawh" class="editForm" style="width:99%;">
					<div align="left">
						<fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-tab-pjxx')">&nbsp;基本信息&gt;&gt;</a></legend>
						<table class="editTable" id="dia-tab-pjxx">
							<tr >
								<td><label>所属单位：</label></td>
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="1,is_null,30" value="服务商一" readOnly/></td>
							    <td><label>提报时间：</label></td>
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="1,is_null,30" value="2014-05-22 08:08:00" readOnly/></td>
							    <td><label>提报人：</label></td>
							    <td><input type="text" id="dia-in-lxfs"  name="dia-in-lxfs" datasource="LXFS" datatype="1,is_null,30" value="张三" readOnly/></td>
						    </tr>
							<tr>
							    <td><label>订单类型：</label></td>
							    <td>
							    	<input type="text" id="dia-in-ddlx"  name="dia-in-ddlx" kind="dic" src="E#ZF=直发订单" datasource="DDLX" datatype="1,is_null,30" readOnly value="直发订单"/>
							    </td>
							    <td><label>供货配件库：</label></td>
							    <td>
							    	<input type="text" id="dia-in-ghpjk"  name="dia-in-ghpjk" kind="dic" src="E#1=配送中心一:2=配送中心二:3=陕重汽配件公司" datasource="GHPJK" datatype="1,is_null,30" value="配送中心一" readOnly/>
							    </td>
							    <td><label>期望交货日期：</label></td>
							    <td>
							    	<input type="text" id="dia-in-qwjhrq" name="dia-in-qwjhrq" datasource="QWJHRQ" datatype="1,is_null,30" value="2014-05-31" readOnly />
							    </td>
							 </tr>
							 <tr>
							 	<td><label>运输方式：</label></td>
							    <td>
							    	<input type="text" id="dia-in-ysfs"  name="dia-in-ysfs" kind="dic" src="E#1=发运:2=自提" datasource="YSFS" datatype="1,is_null,30" value="发运" readOnly />
							    </td>
								<td><label>交货地点：</label></td>
							    <td colspan="3">
							    	<input type="text" style="width:89%;" id="dia-in-jhdd"  name="dia-in-jhdd" datasource="JHDD" dicWidth="500" kind="dic" src="E#1=吉林省长春市高新产业开发区飞跃东路200号:2=吉林省长春市高新产业开发区飞卫星路201号:99=其他"  datatype="1,is_null,30" value="吉林省长春市高新产业开发区飞卫星路201号" readOnly />
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
							    <td><input type="text" id="dia-in-lxr"  name="dia-in-lxr" datasource="LXR" datatype="1,is_null,30" value="张三" readOnly/></td>
							    <td><label>联系方式：</label></td>
							    <td><input type="text" id="dia-in-lxfs"  name="dia-in-lxfs" datasource="LXFS" datatype="1,is_null,30" value="11111" readOnly/></td>
								<td><label>邮政编码：</label></td>
							    <td><input type="text" id="dia-in-yzbm"  name="dia-in-yzbm" datasource="YZBM" datatype="1,is_digit,15" value="1111" readOnly/></td>
						    </tr>
						</table>
						</fieldset>
						<fieldset >
						<legend align="right"><a onclick="onTitleClick('shxx')">&nbsp;审核信息&gt;&gt;</a></legend>
						<div id="shxx">
						<div id="dia-shxx" style="overflow:hidden;width:95%;text-align:center;">
							<table style="display:;width:100%;" id="dia-tab-shxx" name="tablist" class="dlist" ref="dia-shxx" >
									<thead>
										<tr>
											<th fieldname="ZHLXDM" >审核时间</th>
											<th fieldname="ZHLXMC" >审核结果</th>
											<th fieldname="KYYE">审核部门</th>
											<th fieldname="BCSYJE" >审核人</th>
											<th fieldname="BCSYJE" >审核意见</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><div>2014-05-22 09:30:32</div></td>
											<td><div>审核驳回</div></td>
											<td><div>配件业务科</div></td>
											<td><div>审核员一</div></td>
											<td><div>订单填写不规范</div></td>
										</tr>
										<tr>
											<td><div>2014-05-22 11:30:20</div></td>
											<td><div>审核同意</div></td>
											<td><div>配件业务科</div></td>
											<td><div>审核员一</div></td>
											<td><div>同意</div></td>
										</tr>
									</tbody>
							</table>
						</div>
						</div>
						</fieldset>
					</div>
				</form>
			</div>
			</div>
			<div class="page">
			<div class="pageContent" >
				<div id="dia-pjmx" style="height:380px;overflow:auto;">
					<table style="display:none;width:100%;" id="dia-tab-pjlb" name="tablist"  ref="dia-pjmx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th fieldname="PJDM"  fdatatype="0,is_null,30" >配件图号</th>
							<th fieldname="PJMC" colwidth="150" freadonly="true">配件名称</th>
							<th fieldname="JLDWMC" colwidth="50">计量单位</th>
							<th fieldname="ZXBZ" colwidth="90" freadonly="true" >最小包装数/单位</th>
							<th fieldname="SFZDGYS" colwidth="50" >是否指定供应商</th>
							<th fieldname="GYS名称" colwidth="50">供应商</th>
							<th fieldname="SL" colwidth="60" fdatatype="0,is_digit,5">订购数量</th>
							<th fieldname="JXSJ" colwidth="60" ftype="input" fdatatype="0,is_money,30"  >采购单价</th>
							<th fieldname="JXSJ" colwidth="60" ftype="input" fdatatype="0,is_money,30"  >金额</th>
						</tr>
					</thead>
					<tbody>
						<tr style="">
							<td class="rownums"><div>1</div></td>
							<td><div>PJ002</div></td>
							<td><div>配件名称2</div></td>
							<td><div>件</div></td>
							<td><div>10/包</div></td>
							<td><div>是</div></td>
							<td><div>供应商一</div></td>
							<td><div>20</div></td>
							<td><div>800.00</div></td>
							<td><div align="right">16,000.00</div></td>
						</tr>
					</tbody>
			</table>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-approve1">审核通过</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" id="dia-approveback">审核驳回</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" id="dia-close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
				<div style="text-align:center;">
					<span>审核意见：</span><input type="text" id="checkInfo" name="checkInfo" style="height:18px;width:50%;" datatype="1,is_null,100"></input>
				</div>
			</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
//弹出窗体
$(function()
{
	//设置高度
	$("#ddjbxx").height(document.documentElement.clientHeight-50);
	$("#dia-tab-fklb").show();
	$("#dia-tab-shxx").show();
	$("#dia-tab-pjlb").show();
	$("#dia-tab-pjlb").jTable();
	$("#dia-close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#dia-approveback").bind("click",function(){
		if($("#checkInfo").val())
	    {
	    }else
	    {
	    	alertMsg.warn("请输入审核意见.");
	    	return false;
	    }
	});
});
</script>