<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>预授权审核</title>
<script type="text/javascript">
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
	$("#ysqfjlb").show();
	$("#ysqfjlb").jTable(); //附件
	if($("#dia-tab-cldlb").is(":hidden"))
		{
			$("#dia-tab-cldlb").show();
			$("#dia-tab-cldlb").jTable();
		}
	$("#sudshgj").show();
	$("#sudshgj").jTable(); //审核轨迹
	 addXm();
	
});
function afterDicItemClick(id, $row, selIndex) 
{
	return true;
}

function doSave(value){
	if(value==1)
	{
		alertMsg.info("审核通过");
	}else
	{
		alertMsg.info("审核驳回");
	}
}
function addXm()
{
	var $tab = $("#dia-tab-cldlb_content");
	$tab.createRow();
}
</script>
</head>
<body>
<div id="dia-layout">
	<div class="tabs" eventType="click" id="dia-tabs" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="javascript:void(0)"><span>基本信息</span></a></li>
						<li><a href="javascript:void(0)"><span>项目信息</span></a></li>
						<li><a href="javascript:void(0)"><span>附件信息</span></a></li>
						<li><a href="javascript:void(0)"><span>审核信息</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent">
				<div class="page" id="dia-div-wxxxbj" style="overflow:auto;">
					<div class="pageContent">
						<form method="post" id="dia-fm-wxdawh" class="editForm" style="width: 99%;">
							<div align="left">
								<fieldset>
									<legend align="right">
										<a onclick="onTitleClick('dia-tab-wxxx')">&nbsp;基本信息编辑&gt;&gt;</a>
									</legend>
									<table class="editTable" id="dia-tab-wxxx">
									   <tr>
									 		<td><label>预授权单号：</label></td>
											<td><div id="dia-spdh">预授权单号1</div></td>
											<td><label>预授权类型：</label></td>
											<td>照顾性保修</td>
										</tr>
										<tr>
											<td><label>VIN：</label></td>
											<td><input type="text" id="dia-in-vin" name="dia-in-vin" readonly="readonly" value="VIN1" /></td>
											<td><label>发动机号：</label></td>
											<td><input type="text" id="dia-in-engineno" name="dia-in-engineno" readonly="readonly" value="发动机号1"  /></td>
										</tr>
										<tr>
											<td><label>车辆型号：</label></td>
											<td><input type="text" id="dia-in-clxh" name="dia-in-clxh" value="车辆型号1" readonly="readonly" /></td>
											<td><label>合格证号：</label></td>
											<td><input type="text" id="dia-in-hgzh" name="dia-in-hgzh" value="合格证号1" readonly="readonly" /></td>
											<td><label>发动机型号：</label></td>
											<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="发动机型号1" readonly="readonly" /></td>
										</tr>
										<tr>
											<td><label>用户类型：</label></td>
											<td><input type="text" id="dia-in-yhlx" name="dia-in-yhlx" value="民车" readonly="readonly" /></td>
											<td><label>车辆用途：</label></td>
											<td><input type="text" id="dia-in-clyt" name="dia-in-clyt" value="非公路用车" readonly="readonly" /></td>
											<td><label>驱动形式：</label></td>
											<td><input type="text" id="dia-in-qdxs" name="dia-in-qdxs" value="6*2" readonly="readonly" /></td>
										</tr>
										<tr >
											<td><label>购车日期：</label></td>
											<td><input type="text" id="dia-in-clxsrq" name="dia-in-clxsrq" value="2013-01-01" datatype="1,is_null,30" readonly="readonly" /></td>
											<td><label>行驶里程：</label></td>
											<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="3100" datatype="1,is_number,30" readonly="readonly" /></td>
											<td><label>保修卡号：</label></td>
											<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="保修卡号1" datatype="1,is_number,30" readonly="readonly" /> </td>
										</tr>
										<tr >
											<td><label>出厂日期：</label></td>
											<td><input type="text" id="dia-in-ccrq" name="dia-in-ccrq" value="2013-01-01" datatype="1,is_null,30" readonly="readonly" /></td>
											<td><label>首保日期：</label></td>
											<td colspan="3"><input type="text" id="dia-in-sbrq" name="dia-in-xslc" value="2013-04-01" datatype="1,is_number,30" readonly="readonly" />
											</td>
										</tr>
										<tr>
											<td><label>车牌号码：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="车牌号01" datatype="1,is_number,30"  readonly="readonly"/></td>
											<td><label>用户名称：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="张三" datatype="1,is_number,30" readonly="readonly"/></td>
											<td><label>身份证号：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" datatype="1,is_number,30" readonly="readonly"/></td>

										</tr>
										<tr>
											<td><label>联系人：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="李四" datatype="1,is_number,30" readonly="readonly"/></td>
											<td><label>电话：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" datatype="1,is_number,30" readonly="readonly"/></td>
											<td><label>用户地址：</label></td>
											<td><textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" datatype="1,is_null,100" readonly="readonly">地址</textarea></td>
										</tr>
										<tr>
											<td><label>备注：</label></td>
											<td colspan="5">备注1</td>
										</tr>
										<tr>
											<td><label>审核意见：</label></td>
											<td colspan="5"><textarea id="dia-in-shbz" style="width: 450px; height: 50px;" name="dia-in-shbz" datatype="1,is_null,100"></textarea></td>
										</tr>
									</table>
								</fieldset>
							</div>
						</form>
						<div class="formBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doSave(1);">审核通过</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doSave(2);">审核驳回</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一步</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="page">
					<div class="pageContent">  
						 <div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-shgj')">&nbsp;项目信息&gt;&gt;</a></legend>
						 <div id="dia-shgj">
							<table style="display:none;width:100%;" id="dia-tab-cldlb" name="tablist" ref="dia-fkfs" edit="true" >
							<thead>
								<tr>
									<th fieldname="XM_TYPE" colwidth="60"  fvalue="项目类型1" freadonly="true" >项目类型</th>
									<th fieldname="XM_CODE" colwidth="60"  fvalue="项目代码1" freadonly="true" >项目代码</th>
									<th fieldname="XM_NAME" colwidth="60" fvalue="项目名称1" freadonly="true">项目名称</th>
									<th fieldname="REMARK" colwidth="60"  ftype="input"  freadonly="true">备注</th>
								</tr>
							</thead>
							<tbody>
						   </tbody>
					</table>
						</div>
						 </fieldset>
		             </div> 
					</div>
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一步</button></div></div></li>
						</ul>
					</div>
				</div>
				<div class="page">
					<div class="pageContent">  
						 <div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-spdfj')">&nbsp;已传附件列表&gt;&gt;</a></legend>
						    <div id="dia-spdfj">
							<table width="100%" id="ysqfjlb" name="ysqfjlb" style="display: none" >
								<thead>
									<tr>
										<th  name="XH" style="display:" >序号</th>
										<th>附件名称</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td><a href="#" >1.jsp</a></td>
									</tr>
									<tr>
										<td>2</td>
										<td><a href="#" >2.jsp</a></td>
									</tr>
								</tbody>
							</table>
						</div>
						 </fieldset>
		             </div> 
					</div>
					<div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一步</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一步</button></div></div></li>
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
										<th>审核结果</th>
										<th>审核意见</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>系统用户</td>
										<td>2014-05-28 9:12</td>
										<td>审核驳回</td>
										<td >配件超三包期</td>
									</tr>
									<tr>
										<td>2</td>
										<td>张三</td>
										<td>2014-05-28 9:20</td>
										<td>审核通过</td>
										<td ></td>
									</tr>
									<tr>
										<td>3</td>
										<td>李四</td>
										<td>2014-05-28 9:30</td>
										<td>审核驳回</td>
										<td >照片传的不清晰</td>
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