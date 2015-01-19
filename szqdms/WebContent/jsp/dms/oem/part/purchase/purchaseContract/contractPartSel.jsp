<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div>
    <div class="page">
        <div class="pageHeader">
            <form method="post" id="fm-searchPart">
                <div class="searchBar" align="left">
                    <table class="searchContent" id="tab-searchPart">
                        <!-- 定义查询条件 -->
                        <tr>
                            <td><label>配件图号：</label></td>
                            <td><input type="text" id="dia-PART_NO" name="dia-PART_NO" datasource="PART_NO"
                                       datatype="1,is_null,30" operation="like"/></td>
                            <td><label>配件名称：</label></td>
                            <td><input type="text" id="dia-PART_NAME" name="dia-PART_NAME" datasource="PART_NAME"
                                       datatype="1,is_null,30" operation="like"/></td>
                        </tr>
                    </table>
                    <div class="subBar">
                        <ul>
                            <li>
                                <div class="buttonActive">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-searchPart">查&nbsp;&nbsp;询</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-confirmPart">确&nbsp;&nbsp;定</button>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="button">
                                    <div class="buttonContent">
                                        <button type="button" id="btn-closePart">关&nbsp;&nbsp;闭</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
        <div class="pageContent">
            <div id="div-partList">
                <table style="display:none;width:100%;" id="tab-partList" name="tablist" ref="div-partList"
                       refQuery="tab-searchPart">
                    <thead>
                        <tr>
                            <th type="multi" name="XH" unique="PART_ID" style=""></th>
                            <th fieldname="PART_NAME">配件名称</th>
                            <th fieldname="PART_ID" style="display:none">配件ID</th>
                            <th fieldname="PART_CODE">配件代码</th>
                            <th fieldname="UNIT_PRICE" colwidth="60" refer="createInputBox" >采购价</th>
                            <th fieldname="REMARKS" colwidth="60" refer="createInput" >备注</th>
                           </tr>
                    </thead>
                </table>
            </div>
            <fieldset id="selectedPart" style="display: none">
                <legend align="left" >&nbsp;[已选定配件]</legend>
                <div>
                    <table style="width:100%;">
                        <tr>
                            <td>
                                <textarea style="width:80%;height:26px;display: none" id="val0" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val1" name="multivals" readOnly></textarea>
                                <textarea style="width:99%;height:50px;" id="val2" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val3" name="multivals" readOnly></textarea>
                                <textarea style="width:80%;height:26px;display: none" id="val4" name="multivals" readOnly></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <form id="fm-partInfo">
                <input type="hidden" id="contractId" name="contractId" datasource="CONTRACT_ID"/>
                <input type="hidden" id="partIds" name="partIds" datasource="PARTIDS"/>
                <input type="hidden" id="partCodes" name="partCodes" datasource="PARTCODES"/>
                <input type="hidden" id="partNames" name="partNames" datasource="PARTNAMES"/>
                <input type="hidden" id="unitPrices" name="unitPrices" datasource="UNITPIRCES"/>
                <input type="hidden" id="remarks" name="remarks" datasource="REMARKS"/>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    //弹出窗体
    var partSelWin = $("body").data("partSelWin");
    $(function () {
        $("#btn-closePart").click(function () {
            $.pdialog.close(partSelWin);
            return false;
        });
        $("#btn-searchPart").bind("click", function (event) {
            //查询配件URL
            var searchPartUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/PurchaseContractManageAction/searchPart.ajax?CONTRACT_ID="+$('#dia-CONTRACT_ID').val();
            var $f = $("#fm-searchPart");
            var sCondition = {};
            sCondition = $f.combined() || {};
            doFormSubmit($f, searchPartUrl, "btn-search", 1, sCondition, "tab-partList");
            $('#selectedPart').show();
        });
        //确定按钮响应
        $('#btn-confirmPart').bind('click',function(){
            //添加促销配件URL
            var addPartsUrl = "<%=request.getContextPath()%>/part/purchaseMng/purchaseContract/PurchaseContractManageAction/insertParts.ajax";
            var partIds = $('#val0').val();
            if(!partIds){
                alertMsg.warn('请选择配件!')
            }else{
                var promPrices = $('#val4').val();
                var tmpArr = promPrices.split(',');
                for (var i = 0;i<tmpArr.length;i++){
                    if(tmpArr[i]=='myNull'){
                        alertMsg.warn('请输入本次采购价!');
                        return;
                    }
                }
                $('#contractId').val($('#dia-CONTRACT_ID').val());//促销活动ID
                $('#partIds').val($('#val0').val());
                $('#partCodes').val($('#val1').val());
                $('#partNames').val($('#val2').val());
                $('#unitPrices').val($('#val3').val());
                $('#remarks').val($('#val4').val());
                

                //获取需要提交的form对象
                var $f = $("#fm-partInfo");
                //submitForm($f)方法：点击保存前，校验输入框内容是否符合输入规则
                if (submitForm($f) == false) return false;
                var sCondition = {};
                //将需要提交的内容拼接成json
                sCondition = $f.combined(1) || {};
                doNormalSubmit($f, addPartsUrl, "btn-confirmPart", sCondition, insertPromPartCallBack);
            }
        });
    });

    //新增促销配件回调
    function insertPromPartCallBack(){
        $.pdialog.close(partSelWin);
        //查询促销配件
        searchContractPart();
    }

    //将本次促销价字段渲染为文本框
    function createInputBox(obj)
    {
        return '<input type="text" name="UNIT_PRICE" onblur="checkIsAccount(this)"/>';
    }
    function createInput(obj)
    {
        return '<input type="text" name="REMARKS" onblur="checkIsNull(this)"/>';
    }

    //列表复选
    function doCheckbox(checkbox) {
        var $tr = $(checkbox).parent().parent().parent();
        var reg = /^(([1-9]\d*)|0)(\.\d{1,2})?$/;//金额正则(最多两位小数)
        var unitPrice = $tr.find("td").eq(5).find("input").val();
        if (!unitPrice || !reg.test(unitPrice)){//为空或者不是金额
        	unitPrice = 'myNull';
        }
        var remarks = $tr.find("td").eq(6).find("input").val();
        var arr = [];
        arr.push($tr.attr("PART_ID"));
        arr.push($tr.attr("PART_CODE"));
        arr.push($tr.attr("PART_NAME"));
        arr.push(unitPrice);
        arr.push(remarks);
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");
        multiSelected($checkbox, arr);
    }
    //校验是否是金额
    function checkIsAccount(obj){
        var reg =/^(([1-9]\d*)|0)(\.\d{1,2})?$/;//金额正则(最多两位小数)
        var $tr = $(obj).parent().parent().parent();//所选行
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");//复选框

        var unitPrice = $tr.find("td").eq(5).find("input").val();//输入的促销价
        if(!unitPrice||!reg.test(unitPrice))//为空或者不是金额
        {
            alertMsg.warn("请正确输入采购价！");
            $tr.find("td").eq(5).find("input").val("");
            $checkbox.attr("checked",false);
            doCheckbox($checkbox.get(0));
            return;
        }
        $checkbox.attr("checked",true);
        doCheckbox($checkbox.get(0));
    }
   function checkIsNull(obj){
        var $tr = $(obj).parent().parent().parent();//所选行
        var $checkbox = $tr.find("td").eq(1).find("input[type='checkbox']");//复选框
        $checkbox.attr("checked",true);
        doCheckbox($checkbox.get(0));
    } 
</script>