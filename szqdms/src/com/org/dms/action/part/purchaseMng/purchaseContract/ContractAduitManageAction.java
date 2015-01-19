package com.org.dms.action.part.purchaseMng.purchaseContract;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseContract.ContractAduitManageDao;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractVO;
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

public class ContractAduitManageAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "ContractAduitManageAction");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private ContractAduitManageDao dao = ContractAduitManageDao.getInstance(atx);
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
	     * @date()2014年7月5日 上午9:38:11
	     * @user()Sonia
	     * TODO:合同审核通过
	     * @throws Exception
	     */
	    public void contractPass() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				c_po.setValue(hm);
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCheckUser(user.getAccount());
				c_po.setCheckStatus(DicConstant.CGHTZT_06);
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setCompanyId(user.getCompanyId());
				c_po.setOemCompany(user.getOemCompanyId());
				
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
				atx.setOutMsg("","合同审核成功！");

				String CONTRACT_ID = hm.get("CONTRACT_ID");
				QuerySet qs = dao.getPassNum(CONTRACT_ID);
				String num_1 = qs.getString(1, 1);
				/**
				 * 取审核通过率，如比系统设定值大，则更新合同主表状态为审核通过
				 */
				float rate =Float.parseFloat(num_1);
				
				UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100401");
				String paraValue = userPara.getParavalue1();
				float num_3 = Float.parseFloat(paraValue);
				if(rate>= num_3){
					tempVO.setContractId(CONTRACT_ID);
					tempVO.setContractStatus(DicConstant.CGHTZT_06);
					tempVO.setUpdateUser(user.getAccount());
					tempVO.setUpdateTime(Pub.getCurrentDate());
		            dao.updateContract(tempVO);
				}
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    /**
	     * 
	     * @date()2014年7月5日 上午9:38:11
	     * @user()Sonia
	     * TODO:合同审核驳回
	     * @throws Exception
	     */
	    public void contractRejected() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuPchContractVO tempVO = new PtBuPchContractVO();
	        try
	        {
				/**
				 * 插入合同生命轨迹
				 */
				PtBuPchContractCheckVO c_po = new PtBuPchContractCheckVO();
				HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				c_po.setValue(hm);
				c_po.setCheckDate(Pub.getCurrentDate());
				c_po.setCheckUser(user.getAccount());
				c_po.setCheckStatus(DicConstant.CGHTZT_07);
				c_po.setCreateUser(user.getAccount());
				c_po.setCreateTime(Pub.getCurrentDate());
				c_po.setOrgId(user.getOrgId());
				c_po.setCompanyId(user.getCompanyId());
				c_po.setOemCompany(user.getOemCompanyId());
				c_po.setStatus(DicConstant.YXBS_01);
				dao.insertContractTrack(c_po);
				atx.setOutMsg("","合同驳回成功！");
				
				
				
				String CONTRACT_ID = hm.get("CONTRACT_ID");
				QuerySet qs = dao.getPassNum(CONTRACT_ID);
				String num_1 = qs.getString(1, 1);
				/**
				 * 取审核通过率，如比系统设定值大，则更新合同主表状态为审核通过
				 */
				float rate =Float.parseFloat(num_1);
				
				UserParaConfigureVO userPara = (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("100401");
				String paraValue = userPara.getParavalue1();
				float num_3 = Float.parseFloat(paraValue);
				if(rate < num_3){
					tempVO.setContractId(CONTRACT_ID);
					tempVO.setContractStatus(DicConstant.CGHTZT_07);
					tempVO.setUpdateUser(user.getAccount());
					tempVO.setUpdateTime(Pub.getCurrentDate());
		            dao.updateContract(tempVO);
				}
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
