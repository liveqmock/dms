package com.org.dms.action.part.marketMng.marketActiveMng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.marketMng.marketActiveMng.ProgramActiveDao;
import com.org.dms.vo.part.PtBuProActiveDealerVO;
import com.org.dms.vo.part.PtBuProActiveVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class ProgramActiveAction {

	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ProgramActiveAction");
    private ActionContext atx = ActionContext.getContext();
    private ProgramActiveDao dao = ProgramActiveDao.getInstance(atx);

    /**
     * 查询最新的活动代码
     * @throws Exception
     * Author suoxiuli 2014-10-14
     */
    public String newActiveCodeSearch() throws Exception
    {
		
		//获得当前1号日期
		Date date = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM");
		String currentMonth = df1.format(date)+"-01";
		String activeCode = "";
		
		try
        {
			//获得当前月份的最大活动代码
			QuerySet qs = dao.newActiveCodeSearch(currentMonth);
			
			if(qs.getRowCount() > 0)
			{
				String currMonthMaxActiveCode = qs.getString(1, 1);
				int max = Integer.parseInt(currMonthMaxActiveCode)+1;
				activeCode = max+"";
			} else {
				SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");
				activeCode = df2.format(date)+"000";
			}
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
		return activeCode;
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
        	PtBuProActiveVO vo = new PtBuProActiveVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断用户账号是否已存在
			/*QuerySet qs = dao.checkActiveCode(vo.getActiveCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("活动方案已存在，保存失败！");
				}
			}*/
			vo.setActiveCode(newActiveCodeSearch());
			vo.setActiveStatus("206001");
			vo.setOrgId(user.getOrgId());
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			dao.insertProActive(vo);
			atx.setOutMsg(vo.getRowXml(),"活动方案新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新活动方案制定
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuProActiveVO tempVO = new PtBuProActiveVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			String oldStatus = hm.get("OLD_STATUS");
			
			//判断用户账号是否已存在
			/*QuerySet qs = dao.checkActiveCode(tempVO.getActiveCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0 && oldStatus.equals(tempVO.getStatus()))
				{
					throw new Exception("活动方案已存在，编辑失败！");
				}
			}*/
			
			//设置通用字段
			tempVO.setOrgId(user.getOrgId());
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateProActive(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"直发类型修改成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除活动方案制定
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void delete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String activeId = Pub.val(request, "activeID");
        try
        {
            boolean b = dao.updateProActiveStatus(activeId, user.getAccount());
            atx.setOutMsg("","活动方案删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 活动方案制定查询
     * @throws Exception
     * Author suoxiuli 2014-09-17
     */
    public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String orgId = Pub.val(request, "orgId");
		try
		{
			BaseResultSet bs = dao.search(page,conditions,orgId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 活动方案对应渠道商查询
     * @throws Exception
     * Author suoxiuli 2014-09-17
     */
    public void proActiveDealerSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String activeId = Pub.val(request, "activeId");
		try
		{
			BaseResultSet bs = dao.proActiveDealerSearch(page,conditions,activeId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 渠道商信息查询
     * @throws Exception
     * Author suoxiuli 2014-09-17
     */
    public void dealerSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String activeId = Pub.val(request, "activeId");
		String bscCode = Pub.val(request, "bscCode");
		try
		{
			BaseResultSet bs = dao.dealerSearch(page,conditions,activeId, bscCode);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 新增活动方案渠道商
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void insertDealers() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	String valDealers = Pub.val(request, "valDealers");
        	String[] orgIdArr = valDealers.split(",");
        	for (int i=0; i<orgIdArr.length; i++) {
	        	PtBuProActiveDealerVO vo = new PtBuProActiveDealerVO();
				HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				vo.setValue(hm);
				vo.setOrgId(orgIdArr[i]);
				vo.setStatus("100201");
				vo.setCreateUser(user.getAccount());
				vo.setCreateTime(Pub.getCurrentDate());
				dao.insertDealers(vo);
        	}
			atx.setOutMsg("","活动方案新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 删除活动方案渠道商信息
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void deleteProActiveDealers() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        try
        {
        	String activeDealersId = Pub.val(request, "activeDealersId");
        	dao.deleteProActiveDealers(activeDealersId);
			atx.setOutMsg("","活动方案渠道商删除成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
//活动方案下发
    /**
     * 活动方案下发
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void proActiveIssue() throws Exception
    {
        //获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String activeId = Pub.val(request, "activeId");
        try
        {
            //更新活动方案状态为已下发
            boolean b = dao.proActiveIssue(activeId, DicConstant.HDLCZT_02,user.getAccount());
            atx.setOutMsg("","活动方案下发成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
