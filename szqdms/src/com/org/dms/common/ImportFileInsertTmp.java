package com.org.dms.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;

import com.org.dms.vo.part.PtBaArmyPriceTmpVO;
import com.org.dms.vo.part.PtBaChannelSafeStocksTmpVO;
import com.org.dms.vo.part.PtBaClaimCycleSetTmpVO;
import com.org.dms.vo.part.PtBaInfoTmpVO;
import com.org.dms.vo.part.PtBaPartSupplierRlTmpVO;
import com.org.dms.vo.part.PtBaPchTmpVO;
import com.org.dms.vo.part.PtBaPictureTmpVO;
import com.org.dms.vo.part.PtBaPriceTmpVO;
import com.org.dms.vo.part.PtBaStockSafeStocksTmpVO;
import com.org.dms.vo.part.PtBaTransportAddressTmpVO;
import com.org.dms.vo.part.PtBaWarehouseKeeperTmpVO;
import com.org.dms.vo.part.PtBuAccountTmpVO;
import com.org.dms.vo.part.PtBuBoxUpTmpVO;
import com.org.dms.vo.part.PtBuClaimApplyTmpVO;
import com.org.dms.vo.part.PtBuForcastDtlTmpVO;
import com.org.dms.vo.part.PtBuInventoryDtlTmpVO;
import com.org.dms.vo.part.PtBuInventoryTmpVO;
import com.org.dms.vo.part.PtBuMoveOutPartTmpVO;
import com.org.dms.vo.part.PtBuPartContChkTmpVO;
import com.org.dms.vo.part.PtBuPchContPartTmpVO;
import com.org.dms.vo.part.PtBuPchOrderPartTmpVO;
import com.org.dms.vo.part.PtBuPchReturnPartTmpVO;
import com.org.dms.vo.part.PtBuPromotionPartTmpVO;
import com.org.dms.vo.part.PtBuReturnApplyDtlTmpVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlTmpVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryTmpVO;
import com.org.dms.vo.part.PtBuVelBoxRlTmpVO;
import com.org.dms.vo.service.MainDealerStarTmpVO;
import com.org.dms.vo.service.MainVehicleTmpVO;
import com.org.dms.vo.service.SeBaClPriceTmpVO;
import com.org.dms.vo.service.SeBaClaimCodeTmpVO;
import com.org.dms.vo.service.SeBaExcitationTmpVO;
import com.org.dms.vo.service.SeBaFaultTasktimeTmpVO;
import com.org.dms.vo.service.SeBaOldpartmanageTmpVO;
import com.org.dms.vo.service.SeBaRePriceTmpVO;
import com.org.dms.vo.service.SeBaRulePartTmpVO;
import com.org.dms.vo.service.SeBaTaskAmountTmpVO;
import com.org.dms.vo.service.SeBaTravelCostTmpVO;
import com.org.dms.vo.service.SeBuActivityVehicleTmpVO;
import com.org.dms.vo.service.SeBuClaimSettleModifyTmpVO;
import com.org.dms.vo.service.SeBuClaimSettleTmpVO;
import com.org.dms.vo.service.SeBuOldpartStorageTmpVO;
import com.org.dms.vo.service.SeBuReturnOrderTmpVO;
import com.org.dms.vo.service.SeBuReturnorderDtlTmpVO;
import com.org.framework.common.DBFactory;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * @Title: 导入文件插入临时表
 * @Description:
 * @Copyright: Copyright (c) 2012
 * @Date: 2012-5-23
 * @author andy
 * @mail andy.ten@tom.com
 */
public class ImportFileInsertTmp {
    /**
     * 业务类型
     */
    public static final String PT_BU_PROMOTION_PART_TMP = "insertPT_BU_PROMOTION_PART_TMP"; 
    public static final String SE_BU_RETURNORDER_DTL_TMP = "insertSE_BU_RETURNORDER_DTL_TMP"; //旧件回运导入
    public static final String SE_BU_OLDPART_STORAGE_TMP = "insertSE_BU_OLDPART_STORAGE_TMP"; //旧件入库导入
    public static final String PT_BU_PCH_ORDER_PART_TMP = "insertPT_BU_PCH_ORDER_PART_TMP"; //采购订单配件导入
    public static final String PT_BU_PCH_CONT_PART_TMP = "insertPT_BU_PCH_CONT_PART_TMP"; //采购合同配件导入
    public static final String ARMY_PT_BU_PCH_ORDER_PART_TMP = "insertARMY_PT_BU_PCH_ORDER_PART_TMP"; //军品采购订单配件导入
    public static final String PT_BU_PCH_RETURN_PART_TMP = "insertPT_BU_PCH_RETURN_PART_TMP";//采购退货单导入
    public static final String PT_BU_FORCAST_DTL_TMP = "insertPT_BU_FORCAST_DTL_TMP"; //预测配件明细导入
    public static final String PT_BU_RETURN_APPLY_DTL_TMP = "insertPT_BU_RETURN_APPLY_DTL_TMP"; //退件申请明细导入
    public static final String PT_BU_BOX_UP_TMP = "insertPT_BU_BOX_UP_TMP"; //装箱清单导入
    public static final String PT_BU_VEL_BOX_RL_TMP = "insertPT_BU_VEL_BOX_RL_TMP"; //装箱清单导入
    public static final String SE_BU_CLAIM_SETTLE_TMP = "insertSE_BU_CLAIM_SETTLE_TMP"; //结算单批量调整导入
    public static final String SE_BU_CLAIM_SETTLE_TMP_OTHER = "insertSE_BU_CLAIM_SETTLE_TMP_OTHER"; //结算单批量调整导入-其他费用批量导入
    public static final String SE_BU_CLAIM_SETTLE_TMP_POLICY = "insertSE_BU_CLAIM_SETTLE_TMP_POLICY"; //结算单批量调整导入-政策支持批量导入
    public static final String PT_BA_CLAIM_CYCLE_SET_TMP = "insertPT_BA_CLAIM_CYCLE_SET_TMP"; //配件三包期设置导入
    public static final String SE_BU_ACTIVITY_VEHICLE_TMP = "insertSE_BU_ACTIVITY_VEHICLE_TMP"; //服务活动VIN导入
    public static final String PT_BA_STOCK_SAFESTOCKS_TMP = "insertPT_BA_STOCK_SAFESTOCKS_TMP"; //安全库存配件导入
    public static final String PT_BA_CHANNEL_SAFESTOCK_TMP = "insertPT_BA_CHANNEL_SAFESTOCK_TMP"; //渠道安全库存配件导入
    public static final String PT_BA_TRANSPORT_ADDRESS_TMP = "insertPT_BA_TRANSPORT_ADDRESS_TMP"; //发运地址导入
    public static final String SE_BA_RULE_PART_TMP = "insertSE_BA_RULE_PART_TMP";//三包规则明细导入
    public static final String PT_BU_INVENTORY_DTL_TMP = "insertPT_BU_INVENTORY_DTL_TMP";	//库存盘点明细导入
    public static final String PT_BA_INFO_TMP = "insertPT_BA_INFO_TMP";						//配件档案信息导入    
    public static final String PT_BA_PRICE_TMP = "insertPT_BA_PRICE_TMP";					//配件价格信息导入    
    public static final String PT_BA_PCH_TMP = "insertPT_BA_PCH_TMP";						//配件采购价格信息导入    
    public static final String PT_BU_INVENTORY_TMP = "insertPT_BU_INVENTORY_TMP";			//配件库存盘点信息导入    
    public static final String PT_BA_PICTURE_TMP = "insertPT_BA_PICTURE_TMP";				//配件图片信息导入    
    public static final String SE_BA_CL_PRICE_TMP = "insertSE_BA_CL_PRICE_TMP";				//服务索赔价格信息导入  
    public static final String SE_BA_RE_PRICE_TMP = "insertSE_BA_RE_PRICE_TMP";				//服务追偿价格信息导入  
    public static final String PT_BU_SALE_ORDER_DTL_TMP = "insertPT_BU_SALE_ORDER_DTL_TMP";//配件订单明细导入
    public static final String PT_BU_CLAIM_APPLY_TMP = "insert_PT_BU_CLAIM_APPLY_TMP"; // 配件管理-旧件回运装箱导入
    public static final String SE_BU_RETURN_ORDER_TMP = "insertSE_BU_RETURN_ORDER_TMP";//旧件终审导入
    public static final String MAIN_VEHICLE_TMP = "insertMAIN_VEHICLE_TMP";//车辆导入
    public static final String SE_BA_TRAVEL_COST_TMP = "insertSE_BA_TRAVEL_COST_TMP";//外出费用明细导入
    public static final String SE_BA_CLAIM_CODE_TMP = "insertSE_BA_CLAIM_CODE_TMP";//工时单价明细导入
    public static final String SE_BA_TASK_AMOUNT_TMP = "insertSE_BA_TASK_AMOUNT_TMP";//工时定额明细导入
    public static final String SE_BA_FAULT_TASKTIME_TMP = "insertSE_BA_FAULT_TASKTIME_TMP";//故障模式与工时定额关系明细导入
    public static final String SE_BA_EXCITATION_TMP = "insertSE_BA_EXCITATION_TMP";//激励系数明细导入
    public static final String SE_BU_CLAIM_SETTLE_MODIFY_TMP = "insertSE_BU_CLAIM_SETTLE_MODIFY_TMP";//付款结果更新导入
    public static final String SE_BU_RETURNORDER_PACK_FOCUS = "insertSE_BU_RETURNORDER_PACK_FOCUS";//集中点装箱导入
    public static final String PT_BU_MOVE_OUT_PART_TMP = "insertPT_BU_MOVE_OUT_PART_TMP";//导入移库出库配件明细
    public static final String MAIN_DEALER_STAR_TMP = "insertMAIN_DEALER_STAR_TMP"; //服务商星级导入
    public static final String PT_BU_ACCOUNT_TMP = "insertPT_BU_ACCOUNT_TMP"; //配件账户(材料费，返利)导入
    public static final String PT_BU_SUP_INVOICE_SUMMARY_TMP = "insertPT_BU_SUP_INVOICE_SUMMARY_TMP"; //供应商结算导入
    public static final String SE_BA_OLDPARTMANAGE_TMP = "insertSE_BA_OLDPARTMANAGE_TMP"; //旧件管理费系数导入
    public static final String PT_BA_PART_SUPPLIER_RL_TMP = "insertPT_BA_PART_SUPPLIER_RL_TMP"; //配件与供应商关系导入
    public static final String PT_BU_ASALE_ORDER_DTL_TMP = "insertPT_BU_ASALE_ORDER_DTL_TMP"; //军品销售订单导入
    public static final String PT_BA_ARMY_PRICE_TMP = "insertPT_BA_ARMY_PRICE_TMP";	//配件军品价格信息导入    
    public static final String PT_BU_PART_CONT_CHK_TMP = "insertPT_BU_PART_CONT_CHK_TMP";	//待维护配件合同校验  
    public static final String PT_BA_WAREHOUSE_KEEPER_TMP = "insertPT_BA_WAREHOUSE_KEEPER_TMP";	//库管员属性导入
    
    
    /**
     * 向配件账户表中插入信息
     *
     * 配件账户(PT_BU_ACCOUNT_TMP)
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_ACCOUNT_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_ACCOUNT_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuAccountTmpVO PtBuAccountTmpVO=new PtBuAccountTmpVO();
                    PtBuAccountTmpVO.setTmpNo(key);
                    PtBuAccountTmpVO.setOrgCode(cells[1].getContents().trim());
                    PtBuAccountTmpVO.setAmount(cells[2].getContents().trim().replace(",", ""));
                    PtBuAccountTmpVO.setUserAccount(user.getAccount());
                    factory.insert(PtBuAccountTmpVO);
                }
            }
            factory.getConnection().commit();
        } catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }

    /**
     * 向配件三包期设置表中插入信息
     *
     * 配件三包期设置(PT_BA_CLAIM_CYCLE_SET_TMP)
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_CLAIM_CYCLE_SET_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_CLAIM_CYCLE_SET_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaClaimCycleSetTmpVO ptBaClaimCycleSetTmpVO=new PtBaClaimCycleSetTmpVO();
                    ptBaClaimCycleSetTmpVO.setTmpNo(key);
                    ptBaClaimCycleSetTmpVO.setPartCode(cells[1].getContents().trim());
                    ptBaClaimCycleSetTmpVO.setPartName(cells[2].getContents().trim());
                    ptBaClaimCycleSetTmpVO.setClaimMonth(cells[3].getContents().trim());
                    ptBaClaimCycleSetTmpVO.setExtensionMonth(cells[4].getContents().trim());
                    ptBaClaimCycleSetTmpVO.setUserAccount(user.getAccount());
                    factory.insert(ptBaClaimCycleSetTmpVO);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }

    /**
     * 向服务活动车辆临时表中插入信息
     *
     * 服务活动车辆临时表(SE_BU_ACTIVITY_VEHICLE_TMP)
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BU_ACTIVITY_VEHICLE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BU_ACTIVITY_VEHICLE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBuActivityVehicleTmpVO SeBuActivityVehicleTmpVO=new SeBuActivityVehicleTmpVO();
                    SeBuActivityVehicleTmpVO.setRowNo(key);
        	        SeBuActivityVehicleTmpVO.setVin(cells[1].getContents().trim());  
                    SeBuActivityVehicleTmpVO.setUserAccount(user.getAccount());
                    factory.insert(SeBuActivityVehicleTmpVO);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 向临时表中插入信息
     *
     * 退件申请明细(PT_BU_RETURN_APPLY_DTL_TMP)
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_RETURN_APPLY_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_RETURN_APPLY_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuReturnApplyDtlTmpVO ptBuReturnApplyDtlTmpVO=new PtBuReturnApplyDtlTmpVO();
                    ptBuReturnApplyDtlTmpVO.setTmpNo(key);
                    ptBuReturnApplyDtlTmpVO.setPartCode(cells[1].getContents().trim());
//                    ptBuReturnApplyDtlTmpVO.setPartName(cells[2].getContents().trim());
                    if(!"".equals(cells[2].getContents().trim())&&cells[2].getContents().trim()!=null){
                    	ptBuReturnApplyDtlTmpVO.setSupplierCode(cells[2].getContents().trim());
                    }else{
                    	ptBuReturnApplyDtlTmpVO.setSupplierCode("9XXXXXX");
                    }
//                    ptBuReturnApplyDtlTmpVO.setSupplierCode(cells[2].getContents().trim());
//                    ptBuReturnApplyDtlTmpVO.setSupplierName(cells[4].getContents().trim());
//                    ptBuReturnApplyDtlTmpVO.setUnit(cells[5].getContents().trim());
//                    ptBuReturnApplyDtlTmpVO.setSalePrice(cells[6].getContents().trim());
//                    ptBuReturnApplyDtlTmpVO.setAmount(cells[7].getContents().trim());
                    ptBuReturnApplyDtlTmpVO.setReturnCount(cells[3].getContents().trim());
                    ptBuReturnApplyDtlTmpVO.setUserAccount(user.getAccount());
                    factory.insert(ptBuReturnApplyDtlTmpVO);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }

    /**
     * 向临时表中插入信息
     *
     * 预测配件明细(PT_BU_FORCAST_DTL_TMP)
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_FORCAST_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_FORCAST_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuForcastDtlTmpVO ptBuForcastDtlTmpVO=new PtBuForcastDtlTmpVO();
                    ptBuForcastDtlTmpVO.setTmpNo(key);
                    ptBuForcastDtlTmpVO.setPartCode(cells[1].getContents().trim());
                    ptBuForcastDtlTmpVO.setPartCount(cells[2].getContents().trim());
                    ptBuForcastDtlTmpVO.setUserAccount(user.getAccount());
                    factory.insert(ptBuForcastDtlTmpVO);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_PCH_CONT_PART_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_PCH_CONT_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuPchContPartTmpVO vo=new PtBuPchContPartTmpVO();
                    vo.setTmpNo(key);
                    vo.setPartCode(cells[1].getContents().trim().replace(",", ""));
                    vo.setPartName(cells[2].getContents().trim().replace(",", ""));
                    vo.setUnitPrice(cells[3].getContents().trim());
                    vo.setDeliverCycle(cells[4].getContents().trim());
                    vo.setMinPackUnit(cells[6].getContents().trim());
                    vo.setMinPackCount(cells[7].getContents().trim());
                    vo.setRemarks(cells[5].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 向临时表中插入信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_PROMOTION_PART_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try
        {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_PROMOTION_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++)
            {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext())
                {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    
                    PtBuPromotionPartTmpVO vo=new PtBuPromotionPartTmpVO();
                    vo.setPartCode(cells[1].getContents().trim());
                    vo.setPromPrice(cells[2].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);    
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e)
        {
            factory.getConnection().rollback();
            e.printStackTrace();
        }finally{
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    //旧件回运导入
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BU_RETURNORDER_DTL_TMP(List<Map> list,User user,String bParams) 
            throws Exception 
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try
        {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BU_RETURNORDER_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++)
            {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext())
                {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBuReturnorderDtlTmpVO vo=new SeBuReturnorderDtlTmpVO();
                    vo.setRowNum(key);
                    vo.setOrgCode(cells[2].getContents().trim());
                    vo.setOrgName(cells[3].getContents().trim());
                    vo.setClaimNo(cells[4].getContents().trim());
                    vo.setFaultCode(cells[5].getContents().trim());
                    vo.setModelsCode(cells[6].getContents().trim());
                    vo.setVin(cells[7].getContents().trim());
                    vo.setPartCode(cells[8].getContents().trim());
                    vo.setPartName(cells[9].getContents().trim());
                    vo.setShouldCount(cells[10].getContents().trim());//应返数量
                    vo.setOughtCount(cells[11].getContents().trim());//实返数量
                    vo.setProsupplyCode(cells[12].getContents().trim());
                    vo.setDutysupplyCode(cells[14].getContents().trim());
                    vo.setBrokenReason(cells[16].getContents().trim());
                    try {
						vo.setRemarks(cells[17].getContents().trim());
					} catch (Exception e) {
						vo.setRemarks("");
					}
                    vo.setUserAccount(user.getAccount());
                    //vo.setPartCode();
                    //vo.setPromPrice(cells[2].getContents().trim());
                    factory.insert(vo);    
                }
                factory.getConnection().commit();
            }
        }
        catch (Exception e)
        {
            factory.getConnection().rollback();
            e.printStackTrace();
        }finally{
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    //旧件入库导入
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BU_OLDPART_STORAGE_TMP(List<Map> list,User user,String bParams) 
    		throws Exception 
    		{
    	DBFactory factory = new DBFactory();
    	ActionContext atx = ActionContext.getContext();
    	atx.setDBFactory(factory);
    	try
    	{
    		//根据user账号删除临时表数据
    		//删除临时表
    		String sql = " DELETE FROM SE_BU_OLDPART_STORAGE_TMP WHERE CREATE_USER='" +user.getAccount()+"' ";
    		factory.delete(sql, null);
    		factory.getConnection().commit();
    		for (int i = 0; i < list.size(); i++)
    		{
    			//插入临时表数据
    			Map<String, Cell[]> m = list.get(i);
    			Set<String> keys = m.keySet();
    			Iterator it = keys.iterator();
    			String key = "";
    			while(it.hasNext())
    			{
    				key=(String)it.next();
    				Cell[] cells=(Cell[])m.get(key);
    				SeBuOldpartStorageTmpVO vo = new SeBuOldpartStorageTmpVO();
    				vo.setRowNo(key);
    				vo.setReturnOrderNo(cells[1].getContents().trim());//回运单号
    				vo.setClaimNo(cells[2].getContents().trim());//索赔单号
    				vo.setFaultCode(cells[3].getContents().trim());//故障代码
    				vo.setPartCode(cells[4].getContents().trim());//配件代码
    				vo.setPartName(cells[5].getContents().trim());//配件名称
    				vo.setSupplierCode(cells[6].getContents().trim());//供应商代码
    				vo.setSupplierName(cells[7].getContents().trim());//供应商名称
    				vo.setClaimCount(cells[8].getContents().trim());//索赔单应返数量
    				vo.setOughtCount(cells[9].getContents().trim());//实返数量
    				vo.setMissCount(cells[10].getContents().trim());//缺失数量
    				if(!(null==cells[11].getContents().trim()||cells[11].getContents().trim().equals(""))){
    					vo.setStorageCount(cells[11].getContents().trim());//入库数量
    				}
    				vo.setAlreadyIn(cells[12].getContents().trim());
    				vo.setCreateUser(user.getAccount());
    				factory.insert(vo);    
    			}
    			factory.getConnection().commit();
    		}
    	}
    	catch (Exception e)
    	{
    		factory.getConnection().rollback();
    		e.printStackTrace();
    	}finally{
    		if(factory != null)
    			factory.getConnection().close();
    		factory.setFactory(null);
    	}
    		}
    public static void PT_BU_PCH_CONT_PART_TMP(List<Map> list,User user,String bParams) 
			throws Exception 
	{
		DBFactory factory = new DBFactory();
		ActionContext atx = ActionContext.getContext();
		atx.setDBFactory(factory);
		try
		{
			//根据user账号删除临时表数据
			//删除临时表
			String sql = " DELETE FROM PT_BU_PCH_CONT_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
			factory.delete(sql, null);
			factory.getConnection().commit();
			for (int i = 0; i < list.size(); i++)
			{
				//插入临时表数据
				Map<String, Cell[]> m = list.get(i);
				Set<String> keys = m.keySet();
				Iterator it = keys.iterator();
				String key = "";
				while(it.hasNext())
				{
					key=(String)it.next();
					Cell[] cells=(Cell[])m.get(key);
					PtBuPchOrderPartTmpVO vo=new PtBuPchOrderPartTmpVO();
					vo.setTmpNo(key);
					vo.setPartCode(cells[0].getContents().trim());
					vo.setPchCount(cells[1].getContents().trim());
					if(cells[2].getContents().trim()!=null&&!"".equals(cells[2].getContents().trim())){
						vo.setRemarks(cells[2].getContents().trim());
					}
					vo.setUserAccount(user.getAccount());
					factory.insert(vo);	
				}
				factory.getConnection().commit();
			}
		}
		catch (Exception e)
		{
			factory.getConnection().rollback();
			e.printStackTrace();
		}finally{
			if(factory != null)
				factory.getConnection().close();
			factory.setFactory(null);
		}
	}
    public static void insertPT_BU_PCH_ORDER_PART_TMP(List<Map> list,User user,String bParams) 
			throws Exception 
	{
		DBFactory factory = new DBFactory();
		ActionContext atx = ActionContext.getContext();
		atx.setDBFactory(factory);
		try
		{
			//根据user账号删除临时表数据
			//删除临时表
			String sql = " DELETE FROM PT_BU_PCH_ORDER_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
			factory.delete(sql, null);
			factory.getConnection().commit();
			for (int i = 0; i < list.size(); i++)
			{
				//插入临时表数据
				 Map<String, Cell[]> m = list.get(i);
				Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                	key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
					PtBuPchOrderPartTmpVO vo=new PtBuPchOrderPartTmpVO();
					vo.setTmpNo(key);
					vo.setPartCode(cells[1].getContents().trim());
					vo.setPchCount(cells[2].getContents().trim());
					
					// begin by fuxiao 20150112 采购订单导入Bug:备注不填写则cell[3]超出数组索引列
					// if(cells[3].getContents().trim()!=null&&!"".equals(cells[3].getContents().trim())){
					//	vo.setRemarks(cells[3].getContents().trim());
					// }
					if(cells.length > 3){
						if(cells[3].getContents().trim() !=null &&!"".equals(cells[3].getContents().trim())){
							vo.setRemarks(cells[3].getContents().trim());
						}
					}else{
						vo.setRemarks("");
					}
					// end 
					
					vo.setUserAccount(user.getAccount());
					factory.insert(vo);	
				}
				
				factory.getConnection().commit();
			}
		}
		catch (Exception e)
		{
			factory.getConnection().rollback();
			e.printStackTrace();
		}finally{
			if(factory != null)
				factory.getConnection().close();
			factory.setFactory(null);
		}
	}
    public static void insertARMY_PT_BU_PCH_ORDER_PART_TMP(List<Map> list,User user,String bParams) 
			throws Exception 
	{
		DBFactory factory = new DBFactory();
		ActionContext atx = ActionContext.getContext();
		atx.setDBFactory(factory);
		try
		{
			//根据user账号删除临时表数据
			//删除临时表
			String sql = " DELETE FROM PT_BU_PCH_ORDER_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
			factory.delete(sql, null);
			factory.getConnection().commit();
			for (int i = 0; i < list.size(); i++)
			{
				//插入临时表数据
				 Map<String, Cell[]> m = list.get(i);
				Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                	key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
					PtBuPchOrderPartTmpVO vo=new PtBuPchOrderPartTmpVO();
					vo.setTmpNo(key);
					vo.setPartCode(cells[1].getContents().trim());
					vo.setPchCount(cells[2].getContents().trim());
					if(cells[3].getContents().trim()!=null&&!"".equals(cells[3].getContents().trim())){
						vo.setRemarks(cells[3].getContents().trim());
					}
					vo.setUserAccount(user.getAccount());
					factory.insert(vo);	
				}
				
				factory.getConnection().commit();
			}
		}
		catch (Exception e)
		{
			factory.getConnection().rollback();
			e.printStackTrace();
		}finally{
			if(factory != null)
				factory.getConnection().close();
			factory.setFactory(null);
		}
	}
    public static void insertPT_BU_PCH_RETURN_PART_TMP(List<Map> list,User user,String bParams) 
			throws Exception 
	{
		DBFactory factory = new DBFactory();
		ActionContext atx = ActionContext.getContext();
		atx.setDBFactory(factory);
		try
		{
			//根据user账号删除临时表数据
			String sql = " DELETE FROM PT_BU_PCH_RETURN_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
			factory.delete(sql, null);
			factory.getConnection().commit();
			for (int i = 0; i < list.size(); i++)
			{
				//插入临时表数据
				Map<String, Cell[]> m = list.get(i);
				Set<String> keys = m.keySet();
				Iterator it = keys.iterator();
				String key = "";
				while(it.hasNext())
				{
					key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
					PtBuPchReturnPartTmpVO vo=new PtBuPchReturnPartTmpVO();
					vo.setTmpNo(key);
					vo.setPositionCode(cells[1].getContents().trim());
					vo.setPartCode(cells[2].getContents().trim());
					vo.setPartName(cells[3].getContents().trim());
					vo.setUserAccount(user.getAccount());
					factory.insert(vo);
				}
				factory.getConnection().commit();
			}
		}
		catch (Exception e)
		{
			factory.getConnection().rollback();
			e.printStackTrace();
		}finally{
			if(factory != null)
				factory.getConnection().close();
			factory.setFactory(null);
		}
	}
    
    //装箱清单导入
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_BOX_UP_TMP(List<Map> list,User user,String bParams)
            throws Exception
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try
        {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_BOX_UP_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++)
            {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext())
                {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuBoxUpTmpVO vo=new PtBuBoxUpTmpVO();
                    vo.setRowNo(cells[1].getContents().trim());//行号
                    vo.setIssueNo(cells[2].getContents().trim());//发料单号
                    vo.setPartCode(cells[3].getContents().trim());//备件代码
                    vo.setPartName(cells[4].getContents().trim());//备件名称
                    vo.setBoxNo(cells[9].getContents().trim());//箱号
                    vo.setCount(cells[10].getContents().trim());//数量
                    vo.setTmpNo(key);
                    vo.setUserAccount(user.getAccount());//登录人
                    factory.insert(vo);
                }
                factory.getConnection().commit();
            }
        }
        catch (Exception e)
        {
            factory.getConnection().rollback();
            e.printStackTrace();
        }finally{
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_VEL_BOX_RL_TMP(List<Map> list,User user,String bParams)
            throws Exception
    {
        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try
        {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_VEL_BOX_RL_TMP WHERE USER_ACCONT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++)
            {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext())
                {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuVelBoxRlTmpVO vo=new PtBuVelBoxRlTmpVO();
                    vo.setBoxNo(cells[1].getContents().trim());//行号
                    vo.setTmpNo(key);
                    vo.setUserAccont(user.getAccount());//登录人
                    factory.insert(vo);
                }
                factory.getConnection().commit();
            }
        }
        catch (Exception e)
        {
            factory.getConnection().rollback();
            e.printStackTrace();
        }finally{
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    //批量调整导入
  	@SuppressWarnings({ "rawtypes", "unchecked" })
  	public static void insertSE_BU_CLAIM_SETTLE_TMP(List<Map> list,User user,String bParams) 
  			throws Exception 
  	{
  		DBFactory factory = new DBFactory();
  		ActionContext atx = ActionContext.getContext();
  		atx.setDBFactory(factory);
  		try
  		{
  			//根据user账号删除临时表数据
  			//删除临时表
  			String sql = " DELETE FROM SE_BU_CLAIM_SETTLE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
  			factory.delete(sql, null);
  			factory.getConnection().commit();
  			for (int i = 0; i < list.size(); i++)
  			{
  				//插入临时表数据
  				Map<String, Cell[]> m = list.get(i);
  				Set<String> keys = m.keySet();
  				Iterator it = keys.iterator();
  				String key = "";
  				while(it.hasNext())
  				{
  					key=(String)it.next();
  					Cell[] cells=(Cell[])m.get(key);
  					SeBuClaimSettleTmpVO vo=new SeBuClaimSettleTmpVO();
  					int index = 1;
  					do
  					{
	  					// begin : 业务新需求：屏蔽结算单调整功能中的政策支持字段维护及其他费用字段维护
  						switch(index){
  						case 1:
  							vo.setRowNum(key);//行号
  							break;
  						case 2:
  							vo.setSettleNo(cells[index].getContents().trim());//结算单号
  							break;
  						case 4:
  							vo.setOrgCode(cells[index].getContents().trim());//服务商代码
  							break;
  						case 5:
  							vo.setOrgName(cells[index].getContents().trim());//服务商名称
  							break;
  						case 7:
  							vo.setSeCosts(cells[index].getContents().trim());//服务费
  							break;
  						case 8:
  							vo.setSeReCosts(cells[index].getContents().trim());//旧件运费
  							break;
  						case 9:
  							vo.setSeCashGift(cells[index].getContents().trim());//礼金
  							break;
  						case 10:
  		  					vo.setSeCarAward(cells[index].getContents().trim());//售车奖励
  		  					break;
  						case 11:
  		  					vo.setSeApCosts(cells[index].getContents().trim());//考核费用
  		  					break;
  						case 13:
  							vo.setPartMaterialCosts(cells[index].getContents().trim());//配件三包材料费
  							break;
  						case 14:
  		  					vo.setMaterialcostReduce(cells[index].getContents().trim());//材料费冲减
  		  					break;
  						case 15:
  							vo.setSeRemarks(cells[index].getContents().trim());//服务费备注
  							break;
  		  				default:
  		  					break;
  						}
  						
  						// 必将将++放在下表后面，因为case后的数字有可能不符
  						index++;
  					}
  					while(index < cells.length);
  					vo.setUserAccount(user.getAccount());//登录人
  					factory.insert(vo);	
  				}
  				factory.getConnection().commit();
  			}
  		}
  		catch (Exception e)
  		{
  			factory.getConnection().rollback();
  			e.printStackTrace();
  		}finally{
  			if(factory != null)
  				factory.getConnection().close();
  			factory.setFactory(null);
  		}
  	}
  	
  	
  	/**
  	 * 结算单调整 -> 政策支持费用批量调整
  	 * @param list
  	 * @param user
  	 * @param bParams
  	 * @throws Exception
  	 */
  	@SuppressWarnings({ "rawtypes", "unchecked" })
  	public static void insertSE_BU_CLAIM_SETTLE_TMP_POLICY(List<Map> list,User user,String bParams) 
  			throws Exception 
  	{
  		DBFactory factory = new DBFactory();
  		ActionContext atx = ActionContext.getContext();
  		atx.setDBFactory(factory);
  		try
  		{
  			//根据user账号删除临时表数据
  			//删除临时表
  			String sql = " DELETE FROM SE_BU_CLAIM_SETTLE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
  			factory.delete(sql, null);
  			factory.getConnection().commit();
  			for (int i = 0; i < list.size(); i++)
  			{
  				//插入临时表数据
  				Map<String, Cell[]> m = list.get(i);
  				Set<String> keys = m.keySet();
  				Iterator it = keys.iterator();
  				String key = "";
  				while(it.hasNext())
  				{
  					key=(String)it.next();
  					Cell[] cells=(Cell[])m.get(key);
  					SeBuClaimSettleTmpVO vo=new SeBuClaimSettleTmpVO();
  					vo.setRowNum(cells[1].getContents().trim());//行号
  					vo.setSettleNo(cells[2].getContents().trim());//结算单号
  					vo.setOrgCode(cells[4].getContents().trim());//服务商代码
  					vo.setOrgName(cells[5].getContents().trim());//服务商名称
  					vo.setSettleDate(cells[6].getContents().trim());//结算日期
  					vo.setSePolicySup(cells[7].getContents().trim());//政策支持
  					vo.setUserAccount(user.getAccount());//登录人
  					factory.insert(vo);	
  				}
  				factory.getConnection().commit();
  			}
  		}
  		catch (Exception e)
  		{
  			factory.getConnection().rollback();
  			e.printStackTrace();
  		}finally{
  			if(factory != null)
  				factory.getConnection().close();
  			factory.setFactory(null);
  		}
  	}
  	
  	/**
  	 * 结算单调整 -> 其他费用批量调整
  	 * @param list
  	 * @param user
  	 * @param bParams
  	 * @throws Exception
  	 */
  	@SuppressWarnings({ "rawtypes", "unchecked" })
  	public static void insertSE_BU_CLAIM_SETTLE_TMP_OTHER(List<Map> list,User user,String bParams) 
  			throws Exception 
  	{
  		DBFactory factory = new DBFactory();
  		ActionContext atx = ActionContext.getContext();
  		atx.setDBFactory(factory);
  		try
  		{
  			//根据user账号删除临时表数据
  			//删除临时表
  			String sql = " DELETE FROM SE_BU_CLAIM_SETTLE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
  			factory.delete(sql, null);
  			factory.getConnection().commit();
  			for (int i = 0; i < list.size(); i++)
  			{
  				//插入临时表数据
  				Map<String, Cell[]> m = list.get(i);
  				Set<String> keys = m.keySet();
  				Iterator it = keys.iterator();
  				String key = "";
  				while(it.hasNext())
  				{
  					key=(String)it.next();
  					Cell[] cells=(Cell[])m.get(key);
  					SeBuClaimSettleTmpVO vo=new SeBuClaimSettleTmpVO();
  					vo.setRowNum(cells[1].getContents().trim());//行号
  					vo.setSettleNo(cells[2].getContents().trim());//结算单号
  					vo.setOrgCode(cells[4].getContents().trim());//服务商代码
  					vo.setOrgName(cells[5].getContents().trim());//服务商名称
  					vo.setSettleDate(cells[6].getContents().trim());//结算日期
  					vo.setSeOthers(cells[7].getContents().trim());//其它费用
  					vo.setUserAccount(user.getAccount());//登录人
  					factory.insert(vo);	
  				}
  				factory.getConnection().commit();
  			}
  		}
  		catch (Exception e)
  		{
  			factory.getConnection().rollback();
  			e.printStackTrace();
  		}finally{
  			if(factory != null)
  				factory.getConnection().close();
  			factory.setFactory(null);
  		}
  	}
  	
  	/**
     * 三包规则临时表中插入信息
     *
     * 三包规则临时表(SE_BA_RULE_PART_TMP)
     * @param list
     * @param baixiaoliang 2014-8-30
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_RULE_PART_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BA_RULE_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaRulePartTmpVO vo = new SeBaRulePartTmpVO();
                    vo.setRowNum(key); 
                   // vo.setRuleId(cells[1].getContents().trim());
                    vo.setRuleCode(cells[2].getContents().trim());
                    vo.setRuleName(cells[3].getContents().trim());
                  //  vo.setPartId(cells[4].getContents().trim());
                    vo.setPartCode(cells[4].getContents().trim());
        			vo.setPartName(cells[5].getContents().trim());
        			vo.setMonths(cells[6].getContents().trim());
        			vo.setMileage(cells[7].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            
  /*          
            StringBuffer sql2= new StringBuffer();
            sql2.append("MERGE INTO SE_BA_RULE_PART_TMP M\n" );
            sql2.append("USING (SELECT\n" );
            sql2.append("T.RULE_CODE,T.PART_CODE,C.PART_ID, B.RULE_ID,B.RULE_NAME,C.PART_NAME\n" );
            sql2.append(" FROM SE_BA_RULE_PART_TMP T,SE_BA_RULE B ,PT_BA_INFO C\n" );
            sql2.append("WHERE T.RULE_CODE = B.RULE_CODE  AND T.PART_CODE = C.PART_CODE\n" );
            sql2.append(") N\n" );
            sql2.append("ON (M.RULE_CODE = N.RULE_CODE  AND M.PART_CODE = N.PART_CODE  )\n" );
            sql2.append("WHEN MATCHED THEN\n" );
            sql2.append("UPDATE  SET M.PART_ID=N.PART_ID,  RULE_ID =N.RULE_ID , M.PART_NAME=N.PART_NAME,  M.RULE_NAME =N.RULE_NAME");


			
			 * sql.append(" AND B.RULE_CODE = "); sql.append("'");
			 * sql.append(vo.getRuleCode()); sql.append("'");
			 * sql.append("AND C.PART_CODE = "); sql.append("'");
			 * sql.append(vo.getPartCode()); sql.append("'");
			 
			factory.update(sql2.toString(), null);*/
            
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }

  	/**
     * 向安全库存临时表中插入信息
     *
     * 安全库存临时表(PT_BA_STOCK_SAFESTOCKS_TMP)
     * @param list
     * @param suoxiuli 2014-8-26
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_STOCK_SAFESTOCKS_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_STOCK_SAFESTOCKS_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaStockSafeStocksTmpVO vo = new PtBaStockSafeStocksTmpVO();
                    //vo.setrowNum(cells[0].getContents().trim());
                    vo.setrowNum(key);
                    vo.setStockCode(cells[1].getContents().trim());
                    vo.setPartCode(cells[2].getContents().trim());
        			vo.setLowerLimit(cells[3].getContents().trim());
        			vo.setUpperLimit(cells[4].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    } 
    
    /**
     * 向渠道安全库存临时表中插入信息
     *
     * 渠道安全库存临时表(PT_BA_CHANNEL_SAFESTOCK_TMP)
     * @param list
     * @param suoxiuli 2014-09-02
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_CHANNEL_SAFESTOCK_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_CHANNEL_SAFESTOCK_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaChannelSafeStocksTmpVO vo = new PtBaChannelSafeStocksTmpVO();
                    vo.setrowNum(key);
                    vo.setOrgCode(cells[1].getContents().trim());
                    vo.setPartCode(cells[2].getContents().trim());
        			vo.setLowerLimit(cells[3].getContents().trim());
        			vo.setUpperLimit(cells[4].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    } 
    
    /**
     * 向渠道商发运地址临时表中插入信息
     *
     * 发运地址临时表(PT_BA_TRANSPORT_ADDRESS_TMP)
     * @param list
     * @param suoxiuli 2014-10-24
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_TRANSPORT_ADDRESS_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_TRANSPORT_ADDRESS_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaTransportAddressTmpVO vo = new PtBaTransportAddressTmpVO();
                    vo.setRowNum(key);
                    vo.setOrgCode(cells[1].getContents().trim());
                    vo.setLinkMan(cells[2].getContents().trim());
                    vo.setOrgId(user.getOrgId());
                    vo.setPhone(cells[3].getContents().trim());
                    vo.setMobile(cells[4].getContents().trim());
                    vo.setFax(cells[5].getContents().trim());
                    vo.setEMail(cells[6].getContents().trim());
                    vo.setZipCode(cells[7].getContents().trim());
                    vo.setAddrType(cells[8].getContents().trim());
                    vo.setProvinceCode(cells[9].getContents().trim());
                    vo.setCityCode(cells[10].getContents().trim());
                    vo.setCountryCode(cells[11].getContents().trim());
                    vo.setAddress(cells[12].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    //yhw 将库存明细模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_INVENTORY_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_INVENTORY_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuInventoryDtlTmpVO vo = new PtBuInventoryDtlTmpVO();
                    vo.setRowNum(key);
                    vo.setPositionCode(cells[1].getContents().trim());
                    vo.setPartCode(cells[2].getContents().trim());
                    vo.setSupplierCode(cells[3].getContents().trim());
                    if(!"".equals(cells[4].getContents().trim())&&cells[4].getContents().trim()!=null){
                    	vo.setMaterialCount(cells[4].getContents().trim());
                    }
                    if(!"".equals(cells[5].getContents().trim())&&cells[5].getContents().trim()!=null){
                    	vo.setRemarks(cells[5].getContents().trim());
                    }
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
  //yhw 将配件档案明细模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_INFO_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_INFO_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaInfoTmpVO vo = new PtBaInfoTmpVO();
                    //必填
                    vo.setRowNum(key);
                    
                    // 替换英文符号,为中文符号，
                    vo.setPartCode(cells[2].getContents().trim().replaceAll(",", "，"));
                    vo.setPartName(cells[3].getContents().trim().replaceAll(",", "，"));
                    
                    vo.setUnit(cells[4].getContents().trim());
                    vo.setPartType(cells[5].getContents().trim());
                    vo.setIfAssembly(cells[6].getContents().trim());
                    vo.setIfOut(cells[7].getContents().trim());
                    vo.setIfSuply(cells[8].getContents().trim());
                    vo.setIfOil(cells[9].getContents().trim());
                    vo.setIfScan(cells[10].getContents().trim());;
                    vo.setPartStatus(cells[11].getContents().trim());
                    vo.setMinPack(cells[12].getContents().trim());
                    vo.setMinUnit(cells[13].getContents().trim());
                    
                    
                    //选填
                    vo.setPositionName(cells[14].getContents().trim());
                    vo.setFPositionName(cells[15].getContents().trim());
                    vo.setPartNo(cells[16].getContents().trim());
                    vo.setRebateType(cells[17].getContents().trim());
                    vo.setAttribute(cells[18].getContents().trim());
                    vo.setDirectTypeName(cells[19].getContents().trim());
                    vo.setFPartCode(cells[20].getContents().trim());
                    vo.setFPartName(cells[21].getContents().trim());
                    vo.setBelongAssembly(cells[22].getContents().trim());
//                    vo.setSpeType(cells[21].getContents().trim());
                    vo.setSpeName(cells[23].getContents().trim());
                    
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
  //yhw 将配件价格模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_PRICE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_PRICE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaPriceTmpVO vo = new PtBaPriceTmpVO();
               //必填
                    vo.setRowNum(key);
                    vo.setPartCode(cells[2].getContents().trim());
                    vo.setPartName(cells[3].getContents().trim());
                    vo.setPlanPrice(cells[4].getContents().trim());
                    vo.setSalePrice(cells[5].getContents().trim());
                    vo.setRetailPrice(cells[6].getContents().trim());
                    //用户
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
  //yhw 将配件采购价格模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_PCH_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_PCH_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    
                    PtBaPchTmpVO vo = new PtBaPchTmpVO();
               //必填
                    vo.setRowNum(key);
                    vo.setPartCode(cells[2].getContents().trim());
                    vo.setPartName(cells[3].getContents().trim());
                    vo.setSupplierCode(cells[4].getContents().trim());
                    vo.setSupplierName(cells[5].getContents().trim());
                    vo.setPchPrice(cells[6].getContents().trim());
                    //用户
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
  //yhw 将服务索赔价格模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_CL_PRICE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BA_CL_PRICE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    
                    SeBaClPriceTmpVO vo = new SeBaClPriceTmpVO();
               //必填
                    vo.setRowNum(key);
                    vo.setPartCode(cells[2].getContents().trim());
                    vo.setSeClprice(cells[3].getContents().trim());
                    //用户
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
  //yhw 将服务追偿价格模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_RE_PRICE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BA_RE_PRICE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    
                    SeBaRePriceTmpVO vo = new SeBaRePriceTmpVO();
               //必填
                    vo.setRowNum(key);
                    vo.setPartCode(cells[2].getContents().trim());
                    vo.setSeReprice(cells[3].getContents().trim());
                    //用户
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
  //yhw 将配件库存盘点信息模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_INVENTORY_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_INVENTORY_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    
                    PtBuInventoryTmpVO vo = new PtBuInventoryTmpVO();
               //必填
                    vo.setRowNum(key);
                    vo.setDtlId(cells[2].getContents().trim());
                    vo.setPartCode(cells[3].getContents().trim());
                    vo.setPartName(cells[4].getContents().trim());
                    vo.setSupplierName(cells[5].getContents().trim());
                    vo.setAreaCode(cells[6].getContents().trim());
                    vo.setPositionName(cells[7].getContents().trim());
                    vo.setWhouseKeeper(cells[8].getContents().trim());
                    vo.setPlanPrice(cells[9].getContents().trim());
                    vo.setAmount(cells[10].getContents().trim());
                    
                    //用户
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
  //yhw 将配件照片模板中数据导入到零时表中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_PICTURE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_PICTURE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    
                    PtBaPictureTmpVO vo = new PtBaPictureTmpVO();
               //必填
                    vo.setRowNum(key);
                    vo.setPartCode(cells[2].getContents().trim());
                    vo.setPartName(cells[3].getContents().trim());
                    vo.setPictureName(cells[4].getContents().trim());
                    
                    //用户
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    /**
     * 配件订单明细导入
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_SALE_ORDER_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_SALE_ORDER_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuSaleOrderDtlTmpVO tmpVo=new PtBuSaleOrderDtlTmpVO();
                    tmpVo.setRowNo(key);
                    tmpVo.setPartCode(cells[1].getContents().trim());
                    tmpVo.setSupplierCode(cells[2].getContents().trim());
                    tmpVo.setRemarks(cells[3].getContents().trim());
                    tmpVo.setCount(cells[4].getContents().trim());
                    tmpVo.setUserAccount(user.getAccount());
                    factory.insert(tmpVo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_ASALE_ORDER_DTL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_SALE_ORDER_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuSaleOrderDtlTmpVO tmpVo=new PtBuSaleOrderDtlTmpVO();
                    tmpVo.setRowNo(key);
                    tmpVo.setPartCode(cells[1].getContents().trim());
                    tmpVo.setSupplierCode(cells[2].getContents().trim());
                    tmpVo.setRemarks(cells[3].getContents().trim());
                    tmpVo.setCount(cells[4].getContents().trim());
                    tmpVo.setUserAccount(user.getAccount());
                    factory.insert(tmpVo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    /**
     * 配件管理-旧件回运导入临时表
     * 
     * @param list
     * @param user
     * @param parems
     * @throws Exception 
     */
    public static void insert_PT_BU_CLAIM_APPLY_TMP(List<Map> list, User user, String parems) throws Exception{
    	DBFactory factory = new DBFactory();
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_CLAIM_APPLY_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator<?> it = keys.iterator();
                String key = "";
                int numCount = 1; // 记录行号步进数
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuClaimApplyTmpVO tmpVo=new PtBuClaimApplyTmpVO();
                    int index = 1;
                    do
                    {
                    	switch(index){
                    	case 1:
                    		tmpVo.setRowNo(++index + numCount++);
                    		break;
                    	case 2:
                    		tmpVo.setApplyId(cells[index++].getContents());
                    		break;
                    	case 3:
                    		tmpVo.setApplyNo(cells[index++].getContents());
                    		break;
                    	case 6:
                    		tmpVo.setBoxNo(cells[index++].getContents());
                    		break;
                    	default:
                    		index++;
                    		break;
                    	}
                    }
                    while(index < cells.length);
                    tmpVo.setUserAccount(user.getAccount());
                    factory.insert(tmpVo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 旧件终审导入
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BU_RETURN_ORDER_TMP(List<Map> list,User user,String bParams) throws Exception {
    	
    	DBFactory factory = new DBFactory();
    	ActionContext atx = ActionContext.getContext();
    	atx.setDBFactory(factory);
    	try {
    		//根据user账号删除临时表数据
    		//删除临时表
    		String sql = " DELETE FROM  SE_BU_RETURN_ORDER_TMP WHERE CREATE_USER='" + user.getAccount() +"' ";
    		factory.delete(sql, null);
    		factory.getConnection().commit();
    		for (int i = 0; i < list.size(); i++) {
    			//插入临时表数据
    			Map<String, Cell[]> m = list.get(i);
    			Set<String> keys = m.keySet();
    			Iterator it = keys.iterator();
    			String key = "";
    			while(it.hasNext()) {
    				key=(String)it.next();
    				Cell[] cells=(Cell[])m.get(key);
    				SeBuReturnOrderTmpVO SeBuReturnOrderTmpVO=new SeBuReturnOrderTmpVO();
    				SeBuReturnOrderTmpVO.setRowNo(key);
    				if(null==cells[1].getContents().trim()||cells[1].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setReturnOrderNo("");
    				}else{
    					SeBuReturnOrderTmpVO.setReturnOrderNo(cells[1].getContents().trim());
    				}
    				if(null==cells[2].getContents().trim()||cells[2].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setClaimNo("");
    				}else{
    					SeBuReturnOrderTmpVO.setClaimNo(cells[2].getContents().trim());
    				}
    				if(null==cells[3].getContents().trim()||cells[3].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setFaultCode("");
    				}else{
    					SeBuReturnOrderTmpVO.setFaultCode(cells[3].getContents().trim());
    				}
    				if(null==cells[4].getContents().trim()||cells[4].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setPartCode("");
    				}else{
    					SeBuReturnOrderTmpVO.setPartCode(cells[4].getContents().trim());
    				}
    				if(null==cells[5].getContents().trim()||cells[5].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setPartName("");
    				}else{
    					SeBuReturnOrderTmpVO.setPartName(cells[5].getContents().trim());
    				}
    				if(null==cells[6].getContents().trim()||cells[6].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setSupplierCode("");
    				}else{
    					SeBuReturnOrderTmpVO.setSupplierCode(cells[6].getContents().trim());
    				}
    				if(null==cells[7].getContents().trim()||cells[7].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setSupplierName("");
    				}else{
    					SeBuReturnOrderTmpVO.setSupplierName(cells[7].getContents().trim());
    				}
    				if(null==cells[8].getContents().trim()||cells[8].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setAmount("");
    				}else{
    					SeBuReturnOrderTmpVO.setAmount(cells[8].getContents().trim());
    				}
    				if(null==cells[9].getContents().trim()||cells[9].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setRemarks1("");
    				}else{
    					SeBuReturnOrderTmpVO.setRemarks1(cells[9].getContents().trim());
    				}
    				if(null==cells[10].getContents().trim()||cells[10].getContents().trim().equals("")){
    					SeBuReturnOrderTmpVO.setOldPartStatus("");
    				}else{
    					SeBuReturnOrderTmpVO.setOldPartStatus(cells[10].getContents().trim());
    				}
    				SeBuReturnOrderTmpVO.setCreateUser(user.getAccount());
    				factory.insert(SeBuReturnOrderTmpVO);
    			}
    		}
    		factory.getConnection().commit();
    	}
    	catch (Exception e) {
    		factory.getConnection().rollback();
    		e.printStackTrace();
    	} finally {
    		if(factory != null)
    			factory.getConnection().close();
    		factory.setFactory(null);
    	}
    }
    /**
     * 旧件终审导入
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BU_CLAIM_SETTLE_MODIFY_TMP(List<Map> list,User user,String bParams) throws Exception {
    	
    	DBFactory factory = new DBFactory();
    	ActionContext atx = ActionContext.getContext();
    	atx.setDBFactory(factory);
    	SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		//根据user账号删除临时表数据
    		//删除临时表
    		String sql = " DELETE FROM  SE_BU_CLAIM_SETTLE_MODIFY_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
    		factory.delete(sql, null);
    		factory.getConnection().commit();
    		for (int i = 0; i < list.size(); i++) {
    			//插入临时表数据
    			Map<String, Cell[]> m = list.get(i);
    			Set<String> keys = m.keySet();
    			Iterator it = keys.iterator();
    			String key = "";
    			while(it.hasNext()) {
    				key=(String)it.next();
    				Cell[] cells=(Cell[])m.get(key);
    				SeBuClaimSettleModifyTmpVO SeBuClaimSettleModifyTmpVO=new SeBuClaimSettleModifyTmpVO();
    				SeBuClaimSettleModifyTmpVO.setRowNo(cells[0].getContents().trim());
    				SeBuClaimSettleModifyTmpVO.setOrgCode(cells[1].getContents().trim());
    				SeBuClaimSettleModifyTmpVO.setSettleNo(cells[2].getContents().trim());
					   if(cells[3].getType()==CellType.DATE)
	                   {
	                   	if(!"".equals(cells[3].getContents()) && cells[3].getContents()!=null)
	                   	{	DateCell dc = (DateCell)cells[3];
	                   		Date date = dc.getDate();
	                   		SeBuClaimSettleModifyTmpVO.setPayDate(ds.format(date));
	                   	}
	                   }else
	                   {
	                	   SeBuClaimSettleModifyTmpVO.setPayDate(cells[3].getContents().trim());
	                   }
    				SeBuClaimSettleModifyTmpVO.setPayAmount(cells[4].getContents().trim());
    				SeBuClaimSettleModifyTmpVO.setIfPay(cells[5].getContents().trim());
    				SeBuClaimSettleModifyTmpVO.setRemarks(cells[6].getContents().trim());
    				SeBuClaimSettleModifyTmpVO.setUserAccount(user.getAccount());
    				factory.insert(SeBuClaimSettleModifyTmpVO);
    			}
    		}
    		factory.getConnection().commit();
    	}
    	catch (Exception e) {
    		factory.getConnection().rollback();
    		e.printStackTrace();
    	} finally {
    		if(factory != null)
    			factory.getConnection().close();
    		factory.setFactory(null);
    	}
    }
    
    
    /**
     * 车辆导入
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertMAIN_VEHICLE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String sql = " DELETE FROM MAIN_VEHICLE_TMP WHERE CREATE_USER='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    MainVehicleTmpVO tmpVo=new MainVehicleTmpVO();
                    tmpVo.setRowNo(key);
                    tmpVo.setVin(cells[1].getContents().trim());
                    tmpVo.setEngineNo(cells[2].getContents().trim());
                    tmpVo.setModelsCode(cells[3].getContents().trim());
                    tmpVo.setEngineType(cells[4].getContents().trim());
                    tmpVo.setUserType(cells[5].getContents().trim());
                  //  tmpVo.setUserType(cells[5].getContents().trim());
                  //  tmpVo.setVehicleUse(cells[6].getContents().trim());
                   // tmpVo.setDriveForm(cells[7].getContents().trim());
                   /* if(cells[8].getType()==CellType.DATE)
                    {
                    	if(!"".equals(cells[8].getContents()) && cells[8].getContents()!=null)
                    	{	DateCell dc = (DateCell)cells[8];
                    		Date date = dc.getDate();
                    		tmpVo.setCertificate(ds.format(date));
                    	}
                    }else
                    {
                    	   tmpVo.setCertificate(cells[8].getContents().trim());
                    }*/
                    if(null==cells[6].getContents().trim()||cells[7].getContents().trim().equals("")){
                    }else{
                    	if(cells[6].getType()==CellType.DATE)
                        {
                        	if(!"".equals(cells[6].getContents()) && cells[6].getContents()!=null)
                        	{	DateCell dc = (DateCell)cells[6];
                        		Date date = dc.getDate();
                        		tmpVo.setBuyDate(ds.format(date));
                        	}
                        }else
                        {
                        	 tmpVo.setBuyDate(cells[6].getContents().trim());
                        }
                    }
                    
                    if(null==cells[7].getContents().trim()||cells[7].getContents().trim().equals("")){
                    }else{
                    	if(cells[7].getType()==CellType.DATE)
                        {
                        	if(!"".equals(cells[7].getContents()) && cells[7].getContents()!=null)
                        	{	DateCell dc = (DateCell)cells[7];
                        		Date date = dc.getDate();
                        		tmpVo.setFactoryDate(ds.format(date));
                        	}
                        }else
                        {
                        	   tmpVo.setFactoryDate(cells[7].getContents().trim());
                        }
                    }
                    
                    if(null==cells[8].getContents().trim()||cells[8].getContents().trim().equals("")){
                    }else{
                    	if(cells[8].getType()==CellType.DATE)
                        {
                        	if(!"".equals(cells[8].getContents()) && cells[8].getContents()!=null)
                        	{	DateCell dc = (DateCell)cells[8];
                        		Date date = dc.getDate();
                        		tmpVo.setMaintenanceDate(ds.format(date));
                        	}
                        }else
                        {
                        	   tmpVo.setMaintenanceDate(cells[8].getContents().trim());
                        }
                    }
                    if(null==cells[9].getContents().trim()||cells[9].getContents().trim().equals("")){
                    }else{
                    	if(cells[9].getType()==CellType.DATE)
                        {
                        	if(!"".equals(cells[9].getContents()) && cells[9].getContents()!=null)
                        	{	DateCell dc = (DateCell)cells[9];
                        		Date date = dc.getDate();
                        		tmpVo.setCertificatedate(ds.format(date));
                        	}
                        }else
                        {
                        	   tmpVo.setCertificatedate(cells[9].getContents().trim());
                        }
                    }
                    if(null==cells[10].getContents().trim()||cells[10].getContents().trim().equals("")){
                    }else{
                    	 tmpVo.setGuaranteeNo(cells[10].getContents().trim());
                    }
                    if(null==cells[11].getContents().trim()||cells[11].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setSaleStatus(cells[11].getContents().trim());
                    }
                    
                    if(null==cells[12].getContents().trim()||cells[12].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setLicensePlate(cells[12].getContents().trim());
                    }
                    if(null==cells[13].getContents().trim()||cells[13].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setUserName(cells[13].getContents().trim());
                    }
                    if(null==cells[14].getContents().trim()||cells[14].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setUserNo(cells[14].getContents().trim());
                    }
                    if(null==cells[15].getContents().trim()||cells[15].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setLinkMan(cells[15].getContents().trim());
                    }
                    if(null==cells[16].getContents().trim()||cells[16].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setPhone(cells[16].getContents().trim());
                    }
                    if(null==cells[17].getContents().trim()||cells[17].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setUserAddress(cells[17].getContents().trim());
                    }
                    if(null==cells[18].getContents().trim()||cells[18].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setInsidecode(cells[18].getContents().trim());
                    }
                    if(null==cells[19].getContents().trim()||cells[19].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setConfigure(cells[19].getContents().trim());
                    }
                    if(null==cells[20].getContents().trim()||cells[20].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setContractareano(cells[20].getContents().trim());
                    }
                    if(null==cells[21].getContents().trim()||cells[21].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setProductlinecode(cells[21].getContents().trim());
                    }
                    if(null==cells[22].getContents().trim()||cells[22].getContents().trim().equals("")){
                    }else{
                    	tmpVo.setBlacklistflag(cells[22].getContents().trim());
                    }
                    
                    
                    tmpVo.setCreateUser(user.getAccount());
                    factory.insert(tmpVo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 
     *
     * 外出费用临时表(SE_BA_TRAVEL_COST_TMP)
     * @param list
     * @param baixiaoliang 2014-8-30
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_TRAVEL_COST_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BA_TRAVEL_COST_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaTravelCostTmpVO vo = new SeBaTravelCostTmpVO();
                    //   vo.setRowNum(cells[1].getContents().trim()); 
                    vo.setRowNum(key);
                    vo.setOrgCode(cells[2].getContents().trim());
                    vo.setOrgName(cells[3].getContents().trim());
                    vo.setOfficeNa(cells[4].getContents().trim());
                    vo.setCostsTypeNa(cells[5].getContents().trim()); 
                    vo.setTravelTimesNa(cells[6].getContents().trim());
                    vo.setTravelDateNa(cells[7].getContents().trim());                                 
                    vo.setVehicleTypeNa(cells[8].getContents().trim());
                                       
                    vo.setStartMiles(cells[9].getContents().trim());
                    vo.setEndMiles(cells[10].getContents().trim());
                    vo.setCost(cells[11].getContents().trim());   
                    vo.setStatusNa(cells[12].getContents().trim());
                    //vo.setTravelStatusNa(cells[6].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            
/*            
            StringBuffer sql2= new StringBuffer();
            sql2.append("MERGE INTO SE_BA_TRAVEL_COST_TMP M\n" );
            sql2.append("           USING (\n" );
            sql2.append("           SELECT\n" );
            sql2.append("           T.ORG_CODE, T.COST,T.START_MILES,T.END_MILES, B.ID COSTS_TYPE,C.ID TRAVEL_TIMES,D.ID TRAVEL_DATE,\n" );
            sql2.append("           E.ID VEHICLE_TYPE,T.COSTS_TYPE_NA,T.TRAVEL_TIMES_NA,T.TRAVEL_DATE_NA,T.VEHICLE_TYPE_NA,T.TRAVEL_STATUS_NA,F.ONAME,F.ORG_ID,G.ORG_ID OFFICE_ID,\n" );
            sql2.append("           K.ID STATUS\n" );
            sql2.append("            FROM SE_BA_TRAVEL_COST_TMP T,DIC_TREE B,DIC_TREE C,DIC_TREE D,DIC_TREE E,TM_ORG F,TM_ORG G,DIC_TREE K\n" );
            sql2.append("           WHERE T.COSTS_TYPE_NA = B.DIC_VALUE AND B.PARENT_ID='305100'\n" );
            sql2.append("           AND T.TRAVEL_TIMES_NA = C.DIC_VALUE AND C.PARENT_ID='301200'\n" );
            sql2.append("           AND T.TRAVEL_DATE_NA = D.DIC_VALUE AND D.PARENT_ID='302300'\n" );
            sql2.append("            AND T.VEHICLE_TYPE_NA = E.DIC_VALUE AND E.PARENT_ID= '301300'\n" );
            sql2.append("            AND T.ORG_CODE=F.CODE  AND  F.PID=G.ORG_ID AND  T.STATUS_NA=K.DIC_VALUE AND K.PARENT_ID=100200\n" );
            sql2.append("           AND T.USER_ACCOUNT = '" + user.getAccount() + "'\n");
            sql2.append("           ) N\n" );
            sql2.append("           ON (M.ORG_CODE = N.ORG_CODE    )\n" );
            sql2.append("           WHEN MATCHED THEN\n" );
            sql2.append("           UPDATE  SET  M.COSTS_TYPE= N.COSTS_TYPE,M.TRAVEL_TIMES=N.TRAVEL_TIMES,\n" );
            sql2.append("            M.TRAVEL_DATE=N.TRAVEL_DATE,M.VEHICLE_TYPE=N.VEHICLE_TYPE,M.ORG_ID=N.ORG_ID ,M.ORG_NAME=N.ONAME,");
            sql2.append("         M.OFFICE_ID=N.OFFICE_ID,M.STATUS = N.STATUS");
			factory.update(sql2.toString(), null);*/
            
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    /**
     * 
     *
     * 工时单价临时表(SE_BA_CLAIM_CODE_TMP)
     * @param list
     * @param baixiaoliang 2014-9-6
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_CLAIM_CODE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
        	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
        	Date date =null;
            String sql = " DELETE FROM SE_BA_CLAIM_CODE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaClaimCodeTmpVO vo = new SeBaClaimCodeTmpVO();
                    vo.setRowNum(key); 
                    vo.setOrgCode(cells[2].getContents().trim());
                    vo.setOrgName(cells[3].getContents().trim());
                    vo.setOfficeNa(cells[4].getContents().trim());
                    vo.setTimeTypeNa(cells[5].getContents().trim());                                 
                    vo.setBaseTaskTime(cells[6].getContents().trim());
                                       
                    vo.setTaskTimeRatio(cells[7].getContents().trim());
                    vo.setUnitPrice(cells[8].getContents().trim());
                    date = fmt.parse(cells[9].getContents().trim());
                    vo.setStartDate(date);
                    date = fmt.parse((cells[10].getContents().trim()));
                    vo.setEndDate(date);
                    //vo.setTravelStatusNa(cells[6].getContents().trim());
                    vo.setStatusNa(cells[11].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
                       
 /*                      
            StringBuffer sql2= new StringBuffer();
            sql2.append("MERGE INTO SE_BA_CLAIM_CODE_TMP M\n" );
            sql2.append("USING (SELECT T.ORG_CODE,\n" );
            sql2.append("              B.ID TIME_TYPE,\n" );
            sql2.append("              T.Time_Type_Na,\n" );
            sql2.append("              F.ONAME,\n" );
            sql2.append("              F.ORG_ID,\n" );
            sql2.append("              G.ORG_ID OFFICE_ID,\n" );
            sql2.append("              T.BASE_TASK_TIME,\n" );
            sql2.append("              T.TASK_TIME_RATIO,\n" );
            sql2.append("              T.UNIT_PRICE,\n" );
            sql2.append("              T.START_DATE,\n" );
            sql2.append("              T.END_DATE,K.ID STATUS\n" );
            sql2.append("         FROM SE_BA_CLAIM_CODE_TMP T, DIC_TREE B, TM_ORG F, TM_ORG G,DIC_TREE K\n" );
            sql2.append("        WHERE T.Time_Type_Na = B.DIC_VALUE\n" );
            sql2.append("          AND B.PARENT_ID = '302300'\n" );
            sql2.append("          AND T.ORG_CODE = F.CODE\n" );
            sql2.append("          AND F.PID = G.ORG_ID AND  T.STATUS_NA=K.DIC_VALUE AND K.PARENT_ID=100200\n" );
            sql2.append("          AND T.USER_ACCOUNT = '" + user.getAccount() + "'\n");
            sql2.append("           ) N\n" );
            sql2.append("ON (M.ORG_CODE = N.ORG_CODE )\n" );
            sql2.append("WHEN MATCHED THEN\n" );
            sql2.append("  UPDATE\n" );
            sql2.append("     SET M.BASE_TASK_TIME  = N.BASE_TASK_TIME,\n" );
            sql2.append("         M.TASK_TIME_RATIO = N.TASK_TIME_RATIO,\n" );
            sql2.append("         M.UNIT_PRICE      = N.UNIT_PRICE,\n" );
            sql2.append("         M.START_DATE      = N.START_DATE,\n" );
            sql2.append("         M.END_DATE        = N.END_DATE,\n" );
            sql2.append("         M.TIME_TYPE=N.TIME_TYPE,M.STATUS = N.STATUS,\n" );
            sql2.append("         M.OFFICE_ID=N.OFFICE_ID,M.ORG_ID=N.ORG_ID ,M.ORG_NAME=N.ONAME");


			factory.update(sql2.toString(), null);*/
            
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    /**
     * 
     *
     * 工时定额临时表(SE_BA_TASK_AMOUNT_TMP)
     * @param list
     * @param baixiaoliang 2014-9-6
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_TASK_AMOUNT_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
        	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
        	Date date =null;
            String sql = " DELETE FROM SE_BA_TASK_AMOUNT_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaTaskAmountTmpVO vo = new SeBaTaskAmountTmpVO();
                    vo.setRowNum(key);
                    vo.setTimeCode(cells[2].getContents().trim());
                    vo.setTimeName(cells[3].getContents().trim());                                 
//                    vo.setPositionCode(cells[3].getContents().trim());                                      
                    vo.setAmountSet(cells[4].getContents().trim());
                    vo.setStatusNa(cells[5].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
                       
                       
/*            StringBuffer sql2= new StringBuffer();

          
            sql2.append("MERGE INTO SE_BA_TASK_AMOUNT_TMP M\n" );
            sql2.append("          USING (SELECT\n" );
            sql2.append("\n" );
            sql2.append("                  T.TIME_CODE,C.ID STATUS\n" );
            sql2.append("                   FROM SE_BA_TASK_AMOUNT_TMP T,DIC_TREE C\n" );
            sql2.append("                  WHERE T.STATUS_NA=C.DIC_VALUE AND C.PARENT_ID=100200\n" );
            sql2.append("           AND T.USER_ACCOUNT = '" + user.getAccount() + "'\n");
            sql2.append("                 ) N\n" );
            sql2.append("          ON (M.TIME_CODE = N.TIME_CODE )\n" );
            sql2.append("          WHEN MATCHED THEN\n" );
            sql2.append("            UPDATE SET M.STATUS = N.STATUS ");

			factory.update(sql2.toString(), null);*/
            
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 
     *
     * 故障模式与工时定额关系临时表(SE_BA_FAULT_TASKTIME_TMP)
     * @param list
     * @param baixiaoliang 2014-9-9
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_FAULT_TASKTIME_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
        	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
        	Date date =null;
            String sql = " DELETE FROM SE_BA_FAULT_TASKTIME_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaFaultTasktimeTmpVO vo = new SeBaFaultTasktimeTmpVO();
                    vo.setRowNum(key); 
                    vo.setFaultPatternCode(cells[2].getContents().trim());
                    vo.setFaultPatternName(cells[2].getContents().trim());  
                    vo.setTimeCode(cells[4].getContents().trim());
                    vo.setTimeName(cells[4].getContents().trim());                                 
//                    vo.setPositionCode(cells[3].getContents().trim()); 
                    vo.setStatusNa(cells[6].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
                       
                       
     /*       StringBuffer sql2= new StringBuffer();
            sql2.append("MERGE INTO SE_BA_FAULT_TASKTIME_TMP M\n" );
            sql2.append("USING (SELECT\n" );
            sql2.append("\n" );
            sql2.append("        B.PATTERN_ID,\n" );
            sql2.append("        B.FAULT_CODE,\n" );
            sql2.append("        B.FAULT_NAME,\n" );
            sql2.append("        C.AMOUNT_ID,\n" );
            sql2.append("        C.TIME_CODE,\n" );
            sql2.append("        C.TIME_NAME\n" );
            sql2.append("         FROM SE_BA_FAULT_TASKTIME_TMP T,\n" );
            sql2.append("              SE_BA_FAULT_PATTERN      B,\n" );
            sql2.append("              SE_BA_TASK_AMOUNT        C\n" );
            sql2.append("        WHERE T.TIME_CODE = C.TIME_CODE\n" );
            sql2.append("          AND T.FAULT_PATTERN_CODE = B.FAULT_CODE\n" );
            sql2.append("          AND T.USER_ACCOUNT = '" + user.getAccount() + "'\n");
            sql2.append("          ) N\n" );
            sql2.append("ON (M.FAULT_PATTERN_CODE = N.FAULT_CODE AND M.TIME_CODE = N.TIME_CODE)\n" );
            sql2.append("WHEN MATCHED THEN\n" );
            sql2.append("  UPDATE\n" );
            sql2.append("     SET M.TIME_NAME  = N.TIME_NAME,\n" );
            sql2.append("         M.PATTERN_ID = N.PATTERN_ID,\n" );
            sql2.append("         M.AMOUNT_ID  = N.AMOUNT_ID,");
            sql2.append("         M.FAULT_PATTERN_NAME=N.FAULT_NAME");

			factory.update(sql2.toString(), null);*/
            
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 
     *
     * 服务站激励系数导入临时表(SE_BA_EXCITATION_TMP)
     * @param list
     * @param baixiaoliang 2014-9-9
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_EXCITATION_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
        	DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");  
        	Date date =null;
            String sql = " DELETE FROM SE_BA_EXCITATION_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaExcitationTmpVO vo = new SeBaExcitationTmpVO();
                    vo.setRowNum(key); 
                    vo.setModelsCode(cells[2].getContents().trim());
                    vo.setTypeCode(cells[3].getContents().trim());
                    //vo.setCoefficientType(cells[4].getContents().trim());
                    vo.setCoefficientTypeNa(cells[4].getContents().trim());
                    vo.setCoefficientRadio(cells[5].getContents().trim());
                    //vo.setFaultPatternName(cells[2].getContents().trim());  
               
                   // vo.setTimeName(cells[4].getContents().trim());                                 
//                    vo.setPositionCode(cells[3].getContents().trim());  
                    date = fmt.parse(cells[6].getContents().trim());
                    vo.setStartDate(date);
                    date = fmt.parse((cells[7].getContents().trim()));
                    vo.setEndDate(date);
                    vo.setStatusNa(cells[8].getContents().trim());  
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
                       
   /*                    
            StringBuffer sql2= new StringBuffer();
            sql2.append("MERGE INTO SE_BA_EXCITATION_TMP M\n" );
            sql2.append("USING (SELECT\n" );
            sql2.append("              B.TYPE_ID,\n" );
            sql2.append("              B.TYPE_CODE,\n" );
            sql2.append("              B.TYPE_NAME,\n" );
            sql2.append("              C.MODELS_NAME,\n" );
            sql2.append("              C.MODELS_ID,\n" );
            sql2.append("              C.MODELS_CODE,\n" );
            sql2.append("              D.DIC_VALUE COEFFICIENT_TYPE_NA,\n" );
            sql2.append("              T.COEFFICIENT_TYPE\n" );
            sql2.append("         FROM SE_BA_EXCITATION_TMP T,\n" );
            sql2.append("              SE_BA_ENGINE_TYPE    B,\n" );
            sql2.append("              MAIN_MODELS          C,\n" );
            sql2.append("              DIC_TREE             D\n" );
            sql2.append("        WHERE T.TYPE_CODE = B.TYPE_CODE(+)\n" );
            sql2.append("          AND T.MODELS_CODE = C.MODELS_CODE(+)\n" );
            sql2.append("          AND T.COEFFICIENT_TYPE = D.DIC_CODE\n" );
            sql2.append("          AND D.PARENT_ID = '303300'\n" );
            sql2.append("          AND T.USER_ACCOUNT = '" + user.getAccount() + "'\n");
            sql2.append("\n" );
            sql2.append("       ) N\n" );
            sql2.append("ON (M.TYPE_CODE = N.TYPE_CODE AND M.MODELS_CODE = N.MODELS_CODE AND M.COEFFICIENT_TYPE=N.COEFFICIENT_TYPE )\n" );
            sql2.append("WHEN MATCHED THEN\n" );
            sql2.append("  UPDATE\n" );
            sql2.append("     SET M.COEFFICIENT_TYPE_NA = N.COEFFICIENT_TYPE_NA,\n" );
            sql2.append("         M.ENGINE           = N.TYPE_ID,\n" );
            sql2.append("         M.MODELS_ID        = N.MODELS_ID,");
            sql2.append("         M.MODELS_NAME        = N.MODELS_NAME,");
            sql2.append("         M.TYPE_NAME        = N.TYPE_NAME");


			factory.update(sql2.toString(), null);*/
            
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
  
    /**
     * 车辆导入
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BU_RETURNORDER_PACK_FOCUS(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            String sql = " DELETE FROM SE_BU_RETURNORDER_DTL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBuReturnorderDtlTmpVO tmpVo=new SeBuReturnorderDtlTmpVO();
                    tmpVo.setRowNum(cells[1].getContents().trim());
                    tmpVo.setClaimNo(cells[2].getContents().trim());
                    tmpVo.setFaultCode(cells[3].getContents().trim());
                    tmpVo.setVin(cells[4].getContents().trim());
                    tmpVo.setOrderNo(cells[5].getContents().trim());
                    tmpVo.setPartCode(cells[6].getContents().trim());
                    tmpVo.setPartName(cells[7].getContents().trim());
                    tmpVo.setBoxNo(cells[8].getContents().trim());
                    tmpVo.setProsupplyCode(cells[9].getContents().trim());
                    tmpVo.setUserAccount(user.getAccount());
                    factory.insert(tmpVo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    /**
     * 导入移库出库配件明细
     * @param list
     * @param user
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_MOVE_OUT_PART_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            String sql = " DELETE FROM PT_BU_MOVE_OUT_PART_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuMoveOutPartTmpVO tmpVo=new PtBuMoveOutPartTmpVO();
                    tmpVo.setRowNum(cells[0].getContents().trim());
                    tmpVo.setPartCode(cells[1].getContents().trim());
                    tmpVo.setSupplierCode(cells[2].getContents().trim());
                    tmpVo.setPositionCode(cells[3].getContents().trim());
                    tmpVo.setMoveCount(cells[4].getContents().trim());
                    tmpVo.setUserAccount(user.getAccount());
                    factory.insert(tmpVo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
  	/**
     * 向服务商星级临时表中插入信息
     *
     * 安全库存临时表(MAIN_DEALER_STAR_TMP)
     * @param list
     * @param fanpeng 2014-09-24
     * @param bParams
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertMAIN_DEALER_STAR_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM MAIN_DEALER_STAR_TMP WHERE USER_ACCOUNT='" + user.getPersonName() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    MainDealerStarTmpVO vo = new MainDealerStarTmpVO();
                    vo.setRowNo(cells[0].getContents().trim());
                    vo.setDealerCode(cells[1].getContents().trim());
                    vo.setDealerStar(cells[2].getContents().trim());
                    vo.setUserAccount(user.getPersonName());
                    
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_SUP_INVOICE_SUMMARY_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_SUP_INVOICE_SUMMARY_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuSupInvoiceSummaryTmpVO VO=new PtBuSupInvoiceSummaryTmpVO();
                    VO.setTmpNo(key);
                    VO.setSupplierCode(cells[1].getContents().trim());
                    VO.setAccountType(cells[3].getContents().trim());
                    VO.setSelectMonth(cells[4].getContents().trim());
                    VO.setSettleAmount(cells[5].getContents().trim());
                    VO.setUserAccount(user.getAccount());
                    factory.insert(VO);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertSE_BA_OLDPARTMANAGE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM SE_BA_OLDPARTMANAGE_TMP WHERE USER_ACCOUNT='"+user.getAccount()+"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    SeBaOldpartmanageTmpVO VO=new SeBaOldpartmanageTmpVO();
                    VO.setRowNum(key);
                    VO.setPartCode(cells[1].getContents().trim());
                    VO.setPartName(cells[2].getContents().trim());
                    VO.setOldManageFee(cells[3].getContents().trim());
                    VO.setUserAccount(user.getAccount());
                    factory.insert(VO);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    public static void insertPT_BA_PART_SUPPLIER_RL_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_PART_SUPPLIER_RL_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaPartSupplierRlTmpVO vo=new PtBaPartSupplierRlTmpVO();
                    vo.setTmpNo(key);
                    vo.setPartCode(cells[1].getContents().trim());
                    vo.setSupplierCode(cells[2].getContents().trim());
                    vo.setApplyCycle(cells[3].getContents().trim());
                    vo.setPartStatus(cells[4].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_ARMY_PRICE_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_ARMY_PRICE_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaArmyPriceTmpVO vo=new PtBaArmyPriceTmpVO();
                    vo.setTmpNo(key);
                    vo.setPartCode(cells[1].getContents().trim());
                    vo.setPartName(cells[2].getContents().trim());
                    vo.setArmyPrice(cells[3].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BU_PART_CONT_CHK_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BU_PART_CONT_CHK_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            String sql1 = " DELETE FROM PT_BU_PART_CONT_CHK ";
            factory.delete(sql1, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBuPartContChkTmpVO vo=new PtBuPartContChkTmpVO();
                    vo.setTmpNo(key);
                    vo.setPartCode(cells[1].getContents().trim());
                    vo.setPartName(cells[2].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void insertPT_BA_WAREHOUSE_KEEPER_TMP(List<Map> list,User user,String bParams) throws Exception {

        DBFactory factory = new DBFactory();
        ActionContext atx = ActionContext.getContext();
        atx.setDBFactory(factory);
        try {
            //根据user账号删除临时表数据
            //删除临时表
            String sql = " DELETE FROM PT_BA_WAREHOUSE_KEEPER_TMP WHERE USER_ACCOUNT='" + user.getAccount() +"' ";
            factory.delete(sql, null);
            factory.getConnection().commit();
            for (int i = 0; i < list.size(); i++) {
                //插入临时表数据
                Map<String, Cell[]> m = list.get(i);
                Set<String> keys = m.keySet();
                Iterator it = keys.iterator();
                String key = "";
                while(it.hasNext()) {
                    key=(String)it.next();
                    Cell[] cells=(Cell[])m.get(key);
                    PtBaWarehouseKeeperTmpVO vo=new PtBaWarehouseKeeperTmpVO();
                    vo.setTmpNo(key);
                    vo.setPartCode(cells[1].getContents().trim());
                    vo.setPartName(cells[2].getContents().trim());
                    vo.setWarehouseCode(cells[3].getContents().trim());
                    vo.setWarehouseName(cells[4].getContents().trim());
                    vo.setKeepManAcount(cells[5].getContents().trim());
                    vo.setKeepManName(cells[6].getContents().trim());
                    vo.setUserAccount(user.getAccount());
                    factory.insert(vo);
                }
            }
            factory.getConnection().commit();
        }
        catch (Exception e) {
            factory.getConnection().rollback();
            e.printStackTrace();
        } finally {
            if(factory != null)
                factory.getConnection().close();
            factory.setFactory(null);
        }
    }
}