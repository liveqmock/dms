package com.org.dms.action.part.financeMng.creditMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.creditMng.CreditAdjustedMngDao;
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

public class CreditAdjustedMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    CreditAdjustedMngDao dao = CreditAdjustedMngDao.getInstance(atx);
	
	/**
	 * 
	 * @date()2014年8月5日下午2:20:17
	 * @author Administrator
	 * @to_do:信用额度查询
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
	 * @date()2014年8月5日下午3:14:47
	 * @author Administrator
	 * @to_do:信用额度调整
	 * @throws Exception
	 */
	public void creditAdjusted() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	//新增信用额度
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			//修改渠道信用额度账户余额
//			String ACCOUNT_ID = hm.get("ACCOUNT_ID");
			String LINE_ID = hm.get("LINE_ID");
			String REMARKS = hm.get("REMARK");
			String AFT_LIMIT = hm.get("AFT_LIMIT");
			String NOW_LIMIT = hm.get("NOW_LIMIT");
			String ORG_ID = hm.get("ORG_ID");
			
//			double ADJUST_LIMIT = Double.parseDouble(hm.get("ADJUST_LIMIT"));
//			QuerySet qs = dao.getLimit(ACCOUNT_ID);
//			double BALANCE_AMOUNT = Double.parseDouble(qs.getString(1, "BALANCE_AMOUNT"));
//			double AVAILABLE_AMOUNT = Double.parseDouble(qs.getString(1, "AVAILABLE_AMOUNT"));
//			double NOW_LIMIT = Double.parseDouble(qs.getString(1, "NOW_LIMIT"));
			//修改信用额度表
			PtBuCreditLineVO vo = new PtBuCreditLineVO();
			vo.setLineId(LINE_ID);
			vo.setNowLimit(AFT_LIMIT);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.updateCredit(vo);
			/*******更新账户信息表********/
			dao.updateCreditAccount(ORG_ID,NOW_LIMIT,AFT_LIMIT,user);
/*			if(Float.parseFloat(NOW_LIMIT)<Float.parseFloat(AFT_LIMIT)){
				
			}else{
				
			}*/
			//将调整完的信用额度加到渠道信用额度资金账户
//			PtBuAccountVO u_vo = new PtBuAccountVO();
//			u_vo.setAccountId(ACCOUNT_ID);
//			u_vo.setBalanceAmount(String.valueOf(BALANCE_AMOUNT+ADJUST_LIMIT));
//			u_vo.setAvailableAmount(String.valueOf(AVAILABLE_AMOUNT+ADJUST_LIMIT));
//			u_vo.setUpdateUser(user.getAccount());
//			u_vo.setUpdateTime(Pub.getCurrentDate());
//			dao.updateAccount(u_vo);
			//插入信用额度异动表
			PtBuCreditAdjustLogVO l_vo = new PtBuCreditAdjustLogVO();
			l_vo.setLineId(LINE_ID);
			l_vo.setBefLimit(NOW_LIMIT);
			l_vo.setAftLimit(NOW_LIMIT);
			l_vo.setAjustLimit(String.valueOf(Float.parseFloat(NOW_LIMIT)-Float.parseFloat(AFT_LIMIT)));
			l_vo.setAjustRemarks(REMARKS);
			l_vo.setAjustUser(user.getAccount());
			l_vo.setAjustDate(Pub.getCurrentDate());
			l_vo.setCreateUser(user.getAccount());
			l_vo.setCreateTime(Pub.getCurrentDate());
			dao.inserCreditLog(l_vo);
			atx.setOutMsg("","信用额度新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

}
