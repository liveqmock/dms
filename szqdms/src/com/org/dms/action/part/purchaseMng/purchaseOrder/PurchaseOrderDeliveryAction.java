/**
 * 
 */
package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderDeliveryDao;
import com.org.dms.vo.part.PtBuIssueOrderDtlVO;
import com.org.dms.vo.part.PtBuIssueOrderVO;
import com.org.dms.vo.part.PtBuPchOrderShippingVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuStockInContinualVO;
import com.org.dms.vo.part.PtBuStockInDtlVO;
import com.org.dms.vo.part.PtBuStockInVO;
import com.org.dms.vo.part.PtBuStockOutContinualVO;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
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
 * @author Administrator
 *
 */
public class PurchaseOrderDeliveryAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderDeliveryDao dao = PurchaseOrderDeliveryDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年7月17日下午2:54:56
	     * @author Administrator
	     * @to_do:订单发运查询
	     * @throws Exception
	     */
	    public void orderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.orderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年7月17日下午2:56:40
	     * @author Administrator
	     * @to_do:采购订单配件查询
	     * @throws Exception
	     */
	    public void orderPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				page.setPageRows(99999);
				String SPLIT_ID = Pub.val(request, "SPLIT_ID");
				BaseResultSet bs = dao.orderPartSearch(page,user,SPLIT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年7月18日下午2:57:55
	     * @author Administrator
	     * @to_do:采购订单发货
	     * @throws Exception
	     */
	    public void shippingPart() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String SPLIT_ID = hm.get("SPLITID");
	            String DETAIL_IDS = hm.get("DETAILIDS");//采购订单ID
	            String SHIP_COUNTS = hm.get("SHIPCOUNTS");//配件代码
	            String HADSHIPCOUNTS = hm.get("HADSHIPCOUNTS");//备注
	            String PBNOS = hm.get("PBNOS");
	            String[] DETAIL_ID = DETAIL_IDS.split(",");
	            String[] SHIP_COUNT = SHIP_COUNTS.split(",");
	            String[] HAD_SHIP_COUNT = HADSHIPCOUNTS.split(",");
	            String[] PBNO = PBNOS.split(",");
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            java.util.Date date = Pub.getCurrentDate();
	            df.format(date);
	            int counts = 0;
	            int sls = 0;
	            for (int i = 0; i < DETAIL_ID.length; i++) {
	            	int count = Integer.parseInt(SHIP_COUNT[i])+Integer.parseInt(HAD_SHIP_COUNT[i]);
	            	PtBuPchOrderSplitDtlVO c_vo = new PtBuPchOrderSplitDtlVO();
	            	c_vo.setDetailId(DETAIL_ID[i]);
	            	c_vo.setShipCount(String.valueOf(count));
	            	if(!"".equals(PBNOS)&&null!=PBNOS){
	            		if(!"myNull".equals(PBNO[i])){
		            		c_vo.setDistributionNo(PBNO[i]);
		            	}
	            	}
	            	
	            	dao.shippingPart(c_vo);
	            	counts = counts+count;
	            	QuerySet getPchPrice = dao.getPchPrice(DETAIL_ID[i]);
	            	String pchPrice = getPchPrice.getString(1, "PCH_PRICE");
	            	float ShippingMoney = Integer.parseInt(SHIP_COUNT[i])*Float.parseFloat(pchPrice);
	            	PtBuPchOrderShippingVO s_vo = new PtBuPchOrderShippingVO();
	            	s_vo.setDetailId(DETAIL_ID[i]);
	            	s_vo.setPurchaseId(SPLIT_ID);
	            	s_vo.setShippingAmount(SHIP_COUNT[i]);
	            	s_vo.setShippingMoney(String.valueOf(ShippingMoney));
	            	s_vo.setShipDate(date);
	            	dao.inserShipLog(s_vo);
	            	
	            }
	            QuerySet qs = dao.queryifFirst(SPLIT_ID);
	            
	            PtBuPchOrderSplitVO s_vo = new PtBuPchOrderSplitVO();
	            s_vo.setSplitId(SPLIT_ID);
	            if(qs.getRowCount()>0){
	            	String shipCount = qs.getString(1, "SHIP_COUNT");
	            	String shipTimes = qs.getString(1, "SHIP_TIMES");
	            	int sl = Integer.parseInt(shipCount);
	            	int cs = Integer.parseInt(shipTimes)+1;
	            	sls=counts+sl;
	            	s_vo.setShipCount(String.valueOf(sls));
	            	s_vo.setShipTimes(String.valueOf(cs));
	            	s_vo.setLastShipDate(date);
	            }else{
	            	s_vo.setShipCount(String.valueOf(counts));
	            	s_vo.setFirstShipDate(Pub.getCurrentDate());
	            	s_vo.setLastShipDate(date);
	            	s_vo.setShipTimes("1");
	            }
	            if(counts==0){
	            	s_vo.setCloseDate(Pub.getCurrentDate());
	            	s_vo.setOrderStatus(DicConstant.CGDDZT_05);
	            }else{
	            	 s_vo.setOrderStatus(DicConstant.CGDDZT_04);
	            }
	            dao.updateShipping(s_vo);
	            
	            QuerySet getType = dao.getOrderType(SPLIT_ID);
	            String type = getType.getString(1, 1);
	            //直发发货为零时，关单。
	            if(type.equals(DicConstant.CGDDLX_05)&&counts==0){
	            	QuerySet getSale = dao.getSale(SPLIT_ID);
	            	PtBuSaleOrderVO saleOrderVO = new PtBuSaleOrderVO();
		            saleOrderVO.setOrderId(getSale.getString(1,"ORDER_ID"));
		            saleOrderVO.setOrderStatus(DicConstant.DDZT_06);
		            dao.updateSaleOrder(saleOrderVO);
		            //释放冻结资金
		            dao.orderReleaseFreez(getSale.getString(1,"ORDER_ID"), user);
	            }
	            if(type.equals(DicConstant.CGDDLX_05)&&counts>0){
	            	
            		
	            	QuerySet checkIfSQ = dao.checkIfSQ(SPLIT_ID);
	            	if(checkIfSQ.getRowCount()>0){
	            		/**
			             * 获取原直发销售订单信息
			             */
	            		
		            		for(int k=0;k<DETAIL_ID.length;k++){
		            			dao.updatePchOrderAccept(SPLIT_ID,DETAIL_ID[k],SHIP_COUNT[k],user);
		            		}
				            QuerySet getSale = dao.getSale(SPLIT_ID);
				            String SALE_ID = getSale.getString(1, "ORDER_ID");
				            String SALE_NO = getSale.getString(1, "ORDER_NO");
				            String WAREHOUSE_ID = getSale.getString(1, "WAREHOUSE_ID");
				            String WAREHOUSE_CODE = getSale.getString(1, "WAREHOUSE_CODE");
				            String WAREHOUSE_NAME = getSale.getString(1, "WAREHOUSE_NAME");
				            String ORG_CODE = getSale.getString(1, "ORG_CODE");
				            String ORG_NAME = getSale.getString(1, "ORG_NAME");
				            String ORG_ID = getSale.getString(1, "ORG_ID");
				            String SUPPLIER_ID = getSale.getString(1, "SUPPLIER_ID");
				            String SUPPLIER_CODE = getSale.getString(1, "SUPPLIER_CODE");
				            String SUPPLIER_NAME = getSale.getString(1, "SUPPLIER_NAME");
				            String SPLIT_NO = getSale.getString(1, "SPLIT_NO");
				            String APPLY_USER = getSale.getString(1, "APPLY_USER");
				            /**
				             * 生成厂端入库单
				             */
				            String inId = dao.getId();
				            String inNo = dao.createInBillNo(SPLIT_ID, SPLIT_NO);
				            PtBuStockInVO i_vo = new PtBuStockInVO();
				            i_vo.setInId(inId);
				            i_vo.setInNo(inNo);
				            i_vo.setWarehouseId(WAREHOUSE_ID);
				            i_vo.setWarehouseCode(WAREHOUSE_CODE);
				            i_vo.setWarehouseName(WAREHOUSE_NAME);
				            i_vo.setSupplierId(SUPPLIER_ID);
				            i_vo.setSupplierCode(SUPPLIER_CODE);
				            i_vo.setSupplierName(SUPPLIER_NAME);
				            i_vo.setBuyer(APPLY_USER);
				            i_vo.setOrderId(SPLIT_ID);
				            i_vo.setOrderNo(SPLIT_NO);
				            i_vo.setOrgCode(ORG_CODE);
				            i_vo.setOrgName(ORG_NAME);
				            i_vo.setInType(DicConstant.RKLX_01);
				            i_vo.setInStatus(DicConstant.RKDZT_02);
				            i_vo.setPrintStatus(DicConstant.DYZT_01);
				            i_vo.setInDate(Pub.getCurrentDate());
				            i_vo.setCompanyId(user.getCompanyId());
				            i_vo.setOemCompanyId(user.getOemCompanyId());
				            i_vo.setCreateUser(user.getAccount());
				            i_vo.setCreateTime(Pub.getCurrentDate());
				            i_vo.setStatus(DicConstant.YXBS_01);
				            //通过dao，执行插入
				            dao.insertInBill(i_vo);
				            
				            
				            /**
				             * 生成出库单
				             */
				            PtBuStockOutVO o_vo = new PtBuStockOutVO();
				            String outId = dao.getId();
				            String outNo = SALE_NO + "CK";
				            o_vo.setOutId(outId);
				            o_vo.setOutNo(outNo);
				            o_vo.setOrderId(SALE_ID);
				            o_vo.setOrderNo(SALE_NO);
				            o_vo.setWarehouseId(WAREHOUSE_ID);
				            o_vo.setWarehouseCode(WAREHOUSE_CODE);
				            o_vo.setWarehouseName(WAREHOUSE_NAME);
				            o_vo.setOrgId(ORG_ID);
				            o_vo.setOutStatus(DicConstant.CKDZT_02);
				            o_vo.setOutDate(Pub.getCurrentDate());
				            o_vo.setPrintStatus(DicConstant.DYZT_01);
				            o_vo.setOutType(DicConstant.CKLX_01);
				            o_vo.setCompanyId(user.getCompanyId());
				            o_vo.setOrgId(user.getOrgId());
				            o_vo.setOemCompanyId(user.getOemCompanyId());
				            o_vo.setCreateUser(user.getAccount());
				            o_vo.setCreateTime(Pub.getCurrentDate());
				            o_vo.setStatus(DicConstant.YXBS_01);
				            //通过dao，执行插入
				            dao.insertOutBill(o_vo);
				            
				            
				            PtBuIssueOrderVO sq_i_vo = new PtBuIssueOrderVO();
				            sq_i_vo.setOrderId(SALE_ID);
				            sq_i_vo.setOrderNo(SALE_NO);
				            sq_i_vo.setUserAccount(SUPPLIER_CODE);
				            sq_i_vo.setUserName(SUPPLIER_NAME);
				            sq_i_vo.setIssueNo(SALE_NO+"01");
				            sq_i_vo.setPrintStatus(DicConstant.DYZT_02);
				            sq_i_vo.setIssueStatus(DicConstant.FLDFLZT_02);
				            sq_i_vo.setPrintMan(SUPPLIER_CODE);
				            sq_i_vo.setKeeperId(SUPPLIER_ID);
				            sq_i_vo.setPrintDate(Pub.getCurrentDate());
				            sq_i_vo.setCompanyId(user.getCompanyId());
				            sq_i_vo.setOemCompanyId(user.getOemCompanyId());
				            sq_i_vo.setOrgId(user.getOrgId());
				            sq_i_vo.setStatus(DicConstant.YXBS_01);
				            sq_i_vo.setCreateTime(Pub.getCurrentDate());
				            sq_i_vo.setCreateUser(user.getAccount());
				            dao.insertIssue(sq_i_vo);
				            
				            /**
				             * 获取PART_ID,PART_CODE,PART_NAME,IN_COUNT,PCH_PRICE,PLAN_PRICE,UNIT,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME
				             */
				            for(int j=0;j<DETAIL_ID.length;j++){
				            	
				            	QuerySet getPartInfo = dao.getPartInfo(DETAIL_ID[j]);
				            	String PART_ID = getPartInfo.getString(1, "PART_ID");
				            	String PART_CODE = getPartInfo.getString(1, "PART_CODE");
				            	String PART_NAME = getPartInfo.getString(1, "PART_NAME");
				            	String PCH_PRICE = getPartInfo.getString(1, "PCH_PRICE");
				            	String PLAN_PRICE = getPartInfo.getString(1, "PLAN_PRICE");
				            	String UNIT = getPartInfo.getString(1, "UNIT");
				            	String D_SUPPLIER_ID = getPartInfo.getString(1, "SUPPLIER_ID");
				            	String D_SUPPLIER_CODE = getPartInfo.getString(1, "SUPPLIER_CODE");
				            	String D_SUPPLIER_NAME = getPartInfo.getString(1, "SUPPLIER_NAME");
				            	String POSITION_ID = getPartInfo.getString(1, "POSITION_ID");
				            	String POSITION_CODE = getPartInfo.getString(1, "POSITION_CODE");
				            	String POSITION_NAME = getPartInfo.getString(1, "POSITION_NAME");
				            	String SALE_PRICE = getPartInfo.getString(1, "UNIT_PRICE");
				            	/**
				            	 * 生成入库明细
				            	 */
				            	PtBuStockInDtlVO iDtlVO = new PtBuStockInDtlVO();
				            	iDtlVO.setInId(inId);
				            	iDtlVO.setInNo(inNo);
				            	iDtlVO.setPartId(PART_ID);
				            	iDtlVO.setPartCode(PART_CODE);
				            	iDtlVO.setPartName(PART_NAME);
				            	iDtlVO.setInAmount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	iDtlVO.setPchPrice(PCH_PRICE);
				            	iDtlVO.setPchAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(PCH_PRICE)));
				            	iDtlVO.setPlanPrice(PLAN_PRICE);
				            	iDtlVO.setPlanAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(PLAN_PRICE)));
				            	iDtlVO.setUnit(UNIT);
				            	iDtlVO.setSupplierId(D_SUPPLIER_ID);
				            	iDtlVO.setSupplierCode(D_SUPPLIER_CODE);
				            	iDtlVO.setSupplierName(D_SUPPLIER_NAME);
				            	dao.inserPartIn(iDtlVO);
				            	/**
					             * 生成入库流水
					             */
				            	PtBuStockInContinualVO icVo = new PtBuStockInContinualVO();
				            	QuerySet getInConNo = dao.getInConNo();
				            	String IN_CONTINUAL_NO = getInConNo.getString(1, "NO");
				            	icVo.setContinualNo(IN_CONTINUAL_NO);
				            	icVo.setInId(inId);
				            	icVo.setInNo(inNo);
				            	icVo.setPartId(PART_ID);
				            	icVo.setPartCode(PART_CODE);
				            	icVo.setPartName(PART_NAME);
				            	icVo.setPlanPrice(PLAN_PRICE);
				            	icVo.setInCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	icVo.setInDate(Pub.getCurrentDate());
				            	dao.insertPartContinualIn(icVo);
				            	/**
				            	 * 生成发料单明细
				            	 */
				            	PtBuIssueOrderDtlVO sq_iDtlVO = new PtBuIssueOrderDtlVO();
				            	sq_iDtlVO.setIssueId(sq_i_vo.getIssueId());
				            	sq_iDtlVO.setIssueNo(SALE_NO+"01");
				            	sq_iDtlVO.setOrderId(SALE_ID);
				            	sq_iDtlVO.setPartId(PART_ID);
				            	sq_iDtlVO.setPartCode(PART_CODE);
				            	sq_iDtlVO.setPartName(PART_NAME);
				            	sq_iDtlVO.setSupplierId(D_SUPPLIER_ID);
				            	sq_iDtlVO.setSupplierCode(D_SUPPLIER_CODE);
				            	sq_iDtlVO.setSupplierName(D_SUPPLIER_NAME);
				            	sq_iDtlVO.setPositionId(POSITION_ID);
				            	sq_iDtlVO.setPositionCode(POSITION_CODE);
				            	sq_iDtlVO.setPositionName(POSITION_NAME);
				            	sq_iDtlVO.setShouldCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	sq_iDtlVO.setRealCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	sq_iDtlVO.setUnit(UNIT);
				            	sq_iDtlVO.setCompanyId(user.getCompanyId());
				            	sq_iDtlVO.setOemCompanyId(user.getOemCompanyId());
				            	sq_iDtlVO.setOrgId(user.getOrgId());
				            	sq_iDtlVO.setStatus(DicConstant.YXBS_01);
				            	sq_iDtlVO.setCreateTime(Pub.getCurrentDate());
				            	sq_iDtlVO.setCreateUser(user.getAccount());
				            	dao.insertIssueDtl(sq_iDtlVO);
				            	/**
				            	 * 生成出库单明细
				            	 */
				            	PtBuStockOutDtlVO outDtlVO = new PtBuStockOutDtlVO();
				            	outDtlVO.setOutId(outId);
				            	outDtlVO.setPartId(PART_ID);
				            	outDtlVO.setPartCode(PART_CODE);
				            	outDtlVO.setPartName(PART_NAME);
				            	outDtlVO.setSupplierId(D_SUPPLIER_ID);
				            	outDtlVO.setSupplierCode(D_SUPPLIER_CODE);
				            	outDtlVO.setSupplierName(D_SUPPLIER_NAME);
				            	outDtlVO.setPositionId(POSITION_ID);
				            	outDtlVO.setIssueId(sq_iDtlVO.getIssueId());
				            	outDtlVO.setIssueNo(sq_iDtlVO.getIssueNo());
				            	outDtlVO.setOutAmount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	outDtlVO.setShouldCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	outDtlVO.setUnit(UNIT);
				            	outDtlVO.setPlanPrice(PLAN_PRICE);
				            	outDtlVO.setPlanAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(PLAN_PRICE)));
				            	outDtlVO.setSalePrice(SALE_PRICE);
				            	outDtlVO.setSaleAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(SALE_PRICE)));
				            	dao.inserPartOut(outDtlVO);
				            	/**
				            	 * 生成出库流水
				            	 */
				            	PtBuStockOutContinualVO ocVo = new PtBuStockOutContinualVO();
				            	QuerySet getOutConNo = dao.getOutConNo();
				            	String OUT_CONTINUAL_NO = getOutConNo.getString(1, "C_NO");
				            	ocVo.setContinualNo(OUT_CONTINUAL_NO);
				            	ocVo.setOutId(outId);
				            	ocVo.setOutNo(outNo);
				            	ocVo.setPartId(PART_ID);
				            	ocVo.setPartCode(PART_CODE);
				            	ocVo.setPartName(PART_NAME);
				            	ocVo.setPlanPrice(PLAN_PRICE);
				            	ocVo.setOutCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            	ocVo.setOutDate(Pub.getCurrentDate());
				            	dao.insertPartContinualOut(ocVo);
				            	
				            	/**
				            	 * 更改原销售订单发运数量
				            	 */
				            	QuerySet getSaleDtl = dao.getSaleDtl(SALE_ID,PART_ID);
				            	if(getSaleDtl.getRowCount()>0){
				            		PtBuSaleOrderDtlVO s_dtlVo = new PtBuSaleOrderDtlVO();
				            		s_dtlVo.setDtlId(getSaleDtl.getString(1, "DTL_ID"));
				            		s_dtlVo.setDeliveryCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
				            		dao.updateSaleDtl(s_dtlVo);
				            	}
//				            	//inId
//				            	dao.updateInBillDtl(inId,DicConstant.RKLX_01);
//				                //更新入库流水的计划价
//				                dao.updateInFlow(inId);
				            }
				            /**
				             * 更改销售订单发运状态
				             */
				            QuerySet getrealAmount = dao.getRealAmount(SALE_ID);
				            PtBuSaleOrderVO saleOrderVO = new PtBuSaleOrderVO();
				            saleOrderVO.setOrderId(SALE_ID);
				            saleOrderVO.setShipStatus(DicConstant.DDFYZT_06);
//				            saleOrderVO.setOrderStatus(DicConstant.DDZT_06);
				            saleOrderVO.setRealAmount(String.valueOf(getrealAmount.getString(1, "REAL_AMOUNT")));
				            dao.updateSaleOrder(saleOrderVO);
	            	}else{
	            		/***判断是否为陕汽供货供应商，如果为是，则生成厂端入库单，厂端发料单，厂端出库单，*******************************/
	            		/*
	    	             * 更改验收数量
	    	             */
	            		for(int j=0;j<DETAIL_ID.length;j++){
	            			dao.updatePchOrderAccept(SPLIT_ID,DETAIL_ID[j],SHIP_COUNT[j],user);
	            		}
	            		/*
	    	             * 生成入库单
	    	             */
	            		QuerySet getSale = dao.getSale(SPLIT_ID);
			            String SALE_ID = getSale.getString(1, "ORDER_ID");
			            String SALE_NO = getSale.getString(1, "ORDER_NO");
			            String WAREHOUSE_ID = getSale.getString(1, "WAREHOUSE_ID");
			            String WAREHOUSE_CODE = getSale.getString(1, "WAREHOUSE_CODE");
			            String WAREHOUSE_NAME = getSale.getString(1, "WAREHOUSE_NAME");
			            String ORG_CODE = getSale.getString(1, "ORG_CODE");
			            String ORG_NAME = getSale.getString(1, "ORG_NAME");
			            String ORG_ID = getSale.getString(1, "ORG_ID");
			            String SUPPLIER_ID = getSale.getString(1, "SUPPLIER_ID");
			            String SUPPLIER_CODE = getSale.getString(1, "SUPPLIER_CODE");
			            String SUPPLIER_NAME = getSale.getString(1, "SUPPLIER_NAME");
			            String SPLIT_NO = getSale.getString(1, "SPLIT_NO");
			            String APPLY_USER = getSale.getString(1, "APPLY_USER");
			            /**
			             * 生成厂端入库单
			             */
			            String inId = dao.getId();
			            String inNo = dao.createInBillNo(SPLIT_ID, SPLIT_NO);
			            PtBuStockInVO i_vo = new PtBuStockInVO();
			            i_vo.setInId(inId);
			            i_vo.setInNo(inNo);
			            i_vo.setWarehouseId(WAREHOUSE_ID);
			            i_vo.setWarehouseCode(WAREHOUSE_CODE);
			            i_vo.setWarehouseName(WAREHOUSE_NAME);
			            i_vo.setSupplierId(SUPPLIER_ID);
			            i_vo.setSupplierCode(SUPPLIER_CODE);
			            i_vo.setSupplierName(SUPPLIER_NAME);
			            i_vo.setBuyer(APPLY_USER);
			            i_vo.setOrderId(SPLIT_ID);
			            i_vo.setOrderNo(SPLIT_NO);
			            i_vo.setOrgCode(ORG_CODE);
			            i_vo.setOrgName(ORG_NAME);
			            i_vo.setInType(DicConstant.RKLX_01);
			            i_vo.setInStatus(DicConstant.RKDZT_02);
			            i_vo.setPrintStatus(DicConstant.DYZT_01);
			            i_vo.setInDate(Pub.getCurrentDate());
			            i_vo.setCompanyId(user.getCompanyId());
			            i_vo.setOemCompanyId(user.getOemCompanyId());
			            i_vo.setCreateUser(user.getAccount());
			            i_vo.setCreateTime(Pub.getCurrentDate());
			            i_vo.setStatus(DicConstant.YXBS_01);
			            //通过dao，执行插入
			            dao.insertInBill(i_vo);
			            
			            /**
			             * 更改销售订单发运状态
			             */
			            PtBuSaleOrderVO saleOrderVO = new PtBuSaleOrderVO();
			            saleOrderVO.setOrderId(SALE_ID);
			            saleOrderVO.setShipStatus(DicConstant.DDFYZT_03);
//			            saleOrderVO.setOrderStatus(DicConstant.DDZT_06);
			            dao.updateSaleOrder(saleOrderVO);
			            
			            /**
			             * 生成发料单
			             */
			            QuerySet getKeeper = dao.getKeeper();
			            PtBuIssueOrderVO sq_i_vo = new PtBuIssueOrderVO();
			            sq_i_vo.setOrderId(SALE_ID);
			            sq_i_vo.setOrderNo(SALE_NO);
			            sq_i_vo.setUserAccount(getKeeper.getString(1, "ACCOUNT"));
			            sq_i_vo.setUserName(getKeeper.getString(1, "PERSON_NAME"));
			            sq_i_vo.setIssueNo(SALE_NO+"01");
			            sq_i_vo.setPrintStatus(DicConstant.DYZT_02);
			            sq_i_vo.setIssueStatus(DicConstant.FLDFLZT_02);
			            sq_i_vo.setPrintMan(getKeeper.getString(1, "ACCOUNT"));
			            sq_i_vo.setKeeperId(getKeeper.getString(1, "USER_ID"));
			            sq_i_vo.setPrintDate(Pub.getCurrentDate());
			            sq_i_vo.setCompanyId(user.getCompanyId());
			            sq_i_vo.setOemCompanyId(user.getOemCompanyId());
			            sq_i_vo.setOrgId(user.getOrgId());
			            sq_i_vo.setStatus(DicConstant.YXBS_01);
			            sq_i_vo.setCreateTime(Pub.getCurrentDate());
			            sq_i_vo.setCreateUser(user.getAccount());
			            dao.insertIssue(sq_i_vo);
			            /**
			             * 生成厂端出库单
			             */
			            PtBuStockOutVO o_vo = new PtBuStockOutVO();
			            String outId = dao.getId();
			            String outNo = SALE_NO + "CK";
			            o_vo.setOutId(outId);
			            o_vo.setOutNo(outNo);
			            o_vo.setOrderId(SALE_ID);
			            o_vo.setOrderNo(SALE_NO);
			            o_vo.setWarehouseId(WAREHOUSE_ID);
			            o_vo.setWarehouseCode(WAREHOUSE_CODE);
			            o_vo.setWarehouseName(WAREHOUSE_NAME);
			            o_vo.setOrgId(ORG_ID);
			            o_vo.setOutStatus(DicConstant.CKDZT_02);
			            o_vo.setOutDate(Pub.getCurrentDate());
			            o_vo.setPrintStatus(DicConstant.DYZT_01);
			            o_vo.setOutType(DicConstant.CKLX_01);
			            o_vo.setCompanyId(user.getCompanyId());
			            o_vo.setOrgId(user.getOrgId());
			            o_vo.setOemCompanyId(user.getOemCompanyId());
			            o_vo.setCreateUser(user.getAccount());
			            o_vo.setCreateTime(Pub.getCurrentDate());
			            o_vo.setStatus(DicConstant.YXBS_01);
			            //通过dao，执行插入
			            dao.insertOutBill(o_vo);
			            
			            
			            /**
			             * 获取PART_ID,PART_CODE,PART_NAME,IN_COUNT,PCH_PRICE,PLAN_PRICE,UNIT,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME
			             */
			            for(int j=0;j<DETAIL_ID.length;j++){
			            	QuerySet getPartInfo = dao.getPartInfo(DETAIL_ID[j]);
			            	String PART_ID = getPartInfo.getString(1, "PART_ID");
			            	String PART_CODE = getPartInfo.getString(1, "PART_CODE");
			            	String PART_NAME = getPartInfo.getString(1, "PART_NAME");
			            	String PCH_PRICE = getPartInfo.getString(1, "PCH_PRICE");
			            	String PLAN_PRICE = getPartInfo.getString(1, "PLAN_PRICE");
			            	String UNIT = getPartInfo.getString(1, "UNIT");
			            	String D_SUPPLIER_ID = getPartInfo.getString(1, "SUPPLIER_ID");
			            	String D_SUPPLIER_CODE = getPartInfo.getString(1, "SUPPLIER_CODE");
			            	String D_SUPPLIER_NAME = getPartInfo.getString(1, "SUPPLIER_NAME");
			            	String POSITION_ID = getPartInfo.getString(1, "POSITION_ID");
			            	String POSITION_CODE = getPartInfo.getString(1, "POSITION_CODE");
			            	String POSITION_NAME = getPartInfo.getString(1, "POSITION_NAME");
			            	String SALE_PRICE = getPartInfo.getString(1, "UNIT_PRICE");
			            	/**
			            	 * 生成入库明细
			            	 */
			            	PtBuStockInDtlVO iDtlVO = new PtBuStockInDtlVO();
			            	iDtlVO.setInId(inId);
			            	iDtlVO.setInNo(inNo);
			            	iDtlVO.setPartId(PART_ID);
			            	iDtlVO.setPartCode(PART_CODE);
			            	iDtlVO.setPartName(PART_NAME);
			            	iDtlVO.setInAmount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	iDtlVO.setPchPrice(PCH_PRICE);
			            	iDtlVO.setPchAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(PCH_PRICE)));
			            	iDtlVO.setPlanPrice(PLAN_PRICE);
			            	iDtlVO.setPlanAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(PLAN_PRICE)));
			            	iDtlVO.setUnit(UNIT);
			            	iDtlVO.setSupplierId(D_SUPPLIER_ID);
			            	iDtlVO.setSupplierCode(D_SUPPLIER_CODE);
			            	iDtlVO.setSupplierName(D_SUPPLIER_NAME);
			            	dao.inserPartIn(iDtlVO);
			            	/**
				             * 生成入库流水
				             */
			            	PtBuStockInContinualVO icVo = new PtBuStockInContinualVO();
			            	String IN_CONTINUAL_NO = PartOddNumberUtil.getInContinualNo(atx.getDBFactory());
			            	icVo.setContinualNo(IN_CONTINUAL_NO);
			            	icVo.setInId(inId);
			            	icVo.setInNo(inNo);
			            	icVo.setPartId(PART_ID);
			            	icVo.setPartCode(PART_CODE);
			            	icVo.setPartName(PART_NAME);
			            	icVo.setPlanPrice(PLAN_PRICE);
			            	icVo.setInCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	icVo.setInDate(Pub.getCurrentDate());
			            	dao.insertPartContinualIn(icVo);
			            	/**
			            	 * 生成发料单明细表信息
			            	 */
			            	PtBuIssueOrderDtlVO sq_iDtlVO = new PtBuIssueOrderDtlVO();
			            	sq_iDtlVO.setIssueId(sq_i_vo.getIssueId());
			            	sq_iDtlVO.setIssueNo(SALE_NO+"01");
			            	sq_iDtlVO.setOrderId(SALE_ID);
			            	sq_iDtlVO.setPartId(PART_ID);
			            	sq_iDtlVO.setPartCode(PART_CODE);
			            	sq_iDtlVO.setPartName(PART_NAME);
			            	sq_iDtlVO.setSupplierId(D_SUPPLIER_ID);
			            	sq_iDtlVO.setSupplierCode(D_SUPPLIER_CODE);
			            	sq_iDtlVO.setSupplierName(D_SUPPLIER_NAME);
			            	sq_iDtlVO.setPositionId(POSITION_ID);
			            	sq_iDtlVO.setPositionCode(POSITION_CODE);
			            	sq_iDtlVO.setPositionName(POSITION_NAME);
			            	QuerySet getAduit = dao.getAduit(SALE_ID,PART_ID);
			            	sq_iDtlVO.setShouldCount(getAduit.getString(1, "AUDIT_COUNT"));
			            	//sq_iDtlVO.setShouldCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	sq_iDtlVO.setRealCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	sq_iDtlVO.setUnit(UNIT);
			            	sq_iDtlVO.setCompanyId(user.getCompanyId());
			            	sq_iDtlVO.setOemCompanyId(user.getOemCompanyId());
			            	sq_iDtlVO.setOrgId(user.getOrgId());
			            	sq_iDtlVO.setStatus(DicConstant.YXBS_01);
			            	sq_iDtlVO.setCreateTime(Pub.getCurrentDate());
			            	sq_iDtlVO.setCreateUser(user.getAccount());
			            	dao.insertIssueDtl(sq_iDtlVO);
			            	/**
			            	 * 生成出库单明细
			            	 */
			            	PtBuStockOutDtlVO outDtlVO = new PtBuStockOutDtlVO();
			            	outDtlVO.setOutId(outId);
			            	outDtlVO.setPartId(PART_ID);
			            	outDtlVO.setPartCode(PART_CODE);
			            	outDtlVO.setPartName(PART_NAME);
			            	outDtlVO.setSupplierId(D_SUPPLIER_ID);
			            	outDtlVO.setSupplierCode(D_SUPPLIER_CODE);
			            	outDtlVO.setSupplierName(D_SUPPLIER_NAME);
			            	outDtlVO.setPositionId(POSITION_ID);
			            	outDtlVO.setIssueId(sq_iDtlVO.getIssueId());
			            	outDtlVO.setIssueNo(sq_iDtlVO.getIssueNo());
			            	outDtlVO.setOutAmount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	outDtlVO.setShouldCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	outDtlVO.setUnit(UNIT);
			            	outDtlVO.setPlanPrice(PLAN_PRICE);
			            	outDtlVO.setPlanAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(PLAN_PRICE)));
			            	outDtlVO.setSalePrice(SALE_PRICE);
			            	outDtlVO.setSaleAmount(String.valueOf((Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j]))*Float.parseFloat(SALE_PRICE)));
			            	outDtlVO.setKeepMan(getKeeper.getString(1, "ACCOUNT"));
			            	outDtlVO.setCreateUser(getKeeper.getString(1, "ACCOUNT"));
			            	dao.inserPartOut(outDtlVO);
			            	/**
			            	 * 生成出库流水
			            	 */
			            	PtBuStockOutContinualVO ocVo = new PtBuStockOutContinualVO();
			            	QuerySet getOutConNo = dao.getOutConNo();
			            	String OUT_CONTINUAL_NO = getOutConNo.getString(1, "C_NO");
			            	ocVo.setContinualNo(OUT_CONTINUAL_NO);
			            	ocVo.setOutId(outId);
			            	ocVo.setOutNo(outNo);
			            	ocVo.setPartId(PART_ID);
			            	ocVo.setPartCode(PART_CODE);
			            	ocVo.setPartName(PART_NAME);
			            	ocVo.setPlanPrice(PLAN_PRICE);
			            	ocVo.setOutCount(String.valueOf(Integer.parseInt(SHIP_COUNT[j])+Integer.parseInt(HAD_SHIP_COUNT[j])));
			            	ocVo.setOutDate(Pub.getCurrentDate());
			            	ocVo.setKeeperMan(getKeeper.getString(1, "ACCOUNT"));
			            	ocVo.setCreateMan(getKeeper.getString(1, "ACCOUNT"));
			            	dao.insertPartContinualOut(ocVo);
			            }
	            	}
	            	QuerySet getStorage = dao.getStorage(SPLIT_ID);
	            	PtBuPchOrderSplitVO pSplitVO = new PtBuPchOrderSplitVO();
            		pSplitVO.setSplitId(SPLIT_ID);
            		pSplitVO.setStorageCount(getStorage.getString(1, "COUNT"));
            		pSplitVO.setOrderStatus(DicConstant.CGDDZT_05);
            		pSplitVO.setCloseDate(Pub.getCurrentDate());
            		dao.updateSplitOrder(pSplitVO);
	            }
	            atx.setOutMsg("", "发货成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    

}
