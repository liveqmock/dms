package com.org.dms.action.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.MoveStockOutMngDao;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.dms.vo.part.PtBuStockOutVO_Ext;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 移库出库Action
 *
 * @user : lichuang
 * @date : 2014-08-06
 */
public class MoveStockOutMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "MoveStockOutMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private MoveStockOutMngDao dao = MoveStockOutMngDao.getInstance(atx);


    public void getWarehouse () throws Exception {
        try {
            //获取session中的user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            BaseResultSet bs = dao.getWarehouse(user);
            //RequestWrapper request = atx.getRequest();
            //request.setAttribute("resultlist", qs);
            atx.setOutData("bs",bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 查询出库单
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
            BaseResultSet bs = dao.searchOutBill(page, user, conditions);
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
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuStockOutVO vo = new PtBuStockOutVO();
            PtBuStockOutVO_Ext voExt = new PtBuStockOutVO_Ext();
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);

            // 重复订单验证(相同出库库区和收货库区的订单)
            QuerySet querySet = dao.insertOutBillCheck(hm.get("WAREHOUSE_ID")+hm.get("DES_WAREHOUSE_ID"));
            if (querySet.getRowCount() > 0) {
                throw new Exception("已经存在相同的出库仓库和目标仓库订单!");
            }

            //将hashmap映射到vo对象中,完成匹配赋值
            vo.setValue(hm);
            String outId = dao.getId();
            String outNo = dao.createOutBillNo(hm.get("WAREHOUSE_CODE"));
            vo.setOutId(outId);
            vo.setOutNo(outNo);
            vo.setReceiveWarehouse(hm.get("DES_WAREHOUSE_ID"));
            vo.setCompanyId(user.getCompanyId());
            vo.setOutType(DicConstant.CKLX_03);
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
/*            try {
                // 移库时间
                vo.setMoveDate((Date) new SimpleDateFormat("yyyy-MM-dd HH:mm").parseObject(hm.get("MOVE_DATE")));
            } catch (Exception e) {
                throw new Exception("请输入正确移库时间！");
            }*/
            vo.setMoveDate(Pub.getCurrentDate());
            //通过dao，执行插入
            dao.insertOutBill(vo);
            //返回插入结果和成功信息
            voExt.setValue(hm);
            voExt.setOutId(outId);
            voExt.setOutNo(outNo);
            voExt.setReceiveWarehouse(hm.get("DES_WAREHOUSE_ID"));
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
            // 移库人
            tempVO.setMoveMan(hm.get("MOVE_MAN"));
            try {
                // 移库时间
                tempVO.setMoveDate((Date) new SimpleDateFormat("yyyy-MM-dd HH:mm").parseObject(hm.get("MOVE_DATE")));
            } catch (Exception e) {
                throw new Exception("请输入正确移库时间！");
            }
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
     * 查询配件
     *
     * @throws Exception
     */
    public void searchPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String outId = Pub.val(request, "outId");
            String warehouseId = Pub.val(request, "warehouseId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPart(page, user, conditions, outId, warehouseId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增出库单配件
     *
     * @throws Exception
     */
    public void insertOutBillPart() throws Exception {
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
            String outId = hm.get("OUT_ID");//出库单ID
            String dtlIds = hm.get("DTLIDS");//库存明细ID集合(逗号分隔)
            String ouaAmounts = hm.get("OUTAMOUNTS");//数量(逗号分隔)
            
            QuerySet  checkPlanPrice = dao.checkPlanPrice(dtlIds);
            String codes = checkPlanPrice.getString(1, "CODES");
            if(!"".equals(codes)&&codes!=null){
            	throw new Exception("配件"+checkPlanPrice.getString(1, "CODES")+"尚未维护计划价格，请维护计划价格之后再保存");
            }
            QuerySet getWareHouseId = dao.whId(outId);
            String WAREHOUSE_ID = getWareHouseId.getString(1, 1);
            
            /****校验库存锁定状态*******************************/
            QuerySet checkLock = dao.checkLock1(dtlIds,WAREHOUSE_ID);
            String locks = checkLock.getString(1, "CODES");
            if(!"".equals(locks)&&locks!=null){
            	throw new Exception("配件"+locks+"处于库存锁定状态，不能进行入库操作");
            }
            /****校验库存锁定状态*******************************/
            QuerySet checkLock1 = dao.checkLock2(dtlIds,WAREHOUSE_ID);
            String locks2 = checkLock1.getString(1, "CODES");
            if(!"".equals(locks2)&&locks2!=null){
            	throw new Exception("配件"+locks2+"处于盘点锁定状态，不能进行入库操作");
            }
            String[] dtlIdArr = dtlIds.split(",");
            String[] ouaAmountArr = ouaAmounts.split(",");
            for (int i = 0; i < dtlIdArr.length; i++) {
                String stockDtlId=dtlIdArr[i];
                String amount=ouaAmountArr[i];
                //插入移库出库明细表
                dao.insertOutBillDtl(outId, stockDtlId, amount, user);
                //更新库存明细表可用库存数量
                dao.updateStockDtlAva(stockDtlId, amount, user);
                //更新库存主表可用库存数量
                dao.updateStockAva(stockDtlId, amount, user);
                
                String url="移库出库出库单保存明细占用总数。/action/part/storageMng/stockInMng/stockOutMng/MoveStockOutMngAction/insertOutBillPart.ajax";
                QuerySet getId = dao.getPartId(dtlIdArr[i]);
                dao.insetStockDtl(amount,user,stockDtlId,url,getId.getString(1, "PART_ID"));
            }
            
            //返回插入结果和成功信息
            atx.setOutMsg("", "新增成功！");
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
        page.setPageRows(10000);
        try {
            String OUT_ID = Pub.val(request, "OUT_ID");//出库单ID
            String WAREHOUSE_ID = Pub.val(request, "WAREHOUSE_ID");//仓库ID
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchOutBillPart(page, user, conditions, OUT_ID, WAREHOUSE_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
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
        String outId = Pub.val(request, "outId");
        String warehouseId = Pub.val(request, "warehouseId");
        String warehouseType = Pub.val(request, "warehouseType");
        try {
            //配件出库公共方法
            this.partStockOut(outId, warehouseId);
            //根据目标仓库类型处理，插入操作人与零件关系
            if("100101".equals(warehouseType))
            {
                dao.updateStockInKeeper(outId);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "出库成功！");
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
        try {
            String outId = Pub.val(request, "outId");
            String warehouseId = Pub.val(request, "warehouseId");

            this.partStockOut(outId, warehouseId);
            //返回插入结果和成功信息
            atx.setOutMsg("", "出库成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
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

            //公共保存出库单明细方法
            this.commonSaveOutBillDtl(hm, request, user);
            //返回插入结果和成功信息
            atx.setOutMsg("", "保存成功！");
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
     * 配件出库公共方法
     *
     * @param outId       出库单ID
     * @param warehouseId 仓库ID
     * @throws Exception
     */
    public void partStockOut(String outId, String warehouseId) throws Exception {

        if (!dao.checkOutBillDtlIsExist(outId)) {
            throw new Exception("该出库单未维护出库清单,无法出库!");
        }

        if (!dao.checkOutBillDtlIsValid(outId)) {
            throw new Exception("出库清单中的配件出库数量不能为0,无法出库!");
        }

        User user = (User) atx.getSession().get(Globals.USER_KEY);

        //将出库单状态置为已出库
        PtBuStockOutVO stockOutVO = new PtBuStockOutVO();
        stockOutVO.setOutId(outId);
    	// 出库完成
    	stockOutVO.setOutStatus(DicConstant.CKDZT_02);
        stockOutVO.setOutDate(Pub.getCurrentDate());
        stockOutVO.setUpdateTime(Pub.getCurrentDate());
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

    /**
     * 公共保存出库单明细方法
     *
     * @param hm
     * @param request
     * @param user
     * @throws Exception
     */
    public void commonSaveOutBillDtl(HashMap<String, String> hm, RequestWrapper request, User user) throws Exception {

        String outId = hm.get("OUT_ID");//出库单ID
        String warehouseId = hm.get("WAREHOUSE_ID");//仓库ID
        String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
//        String partCodes = hm.get("PARTCODES");//配件代码(逗号分隔)
        String partNames = hm.get("PARTNAMES");//配件名称(逗号分隔)
        String positionIds = hm.get("POSITIONIDS");//库位ID(逗号分隔)
        String outAmounts = hm.get("OUTAMOUNTS");//实际出库数量(逗号分隔)
        String remarks = hm.get("REMARKS");//备注(逗号分隔)
        String detailIds = hm.get("DETAILIDS");//出库单明细ID(逗号分隔)
        String supplierIds = hm.get("SUPPLIERIDS");//供应商ID(逗号分隔)
//        String supplierCodes = hm.get("SUPPLIERCODES");//供应商代码(逗号分隔)
//        String supplierNames = hm.get("SUPPLIERNAMES");//供应商名称(逗号分隔)
        String[] partIdArr = partIds.split(",");
//        String[] partCodeArr = partCodes.split(",");
        String[] partNameArr = partNames.split(",");
        String[] positionIdArr = positionIds.split(",");
        String[] outAmountArr = outAmounts.split(",");
        String[] remarkArr = remarks.split(",");
        String[] detailIdArr = detailIds.split(",");
        String[] supplierIdArr = supplierIds.split(",");
//        String[] supplierCodeArr = supplierCodes.split(",");
//        String[] supplierNameArr = supplierNames.split(",");
        if (!"".equals(detailIds)) {

            //保存出库单明细之前 先解锁库存明细和库存
            dao.unLockStockDtl(outId, user);
            dao.unLockStock(warehouseId, outId, user);

            for (int i = 0; i < detailIdArr.length; i++) {
                if ("myNull".equals(remarkArr[i])) {
                    remarkArr[i] = "";
                }
                PtBuStockOutDtlVO dtlVO = new PtBuStockOutDtlVO();
                dtlVO.setDetailId(detailIdArr[i]);
                dtlVO.setPositionId(positionIdArr[i]);
                dtlVO.setOutAmount(outAmountArr[i]);
                dtlVO.setRemarks(remarkArr[i]);
                dtlVO.setUpdateUser(user.getAccount());
                dtlVO.setUpdateTime(Pub.getCurrentDate());
                dao.updateOutBillDtl(dtlVO);

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
     * 删除出库单配件
     *
     * @throws Exception
     */
    public void deleteOutBillPart() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String outId = Pub.val(request, "outId");
        String partId = Pub.val(request, "partId");
        String supplierId=Pub.val(request, "supplierId");
        try {
            //解锁库存明细(根据出库单主键+配件+供应商)
            dao.unLockStockDtlByOutBillDtl(outId,partId, supplierId,user);
            //解锁库存(根据出库单主键+配件+供应商)
            dao.unLockStockByOutBillDtl(outId,partId, supplierId,user);
            //dtlVO.setDetailId(DETAIL_ID);
            dao.deleteOutBillDtl(outId, partId, supplierId);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 保存移库出库明细数据(更新数量和备注)
     *
     * @throws Exception
     */
    public void updateOutBillPart() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String outId = Pub.val(request, "outId");//单个出库ID
        String partId = Pub.val(request, "partId");//单个配件ID
        String supplierId=Pub.val(request, "supplierId");//单个供应商ID
        HashMap<String, String> hm;
        //将request流转换为hashmap结构体
        hm = RequestUtil.getValues(request);
        String positionIds = hm.get("POSITIONIDS");//库位ID(逗号分隔)
        String outAmounts = hm.get("OUTAMOUNTS");//当期出库数量(逗号分隔)
        String yAmounts = hm.get("YAMOUNTS");//原出库数量(逗号分隔)
        String remark = hm.get("REMARK");//单个备注
        try {
//            String[] outIdArr = outId.split("@");
            String[] partIdArr = partId.split("@");
            String[] supplierIdArr = supplierId.split("@");
            String[] remarkArr = remark.split("@");
            
//            String[] positionArr=positionIds.split(",");
            for (int j=0;j<partIdArr.length;j++) {
            	if ("myNull".equals(remarkArr[j])) {
            		remarkArr[j] = "";
            	}
                String positionArrs = positionIds.split("@")[j];
                String outAmountArrs = outAmounts.split("@")[j];
                String yAmountArrs = yAmounts.split("@")[j];
                String[] positionArr = positionArrs.split(",");
                for(int i=0;i<positionArr.length;i++) {
                    String positionId=positionArr[i];
                    String outAmount=outAmountArrs.split(",")[i];
                    String yAmount=yAmountArrs.split(",")[i];
                    if(Integer.parseInt(outAmount)<0){
                    	throw new Exception("出库数量不能小于零.");
                    }
                    //更新库存明细冻结数量(可能增加或者减少,yAmount-outAmount)
                    dao.updateLockStockDtlByOutBillDtl(partIdArr[j], supplierIdArr[j], positionId, yAmount, outAmount, user);
                    //更新库存冻结数量(可能增加或者减少,yAmount-outAmount)
                    dao.updateLockStockByOutBillDtl(partIdArr[j], supplierIdArr[j], positionId, yAmount, outAmount, user);
                    //更新出库明细中的出库数量和备注(outAmount)
                    dao.updateOutBillDtl(outId, partIdArr[j], supplierIdArr[j], positionId, outAmount, remarkArr[j], user);
                    
                    String url="移库出库。/action/part/storageMng/stockInMng/stockOutMng/MoveStockOutMngAction/updateOutBillPart.ajax";
                    
                    dao.insetStockDtl(outAmount,user,outId,url,partIdArr[j]);
                
                }
            }
            
          //更新出库单明细的销售单价/销售金额/计划单价/计划金额/单位
          dao.updateOutBillDtl(outId);
          //更新入库流水的计划价
          dao.updateOutFlow(outId);
            
            
            
            
            
            atx.setOutMsg("", "更新成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 移库出库查询配件导入临时表
     * @throws Exception
     */
    public void movePartImpSearch() throws Exception{
        RequestWrapper request = atx.getRequest();
        PageManager page = new PageManager();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
            BaseResultSet bs = dao.movePartImpSearch(page,conditions,user);
            atx.setOutData("bs", bs);
        }
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    /*导入移库出库配件校验方法
     * 共6处校验
     * 1.校验空的数据
     * 2.校验是否存在非法的配件代码、供应商代码、货位代码
     * 3.校验是否存在重复数据
     * 4.校验供货关系不存在(只校验配件基本档案中是指定供应的配件代码)
     * 5.校验在库存表中不存在记录
     * 6.校验导入数量超过可用库存
     * 7.校验导入数据在出库明细表中不存在
     * */
    public List<ExcelErrors> checkData(String bParams)throws Exception{
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        ExcelErrors errors = null;
        List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
        String warehouseId=bParams.split("@")[0];//warehouseId@outId
        String outId=bParams.split("@")[1];//warehouseId@outId
        QuerySet qs1=dao.checkList1(user);//校验空的数据
        
        if(qs1.getRowCount()>0){
            for(int i=0;i<qs1.getRowCount();i++){
                errors=new ExcelErrors();
                String rowNum=qs1.getString(i+1, "ROW_NUM");
                errors.setRowNum(Integer.parseInt(rowNum));
                errors.setErrorDesc("存在空数据");
                errorList.add(errors);
            }
        }
        if(errorList==null|| errorList.size()==0)
        {
            //校验是否存在非法的配件代码、供应商代码、货位代码
            QuerySet qs2=dao.checkList2(user);
            if(qs2.getRowCount()>0){
                for(int i=0;i<qs2.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum =qs2.getString(i+1,"ROW_NUM");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件代码:"+qs2.getString(i+1,"PART_CODE")+"或者,供应商代码:"+qs2.getString(i+1,"SUPPLIER_CODE")+"或者,货位代码:"+qs2.getString(i+1,"POSITION_CODE")+"在系统中不存在");
                    errorList.add(errors);
                }
            }
            //校验是否存在重复数据
            QuerySet qs3=dao.checkList3(user);
            if(qs3.getRowCount()>0){
                for(int i=0;i<qs3.getRowCount();i++){
                    errors=new ExcelErrors();
                    errors.setErrorDesc("配件代码:"+qs3.getString(i+1,"PART_CODE")+"供应商代码:"+qs3.getString(i+1,"SUPPLIER_CODE")+"货位代码:"+qs3.getString(i+1,"POSITION_CODE")+"在导入文件中重复");
                    errorList.add(errors);
                }
            }
            //校验供货关系不存在(只校验配件基本档案中是指定供应的配件代码)
            QuerySet qs4=dao.checkList4(user);
            if(qs4.getRowCount()>0){
                for(int i=0;i<qs4.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum =qs4.getString(i+1,"ROW_NUM");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件代码:"+qs4.getString(i+1,"PART_CODE")+"供应商代码:"+qs4.getString(i+1,"SUPPLIER_CODE")+"在系统中不存在供货关系");
                    errorList.add(errors);
                }
            }
            //校验在库存表中不存在记录
            QuerySet qs5=dao.checkList5(user,warehouseId);
            if(qs5.getRowCount()>0){
                for(int i=0;i<qs5.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum =qs5.getString(i+1,"ROW_NUM");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件代码:"+qs5.getString(i+1,"PART_CODE")+"供应商代码:"+qs5.getString(i+1,"SUPPLIER_CODE")+"货位代码:"+qs5.getString(i+1,"POSITION_CODE")+"无库存");
                    errorList.add(errors);
                }
            }
            //校验导入数量超过可用库存
            QuerySet qs6=dao.checkList6(user,warehouseId);
            if(qs6.getRowCount()>0){
                for(int i=0;i<qs6.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum =qs6.getString(i+1,"ROW_NUM");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件代码:"+qs6.getString(i+1,"PART_CODE")+"供应商代码:"+qs6.getString(i+1,"SUPPLIER_CODE")+"货位代码:"+qs6.getString(i+1,"POSITION_CODE")+"数量:"+qs6.getString(i+1,"MOVE_COUNT")+"超过可用库存数量");
                    errorList.add(errors);
                }
            }
            //校验导入数据在出库明细表中不存在
            QuerySet qs7=dao.checkList7(user,outId);
            if(qs7.getRowCount()>0){
                for(int i=0;i<qs7.getRowCount();i++){
                    errors=new ExcelErrors();
                    String rowNum =qs7.getString(i+1,"ROW_NUM");
                    errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件代码:"+qs7.getString(i+1,"PART_CODE")+"供应商代码:"+qs7.getString(i+1,"SUPPLIER_CODE")+"货位代码:"+qs7.getString(i+1,"POSITION_CODE")+"已在出库配件中存在");
                    errorList.add(errors);
                }
            }
        }
        if(errorList!=null&&errorList.size()>0){
            return errorList;
        }else{
            return null;
        }
    }
    /**
     * 导入移库出库配件 确定方法
     * @throws Exception
     */
    public void movePartImport()throws Exception{
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String outId = Pub.val(request, "outId");//出库单ID
        String warehouseId = Pub.val(request, "warehouseId");//仓库ID
         try
         {
             //插入库存明细
             dao.insetrOutBillDtl(outId, user);
             //更新库存明细表占用数量
             dao.updateStockDtl(user);
             //更新库存主表占用数量
             dao.updateStock(user, warehouseId);        
             atx.setOutMsg("", "导入成功！");
         }
         catch (Exception e)
         {
             logger.error(e);
             atx.setException(e);
         }
    }
}