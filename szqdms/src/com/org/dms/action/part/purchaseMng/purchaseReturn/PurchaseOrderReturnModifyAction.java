package com.org.dms.action.part.purchaseMng.purchaseReturn;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseReturn.PurchaseOrderReturnModifyDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnDtlVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PurchaseOrderReturnModifyAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderReturnModifyDao dao = PurchaseOrderReturnModifyDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年7月30日上午9:19:48
	     * @author Administrator
	     * @to_do:采购退货单调整
	     * @throws Exception
	     */
	    public void returnOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.returnOrderSearch(page,user,conditions);
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
	     * @date()2014年7月30日上午9:20:35
	     * @author Administrator
	     * @to_do:退货单配件查询
	     * @throws Exception
	     */
	    public void returnPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			try
			{
				String RETURN_ID = Pub.val(request, "RETURN_ID");
				BaseResultSet bs = dao.returnPartSearch(page,user,RETURN_ID);
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
	     * @date()2014年7月29日下午2:53:22
	     * @author Administrator
	     * @to_do:采购退货单配件删除
	     * @throws Exception
	     */
	    public void partDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String DETAIL_ID = Pub.val(request, "DETAIL_ID");
				/**
				 * 更改退货总数采购金额计划总金额 BEGIN
				 */
				QuerySet prices = dao.geOldtPartAmount(DETAIL_ID);
				String COUNT = prices.getString(1, "COUNT");
				String PLAN_AMOUNT = prices.getString(1, "PLAN_AMOUNT");
				String AMOUNT = prices.getString(1, "AMOUNT");
				String RETURN_ID = prices.getString(1, "RETURN_ID");
				String R_AMOUNT = prices.getString(1, "R_AMOUNT");
				String R_COUNT = prices.getString(1, "R_COUNT");
				String R_PLAN_AMOUNT = prices.getString(1, "R_PLAN_AMOUNT");
				float new_planAmount = Float.parseFloat(R_PLAN_AMOUNT)-Float.parseFloat(PLAN_AMOUNT);
				float new_pchAmount = Float.parseFloat(R_AMOUNT)-Float.parseFloat(AMOUNT);
				int new_pchCount = Integer.parseInt(R_COUNT)-Integer.parseInt(COUNT);
				PtBuPchReturnVO tmpVO = new PtBuPchReturnVO();
				tmpVO.setReturnId(RETURN_ID);
				tmpVO.setPlanAmount(String.valueOf(new_planAmount));
				tmpVO.setAmount(String.valueOf(new_pchAmount));
				tmpVO.setCount(String.valueOf(new_pchCount));
				dao.updateReturn(tmpVO);
				/**
				 * END
				 */
	            dao.delParts(DETAIL_ID);
				atx.setOutMsg("","配件删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    
	    /**
	     * 
	     * @date()2014年7月30日上午9:40:54
	     * @author Administrator
	     * @to_do:退货单调整完成
	     * @throws Exception
	     */
	    public void returnModify ()throws Exception
		 {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	       try{
	    	String returnId = Pub.val(request, "RETURN_ID");
	       	QuerySet qs=dao.queryAmount(returnId);
            String amount=qs.getString(1, "AMOUNT");
            String count=qs.getString(1, "COUNT");
            String planAmount=qs.getString(1, "PLAN_AMOUNT");
            PtBuPchReturnVO vo =new PtBuPchReturnVO();
            vo.setReturnId(returnId);
            vo.setReturnStatus(DicConstant.CGTHDZT_02);
            vo.setUpdateTime(Pub.getCurrentDate());
            vo.setUpdateUser(user.getAccount());
            vo.setIsAgreed(DicConstant.SF_01);
            vo.setAmount(amount);
            vo.setCount(count);
            vo.setPlanAmount(planAmount);
            dao.updateReturn(vo);//修改退货单状态为已申请
	 	    atx.setOutMsg("1", "提报成功！");
	       	
	       }catch(Exception e){
	       	
	       }
		 }
	    public void orderPartCountSave() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        HashMap<String, String> hm = RequestUtil.getValues(request);
	        try {
				String DETAIL_ID = hm.get("DTL_ID");
				String COUNT = hm.get("COUNT");
				String REMARKS = hm.get("RMK"); 
				String amount = hm.get("AMOUNT"); 
	            if(REMARKS==null){
	            	REMARKS = "";
	            }
	            PtBuPchReturnDtlVO vo=new PtBuPchReturnDtlVO();
	            vo.setDetailId(DETAIL_ID);
	            vo.setCount(COUNT);
	            vo.setAmount(amount);
	            vo.setUpdateTime(Pub.getCurrentDate());
	            vo.setUpdateUser(user.getAccount());
	            dao.updateCount(vo);
	            atx.setOutMsg("","保存成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }

}
