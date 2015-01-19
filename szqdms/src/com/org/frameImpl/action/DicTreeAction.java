package com.org.frameImpl.action;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.org.frameImpl.dao.DicTreeDao;
import com.org.frameImpl.vo.DicTreeVO;
import com.org.framework.Globals;
import com.org.framework.dic.Dics;
import com.org.framework.log.LogManager;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.framework.common.AppInit;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.cache.CacheManager;
import com.org.framework.component.orgmanage.OrgDeptToXml;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;
/**
 * @Description:字典action类
 * @Copyright: Copyright (c) 2011
 * @Date: 2011-10-7
 * @mail   andy.ten@tom.com
 */
public class DicTreeAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger("DicTreeAction");
    
    Vector<Object> filterVectorTemp = new Vector<Object>();
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DicTreeDao dao = DicTreeDao.getInstance(atx);
    
    public void insert()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        DicTreeVO vo = new DicTreeVO();
        String pid = Pub.val(request, "pid");
        String dicName = Pub.val(request, "dicname");
        try
        {
            HashMap<String,String> hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			QuerySet qs = dao.checkCodeExist(vo.getDicCode());
			if(qs.getRowCount() > 0)
				throw new Exception("字典代码重复，请重新输入.");
			vo.setParentId(pid);
			vo.setDicNameCode(dicName);
			vo.setDicLayer(pid);
			vo.setIsLeaf("0");
            dao.insert(vo);
            atx.setOutMsg(vo.getRowXml(), "");
            
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE,
                                    LogManager.RESULT_SUCCESS,
                                    "字典["+vo.getDicCode()+"]新增：" + vo.getDicCode()+":"+vo.getDicValue(), user);
        } 
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }

    public void update()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        DicTreeVO vo = new DicTreeVO();
        String id = Pub.val(request, "id");
        String code = Pub.val(request, "code");
        String pid = Pub.val(request, "pid");
        String dicName = Pub.val(request, "dicname");
        try
        {
            HashMap<String,String> hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			//QuerySet qs = dao.checkCodeExist(vo.getDicCode());
			//if(qs.getRowCount() > 0)
				//throw new Exception("字典代码重复，请重新输入.");
			vo.setId(id);
			vo.setParentId(pid);
			vo.setDicCode(code);
			vo.setDicNameCode(dicName);
            dao.update(vo);
            atx.setOutMsg(vo.getRowXml(), "");
            
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE,
                                    LogManager.RESULT_SUCCESS,
                                    "字典["+vo.getDicCode()+"]变更：" + vo.getDicCode()+":"+vo.getDicValue(), user);
        } 
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }

    public void delete()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        DicTreeVO vo = new DicTreeVO();
        String id = Pub.val(request, "id");
        String code = Pub.val(request, "code");
        String pid = Pub.val(request, "pid");
        String dicName = Pub.val(request, "dicname");
        try
        {
            HashMap<String,String> hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setId(id);
			vo.setParentId(pid);
			vo.setDicCode(code);
			vo.setDicNameCode(dicName);
			if(pid.equals("0")) //根节点删除根节点及根节点下全部子节点
			{
				dao.deleteRoot(pid);
			}else
				dao.delete(vo);
            atx.setOutMsg(vo.getRowXml(), "");
            
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE,
                                    LogManager.RESULT_SUCCESS,
                                    "字典["+vo.getDicCode()+"]删除：" + vo.getDicCode()+":"+vo.getDicValue(), user);
        } 
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }

    public void synchronize() 
        	throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String dicCode = Pub.val(request, "code");
    	String dicPId = Pub.val(request, "pid");
    	String action = Pub.val(request, "type");
    	String dicName = Pub.val(request, "dicname");
    	int type = 0;
    	if("1".equals(action))
    		type = 1;
    	else if("2".equals(action))
    		type = 2;
    	else if("3".equals(action))
    		type = 3;
    	//广播节点
        CacheManager.broadcastChanges(CacheManager.CACHE_DIC,dicCode, type);
        //同步xml
		String path = AppInit.getAppPath();
		Dics.exportDicTreeXmlByType(path+"/dic/", dicPId, dicName);
    }
    
    public void checkExist(String pid)
        throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	ResponseWrapper response = atx.getResponse();
        String kind = Pub.val(request, "kind");
        String[][] result = new DBFactory().select(
            "select count(1) from dic_tree t where t.parent_id ='"+pid+"' and t.dic_value_spell ='" +
            kind + "'");
        response.setContentType("text/html;charset=UTF-8");

        if (result == null || result.length == 0)
        { //kind has not exist
            response.getWriter().print("ok");
        }
        else
        { //exist dictionary
            response.getWriter().print("err");
        }
    }
    
    /**
     * 查询字典方法
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Sep 9, 2014 10:25:47 AM
     */
    public void searchByParent()
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
    
    public void searchRoot()
    		throws Exception
	{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchRoot(page, user, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
    
    public void exportDic()
        throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	ResponseWrapper response = atx.getResponse();
        String dic_id = Pub.val(request, "dicId");
        String kind = Pub.val(request, "dicName");
        String ret = null;
        try
        {
            Dics.exportDicTreeXmlByType(request.getHttpRequest().getSession().getServletContext()
                                   .getRealPath("/dic"), dic_id, kind);
        }
        catch (Exception ex)
        {
        	logger.error(ex);
            atx.setException(ex);
            ret = ex.getMessage();
            if (ret == null)
            {
                ret = "操作失败!";
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        if (ret == null)
        {
            response.getWriter().println("操作成功!");
        }
        else
            response.getWriter().println(ret);
    }
    
    public void expOrg_DeptDic()
        throws Exception
    {
        //获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String path = request.getHttpRequest().getSession().getServletContext().getRealPath("/");
        //String ret = null;
        try
        {
            //组织机构xml文件
            OrgDeptToXml.exportAllXml(path, "ZZJG", "'1','2','3','4'");
            atx.setOutMsg("", "操作成功");
        }
        catch (Exception ex)
        {
        	logger.error(ex);
            atx.setException(ex);
        }
        finally
        {
            
        }
    }
}