package com.org.dms.action.part.financeMng.transfer;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.transfer.TransferReportMngDao;
import com.org.dms.vo.part.PtBuAccountTransferVO;
import com.org.dms.vo.part.PtBuAccountVO;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class TransferReportMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private TransferReportMngDao dao = TransferReportMngDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年8月1日下午4:43:49
	     * @author Administrator
	     * @to_do:转账查询
	     * @throws Exception
	     */
	    public void transferSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.transferSearch(page,user,conditions);
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
	     * @date()2014年8月1日下午6:23:46
	     * @author Administrator
	     * @to_do: 获取账户余额 
	     * @throws Exception
	     */
	    public void getBalanceAmount() throws Exception {
	    	
	         User user = (User) atx.getSession().get(Globals.USER_KEY);
	         BaseResultSet bs = dao.getAccount(user);
	         atx.setOutData("bs", bs);
	    }
	    /**
	     * 
	     * @date()2014年8月1日下午6:40:26
	     * @author Administrator
	     * @to_do:新增转账信息
	     * @throws Exception
	     */
	    public void transferInsert() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try
	        {
	        	PtBuAccountTransferVO vo = new PtBuAccountTransferVO();
				HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				vo.setValue(hm);
				vo.setInType(DicConstant.ZJZHLX_03);
				vo.setOutType(DicConstant.ZJZHLX_05);
				vo.setApplyDate(Pub.getCurrentDate());
				vo.setTransferStatus(DicConstant.ZZZT_01);
				vo.setStatus(DicConstant.YXBS_01);
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());//
				vo.setOrgId(user.getOrgId());
				dao.insertTransfer(vo);
				atx.setOutMsg(vo.getRowXml(),"转账新增成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年8月1日下午6:51:38
	     * @author Administrator
	     * @to_do:转账修改
	     * @throws Exception
	     */
	    public void transferUpdate() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuAccountTransferVO tempVO = new PtBuAccountTransferVO();
	        try
	        {
	            HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				tempVO.setValue(hm);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());//
	            dao.updateTransfer(tempVO);
	            atx.setOutMsg(tempVO.getRowXml(),"转账信息修改成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年8月1日下午6:51:27
	     * @author Administrator
	     * @to_do:转账提报
	     * @throws Exception
	     */
	    public void transferReport() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuAccountTransferVO tempVO = new PtBuAccountTransferVO();
	        try
	        {
				 String TRANSFER_ID = Pub.val(request, "TRANSFER_ID");
				tempVO.setTransferId(TRANSFER_ID);
				tempVO.setTransferStatus(DicConstant.ZZZT_02);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateTransfer(tempVO);
				atx.setOutMsg("","转账请求提交成功！");	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年8月2日下午12:36:01
	     * @author Administrator
	     * @to_do:转账申请删除
	     * @throws Exception
	     */
	    public void transferDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String TRANSFER_ID = Pub.val(request, "TRANSFER_ID");
	            dao.transferDelete(TRANSFER_ID);
				atx.setOutMsg("","转账申请删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
