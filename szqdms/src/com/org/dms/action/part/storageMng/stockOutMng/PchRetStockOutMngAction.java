package com.org.dms.action.part.storageMng.stockOutMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.PchRetStockOutMngDao;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.dms.vo.part.PtBuStockOutVO_Ext;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 采购退货出库Action
 *
 * @user : lichuang
 * @date : 2014-08-05
 */
public class PchRetStockOutMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PchRetStockOutMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PchRetStockOutMngDao dao = PchRetStockOutMngDao.getInstance(atx);


    /**
     * 查询采购退货单
     *
     * @throws Exception
     */
    public void searchOutBill() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
        	HashMap<String, String> hm = RequestUtil.getValues(request);
        	String ACCOUNT = hm.get("USER_ACCOUNT");
            BaseResultSet bs = dao.searchPchReturn(page, user, conditions,ACCOUNT);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询采购退货单
     *
     * @throws Exception
     */
    public void searchPchRet() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPchRet(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增出库单
     *
     * @throws Exception
     */
    public void insertOutBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuStockOutVO vo = new PtBuStockOutVO();
            PtBuStockOutVO_Ext voExt = new PtBuStockOutVO_Ext();
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            //将hashmap映射到vo对象中,完成匹配赋值
            vo.setValue(hm);
            String outId = dao.getId();
            String outNo = hm.get("ORDER_NO");
            vo.setOutId(outId);
            vo.setOutNo(outNo);
            vo.setCompanyId(user.getCompanyId());
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
            //通过dao，执行插入
            dao.insertOutBill(vo);
            //返回插入结果和成功信息
            voExt.setValue(hm);
            voExt.setOutId(outId);
            voExt.setOutNo(outNo);
            voExt.setCompanyId(user.getCompanyId());
            voExt.setOrgId(user.getOrgId());
            voExt.setOemCompanyId(user.getOemCompanyId());
            voExt.setCreateUser(user.getAccount());
            voExt.setCreateTime(Pub.getCurrentDate());
            voExt.setStatus(DicConstant.YXBS_01);
            atx.setOutMsg(voExt.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改出库单
     *
     * @throws Exception
     */
    public void updateOutBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuStockOutVO tempVO = new PtBuStockOutVO();
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            tempVO.setOutId(hm.get("OUT_ID"));
            tempVO.setRemarks(hm.get("REMARKS"));
            tempVO.setUpdateUser(user.getAccount());
            tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateOutBill(tempVO);
            //返回更新结果和成功信息
            PtBuStockOutVO_Ext voExt = new PtBuStockOutVO_Ext();
            voExt.setValue(hm);
            atx.setOutMsg(voExt.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询出库单配件
     *
     * @throws Exception
     */
    public void searchOutBillPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String RETURN_ID = Pub.val(request, "RETURN_ID");//出库单ID
            String account = Pub.val(request, "account");//出库单ID
            //BaseResultSet：结果集封装对象
            page.setPageRows(99999);
            BaseResultSet bs = dao.searchOutBillPart(page, user, conditions, RETURN_ID,account);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 保存出库单明细
     *
     * @throws Exception
     */
    public void saveOutBillDtl() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            this.commonSaveOutBillDtl(hm, user);
            //返回插入结果和成功信息
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件出库(首页)
     *
     * @throws Exception
     */
    public void partStockOutInIndex() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            String outId = Pub.val(request, "outId");
            String returnId = Pub.val(request, "returnId");
            String warehouseId = Pub.val(request, "warehouseId");

            this.partStockOut(outId,returnId, warehouseId);
            //返回插入结果和成功信息
            atx.setOutMsg("", "出库成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件出库(编辑界面)
     *
     * @throws Exception
     */
    public void partStockOutInEdit() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String RETURN_ID = hm.get("RETURN_ID");
            String DETAILIDS = hm.get("DETAILIDS");
            String PARTIDS = hm.get("PARTIDS");
            String PARTCODES = hm.get("PARTCODES");
            String PARTNAMES = hm.get("PARTNAMES");
            String OUTAMOUNTS = hm.get("OUTAMOUNTS");
            String REMARKS = hm.get("REMARKS");
            String account = hm.get("userAcc");
            String DETAIL_ID[]  =DETAILIDS.split(","); 
            String PART_ID[]  =PARTIDS.split(","); 
            String PART_CODE[]  =PARTCODES.split(","); 
            String PART_NAME[]  =PARTNAMES.split(","); 
            String OUT_AMOUNT[]  =OUTAMOUNTS.split(","); 
            String REMARK[]  =REMARKS.split(","); 
            
            QuerySet  checkPlanPrice = dao.checkPlanPrice(PARTIDS);
            String codes = checkPlanPrice.getString(1, "CODES");
            if(!"".equals(codes)&&codes!=null){
            	throw new Exception("配件"+checkPlanPrice.getString(1, "CODES")+"尚未维护计划价格，请维护计划价格之后再出库");
            }
            
            /******************************************************************/
            /**生成出库单主表信息**/
            QuerySet getWareInfo = dao.getWareInfo(RETURN_ID);
            String WAREHOUSE_ID = getWareInfo.getString(1, "WAREHOUSE_ID");
            String WAREHOUSE_CODE = getWareInfo.getString(1, "WAREHOUSE_CODE");
            String WAREHOUSE_NAME = getWareInfo.getString(1, "WAREHOUSE_NAME");
            String RETURN_NO = getWareInfo.getString(1, "RETURN_NO");
            
            /****校验库存锁定状态*******************************/
            QuerySet checkLock = dao.checkLock1(PARTIDS,WAREHOUSE_ID);
            String locks = checkLock.getString(1, "CODES");
            if(!"".equals(locks)&&locks!=null){
            	throw new Exception("配件"+locks+"处于库存锁定状态，不能进行入库操作");
            }
            /****校验库存锁定状态*******************************/
            QuerySet checkLock1 = dao.checkLock2(PARTIDS,WAREHOUSE_ID);
            String locks2 = checkLock1.getString(1, "CODES");
            if(!"".equals(locks2)&&locks2!=null){
            	throw new Exception("配件"+locks2+"处于盘点锁定状态，不能进行入库操作");
            }
            String outId = "";
            QuerySet  checkOut = dao.checkOut(RETURN_ID);
            if(checkOut.getRowCount()>0){
            	outId = checkOut.getString(1, "OUT_ID");
            }else{
              PtBuStockOutVO o_vo = new PtBuStockOutVO();
              o_vo.setOutNo(RETURN_NO);
              o_vo.setOrderId(RETURN_ID);
              o_vo.setOrderNo(RETURN_NO);
              o_vo.setWarehouseId(WAREHOUSE_ID);
              o_vo.setWarehouseCode(WAREHOUSE_CODE);
              o_vo.setWarehouseName(WAREHOUSE_NAME);
              o_vo.setOutStatus(DicConstant.CKDZT_02);
              o_vo.setOutType(DicConstant.CKLX_04);
              o_vo.setOutDate(Pub.getCurrentDate());
              o_vo.setPrintStatus(DicConstant.DYZT_01);
              o_vo.setCreateUser(account);
              o_vo.setCreateTime(Pub.getCurrentDate());
              o_vo.setCompanyId(user.getCompanyId());
              o_vo.setOemCompanyId(user.getOemCompanyId());
              o_vo.setStatus(DicConstant.YXBS_01);
              o_vo.setOrgId(user.getOrgId());
              dao.insertOutBill(o_vo);
              outId = o_vo.getOutId();
            }

            /******************************************************************/
            /**生成出库单明细表信息**/
            for(int i = 0;i<PART_ID.length;i++){
            	PtBuStockOutDtlVO d_vo = new PtBuStockOutDtlVO();
            	QuerySet getRetInfo = dao.getRetInfo(DETAIL_ID[i],OUT_AMOUNT[i]);
                d_vo.setOutId(outId);
                d_vo.setPartId(PART_ID[i]);
                d_vo.setPartCode(PART_CODE[i]);
                d_vo.setPartName(PART_NAME[i]);
                d_vo.setSupplierId(getRetInfo.getString(1, "SUPPLIER_ID"));
                d_vo.setSupplierCode(getRetInfo.getString(1, "SUPPLIER_CODE"));
                d_vo.setSupplierName(getRetInfo.getString(1, "SUPPLIER_NAME"));
                d_vo.setPositionId(getRetInfo.getString(1, "POSITION_ID"));
                d_vo.setOutAmount(OUT_AMOUNT[i]);
                if ("myNull".equals(REMARK[i])) {
                	REMARK[i] = "";
                }else{
                	d_vo.setRemarks(REMARK[i]);
                }
                d_vo.setPlanPrice(getRetInfo.getString(1, "PLAN_PRICE"));
                d_vo.setPlanAmount(getRetInfo.getString(1, "PLAN_AMOUNT"));
                d_vo.setUnit(getRetInfo.getString(1, "UNIT"));
                d_vo.setKeepMan(account);
                d_vo.setCreateUser(account);
                d_vo.setCreateTime(Pub.getCurrentDate());
                dao.insertOutBillDtl(d_vo);
                
                
                String url="采购退货出库。/action/part/storageMng/stockInMng/stockOutMng/PchRetStockOutMngAction/partStockOutInEdit.ajax";
                dao.insetStockDtl(OUT_AMOUNT[i],OUT_AMOUNT[i],OUT_AMOUNT[i],user,outId,url,PART_ID[i]);
            }
            /**关闭采购退货单**/
            QuerySet checkFinish = dao.checkFinish(RETURN_ID);
            if(checkFinish.getRowCount()==0){
            	/**释放冻结**/
                dao.updateStockDtl(outId, user);
                /******************************************************************/
                /**减少总库存**/
                dao.updateStock(WAREHOUSE_ID, outId, user);
                /******************************************************************/
                /**生成出库流水信息**/
                dao.insertOutFlow(outId, user);
                /******************************************************************/
            	PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
                r_vo.setReturnId(RETURN_ID);
                r_vo.setReturnStatus(DicConstant.CGTHDZT_04);
                r_vo.setUpdateUser(account);
                r_vo.setCloseDate(Pub.getCurrentDate());
                r_vo.setUpdateTime(Pub.getCurrentDate());
                dao.updatePchReturn(r_vo);
            }
            
            /******************************************************************/
            //更新出库单明细的销售单价/销售金额/计划单价/计划金额/单位
            dao.updateOutBillDtl(outId);
            //更新入库流水的计划价
            dao.updateOutFlow(outId);
            /******************************************************************/
            //返回插入结果和成功信息
            atx.setOutMsg("", "出库成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除出库单
     *
     * @throws Exception
     */
    public void deleteOutBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String outId = Pub.val(request, "outId");
        String warehouseId = Pub.val(request, "warehouseId");
        try {

            //解锁库存明细和库存
            dao.unLockStockDtl(outId, user);
            dao.unLockStock(warehouseId, outId, user);

            PtBuStockOutVO delVo = new PtBuStockOutVO();
            delVo.setOutId(outId);
            //删除出库单
            dao.deleteOutBill(delVo);
            //删除出库单明细
            dao.deleteOutBillDtl(outId);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 公共保存出库单明细
     *
     * @param hm
     * @param user
     * @throws Exception
     */
    public void commonSaveOutBillDtl(HashMap<String, String> hm, User user) throws Exception {
        String outId = hm.get("OUT_ID");//出库单ID
//        String returnId = hm.get("RETURN_ID");//退货单ID
        String warehouseId = hm.get("WAREHOUSE_ID");//仓库ID
        String detailIds = hm.get("DETAILIDS");//退货单明细(逗号分隔)
        String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
        String partCodes = hm.get("PARTCODES");//配件代码(逗号分隔)
        String partNames = hm.get("PARTNAMES");//配件名称(逗号分隔)
        String positionIds = hm.get("POSITIONIDS");//库位ID(逗号分隔)
        String outAmounts = hm.get("OUTAMOUNTS");//实际出库数量(逗号分隔)
        String remarks = hm.get("REMARKS");//备注(逗号分隔)
        String supplierIds = hm.get("SUPPLIERIDS");//供应商ID(逗号分隔)
        String supplierCodes = hm.get("SUPPLIERCODES");//供应商代码(逗号分隔)
        String supplierNames = hm.get("SUPPLIERNAMES");//供应商名称(逗号分隔)
        String[] detailIdArr = detailIds.split(",");
        String[] partIdArr = partIds.split(",");
        String[] partCodeArr = partCodes.split(",");
        String[] partNameArr = partNames.split(",");
        String[] positionIdArr = positionIds.split(",");
        String[] outAmountArr = outAmounts.split(",");
        String[] remarkArr = remarks.split(",");
        String[] supplierIdArr = supplierIds.split(",");
        String[] supplierCodeArr = supplierCodes.split(",");
        String[] supplierNameArr = supplierNames.split(",");
        if (!"".equals(detailIds)) {

            //保存出库单明细之前 先解锁库存明细和库存
            dao.unLockStockDtl(outId, user);
            dao.unLockStock(warehouseId, outId, user);

            for (int i = 0; i < detailIdArr.length; i++) {
                if ("myNull".equals(remarkArr[i])) {
                    remarkArr[i] = "";
                }
                //校验出库单明细是否存在
                String outBillDtlId = dao.checkOutBillDtlIsExist(outId, partIdArr[i], positionIdArr[i], supplierIdArr[i], user);
                if ("".equals(outBillDtlId)) {//不存在 插入
                    PtBuStockOutDtlVO stockOutDtlVO = new PtBuStockOutDtlVO();
                    stockOutDtlVO.setOutId(outId);
                    stockOutDtlVO.setPositionId(positionIdArr[i]);
                    stockOutDtlVO.setPartId(partIdArr[i]);
                    stockOutDtlVO.setPartCode(partCodeArr[i]);
                    stockOutDtlVO.setPartName(partNameArr[i]);
                    stockOutDtlVO.setOutAmount(outAmountArr[i]);
                    stockOutDtlVO.setRemarks(remarkArr[i]);
                    stockOutDtlVO.setSupplierId(supplierIdArr[i]);
                    stockOutDtlVO.setSupplierCode(supplierCodeArr[i]);
                    stockOutDtlVO.setSupplierName(supplierNameArr[i]);
                    stockOutDtlVO.setCreateTime(Pub.getCurrentDate());
                    stockOutDtlVO.setCreateUser(user.getAccount());
                    dao.insertOutBillDtl(stockOutDtlVO);
                } else {//存在 更新
                    PtBuStockOutDtlVO stockOutDtlVO = new PtBuStockOutDtlVO();
                    stockOutDtlVO.setDetailId(outBillDtlId);
                    stockOutDtlVO.setPositionId(positionIdArr[i]);
                    stockOutDtlVO.setOutAmount(outAmountArr[i]);
                    stockOutDtlVO.setRemarks(remarkArr[i]);
                    stockOutDtlVO.setUpdateTime(Pub.getCurrentDate());
                    stockOutDtlVO.setUpdateUser(user.getAccount());
                    dao.updateOutBillDtl(stockOutDtlVO);
                }

                //校验可用库存是否满足出库数量
                String availableAmount = dao.queryPositionAvailableAmount(partIdArr[i], positionIdArr[i], supplierIdArr[i]);
                if (Integer.parseInt(outAmountArr[i]) > Integer.parseInt(availableAmount)) {
                    throw new Exception("配件名称: " + partNameArr[i] + " 的可用库存已变更为 " + availableAmount + ",不满足当前出库数量!");
                }
            }

            //锁定库存和库存明细
            dao.lockStockDtl(outId, user);
            dao.lockStock(warehouseId, outId, user);
        }
    }

    /**
     * 配件出库公共方法
     *
     * @param outId       出库单ID
     * @param returnId    退货单ID
     * @param warehouseId 仓库ID
     * @throws Exception
     */
    public void partStockOut(String outId,String returnId, String warehouseId) throws Exception {

        if (!dao.checkOutBillDtlIsExist(outId)) {
            throw new Exception("该出库单未维护出库清单,无法出库!");
        }

        User user = (User) atx.getSession().get(Globals.USER_KEY);

        //将采购退货单的退货单状态置为已关闭
        PtBuPchReturnVO returnVO = new PtBuPchReturnVO();
        returnVO.setReturnId(returnId);
        returnVO.setReturnStatus(DicConstant.CGTHDZT_04);
        returnVO.setUpdateTime(Pub.getCurrentDate());
        returnVO.setUpdateUser(user.getAccount());
        dao.updatePchReturn(returnVO);

        //将出库单状态置为已出库
        PtBuStockOutVO stockOutVO = new PtBuStockOutVO();
        stockOutVO.setOutId(outId);
        stockOutVO.setOutStatus(DicConstant.CKDZT_02);
        stockOutVO.setOutDate(Pub.getCurrentDate());
        stockOutVO.setUpdateTime(Pub.getCurrentDate());
        stockOutVO.setUpdateUser(user.getAccount());
        dao.updateOutBill(stockOutVO);

        //更新库存
        dao.updateStock(warehouseId, outId, user);
        //更新库存明细
        dao.updateStockDtl(outId, user);
        //记录出库流水
        dao.insertOutFlow(outId, user);
    }
}