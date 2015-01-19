<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>车辆数据维护</title>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Spdgl/SpdglAction.do";
//初始化方法
$(function(){
	//查询方法
	$("#btn-cx").bind("click",function(event){
		if($("#tab-spdlb").is(":hidden"))
		{
			$("#tab-spdlb").show();
			$("#tab-spdlb").jTable();
		}
	});
});
//列表编辑链接(修改)
function doUpdate(row)
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/jcsjgl/jcxxgl/clsjxz.jsp?action=2", "clsjxz", "车辆数据维护", options);
}
//列表编辑链接(新增)
function doAdd()
{
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps + "/jsp/dms/oem/shfw/jcsjgl/jcxxgl/clsjxz.jsp?action=1", "clsjxz", "车辆数据维护", options);
}
function doDownLoad(filename)
{
	var url = encodeURI("download.jsp?filename="+filename);
	window.location.href= url;
}
function doPldr()
{
	alertMsg.info("弹出导入页面");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置：基础数据管理&gt;基础信息管理&gt;车辆数据维护</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-sbdcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-sbdcx">
					<!-- 定义查询条件 -->
					<tr>
					   <td><label>发动机号：</label></td>
					   <td><input type="text" id="in-fwzdm" name="in-fdjh" datatype="1,is_null,30" operation="like" /></td>
					   <td ><label>VIN：</label></td> 
					   <td > <textarea  cols="18" name="in-vin" id="in-vin" rows="3" ></textarea></td> 
					   <td><label>车辆状态：</label></td>
					   <td>
				         <select type="text" id="dia-in-wccs"  name="dia-in-wccs" kind="dic" src="E#1=有效:0=无效"  datatype="1,is_null,30" value="">
					    		<option value="-1" selected>---</option>
					    </select>
					   </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xz" onclick="doAdd();">新&nbsp;&nbsp;增</button></div></div></li>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-xzmb" onclick="doDownLoad('cldrmb.xls');" >下载模板</button></div></div></li>
					    <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-pldr" onclick="doPldr();">批量导入</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_spdlb" >
			<table style="display:none;width:100%;" id="tab-spdlb" name="tablist" ref="page_spdlb" refQuery="fm-sbdcx" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>
							<th fieldname="VIN" >VIN</th>
							<th fieldname="" >发动机号</th>
							<th fieldname="" >发动型号</th>
							<th fieldname="" >车辆型号</th>
							<th fieldname="" >驱动形式</th>
							<th fieldname="" >用户类型</th>
							<th fieldname="" >车辆用途</th>
							<th fieldname="" >合格证号</th>
							<th fieldname="" >合格发证日期</th>
							<th fieldname="" >车辆制造日期</th>
							<th fieldname="" >车辆出厂日期</th>
							<th fieldname="" >购车日期</th>
							<th fieldname="" >首保日期</th>
							<th fieldname="" >车辆销售状态</th>
							<th fieldname="" >保修卡号</th>
							<th fieldname="" >车牌号码</th>
							<th fieldname="" >用户姓名</th>
							<th fieldname="" >身份证号</th>
							<th fieldname="" >联系人</th>
							<th fieldname="" >联系电话</th>
							<th fieldname="" >地址</th>
							<th fieldname="" >车辆状态</th>
							<th colwidth="105" type="link" title="[修改]"  action="doUpdate" >操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="rownums"><div>1</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>VIN1</div></td>
							<td><div>发动机1</div></td>
							<td><div>发动型号1</div></td>
							<td><div>车辆型号1</div></td>
							<td><div>驱动形式</div></td>
							<td><div>民品</div></td>
							<td><div>非公路用车</div></td>
							<td><div>合格证号1</div></td>
							<td><div>2014-01-01</div></td>
							<td><div>2014-01-01</div></td>
							<td><div>2014-01-01</div></td>
							<td><div>2014-04-21</div></td>
							<td><div>2014-05-21</div></td>
							<td><div>已销售</div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div>有效</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
						</tr>
						<tr>
							<td class="rownums"><div>2</div></td>
							<td style="display:none;"><div><input type="radio" name="in-xh" /></div></td>
							<td><div>VIN2</div></td>
							<td><div>发动机2</div></td>
							<td><div>发动型号2</div></td>
							<td><div>车辆型号2</div></td>
							<td><div>驱动形式2</div></td>
							<td><div>民品</div></td>
							<td><div>非公路用车</div></td>
							<td><div>合格证号2</div></td>
							<td><div>2014-01-01</div></td>
							<td><div>2014-01-01</div></td>
							<td><div>2014-01-01</div></td>
							<td><div>2014-04-21</div></td>
							<td><div>2014-05-21</div></td>
							<td><div>未销售</div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div></div></td>
							<td><div>无效</div></td>
							<td><a href="#" onclick="doUpdate()" class="op">[修改]</a></td>
						</tr>
					</tbody>
			</table>
		</div>
	</div>
	</div>
</div>
</body>
</html>