<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.dms.common.DicConstant"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件照片管理</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery.lightbox-0.5.css" media="screen" />
<script type="text/javascript" src="js/jquery.lightbox-0.5.js"></script>
<script type="text/javascript">
//变量定义
//查询提交方法
var url = "<%=request.getContextPath()%>/Pjjcxxgl/PjdaglAction.do";
var searchUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPictureAction/search.ajax";
var deleteUrl = "<%=request.getContextPath()%>/part/basicInfoMng/PtBaPictureAction/deletepic.ajax";
//定义弹出窗口样式
var diaAddOptions = {max:false,width:720,height:180,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
//初始化方法
$(function(){

	//查询按钮响应
	$("#btn-cx").bind("click", function(event){
		var $f = $("#fm-pjcx");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		//combined()：实现将页面条件按规则拼接成json
    	sCondition = $f.combined() || {};
<!--    	var url = searchUrl + "?partCode="+$("#in-part_code").val()+"&partName="+$("#in-part_name").val();-->
		var url = searchUrl + "?if_null="+$("#in-if_null").val();
    	/**
    	 * doFormSubmit:提交查询请求
    	 * @$f:提交form表单的jquery对象
    	 * @searchUrl:提交请求url路径
    	 * @"search":提交查询操作按钮id
    	 * @1:查询结果返回时显示第几页，默认显示第一页数据
    	 * @sCondition：页面定义的查询条件（json）
    	 * @"yhlb":查询返回结果显示的table表格id
    	 */
		doFormSubmit($f,url,"btn-cx",1,sCondition,"tab-pjlb");
	});

	
	//新增方法
	$("#btn-xz").bind("click",function(event){
		var options = {max:false,width:720,height:210,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
		$.pdialog.open(webApps + "/jsp/dms/oem/part/basicInfo//ptbapicture/ptbapictureAdd.jsp?action=1", "pjzpxz", "配件照片信息新增", options);
							      
	});		
	//下载附件
    $("#btn-downAtt").bind("click", function () {
        var promId = $("#dia-PROM_ID").val();
        if(!promId){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        $.filestore.view(promId);
    });			
    
    //导入
     $('#btn-importExcel').bind('click',function(){
        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
        //最后一个参数表示 导入成功后显示页                               
        importXls("PT_BA_PICTURE_TMP","",5,3,"/jsp/dms/oem/part/basicInfo/ptbapicture/importSuccess.jsp");
	});   
	
	  
	//导出
	$("#btn-downLoad").click(function(){
		var $f = $("#fm-pjcx");
		var sCondition = {};
    	sCondition = $f.combined() || {}; 
    	$("#params").val(sCondition);
    	
        $("#exportFm").attr("action","<%=request.getContextPath()%>/part/basicInfoMng/PtBaPictureAction/download.ajax");
        $("#exportFm").submit();
    });		  
	
});
var selectedRowObj;
//列表上传连接
function doUpload(rowobj)
{
	var $rowobj = null;
	var $link = $(window.event.srcElement);
	$rowobj = $link;
	while($rowobj.get(0).tagName != "TR")
	{
		$rowobj = $rowobj.parent();
	} 
	$("td input[type=radio]",$rowobj).attr("checked",true);
	
	var $tab = $("#tab-pjlb");
	var $rows = $tab.find("tr");
	var $row = null;
	$rows.each(function(){
		var $r = $(this);
		var $radio = $r.find("td").eq(1).find("input[type='radio']:first");
		if($radio.is(":checked"))
		{
			$row = $r;
			return false;
		}
	});
	selectedRowObj = $row;
	$.pdialog.open(webApps+"/jsp/dms/oem/part/basicInfo/ptbapicture/ptbapictureUp.jsp?action=2", "配件图片上传", "配件图片上传", diaAddOptions);
}
//查看
function doView(rowobj)
{
	 var PART_ID = $(rowobj).attr("PART_ID");
        if(!PART_ID){
            alertMsg.warn('请先保存基本信息!');
            return;
        }
        $.filestore.view(PART_ID);
}

var $row;
//删除方法，rowobj：行对象，非jquery类型
function doDelete(rowobj)
{
	$row = $(rowobj);
	
	var url = deleteUrl + "?picture_id="+$(rowobj).attr("PICTURE_ID")+"&status="+$(rowobj).attr("STATUS");

	/**
	 * sendPost:提交一般请求方法
	 * @url:提交请求url路径
	 * @"delete":提交请求句柄id
	 * @"":提交请求参数，可为空（json格式）
	 * @deleteCallBack：提交请求后台执行成功后，页面回调函数
	 * @"true":是否需要confirm确认对话框，"true"为弹出确认框
	 */
	sendPost(url,"delete","",deleteCallBack,"true");
}

function showPic(obj)
{
	var $row = $(obj).parent();
	var fjid = $row.attr("FJID").split(",");
	var fid = $row.attr("FID").split(",");
	var fjmc = $row.attr("FJMC").split(",");
	var wjjbs = $row.attr("WJJBS").split(",");
	var blwjm = $row.attr("BLWJM").split(",");
	
	var s = "";
	for(var i=0;i<wjjbs.length;i++)
	{
		var imgUrl = webApps + '/FileStoreAction/downloadFile.do?fjid='+fid[i]+'&fjmc='+fjmc[i]+'&wjjbs=' +wjjbs[i]+"&blwjm="+blwjm[i];
		s += "<div style='float:left;'><a href='"+imgUrl+"' imgLink='true'><img src='"+imgUrl+"' style='border:solid 1px #CE8031;width:80px;height:80px;margin:5px 5px 5px 5px;' data_src='"+imgUrl+"' name= 'photo' ></img></a>";
		s += "<span style='float:left;'><a href='javascript:void(0);return false;' onclick='doDelPic(this)' fjid='"+fjid[i]+"' style='color:red;'><img style='width:14px;height:14px;' src='<%=request.getContextPath()%>/images/default/gb.gif' /></a></span></div>";
	}
	s += "";
	var k = "<div class='container' ><div class='gallery'>"+s+"</div></div>";
	return k;
}
function callbackSearch(res,tabId)
{
	if(tabId == "tab-pjlb") 
	{
		$("#"+tabId+"").parent().height(document.documentElement.clientHeight-70);
		$("#tab-pjlb a[imgLink='true']").lightBox();
	}
}
function doDelPic(link)
{
	var $row = $(link);
	while($row.get(0).tagName != "TR")
		$row = $row.parent();
	//alert($(link).attr("fjid"));
	var url = deleteUrl + "?fjid="+$(link).attr("fjid");
	//alert(url);
	sendPost(url,"delete","",deleteCallBack,"true");
} 
//删除回调方法
function  deleteCallBack(res)
{
	try
	{
		//if($row) 
		$("#btn-cx").trigger("click");		
	}catch(e)
	{
		alertMsg.error(e);
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="layout" style="width:100%;">
	<div id='background1' class='background'></div>
	<div id='progressBar1' class='progressBar'>loading...</div>
	<h4 class="contentTitle" align="left"><img src="<%=request.getContextPath() %>/images/default/nav.gif" />&nbsp;当前位置： 基础数据管理  &gt; 基础信息管理   &gt; 配件照片管理</h4>
	<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="fm-pjcx">
			<!-- 定义隐藏域条件 -->
			<div class="searchBar" align="left" >
				<table class="searchContent" id="tab-pjcx">
					<!-- 定义查询条件 -->
					<tr>
					    <td><label>配件代码：</label></td>
					    <td><input type="text" id="in-part_code" name="in-part_code" datasource="PART_CODE" datatype="1,is_null,300" operation="like" /></td>
					    <td><label>配件名称：</label></td>					   
					    <td><input type="text" id="in-part_name"  name="in-part_name" datasource="PART_NAME"  datatype="1,is_null,300" operation="like"  /></td>	
					    
					    <td><label>是否有照片：</label></td>
						<td>
							<select type="text" id="in-if_null" name="in-if_null" kind="dic" src="SF"  datatype="1,is_null,300" >
					    		<option value="-1" selected>--</option>
					    	</select>
						</td>
					 <tr>
<!--					 	<td><label>是否有照片：</label></td>-->
<!--					    <td>-->
<!--					    	<select type="text" id="in-status" name="in-status" kind="dic" src="SF" datasource="STATUS" datatype="1,is_null,6" >					  -->
<!--					    	<option value="-1" selected>--</option>-->
<!--					    	</select>-->
<!--					    </td>-->
<!--					    <td><label>配件状态：</label></td>-->
<!--					    <td>-->
<!--					    	<select type="text" id="in-pjzt"  name="in-pjzt" datasource="PART_STATUS" kind="dic" src="PJZT" datatype="1,is_null,30" >-->
<!--					    		<option value="-1" selected>--</option>-->
<!--					    	</select>-->
<!--					    </td>	-->
					 
					 </tr>   
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-cx" >查&nbsp;&nbsp;询</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-xz" >新&nbsp;&nbsp;增</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-importExcel" >导&nbsp;&nbsp;入</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button type="button" id="btn-downLoad" >导&nbsp;&nbsp;出</button></div></div></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="page_yhlb" >
			<table style="display:none;width:100%;" id="tab-pjlb" name="tablist" ref="page_yhlb" refQuery="fm-pjcx" limitH="false" >
					<thead>
						<tr>
							<th fieldname="ROWNUMS" style="display:"></th>
							<th type="single" name="XH" style="display:none;"></th>	
							<th fieldname="PART_ID"  style="display:none;">配件id</th>	
							<th fieldname="PART_CODE" >配件代码</th>								
							<th fieldname="PART_NAME" >配件名称</th>
							<th fieldname="PART_STATUS" >配件状态</th>
							<th fieldname="CREATE_TIME" >上传时间</th>
<!--							<th colwidth="85" type="link" title="[编辑]|[查看]"  action="doUpload|doView" >操作</th>-->
							<th colwidth="85" type="link" title="[编辑]"  action="doUpload" >操作</th>
							<th refer="showPic"></th>
						</tr>
					</thead>					
			</table>
		</div>
	</div>
	</div>
	<form id="exportFm" method="post" style="display:none">
		<input type="hidden" id="params" name="data"></input>
	</form>
</div>
</body>
</html>