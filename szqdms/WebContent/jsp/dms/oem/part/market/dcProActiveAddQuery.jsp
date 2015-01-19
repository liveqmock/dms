<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.org.frameImpl.Constant" %>
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
							    	<input type="text" id="dia-activeCode" name="dia-activeCode" datasource="ACTIVE_CODE" datatype="0,is_null,30"/>
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
                    <div id="dia-page_proActiveDealerList" style="">
                        <table style="display:none;width:100%;" id="dia-tab-proActiveDealerList" name="tablist" ref="dia-page_proActiveDealerList" 
                        	refQuery="dia-tab-searchProActiveDealerList" multivals="dealerDeleteVal">
                            <thead>
	                           	<tr>
	                                <th type="single" name="XH" style="display:none"></th>
	                                <th fieldname="ACTIVE_CODE" >活动代码</th>
	                                <th fieldname="ACTIVE_NAME" >活动名称</th>
	                                <th fieldname="CODE" >渠道商代码</th>
	                                <th fieldname="ONAME" >渠道商名称</th>
	                                <th fieldname="CREATE_USER" >创建人</th>
	                                <th fieldname="CREATE_TIME" >创建时间</th>
	                                <th fieldname="STATUS" >有效标识</th>
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
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
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
        
        //修改页面赋值
        if (diaAction != "1") {
        	//$("#dia-contror").show();
            var selectedRows = $("#tab-programActiveList").getSelectedRows();
            setEditValue("fm-programActive", selectedRows[0].attr("rowdata"));
        } 
        
    	
    	//关闭按钮
    	$("button.close").click(function(){
    		$.pdialog.closeCurrent();
    		return false;
    	});
    	
    })
    
//*********************************** 函数操作  start **********************************************   
    //查询活动渠道商
    function searchDealer(){
    	var activeId = $("#dia-activeId").val();
		var searchProActiveDealerUrl = diaUrl + "/proActiveDealerSearch.ajax?activeId="+activeId;
		var $f = $("#dia-fm-searchProActiveDealer");
		var sCondition = {};
	    sCondition = $f.combined() || {};
	    doFormSubmit($f, searchProActiveDealerUrl, "", 1, sCondition, "dia-tab-proActiveDealerList");
    }
    
//*********************************** 函数操作  end ************************************************      
</script>