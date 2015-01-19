package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderComfirmDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
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

public class PurchaseOrderComfirmAction {
	private Logger logger = com.org.framework.log.log.getLogger("Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderComfirmDao dao = PurchaseOrderComfirmDao.getInstance(atx);
	    // 定义reponse对象
	    private ResponseWrapper responseWrapper = atx.getResponse();
	
	
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author Administrator
	 * @to_do:采购订单通过查询
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
	
	 public void download() throws Exception {

	        try {
	        	//获取封装后的request对象
	        	RequestWrapper request = atx.getRequest();
	            User user = (User) atx.getSession().get(Globals.USER_KEY);
	            String SPLIT_ID = Pub.val(request, "SPLIT_ID");
				
	            List<HeaderBean> header = new ArrayList<HeaderBean>();
	            HeaderBean hBean = null;
	            hBean = new HeaderBean();
	            hBean.setName("SPLIT_NO");
	            hBean.setTitle("订单编号");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PART_CODE");
	            hBean.setTitle("配件代码");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PART_NAME");
	            hBean.setTitle("配件名称");
	            hBean.setWidth(50);
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("UNIT");
	            hBean.setTitle("计量单位");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("MIN_PACK");
	            hBean.setTitle("最小包装");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_COUNT");
	            hBean.setTitle("采购数量");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_PRICE");
	            hBean.setTitle("采购价格");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("PCH_AMOUNT");
	            hBean.setTitle("金额");
	            header.add(hBean);

	            hBean = new HeaderBean();
	            hBean.setName("DELIVERY_CYCLE");
	            hBean.setTitle("供货周期");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("REMARKS");
	            hBean.setTitle("备注 ");
	            header.add(hBean);
	            
	            QuerySet querySet = dao.download(user,SPLIT_ID);
	            ExportManager.exportFile(responseWrapper.getHttpResponse(), "DDCXDC", header, querySet);
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
		}
	 
	/**
	 * 
	 * @date()2014年7月16日下午7:42:16
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
	  * @date()2014年7月16日下午7:54:06
	  * @author Administrator
	  * @to_do:订单确认
	  * @throws Exception
	  */
	 public void orderComfirm ()throws Exception
	 {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        HashMap<String,String> hm;
		hm = RequestUtil.getValues(request);
        try{
        	String SPLIT_ID = Pub.val(request, "SPLIT_ID");
        	QuerySet getType = dao.getPchType(SPLIT_ID);
        	String type = getType.getString(1, 1);
        	if(!DicConstant.CGDDLX_05.equals(type)){
        		PtBuPchOrderSplitVO tmpVo = new PtBuPchOrderSplitVO();
            	tmpVo.setSplitId(SPLIT_ID);
            	tmpVo.setValue(hm);
            	tmpVo.setOrderStatus(DicConstant.CGDDZT_03);
            	tmpVo.setSupplierDate(Pub.getCurrentDate());
            	tmpVo.setUpdateTime(Pub.getCurrentDate());
            	tmpVo.setUpdateUser(user.getAccount());
            	dao.updatePurchaseOrderSplit(tmpVo);
        	}else{
        		String ifOnTime = hm.get("IF_ON_TIME");
        		if(DicConstant.SF_01.equals(ifOnTime)){
        			/**
        			 * 如果及时供货，与原逻辑相同
        			 */
        			PtBuPchOrderSplitVO tmpVo = new PtBuPchOrderSplitVO();
                	tmpVo.setSplitId(SPLIT_ID);
                	tmpVo.setValue(hm);
                	tmpVo.setOrderStatus(DicConstant.CGDDZT_03);
                	tmpVo.setSupplierDate(Pub.getCurrentDate());
                	tmpVo.setUpdateTime(Pub.getCurrentDate());
                	tmpVo.setUpdateUser(user.getAccount());
                	dao.updatePurchaseOrderSplit(tmpVo);
        		}else{
        			/**
        			 * 如果不能及时供货，关闭此采购拆分单，采购订单，同时生成新采购订单
        			 */
        			dao.closeSplit(SPLIT_ID,user);//关闭采购拆分单；
        			dao.closePurchase(SPLIT_ID,user);//关闭采购订单;
        		
        			/**
        			 * 生成新采购订单
        			 */
        			QuerySet getNewPurchaseId = dao.getNewPurchaseId();
        			String NEW_ID = getNewPurchaseId.getString(1, "NEW_ID");
        			dao.createNewPurchase(NEW_ID,SPLIT_ID);//创建采购订单主表信息
        			dao.createNewPurchaseDtl(NEW_ID,SPLIT_ID);//创建采购订单明细表信息
        		}
        	}
        	
        	atx.setOutMsg("","订单确认成功！");
        }catch(Exception e){
        	
        }
	 }

}
