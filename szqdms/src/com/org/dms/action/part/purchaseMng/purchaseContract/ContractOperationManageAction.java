package com.org.dms.action.part.purchaseMng.purchaseContract;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseContract.ContractOperationManageDao;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class ContractOperationManageAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContactOperationManageAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private ContractOperationManageDao dao = ContractOperationManageDao.getInstance(atx);
	    /**
		 * 
		 * @date()2014年7月3日 下午7:02:30
		 * @user()Sonia
		 * TODO:合同签订页面查询
		 * @param page
		 * @param user
		 * @param conditions
		 * @return
		 * @throws Exception
		 */
	    public void signContractSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.signContractSearch(page,user,conditions);
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
	     * @date()2014年7月3日 下午7:05:45
	     * @user()Sonia
	     * TODO:合同签订
	     * @throws Exception
	     */
	    public void contractSign() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setContractStatus(DicConstant.CGHTZT_10);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
				tempVO.setSignDate(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg("","合同签订成功！");
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setContractId(CONTRACT_ID);
				c_po.setCheckStatus(DicConstant.CGHTZT_10);
				c_po.setCheckUser(user.getAccount());
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setCompanyId(user.getCompanyId());
				c_po.setOemCompany(user.getOemCompanyId());
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月3日 下午7:08:56
	     * @user()Sonia
	     * TODO:合同归档页面查询
	     * @throws Exception
	     */
	    public void fileContractSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.fileContractSearch(page,user,conditions);
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
	     * @date()2014年7月3日 下午7:09:34
	     * @user()Sonia
	     * TODO:合同归档
	     * @throws Exception
	     */
	    public void contractFile() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String CONTRACT_ID = hm.get("CONTRACT_ID");
				tempVO.setValue(hm);
				tempVO.setContractStatus(DicConstant.CGHTZT_09);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg("","合同归档成功！");
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setContractId(CONTRACT_ID);
				c_po.setCheckStatus(DicConstant.CGHTZT_09);
				c_po.setCheckUser(user.getAccount());
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setCompanyId(user.getCompanyId());
				c_po.setOemCompany(user.getOemCompanyId());
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    /**
	     * 
	     * @date()2014年7月3日 下午7:10:19
	     * @user()Sonia
	     * TODO:合同关闭页面查询
	     * @throws Exception
	     */
	    public void closeContractSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.closeContractSearch(page,user,conditions);
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
	     * @date()2014年7月3日 下午7:10:47
	     * @user()Sonia
	     * TODO:合同关闭
	     * @throws Exception
	     */
	    public void contractClose() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				 String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setContractStatus(DicConstant.CGHTZT_08);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg("","合同关闭成功！");
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setContractId(CONTRACT_ID);
				c_po.setCheckStatus(DicConstant.CGHTZT_08);
				c_po.setCheckUser(user.getAccount());
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setOemCompany(user.getOemCompanyId());
				c_po.setCompanyId(user.getCompanyId());
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void contractInfoSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				BaseResultSet bs = dao.contractInfo(page,user,conditions,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void contractPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			try
			{
				String CONTRACT_ID = Pub.val(request,"CONTRACT_ID");
				BaseResultSet bs = dao.contractPartSearch(page,user,CONTRACT_ID);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}


}
