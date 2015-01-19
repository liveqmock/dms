package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.VerticalShippingOrderAduitDao;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class VerticalShippingOrderAduitAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private VerticalShippingOrderAduitDao dao = VerticalShippingOrderAduitDao.getInstance(atx);
	    
	    /**
	     * 
	     * @date()2014年8月22日下午6:44:51
	     * @author Administrator
	     * @to_do:直发订单查询
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
	    public void orderPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String PURCHASE_ID = Pub.val(request, "PURCHASE_ID");
				String supplierId = Pub.val(request, "supplierId");
				BaseResultSet bs = dao.orderPartSearch(page,user,PURCHASE_ID,supplierId);
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
	     * @date()2014年8月22日下午6:54:20
	     * @author Administrator
	     * @to_do:直发订单审核
	     * @throws Exception
	     */
	    public void orderComfirm() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchOrderVO tempVO = new PtBuPchOrderVO();
	        try
	        {
				HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String  PURCHASE_ID = hm.get("PURCHASE_ID");
				String SUPPLIER_ID = hm.get("SUPPLIER_ID");
				String SUPPLIER_NAME = hm.get("SUPPLIER_NAME");
				String SUPPLIER_CODE = hm.get("SUPPLIER_CODE");
				String PURCHASE_TYPE = hm.get("PURCHASE_TYPE");
				String SELECT_MONTH = hm.get("SELECT_MONTH");
				String ORDER_NO = PartOddNumberUtil.getPurchaseOrderNo(atx.getDBFactory(),PURCHASE_TYPE,SUPPLIER_CODE );
				String SPLIT_NO = PartOddNumberUtil.getSplitNo(atx.getDBFactory(),ORDER_NO );
				QuerySet checkPrice = dao.checkPrice(PURCHASE_ID,SUPPLIER_ID);
				if(!"".equals(checkPrice.getString(1, "CODES"))&&checkPrice.getString(1, "CODES")!=null){
					throw new Exception("配件"+checkPrice.getString(1, "CODES")+"尚未维护采购价格，请维护采购价格");
				}
				QuerySet checkPos = dao.checkPos(PURCHASE_ID);
				if(!"".equals(checkPos.getString(1, "CODES"))&&checkPos.getString(1, "CODES")!=null){
					throw new Exception("配件"+checkPos.getString(1, "CODES")+"尚未维护对应库位关系，请维护");
				}
				QuerySet checkPlan = dao.checkPlan(PURCHASE_ID);
				if(!"".equals(checkPlan.getString(1, "CODES"))&&checkPlan.getString(1, "CODES")!=null){
					throw new Exception("配件"+checkPlan.getString(1, "CODES")+"尚未维护计划价格，请维护");
				}
				//插入拆分单主表
				
				PtBuPchOrderSplitVO s_po = new PtBuPchOrderSplitVO();
				s_po.setPurchaseId(PURCHASE_ID);
				s_po.setSplitNo(SPLIT_NO);
				s_po.setOrderNo(ORDER_NO);
				s_po.setSelectMonth(SELECT_MONTH);
				s_po.setPurchaseType(PURCHASE_TYPE);
				s_po.setSupplierId(SUPPLIER_ID);
				s_po.setSupplierCode(SUPPLIER_CODE);
				s_po.setSupplierName(SUPPLIER_NAME);
				s_po.setOrderType(PURCHASE_TYPE);
				QuerySet checkIfSQ = dao.checkIfSQ(SUPPLIER_ID);
				if(checkIfSQ.getRowCount()>0){
					s_po.setOrderStatus(DicConstant.CGDDZT_03);
					s_po.setIfOnTime(DicConstant.SF_01);
				}else{
					s_po.setOrderStatus(DicConstant.CGDDZT_02);
				}
				s_po.setApplyUser(user.getAccount());
				s_po.setApplyDate(Pub.getCurrentDate());
				s_po.setStatus(DicConstant.YXBS_01);
				s_po.setCompanyId(user.getCompanyId());
				s_po.setOemCompanyId(user.getOemCompanyId());
				s_po.setCreateUser(user.getAccount());
				s_po.setCreateTime(Pub.getCurrentDate());
				s_po.setOrgId(user.getOrgId());
				s_po.setSettleStatus(DicConstant.GYSJSZT_01);
				dao.insertSplitOrder(s_po);
				//循环将拆分单明细数据插入拆分单明细表,同时将拆分单总采购量，总采购金额，总计划金额放入拆分单表中
				float planAmount = 0;//计划总金额
	            float amount = 0;//采购总金额
	            int count = 0;//总采购数
				String SPLIT_ID = s_po.getSplitId();
				QuerySet qs_p = dao.getSplitPart(PURCHASE_ID,SUPPLIER_ID);
				for(int j =1;j<=qs_p.getRowCount();j++){
					String PART_ID = qs_p.getString(j, "PART_ID");
					String PART_NAME = qs_p.getString(j, "PART_NAME");
					String PART_CODE = qs_p.getString(j, "PART_CODE");
					String PCH_COUNT = qs_p.getString(j, "PCH_COUNT");
					String PCH_PRICE = qs_p.getString(j, "PCH_PRICE");
					String PCH_AMOUNT = qs_p.getString(j, "PCH_AMOUNT");
					String REMARKS = qs_p.getString(j, "REMARKS");
					String IF_SUPLY = qs_p.getString(j, "IF_SUPLY");
					String PLAN_PRICE = qs_p.getString(j, "PLAN_PRICE");
					String DETAIL_ID = qs_p.getString(j, "DETAIL_ID");
					String PLAN_AMOUNT = qs_p.getString(j, "PLAN_AMOUNT");
					
					PtBuPchOrderSplitDtlVO s_p_vo = new PtBuPchOrderSplitDtlVO();
					s_p_vo.setSplitId(SPLIT_ID);
					s_p_vo.setPartId(PART_ID);
					s_p_vo.setPartCode(PART_CODE);
					s_p_vo.setPartName(PART_NAME);
					if(DicConstant.SF_01.equals(IF_SUPLY)){
						s_p_vo.setSupplierId(SUPPLIER_ID);
						s_p_vo.setSupplierCode(SUPPLIER_CODE);
						s_p_vo.setSupplierName(SUPPLIER_NAME);
					}else{
						QuerySet tmp = dao.getTmpSup("9XXXXXX");
						s_p_vo.setSupplierId(tmp.getString(1, "SUPPLIER_ID"));
						s_p_vo.setSupplierCode(tmp.getString(1, "SUPPLIER_CODE"));
						s_p_vo.setSupplierName(tmp.getString(1, "SUPPLIER_NAME"));
					}
					s_p_vo.setPchCount(PCH_COUNT);
					s_p_vo.setPchPrice(PCH_PRICE);
					s_p_vo.setPchAmount(PCH_AMOUNT);
					s_p_vo.setRemarks(REMARKS);
					s_p_vo.setPlanPrice(PLAN_PRICE);
					s_p_vo.setPlanAmount(PLAN_AMOUNT);
					dao.insertSplitOrderPart(s_p_vo);
					
					
					PtBuPchOrderDtlVO d_vo = new PtBuPchOrderDtlVO();
					d_vo.setDetailId(DETAIL_ID);
					d_vo.setPchCount(PCH_COUNT);
					d_vo.setPchPrice(PCH_PRICE);
					d_vo.setPchAmount(PCH_AMOUNT);
					d_vo.setRemarks(REMARKS);
					d_vo.setPlanPrice(PLAN_PRICE);
					d_vo.setPlanAmount(PLAN_AMOUNT);
					dao.updatePurchaseOrderDtl(d_vo);
					//获取计划总金额，采购总金额，总采购数
	                planAmount = planAmount + Float.parseFloat(PLAN_AMOUNT);//计划总金额
	                amount = amount+Float.parseFloat(PCH_AMOUNT);//采购总金额
	                count = count + Integer.parseInt(PCH_COUNT);//总采购数
	                
				}
				//更新拆分单总采购量，拆分单总采购金额，拆分单总计划金额
				PtBuPchOrderSplitVO amVo = new PtBuPchOrderSplitVO();
				amVo.setSplitId(SPLIT_ID);
				amVo.setPlanAmount(String.valueOf(planAmount));//计划总金额
				amVo.setPurchaseAmount(String.valueOf(amount));//采购总金额
				amVo.setPurchaseCount(String.valueOf(count));//总采购数
				dao.updatePurchaseOrderSplit(amVo);
				/**
				 * 主表状态更改
				 */
				
				tempVO.setPurchaseId(PURCHASE_ID);
				tempVO.setSupplierId(SUPPLIER_ID);
				tempVO.setSupplierCode(SUPPLIER_CODE);
				tempVO.setSupplierName(SUPPLIER_NAME);
				tempVO.setOrderNo(ORDER_NO);
				tempVO.setOrderStatus(DicConstant.CGDDZT_02);
				tempVO.setPlanAmount(String.valueOf(planAmount));
				tempVO.setPurchaseAmount(String.valueOf(amount));
				tempVO.setPurchaseCount(String.valueOf(count));
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updatePurchaseOrder(tempVO);
				atx.setOutMsg("","订单审核成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
