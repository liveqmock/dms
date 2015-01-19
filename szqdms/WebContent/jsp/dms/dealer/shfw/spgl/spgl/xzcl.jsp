<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div id="dia-layout">
  <div class="page" >
	<div class="pageHeader" >
		<form method="post" id="dia-fm-clcx">
			</form>
		</div>
	    <div class="pageContent">
	    <div id="dia_page_cllb" >
			<table style="display:none;width:100%;" id="dia-tab-cllb" class="editTable" >
			        <tr>
					    <td><label>建议提报类型：</label></td>
					    <td colspan="5"> 
					      <select type="text" id="dia-in-splx"  name="dia-in-splx" kind="dic" src="E#1=正常保修:2=首保:3=服务活动:4=售前维修:5=定保:6=售前培训检查:7=安全检查:8=三包急件索赔" datasource="CLAIM_TYPE" datatype="0,is_null,30" value="">
								   <option value="1" selected>售前维修</option>
						  </select>
						</td>
					</tr>
					<tr>
					    <td><label>VIN：</label></td>
					    <td> 
					       <input type="text" id="dia-show-vin" name="dia-show-vin" value="VIN1" readonly="readonly" />
						</td>
						<td><label>发动机编号：</label></td>
					    <td> 
					        <input type="text" id="dia-show-engineno" name="dia-show-engineno" value="发动机编号1" readonly="readonly" />
						</td>
					    <td><label>车辆型号：</label></td>
					    <td>
						    <input type="text" id="dia-in-clxh"  name="dia-in-clxh" value="车辆型号1" readonly="readonly"/>
					    </td>
					</tr>
					<tr>
					    <td><label>合格证号：</label></td>
					    <td>
						    <input type="text" id="dia-in-clhgzh"  name="dia-in-clhgzh" value="合格证号1" readonly="readonly"/>
					    </td>
						<td><label>发动机型号：</label></td>
					    <td> 
					        <input type="text" id="dia-in-ecyxts"  name="dia-in-ecyxts"   value="发动机型号1" readonly="readonly"/>
						</td>
					    <td><label>发证日期：</label></td>
					    <td>
						    
					    </td>
					</tr>
					<tr>
					    <td><label>车辆制造日期：</label></td>
					    <td>
						  
					    </td>
						<td><label>驱动形式：</label></td>
					    <td > 
					        <input type="text" id="dia-in-qdxs"  name="dia-in-qdxs"   value="驱动形式1" readonly="readonly"/>
						</td>
						<td><label>保修卡号：</label></td>
					    <td > 
					        
						</td>
					</tr>
					<tr>
					    <td><label>购车日期：</label></td>
					    <td>
						  
					    </td>
						<td><label>出厂日期：</label></td>
					    <td > 
					       
						</td>
						<td><label>首保日期：</label></td>
					    <td > 
					        
						</td>
					</tr>
					<tr>
					<td><label>车牌号码：</label></td>
					    <td > 
					        
						</td>
					    <td><label>用户名称：</label></td>
					    <td>
						  
					    </td>
						<td><label>身份证号：</label></td>
					    <td > 
					       
						</td>
						
					</tr>
					<tr>
					    <td><label>联系人：</label></td>
					    <td > 
					        
						</td>
					    <td><label>电话：</label></td>
					    <td colspan="3">
						  
					    </td>
						
					</tr>
					<tr>
					    <td><label>用户地址：</label></td>
					    <td colspan="5"> 
					        
						</td>	
					</tr>
			</table>
			<div class="formBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-clqdbtn" >确&nbsp;&nbsp;定</button></div></div></li>
							<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
						</ul>
			   </div>
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
var xzcl_dialog = $("body").data("xzcl");
$(function(){
	
		if($("#dia-tab-cllb").is(":hidden"))
		{
			$("#dia-tab-cllb").show();
		}
	$("#dia-clqdbtn").bind("click",function(event){
		 checkVinCallBack();
		$.pdialog.close(xzcl_dialog);
		
	});	
	$("button.close").click(function(){
		$.pdialog.close(xzcl_dialog);
		return false;
	});
});
</script>