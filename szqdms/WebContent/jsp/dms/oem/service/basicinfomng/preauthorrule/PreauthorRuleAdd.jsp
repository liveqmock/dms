<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%> 
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%
	String action = request.getParameter("action");
	String contextPath = request.getContextPath();
	String[] boolComp = { "AND", "OR" };//授权关系下拉框
	if(action == null)
		action = "1";	
%>
<div id="dia-layout">    
<div id="ysqgzxx" style="width:100% ; heigth:100%" class="page">
	<div class="pageContent" style="" >
	<form method="post" id="fm_item" style="display:">
	<input type="hidden" id="dia_RULE_ID" datasource="RULE_ID"/>
	<input type="hidden" id="dia_DTL_ID" datasource="DTL_ID"/>
	
	<input type="hidden" id="dia_PREAUTHOR_RELATION" datasource="PREAUTHOR_RELATION"/>
	<input type="hidden" id="dia_PREAUTHOR_OBJECT" datasource="PREAUTHOR_OBJECT"/>
	<input type="hidden" id="dia_COMPAR_CHARACTER" datasource="COMPAR_CHARACTER"/>
	<input type="hidden" id="dia_VALUE" datasource="VALUE"/>
	 <input type="hidden" id="dia_SEQUENCE" datasource="SEQUENCE"/> 
	  <input type="hidden" id="dia_PREAUTHO_NAME" datasource="PREAUTHO_NAME"/> 
	   <input type="hidden" id="dia_STATUS" datasource="STATUS"/> 
	 
	<input type="hidden"  id="AUTHROLE" name="AUTHROLE" datasource="LEVEL_CODE"/><!-- 授权角色隐藏域 -->
	</form>
	<form method="post" id="DIALI0_form" class="editForm" >	

		<div align="left">
				<input type="hidden"  id="PRIOR_LEVEL" name="PRIOR_LEVEL"  />
			<div id="dia-div-ljxx" >
			    <div class="tabs" id="example">
					  <div class="tabsHeader" id="dia-tabsHeader">
						<div class="tabsHeaderContent" id="dia-tabsHeaderContent" >
						<ul id="dia-partTabs" ></ul>
						</div>
					  </div>
				      <div class="tabsContent" id="dia-partContent" >
				      </div>	
		       </div>	
   			</div>
			<div id="di_ysqgz">
				 <table  style="width:100%,height:50%" id="di_ysqgzlb" name="di_ysqgzlb" ref="di_ysqgz" edit="true" style="overflow:hidden;">
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none"  ></th>
							<th fieldname="PREAUTHOR_RELATION" colWidth="60"  fdatatype="1,is_null,30" ftype="input" freadonly="false" fkind="dic"  fsrc="SQGX">授权关系</th>
							<th fieldname="PREAUTHOR_OBJECT" id="PREAUTHOR_OBJECT" name="PREAUTHOR_OBJECT" colWidth="60" ftype="input"  fdatatype="1,is_null,30" freadonly="true" >授权项</th> 
							<th fieldname="PREAUTHOR_OBJECT_CODE" refer="intFvalue"  id="PREAUTHOR_OBJECT_CODE" name="PREAUTHOR_OBJECT_CODE" colWidth="60"  style="display:none">授权项编码</th> 
							<th fieldname="COMPAR_CHARACTER"  id="COMPAR_CHARACTER" name="COMPAR_CHARACTER" fdatatype="1,is_null,30" colWidth="60" ftype="input" freadonly="false" fkind="dic" fsrc="GXFH">比较符</th> 
							<th fieldname="VALUE" id="fVALUE" name="fVALUE"  colWidth="60" ftype="input"  fdatatype="1,is_null,30" freadonly="false" >值</th>
							<th fieldname="SEQUENCE" id="SEQUENCE" name="SEQUENCE" colWidth="60" ftype="input" freadonly="false">检查顺序</th>
							<th colwidth="85" type="link" title="[编辑]|[移除]"  action="checkFormUpdate|delItem" >操作</th>
						</tr>
					</thead>
					<tbody id="editRule">
					</tbody>
				</table> 
			</div>			
				<br/>
				<div id="di_ysqyl">
					<table class="table_edit">
				<tbody id="reasonReview">
					<tr>
						<td align="center"></td>
						<td><input type="text" id="dia-tmpReason" name="dia-tmpReason"  datatype="0,is_digit,200"  /></td>
						<td align="center">
							<input class="normal_btn" type="button" name="view" value="预览"
								onclick="javascript:assembleItem();" />
						</td>
					</tr>
				</tbody>
			</table>
			</div>
				  	<div>
                  <table >
					<tr>
		    			<td align="left" >授权项：</td>
		      			<td>
		      			<select type="text" class="combox" id="DI_YSGXF" name="DI_YSGXF" kind="dic" src="SQXM" datatype="0,is_null,6" >
     		 				<option value="-1" selected>--</option>
     					</select>
     					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="addPjmx()" id="add">新&nbsp;&nbsp;增</button></div></div>
     					</td>
     			
		    		</tr>
		    	</table> 
                 </div>
		    	<div>
			    	<table>
					    <tr>
					    	<td  align="right" rowspan="3">注：</td>
					    </tr>
					    <tr>
					    	<td colspan="2" align="left" >
					        	<font >1.预授权类型:其它类型、照顾性保修、跨区服务<br /><br />
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
		<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保存授权角色</button></div></div></li>
		 	<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
		</ul>
	</div>	
<script type="text/javascript">
var action =<%=action%>;

var liLength=0; 
var diafaultPartAction ="<%=request.getContextPath()%>/service/basicinfomng/PreauthorLevelMngAction";
var diaSaveAction ="<%=request.getContextPath()%>/service/basicinfomng/PreauthorRuleMngAction";
var ruleDtlAction ="<%=request.getContextPath()%>/service/basicinfomng/PreauthorRuleDtlMngAction";
var diaHtmlId;//页面存储当前的动态操作ID
var maxPartCout=100;//最多添加配件数量
var dia_if_save=0;
$(function(){
$("#di_ysqgzlb").jTable();
/* 	$("#example").tabs({width: $("#example").parent().width(),height: "auto" }); 
	$("#dia-partTabs a").live("click", function() {
        var contentname = $(this).attr("id") + "_content";
        $("#dia-partContent div").hide();
        $("#dia-partTabs li").removeClass("selected");
        $("#" + contentname).show();
        $(this).parent().addClass("selected");
    });
	liLength=$("#dia-partTabs li").length;//当前添加配件的个数 */
	//先查询是否有已添加的零件信息
	searchPartTabsBack();
	$("#DI_YSGXF").val(306102);
	$("#DI_YSGXF").attr("code",306102);
	$("#DI_YSGXF").find("option").val(306102);
	$("#DI_YSGXF").find("option").text("类型");
	$("#DI_YSGXF").attr("readonly",true);
	//修改操作
	if(action != "1")
	{
		var selectedRows = $("#tab-list").getSelectedRows();
		
		//alert($(selectedRows[0]).attr("LEVEL_CODE"));
		var ruleId =$(selectedRows[0]).attr("RULE_ID");
/* 		setEditValue("dia-tb-option",selectedRows[0].attr("rowdata"));
		var rowdata=selectedRows[0].attr("rowdata");	

		var objXML;
		if (typeof(rowdata) == "object") objXML = rowdata;

		else objXML = $.parseXML(rowdata);

		var ruleId=getNodeValue(objXML, "RULE_ID", 0); */
		$("#dia_RULE_ID").val(ruleId);	
		if(levelRows && levelRows.length > 0)
		{
			for(var i=0;i<levelRows.length;i++)
			{  
				var objXML1;
				if (typeof(levelRows[i]) == "object") objXML1 = levelRows[i];
				else objXML1 = $.parseXML(levelRows[i]);
				var datelevelCode =getNodeValue(objXML1, "LEVEL_CODE", 0);//授权级别代码
			//	var tablevelCode=getNodeValue(objXML, "LEVELCODE"+datelevelCode, 0);
			     var tablevelCode=$(selectedRows[0]).attr("LEVELCODE"+datelevelCode);
				if(tablevelCode){
				   //	alert($("#LEVELCODE"+datelevelCode).val());
					$("#dia-LEVELCODE"+datelevelCode).attr("checked","checked");
				}
			}
			
		var $f = $("#fm_item");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"USER_TYPE":查询返回结果显示的table表格id
    	 */
    	var searchUrl= ruleDtlAction + "/search.ajax";
		doFormSubmit($f,searchUrl,"",1,sCondition,"di_ysqgzlb");	
		}
	}
 
	$("#btn-save").bind("click", function(event){
         diasaveRule();
	
	});
});


function diasaveRule(){

  		if(!submitForm('DIALI0_form')) {
			return false;
		}
		var roles = "";
		if(DIALI0_form.checkboxnone.checked){
			roles = DIALI0_form.checkboxnone.value;
		}else{
			var len = DIALI0_form.AUTHROLECHK.length;
			for (i=0; i<len; i++){
				if (DIALI0_form.AUTHROLECHK[i].checked){
					roles = ("" == roles)? DIALI0_form.AUTHROLECHK[i].value: (roles + "," + DIALI0_form.AUTHROLECHK[i].value);
				}
			}
		}
		if(roles.length<1){
		  	alertMsg.warn("您至少选择一种授权角色");
		  	return false;
		}
		//有效标示
		$("#dia-STATUS").val("<%=DicConstant.YXBS_01%>");
		$("#dia-STATUS").attr("code","<%=DicConstant.YXBS_01%>");

		//授权角色赋值：
		fm_item.AUTHROLE.value=roles;   
			//获取需要提交的form对象
		var $f = $("#fm_item");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm_item").combined(1) || {};
		
		 var ruleId= $("#dia_RULE_ID").val();	
		 
		if(ruleId){
		  var addUrl = diaSaveAction + "/update.ajax";
			/**
			 * doNormalSubmit:提交编辑域form表单方法
			 * @$f:提交form的jquery对象
			 * @addUrl：提交请求的url路径
			 * @"btn-save":点击按钮的id
			 * @sCondition:提交内容的json封装
			 * @diaInsertCallBack:请求后台执行完毕后，页面的回调方法
			 */
			
		doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
		} else{
		
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
		}
}

//新增回调函数
function diaInsertCallBack(res)
{
	try
	{
    var  Rows = res.getElementsByTagName("ROW");
	//var result = getNodeText(res.getElementsByTagName("RESULT").item(0));
	//if(result!='1'){
	if(Rows && Rows.length > 0)
		{
	   for(var i=0;i<Rows.length;i++)
			{  
	   if (typeof(Rows[i]) == "object") objXML = Rows[i];
				else objXML = $.parseXML(Rows[i]);
				 
		  var ruleId =getNodeValue(objXML, "RULE_ID", 0);//授权级别名称
		//  var dtlId =getNodeValue(objXML, "DTL_ID", 0);//授权级别名称
		
		/*  $("#dia_if_save").val("1");	 */
		dia_if_save=1;
	    $("#dia_RULE_ID").val(ruleId);	
	    // $("#DTL_ID").val(dtlId);	
	    }
	   }
		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
/* function searchPartTabs()
{
	var searchPartTabsUrl = diafaultPartAction + "/search.ajax?";
	sendPost(searchPartTabsUrl,"","",searchPartTabsBack,"false");
} */

   
//先查询已添加的零件信息回调方法
function searchPartTabsBack()
{
	try
	{
		var rows =levelRows;
		if(rows && rows.length > 0)
		{
			var id="DIALI"+liLength;
			var rowspan1 = parseInt(rows.length/2) + 2;
			
			//$("#dia-partTabs").append("<li class='selected' ><a href='javascript:void(0)' id='"+id+"' title='配件信息"+liLength+"'><span>配件信息"+liLength+"</span></a></li>");
			$("#dia-partContent div").hide();
			var newDiv=$("<div id='"+id+"_content'></div>");
			//var newForm=$("<form id='"+id+"_form' ></form>");
			var newTable=$("<table id='"+id+"_table' refer='true' class='editTable' ref='"+id+"_content' style='width:100%;'></table>");
			var newTd1=$("<td rowspan='"+ rowspan1 +"'  align='right' >授权角色：</td>");
			//var newTd2=$("<td colspan='2'  align='left' ><B>");
			var newTd3=$("<td colspan='2'  align='left' ><B><input type='checkbox' id='checkboxnone' name='checkboxnone' onClick='onSelectAll(this,1);' />取消勾选</B></td>");
 			var newTr2=$("<tr></tr>");	
 			newTr2.append(newTd1);
 			//newTr2.append(newTd2);
 			newTr2.append(newTd3);
 			newTable.append(newTr2);
			
			var newTr1=$("<tr></tr>");	
			for(var i=0;i<rows.length;i++)
			{  
				var objXML;
				if (typeof(rows[i]) == "object") objXML = rows[i];
				else objXML = $.parseXML(rows[i]);
			    var levelName =getNodeValue(objXML, "LEVEL_NAME", 0);//授权级别名称
				var levelCode =getNodeValue(objXML, "LEVEL_CODE", 0);//授权级别代码	    
			  /*   var newTd11=$("<td width='35%'  align='left' ><input type='checkbox' id='AUTHROLECHK' name='AUTHROLECHK' value="+levelCode+" />"+levelName+"</td>"); */
			 /*  var newTd11=$("<td width='35%'  align='left' ><input type='checkbox' id='LEVELCODE"+levelCode+"'  name='AUTHROLECHK' datasource='LEVELCODE"+levelCode+ "' value="+levelCode+" />"+levelName+"</td>"); */
			var newTd11=$("<td width='35%'  align='left' ><input type='checkbox' id='dia-LEVELCODE"+levelCode+"'  name='AUTHROLECHK' datasource='LEVELCODE"+levelCode+ "' value="+levelCode+"  />"+levelName+"</td>");
				
				newTr1.append(newTd11);
				
				if (i%2==1) {
				 	newTable.append(newTr1);
				 	var newTr1=$("<tr></tr>");	
				}
			    		   
				//if(faultTypeCode)$("#"+id+"_levelName").attr("code",faultTypeCode);	 
				//$("#"+id).html("<span>"+oldPartCode+"</span>");
				//$("#"+id).attr("title",oldPartName+"-"+faultTypeName); 
			}
			
			if (rows.length%2 == 1) {
				newTable.append(newTr1);
			} 
			newTable.addClass("editTable");
			//newForm.append(newTable);
			//newDiv.append(newForm);
			newDiv.append(newTable);
			newDiv.addClass("page");
			$("#dia-partContent").append(newDiv);
			setStyle(id+"_table",2);
			$("#"+id+"_content").show();
			liLength++;
		}
		
		//showTabsLength();//更改本页面的Title
	}catch(e)
	{
		alertMsg.error(e.description);
		return false;
	}
	return true;
}

//选择自动拒绝后，前6个级别变灰不能选择 
function onSelectAll(obj,num){
  
	if(num == 1){
		if(obj.checked == true){	
			for(j=0;j<DIALI0_form.AUTHROLECHK.length;j++){
				DIALI0_form.AUTHROLECHK[j].checked = false;
				DIALI0_form.AUTHROLECHK[j].disabled =true;
			}
		}
		else{
			for(i=0;i<DIALI0_form.AUTHROLECHK.length;i++){
				DIALI0_form.AUTHROLECHK[i].disabled =false;
			}	
		}
	}else{

	}
}

/**

<input type="text" id="dia_PREAUTHOR_RELATION" datasource="PREAUTHOR_RELATION"/>
	<input type="text" id="dia_COMPAR_CHARACTER" datasource="COMPAR_CHARACTER"/>
	<input type="text" id="dia_VALUE" datasource="VALUE"/>
	 <input type="text" id="dia_SEQUENCE" datasource="SEQUENCE"/> 
*/
	//表单提交前的验证：
function checkFormUpdate(row){
	try{
	    
			$("td input[type=radio]",$(row)).attr("checked",true);
			var dtlId = $(row).attr("DTL_ID");
			//var dia_RULE_ID = $("#dia_RULE_ID").val();
			 $("#dia_DTL_ID").val(dtlId);
			if(action != "1"){
				var preauthorRelation="";		
				 preauthorRelation =$(row).find("td").eq(2).find("input:first").val();	 
				
			if(preauthorRelation==''){
				alertMsg.warn("请选择授权关系,重新选择!");
				return false;
			}
			
			if($(row).find("td").eq(5).find("input:first").val()==''){
				alertMsg.warn("请选择比较符,重新选择!");
				return false;
			}
			if($(row).find("td").eq(6).find("input:first").val()==''){
				alertMsg.warn("请输入值!");
				return false;
			}
			if($(row).find("td").eq(7).find("input:first").val()==''){
				alertMsg.warn("请检查顺序!");
				return false;
			}
			$("#dia_PREAUTHOR_RELATION").val($(row).find("td").eq(2).find("input:first").attr("code"));		
			$("#dia_PREAUTHOR_OBJECT").val($(row).find("td").eq(4).find("input:first").val());
			$("#dia_COMPAR_CHARACTER").val($(row).find("td").eq(5).find("input:first").attr("code"));
			var diaValue="";
			if($("#dia_PREAUTHOR_OBJECT").val()==306102){
				diaValue=$(row).find("td").eq(6).find("input:first").attr("code");
			}else{
				diaValue=$(row).find("td").eq(6).find("input:first").val();
			}
			$("#dia_VALUE").val(diaValue);
			$("#dia_SEQUENCE").val($(row).find("td").eq(7).find("input:first").val());
			}else{//新曾
			
			  if(dia_if_save==0){
		  	alertMsg.warn("请先保存授权角色");
		  	return false;
		    }			
			  var preauthorRelation="";		
				 preauthorRelation =$(row).find("td").eq(1).find("input:first").val();	 
				
			if(preauthorRelation==''){
				alertMsg.warn("请选择授权关系,重新选择!");
				return false;
			}
			
			if($(row).find("td").eq(4).find("input:first").val()==''){
				alertMsg.warn("请选择比较符,重新选择!");
				return false;
			}
			if($(row).find("td").eq(5).find("input:first").val()==''){
				alertMsg.warn("请输入值!");
				return false;
			}
			if($(row).find("td").eq(6).find("input:first").val()==''){
				alertMsg.warn("请检查顺序!");
				return false;
			}
			$("#dia_PREAUTHOR_RELATION").val($(row).find("td").eq(1).find("input:first").attr("code"));	
			$("#dia_PREAUTHOR_OBJECT").val($(row).find("td").eq(3).find("input:first").val());
			$("#dia_COMPAR_CHARACTER").val($(row).find("td").eq(4).find("input:first").attr("code"));
			$("#dia_VALUE").val($(row).find("td").eq(5).find("input:first").val());
			$("#dia_SEQUENCE").val($(row).find("td").eq(6).find("input:first").val());
			} 		
			//有效标示
			$("#dia-STATUS").val("<%=DicConstant.YXBS_01%>");
			$("#dia-STATUS").attr("code","<%=DicConstant.YXBS_01%>");

				 var $f = $("#fm_item");
		//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
		if (submitForm($f) == false) return false;
		var sCondition = {};
		//将需要提交的内容拼接成json
		sCondition = $("#fm_item").combined(1) || {};
		//doNormalSubmit($f,diaSaveAction+"/insertRule.ajax","btn-save",sCondition,diaInsertCallBack);		
		
		//sendPost(url,"",sCondition,diaListSaveCallBack,"true");
			//需要将隐藏域或者列表只读域传给后台
		if(dtlId)
		{
			var url = ruleDtlAction + "/update.ajax";
			sendPost(url,"",sCondition,diaListSaveCallBack,"true");
		}	
		else{
			var url = ruleDtlAction + "/insert.ajax";
			sendPost(url,"",sCondition,diaListSaveCallBack,"true");
		}
	
	}catch(e){
	alertMsg.error(e);ret = false;}
		return ret; 
}
//预览方法：
function assembleItem(){
	var tmpReason = "";
	try{
		//判断授权项		
		$("#di_ysqgzlb_content tr").each(function(){
			
			if(action != "1"){
			
		    
				 var preauthorObject = $(this).children().eq(3).text();		
				 if(preauthorObject==""){
					 $(this).children().eq(3).val();	
				 }
				 var preauthorRelation = $(this).children().eq(2).text();
				 var comparCharacter = $(this).children().eq(5).text();
				 var value = $(this).children().eq(6).text();
				 if(preauthorObject!=""&&preauthorRelation!=""&&comparCharacter!=""&&value!=""){
				
					 tmpReason=tmpReason+preauthorRelation+"   "+preauthorObject+"  "+comparCharacter+"   "+value+"  "; 
					 
				
				 }
			
			}else{
                
				 var preauthorObject = $(this).children().eq(2).text();	
				 if(preauthorObject==""){
					 $(this).children().eq(2).val();	
				 }
				 var preauthorRelation = $(this).children().eq(1).text();
				 var comparCharacter = $(this).children().eq(4).text();
				 var value = $(this).children().eq(5).text();
				 if(preauthorObject!=""&&preauthorRelation!=""&&comparCharacter!=""&&value!=""){
				
					 tmpReason=tmpReason+preauthorRelation+"   "+preauthorObject+"  "+comparCharacter+"   "+value+"  "; 
					 
				
				 }
	         
			} 			
			
        }) 		 
	}catch(e){
			tmpReason = "无";
	}
	//规则明显存储赋值
	$("#dia_PREAUTHO_NAME").val(tmpReason);
	$("#dia-tmpReason").val(tmpReason);
	/* DIALI0_form.PRIOR_LEVEL.value = tmpReason;
	reasonReview.childNodes[0].childNodes[0].innerHTML = '<STRONG>规则预览：'+ tmpReason + '</STRONG>'; */
	return true;
}	
//行号计数：
var rowlen;	
function addPjmx()
{   
   //	$("#PREAUTHOR_OBJECT").attr("fvalue",$("#DI_YSGXF option:selected").text());
	//$("#PREAUTHOR_OBJECT").attr("fvalue",$("#DI_YSGXF option:selected").val());
	//$("#PREAUTHOR_OBJECT").attr("fcode",$("#DI_YSGXF option:selected").val());
	//$("#PREAUTHOR_OBJECT").attr("code",$("#DI_YSGXF option:selected").val());
	//$("#PREAUTHOR_OBJECT").attr("fvalue",$("#DI_YSGXF option:selected").val());
	$("#PREAUTHOR_OBJECT_CODE").attr("fvalue",$("#DI_YSGXF option:selected").val());
	$("#PREAUTHOR_OBJECT").attr("fvalue",$("#DI_YSGXF option:selected").text());
	/* var a=$("#DI_YSGXF").attr("code");
	var b=$("#DI_YSGXF").val(); */
	//alert($("#PREAUTHOR_OBJECT").attr("code"));
	if($("#DI_YSGXF").val()==306102){
	     $("#fVALUE").attr("fkind","dic");
		 $("#fVALUE").attr("fsrc","YSQLX");
	}
	/* else{
		 $("#fVALUE").attr("fkind","");
		
	} */
    
/* 	if($("#DI_YSGXF").val()==2){
		$("#fVALUE").attr("fsrc","E#1=等于:2=小于:3=小于等于:4=大于:5=大于等于:6=不等于");
		$("#fVALUE").attr("fvalue","等于");
	} */
	var $tab = $("#di_ysqgzlb");
	$tab.createRow();
}
function intFvalue(obj)
{      
	if(obj.text()==306102){
	     $("#fVALUE").attr("fkind","dic");
		 $("#fVALUE").attr("fsrc","YSQLX");
		 return
	}

}
//行编辑保存回调
function diaListSaveCallBack(res){

		var selectedIndex = $("#di_ysqgzlb").getSelectedIndex();
		$("#di_ysqgzlb").updateResult(res,selectedIndex);
		$("#di_ysqgzlb").clearRowEdit($("#di_ysqgzlb").getSelectedRows()[0],selectedIndex);
		//规则名称拼接
		assembleItem();
		var $f = $("#fm_item");
			//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
			if (submitForm($f) == false) return false;
			var sCondition = {};
			//将需要提交的内容拼接成json
			sCondition = $("#fm_item").combined(1) || {};			
			var addUrl = diaSaveAction + "/updatePreauthoName.ajax?&rule_id="+$("#dia_RULE_ID").val();
			//doFormSubmit($f,addUrl,"",1,sCondition,"tab-list");
			//doFormSubmit($f,addUrl,"",1,sCondition,"");
			doFormSubmit($f,addUrl,"",1,sCondition,"");
	return true;
}

function goBack(){
		history.go(-1);
	}

//移除行
function delItem(row)
{
	$row =$(row);
	$("td input[type=radio]",$(row)).attr("checked",true);
	var dtlId = $(row).attr("DTL_ID");
	if(dtlId){
		var url = ruleDtlAction + "/delete.ajax?dtlId="+dtlId;
		sendPost(url,"delete","",diadeleteCallBack,"true");
	}else{
	$("#di_ysqgzlb").removeResult($row);
	}
}


//修改回调
function diadeleteCallBack(res){
	try
	{if($row) 
			$("#di_ysqgzlb").removeResult($row);
	//规则名称拼接
	assembleItem();
	var $f = $("#fm_item");
	//submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
	if (submitForm($f) == false) return false;
	var sCondition = {};
	//将需要提交的内容拼接成json
	sCondition = $("#fm_item").combined(1) || {};			
	var addUrl = diaSaveAction + "/updatePreauthoName.ajax?&rule_id="+$("#dia_RULE_ID").val();
	//doFormSubmit($f,addUrl,"",1,sCondition,"tab-list");
	//doFormSubmit($f,addUrl,"",1,sCondition,"");
	doFormSubmit($f,addUrl,"",1,sCondition,"");
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</div>