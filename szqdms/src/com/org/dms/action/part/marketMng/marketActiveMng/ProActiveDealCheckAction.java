package com.org.dms.action.part.marketMng.marketActiveMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.marketMng.marketActiveMng.ProActiveDealCheckDao;
import com.org.dms.vo.part.PtBuProActiveDealCheckVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class ProActiveDealCheckAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ProActiveDealCheckAction");
    private ActionContext atx = ActionContext.getContext();
    private ProActiveDealCheckDao dao = ProActiveDealCheckDao.getInstance(atx);

    /**
     * 活动方案制定查询
     * @throws Exception
     * Author suoxiuli 2014-09-24
     */
    public void searchProActiveDeal() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchProActiveDeal(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 活动执行方案审核通过|驳回
     * @throws Exception
     * @Author suoxiuli 2014-09-25
     */
    public void check() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String checkResult = Pub.val(request, "checkResult");
        try
        {
        	PtBuProActiveDealCheckVO vo = new PtBuProActiveDealCheckVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			vo.setCheckUser(user.getAccount());
			vo.setCheckDate(Pub.getCurrentDate());
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			dao.insertProActiveDealLogCheck(vo, checkResult);
			
			atx.setOutMsg(vo.getRowXml(),"活动方案编辑成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
}
