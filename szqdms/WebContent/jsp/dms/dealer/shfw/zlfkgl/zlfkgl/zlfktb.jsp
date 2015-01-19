<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>质量反馈提报</title>
<style type="text/css">  
table {  
    border: 1px solid #B1CDE3; 
    padding:0;   
    margin:0 auto;  
    border-collapse: collapse;  
}  
  
td {  
    border: 1px solid #B1CDE3; 
    background: #fff;  
    font-size:12px;  
    padding: 3px 0px 3px 8px;  
    color: #4f6b72;  
    white-space:nowrap;
}  
</style>
<script type="text/javascript">
$(function(){
	$("#gzjlb").show();
	$("#gzjlb").jTable();
	$("#layout").height(document.documentElement.clientHeight-30);
})
function addPart(){
	alertMsg.info("弹出选择配件页面!");
}
function doDelete(){
	alertMsg.info("删除成功！");
}
function doSave(){
	alertMsg.info("提报成功！");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;overflow:auto;" >
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageContent">
		<form method="post" id="fm-qtfyxz" class="editForm" >
			<div align="left">
			<fieldset>
			<table class="editTable" id="nr">
				<tr>
					<td rowspan="2" colspan="2">信息</td>
					<td><label>单位名称：</label></td>
					<td colspan="3"><input type="text"/></td>
					<td><label>填报时间：</label></td>
					<td><input type="text"/></td>
				</tr>
				<tr>
					<td><label>姓名：</label></td>
					<td><input type="text"/></td>
					<td><label>电话：</label></td>
					<td><input type="text"/></td>
					<td><label>传值：</label></td>
					<td><input type="text"/></td>
				</tr>
				<tr>
					<td rowspan="3"  colspan="2">车辆信息</td>
					<td><label>发动机号：</label></td>
					<td><input type="text"/></td>
					<td><label>VIN编码：</label></td>
					<td colspan="3"><input type="text" id="vin" style="width:315px"/>
						<div class="button"><div class="buttonContent"><button type="button" id="dia-checkvin"onclick="checkVin()">验&nbsp;&nbsp;证</button></div></div>
					</td>
				</tr>
				<tr>
					<td ><label>车型：</label></td>
					<td><input type="text"/></td>
					<td><label>发动机型号：</label></td>
					<td><input type="text"/></td>
					<td><label>发动机订货号：</label></td>
					<td ><input type="text"/></td>
				</tr>
				<tr>
					<td ><label>购车日期：</label></td>
					<td><input type="text"/></td>
					<td><label>故障日期：</label></td>
					<td><input type="text"/></td>
					<td><label>故障里程：</label></td>
					<td ><input type="text"/></td>
				</tr>
				<tr>
					<td rowspan="2"  colspan="2">客户信息</td>
					<td><label>公司名称：</label></td>
					<td><input type="text"/></td>
					<td><label>购车数量：</label></td>
					<td colspan="3">
						<input type="checkbox" name="gcsl"/>50辆以上
						<input type="checkbox" name="gcsl"/>10-50辆间
						<input type="checkbox" name="gcsl"/>10辆以下
						<input type="checkbox" name="gcsl"/>1辆
					</td>
				</tr>
				<tr>
					<td><label>联系人：</label></td>
					<td><input type="text"/></td>
					<td><label>电话：</label></td>
					<td ><input type="text"/></td>
					<td><label>传真：</label></td>
					<td ><input type="text"/></td>
				</tr>
				<tr>
					<td  colspan="2">驾驶员信息</td>
					<td><label>姓名：</label></td>
					<td><input type="text"/></td>
					<td><label>电话：</label></td>
					<td ><input type="text"/></td>
					<td><label>状态：</label></td>
					<td >
						<input type="checkbox" name="jsyzt"/>固定
						<input type="checkbox" name="jsyzt"/>不固定
					</td>
				</tr>
				<tr>
					<td  colspan="2">车辆用途</td>
					<td colspan="2">
						<input type="checkbox" name="yt1"/>运输
						<input type="checkbox" name="yt1"/>工程
						<input type="checkbox" name="yt1"/>专用 
						<input type="checkbox" name="yt1"/>其他
					</td>
					<td ><label>日作业时间：</label></td>
					<td colspan="3" style="width:300px">
						 <input type="checkbox" name="yt2"/>8小时以上
					 	 <input type="checkbox" name="yt2"/>4-8小时间
					 	 <input type="checkbox" name="yt2"/>4小时一下
					</td>
				</tr>
				<tr>
					<td  colspan="2">车辆故障地点</td>
					<td colspan="2">
						<input type="checkbox" name="gzdd"/>公路
						<input type="checkbox" name="gzdd"/>非公路
						<input type="checkbox" name="gzdd"/>矿
					</td>
					<td ><label>日常道路状况：</label></td>
					<td colspan="3" style="width:300px">
						 <input type="checkbox" name="rcdlzk"/>高速
					 	 <input type="checkbox" name="rcdlzk"/>普通
					 	 <input type="checkbox" name="rcdlzk"/>非硬化路面
					 	 <input type="checkbox" name="rcdlzk"/>坡道
					</td>
				</tr>
				<tr>
					<td  colspan="2">保养状况</td>
					<td colspan="2">
						<input type="checkbox" name="byzk"/>良好
						<input type="checkbox" name="byzk"/>一般
						<input type="checkbox" name="byzk"/>差
					</td>
					<td ><label>车辆情况：</label></td>
					<td colspan="3" style="width:300px">
						 <input type="checkbox" name="clqk"/>标载
					 	 <input type="checkbox" name="clqk"/>超载
					 	 <input type="checkbox" name="clqk"/>严重超载
					</td>
				</tr>
				<tr>
					<td  colspan="2">途径</td>
					<td><label>反馈途径：</label></td>
					<td ><input type="checkbox" name="byzk"/>已发邮箱
					</td>
					<td ><label>数量：</label></td>
					<td colspan="3" style="width:300px">
						 <font>共</font><input type="text" /><font>张</font>
					</td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
				    <td colspan="7"><textarea class="" rows="2" id="BZ" name="BZ" style="width:100%" datatype="1,is_null,500"></textarea></td>
				</tr>
			</table>
			<br/>
			<div id="gzj">
				<table width="100%" id="gzjlb" name="gzjlb" style="display: none" >
					<thead>
						<tr>
							<th  type="single" name="ra" style="display:" append="plus|addPart">序号</th>
							<th>图号</th>
							<th>名称</th>
							<th>供应商名称</th>
							<th>单价</th>
							<th>数量</th>
							<th colwidth="85" type="link" title="[删除]"  action="doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type ="radio" name="ra"/></td>
							<td>图号1</td>
							<td>名称1</td>
							<td>供应商代码1</td>
							<td>200</td>
							<td>1</td>
							<td ><a class="op" href="#" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td><input type ="radio" name="ra"/></td>
							<td>图号2</td>
							<td>名称2</td>
							<td>供应商代码2</td>
							<td>200</td>
							<td>1</td>
							<td><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td><input type ="radio" name="ra"/></td>
							<td>图号3</td>
							<td>名称3</td>
							<td>供应商代码3</td>
							<td>200</td>
							<td>1</td>
							<td><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
						<tr>
							<td><input type ="radio" name="ra"/></td>
							<td>图号4</td>
							<td>名称4</td>
							<td>供应商代码4</td>
							<td>200</td>
							<td>1</td>
							<td><a href="#" class="op" onclick="doDelete()">[删除]</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">提&nbsp;&nbsp;报</button></div></div></li>
			</ul>
		</div>
	</div>
	</div>
</div>
</body>
</html>