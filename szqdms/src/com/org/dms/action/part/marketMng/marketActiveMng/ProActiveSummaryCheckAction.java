package com.org.dms.action.part.marketMng.marketActiveMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.marketMng.marketActiveMng.ProActiveDealCheckDao;
import com.org.dms.dao.part.marketMng.marketActiveMng.ProActiveSummaryCheckDao;
import com.org.dms.vo.part.PtBuProActiveDealCheckVO;
import com.org.dms.vo.part.PtBuProActiveSummaryCheckVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class ProActiveSummaryCheckAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ProActiveSummaryCheckAction");
    private ActionContext atx = ActionContext.getContext();
    private ProActiveSummaryCheckDao dao = ProActiveSummaryCheckDao.getInstance(atx);

    /**
     * 活动总结提报结果查询
     * @throws Exception
     * Author suoxiuli 2014-09-26
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchProActiveSummary(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 活动总结审核通过|驳回
     * @throws Exception
     * @Author suoxiuli 2014-09-26
     */
    public void check() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String checkResult = Pub.val(request, "checkResult");
        try
        {
        	PtBuProActiveSummaryCheckVO vo = new PtBuProActiveSummaryCheckVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			vo.setCheckUser(user.getAccount());
			vo.setCheckDate(Pub.getCurrentDate());
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			dao.insertProActiveSummaryLogCheck(vo, checkResult);
			
			if (vo.getCheckResult().equals("302001")) {
				atx.setOutMsg(vo.getRowXml(),"活动总结审核通过！");
			}
			else {
				atx.setOutMsg(vo.getRowXml(),"活动总结审核驳回！");
			}
			
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 活动信息查询详细信息(主车厂和渠道商)
     * @throws Exception
     * Author suoxiuli 2014-10-13
     */
    public void searchQuery() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String orgId = Pub.val(request, "orgId");
		try
		{
			BaseResultSet bs = dao.searchProActiveDetail(page,conditions,orgId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
