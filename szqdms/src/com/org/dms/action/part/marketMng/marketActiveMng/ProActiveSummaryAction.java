package com.org.dms.action.part.marketMng.marketActiveMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.marketMng.marketActiveMng.ProActiveDealDao;
import com.org.dms.dao.part.marketMng.marketActiveMng.ProActiveSummaryDao;
import com.org.dms.vo.part.PtBuProActiveDealVO;
import com.org.dms.vo.part.PtBuProActiveSummaryVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.dms.common.DicConstant;

public class ProActiveSummaryAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ProActiveSummaryAction");
    private ActionContext atx = ActionContext.getContext();
    private ProActiveSummaryDao dao = ProActiveSummaryDao.getInstance(atx);

    /**
     * 活动方案总结查询
     * @throws Exception
     * Author suoxiuli 2014-09-26
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String orgId = Pub.val(request, "orgId");
		try
		{
			BaseResultSet bs = dao.searchProActiveSummary(page,conditions,orgId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 新增活动方案总结
     * @throws Exception
     * @Author suoxiuli 2014-09-26
     */
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBuProActiveSummaryVO vo = new PtBuProActiveSummaryVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			String summaryId = vo.getSummaryId();
			if (summaryId == "" || summaryId == null) { 
				//新增
				//判断执行方案是否已存在
				QuerySet qs = dao.checkDealId(vo.getDealId());
				if(qs.getRowCount() > 0)
				{
					String n = qs.getString(1, 1);
					if(Integer.parseInt(n) > 0)
					{
						throw new Exception("活动方案总结已存在，保存失败！");
					}
				}
				
				vo.setStatus(DicConstant.YXBS_01);
				vo.setSummaryStatus(DicConstant.HDZXZT_01);
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());
				dao.insertProActiveSummary(vo);
			}
			else {
				//编辑
				vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
	            dao.updateProActiveSummary(vo);
			}
			atx.setOutMsg(vo.getRowXml(),"活动总结编辑成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 活动方案总结提报
     * @throws Exception
     * @Author suoxiuli 2014-09-26
     */
    public void proActiveSummaryReport() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String summaryId = Pub.val(request, "summaryId");
        try
        {
            boolean b = dao.proActiveSummaryReport(summaryId, DicConstant.HDZXZT_02, user.getAccount());
            atx.setOutMsg("","活动总结提报成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    
}
