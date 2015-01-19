<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>供应商追偿清单查询</title>
<script type="text/javascript">
//查询按钮响应方法
$(function(){
	$("#search").click(function(){
		if($("#zcqdlb").is(":hidden")){
			$("#zcqdlb").show();
			$("#zcqdlb").jTable();
		}
	});
});
function doExp(){
	alertMsg.info("导出追偿清单");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
		<div id='background1' class='background'></div>
		<div id='progressBar1' class='progressBar'>loading...</div>
		<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath()%>/images/default/nav.gif" />&nbsp;当前位置：旧件管理&gt;旧件管理&gt;供应商追偿清单查询</h4>
		<div class="page">
		<div class="pageHeader">
			<form id="zcqdform" method="post">
				<div class="searchBar" align="left">
					<table class="searchContent" id="zcqdTable">
						<tr>
						   	<td><label>服务商代码：</label></td>
							<td><input type="text" value=""/></td>
							<td><label>服务商名称：</label></td>
							<td><input type="text"  value="" /></td>
							<td><label>索赔单号：</label></td>
							<td><input type="text"  value="" /></td>
					 	</tr>
						<tr>
							<td><label>配件代码：</label></td>
							<td><input type="text" value="" /></td>
							<td><label>配件名称：</label></td>
							<td><input type="text" value="" /></td>
							<td><label>审核日期：</label></td>
						    <td >
					    		<input type="text" id="in-ckrq" style="width:75px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text" id="in-jsrq" style="width:75px;margin-left:-30px;" name="in-ckrq" datasource="CKRQ" datatype="1,is_null,30" onclick="WdatePicker()" />
					   		 </td>
						</tr>
						<tr>
							<td><label>费用类别：</label></td>
							<td><select type="text" class="combox" id="XZ_DMLB" name="XZ_DMLB" kind="dic" src="E#1=工时费:2=其他费用:3=材料费:4=首保费:5=外出费"  >
		        					<option value="-1" selected>--</option>
		        				</select>
		        			</td>
							<td><label>费用：</label></td>
						  	<td >
				    			<input type="text" style="width:75px;"  />
				    			<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
								<input type="text"  style="width:75px;margin-left:-30px;" />
				   		 	</td>
				   		 	<td><label>审核员：</label></td>
				   		 	<td><input type="text" /></td>
						</tr>
						<tr>
							<td><label>VIN：</label></td>
      						<td><textarea name="textarea" id="vin" name="vin" cols="18" rows="3" ></textarea></td>
							<td><label>维修类型：</label></td>
							<td><select type="text" class="combox" id="WXLX" kind="dic" src="E#1=救援:2=送修:3=跟踪" >
									<option value=-1>--</option>
								</select> 
							</td>
						</tr>
					</table>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">导&nbsp;&nbsp;出</button></div></div></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="pageContent">
			<div id="zcqd">
				<table width="100%" id="zcqdlb" name="zcqdlb" style="display: none" >
					<thead>
						<tr>
						<!--工时费:2=其他费用:3=材料费:4=强保费:5=外出费  -->
							<th  name="XH" style="display:">序号</th>
							<th>供应商代码</th>
							<th>供应商名称</th>
							<th>服务商代码</th>
							<th>服务商名称</th>
							<th>索赔单号</th>
							<th align="right">工时费(元)</th>
							<th align="right">其他费用(元)</th>
							<th align="right">材料用(元)</th>
							<th align="right">首保费(元)</th>
							<th align="right">外出费(元)</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>供应商代码1</td>
							<td>供应商名称1</td>
							<td>服务商代码1</td>
							<td>服务商名称1</td>
							<td>索赔单号1</td>
							<td>200</td>
							<td>100</td>
							<td>400</td>
							<td></td>
							<td>300</td>
						</tr>
						<tr>
							<td>2</td>
							<td>供应商代码2</td>
							<td>供应商名称2</td>
							<td>服务商代码2</td>
							<td>服务商名称2</td>
							<td>索赔单号2</td>
							<td>200</td>
							<td>100</td>
							<td>400</td>
							<td></td>
							<td>300</td>
						</tr>
						<tr>
							<td>3</td>
							<td>供应商代码3</td>
							<td>供应商名称3</td>
							<td>服务商代码3</td>
							<td>服务商名称3</td>
							<td>索赔单号3</td>
							<td>200</td>
							<td>100</td>
							<td>400</td>
							<td></td>
							<td>300</td>
						</tr>
						<tr>
							<td>4</td>
							<td>供应商代码4</td>
							<td>供应商名称4</td>
							<td>服务商代码4</td>
							<td>服务商名称4</td>
							<td>索赔单号4</td>
							<td>200</td>
							<td>100</td>
							<td>400</td>
							<td></td>
							<td>300</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>