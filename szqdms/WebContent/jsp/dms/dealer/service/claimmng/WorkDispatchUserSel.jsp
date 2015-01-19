<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class="page" >
<div class="pageHeader" >
	<form method="post" id="fm-searchSelUser">
		<div class="searchBar" align="left" >
			<table class="searchContent" id="tab-searchSelUser">
				<tr>
				    <td><label>人员姓名：</label></td>
				    <td><input type="text" id="sel_user_name" name="sel_user_name" datatype="1,is_null,30" dataSource="USER_NAME" operation="like" /></td>
				    <td><label>联系电话：</label></td>
				    <td><input type="text" id="sel_mobil" name="sel_mobil" datatype="1,is_null,30" dataSource="MOBIL" operation="like" /></td>
				</tr>					
			</table>
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" id="btn-searchSelUser" >查&nbsp;&nbsp;询</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div id="page_selUserList" >
		<table style="display:none;width:100%;" id="tab-selUserList" name="tablist" ref="page_selUserList" refQuery="tab-searchSelUser" >
				<thead>
					<tr>
						<th type="multi" name="XH" unique="USER_ID" ></th>
						<th fieldname="USER_NAME">人员姓名</th>
						<th fieldname="MOBIL">联系电话</th>
						<th fieldname="ADDRESS">地址</th>
						<!-- <th type="link" title="操作" action="doOk" class="btnSelect">操作</th> -->
					</tr>
				</thead>
				<tbody>
                </tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
//查询提交方法
var searchSelUrl = "<%=request.getContextPath()%>/service/claimmng/WorkDispatchUerMngAction/search.ajax";
var diadialog = $("body").data("WorkDispatchUserSel");
$(function()
{
	doSearch();
	$("#btn-searchSelUser").bind("click", function(event){
		doSearch();
	});
});
function doSearch(){
	var $f = $("#fm-searchSelUser");
	var sCondition = {};
    sCondition = $f.combined() || {};
	doFormSubmit($f,searchSelUrl,"btn-searchSelUser",1,sCondition,"tab-selUserList");
}


/* function doOk(rowobj)
{
	try
	{
		SelCallBack(rowobj);	
	}catch(e){}
	
	$.pdialog.close(dialog);
} */
//设置关闭按钮事件
$("button.close").click(function() 
{
	$.pdialog.close(diadialog);
	return false;
});
</script>
</div>

