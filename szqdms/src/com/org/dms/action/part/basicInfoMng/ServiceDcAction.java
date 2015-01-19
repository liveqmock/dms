package com.org.dms.action.part.basicInfoMng;

import java.util.*;

import com.org.dms.dao.part.basicInfoMng.ServiceDcDao;
import com.org.dms.vo.part.PtBaServiceDcExtendsVO;
import com.org.dms.vo.part.PtBaServiceDcVO;
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
 * 配送关系管理action
 */
public class ServiceDcAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "ServiceDcAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ServiceDcDao dao = ServiceDcDao.getInstance(atx);

    /**
     * 新增配送关系
     * @throws Exception
     * @Author suoxiuli 2014-07-30
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
        	PtBaServiceDcVO vo = new PtBaServiceDcVO();
        	PtBaServiceDcExtendsVO extendsVo = new PtBaServiceDcExtendsVO();//继承后的VO
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			extendsVo.setValue(hm);
			
			//判断配送关系是否存在
			QuerySet qs = dao.checkOrgIdIsDcId(vo.getDcId(), vo.getOrgId());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此配送关系已存在，保存失败！");
				}
			}
			
			// 判断渠道是否存在默认且已有效的配送中心，如果存在则不能新增。
			if(dao.checkOrgLinkStatus(vo.getOrgId()) && vo.getIfDefault().equals(DicConstant.SF_01)){
					throw new Exception("此渠道已存在默认且有效的配送中心");
			}
			
			//设置通用字段
			vo.setStatus(DicConstant.YXBS_01);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			//通过dao，执行插入
			dao.insertServiceDc(vo);
			
			//继承后的VO
			extendsVo.setRelationId(vo.getRelationId());
			extendsVo.setStatus(DicConstant.YXBS_01);
			extendsVo.setCreateUser(user.getAccount());
			extendsVo.setCreateTime(vo.getCreateTime());
			
			//返回插入结果和成功信息
			atx.setOutMsg(extendsVo.getRowXml(),"配送关系新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新配送关系
     * @throws Exception
     * @Author suoxiuli 2014-07-30
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaServiceDcVO tempVO = new PtBaServiceDcVO();
        PtBaServiceDcExtendsVO extendsTempVO = new PtBaServiceDcExtendsVO();//继承后的VO
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			extendsTempVO.setValue(hm);
			
			String oldStatus = hm.get("OLD_STATUS");
			String oldIfDefault = hm.get("OLD_IF_DEFAULT");
			//判断配送关系是否存在
			QuerySet qs = dao.checkOrgIdIsDcId(tempVO.getDcId(), tempVO.getOrgId());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0 && oldStatus.equals(tempVO.getStatus()) 
						&& oldIfDefault.equals(tempVO.getIfDefault()))
				{
					throw new Exception("此配送关系已存在，编辑失败！");
				}
			}
			
			// 判断渠道是否存在默认且已有效的配送中心，如果存在则不能新增。
			if(dao.checkOrgLinkStatus(tempVO.getOrgId()) && tempVO.getIfDefault().equals(DicConstant.SF_01)){
					throw new Exception("此渠道已存在默认且有效的配送中心");
			}
			
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
			//继承后的VO
			extendsTempVO.setUpdateUser(user.getAccount());
			extendsTempVO.setUpdateTime(tempVO.getUpdateTime());
			
            dao.updateServiceDc(tempVO);
            atx.setOutMsg(extendsTempVO.getRowXml(),"配送关系修改成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除配送关系
     * @throws Exception
     * @Author suoxiuli 2014-07-30
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String relationId = Pub.val(request, "relationId");
        try
        {
            //更新直发类型为无效状态
            boolean b = dao.updateServiceDcStatus(relationId,user.getAccount(),DicConstant.YXBS_02);
            atx.setOutMsg("","配送关系删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 配送关系查询
     * @throws Exception
     * Author suoxiuli 2014-07-30
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
    
    /**
     * 配送中心及渠道商查询
     * @throws Exception
     * Author suoxiuli 2014-08-30
     */
    public void serviceDcSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String flag = Pub.val(request, "flag");
		String orgType = "";
		
		if (flag.equals("1")) {
			orgType = "200005";
		} else {
			orgType = "200006,200007";
		}
		try
		{
			BaseResultSet bs = dao.serviceDcSearch(page,conditions,orgType);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
}