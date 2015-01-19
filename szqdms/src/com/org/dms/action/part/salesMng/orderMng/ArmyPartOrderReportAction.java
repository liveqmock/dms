package com.org.dms.action.part.salesMng.orderMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.orderMng.ArmyPartOrderReportDao;
import com.org.dms.vo.part.PtBuSaleOrderDtlExtendVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderPayVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class ArmyPartOrderReportAction {
	private Logger logger = com.org.framework.log.log.getLogger("ArmyPartOrderReportAction");
	private ActionContext atx = ActionContext.getContext();
	private ArmyPartOrderReportDao dao = ArmyPartOrderReportDao.getInstance(atx);
	/**
	 * 
	 * @date()2014年9月2日下午3:04:08
	 * @author Administrator
	 * @to_do:军品订单查询
	 * @throws Exception
	 */
	public void partOrderSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.partOrderSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	/**
	 * 
	 * @date()2014年9月4日下午3:39:21
	 * @author Administrator
	 * @to_do:信用额度余额查询
	 * @throws Exception
	 */
	public void accountSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.accountSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
        }
	}
	/**
	 * 
	 * @date()2014年10月11日上午9:45:39
	 * @author Administrator
	 * @to_do:查询军品库信息
	 * @throws Exception
	 */
	public void getWarehouse() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.getWarehouse(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
        }
	}
	/**
	 * 
	 * @date()2014年9月5日上午10:01:43
	 * @author Administrator
	 * @to_do:军品订单新增
	 * @throws Exception
	 */
	public void orderInfoInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String firstLetter = "JP";
            String orderNo = PartOddNumberUtil.getSaleOrderNo(atx.getDBFactory(),firstLetter ,user.getOrgCode());
            QuerySet qs = dao.orderInfoSaveSearch(user);
            if(qs.getRowCount()>0){
                throw new Exception("该订单类型有未提报的订单，不能新增订单.");
            }
            vo.setValue(hm);
            vo.setOrderNo(orderNo);
            vo.setOrgId(user.getOrgId());
            vo.setOrgCode(user.getOrgCode());
            vo.setOrgName(user.getOrgDept().getOName());
            vo.setOrderType(DicConstant.DDLX_08);
            vo.setOrderStatus(DicConstant.DDZT_01);
            vo.setIfChanelOrder(DicConstant.SF_02);
            vo.setIfCredit(DicConstant.SF_01);
            vo.setIfDelayOrder(DicConstant.SF_02);
            vo.setCompanyId(user.getCompanyId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
            dao.orderInfoInsert(vo);
            atx.setOutMsg(vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 * 
	 * @date()2014年9月5日上午10:03:12
	 * @author Administrator
	 * @to_do:军品订单修改
	 * @throws Exception
	 */
	public void orderInfoUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderInfoUpdate(vo);
            atx.setOutMsg(vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 * 
	 * @date()2014年9月5日上午10:23:07
	 * @author Administrator
	 * @to_do:订单配件查询
	 * @throws Exception
	 */
    public void orderPartSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orderId = request.getParamValue("orderId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderPartSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	/**
	 * 
	 * @date()2014年9月5日上午10:15:51
	 * @author Administrator
	 * @to_do:军品订单提报
	 * @throws Exception
	 */
	public void partOrderReport() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            String orderId = request.getParamValue("orderId");
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setApplyDate(Pub.getCurrentDate());
        	vo.setOrderStatus(DicConstant.DDZT_02);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderInfoUpdate(vo);
            
            
            /**
             * 新增orderPay信息
             */
            QuerySet qs = dao.orderAmountSearch(orderId);
            QuerySet getPay = dao.getPayInfo(user);
            PtBuSaleOrderPayVO p_vo = new PtBuSaleOrderPayVO();
            p_vo.setOrderId(orderId);
            p_vo.setAccountId(getPay.getString(1, "ACCOUNT_ID"));
            p_vo.setAccountType(getPay.getString(1, "ACCOUNT_TYPE"));
            p_vo.setPayAmount(qs.getString(1, "AMOUNT"));
            p_vo.setCreateUser(user.getAccount());
            p_vo.setCreateTime(Pub.getCurrentDate());
            dao.orderFundsInsert(p_vo);
            /**
             * 更新资金占用
             */
            //更新资金账户
			dao.accountUpdate(getPay.getString(1, "ACCOUNT_ID"), qs.getString(1, "AMOUNT"));
			//插入订单资金占用
			dao.orderFundsOccupy(orderId, getPay.getString(1, "ACCOUNT_ID"), getPay.getString(1, "ACCOUNT_TYPE"), qs.getString(1, "AMOUNT"), user);
            atx.setOutMsg("", "提报成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	public void orderFundsSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orderId = request.getParamValue("orderId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderFundsSearch(page, user, conditions,orderId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	
	public void searchPart() throws Exception {

        try {
            // 定义查询分页对象
        	RequestWrapper request = atx.getRequest();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            PageManager page = new PageManager();
            String orderId = request.getParamValue("orderId");
            String warehouseId = request.getParamValue("warehouseId");
            String conditions = RequestUtil.getConditionsWhere(request, page);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchPart(page, user, conditions,orderId,warehouseId);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	/**
	 * 
	 * @date()2014年9月11日上午10:47:16
	 * @author Administrator
	 * @to_do:军品销售订单配件新增
	 * @throws Exception
	 */
	public void addPartSubmit() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String orderId = hm.get("ORDERID");
            String partIds = hm.get("PARTIDS");
            String partCodes = hm.get("PARTCODES");
            String partNames = hm.get("PARTNAMES");
            String supplierIds = hm.get("SUPPLIERIDS");
            String supplierCodes = hm.get("SUPPLIERCODES");
            String supplierNames = hm.get("SUPPLIERNAMES");
            String orderCounts = hm.get("ORDERCOUNTS");
            String salePrices = hm.get("SALEPRICES");
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            String[] supplierIdArr = supplierIds.split(",");
            String[] supplierCodeArr = supplierCodes.split(",");
            String[] supplierNameArr = supplierNames.split(",");
            String[] orderCountArr = orderCounts.split(",");
            String[] salePriceArr = salePrices.split(",");
            for (int i = 0; i < partIdArr.length; i++) {
                PtBuSaleOrderDtlVO pbsod = new PtBuSaleOrderDtlVO();
                QuerySet querySet = dao.getPlanPrice(partIdArr[i]);
                pbsod.setPlanPrice(querySet.getString(1, "PLAN_PRICE"));
                pbsod.setOrderId(orderId);
                pbsod.setPartId(partIdArr[i]);
                pbsod.setPartCode(partCodeArr[i]);
                pbsod.setPartName(partNameArr[i]);
                if("9XXXXXX".equals(supplierCodeArr[i])){
                	pbsod.setIfSupplier(DicConstant.SF_02);
                	pbsod.setSupplierId(supplierIdArr[i]);
                	pbsod.setSupplierCode(supplierCodeArr[i]);
                	pbsod.setSupplierName(supplierNameArr[i]);
                }else{
                	pbsod.setIfSupplier(DicConstant.SF_01);
                	pbsod.setSupplierId(supplierIdArr[i]);
                	pbsod.setSupplierCode(supplierCodeArr[i]);
                	pbsod.setSupplierName(supplierNameArr[i]);
                }
                pbsod.setOrderCount(orderCountArr[i]);
                pbsod.setUnitPrice(salePriceArr[i]);
                pbsod.setAmount(String.valueOf(Integer.parseInt(orderCountArr[i])*Double.parseDouble(salePriceArr[i])));
                pbsod.setCreateUser(user.getAccount());
                pbsod.setCreateTime(Pub.getCurrentDate());
                dao.orderPartInfoInsert(pbsod);
            }
            QuerySet qs = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            vo.setOrderAmount(qs.getString(1, "AMOUNT"));
            dao.orderAmountUpdate(vo);
            
            atx.setOutMsg(vo.getXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	
	public void partOrderDelete() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String orderId = request.getParamValue("orderId");
            dao.orderPartInfoDelete(orderId);
            dao.orderPayInfoDelete(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderId(orderId);
            dao.orderInfoDelete(vo);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	public void orderPartAddSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String orgType = user.getOrgDept().getOrgType();
        String orderId = request.getParamValue("orderId");
        String warehouseId = request.getParamValue("warehouseId");
        //String supplierId = request.getParamValue("supplierId");
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
    		 BaseResultSet bs = dao.orderPartAddSearch(page, user, conditions,orgType,orderId,warehouseId);
             atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	public void orderPartDelete() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String dtlId = request.getParamValue("dtlId");
            String orderId = request.getParamValue("orderId");
            PtBuSaleOrderDtlVO vo = new PtBuSaleOrderDtlVO();
            vo.setDtlId(dtlId);
            dao.deleteOrderPart(vo);
            QuerySet qs = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            dao.orderAmountUpdate(svo);
            atx.setOutMsg(svo.getXml(), "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	public void orderPartCountSave() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        // 获取页面信息
        HashMap<String, String> hashMap = RequestUtil.getValues(request);
        try {
            String dtlId = hashMap.get("DTL_ID");
            String orderId = hashMap.get("ORDER_ID");
            String orderCount = hashMap.get("ORDER_COUNT");
            String remarks = hashMap.get("REMARKS");
            String unitPrice = hashMap.get("UNIT_PRICE");
            dao.updateOrderPart(dtlId,orderId,orderCount,remarks,user);
            QuerySet qs = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO svo = new PtBuSaleOrderVO();
            svo.setOrderId(orderId);
            svo.setRemarks(remarks);
            svo.setOrderAmount(qs.getString(1, "AMOUNT"));
            dao.orderAmountUpdate(svo);
            PtBuSaleOrderDtlExtendVO ptExtendVO = new PtBuSaleOrderDtlExtendVO();
            ptExtendVO.setDtlId(dtlId);
            ptExtendVO.setRemarks(remarks);
            ptExtendVO.setOrderCount(orderCount);
            ptExtendVO.setOrderAmount(qs.getString(1, "AMOUNT"));
            Double price = (Double.valueOf(unitPrice)*Double.valueOf(orderCount));
            ptExtendVO.setAmount(String.format("%.2f",price));
            ptExtendVO.setUnitPrice(String.format("%.2f",Double.valueOf(unitPrice)));
            atx.setOutMsg(ptExtendVO.getRowXml(), "");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	public void searchImportPart()throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs= dao.searchImportPart(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
   }
	public void insertImportPart()throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId = request.getParamValue("orderId");
        String tmpNo = request.getParamValue("tmpNo");
        String flag = request.getParamValue("flag");
        String sql = "";
        if (!"".equals(tmpNo)&&tmpNo!=null) {
    		sql = " AND A.ROW_NO NOT IN ("+tmpNo+") ";
    	}
        QuerySet qs = dao.partOrderInfoSearch(orderId);
        try {
            if(qs.getRowCount()>0){
                String orderType = qs.getString(1,"ORDER_TYPE");
                String promId = "";
                if(DicConstant.DDLX_06.equals(orderType)){
                    promId = qs.getString(1, "PROM_ID");
                }
                dao.insertPartOrderDetail(user,orderId,promId,sql,flag);
            }


            // 修改订单总金额
            QuerySet qs1 = dao.orderAmountSearch(orderId);
            PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
            vo.setOrderAmount(qs1.getString(1, "AMOUNT"));

//            if ("2".equals(flag)) {
//           	 // 技术升级订单导入
//           	 QuerySet querySet = dao.getserverOrderId(orderId);
//           	 if (querySet.getRowCount() > 0) {
//           		 String orderId1 = querySet.getString(1, "DIR_SOURCE_ORDER_ID");
//           		 if (!orderId1.equals("0")) {
//           			 vo.setOrderId(orderId1);
//           			 dao.orderAmountUpdate(vo);
//           		 }
//           	 }
//            }

            vo.setOrderId(orderId);
            dao.orderAmountUpdate(vo);
            atx.setOutMsg(vo.getXml(), "操作成功");
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
   }
	
	public void expData()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
        	ResponseWrapper responseWrapper= atx.getResponse();
        	User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("COUNT");
            hBean.setTitle("订购数量");
            header.add(2,hBean);

            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "CWSJDC", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	//partOrderDtlDelete
	public void partOrderDtlDelete()throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            String orderId = request.getParamValue("orderId");
            dao.partOrderDtlDelete(orderId);
            atx.setOutMsg("", "删除成功!");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}
