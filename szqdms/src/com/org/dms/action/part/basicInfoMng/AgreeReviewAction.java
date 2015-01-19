package com.org.dms.action.part.basicInfoMng;

import java.util.*;

import com.org.dms.dao.part.basicInfoMng.AgreeReviewDao;
import com.org.dms.vo.part.PtBaAgreeReviewVO;
import com.org.dms.common.DicConstant;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import org.apache.log4j.Logger;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 合同评审组管理action
 */
public class AgreeReviewAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "AgreeReviewAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private AgreeReviewDao dao = AgreeReviewDao.getInstance(atx);

    /**
     * 新增合同评审组成员
     * @throws Exception
     * @Author suoxiuli 2014-07-26
     */
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBaAgreeReviewVO vo = new PtBaAgreeReviewVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断用户账号是否已存在
			QuerySet qs = dao.checkUserAccount(vo.getUserAccount());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("合同评审组成员已存在，保存失败！");
				}
			}
			
			//设置通用字段
			vo.setStatus(DicConstant.YXBS_01);
			vo.setCreateUser(user.getPersonName());
			vo.setCreateTime(Pub.getCurrentDate());
			//通过dao，执行插入
			dao.insertAgreeReview(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"合同评审组成员新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新合同评审组成员
     * @throws Exception
     * @Author suoxiuli 2014-07-26
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaAgreeReviewVO tempVO = new PtBaAgreeReviewVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateAgreeReview(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"合同评审组成员修改成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除合同评审组成员
     * @throws Exception
     * @Author suoxiuli 2014-07-26
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String reviewId = Pub.val(request, "reviewId");
        try
        {
            //更新合同评审组成员为无效状态
            boolean b = dao.updateAgreeReviewStatus(reviewId,user.getAccount(),DicConstant.YXBS_02);
            atx.setOutMsg("","合同评审组成员删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 合同评审组成员查询
     * @throws Exception
     * Author suoxiuli 2014-07-26
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
}