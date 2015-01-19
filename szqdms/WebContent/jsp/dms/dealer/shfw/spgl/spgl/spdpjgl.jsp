<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout"  style="overflow:auto;">
					    <div id="dia-div-ljxx" >
						    <div class="tabs" currentIndex="0" eventType="click" id="example">
							  <div class="tabsHeader">
								<div class="tabsHeaderContent">
									<ul>
									<li id="dia-li-lj1"><a href="javascript:void(0)"><span>零件信息1-主损件</span></a></li>
						            <li id="dia-li-lj2"><a href="javascript:void(0)"><span>零件信息2-主损件</span></a></li>
						            <li id="dia-li-lj3"><a href="javascript:void(0)"><span>零件信息3-附损件</span></a></li>
									</ul>
								</div>
							  </div>
						      <div class="tabsContent" >
						      <div class="page">
								<table class="editTable" id="dia-tab-lj1xx">
									<tr><td><label>处理措施：</label></td>
									    <td>
									         <select type="text" id="dia-in-clcs0" name="dia-in-clcs0" kind="dic" src="E#1=更换:2=维修" datatype="0,is_null,30" value="">
														<option value="1" selected>更换</option>
											</select>
									   </td> 
									   <td><label>故障类别：</label></td>
									    <td>
									         <select type="text" id="dia-in-gzlb0" name="dia-in-gzlb0" kind="dic" src="E#1=主损:2=附损" datatype="0,is_null,30" value="">
														<option value="1" selected>主损</option>
											</select>
									   </td>
									    <td><label>祸首件：</label></td>
									    <td>
									         <input type="text" id="dia-in-hsj0" name="dia-in-hsj0" kind="dic" src="E#配件代码1=配件名称1:配件代码2=配件名称2" datatype="1,is_null,30" value="">
											</input>
									   </td>     
									</tr>
									<tr>
									   <td><label>旧件代码：</label></td>
									   <td ><input type="text" id="dia-in-jjdm0" value="旧件代码" readonly="readonly" hasBtn="true" callFunction="selectOldPart(0)"/></td>
									   <td><label>旧件名称：</label></td>
									   <td ><input type="text" id="dia-in-jjmc0" value="旧件名称" readonly="readonly" /></td>  
									    <td><label>配件类别(旧件)：</label></td>
									    <td> 
									       <input type="text" id="dia-in-jjlb0" value="配件类别1" readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td><label>旧件件数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-jjsl0" value="2" readonly="readonly" />
										</td>
										<td><label>旧件供应厂家：</label></td>
									    <td > 
									        <input type="text" id="dia-in-jjgyc0" value="旧件生产厂家1" readonly="readonly" />
										</td> 
									    <td><label>零件流水号：</label></td>
									    <td>
										    <input type="text" id="dia-in-jjlsh0" value="旧件生产厂家1"  />
									    </td>
									</tr>
									<tr id="dia-xjkz1">
									   <td><label>新件代码：</label></td>
									   <td ><input type="text" id="dia-in-xjdm0" value="新件代码" readonly="readonly" hasBtn="true" callFunction="selectNewPart(0)"/></td>
									   <td><label>新件名称：</label></td>
									   <td ><input type="text" id="dia-in-xjmc0" value="新件名称" readonly="readonly" /></td> 
									   <td><label>新件供应厂家：</label></td>
									    <td > 
									        <input type="text" id="dia-in-xjgyc0" value="新件生产厂家1" readonly="readonly" />
										</td>
									</tr>
									<tr id="dia-xjjgkz1">
									   <td><label>索赔单价：</label></td>
									   <td ><input type="text" id="dia-in-spdj0" value="索赔单价" readonly="readonly" /></td> 
									    <td><label>新件件数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-xjsl0" value="2" readonly="readonly" />
										</td>
										<td><label>新件来源：</label></td>
									    <td>
										   <select type="text" id="dia-in-xjly0" name="dia-in-xjly0" kind="dic" src="E#1=站内储备:2=紧急自购" datatype="0,is_null,30" value="">
												<option value="1" selected>站内储备</option>
										   </select>
									    </td>
									</tr>
									<tr>
									    <td><label>索赔材料费：</label></td>
									    <td> 
									        <input type="text" id="dia-in-spclf0" value="索赔单价*件数" readonly="readonly" />
										</td>
									    <td><label>桥类别：</label></td>
									    <td>
										   <select type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#1=1类:2=2类:3=3类:4=4类:5=5类:6=6类" datatype="0,is_null,30" value="">
														<option value="-1" selected>请选择</option>
											</select>
									    </td>
										<td><label>桥编码：</label></td>
									    <td> 
									         <input type="text" id="dia-in-qbm"  name="dia-in-qbm"  datatype="1,is_null,30" value="桥编码1" readonly="readonly" hasBtn="true" callFunction="selectQiao(0)"/>
										</td>
									</tr>
									<tr>
									    <td><label>故障原因：</label></td>
									    <td>
									    	<textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" datatype="0,is_null,100">故障原因</textarea>
									    </td>
									    <td><label>严重程度：</label></td>
									    <td>
										      <select type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#1=一般故障:2=严重故障:3=致命故障" datatype="0,is_null,30" value="">
														<option value="1" selected>一般故障</option>
											 </select>
									    </td>								     
									</tr>
								</table>
							</div>	
							  <div class="page">
							    <div class="pageContent" id="dia-pag-lj2xx"  >
								  <table class="editTable" id="dia-tab-lj2xx">
									<tr><td><label>处理措施：</label></td>
									    <td>
									         <select type="text" id="dia-in-clcs1" name="dia-in-clcs1" kind="dic" src="E#1=更换:2=维修" datatype="0,is_null,30" value="">
														<option value="1" selected>更换</option>
											</select>
									   </td> 
									   <td><label>故障类别：</label></td>
									    <td>
									         <select type="text" id="dia-in-gzlb0" name="dia-in-gzlb0" kind="dic" src="E#1=主损:2=附损" datatype="0,is_null,30" value="">
														<option value="1" selected>主损</option>
											</select>
									   </td>
									    <td><label>祸首件：</label></td>
									    <td>
									         <input type="text" id="dia-in-hsj0" name="dia-in-hsj0" kind="dic" src="E#配件代码1=配件名称1:配件代码2=配件名称2" datatype="1,is_null,30" value="">
											</input>
									   </td> 
									       
									</tr>
									<tr>
									   <td><label>旧件代码：</label></td>
									   <td ><input type="text" id="dia-in-jjdm0" value="旧件代码" readonly="readonly" hasBtn="true" callFunction="selectOldPart(0)"/></td>
									   <td><label>旧件名称：</label></td>
									   <td ><input type="text" id="dia-in-jjmc0" value="旧件名称" readonly="readonly" /></td> 
									    <td><label>配件类别(旧件)：</label></td>
									    <td> 
									       <input type="text" id="dia-in-jjlb0" value="配件类别1" readonly="readonly" />
										</td>
									</tr>
									<tr>
									   <td><label>旧件件数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-jjsl0" value="2" readonly="readonly" />
										</td>
									   <td><label>旧件供应厂家：</label></td>
									    <td > 
									        <input type="text" id="dia-in-jjgyc0" value="旧件生产厂家1" readonly="readonly" />
										</td>
									    <td><label>零件流水号：</label></td>
									    <td>
										    <input type="text" id="dia-in-jjlsh0" value="旧件生产厂家1"  />
									    </td>
									</tr>
									<tr id="dia-xjkz2">
									   <td><label>新件代码：</label></td>
									   <td ><input type="text" id="dia-in-xjdm0" value="新件代码" readonly="readonly" hasBtn="true" callFunction="selectNewPart(0)"/></td>
									   <td><label>新件名称：</label></td>
									   <td ><input type="text" id="dia-in-xjmc0" value="新件名称" readonly="readonly" /></td> 
									   <td><label>新件供应厂家：</label></td>
									    <td > 
									        <input type="text" id="dia-in-xjgyc0" value="新件生产厂家1" readonly="readonly" />
										</td>
									</tr>
									<tr id="dia-xjjgkz2">
									   <td><label>索赔单价：</label></td>
									   <td ><input type="text" id="dia-in-spdj0" value="索赔单价" readonly="readonly" /></td> 
									    <td><label>新件件数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-xjsl0" value="2" readonly="readonly" />
										</td>
										<td><label>新件来源：</label></td>
									    <td>
										   <select type="text" id="dia-in-xjly0" name="dia-in-xjly0" kind="dic" src="E#1=站内储备:2=紧急自购" datatype="0,is_null,30" value="">
												<option value="1" selected>站内储备</option>
										   </select>
									    </td>
									</tr>
									<tr>
									    <td><label>索赔材料费：</label></td>
									    <td> 
									        <input type="text" id="dia-in-spclf2" value="索赔单价*件数" readonly="readonly" />
										</td>
									    <td><label>桥类别：</label></td>
									    <td>
										   <select type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#1=1类:2=2类:3=3类:4=4类:5=5类:6=6类" datatype="0,is_null,30" value="">
														<option value="-1" selected>请选择</option>
											</select>
									    </td>
										<td><label>桥编码：</label></td>
									    <td> 
									         <input type="text" id="dia-in-qbm"  name="dia-in-qbm"  datatype="1,is_null,30" value="桥编码1" readonly="readonly" hasBtn="true" callFunction="selectQiao(0)"/>
										</td>
									</tr>
									<tr>
									    <td><label>故障原因：</label></td>
									    <td>
									    	<textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" datatype="0,is_null,100">故障原因</textarea>
									    </td>
									    <td><label>严重程度：</label></td>
									    <td>
										      <select type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#1=一般故障:2=严重故障:3=致命故障" datatype="0,is_null,30" value="">
														<option value="1" selected>一般故障</option>
											 </select>
									    </td>									     
									</tr>
								</table>
							   </div>	
							  </div>
							  <div class="page">
							     <div class="pageContent" id="dia-pag-lj3xx"  >
								   <table class="editTable" id="dia-tab-lj3xx">
									<tr><td><label>处理措施：</label></td>
									    <td>
									         <select type="text" id="dia-in-clcs3" name="dia-in-clcs3" kind="dic" src="E#1=更换:2=维修" datatype="0,is_null,30" value="">
														<option value="1" selected>更换</option>
											</select>
									   </td> 
									   <td><label>故障类别：</label></td>
									    <td>
									         <select type="text" id="dia-in-gzlb0" name="dia-in-gzlb0" kind="dic" src="E#1=主损:2=附损" datatype="0,is_null,30" value="">
														<option value="1" selected>主损</option>
											</select>
									   </td>
									    <td><label>祸首件：</label></td>
									    <td>
									         <input type="text" id="dia-in-hsj0" name="dia-in-hsj0" kind="dic" src="E#配件代码1=配件名称1:配件代码2=配件名称2" datatype="1,is_null,30" value="">
											</input>
									   </td>  
									</tr>
									<tr>
								 	   <td><label>旧件代码：</label></td>
									   <td ><input type="text" id="dia-in-jjdm0" value="旧件代码" readonly="readonly" hasBtn="true" callFunction="selectOldPart(0)"/></td>
									   <td><label>旧件名称：</label></td>
									   <td ><input type="text" id="dia-in-jjmc0" value="旧件名称" readonly="readonly" /></td>     
									    <td><label>配件类别(旧件)：</label></td>
									    <td> 
									       <input type="text" id="dia-in-jjlb0" value="配件类别1" readonly="readonly" />
										</td>
										
									</tr>
									<tr>
									    <td><label>旧件件数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-jjsl0" value="2" readonly="readonly" />
										</td>
										 <td><label>旧件供应厂家：</label></td>
									    <td > 
									        <input type="text" id="dia-in-jjgyc0" value="旧件生产厂家1" readonly="readonly" />
										</td>
									   <td><label>零件流水号：</label></td>
									    <td>
										    <input type="text" id="dia-in-jjlsh0" value="旧件生产厂家1"  />
									    </td>
									</tr>
									<tr id="dia-xjkz3">
									   <td><label>新件代码：</label></td>
									   <td ><input type="text" id="dia-in-xjdm0" value="新件代码" readonly="readonly" hasBtn="true" callFunction="selectNewPart(0)"/></td>
									   <td><label>新件名称：</label></td>
									   <td ><input type="text" id="dia-in-xjmc0" value="新件名称" readonly="readonly" /></td> 
									   <td><label>新件供应厂家：</label></td>
									    <td > 
									        <input type="text" id="dia-in-xjgyc0" value="新件生产厂家1" readonly="readonly" />
										</td>
									</tr>
									<tr id="dia-xjjgkz3">
									   <td><label>索赔单价：</label></td>
									   <td ><input type="text" id="dia-in-spdj" value="索赔单价" readonly="readonly" /></td> 
									    <td><label>新件件数：</label></td>
									    <td> 
									        <input type="text" id="dia-in-xjsl0" value="2" readonly="readonly" />
										</td>
										<td><label>新件来源：</label></td>
									    <td>
										   <select type="text" id="dia-in-xjly0" name="dia-in-xjly0" kind="dic" src="E#1=站内储备:2=紧急自购" datatype="0,is_null,30" value="">
												<option value="1" selected>站内储备</option>
										   </select>
									    </td>
									</tr>
									<tr>
									    <td><label>索赔材料费：</label></td>
									    <td> 
									        <input type="text" id="dia-in-spclf3" value="索赔单价*件数" readonly="readonly" />
										</td>
									    <td><label>桥类别：</label></td>
									    <td>
										   <select type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#1=1类:2=2类:3=3类:4=4类:5=5类:6=6类" datatype="0,is_null,30" value="">
														<option value="-1" selected>请选择</option>
											</select>
									    </td>
										<td><label>桥编码：</label></td>
									    <td> 
									         <input type="text" id="dia-in-qbm"  name="dia-in-qbm"  datatype="1,is_null,30" value="桥编码1" readonly="readonly" hasBtn="true" callFunction="selectQiao(0)"/>
										</td>
									</tr>
									<tr>
									    <td><label>故障原因：</label></td>
									    <td>
									    	<textarea id="dia-in-dz" style="width: 150px; height: 30px;" name="dia-in-dz" datatype="0,is_null,100">故障原因</textarea>
									    </td>
									    <td><label>严重程度：</label></td>
									    <td>
										      <select type="text" id="dia-in-qlb0" name="dia-in-qlb0" kind="dic" src="E#1=一般故障:2=严重故障:3=致命故障" datatype="0,is_null,30" value="">
														<option value="1" selected>一般故障</option>
											 </select>
									    </td>								     
									</tr>
								</table>
							     </div>	
							  </div>
						      </div>	
					      </div>	
			           </div>
		             <div class="formBar" >
		                 <ul>
	                            <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-tabs-add" name="dia-tabs-add" onclick="addTabs();" >添&nbsp;&nbsp;加</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-tabs-save" name="dia-tabs-save" onclick="saveTabs();">保&nbsp;&nbsp; 存</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-tabs-del" name="dia-tabs-del" onclick="delTabs();">删&nbsp;&nbsp; 除</button></div></div></li>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关&nbsp;&nbsp; 闭</button></div></div></li>
						</ul>		
					 </div>
	<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
	</div>
<script type="text/javascript">
$(function(){
	$("button.close").click(function(){
		$.pdialog.closeCurrent();
		return false;
	});
});
</script>
</div>

