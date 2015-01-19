package com.org.dms.action.part.salesMng.orderMng;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.orderMng.ClaimCyclOrderReportMngDao;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlExtendVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.alertmsg.AlertInfoVO;
import com.org.framework.alertmsg.AlertManager;
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
import com.org.mvc.context.ResponseWrapper;

/**
 * 三包急件订单提报Action
 *
 * 三包急件订单提报
 * @author zhengyao
 * @date 2014-09-10
 * @version 1.0
 */
public class claimCyclOrderReportMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义三包急件订单提报Dao对象
    private ClaimCyclOrderReportMngDao claimCyclOrderReportMngDao = ClaimCyclOrderReportMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 获取页面信息
    private HashMap<String, String> hashMap = RequestUtil.getValues(requestWrapper);

    
    /**
     * 车辆校验查询
     * @throws Exception
     */
    public void guaranteesHurryPartVinCheck()  throws Exception{

        try {
            String vin=requestWrapper.getParamValue("diVinVal");
            String engineNo=requestWrapper.getParamValue("diEngineNoVal");
            BaseResultSet bs = claimCyclOrderReportMngDao.vinCheckSearch(vin,engineNo);
            bs.setFieldDateFormat("BUY_DATE","yyyy-MM-dd");
            bs.setFieldDateFormat("FACTORY_DATE","yyyy-MM-dd");
            bs.setFieldDateFormat("MAINTENANCE_DATE","yyyy-MM-dd");
            actionContext.setOutData("bs" , bs);
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
            // 服务站订单主键
            String serverOrderId = requestWrapper.getParamValue("orderId");
            // 配送中心订单主键获取
            QuerySet querySet = claimCyclOrderReportMngDao.searchSourceOrderId(serverOrderId);
            String orderId = querySet.getString(1, "ORDER_ID");
            // 订单金额
            String orderAmount = requestWrapper.getParamValue("orderAmount");
            if (orderAmount == null) {
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            }
            if(Double.parseDouble(orderAmount)<=0) {
                throw new Exception("订购金额为零,请维护所需订购的配件后进行提报.");
            } else {
                    //更新配送中心订单明细
                    claimCyclOrderReportMngDao.updateOrderDtl(serverOrderId,orderId);
                    QuerySet qs1 = claimCyclOrderReportMngDao.orderAmountSearch(orderId);
                    orderAmount = qs1.getString(1, "AMOUNT");
                    PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
                    svo.setOrderId(orderId);
                    svo.setOrderAmount(qs1.getString(1, "AMOUNT"));
                    claimCyclOrderReportMngDao.orderAmountUpdate(svo);
                    PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
                    vo.setOrderId(serverOrderId);
                    vo.setApplyDate(Pub.getCurrentDate());
                    vo.setOrderStatus(DicConstant.DDZT_02);
                    vo.setUpdateUser(user.getAccount());
                    vo.setUpdateTime(Pub.getCurrentDate());
                    // 服务站订单更新
                    claimCyclOrderReportMngDao.orderInfoUpdate(vo);
                    vo.setOrderId(orderId);
                    // 配送中心订单更新
                    claimCyclOrderReportMngDao.orderInfoUpdate(vo);
                    //查询配送中心信用额度账户
                    QuerySet qs = claimCyclOrderReportMngDao.queryAccoutInfo(orderId);
                    if(qs.getRowCount()>0){
                    	String accountId  = qs.getString(1, "ACCOUNT_ID");
                        String occpuyAmount  = qs.getString(1, "OCCUPY_AMOUNT");
                        String availableAmount  = qs.getString(1, "AVAILABLE_AMOUNT");
                        //if(Double.parseDouble(availableAmount)>=Double.parseDouble(orderAmount)){
                    	//插入订单账户使用信息
                    	claimCyclOrderReportMngDao.insertOrderPay(orderId, accountId, orderAmount, user);
                        //更新资金账户
                        claimCyclOrderReportMngDao.accountUpdate(accountId, orderAmount);
                        //插入订单资金占用
                        claimCyclOrderReportMngDao.orderFundsOccupy(orderId, accountId, DicConstant.ZJZHLX_04, orderAmount, user);
//                        }else{
//                        	throw new Exception("默认配送中心信用额度可用余额不足，不能提报三包急件订单.");
//                        }
                    }else{
                    	throw new Exception("默认配送中心没有信用额度，不能提报三包急件订单.");
                    }
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
            claimCyclOrderReportMngDao.deleteOrderPart(vo);
            QuerySet qs = claimCyclOrderReportMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            claimCyclOrderReportMngDao.orderAmountUpdate(svo);
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
            claimCyclOrderReportMngDao.updateOrderPart(dtlId,orderId,orderCount,user);
            QuerySet qs = claimCyclOrderReportMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            claimCyclOrderReportMngDao.orderAmountUpdate(svo);
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
            String orgType = user.getOrgDept().getOrgType();
            if (DicConstant.ZZLB_10.equals(orgType) == false&&DicConstant.ZZLB_11.equals(orgType) == false) {
                page.setPageRows(10000);
            }
            BaseResultSet bs = claimCyclOrderReportMngDao.orderPartSearch(page, user, conditions,orderId);
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
                QuerySet querySet = claimCyclOrderReportMngDao.getPlanPrice(partIdArr[i]);
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
                claimCyclOrderReportMngDao.insertSaleOrderDetail(ptBuSaleOrderDtlVO);
            }
            QuerySet qs = claimCyclOrderReportMngDao.orderAmountSearch(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setOrderAmount(qs.getString(1, "AMOUNT"));
            // 修改服务站订单金额
            claimCyclOrderReportMngDao.orderAmountUpdate(vo);
            vo.setOrderId(claimCyclOrderReportMngDao.searchSourceOrderId(orderId).getString(1, "ORDER_ID"));
            // 修改配送中心订单金额
            claimCyclOrderReportMngDao.orderAmountUpdate(vo);
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
            // 查询配送中心
            QuerySet dcQuerySet = claimCyclOrderReportMngDao.searchOrgId(user);
            // 配送中心ID
            String orgId = dcQuerySet.getString(1, "ORG_ID");
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = claimCyclOrderReportMngDao.searchPart(pageManager, user, conditions,orderId,warehouseId,orgId);
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
            // 查询配送中心
            QuerySet dcQuerySet = claimCyclOrderReportMngDao.searchOrgId(user);
            // 配送中心ID
            ptBuSaleOrderVO.setWarehouseId(dcQuerySet.getString(1, "ORG_ID"));
            // 配送中心CODE
            ptBuSaleOrderVO.setWarehouseCode(dcQuerySet.getString(1, "CODE"));
            // 配送中心NAME
            ptBuSaleOrderVO.setWarehouseName(dcQuerySet.getString(1, "ONAME"));
            // 修改服务站订单
            claimCyclOrderReportMngDao.updateClaimCyclOrder(ptBuSaleOrderVO);
            // 查询配送中心订单号
            QuerySet querySet = claimCyclOrderReportMngDao.searchSourceOrderId(ptBuSaleOrderVO.getOrderId());
            // 订单主键
            ptBuSaleOrderVO.setOrderId(querySet.getString(1, "ORDER_ID"));
            // 订单号
//            ptBuSaleOrderVO.setOrderNo(querySet.getString(1, "ORDER_NO"));
            // 供货库ID
            ptBuSaleOrderVO.setWarehouseId(warehouseId);
            // 供货库CODE
            ptBuSaleOrderVO.setWarehouseCode(warehouseCode);
            // 供货库NAME
            ptBuSaleOrderVO.setWarehouseName(warehouseName);
            // 修改配送中心订单
            claimCyclOrderReportMngDao.updateClaimCyclOrder(ptBuSaleOrderVO);
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
    public void searchClaimCyclOrder() throws Exception {

        try {
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String flag = Pub.val(requestWrapper, "flag");
            if ("false".equals(flag)) {
                // 订单未提报,已驳回
                conditions +=" AND B.ORDER_TYPE='" + DicConstant.DDLX_09+ "' AND B.ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+") AND B.ORG_ID = '" + user.getOrgId() + "'";
            } else {
                //已提报
                conditions +=" AND B.ORDER_TYPE='" + DicConstant.DDLX_09+ "' AND B.ORDER_STATUS IN ("+DicConstant.DDZT_02+")";
            }
            
            BaseResultSet bs = claimCyclOrderReportMngDao.searchDirectBusinessOrder(pageManager,conditions,user);
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
    public void dealerSearchClaimCyclOrder() throws Exception {

        try {
            PageManager pageManager = new PageManager();
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            String flag = Pub.val(requestWrapper, "flag");
            if ("false".equals(flag)) {
                // 订单未提报,已驳回
                conditions +=" AND A.ORDER_TYPE='" + DicConstant.DDLX_09+ "' AND A.ORDER_STATUS IN ("+DicConstant.DDZT_01+", "+DicConstant.DDZT_04+") AND A.ORG_ID = '" + user.getOrgId() + "'";
            } else {
                //已提报
                conditions +=" AND A.ORDER_TYPE='" + DicConstant.DDLX_09+ "' AND A.ORDER_STATUS IN ("+DicConstant.DDZT_02+")";
            }
            
            BaseResultSet bs = claimCyclOrderReportMngDao.dealerSearchDirectBusinessOrder(pageManager,conditions,user);
            actionContext.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    /**
     * 三包急件订单提报保存
     *
     * @throws Exception
     */
    public void insertClaimCyclOrder() throws Exception {

        try {
            QuerySet querySet = claimCyclOrderReportMngDao.searchOrgId(user);
            if (querySet.getRowCount() <= 0) {
            	throw new Exception("请联系系统管理员维护默认配送中心.");
            } else {
            	if ("".equals(querySet.getString(1, "CODE"))||querySet.getString(1, "CODE")==null) {
            		throw new Exception("请联系系统管理员维护默认配送中心.");
            	}
            	if ("".equals(querySet.getString(1, "ONAME"))||querySet.getString(1, "ONAME")==null) {
            		throw new Exception("请联系系统管理员维护默认配送中心.");
            	}
            }
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            DBFactory factory = actionContext.getDBFactory();
            // 获取订单首位字符
            String firstLetter = PartOddNumberUtil.getOrderFirstLetter(factory,DicConstant.DDLX_09);
            // 服务站订单主键
            String serverOrderId = SequenceUtil.getCommonSerivalNumber(actionContext.getDBFactory());;
            // 订单编号获取(服务站)
            String serverOrderNo = PartOddNumberUtil.getSaleOrderNo(factory, firstLetter,user.getOrgCode());
            // 订单编号获取(配送中心)
            String dcOrderNo = PartOddNumberUtil.getSaleOrderNo(factory, firstLetter,querySet.getString(1, "CODE"));
            ptBuSaleOrderVO.setValue(hashMap);
            // 三包急件订单编号设置
            ptBuSaleOrderVO.setOrderNo(dcOrderNo);
            // 订单状态(未提报)
            ptBuSaleOrderVO.setOrderStatus(DicConstant.DDZT_01);
            // 渠道组织ID
            ptBuSaleOrderVO.setOrgId(querySet.getString(1, "ORG_ID"));
            // 渠道代码
            ptBuSaleOrderVO.setOrgCode(querySet.getString(1, "CODE"));
            // 渠道名称
            ptBuSaleOrderVO.setOrgName(querySet.getString(1, "ONAME"));
            // 是否使用信用额度(否)
            ptBuSaleOrderVO.setIfCredit(DicConstant.SF_01);
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
            ptBuSaleOrderVO.setDirSourceOrderId(serverOrderId);
            ptBuSaleOrderVO.setDirSourceOrderNo(serverOrderNo);
            // 生成配送中心订单
            claimCyclOrderReportMngDao.insertClaimCyclOrder(ptBuSaleOrderVO);

            ptBuSaleOrderVO.setOrderId(serverOrderId);
            ptBuSaleOrderVO.setOrderNo(serverOrderNo);
            ptBuSaleOrderVO.setDirSourceOrderId("");
            ptBuSaleOrderVO.setDirSourceOrderNo("");
            // 渠道组织ID
            ptBuSaleOrderVO.setOrgId(user.getOrgId());
            // 渠道代码
            ptBuSaleOrderVO.setOrgCode(user.getOrgCode());
            // 渠道名称
            ptBuSaleOrderVO.setOrgName(user.getOrgDept().getOName());
            // 
            ptBuSaleOrderVO.setWarehouseId(querySet.getString(1, "ORG_ID"));
            // 
            ptBuSaleOrderVO.setWarehouseCode(querySet.getString(1, "CODE"));
            // 
            ptBuSaleOrderVO.setWarehouseName(querySet.getString(1, "ONAME"));
            // 是否渠道内订单(是)
            ptBuSaleOrderVO.setIfChanelOrder(DicConstant.SF_01);
            // 服务站订单
            claimCyclOrderReportMngDao.insertClaimCyclOrder(ptBuSaleOrderVO);
            
            //代办提醒 start
            QuerySet qsRole=claimCyclOrderReportMngDao.getRole();
            if(qsRole.getRowCount() > 0){
                for (int i=0;i<qsRole.getRowCount() ;i++){
                    String roleId=qsRole.getString(i+1,"ROLE_ID");
                    AlertInfoVO infoVO=new AlertInfoVO();
                    infoVO.setAlertTitle(user.getOrgDept().getOName()+"有一条销售订单申请！");//标题
                    infoVO.setDesr("订单号:"+dcOrderNo);//提醒内容
                    infoVO.setOverrun("0");//提醒周期
                    infoVO.setAlertRole(roleId);//角色
                    infoVO.setBusType(DicConstant.YWLX_01);//业务类型（配件、售后）
                    infoVO.setBusPk(serverOrderId);//业务主键
                    infoVO.setAlertType(DicConstant.TXLX_01);//提醒类型
                    infoVO.setCreateUser(user.getAccount());//发送人（当前登录人）
                    infoVO.setCreateOrgid(user.getOrgId());//发送部门（当前登录部门）
                    AlertManager.alertInsert(actionContext.getDBFactory(),infoVO);
                }
            }
            //end
            
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
            cvo.setRemarks(hashMap.get("CHECK_REMARKS"));
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
            claimCyclOrderReportMngDao.partOrderCheckLogInsert(cvo);

            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setValue(hashMap);
            vo.setOrderStatus(DicConstant.DDZT_04);
            vo.setIfDelayOrder(DicConstant.SF_02);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            claimCyclOrderReportMngDao.partOrderCheckApproveBack(vo);
            
            PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
            ptBuSaleOrderVO.setValue(hashMap);
            QuerySet querySet = claimCyclOrderReportMngDao.sourceOrderId(vo.getOrderId());
            // 删除资金使用记录
            claimCyclOrderReportMngDao.deleteOrderPay(vo.getOrderId(), user);
            // 释放占用资金
            claimCyclOrderReportMngDao.orderReleaseFreez1(vo.getOrderId(), user);
            
            ptBuSaleOrderVO.setOrderId(querySet.getString(1, "DIR_SOURCE_ORDER_ID"));
            ptBuSaleOrderVO.setOrderStatus(DicConstant.DDZT_04);
            ptBuSaleOrderVO.setIfDelayOrder(DicConstant.SF_02);
            ptBuSaleOrderVO.setUpdateUser(user.getAccount());
            ptBuSaleOrderVO.setUpdateTime(Pub.getCurrentDate());
            claimCyclOrderReportMngDao.partOrderCheckApproveBack(ptBuSaleOrderVO);
            
            
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
            QuerySet querySet1 = claimCyclOrderReportMngDao.partCheck(orderId);
            int count = querySet1.getRowCount();
            if (count > 0) {
            	String partCOde = "";
            	for (int j=0;j<count;j++) {
            		partCOde+=querySet1.getString(j+1, "PART_CODE");
            	}
            	// 订单是否有军品件
            	throw new Exception("该订单有军品库配件，不能审核."+partCOde);
            }
            QuerySet querySet = claimCyclOrderReportMngDao.sourceOrderId(orderId);
            String sourceOrderIdString = querySet.getString(1, "DIR_SOURCE_ORDER_ID");
            String sourceOrderNoString = querySet.getString(1, "DIR_SOURCE_ORDER_NO");
            if ("0".equals(sourceOrderIdString)) {
            	throw new Exception("请联系管理员,维护原单.");
            }
            // 审核数量
            String auditCounts = hashMap.get("AUDITCOUNTS");
            String[] dtlId = dtlIds.split(",");
            String[] auditCount = auditCounts.split(",");
//            QuerySet querySet0 = claimCyclOrderReportMngDao.searchOrderDtl(sourceOrderIdString);
            int auditAllCount=0;
            for(int i=0;i<dtlId.length;i++){
            	auditAllCount = auditAllCount+Integer.parseInt(auditCount[i]);
                // 更新订单明细审核数量(配送中心)
                claimCyclOrderReportMngDao.orderAuditCountUpdate(auditCount[i], dtlId[i], orderId, user);
                // 更新订单明细审核数量(服务站)
                claimCyclOrderReportMngDao.orderAuditCountUpdateServer(auditCount[i], dtlId[i], sourceOrderIdString, user);
            }

            // 更新服务站订单状态
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setValue(hashMap);
//            // 订单状态
//            vo.setOrderStatus(DicConstant.DDZT_03);
//            // 发运状态
//            vo.setShipStatus(DicConstant.DDFYZT_01);
            if(auditAllCount>0){
	            vo.setOrderStatus(DicConstant.DDZT_03);
	            vo.setShipStatus(DicConstant.DDFYZT_01);
            }else{
            	 vo.setOrderStatus(DicConstant.DDZT_06);
 	             vo.setCloseDate(Pub.getCurrentDate());
            }
            // 更新人
            vo.setUpdateUser(user.getAccount());
            // 更新时间
            vo.setUpdateTime(Pub.getCurrentDate());
            // 更新订单状态
            claimCyclOrderReportMngDao.partOrderCheckPass(vo);
            
            // 配送中心订单状态
            QuerySet qs = claimCyclOrderReportMngDao.searchCheckUser(orgId);
            vo.setOrderId("");
            vo.setOrderId(sourceOrderIdString);
            // 更新订单状态
            claimCyclOrderReportMngDao.partOrderCheckPass(vo);

            PtBuSaleOrderCheckVO cvo = new PtBuSaleOrderCheckVO();
            cvo.setValue(hashMap);
            cvo.setRemarks(hashMap.get("CHECK_REMARKS"));
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
            claimCyclOrderReportMngDao.partOrderCheckLogInsert(cvo);
            cvo.setCheckId("");
            cvo.setOrderId(sourceOrderIdString);
            //插入审核日志(服务站)
            claimCyclOrderReportMngDao.partOrderCheckLogInsert(cvo);
            //订单释放资金占用，重新占用
            claimCyclOrderReportMngDao.orderReleaseFreez2(orderId,user);
            //订单占用库存
            claimCyclOrderReportMngDao.orderFreez(orderId);
            if("2".equals(flag)){
                //生成延期订单
                claimCyclOrderReportMngDao.delayOrderInfoInsert(orderId,user,sourceOrderIdString);
            }
            actionContext.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }
    
    public void printPdf() throws Exception{
		RequestWrapper request = actionContext.getRequest();
		ResponseWrapper response =  actionContext.getResponse();
		User user = (User) actionContext.getSession().get(Globals.USER_KEY);
		String ORDER_ID = Pub.val(request, "ORDER_ID");
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
        //设置PDF页面大小、左、右、上和下页边距
		/*Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 38.849998F, 54.849998F, 36.849998F);
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();*/
        Document document = new Document(); 
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		//设置table列宽
		float[] widths = {30F, 90F, 120F, 30F, 55F,55F,40F,40F,60F};
		PdfPTable table = new PdfPTable(widths);
		table.setLockedWidth(true);
		table.setTotalWidth(widths);
		//查询发料单主信息
		QuerySet info = claimCyclOrderReportMngDao.queryOrderInfo(user, ORDER_ID);
		String custormName = info.getString(1, "CUSTORM_NAME");
		String orderNo = info.getString(1, "ORDER_NO");
		String amount = info.getString(1, "ORDER_AMOUNT");
		String printDate = info.getString(1, "PRINT_DATE");
		String linkMan = info.getString(1, "LINK_MAN");
		String deliveryAddr = info.getString(1, "DELIVERY_ADDR");
		String phone = info.getString(1, "PHONE");
		String [] printDates =printDate.split("-");
		//查询发料单明细信息
		QuerySet qs = claimCyclOrderReportMngDao.queryOrderDtl(user,ORDER_ID);
		if(qs.getRowCount()>0){
			//按10条分页，取余算共几页
			int rows = qs.getRowCount();
			int pageRow = rows/40;
			int yu = rows%40;
			if(yu>0){
				pageRow = pageRow+1;
			}
			int pageNum = 1;
			for (int i = 0; i < qs.getRowCount(); i++) {
				//按10条分页，重新设置表头
				if(i%40==0){
					PdfPCell cellA0 = new PdfPCell(new Paragraph("客  户："+custormName,chineseFont));
					cellA0.setNoWrap(true);
					cellA0.setBorder(0);
					cellA0.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellA0.setColspan(3);
					table.addCell(cellA0);
					PdfPCell cellA1 = new PdfPCell(new Paragraph("",chineseFont));
					cellA1.setNoWrap(true);
					cellA1.setBorder(0);
					cellA1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellA1.setColspan(3);
					table.addCell(cellA1);
					PdfPCell cellA2 = new PdfPCell(new Paragraph("编号："+orderNo,chineseFont));
					cellA2.setNoWrap(true);
					cellA2.setBorder(0);
					cellA2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellA2.setColspan(3);
					table.addCell(cellA2);
					
					PdfPCell cellC00 = new PdfPCell(new Paragraph("收货人："+linkMan,chineseFont));
					cellC00.setNoWrap(true);
					cellC00.setBorder(0);
					cellC00.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellC00.setColspan(2);
					table.addCell(cellC00);
					PdfPCell cellC01 = new PdfPCell(new Paragraph("地址："+deliveryAddr,chineseFont));
					cellC01.setNoWrap(true);
					cellC01.setBorder(0);
					cellC01.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellC01.setColspan(4);
					table.addCell(cellC01);
					PdfPCell cellC02 = new PdfPCell(new Paragraph("联系电话："+phone,chineseFont));
					cellC02.setNoWrap(true);
					cellC02.setBorder(0);
					cellC02.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellC02.setColspan(3);
					table.addCell(cellC02);
					
					
					PdfPCell cellB0 = new PdfPCell(new Paragraph("订单总金额："+amount,chineseFont));
					cellB0.setNoWrap(true);
					cellB0.setBorder(0);
					cellB0.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB0.setColspan(2);
					table.addCell(cellB0);
					PdfPCell cellB1 = new PdfPCell(new Paragraph("",chineseFont));
					cellB1.setNoWrap(true);
					cellB1.setBorder(0);
					cellB1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB1.setColspan(2);
					table.addCell(cellB1);
					PdfPCell cellB2 = new PdfPCell(new Paragraph("打印日期:"+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
					cellB2.setNoWrap(true);
					cellB2.setBorder(0);
					cellB2.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB2.setColspan(2);
					table.addCell(cellB2);
					PdfPCell cellB3 = new PdfPCell(new Paragraph("第"+pageNum+"页，共"+pageRow+"页",chineseFont));
					cellB3.setNoWrap(true);
					cellB3.setBorder(0);
					cellB3.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellB3.setColspan(4);
					table.addCell(cellB3);
					
					PdfPCell cell0 = new PdfPCell(new Paragraph("序号",chineseFont));
					cell0.setFixedHeight(18.0F);
					cell0.setBorderWidth(0.5F);
					cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell0);
					PdfPCell cell1 = new PdfPCell(new Paragraph("配件代码", chineseFont));
					cell1.setBorderWidth(0.5F);
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell1);
					PdfPCell cell2 = new PdfPCell(new Paragraph("配件名称", chineseFont));
					cell2.setBorderWidth(0.5F);
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell2);
					PdfPCell cell3 = new PdfPCell(new Paragraph("单位", chineseFont));
					cell3.setBorderWidth(0.5F);
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell3);
					PdfPCell cell4 = new PdfPCell(new Paragraph("最小包装", chineseFont));
					cell4.setBorderWidth(0.5F);
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell4);
					PdfPCell cell5 = new PdfPCell(new Paragraph("订单数量", chineseFont));
					cell5.setBorderWidth(0.5F);
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell5);
					PdfPCell cell6 = new PdfPCell(new Paragraph("实发数量", chineseFont));
					cell6.setBorderWidth(0.5F);
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell6);
					PdfPCell cell7 = new PdfPCell(new Paragraph("订单金额", chineseFont));
					cell7.setBorderWidth(0.5F);
					cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell7);
					PdfPCell cell8 = new PdfPCell(new Paragraph("备注", chineseFont));
					cell8.setBorderWidth(0.5F);
					cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell8);
					pageNum=pageNum+1;
				}
				
				PdfPCell cella = new PdfPCell(new Paragraph(String.valueOf(i+1),chineseFont));
				cella.setFixedHeight(18.0F);
				cella.setBorderWidth(0.5F);
				cella.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cella);
				
				PdfPCell cellb = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_CODE"), chineseFont));
				cellb.setBorderWidth(0.5F);
				cellb.setNoWrap(false);
				table.addCell(cellb);
				
				PdfPCell cellc = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NAME"), chineseFont));
				cellc.setBorderWidth(0.5F);
				cellc.setNoWrap(false);
				table.addCell(cellc);
				
				PdfPCell celld = new PdfPCell(new Paragraph(qs.getString(i+1, "UNIT"), chineseFont));
				celld.setBorderWidth(0.5F);
				celld.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celld);
				
				PdfPCell celle = new PdfPCell(new Paragraph(qs.getString(i+1, "MIN_PACK")+"/"+qs.getString(i+1, "UNIT"), chineseFont));
				celle.setBorderWidth(0.5F);
				celle.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celle);
				
				PdfPCell cellf = new PdfPCell(new Paragraph(qs.getString(i+1, "ORDER_COUNT"), chineseFont));
				cellf.setBorderWidth(0.5F);
				table.addCell(cellf);
				
				PdfPCell cellg = new PdfPCell(new Paragraph("", chineseFont));
				cellg.setBorderWidth(0.5F);
				cellg.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellg);
				PdfPCell cellh = new PdfPCell(new Paragraph(qs.getString(i+1, "AMOUNT"), chineseFont));
				cellh.setBorderWidth(0.5F);
				table.addCell(cellh);
				PdfPCell celli = new PdfPCell(new Paragraph(qs.getString(i+1, "REMARKS"), chineseFont));
				celli.setBorderWidth(0.5F);
				table.addCell(celli);
			}
			document.add(table);
			document.close();
			response.getHttpResponse().reset();
			response.setContentType("application/pdf");
			//设置保存的名称
			response.addHeader("Content-Disposition","inline;filename=ceshi.pdf"); 
			response.setContentLength(ba.size());
			try {
				ServletOutputStream out = response.getHttpResponse().getOutputStream();
				ba.writeTo(out);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
