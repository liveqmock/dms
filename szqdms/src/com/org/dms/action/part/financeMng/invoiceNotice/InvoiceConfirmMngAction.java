package com.org.dms.action.part.financeMng.invoiceNotice;

import java.util.HashMap;

import javax.management.StringValueExp;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.invoiceNotice.InvoiceConfirmMngDao;
import com.org.dms.vo.part.PtBuAccountTransferVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class InvoiceConfirmMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private InvoiceConfirmMngDao dao = InvoiceConfirmMngDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年8月14日下午2:14:36
	     * @author Administrator
	     * @to_do:供应商开票查询
	     * @throws Exception
	     */
	    public void invioceSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.invioceSearch(page,user,conditions);
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
		 * @date()2014年8月11日上午10:40:32
		 * @author Administrator
		 * @to_do:开票明细
		 * @throws Exception
		 */
	    public void invioceOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			try
			{
				String SUM_ID = Pub.val(request, "SUM_ID");
				page.setPageRows(999);
				BaseResultSet bs = dao.invioceOrderSearch(page,user,SUM_ID);
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
	     * @date()2014年8月14日下午4:48:15
	     * @author Administrator
	     * @to_do:开票驳回
	     * @throws Exception
	     */
	    public void invoiceRejected() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
	        try
	        {
	        	PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
	        	s_vo.setValue(hm);
	        	s_vo.setInvoiceStatus(DicConstant.GYSKPZT_05);
	        	s_vo.setUpdateUser(user.getAccount());
	        	s_vo.setUpdateTime(Pub.getCurrentDate());
	        	dao.updateSum(s_vo);
	        	String SUM_ID = hm.get("SUM_ID");
	        	String status = DicConstant.GYSKPZT_05;
	        	dao.updatePchOrder(SUM_ID,status);
	        	dao.updateRetOrder(SUM_ID,status);
	        	dao.updateSumDtl(SUM_ID,status);
	            atx.setOutMsg("","开票驳回成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年8月14日下午4:48:15
	     * @author Administrator
	     * @to_do:开票通过
	     * @throws Exception
	     */
	    public void invoicePass() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
	        try
	        {
	        	PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
	        	s_vo.setValue(hm);
	        	String SUM_ID = hm.get("SUM_ID");
	        	QuerySet getSet = dao.getSet(SUM_ID);
	        	QuerySet getNo = dao.getNo(SUM_ID);
	        	String comNo = getNo.getString(1, "COM_NO");
	        	String oldNo = getSet.getString(1, "INVOICE_NO");
	        	String UNSETTLE_AMOUNT = getSet.getString(1, "UNSETTLE_AMOUNT");
	        	String HAS_INVOICE_AMOUNT = getSet.getString(1, "HAS_INVOICE_AMOUNT");
	        	String SETTLE_AMOUNT = getSet.getString(1, "SETTLE_AMOUNT");
	        	s_vo.setUpdateUser(user.getAccount());
	        	if(!"".equals(oldNo)&&oldNo!=null){
	        		s_vo.setInvoiceNo(oldNo+","+comNo);
	        	}else{
	        		s_vo.setInvoiceNo(comNo);
	        	}
	        	s_vo.setUpdateTime(Pub.getCurrentDate());
	        	s_vo.setUnsettleAmount(String.valueOf(Float.parseFloat(UNSETTLE_AMOUNT)+Float.parseFloat(HAS_INVOICE_AMOUNT)-Float.parseFloat(SETTLE_AMOUNT)));
	        	String status = DicConstant.GYSKPZT_04;
	        	dao.updateSum(s_vo);
	        	dao.updatePchOrder(SUM_ID,status);
	        	dao.updateRetOrder(SUM_ID,status);
	        	dao.updateSumDtl(SUM_ID,status);
	            atx.setOutMsg("","");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void invioceInfo() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        PageManager page = new PageManager();
	        String SUM_ID = request.getParamValue("SUM_ID");
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.invioceInfo(page, SUM_ID, conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }
}
