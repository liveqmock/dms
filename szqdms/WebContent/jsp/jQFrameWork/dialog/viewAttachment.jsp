<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.org.framework.common.User"%>
<%@ page import="com.org.framework.Globals"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String sessionId = request.getSession().getId();
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
%>
<!-- 
	 Title:查看附件列表
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2013-01
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<div class="page" >
	<div class="pageHeader" >
		<form method="post" id="dialog-fm-viewAttach">
		<!-- 定义查询条件 -->
		<input type="hidden" id="dia-in-fjzt" name="dia-in-fjzt" datasource="FJZT" value="1" />
		<input type="hidden" id="dia-in-ywzj" name="dia-in-ywzj" datasource="YWZJ" />
		<div class="searchBar" align="left" >
			<table class="searchContent" id="dialog-tab-viewAttach">
					<tr><td><label>附件名称：</label></td>
					    <td><input type="text" id="dia-fjmc" name="dia-fjmc" datasource="FJMC" datatype="1,is_null,30" operation="like" value=""/></td>
					</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="dialog-btn-search">查&nbsp;&nbsp;询</button></div></div></li>
				</ul>
			</div>
		</div>
		</form>
	</div>
	<div class="pageContent" >
		<div id="page_dialog-fjlb" >
			<table style="display:none"  width="100%" id="dialog-tab-fjlb" layoutH="230" name="tablist" ref="page_dialog-fjlb" refQuery="dialog-tab-viewAttach" >
					<thead>
						<tr>
							<th type="single" name="XH" style="display:none;"></th>
							<th type="link" title="[下载]|[删除]" action="doFjView|doFjRemove" colwidth="85">操作</th>
							<th fieldname="FJMC" ordertype='local' class="asc">附件名称</th>
							<th fieldname="CJR" >上传人</th>
							<th fieldname="CJSJ" ordertype='local' class="asc">上传时间</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
			</table>
		</div>
	</div>
	<form id="dialog-fm-download"style="display:none">
	</form>
</div>	
<script type="text/javascript">
var dialogViewAttach = $("body").data("dialog-viewAttachment");
var dialogViewAttachUrl = webApps + "/FileStoreAction/viewFile.ajax";
$(function(){
	//设置主键
	var dialogFjYwzj = dialogViewAttach.data("op").pk;
	$("#dia-in-ywzj").val(dialogFjYwzj);
	var $f = $("#dialog-fm-viewAttach");
	var sCondition = {};
	sCondition = $("#dialog-fm-viewAttach").combined() || {};
	doFormSubmit($f.eq(0),dialogViewAttachUrl,"dialog-btn-search",1,sCondition,"dialog-tab-fjlb");
	$("#dialog-btn-search").click(function(){
		var $f = $("#dialog-fm-viewAttach");
		var sCondition = {};
    	sCondition = $("#dialog-fm-viewAttach").combined() || {};
		doFormSubmit($f.eq(0),dialogViewAttachUrl,"dialog-btn-search",1,sCondition,"dialog-tab-fjlb");
	});
	$("body").removeData("dialog-viewAttachment");
});
/**
 * 附件下载链接
 */
function doFjView(obj)
{
	var fid = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var blwjm = $(obj).attr("BLWJM");
	$.filestore.download({"fjid":fid,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm});
}
/**
 * 附件删除链接
 */
var diaFjDelRow,diaFjid;
function doFjRemove(obj)
{
	var fileId = $(obj).attr("FID");
	var fjmc = $(obj).attr("FJMC");
	var wjjbs = $(obj).attr("WJJBS");
	var fjid = $(obj).attr("FJID");
	var blwjm = $(obj).attr("BLWJM");
	diaFjid = fjid;
	diaFjDelRow = $(obj);
	$.filestore.remove({"fjid":fjid,"fileId":fileId,"fjmc":fjmc,"wjjbs":wjjbs,"blwjm":blwjm,"callback":delFjBack});
}
function delFjBack(){
	$("#dialog-tab-fjlb").removeResult(diaFjDelRow);
	try{
		fjDelCallBack(diaFjid);
	}catch(e){}
}
</script>
<script src="<%=request.getContextPath()%>/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>