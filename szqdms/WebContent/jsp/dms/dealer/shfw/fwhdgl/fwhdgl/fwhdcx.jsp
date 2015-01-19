<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>服务活动查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#fwhdcxlb").is(":hidden"))
		{
			$("#fwhdcxlb").show();
			$("#fwhdcxlb").jTable();
		}
	});
});
function doDetail(){
	var options = {max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/dealer/shfw/fwhdgl/fwhdgl/fwhdxxmx.jsp", "fwhdxxmx", "服务活动明细", options,true);
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：服务活动管理&gt;服务活动管理&gt;服务活动查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="fwhdcxform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="fwhdcxTable">
						<tr>
							<td><label>活动代码：</label></td>
							<td><input type="text" id="HDDM" name="HDDM" datatype="1,is_null,100"  value="" /></td>
							<td><label>活动名称：</label></td>
							<td><input type="text" id="HDMC" name="HDMC" datatype="1,is_null,100" value="" /></td>
							<td><label>活动类别：</label></td>
							<td><select  type="text" id="HDLB" name="HDLB" kind="dic" class="combox" src="E#1=整改:2=促销"  datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label>处理方式：</label></td>
							<td><select type="text" id="CLFS" name="CLFS"  kind="dic" class="combox" src="E#1=维修:2=零件更换" datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
							<td><label>是否索赔：</label></td>
							<td><select  type="text" id="SFSP" name="SFSP" kind="dic" class="combox" src="SF"  datatype="1,is_null,100" value="" >
									<option value="-1" selected>--</option>
								</select>
							</td>
							<td><label>状态：</label></td>
							<td><select type="text" id="HDZT" name="HDZT"  kind="dic" class="combox" src="E#1=未发布:2=已发布:3=已取消:4=已关闭" datatype="1,is_null,100" value="" >
									<option value=-1 selected>--</option>
								</select>
							</td>
						</tr>
						<tr>
						    <td><label>活动日期：</label></td>
						    <td colspan="5">
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker({isShowWeek:true,doubleCalendar:false,dateFmt:'yyyy-MM-dd'})" />
					   		 </td>
					 	</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="fwhdcx">
				<table width="100%" id="fwhdcxlb" name="fwhdcxlb" style="display: none" >
					<thead>
						<tr>
							<th  name="XH" style="display:">序号</th>
							<th>活动代码</th>
							<th>活动名称</th>
							<th>活动类别</th>
							<th>处理方式</th>
							<th>是否索赔</th>
							<th>开始日期</th>
							<th>结束日期</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td><a href="#" onclick="doDetail()" class="op">代码1</a></td>
							<td>名称1</td>
							<td>整改</td>
							<td>维修</td>
							<td>是</td>
							<td>2013-03-05</td>
							<td>2013-09-25</td>
							<td>未发布</td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="#" onclick="doDetail()" class="op">代码2</a></td>
							<td>名称2</td>
							<td>促销</td>
							<td>零件更换</td>
							<td>否</td>
							<td>2012-06-01</td>
							<td>2012-07-31</td>
							<td>未发布</td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="#" onclick="doDetail()" class="op">代码3</a></td>
							<td>名称3</td>
							<td>促销</td>
							<td>零件更换</td>
							<td>否</td>
							<td>2014-03-01</td>
							<td>2014-06-31</td>
							<td>未发布</td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="#" onclick="doDetail()" class="op">代码4</a></td>
							<td>名称4</td>
							<td>整改</td>
							<td>维修</td>
							<td>否</td>
							<td>2014-03-01</td>
							<td>2014-06-31</td>
							<td>已关闭</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>