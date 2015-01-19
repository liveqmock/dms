package com.org.dms.action.part.purchaseMng.purchaseContract;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseContract.ContractAppendixManageDao;
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

public class ContractAppendixManageAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractAppendixManageAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private ContractAppendixManageDao dao = ContractAppendixManageDao.getInstance(atx);

	    /**
	     * 
	     * @date()2014年7月1日 下午4:01:34
	     * @user()Sonia
	     * TODO:合同查询
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
				String orgId = user.getOrgId();
				QuerySet  checkNew = dao.checkNew(orgId);
				String orgType = checkNew.getString(1, 1);
				if((DicConstant.ZZLB_12).equals(orgType)){
					BaseResultSet bs = dao.contractSearch1(page,user,conditions);
					atx.setOutData("bs", bs);
				}else{
					BaseResultSet bs = dao.contractSearch2(page,user,conditions);
					atx.setOutData("bs", bs);
				}
				
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
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				String CONTRACT_ID = Pub.val(request,"CONTRACT_ID");
				BaseResultSet bs = dao.contractPartSearch(page,user,CONTRACT_ID, conditions);
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
	     * @date()2014年7月2日 下午4:37:00
	     * @user()Sonia
	     * TODO:合同资料删除
	     * @throws Exception
	     */
	    public void fileDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				String FJID = Pub.val(request, "FJID");
	            dao.updateFiles(FJID);
				atx.setOutMsg("","资料删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 
	     * @date()2014年7月3日 下午2:00:45
	     * @user()Sonia
	     * TODO:资料上传提交
	     * @throws Exception
	     */
	    public void uploadReport() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				 String CONTRACT_ID = Pub.val(request, "CONTRACT_ID");
				tempVO.setContractId(CONTRACT_ID);
				tempVO.setContractStatus(DicConstant.CGHTZT_03);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateContract(tempVO);
				atx.setOutMsg("","合同资料提交成功！");
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				c_po.setContractId(CONTRACT_ID);
				c_po.setCheckStatus(DicConstant.CGHTZT_03);
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
}
