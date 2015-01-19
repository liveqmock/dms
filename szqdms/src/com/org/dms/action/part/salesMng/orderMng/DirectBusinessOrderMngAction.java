package com.org.dms.action.part.salesMng.orderMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.orderMng.DirectBusinessOrderMngDao;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderPayVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 直营订单提报Action
 *
 * 直营订单提报
 * @author zhengyao
 * @date 2014-08-25
 * @version 1.0
 */
public class DirectBusinessOrderMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义直营订单提报Dao对象
    private DirectBusinessOrderMngDao directBusinessOrderMngDao = DirectBusinessOrderMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 订单资金查询
     */
    public void orderFundsSearch() throws Exception {
        RequestWrapper request = actionContext.getRequest();
        User user = (User) actionContext.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orderId = request.getParamValue("orderId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = directBusinessOrderMngDao.orderFundsSearch(page, user, conditions,orderId);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    /**
     * 订单提报
     */
    public void partOrderReport() throws Exception {

        try {
            // 订单主键
            String orderId = requestWrapper.getParamValue("orderId");
            // 订单金额
            String orderAmount = requestWrapper.getParamValue("orderAmount");
            if (orderAmount == null) {
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            }
            if(Double.parseDouble(orderAmount)<=0) {
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            } else {
//                // 配件账户可用查询
//                QuerySet fundsqs = (QuerySet)directBusinessOrderMngDao.accountSearch(user,false);
//                // 账户主键
//                String accountId = fundsqs.getString(1, "ACCOUNT_ID");
//                // 账户类型
//                String acountType = fundsqs.getString(1, "ACCOUNT_TYPE");
//                // 可用金额
//                double account = Double.parseDouble(fundsqs.getString(1, "AVAILABLE_AMOUNT"));
//                if(account<Double.parseDouble(orderAmount)) {
//                    throw new Exception("账户使用金额大于账户可用余额,不能进行提报.");
//                } 
                if(Double.parseDouble(orderAmount)<=0){
                    throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
                }else {
                	QuerySet payqs = directBusinessOrderMngDao.orderPayAmountSearch(orderId);
                    if(Double.parseDouble(orderAmount)!=Double.parseDouble(payqs.getString(1, "PAY_AMOUNT"))){
                        throw new Exception("订购金额与账户使用金额不一致,请确认后再进行提报.");
                    }
                    QuerySet fundsqs = directBusinessOrderMngDao.orderFundsAmountSearch(orderId);
                    boolean flag = false;
                    if(fundsqs.getRowCount()>0){
                        for(int i=0;i<fundsqs.getRowCount();i++){
                            if(Double.parseDouble(fundsqs.getString(i+1, "PAY_AMOUNT"))-Double.parseDouble(fundsqs.getString(i+1, "AVAILABLE_AMOUNT"))>0){
                                flag = true;
                            }
                        }
                        if(flag){
                            throw new Exception("账户使用金额大于账户可用余额,不能进行提报.");
                        }else{
                            for(int i=0;i<fundsqs.getRowCount();i++){
                                String accountId  = fundsqs.getString(i+1, "ACCOUNT_ID");
                                String payAmount  = fundsqs.getString(i+1, "PAY_AMOUNT");
                                String accountType  = fundsqs.getString(i+1, "ACCOUNT_TYPE");
                                //更新资金账户
                                directBusinessOrderMngDao.accountUpdate(accountId, payAmount);
                                //插入订单资金占用
                                directBusinessOrderMngDao.orderFundsOccupy(orderId, accountId, accountType, payAmount, user);
                            }
                        }
                    }
//                    // 配件账户(PT_BU_ACCOUNT)
//                    directBusinessOrderMngDao.accountUpdate(accountId, orderAmount);
//                    // 配件订单资金占用(PT_BU_SALE_ORDER_OCCUPY_FUNDS)
//                    directBusinessOrderMngDao.orderFundsOccupy(orderId, accountId, acountType, orderAmount, user);
//                    // 付款明细
//                    PtBuSaleOrderPayVO ptBuSaleOrderPayVO = new PtBuSaleOrderPayVO();
//                    // 销售订单主键
//                    ptBuSaleOrderPayVO.setOrderId(orderId);
//                    // 账户主键
//                    ptBuSaleOrderPayVO.setAccountId(accountId);
//                    // 付款金额
//                    ptBuSaleOrderPayVO.setPayAmount(orderAmount);
//                    // 账户类型
//                    ptBuSaleOrderPayVO.setAccountType(acountType);
//                    // 创建日期
//                    ptBuSaleOrderPayVO.setCreateTime(Pub.getCurrentDate());
//                    // 创建人
//                    ptBuSaleOrderPayVO.setCreateUser(user.getAccount());
//                    // 配件销售订单付款明细(PT_BU_SALE_ORDER_PAY)
//                    directBusinessOrderMngDao.saleOrderPayInsert(ptBuSaleOrderPayVO);
                    PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
                    vo.setOrderId(orderId);
                    vo.setApplyDate(Pub.getCurrentDate());
                    vo.setOrderStatus(DicConstant.DDZT_02);
                    vo.setUpdateUser(user.getAccount());
                    vo.setUpdateTime(Pub.getCurrentDate());
                    directBusinessOrderMngDao.orderInfoUpdate(vo);
                    actionContext.setOutMsg("", "操作成功！");
                }
            }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     *删除配件 
     */
    public void orderPartDelete() throws Exception {

        try {
            String dtlId = requestWrapper.getParamValue("dtlId");
            String orderId = requestWrapper.getParamValue("orderId");
            PtBuSaleOrderDtlVO vo = new PtBuSaleOrderDtlVO();
            vo.setDtlId(dtlId);
            directBusinessOrderMngDao.deleteOrderPart(vo);
            QuerySet qs = directBusinessOrderMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            directBusinessOrderMngDao.orderAmountUpdate(svo);
            actionContext.setOutMsg(svo.getXml(), "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 订单配件查询
     * @throws Exception
     */
    public void orderPartSearch() throws Exception {

        try {
            PageManager page = new PageManager();
            String orderId = requestWrapper.getParamValue("orderId");
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, page);
            page.setPageRows(10000);
            BaseResultSet bs = directBusinessOrderMngDao.orderPartSearch(page, user, conditions,orderId);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 新增配件销售订单明细
     * @throws Exception
     */
    public void insertSaleOrderDetail() throws Exception {

        try {
            // 订单主键
            String orderId = hashMap.get("ORDERID");
            // 配件主键
            String partIds = hashMap.get("PARTIDS");
            // 配件代码
            String partCodes = hashMap.get("PARTCODES");
            // 配件名称
            String partNames = hashMap.get("PARTNAMES");
            // 供应商ID
            String supplierIds = hashMap.get("SUPPLIERIDS");
            // 供应商代码
            String supplierCodes = hashMap.get("SUPPLIERCODES");
            // 供应商名称
            String supplierNames = hashMap.get("SUPPLIERNAMES");
            // 单价
            String unitPrices = hashMap.get("UNITPRICES");
            // 金额
            String amounts = hashMap.get("AMOUNTS");
            // 订购数量
            String orderCounts = hashMap.get("ORDERCOUNTS");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] supplierCodeArr = supplierCodes.split(",");
            String[] supplierNameArr = supplierNames.split(",");
            String[] unitPriceArr = unitPrices.split(",");
            String[] amountArr = amounts.split(",");
            String[] orderCountArr = orderCounts.split(",");
            for (int i = 0; i < partIdArr.length; i++) {
                // 配件销售订单表(pt_bu_sale_order_dtl)对应的实体
                PtBuSaleOrderDtlVO ptBuSaleOrderDtlVO = new PtBuSaleOrderDtlVO();
                QuerySet querySet = directBusinessOrderMngDao.getPlanPrice(partIdArr[i]);
                ptBuSaleOrderDtlVO.setPlanPrice(querySet.getString(1, "PLAN_PRICE"));
                // 订单主键
                ptBuSaleOrderDtlVO.setOrderId(orderId);
                // 配件ID
                ptBuSaleOrderDtlVO.setPartId(partIdArr[i]);
                // 配件代码
                ptBuSaleOrderDtlVO.setPartCode(partCodeArr[i]);
                // 配件名称
                ptBuSaleOrderDtlVO.setPartName(partNameArr[i]);
                // 创建人
                ptBuSaleOrderDtlVO.setCreateUser(user.getAccount());
                // 创建时间
                ptBuSaleOrderDtlVO.setCreateTime(Pub.getCurrentDate());
                if("9XXXXXX".equals(supplierCodeArr[i])) {
                    // ------ 待定供应商
                    ptBuSaleOrderDtlVO.setIfSupplier(DicConstant.SF_02);
                    // 供应商ID
                    ptBuSaleOrderDtlVO.setSupplierId(supplierIdArr[i]);
                    // 供应商CODE
                    ptBuSaleOrderDtlVO.setSupplierCode(supplierCodeArr[i]);
                    // 供应商NAME
                    ptBuSaleOrderDtlVO.setSupplierName(supplierNameArr[i]);
                } else {
                    // ------ 指定供应商
                    ptBuSaleOrderDtlVO.setIfSupplier(DicConstant.SF_01);
                    // 供应商ID
                    ptBuSaleOrderDtlVO.setSupplierId(supplierIdArr[i]);
                    // 供应商CODE
                    ptBuSaleOrderDtlVO.setSupplierCode(supplierCodeArr[i]);
                    // 供应商NAME
                    ptBuSaleOrderDtlVO.setSupplierName(supplierNameArr[i]);
                }
                // 单价
                ptBuSaleOrderDtlVO.setUnitPrice(unitPriceArr[i]);
                // 金额
                ptBuSaleOrderDtlVO.setAmount(amountArr[i]);
                // 订购数量
                ptBuSaleOrderDtlVO.setOrderCount(orderCountArr[i]);
                // 插入配件销售订单表(PT_BU_SALE_ORDER_DTL)
                directBusinessOrderMngDao.insertSaleOrderDetail(ptBuSaleOrderDtlVO);
            }
            QuerySet qs = directBusinessOrderMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setOrderAmount(qs.getString(1, "AMOUNT"));
            // 更新订单总金额
            directBusinessOrderMngDao.orderAmountUpdate(vo);
            actionContext.setOutMsg(vo.getXml(), "新增成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询配件
     * @throws Exception
     */
    public void searchPart() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            String orderId = requestWrapper.getParamValue("orderId");
            String warehouseId = requestWrapper.getParamValue("warehouseId");
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = directBusinessOrderMngDao.searchPart(pageManager, user, conditions,orderId,warehouseId);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 修改直营订单提报
     * @throws Exception
     */
    public void updateDirectBusinessOrder() throws Exception {

        try {
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            ptBuSaleOrderVO.setValue(hashMap);
            ptBuSaleOrderVO.setUpdateTime(Pub.getCurrentDate());
            ptBuSaleOrderVO.setUpdateUser(user.getAccount());
            // 通过Dao,执行删除配件预测
            directBusinessOrderMngDao.updateDirectBusinessOrder(ptBuSaleOrderVO);
            actionContext.setOutMsg("", "修改成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除直营订单提报
     * @throws Exception
     */
    public void deleteDirectBusinessOrder() throws Exception {

        try {
            // 通过request获取页面传递的参数，对于null值通过该方法将转换为""
            String orderId = Pub.val(requestWrapper, "orderId");
            // 配件销售订单表(PT_BU_SALE_ORDER)对应的实体
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            // 订单主键
            ptBuSaleOrderVO.setOrderId(orderId);
            // 通过Dao,执行删除配件预测
            directBusinessOrderMngDao.deleteDirectBusinessOrder(ptBuSaleOrderVO,user);
            // 返回更新结果和成功信息
            actionContext.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 直营订单提报查询
     *
     * @throws Exception
     */
    public void searchDirectBusinessOrder() throws Exception {

        try {
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String flag = Pub.val(requestWrapper, "flag");
            if ("false".equals(flag)) {
                // 订单未提报,已驳回
                conditions +=" AND ORDER_TYPE='" + DicConstant.DDLX_07 + "' AND ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+") AND ORG_ID = '" + user.getOrgId() + "'";
            } else {
                // 订单直营提报
                conditions +=" AND ORDER_TYPE='" + DicConstant.DDLX_07+ "' AND ORDER_STATUS IN ("+DicConstant.DDZT_07+") AND ORG_ID <> '" + user.getOrgId() + "'";
            }
            
            BaseResultSet bs = directBusinessOrderMngDao.searchDirectBusinessOrder(pageManager,conditions,user);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 直营订单提报保存
     *
     * @throws Exception
     */
    public void insertDirectBusinessOrder() throws Exception {

        try {
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            DBFactory factory = actionContext.getDBFactory();
            // 获取订单首位字符
            String firstLetter = PartOddNumberUtil.getOrderFirstLetter(factory,DicConstant.DDLX_07);
            // 订单编号获取
            String orderNo = PartOddNumberUtil.getSaleOrderNo(factory, firstLetter,user.getOrgCode());
            ptBuSaleOrderVO.setValue(hashMap);
            // 直营订单编号设置
            ptBuSaleOrderVO.setOrderNo(orderNo);
            // 订单状态(未提报)
            ptBuSaleOrderVO.setOrderStatus(DicConstant.DDZT_01);
            // 渠道代码
            ptBuSaleOrderVO.setOrgCode(user.getOrgCode());
            // 渠道名称
            ptBuSaleOrderVO.setOrgName(user.getOrgDept().getOName());
            // 是否使用信用额度(否)
            ptBuSaleOrderVO.setIfCredit(DicConstant.SF_02);
            // 是否免运费(否)
            ptBuSaleOrderVO.setIfTrans(DicConstant.SF_02);
            // 所属公司
            ptBuSaleOrderVO.setCompanyId(user.getCompanyId());
            // 渠道组织ID
            ptBuSaleOrderVO.setOrgId(user.getOrgId());
            // 创建人
            ptBuSaleOrderVO.setCreateUser(user.getAccount());
            // 创建时间
            ptBuSaleOrderVO.setCreateTime(Pub.getCurrentDate());
            // 状态(有效)
            ptBuSaleOrderVO.setStatus(DicConstant.YXBS_01);
            ptBuSaleOrderVO.setOemCompanyId(user.getOemCompanyId());
            // 是否渠道内订单(否)
            ptBuSaleOrderVO.setIfChanelOrder(DicConstant.SF_02);
            // 是否延期订单(否)
            ptBuSaleOrderVO.setIfDelayOrder(DicConstant.SF_02);
            // 交货地点类型(大库)
            ptBuSaleOrderVO.setAddrType(DicConstant.JHDDLX_02);
            // 发运状态(未发料)
            ptBuSaleOrderVO.setShipStatus(DicConstant.DDFYZT_01);
            directBusinessOrderMngDao.insertDirectBusinessOrder(ptBuSaleOrderVO);
            actionContext.setOutMsg(ptBuSaleOrderVO.getRowXml(), "操作成功！");
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 资金账户可用余额查询
     *
     * @throws Exception
     */
    public void accountSearch() throws Exception {

        try {
            BaseResultSet bs  = (BaseResultSet)directBusinessOrderMngDao.accountSearch(user,true);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 订单审核驳回
     *
     * @throws Exception
     */
    public void partOrderCheckApproveBack() throws Exception {

        try {
            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setValue(hashMap);
            // 审核人
            cvo.setCheckUser(user.getAccount());
            // 审核时间
            cvo.setCheckDate(Pub.getCurrentDate());
            // 创建人
            cvo.setCreateUser(user.getAccount());
            // 创建时间
            cvo.setCreateTime(Pub.getCurrentDate());
            cvo.setCompanyId(user.getCompanyId());
            cvo.setOrgId(user.getOrgId());
            // 状态(有效)
            cvo.setStatus(DicConstant.YXBS_01);
            // 审核驳回
            cvo.setCheckResult(DicConstant.DDZT_04);
            // 
            cvo.setOemCompanyId(user.getOemCompanyId());
            // 审核ID
            cvo.setCheckOrg(user.getOrgId());
            //插入审核日志
            directBusinessOrderMngDao.partOrderCheckLogInsert(cvo);

            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setValue(hashMap);
            vo.setOrderStatus(DicConstant.DDZT_04);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            directBusinessOrderMngDao.partOrderCheckApproveBack(vo);
            actionContext.setOutMsg(vo.getRowXml(), "操作成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 订单审核通过
     *
     * @throws Exception
     */
    public void partOrderCheckPass() throws Exception {

        try {
            // 订单ID
            String orderId = hashMap.get("ORDER_ID");
            // 延期订单标识(1:不生成延期单,2:生成延期单)
//            String flag = requestWrapper.getParamValue("flag");
//            String dtlIds = hashMap.get("DTLIDS");
            // 审核数量
//            String auditCounts = hashMap.get("AUDITCOUNTS");
            String accountIds = hashMap.get("ACCOUNT_ID");
            String accountTypes = hashMap.get("ACCOUNT_TYPE");
            String payAmounts = hashMap.get("PAY_AMOUNT");
//            String[] dtlId = dtlIds.split(",");
//            String[] auditCount = auditCounts.split(",");
            String[] accountId = accountIds.split(",");
            String[] accountType = accountTypes.split(",");
            String[] payAmount = payAmounts.split(",");
            
            
//            for(int i=0;i<dtlId.length;i++){
//                //更新订单明细审核数量
//                directBusinessOrderMngDao.orderAuditCountUpdate(auditCount[i], dtlId[i], orderId, user);
//            }
            for (int i=0;i<accountType.length;i++) {
            	QuerySet querySet = (QuerySet)directBusinessOrderMngDao.accountSearch0(orderId,accountType[i]);
            	// 账户可用余额
            	Double availableAmout = Double.valueOf(querySet.getString(1, "AVAILABLE_AMOUNT"));
            	if (availableAmout<Double.valueOf(payAmount[i])) {
            		// 账户可用余额小于订单总金额
            		throw new Exception("账户可用余额不足.");
            	}
            }

            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
//            vo.setValue(hashMap);
            
            // 订单状态
            vo.setOrderStatus(DicConstant.DDZT_02);
            // 发运状态
            vo.setShipStatus(DicConstant.DDFYZT_01);
            // 更新人
            vo.setUpdateUser(user.getAccount());
            // 更新时间
            vo.setUpdateTime(Pub.getCurrentDate());
            // 更新订单状态
            directBusinessOrderMngDao.partOrderCheckPass(vo);

            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setValue(hashMap);
            // 审核人
            cvo.setCheckUser(user.getAccount());
            // 审核时间
            cvo.setCheckDate(Pub.getCurrentDate());
            // 创建人
            cvo.setCreateUser(user.getAccount());
            // 创建时间
            cvo.setCreateTime(Pub.getCurrentDate());
            cvo.setCompanyId(user.getCompanyId());
            cvo.setOrgId(user.getOrgId());
            // 状态(有效)
            cvo.setStatus(DicConstant.YXBS_01);
            // 审核通过
            cvo.setCheckResult(DicConstant.DDZT_03);
            // 
            cvo.setOemCompanyId(user.getOemCompanyId());
            // 审核ID
            cvo.setCheckOrg(user.getOrgId());
            //插入审核日志
            directBusinessOrderMngDao.partOrderCheckLogInsert(cvo);

            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            ptBuSaleOrderVO.setValue(hashMap);
            // 新订单主键
            String newOrderId = SequenceUtil.getCommonSerivalNumber(actionContext.getDBFactory());
            // 生成直营店订单
            directBusinessOrderMngDao.saleOrderInsert(newOrderId,ptBuSaleOrderVO,user);

            // 添加付款明细
            PtBuSaleOrderPayVO ptBuSaleOrderPayVO = new PtBuSaleOrderPayVO();
            // 订单主键
            ptBuSaleOrderPayVO.setOrderId(newOrderId);
            // 创建人
            ptBuSaleOrderPayVO.setCreateUser(user.getAccount());
            // 创建时间
            ptBuSaleOrderPayVO.setCreateTime(Pub.getCurrentDate());
            
            for (int i =0 ;i<accountId.length;i++) {
            	// 付款金额
            	ptBuSaleOrderPayVO.setPayAmount(payAmount[i]);
            	ptBuSaleOrderPayVO.setPayId("");
            	// 账户主键
            	ptBuSaleOrderPayVO.setAccountId(accountId[i]);
            	// 账户类型(现金)
            	ptBuSaleOrderPayVO.setAccountType(accountType[i]);
            	directBusinessOrderMngDao.saleOrderPayInsert(ptBuSaleOrderPayVO);
            	
            	// 更新资金账户
//            directBusinessOrderMngDao.orderFundsAmountSearch(newOrderId);
            	directBusinessOrderMngDao.accountUpdate(accountId[i],payAmount[i]);
            	
            	// 资金占用
            	directBusinessOrderMngDao.orderFundsOccupy(newOrderId,accountId[i],accountType[i],payAmount[i],user);
            }

            //订单占用库存
//            directBusinessOrderMngDao.orderFreez(orderId);

//            if("2".equals(flag)){
//                //生成延期订单
//            	directBusinessOrderMngDao.delayOrderInfoInsert(orderId,user);
//            }
            actionContext.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 新增付款信息
     */
    public void orderFundsInsert() throws Exception {
        RequestWrapper request = actionContext.getRequest();
        User user = (User) actionContext.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            
            String orderId  = hm.get("ORDER_ID");
            String accountId= hm.get("ACCOUNT_ID");
            String accountType= hm.get("ACCOUNT_TYPE");
            boolean flag = directBusinessOrderMngDao.orderAccountPaySearch(orderId,accountId,accountType);
            if(flag){
                actionContext.setOutMsg("","该账户类型已使用，请重新选择!");
            }else{
                PtBuSaleOrderPayVO vo = new PtBuSaleOrderPayVO();
                vo.setValue(hm);
                vo.setCreateUser(user.getAccount());
                vo.setCreateTime(Pub.getCurrentDate());
                directBusinessOrderMngDao.orderFundsInsert(vo);
                actionContext.setOutMsg(vo.getXml(), "保存成功!");
            }
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    /**
     * 修改付款信息
     */
    public void orderFundsUpdate() throws Exception {
        RequestWrapper request = actionContext.getRequest();
        User user = (User) actionContext.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            PtBuSaleOrderPayVO vo = new PtBuSaleOrderPayVO();
            vo.setValue(hm);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            directBusinessOrderMngDao.orderFundsUpdate(vo);
            actionContext.setOutMsg(vo.getXml(), "保存成功!");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除付款信息
     */
    public void orderFundsDelete()throws Exception {
        RequestWrapper request = actionContext.getRequest();
        try {
            String payId = request.getParamValue("payId");
            PtBuSaleOrderPayVO vo = new PtBuSaleOrderPayVO();
            vo.setPayId(payId);
            directBusinessOrderMngDao.orderFundsDelete(vo);
            actionContext.setOutMsg("", "删除成功!");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 资金账户查询
     *
     * @throws Exception
     */
    public void accountSearch1() throws Exception {

        try {
            BaseResultSet bs  = (BaseResultSet)directBusinessOrderMngDao.accountSearch1(user);
            bs.setFieldDic("ACCOUNT_TYPE", DicConstant.ZJZHLX);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
}
