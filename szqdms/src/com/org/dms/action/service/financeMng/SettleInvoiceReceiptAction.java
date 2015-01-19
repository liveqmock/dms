package com.org.dms.action.service.financeMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.financeMng.SettleInvoiceReceiptDao;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 结算发票签收ACTION
 * @author zts
 *
 */
public class SettleInvoiceReceiptAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "SettleInvoiceReceiptAction");
	private ActionContext atx = ActionContext.getContext();
	private SettleInvoiceReceiptDao dao = SettleInvoiceReceiptDao.getInstance(atx);

	/**
     * 结算发票签收查询
     * @throws Exception
     */
    public void settleInvoiceReceiptSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.settleInvoiceReceiptSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 结算发票签收
     * @throws Exception
     */
    public void settleReceiptUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
       // String settleId=Pub.val(request, "settleId");
        try
        {

        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String settleId=hm.get("SETTLE_ID");
			String invoiceAmount=hm.get("INVOICE_AMOUNT");
        	SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
        	vo.setSettleId(settleId);
        	vo.setInvoiceAmount(invoiceAmount);//发票金额
        	vo.setSettleStatus(DicConstant.JSZT_05);//发票签收
        	vo.setReceiveTime(Pub.getCurrentDate());//签收日期
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.settleUpdate(vo);
			atx.setOutMsg("","签收成功.");
	     }
	     catch (Exception e)
	     {
	    	 atx.setException(e);
	         logger.error(e);
	     }
    }
}
