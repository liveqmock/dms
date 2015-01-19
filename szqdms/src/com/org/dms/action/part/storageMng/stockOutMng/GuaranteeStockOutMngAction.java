package com.org.dms.action.part.storageMng.stockOutMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.GuaranteeStockOutMngDao;
import com.org.dms.vo.part.PtBuIssueOrderVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuStockOutContinualVO;
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
 * 三包急件出库Action
 *
 * @user : lichuang
 * @date : 2014-08-11
 */
public class GuaranteeStockOutMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "GuaranteeStockOutMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private GuaranteeStockOutMngDao dao = GuaranteeStockOutMngDao.getInstance(atx);


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
        	HashMap<String, String> hm = RequestUtil.getValues(request);
        	String ACCOUNT = hm.get("USER_ACCOUNT");
            BaseResultSet bs = dao.searchOutBill(page, user, conditions,ACCOUNT);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询销售订单
     *
     * @throws Exception
     */
    public void searchSale() throws Exception {
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
            BaseResultSet bs = dao.searchSale(page, user, conditions, WAREHOUSE_ID);
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
            String outNo = hm.get("ORDER_NO") + "CK";
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

//    /**
//     * 查询出库单配件
//     *
//     * @throws Exception
//     */
//    public void searchOutBillPart() throws Exception {
//        //定义request对象
//        RequestWrapper request = atx.getRequest();
//        //获取session中的user对象
//        User user = (User) atx.getSession().get(Globals.USER_KEY);
//        //定义查询分页对象
//        PageManager page = new PageManager();
//        //将request流中的信息转化为where条件
//        String conditions = RequestUtil.getConditionsWhere(request, page);
//        try {
//            String OUT_ID = Pub.val(request, "OUT_ID");//出库单ID
//            String ORDER_ID = Pub.val(request, "ORDER_ID");//销售订单ID
//            //BaseResultSet：结果集封装对象
//            BaseResultSet bs = dao.searchOutBillPart(page, user, conditions, OUT_ID, ORDER_ID);
//            //输出结果集，第一个参数”bs”为固定名称，不可修改
//            atx.setOutData("bs", bs);
//        } catch (Exception e) {
//            logger.error(e);
//            atx.setException(e);
//        }
//    }
    
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
            String ISSUE_ID = Pub.val(request, "ISSUE_ID");//出库单ID
            String account = Pub.val(request, "account");
            //BaseResultSet：结果集封装对象
            page.setPageRows(99999);
            BaseResultSet bs = dao.searchOutBillPart(page, user, conditions, ISSUE_ID,account);
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
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String flag = hm.get("FLAG");// 按钮标识(出库,出库完成)
            String issueId = hm.get("ISSUE_ID");//发料单ID
            String orderId = hm.get("ORDER_ID");//销售订单ID
            String detailIds = hm.get("DETAILIDS");
            String PARTIDS = hm.get("PARTIDS");
            String PARTCODES = hm.get("PARTCODES");
            String PARTNAMES = hm.get("PARTNAMES");
            String OUTAMOUNTS = hm.get("OUTAMOUNTS");
            String REMARKS = hm.get("REMARKS");
            String account = hm.get("USERACC");
            String PART_ID[]  =PARTIDS.split(","); 
            String PART_CODE[]  =PARTCODES.split(","); 
            String PART_NAME[]  =PARTNAMES.split(","); 
            String OUT_AMOUNT[]  =OUTAMOUNTS.split(","); 
            String REMARK[]  =REMARKS.split(","); 
            String detailId[]  =detailIds.split(",");
            
            QuerySet getWareInfo = dao.getWareInfo(orderId);
            String WAREHOUSE_ID = getWareInfo.getString(1, "WAREHOUSE_ID");
            String WAREHOUSE_CODE = getWareInfo.getString(1, "WAREHOUSE_CODE");
            String WAREHOUSE_NAME = getWareInfo.getString(1, "WAREHOUSE_NAME");
            String ORDER_NO = getWareInfo.getString(1, "ORDER_NO");
            
            QuerySet  checkPlanPrice = dao.checkPlanPrice(PARTIDS);
            String codes = checkPlanPrice.getString(1, "CODES");
            if(!"".equals(codes)&&codes!=null){
            	throw new Exception("配件"+checkPlanPrice.getString(1, "CODES")+"尚未维护计划价格，请维护计划价格之后再出库");
            }
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
            
            QuerySet  checkUnique = dao.checkOut(orderId);
            String OUT_ID = "";
            if(checkUnique.getRowCount()>0){
                OUT_ID = checkUnique.getString(1, 1);
            }else{
                /**生成出库单主表信息**/
                
                PtBuStockOutVO o_vo = new PtBuStockOutVO();
                o_vo.setOutNo(ORDER_NO+"CK");
                o_vo.setOrderId(orderId);
                o_vo.setOrderNo(ORDER_NO);
                o_vo.setWarehouseId(WAREHOUSE_ID);
                o_vo.setWarehouseCode(WAREHOUSE_CODE);
                o_vo.setWarehouseName(WAREHOUSE_NAME);
                o_vo.setOutStatus(DicConstant.CKDZT_01);
                o_vo.setOutType(DicConstant.CKLX_06);
                o_vo.setOutDate(Pub.getCurrentDate());
                o_vo.setPrintStatus(DicConstant.DYZT_01);
                o_vo.setCreateUser(account);
                o_vo.setCreateTime(Pub.getCurrentDate());
                o_vo.setCompanyId(user.getCompanyId());
                o_vo.setOemCompanyId(user.getOemCompanyId());
                o_vo.setOrgId(user.getOrgId());
                dao.insertOutBill(o_vo);
                OUT_ID = o_vo.getOutId();
            }
            /******************************************************************/
            for(int i = 0;i<detailId.length;i++){
//                PtBuStockOutDtlVO d_vo = new PtBuStockOutDtlVO();
                QuerySet getRetInfo = dao.getIssue(PART_ID[i],issueId);
//                d_vo.setOutId(OUT_ID);
//                d_vo.setPartId(PART_ID[i]);
//                d_vo.setPartCode(PART_CODE[i]);
//                d_vo.setPartName(PART_NAME[i]);
//                d_vo.setSupplierId(getRetInfo.getString(1, "SUPPLIER_ID"));
//                d_vo.setSupplierCode(getRetInfo.getString(1, "SUPPLIER_CODE"));
//                d_vo.setSupplierName(getRetInfo.getString(1, "SUPPLIER_NAME"));
//                d_vo.setPositionId(getRetInfo.getString(1, "POSITION_ID"));
//                d_vo.setOutAmount(OUT_AMOUNT[i]);
//                if ("myNull".equals(REMARK[i])) {
//                    REMARK[i] = "";
//                }else{
//                    d_vo.setRemarks(REMARK[i]);
//                }
//                d_vo.setUnit(getRetInfo.getString(1, "UNIT"));
//                d_vo.setKeepMan(account);
//                d_vo.setCreateUser(account);
//                d_vo.setCreateTime(Pub.getCurrentDate());
//                dao.insertOutBillDtl(d_vo);
                if ("myNull".equals(REMARK[i])) {
                    REMARK[i] = "";
                }
                //校验出库单明细是否存在
                String outBillDtlId = dao.checkOutBillDtlIsExist(OUT_ID, PART_ID[i], getRetInfo.getString(1, "POSITION_ID"), getRetInfo.getString(1, "SUPPLIER_ID"), user);
                if ("".equals(outBillDtlId)) {//不存在 插入
                    PtBuStockOutDtlVO stockOutDtlVO = new PtBuStockOutDtlVO();
                    stockOutDtlVO.setOutId(OUT_ID);
                    stockOutDtlVO.setPositionId(getRetInfo.getString(1, "POSITION_ID"));
                    stockOutDtlVO.setPartId(PART_ID[i]);
                    stockOutDtlVO.setPartCode(PART_CODE[i]);
                    stockOutDtlVO.setPartName(PART_NAME[i]);
//                    stockOutDtlVO.setShouldCount(shouldCountArr[i]);
                    stockOutDtlVO.setOutAmount(OUT_AMOUNT[i]);
                    stockOutDtlVO.setRemarks(REMARK[i]);
//                    stockOutDtlVO.setIssueId(issueIdArr[i]);
//                    stockOutDtlVO.setIssueNo(issueNoArr[i]);
                    stockOutDtlVO.setKeepMan(account);
                    stockOutDtlVO.setSupplierId(getRetInfo.getString(1, "SUPPLIER_ID"));
                    stockOutDtlVO.setSupplierCode(getRetInfo.getString(1, "SUPPLIER_CODE"));
                    stockOutDtlVO.setSupplierName(getRetInfo.getString(1, "SUPPLIER_NAME"));
                    stockOutDtlVO.setCreateTime(Pub.getCurrentDate());
                    stockOutDtlVO.setCreateUser(user.getAccount());
                    dao.insertOutBillDtl(stockOutDtlVO);
                    
                    PtBuStockOutContinualVO c_vo = new PtBuStockOutContinualVO();
                    QuerySet getcNo = dao.getcNo();
                    QuerySet getPlanPrice = dao.getPlanPrice(PART_ID[i]);
                    QuerySet getOutCnId = dao.getOutConId();
                    c_vo.setContinualId(getOutCnId.getString(1, 1));
                    c_vo.setContinualNo(getcNo.getString(1, "C_NO"));
                    c_vo.setOutId(OUT_ID);
                    c_vo.setOutDate(Pub.getCurrentDate());
                    c_vo.setOutNo(ORDER_NO+"CK");
                    c_vo.setPartId(PART_ID[i]);
                    c_vo.setPartCode(PART_CODE[i]);
                    c_vo.setPartName(PART_NAME[i]);
                    c_vo.setOutCount(OUT_AMOUNT[i]);
                    c_vo.setPlanPrice(getPlanPrice.getString(1, "PLAN_PRICE"));
                    c_vo.setKeeperMan(user.getAccount());
                    c_vo.setCreateTime(Pub.getCurrentDate());
                    c_vo.setCreateMan(user.getAccount());
                    c_vo.setOutCountTmp("0");
//                    c_vo.setOutCountTmp(String.valueOf(Integer.parseInt(OUT_AMOUNT[i])-Integer.parseInt(getOld.getString(1, "OUT_COUNT"))));
                    dao.insertOutCon(c_vo);
                } else {//存在 更新
                    PtBuStockOutDtlVO stockOutDtlVO = new PtBuStockOutDtlVO();
                    stockOutDtlVO.setDetailId(outBillDtlId);
                    stockOutDtlVO.setOutAmount(OUT_AMOUNT[i]);
                    stockOutDtlVO.setRemarks(REMARK[i]);
                    stockOutDtlVO.setUpdateTime(Pub.getCurrentDate());
                    stockOutDtlVO.setUpdateUser(user.getAccount());
                    dao.updateOutBillDtl(stockOutDtlVO);
                    
                    PtBuStockOutContinualVO c_vo = new PtBuStockOutContinualVO();
                    QuerySet getcNo = dao.getcNo();
                    QuerySet getPlanPrice = dao.getPlanPrice(PART_ID[i]);
                    QuerySet getOld = dao.getOld(OUT_ID,PART_ID[i]);
                    QuerySet getOutCnId = dao.getOutConId();
                    c_vo.setContinualId(getOutCnId.getString(1, 1));
                    c_vo.setContinualNo(getcNo.getString(1, "C_NO")); 
                    c_vo.setOutId(OUT_ID);
                    c_vo.setOutDate(Pub.getCurrentDate());
                    c_vo.setOutNo(ORDER_NO+"CK");
                    c_vo.setPartId(PART_ID[i]);
                    c_vo.setPartCode(PART_CODE[i]);
                    c_vo.setPartName(PART_NAME[i]);
                    c_vo.setOutCount(OUT_AMOUNT[i]);
                    c_vo.setPlanPrice(getPlanPrice.getString(1, "PLAN_PRICE"));
                    c_vo.setKeeperMan(user.getAccount());
                    c_vo.setCreateTime(Pub.getCurrentDate());
                    c_vo.setCreateMan(user.getAccount());
                    c_vo.setOutCountTmp(String.valueOf(Integer.parseInt(OUT_AMOUNT[i])-Integer.parseInt(getOld.getString(1, "OUT_COUNT"))));
                    dao.insertOutCon(c_vo);
                }
                dao.updateIssueOrderDtl(orderId,OUT_AMOUNT[i],issueId,detailId[i]);
            }
            
            
            // 入库完成
            if ("true".equals(flag)) {
	            /******************************************************************/
	            /**更改发料单状态**/
	            PtBuIssueOrderVO i_vo = new PtBuIssueOrderVO();
	            i_vo.setIssueId(issueId);
	            i_vo.setIssueStatus(DicConstant.FLDFLZT_02);
	            i_vo.setUpdateUser(account);
	            i_vo.setUpdateTime(Pub.getCurrentDate());
	            dao.updateIssue(i_vo);
	            /******************************************************************/
	            /**生成出库流水信息**/
//	            dao.insertOutFlow(OUT_ID, account);
	            /******************************************************************/
	            /**释放冻结**/
	            dao.updateStockDtl(orderId, account);
	            /******************************************************************/
	            /**减少总库存**/
	            dao.updateStock(WAREHOUSE_ID, orderId, account);
	            /******************************************************************/
	            String url="三包销售出库。/action/part/storageMng/stockInMng/stockOutMng/GuaranteeStockOutMngAction/partStockOutInEdit.ajax";
	            QuerySet getAmount = dao.getAmount(OUT_ID,orderId,user);
	            for(int a=0;a<getAmount.getRowCount();a++){
	            	String curInCount = getAmount.getString(a+1, "OUT_AMOUNT");
	            	String aAmount = getAmount.getString(a+1, "AAMONT");
	            	String SHOULD_COUNT = getAmount.getString(a+1, "SHOULD_COUNT");
		            String partId = getAmount.getString(a+1, "PART_ID");
	                // 插入库存变化明细。
	                dao.insetStockDtl(curInCount,aAmount,SHOULD_COUNT,user,OUT_ID,url,partId);
	            }
	            /**如果所有发料单都转换为入库单之后更新出库单状态**/
	            QuerySet chkout = dao.checkIfAllOut(orderId,issueId);
	            if(chkout.getRowCount()==0){
	            	QuerySet qs= dao.getOutAmount(OUT_ID);
	                PtBuStockOutVO outVO = new PtBuStockOutVO();
	                outVO.setOutId(OUT_ID);
	                outVO.setOutStatus(DicConstant.CKDZT_02);
	                outVO.setUpdateTime(Pub.getCurrentDate());
	                outVO.setUpdateUser(account);
	                dao.updateOutBill(outVO);
//	            	/**更改订单发运状态***/
//	        		PtBuSaleOrderVO s_vo = new PtBuSaleOrderVO();
//	        		s_vo.setOrderId(orderId);
//	        		s_vo.setShipStatus(DicConstant.DDFYZT_03);
//	        		s_vo.setUpdateTime(Pub.getCurrentDate());
//	        		s_vo.setUpdateUser(user.getAccount());
//	        		dao.updateSaleOrder(s_vo);
	        		if(qs.getRowCount()>0){
	            		String outAmount = qs.getString(1, "OUT_AMOUNT");
	            		if(Integer.parseInt(outAmount)>0){
	    	                /**更改订单发运状态***/
	    	                PtBuSaleOrderVO s_vo = new PtBuSaleOrderVO();
	    	                s_vo.setOrderId(orderId);
	    	                s_vo.setShipStatus(DicConstant.DDFYZT_03);
	    	                s_vo.setUpdateTime(Pub.getCurrentDate());
	    	                s_vo.setUpdateUser(user.getAccount());
	    	                dao.updateSaleOrder(s_vo);
	            		}else{
	            			//释放资金占用
	            			dao.orderReleaseFreez(orderId, user);
	    	                PtBuSaleOrderVO s_vo = new PtBuSaleOrderVO();
	    	                s_vo.setOrderId(orderId);
	    	                //s_vo.setShipStatus(DicConstant.DDFYZT_03);
	    	                s_vo.setOrderStatus(DicConstant.DDZT_06);
	    	                s_vo.setCloseDate(Pub.getCurrentDate());
	    	                s_vo.setUpdateTime(Pub.getCurrentDate());
	    	                s_vo.setUpdateUser(user.getAccount());
	    	                dao.updateSaleOrder(s_vo);
	            		}
	            	}
	            }
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
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        try {
            String outId = Pub.val(request, "outId");
            if (!dao.checkOutBillDtlIsExist(outId)) {
                throw new Exception("该出库单未维护出库清单,无法出库!");
            }
            String orderId = Pub.val(request, "orderId");
            String warehouseId = Pub.val(request, "warehouseId");
            this.partStockOut(outId, orderId, warehouseId);
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
            this.commonSaveOutBillDtl(hm, user);
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
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String outId = Pub.val(request, "outId");
        try {
//            if(dao.checkOutBillDtlIsExist(outId)){
//                throw new Exception("该出库单已进行过出库操作,无法删除!");
//            }
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
     * @param orderId     销售订单ID
     * @param warehouseId 仓库ID
     * @throws Exception
     */
    public void partStockOut(String outId, String orderId, String warehouseId) throws Exception {
        User user = (User) atx.getSession().get(Globals.USER_KEY);

        //将销售订单的订单发运状态置为已发料
        PtBuSaleOrderVO saleOrderVO = new PtBuSaleOrderVO();
        saleOrderVO.setOrderId(orderId);
        saleOrderVO.setShipStatus(DicConstant.DDFYZT_03);
        saleOrderVO.setUpdateTime(Pub.getCurrentDate());
        saleOrderVO.setUpdateUser(user.getAccount());
        dao.updateSaleOrder(saleOrderVO);

        //将出库单状态置为已出库
        PtBuStockOutVO stockOutVO = new PtBuStockOutVO();
        stockOutVO.setOutId(outId);
        stockOutVO.setOutStatus(DicConstant.CKDZT_02);
        stockOutVO.setOutDate(Pub.getCurrentDate());
        stockOutVO.setUpdateTime(Pub.getCurrentDate());
        stockOutVO.setUpdateTime(Pub.getCurrentDate());
        stockOutVO.setUpdateUser(user.getAccount());
        dao.updateOutBill(stockOutVO);
        //更新发料单明细的实发数量
        dao.updateIssueOrderDtl(orderId, outId, user);
        //将发料单的发料状态置为已发料
        dao.updateIssueOrder(orderId, user);
        //更新库存
//        dao.updateStock(warehouseId, orderId, user);
//        //更新库存明细
//        dao.updateStockDtl(orderId, user);
//        //记录出库流水
//        dao.insertOutFlow(outId, user);
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
        String orderId = hm.get("ORDER_ID");//销售订单ID
        String issueDtlIds = hm.get("ISSUEDTLIDS");//发料单明细ID(逗号分隔)
        String partIds = hm.get("PARTIDS");//配件ID(逗号分隔)
        String partCodes = hm.get("PARTCODES");//配件代码(逗号分隔)
        String partNames = hm.get("PARTNAMES");//配件名称(逗号分隔)
        String positionIds = hm.get("POSITIONIDS");//库位ID(逗号分隔)
        String outAmounts = hm.get("OUTAMOUNTS");//实际出库数量(逗号分隔)
        String remarks = hm.get("REMARKS");//备注(逗号分隔)
        String issueIds = hm.get("ISSUEIDS");//发料单ID(逗号分隔)
        String issueNos = hm.get("ISSUENOS");//发料单号(逗号分隔)
        String userAccounts = hm.get("USERACCOUNTS");//库管员(逗号分隔)
        String shouldCounts = hm.get("SHOULDCOUNTS");//应发数量(逗号分隔)
        String supplierIds = hm.get("SUPPLIERIDS");//供应商ID(逗号分隔)
        String supplierCodes = hm.get("SUPPLIERCODES");//供应商代码(逗号分隔)
        String supplierNames = hm.get("SUPPLIERNAMES");//供应商名称(逗号分隔)

        String[] issueDtlIdArr = issueDtlIds.split(",");
        String[] partIdArr = partIds.split(",");
        String[] partCodeArr = partCodes.split(",");
        String[] partNameArr = partNames.split(",");
        String[] positionIdArr = positionIds.split(",");
        String[] outAmountArr = outAmounts.split(",");
        String[] remarkArr = remarks.split(",");
        String[] issueIdArr = issueIds.split(",");
        String[] issueNoArr = issueNos.split(",");
        String[] userAccountArr = userAccounts.split(",");
        String[] shouldCountArr = shouldCounts.split(",");
        String[] supplierIdArr = supplierIds.split(",");
        String[] supplierCodeArr = supplierCodes.split(",");
        String[] supplierNameArr = supplierNames.split(",");
        if (!"".equals(partIds)) {
            for (int i = 0; i < issueDtlIdArr.length; i++) {
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
                    stockOutDtlVO.setShouldCount(shouldCountArr[i]);
                    stockOutDtlVO.setOutAmount(outAmountArr[i]);
                    stockOutDtlVO.setRemarks(remarkArr[i]);
                    stockOutDtlVO.setIssueId(issueIdArr[i]);
                    stockOutDtlVO.setIssueNo(issueNoArr[i]);
                    stockOutDtlVO.setKeepMan(userAccountArr[i]);
                    stockOutDtlVO.setSupplierId(supplierIdArr[i]);
                    stockOutDtlVO.setSupplierCode(supplierCodeArr[i]);
                    stockOutDtlVO.setSupplierName(supplierNameArr[i]);
                    stockOutDtlVO.setCreateTime(Pub.getCurrentDate());
                    stockOutDtlVO.setCreateUser(user.getAccount());
                    dao.insertOutBillDtl(stockOutDtlVO);
                } else {//存在 更新
                    PtBuStockOutDtlVO stockOutDtlVO = new PtBuStockOutDtlVO();
                    stockOutDtlVO.setDetailId(outBillDtlId);
                    stockOutDtlVO.setOutAmount(outAmountArr[i]);
                    stockOutDtlVO.setRemarks(remarkArr[i]);
                    stockOutDtlVO.setUpdateTime(Pub.getCurrentDate());
                    stockOutDtlVO.setUpdateUser(user.getAccount());
                    dao.updateOutBillDtl(stockOutDtlVO);
                }
            }
        }

        //更新出库单明细的销售单价
        dao.updateOutBillDtl(outId, orderId);
    }
}