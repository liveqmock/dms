<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>计划提报明细</title>
<script type="text/javascript">
//查询按钮响应方法
 $(function(){
	$("#jhmxlb").show();
	$("#jhmxlb").jTable();
	$("#search").click(function(){
		if($("#jhmxlb").is(":hidden")){
			$("#jhmxlb").show();
			$("#jhmxlb").jTable();
		}
	});
	var dialog = parent.$("body").data("jhmx");
	$("button.close").click(function(){
		parent.$.pdialog.close(dialog);
		return false;
	});
	
	$("#btnj1").click(function(){
		var val1=$("#val1").val()-0;
		var val=val1-1;
		$("#val1").val(val);
	});
	$("#btnj11").click(function(){
		var val1=$("#val1").val()-0;
		var val=val1+1;
		$("#val1").val(val);
	});
	
	$("#btnj2").click(function(){
		var val1=$("#val2").val()-0;
		var val=val1-1;
		$("#val2").val(val);
	});
	$("#btnj22").click(function(){
		var val1=$("#val2").val()-0;
		var val=val1+1;
		$("#val2").val(val);
	});
	$("#btnj3").click(function(){
		var val1=$("#val3").val()-0;
		var val=val1-1;
		$("#val3").val(val);
	});
	$("#btnj33").click(function(){
		var val1=$("#val3").val()-0;
		var val=val1+1;
		$("#val3").val(val);
	});
	$("#btnj4").click(function(){
		var val1=$("#val4").val()-0;
		var val=val1-1;
		$("#val4").val(val);
	});
	$("#btnj44").click(function(){
		var val1=$("#val4").val()-0;
		var val=val1+1;
		$("#val4").val(val);
	});
});
function doAdd(){
	var options = {max:false,width:850,height:300,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	$.pdialog.open(webApps+"/jsp/dms/oem/pjxs/jhgl/jhgl/jhmxxz.jsp", "jhmxxz", "计划明细新增", options);
}
function doExp(){
	alertMsg.info("下载计划明细！");
}
function doImp(){
	alertMsg.info("导入配件！");
}
function doSave(){
	alertMsg.info("保存成功!");
}
function doDelete(){
	alertMsg.info("删除成功!");
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<div class="page">
	<div class="pageHeader">
		<form id="jhmxform" method="post">
			<div class="searchBar" align="left">
				<table class="searchContent" id="jhmxTable">
					<tr>
						<td><label>计划代码：</label></td>
						<td><input type="text" id="FM_JHDM" name="FM_JHDM"  value="计划代码1" readonly="readonly" /></td>
						<td><label>计划名称：</label></td>
						<td><input type="text" id="FM_JHMC" name="FM_JHMC"  value="计划名称1" readonly="readonly" /></td>
						<td><label>计划日期：</label></td>
						<td><input type="text" id="FM_JHRQ" name="FM_JHRQ" value="2014-04-26至2014-05-25" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>配件仓库：</label></td>
						<td><select type="text" id="FM_PJCK" name="FM_PJCK" class="combox" kind="dic" src="E#1=中心配件仓库" >
								<option value=-1>--</option>
							</select>
						</td>
						<td><label>配件代码：</label></td>
						<td><input type="text" id="FM_PJDM" name="FM_PJDM"   /></td>
						<td><label>配件名称：</label></td>
						<td><input type="text" id="FM_PJMC" name="FM_PJMC"  /></td>
					</tr>
					<tr>
						<td><label>是否新增：</label></td>
						<td><select type="text" id="FM_SFXZ" name="FM_SFXZ"  class="combox" kind="dic" src="SF">
								<option value=-1>--</option>
							</select></td>
					    <td><label>推荐计划量：</label></td>
					    <td >
				    		<input type="text" id="in-jhl1" style="width:75px;" name="in-jhl1"  datatype="1,is_null,30" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-jhl2" style="width:75px;margin-left:-30px;" name="in-jhl2" datatype="1,is_null,30"  />
				   		 </td>
						<td><label>调节后数量：</label></td>
						<td >
				    		<input type="text" id="in-djhsl1" style="width:75px;" name="in-djhsl1"  datatype="1,is_null,30" />
				    		<span style="float:left;margin-left:-50px;margin-top:5px;">至</span>
							<input type="text" id="in-djhsl2" style="width:75px;margin-left:-30px;" name="in-djhsl2" datatype="1,is_null,30"  />
				   		</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="search">查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="add" onclick="doAdd()">新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exp" onclick="doExp()">下载全部</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="imp" onclick="doImp()">批量导入</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="jhmx">
			<table width="100%" id="jhmxlb" name="jhmxlb" style="display: none" >
				<thead>
					<tr>
						<th type="multi" name="XH" style="display:"></th>
						<th>配件仓库</th>
						<th>配件代码</th>
						<th>配件名称</th>
						<th>是否新增</th>
						<th>提报数量</th>
						<th>总出库量</th>
						<th colwidth="80">配件总装车量</th>
						<th colwidth="100">现有车型配件增量</th>
						<th>故障率</th>
						<th colwidth="80">是否保修故障率</th>
						<th>本区域出库量</th>
						<th>库存数量</th>
						<th>推荐计划</th>
						<th>调节数量</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>配件仓库1</td>
						<td>配件代码1</td>
						<td>配件名称1</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
						<td><input type="button" id="btnj1" style="width:20px" value="-"/><input type="text" id="val1" style="width:35px" value="83"/><input type="button"  id="btnj11" style="width:20px" value="+"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>配件仓库2</td>
						<td>配件代码2</td>
						<td>配件名称2</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
						<td><input type="button" id="btnj2" style="width:20px" value="-"/><input type="text" id="val2" style="width:35px" value="83"/><input type="button"  id="btnj22" style="width:20px" value="+"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>配件仓库3</td>
						<td>配件代码3</td>
						<td>配件名称3</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
						<td><input type="button" id="btnj3" style="width:20px" value="-"/><input type="text" id="val3" style="width:35px" value="83"/><input type="button"  id="btnj33" style="width:20px" value="+"/></td>
					</tr>
					<tr>
						<td><input type ="checkbox" /></td>
						<td>配件仓库4</td>
						<td>配件代码4</td>
						<td>配件名称4</td>
						<td>否</td>
						<td>50</td>
						<td>178</td>
						<td>300</td>
						<td>200</td>
						<td>0.078</td>
						<td>否</td>
						<td>140</td>
						<td>72</td>
						<td>83</td>
						<td><input type="button" id="btnj4" style="width:20px" value="-"/><input type="text" id="val4" style="width:35px" value="83"/><input type="button"  id="btnj44" style="width:20px" value="+"/></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="formBar" id="opBtn" >
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doDelete()" id="delete">删&nbsp;&nbsp;除</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>
	</div>
</div>
</body>
</html>