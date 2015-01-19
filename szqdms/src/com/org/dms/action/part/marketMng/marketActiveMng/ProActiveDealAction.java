package com.org.dms.action.part.marketMng.marketActiveMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.marketMng.marketActiveMng.ProActiveDealDao;
import com.org.dms.vo.part.PtBuProActiveDealVO;
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

public class ProActiveDealAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ProActiveDealAction");
    private ActionContext atx = ActionContext.getContext();
    private ProActiveDealDao dao = ProActiveDealDao.getInstance(atx);

    /**
     * 活动方案制定查询
     * @throws Exception
     * Author suoxiuli 2014-09-24
     */
    public void searchProActive() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String orgId = Pub.val(request, "orgId");
		try
		{
			BaseResultSet bs = dao.searchProActive(page, conditions, orgId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 新增活动方案制定
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBuProActiveDealVO vo = new PtBuProActiveDealVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setOrgId(user.getOrgId());
			
			String dealId = vo.getDealId();
			if (dealId == "" || dealId == null) { 
				//新增
				//判断用户账号是否已存在
				QuerySet qs = dao.checkActiveCodeAndOrgId(vo.getActiveCode(), vo.getOrgId());
				if(qs.getRowCount() > 0)
				{
					String n = qs.getString(1, 1);
					if(Integer.parseInt(n) > 0)
					{
						throw new Exception("活动方案已存在，保存失败！");
					}
				}
				
				vo.setStatus(DicConstant.YXBS_01);
				vo.setDealStatus(DicConstant.HDZXZT_01);
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());
				dao.insertProActiveDeal(vo);
			}
			else {
				//编辑
				vo.setUpdateUser(user.getAccount());
				vo.setUpdateTime(Pub.getCurrentDate());
	            dao.updateProActiveDeal(vo);
			}
			atx.setOutMsg(vo.getRowXml(),"活动方案编辑成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 活动执行方案提报
     * @throws Exception
     * @Author suoxiuli 2014-09-25
     */
    public void proActiveDealReport() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String dealId = Pub.val(request, "dealId");
        try
        {
            boolean b = dao.proActiveDealReport(dealId, DicConstant.HDZXZT_02, user.getAccount());
            atx.setOutMsg("","活动执行方案提报成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    
}
