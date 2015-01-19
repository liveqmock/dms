<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs"  eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>活动方案基本信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>经销商信息</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--活动方案基本信息Tab begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="fm-programActive" class="editForm" style="width:99%;">
				<input type="hidden" id="dia-activeId" name="dia-activeId" datasource="ACTIVE_ID" />
				<input type="hidden" id="dia-activeStatus" name="dia-activeStatus" datasource="ACTIVE_STATUS" />
		  		<div align="left">
			  		<fieldset>
						<table class="editTable" id="dia-tab-htxx">
							<tr>
								<td><label>活动代码：</label></td>
							    <td>
							    	<input type="text" id="dia-activeCode" name="dia-activeCode" datasource="ACTIVE_CODE" datatype="1,is_null,30" readonly='true'/>
							    </td>
							    <td><label>活动名称：</label></td>
							    <td>
							    	<input type="text" id="dia-activeName" name="dia-activeName" datasource="ACTIVE_NAME" datatype="0,is_null,30"/>
							    </td>
							    <td><label>有&nbsp;  效&nbsp;  标&nbsp;  识：</label></td>
							     <td>
							    	<select id="dia-status" name="dia-status" kind="dic" src="YXBS" datasource="STATUS" datatype="0,is_null,6" operation="=" style="width:75px;" >
							    		<option value="100201" selected>有效</option>
							    	</select>
							     </td>
							</tr>
							
							<tr>
								<td><label>开始时间：</label></td>
							    <td>
						    		<input type="text" id="dia-startDate"  name="dia-startDate" dataSource="START_DATE" datatype="0,is_date,30"  
						    			kind="date"  onclick="WdatePicker()" readonly="true" />
						   		 </td>
							     <td><label>结束时间：</label></td>
							     <td colspan="3">
						    		<input type="text" id="dia-endDate"  name="dia-endDate" dataSource="END_DATE" datatype="0,is_date,30" 
						    			kind="date"  onclick="WdatePicker()" readonly="true" />
						   		 </td>
							</tr>
							<tr>
							    <td><label>活动方案内容：</label></td>
							    <td colspan="5">
								  <textarea id="dia-activeContent" style="width:93%;height:40px;" name="dia-activeContent" datasource="ACTIVE_CONTENT" datatype="1,is_null,1000"></textarea>
							    </td>
							</tr>
							<tr>
							    <td><label>备　　注：</label></td>
							    <td colspan="5">
								  <textarea id="dia-remarks" style="width:93%;height:40px;" name="dia-remarks" datasource="REMARKS" datatype="1,is_null,1000"></textarea>
							    </td>
							</tr>
						</table>	
			  		</fieldset>
				</div>
				</form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--活动方案基本信息Tab end-->
            <!--经销商信息Tab begin-->
            <div class="page">
                <div class="pageContent" >
                	<form method="post" id="dia-fm-searchProActiveDealer" class="editForm">
                        <table class="searchContent" id="dia-tab-searchProActiveDealerList"></table>
                    </form>
                    <div class="panelBar">
						<ul class="toolBar">
							<li class="line">line</li>
							<li><a class="add" href="javascript:void(0);" id="btn-batchAddDealers" title=""><span>批量新增</span></a></li>
							<li class="line">line</li>
							<li><a class="delete" href="javascript:void(0);" id="btn-batchDeleteAll" title="确定要删除吗?"><span>批量删除</span></a></li>
							<li class="line">line</li>
							<li><a class="icon" href="javascript:void(0);" id="btn-download" title="确定要删除吗?"><span>下载导入模板</span></a></li>
							<li class="line">line</li>
							<li><a class="add" href="javascript:void(0);" id="btn-import" title="确定要导入吗?"><span>导入数据</span></a></li>
						</ul>
					</div>
                    <div id="dia-page_proActiveDealerList" style="">
                        <table style="display:none;width:100%;" id="dia-tab-proActiveDealerList" name="tablist" ref="dia-page_proActiveDealerList" 
                        	refQuery="dia-tab-searchProActiveDealerList" multivals="dealerDeleteVal">
                            <thead>
	                           	<tr>
	                                <th type="multi" id="CX-XH" name="CX-XH" unique="DTL_ID"></th>
	                                <th fieldname="ACTIVE_CODE" >活动代码</th>
	                                <th fieldname="ACTIVE_NAME" >活动名称</th>
	                                <th fieldname="CODE" >渠道商代码</th>
	                                <th fieldname="ONAME" >渠道商名称</th>
	                                <th fieldname="CREATE_USER" >创建人</th>
	                                <th fieldname="CREATE_TIME" >创建时间</th>
	                                <th fieldname="STATUS" >有效标识</th>
	                                <th colwidth="105" type="link" title="[删除]"  action="doDealerDelete">操作</th>
	                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" id="btn-pre">上&nbsp;一&nbsp;步</button></div></div></li>
<!--                        <li id="dia-contror" style="display: none"><div class="button"><div class="buttonContent"><button type="button" id="btn-report" >提&nbsp;&nbsp;报</button></div></div></li>-->
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
                
                <legend align="left" >&nbsp;[已选定活动对应的渠道商]</legend>
				<div id="dealerDeleteVal">
					<table style="display:none">
						<tr>
							<td><textarea id="val3" name="multivals" style="width:400px;height:10px" column="1" style="display:none" ></textarea></td>
						</tr>
					</table>
				</div>
            </div>
            <!--经销商信息Tab end-->
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //注册上一步/下一步按钮响应
    (function ($) {
        $.contractMng = {
            //初始化方法
            init: function () {
                //设置页面默认值
                $tabs = $("div.tabs:first");
                var iH = document.documentElement.clientHeight;
                $(".tabsContent").height(iH - 70);
                $("button[name='btn-next']").bind("click", function () {
                    $.contractMng.doNextTab($tabs);
                });
                $("button[name='btn-pre']").bind("click", function () {
                    $.contractMng.doPreTab($tabs);
                });
            },
            doNextTab: function ($tabs) {
                $tabs.switchTab(parseInt($tabs.attr("currentIndex")) + 1);
                (function (ci) {
                    switch (parseInt(ci)) {
                        case 1://第2个tab页
                            if(!$('#dia-activeId').val()){
                                alertMsg.warn('请先保存活动方案信息!');
                            }else{
                                if($("#dia-tab-proActiveDealerList").is(":hidden"))
                                {
                                    $("#dia-tab-proActiveDealerList").show();
                                    $("#dia-tab-proActiveDealerList").jTable();
                                }
                                searchDealer();
                            }
                            break;
                        default:
                            break;
                    }
                })(parseInt($tabs.attr("currentIndex")));
            },
            doPreTab: function ($tabs) {
                $tabs.switchTab($tabs.attr("currentIndex") - 1);
            }
        };
    })(jQuery);
    
    
	//取父页面传过的参数确定新增或者修改
    var diaAction = "<%=action%>";
    var diaUrl = "<%=request.getContextPath()%>/part/marketMng/marketActiveMng/ProgramActiveAction";
    var flag = true;
    var sign = true;
    
    $(function () {

        var iH = document.documentElement.clientHeight;
        $(".tabsContent").height(iH - 70);
        
        //上一页按钮
        $("#btn-pre").bind("click",function(event){
            $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
        });
        
        //下一页按钮
        $("#btn-next").bind("click",function(event){
            var $tabs = $("#dia-tabs");
            $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
            
            //跳转后实现方法
            switch(parseInt($tabs.attr("currentIndex")))
            {
                case 1:
                    if (!$('#dia-activeId').val()) {
                        alertMsg.warn('请先保存活动方案信息!')
                        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                    } else {
                        if(flag){
                            if ($("#dia-tab-proActiveDealerList").is(":hidden")) {
                                $("#dia-tab-proActiveDealerList").show();
                                $("#dia-tab-proActiveDealerList").jTable();
                            }
                            searchDealer();
                        }
                        flag = false;
                    }
                    break;
                default:
                    break;
            }
        });
        
        if (diaAction == "1") {
            //新增页面自动生成活动代码
            $("#dia-activeCode").val("自动生成编号");
        } else {
        	//修改页面赋值
        	var selectedRows = $("#tab-programActiveList").getSelectedRows();
            setEditValue("fm-programActive", selectedRows[0].attr("rowdata"));
        }
        
        //保存按钮
        $("#btn-save").bind("click", function(event){
			var $f = $("#fm-programActive");
			if (submitForm($f) == false) return false;
			
			var startDate = $("#dia-startDate").val().replace(/-/g,"");
			var endDate = $("#dia-endDate").val().replace(/-/g,"");
			
			if (parseInt(startDate) >= parseInt(endDate)) {
				alert("开始时间必须小于结束时间！");
				return false;
			}
			
			var sCondition = {};
			sCondition = $f.combined(1) || {};
			if(diaAction == 1)
			{   
				var addUrl = diaUrl + "/insert.ajax";
				doNormalSubmit($f,addUrl,"btn-save",sCondition,diaInsertCallBack);
			}else	
			{
				var updateUrl = diaUrl + "/update.ajax";
				doNormalSubmit($f,updateUrl,"btn-save",sCondition,diaUpdateCallBack);
			}
		});
		
		//批量新增按钮
		$("#btn-batchAddDealers").bind("click", function(event){
	    
	    	var $tab = $("#dia-tab-proActiveDealerList");
	    	if(sign==false){
	    		alertMsg.warn("当前行数据尚未提交成功！");
	    		return false;
	    	}else{
	    		//$tab.createRow();
	    		//定义弹出窗口样式
				var diaDealerOptions = {max:false,width:720,height:500,mask:true,mixable:true,minable:true,resizable:true,drawable:true};
	    		$.pdialog.open(webApps+"/jsp/dms/oem/part/market/dealerBatchAdd.jsp?action=3", "新增渠道商", "新增渠道商", diaDealerOptions);
	    	}
	    });
    
    	//批量删除按钮
        $("#btn-batchDeleteAll").bind("click", function(){
        	var activeDealersId = $("#val3").val();
        	var deleteAllUrl = diaUrl + "/deleteProActiveDealers.ajax?activeDealersId="+activeDealersId;
    		sendPost(deleteAllUrl,"","",batchDeleteCallBack,"true");  
    	});
    	
    	//下载导入模板按钮
    	$('#btn-download').bind('click', function () {
	        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=PchContPartImp.xls");
	        window.location.href = url;
	    });
	    
	    //导入数据按钮
		$('#btn-import').bind('click', function () {
	        if(!$('#dia-activeId').val()){
	            alertMsg.warn('请先保存基本信息!');
	            return;
	        }
	        //9:表示最大列，3：表示有效数据是第几行开始，第一行是1
	        //最后一个参数表示 导入成功后显示页
	        importXls("PT_BU_PCH_CONTRACT_PART_TMP",$('#dia-activeId').val(),3,3,"/jsp/dms/oem/part/purchase/purchaseOrder/importSuccess.jsp");
	    });
    
    	//关闭按钮
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	
    })
    
//*********************************** 回调方法  start **********************************************
	//查询最新的活动代码
    function diaNewActiveCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var activeCode = getNodeText(rows[0].getElementsByTagName("ACTIVE_CODE").item(0));
            $('#dia-activeCode').val(activeCode);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    
	//新增方法回调
    function diaInsertCallBack(res)
    {
    	var rows = res.getElementsByTagName("ROW");
    	try
    	{
    		var activeId = getNodeText(rows[0].getElementsByTagName("ACTIVE_ID").item(0));
    		var activeCode = getNodeText(rows[0].getElementsByTagName("ACTIVE_CODE").item(0));
            $('#dia-activeId').val(activeId);
            $('#dia-activeCode').val(activeCode);
            //$("#dia-contror").show();
    		$("#tab-programActiveList").insertResult(res,0);
    		
    		diaAction = 2;
    		if($("#tab-programActiveList_content").size()>0){
    			$("td input[type=radio]",$("#tab-programActiveList_content").find("tr").eq(0)).attr("checked",true);			
    		}else{
    			$("td input[type=radio]",$("#tab-programActiveList").find("tr").eq(0)).attr("checked",true);
    		}
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    
	//修改方法回调
    function diaUpdateCallBack(res)
    {
    	try
    	{
    		var selectedIndex = $("#tab-programActiveList").getSelectedIndex();
    		$("#tab-programActiveList").updateResult(res,selectedIndex);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    
    //批量删除回调方法
    function batchDeleteCallBack(res){
		$("#val3").val("");
		searchDealer();
	}
	
	//单个删除回调方法
    function  deleteCallBack(res)
    {
    	try
    	{
    		if($row) 
    			$("#dia-tab-proActiveDealerList").removeResult($row);
    	}catch(e)
    	{
    		alertMsg.error(e);
    		return false;
    	}
    	return true;
    }
    
    //提报按钮回调方法
    /**var $row;
    function reportCallBack(res){
    	toSearchContract();
    	$.pdialog.closeCurrent();
    }*/
//*********************************** 回调方法  end ************************************************    
 
//*********************************** 函数操作  start **********************************************   
    //活动方案渠道商单个删除
    var $row;
    function doDealerDelete(rowobj)
    {
    	$row = $(rowobj);
    	var activeDealersId = $(rowobj).attr("DTL_ID");
    	if(activeDealersId){
    		var url = diaUrl + "/deleteProActiveDealers.ajax?activeDealersId="+activeDealersId;
        	sendPost(url, "", "", deleteCallBack, "true");
    	}else{
    		alertMsg.warn("请先维护活动方案渠道商信息！");
			return false;
    	}
    }
    
    //查询活动方案的经销商信息
    function searchDealer(){
    	var activeId = $("#dia-activeId").val();
		var searchProActiveDealerUrl = diaUrl + "/proActiveDealerSearch.ajax?activeId="+activeId;
		var $f = $("#dia-fm-searchProActiveDealer");
		var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchProActiveDealerUrl, "", 1, sCondition, "dia-tab-proActiveDealerList");
    }
    
//*********************************** 函数操作  end ************************************************      
	
    function doCheck(obj){
    	var $obj = $(obj);
		var t = $obj.val();
		var d = new Date();
		if(t<=d){
			alertMsg.warn("有效期不能小于当前时间！");
			$obj.val("");
			return false;
		}else{
			return true;
		}

    	
    }
    function isCHN(obj)
    {
    	var $obj = $(obj);
        var reg =  /^[\u4e00-\u9fa5]{0,}$/;
        if(reg.test($obj.val()))
        {
            return true;
        }else
        {
        	alertMsg.warn("请输入汉字！");
			$obj.val("");
            return false;
        }
    }
    
    function blurSave(obj)
    {
    	var $obj = $(obj);
    	if(!$obj.val()){
    		alertMsg.warn("该处不能为空！");
    	}else{
    		try
    		{
    			while($obj.get(0).tagName != "TR")
	    			$obj = $obj.parent();
	    		//doPartSave($obj);
			}catch(e){
				alertMsg.error(e);
			}
			return true;
    	}
    	
    }
    function checkChn(obj){
    	var $obj = $(obj);
    	var reg =  /^[\u4e00-\u9fa5]{0,}$/;
    	if(reg.test($obj.val()))
        {
    		alertMsg.warn("配件编码不能为汉字！");
			$obj.val("");
            return false;
        }else
        {
        	return true;
        }
    }
    
    
    
	function doImportConfirm(a,b)
	{
	    var impUrl = diaSaveAction +'/importPart.ajax?CONTRACT_ID='+$('#dia-activeId').val();
	    sendPost(impUrl,"","",impPromPartCallback);
	}
	
	function impPromPartCallback(res){
	    try
	    {
	    	searchDealer();
	    }catch(e)
	    {
	        alertMsg.error(e.description);
	        return false;
	    }
	    return true;
	}
	
//列表复选
function doCheckbox(checkbox){  
	var $t = $(checkbox);
	var arr = [];
   	while($t.get(0).tagName != "TABLE"){
		$t = $t.parent();
	}
	 
	if($t.attr("id").indexOf("dia-tab-proActiveDealerList")==0){
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("DTL_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#dealerDeleteVal"));
	} 
	
	if($t.attr("id").indexOf("tab-searchDealerList")==0){   
		var $tr = $(checkbox).parent().parent().parent();
		var mxid = $(checkbox).val();
		arr.push($tr.attr("ORG_ID"));
		var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
		multiSelected($checkbox,arr,$("#dealerSelectVal"));
	} 
}
</script>