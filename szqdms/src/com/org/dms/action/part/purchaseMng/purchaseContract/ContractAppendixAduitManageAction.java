package com.org.dms.action.part.purchaseMng.purchaseContract;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseContract.ContractAppendixAduitManageDao;
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

public class ContractAppendixAduitManageAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractAppendixAduitManageAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private ContractAppendixAduitManageDao dao = ContractAppendixAduitManageDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年7月3日 下午4:40:03
	     * @user()Sonia
	     * TODO:合同资料审核查询
	     * @throws Exception
	     */
	    public void contractSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.contractSearch(page,user,conditions);
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
	     * @date()2014年7月3日 下午3:43:09
	     * @user()Sonia
	     * TODO:合同备件查询
	     * @throws Exception
	     */
	    public void contractPartSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
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
	    /**
	     * 
	     * @date()2014年7月5日 下午4:29:39
	     * @user()Sonia
	     * TODO:合同资料(附件)查询
	     * @throws Exception
	     */
	    public void contractAppendixSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			RequestUtil.getConditionsWhere(request,page);
			String DETAIL_ID = Pub.val(request, "DETAIL_ID");
			try
			{
				BaseResultSet bs = dao.contractAppendixSearch(page,user,DETAIL_ID);
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
	     * @date()2014年7月4日 上午9:52:48
	     * @user()Sonia
	     * TODO:合同资料审核通过
	     * @throws Exception
	     */
	    public void appendixPass() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String CONTRACT_ID = hm.get("CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setContractStatus(DicConstant.CGHTZT_04);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg(tempVO.getRowXml(),"合同资料审核成功！");
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setValue(hm);
				c_po.setCheckStatus(DicConstant.CGHTZT_04);
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCheckUser(user.getAccount());
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
	     * @date()2014年7月4日 上午10:00:47
	     * @user()Sonia
	     * TODO:资料审核驳回
	     * @throws Exception
	     */
	    public void appendixRejected() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String CONTRACT_ID = hm.get("CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setContractStatus(DicConstant.CGHTZT_05);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg(tempVO.getRowXml(),"合同资料驳回成功！");
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setValue(hm);
				c_po.setCheckStatus(DicConstant.CGHTZT_05);
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCheckUser(user.getAccount());
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

}
