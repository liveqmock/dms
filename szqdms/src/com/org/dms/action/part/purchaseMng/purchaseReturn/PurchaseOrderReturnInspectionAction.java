package com.org.dms.action.part.purchaseMng.purchaseReturn;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseReturn.PurchaseOrderReturnInspectionDao;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;


public class PurchaseOrderReturnInspectionAction{
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderReturnInspectionDao dao = PurchaseOrderReturnInspectionDao.getInstance(atx);
	    
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
		 * @date()2014年7月26日上午11:01:30
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
		 * @date()2014年7月26日上午11:02:22
		 * @author Administrator
		 * @to_do:退货单验货确认
		 * @throws Exception
		 */
		public void purchaseOrderReturnInspectConfirm ()throws Exception
		 {
		    RequestWrapper request = atx.getRequest();
	       User user = (User) atx.getSession().get(Globals.USER_KEY);
	       try{
	       	String RETURN_ID = Pub.val(request, "RETURN_ID");
	       	PtBuPchReturnVO tmpVo = new PtBuPchReturnVO();
	       	tmpVo.setReturnId(RETURN_ID);
	       	tmpVo.setSignStauts(DicConstant.THDYSZT_01);
	       	tmpVo.setUpdateTime(Pub.getCurrentDate());
	       	tmpVo.setUpdateUser(user.getAccount());
	       	dao.updateReturn(tmpVo);
	       	atx.setOutMsg("","退货单验货确认成功！");
	       }catch(Exception e){
	       	
	       }
		 }

}
