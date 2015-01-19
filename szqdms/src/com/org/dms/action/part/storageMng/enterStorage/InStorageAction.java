package com.org.dms.action.part.storageMng.enterStorage;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.enterStorage.InStorageDao;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
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
 * 
 * @author wjx
 * 2014年7月15日
 */
public class InStorageAction {

	   private Logger logger = com.org.framework.log.log.getLogger(
	        "PurchaseContractManageAction");
	    private ActionContext atx = ActionContext.getContext();
	   //定义dao
	    private InStorageDao  dao =InStorageDao.getInstance(atx);
	    
	    public void ordersearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String orgId = user.getOrgId();
			QuerySet getIfArmy = dao.getIfAmry(orgId);
			String ifArmy = getIfArmy.getString(1, 1);
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.ordersearch(page,user,conditions,ifArmy);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 查询发运单明细
	     * @throws Exception
	     */
	    public void orderXxsearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String orderId = request.getParamValue("orderId");
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
//				page.setPageRows(10000);
				BaseResultSet bs = dao.orderXxsearch(page,user,conditions,orderId);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void orderOverInsert() throws Exception
	    {
	        //获取封装后的request对象
	        RequestWrapper request = atx.getRequest();
	        //获取封装后的response对象
	        //获取当前登录user对象
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        //失败N，成功Y
	        String Msg ="N";
	        try {
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String dtl_ids = hm.get("DTL_ID");//详细订单ID
	            String sign_counts = hm.get("SIGN_COUNT");//签收数量(逗号分隔)
	            String order_id =hm.get("ORDER_ID");//订单ID
	            String orderNo =hm.get("ORDER_NO");//订单编号
	            String part_ids =hm.get("PART_ID");//配件ID
	            String part_codes =hm.get("PART_CODE");//配件代码
	            //String part_nos =hm.get("PART_NO");//配件NO
	            String part_names =hm.get("PART_NAME");//配件名称
	            String supplierIds =hm.get("SUPPLIER_IDS");//供应商ID
	            String supplierCodes =hm.get("SUPPLIER_CODES");//供应商代码
	            String supplierNames =hm.get("SUPPLIER_NAMES");//供应商名称
	            String remarks= hm.get("REMARKS");
	            String shipId = hm.get("SHIP_ID");
//	            String[] dtlid = dtl_ids.split(",");
//	            String[] part_id = part_ids.split(",");
//	            String[] part_code = part_codes.split(",");
//	            String[] part_name = part_names.split(",");
//	            //String[] part_no = part_nos.split(",");
//	            String[] supplierId = supplierIds.split(",");
//	            String[] supplierCode = supplierCodes.split(",");
//	            String[] supplierName = supplierNames.split(",");
//	            String[] signcount = sign_counts.split(",");
	            // 验收明细
	            QuerySet qSet = dao.orderDtl(user, order_id);
	            if (qSet.getRowCount() <= 0) {
	            	throw new Exception("验收明细为空,不能验收.");
	            }
	            int count = qSet.getRowCount();
	            //检查需要签收数量
	            //QuerySet qs =dao.orderChek(order_id);
	            //String count =qs.getString(1, 1);
	            //int conuntorder =Integer.parseInt(count);
	            //需签收数量与提交数量对比
	            //if(conuntorder==dtlid.length){
        	  for (int i = 0; i < count; i++) {
            	PtBuSaleOrderDtlVO pbodVo = new PtBuSaleOrderDtlVO();
            	pbodVo.setDtlId(qSet.getString(i+1, "DTL_ID"));
            	pbodVo.setSignCount(qSet.getString(i+1, "SIGN_COUNT"));
            	pbodVo.setUpdateTime(Pub.getCurrentDate());
            	pbodVo.setUpdateUser(user.getAccount());
                dao.updateInStorageOrder(pbodVo);
              String stock_id ="";
              //根据partid查询配件库存
              QuerySet qsstock =dao.dealerstock(qSet.getString(i+1, "PART_ID"),qSet.getString(i+1, "SUPPLIER_ID"),user);
              PtBuDealerStockVO pbdsVo = new PtBuDealerStockVO();
              //已经存在库存更新
              if(qsstock.getRowCount()>0){
            	  stock_id =qsstock.getString(1, 1);
            	  String amount =qsstock.getString(1, 2);
            	  String available_amount =qsstock.getString(1, 3);
            	  amount =String.valueOf(Integer.parseInt(qSet.getString(i+1, "SIGN_COUNT"))+Integer.parseInt(amount));
            	  available_amount=String.valueOf(Integer.parseInt(qSet.getString(i+1, "SIGN_COUNT"))+Integer.parseInt(available_amount));
            	  pbdsVo.setStockId(stock_id);
            	  pbdsVo.setAmount(amount);
            	  pbdsVo.setAvailableAmount(available_amount);
            	  pbdsVo.setUpdateUser(user.getAccount());
            	  pbdsVo.setUpdateTime(Pub.getCurrentDate());
            	  dao.updateDealerStock(pbdsVo);
              }
              	// 不存在插入新的记录
              else{
            	  pbdsVo.setPartId(qSet.getString(i+1, "PART_ID"));
            	  pbdsVo.setPartCode(qSet.getString(i+1, "PART_CODE"));
            	  pbdsVo.setPartName(qSet.getString(i+1, "PART_NAME"));
            	  pbdsVo.setSupplierId(qSet.getString(i+1, "SUPPLIER_ID"));
            	  pbdsVo.setSupplierCode(qSet.getString(i+1, "SUPPLIER_CODE"));
            	  pbdsVo.setSupplierName(qSet.getString(i+1, "SUPPLIER_NAME"));
            	  pbdsVo.setOrgCode(user.getOrgCode());
            	  pbdsVo.setOrgId(user.getOrgId());
            	  pbdsVo.setAmount(qSet.getString(i+1, "SIGN_COUNT"));
            	  pbdsVo.setOccupyAmount("0");
            	  pbdsVo.setAvailableAmount(qSet.getString(i+1, "SIGN_COUNT"));
            	  pbdsVo.setStorageStatus(DicConstant.KCZT_01);
            	  pbdsVo.setCompanyId(user.getCompanyId());
            	  pbdsVo.setCreateUser(user.getAccount());
            	  pbdsVo.setCreateTime(Pub.getCurrentDate());
            	  pbdsVo.setStatus(DicConstant.YXBS_01);
            	  pbdsVo.setOemCompanyId(user.getOemCompanyId());
            	  dao.orderStockInsert(pbdsVo);
            	  stock_id =pbdsVo.getStockId();
              }
              //插入轨迹表
              PtBuDealerStockChangeVO pbdscVo =new PtBuDealerStockChangeVO();
        	  pbdscVo.setStockId(stock_id);
        	  pbdscVo.setOrgId(user.getOrgId());
        	  pbdscVo.setOrgCode(user.getOrgCode());
        	  pbdscVo.setPartId(qSet.getString(i+1, "PART_ID"));
        	  pbdscVo.setPartCode(qSet.getString(i+1, "PART_CODE"));
        	  pbdscVo.setPartName(qSet.getString(i+1, "PART_NAME"));
        	  pbdscVo.setCount(qSet.getString(i+1, "SIGN_COUNT"));
        	  pbdscVo.setApplyDate(Pub.getCurrentDate());
        	  pbdscVo.setApplyType(DicConstant.CZLX_01);
        	  pbdscVo.setSupplierId(qSet.getString(i+1, "SUPPLIER_ID"));
        	  pbdscVo.setSupplierCode(qSet.getString(i+1, "SUPPLIER_CODE"));
        	  pbdscVo.setSupplierName(qSet.getString(i+1, "SUPPLIER_NAME"));
        	  //pbdscVo.setRemarks(remarks);
        	  pbdscVo.setCreateUser(user.getAccount());
        	  pbdscVo.setCreateTime(Pub.getCurrentDate());
        	  pbdscVo.setStatus(DicConstant.YXBS_01);
        	  pbdscVo.setStorageType(DicConstant.QDCRKLX_02);
        	  pbdscVo.setInNo(orderNo);
              dao.orderStockChangeInsert(pbdscVo);
            }
        	  //更新签收状态
        	  PtBuSaleOrderVO pboVo= new PtBuSaleOrderVO();
        	  pboVo.setOrderId(order_id);
        	  pboVo.setShipStatus(DicConstant.DDFYZT_07);
        	  pboVo.setReceipt(remarks);
        	  dao.updateInStorageOrderStauts(pboVo);
        	  
        	  
        	  QuerySet qsOrder = dao.selectSourceOrder(order_id);
              if (qsOrder.getRowCount() > 0) {
              	// 有原单(三包急件，技术升级，直营订单)
            	  pboVo.setOrderId(qsOrder.getString(1, "DIR_SOURCE_ORDER_ID"));
              	// 更新实发金额(原订单)
              	dao.updateInStorageOrderStauts(pboVo);
              }

        	  //检验发运单对应订单全部验收，更新发运单状态
        	  if(!"".equals(shipId)&&shipId!=null){
        		  QuerySet qs = dao.queryShipOrder(shipId);
        		  if(qs.getRowCount()==0){
	        		  PtBuOrderShipVO svo = new PtBuOrderShipVO();
	        		  svo.setShipId(shipId);
	        		  svo.setShipStatus(DicConstant.FYDZT_05);
	        		  svo.setUpdateUser(user.getAccount());
	        		  svo.setUpdateTime(Pub.getCurrentDate());
	        		  dao.updateShipOrder(svo);
        		  }
        	  }
        	  Msg="更新成功";
        	  atx.setOutMsg(pboVo.getRowXml(),Msg);
	          
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
		}
}
