package com.org.dms.action.part.storageMng.stockInMng;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockInMng.PchStockInMngDao;
import com.org.dms.vo.part.PtBuStockDtlVO;
import com.org.dms.vo.part.PtBuStockInContinualVO;
import com.org.dms.vo.part.PtBuStockInDtlVO;
import com.org.dms.vo.part.PtBuStockInVO;
import com.org.dms.vo.part.PtBuStockInVO_Ext;
import com.org.dms.vo.part.PtBuStockVO;
import com.org.framework.Globals;
import com.org.framework.alertmsg.AlertInfoVO;
import com.org.framework.alertmsg.AlertManager;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 采购验收入库Action
 *
 * @user : lichuang
 * @date : 2014-07-15
 */
public class PchStockInMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PchStockInMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PchStockInMngDao dao = PchStockInMngDao.getInstance(atx);


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
        HashMap<String, String> hm = RequestUtil.getValues(request);
        String distributionNo = hm.get("DISTRIBUTION_NO");
		String partCode = hm.get("PART_CODE");
		String partName = hm.get("PART_NAME");
		String ACCOUNT = hm.get("USER_ACCOUNT");
        try {
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchInBill(page, user, conditions,partCode,partName,distributionNo,ACCOUNT);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询采购拆分单
     *
     * @throws Exception
     */
    public void searchPch() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String warehouseType = Pub.val(request, "warehouseType");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPch(page, user, conditions, warehouseType);
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
            //String IN_ID = Pub.val(request, "IN_ID");//入库单ID
            String ORDER_ID = Pub.val(request, "ORDER_ID");//采购拆分单ID
            String WAREHOUSE_ID = Pub.val(request, "WAREHOUSE_ID");//入库仓库ID
            String account = Pub.val(request, "account");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchInBillPart(page, user, conditions, ORDER_ID, WAREHOUSE_ID,account);
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
            String inNo = dao.createInBillNo(hm.get("ORDER_ID"), hm.get("ORDER_NO"));
            vo.setInId(inId);
            vo.setInNo(inNo);
            vo.setCompanyId(user.getCompanyId());
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
            dao.updateInBill(tempVO);
            PtBuStockInVO_Ext extVO = new PtBuStockInVO_Ext();
            extVO.setValue(hm);
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
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String splitId = hm.get("SPLIT_ID");//采购拆分单ID
            String splitNo = hm.get("SPLIT_NO");//采购拆分单ID
            String warehouseId = hm.get("WAREHOUSE_ID");//仓库ID
            String warehouseCode = hm.get("WAREHOUSE_CODE");//仓库代码
            String warehouseName = hm.get("WAREHOUSE_NAME");//仓库名称
            String supplierId = hm.get("SUPPLIER_ID");
            String supplierCode = hm.get("SUPPLIER_CODE");
            String supplierName = hm.get("SUPPLIER_NAME");
            String buyer = hm.get("BUYER");
            String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
            String partCodes = hm.get("PARTCODES");//配件代码(逗号分隔)
            String partNames = hm.get("PARTNAMES");//配件名称(逗号分隔)
            String curInAmounts = hm.get("CURINAMOUNTS");//库位+本库位入库数量:库位id|库位code|库位name|数量 #...
            String remarks = hm.get("REMARKS");//备注(逗号分隔)
            String inAmounts = hm.get("INAMOUNTS");//本单已入库数量(逗号分隔)
            String splitDtlIds = hm.get("SPLITDTLIDS");//采购拆分单明细ID(逗号分隔)
            String supplierIds = hm.get("SUPPLIERIDS");//供应商ID(逗号分隔)
            String supplierCodes = hm.get("SUPPLIERCODES");//供应商代码(逗号分隔)
            String supplierNames = hm.get("SUPPLIERNAMES");//供应商名称(逗号分隔)
            String account = hm.get("userAcc");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] curInAmountArr = curInAmounts.split(",");
            String[] remarkArr = remarks.split(",");
            String[] splitDtlArr = splitDtlIds.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] supplierCodeArr = supplierCodes.split(",");
            String[] supplierNameArr = supplierNames.split(",");
            String[] inAmountArr = inAmounts.split(",");
            QuerySet qs = dao.selectStockInInfo(splitId);
            /****校验计划价***********************************/
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
            //已有入库单
            if(qs.getRowCount()>0){
            	String inId = qs.getString(1, "IN_ID");
            	String inNo = qs.getString(1, "IN_NO");
            	int totalInCount = 0;//本次入库数量之和
                for (int i = 0; i < partIdArr.length; i++) {
                	if ("myNull".equals(remarkArr[i])) {
                        remarkArr[i] = "";
                    }
	                QuerySet dqs = dao.selectStockInDetailInfo(inId, partIdArr[i], supplierIdArr[i]);
	                if(dqs.getRowCount()>0){
	                	String [] curInArr = curInAmountArr[i].split("#");
		                Integer curCount = 0;
	                	Map<String, String> map = new HashMap<String, String>();
	                    map.put("IN_ID", inId);
	                    map.put("PART_ID", partIdArr[i]);
	                    map.put("SUPPLIER_ID", supplierIdArr[i]);
	                    for(int j=0;j<curInArr.length;j++){
	                    	String curInCount = curInArr[j].split("@")[3];
	                    	curCount= curCount+Integer.parseInt(curInCount);
	                    }
	                    map.put("CUR_IN_AMOUNT", String.valueOf(curCount));
	                    map.put("REMARK", remarkArr[i]);
	                    dao.updateInBillDtl(map, user);
	                }else{
	                	String [] curInArr = curInAmountArr[i].split("#");
		                Integer curCount = 0;
	                	for(int j=0;j<curInArr.length;j++){
	                    	String curInCount = curInArr[j].split("@")[3];
	                    	curCount= curCount+Integer.parseInt(curInCount);
	                    }
	                    PtBuStockInDtlVO dtlVO = new PtBuStockInDtlVO();
	                    dtlVO.setInId(inId);
	                    dtlVO.setPartId(partIdArr[i]);
	                    dtlVO.setPartCode(partCodeArr[i]);
	                    dtlVO.setPartName(partNameArr[i]);
	                    dtlVO.setInAmount(String.valueOf(curCount));
	                    dtlVO.setRemarks(remarkArr[i]);
	                    dtlVO.setSupplierId(supplierIdArr[i]);
	                    dtlVO.setSupplierCode(supplierCodeArr[i]);
	                    dtlVO.setSupplierName(supplierNameArr[i]);
	                    dao.insertInBillDtl(dtlVO);
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
	                        String url="采购入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
	                        // 插入库存变化明细。
	                        dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
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
	                    stockVO.setAmount("0");
	                    stockVO.setOccupyAmount("0");
	                    stockVO.setAvailableAmount("0");
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
	                	String [] curInArr = curInAmountArr[i].split("#");
		                Integer curCount = 0;
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
		                    String url="采购入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
		                    // 插入库存变化明细。
		                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
	                    }
	                    //更新库存数量、可用数量
	                    dao.updateStock(stockId, String.valueOf(curCount), user);
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
	                QuerySet getInConNo = dao.getInConNo();
	                continualVO.setContinualNo(getInConNo.getString(1, "NO"));
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
                //修改采购拆分单的已入库数量
                dao.updateSplit(splitId, totalInCount, user);
                //更新入库单的库管员和入库日期
                PtBuStockInVO stockInVO = new PtBuStockInVO();
                stockInVO.setInId(inId);
                stockInVO.setKeeperMan(user.getAccount());
                stockInVO.setInDate(Pub.getCurrentDate());
                stockInVO.setUpdateUser(user.getAccount());
                stockInVO.setUpdateTime(Pub.getCurrentDate());
                dao.updateInBill(stockInVO);
                //更新入库单明细的采购单价/采购金额/计划单价/计划金额/单位
                dao.updateInBillDtl(inId,DicConstant.RKLX_01);
                //更新入库流水的计划价
                dao.updateInFlow(inId);
            }else{//新增入库单
            	PtBuStockInVO vo =new PtBuStockInVO();
            	String inId = dao.getId();
                String inNo = dao.createInBillNo(splitId, splitNo);
                vo.setInId(inId);
                vo.setInNo(inNo);
                vo.setWarehouseId(warehouseId);
                vo.setWarehouseCode(warehouseCode);
                vo.setWarehouseName(warehouseName);
                vo.setCompanyId(user.getCompanyId());
                vo.setInType(DicConstant.RKLX_01);
                vo.setOrderId(splitId);
                vo.setOrderNo(splitNo);
                vo.setSupplierId(supplierId);
                vo.setSupplierCode(supplierCode);
                vo.setSupplierName(supplierName);
                vo.setBuyer(buyer);
                vo.setInStatus(DicConstant.RKDZT_02);
                vo.setOemCompanyId(user.getOemCompanyId());
                vo.setCreateUser(account);
                vo.setPrintStatus(DicConstant.DYZT_01);
                vo.setCreateTime(Pub.getCurrentDate());
                vo.setStatus(DicConstant.YXBS_01);
                dao.insertInBill(vo);
                int totalInCount = 0;//本次入库数量之和
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
	                dtlVO.setPartId(partIdArr[i]);
	                dtlVO.setPartCode(partCodeArr[i]);
	                dtlVO.setPartName(partNameArr[i]);
	                dtlVO.setInAmount(String.valueOf(curCount));
	                dtlVO.setRemarks(remarkArr[i]);
	                dtlVO.setSupplierId(supplierIdArr[i]);
	                dtlVO.setSupplierCode(supplierCodeArr[i]);
	                dtlVO.setSupplierName(supplierNameArr[i]);
	                dao.insertInBillDtl(dtlVO);
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
	                        String url="采购入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
		                    // 插入库存变化明细。
		                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
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
	                    stockVO.setAmount("0");
	                    stockVO.setOccupyAmount("0");
	                    stockVO.setAvailableAmount("0");
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
	                    Integer count = 0;
	                    for(int j=0;j<curInArr.length;j++){
	                    	String curInCount = curInArr[j].split("@")[3];
	                    	String wpIdArr = curInArr[j].split("@")[0];
	                    	String wpCodeArr = curInArr[j].split("@")[1];
	                    	String wpNameArr = curInArr[j].split("@")[2];
	                    	count= count+Integer.parseInt(curInCount);
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
		                    String url="采购入库。/action/part/storageMng/stockInMng/partStockIn.ajax";
		                    // 插入库存变化明细。
		                    dao.insetStockDtl(curInCount,user,stockId,url,partIdArr[i]);
	                    }
	                    dao.updateStock(stockId, String.valueOf(count), user);
	                }
	                //更新采购拆分单明细的已入库数量
	                Map<String, String> map = new HashMap<String, String>();
	                map.put("DETAIL_ID", splitDtlArr[i]);
	                map.put("CUR_IN_AMOUNT", String.valueOf(curCount));
	                dao.updateSplitDtl(map, user);
	                //本次入库数量之和
	                totalInCount += Integer.parseInt(String.valueOf(curCount));
	                //新增入库流水记录
	                PtBuStockInContinualVO continualVO = new PtBuStockInContinualVO();
	                QuerySet getConNo = dao.getInConNo();
	                continualVO.setContinualNo(getConNo.getString(1, "NO"));
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
                //修改采购拆分单的已入库数量
                dao.updateSplit(splitId, totalInCount, user);
                //更新入库单的库管员和入库日期
                PtBuStockInVO stockInVO = new PtBuStockInVO();
                stockInVO.setInId(inId);
                stockInVO.setKeeperMan(account);
                stockInVO.setInDate(Pub.getCurrentDate());
                stockInVO.setUpdateUser(account);
                stockInVO.setUpdateTime(Pub.getCurrentDate());
                dao.updateInBill(stockInVO);
                //更新入库单明细的采购单价/采购金额/计划单价/计划金额/单位
                dao.updateInBillDtl(inId,DicConstant.RKLX_01);
                //更新入库流水的计划价
                dao.updateInFlow(inId);
            }
            //判断采购数量与入库数量相同时，关闭采购拆分单
            dao.updateSplitStatus(splitId,  user);
            
            
            for (int i = 0;i<partIdArr.length;i++) {
                //代办提醒 start
                QuerySet qsRole=dao.getRole();
                if(qsRole.getRowCount() > 0){
                    for (int j=0;j<qsRole.getRowCount() ;j++){
                        String roleId=qsRole.getString(j+1,"ROLE_ID");
                        AlertInfoVO infoVO=new AlertInfoVO();
                        infoVO.setAlertTitle(user.getOrgDept().getOName()+"有配件采购入库！");//标题
                        infoVO.setDesr("配件代码:"+partCodeArr[i]);//提醒内容
                        infoVO.setOverrun("0");//提醒周期
                        infoVO.setAlertRole(roleId);//角色
                        infoVO.setBusType(DicConstant.YWLX_01);//业务类型（配件、售后）
                        infoVO.setBusPk(partIdArr[i]);//业务主键
                        infoVO.setAlertType(DicConstant.TXLX_01);//提醒类型
                        infoVO.setCreateUser(user.getAccount());//发送人（当前登录人）
                        infoVO.setCreateOrgid(user.getOrgId());//发送部门（当前登录部门）
                        AlertManager.alertInsert(atx.getDBFactory(),infoVO);
                    }
                }
                //end
            }
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
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
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
}