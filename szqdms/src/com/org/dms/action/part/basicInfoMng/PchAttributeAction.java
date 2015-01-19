package com.org.dms.action.part.basicInfoMng;

import java.util.HashMap;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PchAttributeDao;
import com.org.dms.vo.part.PtBaDirectTypeVO;
import com.org.dms.vo.part.PtBaPchAttributeVO;
import com.org.frameImpl.Constant;
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
 * 采购员属性管理action
 */
public class PchAttributeAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "PchAttributeAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PchAttributeDao dao = PchAttributeDao.getInstance(atx);

    /**
     * 采购员属性批量新增
     * @throws Exception
     * @Author suoxiuli 2014-07-22
     */
    public void batchInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String partIds = Pub.val(request, "partIds");
        
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 			String userAccount = hm.get("USER_ACCOUNT");
 			String userName = hm.get("PERSON_NAME");
 			QuerySet checkUnique = dao.checkUnique(partIds,userAccount);
 			if(!"".equals(checkUnique.getString(1, "CODES"))&&checkUnique.getString(1, "CODES")!=null){
 				throw new Exception("配件"+checkUnique.getString(1, "CODES")+"已有对应采购员,请核对");
 			}
 	 			
			dao.batchInsertPchAttribute(partIds, userAccount, userName, user
					.getAccount(), DicConstant.YXBS_01);
            
            atx.setOutMsg("","采购员属性批量新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 采购员属性单个修改
     * @throws Exception
     * @Author suoxiuli 2014-07-22
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaPchAttributeVO tempVO = new PtBaPchAttributeVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			
			/**
			//获取页面传过来的信息
			String pchAttributeId = hm.get("PCHATTRIBUTE_ID");
			String newUserAccount = hm.get("USER_ACCOUNT"); //新用户
			String oldUserAccount = hm.get("OLD_USER_ACCOUNT"); //旧用户
			
			//如果新用户和旧用户是一样的，直接跳出此方法
			if (newUserAccount.equals(oldUserAccount)) {
				atx.setOutMsg(tempVO.getRowXml(),"修改前后采购员一样！");
				return ;
			}
			
			//1、更新采购属性为无效状态(旧记录为无效)
            boolean b = dao.updatePchAttributeStatus(pchAttributeId, user.getAccount(), Constant.YXBS_02);
            
            //2、新增一条采购属性记录
            hm.put("PCHATTRIBUTE_ID", "");
            tempVO.setValue(hm);
            tempVO.setCreateUser(user.getAccount());
            tempVO.setCreateTime(Pub.getCurrentDate());
            */
			
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updatePchAttribute(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"采购员属性修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 批量更新库管员属性
     * @throws Exception
     * @Author suoxiuli 2014-07-22
     */
    public void batchUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String pchAttributeIds = Pub.val(request, "pchAttributeIds");
        
        try
        {
        	
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			
			String userAccount = hm.get("USER_ACCOUNT");
			String userName = hm.get("PERSON_NAME");
			String updateUser = user.getAccount();
            dao.batchUpdatePchAttribute(pchAttributeIds,userAccount,userName,updateUser);
            atx.setOutMsg("","采购员属性批量修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除库管员属性
     * @throws Exception
     * @Author suoxiuli 2014-07-22
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String pchAttributeIds = Pub.val(request, "pchAttributeIds");
        
        //设置通用字段
        String updateUser = user.getAccount();
		
        try
        {
            //更新采购属性为无效状态
            boolean b = dao.updatePchAttributeStatus(pchAttributeIds, updateUser, Constant.YXBS_02);
            atx.setOutMsg("","采购员属性删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询所有有效库管员属性
     * @throws Exception
     * Author suoxiuli 2014-07-22
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
     * 批量搜索没有采购员的配件
     * @throws Exception
     * Author suoxiuli 2014-07-22
     */
    public void searchNewPart() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchNewPart(page, conditions, DicConstant.YXBS_01, DicConstant.PJZT_01);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    
}