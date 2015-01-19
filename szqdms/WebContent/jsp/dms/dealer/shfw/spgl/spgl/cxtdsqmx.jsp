<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page">
	<div class="pageContent" style="" >
		<div class="pageContent">  
				<div align="left">
		                 <fieldset>
						<legend align="right"><a onclick="onTitleClick('dia-shgj')">&nbsp;历史审核轨迹&gt;&gt;</a></legend>
						    <div id="dia-shgj">
							<table width="100%" id="sudshgj" name="sudshgj" style="display: none" >
								<thead>
									<tr>
										<th  name="XH" style="display:" >序号</th>
										<th>索赔单号</th>
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
										<td>索赔单号1</td>
										<td>系统用户</td>
										<td>系统自动审核</td>
										<td>2014-05-28 9:12</td>
										<td>自动审核驳回</td>
										<td >配件超三包期</td>
									</tr>
									<tr>
										<td>2</td>
										<td>索赔单号1</td>
										<td>张三</td>
										<td>供应商审核</td>
										<td>2014-05-28 9:20</td>
										<td>审核通过</td>
										<td >照片不太清晰</td>
									</tr>
									<tr>
										<td>3</td>
										<td>索赔单号1</td>
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
		             <form method="post" id="dia-fm-rywh" class="editForm" style="width:99%;">
		              <div align="left">
			          <fieldset>
						<table class="editTable" id="dia-tab-ryxx">
							<tr>
							    <td><label>申请原因：</label></td>
							    <td >
								  <textarea id="dia-in-bz" style="width:450px;height:40px;" name="dia-in-bz"  datatype="0,is_null,100"></textarea>
							    </td>
							</tr>
						</table>
			       </fieldset>
			</div>
			       <div align="left">
							<fieldset>
							<table class="editTable" id="fwhdfjxz">
							    <tr>
									<td><label>上传附件：</label></td>
									<td><input type="file" id="DI_WJSC" name="DI_WJSC" /></td>
								</tr>
							</table>
							</fieldset>
							</div>
			</form>		
					</div>
			    <div class="formBar">
				<ul>
				     <li><div class="button"><div class="buttonContent"><button type="button" id="addAtt" onclick="doAddAtt()">上传附件</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-save" onclick="doDiaRySave();">申&nbsp;&nbsp;请</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
	$("#sudshgj").show();
	$("#sudshgj").jTable(); //审核轨迹
});
function doDiaRySave()
{
	alertMsg.info("申请完成");
	$.pdialog.closeCurrent();
}
function doAddAtt()
{
	alertMsg.info("上传附件完成");
}
</script>
</div>

