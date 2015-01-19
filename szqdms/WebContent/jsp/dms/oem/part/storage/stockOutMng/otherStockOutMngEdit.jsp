<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.common.QuerySet"%>
<%@page import="com.org.mvc.context.ActionContext"%>
<%@page import="com.org.mvc.context.RequestWrapper"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.org.framework.util.Pub"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.org.framework.Globals" %>
<%@ page import="com.org.framework.common.User" %>
<%@ page import="com.org.dms.common.DicConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script src="<%=request.getContextPath() %>/lib/plugins/importXls.js" type="text/javascript"></script>
<%
    //RequestWrapper requestWrapper = ActionContext.getContext().getRequest();
    //requestWrapper.getAttribute("bs");
    User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
    String account = user.getPersonName();
    String contentPath = request.getContextPath();
    String action = request.getParameter("action");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(Pub.getCurrentDate());
    if (action == null)
        action = "1";
%>
<div id="dia-layout">
    <div class="tabs" eventType="click" id="dia-tabs">
        <div class="tabsHeader">
            <div class="tabsHeaderContent">
                <ul>
                    <li><a href="javascript:void(0);"><span>出库单信息</span></a></li>
                    <li><a href="javascript:void(0);"><span>配件出库清单</span></a></li>
                </ul>
            </div>
        </div>
        <div class="tabsContent">
            <!--出库单信息 begin-->
            <div style="height:auto;overflow:hidden;">
                <form method="post" id="fm-outBillInfo" class="editForm">
                    <%--隐藏域查询条件--%>
                        <input type="hidden" id="dia-OUT_ID" name="dia-OUT_ID" datasource="OUT_ID"/>
                        <input type="hidden" id="dia-OUT_STATUS" name="dia-OUT_STATUS" datasource="OUT_STATUS" value="<%=DicConstant.CKDZT_01%>"/>
                        <input type="hidden" id="dia-OUT_TYPE" name="dia-OUT_TYPE" datasource="OUT_TYPE" value="<%=DicConstant.CKLX_05%>"/>
                        <input type="hidden" id="dia-PRINT_STATUS" name="dia-PRINT_STATUS" datasource="PRINT_STATUS" value="<%=DicConstant.DYZT_01%>"/>
                        <input type="hidden" id="dia-IF_IN_FLAG" name="dia-IF_IN_FLAG" datasource="IF_IN_FLAG" value="<%=DicConstant.SF_02%>"/>
                    <div align="left">
                        <fieldset>
                            <legend align="right"><a onclick="onTitleClick('tab-outBillInfo')">&nbsp;出库单信息编辑&gt;&gt;</a></legend>
                            <table class="editTable" id="tab-outBillInfo">
                                <tr>
                                    <td><label>出库单号：</label></td>
                                    <td>
                                        <input type="text" id="dia-OUT_NO" name="dia-OUT_NO" datasource="OUT_NO" readonly value="自动生成"/>
                                    </td>
                                    <td><label>出库仓库：</label></td>
                                    <td>
                                        <input type="hidden" id="dia-WAREHOUSE_ID" name="dia-WAREHOUSE_ID" datasource="WAREHOUSE_ID"/>
                                        <input type="hidden" id="dia-WAREHOUSE_NAME" name="dia-WAREHOUSE_NAME" datasource="WAREHOUSE_NAME"/>
                                        <input type="text" id="dia-WAREHOUSE_CODE" name="dia-WAREHOUSE_CODE" datasource="WAREHOUSE_CODE" kind="dic" src="" datatype="0,is_null,30"/>
                                    </td>
                                    <td><label>其他出库类型：</label></td>
                                    <td>
                                        <input type="text" id="dia-OTHER_OUT_TYPE" name="dia-OTHER_OUT_TYPE" datasource="OTHER_OUT_TYPE" kind="dic" src="QTCKCKLX" datatype="0,is_null,30"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label>备注：</label></td>
                                    <td colspan="6"><textarea class="" rows="3" id="dia-remarks" name="dia-remarks" datasource="REMARKS" style="width:500px;" datatype="1,is_null,500"></textarea></td>
                                </tr>
                            </table>
                        </fieldset>
                    </div>
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="btn-save">保&nbsp;&nbsp;存</button></div></div></li>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="button" name="btn-next">下&nbsp;一&nbsp;步</button></div></div></li>
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--出库单信息 end-->
            <!--配件出库清单 begin-->
            <div>
                <form method="post" id="fm-searchOutBillPart" class="editForm">
                    <table class="searchContent" id="tab-searchOutBillPart">
                    </table>
                </form>
                <div class="panelBar">
                    <ul class="toolBar">
                        <li class="line">line</li>
                        <li><a class="edit" href="javascript:void(0);" id="btn-addPart"><span>新增配件</span></a></li>
<!--                         <li class="line">line</li>
                        <li><a class="icon" href="javascript:void(0);" id="download"><span>下载导入模板</span></a></li>
                        <li class="line">line</li>
                        <li><a class="add" href="javascript:void(0);" id="dia-imp" ><span>导入配件</span></a></li> -->
                    </ul>
                </div>
                <div id="div-outBillPartList" style="">
                    <table style="display:none;width:100%;" id="tab-outBillPartList" name="tablist" layoutH="500px" multivals="div-selectedPart" ref="div-outBillPartList" refQuery="tab-searchOutBillPart">
                        <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style="" selected></th>
                            <th fieldname="PART_CODE" colwidth="120">配件代码</th>
                            <th fieldname="PART_NAME" colwidth="160" maxlength="40">配件名称</th>
                            <th fieldname="SUPPLIER_NAME" refer="ctrlShow" colwidth="80">供应商</th>
                            <th fieldname="AVAILABLE_AMOUNTS" colwidth="300"  maxlength="10000" refer="createInputBox3" >库位代码/总库存/锁定/可用/出库数量</th>
                            <th fieldname="REMARKS" colwidth="150" refer="createInputBox4">备注</th>
                            <th colwidth="150"  refer="createOpter"  >操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <form id="fm-partInfo" style="diaplay:none">
                 <input type="hidden" id="dia-positionIds" name="dia-positionIds" datasource="POSITIONIDS"/>
                 <input type="hidden" id="dia-outAmounts" name="dia-outAmounts" datasource="OUTAMOUNTS"/>
                 <input type="hidden" id="dia-yAmounts" name="dia-yAmounts" datasource="YAMOUNTS"/>
                  <input type="hidden" id="dia-remark" name="dia-remark" datasource="REMARK"/>
<!--                   <input type="hidden" id="flag" name="flag" datasource="FLAG"/> -->
                </form>
                <div class="formBar">
                    <ul>
                        <li><div class="button"><div class="buttonContent"><button type="button" name="btn-pre">上一步</button></div></div></li>
                       <!--   <li><div class="button"><div class="buttonContent"><button type="button" id="btn-saveDtl">保&nbsp;&nbsp;存</button></div></div></li>-->
                        <li id="other_out_end"><div class="button"><div class="buttonContent"><button type="button" id="btn-stockOut">出&nbsp;&nbsp;库</button></div></div></li>
<!--                         <li><div class="button"><div class="buttonContent"><button type="button" id="btn-stockOutEnd">出库完成</button></div></div></li> -->
                        <li><div class="button"><div class="buttonContent"><button class="close" type="button">关&nbsp;&nbsp;闭</button></div></div></li>
                    </ul>
                </div>
            </div>
            <!--配件出库清单 end-->
        </div>
        <div class="tabsFooter">
            <div class="tabsFooterContent"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

var diaSaveAction = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OtherStockOutMngAction";
var diaAction = "<%=action%>";
var flag = true;
$(function () {
    var iH = document.documentElement.clientHeight;
    $(".tabsContent").height(iH - 20);
    $("#div-outBillPartList").height(iH - 140);
    $("button[name='btn-pre']").bind("click",function(event){
        $("#dia-tabs").switchTab(parseInt($("#dia-tabs").attr("currentIndex"))-1);
    });
    $("button[name='btn-next']").bind("click",function(event){
        var $tabs = $("#dia-tabs");
        $tabs.switchTab(parseInt($tabs.attr("currentIndex"))+1);
        //跳转后实现方法
        switch(parseInt($tabs.attr("currentIndex")))
        {
            case 1:
                if (!$('#dia-OUT_ID').val()) {
                    alertMsg.warn('请先保存出库单信息!');
                    $tabs.switchTab(parseInt($tabs.attr("currentIndex"))-1);
                } else {
                    if(flag){
                        searchOutBillPart();
                    }
                    flag = false;
                }
                break;
            default:
                break;
        }
    });
    //保存基本信息按钮响应
 /*    $('#btn-save').bind('click', function () {
        //获取需要提交的form对象
        var $f = $("#fm-outBillInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        if (diaAction == 1)    //新增
        {
            var addUrl = diaSaveAction + "/insertOutBill.ajax";
            doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
        } else    //更新
        {
            var updateUrl = diaSaveAction + "/updateOutBill.ajax";
            doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack);
        }
    }); */
    $('#btn-save').bind('click', function () {
        //获取需要提交的form对象
        var $f = $("#fm-outBillInfo");
        //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
        if (submitForm($f) == false) return false;
        var sCondition = {};
        //将需要提交的内容拼接成json
        sCondition = $f.combined(1) || {};
        if (diaAction == 1)	//新增
        {
            var addUrl = diaSaveAction + "/insertOutBill.ajax?userAccount="+$("#PERSON_NAME").attr("code");
            doNormalSubmit($f, addUrl, "btn-save", sCondition, diaInsertCallBack);
        } else	//更新
        {
            var updateUrl = diaSaveAction + "/updateOutBill.ajax";
            doNormalSubmit($f, updateUrl, "btn-save", sCondition, diaUpdateCallBack1);
        }
    });

    if (diaAction != "1") {//修改
        var selectedRows = $("#tab-outBillList").getSelectedRows();
        setEditValue("fm-outBillInfo", selectedRows[0].attr("rowdata"));
    }else{
    	var userAccount = $("#PERSON_NAME").attr("code");
    	$("#dia-WAREHOUSE_CODE").attr("src","T#PT_BA_WAREHOUSE A,TM_USER B:A.WAREHOUSE_CODE:A.WAREHOUSE_NAME{A.WAREHOUSE_CODE,A.WAREHOUSE_NAME,A.WAREHOUSE_ID}:1=1 AND A.STATUS=<%=DicConstant.YXBS_01 %> AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> AND A.ORG_ID = B.ORG_ID AND B.ACCOUNT = '"+userAccount+"' ORDER BY WAREHOUSE_CODE");
    }
    $('#btn-addPart').bind('click',function(){
        
        openPartSelWin();
    });

    //出库按钮响应
    $('#btn-stockOut').bind('click',function(){
        // 保存操作
        doDiaUpdate();
    });

  //下载模板
    $('#download').bind('click', function () {
        var url = encodeURI("<%=request.getContextPath()%>/jsp/dms/common/download.jsp?filename=outPart.xls");
        window.location.href = url;
    });
    //导入
    $("#dia-imp").bind("click",function(){
        var warehouseId=$("#dia-WAREHOUSE_ID").val();
        var outId=$("#dia-OUT_ID").val();
        importXls("PT_BU_MOVE_OUT_PART_TMP",warehouseId+"@"+outId,5,3,"/jsp/dms/oem/part/storage/stockOutMng/importOutPartSuccess.jsp");
    });
});

    // 回调函数
    function getWarehouseCallBack(res) {
        var rows = res.getElementsByTagName("ROW");
        // 读取XML中的FLAG属性(FLAG:true有重复数据;)
        $("#dia-WAREHOUSE_ID").val(getNodeText(rows[0].getElementsByTagName("WAREHOUSE_ID").item(0)));
        $("#dia-WAREHOUSE_CODE").attr("code",getNodeText(rows[0].getElementsByTagName("WAREHOUSE_CODE").item(0)));
        $("#dia-WAREHOUSE_CODE").val(getNodeText(rows[0].getElementsByTagName("WAREHOUSE_NAME").item(0)));
        $("#dia-WAREHOUSE_NAME").val(getNodeText(rows[0].getElementsByTagName("WAREHOUSE_NAME").item(0)));
    }

function partStockOutCallBack(){
    var editWin = $("body").data("editWin");
    var selectedRows = $("#tab-outBillList").getSelectedRows();
    $("#tab-outBillList").removeResult(selectedRows[0]);
    $.pdialog.close(editWin);
}

function setDiaDefaultValue() {

}

//新增回调函数
function diaInsertCallBack(res) {
    try {
        var rows = res.getElementsByTagName("ROW");
        if (rows && rows.length > 0) {
            //获取新增出库单ID 并设置到隐藏域中
            var outId = getNodeText(rows[0].getElementsByTagName("OUT_ID").item(0));
            var outNo = getNodeText(rows[0].getElementsByTagName("OUT_NO").item(0));
            $('#dia-OUT_ID').val(outId);
            $('#dia-OUT_NO').val(outNo);
        }
        $("#tab-outBillList").insertResult(res,0);
        if($("#tab-outBillList_content").size()>0){
            $("td input[type=radio]",$("#tab-outBillList_content").find("tr").eq(0)).attr("checked",true);
        }else{
            $("td input[type=radio]",$("#tab-outBillList").find("tr").eq(0)).attr("checked",true);
        }

        diaAction = 2;
        $('#dia-WAREHOUSE_CODE').attr('readOnly','true');
        $('#dia-WAREHOUSE_CODE').attr('src','');
        $('#dia-WAREHOUSE_CODE').parent().find('span').hide();
        $('#dia-DES_WAREHOUSE_CODE').attr('readOnly','true');
        $('#dia-DES_WAREHOUSE_CODE').attr('src','');
        $('#dia-DES_WAREHOUSE_CODE').parent().find('span').hide();
        $('#dia-ORDER_NO').parent().find('img').hide();
        $('#dia-ORDER_NO').parent().find('span').hide();
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

//更新回调函数
function diaUpdateCallBack1() {
    try {
//         var selectedIndex = $("#tab-outBillList").getSelectedIndex();
//         $("#tab-outBillList").updateResult(res, selectedIndex);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}

	function diaUpdateCallBack2() {
	try {
		$("#other_out_end").hide();
	    search1();
	    $.pdialog.closeCurrent();
	//  var selectedIndex = $("#tab-outBillList").getSelectedIndex();
	//  $("#tab-outBillList").updateResult(res, selectedIndex);
	} catch (e) {
	 alertMsg.error(e);
	 return false;
	}
	return true;
	}
//查询出库单备件
function searchOutBillPart() {
    var searchOutBillPartUrl = "<%=request.getContextPath()%>/part/storageMng/stockOutMng/OtherStockOutMngAction/searchOutBillPart.ajax?OUT_ID=" + $('#dia-OUT_ID').val()+"&WAREHOUSE_ID="+$('#dia-WAREHOUSE_ID').val();
    var $f = $("#fm-searchOutBillPart");
    var sCondition = {};
    sCondition = $f.combined() || {};
    doFormSubmit($f, searchOutBillPartUrl, "", 1, sCondition, "tab-outBillPartList");
}
//弹出配件选择列表
function openPartSelWin(){
    var options = {max: true, mask: true, mixable: true, minable: true, resizable: false, drawable: true};
    $.pdialog.open(webApps + "/jsp/dms/oem/part/storage/stockOutMng/otherStockOutMngPartSel.jsp?account="+$("PERSON_NAME").attr("code"), "partSelWin", "配件信息查询", options);
}

//表选字典回调方法
function afterDicItemClick(id, $row, selIndex){
    //$row 行对象
    //selIndex 字典中第几行
    // 出库仓
    if(id == 'dia-WAREHOUSE_CODE'){
        // 修改目标仓字典
        $('#dia-WAREHOUSE_ID').val($row.attr('A.WAREHOUSE_ID'));
        $('#dia-WAREHOUSE_NAME').val($row.attr('A.WAREHOUSE_NAME'));
        
    }
    // 目标仓
    if(id == 'dia-DES_WAREHOUSE_CODE'){
        // 修改出库仓字典
        $("#dia-WAREHOUSE_CODE").attr("src","T#PT_BA_WAREHOUSE:WAREHOUSE_CODE:WAREHOUSE_NAME{WAREHOUSE_ID,WAREHOUSE_NAME}:1=1 AND STATUS=<%=DicConstant.YXBS_01 %> AND WAREHOUSE_ID IN (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE WHERE ORG_ID='<%=user.getOrgId()%>') AND OEM_COMPANY_ID = <%=user.getOemCompanyId()%> ORDER BY WAREHOUSE_CODE");
        $('#dia-DES_WAREHOUSE_ID').val($row.attr('WAREHOUSE_ID'));
        $('#dia-DES_WAREHOUSE_NAME').val($row.attr('WAREHOUSE_NAME'));
        $('#dia-DES_WAREHOUSE_TYPE').val($row.attr('WAREHOUSE_TYPE'));
    }
    if(id.indexOf('WA_') == 0){
        var $obj = $('#'+id);
        var $tr = $obj.parent().parent().parent();
        var partId = $tr.attr('PART_ID');
        var supplierId = $tr.attr('SUPPLIER_ID');
        var areaId = $row.attr('A.AREA_ID');
        var dicSql = 'T#PT_BA_WAREHOUSE_PART_RL A,PT_BA_WAREHOUSE_POSITION B,PT_BU_STOCK_DTL C:A.POSITION_CODE:A.POSITION_NAME{A.POSITION_ID,A.POSITION_NAME,C.AVAILABLE_AMOUNT}:1=1';
        dicSql+=' AND A.POSITION_ID = B.POSITION_ID';
        dicSql+=' AND A.POSITION_ID = C.POSITION_ID';
        dicSql+=' AND B.AREA_ID = '+areaId;
        dicSql+=' AND C.SUPPLIER_ID = '+supplierId;
        dicSql+=' AND A.PART_ID = C.PART_ID';
        dicSql+=' AND A.PART_ID = '+partId+' ORDER BY B.POSITION_CODE';
        var $wp = $('#WP_'+id.substr(3));
        $wp.attr('code','');
        $wp.attr('value','');
        $wp.attr('POSITION_ID','');
        $wp.attr('src',dicSql);
        $obj.attr('AREA_ID',areaId);
    }
    if(id.indexOf('WP_')==0){
        var $obj = $('#'+id);
        var positionId = $row.attr('A.POSITION_ID');
        var availableAmount = $row.attr('C.AVAILABLE_AMOUNT');
        $obj.parent().parent().next('td').text(availableAmount);
        $obj.attr('POSITION_ID',positionId);
    }

    return true;
}

//将出库数量字段渲染为文本框
function createInputBox3(obj)
{
	var $tr = $(obj).parent();
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    $checkbox.attr("checked",true);
    var $obj = $(obj);
    var $tr = $obj.parent();
    var partId = $tr.attr('PART_ID');
    var outAmounts = $tr.attr('OUT_AMOUNTS');
    var amounts = $tr.attr('AMOUNTS');
    var occupyAmounts = $tr.attr('OCCUPY_AMOUNTS');
    var avaAmountS = $tr.attr('AVAILABLE_AMOUNTS');
    //设置库位选择
    var pIds = $tr.attr("POSITION_IDS");
    var pCodes = $tr.attr("POSITION_CODES");
    var pNames = $tr.attr("POSITION_NAMES");
    var s = "";
    if(pIds){
        var pId = pIds.split(",");
        var pName = pNames.split(",");
        var pCode = pCodes.split(",");
        var outAmount = outAmounts.split(",");//出库数量集合
        var amount = amounts.split(",");//库存总数集合
        var occupyAmount = occupyAmounts.split(",");//锁定库存数量集合
        var avaAmount = avaAmountS.split(",");//可用的数量集合
      
        for(var i=0;i<pId.length;i++)
        {
           var turnAmount=parseInt(outAmount[i],10)+parseInt(avaAmount[i],10);//真实的可用库存
            s += "<div  name='WPS' style='line-height:23px;width:99%;border-bottom:1px solid red'>";
            s += "<span id='WP_'"+partId+"_"+pId[i]+" name='WAREHOUSE_POSITION_CODE' wpId='"+pId[i]+"' wpCode='"+pCode[i]+"' wpName='"+pName[i]+"' style='float:left;padding-top:3px;margin-right:2px;'>库位:"+pCode[i]+"&nbsp;&nbsp;总库存:&nbsp;"+amount+"&nbsp;锁定:&nbsp;"+occupyAmount+"&nbsp;可用:&nbsp;"+avaAmount+"</span>&nbsp;&nbsp;";
            s += "本次出库:&nbsp;<input id='WPS_'"+partId+"_"+pId[i]+"'  onblur='doOutBlur(this);' wpAmount='"+turnAmount+"' outAmount='"+outAmount[i]+"' name='WAREHOUSE_POSITION_CODE_COUNT' value='"+outAmount[i]+"' style='width:30px;padding-top:-5px;' /></div>";
        }
        $tr.height(28*(pId.length));
        $tr.css("line-height",26*(pId.length));
    }
    return "<div>"+s+"</div>";
}

//将备注字段渲染为文本框
function createInputBox4(obj)
{
    return "<input type='text' name='OUT_AMOUNT' maxlength='1000'  value='"+obj.text()+"' style='width:150px;padding-top:-5px;'/>";
}
function doOutBlur(obj)
{
    var $obj = $(obj);
    var $input = $(obj);
    var $tr = $obj;
    while($tr.get(0).tagName != "TR")
        $tr = $tr.parent();
    var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
    if($input.val() == "") {//为空直接返回
        alertMsg.warn("请输入入库数量.");
        $checkbox.attr("checked",false);
        return false;
    }
    if($input.val().length > 0)
    {
        if(!isNumV($input.val()))
        {
        	$checkbox.attr("checked",false);
            alertMsg.warn("请正确输入的入库数量.");
            return false;
        }
    }
    //校验出库数量<=可用数量
    var avaCount = $input.attr("wpAmount");
       if(parseInt($input.val(),10) > parseInt(avaCount,10))
       {
           $checkbox.attr("checked",false);
           alertMsg.warn("出库数量不能大于可用库存.");
           return false;
       }
       if($input.val() > 0){
        $checkbox.attr("checked",true);
    } else {
        //判断当前行是否库位数量都是空，如果是空，则取消checkbox选中
        var $div = $tr.find("div[name='WPS']");
        var f = true;
        $("input",$div).each(function(){
            if($(this).val()) {
                f = false;
                return false;
            }
        });
        if(f == true) {
            $checkbox.attr("checked",false);
        }
    }
}
function toAppendStr(obj){
    var $tr =$(obj).parent();
    return $tr.attr("MIN_PACK")+"/"+$tr.attr("MIN_UNIT_sv");
}

function ctrlShow(obj){
    var $tr =$(obj).parent();
    if($tr.attr('SUPPLIER_CODE')=='9XXXXXX'){
        return "";
    }else{
        return $(obj).text();
    }
}
//创建删除和保存按钮
function createOpter(obj)
{
  obj.html("<A class=op title=[删除] onclick='doDiaDelete(this.parentElement.parentElement)' href='javascript:void(0);'>[删除]</A>");
       
}
//删除操作
var $diaRow;
function doDiaDelete(rowobj) {
    $diaRow = $(rowobj);
       var url = diaSaveAction+"/deleteOutBillPart.ajax" + "?partId=" + $(rowobj).attr("PART_ID")+"&outId="+$('#dia-OUT_ID').val()+"&supplierId="+ $(rowobj).attr("SUPPLIER_ID");
    sendPost(url, "", "", diaDeleteCallBack, "true");
}
//更新操作
function doDiaUpdate()
{
    $("#dia-positionIds").val("");
    $("#dia-outAmounts").val("");
    $("#dia-yAmounts").val("");
    $("#dia-remark").val("");
    var outAmounts="";//当前入库数量
    var positionIds="";//当前库位ID
    var yAmounts="";//保存之前的入库数量
            var flag=false;
            var positionIds1 = "";
            var outAmounts1 = "";
            var yAmounts1 = "";
            var partId ="";
            var supplierId = "";
            var remark = "";
    $("tr",$("#tab-outBillPartList")).each(function(){
        var $tr = $(this);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']:first");
        if($checkbox.is(":checked")) {
        	
            partId+=partId.length?"@"+$tr.attr("PART_ID"):$tr.attr("PART_ID");
            supplierId+=supplierId.length?"@"+$tr.attr("SUPPLIER_ID"):$tr.attr("SUPPLIER_ID");
            var id_remark = $tr.find("td").eq(6).find("input:first").val().length?$tr.find("td").eq(6).find("input:first").val():"myNull";
            remark+=remark.length?"@"+id_remark:id_remark;
            
            
            positionIds += positionIds1.length?"@":"";
            outAmounts += outAmounts1.length?"@":"";
            yAmounts += yAmounts1.length?"@":"";
            
            $tr.find("div[name='WPS']").each(function() {
                var $span = $(this).find("span:first");
                var $input = $(this).find("input:first");
                positionIds1 = "";
                outAmounts1 = "";
                yAmounts1 = "";
                //校验出库数量<=可用数量
                var avaCount = $input.attr("wpAmount");
                var yAmount = $input.attr("outAmount");
                   if(parseInt($input.val(),10) > parseInt(avaCount,10))
                   {
                       alertMsg.warn("出库数量不能大于可用库存.");
                       flag=true;
                       return false;
                   }
                       positionIds1 += positionIds1.length?"," + $span.attr("wpId"):$span.attr("wpId");
                       outAmounts1 += outAmounts1.length?"," + $input.val():$input.val();
                       yAmounts1 += yAmounts1.length?"," + yAmount:yAmount;
            });
            positionIds+= positionIds1;
            outAmounts+= outAmounts1;
            yAmounts+= yAmounts1;
        }
        });
    if(flag)//当存在错误时不执行保存方法
    {
        return false;
    }else
    {
        if(positionIds=="")
        {
            alertMsg.warn("出库数据未发生变化,无需保存.");
            return false;
        }else
        {
            $("#dia-positionIds").val(positionIds);
            $("#dia-outAmounts").val(outAmounts);
            $("#dia-yAmounts").val(yAmounts);
            $("#dia-remark").val(remark);
            var url = diaSaveAction+"/updateOutBillPart.ajax" + "?partId=" + partId+"&outId="+$('#dia-OUT_ID').val()+"&supplierId="+ supplierId;
            //获取需要提交的form对象
            var $f = $("#fm-partInfo");
            //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
            if (submitForm($f) == false) return false;
            var sCondition = {};
            //将需要提交的内容拼接成json
            sCondition = $f.combined(1) || {};
            doNormalSubmit($f, url, "btn-saveDtl", sCondition, diaUpdateCallBack2);
        }
    }
    
}
//删除回调
function diaDeleteCallBack(res) {
    try {
        if ($diaRow)
            $("#tab-outBillPartList").removeResult($diaRow);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
function diaUpdateCallBack(res)
{
    var partStockOutUrl = diaSaveAction+'/partStockOutInEdit.ajax?outId='+$('#dia-OUT_ID').val()+"&warehouseId="+$('#dia-WAREHOUSE_ID').val()+"&warehouseType="+$('#dia-DES_WAREHOUSE_TYPE').val();
    //获取需要提交的form对象
    var $f = $("#fm-partInfo");
    //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
    if (submitForm($f) == false) return false;
    var sCondition = {};
    //将需要提交的内容拼接成json
    sCondition = $f.combined(1) || {};
    doNormalSubmit($f, partStockOutUrl, "btn-stockOut", sCondition, partStockOutCallBack,"false");
}
function isNumV(val)
{
    var reg = /^[1-9]\d*$/;
    if(reg.test(val))
    {
        return true;
    }else
    {
        return false;
    }
}
function createOpter(obj)
{
  obj.html("<A class=op title=[删除] onclick='doDiaDelete(this.parentElement.parentElement)' href='javascript:void(0);'>[删除]</A>");
       
}
var $diaRow;
function doDiaDelete(rowobj) {
    $diaRow = $(rowobj);
       var url = diaSaveAction+"/deleteOutBillPart.ajax" + "?partId=" + $(rowobj).attr("PART_ID")+"&outId="+$('#dia-OUT_ID').val()+"&supplierId="+ $(rowobj).attr("SUPPLIER_ID");
    sendPost(url, "", "", diaDeleteCallBack, "true");
}
function diaDeleteCallBack(res) {
    try {
        if ($diaRow)
            $("#tab-outBillPartList").removeResult($diaRow);
    } catch (e) {
        alertMsg.error(e);
        return false;
    }
    return true;
}
</script>