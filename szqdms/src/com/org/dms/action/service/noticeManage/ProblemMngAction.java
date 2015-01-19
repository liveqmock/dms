package com.org.dms.action.service.noticeManage;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.noticeManage.ProblemMngDao;
import com.org.dms.vo.service.MainProblemFeedbackVO;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Title: szqdms
 * @description: 渠道商反馈问题方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年11月12日 
 */
public class ProblemMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ProblemMngDao dao = ProblemMngDao.getInstance(atx);

    /**
     * 渠道商问题反馈描述
     * @throws Exception
     */
    public void ask() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainProblemFeedbackVO vo = new MainProblemFeedbackVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//设置通用字段
			vo.setApplyUser(user.getPersonName());//设置提报单位
			vo.setApplyDate(Pub.getCurrentDate());//设置提报时间
			vo.setProblemStatus(DicConstant.WTHFZT_01);//设置问题回复状态为"未回复"
			//通过dao，执行插入
			dao.ask(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"反馈问题成功，如需上传附件，请上传附件！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }
    
    /**
     * 本部回复反馈问题
     * @throws Exception
     */
    public void answer() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainProblemFeedbackVO vo = new MainProblemFeedbackVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//设置通用字段
			vo.setFeedbackUser(user.getPersonName());//设置反馈人
			vo.setFeedbackDate(Pub.getCurrentDate());//设置反馈时间
			vo.setProblemStatus(DicConstant.WTHFZT_02);//设置问题回复状态为"已回复"
			//通过dao，执行插入
			dao.answer(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"反馈问题回复成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 渠道商查询问题清单
     * @throws Exception
     */
    public void dealerSearch() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		
		//获取渠道商名称
		String dealerName = user.getPersonName();
		
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.dealerSearch(page,user,conditions,dealerName);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}   
    
    /**
     * 本部查询问题清单
     * @throws Exception
     */
    public void oemSearch() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.oemSearch(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}   
}