package com.org.frameImpl.action.sysmng.orgmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.frameImpl.dao.sysmng.orgmng.CompanyMngDao;
import com.org.frameImpl.vo.TmCompanyVO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.framework.Globals;
import com.org.framework.common.User;
import com.org.framework.common.cache.CacheManager;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 公司管理类
 * @author andy
 *
 */
public class CompanyMngAction
{
    private Logger logger = com.org.framework.log.log.getLogger(
            "CompanyMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private CompanyMngDao dao = CompanyMngDao.getInstance(atx);
    
    /**
     * 新增方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 9:38:01 AM
     */
    public void insert()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		try
		{
			TmCompanyVO vo = new TmCompanyVO();
			HashMap<String,String> hm = new HashMap<String, String>();
			hm = RequestUtil.getValues(request);
			vo.setValue(hm); 
			//判断机构代码是否已存在
			QuerySet qs = dao.checkCompanyCode(vo.getCode());
			if(qs.getRowCount() > 0)
			{ 
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("公司代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			dao.insertCompany(vo);
            atx.setOutMsg(vo.getRowXml(), "操作成功！");
            
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 更新
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 9, 2014 2:14:30 PM
     */
    public void update()
        throws Exception
    { 
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        TmCompanyVO tempVO = new TmCompanyVO();
        try
        {
            HashMap<String,String> hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			//设置更新人、更新时间
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateCompany(tempVO);
            atx.setOutMsg(tempVO.getRowXml(), "操作成功！");
            
        } 
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 同步集群方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 1:35:17 PM
     */
    public void synchronize() 
        	throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String companyId = Pub.val(request, "companyId");
    	String action = Pub.val(request, "type");
    	int type = 0;
    	if("1".equals(action))
    		type = 1;
    	else if("2".equals(action))
    		type = 2;
    	else if("3".equals(action))
    		type = 3;
    	//广播节点
        CacheManager.broadcastChanges(CacheManager.CACHE_ORG_COMPANY,companyId, type);
    }
    /**
     * 删除方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 9:44:58 AM
     */
    public void delete()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        String str = "";
        try
        {
        	String companyId = Pub.val(request, "companyId");
        	if("".equals(companyId))
        		throw new Exception("无主键，删除失败！");
            
        	boolean res = dao.delete(companyId);
            if (res)
            { 
                logger.info("组织机构 [" + companyId + "] 删除...");
                str = "操作成功！";
                atx.setOutData("", str);
                
            }else
            {
            	atx.setOutMsg("", "删除失败！");
            }
        }
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    /**
     * 机构管理：查询机构信息
     * @throws Exception
     */
    public void search()
    		throws Exception
	{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page, user, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
}
