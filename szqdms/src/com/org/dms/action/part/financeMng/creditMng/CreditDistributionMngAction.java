package com.org.dms.action.part.financeMng.creditMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.creditMng.CreditDistributionMngDao;
import com.org.dms.vo.part.PtBuAccountVO;
import com.org.dms.vo.part.PtBuCreditAdjustLogVO;
import com.org.dms.vo.part.PtBuCreditLineVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class CreditDistributionMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    CreditDistributionMngDao dao = CreditDistributionMngDao.getInstance(atx);
	    
    /**
     * 
     * @date()2014年8月4日下午3:34:01
     * @author Administrator
     * @to_do:信用额度分配查询
     * @throws Exception
     */
    public void searchCredit() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchCredit(page,user,conditions);
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
     * @date()2014年8月4日下午3:26:36
     * @author Administrator
     * @to_do:信用额度分配
     * @throws Exception
     */
    public void creditInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	//新增信用额度
        	PtBuCreditLineVO vo = new PtBuCreditLineVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String ORG_ID = hm.get("ORG_ID");
			String NOW_LIMIT = hm.get("NOW_LIMIT");
			String CREDIT_TYPE = hm.get("CREDIT_TYPE");
			if(DicConstant.XYEDLX_01.equals(CREDIT_TYPE)){
				QuerySet ckeck = dao.checkInDate(ORG_ID);
				if(ckeck.getRowCount()>0){
					throw new Exception("该供应商固定额度尚未过期，不能再维护固定信用额度.");
				}else{
					vo.setValue(hm);
					vo.setStatus(DicConstant.YXBS_01);
					vo.setCompanyId(user.getCompanyId());
					vo.setOemCompanyId(user.getOemCompanyId());
					vo.setCreateUser(user.getAccount());
					vo.setCreateTime(Pub.getCurrentDate());//
					dao.insertCredit(vo);
					//修改渠道信用额度账户余额
					
					dao.updateAccountCredit(ORG_ID,NOW_LIMIT,user);
//					QuerySet qs= dao.getAccountId(ORG_ID);//获取信用账户ID 
//					String ACCOUNT_ID = qs.getString(1, "ACCOUNT_ID");
//					PtBuAccountVO u_vo = new PtBuAccountVO();
//					u_vo.setAccountId(ACCOUNT_ID);
//					u_vo.setBalanceAmount(NOW_LIMIT);
//					u_vo.setAvailableAmount(NOW_LIMIT);
//					u_vo.setUpdateUser(user.getAccount());
//					u_vo.setUpdateTime(Pub.getCurrentDate());
//					dao.updateAccount(u_vo);
					//插入信用额度异动表
					PtBuCreditAdjustLogVO l_vo = new PtBuCreditAdjustLogVO();
					l_vo.setLineId(vo.getLineId());
					l_vo.setBefLimit("0");
					l_vo.setAftLimit(NOW_LIMIT);
					l_vo.setAjustLimit(NOW_LIMIT);
					l_vo.setAjustUser(user.getAccount());
					l_vo.setAjustDate(Pub.getCurrentDate());
					l_vo.setCreateUser(user.getAccount());
					l_vo.setCreateTime(Pub.getCurrentDate());
					dao.inserCreditLog(l_vo);
					atx.setOutMsg(vo.getRowXml(),"信用额度新增成功！");
				}
			}else{
				vo.setValue(hm);
				vo.setStatus(DicConstant.YXBS_01);
				vo.setCompanyId(user.getCompanyId());
				vo.setOemCompanyId(user.getOemCompanyId());
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());//
				dao.insertCredit(vo);
				//修改渠道信用额度账户余额
				
				dao.updateAccountCredit(ORG_ID,NOW_LIMIT,user);
				//插入信用额度异动表
				PtBuCreditAdjustLogVO l_vo = new PtBuCreditAdjustLogVO();
				l_vo.setLineId(vo.getLineId());
				l_vo.setBefLimit("0");
				l_vo.setAftLimit(NOW_LIMIT);
				l_vo.setAjustLimit(NOW_LIMIT);
				l_vo.setAjustUser(user.getAccount());
				l_vo.setAjustDate(Pub.getCurrentDate());
				l_vo.setCreateUser(user.getAccount());
				l_vo.setCreateTime(Pub.getCurrentDate());
				dao.inserCreditLog(l_vo);
				atx.setOutMsg(vo.getRowXml(),"信用额度新增成功！");
			}
			
			
			
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

}
