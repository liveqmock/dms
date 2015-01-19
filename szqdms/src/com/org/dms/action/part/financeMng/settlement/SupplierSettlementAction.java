package com.org.dms.action.part.financeMng.settlement;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.settlement.SupplierSettlementDao;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuSupInvoiceSummaryVO;
import com.org.dms.vo.part.PtBuSupSettleLogVO;
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

public class SupplierSettlementAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private SupplierSettlementDao dao = SupplierSettlementDao.getInstance(atx);
	
	
	public void settleSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.settleSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	public void settleOrderSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String SUM_ID = Pub.val(request, "SUM_ID");
			BaseResultSet bs = dao.settleOrderSearch(page,user,SUM_ID);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	public void supplierSettle() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String SUM_IDS = hm.get("SUM_ID");//订单ID
            String SUM_ID[] = SUM_IDS.split(",");
            for(int i=0;i<SUM_ID.length;i++){
            	QuerySet getType = dao.getType(SUM_ID[i]);
            	if(getType.getRowCount()>0){
            		for(int j =1;j<=getType.getRowCount();j++){
            			String type = getType.getString(j, "TYPE");
                    	String orderId = getType.getString(j,"ORDER_ID");
                    	if("1".equals(type)){
                    		PtBuPchOrderSplitVO p_Vo = new PtBuPchOrderSplitVO();
                    		p_Vo.setSplitId(orderId);
                    		p_Vo.setSettleStatus(DicConstant.GYSJSZT_02);
                    		p_Vo.setSettleDate(Pub.getCurrentDate());
                    		p_Vo.setUpdateTime(Pub.getCurrentDate());
                    		p_Vo.setUpdateUser(user.getAccount());
                    		dao.updatePch(p_Vo);
                    	}else{
                    		PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
                    		r_vo.setReturnId(orderId);
                    		r_vo.setSettleStatus(DicConstant.GYSJSZT_02);
                    		r_vo.setSettleDate(Pub.getCurrentDate());
                    		r_vo.setUpdateTime(Pub.getCurrentDate());
                    		r_vo.setUpdateUser(user.getAccount());
                    		dao.updateRet(r_vo);
                    		
                    	}
            		}
            	}
            	QuerySet checkAll = dao.checkAll(SUM_ID[i]);
            	PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
            	s_vo.setSumId(SUM_ID[i]);
            	if(checkAll.getRowCount()>0){
            		s_vo.setSettleStatus(DicConstant.GYSJSZT_03);
            	}else{
            		s_vo.setSettleStatus(DicConstant.GYSJSZT_02);
            	}
            	dao.updateSum(s_vo);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "结算成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	
	
	public void orderSettle() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
			 String ORDER_ID = Pub.val(request, "ORDER_ID");
			 String type = Pub.val(request, "TYPE");
			 String SUM_ID = Pub.val(request, "SUM_ID");
			 if("1".equals(type)){
         		PtBuPchOrderSplitVO p_Vo = new PtBuPchOrderSplitVO();
         		p_Vo.setSplitId(ORDER_ID);
         		p_Vo.setSettleStatus(DicConstant.GYSJSZT_02);
         		p_Vo.setSettleDate(Pub.getCurrentDate());
         		p_Vo.setUpdateTime(Pub.getCurrentDate());
         		p_Vo.setUpdateUser(user.getAccount());
         		dao.updatePch(p_Vo);
         	}else{
         		PtBuPchReturnVO r_vo = new PtBuPchReturnVO();
         		r_vo.setReturnId(ORDER_ID);
         		r_vo.setSettleStatus(DicConstant.GYSJSZT_02);
         		r_vo.setSettleDate(Pub.getCurrentDate());
         		r_vo.setUpdateTime(Pub.getCurrentDate());
         		r_vo.setUpdateUser(user.getAccount());
         		dao.updateRet(r_vo);
         		
         	}
			QuerySet checkAll = dao.checkAll(SUM_ID);
         	PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
         	s_vo.setSumId(SUM_ID);
         	if(checkAll.getRowCount()>0){
         		s_vo.setSettleStatus(DicConstant.GYSJSZT_03);
         	}else{
         		s_vo.setSettleStatus(DicConstant.GYSJSZT_02);
         	}
         	dao.updateSum(s_vo);
			atx.setOutMsg("","订单结算成功！");
            
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	
	
	
	
	public void finishSettle() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
			 String SUM_ID = Pub.val(request, "SUM_ID");
			 String AMOUNT = Pub.val(request, "AMOUNT");
			 String ACCOUNT_TYPE = Pub.val(request, "ACCOUNT_TYPE");
			 HashMap<String, String> hm;
	            hm = RequestUtil.getValues(request);
	            String ORDERIDS = hm.get("ORDERIDS");//订单ID
	            String ORDER_ID[] = ORDERIDS.split(",");
	            for(int i = 0;i<ORDER_ID.length;i++){
	            	PtBuPchOrderSplitVO p_vo = new PtBuPchOrderSplitVO();
	        		p_vo.setSplitId(ORDER_ID[i]);
	        		p_vo.setSettleStatus(DicConstant.GYSJSZT_02);
	        		p_vo.setUpdateUser(user.getAccount());
	        		p_vo.setUpdateTime(Pub.getCurrentDate());
	        		dao.updatePch(p_vo);
	            }
	            dao.updatePchOrder(SUM_ID);
/*         	PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
         	s_vo.setSumId(SUM_ID);
         	s_vo.setSettleAmount(AMOUNT);
         	if(checkAll.getRowCount()>0){
         		s_vo.setSettleStatus(DicConstant.GYSJSZT_03);
         	}else{
         		s_vo.setSettleStatus(DicConstant.GYSJSZT_02);
         	}
         	dao.updateSum(s_vo);*/
			dao.upSumAmount(SUM_ID,AMOUNT,ACCOUNT_TYPE,user);
			atx.setOutMsg("","订单结算成功！");
            
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	public void settleModSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.settleModSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	//settleUpdate
	public void settleUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String,String> hm;
            hm = RequestUtil.getValues(request);
            
            String SUM_ID = Pub.val(request, "SUM_ID");
            String AMOUNT = hm.get("AMOUNT");
            String REMARKS = hm.get("REMARKS");
             /**
              * 更改结算单结算金额
              */
            dao.modSettle(SUM_ID,AMOUNT,user);
            /**
             * 插入调整日志表
             */
            QuerySet getSet = dao.getSuoSet(SUM_ID);
            QuerySet getId = dao.getId();
            PtBuSupSettleLogVO vo = new PtBuSupSettleLogVO();
            vo.setLogId(getId.getString(1, 1));
            vo.setSumId(SUM_ID);
            vo.setAmount(AMOUNT);
            if(!"".equals(REMARKS)&&REMARKS!=null){
            	vo.setRemarks(REMARKS);	
            }
            vo.setAmountType(getSet.getString(1, "ACCOUNT_TYPE"));
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setCreateUser(user.getAccount());
            dao.insertLog(vo);
            atx.setOutMsg("","操作成功");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	
	public void searchImport() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            RequestWrapper request = atx.getRequest();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)dao.searchImport(pageManager, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
	
	public void insertImport() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
        	User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 预测主键
            String tmpNo = Pub.val(request, "tmpNo");
        	QuerySet getTmp = dao.getTmp(user,tmpNo);
        	for(int i = 0;i<getTmp.getRowCount();i++){
        		String SumId = getTmp.getString(i+1, "SUM_ID");
        		String accountType = getTmp.getString(i+1, "TYPE");
            	String amount = getTmp.getString(i+1, "AMOUNT");
            	String settleAmount = getTmp.getString(i+1, "SETTLE_AMOUNT");
            	PtBuSupInvoiceSummaryVO s_vo = new PtBuSupInvoiceSummaryVO();
            	s_vo.setSumId(SumId);
            	s_vo.setSettleAmount(settleAmount);
            	s_vo.setAccountType(accountType);
            	s_vo.setSettleStatus(DicConstant.GYSJSZT_02);
            	s_vo.setUnsettleAmount(String.valueOf(Float.parseFloat(amount)-Float.parseFloat(settleAmount)));
            	dao.updateSum(s_vo);

            	dao.updateOrder(SumId,user);
        	}
        	
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	
	public void expData()throws Exception{
    	RequestWrapper request = atx.getRequest();
        ResponseWrapper response = atx.getResponse();
        String tmpNo = Pub.val(request, "tmpNo");
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("TMP_NO");
            hBean.setTitle("序号");
            header.add(0, hBean);
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(1, hBean);
            hBean = new HeaderBean();
            hBean.setName("ERP_NO");
            hBean.setTitle("ERP编码");
            header.add(2, hBean);
            hBean = new HeaderBean();
            hBean.setName("ACCOUNT_TYPE");
            hBean.setTitle("账户类型");
            header.add(3, hBean);
            hBean = new HeaderBean();
            hBean.setName("SELECT_MONTH");
            hBean.setTitle("结算月度");
            header.add(4, hBean);
            hBean = new HeaderBean();
            hBean.setName("SETTLE_AMOUNT");
            hBean.setTitle("结算金额");
            header.add(5, hBean);
            QuerySet qs = dao.expData(tmpNo,user);
            ExportManager.exportFile(response.getHttpResponse(), "gysjsdrmb", header, qs);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
	
	public void exportExcel() throws Exception{
		ResponseWrapper response= atx.getResponse();
    	RequestWrapper requestWrapper = atx.getRequest();
    	PageManager page = new PageManager();
    	page.setPageRows(99999);
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商编码");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ERP_NO");
    		hBean.setTitle("ERP编码");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ACCOUNT_TYPE");
    		hBean.setTitle("账户类型");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("INVOICE_MONTH");
    		hBean.setTitle("结算月度");
    		header.add(hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_AMOUNT");
    		hBean.setTitle("结算金额");
    		header.add(hBean);
    		
    		QuerySet qs = dao.download(conditions,page);
    		os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
    		ExportManager.exportFile(response.getHttpResponse(), "供应商结算", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
        finally
        {
        	if (os != null)
            {
              os.close();
            }
        }
	}


}
