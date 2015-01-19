package com.org.dms.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.org.dms.action.part.basicInfoMng.ChannelSafeStockAction;
import com.org.dms.action.part.basicInfoMng.PtBaInfoAction;
import com.org.dms.action.part.basicInfoMng.PtBaPictureAction;
import com.org.dms.action.part.basicInfoMng.PtBuInventoryDtlAction;
import com.org.dms.action.part.basicInfoMng.SafeStockAction;
import com.org.dms.action.part.basicInfoMng.TransAddressAction;
import com.org.dms.action.part.storageMng.stockOutMng.MoveStockOutMngAction;
import com.org.dms.action.service.basicinfomng.ClaimCodeMngAction;
import com.org.dms.action.service.basicinfomng.DealerStarMngAction;
import com.org.dms.action.service.basicinfomng.ExcitationCoefficientMngAction;
import com.org.dms.action.service.basicinfomng.FaultTasktimeMngAction;
import com.org.dms.action.service.basicinfomng.RulePartMngAction;
import com.org.dms.action.service.basicinfomng.ServicePriceAction;
import com.org.dms.action.service.basicinfomng.TaskAmountMngAction;
import com.org.dms.action.service.basicinfomng.TravelCostMngAction;
import com.org.dms.action.service.financeMng.PaymentAction;
import com.org.dms.action.service.oldpartMng.OldPartReturnAction;
import com.org.dms.dao.part.basicInfoMng.PartPriceCheckDao;
import com.org.dms.dao.part.basicInfoMng.PartSupplierRlCheckDao;
import com.org.dms.dao.part.basicInfoMng.PtBaInfoCheckDao;
import com.org.dms.dao.part.basicInfoMng.PtBuInventoryImpCheckDao;
import com.org.dms.dao.part.basicInfoMng.WarehouseKeeperCheckDao;
import com.org.dms.dao.part.financeMng.cashAccountMng.CashAccountImportCheckDao;
import com.org.dms.dao.part.financeMng.settlement.SupplierSettlementCheckDao;
import com.org.dms.dao.part.partClaimMng.ClaimCyclSetImportCheckDao;
import com.org.dms.dao.part.planMng.forecast.ForecastImportCheckDao;
import com.org.dms.dao.part.purchaseMng.purchaseContract.ContractCheckDao;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderImpCheckDao;
import com.org.dms.dao.part.purchaseMng.purchaseReturn.PurchaseReturnImportCheckDao;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderImportCheckDao;
import com.org.dms.dao.part.salesMng.returnPurchaseMng.ReturnPurchaseImportCheckMngDao;
import com.org.dms.dao.part.storageMng.boxUpMng.BoxUpImportCheckDao;
import com.org.dms.dao.service.basicinfomng.OldPartManageCheckDao;
import com.org.dms.dao.service.financeMng.StatementAdjustImpCheckDao;
import com.org.dms.dao.service.oldpartMng.OldPartImportCheckDao;
import com.org.dms.dao.service.oldpartMng.OldPartPackImpCheckDao;
import com.org.dms.dao.service.oldpartMng.OldPartPackPJGLDao;
import com.org.dms.dao.service.serviceactivity.MainVehicleSetImportCheckDao;
import com.org.dms.dao.service.serviceactivity.ServiceVinSetImportCheckDao;
import com.org.framework.Globals;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.mvc.context.ActionContext;

/**
 * @Title: 校验临时表的数据
 * @Description:
 * @Copyright: Copyright (c) 2012
 * @Date: 2012-5-23
 * @author andy
 * @mail andy.ten@tom.com
 */
public class ImportFileCheckTmp {
    /**
     * 业务类型
     */
    public static final String PT_BU_PROMOTION_PART_TMP = "checkPT_BU_PROMOTION_PART_TMP"; 
    public static final String SE_BU_RETURNORDER_DTL_TMP = "checkSE_BU_RETURNORDER_DTL_TMP";//旧件回运导入 
    public static final String SE_BU_OLDPART_STORAGE_TMP = "checkSE_BU_OLDPART_STORAGE_TMP"; //旧件入库导入
    public static final String PT_BU_PCH_ORDER_PART_TMP = "checkPT_BU_PCH_ORDER_PART_TMP"; //采购订单配件导入
    public static final String ARMY_PT_BU_PCH_ORDER_PART_TMP = "checkARMY_PT_BU_PCH_ORDER_PART_TMP"; //军品采购订单配件导入
    public static final String PT_BU_PCH_RETURN_PART_TMP = "checkPT_BU_PCH_RETURN_PART_TMP";//采购退货单导入
    public static final String PT_BU_PCH_CONT_PART_TMP = "checkPT_BU_PCH_CONT_PART_TMP";//采购退货单导入
    public static final String PT_BU_FORCAST_DTL_TMP = "checkPT_BU_FORCAST_DTL_TMP"; //预测配件明细导入
    public static final String PT_BU_RETURN_APPLY_DTL_TMP = "checkPT_BU_RETURN_APPLY_DTL_TMP"; //退件申请明细导入
    public static final String PT_BU_BOX_UP_TMP = "checkPT_BU_BOX_UP_TMP"; //装箱清单导入
    public static final String PT_BU_VEL_BOX_RL_TMP = "checkPT_BU_VEL_BOX_RL_TMP"; //装箱清单导入
    public static final String SE_BU_CLAIM_SETTLE_TMP = "checkSE_BU_CLAIM_SETTLE_TMP"; //结算单批量调整导入
    public static final String SE_BU_CLAIM_SETTLE_TMP_OTHER = "checkSE_BU_CLAIM_SETTLE_TMP"; //结算单批量调整导入-其他费用批量导入(共用同一个校验)
    public static final String SE_BU_CLAIM_SETTLE_TMP_POLICY = "checkSE_BU_CLAIM_SETTLE_TMP"; //结算单批量调整导入-政策支持批量导入(共用同一个校验)
    public static final String PT_BA_CLAIM_CYCLE_SET_TMP = "checkPT_BA_CLAIM_CYCLE_SET_TMP"; //配件三包期设置导入
    public static final String SE_BU_ACTIVITY_VEHICLE_TMP = "checkSE_BU_ACTIVITY_VEHICLE_TMP"; //服务活动VIN导入
    public static final String PT_BA_STOCK_SAFESTOCKS_TMP = "checkPT_BA_STOCK_SAFESTOCKS_TMP"; //安全库存配件导入
    public static final String PT_BA_CHANNEL_SAFESTOCK_TMP = "checkPT_BA_CHANNEL_SAFESTOCK_TMP"; //渠道安全库存配件导入
    public static final String PT_BA_TRANSPORT_ADDRESS_TMP = "checkPT_BA_TRANSPORT_ADDRESS_TMP"; //渠道发运地址导入
    public static final String SE_BA_RULE_PART_TMP = "checkSE_BA_RULE_PART_TMP"; //三包规则配件导入
    public static final String PT_BU_INVENTORY_DTL_TMP = "checkPT_BU_INVENTORY_DTL_TMP"; 		//库存盘点明细信息导入
    public static final String PT_BA_INFO_TMP = "checkPT_BA_INFO_TMP"; 							//配件档案明细信息导入    
    public static final String PT_BA_PRICE_TMP = "checkPT_BA_PRICE_TMP"; 						//配件价格明细信息导入    
    public static final String PT_BA_PCH_TMP = "checkPT_BA_PCH_TMP"; 							//配件采购价格明细信息导入    
    public static final String PT_BU_INVENTORY_TMP = "checkPT_BU_INVENTORY_TMP"; 				//配件库存盘点信息导入   
    public static final String PT_BA_PICTURE_TMP = "checkPT_BA_PICTURE_TMP"; 					//配件图片信息导入
    public static final String SE_BA_CL_PRICE_TMP = "checkSE_BA_CL_PRICE_TMP"; 					//服务索赔价格信息导入 
    public static final String SE_BA_RE_PRICE_TMP = "checkSE_BA_RE_PRICE_TMP"; 					//服务追偿价格信息导入 
    public static final String PT_BU_SALE_ORDER_DTL_TMP = "checkPT_BU_SALE_ORDER_DTL_TMP"; //配件订单明细导入
    public static final String PT_BU_CLAIM_APPLY_TMP = "check_PT_BU_CLAIM_APPLY_TMP"; // 配件管理-旧件回运装箱导入
    public static final String SE_BU_RETURN_ORDER_TMP = "checkSE_BU_RETURN_ORDER_TMP"; //终审配件导入
    public static final String MAIN_VEHICLE_TMP = "checkMAIN_VEHICLE_TMP";//车辆导入
    public static final String SE_BA_TRAVEL_COST_TMP = "checkSE_BA_TRAVEL_COST_TMP"; //外出费用导入
    public static final String SE_BA_CLAIM_CODE_TMP = "checkSE_BA_CLAIM_CODE_TMP";//工时单价导入
    public static final String SE_BA_TASK_AMOUNT_TMP="checkSE_BA_TASK_AMOUNT_TMP";//工时定额导入
    public static final String SE_BA_FAULT_TASKTIME_TMP="checkSE_BA_FAULT_TASKTIME_TMP";//故障模式与工时定额关系
    public static final String SE_BA_EXCITATION_TMP="checkSE_BA_EXCITATION_TMP";//激励系数导入
    public static final String SE_BU_CLAIM_SETTLE_MODIFY_TMP="checkSE_BU_CLAIM_SETTLE_MODIFY_TMP";//付款结果更新导入
    public static final String SE_BU_RETURNORDER_PACK_FOCUS = "checkSE_BU_RETURNORDER_PACK_FOCUS";//集中点装箱校验
    public static final String PT_BU_MOVE_OUT_PART_TMP = "checkPT_BU_MOVE_OUT_PART_TMP";//移库出库配件明细
    public static final String MAIN_DEALER_STAR_TMP = "checkMAIN_DEALER_STAR_TMP"; //服务商星级导入
    public static final String PT_BU_ACCOUNT_TMP = "checkPT_BU_ACCOUNT_TMP"; //配件账户(材料费，返利)导入
    public static final String PT_BU_SUP_INVOICE_SUMMARY_TMP = "checkPT_BU_SUP_INVOICE_SUMMARY_TMP"; //供应商结算导入
    public static final String SE_BA_OLDPARTMANAGE_TMP = "checkSE_BA_OLDPARTMANAGE_TMP"; //旧件管理费系数导入
    public static final String PT_BA_PART_SUPPLIER_RL_TMP = "checkPT_BA_PART_SUPPLIER_RL_TMP"; //配件与供应商关系
    public static final String PT_BU_ASALE_ORDER_DTL_TMP = "checkPT_BU_ASALE_ORDER_DTL_TMP"; //军品销售订单导入
    public static final String PT_BA_ARMY_PRICE_TMP = "checkPT_BA_ARMY_PRICE_TMP";	//配件军品价格信息导入    
    public static final String PT_BU_PART_CONT_CHK_TMP = "checkPT_BU_PART_CONT_CHK_TMP";	//待维护配件合同校验  
    public static final String PT_BA_WAREHOUSE_KEEPER_TMP = "checkPT_BA_WAREHOUSE_KEEPER_TMP";	//库管员属性导入
    
    
    /**
     * 配件三包期设置
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_CLAIM_CYCLE_SET_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            ClaimCyclSetImportCheckDao dao = new ClaimCyclSetImportCheckDao();
            // 配件代码是否存在
            QuerySet querySet = dao.partCheck(user, factory);
            // 三包月份和延保月份(是否为数字验证)
            QuerySet querySet2 = dao.partCountCheck(user, factory);
    
            factory.getConnection().commit();

            if (querySet.getRowCount() >0) {
                // 配件是否存在验证
                for (int k=0;k<querySet.getRowCount();k++) {
                    // 配件代码
                    ExcelErrors excelErrors = new ExcelErrors();
                    String partCode = querySet.getString(k+1, "PART_CODE");
                    int tmpNo = Integer.valueOf(querySet.getString(k+1, "TMP_NO"));
                    excelErrors.setRowNum(tmpNo);
                    excelErrors.setErrorDesc("配件代码:"+partCode+"不存在！");
                    errorList.add(excelErrors);
                }
            }
    
            for (int i=0;i<querySet2.getRowCount();i++) {
                // 配件代码
                String partCode = querySet2.getString(i+1, "PART_CODE");
                // 三包月份
                String claim = querySet2.getString(i+1, "CLAIM_MONTH");
                String extension = querySet2.getString(i+1, "EXTENSION_MONTH");
                Pattern pattern = Pattern.compile("[0-9]*");  
                Matcher matcher = pattern.matcher(claim+extension);
                ExcelErrors excelErrors = new ExcelErrors();
                if (matcher.matches() == false) {
                    int tmpNo = Integer.valueOf(querySet2.getString(i+1, "TMP_NO"));
                    excelErrors.setRowNum(tmpNo);
                    excelErrors.setErrorDesc("配件代码:"+partCode+"的三包月份或延保月份错误！");
                    errorList.add(excelErrors);
                }
            }
    
            //校验临时表数据
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }

    /**
     * 预测配件明细
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_FORCAST_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            ForecastImportCheckDao dao = new ForecastImportCheckDao();
            // 配件重复验证
            QuerySet querySet1 = dao.partRepeatCheck(bParams,user,factory);
            // 配件是否存在验证
            QuerySet querySet2 = dao.partCheck(user,factory);
            // 配件数量验证
            QuerySet querySet3 = dao.partCountCheck(user,factory);
    
            factory.getConnection().commit();

            if (querySet1.getRowCount() > 0) {
                // 配件重复验证
                for (int i=0;i<querySet1.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String partCode = querySet1.getString(i+1, "PART_CODE");
                    // 行号
                    String tmpNo = querySet1.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件代码:"+partCode+"已添加！");
                    errorList.add(excelErrors);
                }
            }

            if (querySet2.getRowCount() > 0) {
                // 配件是否存在验证
                for (int k=0;k<querySet2.getRowCount();k++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String partCode = querySet2.getString(k+1, "PART_CODE");
                    // 行号
                    String tmpNo = querySet2.getString(k+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件代码:"+partCode+"不存在！");
                    errorList.add(excelErrors);
                }
            }

            if (querySet3.getRowCount() > 0) {
                for (int j=0;j<querySet3.getRowCount();j++) {
                    // 配件代码
                    String partCode = querySet3.getString(j+1, "PART_CODE");
                    // 配件数量
                    String partCount = querySet3.getString(j+1, "PART_COUNT");
                    // 行号
                    String tmpNo = querySet3.getString(j+1, "TMP_NO");
                    Pattern pattern = Pattern.compile("[0-9]*");  
                    Matcher matcher = pattern.matcher(partCount);  
                    if (matcher.matches() == false) {
                        ExcelErrors excelErrors = new ExcelErrors();
                        excelErrors.setRowNum(Integer.valueOf(tmpNo));
                        excelErrors.setErrorDesc("配件代码:"+partCode+"的配件数量错误！");
                        errorList.add(excelErrors);
                    }
                }
            }

            //校验临时表数据
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    
    
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_PART_SUPPLIER_RL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            PartSupplierRlCheckDao dao = new PartSupplierRlCheckDao();
            // 配件是否存在
            QuerySet querySet1 = dao.checkPart(user,factory);
            // 供应商是否存在
            QuerySet querySet2 = dao.checkSupplier(user,factory);
            //供货周期是否存在
            QuerySet querySet3 = dao.checkApplyCycle(user,factory);
            //供货关系是否重复
            QuerySet querySet4 = dao.checkUnique(user,factory);
            //供应商是否在合同内
            QuerySet querySet5 = dao.checkCont(user,factory);
            //配件是否在合同内
            QuerySet querySet6 = dao.checkContDtl(user,factory);
            
            QuerySet querySet7 = dao.checkOne(user, factory);
            factory.getConnection().commit();
            if (querySet1.getRowCount() > 0) {
                // 配件重复验证
                for (int i=0;i<querySet1.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String partCode = querySet1.getString(i+1, "PART_CODE");
                    // 行号
                    String tmpNo = querySet1.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件代码:"+partCode+"不存在！");
                    errorList.add(excelErrors);
                }
            }
            if (querySet2.getRowCount() > 0) {
                // 配件重复验证
                for (int i=0;i<querySet2.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String supplierCode = querySet2.getString(i+1, "SUPPLIER_CODE");
                    // 行号
                    String tmpNo = querySet2.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("供应商代码:"+supplierCode+"不存在！");
                    errorList.add(excelErrors);
                }
            }
            if (querySet3.getRowCount() > 0) {
                for (int i=0;i<querySet3.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    String partCode = querySet3.getString(i+1, "PART_CODE");
//                    String applyCycle = querySet3.getString(i+1, "APPLY_CYCLE");
                    String tmpNo = querySet3.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件"+partCode+"的供货周期不存在！");
                    errorList.add(excelErrors);
                }
            }
            if (querySet4.getRowCount() > 0) {
                for (int i=0;i<querySet4.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    String supplierCode = querySet4.getString(i+1, "SUPPLIER_CODE");
                    String partCode = querySet4.getString(i+1, "PART_CODE");
                    String tmpNo = querySet4.getString(i+1, "TMP_NO");
                    String status = querySet4.getString(i+1, "PART_STATUS");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件"+partCode+"与供应商代码:"+supplierCode+"的已存在状态为"+status+"的供货关系");
                    errorList.add(excelErrors);
                }
            }
            if (querySet5.getRowCount() > 0) {
                for (int i=0;i<querySet5.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    String supplierCode = querySet5.getString(i+1, "SUPPLIER_CODE");
                    String tmpNo = querySet5.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("供应商"+supplierCode+"没有对应的采购合同");
                    errorList.add(excelErrors);
                }
            }
            if (querySet6.getRowCount() > 0) {
                for (int i=0;i<querySet6.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    String supplierCode = querySet6.getString(i+1, "SUPPLIER_CODE");
                    String partCode = querySet6.getString(i+1, "PART_CODE");
                    String tmpNo = querySet6.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("供应商"+supplierCode+"与"+partCode+"没有对应的采购合同");
                    errorList.add(excelErrors);
                }
            }
            if (querySet7.getRowCount() > 0) {
                for (int i=0;i<querySet7.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    String partCode = querySet7.getString(i+1, "PART_CODE");
                    String tmpNo = querySet7.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc(partCode+"在导入模板中重复");
                    errorList.add(excelErrors);
                }
            }

            //校验临时表数据
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_PCH_CONT_PART_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            ContractCheckDao dao = new ContractCheckDao();
            // 配件重复验证
            QuerySet querySet1 = dao.checkUnique(bParams,user,factory);
            // 采购价 (是否是数字),最小包装单位(是否是汉字)
            QuerySet querySet2 = dao.check(user,factory);
            QuerySet getSup = dao.getSup(bParams,factory);
            String supplierCode=  getSup.getString(1, "SUPPLIER_CODE");
            QuerySet queryset3 = dao.checkOther(user,supplierCode, factory);
    
            factory.getConnection().commit();

            if (querySet1.getRowCount() > 0) {
                // 配件重复验证
                for (int i=0;i<querySet1.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String partCode = querySet1.getString(i+1, "PART_CODE");
                    // 行号
                    String tmpNo = querySet1.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件代码:"+partCode+"重复！");
                    errorList.add(excelErrors);
                }
            }

            if (querySet2.getRowCount() > 0) {
                // 配件是否存在验证
                for (int k=0;k<querySet2.getRowCount();k++) {
                    // 采购价格
                    String unitPrice = querySet2.getString(k+1, "UNIT_PRICE");
                    // 最小包装单位
                    String minPackUnit = querySet2.getString(k+1, "MIN_PACK_UNIT");
                    // 采购价格验证
                    Pattern pattern = Pattern.compile("0|-?^[0-9]+(.[0-9]{1,2})?$");  
                    Matcher matcher = pattern.matcher(unitPrice);
                    
                    // 最小包装单位验证
                    Pattern pattern1 = Pattern.compile("^[\u4e00-\u9fa5]{0,}$");  
                    Matcher matcher1 = pattern1.matcher(minPackUnit);
                    if (matcher.matches() == false) {
                        ExcelErrors excelErrors = new ExcelErrors();
                        // 配件代码
                        String partCode = querySet2.getString(k+1, "PART_CODE");
                        // 行号
                        String tmpNo = querySet2.getString(k+1, "TMP_NO");
                        excelErrors.setRowNum(Integer.valueOf(tmpNo));
                        excelErrors.setErrorDesc("配件代码:"+partCode+"采购价格不正确！");
                        errorList.add(excelErrors);
                    }
                    if (matcher1.matches() == false) {
                        ExcelErrors excelErrors = new ExcelErrors();
                        // 配件代码
                        String partCode = querySet2.getString(k+1, "PART_CODE");
                        // 行号
                        String tmpNo = querySet2.getString(k+1, "TMP_NO");
                        excelErrors.setRowNum(Integer.valueOf(tmpNo));
                        excelErrors.setErrorDesc("配件代码:"+partCode+"最小包装单位不正确！");
                        errorList.add(excelErrors);
                    }
                }
            }

            if (queryset3.getRowCount() > 0) {
                // 配件重复验证
                for (int i=0;i<queryset3.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String partCode = queryset3.getString(i+1, "PART_CODE");
                    // 行号
                    String tmpNo = queryset3.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("配件代码:"+partCode+"已存在供应商："+supplierCode+"的其他合同中.");
                    errorList.add(excelErrors);
                }
            }

            //校验临时表数据
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    
    /**
     * 退件申请明细
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_RETURN_APPLY_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList=new ArrayList<ExcelErrors>();
            ExcelErrors errors = null;
            ReturnPurchaseImportCheckMngDao dao = new ReturnPurchaseImportCheckMngDao();
            QuerySet qs = dao.partOrderInfoSearch(bParams,factory);
            if(qs.getRowCount()>0){
                QuerySet qs1 = dao.partBaInfoSearch(user,factory,bParams);
                if(qs1.getRowCount()>0){
                    for(int i=0;i<qs1.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs1.getString(i+1, "TMP_NO")));
                        String partCode = qs1.getString(i+1, "PART_CODE"); 
                        errors.setErrorDesc(partCode+"配件不存在.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs3 = dao.supplierBaInfoSearch(user,factory);
                if(qs3.getRowCount()>0){
                    for(int i=0;i<qs3.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs3.getString(i+1, "TMP_NO")));
                        String supplierCode = qs3.getString(i+1, "SUPPLIER_CODE"); 
                        errors.setErrorDesc(supplierCode+"供应商不存在.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs4 = dao.partSupplierBaInfoSearch(user,factory);
                if(qs4.getRowCount()>0){
                    for(int i=0;i<qs4.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs4.getString(i+1, "TMP_NO")));
                        String partCode = qs4.getString(i+1, "PART_CODE");
                        String supplierCode = qs4.getString(i+1, "SUPPLIER_CODE"); 
                        errors.setErrorDesc(partCode+"配件需指定的供应商，且与"+supplierCode+"供应商不存在供货关系.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs5 = dao.partOrderCountSearch(user,factory);
                if(qs5.getRowCount()>0){
                    for(int i=0;i<qs5.getRowCount();i++){
                        String partCode = qs5.getString(i+1, "PART_CODE");
                        String count = qs5.getString(i+1, "RETURN_COUNT");
                        String reg = "^[1-9]d*$";
                        if(reg.matches(count)){
                            errors=new ExcelErrors();
                            errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "TMP_NO")));
                            errors.setErrorDesc(partCode+"配件,订购数量不正确.");
                            errorList.add(errors);
                        }
                    }
                }
            }
            factory.getConnection().commit();
            //校验临时表数据
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_PROMOTION_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        QuerySet qs = factory.select(null, " SELECT * FROM PT_BU_PROMOTION_PART_TMP");
        System.out.println(qs.getRowCount());
        //校验临时表数据
        return errList;
    }
    
     //旧件回运导入
      @SuppressWarnings("rawtypes")
      public static List<ExcelErrors> checkSE_BU_RETURNORDER_DTL_TMP(List<Map> list,User user,String bParams) 
              throws Exception 
              {
                  
                  DBFactory factory = new DBFactory();
                  ActionContext atx = ActionContext.getContext();
                  atx.setDBFactory(factory);
                  try {
                      List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
                      ExcelErrors errors = null;
                    OldPartPackImpCheckDao dao = new OldPartPackImpCheckDao();
                      
                      factory.getConnection().commit();
                      QuerySet qs1=dao.checkList1(user,factory);//索赔单号、故障代码、配件代码、供应商代码
                      QuerySet qs6=dao.checkList6(user,factory);//数量不能大于故障配件数量
                      //QuerySet qs2=dao.checkList2(user,factory);//校验箱号不能为空
                      QuerySet qs3=dao.checkList3(user,factory);//校验导入的数据是否重复
                      //QuerySet qs4=dao.checkList4(user,factory);//校验导入的数据是否在旧件明细表中存在
                      QuerySet qs5=dao.checkList5(user,factory);//校验导入的数据不存在旧件不回运表
                      QuerySet qs7=dao.checkList7(user,factory);//校验导入的数据是否非本集中点对应的服务站产生的数据。
                      QuerySet qsAll = dao.queryAll(user,factory);//检索临时表中信息。
                      if(qsAll.getRowCount()>0){
                    	  for(int i=0;i<qsAll.getRowCount();i++){
                    		  String claimNo= qsAll.getString(i+1, "CLAIM_NO");
                    		  String faultCode= qsAll.getString(i+1, "FAULT_CODE");
                    		  String partCode= qsAll.getString(i+1, "PART_CODE");
                    		  String prosupplyCode= qsAll.getString(i+1, "PROSUPPLY_CODE");
                    		  String rowNo= qsAll.getString(i+1, "ROW_NUM");
                    		  QuerySet check = dao.queryDtl(claimNo,faultCode,partCode,prosupplyCode,factory);
                    		  
                    		  if(check.getRowCount()>0){
                    			  String orderId =check.getString(1, "ORDER_ID");
                    			  if(orderId==bParams||bParams.equals("orderId")){
                    			  }else{
                    				  errors=new ExcelErrors();
                                      errors.setRowNum(Integer.parseInt(rowNo));
                                      errors.setErrorDesc("第"+""+rowNo+""+"已在其他回运单中存在。 ");
                                      errorList.add(errors);
                    			  }
                    		  }
                    	  }
                      }
                      if(qs7.getRowCount()>0){
                    	  for(int i=0;i<qs7.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum=qs7.getString(i+1, "ROW_NUM"); 
                              String claimNo=qs7.getString(i+1,"CLAIM_NO");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("索赔单号:"+claimNo+"非本旧件集中点下属服务站产生。");
                              errorList.add(errors);
                          }
                      }
                      
                      if(qs1.getRowCount()>0){
                          for(int i=0;i<qs1.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum=qs1.getString(i+1, "ROW_NUM"); 
                              String claimNo=qs1.getString(i+1,"CLAIM_NO");
                              String partCode=qs1.getString(i+1,"PART_CODE");
                              String supplyCode=qs1.getString(i+1,"PROSUPPLY_CODE");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"与原索赔单数据不一致！");
                              errorList.add(errors);
                          }
                      }
                      
                      /*if(qs2.getRowCount()>0){
                          for(int i=0;i<qs2.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum =qs2.getString(i+1,"ROW_NUM");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("箱号不能空！");
                              errorList.add(errors);
                          }
                      }*/
                      if(qs3.getRowCount()>0){
                          for(int i=0;i<qs3.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum=qs3.getString(i+1,"ROW_NUM");
                              String faultCode=qs3.getString(i+1,"FAULT_CODE");
                              String claimNo=qs3.getString(i+1,"CLAIM_NO");
                              String partCode=qs3.getString(i+1,"PART_CODE");
                              String supplyCode=qs3.getString(i+1,"PROSUPPLY_CODE");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",故障代码："+faultCode+"供应商代码:"+supplyCode+"在第"+rowNum+"行导入数据重复！");
                              errorList.add(errors);
                          }
                      }
                      /*if(qs4.getRowCount()>0){
                          for(int i=0;i<qs4.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum=qs4.getString(i+1, "ROW_NUM"); 
                              String claimNo=qs4.getString(i+1,"CLAIM_NO");
                              String partCode=qs4.getString(i+1,"PART_CODE");
                              String supplyCode=qs4.getString(i+1,"PROSUPPLY_CODE");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"数据已导入！");
                              errorList.add(errors);
                          }
                      }*/
                      if(qs5.getRowCount()>0){
                          for(int i=0;i<qs5.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum=qs5.getString(i+1, "ROW_NUM"); 
                              String claimNo=qs5.getString(i+1,"CLAIM_NO");
                              String partCode=qs5.getString(i+1,"PART_CODE");
                              String supplyCode=qs5.getString(i+1,"PROSUPPLY_CODE");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"数据申请不回运！");
                              errorList.add(errors);
                          }
                      }
                      if(qs6.getRowCount()>0){
                          for(int i=0;i<qs6.getRowCount();i++){
                              errors=new ExcelErrors();
                              String rowNum=qs6.getString(i+1, "ROW_NUM"); 
                              String claimNo=qs6.getString(i+1,"CLAIM_NO");
                              String partCode=qs6.getString(i+1,"PART_CODE");
                              String supplyCode=qs6.getString(i+1,"PROSUPPLY_CODE");
                              errors.setRowNum(Integer.parseInt(rowNum));
                              errors.setErrorDesc("索赔单号:"+claimNo+",配件代码:"+partCode+",供应商代码:"+supplyCode+"配件数量大于索赔单数量！");
                              errorList.add(errors);
                          }
                      }
                      
                      //校验临时表数据
                      return errorList;
                  }
                  catch (Exception e) {
                      factory.getConnection().rollback();
                      e.printStackTrace();
                      return null;
                  } finally {
                      if(factory != null)
                          factory.getConnection().close();
                      factory.setFactory(null);
                      factory = null;
                  }
              }
      //旧件入库导入
      @SuppressWarnings("rawtypes")
      public static List<ExcelErrors> checkSE_BU_OLDPART_STORAGE_TMP(List<Map> list,User user,String bParams)throws Exception {
    	  
    	  DBFactory factory = new DBFactory();
    	  ActionContext atx = ActionContext.getContext();
    	  atx.setDBFactory(factory);
    	  try {
    		  List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
    		  ExcelErrors errors = null;
    		  OldPartPackImpCheckDao dao = new OldPartPackImpCheckDao();
    		  String p="(([0-9]\\d*)|0)?$";//0与正整数
    		  factory.getConnection().commit();
    		  QuerySet qs1=dao.checkList8(user,factory);//校验本次入库数量不能为空
    		  QuerySet qs2=dao.checkList9(user,factory);//查询所有本次导入的入库数量
    		  if(qs2.getRowCount()>0){
    			  
    		  }
    		  
    		  if(qs1.getRowCount()>0){
    			  for(int i=0;i<qs1.getRowCount();i++){
    				  errors=new ExcelErrors();
    				  String rowNum=qs1.getString(i+1, "ROW_NO"); 
    				  errors.setRowNum(Integer.parseInt(rowNum));
    				  errors.setErrorDesc("本次入库数量不能为空。");
    				  errorList.add(errors);
    			  }
    		  }
    		  if(qs2.getRowCount()>0){
    			  for(int i=0;i<qs2.getRowCount();i++){
    				  String rksl = qs2.getString(i+1, "STORAGE_COUNT");
    				  if(!rksl.matches(p)){
    					  errors=new ExcelErrors();
        				  String rowNum=qs2.getString(i+1, "ROW_NO"); 
        				  errors.setRowNum(Integer.parseInt(rowNum));
        				  errors.setErrorDesc("请正确填写入库数量。");
        				  errorList.add(errors); 
    				  }else{
    					  //如果输入的入库数量格式正确，进入下一步校验：填写的数量+已入库数量是否大于旧件集中点实际返回旧件数量
	    				  String orderNo =qs2.getString(i+1, "RETURN_ORDER_NO");//回运单号
	    				  String claimNo =qs2.getString(i+1, "CLAIM_NO");//索赔单号
	    				  String faultCode =qs2.getString(i+1, "FAULT_CODE");//故障代码
	    				  String partCode =qs2.getString(i+1, "PART_CODE");//配件代码		  
	    				  String supCode =qs2.getString(i+1, "SUPPLIER_CODE");//供应商代码    				  
	    				  String StorageCount =qs2.getString(i+1, "STORAGE_COUNT");//本次入库数量
	    				  QuerySet qsCount=dao.queryCount(user,factory,orderNo,claimNo,faultCode,partCode,supCode);//查询所有本次导入的入库数量
	    				  if(qsCount.getRowCount()>0){
							  String alreadyIn=qsCount.getString(1, "ALREADY_IN");//已入库数量
							  String oughtCount=qsCount.getString(1, "OUGHT_COUNT");//旧件集中点实返数量
							  int sl=Integer.valueOf(StorageCount)+Integer.valueOf(alreadyIn);//本次入库数量+已入库数量
							  if(sl>Integer.valueOf(oughtCount)){
								//如果(本次入库数量+已入库数量)>旧件集中点实返数量说明填写的入库数量过大。
								  errors=new ExcelErrors();
			    				  String rowNum=qs2.getString(i+1, "ROW_NO"); 
			    				  errors.setRowNum(Integer.parseInt(rowNum));
			    				  errors.setErrorDesc("填写的本次入库数量:"+StorageCount+"+已入库:"+alreadyIn+"数量已超过旧件集中点实际返回数量:"+oughtCount+"。");
			    				  errorList.add(errors);
							  }
	    				  }
	    			  }
				  }
			  }
    		  QuerySet qs3=dao.queryReturnNo(user,factory);//回运单号是否存在
              if(qs3.getRowCount()>0){
                  for(int i=0;i<qs3.getRowCount();i++){
                      errors=new ExcelErrors();
                      String returnOrderNo =qs3.getString(i+1, "RETURN_ORDER_NO");
                      String rowNo =qs3.getString(i+1, "ROW_NO");
                      if(null==rowNo||rowNo.equals("")){
                      }else{
                          errors.setRowNum(Integer.parseInt(rowNo));
                      }
                      errors.setErrorDesc("回运单号："+returnOrderNo+"不存在！");
                      errorList.add(errors);
                  }
              }
              QuerySet qs4 = dao.queryNos(user,factory);//判断索赔单号、回运单号、配件代码、供应商、故障是否一致
              if(qs4.getRowCount()>0){
                  for(int k=0;k<qs4.getRowCount();k++){
                      errors=new ExcelErrors();
                      String rowNo =qs4.getString(k+1, "ROW_NO");
                      String claimNo = qs4.getString(k+1, "CLAIM_NO");
                      String partCode=qs4.getString(k+1, "PART_CODE");
                      String returnOrderNo =qs4.getString(k+1, "RETURN_ORDER_NO");
                      String faultCode=qs4.getString(k+1, "FAULT_CODE");
                      String supplierCode=qs4.getString(k+1, "SUPPLIER_CODE");
                      if(null==rowNo||rowNo.equals("")){
                      }else{
                          errors.setRowNum(Integer.parseInt(rowNo));
                      }
                      errors.setErrorDesc("索赔单号："+claimNo+",回运单号："+returnOrderNo+",旧件代码："+partCode+",供应商代码："+supplierCode+",故障代码："+faultCode+"数据不一致！");
                      errorList.add(errors);
                  }
              }
    		  //校验临时表数据
    		  return errorList;
    	  }
    	  catch (Exception e) {
    		  factory.getConnection().rollback();
    		  e.printStackTrace();
    		  return null;
    	  } finally {
    		  if(factory != null)
    			  factory.getConnection().close();
    		  factory.setFactory(null);
    		  factory = null;
    	  }
      }
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_PCH_ORDER_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        PurchaseOrderImpCheckDao dao = new PurchaseOrderImpCheckDao();
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        QuerySet getId = dao.getSup(factory,bParams);
        String SUPPLIER_ID = getId.getString(1, "SUPPLIER_ID");
        QuerySet check01 = dao.checkExist(factory,user);//校验导入配件是否存在配件主信息表
        if(check01.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check01.getRowCount();i++){
                String TMP_NO = check01.getString(i, "TMP_NO");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码不存在！");
                errorList.add(errors);
            }
            
        }
        QuerySet check02 = dao.checkAttribute(factory,user);//校验配件的采购属性
        if(check02.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check02.getRowCount();i++){
                String TMP_NO = check02.getString(i, "TMP_NO");
                String PART_CODE = check02.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"采购属性未维护！");
                errorList.add(errors);
            }
            
        }
        QuerySet check03 = dao.checkPchCycle(factory,user,SUPPLIER_ID);//校验配件的供货周期
        if(check03.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check03.getRowCount();i++){
                String TMP_NO = check03.getString(i, "TMP_NO");
                String PART_CODE = check03.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"采购周期未维护！");
                errorList.add(errors);
            }
            
        }
        QuerySet check04 = dao.checkPchPrice(factory,user,SUPPLIER_ID);//校验配件的采购价格
        if(check04.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check04.getRowCount();i++){
                String TMP_NO = check04.getString(i, "TMP_NO");
                String PART_CODE = check04.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"采购价格未维护！");
            }
            errorList.add(errors);
        }
        QuerySet check05 = dao.checkPlanPrice(factory,user,SUPPLIER_ID);//校验配件的计划价格
        if(check05.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check05.getRowCount();i++){
                String TMP_NO = check05.getString(i, "TMP_NO");
                String PART_CODE = check05.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"计划采购价格未维护！");
                errorList.add(errors);
            }
            
        }
        QuerySet check06 = dao.checkUnique(factory,user,bParams);//校验配件的计划价格
        if(check06.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check06.getRowCount();i++){
                String TMP_NO = check06.getString(i, "TMP_NO");
                String PART_CODE = check06.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"已经在此订单中维护");
                errorList.add(errors);
            }
            
        }
        QuerySet check07 = dao.checkContract(factory,user,bParams);//校验配件的计划价格
        if(check07.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check07.getRowCount();i++){
                String TMP_NO = check07.getString(i, "TMP_NO");
                String PART_CODE = check07.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"没有对应采购合同");
                errorList.add(errors);
            }
            
        }
        QuerySet check08 = dao.checkCount(user,factory);//校验采购数量
        for(int i=0;i<check08.getRowCount();i++){
            String partCode = check08.getString(i+1, "PART_CODE");
            String count = check08.getString(i+1, "PCH_COUNT");
            Pattern pattern = Pattern.compile("[0-9]*");  
            Matcher matcher = pattern.matcher(count);  
            if ("".equals(count)) {
            	errors=new ExcelErrors();
                errors.setRowNum(Integer.parseInt(check08.getString(i+1, "TMP_NO")));
                errors.setErrorDesc("配件"+partCode+",订购数量为空.");
                errorList.add(errors);
                continue;
            }
            if ("0".equals(count)) {
            	errors=new ExcelErrors();
                errors.setRowNum(Integer.parseInt(check08.getString(i+1, "TMP_NO")));
                errors.setErrorDesc("配件"+partCode+",订购数量为0.");
                errorList.add(errors);
                continue;
            }
            if (matcher.matches() == false) {
                errors=new ExcelErrors();
                errors.setRowNum(Integer.parseInt(check08.getString(i+1, "TMP_NO")));
                errors.setErrorDesc("配件"+partCode+",订购数量不正确.");
                errorList.add(errors);
            }
        }
        if(errorList!=null&&errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
    }
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkARMY_PT_BU_PCH_ORDER_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        /*//校验临时表数据
        PurchaseOrderImpCheckAction check = new PurchaseOrderImpCheckAction();
        errList = check.checkArmyData(user,bParams);
        
        return errList;*/
        PurchaseOrderImpCheckDao dao = new PurchaseOrderImpCheckDao();
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        QuerySet getId = dao.getSup(factory,bParams);
        String SUPPLIER_ID = getId.getString(1, "SUPPLIER_ID");
        QuerySet check01 = dao.checkExist(factory,user);//校验导入配件是否存在配件主信息表
        if(check01.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check01.getRowCount();i++){
                String TMP_NO = check01.getString(i, "TMP_NO");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码不存在！");
                errorList.add(errors);
            }
        }
        QuerySet check02 = dao.checkAttribute(factory,user);//校验配件的采购属性
        if(check02.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check02.getRowCount();i++){
                String TMP_NO = check02.getString(i, "TMP_NO");
                String PART_CODE = check02.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"采购属性未维护！");
                errorList.add(errors);
            }
        }
        QuerySet check03 = dao.checkPchCycle(factory,user,SUPPLIER_ID);//校验配件的供货周期
        if(check03.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check03.getRowCount();i++){
                String TMP_NO = check03.getString(i, "TMP_NO");
                String PART_CODE = check03.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"采购周期未维护！");
                errorList.add(errors);
            }
        }
        QuerySet check04 = dao.checkPchPrice(factory,user,SUPPLIER_ID);//校验配件的采购价格
        if(check04.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check04.getRowCount();i++){
                String TMP_NO = check04.getString(i, "TMP_NO");
                String PART_CODE = check04.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"采购价格未维护！");
                errorList.add(errors);
            }
        }
        QuerySet check05 = dao.checkPlanPrice(factory,user,SUPPLIER_ID);//校验配件的计划价格
        if(check05.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check05.getRowCount();i++){
                String TMP_NO = check05.getString(i, "TMP_NO");
                String PART_CODE = check05.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"计划价格未维护！");
                errorList.add(errors);
            }
        }
        QuerySet check06 = dao.checkUnique(factory,user,bParams);//校验配件的计划价格
        if(check06.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check06.getRowCount();i++){
                String TMP_NO = check06.getString(i, "TMP_NO");
                String PART_CODE = check06.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"已经在此订单中维护！");
                errorList.add(errors);
            }
        }
        QuerySet check07 = dao.checkContract(factory,user,bParams);//校验配件的计划价格
        if(check07.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check07.getRowCount();i++){
                String TMP_NO = check07.getString(i, "TMP_NO");
                String PART_CODE = check07.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件"+PART_CODE+"没有对应采购合同");
                errorList.add(errors);
            }
        }
        QuerySet check08 = dao.checkCount(user,factory);//校验采购数量
        for(int i=0;i<check08.getRowCount();i++){
            String partCode = check08.getString(i+1, "PART_CODE");
            String count = check08.getString(i+1, "PCH_COUNT");
            Pattern pattern = Pattern.compile("[0-9]*");  
            Matcher matcher = pattern.matcher(count);  
            if ("".equals(count)) {
            	errors=new ExcelErrors();
                errors.setRowNum(Integer.parseInt(check08.getString(i+1, "TMP_NO")));
                errors.setErrorDesc("配件"+partCode+",订购数量为空.");
                errorList.add(errors);
                continue;
            }
            if ("0".equals(count)) {
            	errors=new ExcelErrors();
                errors.setRowNum(Integer.parseInt(check08.getString(i+1, "TMP_NO")));
                errors.setErrorDesc("配件"+partCode+",订购数量为0.");
                errorList.add(errors);
                continue;
            }
            if (matcher.matches() == false) {
                errors=new ExcelErrors();
                errors.setRowNum(Integer.parseInt(check08.getString(i+1, "TMP_NO")));
                errors.setErrorDesc("配件"+partCode+",订购数量不正确.");
                errorList.add(errors);
            }
        }
        if(errorList!=null&&errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
    }
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_PCH_RETURN_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        PurchaseReturnImportCheckDao orderdao = new PurchaseReturnImportCheckDao();

        QuerySet querysId =orderdao.querySid(factory,bParams);
        String sId = querysId.getString(1, "SUPPLIER_ID");
        QuerySet qs3 = orderdao.queryId(factory);
        String wId = qs3.getString(1, "WAREHOUSE_ID");
        //校验填写的配件在本退货单中是否存在供货关系。
        QuerySet ifSuply=orderdao.queryIfSuply(factory,user,bParams);
        if(ifSuply.getRowCount()>0){
            for(int i=0;i<ifSuply.getRowCount();i++){
                errors=new ExcelErrors();
                String TMP_NO=ifSuply.getString(i+1, "TMP_NO"); 
                String partCode=ifSuply.getString(i+1, "PART_CODE"); 
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("配件代码:"+partCode+"在本退货单中不存在供货关系！");
                errorList.add(errors);
            }
        }
        //校验退货单填写是否正确
        QuerySet qs =orderdao.checkList1(factory,user);
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                errors=new ExcelErrors();
                String TMP_NO=qs.getString(i+1, "TMP_NO"); 
                String returnNo=qs.getString(i+1, "RETURN_NO"); 
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("退货单号:"+returnNo+"不存在。");
                errorList.add(errors);
            }
        }
        //校验配件代码填写是否正确
        QuerySet qs1=orderdao.checkPartCode(factory,user);
        if(qs1.getRowCount()>0){
            if(qs1.getRowCount()>0){
                for(int i=0;i<qs1.getRowCount();i++){
                    errors=new ExcelErrors();
                    String TMP_NO=qs1.getString(i+1, "TMP_NO"); 
                    String PART_CODE=qs1.getString(i+1, "PART_CODE"); 
                    errors.setRowNum(Integer.parseInt(TMP_NO));
                    errors.setErrorDesc("配件代码:"+PART_CODE+"不存在。");
                    errorList.add(errors);
                }
            }
        }
        //校验配件代码与库位代码是否存在对应关系
        QuerySet qs2 =orderdao.checkPartPoCode(factory,user);
        if(qs2.getRowCount()>0){
            if(qs2.getRowCount()>0){
                for(int i=0;i<qs2.getRowCount();i++){
                    errors=new ExcelErrors();
                    String TMP_NO=qs2.getString(i+1, "TMP_NO"); 
                    String PART_CODE=qs2.getString(i+1, "PART_CODE"); 
                    String POSITION_CODE=qs2.getString(i+1, "POSITION_CODE");
                    errors.setRowNum(Integer.parseInt(TMP_NO));
                    errors.setErrorDesc("配件代码:"+PART_CODE+"与库位代码:"+POSITION_CODE+"不存在对应关系！");
                    errorList.add(errors);
                }
            }
        }
        //校验填写的退货数是否满足可退货数（已入库数-已退货数）的条件！
        QuerySet qs4=orderdao.checkKthAmount(factory,user,sId,wId);
        if(qs4.getRowCount()>0){
            for(int i=0;i<qs4.getRowCount();i++){
                errors=new ExcelErrors();
                String TMP_NO=qs4.getString(i+1, "TMP_NO"); 
                String PART_CODE=qs4.getString(i+1, "PART_CODE"); 
                String POSITION_CODE=qs4.getString(i+1, "POSITION_CODE");
                String RETURN_COUNT=qs4.getString(i+1, "RETURN_COUNT");
                String SL=qs4.getString(i+1, "SL");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("配件代码:"+PART_CODE+"退货数量:"+RETURN_COUNT+"大于库位代码:"+POSITION_CODE+"中可退货数:"+SL+"！");
                errorList.add(errors);
            }    
        }
        //校验填写的退货数是否满足可用库存的条件！
        QuerySet qs5=orderdao.checkAvaAmount(factory,user,sId,wId);
        if(qs5.getRowCount()>0){
            for(int i=0;i<qs5.getRowCount();i++){
                errors=new ExcelErrors();
                String TMP_NO=qs5.getString(i+1, "TMP_NO"); 
                String PART_CODE=qs5.getString(i+1, "PART_CODE"); 
                String POSITION_CODE=qs5.getString(i+1, "POSITION_CODE");
                String AVAILABLE_AMOUNT=qs5.getString(i+1, "AVAILABLE_AMOUNT");
                String RETURN_COUNT=qs5.getString(i+1, "RETURN_COUNT");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("配件代码:"+PART_CODE+"退货数:"+RETURN_COUNT+"大于库位代码:"+POSITION_CODE+"的可用库存数:"+AVAILABLE_AMOUNT+"!");
                errorList.add(errors);
            }    
        }
    
        if(errorList!=null&&errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
        
    }
    //旧件装箱校验
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_BOX_UP_TMP(List<Map> list,User user,String bParams)
            throws Exception
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        BoxUpImportCheckDao dao = new BoxUpImportCheckDao();
        
        QuerySet qs1 = dao.checkList1(factory,user);
        ExcelErrors errors = new ExcelErrors();
        if (qs1.getRowCount() > 0) {
            for (int i = 0; i < qs1.getRowCount(); i++) {
                
                errors = new ExcelErrors();
                String TMP_NO = qs1.getString(i + 1, "TMP_NO");
                String ISSUE_NO = qs1.getString(i + 1, "ISSUE_NO");
                String PART_CODE = qs1.getString(i + 1, "PART_CODE");
                String BOX_NO = qs1.getString(i + 1, "BOX_NO");
                String COUNT = qs1.getString(i + 1, "COUNT");

                if (null == ISSUE_NO || "".equals(ISSUE_NO)) {
                    errors.setRowNum(Integer.valueOf(TMP_NO));
                    errors.setErrorDesc("第" + TMP_NO + "行,发料单号不能为空!");
                    errorList.add(errors);
                }
                if (null == PART_CODE || "".equals(PART_CODE)) {
                    errors.setRowNum(Integer.valueOf(TMP_NO));
                    errors.setErrorDesc("第" + TMP_NO + "行,配件代码不能为空");
                    errorList.add(errors);
                }
                if (null == BOX_NO || "".equals(BOX_NO)) {
                    errors.setRowNum(Integer.valueOf(TMP_NO));
                    errors.setErrorDesc("第" + TMP_NO + "行,箱号不能为空!");
                    errorList.add(errors);
                }
                if (null == COUNT || "".equals(COUNT)) {
                    errors.setRowNum(Integer.valueOf(TMP_NO));
                    errors.setErrorDesc("第" + TMP_NO + "行,数量不能为空!");
                    errorList.add(errors);
                }
            }
        }
        if (errorList.size() == 0) {
            QuerySet qs2 = dao.checkList2(factory,user, bParams);
            if (qs2.getRowCount() > 0) {
                for (int i = 0; i < qs2.getRowCount(); i++) {
                    errors = new ExcelErrors();
                    String TMP_NO = qs2.getString(i + 1, "ROW_NO");
                    errors.setRowNum(Integer.valueOf(TMP_NO));
                    errors.setErrorDesc("第" + TMP_NO + "行,发料单号与备件代码不属于此销售订单");
                    errorList.add(errors);
                }
            }
        }
        factory.getConnection().commit();
        if (errorList != null && errorList.size() > 0) {
            return errorList;
        } else {
            return null;
        }
        
        

    }
    
    public static List<ExcelErrors> checkPT_BU_VEL_BOX_RL_TMP(List<Map> list,User user,String bParams)
            throws Exception
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        BoxUpImportCheckDao dao = new BoxUpImportCheckDao();
        String b[] = bParams.split(",");
        String VEHICLE_ID = b[0];
        String SHIP_ID = b[1];
        QuerySet qs1 = dao.checkVel(factory,user,VEHICLE_ID,SHIP_ID);
        ExcelErrors errors = new ExcelErrors();
        if (qs1.getRowCount() > 0) {
            for (int i = 0; i < qs1.getRowCount(); i++) {
                
                errors = new ExcelErrors();
                String TMP_NO = qs1.getString(i + 1, "TMP_NO");
                String BOX_NO = qs1.getString(i + 1, "BOX_NO");

                errors.setRowNum(Integer.valueOf(TMP_NO));
                errors.setErrorDesc("第" + TMP_NO + "行,箱号"+BOX_NO+"已经维护!");
                errorList.add(errors);
            }
        }
        QuerySet qs2 = dao.checkBox(factory,user,SHIP_ID);
        if (qs2.getRowCount() > 0) {
            for (int i = 0; i < qs2.getRowCount(); i++) {
                errors = new ExcelErrors();
                String TMP_NO = qs2.getString(i + 1, "TMP_NO");
                errors.setRowNum(Integer.valueOf(TMP_NO));
                errors.setErrorDesc("第" + TMP_NO + "行,该箱号不存在");
                errorList.add(errors);
            }
        }
        factory.getConnection().commit();
        if (errorList != null && errorList.size() > 0) {
            return errorList;
        } else {
            return null;
        }
        
        

    }
    
    //批量调整导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BU_CLAIM_SETTLE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new ArrayList<ExcelErrors>();
		StatementAdjustImpCheckDao dao =new StatementAdjustImpCheckDao();
		 try {
			QuerySet qs1=dao.checkList1(user,factory);//结算单号不能为空，校验费用都是数字
			QuerySet qs2=dao.checkList2(user,factory);//结算单号是当前月份的单号
			if(qs1.getRowCount()>0){
				for(int i=0;i<qs1.getRowCount();i++){
					errors=new ExcelErrors();
					String p="^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$";//校验钱
					
					String rowNum=qs1.getString(i+1, "ROW_NUM"); 
					String settleNo=qs1.getString(i+1,"SETTLE_NO");//结算单号
					String seReCosts=qs1.getString(i+1,"SE_RE_COSTS");//旧件运费、配件返利
		    		String sePolicySup=qs1.getString(i+1,"SE_POLICY_SUP");//政策支持
		    		String seCashGift=qs1.getString(i+1,"SE_CASH_GIFT");//礼金
		    		String seCarAward=qs1.getString(i+1,"SE_CAR_AWARD");//售车奖励
		    		String seApCosts=qs1.getString(i+1,"SE_AP_COSTS");//考核费用
		    		String seOthers=qs1.getString(i+1,"SE_OTHERS");//其它费用
		    		String materialCostReduce=qs1.getString(i+1,"MATERIALCOST_REDUCE");//材料费冲减
		    		String partMaterialCosts=qs1.getString(i+1,"PART_MATERIAL_COSTS");//配件三包材料费
					if(null==settleNo||"".equals(settleNo)){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("结算单号不能为空.");
	                    errorList.add(errors);
	                }
					/****服务项校验*****/
					if(null==settleNo||settleNo.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("旧件运费/配件返利金额不可为空.");
                        errorList.add(errors);
		    		}
					//旧件运费/配件返利
					if(null!=seReCosts&&!"".equals(seReCosts)){
						if(!seReCosts.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
							errors.setErrorDesc("请输入正确的旧件运费/配件返利金额.");
	                        errorList.add(errors);
	                    }
	                }
					if(null==partMaterialCosts||partMaterialCosts.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("配件三包材料费不可为空.");
						errorList.add(errors);
					}
					if(null!=partMaterialCosts&&!"".equals(partMaterialCosts)){
						if(!seReCosts.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
							errors.setErrorDesc("请输入正确的配件三包材料费金额.");
							errorList.add(errors);
						}
					}
					if(null==materialCostReduce||materialCostReduce.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("材料费冲减不可为空.");
						errorList.add(errors);
					}
					if(null!=materialCostReduce&&!"".equals(materialCostReduce)){
						if(!seReCosts.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
							errors.setErrorDesc("请输入正确的材料费冲减金额.");
							errorList.add(errors);
						}
					}
				/*	//政策支持
					if(null==sePolicySup||sePolicySup.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("政策支持金额不可为空.");
                        errorList.add(errors);
		    		}
					if(null!=sePolicySup&&!"".equals(sePolicySup)){
						if(!sePolicySup.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
	                        errors.setErrorDesc("请输入正确的政策支持金额.");
	                        errorList.add(errors);
	                    }
	                }*/
					//礼金
					if(null==seCashGift||seCashGift.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("礼金金额不可为空.");
                        errorList.add(errors);
		    		}
					if(null!=seCashGift&&!"".equals(seCashGift)){
						if(!seCashGift.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
	                        errors.setErrorDesc("请输入正确的礼金金额.");
	                        errorList.add(errors);
	                    }
	                }
					//售车奖励
					if(null==seCarAward||seCarAward.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("售车奖励金额不可为空.");
                        errorList.add(errors);
		    		}
					if(null!=seCarAward&&!"".equals(seCarAward)){
						if(!seCarAward.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
	                        errors.setErrorDesc("请输入正确的售车奖励金额.");
	                        errorList.add(errors);
	                    }
	                }
					//售车奖励
					if(null==seApCosts||seApCosts.equals("")){
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("考核费用不可为空.");
                        errorList.add(errors);
		    		}
					if(null!=seApCosts&&!"".equals(seApCosts)){
						if(!seApCosts.matches(p)){
							errors=new ExcelErrors();
							errors.setRowNum(Integer.parseInt(rowNum));
	                        errors.setErrorDesc("请输入正确的考核费用.");
	                        errorList.add(errors);
	                    }
	                }
					/*//其它费用
					if(null==seOthers||seOthers.equals("")){
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("其它费用金额不可为空.");
                        errorList.add(errors);
		    		}
					if(null!=seOthers&&!"".equals(seOthers)){
						if(!seOthers.matches(p)){
							errors.setRowNum(Integer.parseInt(rowNum));
	                        errors.setErrorDesc("请输入正确的其它费用金额.");
	                        errorList.add(errors);
	                    }
	                }*/
				}
			}
			if(qs2.getRowCount()>0){
				for(int i=0;i<qs2.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNum =qs2.getString(i+1,"ROW_NUM");
					String settleNo =qs2.getString(i+1,"SETTLE_NO");
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("结算单号"+settleNo+"不是该月份的结算单号.");
					errorList.add(errors);
				}
			}
			return errorList;
    	}
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
            factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    //批量调整导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BU_ACTIVITY_VEHICLE_TMP(List<Map> list,User user,String bParams)throws Exception 
    {
        
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            ExcelErrors errors = null;
            ServiceVinSetImportCheckDao dao = new ServiceVinSetImportCheckDao();
            
            factory.getConnection().commit();

            QuerySet qs1=dao.checkList(user,factory);
            if(qs1.getRowCount()>0){
                for(int i=0;i<qs1.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNo=qs1.getString(i+1, "ROW_NO"); 
                    String vin=qs1.getString(i+1,"VIN");
                    errors.setRowNum(Integer.parseInt(rowNo));
                    errors.setErrorDesc("底盘号:"+vin+"在车辆数据中不存在。");
                    errorList.add(errors);
                }
            }
            QuerySet qs2=dao.checkList2(user,bParams,factory);
            if(qs2.getRowCount()>0){
                for(int i=0;i<qs2.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNo=qs2.getString(i+1, "ROW_NO"); 
                    String vin=qs2.getString(i+1,"VIN");
                    errors.setRowNum(Integer.parseInt(rowNo));
                    errors.setErrorDesc("底盘号:"+vin+"已维护到服务活动VIN信息中，不可重复添加");
                    errorList.add(errors);
                }
            }
            QuerySet qs3=dao.checkList3(user,factory);
            if(qs3.getRowCount()>0){
                String row=null;
                for(int i=0;i<qs3.getRowCount();i++){
                    errors=new ExcelErrors();
                    String vin=qs3.getString(i+1,"VIN");
                    String rowNo=qs3.getString(i+1,"ROW_NO");
                    errors.setRowNum(Integer.parseInt(rowNo));
                    //if(row!=null){
                    //     row=row+","+rowNo;
                    //}else{
                    //    row=rowNo;
                    //}
                    errors.setErrorDesc("底盘号:"+vin+"在导入模板中第"+rowNo+"行重复填写！");
                    errorList.add(errors);
                }
            }
            //校验临时表数据
            return errorList;
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    
    //安全库存配件导入   suoxiuli 2014-09-01
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_STOCK_SAFESTOCKS_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        SafeStockAction check = new SafeStockAction();
        errList = check.checkData();
        
        return errList;
    }
    
    //渠道安全库存配件导入   suoxiuli 2014-09-01
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_CHANNEL_SAFESTOCK_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        ChannelSafeStockAction check = new ChannelSafeStockAction();
        errList = check.checkData();
        
        return errList;
    }
    
    //渠道发运地址导入   suoxiuli 2014-10-25
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_TRANSPORT_ADDRESS_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        TransAddressAction check = new TransAddressAction();
        errList = check.checkData();
        
        return errList;
    }
    
    //bxl三包规则导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_RULE_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        RulePartMngAction check = new RulePartMngAction();
        errList = check.checkData();
        
        return errList;
    }
    
  //yhw库存盘点明细，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_INVENTORY_DTL_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
//        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
//        DBFactory factory = new DBFactory();
//        ActionContext atx = ActionContext.getContext();
//        atx.setDBFactory(factory);
//        //校验临时表数据
//        PtBuInventoryDtlAction check = new PtBuInventoryDtlAction();  
//        errList = check.checkData();
//        
//        return errList;

/*        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        11
        //校验临时表数据
//        PtBuInventoryAction check = new PtBuInventoryAction();  
//        errList = check.checkData();
        
        return errList;*/

    	String INVENTORY_ID = bParams;
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        PtBuInventoryImpCheckDao dao = new PtBuInventoryImpCheckDao();
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        QuerySet check01 = dao.checkExist1(factory,user);//校验导入配件是否存在配件主信息表
        if(check01.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check01.getRowCount();i++){
                String TMP_NO = check01.getString(i, "ROW_NUM");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码不存在！");
                errorList.add(errors);
            }
            
        }
        QuerySet check02 = dao.checkCount(factory,user);//校验导入配件对应库区信息是否存在
        if(check02.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check02.getRowCount();i++){
                String TMP_NO = check02.getString(i, "ROW_NUM");
                String PART_CODE = check02.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"实物数量为空！");
                errorList.add(errors);
            }
        }
        QuerySet check03 = dao.checkPosi1(factory,user,INVENTORY_ID);//校验导入配件对应库区信息是否存在
        if(check03.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check03.getRowCount();i++){
                String TMP_NO = check03.getString(i, "ROW_NUM");
                String PART_CODE = check03.getString(i, "PART_CODE");
                String POSITION_CODE = check03.getString(i, "POSITION_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"不存在库位"+POSITION_CODE+"中！");
                errorList.add(errors);
            }
            
        }
        QuerySet check04 = dao.checkUnique1(factory,user);//校验导入配件对应库区信息是否存在
        if(check04.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check04.getRowCount();i++){
                String TMP_NO = check04.getString(i, "ROW_NUM");
                String PART_CODE = check04.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"重复！");
                errorList.add(errors);
            }
            
        }
        if(errorList!=null&&errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
    
    
    }
  //yhw配件档案明细，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_INFO_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
//        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
//        DBFactory factory = new DBFactory();
//        ActionContext atx = ActionContext.getContext();
//        atx.setDBFactory(factory);
//        //校验临时表数据
//        PtBaInfoAction check = new PtBaInfoAction();  
//        errList = check.checkData();
//        
//        return errList;
    	DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
    	PtBaInfoCheckDao dao = new PtBaInfoCheckDao();
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchPtBaInfoTmp(user, "",factory);//查询此用户下的所有配件档案临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
				String UNIT = qs.getString(i+1,"UNIT");//计量单位
				String PART_TYPE = qs.getString(i+1,"PART_TYPE");//配件类型
				String IF_ASSEMBLY = qs.getString(i+1,"IF_ASSEMBLY");//是否大总成
				String IF_OUT = qs.getString(i+1,"IF_OUT");//是否保外
				String IF_SUPLY = qs.getString(i+1,"IF_SUPLY");//是否指定供货商
				String IF_OIL = qs.getString(i+1,"IF_OIL");//是否油品
				String IF_SCAN = qs.getString(i+1,"IF_SCAN");//是否扫码
				String PART_STATUS = qs.getString(i+1,"PART_STATUS");//配件状态
				String MIN_PACK = qs.getString(i+1,"MIN_PACK");//最小包装数
				String MIN_UNIT = qs.getString(i+1,"MIN_UNIT");//最小包装单位
				
				//配件代码
				if(null==PART_CODE || "".equals(PART_CODE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不能为空!");
                    errorList.add(errors);
                }
				//配件名称
				if(null==PART_NAME || "".equals(PART_NAME)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件名称不能为空!");
                    errorList.add(errors);
                }
				//计量单位
				if(null==UNIT || "".equals(UNIT)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("计量单位不能为空!");
                    errorList.add(errors);
                }else{
                	if(UNIT.matches(p)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("计量单位不能填写数字!");
	                    errorList.add(errors);
	    			}
                }
				//配件类型
				if(null==PART_TYPE || "".equals(PART_TYPE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件类型不能为空!");
                    errorList.add(errors);
                }else{
                	if(!"混用件".equals(PART_TYPE)&&!"民品件".equals(PART_TYPE)&&!"军品件".equals(PART_TYPE)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("配件类型不能填写除'民品件'、'民品件'、'军品件'之外的内容!");
	                    errorList.add(errors);
	    			}
                }
				//是否大总成
				if(null==IF_ASSEMBLY || "".equals(IF_ASSEMBLY)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否大总成不能为空!");
                    errorList.add(errors);
                }if(!"是".equals(IF_ASSEMBLY)&&!"否".equals(IF_ASSEMBLY)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否大总成不能填写除'是'、'否'之外的内容!");
                    errorList.add(errors);
    			}
				//是否保外
				if(null==IF_OUT || "".equals(IF_OUT)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否保外不能为空!");
                    errorList.add(errors);
                }if(!"是".equals(IF_OUT)&&!"否".equals(IF_OUT)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否保外不能填写除'是'、'否'之外的内容!");
                    errorList.add(errors);
    			}
				//是否指定供货商
				if(null==IF_SUPLY || "".equals(IF_SUPLY)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否指定供货商不能为空!");
                    errorList.add(errors);
                }if(!"是".equals(IF_SUPLY)&&!"否".equals(IF_SUPLY)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否指定供货商不能填写除'是'、'否'之外的内容!");
                    errorList.add(errors);
    			}
				//是否油品
				if(null==IF_OIL || "".equals(IF_OIL)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否油品不能为空!");
                    errorList.add(errors);
                }if(!"是".equals(IF_OIL)&&!"否".equals(IF_OIL)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否油品不能不能填写除'是'、'否'之外的内容!");
                    errorList.add(errors);
    			}
				//是否扫码
				if(null==IF_SCAN || "".equals(IF_SCAN)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否扫码不能为空!");
                    errorList.add(errors);
                }if(!"是".equals(IF_SCAN)&&!"否".equals(IF_SCAN)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否扫码不能填写除'是'、'否'之外的内容!");
                    errorList.add(errors);
    			}
				//配件状态
				if(null==PART_STATUS || "".equals(PART_STATUS)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件状态不能为空!");
                    errorList.add(errors);
                }if(!"有效".equals(PART_STATUS)&&!"无效".equals(PART_STATUS)&&!"待报废".equals(PART_STATUS)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件状态不能填写除'有效'、'无效'、'待报废'之外的内容!");
                    errorList.add(errors);
    			}
              //最小包装数
				if(null==MIN_PACK || "".equals(MIN_PACK)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("最小包装数不能为空!");
                    errorList.add(errors);
                }
              //最小包装单位
				if(null==MIN_UNIT || "".equals(MIN_UNIT)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("最小包装单位不能为空!");
                    errorList.add(errors);
                }if(MIN_UNIT.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("最小包装单位不能为数字!");
                    errorList.add(errors);
    			}
		
			}
			//2.重复数据校验,零时表中存在相同的配件代码则必须删除一个		
			QuerySet qs2 = dao.searchPtBaInfoTmpRepeatData(user,"",factory); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchPtBaInfoTmpRepeatData1(user, partCode,factory);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = "配件代码重复，请删除重复数据！重复行是("+errorStr+")！";
					
					//添加错误描述
					errors.setErrorDesc(errorStr);
					errorList.add(errors);
				}
			}
		}
		QuerySet check =dao.checkStock(user,factory);
		if(check.getRowCount() > 0){
        	for(int i=0;i<check.getRowCount();i++){
                errors=new ExcelErrors();
                String rowNum=check.getString(i+1, "ROW_NUM"); 
                String partCode=check.getString(i+1,"PART_CODE");
                errors.setRowNum(Integer.parseInt(rowNum));
                errors.setErrorDesc( "配件"+partCode+"库存不为0不能置为无效状态");
                errorList.add(errors);
            }
        }
		if(errorList!=null && errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    
    }
    //yhw配件价格明细，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_PRICE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
//        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
//        DBFactory factory = new DBFactory();
//        ActionContext atx = ActionContext.getContext();
//        atx.setDBFactory(factory);
//        //校验临时表数据
//        PtBaInfoAction check = new PtBaInfoAction();  
//        errList = check.checkPriceData();
    	DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            ExcelErrors errors = null;
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            PtBaInfoCheckDao dao = new PtBaInfoCheckDao();

            // 价格无改动
            QuerySet qs0 = dao.ptBaPriceTmpCheck(user, factory);
            if (qs0.getRowCount() >0) {
            	for(int i=0;i<qs0.getRowCount();i++){
            		String rowNum = qs0.getString(i+1, "ROW_NUM"); //行号
            		errors=new ExcelErrors();
            		errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件价格无修改.");
                    errorList.add(errors);
            	}
            }
            
            // 配件不存在
            QuerySet qs01 = dao.ptBaPriceTmpCheck1(user, factory);
            if (qs0.getRowCount() >0) {
            	for(int i=0;i<qs01.getRowCount();i++){
            		String rowNum = qs01.getString(i+1, "ROW_NUM"); //行号
            		errors=new ExcelErrors();
            		errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件不存在.");
                    errorList.add(errors);
            	}
            }

            QuerySet qs = dao.searchPtBaPriceTmp(user, "",factory);//查询此用户下的所有配件价格临时表信息
    		if(qs.getRowCount()>0){
    			for(int i=0;i<qs.getRowCount();i++){
    				errors=new ExcelErrors();
    				String p="^(0|[1-9][0-9]*)$";//校验钱
    				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
    				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
    				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
//    				String PCH_PRICE = qs.getString(i+1, "PCH_PRICE"); //采购价格
    				String PLAN_PRICE = qs.getString(i+1, "PLAN_PRICE"); //计划价格
    				String SALE_PRICE = qs.getString(i+1, "SALE_PRICE"); //销售价格
    				String RETAIL_PRICE = qs.getString(i+1, "RETAIL_PRICE"); //零售价格
//    				String SUPPLIER_CODE = qs.getString(i+1, "SUPPLIER_CODE"); //供应商代码
//    				String SUPPLIER_NAME = qs.getString(i+1, "SUPPLIER_NAME"); //供应商名称

    				
    	   //1.必填项空校验
    				//配件代码
    				if(null==PART_CODE || "".equals(PART_CODE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("配件代码不能为空!");
                        errorList.add(errors);
                    }
    				//配件名称
    				if(null==PART_NAME || "".equals(PART_NAME)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("配件名称不能为空!");
                        errorList.add(errors);
                    }
//    				//采购价格
//    				if(null==PCH_PRICE || "".equals(PCH_PRICE)){
//    					errors=new ExcelErrors();
//    					errors.setRowNum(Integer.parseInt(rowNum));
//    					errors.setErrorDesc("采购价格不能为空!");
//                        errorList.add(errors);
//                    }
    				
    				//计划价格
    				if(null==PLAN_PRICE || "".equals(PLAN_PRICE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("计划价格不能为空!");
                        errorList.add(errors);
                    }
    				
    				//销售价格
    				if(null==SALE_PRICE || "".equals(SALE_PRICE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("销售价格不能为空!");
                        errorList.add(errors);
                    }
    				
    				//零售价格
    				if(null==RETAIL_PRICE || "".equals(RETAIL_PRICE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("零售价格不能为空!");
                        errorList.add(errors);
                    }
    				
//    				//供应商代码
//    				if(null==SUPPLIER_CODE || "".equals(SUPPLIER_CODE)){
//    					errors=new ExcelErrors();
//    					errors.setRowNum(Integer.parseInt(rowNum));
//    					errors.setErrorDesc("供应商代码不能为空!");
//                        errorList.add(errors);
//                    }
//    				
//    				//供应商名称
//    				if(null==SUPPLIER_NAME || "".equals(SUPPLIER_NAME)){
//    					errors=new ExcelErrors();
//    					errors.setRowNum(Integer.parseInt(rowNum));
//    					errors.setErrorDesc("供应商名称不能为空!");
//                        errorList.add(errors);
//                    }
    				
    		
    			}
    			//2.重复数据校验,零时表中存在相同的配件代码则必须删除一个		
    			QuerySet qs2 = dao.searchPtBaPriceTmpRepeatData(user,"",factory); 
    			if(qs2.getRowCount() > 0)
    			{
    				for(int j=0; j<qs2.getRowCount(); j++){
    					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
    					
    					String errorStr = "";
    					QuerySet qs3 = dao.searchPtBaPriceTmpRepeatData(user, partCode,factory);
    					for(int k=0; k<qs3.getRowCount(); k++){
    						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
    						
    						errors=new ExcelErrors();
    						errors.setRowNum(Integer.parseInt(rowNum3));
    						
    						if (k != (qs3.getRowCount() -1)) {
    							errors.setErrorDesc("配件代码是重复数据!");
    							errorList.add(errors);
    						}
    						
    						errorStr = errorStr + rowNum3 + ",";
    					}
    					
    					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
    					errorStr = errorStr + "配件代码重复，请删除重复数据！重复行是("+errorStr+")！";
    					
    					//添加错误描述
    					errors.setErrorDesc(errorStr);
    					errorList.add(errors);
    				}
    			}
    		}
            
            factory.getConnection().commit();
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
            }
    }
  //yhw配件采购价格明细，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_PCH_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
//        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
//        DBFactory factory = new DBFactory();
//        ActionContext atx = ActionContext.getContext();
//        atx.setDBFactory(factory);
//        //校验临时表数据
//        PtBaInfoAction check = new PtBaInfoAction();  
//        errList = check.checkPchPriceData();
    	DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            ExcelErrors errors = null;
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            PtBaInfoCheckDao dao = new PtBaInfoCheckDao();
            
            // 价格无改动
            QuerySet qs0 = dao.ptBaPchPriceTrueTmpCheck(user, factory);
            if (qs0.getRowCount() >0) {
            	for(int i=0;i<qs0.getRowCount();i++){
            		String rowNum = qs0.getString(i+1, "ROW_NUM"); //行号
            		errors=new ExcelErrors();
            		errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件价格无修改.");
                    errorList.add(errors);
            	}
            }
            
            // 配件不存在
            QuerySet qs01 = dao.ptBaPchPriceTrueTmpCheck1(user, factory);
            if (qs0.getRowCount() >0) {
            	for(int i=0;i<qs01.getRowCount();i++){
            		String rowNum = qs01.getString(i+1, "ROW_NUM"); //行号
            		errors=new ExcelErrors();
            		errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件不存在.");
                    errorList.add(errors);
            	}
            }
            QuerySet qs = dao.searchPtBaPchPriceTrueTmp(user, "",factory);//查询此用户下的所有配件采购价格临时表信息
    		if(qs.getRowCount()>0){
    			for(int i=0;i<qs.getRowCount();i++){
    				errors=new ExcelErrors();
    				String p="^(0|[1-9][0-9]*)$";//校验钱
    				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
    				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
    				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
    				String SUPPLIER_CODE = qs.getString(i+1, "SUPPLIER_CODE"); //供应商代码
    				String SUPPLIER_NAME = qs.getString(i+1, "SUPPLIER_NAME"); //供应商名称
    				String PCH_PRICE = qs.getString(i+1, "PCH_PRICE"); //采购价格

    				
    	   //1.必填项空校验
    				//配件代码
    				if(null==PART_CODE || "".equals(PART_CODE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("配件代码不能为空!");
                        errorList.add(errors);
                    }
    				//配件名称
    				if(null==PART_NAME || "".equals(PART_NAME)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("配件名称不能为空!");
                        errorList.add(errors);
                    }
    				//供应商代码
    				if(null==SUPPLIER_CODE || "".equals(SUPPLIER_CODE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("供应商代码不能为空!");
                        errorList.add(errors);
                    }
    				
    				//供应商名称
    				if(null==SUPPLIER_NAME || "".equals(SUPPLIER_NAME)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("供应商名称不能为空!");
                        errorList.add(errors);
                    }
    				//采购价格
    				if(null==PCH_PRICE || "".equals(PCH_PRICE)){
    					errors=new ExcelErrors();
    					errors.setRowNum(Integer.parseInt(rowNum));
    					errors.setErrorDesc("采购价格不能为空!");
                        errorList.add(errors);
                    }
    				
    				
    				
    				
    				
    				
    		
    			}
    			//2.重复数据校验,零时表中存在相同的(配件代码+供应商代码)则必须删除一个		
    			QuerySet qs2 = dao.searchPtBaPchPriceTmpRepeatData(user,"","",factory); 
    			if(qs2.getRowCount() > 0)
    			{
    				for(int j=0; j<qs2.getRowCount(); j++){
    					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
    					String supplierCode = qs2.getString(j+1, "SUPPLIER_CODE"); //供应商代码
    					
    					String errorStr = "";
    					QuerySet qs3 = dao.searchPtBaPchPriceTmpRepeatData(user, partCode, supplierCode,factory);
    					for(int k=0; k<qs3.getRowCount(); k++){
    						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
    						
    						errors=new ExcelErrors();
    						errors.setRowNum(Integer.parseInt(rowNum3));
    						
    						if (k != (qs3.getRowCount() -1)) {
    							errors.setErrorDesc("配件代码+供应商代码是重复数据!");
    							errorList.add(errors);
    						}
    						
    						errorStr = errorStr + rowNum3 + ",";
    					}
    					
    					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
    					errorStr = errorStr + "配件代+供应商代码重复，请删除重复数据！重复行是("+errorStr+")！";
    					
    					//添加错误描述
    					errors.setErrorDesc(errorStr);
    					errorList.add(errors);
    				}
    			}
    		}
            
            
            factory.getConnection().commit();
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
            }
    }
  //yhw配件库存盘点信息，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_INVENTORY_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
/*        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        11
        //校验临时表数据
//        PtBuInventoryAction check = new PtBuInventoryAction();  
//        errList = check.checkData();
        
        return errList;*/

    	String INVENTORY_ID = bParams;
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        PtBuInventoryImpCheckDao dao = new PtBuInventoryImpCheckDao();
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        QuerySet check01 = dao.checkExist(factory,user);//校验导入配件是否存在配件主信息表
        if(check01.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check01.getRowCount();i++){
                String TMP_NO = check01.getString(i, "ROW_NUM");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码不存在！");
                errorList.add(errors);
            }
            
        }
/*        QuerySet check02 = dao.checkArea(factory,user,INVENTORY_ID);//校验导入配件对应库区信息是否存在
        if(check02.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check02.getRowCount();i++){
                String TMP_NO = check02.getString(i, "ROW_NUM");
                String PART_CODE = check02.getString(i, "PART_CODE");
                String AREA_CODE = check02.getString(i, "AREA_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"不存在库区"+AREA_CODE+"中！");
            }
            errorList.add(errors);
        }*/
        QuerySet check03 = dao.checkPosi(factory,user,INVENTORY_ID);//校验导入配件对应库区信息是否存在
        if(check03.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check03.getRowCount();i++){
                String TMP_NO = check03.getString(i, "ROW_NUM");
                String PART_CODE = check03.getString(i, "PART_CODE");
                String POSITION_NAME = check03.getString(i, "POSITION_NAME");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"不存在库位"+POSITION_NAME+"中！");
                errorList.add(errors);
            }
            
        }
        QuerySet check04 = dao.checkUnique(factory,user);//校验导入配件对应库区信息是否存在
        if(check04.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check04.getRowCount();i++){
                String TMP_NO = check04.getString(i, "ROW_NUM");
                String PART_CODE = check04.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"重复！");
                errorList.add(errors);
            }
        }
        QuerySet check05 = dao.checkOther(factory,user);//校验导入配件对应库区信息是否存在
        if(check05.getRowCount()>0){
            errors=new ExcelErrors();
            for(int i = 1;i<=check05.getRowCount();i++){
                String TMP_NO = check05.getString(i, "ROW_NUM");
                String PART_CODE = check05.getString(i, "PART_CODE");
                errors.setRowNum(Integer.parseInt(TMP_NO));
                errors.setErrorDesc("第"+TMP_NO+"行配件代码"+PART_CODE+"已存在在其他盘点中的盘点清单中不能重复盘点！");
                errorList.add(errors);
            }
        }
        if(errorList!=null&&errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
    
    }
  //yhw配件图片明细，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BA_PICTURE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        PtBaPictureAction check = new PtBaPictureAction();  
        errList = check.checkData();
        
        return errList;
    }
  //yhw服务索赔价格信息导入，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_CL_PRICE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        ServicePriceAction check = new ServicePriceAction();  
        errList = check.checkClData();
        
        return errList;
    }
  //yhw服务追偿价格信息导入，校验零时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_RE_PRICE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        ServicePriceAction check = new ServicePriceAction();  
        errList = check.checkReData();
        
        return errList;
    }
    
    //配件订单明细导入，校验导入临时表中的数据
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_SALE_ORDER_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {
        
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            ExcelErrors errors = null;
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            String orgType = user.getOrgDept().getOrgType();
            PartOrderImportCheckDao dao = new PartOrderImportCheckDao();
            QuerySet qs = dao.partOrderInfoSearch(bParams,factory);
            if(qs.getRowCount()>0){
                String orderType = qs.getString(1,"ORDER_TYPE");
                String promId = "";
                String directTypeId = "";
                if(DicConstant.DDLX_06.equals(orderType)){
                    promId = qs.getString(1, "PROM_ID");
                }
                if(DicConstant.DDLX_05.equals(orderType)){
                    directTypeId = qs.getString(1, "DIRECT_TYPE_ID");
                }
                QuerySet qs1 = dao.partBaInfoSearch(user,factory);
                if(qs1.getRowCount()>0){
                    for(int i=0;i<qs1.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs1.getString(i+1, "ROW_NO")));
                        String partCode = qs1.getString(i+1, "PART_CODE"); 
                        errors.setErrorDesc(partCode+"配件不存在.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs2 = dao.partInfoSearch(user,orderType,promId,directTypeId,factory);
                if(qs2.getRowCount()>0){
                    for(int i=0;i<qs2.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs2.getString(i+1, "ROW_NO")));
                        String partCode = qs2.getString(i+1, "PART_CODE"); 
                        errors.setErrorDesc(partCode+"配件不符合订单要求.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs3 = dao.supplierBaInfoSearch(user,factory);
                if(qs3.getRowCount()>0){
                    for(int i=0;i<qs3.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs3.getString(i+1, "ROW_NO")));
                        String supplierCode = qs3.getString(i+1, "SUPPLIER_CODE"); 
                        errors.setErrorDesc(supplierCode+"供应商不存在.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs4 = dao.partSupplierBaInfoSearch(user,factory);
                if(qs4.getRowCount()>0){
                    for(int i=0;i<qs4.getRowCount();i++){
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs4.getString(i+1, "ROW_NO")));
                        String partCode = qs4.getString(i+1, "PART_CODE");
                        String supplierCode = qs4.getString(i+1, "SUPPLIER_CODE"); 
                        errors.setErrorDesc(partCode+"配件需指定的供应商，且与"+supplierCode+"供应商不存在供货关系.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs5 = dao.partOrderCountSearch(user,factory);
                if(qs5.getRowCount()>0){
                    for(int i=0;i<qs5.getRowCount();i++){
                        String partCode = qs5.getString(i+1, "PART_CODE");
                        String count = qs5.getString(i+1, "COUNT");
                        String minPack = qs5.getString(i+1, "MIN_PACK");
//                        String reg = "^[1-9]d*$";
                        Pattern pattern = Pattern.compile("[0-9]*");  
                        Matcher matcher = pattern.matcher(count);  
                        if ("".equals(count)) {
                        	errors=new ExcelErrors();
                            errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                            errors.setErrorDesc(partCode+"配件,订购数量为空.");
                            errorList.add(errors);
                            continue;
                        }
                        if (matcher.matches() == false) {
                            errors=new ExcelErrors();
                            errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                            errors.setErrorDesc(partCode+"配件,订购数量不正确.");
                            errorList.add(errors);
                        } else {
                        	if (count.indexOf("0")!=0) {
                        		if(DicConstant.ZZLB_09.equals(orgType)){
                        			if(!(Integer.parseInt(count)%Integer.parseInt(minPack)==0)){
                        				errors=new ExcelErrors();
                        				errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                        				errors.setErrorDesc(partCode+"配件,订购数量应为最小包装数量"+minPack+"的倍数.");
                        				errorList.add(errors);
                        			}
                        		}
                        	} else {
                        		errors=new ExcelErrors();
                                errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                                errors.setErrorDesc(partCode+"配件,订购数量不正确.");
                                errorList.add(errors);
                        	}
                        }
                    }
                }
                QuerySet qs6 = dao.supplierCheck(user,factory);
                if(qs6.getRowCount()>0){
                    for(int i=0;i<qs6.getRowCount();i++){
                        String partCode = qs6.getString(i+1, "PART_CODE");
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs6.getString(i+1, "ROW_NO")));
                        errors.setErrorDesc(partCode+"请指定供应商.");
                        errorList.add(errors);
                    }
                }
                QuerySet qs7 = dao.partOrderPriceSearch(user,factory,orderType);
                if(qs7.getRowCount()>0){
                    for(int i=0;i<qs7.getRowCount();i++){
                        String partCode = qs7.getString(i+1, "PART_CODE");
                        errors=new ExcelErrors();
                        errors.setRowNum(Integer.parseInt(qs7.getString(i+1, "ROW_NO")));
                        if (DicConstant.DDLX_07.equals(orderType)) {
                 		   // 直营订单
                           errors.setErrorDesc(partCode+"配件没有零售价格,不能提报.");
                 	   } else if (DicConstant.DDLX_10.equals(orderType)) {
                 		   // 技术升级订单   
                 		  errors.setErrorDesc(partCode+"配件没有计划价格,不能提报.");
                 	   } else {
                 		   // 普通订单
                 		   errors.setErrorDesc(partCode+"配件没有销售价格,不能提报.");
                 	   }
                        errorList.add(errors);
                    }
                }
            }
            factory.getConnection().commit();
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
            }
    }
    
    
    
    public static List<ExcelErrors> checkPT_BU_ASALE_ORDER_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {
      
      DBFactory factory = new DBFactory();
      ActionContext atx = ActionContext.getContext();
      atx.setDBFactory(factory);
      try {
          ExcelErrors errors = null;
          List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
          String orgType = user.getOrgDept().getOrgType();
          PartOrderImportCheckDao dao = new PartOrderImportCheckDao();
          QuerySet qs = dao.partOrderInfoSearch(bParams,factory);
          if(qs.getRowCount()>0){
              QuerySet qs1 = dao.armyPartBaInfoSearch(user,factory);
              if(qs1.getRowCount()>0){
                  for(int i=0;i<qs1.getRowCount();i++){
                      errors=new ExcelErrors();
                      errors.setRowNum(Integer.parseInt(qs1.getString(i+1, "ROW_NO")));
                      String partCode = qs1.getString(i+1, "PART_CODE"); 
                      errors.setErrorDesc(partCode+"配件不存在.");
                      errorList.add(errors);
                  }
              }
              QuerySet qs3 = dao.supplierBaInfoSearch(user,factory);
              if(qs3.getRowCount()>0){
                  for(int i=0;i<qs3.getRowCount();i++){
                      errors=new ExcelErrors();
                      errors.setRowNum(Integer.parseInt(qs3.getString(i+1, "ROW_NO")));
                      String supplierCode = qs3.getString(i+1, "SUPPLIER_CODE"); 
                      errors.setErrorDesc(supplierCode+"供应商不存在.");
                      errorList.add(errors);
                  }
              }
              QuerySet qs4 = dao.partSupplierBaInfoSearch(user,factory);
              if(qs4.getRowCount()>0){
                  for(int i=0;i<qs4.getRowCount();i++){
                      errors=new ExcelErrors();
                      errors.setRowNum(Integer.parseInt(qs4.getString(i+1, "ROW_NO")));
                      String partCode = qs4.getString(i+1, "PART_CODE");
                      String supplierCode = qs4.getString(i+1, "SUPPLIER_CODE"); 
                      errors.setErrorDesc(partCode+"配件需指定的供应商，且与"+supplierCode+"供应商不存在供货关系.");
                      errorList.add(errors);
                  }
              }
              QuerySet qs5 = dao.partArmyOrderCountSearch(user,factory);
              if(qs5.getRowCount()>0){
                  for(int i=0;i<qs5.getRowCount();i++){
                      String partCode = qs5.getString(i+1, "PART_CODE");
                      String count = qs5.getString(i+1, "COUNT");
                      String minPack = qs5.getString(i+1, "MIN_PACK");
                      Pattern pattern = Pattern.compile("[1-9]*");  
                      Matcher matcher = pattern.matcher(count);  
//                      if (matcher.matches() == false) {
//                          errors=new ExcelErrors();
//                          errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
//                          errors.setErrorDesc(partCode+"配件,订购数量不正确.");
//                          errorList.add(errors);
//                      } else {
                          if(DicConstant.ZZLB_09.equals(orgType)){
                              if(!(Integer.parseInt(count)%Integer.parseInt(minPack)==0)){
                                  errors=new ExcelErrors();
                                  errors.setRowNum(Integer.parseInt(qs5.getString(i+1, "ROW_NO")));
                                  errors.setErrorDesc(partCode+"配件,订购数量应为最小包装数量"+minPack+"的倍数.");
                                  errorList.add(errors);
                              }
                          }
//                      }
                  }
              }
              QuerySet qs6 = dao.supplierCheck(user,factory);
              if(qs6.getRowCount()>0){
                  for(int i=0;i<qs6.getRowCount();i++){
                      String partCode = qs6.getString(i+1, "PART_CODE");
                      errors=new ExcelErrors();
                      errors.setRowNum(Integer.parseInt(qs6.getString(i+1, "ROW_NO")));
                      errors.setErrorDesc(partCode+"请指定供应商.");
                      errorList.add(errors);
                  }
              }
              QuerySet qs7 = dao.partOrderArmyPriceSearch(user,factory);
              if(qs7.getRowCount()>0){
                  for(int i=0;i<qs7.getRowCount();i++){
                      String partCode = qs7.getString(i+1, "PART_CODE");
                      errors=new ExcelErrors();
                      errors.setRowNum(Integer.parseInt(qs7.getString(i+1, "ROW_NO")));
                      errors.setErrorDesc(partCode+"配件没有价格,不能提报.");
                      errorList.add(errors);
                  }
              }
          }
          factory.getConnection().commit();
          return errorList;
      } catch (Exception e) {
          factory.getConnection().rollback();
          e.printStackTrace();
          return null;
      } finally {
          if(factory != null)
              factory.getConnection().close();
          factory.setFactory(null);
          factory = null;
          }
  }
    
    /**
     * 配件管理-旧件回运导入，校验导入临时表中的数据
     * 
     * @param list
     * @param user
     * @param params
     * @return
     * @throws Exception 
     */
    public static List<ExcelErrors> check_PT_BU_CLAIM_APPLY_TMP(List<Map<?,?>> list, User user, String params) throws Exception{
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory dbFactory = null;
        try {
            
            // TODO Dao对象继承BaseDao，导致每次New生成新的Connection连接
            OldPartPackPJGLDao dao = new OldPartPackPJGLDao(); 
            
            // 所以对Dao对象，添加getFactory方法，使得在方法中手工关闭连接。
            dbFactory = dao.getDBFactory();
            
            //校验临时表数据
            ExcelErrors errors = null;
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            QuerySet qs = dao.searchImportDataByAccount(user);
            for(int i = 0 ; i < qs.getRowCount() ; i++){
                int applyId = 0;
                
                try {
                    applyId = Integer.parseInt(qs.getString(i + 1, "APPLY_ID"));
                } catch (Exception e) {
                    e.printStackTrace();
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs.getString(i+1, "ROW_NO")));
                    errors.setErrorDesc("APPLY_ID = "+applyId + ", 不正确");
                    errorList.add(errors);
                }
                
                // 根据申请单applyId查询数量
                if(Integer.parseInt(dao.searchPartDataByApplyId(applyId).getString(1, "CLAIM_APPLY_COUNT")) <= 0){
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs.getString(i+1, "ROW_NO")));
                    String applyNo = qs.getString(i+1, "APPLY_NO"); 
                    errors.setErrorDesc("APPLY_ID = "+applyId + ", APPLY_NO = " + applyNo +" , 系统不存在此数据.");
                    errorList.add(errors);
                }
                
            /*                
             * 不校验箱号
                  // 添加数字字母正则验证：true是满足条件，false不满足条件
                Pattern pattern = Pattern.compile("^[\\d\\w]+$");
                 String boxNo = qs.getString(i + 1, "BOX_NO");
                
                // 添加数字字母正则验证：true是满足条件，false不满足条件
                if(!pattern.matcher(boxNo).matches()){
                    errors=new ExcelErrors();
                    errors.setRowNum(Integer.parseInt(qs.getString(i+1, "ROW_NO")));
                    errors.setErrorDesc("BOX_NO = " + boxNo + " , Box只能为数字或字母组合");
                    errorList.add(errors);
                }
            */
            }
            if(errorList!=null && errorList.size()>0){
                return errorList;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             if(dbFactory != null) {
                 dbFactory.getConnection().close();
             }
        }
        return errList;
    }
    
  //批量调整导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BU_RETURN_ORDER_TMP(List<Map> list,User user,String bParams)throws Exception 
    {
        
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            ExcelErrors errors = null;
            OldPartImportCheckDao dao = new OldPartImportCheckDao();
            
            factory.getConnection().commit();

            QuerySet qs1=dao.queryReturnNo(user,factory);//回运单号是否存在
            if(qs1.getRowCount()>0){
                for(int i=0;i<qs1.getRowCount();i++){
                    errors=new ExcelErrors();
                    String returnOrderNo =qs1.getString(i+1, "RETURN_ORDER_NO");
                    String rowNo =qs1.getString(i+1, "ROW_NO");
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("回运单号："+returnOrderNo+"不存在！");
                    errorList.add(errors);
                }
            }
            QuerySet qs2 = dao.queryClaimNo(user,factory);//判断索赔单号在回运单中是否存在
            if(qs2.getRowCount()>0){
                for(int j=0;j<qs2.getRowCount();j++){
                    errors=new ExcelErrors();
                    String rowNo =qs2.getString(j+1, "ROW_NO");
                    String claimNo = qs2.getString(j+1, "CLAIM_NO");
                    String returnOrderNo =qs2.getString(j+1, "RETURN_ORDER_NO");
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("索赔单号："+claimNo+"在回运单："+returnOrderNo+"中不存在！");
                    errorList.add(errors);
                }
            }
            QuerySet qs3 = dao.queryNos(user,factory);//判断索赔单号、回运单号、配件代码、供应商、故障是否一致
            if(qs3.getRowCount()>0){
                for(int k=0;k<qs3.getRowCount();k++){
                    errors=new ExcelErrors();
                    String rowNo =qs3.getString(k+1, "ROW_NO");
                    String claimNo = qs3.getString(k+1, "CLAIM_NO");
                    String partCode=qs3.getString(k+1, "PART_CODE");
                    String returnOrderNo =qs3.getString(k+1, "RETURN_ORDER_NO");
                    String faultCode=qs3.getString(k+1, "FAULT_CODE");
                    String supplierCode=qs3.getString(k+1, "SUPPLIER_CODE");
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("索赔单号："+claimNo+",回运单号："+returnOrderNo+",旧件代码："+partCode+",供应商代码："+supplierCode+",故障代码："+faultCode+"数据不一致！");
                    errorList.add(errors);
                }
            }
            QuerySet qs4 = dao.queryStatus(user,factory);//判断状态
            if(qs4.getRowCount()>0){
                for(int l=0;l<qs4.getRowCount();l++){
                    errors=new ExcelErrors();
                    String rowNo =qs4.getString(l+1, "ROW_NO");
                    String claimNo = qs4.getString(l+1, "CLAIM_NO");
                    String partCode=qs4.getString(l+1, "PART_CODE");
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("索赔单号："+claimNo+"中的旧件代码为："+partCode+"已审核通过，或作废。不可再次更改状态！");
                    errorList.add(errors);
                }
            }
            QuerySet qs5=dao.checkList3(user,factory);//判断导入数据是否重复
            if(qs5.getRowCount()>0){
                for(int h=0;h<qs5.getRowCount();h++){
                    errors=new ExcelErrors();
                    String rowNo = qs5.getString(h+1, "ROW_NO");
                    String claimNo = qs5.getString(h+1, "CLAIM_NO");
                    String partCode=qs5.getString(h+1, "PART_CODE");
                    String returnOrderNo =qs5.getString(h+1, "RETURN_ORDER_NO");
                    String faultCode=qs5.getString(h+1, "FAULT_CODE");
                    String supplierCode=qs5.getString(h+1, "SUPPLIER_CODE");
                    errors.setRowNum(Integer.parseInt(rowNo));
                    errors.setErrorDesc("索赔单号："+claimNo+",回运单号："+returnOrderNo+",旧件代码："+partCode+",供应商代码："+supplierCode+",故障代码："+faultCode+"在导入模板中第"+rowNo+"行已重复填写");
                    errorList.add(errors);
                }
            }
            QuerySet qs = dao.queryImp(user,factory);
            for(int i=0;i<qs.getRowCount();i++){
                String returnOrderNo =qs.getString(i+1, "RETURN_ORDER_NO");
                String claimNo = qs.getString(i+1, "CLAIM_NO");
                String faultCode=qs.getString(i+1, "FAULT_CODE");
                String partCode=qs.getString(i+1, "PART_CODE");
                String rowNo =qs.getString(i+1, "ROW_NO");
                String supplierCode=qs.getString(i+1, "SUPPLIER_CODE");
                String oldPartStatus=qs.getString(i+1, "OLD_PART_STATUS");
                String amount=qs.getString(i+1, "AMOUNT");//Excel中填写的数量
                QuerySet qids=dao.queryid(claimNo, faultCode, partCode, supplierCode, returnOrderNo,factory);
                String oughtCount=qids.getString(1, "OUGHT_COUNT");
                String actulCount=qids.getString(1, "ACTUL_COUNT");
                String missCount=qids.getString(1, "MISS_COUNT");
                String shoulCount=qids.getString(1, "SHOULD_COUNT");
                String ropStatus=qids.getString(1, "OLD_PART_STATUS");//旧件原状态
                if(null==amount||amount.equals("")){
                    errors=new ExcelErrors();
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("没有填写数量。");
                    errorList.add(errors);
                }
                if(Integer.valueOf(amount)<0){
                	errors=new ExcelErrors();
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("数量不可填写负值。");
                    errorList.add(errors);
                }
                if(null==oldPartStatus||oldPartStatus.equals("")){
                    errors=new ExcelErrors();
                    if(null==rowNo||rowNo.equals("")){
                    }else{
                        errors.setRowNum(Integer.parseInt(rowNo));
                    }
                    errors.setErrorDesc("没有选择旧件状态。");
                    errorList.add(errors);
                }
                if(oldPartStatus.equals("待审")){//如果目标状态为“待审”
                    if(Integer.parseInt(amount)+Integer.parseInt(actulCount)>Integer.parseInt(oughtCount)){
                        errors=new ExcelErrors();
                        if(null==rowNo||rowNo.equals("")){
                        }else{
                            errors.setRowNum(Integer.parseInt(rowNo));
                        }
                        errors.setErrorDesc("填写的数量大于实际返回旧件数量。");
                        errorList.add(errors);
                    }
                    if(ropStatus.equals(DicConstant.JJZT_02)){
                        errors=new ExcelErrors();
                        if(null==rowNo||rowNo.equals("")){
                        }else{
                            errors.setRowNum(Integer.parseInt(rowNo));
                        }
                        errors.setErrorDesc("该配件已审核通过，不可修改。");
                        errorList.add(errors);
                    }
                }else if(oldPartStatus.equals("正常")){//旧件目标状态为正常。
                    if(ropStatus.equals(DicConstant.JJZT_03)){
                        if(Integer.parseInt(amount)>Integer.parseInt(actulCount)){
                            errors=new ExcelErrors();
                            if(null==rowNo||rowNo.equals("")){
                            }else{
                                errors.setRowNum(Integer.parseInt(rowNo));
                            }
                            errors.setErrorDesc("填写的数量大于待审数量。");
                            errorList.add(errors);
                        }
                    }else if (ropStatus.equals(DicConstant.JJZT_04)){
                        if(Integer.parseInt(amount)>Integer.parseInt(missCount)){
                            errors=new ExcelErrors();
                            if(null==rowNo||rowNo.equals("")){
                            }else{
                                errors.setRowNum(Integer.parseInt(rowNo));
                            }
                            errors.setErrorDesc("填写的数量大于缺失数量。");
                            errorList.add(errors);
                        }
                    }else if(ropStatus.equals(DicConstant.JJZT_01)){
                        errors=new ExcelErrors();
                        if(null==rowNo||rowNo.equals("")){
                        }else{
                            errors.setRowNum(Integer.parseInt(rowNo));
                        }
                        errors.setErrorDesc("该配件状态已为正常状态，不需要修改。");
                        errorList.add(errors);
                    }else if(ropStatus.equals(DicConstant.JJZT_02)){
                        errors=new ExcelErrors();
                        if(null==rowNo||rowNo.equals("")){
                        }else{
                            errors.setRowNum(Integer.parseInt(rowNo));
                        }
                        errors.setErrorDesc("该配件已审核通过，不可修改。");
                        errorList.add(errors);
                    }
                }else if(oldPartStatus.equals("缺失")) {
                	if(Integer.valueOf(amount)+Integer.valueOf(missCount)>Integer.valueOf(shoulCount)){
                		errors=new ExcelErrors();
                        if(null==rowNo||rowNo.equals("")){
                        }else{
                            errors.setRowNum(Integer.parseInt(rowNo));
                        }
                        errors.setErrorDesc("填写的数量加上已缺失的数量不可以大于索赔单产生的总数量。");
                        errorList.add(errors);
                	}
                }
            }
            //校验临时表数据
            return errorList;
        }
        
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    
    
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BU_CLAIM_SETTLE_MODIFY_TMP(List<Map> list,User user,String bParams) throws Exception {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        PaymentAction check = new PaymentAction();  
        errList = check.checkSettlePayUpdate();
        return errList;
    }
    
    /**
     * 车辆导入
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkMAIN_VEHICLE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            ExcelErrors errors = null;
            MainVehicleSetImportCheckDao dao = new MainVehicleSetImportCheckDao();
            
            factory.getConnection().commit();

            QuerySet qs1=dao.checkList1(user,factory);//校验数据是否重复
    		QuerySet qs2=dao.checkList2(user,factory);//校验数据是否在车辆表中存在
    		QuerySet qs3=dao.checkList3(user,factory);//校验数据是否在车辆表中存在
    		if(qs1.getRowCount()>0){
    			for(int i=0;i<qs1.getRowCount();i++){
    				errors=new ExcelErrors();
    				String vin =qs1.getString(i+1,"VIN");
    				String rowNo =qs1.getString(i+1,"ROW_NO");
    				errors.setRowNum(Integer.parseInt(rowNo));
    				errors.setErrorDesc("导入数据中的vin:"+vin+"在第"+rowNo+"重复！");
    				errorList.add(errors);
    			}
    		}
    		if(qs2.getRowCount()>0){
    			for(int i=0;i<qs2.getRowCount();i++){
    				errors=new ExcelErrors();
    				String rowNo =qs2.getString(i+1,"ROW_NO");
    				String vin =qs2.getString(i+1,"VIN");
    				errors.setRowNum(Integer.parseInt(rowNo));
    				errors.setErrorDesc("导入数据中的vin:"+vin+"在车辆表中存在！");
    				errorList.add(errors);
    			}
    		}
    		if(qs3.getRowCount()>0){
    			for(int i=0;i<qs3.getRowCount();i++){
    				errors=new ExcelErrors();
    				String rowNo =qs3.getString(i+1,"ROW_NO");
    				String vin =qs3.getString(i+1,"VIN");
    				errors.setRowNum(Integer.parseInt(rowNo));
    				errors.setErrorDesc("导入数据中的vin:"+vin+"位数不足,或者超过17位。");
    				errorList.add(errors);
    			}
    		}
            //校验临时表数据
            return errorList;
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
            factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    //bxl外出费用导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_TRAVEL_COST_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        TravelCostMngAction check = new TravelCostMngAction();
        errList = check.checkData();
        
        return errList;
    }
    //bxl工时单价导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_CLAIM_CODE_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        ClaimCodeMngAction check = new ClaimCodeMngAction();
        errList = check.checkData();
        
        return errList;
    }
    //bxl工时定额导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_TASK_AMOUNT_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        TaskAmountMngAction check = new TaskAmountMngAction();
        errList = check.checkData();
        
        return errList;
    }
    //bxl故障模式与工时定额关系导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_FAULT_TASKTIME_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        FaultTasktimeMngAction check = new FaultTasktimeMngAction();
        errList = check.checkData();
        
        return errList;
    }
    //bxl激励系数导入
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_EXCITATION_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        ExcitationCoefficientMngAction check = new ExcitationCoefficientMngAction();
        errList = check.checkData();
        
        return errList;
    }
    
    /**
     * 车辆导入
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BU_RETURNORDER_PACK_FOCUS(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        OldPartReturnAction mybean = new OldPartReturnAction();  
        errList = mybean.checkData();
        return errList;
    }
    /**
     * 车辆导入
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_MOVE_OUT_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        MoveStockOutMngAction mybean = new MoveStockOutMngAction();  
        errList = mybean.checkData(bParams);
        return errList;
    }
    
    //服务商星级导入   fanpeng 2014-09-26
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkMAIN_DEALER_STAR_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        //校验临时表数据
        DealerStarMngAction check = new DealerStarMngAction();
        errList = check.checkData();
        
        return errList;
    }

    /**
     * 配件账户(材料费，返利)导入
     * @param list
     * @param user
     * @param bParams
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_ACCOUNT_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            CashAccountImportCheckDao dao = new CashAccountImportCheckDao();
            // 查询导入的所有数据
            QuerySet querySet1 = dao.searchAmount(user,factory);
            if (querySet1.getRowCount() > 0) {
    
                // 渠道商账户是否存在验证
                QuerySet querySet = dao.accountIdCheck(user,bParams,factory);
                if (querySet.getRowCount() > 0) {
                    for (int i =0;i<querySet.getRowCount();i++) {
                        ExcelErrors excelErrors = new ExcelErrors();
                        String orgCode = querySet1.getString(i+1, "ORG_CODE");
                        String tmpNo = querySet1.getString(i+1, "TMP_NO");
                        // 渠道商账户不存在
                        excelErrors.setRowNum(Integer.valueOf(tmpNo));
                        excelErrors.setErrorDesc("渠道商"+orgCode+"：不存在.");
                        errorList.add(excelErrors);
                    }
                }
    
                if (querySet1.getRowCount() > 0) {
                    for (int j=0;j<querySet1.getRowCount();j++) {
                        String amount = querySet1.getString(j+1, "AMOUNT");
                        String orgCode = querySet1.getString(j+1, "ORG_CODE");
                        String tmpNo = querySet1.getString(j+1, "TMP_NO");
                        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
                        java.util.regex.Matcher match=pattern.matcher(amount); 
                        if(match.matches()==false) {
                            ExcelErrors excelErrors = new ExcelErrors();
                            // 金额不正确
                            excelErrors.setRowNum(Integer.valueOf(tmpNo));
                            excelErrors.setErrorDesc("渠道商"+orgCode+"：金额格式错误.");
                            errorList.add(excelErrors);
                        }
                    }
                }
            }
            factory.getConnection().commit();
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkPT_BU_SUP_INVOICE_SUMMARY_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            SupplierSettlementCheckDao dao = new SupplierSettlementCheckDao();
            // 配件重复验证
            QuerySet querySet1 = dao.checkSet(user,factory);
            // 配件是否存在验证
            QuerySet querySet2 = dao.checkSup(user,factory);
            // 配件数量验证
            QuerySet querySet3 = dao.checkAmount(user,factory);
    
            factory.getConnection().commit();

            if (querySet1.getRowCount() > 0) {
                // 配件重复验证
                for (int i=0;i<querySet1.getRowCount();i++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String SUPPLIER_CODE = querySet1.getString(i+1, "SUPPLIER_CODE");
                    // 行号
                    String tmpNo = querySet1.getString(i+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("供应商:"+SUPPLIER_CODE+"已结算！");
                    errorList.add(excelErrors);
                }
            }

            if (querySet2.getRowCount() > 0) {
                // 配件是否存在验证
                for (int k=0;k<querySet2.getRowCount();k++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String SUPPLIER_CODE = querySet2.getString(k+1, "SUPPLIER_CODE");
                    // 行号
                    String tmpNo = querySet2.getString(k+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("供应商:"+SUPPLIER_CODE+"不存在");
                    errorList.add(excelErrors);
                }
            }

            if (querySet3.getRowCount() > 0) {
                for (int j=0;j<querySet3.getRowCount();j++) {
                    ExcelErrors excelErrors = new ExcelErrors();
                    // 配件代码
                    String SUPPLIER_CODE = querySet3.getString(j+1, "SUPPLIER_CODE");
                    // 行号
                    String tmpNo = querySet3.getString(j+1, "TMP_NO");
                    excelErrors.setRowNum(Integer.valueOf(tmpNo));
                    excelErrors.setErrorDesc("供应商:"+SUPPLIER_CODE+"结算金额为0");
                    errorList.add(excelErrors);
                }
            }

            //校验临时表数据
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
        }
    }
    @SuppressWarnings("rawtypes")
    public static List<ExcelErrors> checkSE_BA_OLDPARTMANAGE_TMP(List<Map> list,User user,String bParams) 
	    throws Exception 
	    {
	        
	        DBFactory factory = new DBFactory();
	        ActionContext atx = ActionContext.getContext();
	        atx.setDBFactory(factory);
	        try {
	            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
	            ExcelErrors errors = null;
	            OldPartManageCheckDao dao = new OldPartManageCheckDao();
	            
	            factory.getConnection().commit();
	            QuerySet qs1=dao.checkList1(user,factory);//代码和名称一致
	            QuerySet qs2=dao.checkList2(user,factory);//重复记录
	            QuerySet qs3=dao.checkList3(user,factory);//系数不能为空
	            QuerySet qs4=dao.checkList4(user,factory);//校验必须是数字
	            String p="^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";//校验两位小数
	            if(qs1.getRowCount()>0){
	                for(int i=0;i<qs1.getRowCount();i++){
	                    errors=new ExcelErrors();
	                    String rowNum=qs1.getString(i+1, "ROW_NUM"); 
	                    String partCode=qs1.getString(i+1,"PART_CODE");
	                    String partName=qs1.getString(i+1,"PART_NAME");
	                    errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc( "配件代码:"+partCode+",配件名称:"+partName+"单数据不一致.");
	                    errorList.add(errors);
	                }
	            }
	            if(qs2.getRowCount() > 0){
	            	for(int i=0;i<qs2.getRowCount();i++){
	                    errors=new ExcelErrors();
	                    String rowNum=qs2.getString(i+1, "ROW_NUM"); 
	                    String partCode=qs2.getString(i+1,"PART_CODE");
	                    String partName=qs2.getString(i+1,"PART_NAME");
	                    errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc( "配件代码:"+partCode+",配件名称:"+partName+"数据重复.");
	                    errorList.add(errors);
	                }
	            }
	            if(qs3.getRowCount() > 0){
	            	for(int i=0;i<qs3.getRowCount();i++){
	                    errors=new ExcelErrors();
	                    String rowNum=qs3.getString(i+1, "ROW_NUM"); 
	                    String partCode=qs3.getString(i+1,"PART_CODE");
	                    String partName=qs3.getString(i+1,"PART_NAME");
	                    errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc( "配件代码:"+partCode+",配件名称:"+partName+"中的旧件管理费系数为空.");
	                    errorList.add(errors);
	                }
	            }
	            if(qs4.getRowCount() > 0){
	            	for(int i=0;i<qs4.getRowCount();i++){
	                    errors=new ExcelErrors();
	                    String rowNum=qs4.getString(i+1, "ROW_NUM"); 
	                    String partCode=qs4.getString(i+1,"PART_CODE");
	                    String partName=qs4.getString(i+1,"PART_NAME");
	                    String oldManageFee=qs4.getString(i+1,"OLD_MANAGE_FEE");
	                    errors.setRowNum(Integer.parseInt(rowNum));
	                    
	                   
	    				if(null!=oldManageFee&&!"".equals(oldManageFee)){
	    					if(!oldManageFee.matches(p)){
	    						errors.setRowNum(Integer.parseInt(rowNum));
	                            errors.setErrorDesc("配件代码:"+partCode+",配件名称:"+partName+"请输入正确的旧件管理费系数.");
	                            errorList.add(errors);
	                        }
	                    }
	                }
	            }
	            //校验临时表数据
	            return errorList;
	        }
	        catch (Exception e) {
	            factory.getConnection().rollback();
	            e.printStackTrace();
	            return null;
	        } finally {
	            if(factory != null)
	                factory.getConnection().close();
	            factory.setFactory(null);
	            factory = null;
	        }
	    }
    
    
    public static List<ExcelErrors> checkPT_BA_ARMY_PRICE_TMP(List<Map> list,User user,String bParams) throws Exception {
        
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            ExcelErrors errors = null;
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            PartPriceCheckDao dao = new PartPriceCheckDao();
            QuerySet qs1 =dao.checkPart(user,factory);
            QuerySet qs2 = dao.checkPrice(user,factory);
            QuerySet qs3 = dao.checkZero(user,factory);
            
            
            // 价格无改动
            QuerySet qs0 = dao.ptBaPriceTmpCheck(user, factory);
            if (qs0.getRowCount() >0) {
            	for(int i=0;i<qs0.getRowCount();i++){
            		String rowNum = qs0.getString(i+1, "TMP_NO"); //行号
            		errors=new ExcelErrors();
            		errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件价格无修改.");
                    errorList.add(errors);
            	}
            }
            
            // 配件不存在
            QuerySet qs01 = dao.ptBaPriceTmpCheck1(user, factory);
            if (qs0.getRowCount() >0) {
            	for(int i=0;i<qs01.getRowCount();i++){
            		String rowNum = qs01.getString(i+1, "TMP_NO"); //行号
            		errors=new ExcelErrors();
            		errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件不存在.");
                    errorList.add(errors);
            	}
            }
            if(qs1.getRowCount() > 0){
            	for(int i=0;i<qs1.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum=qs1.getString(i+1, "TMP_NO"); 
                    String partCode=qs1.getString(i+1,"PART_CODE");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc( "配件代码:"+partCode+"不存在");
                    errorList.add(errors);
                }
            }
            if(qs2.getRowCount() > 0){
            	for(int i=0;i<qs2.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum=qs2.getString(i+1, "TMP_NO"); 
                    String partCode=qs2.getString(i+1,"PART_CODE");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc( "配件代码:"+partCode+"输入价格为0");
                    errorList.add(errors);
                }
            }
            String p="^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";
            if(qs3.getRowCount() > 0){
            	for(int i=0;i<qs3.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum=qs3.getString(i+1, "TMP_NO"); 
                    String partCode=qs3.getString(i+1,"PART_CODE");
                    String armyPrice = qs3.getString(i+1, "ARMY_PRICE");
                    if(!"".equals(armyPrice)&&armyPrice!=null){
    					if(!armyPrice.matches(p)){
    						errors.setRowNum(Integer.parseInt(rowNum));
                            errors.setErrorDesc( "配件代码:"+partCode+"输入价格不正确");
                            errorList.add(errors);
                        }
                    }else{
                    	errors.setRowNum(Integer.parseInt(rowNum));
                        errors.setErrorDesc( "配件代码:"+partCode+"输入价格为空");
                        errorList.add(errors);
                    }
                    
                    
                    
                }
            }
            factory.getConnection().commit();
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
            }
    }
public static List<ExcelErrors> checkPT_BU_PART_CONT_CHK_TMP(List<Map> list,User user,String bParams) throws Exception {
        
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
            return errorList;
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
            factory = null;
            }
    }
public static List<ExcelErrors> checkPT_BA_WAREHOUSE_KEEPER_TMP(List<Map> list,User user,String bParams) throws Exception {
    
    DBFactory factory = new DBFactory();
    ActionContext atx = ActionContext.getContext();
    atx.setDBFactory(factory);
    try {
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        WarehouseKeeperCheckDao dao = new WarehouseKeeperCheckDao();
        QuerySet qs1 =dao.checkPart(user,factory);
        QuerySet qs2 = dao.checkWarehouse(user,factory);
        QuerySet qs3 = dao.checkUser(user,factory);
        if(qs1.getRowCount() > 0){
        	for(int i=0;i<qs1.getRowCount();i++){
                errors=new ExcelErrors();
                String rowNum=qs1.getString(i+1, "TMP_NO"); 
                String partCode=qs1.getString(i+1,"PART_CODE");
                errors.setRowNum(Integer.parseInt(rowNum));
                errors.setErrorDesc( "配件代码:"+partCode+"尚未在基础档案中维护");
                errorList.add(errors);
            }
        }
        if(qs2.getRowCount() > 0){
        	for(int i=0;i<qs2.getRowCount();i++){
                errors=new ExcelErrors();
                String rowNum=qs2.getString(i+1, "TMP_NO"); 
                String partCode=qs2.getString(i+1,"WAREHOUSE_CODE");
                errors.setRowNum(Integer.parseInt(rowNum));
                errors.setErrorDesc( "仓库代码:"+partCode+"不存在");
                errorList.add(errors);
            }
        }
        if(qs3.getRowCount() > 0){
        	for(int i=0;i<qs3.getRowCount();i++){
                errors=new ExcelErrors();
                String rowNum=qs3.getString(i+1, "TMP_NO"); 
                String partCode=qs3.getString(i+1,"KEEP_MAN_ACOUNT");
                errors.setRowNum(Integer.parseInt(rowNum));
                errors.setErrorDesc( "用户:"+partCode+"不是库管员");
                errorList.add(errors);
            }
        }
        return errorList;
    } catch (Exception e) {
        factory.getConnection().rollback();
        e.printStackTrace();
        return null;
    } finally {
        if(factory != null)
            factory.getConnection().close();
        factory.setFactory(null);
        factory = null;
        }
}
}