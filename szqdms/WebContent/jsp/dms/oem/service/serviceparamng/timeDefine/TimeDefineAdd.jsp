
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
<jsp:scriptlet>
	String action = request.getParameter("action");
	if(action == null)
		action = "1";
</jsp:scriptlet>
<div id="cdrqxx" style="width:100%;">
	<div class="page">
	<div class="pageContent" style="">
		<form method="post" id="dia-fm-option" class="editForm">
			 <input type="hidden" id="dia-ORG_ID" name="dia-ORG_ID" datasource="ORG_ID"/>	
			<div align="left">
			<fieldset>
			<table class="editTable" id="dia-tb-option">
				 <td><input type="hidden" id="dia-EXCEED_ID" name="dia-EXCEED_ID" datasource="EXCEED_ID"/></td>	
	
				<tr>
				    <td><label>时间：</label></td>
						<td><input type="text" id="dia-TimeDefine_NO" name="dia-TimeDefine_NO" datasource="TimeDefine_NO" datatype="1,is_null,100" value=""/></td>	
			   </tr>
			   <tr>		
					<td><label>开始时间：</label></td>
					<td><input type="text" id="dia-OVERDUE_DAYS" name="dia-OVERDUE_DAYS" datasource="OVERDUE_DAYS" datatype="1,is_null,100" value=""/></td>
			   		  </tr>
			   <tr>		
			   		<td><label>结束时间：</label></td>
					<td><input type="text" id="dia-OVERDUE_DAYS" name="dia-OVERDUE_DAYS" datasource="OVERDUE_DAYS" datatype="1,is_null,100" value=""/></td>
			   
			    </tr>
			</table>
			</fieldset>
			</div>
		</form>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="doSave()" id="btn-save">保  存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button class="close" type="button">关  闭</button></div></div></li>
			</ul>
		</div>
		
	</div>
	</div>	
</div>
<script type="text/javascript">
var diaSaveAction = "<%=request.getContextPath()%>/service/serviceparamng/TimeDefineMngAction";
var diaAction = "<%=action%>";
//初始化
$(function(){
 
   //绑定保存按钮
	$("#btn-save").bind("click", function(event){

			//获取需要提交的form对象
		var $f = $("#dia-fm-option");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#dia-fm-option").combined(1) || {};
		
		if(diaAction == 1)	//插入动作
		{
			var addUrl = diaSaveAction + "/insert.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			
			doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		
		}else	//更新动作
		{
			var updateUrl = diaSaveAction + "/update.ajax";
			doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
		}
	
	});
 
	//修改操作
	if(diaAction != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));
	
	 
	}else
	{
		
		setDiaDefaultValue();
	}
   }
);


function setDiaDefaultValue()
{
//新增无初始化
   
}


//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
	$("#tab-list").insertResult(res,0);	
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
//修改回调
function diaUpdateCallBack(res){
	try
	{		
		var selectedIndex = $("#tab-list").getSelectedIndex();
		$("#tab-list").updateResult(res,selectedIndex);
		$.pdialog.closeCurrent();
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}


</script>