<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>重新提报审核</title>
<script type="text/javascript">
$(function() 
{
	//设置高度
	$("#dia-div-wxxxbj").height(document.documentElement.clientHeight-30);
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
	$("#sudfjlb").jTable(); //附件
	$("#sudshgj").jTable(); //审核轨迹
});
function doCheck(msg)
{
	alertMsg.info(msg);
	parent.$.pdialog.closeCurrent();
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
						<li><a href="javascript:void(0)"><span>零件信息</span></a></li>
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
							<table class="editTable" id="dia-tab-fyxx">
							<tr><td><label>外出次数：</label></td>
								    <td>
								        <input type="text" id="dia-in-wccs"  name="dia-in-wccs"  value="2次" readonly="readonly"/>
								   </td> 
								   <td><label>外出总费用：</label></td>
								   <td ><input type="text" id="dia-in-wczfy" value="100" readonly="readonly"/></td>
								   <td><label>一次外出费用：</label></td>
								   <td ><input type="text" id="dia-in-wczfy" value="100" readonly="readonly"/></td>     
								</tr>
								<tr>
								    <td><label>外出方式：</label></td>
								    <td> 
								        <input type="text" id="dia-in-wccs"  name="dia-in-wccs"   value="自备车" readonly="readonly"/>
									</td>
								    <td><label>外出人数：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"   value="2" readonly="readonly"/>
								    </td>
								    <td><label>外出人员：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcry"  name="dia-in-wcry"  value="张三、李四" readonly="readonly"/>
								    </td>
								</tr>
								<tr>
								    <td><label>出发时间：</label></td>
								    <td> 
								        <input type="text" id="dia-in-cfsj"  name="dia-in-cfsj"  value="2014-05-01 8:00" readonly="readonly" />
									</td>
								    <td><label>有效里程：</label></td>
								    <td>
									    <input type="text" id="dia-in-wcrs"  name="dia-in-wcrs"   value="100" readonly="readonly"/>
								    </td>
								    <td><label>服务车牌号：</label></td>
								    <td>
									    <input type="text" id="dia-in-fwcph"  name="dia-in-fwcph"  value="车牌1" readonly="readonly"/>
								    </td>
								</tr>
								<tr>
								    
									<td><label>到达时间：</label></td>
								    <td > 
								        <input type="text" id="dia-in-ddsj"  name="dia-in-ddsj"  value="2014-05-01 10:00" readonly="readonly"/>
									</td>
									<td><label>离开时间：</label></td>
								    <td colspan="3"> 
								        <input type="text" id="dia-in-lksj"  name="dia-in-lksj"   value="2014-05-01 11:00" readonly="readonly" />
									</td>
									
								</tr>
								<tr>
									<td><label>有效天数：</label></td>
								    <td> 
								        <input type="text" id="dia-in-yxts"  name="dia-in-yxts" value="离开时间-出发时间计算"  readonly="readonly"/>
									</td>
									<td><label>外出时间：</label></td>
								    <td colspan="3"> <input type="text" id="dia-in-wcsj"  name="dia-in-wcsj" value="白天"  readonly="readonly"/></td>
								</tr>
								<tr>
								    <td><label>在途餐补：</label></td>
								    <td>
									    <input type="text" id="dia-in-ztcb"  name="dia-in-ztcb"  value="人数*有效天数*（人/天单价）    只针对自备车" readonly="readonly"/>
								    </td>
									<td><label>服务车费：</label></td>
								    <td> 
								         <input type="text" id="dia-in-fwcf"  name="dia-in-fwcf"  value="有效里程*系统设定的单价  只针对自备" readonly="readonly"/>
									</td>
								    <td><label>车船费：</label></td>
								    <td>
									     <input type="text" id="dia-in-ccf"  name="dia-in-ccf"  value="人数*有效天数*（人/天单价）    只针对自备车 " readonly="readonly"/>
								    </td>
								</tr>
								<tr>
								    <td><label>差旅费：</label></td>
								    <td>
									     <input type="text" id="dia-in-clf"  name="dia-in-clf"  value="人数*有效天数*（人/天单价）    只针对自备车" readonly="readonly"/>
								    </td>
								    <td ><label>其他费：</label></td>
								    <td colspan="3">
									     <input type="text" id="dia-in-qtf"  name="dia-in-qtf"   readonly="readonly" value="0"/>
								    </td>
								</tr>   							
							</table>
					        </div>
					        <div align="left">
			                  <fieldset>
								<table class="editTable" id="dia-tab-ryxx">
									<tr>
									    <td><label>审核意见：</label></td>
									    <td >
										  <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-shyj"  datatype="0,is_null,100"></textarea>
									    </td>
									</tr>
								</table>
			       			</fieldset>
							</div>
						</form>
		             </div>
						<div class="formBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-zldshtg" onclick="doCheck('审核通过');">审核通过</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-zldshbh" onclick="doCheck('审核驳回');">审核驳回</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next0" name="btn-next">下一页</button></div></div></li>
							</ul>
						</div>
					</div>
				<div class="page" id="dia-div-wxxxbj" style="overflow:auto;">
					<div class="pageContent">
						<form method="post" id="dia-fm-wxdawh" class="editForm" style="width: 99%;">
							<div align="left">
								<fieldset>
									<legend align="right">
										<a onclick="onTitleClick('dia-tab-wxxx')">&nbsp;维修信息&gt;&gt;</a>
									</legend>
									<table class="editTable" id="dia-tab-wxxx">
										<tr>
											<td><label>VIN：</label></td>
											<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="vin1" readonly="readonly"/></td>
											<td><label>发动机号：</label></td>
											<td><input type="text" id="dia-in-engineno" name="dia-in-engineno" value="发动机号1" readonly="readonly"/></td>
											<td colspan="2"></td>
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
											<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="6*2" readonly="readonly" /></td>
										</tr>
										<tr >
											<td><label>购车日期：</label></td>
											<td><input type="text" id="dia-in-clxsrq" name="dia-in-clxsrq" value="2013-01-01" readonly="readonly"/></td>
											<td><label>行驶里程：</label></td>
											<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="3100" readonly="readonly"/></td>
											<td><label>保修卡号：</label></td>
											<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="保修卡号1" readonly="readonly"/> </td>
										</tr>
										<tr >
											<td><label>出厂日期：</label></td>
											<td><input type="text" id="dia-in-ccrq" name="dia-in-ccrq" value="2013-01-01" readonly="readonly" /></td>
											<td><label>首保日期：</label></td>
											<td colspan="3"><input type="text" id="dia-in-sbrq" name="dia-in-xslc" value="2013-04-01" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td><label>车牌号码：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="车牌号01" readonly="readonly"/></td>
											<td><label>用户名称：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="张三" readonly="readonly"/></td>
											<td><label>身份证号：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" readonly="readonly"/></td>

										</tr>
										<tr>
											<td><label>联系人：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="李四" readonly="readonly"/></td>
											<td><label>电话：</label></td>
											<td><input type="text" id="dia-in-cphm" name="dia-in-cphm" value="XXX" readonly="readonly"/></td>
											<td><label>用户地址：</label></td>
											<td><textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" readonly="readonly">地址</textarea></td>
										</tr>
										<tr>
											<td><label>处理单号：</label></td>
											<td><div id="dia-spdh">XXXX</div></td>
											<td><label>处理单类型：</label></td>
											<td><input type="text" id="dia-in-splx" name="dia-in-splx" value="正常保修" readonly="readonly"/></td>
											<td><label>是否预授权：</label></td>
											<td><input type="text" id="dia-in-sfysq" name="dia-in-sfysq" value="否" readonly="readonly"/></td>
										</tr>
										<tr>
											<td><label>检修人：</label></td>
											<td><input type="text" id="dia-in-jxr" name="dia-in-jxr" value="XXX" readonly="readonly"/></td>
											<td><label>检修时间：</label></td>
											<td><input type="text" id="dia-in-jxrq" name="dia-in-jxrq" value="2014-05-28 10:00" readonly="readonly"/></td>
											<td><label>检修地址：</label></td>
											<td><textarea id="dia-in-jxdz" style="width: 150px; height: 30px;" name="dia-in-jxdz" readonly="readonly">地址</textarea></td>
										</tr>
										<tr>
											<td><label>报修人：</label></td>
											<td><input type="text" id="dia-in-bxr" name="dia-in-bxr" value="王五" readonly="readonly"/></td>
											<td><label>报修时间：</label></td>
											<td><input type="text" id="dia-in-bxrq" name="dia-in-bxrq" value="2014-05-28 9:00" readonly="readonly"/></td>
											<td><label>报修人电话：</label></td>
											<td><input type="text" id="dia-in-bxrdh" name="dia-in-bxrdh" value="xxx" readonly="readonly"/></td>
										</tr>
										<tr>
											<td><label>报修人类型：</label></td>
											<td><input type="text" id="dia-in-bxrlx" name="dia-in-bxrlx" value="车主" readonly="readonly"/></td>
											<td><label>报修地址：</label></td>
											<td ><textarea id="dia-in-bxdz" style="width: 150px; height: 30px;" name="dia-in-bxdz" readonly="readonly">地址</textarea></td>
											<td><label>故障分析：</label></td>
											<td ><input type="text" id="dia-in-hsj0" name="dia-in-gzfx0" value="故障代码1" readonly="readonly"/></td>
										</tr>
										<tr>
											<td><label>故障信息来源：</label></td>
											<td><input type="text" id="dia-in-gzxxly" name="dia-in-gzxxly" readonly="readonly" value="电话" /></td>
											<td><label>故障地点：</label></td>
											<td ><input type="text" id="dia-in-gzdd" name="dia-in-gzdd" value="地点1" readonly="readonly"/></td>
											<td><label>故障时间：</label></td>
											<td><input type="text" id="dia-in-gzsj" name="dia-in-gzsj" value="2014-05-28 8:00" readonly="readonly"/></td>
										</tr>
										<tr>
											<td><label>备注：</label></td>
											<td colspan="5"><textarea id="dia-in-bxbz" style="width: 450px; height: 50px;" name="dia-in-bxbz" readonly="readonly"></textarea></td>
										</tr>
									</table>
								</fieldset>
							</div>
						</form>
						<div class="formBar">
							<ul>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre1" name="btn-pre">上一页</button></div></div></li>
								<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next1" name="btn-next">下一页</button></div></div></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="page">
				   <div class="pageContent">
					    <div id="dia-div-ljxx" >
						    <div class="tabs" currentIndex="0" eventType="click" id="example">
							  <div class="tabsHeader">
								<div class="tabsHeaderContent">
									<ul>
									<li id="dia-li-lj1"><a href="javascript:void(0)"><span>零件信息1</span></a></li>
						            <li id="dia-li-lj2"><a href="javascript:void(0)"><span>零件信息2</span></a></li>
									</ul>
								</div>
							  </div>
						      <div class="tabsContent" >
							  <div class="page">
							    <div class="pageContent" id="dia-pag-lj2xx"  >
								  <table class="editTable" id="dia-tab-lj2xx">
									<tr>
									   <td><label>处理措施：</label></td>
									   <td><input type="text" id="dia-in-clcs1" value="更换" readonly="readonly" /></td> 
									   <td><label>旧件代码：</label></td>
									   <td><input type="text" id="dia-in-jjdm0" value="旧件代码" readonly="readonly" /></td>
									   <td><label>旧件名称：</label></td>
									   <td><input type="text" id="dia-in-jjmc0" value="旧件名称" readonly="readonly" /></td>      
									</tr>
									<tr>
									    <td><label>配件类别(旧件)：</label></td>
									    <td><input type="text" id="dia-in-jjlb0" value="配件类别1" readonly="readonly" /></td>
										<td><label>旧件件数：</label></td>
									    <td><input type="text" id="dia-in-jjsl0" value="2" readonly="readonly" /></td>
									    <td><label>旧件供应厂家：</label></td>
									    <td><input type="text" id="dia-in-jjgyc0" value="旧件生产厂家1" readonly="readonly" /></td>
									</tr>
									<tr>
									    <td><label>零件流水号：</label></td>
									    <td><input type="text" id="dia-in-jjlsh0" value="" readonly="readonly"  /></td>
										<td><label>故障类别：</label></td>
									    <td><input type="text" id="dia-in-jjlsh0" value="主损" readonly="readonly"  /></td>
									    <td><label>祸首件：</label></td>
									    <td><input type="text" id="dia-in-hsj0" value="" readonly="readonly"  /></td> 
									</tr>
									<tr>
									   <td><label>新件代码：</label></td>
									   <td><input type="text" id="dia-in-xjdm0" value="新件代码" readonly="readonly" /></td>
									   <td><label>新件名称：</label></td>
									   <td><input type="text" id="dia-in-xjmc0" value="新件名称" readonly="readonly" /></td> 
									   <td><label>新件供应厂家：</label></td>
									   <td><input type="text" id="dia-in-xjgyc0" value="新件生产厂家1" readonly="readonly" /></td>
									</tr>
									<tr>
									   <td><label>索赔单价：</label></td>
									   <td><input type="text" id="dia-in-spdj0" value="索赔单价" readonly="readonly" /></td> 
									   <td><label>新件件数：</label></td>
									   <td><input type="text" id="dia-in-xjsl0" value="2" readonly="readonly" /></td>
									   <td><label>新件来源：</label></td>
									   <td><input type="text" id="dia-in-xjly0" value="站内储备" readonly="readonly" /></td>
									</tr>
									<tr>
									    <td><label>索赔材料费：</label></td>
									    <td><input type="text" id="dia-in-spclf2" value="索赔单价*件数" readonly="readonly" /></td>
									    <td><label>桥类别：</label></td>
									    <td><input type="text" id="dia-in-qlb0" value="1类" readonly="readonly" /></td>
										<td><label>桥编码：</label></td>
									    <td><input type="text" id="dia-in-qbm"  name="dia-in-qbm"  value="桥编码1" readonly="readonly"/></td>
									</tr>
									<tr>
									    <td><label>故障模式：</label></td>
									    <td><input type="text" id="dia-in-gzms"  name="dia-in-gzms"   value="故障模式1" readonly="readonly" /></td>
									    <td><label>严重程度：</label></td>
									    <td><input type="text" id="dia-in-qlb0"  name="dia-in-qlb0"   value="一般故障" readonly="readonly" /></td>
									    <td ><label>作业类别：</label></td>
									    <td><input type="text" id="dia-in-qlb0"  name="dia-in-qlb0"   value="作业类别1" readonly="readonly" /></td>									     
									</tr>
									<tr>
									    <td ><label>作业：</label></td>
									    <td><input type="text" id="dia-in-qlb0" name="dia-in-qlb0" value="作业1" readonly="readonly"/></td>
									    <td><label>维修工时：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="5" readonly="readonly" /></td>
										<td><label>工时单价：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="36" readonly="readonly" /></td>
									</tr>
									<tr>
									    <td><label>星级单价：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="4" readonly="readonly" /></td>
									    <td><label>激励单价：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="2" readonly="readonly" /></td>
										<td><label>工时费：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="5*(36+4+2)" readonly="readonly" /></td>
									</tr>
								</table>
							   </div>	
							  </div>
							  <div class="page">
							     <div class="pageContent" id="dia-pag-lj3xx"  >
								   <table class="editTable" id="dia-tab-lj3xx">
									<tr>
									   <td><label>处理措施：</label></td>
									   <td><input type="text" id="dia-in-clcs1" value="更换" readonly="readonly" /></td> 
									   <td><label>旧件代码：</label></td>
									   <td><input type="text" id="dia-in-jjdm0" value="旧件代码" readonly="readonly" /></td>
									   <td><label>旧件名称：</label></td>
									   <td><input type="text" id="dia-in-jjmc0" value="旧件名称" readonly="readonly" /></td>      
									</tr>
									<tr>
									    <td><label>配件类别(旧件)：</label></td>
									    <td><input type="text" id="dia-in-jjlb0" value="配件类别1" readonly="readonly" /></td>
										<td><label>旧件件数：</label></td>
									    <td><input type="text" id="dia-in-jjsl0" value="2" readonly="readonly" /></td>
									    <td><label>旧件供应厂家：</label></td>
									    <td><input type="text" id="dia-in-jjgyc0" value="旧件生产厂家1" readonly="readonly" /></td>
									</tr>
									<tr>
									    <td><label>零件流水号：</label></td>
									    <td><input type="text" id="dia-in-jjlsh0" value="" readonly="readonly"  /></td>
										<td><label>故障类别：</label></td>
									    <td><input type="text" id="dia-in-jjlsh0" value="附损" readonly="readonly"  /></td>
									    <td><label>祸首件：</label></td>
									    <td><input type="text" id="dia-in-hsj0" value="配件代码1" readonly="readonly"  /></td> 
									</tr>
									<tr>
									   <td><label>新件代码：</label></td>
									   <td><input type="text" id="dia-in-xjdm0" value="新件代码" readonly="readonly" /></td>
									   <td><label>新件名称：</label></td>
									   <td><input type="text" id="dia-in-xjmc0" value="新件名称" readonly="readonly" /></td> 
									   <td><label>新件供应厂家：</label></td>
									   <td><input type="text" id="dia-in-xjgyc0" value="新件生产厂家1" readonly="readonly" /></td>
									</tr>
									<tr>
									   <td><label>索赔单价：</label></td>
									   <td><input type="text" id="dia-in-spdj0" value="索赔单价" readonly="readonly" /></td> 
									   <td><label>新件件数：</label></td>
									   <td><input type="text" id="dia-in-xjsl0" value="2" readonly="readonly" /></td>
									   <td><label>新件来源：</label></td>
									   <td><input type="text" id="dia-in-xjly0" value="站内储备" readonly="readonly" /></td>
									</tr>
									<tr>
									    <td><label>索赔材料费：</label></td>
									    <td><input type="text" id="dia-in-spclf2" value="索赔单价*件数" readonly="readonly" /></td>
									    <td><label>桥类别：</label></td>
									    <td><input type="text" id="dia-in-qlb0" value="1类" readonly="readonly" /></td>
										<td><label>桥编码：</label></td>
									    <td><input type="text" id="dia-in-qbm"  name="dia-in-qbm"  value="桥编码1" readonly="readonly"/></td>
									</tr>
									<tr>
									    <td><label>故障模式：</label></td>
									    <td><input type="text" id="dia-in-gzms"  name="dia-in-gzms"   value="故障模式1" readonly="readonly" /></td>
									    <td><label>严重程度：</label></td>
									    <td><input type="text" id="dia-in-qlb0"  name="dia-in-qlb0"   value="一般故障" readonly="readonly" /></td>
									    <td ><label>作业类别：</label></td>
									    <td><input type="text" id="dia-in-qlb0"  name="dia-in-qlb0"   value="作业类别1" readonly="readonly" /></td>									     
									</tr>
									<tr>
									    <td ><label>作业：</label></td>
									    <td><input type="text" id="dia-in-qlb0" name="dia-in-qlb0" value="作业1" readonly="readonly"/></td>
									    <td><label>维修工时：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="5" readonly="readonly" /></td>
										<td><label>工时单价：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="36" readonly="readonly" /></td>
									</tr>
									<tr>
									    <td><label>星级单价：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="4" readonly="readonly" /></td>
									    <td><label>激励单价：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="2" readonly="readonly" /></td>
										<td><label>工时费：</label></td>
									    <td><input type="text" id="dia-in-xjgyc0" value="5*(36+4+2)" readonly="readonly" /></td>
									</tr>
								</table>
							     </div>	
							  </div>
						      </div>	
					      </div>	
			           </div>
					</div>
					  <div class="formBar">
						<ul>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre2" name="btn-pre">上一页</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next2" name="btn-next">下一页</button></div></div></li>
						</ul>
					</div>
	             </div>
	             <div class="page">
					<div class="pageContent">  
						 <div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-spdfj')">&nbsp;附件列表&gt;&gt;</a></legend>
						    <div id="dia-spdfj">
							<table width="100%" id="sudfjlb" name="spdfjlb"  >
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
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre3" name="btn-pre">上一页</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-next3" name="btn-next">下一页</button></div></div></li>
						</ul>
					</div>
				</div>
				<div class="page">
					<div class="pageContent">  
						 <div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-shgj')">&nbsp;历史审核轨迹&gt;&gt;</a></legend>
						    <div id="dia-shgj">
							<table width="100%" id="sudshgj" name="sudshgj"  >
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
							<li><div class="button"><div class="buttonContent"><button type="button" id="dia-pre4" name="btn-pre">上一页</button></div></div></li>
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