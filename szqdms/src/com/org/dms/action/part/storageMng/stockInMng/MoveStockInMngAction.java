package com.org.dms.action.part.storageMng.stockInMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.storageMng.stockInMng.MoveStockInMngDao;
import com.org.dms.vo.part.PtBuStockDtlVO;
import com.org.dms.vo.part.PtBuStockInContinualVO;
import com.org.dms.vo.part.PtBuStockInDtlVO;
import com.org.dms.vo.part.PtBuStockInVO;
import com.org.dms.vo.part.PtBuStockInVO_Ext;
import com.org.dms.vo.part.PtBuStockVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 移库入库Action
 *
 * @user : lichuang
 * @date : 2014-07-15
 */
public class MoveStockInMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "MoveStockInMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private MoveStockInMngDao dao = MoveStockInMngDao.getInstance(atx);


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
     * 查询移库出库单
     *
     * @throws Exception
     */
    public void searchMove() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String warehouseId = Pub.val(request, "warehouseId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchMove(page, user, conditions, warehouseId);
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
        page.setPageRows(10000);
        try {
            String outId = Pub.val(request, "outId");//出库单ID
            String warehouseId = Pub.val(request, "warehouseId");//仓库ID
            String warehouseType = Pub.val(request, "warehouseType");//仓库类型
            String account = Pub.val(request, "account");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchInBillPart(page, user, conditions, outId, warehouseType,warehouseId,account);
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
            String inNo = hm.get("ORDER_NO");
            vo.setInId(inId);
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
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	String url="移库入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String warehouseType = hm.get("WAREHOUSE_TYPE");
            String warehouseId = hm.get("WAREHOUSE_ID");
            String warehouseCode = hm.get("WAREHOUSE_CODE");
            String warehouseName = hm.get("WAREHOUSE_NAME");
            String outId = hm.get("OUT_ID");//入库单ID
            String outNo = hm.get("OUT_NO");//入库单号
            String partIds = hm.get("PART_IDS");//配件ID(逗号分隔)
            String partCodes = hm.get("PART_CODES");//配件代码(逗号分隔)
            String partNames = hm.get("PART_NAMES");//配件名称(逗号分隔)
            String curInAmounts = hm.get("CURINAMOUNTS");//库位+本库位入库数量:库位id|库位code|库位name|数量 #...
            String remarks = hm.get("REMARKS");//备注(逗号分隔)
            String supplierIds = hm.get("SUPPLIER_IDS");//供应商ID(逗号分隔)
            String supplierCodes = hm.get("SUPPLIER_CODES");//供应商代码(逗号分隔)
            String supplierNames = hm.get("SUPPLIER_NAMES");//供应商名称(逗号分隔)
            String account = hm.get("USERACC");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] curInAmountArr = curInAmounts.split(",");
            String[] remarkArr = remarks.split(",");
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
            String inId = "";
            if("100101".equals(warehouseType)){
            	dao.deleteInBillKeeper(account,outId);
            	QuerySet keeper = dao.selectInBillKeeper(account,outId);
            	QuerySet qs = dao.checkInBillIsExist(outId);
            	if(qs.getRowCount()>0){
            		inId = qs.getString(1, "IN_ID");
            		if(keeper.getRowCount()==0){
            			PtBuStockInVO vo = new PtBuStockInVO();
            			vo.setInId(inId);
            			vo.setInStatus(DicConstant.RKDZT_02);
            			vo.setUpdateUser(account);
            			vo.setInDate(Pub.getCurrentDate());
            			vo.setUpdateTime(Pub.getCurrentDate());
            			dao.updateInBill(vo);
            		}
            		for (int i = 0; i < partIdArr.length; i++) {
                    	if ("myNull".equals(remarkArr[i])) {
                            remarkArr[i] = "";
                        }
                    	String [] curInArr = curInAmountArr[i].split("#");
     	                Integer curCount = 0;
     	                for(int j=0;j<curInArr.length;j++){
    	                	String curInCount = curInArr[j].split("@")[3];
    	                	curCount= curCount+Integer.parseInt(curInCount);
    	                }
    	                PtBuStockInDtlVO dtlVO = new PtBuStockInDtlVO();
    	                dtlVO.setInId(inId);
    	                dtlVO.setInNo(outNo);
    	                dtlVO.setPartId(partIdArr[i]);
    	                dtlVO.setPartCode(partCodeArr[i]);
    	                dtlVO.setPartName(partNameArr[i]);
    	                dtlVO.setInAmount(String.valueOf(curCount));
    	                dtlVO.setRemarks(remarkArr[i]);
    	                dtlVO.setSupplierId(supplierIdArr[i]);
    	                dtlVO.setSupplierCode(supplierCodeArr[i]);
    	                dtlVO.setSupplierName(supplierNameArr[i]);
    	                dao.insertInBillDtl(dtlVO);
    	                //新增入库流水记录
                        PtBuStockInContinualVO continualVO = new PtBuStockInContinualVO();
                        continualVO.setContinualNo(PartOddNumberUtil.getInContinualNo(atx.getDBFactory()));
                        continualVO.setInId(inId);
                        continualVO.setInNo(outNo);
                        continualVO.setPartId(partIdArr[i]);
                        continualVO.setPartCode(partCodeArr[i]);
                        continualVO.setPartName(partNameArr[i]);
                        continualVO.setInCount(String.valueOf(curCount));
                        continualVO.setInCountTmp(String.valueOf(curCount));
                        continualVO.setKeeperMan(account);
                        continualVO.setInDate(Pub.getCurrentDate());
                        dao.insertContinual(continualVO);
    	                //校验库存是否存在
    	                String stockId = dao.checkStockIsExist(warehouseId, partIdArr[i], supplierIdArr[i], user);
    	                if (!"".equals(stockId)) {//存在
    	                    for(int j=0;j<curInArr.length;j++){
    	                    	String curInCount = curInArr[j].split("@")[3];
    	                    	String wpIdArr = curInArr[j].split("@")[0];
    	                    	String wpCodeArr = curInArr[j].split("@")[1];
    	                    	String wpNameArr = curInArr[j].split("@")[2];
    	                    	//校验库存明细(库位)是否存在
    	                        String stockDtlId = "";
    	                        stockDtlId = dao.checkStockDtlIsExist(wpIdArr, partIdArr[i], supplierIdArr[i], user);
    	                        if (!"".equals(stockDtlId)) {//存在
    	                            // 更新库存明细
    	                            dao.updateStockDtl(stockDtlId, curInCount, user);
    	                        } else {//不存在
    	                            // 新增库存明细
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
    	                        }
    	                     // 插入库存变化明细。
    		                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
    	                    }
    	                    //更新库存
    	                    dao.updateStock(stockId, String.valueOf(curCount), user);
    	                } else {//不存在 新增库存和库存明细
    	                    for(int j=0;j<curInArr.length;j++){
    	                    	String curInCount = curInArr[j].split("@")[3];
    	                    	String wpIdArr = curInArr[j].split("@")[0];
    	                    	String wpCodeArr = curInArr[j].split("@")[1];
    	                    	String wpNameArr = curInArr[j].split("@")[2];
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
    		                    // 插入库存变化明细。
    		                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
    	                    }
    	                    PtBuStockVO stockVO = new PtBuStockVO();//库存
    	                    stockId = dao.getId();
    	                    stockVO.setStockId(stockId);
    	                    stockVO.setPartId(partIdArr[i]);
    	                    stockVO.setPartCode(partCodeArr[i]);
    	                    stockVO.setPartName(partNameArr[i]);
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
                	}
            	}else{
            		PtBuStockInVO vo = new PtBuStockInVO();
                	inId= SequenceUtil.getCommonSerivalNumber(atx.getDBFactory());
                	vo.setInId(inId);
                	vo.setInNo(outNo);
                	vo.setWarehouseId(warehouseId);
                	vo.setWarehouseCode(warehouseCode);
                	vo.setWarehouseName(warehouseName);
                	vo.setInType(DicConstant.RKLX_02);
                	vo.setOrderId(outId);
                	vo.setOrderNo(outNo);
                	vo.setCompanyId(user.getCompanyId());
                	vo.setCreateUser(account);
                	vo.setCreateTime(Pub.getCurrentDate());
                	//vo.setInDate(Pub.getCurrentDate());
                	vo.setStatus(DicConstant.YXBS_01);
                	if(keeper.getRowCount()>0){
                		vo.setInStatus(DicConstant.RKDZT_01);
                	}else{
                		vo.setInStatus(DicConstant.RKDZT_02);
                		vo.setInDate(Pub.getCurrentDate());
                	}
                	vo.setPrintStatus(DicConstant.DYZT_01);
                	vo.setKeeperMan(account);
                	dao.insertInBill(vo);
                	for (int i = 0; i < partIdArr.length; i++) {
                    	if ("myNull".equals(remarkArr[i])) {
                            remarkArr[i] = "";
                        }
                    	String [] curInArr = curInAmountArr[i].split("#");
     	                Integer curCount = 0;
     	                for(int j=0;j<curInArr.length;j++){
    	                	String curInCount = curInArr[j].split("@")[3];
    	                	curCount= curCount+Integer.parseInt(curInCount);
    	                }
    	                PtBuStockInDtlVO dtlVO = new PtBuStockInDtlVO();
    	                dtlVO.setInId(inId);
    	                dtlVO.setInNo(outNo);
    	                dtlVO.setPartId(partIdArr[i]);
    	                dtlVO.setPartCode(partCodeArr[i]);
    	                dtlVO.setPartName(partNameArr[i]);
    	                dtlVO.setInAmount(String.valueOf(curCount));
    	                dtlVO.setRemarks(remarkArr[i]);
    	                dtlVO.setSupplierId(supplierIdArr[i]);
    	                dtlVO.setSupplierCode(supplierCodeArr[i]);
    	                dtlVO.setSupplierName(supplierNameArr[i]);
    	                dao.insertInBillDtl(dtlVO);
    	                //新增入库流水记录
                        PtBuStockInContinualVO continualVO = new PtBuStockInContinualVO();
                        continualVO.setContinualNo(PartOddNumberUtil.getInContinualNo(atx.getDBFactory()));
                        continualVO.setInId(inId);
                        continualVO.setInNo(outNo);
                        continualVO.setPartId(partIdArr[i]);
                        continualVO.setPartCode(partCodeArr[i]);
                        continualVO.setPartName(partNameArr[i]);
                        continualVO.setInCount(String.valueOf(curCount));
                        continualVO.setInCountTmp(String.valueOf(curCount));
                        continualVO.setKeeperMan(account);
                        continualVO.setInDate(Pub.getCurrentDate());
                        dao.insertContinual(continualVO);
    	                //校验库存是否存在
    	                String stockId = dao.checkStockIsExist(warehouseId, partIdArr[i], supplierIdArr[i], user);
    	                if (!"".equals(stockId)) {//存在
    	                    for(int j=0;j<curInArr.length;j++){
    	                    	String curInCount = curInArr[j].split("@")[3];
    	                    	String wpIdArr = curInArr[j].split("@")[0];
    	                    	String wpCodeArr = curInArr[j].split("@")[1];
    	                    	String wpNameArr = curInArr[j].split("@")[2];
    	                    	//校验库存明细(库位)是否存在
    	                        String stockDtlId = "";
    	                        stockDtlId = dao.checkStockDtlIsExist(wpIdArr, partIdArr[i], supplierIdArr[i], user);
    	                        if (!"".equals(stockDtlId)) {//存在
    	                            // 更新库存明细
    	                            dao.updateStockDtl(stockDtlId, curInCount, user);
    	                        } else {//不存在
    	                            // 新增库存明细
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
    	                            // 插入库存变化明细。
    	    	                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
    	                        }
    	                    }
    	                    //更新库存
    	                    dao.updateStock(stockId, String.valueOf(curCount), user);
    	                } else {//不存在 新增库存和库存明细
    	                    for(int j=0;j<curInArr.length;j++){
    	                    	String curInCount = curInArr[j].split("@")[3];
    	                    	String wpIdArr = curInArr[j].split("@")[0];
    	                    	String wpCodeArr = curInArr[j].split("@")[1];
    	                    	String wpNameArr = curInArr[j].split("@")[2];
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
    		                    // 插入库存变化明细。
	    	                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
    	                    }
    	                    PtBuStockVO stockVO = new PtBuStockVO();//库存
    	                    stockId = dao.getId();
    	                    stockVO.setStockId(stockId);
    	                    stockVO.setPartId(partIdArr[i]);
    	                    stockVO.setPartCode(partCodeArr[i]);
    	                    stockVO.setPartName(partNameArr[i]);
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
                	}
            	}
            }else{
            	PtBuStockInVO vo = new PtBuStockInVO();
            	inId= SequenceUtil.getCommonSerivalNumber(atx.getDBFactory());
            	vo.setInId(inId);
            	vo.setInNo(outNo);
            	vo.setWarehouseId(warehouseId);
            	vo.setWarehouseCode(warehouseCode);
            	vo.setWarehouseName(warehouseName);
            	vo.setInType(DicConstant.RKLX_02);
            	vo.setOrderId(outId);
            	vo.setOrderNo(outNo);
            	vo.setCompanyId(user.getCompanyId());
            	vo.setCreateUser(account);
            	vo.setCreateTime(Pub.getCurrentDate());
            	vo.setStatus(DicConstant.YXBS_01);
            	vo.setInStatus(DicConstant.RKDZT_02);
            	vo.setInDate(Pub.getCurrentDate());
            	vo.setPrintStatus(DicConstant.DYZT_01);
            	vo.setKeeperMan(account);
            	dao.insertInBill(vo);
            	for (int i = 0; i < partIdArr.length; i++) {
                	if ("myNull".equals(remarkArr[i])) {
                        remarkArr[i] = "";
                    }
                	String [] curInArr = curInAmountArr[i].split("#");
 	                Integer curCount = 0;
 	                for(int j=0;j<curInArr.length;j++){
	                	String curInCount = curInArr[j].split("@")[3];
	                	curCount= curCount+Integer.parseInt(curInCount);
	                }
	                PtBuStockInDtlVO dtlVO = new PtBuStockInDtlVO();
	                dtlVO.setInId(inId);
	                dtlVO.setInNo(outNo);
	                dtlVO.setPartId(partIdArr[i]);
	                dtlVO.setPartCode(partCodeArr[i]);
	                dtlVO.setPartName(partNameArr[i]);
	                dtlVO.setInAmount(String.valueOf(curCount));
	                dtlVO.setRemarks(remarkArr[i]);
	                dtlVO.setSupplierId(supplierIdArr[i]);
	                dtlVO.setSupplierCode(supplierCodeArr[i]);
	                dtlVO.setSupplierName(supplierNameArr[i]);
	                dao.insertInBillDtl(dtlVO);
	                //新增入库流水记录
                    PtBuStockInContinualVO continualVO = new PtBuStockInContinualVO();
                    continualVO.setContinualNo(PartOddNumberUtil.getInContinualNo(atx.getDBFactory()));
                    continualVO.setInId(inId);
                    continualVO.setInNo(outNo);
                    continualVO.setPartId(partIdArr[i]);
                    continualVO.setPartCode(partCodeArr[i]);
                    continualVO.setPartName(partNameArr[i]);
                    continualVO.setInCount(String.valueOf(curCount));
                    continualVO.setInCountTmp(String.valueOf(curCount));
                    continualVO.setKeeperMan(account);
                    continualVO.setInDate(Pub.getCurrentDate());
                    dao.insertContinual(continualVO);
	                //校验库存是否存在
	                String stockId = dao.checkStockIsExist(warehouseId, partIdArr[i], supplierIdArr[i], user);
	                if (!"".equals(stockId)) {//存在
	                    for(int j=0;j<curInArr.length;j++){
	                    	String curInCount = curInArr[j].split("@")[3];
	                    	String wpIdArr = curInArr[j].split("@")[0];
	                    	String wpCodeArr = curInArr[j].split("@")[1];
	                    	String wpNameArr = curInArr[j].split("@")[2];
	                    	//校验库存明细(库位)是否存在
	                        String stockDtlId = "";
	                        stockDtlId = dao.checkStockDtlIsExist(wpIdArr, partIdArr[i], supplierIdArr[i], user);
	                        if (!"".equals(stockDtlId)) {//存在
	                            // 更新库存明细
	                            dao.updateStockDtl(stockDtlId, curInCount, user);
	                        } else {//不存在
	                            // 新增库存明细
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
	        	                // 插入库存变化明细。
	                            dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
	                        }
	                    }
	                    //更新库存
	                    dao.updateStock(stockId, String.valueOf(curCount), user);
	                } else {//不存在 新增库存和库存明细
	                	PtBuStockVO stockVO = new PtBuStockVO();//库存
	                    stockId = dao.getId();
	                    stockVO.setStockId(stockId);
	                    stockVO.setPartId(partIdArr[i]);
	                    stockVO.setPartCode(partCodeArr[i]);
	                    stockVO.setPartName(partNameArr[i]);
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
	                    for(int j=0;j<curInArr.length;j++){
	                    	String curInCount = curInArr[j].split("@")[3];
	                    	String wpIdArr = curInArr[j].split("@")[0];
	                    	String wpCodeArr = curInArr[j].split("@")[1];
	                    	String wpNameArr = curInArr[j].split("@")[2];
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
		                    stockDtlVO.setCreateUser(user.getAccount());
		                    stockDtlVO.setCreateTime(Pub.getCurrentDate());
		                    dao.insertStockDtl(stockDtlVO);
		                    // 插入库存变化明细。
                            dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
	                    }
	                }
            	}
            }
            
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

//    /**
//     * 删除入库单
//     *
//     * @throws Exception
//     */
//    public void deleteInBill() throws Exception {
//        //获取封装后的request对象
//        RequestWrapper request = atx.getRequest();
//        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
//        String inId = Pub.val(request, "inId");
//        try {
//            if (dao.checkInBillDtlIsExist(inId)) {
//                throw new Exception("该入库单已进行过入库操作,无法删除!");
//            }
//            PtBuStockInVO delVo = new PtBuStockInVO();
//            delVo.setInId(inId);
//            //删除入库单
//            dao.deleteInBill(delVo);
//            //删除入库单明细
//            dao.deleteInBillDtl(inId);
//            atx.setOutMsg("", "删除成功！");
//        } catch (Exception e) {
//            atx.setException(e);
//            logger.error(e);
//        }
//    }
//
//    /**
//     * 入库完成
//     *
//     * @throws Exception
//     */
//    public void completeInBill() throws Exception {
//        //获取封装后的request对象
//        RequestWrapper request = atx.getRequest();
//        //获取当前登录user对象
//        User user = (User) atx.getSession().get(Globals.USER_KEY);
//        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
//        String inId = Pub.val(request, "inId");
//        try {
//            if (!dao.checkInBillDtlIsExist(inId)) {
//                throw new Exception("该入库单未进行过入库操作,无法入库完成!");
//            }
//            PtBuStockInVO stockInVO = new PtBuStockInVO();
//            stockInVO.setInId(inId);
//            stockInVO.setInStatus(DicConstant.RKDZT_02);
//            stockInVO.setUpdateTime(Pub.getCurrentDate());
//            stockInVO.setUpdateUser(user.getAccount());
//            dao.updateInBill(stockInVO);
//            atx.setOutMsg("", "入库完成！");
//        } catch (Exception e) {
//            atx.setException(e);
//            logger.error(e);
//        }
//    }
}