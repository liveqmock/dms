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
						<legend align="right"><a onclick="onTitleClick('dia-shgj')">&nbsp;索赔单车辆信息&gt;&gt;</a></legend>
						    <div id="dia-shgj">
							<table width="100%" id="dia-spclxx" name="dia-spclxx" style="display: none" class="editTable" >
						       <tr>
									<td><label>VIN：</label></td>
									<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="vin1" readonly="readonly"  /></td>
									<td><label>发动机号：</label></td>
									<td colspan="3"><input type="text" id="dia-in-engineno" name="dia-in-engineno" value="发动机号" readonly="readonly" /></td>
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
									<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="民车" readonly="readonly"  /></td>
									<td><label>车辆用途：</label></td>
									<td><input type="text" id="dia-in-vin" name="dia-in-vin" value="非公路用车" readonly="readonly"  /></td>
									<td><label>驱动形式：</label></td>
									<td><input type="text" id="dia-in-fdjxh" name="dia-in-fdjxh" value="6*2" readonly="readonly" /></td>
								</tr>
								<tr id="gclibxk" >
									<td><label>购车日期：</label></td>
									<td><input type="text" id="dia-in-clxsrq" name="dia-in-clxsrq" value="2013-01-01" readonly="readonly" /></td>
									<td><label>行驶里程：</label></td>
									<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="3100"  readonly="readonly" /></td>
									<td><label>保修卡号：</label></td>
									<td><input type="text" id="dia-in-xslc" name="dia-in-xslc" value="保修卡号1" readonly="readonly"  /> </td>
								</tr>
								<tr >
									<td><label>出厂日期：</label></td>
									<td><input type="text" id="dia-in-ccrq" name="dia-in-ccrq" value="2013-01-01"  readonly="readonly" /></td>
									<td><label>首保日期：</label></td>
									<td colspan="3"><input type="text" id="dia-in-sbrq" name="dia-in-xslc" value="2013-04-01"  readonly="readonly"  />
									</td>
								</tr>
							</table>
						</div>
						 </fieldset>
		             </div> 
		             <form method="post" id="dia-fm-rywh" class="editForm" style="width:99%;">
			              <div align="left">
					           <fieldset>
								<table class="editTable" id="dia-tab-ryxx">
									<tr>
									    <td><label>正确里程：</label></td>
									    <td ><input type="text" id="dia-in-zqlc" name="dia-in-zqlc" value="5100"   datatype="1,is_number,10"/>
									    </td>
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
	$("#dia-spclxx").show();
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

