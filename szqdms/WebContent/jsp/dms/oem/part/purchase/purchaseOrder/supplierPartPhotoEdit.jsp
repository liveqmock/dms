<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.dms.common.DicConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%
	String sId = request.getParameter("sId");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/head.jsp" />
<title>配件照片审核</title>
<script src="<%=request.getContextPath() %>/lib/plugins/mine.filestore.js" type="text/javascript"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery.lightbox-0.5.css" media="screen" />
<script type="text/javascript" src="js/jquery.lightbox-0.5.js"></script>
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<script type="text/javascript">
var $true=true;
var $checkHisTrue=true;
var $fileTrue=true;
var authorStatus=null;
var dia_dialog = parent.$("body").data("preAuthApply");
var itemSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/itemSearch.ajax";
var fileSearchUrl = "<%=request.getContextPath()%>/service/preauthMng/PreAuthApplyAction/fileSearch.ajax";
var mngUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseOrder/SupplierPartPhotoApplyMngAciton";
$(function() 
{
	var supId=parent.sId;
	var searchUrl = mngUrl+"/detaileSearch.ajax?supId="+supId;
	var $f = $("#fm-part-photos");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"dia-tab-partinfo");
	//未审核件查询
	$("#dia-searchCheck").bind('click',function(){
		var supId=parent.sId;
		var url = mngUrl+"/detaileSearchCheck.ajax?supId="+supId;
		var $f = $("#fm-part-photos");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		sCondition = $f.combined() || {};
		doFormSubmit($f,url,"dia-searchCheck",1,sCondition,"dia-tab-partinfo");
	});
	//已审核件查询
	$("#dia-searchChecked").bind('click',function(){
		var supId=parent.sId;
		var url = mngUrl+"/detaileSearchChecked.ajax?supId="+supId;
		var $f = $("#fm-part-photos");//获取页面提交请求的form对象
		var sCondition = {};//定义json条件对象
		sCondition = $f.combined() || {};
		doFormSubmit($f,url,"dia-searchCheck",1,sCondition,"dia-tab-partinfo");
	});
   //审核通过按钮响应	
    $('#dia-save').bind('click',function(){
        //设置各隐藏域值
        var photoId="";
        $("tr",$("#dia-tab-partinfo")).each(function(){
        	var $tr = $(this);
        	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        	if($checkbox.is(":checked"))
        	{
        		photoId += photoId.length?"," + $tr.attr("PHOTO_ID"):$tr.attr("PHOTO_ID");
        		
        	}
        });
        if(photoId.length==0){
        	alertMsg.info("请选择配件.");
        	return false;
        }
        $('#photoIds').val(photoId);

        var insertPartUrl =mngUrl+"/passPartPhoto.ajax";
        //获取需要提交的form对象
        var $f = $("#fm-photosVal");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, insertPartUrl, "btn-confirmPart", sCondition, insertPartCallBack);
    });
	 //驳回按钮响应
    $("#dia-reject").bind('click',function(){
    	
    	//设置各隐藏域值
        var photoId="";
        $("tr",$("#dia-tab-partinfo")).each(function(){
        	var $tr = $(this);
        	var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        	if($checkbox.is(":checked"))
        	{
        		photoId += photoId.length?"," + $tr.attr("PHOTO_ID"):$tr.attr("PHOTO_ID");
        		
        	}
        });
        if(photoId.length==0){
        	alertMsg.info("请选择配件.");
        	return false;
        }
        $('#photoIds').val(photoId);
        
        var insertPartUrl =mngUrl+"/rejectPartPhoto.ajax";
      //获取需要提交的form对象
        var $f = $("#fm-photosVal");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        doNormalSubmit($f, insertPartUrl, "btn-confirmPart", sCondition, insertPartCallBack);
    });
	//关闭当前页面
	$("button.close").click(function() 
	{
		parent.$.pdialog.close("photoCheck");
		return false;
	});
});

//新增促销配件回调
function insertPartCallBack(){
	var supId=parent.sId;
	var searchUrl = mngUrl+"/detaileSearch.ajax?supId="+supId;
	var $f = $("#fm-part-photos");//获取页面提交请求的form对象
	var sCondition = {};//定义json条件对象
	sCondition = $f.combined() || {};
	doFormSubmit($f,searchUrl,"btn-search",1,sCondition,"dia-tab-partinfo");
    $("#val0").val("");
    $("#val1").val("");
    $("#val2").val("");
}
/**
 * 附件下载链接
 */
function showPic(obj)
{
	var $row = $(obj).parent();
	var fid = $row.attr("FID").split(",");
	var fjmc = $row.attr("FJMC").split(",");
	var wjjbs = $row.attr("WJJBS").split(",");
	var blwjm = $row.attr("BLWJM").split(",");
	
	var s = "";
	
	for(var i=0;i<wjjbs.length;i++)
	{
		var imgUrl = webApps + '/FileStoreAction/downloadFile.do?fjid='+fid[i]+'&fjmc='+fjmc[i]+'&wjjbs=' +wjjbs[i]+"&blwjm="+blwjm[i];
		s += "<a href='"+imgUrl+"'><img src='"+imgUrl+"' style='border:solid 1px #CE8031;width:80px;height:80px;margin:5px 5px 5px 5px;' data_src='"+imgUrl+"' name= 'photo' ></img></a>";
	}
	s += "";
	var k = "<div class='container' ><div class='gallery'>"+s+"</div></div>";
	return k;
}
function callbackSearch(res,tabId)
{
	if(tabId == "dia-tab-partinfo") 
	{
		$("#"+tabId+"").parent().height(document.documentElement.clientHeight-30);
		$("#dia-tab-partinfo a").lightBox();
	}
}
function myInputBox(obj)
{
    return '<input type="text" name="REMARKS" onblur="doMyRemarksInputBlur(this)"/ >';
}
//input框焦点移开事件 步骤一
function doMyRemarksInputBlur(obj){
    var $obj = $(obj);
    if($obj.val() == "")//为空直接返回
        return ;
    var $tr = $obj.parent().parent().parent();
    var checkObj = $("input:first",$tr.find("td").eq(1));
    var s = $obj.val();
    if(s)
    {
        checkObj.attr("checked", true);
    }
    doSelectedBefore1($tr,checkObj,1);
}
/**
 * $tr:当前行对象jquery 步骤二
 * @param $obj:checkbox的jQuery对象
 * @param type:
 */
function doSelectedBefore1($tr,$obj,type)
{
    var $input = $tr.find("td").eq(6).find("input:first");

    var bz = "";
    if($input && $input.get(0).tagName=="INPUT")
    	bz = $input.val();
    doCheckbox($obj.get(0));
}
  //列表复选
    function doCheckbox(checkbox)
    {
    	var arr = [];
    	var $checkbox = $(checkbox);
    	while($checkbox[0].tagName != "TABLE"){
    		$checkbox = $checkbox.parent();
    	}
    	if($checkbox.attr("id").indexOf("dia-tab-partinfo")==0){
    		var $tr = $(checkbox).parent().parent().parent();
    	    var $inputBz = $tr.find("td").eq(6).find("input:first");
    	    var bz = "";
    		bz = $inputBz.val();
    	  	//判断入库数量是否有值，如没有值，提示输入
    	    var arr = [];
    	    arr.push($tr.attr("PHOTO_ID"));
    	    if(bz.length==0){
    	    	arr.push("anull");
    	    }else{
    	    	arr.push(bz);
    	    }
    	    arr.push($tr.attr("PART_ID"));
    	    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    	    multiSelected($checkbox, arr,$("#photoVals"));
    	}
    }
</script>
</head>
<body>
<div id="dia-layout">
		<div class="page" id="preAuthBasic" style="overflow:auto;">
			<div class="pageContent" >
				<form method="post" id="fm-part-photos" class="editForm" style="width: 99%;">
					<div align="left" id="dia-photoShow" style="overflow:hidden;">
						<table style="display:none;width:99%;" id="dia-tab-partinfo" name="tablist" multivals="photoVals" ref="fm-part-photos" edit="false" >
							<thead>
								<tr>
									<th type="multi" name="XH" unique="PHOTO_ID"></th>
									<th fieldname="PART_CODE" >供货品种代码</th>
									<th fieldname="PART_NAME" >供货品种名称</th>
									<th fieldname="MIN_UNIT" >最小包装单位</th>
									<th fieldname="MIN_PACK" >最小包装数</th>
									<!-- <th fieldname="REMARKS" refer="myInputBox">备注</th> -->
									<th fieldname="APPLY_STATUS"  >照片审核状态</th>
									<th refer="showPic" ></th>
								</tr>
							</thead>
						</table>
					</div>
				</form>
				<div id="photoVals">
						<table style="display:none">
							<tr>
								<td><textarea id="val0" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
								<td><textarea id="val1" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
								<td><textarea id="val2" name="multivals" style="width:400px;height:10px" column="1" style="" ></textarea></td>
							</tr>
						</table>
					</div>
					<form id="fm-photosVal">
						<input type="hidden" id="photoIds" name="photoId" datasource="PHOTOID"/>
		                <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
					</form>
				<div class="formBar">
					<ul>
						<li id="dia-save-li"><div class="buttonActive"><div class="buttonContent"><button type="button" id="dia-searchCheck">查询未审核件</button></div></div></li>
						<li id="dia-save-li"><div class="button"><div class="buttonContent"><button type="button" id="dia-searchChecked">查询已审核件</button></div></div></li>
						<li id="dia-save-li"><div class="button"><div class="buttonContent"><button type="button" id="dia-save">通&nbsp;&nbsp;过</button></div></div></li>
						<li id="dia-reject_li"><div class="button"><div class="buttonContent"><button type="button" id="dia-reject">驳&nbsp;&nbsp;回</button></div></div></li>
						<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
					</ul>
				</div>
			</div>
		</div>
</div>
</body>
</html>