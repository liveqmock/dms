<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
%>
<div id="dia-layout">    
<div id="ysqgzxx" style="width:100% ; heigth:100%" class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm-ysqgzxz" class="editForm" >
		<div align="left">
		<table class="editTable" id="ysqgzxz">
			<tr>
		    	<td rowspan="4"  align="right" >授权角色：</td>
		    	<td colspan="2"  align="left" ><B>
		        	<input type="checkbox" id="checkboxnone" name="checkboxnone" onClick="onSelectAll(this,1);" /> 自动拒绝</B>(符合本条规则的索赔申请将自动被系统拒绝)
		        </td>
		    </tr>
		    <tr>
		    	<td width="27%"  align="left" >
		        	<input type="checkbox" id="WCFWJL" name="AUTHROLECHK" />外出服务经理
		        </td>
		      	<td width="48%" >
		        	<input type="checkbox" id="FWZCKKY" name="AUTHROLECHK" />服务追偿科科员
		       	</td>
		    </tr>
		     <tr>
		    	<td width="27%"  align="left" >
		        	<input type="checkbox" id="FWZCKKZ" name="AUTHROLECHK" />服务追偿科科长
		        </td>
		      	<td width="48%" >
		        	<input type="checkbox" id="FWBJL" name="AUTHROLECHK" />服务部经理
		       	</td>
		    </tr>
		    <tr>
		    	<td  colspan="3" width="27%" align="center">
		        	<input type="checkbox" name="AUTHROLECHK" />销售公司经理
		      	</td>
		 	</tr>
		</table>
			<div id="di_ysqgz">
				 <table  style="width:100%,height:50%" id="di_ysqgzlb" name="di_ysqgzlb" style="display:" ref="di_ysqgz" edit="true" style="overflow:hidden;">
					<thead>
						<tr>
							<th fieldname="SQGX" colWidth="60" ftype="input" freadonly="false" fkind="dic" fvalue="AND" fsrc="E#1=AND:2=OR">授权关系</th>
							<th fieldname="SQX" id="fsqx" colWidth="60" freadonly="true">授权项</th>
							<th fieldname="BJF" id="fbjf" colWidth="60" ftype="input" freadonly="false" fkind="dic" fvalue="Begin" fsrc="E#1=Begin:2=Equal:3=notBegin:4=notEqual">比较符</th>
							<th fieldname="Z" colWidth="60" ftype="input" freadonly="false" >值</th>
							<th fieldname="JCSX" colWidth="60" ftype="input" freadonly="false">检查顺序</th>
							<th  colwidth="85" type="link" title="[移除]"  action="doDelete">操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table> 
				
				<br/>
				<table >
					<tr>
		    			<td align="left" >授权项：</td>
		      			<td>
		      			<select type="text" class="combox" id="DI_YSGXF" name="DI_YSGXF" kind="dic" src="E#1=维修操作代码:2=零件金额:3=维修总金额:4=其他项目费用:5=索赔类型:6=索赔申请次数:7=经销商代码:8=车型代码" >
     		 				<option value="1" selected>维修操作代码</option>
     					</select>
     					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="addPjmx()" id="add">新&nbsp;&nbsp;增</button></div></div>
     					</td>
     			
		    		</tr>
		    	</table>
		    	<br></br>
		    	<table>
				    <tr>
				    	<td  align="right" rowspan="3">注：</td>
				    </tr>
				    <tr>
				    	<td colspan="2" align="left" >
				        	<font >1.索赔类型:一般索赔、售前维修、特殊赔偿、服务活动、免费保养。<br /><br />
				        	  	  2.规则运算不区分OR和AND的优先级，会按照上面的排列顺序依次执行<br /><br />
				        	      3.金额、费用单位为元 <br /><br />
				            </font>
				        </td>
				    </tr>
		  	  </table>
		  </div>
		  </div>
	</form>
	</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="save">保&nbsp;&nbsp;存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>	
<script type="text/javascript">
var action =<%=action%>;
$(function(){
	//初始化页面
	$("#di_ysqgzlb").jTable();
	if(action != "1")
	{
		$("#FWBJL").attr("checked",true);
		$("#WCFWJL").attr("checked",true);
	}
});
function addPjmx()
{
	$("#fsqx").attr("fvalue",$("#DI_YSGXF option:selected").text());
	if($("#DI_YSGXF").val()==2){
		$("#fbjf").attr("fsrc","E#1=等于:2=小于:3=小于等于:4=大于:5=大于等于:6=不等于");
		$("#fbjf").attr("fvalue","等于");
	}
	var $tab = $("#di_ysqgzlb_content");
	$tab.createRow();
}
function onSelectAll(){
	if($("#checkboxnone").attr("checked")=='checked'){
		$("[name=AUTHROLECHK]").attr("checked",false);
		$("[name=AUTHROLECHK]").attr("disabled","disabled");
	}else{
		$("[name=AUTHROLECHK]").attr("disabled",false);
	}
}
function doSave(){
	var $f = $("#fm-ysqgzxz");
	if (submitForm($f) == false) return false;
	alertMsg.info("保存成功");
}
//弹出窗体
var dialog = $("body").data("ysqgzxx");
$(function()
{
	$("button.close").click(function(){
		$.pdialog.close(dialog);
		return false;
	});
});
</script>
</div>