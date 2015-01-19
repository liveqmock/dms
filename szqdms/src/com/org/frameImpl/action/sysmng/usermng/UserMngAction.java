package com.org.frameImpl.action.sysmng.usermng;

import java.util.*;

import com.org.dms.common.DicConstant;
import com.org.frameImpl.dao.sysmng.usermng.UserMngDao;
import com.org.frameImpl.vo.TmUserVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.cache.CacheManager;
import com.org.framework.component.orgmanage.OrgDeptManager;
import com.org.framework.component.orgmanage.UserManager;
import com.org.framework.log.LogManager;
import com.org.framework.util.Encipher;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;

import org.apache.log4j.Logger;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 用户管理action
 */
public class UserMngAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OrgPersonAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private UserMngDao dao = UserMngDao.getInstance(atx);

    /**
     * 新增用户
     * @throws Exception
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
            TmUserVO vo = new TmUserVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			//将account转换为大写
			vo.setAccount(vo.getAccount().toUpperCase());
			//新建用户，初始化密码0000
			vo.setPassword("000000");
			vo.setPassword(Encipher.MD5Encryption(vo.getPassword()));
			//判断用户账号是否已存在
			QuerySet qs = dao.checkAccount(vo.getAccount());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("用户账号已存在，保存失败！");
				}
			}
			//设置组织code
			vo.setOrgCode(OrgDeptManager.getInstance().getDeptByID(vo.getOrgId()).getOrgCode());
			//设置通用字段
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setUpdateTime(Pub.getCurrentDate());
			//通过dao，执行插入
			dao.insertUser(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"用户新增成功！");
            //日志
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_INSERT,LogManager.RESULT_SUCCESS
                                    ,"添加用户 ["+vo.getAccount()+" / "+vo.getAccount()+"] 成功",user);
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
            //日志
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_FAILURE,"添加用户失败："+e.toString(), user);
        }
    }

    /**
     * 更新用户
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        TmUserVO tempVO = new TmUserVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			//设置中英文
			tempVO.setLanguage((String)request.getSession().get(Globals.SYS_LANGUAGE));
			//设置组织code
			tempVO.setOrgCode(OrgDeptManager.getInstance().getDeptByID(tempVO.getOrgId()).getOrgCode());
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			//通过dao，执行更新
            dao.updateUser(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"用户信息修改成功！");
          
            //日志
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE, LogManager.RESULT_SUCCESS
                    , "修改用户 [" + tempVO.getAccount() + " / " + tempVO.getPersonName() + "] 成功", user);
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
            //日志
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_FAILURE
                    ,"修改用户失败："+e.toString(),user);
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
    	String account = Pub.val(request, "account");
    	String action = Pub.val(request, "type");
    	int type = 0;
    	if("1".equals(action))
    		type = 1;
    	else if("2".equals(action))
    		type = 2;
    	else if("3".equals(action))
    		type = 3;
    	else if("4".equals(action))
    		type = 4;
    	//广播节点
        CacheManager.broadcastChanges(CacheManager.CACHE_USER,account.toUpperCase(), type);
    }
    
    /**
     * 删除用户
     * @throws Exception
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String userId = Pub.val(request, "userId");
        String account = Pub.val(request, "account");
        try
        {
            //更新用户为无效状态
            boolean res = dao.updateUserStatus(userId, DicConstant.YXBS_02);
            if(res)
            {
            	//删除用户对应角色
                dao.deleteUserRolesByAccount(userId);
                logger.info("@account [" + account + "] 注销成功...");
                //返回更新结果和成功信息
                atx.setOutMsg("","用户信息修改成功！");

                LogManager.writeUserLog("","",Globals.OPERATION_TYPE_DELETE,LogManager.RESULT_SUCCESS
                                        ,"删除用户 ["+account+"] 成功",user);
            }
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_DELETE,LogManager.RESULT_FAILURE
                    ,"删除用户失败："+e.toString(),user);
        }
    }


    /**
     * 修改密码
     * @throws Exception
     */
    public void chgPass() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String userid="",account="",oldPass,newPass;
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			oldPass = hm.get("OLDPASSWORD");
			if(oldPass == null) oldPass = "";
			newPass = hm.get("NEWPASSWORD");
			userid = hm.get("USER_ID");
			account = hm.get("ACCOUNT");
            String[][] res = dao.searchPassword(userid);
            String password = "";
            if (res != null && res.length > 0)
                password = res[0][0];	
            if (password == null)
                password = "";
            //对数据库中用户密码进行解密
            if (!password.equals(Encipher.MD5Encryption(oldPass)))
            {
                throw new Exception("旧密码不正确，请重新输入!");
            }
            //对用户密码进行加密
            newPass = Encipher.MD5Encryption(newPass);
            dao.updatePwd(userid, newPass);

            logger.info("@user [" + userid + "] 修改密码成功...");
            atx.setOutMsg("","修改密码成功.");
            //add by andy.ten@tom.com
            //广播节点
            //CacheManager.broadcastChanges(CacheManager.CACHE_USER,account, 2);
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_SUCCESS
                                    ,"用户 ["+userid+" / "+user.getPersonName()+"] 修改密码成功",user);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_FAILURE
                                    ,"用户 ["+userid+" / "+user.getPersonName()+"] 修改密码失败！",user);
        }
    }

    /**
     * @title: initPass 
     * @description: 密码重置方法
     * @throws Exception    设定文件 
     * @return void    返回类型 
     * @auther andy.ten@tom.com
     * @date 2014年6月27日 上午10:30:32
     */
    public void resetPass()
        throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String userId = Pub.val(request, "userId");
        String account = Pub.val(request, "account");
        try
        {
            String newPass = Encipher.MD5Encryption("000000");
            boolean res = dao.resetPass(userId, newPass);
            if(res)
            {
            	logger.info("@account [" + account + "] 密码重置成功...");
            	atx.setOutMsg("","重置密码成功！");
            	//add by andy.ten@tom.com
                //广播节点
                CacheManager.broadcastChanges(CacheManager.CACHE_USER,account, 2);
                LogManager.writeUserLog("","",Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_SUCCESS
                                        ,"初始化用户 ["+account+" / "+ UserManager.getInstance().getUserByAccount(account).getPersonName()+"] 密码成功",user);
            }
        }
        catch (Exception e)
        {
        	logger.error(e);
        	atx.setException(e);
            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_UPDATE,LogManager.RESULT_FAILURE
                                    ,"初始化用户 ["+account+" / "+UserManager.getInstance().getUserByAccount(account).getPersonName()+"] 密码失败！",user);
        }
    }

    

    /**
     * @title: search
     * @description: TODO(查询用户方法)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther andy.ten@tom.com
     * @date 2014年6月26日 下午2:27:54
     */
    public void search() throws Exception
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
			BaseResultSet bs = dao.search(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    public void searchUser() throws Exception
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
			BaseResultSet bs = dao.searchUser(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /*
     * 查询未分配的角色
     */
    public void selectUnGrantedRole()
            		throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String userId = Pub.val(request, "userId");
		page.setPageRows(100);
		try
		{
			//BaseResultSet bs = dao.selectUnGrantedRole(page,userId);//原始方法
			/**modify by gouwentan 对于用户名非ADMIN开头的，只查询渠道角色   2014-11-21**/
			BaseResultSet bs = dao.selectUnGrantedRoleNew(page,userId,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /*
     * 查询已分配的角色
     */
    public void selectGrantedRole()
            		throws Exception
    {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String userId = Pub.val(request, "userId");
		page.setPageRows(20);
		
		try
		{
			BaseResultSet bs = dao.selectGrantedRole(page,userId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
    
    public void saveRoleUser()
            		throws Exception
	{
		String account,userId,roleIds,roleCodes;
		RequestWrapper request = atx.getRequest();
		try
		{
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			account = hm.get("ACCOUNT");
			userId = hm.get("USERID");
			roleIds = hm.get("ROLEIDS");
			roleCodes = hm.get("ROLECODES");
			String[] listId = roleIds.split(",");
			String[] listCode = roleCodes.split(",");
			dao.saveRoleUser(userId,account, listId,listCode);
			//CacheManager.broadcastChanges(CacheManager.CACHE_USER,account,
			               //CacheManager.MAP_CHANGEED);
			atx.setOutMsg("","操作成功！");
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
}