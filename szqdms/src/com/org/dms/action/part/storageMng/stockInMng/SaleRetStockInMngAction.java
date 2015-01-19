package com.org.dms.action.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockInMng.SaleRetStockInMngDao;
import com.org.dms.vo.part.*;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 销售退件入库Action
 *
 * @user : lichuang
 * @date : 2014-08-06
 */
public class SaleRetStockInMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "SaleRetStockInMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private SaleRetStockInMngDao dao = SaleRetStockInMngDao.getInstance(atx);


    /**
     * 查询入库单
     *
     * @throws Exception
     */
    public void searchInBill() throws Exception {
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
            BaseResultSet bs = dao.searchInBill(page, user, conditions,ACCOUNT);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询销售退件单
     *
     * @throws Exception
     */
    public void searchRet() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String WAREHOUSE_ID = Pub.val(request, "WAREHOUSE_ID");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchRet(page, user, conditions, WAREHOUSE_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询入库单配件
     *
     * @throws Exception
     */
    public void searchInBillPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            page.setPageRows(10000);
            String IN_ID = Pub.val(request, "IN_ID");//入库单ID
            String ORDER_ID = Pub.val(request, "ORDER_ID");//销售退件单ID
            String WAREHOUSE_ID = Pub.val(request, "WAREHOUSE_ID");//入库仓库ID
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchInBillPart(page, user, conditions, IN_ID, ORDER_ID, WAREHOUSE_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增入库单
     *
     * @throws Exception
     */
    public void insertInBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuStockInVO vo = new PtBuStockInVO();
            PtBuStockInVO_Ext voExt = new PtBuStockInVO_Ext();
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            //将hashmap映射到vo对象中,完成匹配赋值
            vo.setValue(hm);
            String inId = dao.getId();
            vo.setInId(inId);
            String inNo = dao.createInBillNo(hm.get("ORDER_NO"));
            vo.setInNo(inNo);
            vo.setCompanyId(user.getCompanyId());
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
            //通过dao，执行插入
            dao.insertInBill(vo);
            //返回插入结果和成功信息
            voExt.setValue(hm);
            voExt.setInId(inId);
            voExt.setInNo(inNo);
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
     * 修改入库单
     *
     * @throws Exception
     */
    public void updateInBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuStockInVO tempVO = new PtBuStockInVO();
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            tempVO.setInId(hm.get("IN_ID"));
            tempVO.setRemarks(hm.get("REMARKS"));
            tempVO.setUpdateUser(user.getAccount());
            tempVO.setUpdateTime(Pub.getCurrentDate());
            PtBuStockInVO_Ext extVO = new PtBuStockInVO_Ext();
            extVO.setValue(hm);
            dao.updateInBill(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(extVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配件入库
     *
     * @throws Exception
     */
    public void partStockIn() throws Exception {

        try {

            //获取封装后的request对象
            RequestWrapper request = atx.getRequest();
            //获取封装后的response对象
            //ResponseWrapper response = atx.getResponse();
            //获取当前登录user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            // 销售退件订单ID
            String splitId = hm.get("SPLIT_ID");
            // 销售退件订单NO
            String splitNo = hm.get("SPLIT_NO");
            // 渠道ID
            String orgId = hm.get("ORG_ID");
            // 渠道CODE
            String orgCode = hm.get("ORG_CODE");
            // 渠道NAME
            String orgName = hm.get("ORG_NAME");
            //仓库ID
            String warehouseId = hm.get("WAREHOUSE_ID");
            //仓库代码
            String warehouseCode = hm.get("WAREHOUSE_CODE");
            //仓库名称
            String warehouseName = hm.get("WAREHOUSE_NAME");
            //配件ID(逗号分隔)
            String partIds = hm.get("PARTIDS");
            //配件代码(逗号分隔)
            String partCodes = hm.get("PARTCODES");
            //配件名称(逗号分隔)
            String partNames = hm.get("PARTNAMES");
            //库位+本库位入库数量:库位id|库位code|库位name|数量 #...
            String curInAmounts = hm.get("CURINAMOUNTS");
            //备注(逗号分隔)
            String remarks = hm.get("REMARKS");
            //本单已入库数量(逗号分隔)
            String inAmounts = hm.get("INAMOUNTS");
            //采购拆分单明细ID(逗号分隔)
            String splitDtlIds = hm.get("SPLITDTLIDS");
            //供应商ID(逗号分隔)
            String supplierIds = hm.get("SUPPLIERIDS");
            //供应商代码(逗号分隔)
            String supplierCodes = hm.get("SUPPLIERCODES");
            //供应商名称(逗号分隔)
            String supplierNames = hm.get("SUPPLIERNAMES");
            String account = hm.get("account");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] curInAmountArr = curInAmounts.split(",");
            String[] remarkArr = remarks.split(",");
            String[] inAmountArr = inAmounts.split(",");
            String[] splitDtlArr = splitDtlIds.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] supplierCodeArr = supplierCodes.split(",");
            String[] supplierNameArr = supplierNames.split(",");
            
            
            QuerySet  checkPlanPrice = dao.checkPlanPrice(partIds);
            String codes = checkPlanPrice.getString(1, "CODES");
            if(!"".equals(codes)&&codes!=null){
            	throw new Exception("配件"+checkPlanPrice.getString(1, "CODES")+"尚未维护计划价格，请维护计划价格之后再入库");
            }
            /****校验库存锁定状态*******************************/
            QuerySet checkLock = dao.checkLock1(partIds,warehouseId);
            String locks = checkLock.getString(1, "CODES");
            if(!"".equals(locks)&&locks!=null){
            	throw new Exception("配件"+locks+"处于库存锁定状态，不能进行入库操作");
            }
            /****校验库存锁定状态*******************************/
            QuerySet checkLock1 = dao.checkLock2(partIds,warehouseId);
            String locks2 = checkLock1.getString(1, "CODES");
            if(!"".equals(locks2)&&locks2!=null){
            	throw new Exception("配件"+locks2+"处于盘点锁定状态，不能进行入库操作");
            }

            QuerySet qs = dao.selectStockInInfo(splitId);
            String inId = "";//入库单ID
            String inNo = "";//入库单号
            if(qs.getRowCount()>0){
                // ------ 入库单存在
                // 入库ID
                inId = qs.getString(1, "IN_ID");
                // 入库单号
                inNo = qs.getString(1, "IN_NO");
            } else {
                // ------ 入库单不存在
                PtBuStockInVO vo =new PtBuStockInVO();
                inId = dao.getId();
                inNo =  splitNo;
                vo.setInId(inId);
                vo.setInNo(inNo);
                vo.setWarehouseId(warehouseId);
                vo.setWarehouseCode(warehouseCode);
                vo.setWarehouseName(warehouseName);
                vo.setCompanyId(user.getCompanyId());
                vo.setInType(DicConstant.RKLX_03);
                // 销售退件订单ID
                vo.setOrderId(splitId);
                // 销售退件订单号
                vo.setOrderNo(splitNo);
                // 渠道ID
                vo.setOrgId(orgId);
                // 渠道CODE
                vo.setOrgCode(orgCode);
                // 渠道NAME
                vo.setOrgName(orgName);
                vo.setInStatus(DicConstant.RKDZT_02);
                vo.setOemCompanyId(user.getOemCompanyId());
                vo.setCreateUser(account);
                vo.setPrintStatus(DicConstant.DYZT_01);
                vo.setCreateTime(Pub.getCurrentDate());
                vo.setStatus(DicConstant.YXBS_01);
                dao.insertInBill(vo);
            }
            int totalInCount = 0;//本次入库数量之和
            for (int i = 0; i < partIdArr.length; i++) {
                if ("myNull".equals(remarkArr[i])) {
                    remarkArr[i] = "";
                }
                if (Integer.parseInt(inAmountArr[i]) > 0) {//已入库数量大于0 则修改入库单明细
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("IN_ID", inId);
                    map.put("PART_ID", partIdArr[i]);
                    String [] curInArr = curInAmountArr[i].split("#");
                    Integer curCount = 0;
                    for(int j=0;j<curInArr.length;j++){
                        String curInCount = curInArr[j].split("@")[3];
                        curCount= curCount+Integer.parseInt(curInCount);
                    }
                    map.put("SUPPLIER_ID", supplierIdArr[i]);
                    map.put("CUR_IN_AMOUNT", String.valueOf(curCount));
                    map.put("REMARK", remarkArr[i]);
                    dao.updateInBillDtl(map, user);
                } else if ((Integer.parseInt(inAmountArr[i]) == 0)) {//已入库数量为0 则新增入库单明细
                    String [] curInArr = curInAmountArr[i].split("#");
                    Integer curCount = 0;
                    for(int j=0;j<curInArr.length;j++){
                        String curInCount = curInArr[j].split("@")[3];
                        curCount= curCount+Integer.parseInt(curInCount);
                    }
                    PtBuStockInDtlVO dtlVO = new PtBuStockInDtlVO();
                    // 入库单ID
                    dtlVO.setInId(inId);
                    // 配件ID
                    dtlVO.setPartId(partIdArr[i]);
                    // 配件CODE
                    dtlVO.setPartCode(partCodeArr[i]);
                    // 配件NAME
                    dtlVO.setPartName(partNameArr[i]);
                    dtlVO.setInAmount(String.valueOf(curCount));
                    dtlVO.setInNo(splitNo);
                    dtlVO.setRemarks(remarkArr[i]);
                    dtlVO.setSupplierId(supplierIdArr[i]);
                    dtlVO.setSupplierCode(supplierCodeArr[i]);
                    dtlVO.setSupplierName(supplierNameArr[i]);
                    dao.insertInBillDtl(dtlVO);
                } else {
                    throw new Exception("已入库数量不正确!");
                }

                //校验库存是否存在
                String stockId = dao.checkStockIsExist(warehouseId, partIdArr[i], supplierIdArr[i], user);
                if (!"".equals(stockId)) {//存在
                    String [] curInArr = curInAmountArr[i].split("#");
                    Integer curCount = 0;
                    for(int j=0;j<curInArr.length;j++){
                        String curInCount = curInArr[j].split("@")[3];
                        String wpIdArr = curInArr[j].split("@")[0];
                        String wpCodeArr = curInArr[j].split("@")[1];
                        String wpNameArr = curInArr[j].split("@")[2];
                        curCount= curCount+Integer.parseInt(curInCount);
                        //校验库存明细(库位)是否存在
                        String stockDtlId = "";
                        stockDtlId = dao.checkStockDtlIsExist(wpIdArr, partIdArr[i], supplierIdArr[i], user);
                        if (!"".equals(stockDtlId)) {//存在
                            // 更新库存明细
                            dao.updateStockDtl(stockDtlId, curInCount, user);
                        } else {//不存在
                            // 新增库存明细
                            PtBuStockDtlVO stockDtlVO = new PtBuStockDtlVO();
                            QuerySet qSet = dao.getArea(wpIdArr);
		                    if (qSet.getRowCount()>0) {
		                    	stockDtlVO.setAreaId(qSet.getString(1, "AREA_ID"));
		                    	stockDtlVO.setAreaCode(qSet.getString(1, "AREA_CODE"));
		                    	stockDtlVO.setAreaName(qSet.getString(1, "AREA_NAME"));
		                    } else {
		                    	throw new Exception("请联系管理员，维护库位关系。");
		                    }
                            //库存明细
                            stockDtlVO.setStockId(stockId);
                            stockDtlVO.setPartId(partIdArr[i]);
                            stockDtlVO.setPartCode(partCodeArr[i]);
                            stockDtlVO.setPartName(partNameArr[i]);
                            stockDtlVO.setPositionId(wpIdArr);
                            stockDtlVO.setPositionCode(wpCodeArr);
                            stockDtlVO.setPositionName(wpNameArr);
                            stockDtlVO.setAmount(curInCount);
                            stockDtlVO.setAvailableAmount(curInCount);
                            stockDtlVO.setOccupyAmount("0");
                            stockDtlVO.setSupplierId(supplierIdArr[i]);
                            stockDtlVO.setSupplierCode(supplierCodeArr[i]);
                            stockDtlVO.setSupplierName(supplierNameArr[i]);
                            stockDtlVO.setCreateUser(account);
                            stockDtlVO.setCreateTime(Pub.getCurrentDate());
                            dao.insertStockDtl(stockDtlVO);
                        }
                        String url="销售退件入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
	                    // 插入库存变化明细。
	                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
                    }
                  //更新库存
                    dao.updateStock(stockId, String.valueOf(curCount), user);
                } else {//不存在 新增库存和库存明细
                    String [] curInArr = curInAmountArr[i].split("#");
                    Integer curCount = 0;
//                    QuerySet getStockId = dao.getStockId();
//                    stockId = getStockId.getString(1, "STOCK_ID");
                    stockId = dao.getId();
                    for(int j=0;j<curInArr.length;j++){
                        String curInCount = curInArr[j].split("@")[3];
                        String wpIdArr = curInArr[j].split("@")[0];
                        String wpCodeArr = curInArr[j].split("@")[1];
                        String wpNameArr = curInArr[j].split("@")[2];
                        curCount= curCount+Integer.parseInt(curInCount);
                        PtBuStockDtlVO stockDtlVO = new PtBuStockDtlVO();//库存明细
                        QuerySet qSet = dao.getArea(wpIdArr);
	                    if (qSet.getRowCount()>0) {
	                    	stockDtlVO.setAreaId(qSet.getString(1, "AREA_ID"));
	                    	stockDtlVO.setAreaCode(qSet.getString(1, "AREA_CODE"));
	                    	stockDtlVO.setAreaName(qSet.getString(1, "AREA_NAME"));
	                    } else {
	                    	throw new Exception("请联系管理员，维护库位关系。");
	                    }
                        stockDtlVO.setStockId(stockId);
                        stockDtlVO.setPartId(partIdArr[i]);
                        stockDtlVO.setPartCode(partCodeArr[i]);
                        stockDtlVO.setPartName(partNameArr[i]);
                        stockDtlVO.setPositionId(wpIdArr);
                        stockDtlVO.setPositionCode(wpCodeArr);
                        stockDtlVO.setPositionName(wpNameArr);
                        stockDtlVO.setAmount(curInCount);
                        stockDtlVO.setAvailableAmount(curInCount);
                        stockDtlVO.setOccupyAmount("0");
                        stockDtlVO.setSupplierId(supplierIdArr[i]);
                        stockDtlVO.setSupplierCode(supplierCodeArr[i]);
                        stockDtlVO.setSupplierName(supplierNameArr[i]);
                        stockDtlVO.setCreateUser(account);
                        stockDtlVO.setCreateTime(Pub.getCurrentDate());
                        dao.insertStockDtl(stockDtlVO);
                        String url="销售退件入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
	                    // 插入库存变化明细。
	                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
                    }
                    PtBuStockVO stockVO = new PtBuStockVO();//库存
                    stockVO.setStockId(stockId);
                    stockVO.setPartId(partIdArr[i]);
                    stockVO.setPartCode(partCodeArr[i]);
                    stockVO.setPartName(partNameArr[i]);
                    stockVO.setPartNo(partNameArr[i]);
                    stockVO.setWarehouseId(warehouseId);
                    stockVO.setWarehouseCode(warehouseCode);
                    stockVO.setWarehouseName(warehouseName);
                    stockVO.setAmount(String.valueOf(curCount));
                    stockVO.setOccupyAmount("0");
                    stockVO.setAvailableAmount(String.valueOf(curCount));
                    stockVO.setStockStatus(DicConstant.KCZT_01);
                    stockVO.setSupplierId(supplierIdArr[i]);
                    stockVO.setSupplierCode(supplierCodeArr[i]);
                    stockVO.setSupplierName(supplierNameArr[i]);
                    stockVO.setCreateUser(account);
                    stockVO.setCreateTime(Pub.getCurrentDate());
                    stockVO.setStatus(DicConstant.YXBS_01);
                    stockVO.setOrgId(user.getOrgId());
                    stockVO.setCompanyId(user.getCompanyId());
                    stockVO.setOemCompanyId(user.getOemCompanyId());
                    dao.insertStock(stockVO);

                }

              //更新采购拆分单明细的已入库数量
                Map<String, String> map = new HashMap<String, String>();
                map.put("DETAIL_ID", splitDtlArr[i]);
                String [] curInArr = curInAmountArr[i].split("#");
                Integer curCount = 0;
                for(int j=0;j<curInArr.length;j++){
                    String curInCount = curInArr[j].split("@")[3];
                    curCount= curCount+Integer.parseInt(curInCount);
                }
                map.put("CUR_IN_AMOUNT", String.valueOf(curCount));
                dao.updateSplitDtl(map, user);
                //本次入库数量之和
                totalInCount += Integer.parseInt(String.valueOf(curCount));
                //新增入库流水记录
                PtBuStockInContinualVO continualVO = new PtBuStockInContinualVO();
                continualVO.setContinualNo("自动生成");
                continualVO.setInId(inId);
                continualVO.setInNo(inNo);
                continualVO.setPartId(partIdArr[i]);
                continualVO.setPartCode(partCodeArr[i]);
                continualVO.setPartName(partNameArr[i]);
                continualVO.setInCount(String.valueOf(curCount));
                continualVO.setInCountTmp(String.valueOf((curCount-Integer.valueOf(inAmountArr[i]))));
                continualVO.setKeeperMan(account);
                continualVO.setInDate(Pub.getCurrentDate());
                dao.insertContinual(continualVO);
            }
//            //修改采购拆分单的已入库数量
//            dao.updateSplit(splitId, totalInCount, user);
            //更新入库单的库管员和入库日期
            PtBuStockInVO stockInVO = new PtBuStockInVO();
            // 入库完成验证
            QuerySet querySet = dao.inStockCheck(inId,splitId);
            if (querySet.getRowCount() > 0 ) {
                // 更改入库单状态
                stockInVO.setInStatus(DicConstant.RKDZT_02);
                // 关闭退件申请订单
                PtBuReturnApplyVO ptBuReturnApplyVO = new PtBuReturnApplyVO();
                // 销售退件订单ID
                ptBuReturnApplyVO.setReturnId(splitId);
                // 销售退件订单状态(已关闭)
                ptBuReturnApplyVO.setApplySatus(DicConstant.TJSQDZT_05);
                // 修改人
                ptBuReturnApplyVO.setUpdateUser(account);
                // 修改时间
                ptBuReturnApplyVO.setUpdateTime(Pub.getCurrentDate());
                ptBuReturnApplyVO.setInvoiceStatus(DicConstant.KPZT_01);
                ptBuReturnApplyVO.setCloseDate(Pub.getCurrentDate());
                // 修改销售退件申请单
                dao.updateReturnApply(ptBuReturnApplyVO);
                //更改退件申请单计划价格，入库数量
                dao.updateRetOrder(inId,splitId);
            }
            stockInVO.setInId(inId);
            stockInVO.setKeeperMan(account);
            stockInVO.setInDate(Pub.getCurrentDate());
            stockInVO.setUpdateUser(account);
            stockInVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateInBill(stockInVO);
            
            //更新入库单明细的采购单价/采购金额/计划单价/计划金额/单位
            dao.updateInBillDtl(inId);
            //更新入库流水的计划价
            dao.updateInFlow(inId);
            
            
            //返回插入结果和成功信息
            atx.setOutMsg("", "入库成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除入库单
     *
     * @throws Exception
     */
    public void deleteInBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inId = Pub.val(request, "inId");
        try {
            if (dao.checkInBillDtlIsExist(inId)) {
                throw new Exception("该入库单已进行过入库操作,无法删除!");
            }
            PtBuStockInVO delVo = new PtBuStockInVO();
            delVo.setInId(inId);
            //删除入库单
            dao.deleteInBill(delVo);
            //删除入库单明细
            dao.deleteInBillDtl(inId);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 入库完成
     *
     * @throws Exception
     */
    public void completeInBill() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String inId = Pub.val(request, "inId");
        try {
            if (!dao.checkInBillDtlIsExist(inId)) {
                throw new Exception("该入库单未进行过入库操作,无法入库完成!");
            }
            PtBuStockInVO stockInVO = new PtBuStockInVO();
            stockInVO.setInId(inId);
            stockInVO.setInStatus(DicConstant.RKDZT_02);
            stockInVO.setUpdateTime(Pub.getCurrentDate());
            stockInVO.setUpdateUser(user.getAccount());
            dao.updateInBill(stockInVO);
            
            // 入库单明细配件查询
            QuerySet querySet = dao.searchReturnPart(inId,user);
            int count = querySet.getRowCount();
            if (count > 0) {
                for (int i=0;i<count;i++) {
                    // 配件ID
                    String partId = querySet.getString(i+1, "PART_ID");
                    // 供应商ID
                    String supplierId = querySet.getString(i+1, "SUPPLIER_ID");
                    // 入库数量
                    String returnCount = querySet.getString(i+1, "IN_AMOUNT");
                    // 减库存
                    dao.updateStock(partId,supplierId,returnCount,inId);
                }
            }
            //将退货金额转入渠道账户
            QuerySet getmoney = dao.getMoney(inId);
            String moneyString =getmoney.getString(1, "AMOUNT");
            QuerySet getAccount = dao.getAccount(inId);
            String ACCOUNT_ID = getAccount.getString(1, "ACCOUNT_ID");
            dao.updateAccount(ACCOUNT_ID,moneyString);
            PtBuAccountLogVO vo = new PtBuAccountLogVO();
            vo.setAccountId(ACCOUNT_ID);
            vo.setAmount(moneyString);
            vo.setLogType(DicConstant.ZJYDLX_01);
            vo.setRemarks("退库单销售退件款");
            vo.setSourceaccountType(DicConstant.ZJZHLX_03);
            vo.setInDate(Pub.getCurrentDate());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());//
            vo.setOrgId(user.getOrgId());
            //vo.setInDate(date);
            dao.accountLogInsert(vo);
            atx.setOutMsg("", "入库完成！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}