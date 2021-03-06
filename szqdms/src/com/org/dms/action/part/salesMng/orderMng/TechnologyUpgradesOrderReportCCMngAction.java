package com.org.dms.action.part.salesMng.orderMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.orderMng.TechnologyUpgradesOrderReportCCMngDao;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlExtendVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
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
 * 技术升级订单提报Action
 *
 * 技术升级订单提报
 * @author zhengyao
 * @date 2014-09-16
 * @version 1.0
 */
public class TechnologyUpgradesOrderReportCCMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义技术升级订单提报Dao对象
    private TechnologyUpgradesOrderReportCCMngDao technologyUpgradesOrderReportCCMngDao = TechnologyUpgradesOrderReportCCMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    /**
     * 订单提报
     */
    public void partOrderReport() throws Exception {

        try {
            String ifSuper = requestWrapper.getParamValue("ifSuper");
            // 配送中心订单主键
            String orderId = requestWrapper.getParamValue("orderId");
//            // 配送中心订单主键获取
//            QuerySet querySet = technologyUpgradesOrderReportCCMngDao.searchSourceOrderId(serverOrderId);
            // 订单金额
            String orderAmount = requestWrapper.getParamValue("orderAmount");
            if (orderAmount == null) {
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            }
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setApplyDate(Pub.getCurrentDate());
            vo.setOrderStatus(DicConstant.DDZT_02);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            if(Double.parseDouble(orderAmount)<=0) {
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            } else {
                if (DicConstant.SF_01.equals(ifSuper)) {
//                    if (querySet.getRowCount() > 0) {
//                        String orderId = querySet.getString(1, "ORDER_ID");
                        // 更新配送中心订单明细
                        technologyUpgradesOrderReportCCMngDao.updateOrderDtl(orderId);
                        QuerySet qs1 = technologyUpgradesOrderReportCCMngDao.orderAmountSearch(orderId);
                        PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
                        svo.setOrderId(orderId);
                        svo.setOrderAmount(qs1.getString(1, "AMOUNT"));
                        technologyUpgradesOrderReportCCMngDao.orderAmountUpdate(svo);
                        QuerySet querySet = technologyUpgradesOrderReportCCMngDao.getserverOrderId(orderId);
                        if (querySet.getRowCount() > 0) {
                        	vo.setOrderId(querySet.getString(1, "DIR_SOURCE_ORDER_ID"));
                        }
                        // 服务站订单更新
                        technologyUpgradesOrderReportCCMngDao.orderInfoUpdate(vo);
                        vo.setOrderId(orderId);
                        // 配送中心订单更新
                        technologyUpgradesOrderReportCCMngDao.orderInfoUpdate(vo);
//                    }
                }
              //查询配送中心信用额度账户
//                QuerySet qs = technologyUpgradesOrderReportCCMngDao.queryAccoutInfo(orderId);
//                if(qs.getRowCount()>0){
//                	String accountId  = qs.getString(1, "ACCOUNT_ID");
//                    String occpuyAmount  = qs.getString(1, "OCCUPY_AMOUNT");
//                    String availableAmount  = qs.getString(1, "AVAILABLE_AMOUNT");
////                    if(Double.parseDouble(availableAmount)>=Double.parseDouble(orderAmount)){
//                    	//插入订单账户使用信息
//                    	technologyUpgradesOrderReportCCMngDao.insertOrderPay(orderId, accountId, orderAmount, user);
//                        //更新资金账户
//                    	technologyUpgradesOrderReportCCMngDao.accountUpdate(accountId, orderAmount);
//                        //插入订单资金占用
//                    	technologyUpgradesOrderReportCCMngDao.orderFundsOccupy(orderId, accountId, DicConstant.ZJZHLX_04, orderAmount, user);
////                    }else{
////                    	throw new Exception("默认配送中心信用额度可用余额不足，不能提报技术升级订单.");
////                    }
//                }else{
//                	throw new Exception("默认配送中心没有信用额度，不能提报技术升级订单.");
//                }
                vo.setOrderId(orderId);
                // 服务站订单更新
                technologyUpgradesOrderReportCCMngDao.orderInfoUpdate(vo);
                actionContext.setOutMsg("", "操作成功！");
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
            technologyUpgradesOrderReportCCMngDao.deleteOrderPart(vo);
            QuerySet qs = technologyUpgradesOrderReportCCMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            technologyUpgradesOrderReportCCMngDao.orderAmountUpdate(svo);
            actionContext.setOutMsg(svo.getXml(), "删除成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     *修改配件订购数量 
     */
    public void orderPartCountSave() throws Exception {

        try {
            String dtlId = hashMap.get("DTL_ID");
            String orderId = hashMap.get("ORDER_ID");
            String orderCount = hashMap.get("ORDER_COUNT");
            String unitPrice = hashMap.get("UNIT_PRICE");
            technologyUpgradesOrderReportCCMngDao.updateOrderPart(dtlId,orderId,orderCount,user);
            QuerySet qs = technologyUpgradesOrderReportCCMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            technologyUpgradesOrderReportCCMngDao.orderAmountUpdate(svo);
            PtBuSaleOrderDtlExtendVO ptExtendVO = new PtBuSaleOrderDtlExtendVO();
            ptExtendVO.setDtlId(dtlId);
            ptExtendVO.setOrderCount(orderCount);
            ptExtendVO.setOrderAmount(qs.getString(1, "AMOUNT"));
            Double price = (Double.valueOf(unitPrice)*Double.valueOf(orderCount));
            ptExtendVO.setAmount(String.format("%.2f",price));
            ptExtendVO.setUnitPrice(String.format("%.2f",Double.valueOf(unitPrice)));
            actionContext.setOutMsg(ptExtendVO.getRowXml(), "");
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
            BaseResultSet bs = technologyUpgradesOrderReportCCMngDao.orderPartSearch(page, user, conditions,orderId);
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
                QuerySet querySet = technologyUpgradesOrderReportCCMngDao.getPlanPrice(partIdArr[i]);
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
                technologyUpgradesOrderReportCCMngDao.insertSaleOrderDetail(ptBuSaleOrderDtlVO);
            }
            QuerySet qs = technologyUpgradesOrderReportCCMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderAmount(qs.getString(1, "AMOUNT"));
            
            QuerySet querySet = technologyUpgradesOrderReportCCMngDao.getserverOrderId(orderId);
            if (querySet.getRowCount() > 0) {
            	String orderId1 = querySet.getString(1, "DIR_SOURCE_ORDER_ID");
            	if (!orderId1.equals("0")) {
            		vo.setOrderId(orderId1);
            		technologyUpgradesOrderReportCCMngDao.orderAmountUpdate(vo);
            	}
            }
            
            vo.setOrderId(orderId);
            technologyUpgradesOrderReportCCMngDao.orderAmountUpdate(vo);
            
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
            BaseResultSet baseResultSet = technologyUpgradesOrderReportCCMngDao.searchPart(pageManager, user, conditions,orderId,warehouseId);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 修改三包急件订单提报
     * @throws Exception
     */
    public void updateClaimCyclOrder() throws Exception {

        try {
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            ptBuSaleOrderVO.setValue(hashMap);
            ptBuSaleOrderVO.setUpdateTime(Pub.getCurrentDate());
            ptBuSaleOrderVO.setUpdateUser(user.getAccount());
            // 供货库ID
            String warehouseId = ptBuSaleOrderVO.getWarehouseId();
            // 供货库CODE
            String warehouseCode = ptBuSaleOrderVO.getWarehouseCode();
            // 供货库NAME
            String warehouseName = ptBuSaleOrderVO.getWarehouseName();
            if (DicConstant.SF_01.endsWith(hashMap.get("IF_SUPER"))) {
                // 查询配送中心
                QuerySet dcQuerySet = technologyUpgradesOrderReportCCMngDao.searchOrgId(hashMap.get("ORG_ID"));
                if (dcQuerySet.getRowCount() <= 0) {
                    throw new Exception("请联系系统管理员维护默认配送中心.");
                }
                // 配送中心ID
                ptBuSaleOrderVO.setWarehouseId(dcQuerySet.getString(1, "ORG_ID"));
                // 配送中心CODE
                ptBuSaleOrderVO.setWarehouseCode(dcQuerySet.getString(1, "CODE"));
                // 配送中心NAME
                ptBuSaleOrderVO.setWarehouseName(dcQuerySet.getString(1, "ONAME"));
                // 修改服务站订单
                technologyUpgradesOrderReportCCMngDao.updateClaimCyclOrder(ptBuSaleOrderVO);
                // 查询配送中心订单号
                QuerySet querySet = technologyUpgradesOrderReportCCMngDao.searchSourceOrderId(ptBuSaleOrderVO.getOrderId());
                // 订单主键
                ptBuSaleOrderVO.setOrderId(querySet.getString(1, "ORDER_ID"));
                // 订单号
                ptBuSaleOrderVO.setOrderNo(querySet.getString(1, "ORDER_NO"));
            }
            // 供货库ID
            ptBuSaleOrderVO.setWarehouseId(warehouseId);
            // 供货库CODE
            ptBuSaleOrderVO.setWarehouseCode(warehouseCode);
            // 供货库NAME
            ptBuSaleOrderVO.setWarehouseName(warehouseName);
            // 修改配送中心订单
            technologyUpgradesOrderReportCCMngDao.updateClaimCyclOrder(ptBuSaleOrderVO);
            actionContext.setOutMsg("", "修改成功！");
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
    public void dealerSearchClaimCyclOrder() throws Exception {

        try {
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String flag = Pub.val(requestWrapper, "flag");
            if ("false".equals(flag)) {
                // 订单未提报,已驳回
                conditions +=" AND B.ORDER_TYPE='" + DicConstant.DDLX_10+ "' AND B.ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+") AND B.CREATE_USER = '" + user.getAccount() + "'";
            } else {
                // 订单直营提报
                conditions +=" AND B.ORDER_TYPE='" + DicConstant.DDLX_10+ "' AND B.ORDER_STATUS IN ("+DicConstant.DDZT_02+")";
            }
            
            BaseResultSet bs = technologyUpgradesOrderReportCCMngDao.searchDirectBusinessOrder(pageManager,conditions,user);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }

    /**
     * 直营订单提报查询
     *
     * @throws Exception
     */
//    public void dealerSearchClaimCyclOrder() throws Exception {
//
//        try {
//            PageManager pageManager = new PageManager();
//            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
//            String flag = Pub.val(requestWrapper, "flag");
//            if ("false".equals(flag)) {
//                // 订单未提报,已驳回
//                conditions +=" AND A.ORDER_TYPE='" + DicConstant.DDLX_10+ "' AND A.ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+") AND ";
//                // A.ORG_ID = '" + user.getOrgId() + "'";
//            } else {
//                //已提报
//                conditions +=" AND A.ORDER_TYPE='" + DicConstant.DDLX_10+ "' AND A.ORDER_STATUS IN ("+DicConstant.DDZT_02+")";
//            }
//            
//            BaseResultSet bs = technologyUpgradesOrderReportCCMngDao.dealerSearchDirectBusinessOrder(pageManager,conditions,user);
//            actionContext.setOutData("bs", bs);
//        } catch (Exception e) {
//            logger.error(e);
//            actionContext.setException(e);
//        }
//    }

    /**
     * 技术升级订单提报保存
     *
     * @throws Exception
     */
    public void insertClaimCyclOrder() throws Exception {

        try {
//            QuerySet querySet = technologyUpgradesOrderReportMngDao.searchOrgId(user);
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            DBFactory factory = actionContext.getDBFactory();
            // 获取订单首位字符
            String firstLetter = PartOddNumberUtil.getOrderFirstLetter(factory,DicConstant.DDLX_10);
            
            ptBuSaleOrderVO.setValue(hashMap);
            // 订单状态(未提报)
            ptBuSaleOrderVO.setOrderStatus(DicConstant.DDZT_01);
            // 是否使用信用额度(否)
            ptBuSaleOrderVO.setIfCredit(DicConstant.SF_02);
            // 是否免运费(否)
            ptBuSaleOrderVO.setIfTrans(DicConstant.SF_02);
            // 所属公司
            ptBuSaleOrderVO.setCompanyId(user.getCompanyId());
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
            // 是否为渠道提订单
            ptBuSaleOrderVO.setIfSuper(hashMap.get("IF_SUPER"));
            // 服务站订单ID
            String serverOrderId = "";
            // 服务站订单编号
            String serverOrderNo = "";
            QuerySet querySet = technologyUpgradesOrderReportCCMngDao.searchOrgId(hashMap.get("ORG_ID"));
            String dcOrderNo = "";
            if (DicConstant.SF_01.endsWith(hashMap.get("IF_SUPER"))) {
                if (querySet.getRowCount() <= 0) {
                    throw new Exception("请联系系统管理员维护默认配送中心.");
                }
                // 配送中心订单编号获取
                dcOrderNo = PartOddNumberUtil.getSaleOrderNo(factory, firstLetter,querySet.getString(1, "CODE"));
                // 配送中心订单编号
                ptBuSaleOrderVO.setOrderNo(dcOrderNo);
                // 订单编号获取(服务站)
                serverOrderNo = PartOddNumberUtil.getSaleOrderNo(factory, firstLetter,hashMap.get("ORG_CODE"));
                // 服务站订单主键
                serverOrderId = SequenceUtil.getCommonSerivalNumber(actionContext.getDBFactory());;
                // 渠道组织ID
                ptBuSaleOrderVO.setOrgId(querySet.getString(1, "ORG_ID"));
                // 渠道代码
                ptBuSaleOrderVO.setOrgCode(querySet.getString(1, "CODE"));
                // 渠道名称
                ptBuSaleOrderVO.setOrgName(querySet.getString(1, "ONAME"));
                ptBuSaleOrderVO.setDirSourceOrderId(serverOrderId);
                ptBuSaleOrderVO.setDirSourceOrderNo(serverOrderNo);
            } else {
                // 渠道组织ID
                ptBuSaleOrderVO.setOrgId(user.getOrgId());
                // 渠道代码
                ptBuSaleOrderVO.setOrgCode(user.getOrgCode());
                // 渠道名称
                ptBuSaleOrderVO.setOrgName(user.getOrgDept().getOName());
                // 自提订单编号获取
                serverOrderNo = PartOddNumberUtil.getSaleOrderNo(factory, firstLetter,user.getOrgCode());
                // 订单编号
                ptBuSaleOrderVO.setOrderNo(serverOrderNo);
            }
            // 配送中心订单
            technologyUpgradesOrderReportCCMngDao.insertClaimCyclOrder(ptBuSaleOrderVO);
            String dcOrderId = "";
            dcOrderId = ptBuSaleOrderVO.getOrderId();
            
            // 提渠道订单
            if (DicConstant.SF_01.endsWith(hashMap.get("IF_SUPER"))) {
                ptBuSaleOrderVO.setOrderId(serverOrderId);
                ptBuSaleOrderVO.setOrderNo(serverOrderNo);
                ptBuSaleOrderVO.setDirSourceOrderId("");
                ptBuSaleOrderVO.setDirSourceOrderNo("");
                // 渠道组织ID
                ptBuSaleOrderVO.setOrgId(user.getOrgId());
                // 
                ptBuSaleOrderVO.setWarehouseId(querySet.getString(1, "ORG_ID"));
                // 
                ptBuSaleOrderVO.setWarehouseCode(querySet.getString(1, "CODE"));
                // 
                ptBuSaleOrderVO.setWarehouseName(querySet.getString(1, "ONAME"));
                // 是否渠道内订单(是)
                ptBuSaleOrderVO.setIfChanelOrder(DicConstant.SF_01);
                // 服务站订单
                technologyUpgradesOrderReportCCMngDao.insertClaimCyclOrder(ptBuSaleOrderVO);
                ptBuSaleOrderVO.setOrgId(querySet.getString(1, "ORG_ID"));
                ptBuSaleOrderVO.setOrderNo(dcOrderNo);
                ptBuSaleOrderVO.setOrderId(dcOrderId);
            }
            actionContext.setOutMsg(ptBuSaleOrderVO.getRowXml(), "操作成功！");
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
            technologyUpgradesOrderReportCCMngDao.partOrderCheckLogInsert(cvo);

            // 释放占用资金
            
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setValue(hashMap);
            vo.setOrderStatus(DicConstant.DDZT_04);
            vo.setIfDelayOrder(DicConstant.SF_02);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            technologyUpgradesOrderReportCCMngDao.partOrderCheckApproveBack(vo);
            
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            ptBuSaleOrderVO.setValue(hashMap);
            QuerySet querySet = technologyUpgradesOrderReportCCMngDao.sourceOrderId(vo.getOrderId());
            ptBuSaleOrderVO.setOrderId(querySet.getString(1, "DIR_SOURCE_ORDER_ID"));
            ptBuSaleOrderVO.setIfDelayOrder(DicConstant.SF_02);
            ptBuSaleOrderVO.setOrderStatus(DicConstant.DDZT_04);
            ptBuSaleOrderVO.setUpdateUser(user.getAccount());
            ptBuSaleOrderVO.setUpdateTime(Pub.getCurrentDate());
            technologyUpgradesOrderReportCCMngDao.partOrderCheckApproveBack(ptBuSaleOrderVO);
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
            String flag = requestWrapper.getParamValue("flag");
            String orgId = requestWrapper.getParamValue("orgId");
            String dtlIds = hashMap.get("DTLIDS");
            QuerySet querySet = technologyUpgradesOrderReportCCMngDao.sourceOrderId(orderId);
            String sourceOrderIdString = querySet.getString(1, "DIR_SOURCE_ORDER_ID");
            // 审核数量
            String auditCounts = hashMap.get("AUDITCOUNTS");
            String[] dtlId = dtlIds.split(",");
            String[] auditCount = auditCounts.split(",");
            QuerySet querySet0 = technologyUpgradesOrderReportCCMngDao.searchOrderDtl(sourceOrderIdString);
            // 是为渠道提订单
            if (DicConstant.SF_01.equals(hashMap.get("ORDER_ID"))) {
            	for(int i=0;i<dtlId.length;i++){
            		// 更新订单明细审核数量(配送中心)
            		technologyUpgradesOrderReportCCMngDao.orderAuditCountUpdate(auditCount[i], dtlId[i], orderId, user);
            		// 更新订单明细审核数量(服务站)
            		technologyUpgradesOrderReportCCMngDao.orderAuditCountUpdate(auditCount[i], querySet0.getString(i+1, "DTL_ID"), sourceOrderIdString, user);
            	}
            } else {
            	
            }

            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setValue(hashMap);
            // 订单状态
            vo.setOrderStatus(DicConstant.DDZT_03);
            // 发运状态
            vo.setShipStatus(DicConstant.DDFYZT_01);
            // 更新人
            vo.setUpdateUser(user.getAccount());
            // 更新时间
            vo.setUpdateTime(Pub.getCurrentDate());
            // 更新订单状态
            technologyUpgradesOrderReportCCMngDao.partOrderCheckPass(vo);

            // 配送中心订单状态
            QuerySet qs = technologyUpgradesOrderReportCCMngDao.searchCheckUser(orgId);
            vo.setOrderId("");
            vo.setOrderId(sourceOrderIdString);
            // 更新订单状态
            technologyUpgradesOrderReportCCMngDao.partOrderCheckPass(vo);

            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setValue(hashMap);
            // 审核人
            cvo.setCheckUser(qs.getString(1, "USER_ACCOUNT"));
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
            //插入审核日志(配送中心)
            technologyUpgradesOrderReportCCMngDao.partOrderCheckLogInsert(cvo);
            cvo.setCheckId("");
            cvo.setOrderId(sourceOrderIdString);
            //插入审核日志(服务站)
            technologyUpgradesOrderReportCCMngDao.partOrderCheckLogInsert(cvo);

            //订单占用库存
            technologyUpgradesOrderReportCCMngDao.orderFreez(orderId);
            if("2".equals(flag)){
                //生成延期订单
                technologyUpgradesOrderReportCCMngDao.delayOrderInfoInsert(orderId,user);
            }
            actionContext.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
}
