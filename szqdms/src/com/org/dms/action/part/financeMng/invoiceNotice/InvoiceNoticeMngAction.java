package com.org.dms.action.part.financeMng.invoiceNotice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.invoiceNotice.InvoiceNoticeMngDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryDtlVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class InvoiceNoticeMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private InvoiceNoticeMngDao dao = InvoiceNoticeMngDao.getInstance(atx);
	    //定义全局变量END_DAY 结算日期
	    UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100501");
		String END_DAY_01 = userPara.getParavalue1();
		String END_DAY_02 = userPara.getParavalue2();
	    /**
	     * 
	     * @date()2014年8月7日下午4:17:01
	     * @author Administrator
	     * @to_do:开票通知查询
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
//				BaseResultSet bs = dao.invioceSearch(page,user,conditions,END_DAY_01,END_DAY_02);
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
	     * @date()2014年8月7日下午4:28:44
	     * @author Administrator
	     * @to_do:开票信息明细查询
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
	     * @date()2014年8月8日上午10:59:20
	     * @author Administrator
	     * @to_do:通知供应商开票
	     * @throws Exception
	     */
	    public void noticeSupplier() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        Calendar cal=Calendar.getInstance();    
	        int y = cal.get(Calendar.YEAR);
	        int m=cal.get(Calendar.MONTH);    
			try
			{
				String SUM_IDS=Pub.val(request, "SUM_ID");
				String SUM_ID[] = SUM_IDS.split(",");
				for(int a=0;a<SUM_ID.length;a++){
					QuerySet getUnAmount = dao.getUnAmount(SUM_ID[a]);
					String umAmount = getUnAmount.getString(1, "UN_AMOUNT");
					PtBuSupInvoiceSummaryVO vo = new PtBuSupInvoiceSummaryVO();
					vo.setSumId(SUM_ID[a]);
					vo.setUnInvoiceAmount(umAmount);
					vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
					dao.updateSup(vo);
					
					dao.updateSupDtl(SUM_ID[a]);
					/**
					 * 插入供应商开票汇总表
					 */
					
//					PtBuSupInvoiceSummaryVO c_vo = new PtBuSupInvoiceSummaryVO();
//					QuerySet aSet = dao.download(user, SUPPLIER_ID[a], END_DAY_01,END_DAY_02);
//					String SUPPLIER_NAME = aSet.getString(1, "SUPPLIER_NAME");
//					String SUPPLIER_CODE = aSet.getString(1, "SUPPLIER_CODE");
//					String IN_COUNT = aSet.getString(1, "STORGAE_COUNT");
//					String IN_AMOUNT = aSet.getString(1, "STORAGE_AMOUNT");
//					String RETURN_COUNT = aSet.getString(1, "RET_COUNT");
//					String RETURN_AMOUNT = aSet.getString(1, "RET_COUNT");
//					String INVOICE_AMOUNT = aSet.getString(1, "INVOICE_AMOUNT");
//					c_vo.setSupplierId(SUPPLIER_ID[a]);
//					c_vo.setSupplierCode(SUPPLIER_CODE);
//					c_vo.setSupplierName(SUPPLIER_NAME);
//					c_vo.setInCount(IN_COUNT);
//					c_vo.setInAmount(IN_AMOUNT);
//					c_vo.setReturnCount(RETURN_COUNT);
//					c_vo.setReturnAmount(RETURN_AMOUNT);
//					c_vo.setInvoiceAmount(INVOICE_AMOUNT);
//					c_vo.setInvoiceStatus(DicConstant.GYSKPZT_01);
//					c_vo.setSettleStatus(DicConstant.GYSJSZT_01);
//					c_vo.setInvoiceMonth(y+"-"+m);
//					c_vo.setStatus(DicConstant.YXBS_01);
//					c_vo.setCompanyId(user.getCompanyId());
//					c_vo.setOemCompanyId(user.getOemCompanyId());
//					c_vo.setCreateUser(user.getAccount());
//					c_vo.setCreateTime(Pub.getCurrentDate());//
//					c_vo.setOrgId(user.getOrgId());
//					dao.insertSummary(c_vo);
//					//获取采购拆分单信息
					QuerySet getPch = dao.getPchOrderId(SUM_ID[a]);//获取采购拆分单ID，更新开票状态
					
					for(int i = 1;i<=getPch.getRowCount();i++){
						/**
						 * 更改采购拆分单的开票信息
						 */
						String status = getPch.getString(i, "INVOICE_STATUS");
						if(DicConstant.GYSKPZT_01.equals(status)){
							PtBuPchOrderSplitVO p_vo = new PtBuPchOrderSplitVO();
							p_vo.setSplitId(getPch.getString(i, "ORDER_ID"));
							p_vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
							p_vo.setUpdateUser(user.getAccount());
							p_vo.setUpdateTime(Pub.getCurrentDate());
							dao.updatePch(p_vo);
						}
						
						
					}
					//获取采购退货单信息
					QuerySet getRet = dao.getRetOrderId(SUM_ID[a]);//获取采购退货单ID，更新开票状态
					
					for(int j = 1;j<=getRet.getRowCount();j++){
						/**
						 * 更改采购退货单的开票信息
						 */
						String status = getRet.getString(j, "INVOICE_STATUS");
						if(DicConstant.GYSKPZT_01.equals(status)){
							PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
							r_vo.setReturnId(getRet.getString(j, "ORDER_ID"));
							r_vo.setInvoiceStatus(DicConstant.GYSKPZT_02);
							r_vo.setUpdateUser(user.getAccount());
							r_vo.setUpdateTime(Pub.getCurrentDate());
							dao.updateRet(r_vo);
						}
						
					}
				}
				
				 atx.setOutMsg("","通知开票成功！");
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年8月13日上午10:24:37
	     * @author Administrator
	     * @to_do:供应商开票信息汇总下载
	     * @throws Exception
	     */
	    public void download()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	ResponseWrapper response= atx.getResponse();
	    	String SUPPLIER_ID = Pub.val(request, "SUPPLIER_ID");
	        try
	        {
	        	List<HeaderBean> header = new ArrayList<HeaderBean>();
		    	HeaderBean hBean = null;
		    	hBean = new HeaderBean();
	    		hBean.setName("SUPPLIER_NAME");
	    		hBean.setTitle("供应商名称");
	    		header.add(0,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("SUPPLIER_CODE");
	    		hBean.setTitle("供应商代码");
	    		header.add(1,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("STORGAE_COUNT");
	    		hBean.setTitle("采购入库数量汇总");
	    		header.add(2,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("STORAGE_AMOUNT");
	    		hBean.setTitle("采购金额汇总(元)");
	    		header.add(3,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("RET_COUNT");
	    		hBean.setTitle("退货数量汇总");
	    		header.add(4,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("RET_AMOUNT");
	    		hBean.setTitle("退货金额汇总(元)");
	    		header.add(4,hBean);
	    		hBean = new HeaderBean();
	    		hBean.setName("INVOICE_AMOUNT");
	    		hBean.setTitle("开票金额汇总(元)");
	    		header.add(4,hBean);
	    		QuerySet qs = dao.download(user,SUPPLIER_ID,END_DAY_01,END_DAY_02);
	    		ExportManager.exportFile(response.getHttpResponse(), "供应商开票汇总", header, qs);
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
