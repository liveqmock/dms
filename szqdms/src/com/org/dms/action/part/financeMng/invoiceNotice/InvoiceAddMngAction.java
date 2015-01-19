package com.org.dms.action.part.financeMng.invoiceNotice;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.invoiceNotice.InvoiceAddMngDao;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class InvoiceAddMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private InvoiceAddMngDao dao = InvoiceAddMngDao.getInstance(atx);
	    //定义全局变量END_DAY 结算日期
	    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100501");
	    String END_DAY_01 = userPara.getParavalue1();
		String END_DAY_02 = userPara.getParavalue2();
		/**
		 * 
		 * @date()2014年8月11日上午10:40:17
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
			String conditions = RequestUtil.getConditionsWhere(request,page);
			page.setPageRows(10000);
			try
			{
				String SUM_ID = Pub.val(request, "SUM_ID");
				BaseResultSet bs = dao.invioceOrderSearch(page,user,SUM_ID,conditions);
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
		 * @date()2014年8月11日上午10:42:41
		 * @author Administrator
		 * @to_do:将发票号插入订单表中
		 * @throws Exception
		 */
		public void insertInvoice() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	String SUM_ID = Pub.val(request, "SUM_ID");
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String ORDERIDS = hm.get("ORDERIDS");//订单ID
	            String TYPES = hm.get("TYPES");//订单类型
	            String INVOICENO = hm.get("INVOICENOS");//发票号
	            String AMOUNT = hm.get("INVOICEAMOUNTS");
	            String RMK = hm.get("REMARK");
	            String unAmount = hm.get("UNINVOICEAMOUNTS");
	            String hasAmount = hm.get("HASINVOICEAMOUNTS");
	            String[] ORDER_ID = ORDERIDS.split(",");
	            String[] TYPE = TYPES.split(",");
	            float amount = 0;
	            for (int i = 0; i < ORDER_ID.length; i++) {
	            	if (TYPE[i].equals("1")){
	            		PtBuPchOrderSplitVO p_vo = new PtBuPchOrderSplitVO();
	            		p_vo.setSplitId(ORDER_ID[i]);
	            		p_vo.setInvoiceNo(INVOICENO);
	            		p_vo.setInvoiceStatus(DicConstant.GYSKPZT_03);
	            		p_vo.setInvoiceDate(Pub.getCurrentDate());
	            		p_vo.setUpdateUser(user.getAccount());
	            		p_vo.setUpdateTime(Pub.getCurrentDate());
	            		dao.updatePch(p_vo);
	            		QuerySet  getAmount = dao.getAmount(ORDER_ID[i]);
	            		String PURCHASE_AMOUNT = getAmount.getString(1, 1);
	            		amount = amount+Float.parseFloat(PURCHASE_AMOUNT);
	            	}else {
	            		PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
	            		r_vo.setReturnId(ORDER_ID[i]);
	            		r_vo.setInvoiceNo(INVOICENO);
	            		r_vo.setInvoiceStatus(DicConstant.GYSKPZT_03);
	            		r_vo.setInvoiceDate(Pub.getCurrentDate());
	            		r_vo.setUpdateUser(user.getAccount());
	            		r_vo.setUpdateTime(Pub.getCurrentDate());
	            		dao.updateRet(r_vo);
					}
	            }
	            QuerySet getNo = dao.getNo(SUM_ID);
	            String oldNo = getNo.getString(1, "INVOICE_NO");
	            dao.reportSum(SUM_ID,INVOICENO,ORDERIDS);
//	            PtBuSupInvoiceSummaryVO ssVo = new PtBuSupInvoiceSummaryVO();
//	            ssVo.setSumId(SUM_ID);	
//	            if(!"".equals(oldNo)&&oldNo!=null){
//	            	ssVo.setInvoiceNo(oldNo+","+INVOICENO);
//	            }else{
//	            	ssVo.setInvoiceNo(INVOICENO);
//	            }
//	            ssVo.setHasInvoiceAmount(hasAmount);
//	            ssVo.setUnInvoiceAmount(unAmount);
//	            ssVo.setRemarks(RMK);
//	            ssVo.setInvoiceStatus(DicConstant.GYSKPZT_02);
//	            dao.updateSum(ssVo);
	            dao.updateSumSup(hm,user,SUM_ID);
	            //返回插入结果和成功信息
	            atx.setOutMsg("", "提交成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
		/**
		 * 
		 * @date()2014年8月11日上午10:42:41
		 * @author Administrator
		 * @to_do:修改明细表的开票状态在页面做判断条件
		 * @throws Exception
		 */
		public void addInvoice() throws Exception {
			RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	String SUM_ID = Pub.val(request, "SUM_ID");
	            HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String ORDERIDS = hm.get("ORDERIDS");//订单ID
	            String INVOICENO = hm.get("INVOICENOS");//发票号
	            dao.updateSumDtl(SUM_ID);
	            dao.addSumDtl(SUM_ID,INVOICENO,ORDERIDS);
	            dao.updateSumSup(hm,user,SUM_ID);
	            //返回插入结果和成功信息
	            atx.setOutMsg("", "录入发票号成功！");
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
		/**
		 * 
		 * @date()2014年8月11日下午4:39:39
		 * @author Administrator
		 * @to_do:更改订单开票状态
		 * @throws Exception
		 */
		public void finishInvoice() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        String SUM_ID = Pub.val(request, "SUM_ID");
	        try
	        {
				QuerySet qs = dao.getOrder(SUM_ID);
				for(int i =1;i<=qs.getRowCount();i++){
					String id = qs.getString(i, "ORDER_ID");
					String type = qs.getString(i, "TYPE");
					if(type.equals("1"))
					{
						PtBuPchOrderSplitVO p_vo = new PtBuPchOrderSplitVO();
						p_vo.setSplitId(id);
						p_vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
						p_vo.setUpdateUser(user.getAccount());
	            		p_vo.setUpdateTime(Pub.getCurrentDate());
	            		dao.updatePch(p_vo);
					}else {
						PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
						r_vo.setReturnId(id);
						r_vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
						r_vo.setUpdateUser(user.getAccount());
	            		r_vo.setUpdateTime(Pub.getCurrentDate());
	            		dao.updateRet(r_vo);
					}
				}
				PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
				s_vo.setSumId(SUM_ID);
				QuerySet  checkAll = dao.checkAll(SUM_ID);
				if(checkAll.getRowCount()>0){
					String S_NO = checkAll.getString(1, "S_NO");
					String R_NO = checkAll.getString(1, "R_NO");
					
					if("0".equals(R_NO)&&"0".equals(S_NO)){
						s_vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
					}else{
						s_vo.setInvoiceStatus(DicConstant.GYSKPZT_06);
					}
				}else{
					s_vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
				}
				dao.updateSum(s_vo);
				atx.setOutMsg("","发票提交成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
		public void getInvoiceNo() throws Exception {
	        RequestWrapper request = atx.getRequest();
	        PageManager page = new PageManager();
	        String SUM_ID = request.getParamValue("SUM_ID");
	        String conditions = RequestUtil.getConditionsWhere(request, page);
	        try {
	            BaseResultSet bs = dao.getInvoiceNo(page, SUM_ID, conditions);
	            atx.setOutData("bs", bs);
	        } catch (Exception e) {
	            logger.error(e);
	            atx.setException(e);
	        }
	    }

}
