package com.org.dms.action.part.financeMng.invoiceMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.invoiceMng.DealerInvoiceMngDao;
import com.org.dms.vo.part.PtBuDealerInvoiceSummaryVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class DealerInvoiceMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerInvoiceMngDao dao = DealerInvoiceMngDao.getInstance(atx);
	    
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
	    public void invioceOrderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			page.setPageRows(10000);
			try
			{
				String SUM_ID = Pub.val(request, "SUM_ID");
				BaseResultSet bs = dao.invioceOrderSearch(page,user,SUM_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //finishInvoice
	    public void finishInvoice() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	String SUM_ID = Pub.val(request, "SUM_ID");
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String no = hm.get("NO");
	            PtBuDealerInvoiceSummaryVO vo = new PtBuDealerInvoiceSummaryVO();
	            vo.setSumId(SUM_ID);
	            vo.setValue(hm);
	            vo.setInvoiceStatus(DicConstant.KPZT_02);
	            vo.setExpressStatus(DicConstant.FPYJZT_01);
	            vo.setUpdateUser(user.getAccount());
	            vo.setUpdateTime(Pub.getCurrentDate());
	            vo.setInvoiceDate(Pub.getCurrentDate());
	            vo.setInvoiceNo(no);
	            dao.updateSum(vo);
	            dao.updateSaleStatus(SUM_ID,no);
	            dao.updateRetStatus(SUM_ID, no);
	            atx.setOutMsg("", "录入发票号成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    //searchSummary
	    public void searchSummary() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			
			try
			{
				BaseResultSet bs = dao.searchSummary(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    public void updateExpressInvoice() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuDealerInvoiceSummaryVO tempVO = new PtBuDealerInvoiceSummaryVO();
	        try
	        {
	            HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				tempVO.setValue(hm);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updateSum(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"邮寄信息添加成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    //expressInvoice
	    public void expressInvoice() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	String sumId = Pub.val(request, "SUM_ID");
				PtBuDealerInvoiceSummaryVO tempVO = new PtBuDealerInvoiceSummaryVO();
				tempVO.setSumId(sumId);
				tempVO.setExpressStatus(DicConstant.FPYJZT_02);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updateSum(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"邮寄成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void searchSign() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			
			try
			{
				BaseResultSet bs = dao.searchSign(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //invoiceSign
	    public void invoiceSign() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	String sumId = Pub.val(request, "SUM_ID");
				PtBuDealerInvoiceSummaryVO tempVO = new PtBuDealerInvoiceSummaryVO();
				tempVO.setSumId(sumId);
				tempVO.setExpressStatus(DicConstant.FPYJZT_03);
				tempVO.setSignUser(user.getAccount());
				tempVO.setSignDate(Pub.getCurrentDate());
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updateSum(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"签收成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
}
